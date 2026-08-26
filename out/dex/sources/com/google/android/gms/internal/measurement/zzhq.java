package com.google.android.gms.internal.measurement;

import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzhq extends RuntimeException {
    private final List<String> zzalx;

    public zzhq(zzgi zzgiVar) {
        super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
        this.zzalx = null;
    }
}
