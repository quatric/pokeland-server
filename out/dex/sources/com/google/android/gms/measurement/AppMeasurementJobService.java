package com.google.android.gms.measurement;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v4.view.MotionEventCompat;
import com.google.android.gms.measurement.internal.zzit;
import com.google.android.gms.measurement.internal.zzix;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@TargetApi(MotionEventCompat.AXIS_DISTANCE)
public final class AppMeasurementJobService extends JobService implements zzix {
    private zzit<AppMeasurementJobService> zzm;

    private final zzit<AppMeasurementJobService> zze() {
        if (this.zzm == null) {
            this.zzm = new zzit<>(this);
        }
        return this.zzm;
    }

    @Override // android.app.Service
    @MainThread
    public final void onCreate() {
        super.onCreate();
        zze().onCreate();
    }

    @Override // android.app.Service
    @MainThread
    public final void onDestroy() {
        zze().onDestroy();
        super.onDestroy();
    }

    @Override // android.app.Service
    @MainThread
    public final void onRebind(Intent intent) {
        zze().onRebind(intent);
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        return zze().onStartJob(jobParameters);
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override // android.app.Service
    @MainThread
    public final boolean onUnbind(Intent intent) {
        return zze().onUnbind(intent);
    }

    @Override // com.google.android.gms.measurement.internal.zzix
    @TargetApi(MotionEventCompat.AXIS_DISTANCE)
    public final void zza(JobParameters jobParameters, boolean z) {
        jobFinished(jobParameters, false);
    }

    @Override // com.google.android.gms.measurement.internal.zzix
    public final void zza(Intent intent) {
    }

    @Override // com.google.android.gms.measurement.internal.zzix
    public final boolean zza(int i) {
        throw new UnsupportedOperationException();
    }
}
