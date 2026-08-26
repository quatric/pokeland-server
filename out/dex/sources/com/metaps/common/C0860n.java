package com.metaps.common;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import java.util.List;

/* JADX INFO: renamed from: com.metaps.common.n */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0860n {
    /* JADX INFO: renamed from: a */
    public static int m1036a(Context context, int i) {
        return (int) ((i * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    /* JADX INFO: renamed from: a */
    public static int m1037a(String str) {
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str.charAt(i2) == '1') {
                i++;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: a */
    public static String m1038a(String str, int i, int i2) {
        C0847a.m903a(C0860n.class.toString(), "FQ: " + str + " add " + i + " for max length " + i2);
        if (str.length() >= i2) {
            str = str.substring((str.length() - i2) + 1);
        }
        return str + String.valueOf(i);
    }

    /* JADX INFO: renamed from: a */
    public static boolean m1039a(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.importance == 100 && runningAppProcessInfo.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: a */
    public static boolean m1040a(Context context, String str) {
        try {
            context.getPackageManager().getApplicationInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    /* JADX INFO: renamed from: b */
    public static boolean m1041b(Context context) {
        return !((KeyguardManager) context.getSystemService("keyguard")).inKeyguardRestrictedInputMode() && ((PowerManager) context.getSystemService("power")).isScreenOn();
    }
}
