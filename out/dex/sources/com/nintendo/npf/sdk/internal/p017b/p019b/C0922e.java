package com.nintendo.npf.sdk.internal.p017b.p019b;

import com.nintendo.npf.sdk.internal.p023e.C0955e;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.b.e */
/* JADX INFO: compiled from: NPFCommunicationStatistics.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0922e {

    /* JADX INFO: renamed from: a */
    private static long f1208a = 0;

    /* JADX INFO: renamed from: b */
    private static long f1209b = 0;

    /* JADX INFO: renamed from: c */
    private static boolean f1210c = false;

    /* JADX INFO: renamed from: a */
    public static void m1243a() {
        f1210c = true;
    }

    /* JADX INFO: renamed from: a */
    public static void m1244a(long j) {
        if (!f1210c || j <= 0) {
            return;
        }
        f1208a += j;
        C0955e.m1391a("NPFCommunication", "RequestDataSize: " + f1208a);
    }

    /* JADX INFO: renamed from: b */
    public static void m1245b(long j) {
        if (!f1210c || j <= 0) {
            return;
        }
        f1209b += j;
        C0955e.m1391a("NPFCommunication", "ResponseDataSize: " + f1209b);
    }

    /* JADX INFO: renamed from: b */
    public static boolean m1246b() {
        return f1210c;
    }

    /* JADX INFO: renamed from: c */
    public static long m1247c() {
        return f1208a;
    }

    /* JADX INFO: renamed from: d */
    public static long m1248d() {
        return f1209b;
    }
}
