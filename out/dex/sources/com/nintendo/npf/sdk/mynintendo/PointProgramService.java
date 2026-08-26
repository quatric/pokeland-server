package com.nintendo.npf.sdk.mynintendo;

import android.app.Activity;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.util.Calendar;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class PointProgramService {

    /* JADX INFO: renamed from: a */
    private static final String f1764a = "PointProgramService";

    /* JADX INFO: renamed from: b */
    private static long f1765b = 60000;

    /* JADX INFO: renamed from: c */
    private static long f1766c;

    public interface EventCallback {
        void onAppeared(PointProgramService pointProgramService);

        void onDismiss(NPFError nPFError);

        void onHide(PointProgramService pointProgramService);

        void onNintendoAccountLogin(PointProgramService pointProgramService);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.mynintendo.PointProgramService$a */
    private static class C1042a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1774a = InterfaceC0875a.a.m1072b();
    }

    /* JADX INFO: renamed from: a */
    private static void m1782a(final Activity activity, final float f, final String str, final String str2, final EventCallback eventCallback) {
        final EventCallback eventCallback2 = new EventCallback() { // from class: com.nintendo.npf.sdk.mynintendo.PointProgramService.1
            @Override // com.nintendo.npf.sdk.mynintendo.PointProgramService.EventCallback
            public void onAppeared(PointProgramService pointProgramService) {
                EventCallback eventCallback3 = eventCallback;
                if (eventCallback3 != null) {
                    eventCallback3.onAppeared(pointProgramService);
                }
            }

            @Override // com.nintendo.npf.sdk.mynintendo.PointProgramService.EventCallback
            public void onDismiss(NPFError nPFError) {
                EventCallback eventCallback3 = eventCallback;
                if (eventCallback3 != null) {
                    eventCallback3.onDismiss(nPFError);
                }
            }

            @Override // com.nintendo.npf.sdk.mynintendo.PointProgramService.EventCallback
            public void onHide(PointProgramService pointProgramService) {
                EventCallback eventCallback3 = eventCallback;
                if (eventCallback3 != null) {
                    eventCallback3.onHide(pointProgramService);
                }
            }

            @Override // com.nintendo.npf.sdk.mynintendo.PointProgramService.EventCallback
            public void onNintendoAccountLogin(PointProgramService pointProgramService) {
                EventCallback eventCallback3 = eventCallback;
                if (eventCallback3 != null) {
                    eventCallback3.onNintendoAccountLogin(pointProgramService);
                }
            }
        };
        if (C1042a.f1774a.mo1065s().m1332g() == null || C1042a.f1774a.mo1065s().m1332g().length() == 0) {
            eventCallback2.onDismiss(new C1025o(NPFError.ErrorType.PROCESS_CANCEL, -1, "not set pointProgramHost"));
            return;
        }
        C0955e.m1391a(f1764a, "fragment : " + str2);
        BaaSUser baaSUserM1665a = C1042a.f1774a.mo1048b().m1665a();
        boolean z = C1042a.f1774a.mo1050d().m1633b(baaSUserM1665a) && baaSUserM1665a.getNintendoAccount() != null;
        final String strM1347v = C1042a.f1774a.mo1065s().m1347v();
        if (!z) {
            C1042a.f1774a.mo1058l().m1441a(activity, f, m1783b(str, str2, null), strM1347v, eventCallback2);
            return;
        }
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        long jM1708a = C1042a.f1774a.mo1051e().m1708a(C1042a.f1774a.mo1048b().m1673b());
        if (jM1708a == 0 || jM1708a - timeInMillis >= f1765b) {
            C1042a.f1774a.mo1058l().m1441a(activity, f, m1783b(str, str2, C1042a.f1774a.mo1048b().m1665a().getNintendoAccount().getAccessToken()), strM1347v, eventCallback2);
        } else {
            C1042a.f1774a.mo1049c().m1517a(new BaaSUser.AuthorizationCallback() { // from class: com.nintendo.npf.sdk.mynintendo.PointProgramService.2
                @Override // com.nintendo.npf.sdk.user.BaaSUser.AuthorizationCallback
                public void onComplete(BaaSUser baaSUser, NPFError nPFError) {
                    C1042a.f1774a.mo1058l().m1441a(activity, f, PointProgramService.m1783b(str, str2, C1042a.f1774a.mo1048b().m1665a().getNintendoAccount().getAccessToken()), strM1347v, eventCallback2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public static String m1783b(String str, String str2, String str3) {
        String str4;
        String str5 = "";
        if (str3 != null) {
            str4 = "&access_token=" + str3;
        } else {
            str4 = "";
        }
        if (C1042a.f1774a.mo1065s().m1312C() && f1766c > 0) {
            str5 = "&debug_current_timestamp=" + f1766c;
        }
        return String.format("https://%s/inapp?platform=google&client_id=%s&country=%s&page=%s%s%s", C1042a.f1774a.mo1065s().m1332g(), C1042a.f1774a.mo1065s().m1329d(), str, str2, str4, str5);
    }

    public static long getDebugCurrentTimestamp() {
        return f1766c;
    }

    public static long getRetryAuthLimitTime() {
        return f1765b;
    }

    public static void setDebugCurrentTimestamp(long j) {
        if (C1042a.f1774a.mo1065s().m1312C()) {
            f1766c = j;
        }
    }

    public static void setRetryAuthLimitTime(long j) {
        f1765b = j;
    }

    public static void showMissionUI(Activity activity, float f, String str, EventCallback eventCallback) {
        m1782a(activity, f, str, "mission", eventCallback);
    }

    public static void showRewardUI(Activity activity, float f, String str, EventCallback eventCallback) {
        m1782a(activity, f, str, "reward", eventCallback);
    }

    public void dismiss() {
    }

    public void hide() {
    }

    public boolean isShowing() {
        return false;
    }

    public void resume(boolean z) {
    }
}
