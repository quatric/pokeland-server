package com.metaps.analytics;

import android.app.Activity;
import android.content.Context;
import com.metaps.analytics.assist.AppSpot;
import com.metaps.analytics.assist.AppSpotConfig;
import com.metaps.analytics.assist.AppSpotListener;
import com.metaps.analytics.assist.AppSpotType;
import com.metaps.analytics.assist.C0802e;
import com.metaps.common.C0853g;
import com.metaps.common.C0856j;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Analytics {
    public static final String PROFILE_KEY_AGE = "AGE";
    public static final String PROFILE_KEY_AGE_GROUP = "AGE_GROUP";
    public static final String PROFILE_KEY_BIRTHDAY = "BIRTHDAY";
    public static final String PROFILE_KEY_FRIENDS_COUNT = "FRIENDS_COUNT";
    public static final String PROFILE_KEY_GENDER = "GENDER";
    public static final String PROFILE_KEY_LEVEL = "LEVEL";
    public static final String PROFILE_KEY_NAME = "NAME";
    public static final String PROFILE_KEY_ORIGINAL_ID = "ORIGINAL_ID";
    public static final String PROFILE_KEY_RANK = "RANK";

    private Analytics() {
    }

    public static Campaign getMatchedCampaign() {
        return C0829r.m808a().m811b();
    }

    public static String getPushNotificationCustomText() {
        return C0856j.m1005c();
    }

    public static boolean isPushNotificationEnabled(Context context) {
        return C0853g.m947e(context);
    }

    public static void setAttribute(String str, String str2) {
        C0785a.m628b(str, str2);
    }

    public static void setCurrentPage(String str) {
        C0785a.m627b(str);
    }

    public static void setDeepLinkUrl(String str) {
        C0785a.m633c(str);
    }

    public static void setLocationEnabled(boolean z) {
        C0785a.m631b(z);
    }

    public static void setLogEnabled(boolean z) {
        C0785a.m625a(z);
    }

    public static boolean setPushNotificationEnabled(Context context, boolean z) {
        return C0853g.m941a(context, z);
    }

    public static void setUserProfile(String str, String str2) {
        C0785a.m634c(str, str2);
    }

    public static void start(Activity activity) {
        start(activity, null);
    }

    public static void start(Activity activity, String str) {
        C0785a.m615a(activity, str);
    }

    public static AppSpot startAppSpotLoading(Context context, String str, AppSpotType appSpotType, AppSpotConfig appSpotConfig, AppSpotListener appSpotListener) {
        return C0802e.m677a(context, str, appSpotType, appSpotConfig, appSpotListener);
    }

    public static void stop(Activity activity) {
        stop(activity, null);
    }

    public static void stop(Activity activity, String str) {
        C0785a.m626b(activity, str);
    }

    public static void trackAction(String str) {
        C0785a.m618a(str);
    }

    public static void trackAction(String str, String str2) {
        C0785a.m622a(str, str2);
    }

    public static void trackEvent(String str, String str2) {
        C0785a.m623a(str, str2, 1);
    }

    public static void trackEvent(String str, String str2, int i) {
        C0785a.m623a(str, str2, i);
    }

    public static void trackPurchase(String str, double d, String str2) {
        C0785a.m619a(str, d, str2);
    }

    public static void trackSpend(String str, String str2, int i) {
        C0785a.m629b(str, str2, i);
    }
}
