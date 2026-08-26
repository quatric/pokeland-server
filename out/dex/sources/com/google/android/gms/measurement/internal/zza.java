package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zza extends zzd {
    private final Map<String, Long> zzby;
    private final Map<String, Integer> zzbz;
    private long zzca;

    public zza(zzfj zzfjVar) {
        super(zzfjVar);
        this.zzbz = new ArrayMap();
        this.zzby = new ArrayMap();
    }

    @WorkerThread
    private final void zza(long j, zzhr zzhrVar) {
        if (zzhrVar == null) {
            zzab().zzgs().zzao("Not logging ad exposure. No active activity");
            return;
        }
        if (j < 1000) {
            zzab().zzgs().zza("Not logging ad exposure. Less than 1000 ms. exposure", Long.valueOf(j));
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putLong("_xt", j);
        zzhq.zza(zzhrVar, bundle, true);
        zzq().logEvent("am", "_xa", bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zza(String str, long j) {
        zzm();
        zzo();
        Preconditions.checkNotEmpty(str);
        if (this.zzbz.isEmpty()) {
            this.zzca = j;
        }
        Integer num = this.zzbz.get(str);
        if (num != null) {
            this.zzbz.put(str, Integer.valueOf(num.intValue() + 1));
        } else if (this.zzbz.size() >= 100) {
            zzab().zzgn().zzao("Too many ads visible");
        } else {
            this.zzbz.put(str, 1);
            this.zzby.put(str, Long.valueOf(j));
        }
    }

    @WorkerThread
    private final void zza(String str, long j, zzhr zzhrVar) {
        if (zzhrVar == null) {
            zzab().zzgs().zzao("Not logging ad unit exposure. No active activity");
            return;
        }
        if (j < 1000) {
            zzab().zzgs().zza("Not logging ad unit exposure. Less than 1000 ms. exposure", Long.valueOf(j));
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("_ai", str);
        bundle.putLong("_xt", j);
        zzhq.zza(zzhrVar, bundle, true);
        zzq().logEvent("am", "_xu", bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zzb(String str, long j) {
        zzm();
        zzo();
        Preconditions.checkNotEmpty(str);
        Integer num = this.zzbz.get(str);
        if (num == null) {
            zzab().zzgk().zza("Call to endAdUnitExposure for unknown ad unit id", str);
            return;
        }
        zzhr zzhrVarZzin = zzt().zzin();
        int iIntValue = num.intValue() - 1;
        if (iIntValue != 0) {
            this.zzbz.put(str, Integer.valueOf(iIntValue));
            return;
        }
        this.zzbz.remove(str);
        Long l = this.zzby.get(str);
        if (l == null) {
            zzab().zzgk().zzao("First ad unit exposure time was never set");
        } else {
            long jLongValue = j - l.longValue();
            this.zzby.remove(str);
            zza(str, jLongValue, zzhrVarZzin);
        }
        if (this.zzbz.isEmpty()) {
            long j2 = this.zzca;
            if (j2 == 0) {
                zzab().zzgk().zzao("First ad exposure time was never set");
            } else {
                zza(j - j2, zzhrVarZzin);
                this.zzca = 0L;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zzd(long j) {
        Iterator<String> it = this.zzby.keySet().iterator();
        while (it.hasNext()) {
            this.zzby.put(it.next(), Long.valueOf(j));
        }
        if (this.zzby.isEmpty()) {
            return;
        }
        this.zzca = j;
    }

    public final void beginAdUnitExposure(String str, long j) {
        if (str == null || str.length() == 0) {
            zzab().zzgk().zzao("Ad unit id must be a non-empty string");
        } else {
            zzaa().zza(new zzc(this, str, j));
        }
    }

    public final void endAdUnitExposure(String str, long j) {
        if (str == null || str.length() == 0) {
            zzab().zzgk().zzao("Ad unit id must be a non-empty string");
        } else {
            zzaa().zza(new zzb(this, str, j));
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

    @WorkerThread
    public final void zzc(long j) {
        zzhr zzhrVarZzin = zzt().zzin();
        for (String str : this.zzby.keySet()) {
            zza(str, j - this.zzby.get(str).longValue(), zzhrVarZzin);
        }
        if (!this.zzby.isEmpty()) {
            zza(j - this.zzca, zzhrVarZzin);
        }
        zzd(j);
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
