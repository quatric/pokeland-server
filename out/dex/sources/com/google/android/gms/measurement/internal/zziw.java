package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zziw extends zzg {
    private Handler handler;

    @VisibleForTesting
    private long zzse;

    @VisibleForTesting
    private long zzsf;
    private final zzaa zzsg;
    private final zzaa zzsh;

    zziw(zzfj zzfjVar) {
        super(zzfjVar);
        this.zzsg = new zziz(this, this.zzj);
        this.zzsh = new zziy(this, this.zzj);
        this.zzse = zzx().elapsedRealtime();
        this.zzsf = this.zzse;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zzaa(long j) {
        zzo();
        zziy();
        if (zzad().zze(zzr().zzag(), zzak.zzid)) {
            zzac().zzmd.set(false);
        }
        zzab().zzgs().zza("Activity resumed, time", Long.valueOf(j));
        this.zzse = j;
        this.zzsf = this.zzse;
        if (this.zzj.isEnabled()) {
            if (zzad().zzx(zzr().zzag())) {
                zza(zzx().currentTimeMillis(), false);
                return;
            }
            this.zzsg.cancel();
            this.zzsh.cancel();
            if (zzac().zzx(zzx().currentTimeMillis())) {
                zzac().zzlw.set(true);
                zzac().zzmb.set(0L);
            }
            if (zzac().zzlw.get()) {
                this.zzsg.zzv(Math.max(0L, zzac().zzlu.get() - zzac().zzmb.get()));
            } else {
                this.zzsh.zzv(Math.max(0L, 3600000 - zzac().zzmb.get()));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zzab(long j) {
        zzo();
        zziy();
        if (zzad().zze(zzr().zzag(), zzak.zzid)) {
            zzac().zzmd.set(true);
        }
        this.zzsg.cancel();
        this.zzsh.cancel();
        zzab().zzgs().zza("Activity paused, time", Long.valueOf(j));
        if (this.zzse != 0) {
            zzac().zzmb.set(zzac().zzmb.get() + (j - this.zzse));
        }
    }

    @WorkerThread
    private final void zzac(long j) {
        zzo();
        zzab().zzgs().zza("Session started, time", Long.valueOf(zzx().elapsedRealtime()));
        Long lValueOf = zzad().zzv(zzr().zzag()) ? Long.valueOf(j / 1000) : null;
        zzq().zza("auto", "_sid", lValueOf, j);
        zzac().zzlw.set(false);
        Bundle bundle = new Bundle();
        if (zzad().zzv(zzr().zzag())) {
            bundle.putLong("_sid", lValueOf.longValue());
        }
        zzq().zza("auto", "_s", j, bundle);
        zzac().zzma.set(j);
    }

    private final void zziy() {
        synchronized (this) {
            if (this.handler == null) {
                this.handler = new com.google.android.gms.internal.measurement.zzh(Looper.getMainLooper());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zzjc() {
        zzo();
        zza(false, false);
        zzp().zzc(zzx().elapsedRealtime());
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @WorkerThread
    final void zza(long j, boolean z) {
        zzo();
        zziy();
        this.zzsg.cancel();
        this.zzsh.cancel();
        if (zzac().zzx(j)) {
            zzac().zzlw.set(true);
            zzac().zzmb.set(0L);
        }
        if (z && zzad().zzy(zzr().zzag())) {
            zzac().zzma.set(j);
        }
        if (zzac().zzlw.get()) {
            zzac(j);
        } else {
            this.zzsh.zzv(Math.max(0L, 3600000 - zzac().zzmb.get()));
        }
    }

    @WorkerThread
    public final boolean zza(boolean z, boolean z2) {
        zzo();
        zzbi();
        long jElapsedRealtime = zzx().elapsedRealtime();
        zzac().zzma.set(zzx().currentTimeMillis());
        long j = jElapsedRealtime - this.zzse;
        if (!z && j < 1000) {
            zzab().zzgs().zza("Screen exposed for less than 1000 ms. Event not sent. time", Long.valueOf(j));
            return false;
        }
        zzac().zzmb.set(j);
        zzab().zzgs().zza("Recording user engagement, ms", Long.valueOf(j));
        Bundle bundle = new Bundle();
        bundle.putLong("_et", j);
        zzhq.zza(zzt().zzin(), bundle, true);
        if (zzad().zzz(zzr().zzag())) {
            if (zzad().zze(zzr().zzag(), zzak.zzii)) {
                if (!z2) {
                    zzjb();
                }
            } else if (z2) {
                bundle.putLong("_fr", 1L);
            } else {
                zzjb();
            }
        }
        if (!zzad().zze(zzr().zzag(), zzak.zzii) || !z2) {
            zzq().logEvent("auto", "_e", bundle);
        }
        this.zzse = jElapsedRealtime;
        this.zzsh.cancel();
        this.zzsh.zzv(Math.max(0L, 3600000 - zzac().zzmb.get()));
        return true;
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

    @Override // com.google.android.gms.measurement.internal.zzg
    protected final boolean zzbk() {
        return false;
    }

    @WorkerThread
    final void zziz() {
        zzo();
        this.zzsg.cancel();
        this.zzsh.cancel();
        this.zzse = 0L;
        this.zzsf = this.zzse;
    }

    @WorkerThread
    @VisibleForTesting
    protected final void zzja() {
        zzo();
        zzac(zzx().currentTimeMillis());
    }

    @WorkerThread
    @VisibleForTesting
    final long zzjb() {
        long jElapsedRealtime = zzx().elapsedRealtime();
        long j = jElapsedRealtime - this.zzsf;
        this.zzsf = jElapsedRealtime;
        return j;
    }

    @Override // com.google.android.gms.measurement.internal.zzd, com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzl() {
        super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzd, com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzm() {
        super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzd, com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzn() {
        super.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzd, com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzo() {
        super.zzo();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zza zzp() {
        return super.zzp();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzgp zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzdy zzr() {
        return super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzhv zzs() {
        return super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzhq zzt() {
        return super.zzt();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzeb zzu() {
        return super.zzu();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zziw zzv() {
        return super.zzv();
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
