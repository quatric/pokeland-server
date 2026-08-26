package com.google.android.gms.internal.firebase_messaging;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface zzb {
    ExecutorService zza(ThreadFactory threadFactory, int i);

    ScheduledExecutorService zza(int i, ThreadFactory threadFactory, int i2);
}
