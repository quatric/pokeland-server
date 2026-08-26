package com.google.common.p015io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtIncompatible
final class MultiInputStream extends InputStream {

    /* JADX INFO: renamed from: in */
    private InputStream f344in;

    /* JADX INFO: renamed from: it */
    private Iterator<? extends ByteSource> f345it;

    public MultiInputStream(Iterator<? extends ByteSource> it) throws IOException {
        this.f345it = (Iterator) Preconditions.checkNotNull(it);
        advance();
    }

    private void advance() throws IOException {
        close();
        if (this.f345it.hasNext()) {
            this.f344in = this.f345it.next().openStream();
        }
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        InputStream inputStream = this.f344in;
        if (inputStream == null) {
            return 0;
        }
        return inputStream.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        InputStream inputStream = this.f344in;
        if (inputStream != null) {
            try {
                inputStream.close();
            } finally {
                this.f344in = null;
            }
        }
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return false;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        InputStream inputStream = this.f344in;
        if (inputStream == null) {
            return -1;
        }
        int i = inputStream.read();
        if (i != -1) {
            return i;
        }
        advance();
        return read();
    }

    @Override // java.io.InputStream
    public int read(@Nullable byte[] bArr, int i, int i2) throws IOException {
        InputStream inputStream = this.f344in;
        if (inputStream == null) {
            return -1;
        }
        int i3 = inputStream.read(bArr, i, i2);
        if (i3 != -1) {
            return i3;
        }
        advance();
        return read(bArr, i, i2);
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        InputStream inputStream = this.f344in;
        if (inputStream == null || j <= 0) {
            return 0L;
        }
        long jSkip = inputStream.skip(j);
        if (jSkip != 0) {
            return jSkip;
        }
        if (read() == -1) {
            return 0L;
        }
        return this.f344in.skip(j - 1) + 1;
    }
}
