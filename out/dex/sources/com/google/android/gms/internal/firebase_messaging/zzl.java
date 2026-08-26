package com.google.android.gms.internal.firebase_messaging;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzl extends FilterInputStream {
    private long zzh;
    private long zzi;

    zzl(InputStream inputStream, long j) {
        super(inputStream);
        this.zzi = -1L;
        zzg.checkNotNull(inputStream);
        this.zzh = PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final int available() throws IOException {
        return (int) Math.min(this.in.available(), this.zzh);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final synchronized void mark(int i) {
        this.in.mark(i);
        this.zzi = this.zzh;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final int read() throws IOException {
        if (this.zzh == 0) {
            return -1;
        }
        int i = this.in.read();
        if (i != -1) {
            this.zzh--;
        }
        return i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final int read(byte[] bArr, int i, int i2) throws IOException {
        long j = this.zzh;
        if (j == 0) {
            return -1;
        }
        int i3 = this.in.read(bArr, i, (int) Math.min(i2, j));
        if (i3 != -1) {
            this.zzh -= (long) i3;
        }
        return i3;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final synchronized void reset() throws IOException {
        if (!this.in.markSupported()) {
            throw new IOException("Mark not supported");
        }
        if (this.zzi == -1) {
            throw new IOException("Mark not set");
        }
        this.in.reset();
        this.zzh = this.zzi;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final long skip(long j) throws IOException {
        long jSkip = this.in.skip(Math.min(j, this.zzh));
        this.zzh -= jSkip;
        return jSkip;
    }
}
