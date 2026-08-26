package com.google.api.client.testing.util;

import com.google.api.client.util.Beta;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public class TestableByteArrayOutputStream extends ByteArrayOutputStream {
    private boolean closed;

    @Override // java.io.ByteArrayOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.closed = true;
    }

    public final byte[] getBuffer() {
        return this.buf;
    }

    public final boolean isClosed() {
        return this.closed;
    }
}
