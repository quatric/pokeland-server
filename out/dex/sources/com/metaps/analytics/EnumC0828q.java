package com.metaps.analytics;

/* JADX INFO: renamed from: com.metaps.analytics.q */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public enum EnumC0828q {
    NOT_SPECIFIED(0, "not specified"),
    ALLOWED(1, "allowed"),
    DECLINED(9, "declined");


    /* JADX INFO: renamed from: d */
    private final int f733d;

    /* JADX INFO: renamed from: e */
    private final String f734e;

    EnumC0828q(int i, String str) {
        this.f733d = i;
        this.f734e = str;
    }

    /* JADX INFO: renamed from: a */
    public static EnumC0828q m805a(int i) {
        for (EnumC0828q enumC0828q : values()) {
            if (enumC0828q.m806a() == i) {
                return enumC0828q;
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: a */
    public int m806a() {
        return this.f733d;
    }

    /* JADX INFO: renamed from: b */
    public String m807b() {
        return this.f734e;
    }
}
