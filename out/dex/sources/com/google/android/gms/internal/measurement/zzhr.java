package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhr extends zzhp<zzhs, zzhs> {
    zzhr() {
    }

    private static void zza(Object obj, zzhs zzhsVar) {
        ((zzey) obj).zzahz = zzhsVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final /* synthetic */ void zza(zzhs zzhsVar, int i, long j) {
        zzhsVar.zzb(i << 3, Long.valueOf(j));
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final /* synthetic */ void zza(zzhs zzhsVar, int i, zzdp zzdpVar) {
        zzhsVar.zzb((i << 3) | 2, zzdpVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final /* synthetic */ void zza(zzhs zzhsVar, int i, zzhs zzhsVar2) {
        zzhsVar.zzb((i << 3) | 3, zzhsVar2);
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final /* synthetic */ void zza(zzhs zzhsVar, zzim zzimVar) throws IOException {
        zzhsVar.zzb(zzimVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final boolean zza(zzgy zzgyVar) {
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final /* synthetic */ void zzb(zzhs zzhsVar, int i, long j) {
        zzhsVar.zzb((i << 3) | 1, Long.valueOf(j));
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final /* synthetic */ void zzc(zzhs zzhsVar, int i, int i2) {
        zzhsVar.zzb((i << 3) | 5, Integer.valueOf(i2));
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final /* synthetic */ void zzc(zzhs zzhsVar, zzim zzimVar) throws IOException {
        zzhsVar.zza(zzimVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final /* synthetic */ void zze(Object obj, zzhs zzhsVar) {
        zza(obj, zzhsVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final /* synthetic */ void zzf(Object obj, zzhs zzhsVar) {
        zza(obj, zzhsVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final /* synthetic */ zzhs zzg(zzhs zzhsVar, zzhs zzhsVar2) {
        zzhs zzhsVar3 = zzhsVar;
        zzhs zzhsVar4 = zzhsVar2;
        return zzhsVar4.equals(zzhs.zzwq()) ? zzhsVar3 : zzhs.zza(zzhsVar3, zzhsVar4);
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final void zzj(Object obj) {
        ((zzey) obj).zzahz.zzry();
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final /* synthetic */ zzhs zzp(zzhs zzhsVar) {
        zzhs zzhsVar2 = zzhsVar;
        zzhsVar2.zzry();
        return zzhsVar2;
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final /* synthetic */ int zzt(zzhs zzhsVar) {
        return zzhsVar.zzuk();
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final /* synthetic */ zzhs zzwp() {
        return zzhs.zzwr();
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final /* synthetic */ zzhs zzx(Object obj) {
        return ((zzey) obj).zzahz;
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final /* synthetic */ zzhs zzy(Object obj) {
        zzhs zzhsVar = ((zzey) obj).zzahz;
        if (zzhsVar != zzhs.zzwq()) {
            return zzhsVar;
        }
        zzhs zzhsVarZzwr = zzhs.zzwr();
        zza(obj, zzhsVarZzwr);
        return zzhsVarZzwr;
    }

    @Override // com.google.android.gms.internal.measurement.zzhp
    final /* synthetic */ int zzz(zzhs zzhsVar) {
        return zzhsVar.zzws();
    }
}
