package com.google.firebase.iid;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzh {
    private static final Executor zzaf = zzj.zzam;

    static final /* synthetic */ Thread zza(Runnable runnable) {
        return new Thread(runnable, "firebase-iid-executor");
    }

    static Executor zzd() {
        return zzaf;
    }

    static Executor zze() {
        return new ThreadPoolExecutor(0, 1, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue(), zzk.zzan);
    }
}
