package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfu extends zzfs {
    private static final Class<?> zzajv = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzfu() {
        super();
    }

    private static <L> List<L> zza(Object obj, long j, int i) {
        Object obj2;
        List<L> listZzap;
        List<L> listZzd = zzd(obj, j);
        if (listZzd.isEmpty()) {
            if (listZzd instanceof zzfp) {
                listZzap = new zzfq(i);
            } else {
                listZzap = ((listZzd instanceof zzgu) && (listZzd instanceof zzff)) ? ((zzff) listZzd).zzap(i) : new ArrayList<>(i);
            }
            zzhv.zza(obj, j, listZzap);
            return listZzap;
        }
        if (zzajv.isAssignableFrom(listZzd.getClass())) {
            ArrayList arrayList = new ArrayList(listZzd.size() + i);
            arrayList.addAll(listZzd);
            zzhv.zza(obj, j, arrayList);
            obj2 = arrayList;
        } else {
            if (!(listZzd instanceof zzhu)) {
                if (!(listZzd instanceof zzgu) || !(listZzd instanceof zzff)) {
                    return listZzd;
                }
                zzff zzffVar = (zzff) listZzd;
                if (zzffVar.zzrx()) {
                    return listZzd;
                }
                zzff zzffVarZzap = zzffVar.zzap(listZzd.size() + i);
                zzhv.zza(obj, j, zzffVarZzap);
                return zzffVarZzap;
            }
            zzfq zzfqVar = new zzfq(listZzd.size() + i);
            zzfqVar.addAll((zzhu) listZzd);
            zzhv.zza(obj, j, zzfqVar);
            obj2 = zzfqVar;
        }
        return (List<L>) obj2;
    }

    private static <E> List<E> zzd(Object obj, long j) {
        return (List) zzhv.zzp(obj, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzfs
    final <L> List<L> zza(Object obj, long j) {
        return zza(obj, j, 10);
    }

    @Override // com.google.android.gms.internal.measurement.zzfs
    final <E> void zza(Object obj, Object obj2, long j) {
        List listZzd = zzd(obj2, j);
        List listZza = zza(obj, j, listZzd.size());
        int size = listZza.size();
        int size2 = listZzd.size();
        if (size > 0 && size2 > 0) {
            listZza.addAll(listZzd);
        }
        if (size > 0) {
            listZzd = listZza;
        }
        zzhv.zza(obj, j, listZzd);
    }

    @Override // com.google.android.gms.internal.measurement.zzfs
    final void zzb(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zzhv.zzp(obj, j);
        if (list instanceof zzfp) {
            objUnmodifiableList = ((zzfp) list).zzvg();
        } else {
            if (zzajv.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzgu) && (list instanceof zzff)) {
                zzff zzffVar = (zzff) list;
                if (zzffVar.zzrx()) {
                    zzffVar.zzry();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zzhv.zza(obj, j, objUnmodifiableList);
    }
}
