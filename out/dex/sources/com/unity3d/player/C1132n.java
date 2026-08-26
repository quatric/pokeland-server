package com.unity3d.player;

/* JADX INFO: renamed from: com.unity3d.player.n */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C1132n {

    /* JADX INFO: renamed from: a */
    private static boolean f2064a = false;

    /* JADX INFO: renamed from: b */
    private boolean f2065b = false;

    /* JADX INFO: renamed from: c */
    private boolean f2066c = false;

    /* JADX INFO: renamed from: d */
    private boolean f2067d = true;

    /* JADX INFO: renamed from: e */
    private boolean f2068e = false;

    C1132n() {
    }

    /* JADX INFO: renamed from: a */
    static void m1944a() {
        f2064a = true;
    }

    /* JADX INFO: renamed from: b */
    static void m1945b() {
        f2064a = false;
    }

    /* JADX INFO: renamed from: c */
    static boolean m1946c() {
        return f2064a;
    }

    /* JADX INFO: renamed from: a */
    final void m1947a(boolean z) {
        this.f2065b = z;
    }

    /* JADX INFO: renamed from: b */
    final void m1948b(boolean z) {
        this.f2067d = z;
    }

    /* JADX INFO: renamed from: c */
    final void m1949c(boolean z) {
        this.f2068e = z;
    }

    /* JADX INFO: renamed from: d */
    final void m1950d(boolean z) {
        this.f2066c = z;
    }

    /* JADX INFO: renamed from: d */
    final boolean m1951d() {
        return this.f2067d;
    }

    /* JADX INFO: renamed from: e */
    final boolean m1952e() {
        return this.f2068e;
    }

    /* JADX INFO: renamed from: f */
    final boolean m1953f() {
        return f2064a && this.f2065b && !this.f2067d && !this.f2066c;
    }

    /* JADX INFO: renamed from: g */
    final boolean m1954g() {
        return this.f2066c;
    }

    public final String toString() {
        return super.toString();
    }
}
