package com.fasterxml.jackson.core.p014io;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class MergedStream extends InputStream {

    /* JADX INFO: renamed from: _b */
    private byte[] f258_b;
    private final IOContext _ctxt;
    private final int _end;
    private final InputStream _in;
    private int _ptr;

    public MergedStream(IOContext iOContext, InputStream inputStream, byte[] bArr, int i, int i2) {
        this._ctxt = iOContext;
        this._in = inputStream;
        this.f258_b = bArr;
        this._ptr = i;
        this._end = i2;
    }

    private void _free() {
        byte[] bArr = this.f258_b;
        if (bArr != null) {
            this.f258_b = null;
            IOContext iOContext = this._ctxt;
            if (iOContext != null) {
                iOContext.releaseReadIOBuffer(bArr);
            }
        }
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.f258_b != null ? this._end - this._ptr : this._in.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        _free();
        this._in.close();
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        if (this.f258_b == null) {
            this._in.mark(i);
        }
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return this.f258_b == null && this._in.markSupported();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        byte[] bArr = this.f258_b;
        if (bArr == null) {
            return this._in.read();
        }
        int i = this._ptr;
        this._ptr = i + 1;
        int i2 = bArr[i] & 255;
        if (this._ptr >= this._end) {
            _free();
        }
        return i2;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.f258_b == null) {
            return this._in.read(bArr, i, i2);
        }
        int i3 = this._end - this._ptr;
        if (i2 > i3) {
            i2 = i3;
        }
        System.arraycopy(this.f258_b, this._ptr, bArr, i, i2);
        this._ptr += i2;
        if (this._ptr >= this._end) {
            _free();
        }
        return i2;
    }

    @Override // java.io.InputStream
    public void reset() throws IOException {
        if (this.f258_b == null) {
            this._in.reset();
        }
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        long j2;
        if (this.f258_b != null) {
            int i = this._end;
            int i2 = this._ptr;
            long j3 = i - i2;
            if (j3 > j) {
                this._ptr = i2 + ((int) j);
                return j;
            }
            _free();
            j2 = j3 + 0;
            j -= j3;
        } else {
            j2 = 0;
        }
        return j > 0 ? j2 + this._in.skip(j) : j2;
    }
}
