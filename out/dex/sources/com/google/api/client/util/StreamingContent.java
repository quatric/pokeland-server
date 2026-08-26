package com.google.api.client.util;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface StreamingContent {
    void writeTo(OutputStream outputStream) throws IOException;
}
