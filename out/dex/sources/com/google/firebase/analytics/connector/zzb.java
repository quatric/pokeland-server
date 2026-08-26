package com.google.firebase.analytics.connector;

import java.util.concurrent.Executor;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final /* synthetic */ class zzb implements Executor {
    static final Executor zzacf = new zzb();

    private zzb() {
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        runnable.run();
    }
}
