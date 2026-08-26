package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzgz {
    private static final Class<?> zzalg = zzwf();
    private static final zzhp<?, ?> zzalh = zzt(false);
    private static final zzhp<?, ?> zzali = zzt(true);
    private static final zzhp<?, ?> zzalj = new zzhr();

    static <UT, UB> UB zza(int i, int i2, UB ub, zzhp<UT, UB> zzhpVar) {
        if (ub == null) {
            ub = zzhpVar.zzwp();
        }
        zzhpVar.zza(ub, i, i2);
        return ub;
    }

    static <UT, UB> UB zza(int i, List<Integer> list, zzfe zzfeVar, UB ub, zzhp<UT, UB> zzhpVar) {
        UB ub2;
        if (zzfeVar == null) {
            return ub;
        }
        if (!(list instanceof RandomAccess)) {
            Iterator<Integer> it = list.iterator();
            loop1: while (true) {
                ub2 = ub;
                while (it.hasNext()) {
                    int iIntValue = it.next().intValue();
                    if (!zzfeVar.zzg(iIntValue)) {
                        ub = (UB) zza(i, iIntValue, ub2, zzhpVar);
                        it.remove();
                    }
                }
                break loop1;
            }
        } else {
            int size = list.size();
            ub2 = ub;
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int iIntValue2 = list.get(i3).intValue();
                if (zzfeVar.zzg(iIntValue2)) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(iIntValue2));
                    }
                    i2++;
                } else {
                    ub2 = (UB) zza(i, iIntValue2, ub2, zzhpVar);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
        }
        return ub2;
    }

    public static void zza(int i, List<String> list, zzim zzimVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zza(i, list);
    }

    public static void zza(int i, List<?> list, zzim zzimVar, zzgx zzgxVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zza(i, list, zzgxVar);
    }

    public static void zza(int i, List<Double> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzg(i, list, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static <T, FT extends zzeq<FT>> void zza(zzen<FT> zzenVar, T t, T t2) {
        zzeo<T> zzeoVarZzh = zzenVar.zzh(t2);
        if (zzeoVarZzh.zzaex.isEmpty()) {
            return;
        }
        zzenVar.zzi(t).zza(zzeoVarZzh);
    }

    static <T> void zza(zzgb zzgbVar, T t, T t2, long j) {
        zzhv.zza(t, j, zzgbVar.zzb(zzhv.zzp(t, j), zzhv.zzp(t2, j)));
    }

    static <T, UT, UB> void zza(zzhp<UT, UB> zzhpVar, T t, T t2) {
        zzhpVar.zze(t, zzhpVar.zzg(zzhpVar.zzx(t), zzhpVar.zzx(t2)));
    }

    static int zzaa(List<Integer> list) {
        int iZzbl;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfa) {
            zzfa zzfaVar = (zzfa) list;
            iZzbl = 0;
            while (i < size) {
                iZzbl += zzee.zzbl(zzfaVar.getInt(i));
                i++;
            }
        } else {
            iZzbl = 0;
            while (i < size) {
                iZzbl += zzee.zzbl(list.get(i).intValue());
                i++;
            }
        }
        return iZzbl;
    }

    static int zzab(List<?> list) {
        return list.size() << 2;
    }

    static int zzac(List<?> list) {
        return list.size() << 3;
    }

    static int zzad(List<?> list) {
        return list.size();
    }

    public static void zzb(int i, List<zzdp> list, zzim zzimVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzb(i, list);
    }

    public static void zzb(int i, List<?> list, zzim zzimVar, zzgx zzgxVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzb(i, list, zzgxVar);
    }

    public static void zzb(int i, List<Float> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzf(i, list, z);
    }

    static int zzc(int i, Object obj, zzgx zzgxVar) {
        return obj instanceof zzfn ? zzee.zza(i, (zzfn) obj) : zzee.zzb(i, (zzgi) obj, zzgxVar);
    }

    static int zzc(int i, List<?> list) {
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int iZzbi = zzee.zzbi(i) * size;
        if (list instanceof zzfp) {
            zzfp zzfpVar = (zzfp) list;
            while (i2 < size) {
                Object objZzbw = zzfpVar.zzbw(i2);
                iZzbi += objZzbw instanceof zzdp ? zzee.zzb((zzdp) objZzbw) : zzee.zzds((String) objZzbw);
                i2++;
            }
        } else {
            while (i2 < size) {
                Object obj = list.get(i2);
                iZzbi += obj instanceof zzdp ? zzee.zzb((zzdp) obj) : zzee.zzds((String) obj);
                i2++;
            }
        }
        return iZzbi;
    }

    static int zzc(int i, List<?> list, zzgx zzgxVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzbi = zzee.zzbi(i) * size;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            iZzbi += obj instanceof zzfn ? zzee.zza((zzfn) obj) : zzee.zzb((zzgi) obj, zzgxVar);
        }
        return iZzbi;
    }

    public static void zzc(int i, List<Long> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzc(i, list, z);
    }

    static int zzd(int i, List<zzdp> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzbi = size * zzee.zzbi(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            iZzbi += zzee.zzb(list.get(i2));
        }
        return iZzbi;
    }

    static int zzd(int i, List<zzgi> list, zzgx zzgxVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzc = 0;
        for (int i2 = 0; i2 < size; i2++) {
            iZzc += zzee.zzc(i, list.get(i2), zzgxVar);
        }
        return iZzc;
    }

    public static void zzd(int i, List<Long> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzd(i, list, z);
    }

    static boolean zzd(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    public static void zze(int i, List<Long> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzn(i, list, z);
    }

    public static void zzf(int i, List<Long> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zze(i, list, z);
    }

    public static void zzg(int i, List<Long> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzl(i, list, z);
    }

    public static void zzg(Class<?> cls) {
        Class<?> cls2;
        if (!zzey.class.isAssignableFrom(cls) && (cls2 = zzalg) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zzh(int i, List<Integer> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zza(i, list, z);
    }

    public static void zzi(int i, List<Integer> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzj(i, list, z);
    }

    public static void zzj(int i, List<Integer> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzm(i, list, z);
    }

    public static void zzk(int i, List<Integer> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzb(i, list, z);
    }

    public static void zzl(int i, List<Integer> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzk(i, list, z);
    }

    public static void zzm(int i, List<Integer> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzh(i, list, z);
    }

    public static void zzn(int i, List<Boolean> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzi(i, list, z);
    }

    static int zzo(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zzu(list) + (list.size() * zzee.zzbi(i));
    }

    static int zzp(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzv(list) + (size * zzee.zzbi(i));
    }

    static int zzq(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzw(list) + (size * zzee.zzbi(i));
    }

    static int zzr(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzx(list) + (size * zzee.zzbi(i));
    }

    static int zzs(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzy(list) + (size * zzee.zzbi(i));
    }

    static int zzt(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzz(list) + (size * zzee.zzbi(i));
    }

    private static zzhp<?, ?> zzt(boolean z) {
        try {
            Class<?> clsZzwg = zzwg();
            if (clsZzwg == null) {
                return null;
            }
            return (zzhp) clsZzwg.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z));
        } catch (Throwable unused) {
            return null;
        }
    }

    static int zzu(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzaa(list) + (size * zzee.zzbi(i));
    }

    static int zzu(List<Long> list) {
        int iZzbq;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfw) {
            zzfw zzfwVar = (zzfw) list;
            iZzbq = 0;
            while (i < size) {
                iZzbq += zzee.zzbq(zzfwVar.getLong(i));
                i++;
            }
        } else {
            iZzbq = 0;
            while (i < size) {
                iZzbq += zzee.zzbq(list.get(i).longValue());
                i++;
            }
        }
        return iZzbq;
    }

    static int zzv(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzee.zzj(i, 0);
    }

    static int zzv(List<Long> list) {
        int iZzbr;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfw) {
            zzfw zzfwVar = (zzfw) list;
            iZzbr = 0;
            while (i < size) {
                iZzbr += zzee.zzbr(zzfwVar.getLong(i));
                i++;
            }
        } else {
            iZzbr = 0;
            while (i < size) {
                iZzbr += zzee.zzbr(list.get(i).longValue());
                i++;
            }
        }
        return iZzbr;
    }

    static int zzw(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzee.zzg(i, 0L);
    }

    static int zzw(List<Long> list) {
        int iZzbs;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfw) {
            zzfw zzfwVar = (zzfw) list;
            iZzbs = 0;
            while (i < size) {
                iZzbs += zzee.zzbs(zzfwVar.getLong(i));
                i++;
            }
        } else {
            iZzbs = 0;
            while (i < size) {
                iZzbs += zzee.zzbs(list.get(i).longValue());
                i++;
            }
        }
        return iZzbs;
    }

    public static zzhp<?, ?> zzwc() {
        return zzalh;
    }

    public static zzhp<?, ?> zzwd() {
        return zzali;
    }

    public static zzhp<?, ?> zzwe() {
        return zzalj;
    }

    private static Class<?> zzwf() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zzwg() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused) {
            return null;
        }
    }

    static int zzx(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzee.zzc(i, true);
    }

    static int zzx(List<Integer> list) {
        int iZzbo;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfa) {
            zzfa zzfaVar = (zzfa) list;
            iZzbo = 0;
            while (i < size) {
                iZzbo += zzee.zzbo(zzfaVar.getInt(i));
                i++;
            }
        } else {
            iZzbo = 0;
            while (i < size) {
                iZzbo += zzee.zzbo(list.get(i).intValue());
                i++;
            }
        }
        return iZzbo;
    }

    static int zzy(List<Integer> list) {
        int iZzbj;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfa) {
            zzfa zzfaVar = (zzfa) list;
            iZzbj = 0;
            while (i < size) {
                iZzbj += zzee.zzbj(zzfaVar.getInt(i));
                i++;
            }
        } else {
            iZzbj = 0;
            while (i < size) {
                iZzbj += zzee.zzbj(list.get(i).intValue());
                i++;
            }
        }
        return iZzbj;
    }

    static int zzz(List<Integer> list) {
        int iZzbk;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfa) {
            zzfa zzfaVar = (zzfa) list;
            iZzbk = 0;
            while (i < size) {
                iZzbk += zzee.zzbk(zzfaVar.getInt(i));
                i++;
            }
        } else {
            iZzbk = 0;
            while (i < size) {
                iZzbk += zzee.zzbk(list.get(i).intValue());
                i++;
            }
        }
        return iZzbk;
    }
}
