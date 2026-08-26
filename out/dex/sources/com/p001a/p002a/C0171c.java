package com.p001a.p002a;

import android.support.annotation.NonNull;
import org.jetbrains.annotations.Contract;

/* JADX INFO: renamed from: com.a.a.c */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0171c {

    /* JADX INFO: renamed from: a */
    public static final int f16a = -1;

    /* JADX INFO: renamed from: b */
    public static final int f17b = 0;

    /* JADX INFO: renamed from: c */
    public static final int f18c = 1;

    /* JADX INFO: renamed from: d */
    public static final int f19d = 2;

    /* JADX INFO: renamed from: e */
    public static final int f20e = 3;

    /* JADX INFO: renamed from: f */
    public static final int f21f = 4;

    /* JADX INFO: renamed from: g */
    public static final int f22g = 5;

    /* JADX INFO: renamed from: h */
    public static final int f23h = 6;

    /* JADX INFO: renamed from: i */
    @NonNull
    public final String f24i;

    /* JADX INFO: renamed from: j */
    public final long f25j;

    /* JADX INFO: renamed from: k */
    public final long f26k;

    /* JADX INFO: renamed from: l */
    public final int f27l;

    /* JADX INFO: renamed from: m */
    public final boolean f28m;

    C0171c(@NonNull String str, long j, long j2, int i, boolean z) {
        this.f24i = str;
        this.f25j = j;
        this.f26k = j2;
        this.f27l = i;
        this.f28m = z;
    }

    @Contract(pure = true)
    /* JADX INFO: renamed from: a */
    public final boolean m11a() {
        return (this.f24i.isEmpty() || this.f25j == -1 || this.f26k == -1 || this.f27l != 0) ? false : true;
    }

    @Contract(pure = true)
    /* JADX INFO: renamed from: b */
    public final boolean m12b() {
        int i = this.f27l;
        return (i == 2 || i == 5) ? false : true;
    }

    @Contract(pure = true)
    /* JADX INFO: renamed from: c */
    public final boolean m13c() {
        return this.f27l != 6;
    }
}
