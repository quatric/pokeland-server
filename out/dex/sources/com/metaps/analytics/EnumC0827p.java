package com.metaps.analytics;

/* JADX INFO: renamed from: com.metaps.analytics.p */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public enum EnumC0827p {
    NOT_SPECIFIED(0, "not specified"),
    INCLUDED(1, "included"),
    NOT_INCLUDED(9, "not included");


    /* JADX INFO: renamed from: d */
    private final int f727d;

    /* JADX INFO: renamed from: e */
    private final String f728e;

    EnumC0827p(int i, String str) {
        this.f727d = i;
        this.f728e = str;
    }

    /* JADX INFO: renamed from: a */
    public static EnumC0827p m802a(int i) {
        for (EnumC0827p enumC0827p : values()) {
            if (enumC0827p.m803a() == i) {
                return enumC0827p;
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: a */
    public int m803a() {
        return this.f727d;
    }

    /* JADX INFO: renamed from: b */
    public String m804b() {
        return this.f728e;
    }
}
