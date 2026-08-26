package com.metaps.common;

/* JADX INFO: renamed from: com.metaps.common.m */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0859m {

    /* JADX INFO: renamed from: a */
    private static long f998a;

    /* JADX INFO: renamed from: a */
    public static long m1031a() {
        return System.currentTimeMillis() + f998a;
    }

    /* JADX INFO: renamed from: a */
    public static void m1032a(long j) {
        f998a = j - System.currentTimeMillis();
    }

    /* JADX INFO: renamed from: b */
    public static long m1033b() {
        return (System.currentTimeMillis() + f998a) / 1000;
    }

    /* JADX INFO: renamed from: b */
    public static long m1034b(long j) {
        return j + f998a;
    }

    /* JADX INFO: renamed from: c */
    public static long m1035c(long j) {
        return j + (f998a / 1000);
    }
}
