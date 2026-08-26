package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzei implements zzim {
    private final zzee zzadn;

    private zzei(zzee zzeeVar) {
        this.zzadn = (zzee) zzez.zza(zzeeVar, "output");
        this.zzadn.zzaed = this;
    }

    public static zzei zza(zzee zzeeVar) {
        return zzeeVar.zzaed != null ? zzeeVar.zzaed : new zzei(zzeeVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zza(int i, double d) throws IOException {
        this.zzadn.zza(i, d);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zza(int i, float f) throws IOException {
        this.zzadn.zza(i, f);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zza(int i, long j) throws IOException {
        this.zzadn.zza(i, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zza(int i, zzdp zzdpVar) throws IOException {
        this.zzadn.zza(i, zzdpVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final <K, V> void zza(int i, zzfz<K, V> zzfzVar, Map<K, V> map) throws IOException {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.zzadn.zzb(i, 2);
            this.zzadn.zzbf(zzga.zza(zzfzVar, entry.getKey(), entry.getValue()));
            zzga.zza(this.zzadn, zzfzVar, entry.getKey(), entry.getValue());
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zza(int i, Object obj) throws IOException {
        if (obj instanceof zzdp) {
            this.zzadn.zzb(i, (zzdp) obj);
        } else {
            this.zzadn.zzb(i, (zzgi) obj);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zza(int i, Object obj, zzgx zzgxVar) throws IOException {
        this.zzadn.zza(i, (zzgi) obj, zzgxVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zza(int i, List<String> list) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzfp)) {
            while (i2 < list.size()) {
                this.zzadn.zzb(i, list.get(i2));
                i2++;
            }
            return;
        }
        zzfp zzfpVar = (zzfp) list;
        while (i2 < list.size()) {
            Object objZzbw = zzfpVar.zzbw(i2);
            if (objZzbw instanceof String) {
                this.zzadn.zzb(i, (String) objZzbw);
            } else {
                this.zzadn.zza(i, (zzdp) objZzbw);
            }
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zza(int i, List<?> list, zzgx zzgxVar) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zza(i, list.get(i2), zzgxVar);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zza(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzadn.zzc(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zzadn.zzb(i, 2);
        int iZzbj = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzbj += zzee.zzbj(list.get(i3).intValue());
        }
        this.zzadn.zzbf(iZzbj);
        while (i2 < list.size()) {
            this.zzadn.zzbe(list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzb(int i, long j) throws IOException {
        this.zzadn.zzb(i, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzb(int i, Object obj, zzgx zzgxVar) throws IOException {
        zzee zzeeVar = this.zzadn;
        zzeeVar.zzb(i, 3);
        zzgxVar.zza((zzgi) obj, zzeeVar.zzaed);
        zzeeVar.zzb(i, 4);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzb(int i, String str) throws IOException {
        this.zzadn.zzb(i, str);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzb(int i, List<zzdp> list) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zzadn.zza(i, list.get(i2));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzb(int i, List<?> list, zzgx zzgxVar) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzb(i, list.get(i2), zzgxVar);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzb(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzadn.zzf(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zzadn.zzb(i, 2);
        int iZzbm = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzbm += zzee.zzbm(list.get(i3).intValue());
        }
        this.zzadn.zzbf(iZzbm);
        while (i2 < list.size()) {
            this.zzadn.zzbh(list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzb(int i, boolean z) throws IOException {
        this.zzadn.zzb(i, z);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzbr(int i) throws IOException {
        this.zzadn.zzb(i, 3);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzbs(int i) throws IOException {
        this.zzadn.zzb(i, 4);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzc(int i, int i2) throws IOException {
        this.zzadn.zzc(i, i2);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzc(int i, long j) throws IOException {
        this.zzadn.zzc(i, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzc(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzadn.zza(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zzadn.zzb(i, 2);
        int iZzbq = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzbq += zzee.zzbq(list.get(i3).longValue());
        }
        this.zzadn.zzbf(iZzbq);
        while (i2 < list.size()) {
            this.zzadn.zzbn(list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzd(int i, int i2) throws IOException {
        this.zzadn.zzd(i, i2);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzd(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzadn.zza(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zzadn.zzb(i, 2);
        int iZzbr = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzbr += zzee.zzbr(list.get(i3).longValue());
        }
        this.zzadn.zzbf(iZzbr);
        while (i2 < list.size()) {
            this.zzadn.zzbn(list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zze(int i, int i2) throws IOException {
        this.zzadn.zze(i, i2);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zze(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzadn.zzc(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zzadn.zzb(i, 2);
        int iZzbt = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzbt += zzee.zzbt(list.get(i3).longValue());
        }
        this.zzadn.zzbf(iZzbt);
        while (i2 < list.size()) {
            this.zzadn.zzbp(list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzf(int i, int i2) throws IOException {
        this.zzadn.zzf(i, i2);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzf(int i, List<Float> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzadn.zza(i, list.get(i2).floatValue());
                i2++;
            }
            return;
        }
        this.zzadn.zzb(i, 2);
        int iZzb = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzb += zzee.zzb(list.get(i3).floatValue());
        }
        this.zzadn.zzbf(iZzb);
        while (i2 < list.size()) {
            this.zzadn.zza(list.get(i2).floatValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzg(int i, List<Double> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzadn.zza(i, list.get(i2).doubleValue());
                i2++;
            }
            return;
        }
        this.zzadn.zzb(i, 2);
        int iZze = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZze += zzee.zze(list.get(i3).doubleValue());
        }
        this.zzadn.zzbf(iZze);
        while (i2 < list.size()) {
            this.zzadn.zzd(list.get(i2).doubleValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzh(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzadn.zzc(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zzadn.zzb(i, 2);
        int iZzbo = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzbo += zzee.zzbo(list.get(i3).intValue());
        }
        this.zzadn.zzbf(iZzbo);
        while (i2 < list.size()) {
            this.zzadn.zzbe(list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzi(int i, long j) throws IOException {
        this.zzadn.zza(i, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzi(int i, List<Boolean> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzadn.zzb(i, list.get(i2).booleanValue());
                i2++;
            }
            return;
        }
        this.zzadn.zzb(i, 2);
        int iZzr = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzr += zzee.zzr(list.get(i3).booleanValue());
        }
        this.zzadn.zzbf(iZzr);
        while (i2 < list.size()) {
            this.zzadn.zzq(list.get(i2).booleanValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzj(int i, long j) throws IOException {
        this.zzadn.zzc(i, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzj(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzadn.zzd(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zzadn.zzb(i, 2);
        int iZzbk = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzbk += zzee.zzbk(list.get(i3).intValue());
        }
        this.zzadn.zzbf(iZzbk);
        while (i2 < list.size()) {
            this.zzadn.zzbf(list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzk(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzadn.zzf(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zzadn.zzb(i, 2);
        int iZzbn = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzbn += zzee.zzbn(list.get(i3).intValue());
        }
        this.zzadn.zzbf(iZzbn);
        while (i2 < list.size()) {
            this.zzadn.zzbh(list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzl(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzadn.zzc(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zzadn.zzb(i, 2);
        int iZzbu = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzbu += zzee.zzbu(list.get(i3).longValue());
        }
        this.zzadn.zzbf(iZzbu);
        while (i2 < list.size()) {
            this.zzadn.zzbp(list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzm(int i, int i2) throws IOException {
        this.zzadn.zzf(i, i2);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzm(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzadn.zze(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zzadn.zzb(i, 2);
        int iZzbl = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzbl += zzee.zzbl(list.get(i3).intValue());
        }
        this.zzadn.zzbf(iZzbl);
        while (i2 < list.size()) {
            this.zzadn.zzbg(list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzn(int i, int i2) throws IOException {
        this.zzadn.zzc(i, i2);
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final void zzn(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zzadn.zzb(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zzadn.zzb(i, 2);
        int iZzbs = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzbs += zzee.zzbs(list.get(i3).longValue());
        }
        this.zzadn.zzbf(iZzbs);
        while (i2 < list.size()) {
            this.zzadn.zzbo(list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzim
    public final int zztk() {
        return zzey.zzd.zzaio;
    }
}
