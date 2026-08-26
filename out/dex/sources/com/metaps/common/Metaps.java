package com.metaps.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import com.metaps.analytics.C0829r;
import com.metaps.analytics.C0834w;
import com.metaps.analytics.CampaignListener;
import com.metaps.analytics.EnumC0827p;
import com.metaps.analytics.EnumC0828q;
import com.metaps.analytics.assist.C0802e;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Metaps {

    /* JADX INFO: renamed from: a */
    private static final int f852a = 60;

    /* JADX INFO: renamed from: b */
    private static String f853b;

    /* JADX INFO: renamed from: c */
    private static int f854c;

    /* JADX INFO: renamed from: a */
    private static synchronized void m900a(String str) {
        f853b = m901b(str);
    }

    /* JADX INFO: renamed from: b */
    private static String m901b(String str) {
        return str.replaceAll("\\p{Cntrl}|\\p{Space}", "");
    }

    public static synchronized int getAliveSessionTime() {
        return f854c;
    }

    public static synchronized String getApplicationId() {
        return f853b;
    }

    public static boolean initialize(Context context, String str) {
        return initialize(context, str, null);
    }

    public static synchronized boolean initialize(Context context, String str, int i, CampaignListener campaignListener) {
        C0847a.m908b("MetapsAnalytics ver 1.7.1");
        if (isInitialized()) {
            C0847a.m911c("Already initialized.");
            return false;
        }
        if (C0854h.m977d()) {
            C0847a.m911c("Unavailable to use Analytics SDK. In's in the zombie mode.");
            return false;
        }
        if (str != null && str.length() != 0) {
            if (i < 0) {
                C0847a.m911c("You must set positive integer to alive session time");
                return false;
            }
            m900a(str);
            f854c = i;
            C0852f.m930a().m933a(context, new C0849c(), str, context.getPackageName());
            C0802e.m678a(context);
            if (EnumC0827p.INCLUDED.m803a() != C0853g.m948f(context) || EnumC0828q.DECLINED.m806a() != C0853g.m949g(context)) {
                C0829r.m808a().m810a(context, str, campaignListener);
            }
            return true;
        }
        C0847a.m911c("appId parameter cannot be null or blank");
        return false;
    }

    public static boolean initialize(Context context, String str, CampaignListener campaignListener) {
        return initialize(context, str, f852a, campaignListener);
    }

    public static boolean initializeSettings(Context context) {
        return initializeSettings(context, null);
    }

    public static boolean initializeSettings(Context context, CampaignListener campaignListener) {
        String str;
        C0847a.m908b("MetapsAnalytics ver 1.7.1");
        if (isInitialized()) {
            str = "Already initialized.";
        } else {
            try {
                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                if (applicationInfo == null) {
                    str = "Not found meta-data info";
                } else if (applicationInfo.metaData == null) {
                    str = "Not found meta-data";
                } else {
                    if (applicationInfo.metaData.containsKey("com.metaps.analytics.enableLog")) {
                        boolean z = applicationInfo.metaData.getBoolean("com.metaps.analytics.enableLog", false);
                        C0847a.m906a(z);
                        if (z) {
                            C0847a.m908b("Enable log");
                        }
                    }
                    if (applicationInfo.metaData.containsKey("com.metaps.analytics.enableKochavaLog")) {
                        boolean z2 = applicationInfo.metaData.getBoolean("com.metaps.analytics.enableKochavaLog", false);
                        C0847a.m910b(z2);
                        if (z2) {
                            C0847a.m908b("Enable Kochava log");
                        }
                    }
                    String string = applicationInfo.metaData.getString("com.metaps.analytics.appId");
                    if (string != null) {
                        C0847a.m908b("appId is " + string);
                        if (applicationInfo.metaData.containsKey("com.metaps.analytics.enableAutoPurchaseEvent")) {
                            boolean z3 = applicationInfo.metaData.getBoolean("com.metaps.analytics.enableAutoPurchaseEvent", false);
                            C0834w.m880a(z3);
                            if (z3) {
                                C0847a.m908b("Enable auto purchase event");
                            }
                        }
                        int i = applicationInfo.metaData.getInt("com.metaps.analytics.sessionTime", 0);
                        if (i > 0) {
                            C0847a.m908b("sessionTime is " + i);
                            initialize(context, string, i, campaignListener);
                        } else {
                            initialize(context, string, campaignListener);
                        }
                        if (applicationInfo.metaData.containsKey("com.metaps.analytics.enableLocation")) {
                            boolean z4 = applicationInfo.metaData.getBoolean("com.metaps.analytics.enableLocation", true);
                            C0855i.m978a().m987a(z4);
                            if (!z4) {
                                C0847a.m908b("Disable location");
                            }
                        }
                        if (applicationInfo.metaData.containsKey("com.metaps.analytics.highLocationAccuracy")) {
                            if (applicationInfo.metaData.getBoolean("com.metaps.analytics.highLocationAccuracy", false)) {
                                C0847a.m908b("Set high location accuracy");
                                C0855i.m978a().m989c();
                            } else {
                                C0855i.m978a().m990d();
                            }
                        }
                        return true;
                    }
                    str = "Not found appId meta-data";
                }
            } catch (PackageManager.NameNotFoundException unused) {
                str = "Not found meta-data from AndroidManifest.xml";
            }
        }
        C0847a.m911c(str);
        return false;
    }

    public static boolean isInitialized() {
        return f853b != null;
    }

    public static void onActivityResult(Context context, int i, int i2, Intent intent) {
        C0847a.m908b("onActivityResult called. requestCode:" + i + " resultCode:" + i2);
        C0834w.m879a(context, i2, intent);
    }
}
