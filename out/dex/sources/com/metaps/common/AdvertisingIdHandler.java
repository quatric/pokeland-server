package com.metaps.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class AdvertisingIdHandler {

    /* JADX INFO: renamed from: a */
    private static final String f843a = "com.google.android.gms.version";

    /* JADX INFO: renamed from: b */
    private static final String f844b = "com.google.android.gms.ads.identifier.AdvertisingIdClient";

    /* JADX INFO: renamed from: c */
    private static final String f845c = "com.google.android.gms.ads.identifier.AdvertisingIdClient$Info";

    /* JADX INFO: renamed from: d */
    private static final String f846d = "getAdvertisingIdInfo";

    /* JADX INFO: renamed from: e */
    private static final String f847e = "getId";

    /* JADX INFO: renamed from: f */
    private static final String f848f = "isLimitAdTrackingEnabled";

    /* JADX INFO: renamed from: g */
    private static AdvertisingIdHandler f849g;

    /* JADX INFO: renamed from: h */
    private Object f850h;

    /* JADX INFO: renamed from: i */
    private boolean f851i = false;

    private AdvertisingIdHandler(Context context) {
        boolean z;
        StringBuilder sb;
        String message;
        Exception exc;
        this.f850h = null;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo == null || applicationInfo.metaData == null) {
                z = false;
            } else if (applicationInfo.metaData.get(f843a) != null) {
                z = true;
            } else {
                C0847a.m908b("[Advertising Id] No meta found for Google Play Services");
                z = false;
            }
        } catch (PackageManager.NameNotFoundException e) {
            C0847a.m909b(AdvertisingIdHandler.class.toString(), "Failed to search for meta-data " + e.getMessage());
        }
        if (!z) {
            C0847a.m908b("[Advertising Id] constructor - To use Advertising ID, <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" /> should be added to your AndroidManifest.xml file in the <application> tag");
            return;
        }
        try {
            this.f850h = Class.forName(f844b).getMethod(f846d, Context.class).invoke(null, context);
        } catch (ClassNotFoundException e2) {
            sb = new StringBuilder();
            sb.append("[Advertising Id] A class from Google Play Services is not available ");
            message = e2.getMessage();
            exc = e2;
            sb.append(message);
            C0847a.m911c(sb.toString());
            C0847a.m905a(AdvertisingIdHandler.class.toString(), "Advertising Id", exc);
        } catch (IllegalAccessException e3) {
            sb = new StringBuilder();
            sb.append("[Advertising Id] Illegal access exception ");
            message = e3.getMessage();
            exc = e3;
            sb.append(message);
            C0847a.m911c(sb.toString());
            C0847a.m905a(AdvertisingIdHandler.class.toString(), "Advertising Id", exc);
        } catch (NoSuchMethodException e4) {
            sb = new StringBuilder();
            sb.append("[Advertising Id] A method from Google Play Services is not available ");
            message = e4.getMessage();
            exc = e4;
            sb.append(message);
            C0847a.m911c(sb.toString());
            C0847a.m905a(AdvertisingIdHandler.class.toString(), "Advertising Id", exc);
        } catch (InvocationTargetException e5) {
            sb = new StringBuilder();
            sb.append("[Advertising Id] Failed to invoke method ");
            message = e5.getMessage();
            exc = e5;
            sb.append(message);
            C0847a.m911c(sb.toString());
            C0847a.m905a(AdvertisingIdHandler.class.toString(), "Advertising Id", exc);
        }
    }

    /* JADX INFO: renamed from: a */
    public static AdvertisingIdHandler m897a(Context context) {
        if (f849g == null) {
            f849g = new AdvertisingIdHandler(context);
        }
        return f849g;
    }

    /* JADX INFO: renamed from: a */
    public String m898a() {
        StringBuilder sb;
        String message;
        Exception exc;
        if (this.f850h == null) {
            return "";
        }
        try {
            String str = (String) Class.forName(f845c).getMethod(f847e, new Class[0]).invoke(this.f850h, new Object[0]);
            if (str != null && str.length() > 0 && !this.f851i) {
                C0847a.m912c("Advertising Id", str);
                this.f851i = true;
            }
            return str;
        } catch (ClassNotFoundException e) {
            sb = new StringBuilder();
            sb.append("[Advertising Id] A class from Google Play Services is not available ");
            message = e.getMessage();
            exc = e;
            sb.append(message);
            C0847a.m911c(sb.toString());
            C0847a.m905a(AdvertisingIdHandler.class.toString(), "Advertising Id", exc);
            return "";
        } catch (IllegalAccessException e2) {
            sb = new StringBuilder();
            sb.append("[Advertising Id] Illegal access exception ");
            message = e2.getMessage();
            exc = e2;
            sb.append(message);
            C0847a.m911c(sb.toString());
            C0847a.m905a(AdvertisingIdHandler.class.toString(), "Advertising Id", exc);
            return "";
        } catch (NoSuchMethodException e3) {
            sb = new StringBuilder();
            sb.append("[Advertising Id] A method from Google Play Services is not available ");
            message = e3.getMessage();
            exc = e3;
            sb.append(message);
            C0847a.m911c(sb.toString());
            C0847a.m905a(AdvertisingIdHandler.class.toString(), "Advertising Id", exc);
            return "";
        } catch (InvocationTargetException e4) {
            sb = new StringBuilder();
            sb.append("[Advertising Id] Failed to invoke method ");
            message = e4.getMessage();
            exc = e4;
            sb.append(message);
            C0847a.m911c(sb.toString());
            C0847a.m905a(AdvertisingIdHandler.class.toString(), "Advertising Id", exc);
            return "";
        }
    }

    /* JADX INFO: renamed from: b */
    public boolean m899b() {
        StringBuilder sb;
        String message;
        Exception exc;
        if (this.f850h != null) {
            try {
                return ((Boolean) Class.forName(f845c).getMethod(f848f, new Class[0]).invoke(this.f850h, new Object[0])).booleanValue();
            } catch (ClassNotFoundException e) {
                sb = new StringBuilder();
                sb.append("[Advertising Id] A class from Google Play Services is not available ");
                message = e.getMessage();
                exc = e;
                sb.append(message);
                C0847a.m911c(sb.toString());
                C0847a.m905a(AdvertisingIdHandler.class.toString(), "Advertising Id", exc);
                return false;
            } catch (IllegalAccessException e2) {
                sb = new StringBuilder();
                sb.append("[Advertising Id] Illegal access exception ");
                message = e2.getMessage();
                exc = e2;
                sb.append(message);
                C0847a.m911c(sb.toString());
                C0847a.m905a(AdvertisingIdHandler.class.toString(), "Advertising Id", exc);
                return false;
            } catch (NoSuchMethodException e3) {
                sb = new StringBuilder();
                sb.append("[Advertising Id] A method from Google Play Services is not available ");
                message = e3.getMessage();
                exc = e3;
                sb.append(message);
                C0847a.m911c(sb.toString());
                C0847a.m905a(AdvertisingIdHandler.class.toString(), "Advertising Id", exc);
                return false;
            } catch (InvocationTargetException e4) {
                sb = new StringBuilder();
                sb.append("[Advertising Id] Failed to invoke method ");
                message = e4.getMessage();
                exc = e4;
                sb.append(message);
                C0847a.m911c(sb.toString());
                C0847a.m905a(AdvertisingIdHandler.class.toString(), "Advertising Id", exc);
                return false;
            }
        }
        return false;
    }
}
