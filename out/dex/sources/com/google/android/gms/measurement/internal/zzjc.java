package com.google.android.gms.measurement.internal;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.MotionEventCompat;
import com.google.android.gms.common.util.Clock;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzjc extends zzjh {
    private final AlarmManager zzsj;
    private final zzaa zzsk;
    private Integer zzsl;

    protected zzjc(zzjg zzjgVar) {
        super(zzjgVar);
        this.zzsj = (AlarmManager) getContext().getSystemService(NotificationCompat.CATEGORY_ALARM);
        this.zzsk = new zzjf(this, zzjgVar.zzjt(), zzjgVar);
    }

    private final int getJobId() {
        if (this.zzsl == null) {
            String strValueOf = String.valueOf(getContext().getPackageName());
            this.zzsl = Integer.valueOf((strValueOf.length() != 0 ? "measurement".concat(strValueOf) : new String("measurement")).hashCode());
        }
        return this.zzsl.intValue();
    }

    @TargetApi(MotionEventCompat.AXIS_DISTANCE)
    private final void zzjd() {
        JobScheduler jobScheduler = (JobScheduler) getContext().getSystemService("jobscheduler");
        int jobId = getJobId();
        zzab().zzgs().zza("Cancelling job. JobID", Integer.valueOf(jobId));
        jobScheduler.cancel(jobId);
    }

    private final PendingIntent zzje() {
        Context context = getContext();
        return PendingIntent.getBroadcast(context, 0, new Intent().setClassName(context, "com.google.android.gms.measurement.AppMeasurementReceiver").setAction("com.google.android.gms.measurement.UPLOAD"), 0);
    }

    public final void cancel() {
        zzbi();
        this.zzsj.cancel(zzje());
        this.zzsk.cancel();
        if (Build.VERSION.SDK_INT >= 24) {
            zzjd();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ zzfc zzaa() {
        return super.zzaa();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ zzef zzab() {
        return super.zzab();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzeo zzac() {
        return super.zzac();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzs zzad() {
        return super.zzad();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ zzr zzae() {
        return super.zzae();
    }

    @Override // com.google.android.gms.measurement.internal.zzjh
    protected final boolean zzbk() {
        this.zzsj.cancel(zzje());
        if (Build.VERSION.SDK_INT < 24) {
            return false;
        }
        zzjd();
        return false;
    }

    @Override // com.google.android.gms.measurement.internal.zzje
    public final /* bridge */ /* synthetic */ zzjo zzgw() {
        return super.zzgw();
    }

    @Override // com.google.android.gms.measurement.internal.zzje
    public final /* bridge */ /* synthetic */ zzp zzgx() {
        return super.zzgx();
    }

    @Override // com.google.android.gms.measurement.internal.zzje
    public final /* bridge */ /* synthetic */ zzx zzgy() {
        return super.zzgy();
    }

    @Override // com.google.android.gms.measurement.internal.zzje
    public final /* bridge */ /* synthetic */ zzfd zzgz() {
        return super.zzgz();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzl() {
        super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzm() {
        super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzn() {
        super.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzo() {
        super.zzo();
    }

    public final void zzv(long j) {
        zzbi();
        zzae();
        Context context = getContext();
        if (!zzez.zzl(context)) {
            zzab().zzgr().zzao("Receiver not registered/enabled");
        }
        if (!zzjs.zzb(context, false)) {
            zzab().zzgr().zzao("Service not registered/enabled");
        }
        cancel();
        long jElapsedRealtime = zzx().elapsedRealtime() + j;
        if (j < Math.max(0L, zzak.zzhc.get(null).longValue()) && !this.zzsk.zzcp()) {
            zzab().zzgs().zzao("Scheduling upload with DelayedRunnable");
            this.zzsk.zzv(j);
        }
        zzae();
        if (Build.VERSION.SDK_INT < 24) {
            zzab().zzgs().zzao("Scheduling upload with AlarmManager");
            this.zzsj.setInexactRepeating(2, jElapsedRealtime, Math.max(zzak.zzgx.get(null).longValue(), j), zzje());
            return;
        }
        zzab().zzgs().zzao("Scheduling upload with JobScheduler");
        Context context2 = getContext();
        ComponentName componentName = new ComponentName(context2, "com.google.android.gms.measurement.AppMeasurementJobService");
        int jobId = getJobId();
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString("action", "com.google.android.gms.measurement.UPLOAD");
        JobInfo jobInfoBuild = new JobInfo.Builder(jobId, componentName).setMinimumLatency(j).setOverrideDeadline(j << 1).setExtras(persistableBundle).build();
        zzab().zzgs().zza("Scheduling job. JobID", Integer.valueOf(jobId));
        com.google.android.gms.internal.measurement.zzi.zza(context2, jobInfoBuild, "com.google.android.gms", "UploadAlarm");
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzac zzw() {
        return super.zzw();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ Clock zzx() {
        return super.zzx();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzed zzy() {
        return super.zzy();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzjs zzz() {
        return super.zzz();
    }
}
