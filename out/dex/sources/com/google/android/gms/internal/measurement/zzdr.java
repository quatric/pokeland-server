package com.google.android.gms.internal.measurement;

import java.util.Comparator;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzdr implements Comparator<zzdp> {
    zzdr() {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ int compare(zzdp zzdpVar, zzdp zzdpVar2) {
        zzdp zzdpVar3 = zzdpVar;
        zzdp zzdpVar4 = zzdpVar2;
        zzdu zzduVar = (zzdu) zzdpVar3.iterator();
        zzdu zzduVar2 = (zzdu) zzdpVar4.iterator();
        while (zzduVar.hasNext() && zzduVar2.hasNext()) {
            int iCompare = Integer.compare(zzdp.zza(zzduVar.nextByte()), zzdp.zza(zzduVar2.nextByte()));
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return Integer.compare(zzdpVar3.size(), zzdpVar4.size());
    }
}
