package com.google.firebase.analytics;

import android.os.Bundle;
import com.google.android.gms.internal.measurement.zzz;
import com.google.android.gms.measurement.internal.zzgk;
import com.google.android.gms.measurement.internal.zzgn;
import com.google.android.gms.measurement.internal.zzhi;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zza implements zzhi {
    private final /* synthetic */ zzz zzabz;

    zza(zzz zzzVar) {
        this.zzabz = zzzVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final void beginAdUnitExposure(String str) {
        this.zzabz.beginAdUnitExposure(str);
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final void clearConditionalUserProperty(String str, String str2, Bundle bundle) {
        this.zzabz.clearConditionalUserProperty(str, str2, bundle);
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final void endAdUnitExposure(String str) {
        this.zzabz.endAdUnitExposure(str);
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final long generateEventId() {
        return this.zzabz.generateEventId();
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final List<Bundle> getConditionalUserProperties(String str, String str2) {
        return this.zzabz.getConditionalUserProperties(str, str2);
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final String getCurrentScreenClass() {
        return this.zzabz.getCurrentScreenClass();
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final String getCurrentScreenName() {
        return this.zzabz.getCurrentScreenName();
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final String getGmpAppId() {
        return this.zzabz.getGmpAppId();
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final int getMaxUserProperties(String str) {
        return this.zzabz.getMaxUserProperties(str);
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final Map<String, Object> getUserProperties(String str, String str2, boolean z) {
        return this.zzabz.getUserProperties(str, str2, z);
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final void logEventInternal(String str, String str2, Bundle bundle) {
        this.zzabz.logEventInternal(str, str2, bundle);
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final void logEventInternalNoInterceptor(String str, String str2, Bundle bundle, long j) {
        this.zzabz.logEventInternalNoInterceptor(str, str2, bundle, j);
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final void setConditionalUserProperty(Bundle bundle) {
        this.zzabz.setConditionalUserProperty(bundle);
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final void setDataCollectionEnabled(boolean z) {
        this.zzabz.setDataCollectionEnabled(z);
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final void setMeasurementEnabled(boolean z) {
        this.zzabz.setMeasurementEnabled(z);
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final void setUserPropertyInternal(String str, String str2, Object obj) {
        this.zzabz.setUserPropertyInternal(str, str2, obj);
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final void zza(zzgk zzgkVar) {
        this.zzabz.zza(zzgkVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final void zza(zzgn zzgnVar) {
        this.zzabz.zza(zzgnVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final Object zzb(int i) {
        return this.zzabz.zzb(i);
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final void zzb(zzgn zzgnVar) {
        this.zzabz.zzb(zzgnVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzhi
    public final String zzi() {
        return this.zzabz.zzi();
    }
}
