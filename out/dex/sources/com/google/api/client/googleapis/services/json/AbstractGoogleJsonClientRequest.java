package com.google.api.client.googleapis.services.json;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonErrorContainer;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.json.JsonHttpContent;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class AbstractGoogleJsonClientRequest<T> extends AbstractGoogleClientRequest<T> {
    private final Object jsonContent;

    /* JADX WARN: Illegal instructions before constructor call */
    protected AbstractGoogleJsonClientRequest(AbstractGoogleJsonClient abstractGoogleJsonClient, String str, String str2, Object obj, Class<T> cls) {
        JsonHttpContent wrapperKey = null;
        if (obj != null) {
            wrapperKey = new JsonHttpContent(abstractGoogleJsonClient.getJsonFactory(), obj).setWrapperKey(abstractGoogleJsonClient.getObjectParser().getWrapperKeys().isEmpty() ? null : "data");
        }
        super(abstractGoogleJsonClient, str, str2, wrapperKey, cls);
        this.jsonContent = obj;
    }

    @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
    public AbstractGoogleJsonClient getAbstractGoogleClient() {
        return (AbstractGoogleJsonClient) super.getAbstractGoogleClient();
    }

    public Object getJsonContent() {
        return this.jsonContent;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
    public GoogleJsonResponseException newExceptionOnError(HttpResponse httpResponse) {
        return GoogleJsonResponseException.from(getAbstractGoogleClient().getJsonFactory(), httpResponse);
    }

    public final void queue(BatchRequest batchRequest, JsonBatchCallback<T> jsonBatchCallback) throws IOException {
        super.queue(batchRequest, GoogleJsonErrorContainer.class, jsonBatchCallback);
    }

    @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
    public AbstractGoogleJsonClientRequest<T> set(String str, Object obj) {
        return (AbstractGoogleJsonClientRequest) super.set(str, obj);
    }

    @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
    public AbstractGoogleJsonClientRequest<T> setDisableGZipContent(boolean z) {
        return (AbstractGoogleJsonClientRequest) super.setDisableGZipContent(z);
    }

    @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
    public AbstractGoogleJsonClientRequest<T> setRequestHeaders(HttpHeaders httpHeaders) {
        return (AbstractGoogleJsonClientRequest) super.setRequestHeaders(httpHeaders);
    }
}
