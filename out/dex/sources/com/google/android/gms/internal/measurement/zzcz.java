package com.google.android.gms.internal.measurement;

import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzcz {
    @NonNullDecl
    public static <T> T checkNotNull(@NonNullDecl T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }
}
