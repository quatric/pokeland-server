package com.metaps.analytics.assist;

import android.content.Context;

/* JADX INFO: renamed from: com.metaps.analytics.assist.e */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0802e {

    /* JADX INFO: renamed from: com.metaps.analytics.assist.e$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* JADX INFO: renamed from: a */
        static final /* synthetic */ int[] f456a = new int[AppSpotType.values().length];

        static {
            try {
                f456a[AppSpotType.INTERSTITIAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f456a[AppSpotType.ICON.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f456a[AppSpotType.BANNER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f456a[AppSpotType.BANNER_BIG.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f456a[AppSpotType.BANNER_RECTANGLE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public static AppSpot m677a(Context context, String str, AppSpotType appSpotType, AppSpotConfig appSpotConfig, AppSpotListener appSpotListener) {
        int i = AnonymousClass1.f456a[appSpotType.ordinal()];
        if (i == 1) {
            return new C0800c(context, str, appSpotType, appSpotConfig, appSpotListener);
        }
        if (i == 2) {
            return new C0799b(context, str, appSpotType, appSpotConfig, appSpotListener);
        }
        if (i == 3 || i == 4 || i == 5) {
            return new C0798a(context, str, appSpotType, appSpotConfig, appSpotListener);
        }
        return null;
    }

    /* JADX INFO: renamed from: a */
    public static void m678a(Context context) {
        C0803f.m682a();
    }
}
