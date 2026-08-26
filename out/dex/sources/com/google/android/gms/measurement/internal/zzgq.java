package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzgq implements Runnable {
    private final /* synthetic */ String zzbn;
    private final /* synthetic */ zzgp zzpt;
    private final /* synthetic */ Object zzqa;
    private final /* synthetic */ long zzqb;
    private final /* synthetic */ String zzx;

    zzgq(zzgp zzgpVar, String str, String str2, Object obj, long j) {
        this.zzpt = zzgpVar;
        this.zzx = str;
        this.zzbn = str2;
        this.zzqa = obj;
        this.zzqb = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzpt.zza(this.zzx, this.zzbn, this.zzqa, this.zzqb);
    }
}
