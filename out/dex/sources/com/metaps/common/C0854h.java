package com.metaps.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.metaps.common.h */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0854h {

    /* JADX INFO: renamed from: C */
    private static final String f917C = "com.metaps.common.UnityWrapper";

    /* JADX INFO: renamed from: D */
    private static final String f918D = "com.metaps.cordova.MetapsAnalyticsPlugin";

    /* JADX INFO: renamed from: a */
    protected static final String f919a = "1.7.1";

    /* JADX INFO: renamed from: b */
    public static final int f920b = 0;

    /* JADX INFO: renamed from: c */
    public static final int f921c = 1;

    /* JADX INFO: renamed from: d */
    public static final int f922d = 2;

    /* JADX INFO: renamed from: e */
    public static final int f923e = 15000;

    /* JADX INFO: renamed from: f */
    public static final int f924f = 15000;

    /* JADX INFO: renamed from: g */
    public static final String f925g = "AnalyticsSDK ver";

    /* JADX INFO: renamed from: h */
    public static final String f926h = "Android";

    /* JADX INFO: renamed from: i */
    public static final long f927i = 86400000;

    /* JADX INFO: renamed from: j */
    public static final long f928j = 946684800;

    /* JADX INFO: renamed from: k */
    public static final int f929k = 32;

    /* JADX INFO: renamed from: l */
    public static final int f930l = 128;

    /* JADX INFO: renamed from: m */
    public static final String f931m = "mANALYTICS";

    /* JADX INFO: renamed from: n */
    public static final String f932n = "install.time";

    /* JADX INFO: renamed from: o */
    public static final String f933o = "mADS";

    /* JADX INFO: renamed from: p */
    public static final String f934p = "notifs.list";

    /* JADX INFO: renamed from: q */
    private static int f935q = 0;

    /* JADX INFO: renamed from: s */
    private static final String f937s = "Unity";

    /* JADX INFO: renamed from: t */
    private static final String f938t = "Cordova";

    /* JADX INFO: renamed from: x */
    private static final String f942x = "com.metaps.analytics.Config";

    /* JADX INFO: renamed from: y */
    private static final String f943y = "getEnv";

    /* JADX INFO: renamed from: u */
    private static final Map<Integer, String> f939u = new HashMap<Integer, String>() { // from class: com.metaps.common.h.1

        /* JADX INFO: renamed from: a */
        private static final long f945a = 1;

        {
            put(0, "https://api-analytics-bootstrap.metaps.com/apps/%s/%s.json");
            put(1, "https://api-analytics-bootstrap-stg.metaps.com/apps/%s/%s.json");
            put(2, "https://api-analytics-bootstrap-dev.metaps.com/apps/%s/%s.json");
        }
    };

    /* JADX INFO: renamed from: v */
    private static final Map<Integer, String> f940v = new HashMap<Integer, String>() { // from class: com.metaps.common.h.2

        /* JADX INFO: renamed from: a */
        private static final long f946a = 1;

        {
            put(0, "https://api-analytics.metaps.com/setting/app_config?app_key=%s&pkg_id=%s&os_name=%s");
            put(1, "https://api-analytics-stg.metaps.com/setting/app_config?app_key=%s&pkg_id=%s&os_name=%s");
            put(2, "https://api-analytics-dev.metaps.com/setting/app_config?app_key=%s&pkg_id=%s&os_name=%s");
        }
    };

    /* JADX INFO: renamed from: w */
    private static boolean f941w = true;

    /* JADX INFO: renamed from: z */
    private static boolean f944z = false;

    /* JADX INFO: renamed from: A */
    private static boolean f915A = true;

    /* JADX INFO: renamed from: r */
    private static final String f936r = "Native";

    /* JADX INFO: renamed from: B */
    private static String f916B = f936r;

    /* JADX INFO: renamed from: a */
    public static int m968a() {
        if (f941w) {
            try {
                Integer num = (Integer) Class.forName(f942x).getMethod(f943y, new Class[0]).invoke(null, new Object[0]);
                if (f935q != num.intValue()) {
                    f935q = num.intValue();
                }
            } catch (RuntimeException | Exception unused) {
                f941w = false;
            }
        }
        return f935q;
    }

    /* JADX INFO: renamed from: a */
    public static synchronized long m969a(Context context) {
        return context.getSharedPreferences(f931m, 0).getLong(f932n, 0L);
    }

    /* JADX INFO: renamed from: a */
    public static String m970a(String str) {
        return String.format(f939u.get(Integer.valueOf(m968a())), str, "1.7.1".replace('.', '_'));
    }

    /* JADX INFO: renamed from: a */
    public static String m971a(String str, String str2, String str3) {
        return String.format(f940v.get(Integer.valueOf(m968a())), str, str2, str3);
    }

    /* JADX INFO: renamed from: a */
    public static void m972a(boolean z) {
        f944z = z;
    }

    /* JADX INFO: renamed from: b */
    public static synchronized long m973b(Context context) {
        long jM1033b;
        jM1033b = 0;
        try {
            jM1033b = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime / 1000;
        } catch (PackageManager.NameNotFoundException unused) {
            C0847a.m909b(C0854h.class.toString(), "Failed to get file first install time");
        }
        if (jM1033b < f928j) {
            jM1033b = C0859m.m1033b();
        }
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(f931m, 0).edit();
        editorEdit.putLong(f932n, jM1033b);
        editorEdit.commit();
        return jM1033b;
    }

    /* JADX INFO: renamed from: b */
    public static String m974b() {
        if (f915A) {
            try {
                Class.forName(f917C);
                f916B = "Unity";
            } catch (Exception unused) {
            }
            try {
                Class.forName(f918D);
                f916B = f938t;
            } catch (Exception unused2) {
            }
            f915A = false;
        }
        return f916B;
    }

    /* JADX INFO: renamed from: c */
    public static String m975c() {
        return "1.7.1/" + m974b();
    }

    /* JADX INFO: renamed from: c */
    public static synchronized boolean m976c(Context context) {
        boolean z;
        z = m969a(context) == 0;
        if (z) {
            m973b(context);
        }
        return z;
    }

    /* JADX INFO: renamed from: d */
    public static boolean m977d() {
        return f944z;
    }
}
