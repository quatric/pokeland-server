package com.google.android.gms.internal.measurement;

import android.util.Log;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzcp extends zzcm<Long> {
    zzcp(zzct zzctVar, String str, Long l) {
        super(zzctVar, str, l, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.measurement.zzcm
    /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public final Long zzc(Object obj) {
        if (obj instanceof Long) {
            return (Long) obj;
        }
        if (obj instanceof String) {
            try {
                return Long.valueOf(Long.parseLong((String) obj));
            } catch (NumberFormatException unused) {
            }
        }
        String strZzrm = super.zzrm();
        String strValueOf = String.valueOf(obj);
        StringBuilder sb = new StringBuilder(String.valueOf(strZzrm).length() + 25 + String.valueOf(strValueOf).length());
        sb.append("Invalid long value for ");
        sb.append(strZzrm);
        sb.append(": ");
        sb.append(strValueOf);
        Log.e("PhenotypeFlag", sb.toString());
        return null;
    }
}
