package com.google.android.gms.measurement.internal;

import android.os.Bundle;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzht implements Runnable {
    private final /* synthetic */ zzhq zzqz;
    private final /* synthetic */ boolean zzra;
    private final /* synthetic */ zzhr zzrb;
    private final /* synthetic */ zzhr zzrc;

    zzht(zzhq zzhqVar, boolean z, zzhr zzhrVar, zzhr zzhrVar2) {
        this.zzqz = zzhqVar;
        this.zzra = z;
        this.zzrb = zzhrVar;
        this.zzrc = zzhrVar2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        if (this.zzqz.zzad().zzz(this.zzqz.zzr().zzag())) {
            z = this.zzra && this.zzqz.zzqo != null;
            if (z) {
                zzhq zzhqVar = this.zzqz;
                zzhqVar.zza(zzhqVar.zzqo, true);
            }
        } else {
            if (this.zzra && this.zzqz.zzqo != null) {
                zzhq zzhqVar2 = this.zzqz;
                zzhqVar2.zza(zzhqVar2.zzqo, true);
            }
            z = false;
        }
        zzhr zzhrVar = this.zzrb;
        if ((zzhrVar != null && zzhrVar.zzqw == this.zzrc.zzqw && zzjs.zzs(this.zzrb.zzqv, this.zzrc.zzqv) && zzjs.zzs(this.zzrb.zzqu, this.zzrc.zzqu)) ? false : true) {
            Bundle bundle = new Bundle();
            zzhq.zza(this.zzrc, bundle, true);
            zzhr zzhrVar2 = this.zzrb;
            if (zzhrVar2 != null) {
                if (zzhrVar2.zzqu != null) {
                    bundle.putString("_pn", this.zzrb.zzqu);
                }
                bundle.putString("_pc", this.zzrb.zzqv);
                bundle.putLong("_pi", this.zzrb.zzqw);
            }
            if (this.zzqz.zzad().zzz(this.zzqz.zzr().zzag()) && z) {
                long jZzjb = this.zzqz.zzv().zzjb();
                if (jZzjb > 0) {
                    this.zzqz.zzz().zzb(bundle, jZzjb);
                }
            }
            this.zzqz.zzq().zza("auto", "_vs", bundle);
        }
        zzhq zzhqVar3 = this.zzqz;
        zzhqVar3.zzqo = this.zzrc;
        zzhqVar3.zzs().zza(this.zzrc);
    }
}
