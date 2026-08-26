package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface zzhi {
    void beginAdUnitExposure(String str);

    void clearConditionalUserProperty(String str, String str2, Bundle bundle);

    void endAdUnitExposure(String str);

    long generateEventId();

    List<Bundle> getConditionalUserProperties(String str, String str2);

    String getCurrentScreenClass();

    String getCurrentScreenName();

    String getGmpAppId();

    int getMaxUserProperties(String str);

    Map<String, Object> getUserProperties(String str, String str2, boolean z);

    void logEventInternal(String str, String str2, Bundle bundle);

    void logEventInternalNoInterceptor(String str, String str2, Bundle bundle, long j);

    void setConditionalUserProperty(Bundle bundle);

    void setDataCollectionEnabled(boolean z);

    void setMeasurementEnabled(boolean z);

    void setUserPropertyInternal(String str, String str2, Object obj);

    void zza(zzgk zzgkVar);

    void zza(zzgn zzgnVar);

    Object zzb(int i);

    void zzb(zzgn zzgnVar);

    String zzi();
}
