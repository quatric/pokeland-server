package com.google.firebase.messaging;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.common.primitives.Ints;
import java.util.Arrays;
import java.util.Iterator;
import java.util.MissingFormatArgumentException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.GuardedBy;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzb {
    private static final AtomicInteger zzdt = new AtomicInteger((int) SystemClock.elapsedRealtime());
    private final Context zzag;
    private final String zzdu;

    @GuardedBy("this")
    private Bundle zzdv;

    public zzb(Context context, String str) {
        this.zzag = context;
        this.zzdu = str;
    }

    private final PendingIntent zza(int i, Intent intent) {
        return PendingIntent.getBroadcast(this.zzag, i, new Intent("com.google.firebase.MESSAGING_EVENT").setComponent(new ComponentName(this.zzag, "com.google.firebase.iid.FirebaseInstanceIdReceiver")).putExtra("wrapped_intent", intent), Ints.MAX_POWER_OF_TWO);
    }

    public static String zza(Bundle bundle, String str) {
        String string = bundle.getString(str);
        return string == null ? bundle.getString(str.replace("gcm.n.", "gcm.notification.")) : string;
    }

    private static void zza(Intent intent, Bundle bundle) {
        for (String str : bundle.keySet()) {
            if (str.startsWith("google.c.a.") || str.equals("from")) {
                intent.putExtra(str, bundle.getString(str));
            }
        }
    }

    private final synchronized Bundle zzar() {
        if (this.zzdv != null) {
            return this.zzdv;
        }
        try {
            ApplicationInfo applicationInfoZzc = zzc(128);
            if (applicationInfoZzc != null && applicationInfoZzc.metaData != null) {
                this.zzdv = applicationInfoZzc.metaData;
                return this.zzdv;
            }
        } catch (PackageManager.NameNotFoundException e) {
            String strValueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 35);
            sb.append("Couldn't get own application info: ");
            sb.append(strValueOf);
            Log.w("FirebaseMessaging", sb.toString());
        }
        return Bundle.EMPTY;
    }

    @TargetApi(MotionEventCompat.AXIS_SCROLL)
    private final boolean zzb(int i) {
        if (Build.VERSION.SDK_INT != 26) {
            return true;
        }
        try {
            if (!(this.zzag.getResources().getDrawable(i, null) instanceof AdaptiveIconDrawable)) {
                return true;
            }
            StringBuilder sb = new StringBuilder(77);
            sb.append("Adaptive icons cannot be used in notifications. Ignoring icon id: ");
            sb.append(i);
            Log.e("FirebaseMessaging", sb.toString());
            return false;
        } catch (Resources.NotFoundException unused) {
            StringBuilder sb2 = new StringBuilder(66);
            sb2.append("Couldn't find resource ");
            sb2.append(i);
            sb2.append(", treating it as an invalid icon");
            Log.e("FirebaseMessaging", sb2.toString());
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Object[] zzb(Bundle bundle, String str) {
        String strValueOf = String.valueOf(str);
        String strZza = zza(bundle, "_loc_args".length() != 0 ? strValueOf.concat("_loc_args") : new String(strValueOf));
        if (TextUtils.isEmpty(strZza)) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(strZza);
            String[] strArr = new String[jSONArray.length()];
            for (int i = 0; i < strArr.length; i++) {
                strArr[i] = jSONArray.opt(i);
            }
            return strArr;
        } catch (JSONException unused) {
            String strValueOf2 = String.valueOf(str);
            String strSubstring = ("_loc_args".length() != 0 ? strValueOf2.concat("_loc_args") : new String(strValueOf2)).substring(6);
            StringBuilder sb = new StringBuilder(String.valueOf(strSubstring).length() + 41 + String.valueOf(strZza).length());
            sb.append("Malformed ");
            sb.append(strSubstring);
            sb.append(": ");
            sb.append(strZza);
            sb.append("  Default value will be used.");
            Log.w("FirebaseMessaging", sb.toString());
            return null;
        }
    }

    private final ApplicationInfo zzc(int i) throws PackageManager.NameNotFoundException {
        return this.zzag.getPackageManager().getApplicationInfo(this.zzdu, i);
    }

    private final String zzc(Bundle bundle, String str) {
        String strZza = zza(bundle, str);
        return !TextUtils.isEmpty(strZza) ? strZza : zze(bundle, str);
    }

    public static String zzd(Bundle bundle, String str) {
        String strValueOf = String.valueOf(str);
        return zza(bundle, "_loc_key".length() != 0 ? strValueOf.concat("_loc_key") : new String(strValueOf));
    }

    private final String zze(Bundle bundle, String str) {
        String strZzd = zzd(bundle, str);
        if (TextUtils.isEmpty(strZzd)) {
            return null;
        }
        Resources resources = this.zzag.getResources();
        int identifier = resources.getIdentifier(strZzd, "string", this.zzdu);
        if (identifier == 0) {
            String strValueOf = String.valueOf(str);
            String strSubstring = ("_loc_key".length() != 0 ? strValueOf.concat("_loc_key") : new String(strValueOf)).substring(6);
            StringBuilder sb = new StringBuilder(String.valueOf(strSubstring).length() + 49 + String.valueOf(str).length());
            sb.append(strSubstring);
            sb.append(" resource not found: ");
            sb.append(str);
            sb.append(" Default value will be used.");
            Log.w("FirebaseMessaging", sb.toString());
            return null;
        }
        Object[] objArrZzb = zzb(bundle, str);
        if (objArrZzb == null) {
            return resources.getString(identifier);
        }
        try {
            return resources.getString(identifier, objArrZzb);
        } catch (MissingFormatArgumentException e) {
            String string = Arrays.toString(objArrZzb);
            StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 58 + String.valueOf(string).length());
            sb2.append("Missing format argument for ");
            sb2.append(str);
            sb2.append(": ");
            sb2.append(string);
            sb2.append(" Default value will be used.");
            Log.w("FirebaseMessaging", sb2.toString(), e);
            return null;
        }
    }

    @NonNull
    private final CharSequence zzg(Bundle bundle) {
        String strZzc = zzc(bundle, "gcm.n.title");
        if (!TextUtils.isEmpty(strZzc)) {
            return strZzc;
        }
        try {
            return zzc(0).loadLabel(this.zzag.getPackageManager());
        } catch (PackageManager.NameNotFoundException e) {
            String strValueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 35);
            sb.append("Couldn't get own application info: ");
            sb.append(strValueOf);
            Log.e("FirebaseMessaging", sb.toString());
            return "";
        }
    }

    public static boolean zzh(Bundle bundle) {
        return "1".equals(zza(bundle, "gcm.n.e")) || zza(bundle, "gcm.n.icon") != null;
    }

    public static String zzi(Bundle bundle) {
        String strZza = zza(bundle, "gcm.n.sound2");
        return TextUtils.isEmpty(strZza) ? zza(bundle, "gcm.n.sound") : strZza;
    }

    @Nullable
    static Uri zzj(@NonNull Bundle bundle) {
        String strZza = zza(bundle, "gcm.n.link_android");
        if (TextUtils.isEmpty(strZza)) {
            strZza = zza(bundle, "gcm.n.link");
        }
        if (TextUtils.isEmpty(strZza)) {
            return null;
        }
        return Uri.parse(strZza);
    }

    private static boolean zzk(Bundle bundle) {
        return bundle != null && "1".equals(bundle.getString("google.c.a.e"));
    }

    private final int zzl(String str) {
        if (!TextUtils.isEmpty(str)) {
            Resources resources = this.zzag.getResources();
            int identifier = resources.getIdentifier(str, "drawable", this.zzdu);
            if (identifier != 0 && zzb(identifier)) {
                return identifier;
            }
            int identifier2 = resources.getIdentifier(str, "mipmap", this.zzdu);
            if (identifier2 != 0 && zzb(identifier2)) {
                return identifier2;
            }
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 61);
            sb.append("Icon resource ");
            sb.append(str);
            sb.append(" not found. Notification will use default icon.");
            Log.w("FirebaseMessaging", sb.toString());
        }
        int i = zzar().getInt("com.google.firebase.messaging.default_notification_icon", 0);
        if (i == 0 || !zzb(i)) {
            try {
                i = zzc(0).icon;
            } catch (PackageManager.NameNotFoundException e) {
                String strValueOf = String.valueOf(e);
                StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf).length() + 35);
                sb2.append("Couldn't get own application info: ");
                sb2.append(strValueOf);
                Log.w("FirebaseMessaging", sb2.toString());
            }
        }
        return (i == 0 || !zzb(i)) ? android.R.drawable.sym_def_app_icon : i;
    }

    private final Integer zzm(String str) {
        if (Build.VERSION.SDK_INT < 21) {
            return null;
        }
        if (!TextUtils.isEmpty(str)) {
            try {
                return Integer.valueOf(Color.parseColor(str));
            } catch (IllegalArgumentException unused) {
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 54);
                sb.append("Color ");
                sb.append(str);
                sb.append(" not valid. Notification will use default color.");
                Log.w("FirebaseMessaging", sb.toString());
            }
        }
        int i = zzar().getInt("com.google.firebase.messaging.default_notification_color", 0);
        if (i != 0) {
            try {
                return Integer.valueOf(ContextCompat.getColor(this.zzag, i));
            } catch (Resources.NotFoundException unused2) {
                Log.w("FirebaseMessaging", "Cannot find the color resource referenced in AndroidManifest.");
            }
        }
        return null;
    }

    @TargetApi(MotionEventCompat.AXIS_SCROLL)
    private final String zzn(String str) {
        if (!PlatformVersion.isAtLeastO()) {
            return null;
        }
        int i = 0;
        try {
            i = zzc(0).targetSdkVersion;
        } catch (PackageManager.NameNotFoundException unused) {
        }
        if (i < 26) {
            return null;
        }
        NotificationManager notificationManager = (NotificationManager) this.zzag.getSystemService(NotificationManager.class);
        if (!TextUtils.isEmpty(str)) {
            if (notificationManager.getNotificationChannel(str) != null) {
                return str;
            }
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 122);
            sb.append("Notification Channel requested (");
            sb.append(str);
            sb.append(") has not been created by the app. Manifest configuration, or default, value will be used.");
            Log.w("FirebaseMessaging", sb.toString());
        }
        String string = zzar().getString("com.google.firebase.messaging.default_notification_channel_id");
        if (TextUtils.isEmpty(string)) {
            Log.w("FirebaseMessaging", "Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.");
        } else {
            if (notificationManager.getNotificationChannel(string) != null) {
                return string;
            }
            Log.w("FirebaseMessaging", "Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.");
        }
        if (notificationManager.getNotificationChannel("fcm_fallback_notification_channel") == null) {
            notificationManager.createNotificationChannel(new NotificationChannel("fcm_fallback_notification_channel", this.zzag.getString(this.zzag.getResources().getIdentifier("fcm_fallback_notification_channel_label", "string", this.zzdu)), 3));
        }
        return "fcm_fallback_notification_channel";
    }

    public final zza zzf(Bundle bundle) {
        Uri defaultUri;
        Intent launchIntentForPackage;
        PendingIntent activity;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.zzag, zzn(zza(bundle, "gcm.n.android_channel_id")));
        builder.setAutoCancel(true);
        builder.setContentTitle(zzg(bundle));
        String strZzc = zzc(bundle, "gcm.n.body");
        if (!TextUtils.isEmpty(strZzc)) {
            builder.setContentText(strZzc);
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(strZzc));
        }
        builder.setSmallIcon(zzl(zza(bundle, "gcm.n.icon")));
        String strZzi = zzi(bundle);
        PendingIntent pendingIntentZza = null;
        if (TextUtils.isEmpty(strZzi)) {
            defaultUri = null;
        } else if ("default".equals(strZzi) || this.zzag.getResources().getIdentifier(strZzi, "raw", this.zzdu) == 0) {
            defaultUri = RingtoneManager.getDefaultUri(2);
        } else {
            String str = this.zzdu;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 24 + String.valueOf(strZzi).length());
            sb.append("android.resource://");
            sb.append(str);
            sb.append("/raw/");
            sb.append(strZzi);
            defaultUri = Uri.parse(sb.toString());
        }
        if (defaultUri != null) {
            builder.setSound(defaultUri);
        }
        String strZza = zza(bundle, "gcm.n.click_action");
        if (TextUtils.isEmpty(strZza)) {
            Uri uriZzj = zzj(bundle);
            if (uriZzj != null) {
                launchIntentForPackage = new Intent("android.intent.action.VIEW");
                launchIntentForPackage.setPackage(this.zzdu);
                launchIntentForPackage.setData(uriZzj);
            } else {
                launchIntentForPackage = this.zzag.getPackageManager().getLaunchIntentForPackage(this.zzdu);
                if (launchIntentForPackage == null) {
                    Log.w("FirebaseMessaging", "No activity found to launch app");
                }
            }
        } else {
            launchIntentForPackage = new Intent(strZza);
            launchIntentForPackage.setPackage(this.zzdu);
            launchIntentForPackage.setFlags(268435456);
        }
        if (launchIntentForPackage == null) {
            activity = null;
        } else {
            launchIntentForPackage.addFlags(67108864);
            Bundle bundle2 = new Bundle(bundle);
            Iterator<String> it = bundle2.keySet().iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (next != null && next.startsWith("google.c.")) {
                    it.remove();
                }
            }
            launchIntentForPackage.putExtras(bundle2);
            for (String str2 : bundle2.keySet()) {
                if (str2.startsWith("gcm.n.") || str2.startsWith("gcm.notification.")) {
                    launchIntentForPackage.removeExtra(str2);
                }
            }
            activity = PendingIntent.getActivity(this.zzag, zzdt.incrementAndGet(), launchIntentForPackage, Ints.MAX_POWER_OF_TWO);
            if (zzk(bundle)) {
                Intent intent = new Intent("com.google.firebase.messaging.NOTIFICATION_OPEN");
                zza(intent, bundle);
                intent.putExtra("pending_intent", activity);
                activity = zza(zzdt.incrementAndGet(), intent);
            }
        }
        builder.setContentIntent(activity);
        if (zzk(bundle)) {
            Intent intent2 = new Intent("com.google.firebase.messaging.NOTIFICATION_DISMISS");
            zza(intent2, bundle);
            pendingIntentZza = zza(zzdt.incrementAndGet(), intent2);
        }
        if (pendingIntentZza != null) {
            builder.setDeleteIntent(pendingIntentZza);
        }
        Integer numZzm = zzm(zza(bundle, "gcm.n.color"));
        if (numZzm != null) {
            builder.setColor(numZzm.intValue());
        }
        String strZza2 = zza(bundle, "gcm.n.tag");
        if (TextUtils.isEmpty(strZza2)) {
            long jUptimeMillis = SystemClock.uptimeMillis();
            StringBuilder sb2 = new StringBuilder(37);
            sb2.append("FCM-Notification:");
            sb2.append(jUptimeMillis);
            strZza2 = sb2.toString();
        }
        return new zza(builder, strZza2, 0);
    }
}
