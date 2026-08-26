package com.google.firebase.iid;

import java.util.concurrent.Executor;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final /* synthetic */ class zzj implements Executor {
    static final Executor zzam = new zzj();

    private zzj() {
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        runnable.run();
    }
}
