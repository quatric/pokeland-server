package com.nintendo.npf.sdk.internal.impl.cpp;

import android.app.Activity;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.mynintendo.PointProgramService;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class PointProgramServiceEventHandler implements PointProgramService.EventCallback {

    /* JADX INFO: renamed from: a */
    private static final String f1524a = "PointProgramServiceEventHandler";

    /* JADX INFO: renamed from: b */
    private static PointProgramService f1525b;

    /* JADX INFO: renamed from: c */
    private static final PointProgramServiceEventHandler f1526c = new PointProgramServiceEventHandler();

    public static void dissmiss() {
        PointProgramService pointProgramService = f1525b;
        if (pointProgramService != null) {
            pointProgramService.dismiss();
        }
    }

    public static long getDebugCurrentTimestamp() {
        return PointProgramService.getDebugCurrentTimestamp();
    }

    public static void hide() {
        PointProgramService pointProgramService = f1525b;
        if (pointProgramService != null) {
            pointProgramService.hide();
        }
    }

    public static boolean isShowing() {
        PointProgramService pointProgramService = f1525b;
        if (pointProgramService != null) {
            return pointProgramService.isShowing();
        }
        return false;
    }

    private static native void onAppeared();

    private static native void onDismiss(String str);

    private static native void onHide();

    private static native void onNintendoAccountLogin();

    public static void resume(boolean z) {
        PointProgramService pointProgramService = f1525b;
        if (pointProgramService != null) {
            pointProgramService.resume(z);
        }
    }

    public static void setDebugCurrentTimestamp(long j) {
        PointProgramService.setDebugCurrentTimestamp(j);
    }

    public static void showMissionUi(Activity activity, float f, String str) {
        C0955e.m1391a(f1524a, "showMissionUi: widthRate=" + f + " countryCode=" + str);
        PointProgramService.showMissionUI(activity, f, str, f1526c);
    }

    public static void showRewardUi(Activity activity, float f, String str) {
        C0955e.m1391a(f1524a, "showRewardUi: width=" + f + " countryCode=" + str);
        PointProgramService.showRewardUI(activity, f, str, f1526c);
    }

    @Override // com.nintendo.npf.sdk.mynintendo.PointProgramService.EventCallback
    public void onAppeared(PointProgramService pointProgramService) {
        C0955e.m1391a(f1524a, "onAppeared");
        f1525b = pointProgramService;
        onAppeared();
    }

    @Override // com.nintendo.npf.sdk.mynintendo.PointProgramService.EventCallback
    public void onDismiss(NPFError nPFError) {
        C0955e.m1391a(f1524a, "onDisMiss");
        String string = null;
        f1525b = null;
        if (nPFError != null) {
            try {
                string = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        onDismiss(string);
    }

    @Override // com.nintendo.npf.sdk.mynintendo.PointProgramService.EventCallback
    public void onHide(PointProgramService pointProgramService) {
        C0955e.m1391a(f1524a, "onHide");
        f1525b = pointProgramService;
        onHide();
    }

    @Override // com.nintendo.npf.sdk.mynintendo.PointProgramService.EventCallback
    public void onNintendoAccountLogin(PointProgramService pointProgramService) {
        C0955e.m1391a(f1524a, "onNintendoAccountLogin");
        f1525b = pointProgramService;
        onNintendoAccountLogin();
    }
}
