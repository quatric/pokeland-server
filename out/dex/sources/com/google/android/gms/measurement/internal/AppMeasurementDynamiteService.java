package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@DynamiteApi
public class AppMeasurementDynamiteService extends com.google.android.gms.internal.measurement.zzn {

    @VisibleForTesting
    zzfj zzj = null;
    private Map<Integer, zzgn> zzdk = new ArrayMap();

    class zza implements zzgn {
        private com.google.android.gms.internal.measurement.zzq zzdo;

        zza(com.google.android.gms.internal.measurement.zzq zzqVar) {
            this.zzdo = zzqVar;
        }

        @Override // com.google.android.gms.measurement.internal.zzgn
        public final void onEvent(String str, String str2, Bundle bundle, long j) {
            try {
                this.zzdo.onEvent(str, str2, bundle, j);
            } catch (RemoteException e) {
                AppMeasurementDynamiteService.this.zzj.zzab().zzgn().zza("Event listener threw exception", e);
            }
        }
    }

    class zzb implements zzgk {
        private com.google.android.gms.internal.measurement.zzq zzdo;

        zzb(com.google.android.gms.internal.measurement.zzq zzqVar) {
            this.zzdo = zzqVar;
        }

        @Override // com.google.android.gms.measurement.internal.zzgk
        public final void interceptEvent(String str, String str2, Bundle bundle, long j) {
            try {
                this.zzdo.onEvent(str, str2, bundle, j);
            } catch (RemoteException e) {
                AppMeasurementDynamiteService.this.zzj.zzab().zzgn().zza("Event interceptor threw exception", e);
            }
        }
    }

    private final void zza(com.google.android.gms.internal.measurement.zzp zzpVar, String str) {
        this.zzj.zzz().zzb(zzpVar, str);
    }

    private final void zzbi() {
        if (this.zzj == null) {
            throw new IllegalStateException("Attempting to perform action before initialize.");
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void beginAdUnitExposure(String str, long j) throws RemoteException {
        zzbi();
        this.zzj.zzp().beginAdUnitExposure(str, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void clearConditionalUserProperty(String str, String str2, Bundle bundle) throws RemoteException {
        zzbi();
        this.zzj.zzq().clearConditionalUserProperty(str, str2, bundle);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void endAdUnitExposure(String str, long j) throws RemoteException {
        zzbi();
        this.zzj.zzp().endAdUnitExposure(str, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void generateEventId(com.google.android.gms.internal.measurement.zzp zzpVar) throws RemoteException {
        zzbi();
        this.zzj.zzz().zza(zzpVar, this.zzj.zzz().zzjv());
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void getAppInstanceId(com.google.android.gms.internal.measurement.zzp zzpVar) throws RemoteException {
        zzbi();
        this.zzj.zzaa().zza(new zzh(this, zzpVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void getCachedAppInstanceId(com.google.android.gms.internal.measurement.zzp zzpVar) throws RemoteException {
        zzbi();
        zza(zzpVar, this.zzj.zzq().zzi());
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void getConditionalUserProperties(String str, String str2, com.google.android.gms.internal.measurement.zzp zzpVar) throws RemoteException {
        zzbi();
        this.zzj.zzaa().zza(new zzl(this, zzpVar, str, str2));
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void getCurrentScreenClass(com.google.android.gms.internal.measurement.zzp zzpVar) throws RemoteException {
        zzbi();
        zza(zzpVar, this.zzj.zzq().getCurrentScreenClass());
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void getCurrentScreenName(com.google.android.gms.internal.measurement.zzp zzpVar) throws RemoteException {
        zzbi();
        zza(zzpVar, this.zzj.zzq().getCurrentScreenName());
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void getDeepLink(com.google.android.gms.internal.measurement.zzp zzpVar) throws RemoteException {
        zzbi();
        zzgp zzgpVarZzq = this.zzj.zzq();
        zzgpVarZzq.zzo();
        if (!zzgpVarZzq.zzad().zzd(null, zzak.zzjc)) {
            zzgpVarZzq.zzz().zzb(zzpVar, "");
        } else if (zzgpVarZzq.zzac().zzme.get() > 0) {
            zzgpVarZzq.zzz().zzb(zzpVar, "");
        } else {
            zzgpVarZzq.zzac().zzme.set(zzgpVarZzq.zzx().currentTimeMillis());
            zzgpVarZzq.zzj.zza(zzpVar);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void getGmpAppId(com.google.android.gms.internal.measurement.zzp zzpVar) throws RemoteException {
        zzbi();
        zza(zzpVar, this.zzj.zzq().getGmpAppId());
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void getMaxUserProperties(String str, com.google.android.gms.internal.measurement.zzp zzpVar) throws RemoteException {
        zzbi();
        this.zzj.zzq();
        Preconditions.checkNotEmpty(str);
        this.zzj.zzz().zza(zzpVar, 25);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void getTestFlag(com.google.android.gms.internal.measurement.zzp zzpVar, int i) throws RemoteException {
        zzbi();
        if (i == 0) {
            this.zzj.zzz().zzb(zzpVar, this.zzj.zzq().zzih());
            return;
        }
        if (i == 1) {
            this.zzj.zzz().zza(zzpVar, this.zzj.zzq().zzii().longValue());
            return;
        }
        if (i != 2) {
            if (i == 3) {
                this.zzj.zzz().zza(zzpVar, this.zzj.zzq().zzij().intValue());
                return;
            } else {
                if (i != 4) {
                    return;
                }
                this.zzj.zzz().zza(zzpVar, this.zzj.zzq().zzig().booleanValue());
                return;
            }
        }
        zzjs zzjsVarZzz = this.zzj.zzz();
        double dDoubleValue = this.zzj.zzq().zzik().doubleValue();
        Bundle bundle = new Bundle();
        bundle.putDouble("r", dDoubleValue);
        try {
            zzpVar.zzb(bundle);
        } catch (RemoteException e) {
            zzjsVarZzz.zzj.zzab().zzgn().zza("Error returning double value to wrapper", e);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void getUserProperties(String str, String str2, boolean z, com.google.android.gms.internal.measurement.zzp zzpVar) throws RemoteException {
        zzbi();
        this.zzj.zzaa().zza(new zzi(this, zzpVar, str, str2, z));
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void initForTests(Map map) throws RemoteException {
        zzbi();
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void initialize(IObjectWrapper iObjectWrapper, com.google.android.gms.internal.measurement.zzx zzxVar, long j) throws RemoteException {
        Context context = (Context) ObjectWrapper.unwrap(iObjectWrapper);
        zzfj zzfjVar = this.zzj;
        if (zzfjVar == null) {
            this.zzj = zzfj.zza(context, zzxVar);
        } else {
            zzfjVar.zzab().zzgn().zzao("Attempting to initialize multiple times");
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void isDataCollectionEnabled(com.google.android.gms.internal.measurement.zzp zzpVar) throws RemoteException {
        zzbi();
        this.zzj.zzaa().zza(new zzk(this, zzpVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void logEvent(String str, String str2, Bundle bundle, boolean z, boolean z2, long j) throws RemoteException {
        zzbi();
        this.zzj.zzq().logEvent(str, str2, bundle, z, z2, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void logEventAndBundle(String str, String str2, Bundle bundle, com.google.android.gms.internal.measurement.zzp zzpVar, long j) throws RemoteException {
        zzbi();
        Preconditions.checkNotEmpty(str2);
        (bundle != null ? new Bundle(bundle) : new Bundle()).putString("_o", "app");
        this.zzj.zzaa().zza(new zzj(this, zzpVar, new zzai(str2, new zzah(bundle), "app", j), str));
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void logHealthData(int i, String str, IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3) throws RemoteException {
        zzbi();
        this.zzj.zzab().zza(i, true, false, str, iObjectWrapper == null ? null : ObjectWrapper.unwrap(iObjectWrapper), iObjectWrapper2 == null ? null : ObjectWrapper.unwrap(iObjectWrapper2), iObjectWrapper3 != null ? ObjectWrapper.unwrap(iObjectWrapper3) : null);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void onActivityCreated(IObjectWrapper iObjectWrapper, Bundle bundle, long j) throws RemoteException {
        zzbi();
        zzhj zzhjVar = this.zzj.zzq().zzpu;
        if (zzhjVar != null) {
            this.zzj.zzq().zzif();
            zzhjVar.onActivityCreated((Activity) ObjectWrapper.unwrap(iObjectWrapper), bundle);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void onActivityDestroyed(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        zzbi();
        zzhj zzhjVar = this.zzj.zzq().zzpu;
        if (zzhjVar != null) {
            this.zzj.zzq().zzif();
            zzhjVar.onActivityDestroyed((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void onActivityPaused(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        zzbi();
        zzhj zzhjVar = this.zzj.zzq().zzpu;
        if (zzhjVar != null) {
            this.zzj.zzq().zzif();
            zzhjVar.onActivityPaused((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void onActivityResumed(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        zzbi();
        zzhj zzhjVar = this.zzj.zzq().zzpu;
        if (zzhjVar != null) {
            this.zzj.zzq().zzif();
            zzhjVar.onActivityResumed((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void onActivitySaveInstanceState(IObjectWrapper iObjectWrapper, com.google.android.gms.internal.measurement.zzp zzpVar, long j) throws RemoteException {
        zzbi();
        zzhj zzhjVar = this.zzj.zzq().zzpu;
        Bundle bundle = new Bundle();
        if (zzhjVar != null) {
            this.zzj.zzq().zzif();
            zzhjVar.onActivitySaveInstanceState((Activity) ObjectWrapper.unwrap(iObjectWrapper), bundle);
        }
        try {
            zzpVar.zzb(bundle);
        } catch (RemoteException e) {
            this.zzj.zzab().zzgn().zza("Error returning bundle value to wrapper", e);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void onActivityStarted(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        zzbi();
        zzhj zzhjVar = this.zzj.zzq().zzpu;
        if (zzhjVar != null) {
            this.zzj.zzq().zzif();
            zzhjVar.onActivityStarted((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void onActivityStopped(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        zzbi();
        zzhj zzhjVar = this.zzj.zzq().zzpu;
        if (zzhjVar != null) {
            this.zzj.zzq().zzif();
            zzhjVar.onActivityStopped((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void performAction(Bundle bundle, com.google.android.gms.internal.measurement.zzp zzpVar, long j) throws RemoteException {
        zzbi();
        zzpVar.zzb(null);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void registerOnMeasurementEventListener(com.google.android.gms.internal.measurement.zzq zzqVar) throws RemoteException {
        zzbi();
        zzgn zzaVar = this.zzdk.get(Integer.valueOf(zzqVar.mo440id()));
        if (zzaVar == null) {
            zzaVar = new zza(zzqVar);
            this.zzdk.put(Integer.valueOf(zzqVar.mo440id()), zzaVar);
        }
        this.zzj.zzq().zza(zzaVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void resetAnalyticsData(long j) throws RemoteException {
        zzbi();
        this.zzj.zzq().resetAnalyticsData(j);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void setConditionalUserProperty(Bundle bundle, long j) throws RemoteException {
        zzbi();
        if (bundle == null) {
            this.zzj.zzab().zzgk().zzao("Conditional user property must not be null");
        } else {
            this.zzj.zzq().setConditionalUserProperty(bundle, j);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void setCurrentScreen(IObjectWrapper iObjectWrapper, String str, String str2, long j) throws RemoteException {
        zzbi();
        this.zzj.zzt().setCurrentScreen((Activity) ObjectWrapper.unwrap(iObjectWrapper), str, str2);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void setDataCollectionEnabled(boolean z) throws RemoteException {
        zzbi();
        this.zzj.zzq().zza(z);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void setEventInterceptor(com.google.android.gms.internal.measurement.zzq zzqVar) throws RemoteException {
        zzbi();
        zzgp zzgpVarZzq = this.zzj.zzq();
        zzb zzbVar = new zzb(zzqVar);
        zzgpVarZzq.zzm();
        zzgpVarZzq.zzbi();
        zzgpVarZzq.zzaa().zza(new zzgu(zzgpVarZzq, zzbVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void setInstanceIdProvider(com.google.android.gms.internal.measurement.zzv zzvVar) throws RemoteException {
        zzbi();
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void setMeasurementEnabled(boolean z, long j) throws RemoteException {
        zzbi();
        this.zzj.zzq().setMeasurementEnabled(z);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void setMinimumSessionDuration(long j) throws RemoteException {
        zzbi();
        this.zzj.zzq().setMinimumSessionDuration(j);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void setSessionTimeoutDuration(long j) throws RemoteException {
        zzbi();
        this.zzj.zzq().setSessionTimeoutDuration(j);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void setUserId(String str, long j) throws RemoteException {
        zzbi();
        this.zzj.zzq().zza(null, "_id", str, true, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void setUserProperty(String str, String str2, IObjectWrapper iObjectWrapper, boolean z, long j) throws RemoteException {
        zzbi();
        this.zzj.zzq().zza(str, str2, ObjectWrapper.unwrap(iObjectWrapper), z, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public void unregisterOnMeasurementEventListener(com.google.android.gms.internal.measurement.zzq zzqVar) throws RemoteException {
        zzbi();
        zzgn zzgnVarRemove = this.zzdk.remove(Integer.valueOf(zzqVar.mo440id()));
        if (zzgnVarRemove == null) {
            zzgnVarRemove = new zza(zzqVar);
        }
        this.zzj.zzq().zzb(zzgnVarRemove);
    }
}
