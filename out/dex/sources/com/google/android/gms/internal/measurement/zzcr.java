package com.google.android.gms.internal.measurement;

import android.util.Log;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzcr extends zzcm<Double> {
    zzcr(zzct zzctVar, String str, Double d) {
        super(zzctVar, str, d, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.measurement.zzcm
    /* JADX INFO: renamed from: zze, reason: merged with bridge method [inline-methods] */
    public final Double zzc(Object obj) {
        if (obj instanceof Double) {
            return (Double) obj;
        }
        if (obj instanceof Float) {
            return Double.valueOf(((Float) obj).doubleValue());
        }
        if (obj instanceof String) {
            try {
                return Double.valueOf(Double.parseDouble((String) obj));
            } catch (NumberFormatException unused) {
            }
        }
        String strZzrm = super.zzrm();
        String strValueOf = String.valueOf(obj);
        StringBuilder sb = new StringBuilder(String.valueOf(strZzrm).length() + 27 + String.valueOf(strValueOf).length());
        sb.append("Invalid double value for ");
        sb.append(strZzrm);
        sb.append(": ");
        sb.append(strValueOf);
        Log.e("PhenotypeFlag", sb.toString());
        return null;
    }
}
