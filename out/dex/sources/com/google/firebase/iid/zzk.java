package com.google.firebase.iid;

import java.util.concurrent.ThreadFactory;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final /* synthetic */ class zzk implements ThreadFactory {
    static final ThreadFactory zzan = new zzk();

    private zzk() {
    }

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        return zzh.zza(runnable);
    }
}
