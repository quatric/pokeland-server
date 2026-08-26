package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzp extends zzjh {
    zzp(zzjg zzjgVar) {
        super(zzjgVar);
    }

    private final Boolean zza(double d, com.google.android.gms.internal.measurement.zzbk.zzc zzcVar) {
        try {
            return zza(new BigDecimal(d), zzcVar, Math.ulp(d));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private final Boolean zza(long j, com.google.android.gms.internal.measurement.zzbk.zzc zzcVar) {
        try {
            return zza(new BigDecimal(j), zzcVar, 0.0d);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Boolean zza(com.google.android.gms.internal.measurement.zzbk.zza zzaVar, String str, List<com.google.android.gms.internal.measurement.zzbs.zze> list, long j) {
        Boolean boolZza;
        if (zzaVar.zzkd()) {
            Boolean boolZza2 = zza(j, zzaVar.zzke());
            if (boolZza2 == null) {
                return null;
            }
            if (!boolZza2.booleanValue()) {
                return false;
            }
        }
        HashSet hashSet = new HashSet();
        for (com.google.android.gms.internal.measurement.zzbk.zzb zzbVar : zzaVar.zzkc()) {
            if (zzbVar.zzkr().isEmpty()) {
                zzab().zzgn().zza("null or empty param name in filter. event", zzy().zzaj(str));
                return null;
            }
            hashSet.add(zzbVar.zzkr());
        }
        ArrayMap arrayMap = new ArrayMap();
        for (com.google.android.gms.internal.measurement.zzbs.zze zzeVar : list) {
            if (hashSet.contains(zzeVar.getName())) {
                if (zzeVar.zzna()) {
                    arrayMap.put(zzeVar.getName(), zzeVar.zzna() ? Long.valueOf(zzeVar.zznb()) : null);
                } else if (zzeVar.zznd()) {
                    arrayMap.put(zzeVar.getName(), zzeVar.zznd() ? Double.valueOf(zzeVar.zzne()) : null);
                } else {
                    if (!zzeVar.zzmx()) {
                        zzab().zzgn().zza("Unknown value for param. event, param", zzy().zzaj(str), zzy().zzak(zzeVar.getName()));
                        return null;
                    }
                    arrayMap.put(zzeVar.getName(), zzeVar.zzmy());
                }
            }
        }
        Iterator<com.google.android.gms.internal.measurement.zzbk.zzb> it = zzaVar.zzkc().iterator();
        while (true) {
            if (!it.hasNext()) {
                return true;
            }
            com.google.android.gms.internal.measurement.zzbk.zzb next = it.next();
            boolean z = next.zzkp() && next.zzkq();
            String strZzkr = next.zzkr();
            if (strZzkr.isEmpty()) {
                zzab().zzgn().zza("Event has empty param name. event", zzy().zzaj(str));
                return null;
            }
            V v = arrayMap.get(strZzkr);
            if (v instanceof Long) {
                if (!next.zzkn()) {
                    zzab().zzgn().zza("No number filter for long param. event, param", zzy().zzaj(str), zzy().zzak(strZzkr));
                    return null;
                }
                Boolean boolZza3 = zza(((Long) v).longValue(), next.zzko());
                if (boolZza3 == null) {
                    return null;
                }
                if (boolZza3.booleanValue() == z) {
                    return false;
                }
            } else if (v instanceof Double) {
                if (!next.zzkn()) {
                    zzab().zzgn().zza("No number filter for double param. event, param", zzy().zzaj(str), zzy().zzak(strZzkr));
                    return null;
                }
                Boolean boolZza4 = zza(((Double) v).doubleValue(), next.zzko());
                if (boolZza4 == null) {
                    return null;
                }
                if (boolZza4.booleanValue() == z) {
                    return false;
                }
            } else {
                if (!(v instanceof String)) {
                    if (v == 0) {
                        zzab().zzgs().zza("Missing param for filter. event, param", zzy().zzaj(str), zzy().zzak(strZzkr));
                        return false;
                    }
                    zzab().zzgn().zza("Unknown param type. event, param", zzy().zzaj(str), zzy().zzak(strZzkr));
                    return null;
                }
                if (next.zzkl()) {
                    boolZza = zza((String) v, next.zzkm());
                } else {
                    if (!next.zzkn()) {
                        zzab().zzgn().zza("No filter for String param. event, param", zzy().zzaj(str), zzy().zzak(strZzkr));
                        return null;
                    }
                    String str2 = (String) v;
                    if (!zzjo.zzbj(str2)) {
                        zzab().zzgn().zza("Invalid param value for number filter. event, param", zzy().zzaj(str), zzy().zzak(strZzkr));
                        return null;
                    }
                    boolZza = zza(str2, next.zzko());
                }
                if (boolZza == null) {
                    return null;
                }
                if (boolZza.booleanValue() == z) {
                    return false;
                }
            }
        }
    }

    private final Boolean zza(com.google.android.gms.internal.measurement.zzbk.zzd zzdVar, com.google.android.gms.internal.measurement.zzbs.zzk zzkVar) {
        com.google.android.gms.internal.measurement.zzbk.zzb zzbVarZzli = zzdVar.zzli();
        boolean zZzkq = zzbVarZzli.zzkq();
        if (zzkVar.zzna()) {
            if (zzbVarZzli.zzkn()) {
                return zza(zza(zzkVar.zznb(), zzbVarZzli.zzko()), zZzkq);
            }
            zzab().zzgn().zza("No number filter for long property. property", zzy().zzal(zzkVar.getName()));
            return null;
        }
        if (zzkVar.zznd()) {
            if (zzbVarZzli.zzkn()) {
                return zza(zza(zzkVar.zzne(), zzbVarZzli.zzko()), zZzkq);
            }
            zzab().zzgn().zza("No number filter for double property. property", zzy().zzal(zzkVar.getName()));
            return null;
        }
        if (!zzkVar.zzmx()) {
            zzab().zzgn().zza("User property has no value, property", zzy().zzal(zzkVar.getName()));
            return null;
        }
        if (zzbVarZzli.zzkl()) {
            return zza(zza(zzkVar.zzmy(), zzbVarZzli.zzkm()), zZzkq);
        }
        if (!zzbVarZzli.zzkn()) {
            zzab().zzgn().zza("No string or number filter defined. property", zzy().zzal(zzkVar.getName()));
        } else {
            if (zzjo.zzbj(zzkVar.zzmy())) {
                return zza(zza(zzkVar.zzmy(), zzbVarZzli.zzko()), zZzkq);
            }
            zzab().zzgn().zza("Invalid user property value for Numeric number filter. property, value", zzy().zzal(zzkVar.getName()), zzkVar.zzmy());
        }
        return null;
    }

    @VisibleForTesting
    private static Boolean zza(Boolean bool, boolean z) {
        if (bool == null) {
            return null;
        }
        return Boolean.valueOf(bool.booleanValue() != z);
    }

    private final Boolean zza(String str, com.google.android.gms.internal.measurement.zzbk.zzc zzcVar) {
        if (!zzjo.zzbj(str)) {
            return null;
        }
        try {
            return zza(new BigDecimal(str), zzcVar, 0.0d);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private final Boolean zza(String str, com.google.android.gms.internal.measurement.zzbk.zze.zza zzaVar, boolean z, String str2, List<String> list, String str3) {
        if (str == null) {
            return null;
        }
        if (zzaVar == com.google.android.gms.internal.measurement.zzbk.zze.zza.IN_LIST) {
            if (list == null || list.size() == 0) {
                return null;
            }
        } else if (str2 == null) {
            return null;
        }
        if (!z && zzaVar != com.google.android.gms.internal.measurement.zzbk.zze.zza.REGEXP) {
            str = str.toUpperCase(Locale.ENGLISH);
        }
        switch (zzo.zzdu[zzaVar.ordinal()]) {
            case 1:
                try {
                    return Boolean.valueOf(Pattern.compile(str3, z ? 0 : 66).matcher(str).matches());
                } catch (PatternSyntaxException unused) {
                    zzab().zzgn().zza("Invalid regular expression in REGEXP audience filter. expression", str3);
                    return null;
                }
            case 2:
                return Boolean.valueOf(str.startsWith(str2));
            case 3:
                return Boolean.valueOf(str.endsWith(str2));
            case 4:
                return Boolean.valueOf(str.contains(str2));
            case 5:
                return Boolean.valueOf(str.equals(str2));
            case 6:
                return Boolean.valueOf(list.contains(str));
            default:
                return null;
        }
    }

    @VisibleForTesting
    private final Boolean zza(String str, com.google.android.gms.internal.measurement.zzbk.zze zzeVar) {
        List<String> list;
        Preconditions.checkNotNull(zzeVar);
        if (str == null || !zzeVar.zzlk() || zzeVar.zzll() == com.google.android.gms.internal.measurement.zzbk.zze.zza.UNKNOWN_MATCH_TYPE) {
            return null;
        }
        if (zzeVar.zzll() == com.google.android.gms.internal.measurement.zzbk.zze.zza.IN_LIST) {
            if (zzeVar.zzlr() == 0) {
                return null;
            }
        } else if (!zzeVar.zzlm()) {
            return null;
        }
        com.google.android.gms.internal.measurement.zzbk.zze.zza zzaVarZzll = zzeVar.zzll();
        boolean zZzlp = zzeVar.zzlp();
        String strZzln = (zZzlp || zzaVarZzll == com.google.android.gms.internal.measurement.zzbk.zze.zza.REGEXP || zzaVarZzll == com.google.android.gms.internal.measurement.zzbk.zze.zza.IN_LIST) ? zzeVar.zzln() : zzeVar.zzln().toUpperCase(Locale.ENGLISH);
        if (zzeVar.zzlr() == 0) {
            list = null;
        } else {
            List<String> listZzlq = zzeVar.zzlq();
            if (!zZzlp) {
                ArrayList arrayList = new ArrayList(listZzlq.size());
                Iterator<String> it = listZzlq.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().toUpperCase(Locale.ENGLISH));
                }
                listZzlq = Collections.unmodifiableList(arrayList);
            }
            list = listZzlq;
        }
        return zza(str, zzaVarZzll, zZzlp, strZzln, list, zzaVarZzll == com.google.android.gms.internal.measurement.zzbk.zze.zza.REGEXP ? strZzln : null);
    }

    @VisibleForTesting
    private static Boolean zza(BigDecimal bigDecimal, com.google.android.gms.internal.measurement.zzbk.zzc zzcVar, double d) {
        BigDecimal bigDecimal2;
        BigDecimal bigDecimal3;
        BigDecimal bigDecimal4;
        Preconditions.checkNotNull(zzcVar);
        if (zzcVar.zzku() && zzcVar.zzkv() != com.google.android.gms.internal.measurement.zzbk.zzc.zzb.UNKNOWN_COMPARISON_TYPE) {
            if (zzcVar.zzkv() == com.google.android.gms.internal.measurement.zzbk.zzc.zzb.BETWEEN) {
                if (!zzcVar.zzla() || !zzcVar.zzlc()) {
                    return null;
                }
            } else if (!zzcVar.zzky()) {
                return null;
            }
            com.google.android.gms.internal.measurement.zzbk.zzc.zzb zzbVarZzkv = zzcVar.zzkv();
            if (zzcVar.zzkv() == com.google.android.gms.internal.measurement.zzbk.zzc.zzb.BETWEEN) {
                if (zzjo.zzbj(zzcVar.zzlb()) && zzjo.zzbj(zzcVar.zzld())) {
                    try {
                        BigDecimal bigDecimal5 = new BigDecimal(zzcVar.zzlb());
                        bigDecimal4 = new BigDecimal(zzcVar.zzld());
                        bigDecimal3 = bigDecimal5;
                        bigDecimal2 = null;
                    } catch (NumberFormatException unused) {
                    }
                }
                return null;
            }
            if (!zzjo.zzbj(zzcVar.zzkz())) {
                return null;
            }
            try {
                bigDecimal2 = new BigDecimal(zzcVar.zzkz());
                bigDecimal3 = null;
                bigDecimal4 = null;
            } catch (NumberFormatException unused2) {
            }
            if (zzbVarZzkv == com.google.android.gms.internal.measurement.zzbk.zzc.zzb.BETWEEN) {
                if (bigDecimal3 == null) {
                    return null;
                }
            } else if (bigDecimal2 != null) {
            }
            int i = zzo.zzdv[zzbVarZzkv.ordinal()];
            boolean z = false;
            if (i == 1) {
                return Boolean.valueOf(bigDecimal.compareTo(bigDecimal2) == -1);
            }
            if (i == 2) {
                return Boolean.valueOf(bigDecimal.compareTo(bigDecimal2) == 1);
            }
            if (i == 3) {
                if (d == 0.0d) {
                    return Boolean.valueOf(bigDecimal.compareTo(bigDecimal2) == 0);
                }
                if (bigDecimal.compareTo(bigDecimal2.subtract(new BigDecimal(d).multiply(new BigDecimal(2)))) == 1 && bigDecimal.compareTo(bigDecimal2.add(new BigDecimal(d).multiply(new BigDecimal(2)))) == -1) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
            if (i == 4) {
                if (bigDecimal.compareTo(bigDecimal3) != -1 && bigDecimal.compareTo(bigDecimal4) != 1) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }
        return null;
    }

    private static List<com.google.android.gms.internal.measurement.zzbs.zzb> zza(Map<Integer, Long> map) {
        if (map == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(map.size());
        Iterator<Integer> it = map.keySet().iterator();
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            arrayList.add((com.google.android.gms.internal.measurement.zzbs.zzb) ((com.google.android.gms.internal.measurement.zzey) com.google.android.gms.internal.measurement.zzbs.zzb.zzmh().zzk(iIntValue).zzae(map.get(Integer.valueOf(iIntValue)).longValue()).zzug()));
        }
        return arrayList;
    }

    private static void zza(Map<Integer, Long> map, int i, long j) {
        Long l = map.get(Integer.valueOf(i));
        long j2 = j / 1000;
        if (l == null || j2 > l.longValue()) {
            map.put(Integer.valueOf(i), Long.valueOf(j2));
        }
    }

    private static void zzb(Map<Integer, List<Long>> map, int i, long j) {
        List<Long> arrayList = map.get(Integer.valueOf(i));
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            map.put(Integer.valueOf(i), arrayList);
        }
        arrayList.add(Long.valueOf(j / 1000));
    }

    /* JADX WARN: Code duplicated, block: B:125:0x03b1  */
    /* JADX WARN: Code duplicated, block: B:128:0x03d6  */
    /* JADX WARN: Code duplicated, block: B:134:0x03ed  */
    /* JADX WARN: Code duplicated, block: B:136:0x03f7  */
    /* JADX WARN: Code duplicated, block: B:142:0x041b  */
    /* JADX WARN: Code duplicated, block: B:158:0x045c  */
    /* JADX WARN: Code duplicated, block: B:162:0x0487  */
    /* JADX WARN: Code duplicated, block: B:167:0x04a4  */
    /* JADX WARN: Code duplicated, block: B:170:0x04ae A[LOOP:8: B:168:0x04a8->B:170:0x04ae, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:172:0x04bf  */
    /* JADX WARN: Code duplicated, block: B:178:0x04f7  */
    /* JADX WARN: Code duplicated, block: B:181:0x0507  */
    /* JADX WARN: Code duplicated, block: B:184:0x0512  */
    /* JADX WARN: Code duplicated, block: B:185:0x0521  */
    /* JADX WARN: Code duplicated, block: B:187:0x0535  */
    /* JADX WARN: Code duplicated, block: B:191:0x054b  */
    /* JADX WARN: Code duplicated, block: B:193:0x0566  */
    /* JADX WARN: Code duplicated, block: B:194:0x059a  */
    /* JADX WARN: Code duplicated, block: B:196:0x05ce  */
    /* JADX WARN: Code duplicated, block: B:198:0x05e3  */
    /* JADX WARN: Code duplicated, block: B:199:0x0617  */
    /* JADX WARN: Code duplicated, block: B:203:0x0658  */
    /* JADX WARN: Code duplicated, block: B:205:0x0662  */
    /* JADX WARN: Code duplicated, block: B:210:0x0679  */
    /* JADX WARN: Code duplicated, block: B:215:0x06bb  */
    /* JADX WARN: Code duplicated, block: B:216:0x06d8  */
    /* JADX WARN: Code duplicated, block: B:219:0x06f1  */
    /* JADX WARN: Code duplicated, block: B:221:0x0725  */
    /* JADX WARN: Code duplicated, block: B:222:0x074b  */
    /* JADX WARN: Code duplicated, block: B:223:0x0750  */
    /* JADX WARN: Code duplicated, block: B:228:0x076c  */
    /* JADX WARN: Code duplicated, block: B:230:0x0774 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:234:0x0783  */
    /* JADX WARN: Code duplicated, block: B:237:0x0792  */
    /* JADX WARN: Code duplicated, block: B:239:0x07a4  */
    /* JADX WARN: Code duplicated, block: B:240:0x07b5  */
    /* JADX WARN: Code duplicated, block: B:242:0x07e3  */
    /* JADX WARN: Code duplicated, block: B:245:0x07f1  */
    /* JADX WARN: Code duplicated, block: B:305:0x0987  */
    /* JADX WARN: Code duplicated, block: B:306:0x0990  */
    /* JADX WARN: Code duplicated, block: B:463:0x0e84  */
    /* JADX WARN: Code duplicated, block: B:545:0x04f0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:546:0x03c3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:547:0x04d6 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:553:0x049a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:555:0x0481 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:559:0x06a1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:560:0x068f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:567:0x095b A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Instruction removed from duplicated block: B:178:0x04f7, please report this as an issue */
    /* JADX WARN: Multi-variable type inference failed */
    @WorkerThread
    final List<com.google.android.gms.internal.measurement.zzbs.zza> zza(String str, List<com.google.android.gms.internal.measurement.zzbs.zzc> list, List<com.google.android.gms.internal.measurement.zzbs.zzk> list2) {
        Long lValueOf;
        ArrayMap arrayMap;
        Iterator it;
        ArrayMap arrayMap2;
        ArrayMap arrayMap3;
        List listEmptyList;
        List list3;
        Map arrayMap4;
        Map arrayMap5;
        String str2;
        String str3;
        long jLongValue;
        boolean z;
        com.google.android.gms.internal.measurement.zzbs.zzc zzcVar;
        Long l;
        String str4;
        List<com.google.android.gms.internal.measurement.zzbs.zze> list4;
        zzae zzaeVarZzc;
        com.google.android.gms.internal.measurement.zzbs.zzc zzcVar2;
        zzae zzaeVar;
        zzae zzaeVar2;
        long j;
        ArrayMap arrayMap6;
        Map<Integer, List<com.google.android.gms.internal.measurement.zzbk.zza>> mapZzh;
        Map<Integer, List<com.google.android.gms.internal.measurement.zzbk.zza>> map;
        Iterator<Integer> it2;
        int iIntValue;
        ArrayMap arrayMap7;
        BitSet bitSet;
        ArrayMap arrayMap8;
        BitSet bitSet2;
        ArrayMap arrayMap9;
        Map map2;
        Map map3;
        ArrayMap arrayMap10;
        ArrayMap arrayMap11;
        BitSet bitSet3;
        Map map4;
        long j2;
        String str5;
        String str6;
        zzae zzaeVar3;
        ArrayMap arrayMap12;
        ArrayMap arrayMap13;
        Map map5;
        BitSet bitSet4;
        ArrayMap arrayMap14;
        String str7;
        ArrayMap arrayMap15;
        ArrayMap arrayMap16;
        Map<Integer, List<com.google.android.gms.internal.measurement.zzbk.zza>> map6;
        String str8;
        Integer numValueOf;
        Integer numValueOf2;
        BitSet bitSet5;
        zzae zzaeVar4;
        Pair<com.google.android.gms.internal.measurement.zzbs.zzc, Long> pairZza;
        long jLongValue2;
        Long l2;
        ArrayList arrayList;
        Iterator<com.google.android.gms.internal.measurement.zzbs.zze> it3;
        zzx zzxVarZzgy;
        Map<Integer, com.google.android.gms.internal.measurement.zzbs.zzi> arrayMap17;
        Iterator it4;
        Map<Integer, com.google.android.gms.internal.measurement.zzbs.zzi> map7;
        ArrayMap arrayMap18;
        ArrayMap arrayMap19;
        ArrayMap arrayMap20;
        ArrayMap arrayMap21;
        boolean z2;
        Map<Integer, List<Integer>> map8;
        Iterator<Integer> it5;
        String str9 = str;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(list);
        Preconditions.checkNotNull(list2);
        HashSet hashSet = new HashSet();
        ArrayMap arrayMap22 = new ArrayMap();
        ArrayMap arrayMap23 = new ArrayMap();
        ArrayMap arrayMap24 = new ArrayMap();
        ArrayMap arrayMap25 = new ArrayMap();
        ArrayMap arrayMap26 = new ArrayMap();
        boolean zZzq = zzad().zzq(str9);
        boolean zZzd = zzad().zzd(str9, zzak.zziq);
        boolean zZzd2 = zzad().zzd(str9, zzak.zziy);
        boolean zZzd3 = zzad().zzd(str9, zzak.zziz);
        if (!zZzd2 && !zZzd3) {
            lValueOf = null;
            break;
        }
        Iterator<com.google.android.gms.internal.measurement.zzbs.zzc> it6 = list.iterator();
        while (true) {
            if (!it6.hasNext()) {
                lValueOf = null;
                break;
            }
            com.google.android.gms.internal.measurement.zzbs.zzc next = it6.next();
            if ("_s".equals(next.getName())) {
                lValueOf = Long.valueOf(next.getTimestampMillis());
                break;
            }
        }
        if (lValueOf != null && zZzd3) {
            zzx zzxVarZzgy2 = zzgy();
            zzxVarZzgy2.zzbi();
            zzxVarZzgy2.zzo();
            Preconditions.checkNotEmpty(str);
            ContentValues contentValues = new ContentValues();
            contentValues.put("current_session_count", (Integer) 0);
            try {
                zzxVarZzgy2.getWritableDatabase().update("events", contentValues, "app_id = ?", new String[]{str9});
            } catch (SQLiteException e) {
                zzxVarZzgy2.zzab().zzgk().zza("Error resetting session-scoped event counts. appId", zzef.zzam(str), e);
            }
        }
        Map<Integer, com.google.android.gms.internal.measurement.zzbs.zzi> mapZzaf = zzgy().zzaf(str9);
        if (mapZzaf != null && !mapZzaf.isEmpty()) {
            HashSet hashSet2 = new HashSet(mapZzaf.keySet());
            if (!zZzd2 || lValueOf == null) {
                arrayMap17 = mapZzaf;
            } else {
                zzp zzpVarZzgx = zzgx();
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotNull(mapZzaf);
                arrayMap17 = new ArrayMap<>();
                if (!mapZzaf.isEmpty()) {
                    Map<Integer, List<Integer>> mapZzae = zzpVarZzgx.zzgy().zzae(str9);
                    Iterator<Integer> it7 = mapZzaf.keySet().iterator();
                    while (it7.hasNext()) {
                        int iIntValue2 = it7.next().intValue();
                        com.google.android.gms.internal.measurement.zzbs.zzi zziVar = mapZzaf.get(Integer.valueOf(iIntValue2));
                        List<Integer> list5 = mapZzae.get(Integer.valueOf(iIntValue2));
                        if (list5 == null || list5.isEmpty()) {
                            map8 = mapZzae;
                            it5 = it7;
                            arrayMap17.put(Integer.valueOf(iIntValue2), zziVar);
                        } else {
                            map8 = mapZzae;
                            it5 = it7;
                            List<Long> listZza = zzpVarZzgx.zzgw().zza(zziVar.zzpy(), list5);
                            if (listZza.isEmpty()) {
                                mapZzae = map8;
                                it7 = it5;
                            } else {
                                com.google.android.gms.internal.measurement.zzbs.zzi.zza zzaVarZzo = zziVar.zzuj().zzqr().zzo(listZza);
                                zzaVarZzo.zzqq().zzn(zzpVarZzgx.zzgw().zza(zziVar.zzpv(), list5));
                                for (int i = 0; i < zziVar.zzqc(); i++) {
                                    if (list5.contains(Integer.valueOf(zziVar.zzae(i).getIndex()))) {
                                        zzaVarZzo.zzaj(i);
                                    }
                                }
                                for (int i2 = 0; i2 < zziVar.zzqf(); i2++) {
                                    if (list5.contains(Integer.valueOf(zziVar.zzag(i2).getIndex()))) {
                                        zzaVarZzo.zzak(i2);
                                    }
                                }
                                arrayMap17.put(Integer.valueOf(iIntValue2), (com.google.android.gms.internal.measurement.zzbs.zzi) ((com.google.android.gms.internal.measurement.zzey) zzaVarZzo.zzug()));
                            }
                        }
                        mapZzae = map8;
                        it7 = it5;
                        zzpVarZzgx = zzpVarZzgx;
                    }
                }
            }
            Iterator it8 = hashSet2.iterator();
            while (it8.hasNext()) {
                int iIntValue3 = ((Integer) it8.next()).intValue();
                com.google.android.gms.internal.measurement.zzbs.zzi zziVar2 = arrayMap17.get(Integer.valueOf(iIntValue3));
                BitSet bitSet6 = (BitSet) arrayMap23.get(Integer.valueOf(iIntValue3));
                BitSet bitSet7 = (BitSet) arrayMap24.get(Integer.valueOf(iIntValue3));
                if (zZzq) {
                    arrayMap18 = new ArrayMap();
                    if (zziVar2 != null && zziVar2.zzqc() != 0) {
                        for (com.google.android.gms.internal.measurement.zzbs.zzb zzbVar : zziVar2.zzqb()) {
                            if (zzbVar.zzme()) {
                                arrayMap18.put(Integer.valueOf(zzbVar.getIndex()), zzbVar.zzmf() ? Long.valueOf(zzbVar.zzmg()) : null);
                            } else {
                                arrayMap17 = arrayMap17;
                            }
                            arrayMap17 = arrayMap17;
                            it8 = it8;
                        }
                    }
                    it4 = it8;
                    map7 = arrayMap17;
                    arrayMap25.put(Integer.valueOf(iIntValue3), arrayMap18);
                } else {
                    it4 = it8;
                    map7 = arrayMap17;
                    arrayMap18 = null;
                }
                if (bitSet6 == null) {
                    bitSet6 = new BitSet();
                    arrayMap23.put(Integer.valueOf(iIntValue3), bitSet6);
                    bitSet7 = new BitSet();
                    arrayMap24.put(Integer.valueOf(iIntValue3), bitSet7);
                }
                if (zziVar2 != null) {
                    int i3 = 0;
                    while (i3 < (zziVar2.zzpw() << 6)) {
                        if (zzjo.zza(zziVar2.zzpv(), i3)) {
                            arrayMap19 = arrayMap24;
                            arrayMap20 = arrayMap25;
                            arrayMap21 = arrayMap23;
                            zzab().zzgs().zza("Filter already evaluated. audience ID, filter ID", Integer.valueOf(iIntValue3), Integer.valueOf(i3));
                            bitSet7.set(i3);
                            if (zzjo.zza(zziVar2.zzpy(), i3)) {
                                bitSet6.set(i3);
                                z2 = true;
                            }
                            if (arrayMap18 == null && !z2) {
                                arrayMap18.remove(Integer.valueOf(i3));
                            }
                            i3++;
                            arrayMap24 = arrayMap19;
                            arrayMap25 = arrayMap20;
                            arrayMap23 = arrayMap21;
                        } else {
                            arrayMap19 = arrayMap24;
                            arrayMap20 = arrayMap25;
                            arrayMap21 = arrayMap23;
                        }
                        z2 = false;
                        if (arrayMap18 == null) {
                        }
                        i3++;
                        arrayMap24 = arrayMap19;
                        arrayMap25 = arrayMap20;
                        arrayMap23 = arrayMap21;
                    }
                }
                ArrayMap arrayMap27 = arrayMap24;
                ArrayMap arrayMap28 = arrayMap25;
                ArrayMap arrayMap29 = arrayMap23;
                com.google.android.gms.internal.measurement.zzbs.zza.C1276zza c1276zzaZzk = com.google.android.gms.internal.measurement.zzbs.zza.zzmc().zzk(false);
                if (zZzd2) {
                    c1276zzaZzk.zza(mapZzaf.get(Integer.valueOf(iIntValue3)));
                } else {
                    c1276zzaZzk.zza(zziVar2);
                }
                com.google.android.gms.internal.measurement.zzbs.zzi.zza zzaVarZzn = com.google.android.gms.internal.measurement.zzbs.zzi.zzqh().zzo(zzjo.zza(bitSet6)).zzn(zzjo.zza(bitSet7));
                if (zZzq) {
                    zzaVarZzn.zzp(zza(arrayMap18));
                    arrayMap26.put(Integer.valueOf(iIntValue3), new ArrayMap());
                }
                c1276zzaZzk.zza(zzaVarZzn);
                arrayMap22.put(Integer.valueOf(iIntValue3), (com.google.android.gms.internal.measurement.zzbs.zza) ((com.google.android.gms.internal.measurement.zzey) c1276zzaZzk.zzug()));
                arrayMap24 = arrayMap27;
                arrayMap17 = map7;
                arrayMap25 = arrayMap28;
                it8 = it4;
                arrayMap23 = arrayMap29;
            }
        }
        ArrayMap arrayMap30 = arrayMap24;
        ArrayMap arrayMap31 = arrayMap25;
        ArrayMap arrayMap32 = arrayMap23;
        String str10 = "Filter definition";
        String str11 = "Skipping failed audience ID";
        if (!list.isEmpty()) {
            ArrayMap arrayMap33 = new ArrayMap();
            long j3 = 0;
            com.google.android.gms.internal.measurement.zzbs.zzc zzcVar3 = null;
            Long l3 = null;
            for (com.google.android.gms.internal.measurement.zzbs.zzc zzcVar4 : list) {
                String name = zzcVar4.getName();
                List<com.google.android.gms.internal.measurement.zzbs.zze> listZzmj = zzcVar4.zzmj();
                zzgw();
                Long l4 = (Long) zzjo.zzb(zzcVar4, "_eid");
                boolean z3 = l4 != null;
                if (z3) {
                    jLongValue = j3;
                    z = name.equals("_ep");
                    if (z) {
                        zzgw();
                        str4 = (String) zzjo.zzb(zzcVar4, "_en");
                        if (TextUtils.isEmpty(str4)) {
                            zzab().zzgk().zza("Extra parameter without an event name. eventId", l4);
                        } else {
                            if (zzcVar3 == null && l3 != null && l4.longValue() == l3.longValue()) {
                                zzcVar = zzcVar3;
                                l2 = l3;
                                jLongValue2 = jLongValue;
                            } else {
                                pairZza = zzgy().zza(str9, l4);
                                if (pairZza != null || pairZza.first == null) {
                                    zzab().zzgk().zza("Extra parameter without existing main event. eventName, eventId", str4, l4);
                                } else {
                                    com.google.android.gms.internal.measurement.zzbs.zzc zzcVar5 = (com.google.android.gms.internal.measurement.zzbs.zzc) pairZza.first;
                                    jLongValue2 = ((Long) pairZza.second).longValue();
                                    zzgw();
                                    zzcVar = zzcVar5;
                                    l2 = (Long) zzjo.zzb(zzcVar5, "_eid");
                                }
                            }
                            jLongValue = jLongValue2 - 1;
                            if (jLongValue <= 0) {
                                zzxVarZzgy = zzgy();
                                zzxVarZzgy.zzo();
                                zzxVarZzgy.zzab().zzgs().zza("Clearing complex main event info. appId", str9);
                                try {
                                    SQLiteDatabase writableDatabase = zzxVarZzgy.getWritableDatabase();
                                    try {
                                        String[] strArr = new String[1];
                                        try {
                                            strArr[0] = str9;
                                            writableDatabase.execSQL("delete from main_event_params where app_id=?", strArr);
                                        } catch (SQLiteException e2) {
                                            e = e2;
                                            zzxVarZzgy.zzab().zzgk().zza("Error clearing complex main event", e);
                                        }
                                    } catch (SQLiteException e3) {
                                        e = e3;
                                        zzxVarZzgy.zzab().zzgk().zza("Error clearing complex main event", e);
                                        arrayList = new ArrayList();
                                        for (com.google.android.gms.internal.measurement.zzbs.zze zzeVar : zzcVar.zzmj()) {
                                            zzgw();
                                            if (zzjo.zza(zzcVar4, zzeVar.getName()) == null) {
                                                arrayList.add(zzeVar);
                                            }
                                        }
                                        if (arrayList.isEmpty()) {
                                            zzab().zzgn().zza("No unique parameters in main event. eventName", str4);
                                            list4 = listZzmj;
                                        } else {
                                            it3 = listZzmj.iterator();
                                            while (it3.hasNext()) {
                                                arrayList.add(it3.next());
                                            }
                                            list4 = arrayList;
                                        }
                                        l = l2;
                                        zzaeVarZzc = zzgy().zzc(str9, zzcVar4.getName());
                                        if (zzaeVarZzc == null) {
                                            zzab().zzgn().zza("Event aggregate wasn't created during raw event logging. appId, event", zzef.zzam(str), zzy().zzaj(str4));
                                            if (zZzd3) {
                                                zzcVar2 = zzcVar4;
                                                zzaeVar4 = new zzae(str, zzcVar4.getName(), 1L, 1L, 1L, zzcVar2.getTimestampMillis(), 0L, null, null, null, null);
                                            } else {
                                                zzcVar2 = zzcVar4;
                                                zzaeVar4 = new zzae(str, zzcVar2.getName(), 1L, 1L, zzcVar2.getTimestampMillis(), 0L, null, null, null, null);
                                            }
                                            zzaeVar2 = zzaeVar4;
                                        } else {
                                            str9 = str9;
                                            arrayMap26 = arrayMap26;
                                            zzcVar2 = zzcVar4;
                                            arrayMap22 = arrayMap22;
                                            str10 = str10;
                                            hashSet = hashSet;
                                            arrayMap30 = arrayMap30;
                                            str11 = str11;
                                            arrayMap31 = arrayMap31;
                                            arrayMap32 = arrayMap32;
                                            if (zZzd3) {
                                                zzaeVar = new zzae(zzaeVarZzc.zzce, zzaeVarZzc.name, zzaeVarZzc.zzfg + 1, zzaeVarZzc.zzfh + 1, zzaeVarZzc.zzfi + 1, zzaeVarZzc.zzfj, zzaeVarZzc.zzfk, zzaeVarZzc.zzfl, zzaeVarZzc.zzfm, zzaeVarZzc.zzfn, zzaeVarZzc.zzfo);
                                            } else {
                                                zzaeVar = new zzae(zzaeVarZzc.zzce, zzaeVarZzc.name, zzaeVarZzc.zzfg + 1, zzaeVarZzc.zzfh + 1, zzaeVarZzc.zzfi, zzaeVarZzc.zzfj, zzaeVarZzc.zzfk, zzaeVarZzc.zzfl, zzaeVarZzc.zzfm, zzaeVarZzc.zzfn, zzaeVarZzc.zzfo);
                                            }
                                            zzaeVar2 = zzaeVar;
                                        }
                                        zzgy().zza(zzaeVar2);
                                        j = zzaeVar2.zzfg;
                                        arrayMap6 = arrayMap33;
                                        mapZzh = (Map) arrayMap6.get(str4);
                                        if (mapZzh == null) {
                                            mapZzh = zzgy().zzh(str9, str4);
                                            if (mapZzh == null) {
                                                mapZzh = new ArrayMap<>();
                                            }
                                            arrayMap6.put(str4, mapZzh);
                                        }
                                        map = mapZzh;
                                        it2 = map.keySet().iterator();
                                        while (it2.hasNext()) {
                                            iIntValue = it2.next().intValue();
                                            hashSet = hashSet;
                                            if (hashSet.contains(Integer.valueOf(iIntValue))) {
                                                zzab().zzgs().zza(str11, Integer.valueOf(iIntValue));
                                            } else {
                                                arrayMap7 = arrayMap32;
                                                bitSet = (BitSet) arrayMap7.get(Integer.valueOf(iIntValue));
                                                arrayMap8 = arrayMap30;
                                                bitSet2 = (BitSet) arrayMap8.get(Integer.valueOf(iIntValue));
                                                if (zZzq) {
                                                    arrayMap9 = arrayMap31;
                                                    Map map9 = (Map) arrayMap9.get(Integer.valueOf(iIntValue));
                                                    map2 = (Map) arrayMap26.get(Integer.valueOf(iIntValue));
                                                    map3 = map9;
                                                } else {
                                                    arrayMap9 = arrayMap31;
                                                    map2 = null;
                                                    map3 = null;
                                                }
                                                Map map10 = map2;
                                                arrayMap10 = arrayMap22;
                                                if (((com.google.android.gms.internal.measurement.zzbs.zza) arrayMap10.get(Integer.valueOf(iIntValue))) == null) {
                                                    arrayMap11 = arrayMap6;
                                                    arrayMap10.put(Integer.valueOf(iIntValue), (com.google.android.gms.internal.measurement.zzbs.zza) ((com.google.android.gms.internal.measurement.zzey) com.google.android.gms.internal.measurement.zzbs.zza.zzmc().zzk(true).zzug()));
                                                    bitSet5 = new BitSet();
                                                    arrayMap7.put(Integer.valueOf(iIntValue), bitSet5);
                                                    bitSet2 = new BitSet();
                                                    arrayMap8.put(Integer.valueOf(iIntValue), bitSet2);
                                                    if (zZzq) {
                                                        ArrayMap arrayMap34 = new ArrayMap();
                                                        arrayMap9.put(Integer.valueOf(iIntValue), arrayMap34);
                                                        ArrayMap arrayMap35 = new ArrayMap();
                                                        arrayMap26.put(Integer.valueOf(iIntValue), arrayMap35);
                                                        map3 = arrayMap34;
                                                        bitSet3 = bitSet5;
                                                        bitSet2 = bitSet2;
                                                        map4 = arrayMap35;
                                                    } else {
                                                        bitSet = bitSet5;
                                                    }
                                                    while (r20.hasNext()) {
                                                        if (!zZzd3) {
                                                            j2 = j;
                                                        } else {
                                                            j2 = j;
                                                        }
                                                        if (zzab().isLoggable(2)) {
                                                            zzeh zzehVarZzgs = zzab().zzgs();
                                                            Integer numValueOf3 = Integer.valueOf(iIntValue);
                                                            if (zzaVar.zzkb()) {
                                                                numValueOf2 = Integer.valueOf(zzaVar.getId());
                                                            } else {
                                                                numValueOf2 = null;
                                                            }
                                                            zzehVarZzgs.zza("Evaluating filter. audience, filter, event", numValueOf3, numValueOf2, zzy().zzaj(zzaVar.zzjz()));
                                                            str5 = str10;
                                                            zzab().zzgs().zza(str5, zzgw().zza(zzaVar));
                                                        } else {
                                                            arrayMap26 = arrayMap26;
                                                            str11 = str11;
                                                            str5 = str10;
                                                        }
                                                        if (zzaVar.zzkb()) {
                                                        }
                                                        str6 = str5;
                                                        zzaeVar3 = zzaeVar2;
                                                        arrayMap12 = arrayMap10;
                                                        arrayMap13 = arrayMap7;
                                                        map5 = map3;
                                                        bitSet4 = bitSet2;
                                                        arrayMap14 = arrayMap26;
                                                        str7 = str11;
                                                        arrayMap15 = arrayMap8;
                                                        arrayMap16 = arrayMap9;
                                                        map6 = map;
                                                        str8 = str;
                                                        zzeh zzehVarZzgn = zzab().zzgn();
                                                        Object objZzam = zzef.zzam(str);
                                                        if (zzaVar.zzkb()) {
                                                            numValueOf = Integer.valueOf(zzaVar.getId());
                                                        } else {
                                                            numValueOf = null;
                                                        }
                                                        zzehVarZzgn.zza("Invalid event filter ID. appId, id", objZzam, String.valueOf(numValueOf));
                                                        bitSet2 = bitSet4;
                                                        map3 = map5;
                                                        str9 = str8;
                                                        str11 = str7;
                                                        map = map6;
                                                        arrayMap9 = arrayMap16;
                                                        zzaeVar2 = zzaeVar3;
                                                        arrayMap10 = arrayMap12;
                                                        arrayMap7 = arrayMap13;
                                                        arrayMap26 = arrayMap14;
                                                        arrayMap8 = arrayMap15;
                                                        str10 = str6;
                                                    }
                                                    arrayMap30 = arrayMap8;
                                                    arrayMap31 = arrayMap9;
                                                    arrayMap22 = arrayMap10;
                                                    arrayMap32 = arrayMap7;
                                                    it2 = it2;
                                                    j = j;
                                                    arrayMap6 = arrayMap11;
                                                } else {
                                                    arrayMap11 = arrayMap6;
                                                }
                                                bitSet3 = bitSet;
                                                map4 = map10;
                                                while (r20.hasNext()) {
                                                    if (!zZzd3) {
                                                        j2 = j;
                                                    } else {
                                                        j2 = j;
                                                    }
                                                    if (zzab().isLoggable(2)) {
                                                        zzeh zzehVarZzgs2 = zzab().zzgs();
                                                        Integer numValueOf4 = Integer.valueOf(iIntValue);
                                                        if (zzaVar.zzkb()) {
                                                            numValueOf2 = Integer.valueOf(zzaVar.getId());
                                                        } else {
                                                            numValueOf2 = null;
                                                        }
                                                        zzehVarZzgs2.zza("Evaluating filter. audience, filter, event", numValueOf4, numValueOf2, zzy().zzaj(zzaVar.zzjz()));
                                                        str5 = str10;
                                                        zzab().zzgs().zza(str5, zzgw().zza(zzaVar));
                                                    } else {
                                                        arrayMap26 = arrayMap26;
                                                        str11 = str11;
                                                        str5 = str10;
                                                    }
                                                    if (zzaVar.zzkb()) {
                                                    }
                                                    str6 = str5;
                                                    zzaeVar3 = zzaeVar2;
                                                    arrayMap12 = arrayMap10;
                                                    arrayMap13 = arrayMap7;
                                                    map5 = map3;
                                                    bitSet4 = bitSet2;
                                                    arrayMap14 = arrayMap26;
                                                    str7 = str11;
                                                    arrayMap15 = arrayMap8;
                                                    arrayMap16 = arrayMap9;
                                                    map6 = map;
                                                    str8 = str;
                                                    zzeh zzehVarZzgn2 = zzab().zzgn();
                                                    Object objZzam2 = zzef.zzam(str);
                                                    if (zzaVar.zzkb()) {
                                                        numValueOf = Integer.valueOf(zzaVar.getId());
                                                    } else {
                                                        numValueOf = null;
                                                    }
                                                    zzehVarZzgn2.zza("Invalid event filter ID. appId, id", objZzam2, String.valueOf(numValueOf));
                                                    bitSet2 = bitSet4;
                                                    map3 = map5;
                                                    str9 = str8;
                                                    str11 = str7;
                                                    map = map6;
                                                    arrayMap9 = arrayMap16;
                                                    zzaeVar2 = zzaeVar3;
                                                    arrayMap10 = arrayMap12;
                                                    arrayMap7 = arrayMap13;
                                                    arrayMap26 = arrayMap14;
                                                    arrayMap8 = arrayMap15;
                                                    str10 = str6;
                                                }
                                                arrayMap30 = arrayMap8;
                                                arrayMap31 = arrayMap9;
                                                arrayMap22 = arrayMap10;
                                                arrayMap32 = arrayMap7;
                                                it2 = it2;
                                                j = j;
                                                arrayMap6 = arrayMap11;
                                            }
                                        }
                                        str9 = str9;
                                        str11 = str11;
                                        arrayMap33 = arrayMap6;
                                        j3 = jLongValue;
                                        zzcVar3 = zzcVar;
                                        l3 = l;
                                        arrayMap30 = arrayMap30;
                                        arrayMap31 = arrayMap31;
                                        arrayMap22 = arrayMap22;
                                        arrayMap32 = arrayMap32;
                                        str10 = str10;
                                        hashSet = hashSet;
                                        arrayMap26 = arrayMap26;
                                    }
                                } catch (SQLiteException e4) {
                                    e = e4;
                                }
                            } else {
                                zzgy().zza(str, l4, jLongValue, zzcVar);
                            }
                            arrayList = new ArrayList();
                            while (r1.hasNext()) {
                                zzgw();
                                if (zzjo.zza(zzcVar4, zzeVar.getName()) == null) {
                                    arrayList.add(zzeVar);
                                }
                            }
                            if (arrayList.isEmpty()) {
                                it3 = listZzmj.iterator();
                                while (it3.hasNext()) {
                                    arrayList.add(it3.next());
                                }
                                list4 = arrayList;
                            } else {
                                zzab().zzgn().zza("No unique parameters in main event. eventName", str4);
                                list4 = listZzmj;
                            }
                            l = l2;
                        }
                        str11 = str11;
                        j3 = jLongValue;
                        arrayMap33 = arrayMap33;
                    } else {
                        zzcVar4 = zzcVar4;
                        arrayMap33 = arrayMap33;
                        str11 = str11;
                        if (z3) {
                            zzgw();
                            Object objZzb = zzjo.zzb(zzcVar4, "_epc");
                            jLongValue = ((Long) (objZzb != null ? objZzb : 0L)).longValue();
                            if (jLongValue <= 0) {
                                zzab().zzgn().zza("Complex event with zero extra param count. eventName", name);
                            } else {
                                zzgy().zza(str, l4, jLongValue, zzcVar4);
                            }
                            str4 = name;
                            zzcVar = zzcVar4;
                            l = l4;
                        } else {
                            zzcVar = zzcVar3;
                            l = l3;
                            str4 = name;
                        }
                        list4 = listZzmj;
                    }
                    zzaeVarZzc = zzgy().zzc(str9, zzcVar4.getName());
                    if (zzaeVarZzc == null) {
                        zzab().zzgn().zza("Event aggregate wasn't created during raw event logging. appId, event", zzef.zzam(str), zzy().zzaj(str4));
                        if (zZzd3) {
                            zzcVar2 = zzcVar4;
                            zzaeVar4 = new zzae(str, zzcVar4.getName(), 1L, 1L, 1L, zzcVar2.getTimestampMillis(), 0L, null, null, null, null);
                        } else {
                            zzcVar2 = zzcVar4;
                            zzaeVar4 = new zzae(str, zzcVar2.getName(), 1L, 1L, zzcVar2.getTimestampMillis(), 0L, null, null, null, null);
                        }
                        zzaeVar2 = zzaeVar4;
                    } else {
                        str9 = str9;
                        arrayMap26 = arrayMap26;
                        zzcVar2 = zzcVar4;
                        arrayMap22 = arrayMap22;
                        str10 = str10;
                        hashSet = hashSet;
                        arrayMap30 = arrayMap30;
                        str11 = str11;
                        arrayMap31 = arrayMap31;
                        arrayMap32 = arrayMap32;
                        if (zZzd3) {
                            zzaeVar = new zzae(zzaeVarZzc.zzce, zzaeVarZzc.name, zzaeVarZzc.zzfg + 1, zzaeVarZzc.zzfh + 1, zzaeVarZzc.zzfi + 1, zzaeVarZzc.zzfj, zzaeVarZzc.zzfk, zzaeVarZzc.zzfl, zzaeVarZzc.zzfm, zzaeVarZzc.zzfn, zzaeVarZzc.zzfo);
                        } else {
                            zzaeVar = new zzae(zzaeVarZzc.zzce, zzaeVarZzc.name, zzaeVarZzc.zzfg + 1, zzaeVarZzc.zzfh + 1, zzaeVarZzc.zzfi, zzaeVarZzc.zzfj, zzaeVarZzc.zzfk, zzaeVarZzc.zzfl, zzaeVarZzc.zzfm, zzaeVarZzc.zzfn, zzaeVarZzc.zzfo);
                        }
                        zzaeVar2 = zzaeVar;
                    }
                    zzgy().zza(zzaeVar2);
                    j = zzaeVar2.zzfg;
                    arrayMap6 = arrayMap33;
                    mapZzh = (Map) arrayMap6.get(str4);
                    if (mapZzh == null) {
                        mapZzh = zzgy().zzh(str9, str4);
                        if (mapZzh == null) {
                            mapZzh = new ArrayMap<>();
                        }
                        arrayMap6.put(str4, mapZzh);
                    }
                    map = mapZzh;
                    it2 = map.keySet().iterator();
                    while (it2.hasNext()) {
                        iIntValue = it2.next().intValue();
                        hashSet = hashSet;
                        if (hashSet.contains(Integer.valueOf(iIntValue))) {
                            zzab().zzgs().zza(str11, Integer.valueOf(iIntValue));
                        } else {
                            arrayMap7 = arrayMap32;
                            bitSet = (BitSet) arrayMap7.get(Integer.valueOf(iIntValue));
                            arrayMap8 = arrayMap30;
                            bitSet2 = (BitSet) arrayMap8.get(Integer.valueOf(iIntValue));
                            if (zZzq) {
                                arrayMap9 = arrayMap31;
                                Map map11 = (Map) arrayMap9.get(Integer.valueOf(iIntValue));
                                map2 = (Map) arrayMap26.get(Integer.valueOf(iIntValue));
                                map3 = map11;
                            } else {
                                arrayMap9 = arrayMap31;
                                map2 = null;
                                map3 = null;
                            }
                            Map map12 = map2;
                            arrayMap10 = arrayMap22;
                            if (((com.google.android.gms.internal.measurement.zzbs.zza) arrayMap10.get(Integer.valueOf(iIntValue))) == null) {
                                arrayMap11 = arrayMap6;
                                arrayMap10.put(Integer.valueOf(iIntValue), (com.google.android.gms.internal.measurement.zzbs.zza) ((com.google.android.gms.internal.measurement.zzey) com.google.android.gms.internal.measurement.zzbs.zza.zzmc().zzk(true).zzug()));
                                bitSet5 = new BitSet();
                                arrayMap7.put(Integer.valueOf(iIntValue), bitSet5);
                                bitSet2 = new BitSet();
                                arrayMap8.put(Integer.valueOf(iIntValue), bitSet2);
                                if (zZzq) {
                                    ArrayMap arrayMap36 = new ArrayMap();
                                    arrayMap9.put(Integer.valueOf(iIntValue), arrayMap36);
                                    ArrayMap arrayMap37 = new ArrayMap();
                                    arrayMap26.put(Integer.valueOf(iIntValue), arrayMap37);
                                    map3 = arrayMap36;
                                    bitSet3 = bitSet5;
                                    bitSet2 = bitSet2;
                                    map4 = arrayMap37;
                                } else {
                                    bitSet = bitSet5;
                                }
                                for (com.google.android.gms.internal.measurement.zzbk.zza zzaVar : map.get(Integer.valueOf(iIntValue))) {
                                    if (!zZzd3 && zZzd2 && zzaVar.zzki()) {
                                        j2 = zzaeVar2.zzfi;
                                    } else {
                                        j2 = j;
                                    }
                                    if (zzab().isLoggable(2)) {
                                        zzeh zzehVarZzgs3 = zzab().zzgs();
                                        Integer numValueOf5 = Integer.valueOf(iIntValue);
                                        if (zzaVar.zzkb()) {
                                            numValueOf2 = Integer.valueOf(zzaVar.getId());
                                        } else {
                                            numValueOf2 = null;
                                        }
                                        zzehVarZzgs3.zza("Evaluating filter. audience, filter, event", numValueOf5, numValueOf2, zzy().zzaj(zzaVar.zzjz()));
                                        str5 = str10;
                                        zzab().zzgs().zza(str5, zzgw().zza(zzaVar));
                                    } else {
                                        arrayMap26 = arrayMap26;
                                        str11 = str11;
                                        str5 = str10;
                                    }
                                    if (zzaVar.zzkb() || zzaVar.getId() > 256) {
                                        str6 = str5;
                                        zzaeVar3 = zzaeVar2;
                                        arrayMap12 = arrayMap10;
                                        arrayMap13 = arrayMap7;
                                        map5 = map3;
                                        bitSet4 = bitSet2;
                                        arrayMap14 = arrayMap26;
                                        str7 = str11;
                                        arrayMap15 = arrayMap8;
                                        arrayMap16 = arrayMap9;
                                        map6 = map;
                                        str8 = str;
                                        zzeh zzehVarZzgn3 = zzab().zzgn();
                                        Object objZzam3 = zzef.zzam(str);
                                        if (zzaVar.zzkb()) {
                                            numValueOf = Integer.valueOf(zzaVar.getId());
                                        } else {
                                            numValueOf = null;
                                        }
                                        zzehVarZzgn3.zza("Invalid event filter ID. appId, id", objZzam3, String.valueOf(numValueOf));
                                        bitSet2 = bitSet4;
                                        map3 = map5;
                                        str9 = str8;
                                        str11 = str7;
                                        map = map6;
                                        arrayMap9 = arrayMap16;
                                        zzaeVar2 = zzaeVar3;
                                        arrayMap10 = arrayMap12;
                                        arrayMap7 = arrayMap13;
                                        arrayMap26 = arrayMap14;
                                        arrayMap8 = arrayMap15;
                                        str10 = str6;
                                    } else {
                                        if (zZzq) {
                                            boolean zZzkf = zzaVar.zzkf();
                                            boolean zZzkg = zzaVar.zzkg();
                                            boolean z4 = zZzkf || zZzkg || (zZzd2 && zzaVar.zzki());
                                            if (!bitSet3.get(zzaVar.getId()) || z4) {
                                                zzaeVar3 = zzaeVar2;
                                                bitSet4 = bitSet2;
                                                arrayMap12 = arrayMap10;
                                                map6 = map;
                                                str8 = str;
                                                map5 = map3;
                                                arrayMap14 = arrayMap26;
                                                arrayMap16 = arrayMap9;
                                                str6 = str5;
                                                arrayMap13 = arrayMap7;
                                                str7 = str11;
                                                arrayMap15 = arrayMap8;
                                                Boolean boolZza = zza(zzaVar, str4, list4, j2);
                                                zzab().zzgs().zza("Event filter result", boolZza == null ? "null" : boolZza);
                                                if (boolZza == null) {
                                                    hashSet.add(Integer.valueOf(iIntValue));
                                                } else {
                                                    bitSet4.set(zzaVar.getId());
                                                    if (boolZza.booleanValue()) {
                                                        bitSet3.set(zzaVar.getId());
                                                        if (z4 && zzcVar2.zzml()) {
                                                            if (zZzkg) {
                                                                zzb(map4, zzaVar.getId(), zzcVar2.getTimestampMillis());
                                                            } else {
                                                                zza((Map<Integer, Long>) map5, zzaVar.getId(), zzcVar2.getTimestampMillis());
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                zzab().zzgs().zza("Event filter already evaluated true and it is not associated with an enhanced audience. audience ID, filter ID", Integer.valueOf(iIntValue), zzaVar.zzkb() ? Integer.valueOf(zzaVar.getId()) : null);
                                                str9 = str;
                                                str10 = str5;
                                                bitSet2 = bitSet2;
                                                arrayMap26 = arrayMap26;
                                                str11 = str11;
                                                arrayMap8 = arrayMap8;
                                            }
                                        } else {
                                            str6 = str5;
                                            zzaeVar3 = zzaeVar2;
                                            arrayMap12 = arrayMap10;
                                            arrayMap13 = arrayMap7;
                                            map5 = map3;
                                            bitSet4 = bitSet2;
                                            arrayMap14 = arrayMap26;
                                            str7 = str11;
                                            arrayMap15 = arrayMap8;
                                            arrayMap16 = arrayMap9;
                                            map6 = map;
                                            str8 = str;
                                            if (bitSet3.get(zzaVar.getId())) {
                                                zzab().zzgs().zza("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(iIntValue), zzaVar.zzkb() ? Integer.valueOf(zzaVar.getId()) : null);
                                            } else {
                                                Boolean boolZza2 = zza(zzaVar, str4, list4, j2);
                                                zzab().zzgs().zza("Event filter result", boolZza2 == null ? "null" : boolZza2);
                                                if (boolZza2 == null) {
                                                    hashSet.add(Integer.valueOf(iIntValue));
                                                } else {
                                                    bitSet4.set(zzaVar.getId());
                                                    if (boolZza2.booleanValue()) {
                                                        bitSet3.set(zzaVar.getId());
                                                    }
                                                }
                                            }
                                        }
                                        bitSet2 = bitSet4;
                                        map3 = map5;
                                        str9 = str8;
                                        str11 = str7;
                                        map = map6;
                                        arrayMap9 = arrayMap16;
                                        zzaeVar2 = zzaeVar3;
                                        arrayMap10 = arrayMap12;
                                        arrayMap7 = arrayMap13;
                                        arrayMap26 = arrayMap14;
                                        arrayMap8 = arrayMap15;
                                        str10 = str6;
                                    }
                                }
                                arrayMap30 = arrayMap8;
                                arrayMap31 = arrayMap9;
                                arrayMap22 = arrayMap10;
                                arrayMap32 = arrayMap7;
                                it2 = it2;
                                j = j;
                                arrayMap6 = arrayMap11;
                            } else {
                                arrayMap11 = arrayMap6;
                            }
                            bitSet3 = bitSet;
                            map4 = map12;
                            while (r20.hasNext()) {
                                if (!zZzd3) {
                                    j2 = j;
                                } else {
                                    j2 = j;
                                }
                                if (zzab().isLoggable(2)) {
                                    zzeh zzehVarZzgs4 = zzab().zzgs();
                                    Integer numValueOf6 = Integer.valueOf(iIntValue);
                                    if (zzaVar.zzkb()) {
                                        numValueOf2 = Integer.valueOf(zzaVar.getId());
                                    } else {
                                        numValueOf2 = null;
                                    }
                                    zzehVarZzgs4.zza("Evaluating filter. audience, filter, event", numValueOf6, numValueOf2, zzy().zzaj(zzaVar.zzjz()));
                                    str5 = str10;
                                    zzab().zzgs().zza(str5, zzgw().zza(zzaVar));
                                } else {
                                    arrayMap26 = arrayMap26;
                                    str11 = str11;
                                    str5 = str10;
                                }
                                if (zzaVar.zzkb()) {
                                }
                                str6 = str5;
                                zzaeVar3 = zzaeVar2;
                                arrayMap12 = arrayMap10;
                                arrayMap13 = arrayMap7;
                                map5 = map3;
                                bitSet4 = bitSet2;
                                arrayMap14 = arrayMap26;
                                str7 = str11;
                                arrayMap15 = arrayMap8;
                                arrayMap16 = arrayMap9;
                                map6 = map;
                                str8 = str;
                                zzeh zzehVarZzgn4 = zzab().zzgn();
                                Object objZzam4 = zzef.zzam(str);
                                if (zzaVar.zzkb()) {
                                    numValueOf = Integer.valueOf(zzaVar.getId());
                                } else {
                                    numValueOf = null;
                                }
                                zzehVarZzgn4.zza("Invalid event filter ID. appId, id", objZzam4, String.valueOf(numValueOf));
                                bitSet2 = bitSet4;
                                map3 = map5;
                                str9 = str8;
                                str11 = str7;
                                map = map6;
                                arrayMap9 = arrayMap16;
                                zzaeVar2 = zzaeVar3;
                                arrayMap10 = arrayMap12;
                                arrayMap7 = arrayMap13;
                                arrayMap26 = arrayMap14;
                                arrayMap8 = arrayMap15;
                                str10 = str6;
                            }
                            arrayMap30 = arrayMap8;
                            arrayMap31 = arrayMap9;
                            arrayMap22 = arrayMap10;
                            arrayMap32 = arrayMap7;
                            it2 = it2;
                            j = j;
                            arrayMap6 = arrayMap11;
                        }
                    }
                    str9 = str9;
                    str11 = str11;
                    arrayMap33 = arrayMap6;
                    j3 = jLongValue;
                    zzcVar3 = zzcVar;
                    l3 = l;
                    arrayMap30 = arrayMap30;
                    arrayMap31 = arrayMap31;
                    arrayMap22 = arrayMap22;
                    arrayMap32 = arrayMap32;
                    str10 = str10;
                    hashSet = hashSet;
                    arrayMap26 = arrayMap26;
                } else {
                    jLongValue = j3;
                }
                if (z) {
                    zzgw();
                    str4 = (String) zzjo.zzb(zzcVar4, "_en");
                    if (TextUtils.isEmpty(str4)) {
                        zzab().zzgk().zza("Extra parameter without an event name. eventId", l4);
                    } else {
                        if (zzcVar3 == null) {
                            pairZza = zzgy().zza(str9, l4);
                            if (pairZza != null) {
                            }
                            zzab().zzgk().zza("Extra parameter without existing main event. eventName, eventId", str4, l4);
                        } else {
                            pairZza = zzgy().zza(str9, l4);
                            if (pairZza != null) {
                            }
                            zzab().zzgk().zza("Extra parameter without existing main event. eventName, eventId", str4, l4);
                        }
                        jLongValue = jLongValue2 - 1;
                        if (jLongValue <= 0) {
                            zzxVarZzgy = zzgy();
                            zzxVarZzgy.zzo();
                            zzxVarZzgy.zzab().zzgs().zza("Clearing complex main event info. appId", str9);
                            SQLiteDatabase writableDatabase2 = zzxVarZzgy.getWritableDatabase();
                            String[] strArr2 = new String[1];
                            strArr2[0] = str9;
                            writableDatabase2.execSQL("delete from main_event_params where app_id=?", strArr2);
                        } else {
                            zzgy().zza(str, l4, jLongValue, zzcVar);
                        }
                        arrayList = new ArrayList();
                        while (r1.hasNext()) {
                            zzgw();
                            if (zzjo.zza(zzcVar4, zzeVar.getName()) == null) {
                                arrayList.add(zzeVar);
                            }
                        }
                        if (arrayList.isEmpty()) {
                            it3 = listZzmj.iterator();
                            while (it3.hasNext()) {
                                arrayList.add(it3.next());
                            }
                            list4 = arrayList;
                        } else {
                            zzab().zzgn().zza("No unique parameters in main event. eventName", str4);
                            list4 = listZzmj;
                        }
                        l = l2;
                    }
                    str11 = str11;
                    j3 = jLongValue;
                    arrayMap33 = arrayMap33;
                } else {
                    zzcVar4 = zzcVar4;
                    arrayMap33 = arrayMap33;
                    str11 = str11;
                    if (z3) {
                        zzgw();
                        Object objZzb2 = zzjo.zzb(zzcVar4, "_epc");
                        jLongValue = ((Long) (objZzb2 != null ? objZzb2 : 0L)).longValue();
                        if (jLongValue <= 0) {
                            zzab().zzgn().zza("Complex event with zero extra param count. eventName", name);
                        } else {
                            zzgy().zza(str, l4, jLongValue, zzcVar4);
                        }
                        str4 = name;
                        zzcVar = zzcVar4;
                        l = l4;
                    } else {
                        zzcVar = zzcVar3;
                        l = l3;
                        str4 = name;
                    }
                    list4 = listZzmj;
                }
                zzaeVarZzc = zzgy().zzc(str9, zzcVar4.getName());
                if (zzaeVarZzc == null) {
                    zzab().zzgn().zza("Event aggregate wasn't created during raw event logging. appId, event", zzef.zzam(str), zzy().zzaj(str4));
                    if (zZzd3) {
                        zzcVar2 = zzcVar4;
                        zzaeVar4 = new zzae(str, zzcVar4.getName(), 1L, 1L, 1L, zzcVar2.getTimestampMillis(), 0L, null, null, null, null);
                    } else {
                        zzcVar2 = zzcVar4;
                        zzaeVar4 = new zzae(str, zzcVar2.getName(), 1L, 1L, zzcVar2.getTimestampMillis(), 0L, null, null, null, null);
                    }
                    zzaeVar2 = zzaeVar4;
                } else {
                    str9 = str9;
                    arrayMap26 = arrayMap26;
                    zzcVar2 = zzcVar4;
                    arrayMap22 = arrayMap22;
                    str10 = str10;
                    hashSet = hashSet;
                    arrayMap30 = arrayMap30;
                    str11 = str11;
                    arrayMap31 = arrayMap31;
                    arrayMap32 = arrayMap32;
                    if (zZzd3) {
                        zzaeVar = new zzae(zzaeVarZzc.zzce, zzaeVarZzc.name, zzaeVarZzc.zzfg + 1, zzaeVarZzc.zzfh + 1, zzaeVarZzc.zzfi + 1, zzaeVarZzc.zzfj, zzaeVarZzc.zzfk, zzaeVarZzc.zzfl, zzaeVarZzc.zzfm, zzaeVarZzc.zzfn, zzaeVarZzc.zzfo);
                    } else {
                        zzaeVar = new zzae(zzaeVarZzc.zzce, zzaeVarZzc.name, zzaeVarZzc.zzfg + 1, zzaeVarZzc.zzfh + 1, zzaeVarZzc.zzfi, zzaeVarZzc.zzfj, zzaeVarZzc.zzfk, zzaeVarZzc.zzfl, zzaeVarZzc.zzfm, zzaeVarZzc.zzfn, zzaeVarZzc.zzfo);
                    }
                    zzaeVar2 = zzaeVar;
                }
                zzgy().zza(zzaeVar2);
                j = zzaeVar2.zzfg;
                arrayMap6 = arrayMap33;
                mapZzh = (Map) arrayMap6.get(str4);
                if (mapZzh == null) {
                    mapZzh = zzgy().zzh(str9, str4);
                    if (mapZzh == null) {
                        mapZzh = new ArrayMap<>();
                    }
                    arrayMap6.put(str4, mapZzh);
                }
                map = mapZzh;
                it2 = map.keySet().iterator();
                while (it2.hasNext()) {
                    iIntValue = it2.next().intValue();
                    hashSet = hashSet;
                    if (hashSet.contains(Integer.valueOf(iIntValue))) {
                        zzab().zzgs().zza(str11, Integer.valueOf(iIntValue));
                    } else {
                        arrayMap7 = arrayMap32;
                        bitSet = (BitSet) arrayMap7.get(Integer.valueOf(iIntValue));
                        arrayMap8 = arrayMap30;
                        bitSet2 = (BitSet) arrayMap8.get(Integer.valueOf(iIntValue));
                        if (zZzq) {
                            arrayMap9 = arrayMap31;
                            Map map13 = (Map) arrayMap9.get(Integer.valueOf(iIntValue));
                            map2 = (Map) arrayMap26.get(Integer.valueOf(iIntValue));
                            map3 = map13;
                        } else {
                            arrayMap9 = arrayMap31;
                            map2 = null;
                            map3 = null;
                        }
                        Map map14 = map2;
                        arrayMap10 = arrayMap22;
                        if (((com.google.android.gms.internal.measurement.zzbs.zza) arrayMap10.get(Integer.valueOf(iIntValue))) == null) {
                            arrayMap11 = arrayMap6;
                            arrayMap10.put(Integer.valueOf(iIntValue), (com.google.android.gms.internal.measurement.zzbs.zza) ((com.google.android.gms.internal.measurement.zzey) com.google.android.gms.internal.measurement.zzbs.zza.zzmc().zzk(true).zzug()));
                            bitSet5 = new BitSet();
                            arrayMap7.put(Integer.valueOf(iIntValue), bitSet5);
                            bitSet2 = new BitSet();
                            arrayMap8.put(Integer.valueOf(iIntValue), bitSet2);
                            if (zZzq) {
                                ArrayMap arrayMap38 = new ArrayMap();
                                arrayMap9.put(Integer.valueOf(iIntValue), arrayMap38);
                                ArrayMap arrayMap39 = new ArrayMap();
                                arrayMap26.put(Integer.valueOf(iIntValue), arrayMap39);
                                map3 = arrayMap38;
                                bitSet3 = bitSet5;
                                bitSet2 = bitSet2;
                                map4 = arrayMap39;
                            } else {
                                bitSet = bitSet5;
                            }
                            while (r20.hasNext()) {
                                if (!zZzd3) {
                                    j2 = j;
                                } else {
                                    j2 = j;
                                }
                                if (zzab().isLoggable(2)) {
                                    zzeh zzehVarZzgs5 = zzab().zzgs();
                                    Integer numValueOf7 = Integer.valueOf(iIntValue);
                                    if (zzaVar.zzkb()) {
                                        numValueOf2 = Integer.valueOf(zzaVar.getId());
                                    } else {
                                        numValueOf2 = null;
                                    }
                                    zzehVarZzgs5.zza("Evaluating filter. audience, filter, event", numValueOf7, numValueOf2, zzy().zzaj(zzaVar.zzjz()));
                                    str5 = str10;
                                    zzab().zzgs().zza(str5, zzgw().zza(zzaVar));
                                } else {
                                    arrayMap26 = arrayMap26;
                                    str11 = str11;
                                    str5 = str10;
                                }
                                if (zzaVar.zzkb()) {
                                }
                                str6 = str5;
                                zzaeVar3 = zzaeVar2;
                                arrayMap12 = arrayMap10;
                                arrayMap13 = arrayMap7;
                                map5 = map3;
                                bitSet4 = bitSet2;
                                arrayMap14 = arrayMap26;
                                str7 = str11;
                                arrayMap15 = arrayMap8;
                                arrayMap16 = arrayMap9;
                                map6 = map;
                                str8 = str;
                                zzeh zzehVarZzgn5 = zzab().zzgn();
                                Object objZzam5 = zzef.zzam(str);
                                if (zzaVar.zzkb()) {
                                    numValueOf = Integer.valueOf(zzaVar.getId());
                                } else {
                                    numValueOf = null;
                                }
                                zzehVarZzgn5.zza("Invalid event filter ID. appId, id", objZzam5, String.valueOf(numValueOf));
                                bitSet2 = bitSet4;
                                map3 = map5;
                                str9 = str8;
                                str11 = str7;
                                map = map6;
                                arrayMap9 = arrayMap16;
                                zzaeVar2 = zzaeVar3;
                                arrayMap10 = arrayMap12;
                                arrayMap7 = arrayMap13;
                                arrayMap26 = arrayMap14;
                                arrayMap8 = arrayMap15;
                                str10 = str6;
                            }
                            arrayMap30 = arrayMap8;
                            arrayMap31 = arrayMap9;
                            arrayMap22 = arrayMap10;
                            arrayMap32 = arrayMap7;
                            it2 = it2;
                            j = j;
                            arrayMap6 = arrayMap11;
                        } else {
                            arrayMap11 = arrayMap6;
                        }
                        bitSet3 = bitSet;
                        map4 = map14;
                        while (r20.hasNext()) {
                            if (!zZzd3) {
                                j2 = j;
                            } else {
                                j2 = j;
                            }
                            if (zzab().isLoggable(2)) {
                                zzeh zzehVarZzgs6 = zzab().zzgs();
                                Integer numValueOf8 = Integer.valueOf(iIntValue);
                                if (zzaVar.zzkb()) {
                                    numValueOf2 = Integer.valueOf(zzaVar.getId());
                                } else {
                                    numValueOf2 = null;
                                }
                                zzehVarZzgs6.zza("Evaluating filter. audience, filter, event", numValueOf8, numValueOf2, zzy().zzaj(zzaVar.zzjz()));
                                str5 = str10;
                                zzab().zzgs().zza(str5, zzgw().zza(zzaVar));
                            } else {
                                arrayMap26 = arrayMap26;
                                str11 = str11;
                                str5 = str10;
                            }
                            if (zzaVar.zzkb()) {
                            }
                            str6 = str5;
                            zzaeVar3 = zzaeVar2;
                            arrayMap12 = arrayMap10;
                            arrayMap13 = arrayMap7;
                            map5 = map3;
                            bitSet4 = bitSet2;
                            arrayMap14 = arrayMap26;
                            str7 = str11;
                            arrayMap15 = arrayMap8;
                            arrayMap16 = arrayMap9;
                            map6 = map;
                            str8 = str;
                            zzeh zzehVarZzgn6 = zzab().zzgn();
                            Object objZzam6 = zzef.zzam(str);
                            if (zzaVar.zzkb()) {
                                numValueOf = Integer.valueOf(zzaVar.getId());
                            } else {
                                numValueOf = null;
                            }
                            zzehVarZzgn6.zza("Invalid event filter ID. appId, id", objZzam6, String.valueOf(numValueOf));
                            bitSet2 = bitSet4;
                            map3 = map5;
                            str9 = str8;
                            str11 = str7;
                            map = map6;
                            arrayMap9 = arrayMap16;
                            zzaeVar2 = zzaeVar3;
                            arrayMap10 = arrayMap12;
                            arrayMap7 = arrayMap13;
                            arrayMap26 = arrayMap14;
                            arrayMap8 = arrayMap15;
                            str10 = str6;
                        }
                        arrayMap30 = arrayMap8;
                        arrayMap31 = arrayMap9;
                        arrayMap22 = arrayMap10;
                        arrayMap32 = arrayMap7;
                        it2 = it2;
                        j = j;
                        arrayMap6 = arrayMap11;
                    }
                }
                str9 = str9;
                str11 = str11;
                arrayMap33 = arrayMap6;
                j3 = jLongValue;
                zzcVar3 = zzcVar;
                l3 = l;
                arrayMap30 = arrayMap30;
                arrayMap31 = arrayMap31;
                arrayMap22 = arrayMap22;
                arrayMap32 = arrayMap32;
                str10 = str10;
                hashSet = hashSet;
                arrayMap26 = arrayMap26;
            }
        }
        String str12 = str9;
        ArrayMap arrayMap40 = arrayMap26;
        ArrayMap arrayMap41 = arrayMap22;
        String str13 = str10;
        HashSet hashSet3 = hashSet;
        ArrayMap arrayMap42 = arrayMap30;
        ArrayMap arrayMap43 = arrayMap31;
        ArrayMap arrayMap44 = arrayMap32;
        String str14 = str11;
        if (!list2.isEmpty()) {
            ArrayMap arrayMap45 = new ArrayMap();
            Iterator<com.google.android.gms.internal.measurement.zzbs.zzk> it9 = list2.iterator();
            while (it9.hasNext()) {
                com.google.android.gms.internal.measurement.zzbs.zzk next2 = it9.next();
                Map<Integer, List<com.google.android.gms.internal.measurement.zzbk.zzd>> mapZzi = (Map) arrayMap45.get(next2.getName());
                if (mapZzi == null) {
                    mapZzi = zzgy().zzi(str12, next2.getName());
                    if (mapZzi == null) {
                        mapZzi = new ArrayMap<>();
                    }
                    arrayMap45.put(next2.getName(), mapZzi);
                }
                Iterator<Integer> it10 = mapZzi.keySet().iterator();
                while (it10.hasNext()) {
                    int iIntValue4 = it10.next().intValue();
                    if (hashSet3.contains(Integer.valueOf(iIntValue4))) {
                        zzab().zzgs().zza(str14, Integer.valueOf(iIntValue4));
                    } else {
                        arrayMap44 = arrayMap44;
                        BitSet bitSet8 = (BitSet) arrayMap44.get(Integer.valueOf(iIntValue4));
                        ArrayMap arrayMap46 = arrayMap42;
                        BitSet bitSet9 = (BitSet) arrayMap46.get(Integer.valueOf(iIntValue4));
                        if (zZzq) {
                            arrayMap43 = arrayMap43;
                            arrayMap4 = (Map) arrayMap43.get(Integer.valueOf(iIntValue4));
                            arrayMap40 = arrayMap40;
                            arrayMap5 = (Map) arrayMap40.get(Integer.valueOf(iIntValue4));
                        } else {
                            arrayMap43 = arrayMap43;
                            arrayMap40 = arrayMap40;
                            arrayMap4 = null;
                            arrayMap5 = null;
                        }
                        Iterator<Integer> it11 = it10;
                        arrayMap41 = arrayMap41;
                        if (((com.google.android.gms.internal.measurement.zzbs.zza) arrayMap41.get(Integer.valueOf(iIntValue4))) == null) {
                            arrayMap41.put(Integer.valueOf(iIntValue4), (com.google.android.gms.internal.measurement.zzbs.zza) ((com.google.android.gms.internal.measurement.zzey) com.google.android.gms.internal.measurement.zzbs.zza.zzmc().zzk(true).zzug()));
                            bitSet8 = new BitSet();
                            arrayMap44.put(Integer.valueOf(iIntValue4), bitSet8);
                            bitSet9 = new BitSet();
                            arrayMap46.put(Integer.valueOf(iIntValue4), bitSet9);
                            if (zZzq) {
                                arrayMap4 = new ArrayMap();
                                arrayMap43.put(Integer.valueOf(iIntValue4), arrayMap4);
                                arrayMap5 = new ArrayMap();
                                arrayMap40.put(Integer.valueOf(iIntValue4), arrayMap5);
                            }
                        }
                        Iterator<com.google.android.gms.internal.measurement.zzbk.zzd> it12 = mapZzi.get(Integer.valueOf(iIntValue4)).iterator();
                        while (true) {
                            if (!it12.hasNext()) {
                                arrayMap40 = arrayMap40;
                                arrayMap42 = arrayMap46;
                                mapZzi = mapZzi;
                                break;
                            }
                            it12 = it12;
                            com.google.android.gms.internal.measurement.zzbk.zzd next3 = it12.next();
                            mapZzi = mapZzi;
                            str14 = str14;
                            if (zzab().isLoggable(2)) {
                                zzab().zzgs().zza("Evaluating filter. audience, filter, property", Integer.valueOf(iIntValue4), next3.zzkb() ? Integer.valueOf(next3.getId()) : null, zzy().zzal(next3.getPropertyName()));
                                str2 = str13;
                                zzab().zzgs().zza(str2, zzgw().zza(next3));
                            } else {
                                arrayMap40 = arrayMap40;
                                str2 = str13;
                            }
                            if (!next3.zzkb() || next3.getId() > 256) {
                                ArrayMap arrayMap47 = arrayMap46;
                                String str15 = str2;
                                zzab().zzgn().zza("Invalid property filter ID. appId, id", zzef.zzam(str), String.valueOf(next3.zzkb() ? Integer.valueOf(next3.getId()) : null));
                                hashSet3.add(Integer.valueOf(iIntValue4));
                                mapZzi = mapZzi;
                                str14 = str14;
                                arrayMap42 = arrayMap47;
                                str13 = str15;
                                break;
                            }
                            if (zZzq) {
                                boolean zZzkf2 = next3.zzkf();
                                boolean zZzkg2 = next3.zzkg();
                                boolean z5 = zZzd2 && next3.zzki();
                                boolean z6 = zZzkf2 || zZzkg2 || z5;
                                if (!bitSet8.get(next3.getId()) || z6) {
                                    Boolean boolZza3 = zza(next3, next2);
                                    str3 = str2;
                                    zzab().zzgs().zza("Property filter result", boolZza3 == null ? "null" : boolZza3);
                                    if (boolZza3 == null) {
                                        hashSet3.add(Integer.valueOf(iIntValue4));
                                    } else {
                                        bitSet9.set(next3.getId());
                                        if (!zZzd2 || !z5 || boolZza3.booleanValue()) {
                                            if (!zZzd || !bitSet8.get(next3.getId()) || next3.zzkf()) {
                                                bitSet8.set(next3.getId(), boolZza3.booleanValue());
                                            }
                                            if (boolZza3.booleanValue() && z6 && next2.zzqs()) {
                                                long jZzqt = next2.zzqt();
                                                if (zZzd2 && z5 && lValueOf != null) {
                                                    jZzqt = lValueOf.longValue();
                                                }
                                                if (zZzkg2) {
                                                    zzb(arrayMap5, next3.getId(), jZzqt);
                                                } else {
                                                    zza((Map<Integer, Long>) arrayMap4, next3.getId(), jZzqt);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    zzab().zzgs().zza("Property filter already evaluated true and it is not associated with an enhanced audience. audience ID, filter ID", Integer.valueOf(iIntValue4), next3.zzkb() ? Integer.valueOf(next3.getId()) : null);
                                    str13 = str2;
                                }
                            } else {
                                arrayMap46 = arrayMap46;
                                str3 = str2;
                                if (bitSet8.get(next3.getId())) {
                                    zzab().zzgs().zza("Property filter already evaluated true. audience ID, filter ID", Integer.valueOf(iIntValue4), next3.zzkb() ? Integer.valueOf(next3.getId()) : null);
                                } else {
                                    Boolean boolZza4 = zza(next3, next2);
                                    zzab().zzgs().zza("Property filter result", boolZza4 == null ? "null" : boolZza4);
                                    if (boolZza4 == null) {
                                        hashSet3.add(Integer.valueOf(iIntValue4));
                                    } else {
                                        bitSet9.set(next3.getId());
                                        if (boolZza4.booleanValue()) {
                                            bitSet8.set(next3.getId());
                                        }
                                    }
                                }
                            }
                            arrayMap46 = arrayMap46;
                            str13 = str3;
                        }
                        it10 = it11;
                    }
                }
                str12 = str;
            }
        }
        ArrayMap arrayMap48 = arrayMap43;
        ArrayMap arrayMap49 = arrayMap41;
        ArrayMap arrayMap50 = arrayMap44;
        ArrayMap arrayMap51 = arrayMap42;
        ArrayList arrayList2 = new ArrayList();
        Iterator it13 = arrayMap50.keySet().iterator();
        while (it13.hasNext()) {
            int iIntValue5 = ((Integer) it13.next()).intValue();
            if (hashSet3.contains(Integer.valueOf(iIntValue5))) {
                arrayMap = arrayMap50;
            } else {
                com.google.android.gms.internal.measurement.zzbs.zza zzaVar2 = (com.google.android.gms.internal.measurement.zzbs.zza) arrayMap49.get(Integer.valueOf(iIntValue5));
                com.google.android.gms.internal.measurement.zzbs.zza.C1276zza c1276zzaZzmc = zzaVar2 == null ? com.google.android.gms.internal.measurement.zzbs.zza.zzmc() : zzaVar2.zzuj();
                c1276zzaZzmc.zzi(iIntValue5);
                ArrayMap arrayMap52 = arrayMap51;
                com.google.android.gms.internal.measurement.zzbs.zzi.zza zzaVarZzn2 = com.google.android.gms.internal.measurement.zzbs.zzi.zzqh().zzo(zzjo.zza((BitSet) arrayMap50.get(Integer.valueOf(iIntValue5)))).zzn(zzjo.zza((BitSet) arrayMap52.get(Integer.valueOf(iIntValue5))));
                if (zZzq) {
                    arrayMap2 = arrayMap48;
                    zzaVarZzn2.zzp(zza((Map) arrayMap2.get(Integer.valueOf(iIntValue5))));
                    arrayMap3 = arrayMap40;
                    Map map15 = (Map) arrayMap3.get(Integer.valueOf(iIntValue5));
                    if (map15 == null) {
                        it = it13;
                        arrayMap = arrayMap50;
                        listEmptyList = Collections.emptyList();
                    } else {
                        ArrayList arrayList3 = new ArrayList(map15.size());
                        for (Integer num : map15.keySet()) {
                            Iterator it14 = it13;
                            com.google.android.gms.internal.measurement.zzbs.zzj.zza zzaVarZzal = com.google.android.gms.internal.measurement.zzbs.zzj.zzqo().zzal(num.intValue());
                            List list6 = (List) map15.get(num);
                            if (list6 != null) {
                                Collections.sort(list6);
                                Iterator it15 = list6.iterator();
                                while (it15.hasNext()) {
                                    zzaVarZzal.zzbj(((Long) it15.next()).longValue());
                                    map15 = map15;
                                    arrayMap50 = arrayMap50;
                                }
                            }
                            arrayList3.add((com.google.android.gms.internal.measurement.zzbs.zzj) ((com.google.android.gms.internal.measurement.zzey) zzaVarZzal.zzug()));
                            it13 = it14;
                            map15 = map15;
                            arrayMap50 = arrayMap50;
                        }
                        it = it13;
                        arrayMap = arrayMap50;
                        listEmptyList = arrayList3;
                    }
                    if (zZzd && c1276zzaZzmc.zzlw()) {
                        List<com.google.android.gms.internal.measurement.zzbs.zzj> listZzqe = c1276zzaZzmc.zzlx().zzqe();
                        if (listZzqe.isEmpty()) {
                            list3 = listEmptyList;
                        } else {
                            ArrayList arrayList4 = new ArrayList(listEmptyList);
                            ArrayMap arrayMap53 = new ArrayMap();
                            for (com.google.android.gms.internal.measurement.zzbs.zzj zzjVar : listZzqe) {
                                if (zzjVar.zzme() && zzjVar.zzql() > 0) {
                                    arrayMap53.put(Integer.valueOf(zzjVar.getIndex()), Long.valueOf(zzjVar.zzai(zzjVar.zzql() - 1)));
                                }
                            }
                            for (int i4 = 0; i4 < arrayList4.size(); i4++) {
                                com.google.android.gms.internal.measurement.zzbs.zzj zzjVar2 = (com.google.android.gms.internal.measurement.zzbs.zzj) arrayList4.get(i4);
                                Long l5 = (Long) arrayMap53.remove(zzjVar2.zzme() ? Integer.valueOf(zzjVar2.getIndex()) : null);
                                if (l5 != null) {
                                    ArrayList arrayList5 = new ArrayList();
                                    if (l5.longValue() < zzjVar2.zzai(0)) {
                                        arrayList5.add(l5);
                                    }
                                    arrayList5.addAll(zzjVar2.zzqk());
                                    arrayList4.set(i4, (com.google.android.gms.internal.measurement.zzbs.zzj) ((com.google.android.gms.internal.measurement.zzey) zzjVar2.zzuj().zzqw().zzr(arrayList5).zzug()));
                                }
                            }
                            for (Integer num2 : arrayMap53.keySet()) {
                                arrayList4.add((com.google.android.gms.internal.measurement.zzbs.zzj) ((com.google.android.gms.internal.measurement.zzey) com.google.android.gms.internal.measurement.zzbs.zzj.zzqo().zzal(num2.intValue()).zzbj(((Long) arrayMap53.get(num2)).longValue()).zzug()));
                                arrayMap53 = arrayMap53;
                            }
                            list3 = arrayList4;
                        }
                    } else {
                        list3 = listEmptyList;
                    }
                    zzaVarZzn2.zzq(list3);
                } else {
                    it = it13;
                    arrayMap = arrayMap50;
                    arrayMap2 = arrayMap48;
                    arrayMap3 = arrayMap40;
                }
                c1276zzaZzmc.zza(zzaVarZzn2);
                arrayMap49.put(Integer.valueOf(iIntValue5), (com.google.android.gms.internal.measurement.zzbs.zza) ((com.google.android.gms.internal.measurement.zzey) c1276zzaZzmc.zzug()));
                arrayList2.add((com.google.android.gms.internal.measurement.zzbs.zza) ((com.google.android.gms.internal.measurement.zzey) c1276zzaZzmc.zzug()));
                zzx zzxVarZzgy3 = zzgy();
                com.google.android.gms.internal.measurement.zzbs.zzi zziVarZzlv = c1276zzaZzmc.zzlv();
                zzxVarZzgy3.zzbi();
                zzxVarZzgy3.zzo();
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotNull(zziVarZzlv);
                byte[] byteArray = zziVarZzlv.toByteArray();
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("app_id", str);
                contentValues2.put("audience_id", Integer.valueOf(iIntValue5));
                contentValues2.put("current_results", byteArray);
                try {
                    try {
                        if (zzxVarZzgy3.getWritableDatabase().insertWithOnConflict("audience_filter_values", null, contentValues2, 5) == -1) {
                            zzxVarZzgy3.zzab().zzgk().zza("Failed to insert filter results (got -1). appId", zzef.zzam(str));
                        }
                    } catch (SQLiteException e5) {
                        e = e5;
                        zzxVarZzgy3.zzab().zzgk().zza("Error storing filter results. appId", zzef.zzam(str), e);
                    }
                } catch (SQLiteException e6) {
                    e = e6;
                }
                it13 = it;
                arrayMap51 = arrayMap52;
                arrayMap48 = arrayMap2;
                arrayMap40 = arrayMap3;
            }
            arrayMap50 = arrayMap;
        }
        return arrayList2;
    }

    @Override // com.google.android.gms.measurement.internal.zzjh
    protected final boolean zzbk() {
        return false;
    }
}
