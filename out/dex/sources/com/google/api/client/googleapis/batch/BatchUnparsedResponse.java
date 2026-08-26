package com.google.api.client.googleapis.batch;

import com.google.api.client.http.BackOffPolicy;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.util.ByteStreams;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class BatchUnparsedResponse {
    boolean backOffRequired;
    private final String boundary;
    private final InputStream inputStream;
    private final List<BatchRequest.RequestInfo<?, ?>> requestInfos;
    private final boolean retryAllowed;
    boolean hasNext = true;
    List<BatchRequest.RequestInfo<?, ?>> unsuccessfulRequestInfos = new ArrayList();
    private int contentId = 0;

    private static class FakeLowLevelHttpRequest extends LowLevelHttpRequest {
        private List<String> headerNames;
        private List<String> headerValues;
        private InputStream partContent;
        private int statusCode;

        FakeLowLevelHttpRequest(InputStream inputStream, int i, List<String> list, List<String> list2) {
            this.partContent = inputStream;
            this.statusCode = i;
            this.headerNames = list;
            this.headerValues = list2;
        }

        @Override // com.google.api.client.http.LowLevelHttpRequest
        public void addHeader(String str, String str2) {
        }

        @Override // com.google.api.client.http.LowLevelHttpRequest
        public LowLevelHttpResponse execute() {
            return new FakeLowLevelHttpResponse(this.partContent, this.statusCode, this.headerNames, this.headerValues);
        }
    }

    private static class FakeLowLevelHttpResponse extends LowLevelHttpResponse {
        private List<String> headerNames;
        private List<String> headerValues;
        private InputStream partContent;
        private int statusCode;

        FakeLowLevelHttpResponse(InputStream inputStream, int i, List<String> list, List<String> list2) {
            this.headerNames = new ArrayList();
            this.headerValues = new ArrayList();
            this.partContent = inputStream;
            this.statusCode = i;
            this.headerNames = list;
            this.headerValues = list2;
        }

        @Override // com.google.api.client.http.LowLevelHttpResponse
        public InputStream getContent() {
            return this.partContent;
        }

        @Override // com.google.api.client.http.LowLevelHttpResponse
        public String getContentEncoding() {
            return null;
        }

        @Override // com.google.api.client.http.LowLevelHttpResponse
        public long getContentLength() {
            return 0L;
        }

        @Override // com.google.api.client.http.LowLevelHttpResponse
        public String getContentType() {
            return null;
        }

        @Override // com.google.api.client.http.LowLevelHttpResponse
        public int getHeaderCount() {
            return this.headerNames.size();
        }

        @Override // com.google.api.client.http.LowLevelHttpResponse
        public String getHeaderName(int i) {
            return this.headerNames.get(i);
        }

        @Override // com.google.api.client.http.LowLevelHttpResponse
        public String getHeaderValue(int i) {
            return this.headerValues.get(i);
        }

        @Override // com.google.api.client.http.LowLevelHttpResponse
        public String getReasonPhrase() {
            return null;
        }

        @Override // com.google.api.client.http.LowLevelHttpResponse
        public int getStatusCode() {
            return this.statusCode;
        }

        @Override // com.google.api.client.http.LowLevelHttpResponse
        public String getStatusLine() {
            return null;
        }
    }

    private static class FakeResponseHttpTransport extends HttpTransport {
        private List<String> headerNames;
        private List<String> headerValues;
        private InputStream partContent;
        private int statusCode;

        FakeResponseHttpTransport(int i, InputStream inputStream, List<String> list, List<String> list2) {
            this.statusCode = i;
            this.partContent = inputStream;
            this.headerNames = list;
            this.headerValues = list2;
        }

        @Override // com.google.api.client.http.HttpTransport
        protected LowLevelHttpRequest buildRequest(String str, String str2) {
            return new FakeLowLevelHttpRequest(this.partContent, this.statusCode, this.headerNames, this.headerValues);
        }
    }

    BatchUnparsedResponse(InputStream inputStream, String str, List<BatchRequest.RequestInfo<?, ?>> list, boolean z) throws IOException {
        this.boundary = str;
        this.requestInfos = list;
        this.retryAllowed = z;
        this.inputStream = inputStream;
        checkForFinalBoundary(readLine());
    }

    private void checkForFinalBoundary(String str) throws IOException {
        if (str.equals(this.boundary + "--")) {
            this.hasNext = false;
            this.inputStream.close();
        }
    }

    private HttpResponse getFakeResponse(int i, InputStream inputStream, List<String> list, List<String> list2) throws IOException {
        HttpRequest httpRequestBuildPostRequest = new FakeResponseHttpTransport(i, inputStream, list, list2).createRequestFactory().buildPostRequest(new GenericUrl(HttpTesting.SIMPLE_URL), null);
        httpRequestBuildPostRequest.setLoggingEnabled(false);
        httpRequestBuildPostRequest.setThrowExceptionOnExecuteError(false);
        return httpRequestBuildPostRequest.execute();
    }

    private <A, T, E> A getParsedDataClass(Class<A> cls, HttpResponse httpResponse, BatchRequest.RequestInfo<T, E> requestInfo) throws IOException {
        if (cls == Void.class) {
            return null;
        }
        return (A) requestInfo.request.getParser().parseAndClose(httpResponse.getContent(), httpResponse.getContentCharset(), (Class) cls);
    }

    private <T, E> void parseAndCallback(BatchRequest.RequestInfo<T, E> requestInfo, int i, HttpResponse httpResponse) throws IOException {
        BatchCallback<T, E> batchCallback = requestInfo.callback;
        HttpHeaders headers = httpResponse.getHeaders();
        HttpUnsuccessfulResponseHandler unsuccessfulResponseHandler = requestInfo.request.getUnsuccessfulResponseHandler();
        BackOffPolicy backOffPolicy = requestInfo.request.getBackOffPolicy();
        boolean z = false;
        this.backOffRequired = false;
        if (HttpStatusCodes.isSuccess(i)) {
            if (batchCallback == null) {
                return;
            }
            batchCallback.onSuccess((T) getParsedDataClass(requestInfo.dataClass, httpResponse, requestInfo), headers);
            return;
        }
        HttpContent content = requestInfo.request.getContent();
        boolean z2 = this.retryAllowed && (content == null || content.retrySupported());
        boolean zHandleResponse = unsuccessfulResponseHandler != null ? unsuccessfulResponseHandler.handleResponse(requestInfo.request, httpResponse, z2) : false;
        if (!zHandleResponse) {
            if (requestInfo.request.handleRedirect(httpResponse.getStatusCode(), httpResponse.getHeaders())) {
                z = true;
            } else if (z2 && backOffPolicy != null && backOffPolicy.isBackOffRequired(httpResponse.getStatusCode())) {
                this.backOffRequired = true;
            }
        }
        if (z2 && (zHandleResponse || this.backOffRequired || z)) {
            this.unsuccessfulRequestInfos.add(requestInfo);
        } else {
            if (batchCallback == null) {
                return;
            }
            batchCallback.onFailure((E) getParsedDataClass(requestInfo.errorClass, httpResponse, requestInfo), headers);
        }
    }

    private String readLine() throws IOException {
        return trimCrlf(readRawLine());
    }

    private String readRawLine() throws IOException {
        int i = this.inputStream.read();
        if (i == -1) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        while (i != -1) {
            sb.append((char) i);
            if (i == 10) {
                break;
            }
            i = this.inputStream.read();
        }
        return sb.toString();
    }

    private static InputStream trimCrlf(byte[] bArr) {
        int length = bArr.length;
        if (length > 0 && bArr[length - 1] == 10) {
            length--;
        }
        if (length > 0 && bArr[length - 1] == 13) {
            length--;
        }
        return new ByteArrayInputStream(bArr, 0, length);
    }

    private static String trimCrlf(String str) {
        if (str.endsWith("\r\n")) {
            return str.substring(0, str.length() - 2);
        }
        return str.endsWith("\n") ? str.substring(0, str.length() - 1) : str;
    }

    void parseNextResponse() throws IOException {
        String line;
        String line2;
        InputStream inputStreamTrimCrlf;
        String rawLine;
        this.contentId++;
        do {
            line = readLine();
            if (line == null) {
                break;
            }
        } while (!line.equals(""));
        int i = Integer.parseInt(readLine().split(" ")[1]);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        long j = -1;
        while (true) {
            line2 = readLine();
            if (line2 == null || line2.equals("")) {
                break;
            }
            String[] strArrSplit = line2.split(": ", 2);
            String str = strArrSplit[0];
            String str2 = strArrSplit[1];
            arrayList.add(str);
            arrayList2.add(str2);
            if (com.google.common.net.HttpHeaders.CONTENT_LENGTH.equalsIgnoreCase(str.trim())) {
                j = Long.parseLong(str2);
            }
        }
        if (j == -1) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                rawLine = readRawLine();
                if (rawLine == null || rawLine.startsWith(this.boundary)) {
                    break;
                } else {
                    byteArrayOutputStream.write(rawLine.getBytes("ISO-8859-1"));
                }
            }
            inputStreamTrimCrlf = trimCrlf(byteArrayOutputStream.toByteArray());
            line2 = trimCrlf(rawLine);
        } else {
            inputStreamTrimCrlf = new FilterInputStream(ByteStreams.limit(this.inputStream, j)) { // from class: com.google.api.client.googleapis.batch.BatchUnparsedResponse.1
                @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() {
                }
            };
        }
        parseAndCallback(this.requestInfos.get(this.contentId - 1), i, getFakeResponse(i, inputStreamTrimCrlf, arrayList, arrayList2));
        while (true) {
            if (inputStreamTrimCrlf.skip(j) <= 0 && inputStreamTrimCrlf.read() == -1) {
                break;
            }
        }
        if (j != -1) {
            line2 = readLine();
        }
        while (line2 != null && line2.length() == 0) {
            line2 = readLine();
        }
        checkForFinalBoundary(line2);
    }
}
