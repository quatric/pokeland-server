package com.google.android.gms.measurement.internal;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.MainThread;
import android.support.v4.view.MotionEventCompat;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.measurement.internal.zzix;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzit<T extends Context & zzix> {
    private final T zzrz;

    public zzit(T t) {
        Preconditions.checkNotNull(t);
        this.zzrz = t;
    }

    private final zzef zzab() {
        return zzfj.zza(this.zzrz, (com.google.android.gms.internal.measurement.zzx) null).zzab();
    }

    private final void zze(Runnable runnable) {
        zzjg zzjgVarZzm = zzjg.zzm(this.zzrz);
        zzjgVarZzm.zzaa().zza(new zziu(this, zzjgVarZzm, runnable));
    }

    @MainThread
    public final IBinder onBind(Intent intent) {
        if (intent == null) {
            zzab().zzgk().zzao("onBind called with null intent");
            return null;
        }
        String action = intent.getAction();
        if ("com.google.android.gms.measurement.START".equals(action)) {
            return new zzfk(zzjg.zzm(this.zzrz));
        }
        zzab().zzgn().zza("onBind received unknown action", action);
        return null;
    }

    @MainThread
    public final void onCreate() {
        zzfj zzfjVarZza = zzfj.zza(this.zzrz, (com.google.android.gms.internal.measurement.zzx) null);
        zzef zzefVarZzab = zzfjVarZza.zzab();
        zzfjVarZza.zzae();
        zzefVarZzab.zzgs().zzao("Local AppMeasurementService is starting up");
    }

    @MainThread
    public final void onDestroy() {
        zzfj zzfjVarZza = zzfj.zza(this.zzrz, (com.google.android.gms.internal.measurement.zzx) null);
        zzef zzefVarZzab = zzfjVarZza.zzab();
        zzfjVarZza.zzae();
        zzefVarZzab.zzgs().zzao("Local AppMeasurementService is shutting down");
    }

    @MainThread
    public final void onRebind(Intent intent) {
        if (intent == null) {
            zzab().zzgk().zzao("onRebind called with null intent");
        } else {
            zzab().zzgs().zza("onRebind called. action", intent.getAction());
        }
    }

    @MainThread
    public final int onStartCommand(final Intent intent, int i, final int i2) {
        zzfj zzfjVarZza = zzfj.zza(this.zzrz, (com.google.android.gms.internal.measurement.zzx) null);
        final zzef zzefVarZzab = zzfjVarZza.zzab();
        if (intent == null) {
            zzefVarZzab.zzgn().zzao("AppMeasurementService started with null intent");
            return 2;
        }
        String action = intent.getAction();
        zzfjVarZza.zzae();
        zzefVarZzab.zzgs().zza("Local AppMeasurementService called. startId, action", Integer.valueOf(i2), action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            zze(new Runnable(this, i2, zzefVarZzab, intent) { // from class: com.google.android.gms.measurement.internal.zzis
                private final int zzqi;
                private final zzit zzrw;
                private final zzef zzrx;
                private final Intent zzry;

                {
                    this.zzrw = this;
                    this.zzqi = i2;
                    this.zzrx = zzefVarZzab;
                    this.zzry = intent;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    this.zzrw.zza(this.zzqi, this.zzrx, this.zzry);
                }
            });
        }
        return 2;
    }

    @TargetApi(MotionEventCompat.AXIS_DISTANCE)
    @MainThread
    public final boolean onStartJob(final JobParameters jobParameters) {
        zzfj zzfjVarZza = zzfj.zza(this.zzrz, (com.google.android.gms.internal.measurement.zzx) null);
        final zzef zzefVarZzab = zzfjVarZza.zzab();
        String string = jobParameters.getExtras().getString("action");
        zzfjVarZza.zzae();
        zzefVarZzab.zzgs().zza("Local AppMeasurementJobService called. action", string);
        if (!"com.google.android.gms.measurement.UPLOAD".equals(string)) {
            return true;
        }
        zze(new Runnable(this, zzefVarZzab, jobParameters) { // from class: com.google.android.gms.measurement.internal.zziv
            private final zzit zzrw;
            private final zzef zzsc;
            private final JobParameters zzsd;

            {
                this.zzrw = this;
                this.zzsc = zzefVarZzab;
                this.zzsd = jobParameters;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zzrw.zza(this.zzsc, this.zzsd);
            }
        });
        return true;
    }

    @MainThread
    public final boolean onUnbind(Intent intent) {
        if (intent == null) {
            zzab().zzgk().zzao("onUnbind called with null intent");
            return true;
        }
        zzab().zzgs().zza("onUnbind called for intent. action", intent.getAction());
        return true;
    }

    final /* synthetic */ void zza(int i, zzef zzefVar, Intent intent) {
        if (this.zzrz.zza(i)) {
            zzefVar.zzgs().zza("Local AppMeasurementService processed last upload request. StartId", Integer.valueOf(i));
            zzab().zzgs().zzao("Completed wakeful intent.");
            this.zzrz.zza(intent);
        }
    }

    final /* synthetic */ void zza(zzef zzefVar, JobParameters jobParameters) {
        zzefVar.zzgs().zzao("AppMeasurementJobService processed last upload request.");
        this.zzrz.zza(jobParameters, false);
    }
}
