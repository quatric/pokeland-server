package com.google.android.gms.tasks;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class CancellationTokenSource {
    private final zza zzc = new zza();

    public void cancel() {
        this.zzc.cancel();
    }

    public CancellationToken getToken() {
        return this.zzc;
    }
}
