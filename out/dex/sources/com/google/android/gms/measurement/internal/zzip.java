package com.google.android.gms.measurement.internal;

import android.content.ComponentName;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzip implements Runnable {
    private final /* synthetic */ ComponentName val$name;
    private final /* synthetic */ zzin zzrs;

    zzip(zzin zzinVar, ComponentName componentName) {
        this.zzrs = zzinVar;
        this.val$name = componentName;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzrs.zzrd.onServiceDisconnected(this.val$name);
    }
}
