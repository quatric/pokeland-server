package com.metaps.analytics.assist;

/* JADX INFO: renamed from: com.metaps.analytics.assist.g */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public enum EnumC0804g {
    HOUSE_AD(1, "house_ad"),
    PROMOTION(2, "promotion");


    /* JADX INFO: renamed from: c */
    private final int f497c;

    /* JADX INFO: renamed from: d */
    private final String f498d;

    EnumC0804g(int i, String str) {
        this.f497c = i;
        this.f498d = str;
    }

    /* JADX INFO: renamed from: a */
    public static EnumC0804g m690a(int i) {
        for (EnumC0804g enumC0804g : values()) {
            if (enumC0804g.m692a() == i) {
                return enumC0804g;
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: a */
    public static EnumC0804g m691a(String str) {
        for (EnumC0804g enumC0804g : values()) {
            if (enumC0804g.m693b().equals(str)) {
                return enumC0804g;
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: a */
    public int m692a() {
        return this.f497c;
    }

    /* JADX INFO: renamed from: b */
    public String m693b() {
        return this.f498d;
    }
}
