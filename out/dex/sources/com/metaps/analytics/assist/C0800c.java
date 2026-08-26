package com.metaps.analytics.assist;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import com.metaps.common.C0847a;

/* JADX INFO: renamed from: com.metaps.analytics.assist.c */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0800c extends AppSpot {

    /* JADX INFO: renamed from: j */
    private static final int f437j = 200;

    protected C0800c(Context context, String str, AppSpotType appSpotType, AppSpotConfig appSpotConfig, AppSpotListener appSpotListener) {
        super(context, str, appSpotType, appSpotConfig, appSpotListener);
        this.f382a = -1;
        this.f383b = -1;
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        if (getResources().getConfiguration().orientation == 2) {
            this.f386e = displayMetrics.heightPixels;
        }
        this.f388g = 200;
        this.f390i = new View.OnClickListener() { // from class: com.metaps.analytics.assist.c.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                C0847a.m903a(getClass().toString(), "catch the out of dialog click event");
                C0800c.this.m662a(AppSpotListener.DismissReason.OUTSIDE_CLICKED);
            }
        };
    }
}
