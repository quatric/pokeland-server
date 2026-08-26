package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.VisibleForTesting;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@VisibleForTesting
public final class zzin implements ServiceConnection, BaseGmsClient.BaseConnectionCallbacks, BaseGmsClient.BaseOnConnectionFailedListener {
    final /* synthetic */ zzhv zzrd;
    private volatile boolean zzrt;
    private volatile zzec zzru;

    protected zzin(zzhv zzhvVar) {
        this.zzrd = zzhvVar;
    }

    static /* synthetic */ boolean zza(zzin zzinVar, boolean z) {
        zzinVar.zzrt = false;
        return false;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks
    @MainThread
    public final void onConnected(@Nullable Bundle bundle) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnected");
        synchronized (this) {
            try {
                this.zzrd.zzaa().zza(new zzio(this, this.zzru.getService()));
            } catch (DeadObjectException | IllegalStateException unused) {
                this.zzru = null;
                this.zzrt = false;
            }
        }
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseOnConnectionFailedListener
    @MainThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnectionFailed");
        zzef zzefVarZzhs = this.zzrd.zzj.zzhs();
        if (zzefVarZzhs != null) {
            zzefVarZzhs.zzgn().zza("Service connection failed", connectionResult);
        }
        synchronized (this) {
            this.zzrt = false;
            this.zzru = null;
        }
        this.zzrd.zzaa().zza(new zziq(this));
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks
    @MainThread
    public final void onConnectionSuspended(int i) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnectionSuspended");
        this.zzrd.zzab().zzgr().zzao("Service connection suspended");
        this.zzrd.zzaa().zza(new zzir(this));
    }

    @Override // android.content.ServiceConnection
    @MainThread
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onServiceConnected");
        synchronized (this) {
            if (iBinder == null) {
                this.zzrt = false;
                this.zzrd.zzab().zzgk().zzao("Service connected with null binder");
                return;
            }
            zzdx zzdzVar = null;
            try {
                String interfaceDescriptor = iBinder.getInterfaceDescriptor();
                if ("com.google.android.gms.measurement.internal.IMeasurementService".equals(interfaceDescriptor)) {
                    if (iBinder != null) {
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.measurement.internal.IMeasurementService");
                        zzdzVar = iInterfaceQueryLocalInterface instanceof zzdx ? (zzdx) iInterfaceQueryLocalInterface : new zzdz(iBinder);
                    }
                    this.zzrd.zzab().zzgs().zzao("Bound to IMeasurementService interface");
                } else {
                    this.zzrd.zzab().zzgk().zza("Got binder with a wrong descriptor", interfaceDescriptor);
                }
            } catch (RemoteException unused) {
                this.zzrd.zzab().zzgk().zzao("Service connect failed to get IMeasurementService");
            }
            if (zzdzVar == null) {
                this.zzrt = false;
                try {
                    ConnectionTracker.getInstance().unbindService(this.zzrd.getContext(), this.zzrd.zzre);
                } catch (IllegalArgumentException unused2) {
                }
            } else {
                this.zzrd.zzaa().zza(new zzim(this, zzdzVar));
            }
        }
    }

    @Override // android.content.ServiceConnection
    @MainThread
    public final void onServiceDisconnected(ComponentName componentName) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onServiceDisconnected");
        this.zzrd.zzab().zzgr().zzao("Service disconnected");
        this.zzrd.zzaa().zza(new zzip(this, componentName));
    }

    @WorkerThread
    public final void zzb(Intent intent) {
        this.zzrd.zzo();
        Context context = this.zzrd.getContext();
        ConnectionTracker connectionTracker = ConnectionTracker.getInstance();
        synchronized (this) {
            if (this.zzrt) {
                this.zzrd.zzab().zzgs().zzao("Connection attempt already in progress");
                return;
            }
            this.zzrd.zzab().zzgs().zzao("Using local app measurement service");
            this.zzrt = true;
            connectionTracker.bindService(context, intent, this.zzrd.zzre, 129);
        }
    }

    @WorkerThread
    public final void zziw() {
        if (this.zzru != null && (this.zzru.isConnected() || this.zzru.isConnecting())) {
            this.zzru.disconnect();
        }
        this.zzru = null;
    }

    @WorkerThread
    public final void zzix() {
        this.zzrd.zzo();
        Context context = this.zzrd.getContext();
        synchronized (this) {
            if (this.zzrt) {
                this.zzrd.zzab().zzgs().zzao("Connection attempt already in progress");
                return;
            }
            if (this.zzru != null && (this.zzru.isConnecting() || this.zzru.isConnected())) {
                this.zzrd.zzab().zzgs().zzao("Already awaiting connection attempt");
                return;
            }
            this.zzru = new zzec(context, Looper.getMainLooper(), this, this);
            this.zzrd.zzab().zzgs().zzao("Connecting to remote service");
            this.zzrt = true;
            this.zzru.checkAvailabilityAndConnect();
        }
    }
}
