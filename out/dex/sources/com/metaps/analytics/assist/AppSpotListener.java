package com.metaps.analytics.assist;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface AppSpotListener {

    public enum DismissReason {
        BACK_BUTTON,
        CLOSE_BUTTON,
        OUTSIDE_CLICKED,
        METHOD_CALL,
        OPEN_URL,
        TARGET_CLICKED
    }

    public enum NotLoadReason {
        LOAD_FAILED,
        NO_REMOTE_SETTING,
        NO_CONTENT
    }

    public enum NotShowReason {
        NOT_LOADED,
        INVALID_GUI,
        ALREADY_SHOWN
    }

    void onClick(AppSpot appSpot, String str);

    void onDismiss(AppSpot appSpot, DismissReason dismissReason);

    void onLoad(AppSpot appSpot);

    void onNotLoad(AppSpot appSpot, NotLoadReason notLoadReason);

    void onNotShow(AppSpot appSpot, NotShowReason notShowReason);

    void onShow(AppSpot appSpot);
}
