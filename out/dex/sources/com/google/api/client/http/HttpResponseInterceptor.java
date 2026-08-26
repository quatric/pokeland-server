package com.google.api.client.http;

import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface HttpResponseInterceptor {
    void interceptResponse(HttpResponse httpResponse) throws IOException;
}
