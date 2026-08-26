package com.nintendo.npf.sdk.internal.p023e;

import android.support.annotation.Nullable;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.e.b */
/* JADX INFO: compiled from: Lazy.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class AbstractC0952b<T> {

    /* JADX INFO: renamed from: a */
    private static final Object f1280a = new Object();

    /* JADX INFO: renamed from: b */
    private volatile T f1281b = (T) f1280a;

    @Nullable
    /* JADX INFO: renamed from: b */
    protected abstract T mo1074b();

    /* JADX INFO: renamed from: c */
    public T m1386c() {
        T tMo1074b = this.f1281b;
        if (tMo1074b == f1280a) {
            synchronized (this) {
                tMo1074b = this.f1281b;
                if (tMo1074b == f1280a) {
                    tMo1074b = mo1074b();
                    this.f1281b = tMo1074b;
                }
            }
        }
        return tMo1074b;
    }
}
