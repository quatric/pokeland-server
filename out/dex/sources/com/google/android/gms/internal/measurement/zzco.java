package com.google.android.gms.internal.measurement;

import android.util.Log;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzco extends zzcm<Boolean> {
    zzco(zzct zzctVar, String str, Boolean bool) {
        super(zzctVar, str, bool, null);
    }

    @Override // com.google.android.gms.internal.measurement.zzcm
    final /* synthetic */ Boolean zzc(Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (zzbz.zzzw.matcher(str).matches()) {
                return true;
            }
            if (zzbz.zzzx.matcher(str).matches()) {
                return false;
            }
        }
        String strZzrm = super.zzrm();
        String strValueOf = String.valueOf(obj);
        StringBuilder sb = new StringBuilder(String.valueOf(strZzrm).length() + 28 + String.valueOf(strValueOf).length());
        sb.append("Invalid boolean value for ");
        sb.append(strZzrm);
        sb.append(": ");
        sb.append(strValueOf);
        Log.e("PhenotypeFlag", sb.toString());
        return null;
    }
}
