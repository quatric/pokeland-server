package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.RemoteException;
import java.util.ArrayList;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzii implements Runnable {
    private final /* synthetic */ String zzas;
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzp zzdi;
    private final /* synthetic */ zzn zzpg;
    private final /* synthetic */ zzhv zzrd;
    private final /* synthetic */ String zzx;

    zzii(zzhv zzhvVar, String str, String str2, zzn zznVar, com.google.android.gms.internal.measurement.zzp zzpVar) {
        this.zzrd = zzhvVar;
        this.zzx = str;
        this.zzas = str2;
        this.zzpg = zznVar;
        this.zzdi = zzpVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ArrayList<Bundle> arrayList = new ArrayList<>();
        try {
            try {
                zzdx zzdxVar = this.zzrd.zzrf;
                if (zzdxVar == null) {
                    this.zzrd.zzab().zzgk().zza("Failed to get conditional properties", this.zzx, this.zzas);
                    this.zzrd.zzz().zza(this.zzdi, arrayList);
                } else {
                    ArrayList<Bundle> arrayListZzd = zzjs.zzd(zzdxVar.zza(this.zzx, this.zzas, this.zzpg));
                    this.zzrd.zzir();
                    this.zzrd.zzz().zza(this.zzdi, arrayListZzd);
                }
            } catch (RemoteException e) {
                this.zzrd.zzab().zzgk().zza("Failed to get conditional properties", this.zzx, this.zzas, e);
                this.zzrd.zzz().zza(this.zzdi, arrayList);
            }
        } catch (Throwable th) {
            this.zzrd.zzz().zza(this.zzdi, arrayList);
            throw th;
        }
    }
}
