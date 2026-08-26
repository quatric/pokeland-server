package com.google.firebase.analytics.connector.internal;

import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import java.util.Set;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zze implements zza {
    private AppMeasurement zzacc;
    private AnalyticsConnector.AnalyticsConnectorListener zzacj;
    private zzg zzacr = new zzg(this);

    public zze(AppMeasurement appMeasurement, AnalyticsConnector.AnalyticsConnectorListener analyticsConnectorListener) {
        this.zzacj = analyticsConnectorListener;
        this.zzacc = appMeasurement;
        this.zzacc.registerOnMeasurementEventListener(this.zzacr);
    }

    @Override // com.google.firebase.analytics.connector.internal.zza
    public final void registerEventNames(Set<String> set) {
    }

    @Override // com.google.firebase.analytics.connector.internal.zza
    public final void unregisterEventNames() {
    }

    @Override // com.google.firebase.analytics.connector.internal.zza
    public final AnalyticsConnector.AnalyticsConnectorListener zzrr() {
        return this.zzacj;
    }
}
