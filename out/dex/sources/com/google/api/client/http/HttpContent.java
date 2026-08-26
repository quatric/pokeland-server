package com.google.api.client.http;

import com.google.api.client.util.StreamingContent;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface HttpContent extends StreamingContent {
    long getLength() throws IOException;

    String getType();

    boolean retrySupported();

    @Override // com.google.api.client.util.StreamingContent
    void writeTo(OutputStream outputStream) throws IOException;
}
