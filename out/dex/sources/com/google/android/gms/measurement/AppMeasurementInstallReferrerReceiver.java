package com.google.android.gms.measurement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.MainThread;
import com.google.android.gms.measurement.internal.zzez;
import com.google.android.gms.measurement.internal.zzfa;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class AppMeasurementInstallReferrerReceiver extends BroadcastReceiver implements zzfa {
    private zzez zzn;

    @Override // com.google.android.gms.measurement.internal.zzfa
    public final BroadcastReceiver.PendingResult doGoAsync() {
        return goAsync();
    }

    @Override // com.google.android.gms.measurement.internal.zzfa
    public final void doStartService(Context context, Intent intent) {
    }

    @Override // android.content.BroadcastReceiver
    @MainThread
    public final void onReceive(Context context, Intent intent) {
        if (this.zzn == null) {
            this.zzn = new zzez(this);
        }
        this.zzn.onReceive(context, intent);
    }
}
