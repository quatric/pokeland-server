package com.google.android.gms.internal.measurement;

import android.net.Uri;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzcn {
    public static Uri zzdh(String str) {
        String strValueOf = String.valueOf(Uri.encode(str));
        return Uri.parse(strValueOf.length() != 0 ? "content://com.google.android.gms.phenotype/".concat(strValueOf) : new String("content://com.google.android.gms.phenotype/"));
    }
}
