package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzm extends zzb implements zzk {
    zzm(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.measurement.api.internal.IAppMeasurementDynamiteService");
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void beginAdUnitExposure(String str, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(23, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void clearConditionalUserProperty(String str, String str2, Bundle bundle) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        zzd.zza(parcelObtainAndWriteInterfaceToken, bundle);
        zza(9, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void endAdUnitExposure(String str, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(24, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void generateEventId(zzp zzpVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzpVar);
        zza(22, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void getAppInstanceId(zzp zzpVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzpVar);
        zza(20, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void getCachedAppInstanceId(zzp zzpVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzpVar);
        zza(19, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void getConditionalUserProperties(String str, String str2, zzp zzpVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzpVar);
        zza(10, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void getCurrentScreenClass(zzp zzpVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzpVar);
        zza(17, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void getCurrentScreenName(zzp zzpVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzpVar);
        zza(16, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void getDeepLink(zzp zzpVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzpVar);
        zza(41, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void getGmpAppId(zzp zzpVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzpVar);
        zza(21, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void getMaxUserProperties(String str, zzp zzpVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzpVar);
        zza(6, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void getTestFlag(zzp zzpVar, int i) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzpVar);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        zza(38, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void getUserProperties(String str, String str2, boolean z, zzp zzpVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        zzd.writeBoolean(parcelObtainAndWriteInterfaceToken, z);
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzpVar);
        zza(5, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void initForTests(Map map) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeMap(map);
        zza(37, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void initialize(IObjectWrapper iObjectWrapper, zzx zzxVar, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, iObjectWrapper);
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzxVar);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(1, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void isDataCollectionEnabled(zzp zzpVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzpVar);
        zza(40, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void logEvent(String str, String str2, Bundle bundle, boolean z, boolean z2, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        zzd.zza(parcelObtainAndWriteInterfaceToken, bundle);
        zzd.writeBoolean(parcelObtainAndWriteInterfaceToken, z);
        zzd.writeBoolean(parcelObtainAndWriteInterfaceToken, z2);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(2, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void logEventAndBundle(String str, String str2, Bundle bundle, zzp zzpVar, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        zzd.zza(parcelObtainAndWriteInterfaceToken, bundle);
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzpVar);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(3, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void logHealthData(int i, String str, IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        zzd.zza(parcelObtainAndWriteInterfaceToken, iObjectWrapper);
        zzd.zza(parcelObtainAndWriteInterfaceToken, iObjectWrapper2);
        zzd.zza(parcelObtainAndWriteInterfaceToken, iObjectWrapper3);
        zza(33, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void onActivityCreated(IObjectWrapper iObjectWrapper, Bundle bundle, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, iObjectWrapper);
        zzd.zza(parcelObtainAndWriteInterfaceToken, bundle);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(27, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void onActivityDestroyed(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, iObjectWrapper);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(28, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void onActivityPaused(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, iObjectWrapper);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(29, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void onActivityResumed(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, iObjectWrapper);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(30, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void onActivitySaveInstanceState(IObjectWrapper iObjectWrapper, zzp zzpVar, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, iObjectWrapper);
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzpVar);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(31, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void onActivityStarted(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, iObjectWrapper);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(25, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void onActivityStopped(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, iObjectWrapper);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(26, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void performAction(Bundle bundle, zzp zzpVar, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, bundle);
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzpVar);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(32, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void registerOnMeasurementEventListener(zzq zzqVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzqVar);
        zza(35, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void resetAnalyticsData(long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(12, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void setConditionalUserProperty(Bundle bundle, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, bundle);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(8, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void setCurrentScreen(IObjectWrapper iObjectWrapper, String str, String str2, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, iObjectWrapper);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(15, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void setDataCollectionEnabled(boolean z) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.writeBoolean(parcelObtainAndWriteInterfaceToken, z);
        zza(39, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void setEventInterceptor(zzq zzqVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzqVar);
        zza(34, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void setInstanceIdProvider(zzv zzvVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzvVar);
        zza(18, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void setMeasurementEnabled(boolean z, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.writeBoolean(parcelObtainAndWriteInterfaceToken, z);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(11, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void setMinimumSessionDuration(long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(13, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void setSessionTimeoutDuration(long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(14, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void setUserId(String str, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(7, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void setUserProperty(String str, String str2, IObjectWrapper iObjectWrapper, boolean z, long j) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        zzd.zza(parcelObtainAndWriteInterfaceToken, iObjectWrapper);
        zzd.writeBoolean(parcelObtainAndWriteInterfaceToken, z);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        zza(4, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.measurement.zzk
    public final void unregisterOnMeasurementEventListener(zzq zzqVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzqVar);
        zza(36, parcelObtainAndWriteInterfaceToken);
    }
}
