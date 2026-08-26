package com.metaps.common;

import android.util.Log;

/* JADX INFO: renamed from: com.metaps.common.a */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0847a {

    /* JADX INFO: renamed from: a */
    public static final String f855a = "AnalyticsSDK";

    /* JADX INFO: renamed from: b */
    private static final String f856b = "_debug_only";

    /* JADX INFO: renamed from: c */
    private static final long f857c = 1000;

    /* JADX INFO: renamed from: d */
    private static boolean f858d = false;

    /* JADX INFO: renamed from: e */
    private static boolean f859e = false;

    /* JADX INFO: renamed from: a */
    public static void m902a(String str) {
        if (C0854h.m968a() == 2 || C0854h.m968a() == 1 || f858d) {
            Log.v(m915e(""), m913d("", str));
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m903a(String str, String str2) {
        if (C0854h.m968a() == 2 || C0854h.m968a() == 1) {
            Log.d(m915e(f856b), m913d(str, str2));
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m904a(String str, String str2, long j) {
        long jCurrentTimeMillis = System.currentTimeMillis() - j;
        String str3 = str2 + " TIMER " + jCurrentTimeMillis + " ms";
        if (jCurrentTimeMillis >= f857c) {
            m909b(str, m913d("", str3));
        } else {
            m903a(str, m913d("", str3));
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m905a(String str, String str2, Exception exc) {
        if (C0854h.m968a() == 2 || C0854h.m968a() == 1) {
            Log.e(m915e(f856b), m913d(str, str2), exc);
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m906a(boolean z) {
        f858d = z;
    }

    /* JADX INFO: renamed from: a */
    public static boolean m907a() {
        return f859e;
    }

    /* JADX INFO: renamed from: b */
    public static void m908b(String str) {
        if (C0854h.m968a() == 2 || C0854h.m968a() == 1 || f858d) {
            Log.d(m915e(""), m913d("", str));
        }
    }

    /* JADX INFO: renamed from: b */
    public static void m909b(String str, String str2) {
        m905a(str, str2, (Exception) null);
    }

    /* JADX INFO: renamed from: b */
    public static void m910b(boolean z) {
        f859e = z;
    }

    /* JADX INFO: renamed from: c */
    public static void m911c(String str) {
        if (C0854h.m968a() == 2 || C0854h.m968a() == 1 || f858d) {
            Log.e(m915e(""), m913d("", str));
        }
    }

    /* JADX INFO: renamed from: c */
    public static void m912c(String str, String str2) {
        StringBuilder sb = new StringBuilder(str2.length());
        for (int i = 0; i < str2.length(); i++) {
            sb.append('-');
        }
        sb.append("\n");
        String string = sb.toString();
        m908b(str + "\n" + string + str2 + "\n" + string);
    }

    /* JADX INFO: renamed from: d */
    private static String m913d(String str, String str2) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str != null && str.length() > 0) {
            stringBuffer.append("[");
            stringBuffer.append(str);
            stringBuffer.append("] ");
        }
        stringBuffer.append(str2);
        return stringBuffer.toString();
    }

    /* JADX INFO: renamed from: d */
    public static void m914d(String str) {
        Log.d(m915e(""), m913d("", str));
    }

    /* JADX INFO: renamed from: e */
    private static String m915e(String str) {
        StringBuffer stringBuffer = new StringBuffer(f855a);
        stringBuffer.append(str);
        return stringBuffer.toString();
    }
}
