package com.nintendo.npf.sdk.internal.p023e;

import android.util.Log;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p022d.C0947b;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.e.e */
/* JADX INFO: compiled from: SDKLog.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0955e {

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.e.e$a */
    /* JADX INFO: compiled from: SDKLog.java */
    private static class a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1283a = InterfaceC0875a.a.m1072b();

        /* JADX INFO: renamed from: b */
        static final C0947b f1284b = f1283a.mo1065s();

        /* JADX INFO: renamed from: c */
        static final boolean f1285c = f1284b.m1326b();

        /* JADX INFO: renamed from: d */
        static final boolean f1286d = f1284b.m1328c();
    }

    /* JADX INFO: renamed from: a */
    public static void m1391a(String str, String str2) {
        if (a.f1285c && a.f1286d) {
            Log.d(str, str2);
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m1392a(String str, String str2, Throwable th) {
        if (a.f1285c) {
            Log.w(str, str2, th);
        }
    }

    /* JADX INFO: renamed from: b */
    public static void m1393b(String str, String str2) {
        if (a.f1285c) {
            Log.i(str, str2);
        }
    }

    /* JADX INFO: renamed from: b */
    public static void m1394b(String str, String str2, Throwable th) {
        if (a.f1285c) {
            Log.e(str, str2, th);
        }
    }

    /* JADX INFO: renamed from: c */
    public static void m1395c(String str, String str2) {
        if (a.f1285c) {
            Log.w(str, str2);
        }
    }

    /* JADX INFO: renamed from: d */
    public static void m1396d(String str, String str2) {
        if (a.f1285c) {
            Log.e(str, str2);
        }
    }
}
