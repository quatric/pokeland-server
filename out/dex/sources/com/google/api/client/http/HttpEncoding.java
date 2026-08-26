package com.google.api.client.http;

import com.google.api.client.util.StreamingContent;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface HttpEncoding {
    void encode(StreamingContent streamingContent, OutputStream outputStream) throws IOException;

    String getName();
}
