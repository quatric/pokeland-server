package com.metaps.common;

import android.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.text.Html;
import com.metaps.analytics.ReadReceiver;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.common.j */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0856j {

    /* JADX INFO: renamed from: a */
    public static final String f955a = "title";

    /* JADX INFO: renamed from: b */
    public static final String f956b = "body";

    /* JADX INFO: renamed from: c */
    public static final String f957c = "com.metaps.analytics";

    /* JADX INFO: renamed from: d */
    public static final String f958d = "com.metaps.analytics.READ_NOTIFICATION";

    /* JADX INFO: renamed from: e */
    public static final String f959e = "metaps.default";

    /* JADX INFO: renamed from: f */
    private static final String f960f = "com.google.android.gms.version";

    /* JADX INFO: renamed from: g */
    private static final String f961g = "com.google.firebase.iid.FirebaseInstanceId";

    /* JADX INFO: renamed from: h */
    private static final String f962h = "getInstance";

    /* JADX INFO: renamed from: i */
    private static final String f963i = "getToken";

    /* JADX INFO: renamed from: j */
    private static C0856j f964j;

    /* JADX INFO: renamed from: k */
    private static String f965k;

    /* JADX INFO: renamed from: l */
    private static String f966l;

    private C0856j(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null && applicationInfo.metaData != null && applicationInfo.metaData.get(f960f) == null) {
                C0847a.m908b("[Registration Token] No meta found for Google Play Services");
                return;
            }
            m1001b(context);
            if (Build.VERSION.SDK_INT >= 26) {
                m1006c(context);
            }
        } catch (PackageManager.NameNotFoundException e) {
            C0847a.m909b(C0856j.class.toString(), "Failed to search for meta-data " + e.getMessage());
        }
    }

    /* JADX INFO: renamed from: a */
    private Notification m991a(Context context, PendingIntent pendingIntent, Intent intent, C0857k c0857k) {
        return Build.VERSION.SDK_INT >= 11 ? m997b(context, pendingIntent, intent, c0857k) : m1003c(context, pendingIntent, intent, c0857k);
    }

    /* JADX INFO: renamed from: a */
    public static C0856j m992a(Context context) {
        if (f964j == null) {
            f964j = new C0856j(context);
        }
        return f964j;
    }

    /* JADX INFO: renamed from: a */
    public static C0857k m993a(Intent intent) {
        String stringExtra = intent.getStringExtra(f957c);
        if (stringExtra == null) {
            return null;
        }
        try {
            return new C0857k(new JSONObject(stringExtra));
        } catch (JSONException unused) {
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m994a(String str) {
        if (str != null) {
            f966l = str;
        }
    }

    @TargetApi(MotionEventCompat.AXIS_SCROLL)
    /* JADX INFO: renamed from: a */
    public static boolean m995a(Context context, String str) {
        if (str == null) {
            return false;
        }
        try {
            ((NotificationManager) context.getSystemService("notification")).deleteNotificationChannel(str);
            C0847a.m908b("Delete notification channel. channelId: " + str);
            return true;
        } catch (NullPointerException e) {
            C0847a.m911c("Delete notification channel failed : " + e.getMessage());
            return false;
        }
    }

    @TargetApi(MotionEventCompat.AXIS_SCROLL)
    /* JADX INFO: renamed from: a */
    public static boolean m996a(Context context, String str, String str2) {
        if (str != null && str2 != null) {
            if ("default".equals(str.toLowerCase())) {
                str = f959e;
            }
            try {
                ((NotificationManager) context.getSystemService("notification")).createNotificationChannel(new NotificationChannel(str, str2, 3));
                C0847a.m908b("Create or update notification channel. channelId: " + str);
                return true;
            } catch (NullPointerException e) {
                C0847a.m911c("Create or update notification channel failed : " + e.getMessage());
            }
        }
        return false;
    }

    @TargetApi(11)
    /* JADX INFO: renamed from: b */
    private Notification m997b(Context context, PendingIntent pendingIntent, Intent intent, C0857k c0857k) {
        Resources resources = context.getResources();
        Notification.Builder contentIntent = new Notification.Builder(context).setTicker(intent.getStringExtra(f955a)).setWhen(System.currentTimeMillis()).setContentTitle(intent.getStringExtra(f955a)).setContentText(Html.fromHtml(m998b(intent))).setContentIntent(pendingIntent);
        Bitmap bitmapM1004c = c0857k.m1014d() != null ? m1004c(c0857k.m1014d()) : null;
        int iM1007d = m1007d(context);
        int identifier = resources.getIdentifier("metaps_analytics_notification_small_icon", "drawable", context.getPackageName());
        if (bitmapM1004c != null) {
            contentIntent.setLargeIcon(bitmapM1004c);
        } else if (identifier != 0) {
            bitmapM1004c = BitmapFactory.decodeResource(resources, iM1007d);
            contentIntent.setLargeIcon(bitmapM1004c);
        }
        if (identifier != 0) {
            C0847a.m908b("Use metaps_analytics_notification_small_icon for notification small icon.");
            contentIntent.setSmallIcon(identifier);
        } else {
            contentIntent.setSmallIcon(iM1007d);
        }
        int identifier2 = resources.getIdentifier("metaps_analytics_notification_background_color", "color", context.getPackageName());
        if (identifier2 != 0 && Build.VERSION.SDK_INT >= 21) {
            C0847a.m908b("Use metaps_analytics_notification_background_color for notification background color.");
            contentIntent.setColor(resources.getColor(identifier2));
        }
        if (Build.VERSION.SDK_INT >= 26) {
            contentIntent.setChannelId((c0857k.m1015e() == null || !m1002b(context, c0857k.m1015e())) ? f959e : c0857k.m1015e());
        }
        Notification notification = contentIntent.getNotification();
        notification.flags |= 16;
        if (c0857k.m1013c()) {
            notification.defaults |= 1;
        }
        return notification;
    }

    /* JADX INFO: renamed from: b */
    private String m998b(Intent intent) {
        String stringExtra = intent.getStringExtra(f956b);
        if (stringExtra == null) {
            return "";
        }
        try {
            return URLDecoder.decode(stringExtra, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return stringExtra;
        } catch (IllegalArgumentException e) {
            C0847a.m903a(getClass().toString(), e.getMessage());
            return stringExtra;
        }
    }

    /* JADX INFO: renamed from: b */
    public static void m1000b() {
        f966l = null;
    }

    /* JADX INFO: renamed from: b */
    private void m1001b(Context context) {
        new Thread() { // from class: com.metaps.common.j.1
            /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                String string;
                String str;
                Exception exc;
                String string2;
                String str2;
                synchronized (C0856j.this) {
                    try {
                        try {
                            try {
                                Class<?> cls = Class.forName(C0856j.f961g);
                                String unused = C0856j.f965k = (String) cls.getMethod(C0856j.f963i, new Class[0]).invoke(cls.getMethod(C0856j.f962h, new Class[0]).invoke(null, new Object[0]), new Object[0]);
                                if (C0856j.f965k == null || C0856j.f965k.length() <= 0) {
                                    C0847a.m908b("Token is not available yet");
                                    string2 = C0856j.class.toString();
                                    str2 = "Token is not available yet";
                                } else {
                                    C0847a.m912c("Registration Token", C0856j.f965k);
                                    string2 = C0856j.class.toString();
                                    str2 = "Registration Token " + C0856j.f965k;
                                }
                                C0847a.m903a(string2, str2);
                            } catch (ClassNotFoundException e) {
                                C0847a.m911c("[Registration Token] A class from Firebase is not available " + e.getMessage());
                                string = C0856j.class.toString();
                                str = "Registration Token";
                                exc = e;
                                C0847a.m905a(string, str, exc);
                            }
                        } catch (IllegalAccessException e2) {
                            C0847a.m911c("[Registration Token] Illegal access exception " + e2.getMessage());
                            string = C0856j.class.toString();
                            str = "Registration Token";
                            exc = e2;
                            C0847a.m905a(string, str, exc);
                        }
                    } catch (NoSuchMethodException e3) {
                        C0847a.m911c("[Registration Token] A method from Firebase is not available " + e3.getMessage());
                        string = C0856j.class.toString();
                        str = "Registration Token";
                        exc = e3;
                        C0847a.m905a(string, str, exc);
                    } catch (InvocationTargetException e4) {
                        String str3 = "not exception";
                        if (e4.getCause() != null) {
                            str3 = e4.getCause().getClass().toString() + " " + e4.getCause().getMessage();
                        }
                        C0847a.m911c("[Registration Token] Failed to invoke method " + str3);
                        string = C0856j.class.toString();
                        str = "Registration Token";
                        exc = e4;
                        C0847a.m905a(string, str, exc);
                    }
                }
            }
        }.start();
    }

    @TargetApi(MotionEventCompat.AXIS_SCROLL)
    /* JADX INFO: renamed from: b */
    private boolean m1002b(Context context, String str) {
        return ((NotificationManager) context.getSystemService("notification")).getNotificationChannel(str) != null;
    }

    @SuppressLint({"deprecation"})
    /* JADX INFO: renamed from: c */
    private Notification m1003c(Context context, PendingIntent pendingIntent, Intent intent, C0857k c0857k) {
        StringBuilder sb;
        String message;
        String strM998b = m998b(intent);
        try {
            Class<?> cls = Class.forName("android.support.v4.app.NotificationCompat$Builder");
            Notification notification = (Notification) cls.getMethod("build", new Class[0]).invoke(cls.getMethod("setContentText", CharSequence.class).invoke(cls.getMethod("setContentTitle", CharSequence.class).invoke(cls.getMethod("setAutoCancel", Boolean.TYPE).invoke(cls.getMethod("setWhen", Long.TYPE).invoke(cls.getMethod("setTicker", CharSequence.class).invoke(cls.getMethod("setSmallIcon", Integer.TYPE).invoke(cls.getMethod("setContentIntent", PendingIntent.class).invoke(cls.getConstructor(Context.class).newInstance(context), pendingIntent), Integer.valueOf(m1007d(context))), intent.getStringExtra(f955a)), Long.valueOf(System.currentTimeMillis())), true), intent.getStringExtra(f955a)), Html.fromHtml(strM998b)), new Object[0]);
            if (c0857k.m1013c()) {
                notification.defaults |= 1;
            }
            return notification;
        } catch (ClassNotFoundException e) {
            sb = new StringBuilder();
            sb.append("A class from support library is not available: ");
            message = e.getMessage();
            sb.append(message);
            C0847a.m911c(sb.toString());
            return null;
        } catch (IllegalAccessException e2) {
            sb = new StringBuilder();
            sb.append("Illegal access to a method from support library: ");
            message = e2.getMessage();
            sb.append(message);
            C0847a.m911c(sb.toString());
            return null;
        } catch (InstantiationException e3) {
            sb = new StringBuilder();
            sb.append("Illegal access to a constructor from support library: ");
            message = e3.getMessage();
            sb.append(message);
            C0847a.m911c(sb.toString());
            return null;
        } catch (NoSuchMethodException e4) {
            sb = new StringBuilder();
            sb.append("A method from support library is not available: ");
            message = e4.getMessage();
            sb.append(message);
            C0847a.m911c(sb.toString());
            return null;
        } catch (InvocationTargetException e5) {
            sb = new StringBuilder();
            sb.append("Invocation error to a method from support library: ");
            message = e5.getMessage();
            sb.append(message);
            C0847a.m911c(sb.toString());
            return null;
        }
    }

    /* JADX INFO: renamed from: c */
    private static Bitmap m1004c(String str) {
        try {
            URLConnection uRLConnectionOpenConnection = new URL(str).openConnection();
            uRLConnectionOpenConnection.setDoInput(true);
            return BitmapFactory.decodeStream(new BufferedInputStream(uRLConnectionOpenConnection.getInputStream()));
        } catch (IOException unused) {
            C0847a.m911c("Failed to convert image url to Bitmap.");
            return null;
        }
    }

    /* JADX INFO: renamed from: c */
    public static String m1005c() {
        C0847a.m902a("You call Analytics.getPushNotificationCustomText()");
        return f966l;
    }

    @SuppressLint({"WrongConstant"})
    @TargetApi(MotionEventCompat.AXIS_SCROLL)
    /* JADX INFO: renamed from: c */
    private void m1006c(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (notificationManager.getNotificationChannel(f959e) == null) {
            notificationManager.createNotificationChannel(new NotificationChannel(f959e, "Default", 3));
            C0847a.m908b("Create default notification channel. channelId: metaps.default");
        }
    }

    /* JADX INFO: renamed from: d */
    private int m1007d(Context context) {
        int identifier = context.getResources().getIdentifier("metaps_analytics_notification_large_icon", "drawable", context.getPackageName());
        if (identifier != 0) {
            C0847a.m908b("Use metaps_analytics_notification_large_icon for notification fundamental icon.");
            return identifier;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            if (applicationInfo != null) {
                identifier = applicationInfo.icon;
            }
            return identifier != 0 ? identifier : R.drawable.sym_def_app_icon;
        } catch (PackageManager.NameNotFoundException unused) {
            C0847a.m911c("Failed to get app icon for notification icon.");
            return R.drawable.sym_def_app_icon;
        }
    }

    /* JADX INFO: renamed from: a */
    public String m1009a() {
        return f965k;
    }

    /* JADX INFO: renamed from: a */
    public void m1010a(Context context, Intent intent) {
        C0857k c0857kM993a = m993a(intent);
        if (c0857kM993a == null || !c0857kM993a.m1012b()) {
            return;
        }
        Intent intent2 = new Intent(context, (Class<?>) ReadReceiver.class);
        intent2.setAction(f958d);
        intent2.putExtra(f957c, c0857kM993a.m1017g());
        Notification notificationM991a = m991a(context, PendingIntent.getBroadcast(context, 0, intent2, 134217728), intent, c0857kM993a);
        if (notificationM991a == null) {
            return;
        }
        ((NotificationManager) context.getSystemService("notification")).notify(0, notificationM991a);
    }
}
