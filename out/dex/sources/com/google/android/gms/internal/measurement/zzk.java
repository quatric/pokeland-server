package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface zzk extends IInterface {
    void beginAdUnitExposure(String str, long j) throws RemoteException;

    void clearConditionalUserProperty(String str, String str2, Bundle bundle) throws RemoteException;

    void endAdUnitExposure(String str, long j) throws RemoteException;

    void generateEventId(zzp zzpVar) throws RemoteException;

    void getAppInstanceId(zzp zzpVar) throws RemoteException;

    void getCachedAppInstanceId(zzp zzpVar) throws RemoteException;

    void getConditionalUserProperties(String str, String str2, zzp zzpVar) throws RemoteException;

    void getCurrentScreenClass(zzp zzpVar) throws RemoteException;

    void getCurrentScreenName(zzp zzpVar) throws RemoteException;

    void getDeepLink(zzp zzpVar) throws RemoteException;

    void getGmpAppId(zzp zzpVar) throws RemoteException;

    void getMaxUserProperties(String str, zzp zzpVar) throws RemoteException;

    void getTestFlag(zzp zzpVar, int i) throws RemoteException;

    void getUserProperties(String str, String str2, boolean z, zzp zzpVar) throws RemoteException;

    void initForTests(Map map) throws RemoteException;

    void initialize(IObjectWrapper iObjectWrapper, zzx zzxVar, long j) throws RemoteException;

    void isDataCollectionEnabled(zzp zzpVar) throws RemoteException;

    void logEvent(String str, String str2, Bundle bundle, boolean z, boolean z2, long j) throws RemoteException;

    void logEventAndBundle(String str, String str2, Bundle bundle, zzp zzpVar, long j) throws RemoteException;

    void logHealthData(int i, String str, IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3) throws RemoteException;

    void onActivityCreated(IObjectWrapper iObjectWrapper, Bundle bundle, long j) throws RemoteException;

    void onActivityDestroyed(IObjectWrapper iObjectWrapper, long j) throws RemoteException;

    void onActivityPaused(IObjectWrapper iObjectWrapper, long j) throws RemoteException;

    void onActivityResumed(IObjectWrapper iObjectWrapper, long j) throws RemoteException;

    void onActivitySaveInstanceState(IObjectWrapper iObjectWrapper, zzp zzpVar, long j) throws RemoteException;

    void onActivityStarted(IObjectWrapper iObjectWrapper, long j) throws RemoteException;

    void onActivityStopped(IObjectWrapper iObjectWrapper, long j) throws RemoteException;

    void performAction(Bundle bundle, zzp zzpVar, long j) throws RemoteException;

    void registerOnMeasurementEventListener(zzq zzqVar) throws RemoteException;

    void resetAnalyticsData(long j) throws RemoteException;

    void setConditionalUserProperty(Bundle bundle, long j) throws RemoteException;

    void setCurrentScreen(IObjectWrapper iObjectWrapper, String str, String str2, long j) throws RemoteException;

    void setDataCollectionEnabled(boolean z) throws RemoteException;

    void setEventInterceptor(zzq zzqVar) throws RemoteException;

    void setInstanceIdProvider(zzv zzvVar) throws RemoteException;

    void setMeasurementEnabled(boolean z, long j) throws RemoteException;

    void setMinimumSessionDuration(long j) throws RemoteException;

    void setSessionTimeoutDuration(long j) throws RemoteException;

    void setUserId(String str, long j) throws RemoteException;

    void setUserProperty(String str, String str2, IObjectWrapper iObjectWrapper, boolean z, long j) throws RemoteException;

    void unregisterOnMeasurementEventListener(zzq zzqVar) throws RemoteException;
}
