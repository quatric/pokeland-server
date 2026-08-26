package com.metaps.analytics.assist;

import android.content.Context;
import com.google.api.client.http.HttpStatusCodes;
import com.metaps.common.C0860n;

/* JADX INFO: renamed from: com.metaps.analytics.assist.a */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0798a extends AppSpot {

    /* JADX INFO: renamed from: com.metaps.analytics.assist.a$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* JADX INFO: renamed from: a */
        static final /* synthetic */ int[] f435a = new int[AppSpotType.values().length];

        static {
            try {
                f435a[AppSpotType.BANNER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f435a[AppSpotType.BANNER_BIG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f435a[AppSpotType.BANNER_RECTANGLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    protected C0798a(Context context, String str, AppSpotType appSpotType, AppSpotConfig appSpotConfig, AppSpotListener appSpotListener) {
        super(context, str, appSpotType, appSpotConfig, appSpotListener);
        this.f389h = true;
        int iM1036a = C0860n.m1036a(getContext(), m671b(appSpotType));
        this.f385d = iM1036a;
        this.f382a = iM1036a;
        int iM1036a2 = (appSpotConfig == null || !appSpotConfig.m669e()) ? C0860n.m1036a(getContext(), m670a(appSpotType)) : -1;
        this.f386e = iM1036a2;
        this.f383b = iM1036a2;
        if (appSpotConfig != null) {
            this.f384c = appSpotConfig.m668d();
        }
    }

    /* JADX INFO: renamed from: a */
    private int m670a(AppSpotType appSpotType) {
        int i = AnonymousClass1.f435a[appSpotType.ordinal()];
        if (i == 1 || i == 2) {
            return 320;
        }
        if (i != 3) {
            return 0;
        }
        return HttpStatusCodes.STATUS_CODE_MULTIPLE_CHOICES;
    }

    /* JADX INFO: renamed from: b */
    private int m671b(AppSpotType appSpotType) {
        int i = AnonymousClass1.f435a[appSpotType.ordinal()];
        if (i == 1) {
            return 50;
        }
        if (i != 2) {
            return i != 3 ? 0 : 250;
        }
        return 100;
    }
}
