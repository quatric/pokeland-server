package com.google.api.client.http.javanet;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.util.Preconditions;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class NetHttpRequest extends LowLevelHttpRequest {
    private final HttpURLConnection connection;

    NetHttpRequest(HttpURLConnection httpURLConnection) {
        this.connection = httpURLConnection;
        httpURLConnection.setInstanceFollowRedirects(false);
    }

    @Override // com.google.api.client.http.LowLevelHttpRequest
    public void addHeader(String str, String str2) {
        this.connection.addRequestProperty(str, str2);
    }

    @Override // com.google.api.client.http.LowLevelHttpRequest
    public LowLevelHttpResponse execute() throws IOException {
        HttpURLConnection httpURLConnection = this.connection;
        if (getStreamingContent() != null) {
            String contentType = getContentType();
            if (contentType != null) {
                addHeader(HttpHeaders.CONTENT_TYPE, contentType);
            }
            String contentEncoding = getContentEncoding();
            if (contentEncoding != null) {
                addHeader(HttpHeaders.CONTENT_ENCODING, contentEncoding);
            }
            long contentLength = getContentLength();
            if (contentLength >= 0) {
                httpURLConnection.setRequestProperty(HttpHeaders.CONTENT_LENGTH, Long.toString(contentLength));
            }
            String requestMethod = httpURLConnection.getRequestMethod();
            if ("POST".equals(requestMethod) || HttpMethods.PUT.equals(requestMethod)) {
                httpURLConnection.setDoOutput(true);
                if (contentLength < 0 || contentLength > 2147483647L) {
                    httpURLConnection.setChunkedStreamingMode(0);
                } else {
                    httpURLConnection.setFixedLengthStreamingMode((int) contentLength);
                }
                OutputStream outputStream = httpURLConnection.getOutputStream();
                try {
                    getStreamingContent().writeTo(outputStream);
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        throw e;
                    }
                } catch (Throwable th) {
                    try {
                        outputStream.close();
                    } catch (IOException unused) {
                    }
                    throw th;
                }
            } else {
                Preconditions.checkArgument(contentLength == 0, "%s with non-zero content length is not supported", requestMethod);
            }
        }
        try {
            httpURLConnection.connect();
            return new NetHttpResponse(httpURLConnection);
        } catch (Throwable th2) {
            httpURLConnection.disconnect();
            throw th2;
        }
    }

    @Override // com.google.api.client.http.LowLevelHttpRequest
    public void setTimeout(int i, int i2) {
        this.connection.setReadTimeout(i2);
        this.connection.setConnectTimeout(i);
    }
}
