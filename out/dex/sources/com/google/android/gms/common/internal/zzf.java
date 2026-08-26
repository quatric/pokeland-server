package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.common.stats.ConnectionTracker;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzf implements ServiceConnection {
    private ComponentName mComponentName;
    private IBinder zzcz;
    private boolean zzea;
    private final GmsClientSupervisor.zza zzeb;
    private final /* synthetic */ zze zzec;
    private final Set<ServiceConnection> zzdz = new HashSet();
    private int mState = 2;

    public zzf(zze zzeVar, GmsClientSupervisor.zza zzaVar) {
        this.zzec = zzeVar;
        this.zzeb = zzaVar;
    }

    public final IBinder getBinder() {
        return this.zzcz;
    }

    public final ComponentName getComponentName() {
        return this.mComponentName;
    }

    public final int getState() {
        return this.mState;
    }

    public final boolean isBound() {
        return this.zzea;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.zzec.zzdu) {
            this.zzec.mHandler.removeMessages(1, this.zzeb);
            this.zzcz = iBinder;
            this.mComponentName = componentName;
            Iterator<ServiceConnection> it = this.zzdz.iterator();
            while (it.hasNext()) {
                it.next().onServiceConnected(componentName, iBinder);
            }
            this.mState = 1;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (this.zzec.zzdu) {
            this.zzec.mHandler.removeMessages(1, this.zzeb);
            this.zzcz = null;
            this.mComponentName = componentName;
            Iterator<ServiceConnection> it = this.zzdz.iterator();
            while (it.hasNext()) {
                it.next().onServiceDisconnected(componentName);
            }
            this.mState = 2;
        }
    }

    public final void zza(ServiceConnection serviceConnection, String str) {
        ConnectionTracker unused = this.zzec.zzdw;
        Context unused2 = this.zzec.zzdv;
        this.zzeb.zzb(this.zzec.zzdv);
        this.zzdz.add(serviceConnection);
    }

    public final boolean zza(ServiceConnection serviceConnection) {
        return this.zzdz.contains(serviceConnection);
    }

    public final void zzb(ServiceConnection serviceConnection, String str) {
        ConnectionTracker unused = this.zzec.zzdw;
        Context unused2 = this.zzec.zzdv;
        this.zzdz.remove(serviceConnection);
    }

    public final void zze(String str) {
        this.mState = 3;
        this.zzea = this.zzec.zzdw.zza(this.zzec.zzdv, str, this.zzeb.zzb(this.zzec.zzdv), this, this.zzeb.zzq());
        if (this.zzea) {
            this.zzec.mHandler.sendMessageDelayed(this.zzec.mHandler.obtainMessage(1, this.zzeb), this.zzec.zzdy);
        } else {
            this.mState = 2;
            try {
                this.zzec.zzdw.unbindService(this.zzec.zzdv, this);
            } catch (IllegalArgumentException unused) {
            }
        }
    }

    public final void zzf(String str) {
        this.zzec.mHandler.removeMessages(1, this.zzeb);
        this.zzec.zzdw.unbindService(this.zzec.zzdv, this);
        this.zzea = false;
        this.mState = 2;
    }

    public final boolean zzr() {
        return this.zzdz.isEmpty();
    }
}
