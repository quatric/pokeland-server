package com.metaps.analytics.assist;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public enum AppSpotType {
    INTERSTITIAL(1, "interstitial"),
    ICON(2, "icon"),
    BANNER(3, "banner"),
    BANNER_BIG(4, "banner_big"),
    BANNER_RECTANGLE(5, "banner_rectangle");


    /* JADX INFO: renamed from: a */
    private final int f433a;

    /* JADX INFO: renamed from: b */
    private final String f434b;

    AppSpotType(int i, String str) {
        this.f433a = i;
        this.f434b = str;
    }

    public static AppSpotType valueOfId(int i) {
        for (AppSpotType appSpotType : values()) {
            if (appSpotType.getId() == i) {
                return appSpotType;
            }
        }
        return null;
    }

    public int getId() {
        return this.f433a;
    }

    public String getName() {
        return this.f434b;
    }
}
