package com.google.firebase.analytics.connector.internal;

import android.os.Bundle;
import com.google.android.gms.measurement.AppMeasurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzf implements AppMeasurement.OnEventListener {
    private final /* synthetic */ zzc zzacs;

    public zzf(zzc zzcVar) {
        this.zzacs = zzcVar;
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.OnEventListener, com.google.android.gms.measurement.internal.zzgn
    public final void onEvent(String str, String str2, Bundle bundle, long j) {
        if (this.zzacs.zzaci.contains(str2)) {
            Bundle bundle2 = new Bundle();
            bundle2.putString("events", zzd.zzdn(str2));
            this.zzacs.zzacj.onMessageTriggered(2, bundle2);
        }
    }
}
