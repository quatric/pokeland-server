package com.metaps.analytics.assist;

import android.content.Context;
import com.metaps.common.C0860n;

/* JADX INFO: renamed from: com.metaps.analytics.assist.b */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0799b extends AppSpot {

    /* JADX INFO: renamed from: j */
    private static final int f436j = 57;

    protected C0799b(Context context, String str, AppSpotType appSpotType, AppSpotConfig appSpotConfig, AppSpotListener appSpotListener) {
        super(context, str, appSpotType, appSpotConfig, appSpotListener);
        this.f389h = true;
        if (appSpotConfig != null) {
            this.f384c = appSpotConfig.m665a();
            int iM666b = appSpotConfig.m667c() == 1 ? appSpotConfig.m666b() : 1;
            int iM666b2 = appSpotConfig.m667c() == 0 ? appSpotConfig.m666b() : 1;
            this.f385d = C0860n.m1036a(getContext(), f436j) * iM666b;
            this.f386e = C0860n.m1036a(getContext(), f436j) * iM666b2;
        }
        this.f383b = this.f386e;
    }
}
