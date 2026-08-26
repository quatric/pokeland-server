package com.google.android.gms.measurement.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@WorkerThread
final class zzek implements Runnable {
    private final Throwable exception;
    private final String packageName;
    private final int status;
    private final zzel zzkv;
    private final byte[] zzkw;
    private final Map<String, List<String>> zzkx;

    private zzek(String str, zzel zzelVar, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        Preconditions.checkNotNull(zzelVar);
        this.zzkv = zzelVar;
        this.status = i;
        this.exception = th;
        this.zzkw = bArr;
        this.packageName = str;
        this.zzkx = map;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzkv.zza(this.packageName, this.status, this.exception, this.zzkw, this.zzkx);
    }
}
