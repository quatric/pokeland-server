package com.google.android.gms.measurement;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.Size;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.gms.measurement.internal.zzfj;
import com.google.android.gms.measurement.internal.zzgg;
import com.google.android.gms.measurement.internal.zzgi;
import com.google.android.gms.measurement.internal.zzgj;
import com.google.android.gms.measurement.internal.zzgk;
import com.google.android.gms.measurement.internal.zzgl;
import com.google.android.gms.measurement.internal.zzgn;
import com.google.android.gms.measurement.internal.zzhi;
import com.google.android.gms.measurement.internal.zzho;
import com.google.android.gms.measurement.internal.zzjn;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@ShowFirstParty
@Deprecated
public class AppMeasurement {

    @ShowFirstParty
    @KeepForSdk
    public static final String CRASH_ORIGIN = "crash";

    @ShowFirstParty
    @KeepForSdk
    public static final String FCM_ORIGIN = "fcm";

    @ShowFirstParty
    @KeepForSdk
    public static final String FIAM_ORIGIN = "fiam";
    private static volatile AppMeasurement zzi;
    private final zzfj zzj;
    private final zzhi zzk;
    private final boolean zzl;

    @ShowFirstParty
    @KeepForSdk
    public static class ConditionalUserProperty {

        @Keep
        @ShowFirstParty
        @KeepForSdk
        public boolean mActive;

        @Keep
        @ShowFirstParty
        @KeepForSdk
        public String mAppId;

        @Keep
        @ShowFirstParty
        @KeepForSdk
        public long mCreationTimestamp;

        @Keep
        public String mExpiredEventName;

        @Keep
        public Bundle mExpiredEventParams;

        @Keep
        @ShowFirstParty
        @KeepForSdk
        public String mName;

        @Keep
        @ShowFirstParty
        @KeepForSdk
        public String mOrigin;

        @Keep
        @ShowFirstParty
        @KeepForSdk
        public long mTimeToLive;

        @Keep
        public String mTimedOutEventName;

        @Keep
        public Bundle mTimedOutEventParams;

        @Keep
        @ShowFirstParty
        @KeepForSdk
        public String mTriggerEventName;

        @Keep
        @ShowFirstParty
        @KeepForSdk
        public long mTriggerTimeout;

        @Keep
        public String mTriggeredEventName;

        @Keep
        public Bundle mTriggeredEventParams;

        @Keep
        @ShowFirstParty
        @KeepForSdk
        public long mTriggeredTimestamp;

        @Keep
        @ShowFirstParty
        @KeepForSdk
        public Object mValue;

        @KeepForSdk
        public ConditionalUserProperty() {
        }

        private ConditionalUserProperty(@NonNull Bundle bundle) {
            Preconditions.checkNotNull(bundle);
            this.mAppId = (String) zzgg.zza(bundle, "app_id", String.class, null);
            this.mOrigin = (String) zzgg.zza(bundle, "origin", String.class, null);
            this.mName = (String) zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.NAME, String.class, null);
            this.mValue = zzgg.zza(bundle, "value", Object.class, null);
            this.mTriggerEventName = (String) zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, String.class, null);
            this.mTriggerTimeout = ((Long) zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, Long.class, 0L)).longValue();
            this.mTimedOutEventName = (String) zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME, String.class, null);
            this.mTimedOutEventParams = (Bundle) zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS, Bundle.class, null);
            this.mTriggeredEventName = (String) zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME, String.class, null);
            this.mTriggeredEventParams = (Bundle) zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS, Bundle.class, null);
            this.mTimeToLive = ((Long) zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, Long.class, 0L)).longValue();
            this.mExpiredEventName = (String) zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, String.class, null);
            this.mExpiredEventParams = (Bundle) zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, Bundle.class, null);
        }

        @KeepForSdk
        public ConditionalUserProperty(ConditionalUserProperty conditionalUserProperty) {
            Preconditions.checkNotNull(conditionalUserProperty);
            this.mAppId = conditionalUserProperty.mAppId;
            this.mOrigin = conditionalUserProperty.mOrigin;
            this.mCreationTimestamp = conditionalUserProperty.mCreationTimestamp;
            this.mName = conditionalUserProperty.mName;
            Object obj = conditionalUserProperty.mValue;
            if (obj != null) {
                this.mValue = zzho.zza(obj);
                if (this.mValue == null) {
                    this.mValue = conditionalUserProperty.mValue;
                }
            }
            this.mActive = conditionalUserProperty.mActive;
            this.mTriggerEventName = conditionalUserProperty.mTriggerEventName;
            this.mTriggerTimeout = conditionalUserProperty.mTriggerTimeout;
            this.mTimedOutEventName = conditionalUserProperty.mTimedOutEventName;
            Bundle bundle = conditionalUserProperty.mTimedOutEventParams;
            if (bundle != null) {
                this.mTimedOutEventParams = new Bundle(bundle);
            }
            this.mTriggeredEventName = conditionalUserProperty.mTriggeredEventName;
            Bundle bundle2 = conditionalUserProperty.mTriggeredEventParams;
            if (bundle2 != null) {
                this.mTriggeredEventParams = new Bundle(bundle2);
            }
            this.mTriggeredTimestamp = conditionalUserProperty.mTriggeredTimestamp;
            this.mTimeToLive = conditionalUserProperty.mTimeToLive;
            this.mExpiredEventName = conditionalUserProperty.mExpiredEventName;
            Bundle bundle3 = conditionalUserProperty.mExpiredEventParams;
            if (bundle3 != null) {
                this.mExpiredEventParams = new Bundle(bundle3);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Bundle zzd() {
            Bundle bundle = new Bundle();
            String str = this.mAppId;
            if (str != null) {
                bundle.putString("app_id", str);
            }
            String str2 = this.mOrigin;
            if (str2 != null) {
                bundle.putString("origin", str2);
            }
            String str3 = this.mName;
            if (str3 != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.NAME, str3);
            }
            Object obj = this.mValue;
            if (obj != null) {
                zzgg.zza(bundle, obj);
            }
            String str4 = this.mTriggerEventName;
            if (str4 != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, str4);
            }
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, this.mTriggerTimeout);
            String str5 = this.mTimedOutEventName;
            if (str5 != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME, str5);
            }
            Bundle bundle2 = this.mTimedOutEventParams;
            if (bundle2 != null) {
                bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS, bundle2);
            }
            String str6 = this.mTriggeredEventName;
            if (str6 != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME, str6);
            }
            Bundle bundle3 = this.mTriggeredEventParams;
            if (bundle3 != null) {
                bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS, bundle3);
            }
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, this.mTimeToLive);
            String str7 = this.mExpiredEventName;
            if (str7 != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, str7);
            }
            Bundle bundle4 = this.mExpiredEventParams;
            if (bundle4 != null) {
                bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, bundle4);
            }
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, this.mCreationTimestamp);
            bundle.putBoolean(AppMeasurementSdk.ConditionalUserProperty.ACTIVE, this.mActive);
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, this.mTriggeredTimestamp);
            return bundle;
        }
    }

    @ShowFirstParty
    @KeepForSdk
    public static final class Event extends zzgj {

        @ShowFirstParty
        @KeepForSdk
        public static final String AD_REWARD = "_ar";

        @ShowFirstParty
        @KeepForSdk
        public static final String APP_EXCEPTION = "_ae";

        private Event() {
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

    @ShowFirstParty
    @KeepForSdk
    public static final class Param extends zzgi {

        @ShowFirstParty
        @KeepForSdk
        public static final String FATAL = "fatal";

        @ShowFirstParty
        @KeepForSdk
        public static final String TIMESTAMP = "timestamp";

        @ShowFirstParty
        @KeepForSdk
        public static final String TYPE = "type";

        private Param() {
        }
    }

    @ShowFirstParty
    @KeepForSdk
    public static final class UserProperty extends zzgl {

        @ShowFirstParty
        @KeepForSdk
        public static final String FIREBASE_LAST_NOTIFICATION = "_ln";

        private UserProperty() {
        }
    }

    private AppMeasurement(zzfj zzfjVar) {
        Preconditions.checkNotNull(zzfjVar);
        this.zzj = zzfjVar;
        this.zzk = null;
        this.zzl = false;
    }

    private AppMeasurement(zzhi zzhiVar) {
        Preconditions.checkNotNull(zzhiVar);
        this.zzk = zzhiVar;
        this.zzj = null;
        this.zzl = true;
    }

    @Keep
    @Deprecated
    @ShowFirstParty
    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.WAKE_LOCK"})
    public static AppMeasurement getInstance(Context context) {
        return zza(context, null, null);
    }

    public static AppMeasurement zza(Context context, Bundle bundle) {
        if (zzi == null) {
            synchronized (AppMeasurement.class) {
                if (zzi == null) {
                    zzhi zzhiVarZzb = zzb(context, bundle);
                    if (zzhiVarZzb != null) {
                        zzi = new AppMeasurement(zzhiVarZzb);
                    } else {
                        zzi = new AppMeasurement(zzfj.zza(context, null, null, bundle));
                    }
                }
            }
        }
        return zzi;
    }

    @VisibleForTesting
    private static AppMeasurement zza(Context context, String str, String str2) {
        if (zzi == null) {
            synchronized (AppMeasurement.class) {
                if (zzi == null) {
                    zzhi zzhiVarZzb = zzb(context, null);
                    if (zzhiVarZzb != null) {
                        zzi = new AppMeasurement(zzhiVarZzb);
                    } else {
                        zzi = new AppMeasurement(zzfj.zza(context, null, null, null));
                    }
                }
            }
        }
        return zzi;
    }

    private static zzhi zzb(Context context, Bundle bundle) {
        try {
            return (zzhi) Class.forName("com.google.firebase.analytics.FirebaseAnalytics").getDeclaredMethod("getScionFrontendApiImplementation", Context.class, Bundle.class).invoke(null, context, bundle);
        } catch (ClassNotFoundException | Exception unused) {
            return null;
        }
    }

    @Keep
    public void beginAdUnitExposure(@Size(min = 1) @NonNull String str) {
        if (this.zzl) {
            this.zzk.beginAdUnitExposure(str);
        } else {
            this.zzj.zzp().beginAdUnitExposure(str, this.zzj.zzx().elapsedRealtime());
        }
    }

    @Keep
    @ShowFirstParty
    @KeepForSdk
    public void clearConditionalUserProperty(@Size(max = 24, min = 1) @NonNull String str, @Nullable String str2, @Nullable Bundle bundle) {
        if (this.zzl) {
            this.zzk.clearConditionalUserProperty(str, str2, bundle);
        } else {
            this.zzj.zzq().clearConditionalUserProperty(str, str2, bundle);
        }
    }

    @Keep
    @VisibleForTesting
    protected void clearConditionalUserPropertyAs(@Size(min = 1) @NonNull String str, @Size(max = 24, min = 1) @NonNull String str2, @Nullable String str3, @Nullable Bundle bundle) {
        if (this.zzl) {
            throw new IllegalStateException("Unexpected call on client side");
        }
        this.zzj.zzq().clearConditionalUserPropertyAs(str, str2, str3, bundle);
    }

    @Keep
    public void endAdUnitExposure(@Size(min = 1) @NonNull String str) {
        if (this.zzl) {
            this.zzk.endAdUnitExposure(str);
        } else {
            this.zzj.zzp().endAdUnitExposure(str, this.zzj.zzx().elapsedRealtime());
        }
    }

    @Keep
    public long generateEventId() {
        return this.zzl ? this.zzk.generateEventId() : this.zzj.zzz().zzjv();
    }

    @Keep
    @Nullable
    public String getAppInstanceId() {
        return this.zzl ? this.zzk.zzi() : this.zzj.zzq().zzi();
    }

    @KeepForSdk
    public Boolean getBoolean() {
        return this.zzl ? (Boolean) this.zzk.zzb(4) : this.zzj.zzq().zzig();
    }

    @WorkerThread
    @Keep
    @ShowFirstParty
    @KeepForSdk
    public List<ConditionalUserProperty> getConditionalUserProperties(@Nullable String str, @Size(max = 23, min = 1) @Nullable String str2) {
        List<Bundle> conditionalUserProperties = this.zzl ? this.zzk.getConditionalUserProperties(str, str2) : this.zzj.zzq().zzn(str, str2);
        ArrayList arrayList = new ArrayList(conditionalUserProperties == null ? 0 : conditionalUserProperties.size());
        Iterator<Bundle> it = conditionalUserProperties.iterator();
        while (it.hasNext()) {
            arrayList.add(new ConditionalUserProperty(it.next()));
        }
        return arrayList;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @WorkerThread
    @Keep
    @VisibleForTesting
    protected List<ConditionalUserProperty> getConditionalUserPropertiesAs(@Size(min = 1) @NonNull String str, @Nullable String str2, @Size(max = 23, min = 1) @Nullable String str3) {
        if (this.zzl) {
            throw new IllegalStateException("Unexpected call on client side");
        }
        ArrayList<Bundle> arrayListZzd = this.zzj.zzq().zzd(str, str2, str3);
        int i = 0;
        ArrayList arrayList = new ArrayList(arrayListZzd == null ? 0 : arrayListZzd.size());
        ArrayList<Bundle> arrayList2 = arrayListZzd;
        int size = arrayList2.size();
        while (i < size) {
            Bundle bundle = arrayList2.get(i);
            i++;
            arrayList.add(new ConditionalUserProperty(bundle));
        }
        return arrayList;
    }

    @Keep
    @Nullable
    public String getCurrentScreenClass() {
        return this.zzl ? this.zzk.getCurrentScreenClass() : this.zzj.zzq().getCurrentScreenClass();
    }

    @Keep
    @Nullable
    public String getCurrentScreenName() {
        return this.zzl ? this.zzk.getCurrentScreenName() : this.zzj.zzq().getCurrentScreenName();
    }

    @KeepForSdk
    public Double getDouble() {
        return this.zzl ? (Double) this.zzk.zzb(2) : this.zzj.zzq().zzik();
    }

    @Keep
    @Nullable
    public String getGmpAppId() {
        return this.zzl ? this.zzk.getGmpAppId() : this.zzj.zzq().getGmpAppId();
    }

    @KeepForSdk
    public Integer getInteger() {
        return this.zzl ? (Integer) this.zzk.zzb(3) : this.zzj.zzq().zzij();
    }

    @KeepForSdk
    public Long getLong() {
        return this.zzl ? (Long) this.zzk.zzb(1) : this.zzj.zzq().zzii();
    }

    @WorkerThread
    @Keep
    @ShowFirstParty
    @KeepForSdk
    public int getMaxUserProperties(@Size(min = 1) @NonNull String str) {
        if (this.zzl) {
            return this.zzk.getMaxUserProperties(str);
        }
        this.zzj.zzq();
        Preconditions.checkNotEmpty(str);
        return 25;
    }

    @KeepForSdk
    public String getString() {
        return this.zzl ? (String) this.zzk.zzb(0) : this.zzj.zzq().zzih();
    }

    @WorkerThread
    @Keep
    @VisibleForTesting
    protected Map<String, Object> getUserProperties(@Nullable String str, @Size(max = 24, min = 1) @Nullable String str2, boolean z) {
        return this.zzl ? this.zzk.getUserProperties(str, str2, z) : this.zzj.zzq().getUserProperties(str, str2, z);
    }

    @WorkerThread
    @ShowFirstParty
    @KeepForSdk
    public Map<String, Object> getUserProperties(boolean z) {
        if (this.zzl) {
            return this.zzk.getUserProperties(null, null, z);
        }
        List<zzjn> listZzh = this.zzj.zzq().zzh(z);
        ArrayMap arrayMap = new ArrayMap(listZzh.size());
        for (zzjn zzjnVar : listZzh) {
            arrayMap.put(zzjnVar.name, zzjnVar.getValue());
        }
        return arrayMap;
    }

    @WorkerThread
    @Keep
    @VisibleForTesting
    protected Map<String, Object> getUserPropertiesAs(@Size(min = 1) @NonNull String str, @Nullable String str2, @Size(max = 23, min = 1) @Nullable String str3, boolean z) {
        if (this.zzl) {
            throw new IllegalStateException("Unexpected call on client side");
        }
        return this.zzj.zzq().getUserPropertiesAs(str, str2, str3, z);
    }

    @Keep
    @ShowFirstParty
    public void logEventInternal(String str, String str2, Bundle bundle) {
        if (this.zzl) {
            this.zzk.logEventInternal(str, str2, bundle);
        } else {
            this.zzj.zzq().logEvent(str, str2, bundle);
        }
    }

    @ShowFirstParty
    @KeepForSdk
    public void logEventInternalNoInterceptor(String str, String str2, Bundle bundle, long j) {
        if (this.zzl) {
            this.zzk.logEventInternalNoInterceptor(str, str2, bundle, j);
        } else {
            this.zzj.zzq().logEvent(str, str2, bundle, true, false, j);
        }
    }

    @ShowFirstParty
    @KeepForSdk
    public void registerOnMeasurementEventListener(OnEventListener onEventListener) {
        if (this.zzl) {
            this.zzk.zza(onEventListener);
        } else {
            this.zzj.zzq().zza(onEventListener);
        }
    }

    @Keep
    @ShowFirstParty
    @KeepForSdk
    public void setConditionalUserProperty(@NonNull ConditionalUserProperty conditionalUserProperty) {
        Preconditions.checkNotNull(conditionalUserProperty);
        if (this.zzl) {
            this.zzk.setConditionalUserProperty(conditionalUserProperty.zzd());
        } else {
            this.zzj.zzq().setConditionalUserProperty(conditionalUserProperty.zzd());
        }
    }

    @Keep
    @VisibleForTesting
    protected void setConditionalUserPropertyAs(@NonNull ConditionalUserProperty conditionalUserProperty) {
        Preconditions.checkNotNull(conditionalUserProperty);
        if (this.zzl) {
            throw new IllegalStateException("Unexpected call on client side");
        }
        this.zzj.zzq().zzd(conditionalUserProperty.zzd());
    }

    @WorkerThread
    @ShowFirstParty
    @KeepForSdk
    public void setEventInterceptor(EventInterceptor eventInterceptor) {
        if (this.zzl) {
            this.zzk.zza(eventInterceptor);
        } else {
            this.zzj.zzq().zza(eventInterceptor);
        }
    }

    @KeepForSdk
    @Deprecated
    public void setMeasurementEnabled(boolean z) {
        if (this.zzl) {
            this.zzk.setMeasurementEnabled(z);
        } else {
            this.zzj.zzq().setMeasurementEnabled(z);
        }
    }

    @ShowFirstParty
    @KeepForSdk
    public void setUserPropertyInternal(String str, String str2, Object obj) {
        Preconditions.checkNotEmpty(str);
        if (this.zzl) {
            this.zzk.setUserPropertyInternal(str, str2, obj);
        } else {
            this.zzj.zzq().zzb(str, str2, obj, true);
        }
    }

    @ShowFirstParty
    @KeepForSdk
    public void unregisterOnMeasurementEventListener(OnEventListener onEventListener) {
        if (this.zzl) {
            this.zzk.zzb(onEventListener);
        } else {
            this.zzj.zzq().zzb(onEventListener);
        }
    }

    public final void zza(boolean z) {
        if (this.zzl) {
            this.zzk.setDataCollectionEnabled(z);
        } else {
            this.zzj.zzq().zza(z);
        }
    }
}
