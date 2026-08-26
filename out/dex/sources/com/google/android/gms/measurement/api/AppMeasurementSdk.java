package com.google.android.gms.measurement.api;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.Size;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.internal.measurement.zzz;
import com.google.android.gms.measurement.internal.zzgk;
import com.google.android.gms.measurement.internal.zzgn;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
public class AppMeasurementSdk {
    private final zzz zzo;

    @KeepForSdk
    public static final class ConditionalUserProperty {

        @KeepForSdk
        public static final String ACTIVE = "active";

        @KeepForSdk
        public static final String CREATION_TIMESTAMP = "creation_timestamp";

        @KeepForSdk
        public static final String EXPIRED_EVENT_NAME = "expired_event_name";

        @KeepForSdk
        public static final String EXPIRED_EVENT_PARAMS = "expired_event_params";

        @KeepForSdk
        public static final String NAME = "name";

        @KeepForSdk
        public static final String ORIGIN = "origin";

        @KeepForSdk
        public static final String TIMED_OUT_EVENT_NAME = "timed_out_event_name";

        @KeepForSdk
        public static final String TIMED_OUT_EVENT_PARAMS = "timed_out_event_params";

        @KeepForSdk
        public static final String TIME_TO_LIVE = "time_to_live";

        @KeepForSdk
        public static final String TRIGGERED_EVENT_NAME = "triggered_event_name";

        @KeepForSdk
        public static final String TRIGGERED_EVENT_PARAMS = "triggered_event_params";

        @KeepForSdk
        public static final String TRIGGERED_TIMESTAMP = "triggered_timestamp";

        @KeepForSdk
        public static final String TRIGGER_EVENT_NAME = "trigger_event_name";

        @KeepForSdk
        public static final String TRIGGER_TIMEOUT = "trigger_timeout";

        @KeepForSdk
        public static final String VALUE = "value";

        private ConditionalUserProperty() {
        }
    }

    @ShowFirstParty
    @KeepForSdk
    public interface EventInterceptor extends zzgk {
        @Override // com.google.android.gms.measurement.internal.zzgk
        @WorkerThread
        @ShowFirstParty
        @KeepForSdk
        void interceptEvent(String str, String str2, Bundle bundle, long j);
    }

    @ShowFirstParty
    @KeepForSdk
    public interface OnEventListener extends zzgn {
        @Override // com.google.android.gms.measurement.internal.zzgn
        @WorkerThread
        @ShowFirstParty
        @KeepForSdk
        void onEvent(String str, String str2, Bundle bundle, long j);
    }

    public AppMeasurementSdk(zzz zzzVar) {
        this.zzo = zzzVar;
    }

    @KeepForSdk
    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.WAKE_LOCK"})
    public static AppMeasurementSdk getInstance(@NonNull Context context) {
        return zzz.zza(context).zzg();
    }

    @KeepForSdk
    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.WAKE_LOCK"})
    public static AppMeasurementSdk getInstance(@NonNull Context context, @NonNull String str, @NonNull String str2, @NonNull String str3, Bundle bundle) {
        return zzz.zza(context, str, str2, str3, bundle).zzg();
    }

    @KeepForSdk
    public void beginAdUnitExposure(@Size(min = 1) @NonNull String str) {
        this.zzo.beginAdUnitExposure(str);
    }

    @KeepForSdk
    public void clearConditionalUserProperty(@Size(max = 24, min = 1) @NonNull String str, @Nullable String str2, @Nullable Bundle bundle) {
        this.zzo.clearConditionalUserProperty(str, str2, bundle);
    }

    @KeepForSdk
    public void endAdUnitExposure(@Size(min = 1) @NonNull String str) {
        this.zzo.endAdUnitExposure(str);
    }

    @KeepForSdk
    public long generateEventId() {
        return this.zzo.generateEventId();
    }

    @KeepForSdk
    public String getAppIdOrigin() {
        return this.zzo.getAppIdOrigin();
    }

    @KeepForSdk
    @Nullable
    public String getAppInstanceId() {
        return this.zzo.zzi();
    }

    @WorkerThread
    @KeepForSdk
    public List<Bundle> getConditionalUserProperties(@Nullable String str, @Size(max = 23, min = 1) @Nullable String str2) {
        return this.zzo.getConditionalUserProperties(str, str2);
    }

    @KeepForSdk
    @Nullable
    public String getCurrentScreenClass() {
        return this.zzo.getCurrentScreenClass();
    }

    @KeepForSdk
    @Nullable
    public String getCurrentScreenName() {
        return this.zzo.getCurrentScreenName();
    }

    @KeepForSdk
    @Nullable
    public String getGmpAppId() {
        return this.zzo.getGmpAppId();
    }

    @WorkerThread
    @KeepForSdk
    public int getMaxUserProperties(@Size(min = 1) @NonNull String str) {
        return this.zzo.getMaxUserProperties(str);
    }

    @WorkerThread
    @KeepForSdk
    public Map<String, Object> getUserProperties(@Nullable String str, @Size(max = 24, min = 1) @Nullable String str2, boolean z) {
        return this.zzo.getUserProperties(str, str2, z);
    }

    @KeepForSdk
    public void logEvent(String str, String str2, Bundle bundle) {
        this.zzo.logEventInternal(str, str2, bundle);
    }

    @KeepForSdk
    public void logEventNoInterceptor(String str, String str2, Bundle bundle, long j) {
        this.zzo.logEventInternalNoInterceptor(str, str2, bundle, j);
    }

    @KeepForSdk
    public void performAction(Bundle bundle) {
        this.zzo.zza(bundle, false);
    }

    @KeepForSdk
    public Bundle performActionWithResponse(Bundle bundle) {
        return this.zzo.zza(bundle, true);
    }

    @ShowFirstParty
    @KeepForSdk
    public void registerOnMeasurementEventListener(OnEventListener onEventListener) {
        this.zzo.zza(onEventListener);
    }

    @KeepForSdk
    public void setConditionalUserProperty(@NonNull Bundle bundle) {
        this.zzo.setConditionalUserProperty(bundle);
    }

    @KeepForSdk
    public void setCurrentScreen(@NonNull Activity activity, @Size(max = 36, min = 1) @Nullable String str, @Size(max = 36, min = 1) @Nullable String str2) {
        this.zzo.setCurrentScreen(activity, str, str2);
    }

    @WorkerThread
    @ShowFirstParty
    @KeepForSdk
    public void setEventInterceptor(EventInterceptor eventInterceptor) {
        this.zzo.zza(eventInterceptor);
    }

    @KeepForSdk
    public void setMeasurementEnabled(boolean z) {
        this.zzo.setMeasurementEnabled(z);
    }

    @KeepForSdk
    public void setUserProperty(String str, String str2, Object obj) {
        this.zzo.setUserPropertyInternal(str, str2, obj);
    }

    @ShowFirstParty
    @KeepForSdk
    public void unregisterOnMeasurementEventListener(OnEventListener onEventListener) {
        this.zzo.zzb(onEventListener);
    }
}
