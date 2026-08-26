package com.metaps.common;

import android.app.Activity;
import android.util.Log;
import com.metaps.analytics.Analytics;
import com.metaps.analytics.AnalyticsUtil;
import com.metaps.analytics.Campaign;
import com.metaps.analytics.CampaignListener;
import com.metaps.analytics.assist.AppSpot;
import com.metaps.analytics.assist.AppSpotConfig;
import com.metaps.analytics.assist.AppSpotListener;
import com.metaps.analytics.assist.AppSpotType;
import com.unity3d.player.UnityPlayer;
import java.util.HashMap;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class UnityWrapper {
    private static final String HANDLER_LISTENER_APP_SPOT_CLICK = "AppSpotListenerOnClick";
    private static final String HANDLER_LISTENER_APP_SPOT_DISMISS = "AppSpotListenerOnDismiss";
    private static final String HANDLER_LISTENER_APP_SPOT_LOAD = "AppSpotListenerOnLoad";
    private static final String HANDLER_LISTENER_APP_SPOT_NOT_LOAD = "AppSpotListenerOnNotLoad";
    private static final String HANDLER_LISTENER_APP_SPOT_NOT_SHOW = "AppSpotListenerOnNotShow";
    private static final String HANDLER_LISTENER_APP_SPOT_SHOW = "AppSpotListenerOnShow";
    private static final String HANDLER_LISTENER_CAMPAIGN_MATCHED = "CampaignListenerOnCampaignMatched";
    private static final String HANDLER_RECEIVER_FINALIZE_ERROR = "ReceiverFinalizeOnError";
    private static final String HANDLER_RECEIVER_RETRIEVE = "ReceiverRetrieve";
    public static final String LOG_TAG = "AnalyticsSDK_Unity";
    public static final String PLATFORM = "Unity";
    public static final String WRAPPER_VERSION = "1.7.1";
    private static final int[] ORIENTATIONS = {0, 1};
    private static final int[] POSITIONS = {51, 48, 53, 3, 5, 83, 80, 85};
    private static HashMap<String, AppSpot> appSpotsMap = new HashMap<>();
    private static boolean logEnabled = false;
    private static String handlerName = "";

    static class UnityAppSpotListener implements AppSpotListener {
        UnityAppSpotListener() {
        }

        @Override // com.metaps.analytics.assist.AppSpotListener
        public void onClick(AppSpot appSpot, String str) {
            if (UnityWrapper.logEnabled) {
                Log.d(UnityWrapper.LOG_TAG, "AppSpotListener onClick");
            }
            UnityPlayer.UnitySendMessage(UnityWrapper.handlerName, UnityWrapper.HANDLER_LISTENER_APP_SPOT_CLICK, appSpot.getSpotCode() + "," + appSpot.getAppSpotType().ordinal() + "," + str);
        }

        @Override // com.metaps.analytics.assist.AppSpotListener
        public void onDismiss(AppSpot appSpot, AppSpotListener.DismissReason dismissReason) {
            if (UnityWrapper.logEnabled) {
                Log.d(UnityWrapper.LOG_TAG, "AppSpotListener onDismiss");
            }
            UnityPlayer.UnitySendMessage(UnityWrapper.handlerName, UnityWrapper.HANDLER_LISTENER_APP_SPOT_DISMISS, appSpot.getSpotCode() + "," + appSpot.getAppSpotType().ordinal() + "," + dismissReason.ordinal());
        }

        @Override // com.metaps.analytics.assist.AppSpotListener
        public void onLoad(AppSpot appSpot) {
            if (UnityWrapper.logEnabled) {
                Log.d(UnityWrapper.LOG_TAG, "AppSpotListener onLoad");
            }
            UnityPlayer.UnitySendMessage(UnityWrapper.handlerName, UnityWrapper.HANDLER_LISTENER_APP_SPOT_LOAD, appSpot.getSpotCode() + "," + appSpot.getAppSpotType().ordinal());
        }

        @Override // com.metaps.analytics.assist.AppSpotListener
        public void onNotLoad(AppSpot appSpot, AppSpotListener.NotLoadReason notLoadReason) {
            if (UnityWrapper.logEnabled) {
                Log.d(UnityWrapper.LOG_TAG, "AppSpotListener onNotLoad");
            }
            UnityPlayer.UnitySendMessage(UnityWrapper.handlerName, UnityWrapper.HANDLER_LISTENER_APP_SPOT_NOT_LOAD, appSpot.getSpotCode() + "," + appSpot.getAppSpotType().ordinal() + "," + notLoadReason.ordinal());
        }

        @Override // com.metaps.analytics.assist.AppSpotListener
        public void onNotShow(AppSpot appSpot, AppSpotListener.NotShowReason notShowReason) {
            if (UnityWrapper.logEnabled) {
                Log.d(UnityWrapper.LOG_TAG, "AppSpotListener onNotShow");
            }
            UnityPlayer.UnitySendMessage(UnityWrapper.handlerName, UnityWrapper.HANDLER_LISTENER_APP_SPOT_NOT_SHOW, appSpot.getSpotCode() + "," + appSpot.getAppSpotType().ordinal() + "," + notShowReason.ordinal());
        }

        @Override // com.metaps.analytics.assist.AppSpotListener
        public void onShow(AppSpot appSpot) {
            if (UnityWrapper.logEnabled) {
                Log.d(UnityWrapper.LOG_TAG, "AppSpotListener onShow");
            }
            UnityPlayer.UnitySendMessage(UnityWrapper.handlerName, UnityWrapper.HANDLER_LISTENER_APP_SPOT_SHOW, appSpot.getSpotCode() + "," + appSpot.getAppSpotType().ordinal());
        }
    }

    static class UnityCampaignListener implements CampaignListener {
        UnityCampaignListener() {
        }

        @Override // com.metaps.analytics.CampaignListener
        public void onCampaignMatched(Campaign campaign) {
            if (UnityWrapper.logEnabled) {
                Log.d(UnityWrapper.LOG_TAG, "CampaignListener onCampaignMatched");
            }
            UnityPlayer.UnitySendMessage(UnityWrapper.handlerName, UnityWrapper.HANDLER_LISTENER_CAMPAIGN_MATCHED, campaign.getDeferredDeepLink());
        }
    }

    public static boolean deleteNotificationChannel(String str) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.deleteNotificationChannel(" + str + ")");
        }
        return AnalyticsUtil.deleteNotificationChannel(UnityPlayer.currentActivity, str);
    }

    public static void dismissAppSpot(String str) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.dismissAppSpot()");
        }
        AppSpot appSpot = appSpotsMap.get(str);
        if (appSpot != null) {
            appSpot.dismiss();
        }
    }

    public static Campaign getMatchedCampaign() {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.getMatchedCampaign()");
        }
        return Analytics.getMatchedCampaign();
    }

    public static String getPlatform() {
        return PLATFORM;
    }

    public static String getPushNotificationCustomText() {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.getPushNotificationCustomText()");
        }
        return Analytics.getPushNotificationCustomText();
    }

    public static void initialize(final String str, final int i) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.initialize(" + str + ", " + i + ")");
        }
        final Activity activity = UnityPlayer.currentActivity;
        activity.runOnUiThread(new Runnable() { // from class: com.metaps.common.UnityWrapper.1
            @Override // java.lang.Runnable
            public void run() {
                if (Metaps.initializeSettings(activity, new UnityCampaignListener())) {
                    return;
                }
                Metaps.initialize(activity, str, i, new UnityCampaignListener());
            }
        });
    }

    public static boolean isAppSpotLoaded(String str) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.isAppSpotLoaded()");
        }
        AppSpot appSpot = appSpotsMap.get(str);
        if (appSpot != null) {
            return appSpot.isLoaded();
        }
        return false;
    }

    public static boolean isGdprCountryIncluded() {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.isGdprCountryIncluded()");
        }
        return AnalyticsUtil.isGdprCountryIncluded(UnityPlayer.currentActivity);
    }

    public static boolean isGdprUserAllowed() {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.isGdprUserAllowed()");
        }
        return AnalyticsUtil.isGdprUserAllowed(UnityPlayer.currentActivity);
    }

    public static boolean isGdprUserNeedConfirm() {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.isGdprUserNeedConfirm()");
        }
        return AnalyticsUtil.isGdprUserNeedConfirm(UnityPlayer.currentActivity);
    }

    public static boolean isInitialized() {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.isInitialized()");
        }
        Activity activity = UnityPlayer.currentActivity;
        return Metaps.isInitialized();
    }

    public static boolean isPushNotificationEnabled() {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.isPushNotificationEnabled()");
        }
        return Analytics.isPushNotificationEnabled(UnityPlayer.currentActivity);
    }

    public static void setAttribute(final String str, final String str2) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.setAttribute(" + str + ", " + str2 + ")");
        }
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() { // from class: com.metaps.common.UnityWrapper.7
            @Override // java.lang.Runnable
            public void run() {
                Analytics.setAttribute(str, str2);
            }
        });
    }

    public static void setCurrentPage(String str) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.setCurrentPage(" + str + ")");
        }
        Analytics.setCurrentPage(str);
    }

    public static boolean setGdprUserAllowed(boolean z) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.setGdprUserAllowed(\"" + z + "\")");
        }
        return AnalyticsUtil.setGdprUserAllowed(UnityPlayer.currentActivity, z);
    }

    public static void setHandlerName(String str) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call setHandlerName(" + str + ")");
        }
        handlerName = str;
    }

    public static void setLocationEnabled(boolean z) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.setLocationEnabled(" + z + ")");
        }
        Analytics.setLocationEnabled(z);
    }

    public static void setLogEnabled(boolean z) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.setLogEnabled(" + z + ")");
        }
        logEnabled = z;
        Analytics.setLogEnabled(z);
    }

    public static boolean setPushNotificationEnabled(boolean z) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.setPushNotificationEnabled(\"" + z + "\")");
        }
        return Analytics.setPushNotificationEnabled(UnityPlayer.currentActivity, z);
    }

    public static void setUserProfile(final String str, final String str2) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.setUserProfile(" + str + ", " + str2 + ")");
        }
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() { // from class: com.metaps.common.UnityWrapper.8
            @Override // java.lang.Runnable
            public void run() {
                Analytics.setUserProfile(str, str2);
            }
        });
    }

    public static void showAppSpot(String str) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.showAppSpot()");
        }
        AppSpot appSpot = appSpotsMap.get(str);
        if (appSpot != null) {
            appSpot.show(UnityPlayer.currentActivity);
        }
    }

    public static void startAppSpotLoading(String str, int i, int i2, int i3, int i4, int i5, boolean z) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.startAppSpotLoading()");
        }
        AppSpotConfig appSpotConfig = new AppSpotConfig();
        appSpotConfig.setIconCount(i2);
        appSpotConfig.setIconPosition(POSITIONS[i3]);
        appSpotConfig.setIconOrientation(ORIENTATIONS[i4]);
        appSpotConfig.setBannerPosition(POSITIONS[i5]);
        appSpotConfig.setBannerFitScreenWidth(z);
        AppSpot appSpotStartAppSpotLoading = Analytics.startAppSpotLoading(UnityPlayer.currentActivity, str, AppSpotType.values()[i], appSpotConfig, new UnityAppSpotListener());
        if (appSpotStartAppSpotLoading != null) {
            appSpotsMap.put(str, appSpotStartAppSpotLoading);
        }
    }

    public static void startSession() {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.startSession()");
        }
        final Activity activity = UnityPlayer.currentActivity;
        activity.runOnUiThread(new Runnable() { // from class: com.metaps.common.UnityWrapper.2
            @Override // java.lang.Runnable
            public void run() {
                Analytics.start(activity);
            }
        });
    }

    public static void stopSession() {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.stopSession()");
        }
        final Activity activity = UnityPlayer.currentActivity;
        activity.runOnUiThread(new Runnable() { // from class: com.metaps.common.UnityWrapper.3
            @Override // java.lang.Runnable
            public void run() {
                Analytics.stop(activity);
            }
        });
    }

    public static void trackAction(final String str) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.trackAction(" + str + ")");
        }
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() { // from class: com.metaps.common.UnityWrapper.9
            @Override // java.lang.Runnable
            public void run() {
                Analytics.trackAction(str);
            }
        });
    }

    public static void trackAction(final String str, final String str2) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.trackAction(" + str + ", " + str2 + ")");
        }
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() { // from class: com.metaps.common.UnityWrapper.10
            @Override // java.lang.Runnable
            public void run() {
                Analytics.trackAction(str, str2);
            }
        });
    }

    public static void trackEvent(final String str, final String str2, final int i) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.trackEvent(" + str + ", " + str2 + ", " + i + ")");
        }
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() { // from class: com.metaps.common.UnityWrapper.5
            @Override // java.lang.Runnable
            public void run() {
                Analytics.trackEvent(str, str2, i);
            }
        });
    }

    public static void trackPurchase(final String str, final double d, final String str2) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.trackPurchase(" + str + ", " + d + ", " + str2 + ")");
        }
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() { // from class: com.metaps.common.UnityWrapper.4
            @Override // java.lang.Runnable
            public void run() {
                Analytics.trackPurchase(str, d, str2);
            }
        });
    }

    public static void trackSpend(final String str, final String str2, final int i) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.trackSpend(" + str + ", " + str2 + ", " + i + ")");
        }
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() { // from class: com.metaps.common.UnityWrapper.6
            @Override // java.lang.Runnable
            public void run() {
                Analytics.trackSpend(str, str2, i);
            }
        });
    }

    public static boolean updateCreateNotificationChannel(String str, String str2) {
        if (logEnabled) {
            Log.d(LOG_TAG, "call UnityWrapper.updateCreateNotificationChannel(" + str + "," + str2 + ")");
        }
        return AnalyticsUtil.updateCreateNotificationChannel(UnityPlayer.currentActivity, str, str2);
    }
}
