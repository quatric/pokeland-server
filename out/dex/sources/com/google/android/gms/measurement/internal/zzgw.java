package com.google.android.gms.measurement.internal;

import android.os.Bundle;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzgw implements Runnable {
    private final /* synthetic */ zzgp zzpt;
    private final /* synthetic */ Bundle zzqg;

    zzgw(zzgp zzgpVar, Bundle bundle) {
        this.zzpt = zzgpVar;
        this.zzqg = bundle;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzpt.zzf(this.zzqg);
    }
}
