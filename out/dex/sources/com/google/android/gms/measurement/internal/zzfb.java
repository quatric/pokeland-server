package com.google.android.gms.measurement.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfb implements Runnable {
    private final /* synthetic */ Context val$context;
    private final /* synthetic */ zzfj zzmu;
    private final /* synthetic */ zzef zzmv;
    private final /* synthetic */ long zzmx;
    private final /* synthetic */ Bundle zzmy;
    private final /* synthetic */ BroadcastReceiver.PendingResult zzmz;

    zzfb(zzez zzezVar, zzfj zzfjVar, long j, Bundle bundle, Context context, zzef zzefVar, BroadcastReceiver.PendingResult pendingResult) {
        this.zzmu = zzfjVar;
        this.zzmx = j;
        this.zzmy = bundle;
        this.val$context = context;
        this.zzmv = zzefVar;
        this.zzmz = pendingResult;
    }

    @Override // java.lang.Runnable
    public final void run() {
        long j = this.zzmu.zzac().zzlo.get();
        long j2 = this.zzmx;
        if (j > 0 && (j2 >= j || j2 <= 0)) {
            j2 = j - 1;
        }
        if (j2 > 0) {
            this.zzmy.putLong("click_timestamp", j2);
        }
        this.zzmy.putString("_cis", "referrer broadcast");
        zzfj.zza(this.val$context, (com.google.android.gms.internal.measurement.zzx) null).zzq().logEvent("auto", "_cmp", this.zzmy);
        this.zzmv.zzgs().zzao("Install campaign recorded");
        BroadcastReceiver.PendingResult pendingResult = this.zzmz;
        if (pendingResult != null) {
            pendingResult.finish();
        }
    }
}
