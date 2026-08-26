package com.google.firebase.analytics.connector.internal;

import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzc implements zza {
    private AppMeasurement zzacc;
    Set<String> zzaci;
    private AnalyticsConnector.AnalyticsConnectorListener zzacj;
    private zzf zzack = new zzf(this);

    public zzc(AppMeasurement appMeasurement, AnalyticsConnector.AnalyticsConnectorListener analyticsConnectorListener) {
        this.zzacj = analyticsConnectorListener;
        this.zzacc = appMeasurement;
        this.zzacc.registerOnMeasurementEventListener(this.zzack);
        this.zzaci = new HashSet();
    }

    @Override // com.google.firebase.analytics.connector.internal.zza
    public final void registerEventNames(Set<String> set) {
        this.zzaci.clear();
        Set<String> set2 = this.zzaci;
        HashSet hashSet = new HashSet();
        for (String str : set) {
            if (hashSet.size() >= 50) {
                break;
            } else if (zzd.zzdm(str) && zzd.zzdl(str)) {
                hashSet.add(zzd.zzdo(str));
            }
        }
        set2.addAll(hashSet);
    }

    @Override // com.google.firebase.analytics.connector.internal.zza
    public final void unregisterEventNames() {
        this.zzaci.clear();
    }

    @Override // com.google.firebase.analytics.connector.internal.zza
    public final AnalyticsConnector.AnalyticsConnectorListener zzrr() {
        return this.zzacj;
    }
}
