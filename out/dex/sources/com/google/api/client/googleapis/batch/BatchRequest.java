package com.google.api.client.googleapis.batch;

import com.google.api.client.http.BackOffPolicy;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.MultipartContent;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class BatchRequest {
    private final HttpRequestFactory requestFactory;
    private GenericUrl batchUrl = new GenericUrl("https://www.googleapis.com/batch");
    List<RequestInfo<?, ?>> requestInfos = new ArrayList();
    private Sleeper sleeper = Sleeper.DEFAULT;

    class BatchInterceptor implements HttpExecuteInterceptor {
        private HttpExecuteInterceptor originalInterceptor;

        BatchInterceptor(HttpExecuteInterceptor httpExecuteInterceptor) {
            this.originalInterceptor = httpExecuteInterceptor;
        }

        @Override // com.google.api.client.http.HttpExecuteInterceptor
        public void intercept(HttpRequest httpRequest) throws IOException {
            HttpExecuteInterceptor httpExecuteInterceptor = this.originalInterceptor;
            if (httpExecuteInterceptor != null) {
                httpExecuteInterceptor.intercept(httpRequest);
            }
            for (RequestInfo<?, ?> requestInfo : BatchRequest.this.requestInfos) {
                HttpExecuteInterceptor interceptor = requestInfo.request.getInterceptor();
                if (interceptor != null) {
                    interceptor.intercept(requestInfo.request);
                }
            }
        }
    }

    static class RequestInfo<T, E> {
        final BatchCallback<T, E> callback;
        final Class<T> dataClass;
        final Class<E> errorClass;
        final HttpRequest request;

        RequestInfo(BatchCallback<T, E> batchCallback, Class<T> cls, Class<E> cls2, HttpRequest httpRequest) {
            this.callback = batchCallback;
            this.dataClass = cls;
            this.errorClass = cls2;
            this.request = httpRequest;
        }
    }

    public BatchRequest(HttpTransport httpTransport, HttpRequestInitializer httpRequestInitializer) {
        this.requestFactory = httpRequestInitializer == null ? httpTransport.createRequestFactory() : httpTransport.createRequestFactory(httpRequestInitializer);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void execute() throws IOException {
        boolean z;
        Preconditions.checkState(!this.requestInfos.isEmpty());
        HttpRequest httpRequestBuildPostRequest = this.requestFactory.buildPostRequest(this.batchUrl, null);
        httpRequestBuildPostRequest.setInterceptor(new BatchInterceptor(httpRequestBuildPostRequest.getInterceptor()));
        int numberOfRetries = httpRequestBuildPostRequest.getNumberOfRetries();
        BackOffPolicy backOffPolicy = httpRequestBuildPostRequest.getBackOffPolicy();
        if (backOffPolicy != null) {
            backOffPolicy.reset();
        }
        do {
            z = numberOfRetries > 0;
            MultipartContent multipartContent = new MultipartContent();
            multipartContent.getMediaType().setSubType("mixed");
            Iterator<RequestInfo<?, ?>> it = this.requestInfos.iterator();
            int i = 1;
            while (it.hasNext()) {
                multipartContent.addPart(new MultipartContent.Part(new HttpHeaders().setAcceptEncoding(null).set("Content-ID", (Object) Integer.valueOf(i)), new HttpRequestContent(it.next().request)));
                i++;
            }
            httpRequestBuildPostRequest.setContent(multipartContent);
            HttpResponse httpResponseExecute = httpRequestBuildPostRequest.execute();
            try {
                BatchUnparsedResponse batchUnparsedResponse = new BatchUnparsedResponse(httpResponseExecute.getContent(), "--" + httpResponseExecute.getMediaType().getParameter("boundary"), this.requestInfos, z);
                while (batchUnparsedResponse.hasNext) {
                    batchUnparsedResponse.parseNextResponse();
                }
                httpResponseExecute.disconnect();
                List<RequestInfo<?, ?>> list = batchUnparsedResponse.unsuccessfulRequestInfos;
                if (list.isEmpty()) {
                    break;
                }
                this.requestInfos = list;
                if (batchUnparsedResponse.backOffRequired && backOffPolicy != null) {
                    long nextBackOffMillis = backOffPolicy.getNextBackOffMillis();
                    if (nextBackOffMillis != -1) {
                        try {
                            this.sleeper.sleep(nextBackOffMillis);
                        } catch (InterruptedException unused) {
                        }
                    }
                }
                numberOfRetries--;
            } catch (Throwable th) {
                httpResponseExecute.disconnect();
                throw th;
            }
        } while (z);
        this.requestInfos.clear();
    }

    public GenericUrl getBatchUrl() {
        return this.batchUrl;
    }

    public Sleeper getSleeper() {
        return this.sleeper;
    }

    public <T, E> BatchRequest queue(HttpRequest httpRequest, Class<T> cls, Class<E> cls2, BatchCallback<T, E> batchCallback) throws IOException {
        Preconditions.checkNotNull(httpRequest);
        Preconditions.checkNotNull(batchCallback);
        Preconditions.checkNotNull(cls);
        Preconditions.checkNotNull(cls2);
        this.requestInfos.add(new RequestInfo<>(batchCallback, cls, cls2, httpRequest));
        return this;
    }

    public BatchRequest setBatchUrl(GenericUrl genericUrl) {
        this.batchUrl = genericUrl;
        return this;
    }

    public BatchRequest setSleeper(Sleeper sleeper) {
        this.sleeper = (Sleeper) Preconditions.checkNotNull(sleeper);
        return this;
    }

    public int size() {
        return this.requestInfos.size();
    }
}
