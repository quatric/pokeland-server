package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzee implements Runnable {
    private final /* synthetic */ int zzka;
    private final /* synthetic */ String zzkb;
    private final /* synthetic */ Object zzkc;
    private final /* synthetic */ Object zzkd;
    private final /* synthetic */ Object zzke;
    private final /* synthetic */ zzef zzkf;

    zzee(zzef zzefVar, int i, String str, Object obj, Object obj2, Object obj3) {
        this.zzkf = zzefVar;
        this.zzka = i;
        this.zzkb = str;
        this.zzkc = obj;
        this.zzkd = obj2;
        this.zzke = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzeo zzeoVarZzac = this.zzkf.zzj.zzac();
        if (!zzeoVarZzac.isInitialized()) {
            this.zzkf.zza(6, "Persisted config not initialized. Not logging error/warn");
            return;
        }
        if (this.zzkf.zzkg == 0) {
            if (this.zzkf.zzad().zzbn()) {
                zzef zzefVar = this.zzkf;
                zzefVar.zzae();
                zzefVar.zzkg = 'C';
            } else {
                zzef zzefVar2 = this.zzkf;
                zzefVar2.zzae();
                zzefVar2.zzkg = 'c';
            }
        }
        if (this.zzkf.zzr < 0) {
            zzef zzefVar3 = this.zzkf;
            zzefVar3.zzr = zzefVar3.zzad().zzao();
        }
        char cCharAt = "01VDIWEA?".charAt(this.zzka);
        char c = this.zzkf.zzkg;
        long j = this.zzkf.zzr;
        String strZza = zzef.zza(true, this.zzkb, this.zzkc, this.zzkd, this.zzke);
        StringBuilder sb = new StringBuilder(String.valueOf(strZza).length() + 24);
        sb.append("2");
        sb.append(cCharAt);
        sb.append(c);
        sb.append(j);
        sb.append(":");
        sb.append(strZza);
        String string = sb.toString();
        if (string.length() > 1024) {
            string = this.zzkb.substring(0, 1024);
        }
        zzeoVarZzac.zzli.zzc(string, 1L);
    }
}
