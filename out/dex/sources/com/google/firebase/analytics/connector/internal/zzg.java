package com.google.firebase.analytics.connector.internal;

import android.os.Bundle;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzg implements AppMeasurement.OnEventListener {
    private final /* synthetic */ zze zzacv;

    public zzg(zze zzeVar) {
        this.zzacv = zzeVar;
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.OnEventListener, com.google.android.gms.measurement.internal.zzgn
    public final void onEvent(String str, String str2, Bundle bundle, long j) {
        if (str == null || str.equals("crash") || !zzd.zzdk(str2)) {
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putString(AppMeasurementSdk.ConditionalUserProperty.NAME, str2);
        bundle2.putLong("timestampInMillis", j);
        bundle2.putBundle("params", bundle);
        this.zzacv.zzacj.onMessageTriggered(3, bundle2);
    }
}
