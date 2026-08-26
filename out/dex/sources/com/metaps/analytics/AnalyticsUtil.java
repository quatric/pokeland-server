package com.metaps.analytics;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import com.metaps.common.AdvertisingIdHandler;
import com.metaps.common.C0847a;
import com.metaps.common.C0852f;
import com.metaps.common.C0853g;
import com.metaps.common.C0856j;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class AnalyticsUtil {
    public static boolean deleteNotificationChannel(Context context, String str) {
        if (Build.VERSION.SDK_INT >= 26) {
            return C0856j.m995a(context, str);
        }
        return true;
    }

    public static String getAdId(Context context) {
        return AdvertisingIdHandler.m897a(context).m898a();
    }

    public static String getAnalyticsUserToken(Context context) {
        return C0853g.m936a(context).m960c();
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

    public static String getDeviceToken(Context context) {
        return C0856j.m992a(context).m1009a();
    }

    public static boolean isAdTrackingLimited(Context context) {
        return AdvertisingIdHandler.m897a(context).m899b();
    }

    public static boolean isGdprCountryIncluded(Context context) {
        EnumC0827p enumC0827pM802a = EnumC0827p.m802a(C0853g.m948f(context));
        if (EnumC0827p.NOT_SPECIFIED.equals(enumC0827pM802a)) {
            C0852f c0852fM930a = C0852f.m930a();
            long jCurrentTimeMillis = System.currentTimeMillis();
            while (!c0852fM930a.m935c() && System.currentTimeMillis() - jCurrentTimeMillis <= 2000) {
                if (c0852fM930a.m934b() != null) {
                    enumC0827pM802a = EnumC0827p.m802a(c0852fM930a.m934b().intValue());
                    break;
                }
            }
        }
        C0847a.m908b("[GDPR] Country Config : " + enumC0827pM802a);
        return EnumC0827p.INCLUDED.equals(enumC0827pM802a);
    }

    public static boolean isGdprUserAllowed(Context context) {
        EnumC0828q enumC0828qM805a = EnumC0828q.m805a(C0853g.m949g(context));
        C0847a.m908b("[GDPR] User Config : " + enumC0828qM805a);
        return EnumC0828q.ALLOWED.equals(enumC0828qM805a);
    }

    public static boolean isGdprUserNeedConfirm(Context context) {
        EnumC0828q enumC0828qM805a = EnumC0828q.m805a(C0853g.m949g(context));
        C0847a.m908b("[GDPR] User Config : " + enumC0828qM805a);
        return EnumC0828q.NOT_SPECIFIED.equals(enumC0828qM805a);
    }

    public static boolean setGdprUserAllowed(Context context, boolean z) {
        EnumC0828q enumC0828q = z ? EnumC0828q.ALLOWED : EnumC0828q.DECLINED;
        C0847a.m908b("[GDPR] User Config is set as : " + enumC0828q);
        return C0853g.m943b(context, enumC0828q.m806a());
    }

    public static boolean updateCreateNotificationChannel(Context context, String str, String str2) {
        if (Build.VERSION.SDK_INT >= 26) {
            return C0856j.m996a(context, str, str2);
        }
        return true;
    }
}
