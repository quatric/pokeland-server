package com.amazon.device.iap.internal.util;

import java.util.Collection;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.util.d */
/* JADX INFO: compiled from: Validator.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0245d {
    /* JADX INFO: renamed from: a */
    public static void m408a(Object obj, String str) {
        if (obj != null) {
            return;
        }
        throw new IllegalArgumentException(str + " must not be null");
    }

    /* JADX INFO: renamed from: a */
    public static void m409a(String str, String str2) {
        if (m411a(str)) {
            throw new IllegalArgumentException(str2 + " must not be null or empty");
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m410a(Collection<? extends Object> collection, String str) {
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(str + " must not be empty");
        }
    }

    /* JADX INFO: renamed from: a */
    public static boolean m411a(String str) {
        return str == null || str.trim().length() == 0;
    }
}
