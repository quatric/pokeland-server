package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.view.MotionEventCompat;
import com.google.android.gms.dynamic.IObjectWrapper;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class zzn extends zza implements zzk {
    public zzn() {
        super("com.google.android.gms.measurement.api.internal.IAppMeasurementDynamiteService");
    }

    public static zzk asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.measurement.api.internal.IAppMeasurementDynamiteService");
        return iInterfaceQueryLocalInterface instanceof zzk ? (zzk) iInterfaceQueryLocalInterface : new zzm(iBinder);
    }

    @Override // com.google.android.gms.internal.measurement.zza
    protected final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        zzp zzrVar;
        zzp zzrVar2 = null;
        zzp zzrVar3 = null;
        zzp zzrVar4 = null;
        zzp zzrVar5 = null;
        zzq zzsVar = null;
        zzq zzsVar2 = null;
        zzq zzsVar3 = null;
        zzp zzrVar6 = null;
        zzp zzrVar7 = null;
        zzp zzrVar8 = null;
        zzp zzrVar9 = null;
        zzp zzrVar10 = null;
        zzp zzrVar11 = null;
        zzv zzuVar = null;
        zzp zzrVar12 = null;
        zzp zzrVar13 = null;
        zzp zzrVar14 = null;
        zzp zzrVar15 = null;
        switch (i) {
            case 1:
                initialize(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), (zzx) zzd.zza(parcel, zzx.CREATOR), parcel.readLong());
                break;
            case 2:
                logEvent(parcel.readString(), parcel.readString(), (Bundle) zzd.zza(parcel, Bundle.CREATOR), zzd.zza(parcel), zzd.zza(parcel), parcel.readLong());
                break;
            case 3:
                String string = parcel.readString();
                String string2 = parcel.readString();
                Bundle bundle = (Bundle) zzd.zza(parcel, Bundle.CREATOR);
                IBinder strongBinder = parcel.readStrongBinder();
                if (strongBinder == null) {
                    zzrVar = null;
                } else {
                    IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzrVar = iInterfaceQueryLocalInterface instanceof zzp ? (zzp) iInterfaceQueryLocalInterface : new zzr(strongBinder);
                }
                logEventAndBundle(string, string2, bundle, zzrVar, parcel.readLong());
                break;
            case 4:
                setUserProperty(parcel.readString(), parcel.readString(), IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), zzd.zza(parcel), parcel.readLong());
                break;
            case 5:
                String string3 = parcel.readString();
                String string4 = parcel.readString();
                boolean zZza = zzd.zza(parcel);
                IBinder strongBinder2 = parcel.readStrongBinder();
                if (strongBinder2 != null) {
                    IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzrVar2 = iInterfaceQueryLocalInterface2 instanceof zzp ? (zzp) iInterfaceQueryLocalInterface2 : new zzr(strongBinder2);
                }
                getUserProperties(string3, string4, zZza, zzrVar2);
                break;
            case 6:
                String string5 = parcel.readString();
                IBinder strongBinder3 = parcel.readStrongBinder();
                if (strongBinder3 != null) {
                    IInterface iInterfaceQueryLocalInterface3 = strongBinder3.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzrVar15 = iInterfaceQueryLocalInterface3 instanceof zzp ? (zzp) iInterfaceQueryLocalInterface3 : new zzr(strongBinder3);
                }
                getMaxUserProperties(string5, zzrVar15);
                break;
            case 7:
                setUserId(parcel.readString(), parcel.readLong());
                break;
            case 8:
                setConditionalUserProperty((Bundle) zzd.zza(parcel, Bundle.CREATOR), parcel.readLong());
                break;
            case 9:
                clearConditionalUserProperty(parcel.readString(), parcel.readString(), (Bundle) zzd.zza(parcel, Bundle.CREATOR));
                break;
            case 10:
                String string6 = parcel.readString();
                String string7 = parcel.readString();
                IBinder strongBinder4 = parcel.readStrongBinder();
                if (strongBinder4 != null) {
                    IInterface iInterfaceQueryLocalInterface4 = strongBinder4.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzrVar14 = iInterfaceQueryLocalInterface4 instanceof zzp ? (zzp) iInterfaceQueryLocalInterface4 : new zzr(strongBinder4);
                }
                getConditionalUserProperties(string6, string7, zzrVar14);
                break;
            case 11:
                setMeasurementEnabled(zzd.zza(parcel), parcel.readLong());
                break;
            case 12:
                resetAnalyticsData(parcel.readLong());
                break;
            case 13:
                setMinimumSessionDuration(parcel.readLong());
                break;
            case 14:
                setSessionTimeoutDuration(parcel.readLong());
                break;
            case 15:
                setCurrentScreen(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readString(), parcel.readString(), parcel.readLong());
                break;
            case 16:
                IBinder strongBinder5 = parcel.readStrongBinder();
                if (strongBinder5 != null) {
                    IInterface iInterfaceQueryLocalInterface5 = strongBinder5.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzrVar13 = iInterfaceQueryLocalInterface5 instanceof zzp ? (zzp) iInterfaceQueryLocalInterface5 : new zzr(strongBinder5);
                }
                getCurrentScreenName(zzrVar13);
                break;
            case 17:
                IBinder strongBinder6 = parcel.readStrongBinder();
                if (strongBinder6 != null) {
                    IInterface iInterfaceQueryLocalInterface6 = strongBinder6.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzrVar12 = iInterfaceQueryLocalInterface6 instanceof zzp ? (zzp) iInterfaceQueryLocalInterface6 : new zzr(strongBinder6);
                }
                getCurrentScreenClass(zzrVar12);
                break;
            case 18:
                IBinder strongBinder7 = parcel.readStrongBinder();
                if (strongBinder7 != null) {
                    IInterface iInterfaceQueryLocalInterface7 = strongBinder7.queryLocalInterface("com.google.android.gms.measurement.api.internal.IStringProvider");
                    zzuVar = iInterfaceQueryLocalInterface7 instanceof zzv ? (zzv) iInterfaceQueryLocalInterface7 : new zzu(strongBinder7);
                }
                setInstanceIdProvider(zzuVar);
                break;
            case 19:
                IBinder strongBinder8 = parcel.readStrongBinder();
                if (strongBinder8 != null) {
                    IInterface iInterfaceQueryLocalInterface8 = strongBinder8.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzrVar11 = iInterfaceQueryLocalInterface8 instanceof zzp ? (zzp) iInterfaceQueryLocalInterface8 : new zzr(strongBinder8);
                }
                getCachedAppInstanceId(zzrVar11);
                break;
            case 20:
                IBinder strongBinder9 = parcel.readStrongBinder();
                if (strongBinder9 != null) {
                    IInterface iInterfaceQueryLocalInterface9 = strongBinder9.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzrVar10 = iInterfaceQueryLocalInterface9 instanceof zzp ? (zzp) iInterfaceQueryLocalInterface9 : new zzr(strongBinder9);
                }
                getAppInstanceId(zzrVar10);
                break;
            case MotionEventCompat.AXIS_WHEEL /* 21 */:
                IBinder strongBinder10 = parcel.readStrongBinder();
                if (strongBinder10 != null) {
                    IInterface iInterfaceQueryLocalInterface10 = strongBinder10.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzrVar9 = iInterfaceQueryLocalInterface10 instanceof zzp ? (zzp) iInterfaceQueryLocalInterface10 : new zzr(strongBinder10);
                }
                getGmpAppId(zzrVar9);
                break;
            case MotionEventCompat.AXIS_GAS /* 22 */:
                IBinder strongBinder11 = parcel.readStrongBinder();
                if (strongBinder11 != null) {
                    IInterface iInterfaceQueryLocalInterface11 = strongBinder11.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzrVar8 = iInterfaceQueryLocalInterface11 instanceof zzp ? (zzp) iInterfaceQueryLocalInterface11 : new zzr(strongBinder11);
                }
                generateEventId(zzrVar8);
                break;
            case MotionEventCompat.AXIS_BRAKE /* 23 */:
                beginAdUnitExposure(parcel.readString(), parcel.readLong());
                break;
            case MotionEventCompat.AXIS_DISTANCE /* 24 */:
                endAdUnitExposure(parcel.readString(), parcel.readLong());
                break;
            case 25:
                onActivityStarted(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readLong());
                break;
            case MotionEventCompat.AXIS_SCROLL /* 26 */:
                onActivityStopped(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readLong());
                break;
            case MotionEventCompat.AXIS_RELATIVE_X /* 27 */:
                onActivityCreated(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), (Bundle) zzd.zza(parcel, Bundle.CREATOR), parcel.readLong());
                break;
            case MotionEventCompat.AXIS_RELATIVE_Y /* 28 */:
                onActivityDestroyed(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readLong());
                break;
            case 29:
                onActivityPaused(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readLong());
                break;
            case 30:
                onActivityResumed(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readLong());
                break;
            case 31:
                IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                IBinder strongBinder12 = parcel.readStrongBinder();
                if (strongBinder12 != null) {
                    IInterface iInterfaceQueryLocalInterface12 = strongBinder12.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzrVar7 = iInterfaceQueryLocalInterface12 instanceof zzp ? (zzp) iInterfaceQueryLocalInterface12 : new zzr(strongBinder12);
                }
                onActivitySaveInstanceState(iObjectWrapperAsInterface, zzrVar7, parcel.readLong());
                break;
            case 32:
                Bundle bundle2 = (Bundle) zzd.zza(parcel, Bundle.CREATOR);
                IBinder strongBinder13 = parcel.readStrongBinder();
                if (strongBinder13 != null) {
                    IInterface iInterfaceQueryLocalInterface13 = strongBinder13.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzrVar6 = iInterfaceQueryLocalInterface13 instanceof zzp ? (zzp) iInterfaceQueryLocalInterface13 : new zzr(strongBinder13);
                }
                performAction(bundle2, zzrVar6, parcel.readLong());
                break;
            case 33:
                logHealthData(parcel.readInt(), parcel.readString(), IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()));
                break;
            case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
                IBinder strongBinder14 = parcel.readStrongBinder();
                if (strongBinder14 != null) {
                    IInterface iInterfaceQueryLocalInterface14 = strongBinder14.queryLocalInterface("com.google.android.gms.measurement.api.internal.IEventHandlerProxy");
                    zzsVar3 = iInterfaceQueryLocalInterface14 instanceof zzq ? (zzq) iInterfaceQueryLocalInterface14 : new zzs(strongBinder14);
                }
                setEventInterceptor(zzsVar3);
                break;
            case MotionEventCompat.AXIS_GENERIC_4 /* 35 */:
                IBinder strongBinder15 = parcel.readStrongBinder();
                if (strongBinder15 != null) {
                    IInterface iInterfaceQueryLocalInterface15 = strongBinder15.queryLocalInterface("com.google.android.gms.measurement.api.internal.IEventHandlerProxy");
                    zzsVar2 = iInterfaceQueryLocalInterface15 instanceof zzq ? (zzq) iInterfaceQueryLocalInterface15 : new zzs(strongBinder15);
                }
                registerOnMeasurementEventListener(zzsVar2);
                break;
            case MotionEventCompat.AXIS_GENERIC_5 /* 36 */:
                IBinder strongBinder16 = parcel.readStrongBinder();
                if (strongBinder16 != null) {
                    IInterface iInterfaceQueryLocalInterface16 = strongBinder16.queryLocalInterface("com.google.android.gms.measurement.api.internal.IEventHandlerProxy");
                    zzsVar = iInterfaceQueryLocalInterface16 instanceof zzq ? (zzq) iInterfaceQueryLocalInterface16 : new zzs(strongBinder16);
                }
                unregisterOnMeasurementEventListener(zzsVar);
                break;
            case MotionEventCompat.AXIS_GENERIC_6 /* 37 */:
                initForTests(zzd.zzb(parcel));
                break;
            case MotionEventCompat.AXIS_GENERIC_7 /* 38 */:
                IBinder strongBinder17 = parcel.readStrongBinder();
                if (strongBinder17 != null) {
                    IInterface iInterfaceQueryLocalInterface17 = strongBinder17.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzrVar5 = iInterfaceQueryLocalInterface17 instanceof zzp ? (zzp) iInterfaceQueryLocalInterface17 : new zzr(strongBinder17);
                }
                getTestFlag(zzrVar5, parcel.readInt());
                break;
            case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
                setDataCollectionEnabled(zzd.zza(parcel));
                break;
            case MotionEventCompat.AXIS_GENERIC_9 /* 40 */:
                IBinder strongBinder18 = parcel.readStrongBinder();
                if (strongBinder18 != null) {
                    IInterface iInterfaceQueryLocalInterface18 = strongBinder18.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzrVar4 = iInterfaceQueryLocalInterface18 instanceof zzp ? (zzp) iInterfaceQueryLocalInterface18 : new zzr(strongBinder18);
                }
                isDataCollectionEnabled(zzrVar4);
                break;
            case MotionEventCompat.AXIS_GENERIC_10 /* 41 */:
                IBinder strongBinder19 = parcel.readStrongBinder();
                if (strongBinder19 != null) {
                    IInterface iInterfaceQueryLocalInterface19 = strongBinder19.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzrVar3 = iInterfaceQueryLocalInterface19 instanceof zzp ? (zzp) iInterfaceQueryLocalInterface19 : new zzr(strongBinder19);
                }
                getDeepLink(zzrVar3);
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
