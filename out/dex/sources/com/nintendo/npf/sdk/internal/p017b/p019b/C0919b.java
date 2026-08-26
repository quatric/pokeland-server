package com.nintendo.npf.sdk.internal.p017b.p019b;

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.b.b */
/* JADX INFO: compiled from: GzipCompressor.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0919b {
    /* JADX INFO: renamed from: a */
    public static final byte[] m1226a(byte[] bArr) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = null;
        try {
            try {
                GZIPOutputStream gZIPOutputStream2 = new GZIPOutputStream(byteArrayOutputStream);
                try {
                    gZIPOutputStream2.write(bArr);
                    gZIPOutputStream2.flush();
                    gZIPOutputStream2.close();
                    return byteArrayOutputStream.toByteArray();
                } catch (Exception e) {
                    e = e;
                    gZIPOutputStream = gZIPOutputStream2;
                    throw new IllegalStateException(e);
                } catch (Throwable th) {
                    th = th;
                    gZIPOutputStream = gZIPOutputStream2;
                    if (gZIPOutputStream != null) {
                        try {
                            gZIPOutputStream.close();
                        } catch (Exception unused) {
                        }
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
