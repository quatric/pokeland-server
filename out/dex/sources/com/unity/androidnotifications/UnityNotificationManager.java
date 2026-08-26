package com.unity.androidnotifications;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.service.notification.StatusBarNotification;
import android.support.annotation.Keep;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import com.metaps.common.C0856j;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Keep
public class UnityNotificationManager extends BroadcastReceiver {
    public static final String DEFAULT_APP_ICON = "app_icon";
    public static final String SHARED_PREFS_NOTIFICATION_IDS = "UNITY_NOTIFICATION_IDS";
    public static final String UNITY_NOTIFICATION_SETTINGS = "UNITY_NOTIFICATIONS";
    public static UnityNotificationManager mManager;
    private static NotificationCallback mNotificationCallback;
    public Activity mActivity;
    public Context mContext;
    public Class mOpenActivity;
    public boolean reschedule_on_restart;

    public UnityNotificationManager() {
        this.mContext = null;
        this.mActivity = null;
        this.mOpenActivity = null;
        this.reschedule_on_restart = false;
    }

    public UnityNotificationManager(Context context, Activity activity) {
        this.mContext = null;
        this.mActivity = null;
        this.mOpenActivity = null;
        this.reschedule_on_restart = false;
        this.mContext = context;
        this.mActivity = activity;
        try {
            Boolean boolValueOf = Boolean.valueOf(activity.getPackageManager().getApplicationInfo(activity.getPackageName(), 128).metaData.getBoolean("reschedule_notifications_on_restart"));
            if (boolValueOf.booleanValue()) {
                context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, (Class<?>) UnityNotificationRestartOnBootReceiver.class), 1, 1);
            }
            this.reschedule_on_restart = boolValueOf.booleanValue();
            this.mOpenActivity = GetOpenAppActivity(context, false);
            if (this.mOpenActivity == null) {
                this.mOpenActivity = activity.getClass();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("UnityNotifications", "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e2) {
            Log.e("UnityNotifications", "Failed to load meta-data, NullPointer: " + e2.getMessage());
        }
    }

    public static Intent CreateNotificationIntent(Activity activity) {
        return new Intent(activity, (Class<?>) UnityNotificationManager.class);
    }

    public static Intent DeserializeNotificationIntent(String str, Context context) {
        byte[] bArrDecode = Base64.decode(str, 0);
        Bundle bundle = new Bundle();
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.unmarshall(bArrDecode, 0, bArrDecode.length);
        parcelObtain.setDataPosition(0);
        bundle.readFromParcel(parcelObtain);
        Intent intent = new Intent(context, (Class<?>) UnityNotificationManager.class);
        intent.putExtras(bundle);
        return intent;
    }

    public static Class<?> GetOpenAppActivity(Context context, Boolean bool) {
        ApplicationInfo applicationInfo;
        Class<?> cls = null;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            applicationInfo = null;
        }
        Bundle bundle = applicationInfo.metaData;
        if (bundle.containsKey("custom_notification_android_activity")) {
            try {
                cls = Class.forName(bundle.getString("custom_notification_android_activity"));
            } catch (ClassNotFoundException unused) {
            }
        }
        if (cls == null && bool.booleanValue()) {
            try {
                return Class.forName("com.unity3d.player.UnityPlayerActivity");
            } catch (ClassNotFoundException unused2) {
            }
        }
        return cls;
    }

    public static List<Intent> LoadNotificationIntents(Context context) {
        HashSet<String> hashSet = new HashSet(context.getSharedPreferences("UNITY_STORED_NOTIFICATION_IDS", 0).getStringSet(SHARED_PREFS_NOTIFICATION_IDS, new HashSet()));
        ArrayList arrayList = new ArrayList();
        HashSet hashSet2 = new HashSet();
        for (String str : hashSet) {
            String string = context.getSharedPreferences(String.format("u_notification_data_%s", str), 0).getString("data", "");
            if (string.length() > 1) {
                arrayList.add(DeserializeNotificationIntent(string, context));
            } else {
                hashSet2.add(str);
            }
        }
        Iterator it = hashSet2.iterator();
        while (it.hasNext()) {
            deleteExpiredNotificationIntent((String) it.next(), context);
        }
        return arrayList;
    }

    public static void SaveNotificationIntent(Intent intent, Context context) {
        String string = Integer.toString(intent.getIntExtra("id", 0));
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(String.format("u_notification_data_%s", string), 0).edit();
        editorEdit.clear();
        editorEdit.putString("data", SerializeNotificationIntent(intent));
        editorEdit.commit();
        SharedPreferences sharedPreferences = context.getSharedPreferences("UNITY_STORED_NOTIFICATION_IDS", 0);
        HashSet hashSet = new HashSet(sharedPreferences.getStringSet(SHARED_PREFS_NOTIFICATION_IDS, new HashSet()));
        hashSet.add(string);
        SharedPreferences.Editor editorEdit2 = sharedPreferences.edit();
        editorEdit2.clear();
        editorEdit2.putStringSet(SHARED_PREFS_NOTIFICATION_IDS, hashSet);
        editorEdit2.commit();
        LoadNotificationIntents(context);
    }

    public static String SerializeNotificationIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        Parcel parcelObtain = Parcel.obtain();
        extras.writeToParcel(parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        return Base64.encodeToString(bArrMarshall, 0, bArrMarshall.length, 0);
    }

    protected static Notification.Builder buildNotification(Intent intent, Context context) {
        String stringExtra = intent.getStringExtra("channelID");
        String stringExtra2 = intent.getStringExtra("textTitle");
        String stringExtra3 = intent.getStringExtra("textContent");
        int intExtra = intent.getIntExtra("smallIcon", 0);
        boolean booleanExtra = intent.getBooleanExtra("autoCancel", true);
        long longExtra = intent.getLongExtra("fireTime", -1L);
        boolean booleanExtra2 = intent.getBooleanExtra("usesChronometer", false);
        int intExtra2 = intent.getIntExtra("largeIcon", 0);
        intent.getIntExtra("lockscreenVisibility", 0);
        int intExtra3 = intent.getIntExtra("style", 0);
        int intExtra4 = intent.getIntExtra("color", 0);
        int intExtra5 = intent.getIntExtra("number", 0);
        if (intExtra == 0) {
            intExtra = C1080R.drawable.default_icon;
        }
        PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("tapIntent");
        Notification.Builder builder = Build.VERSION.SDK_INT < 26 ? new Notification.Builder(context) : new Notification.Builder(context, stringExtra);
        if (intExtra2 != 0) {
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), intExtra2));
        }
        builder.setContentTitle(stringExtra2).setContentText(stringExtra3).setSmallIcon(intExtra).setContentIntent(pendingIntent).setAutoCancel(booleanExtra);
        if (Build.VERSION.SDK_INT >= 21 && intExtra4 != 0) {
            builder.setColor(intExtra4);
            if (Build.VERSION.SDK_INT >= 26) {
                builder.setColorized(true);
            }
        }
        if (intExtra5 >= 0) {
            builder.setNumber(intExtra5);
        }
        int i = 2;
        if (intExtra3 == 2) {
            builder.setStyle(new Notification.BigTextStyle().bigText(stringExtra3));
        }
        if (Build.VERSION.SDK_INT >= 22) {
            builder.setWhen(longExtra);
            builder.setUsesChronometer(booleanExtra2);
        }
        if (Build.VERSION.SDK_INT < 26) {
            NotificationChannelWrapper notificationChannel = getNotificationChannel(stringExtra, context);
            if (notificationChannel.vibrationPattern == null || notificationChannel.vibrationPattern.length <= 0) {
                builder.setDefaults(-1);
            } else {
                builder.setDefaults(5);
                builder.setVibrate(notificationChannel.vibrationPattern);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                builder.setVisibility(notificationChannel.lockscreenVisibility);
            }
            int i2 = notificationChannel.importance;
            if (i2 == 0) {
                i = -2;
            } else if (i2 == 2) {
                i = -1;
            } else if (i2 == 3 || i2 != 4) {
                i = 0;
            }
            builder.setPriority(i);
        }
        return builder;
    }

    public static Intent buildOpenAppIntent(Intent intent, Context context, Class cls) {
        Intent intent2 = new Intent(context, (Class<?>) cls);
        intent2.addFlags(805306368);
        intent2.putExtras(intent);
        return intent2;
    }

    private static void cancelPendingNotificationIntentInternal(int i, Context context) {
        PendingIntent broadcast = PendingIntent.getBroadcast(context, i, new Intent(context, (Class<?>) UnityNotificationManager.class), 536870912);
        if (broadcast != null) {
            if (context != null) {
                ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(broadcast);
            }
            broadcast.cancel();
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(UNITY_NOTIFICATION_SETTINGS, 0);
        HashSet hashSet = new HashSet(sharedPreferences.getStringSet(SHARED_PREFS_NOTIFICATION_IDS, new HashSet()));
        if (hashSet.contains(Integer.toString(i))) {
            hashSet.remove(Integer.toString(i));
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.clear();
            editorEdit.putStringSet(SHARED_PREFS_NOTIFICATION_IDS, hashSet);
            editorEdit.commit();
        }
    }

    public static void deleteExpiredNotificationIntent(int i, Context context) {
        deleteExpiredNotificationIntent(Integer.toString(i), context);
    }

    public static void deleteExpiredNotificationIntent(String str, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UNITY_STORED_NOTIFICATION_IDS", 0);
        Set<String> stringSet = sharedPreferences.getStringSet(SHARED_PREFS_NOTIFICATION_IDS, new HashSet());
        cancelPendingNotificationIntentInternal(Integer.valueOf(str).intValue(), context);
        HashSet hashSet = new HashSet(stringSet);
        hashSet.remove(str);
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putStringSet(SHARED_PREFS_NOTIFICATION_IDS, hashSet);
        editorEdit.commit();
        context.getSharedPreferences(String.format("u_notification_data_%s", str), 0).edit().clear().commit();
    }

    public static int findResourceidInContextByName(String str, Context context, Activity activity) {
        int identifier = 0;
        if (str == null) {
            return 0;
        }
        Resources resources = context.getResources();
        return (resources == null || (identifier = resources.getIdentifier(str, "mipmap", activity.getPackageName())) != 0) ? identifier : resources.getIdentifier(str, "drawable", activity.getPackageName());
    }

    public static NotificationChannelWrapper getNotificationChannel(String str, Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            return UnityNotificationManagerOreo.getOreoNotificationChannel(str, context);
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(String.format("unity_notification_channel_%s", str), 0);
        NotificationChannelWrapper notificationChannelWrapper = new NotificationChannelWrapper();
        notificationChannelWrapper.f1836id = str;
        notificationChannelWrapper.name = sharedPreferences.getString(C0856j.f955a, "undefined");
        notificationChannelWrapper.importance = sharedPreferences.getInt("importance", 3);
        notificationChannelWrapper.description = sharedPreferences.getString("description", "undefined");
        notificationChannelWrapper.enableLights = sharedPreferences.getBoolean("enableLights", false);
        notificationChannelWrapper.enableVibration = sharedPreferences.getBoolean("enableVibration", false);
        notificationChannelWrapper.canBypassDnd = sharedPreferences.getBoolean("canBypassDnd", false);
        notificationChannelWrapper.canShowBadge = sharedPreferences.getBoolean("canShowBadge", false);
        notificationChannelWrapper.lockscreenVisibility = sharedPreferences.getInt("lockscreenVisibility", 1);
        String[] strArrSplit = sharedPreferences.getString("vibrationPattern", "[]").split(",");
        long[] jArr = new long[strArrSplit.length];
        if (jArr.length > 1) {
            for (int i = 0; i < strArrSplit.length; i++) {
                try {
                    jArr[i] = Long.parseLong(strArrSplit[i]);
                } catch (NumberFormatException unused) {
                    jArr[i] = 1;
                }
            }
        }
        if (jArr.length <= 1) {
            jArr = null;
        }
        notificationChannelWrapper.vibrationPattern = jArr;
        return notificationChannelWrapper;
    }

    public static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService("notification");
    }

    public static UnityNotificationManager getNotificationManagerImpl(Context context) {
        UnityNotificationManager unityNotificationManager = mManager;
        if (unityNotificationManager != null) {
            return unityNotificationManager;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            mManager = new UnityNotificationManagerOreo(context, (Activity) context);
        } else if (Build.VERSION.SDK_INT >= 24) {
            mManager = new UnityNotificationManagerNougat(context, (Activity) context);
        } else {
            mManager = new UnityNotificationManager(context, (Activity) context);
        }
        return mManager;
    }

    public static UnityNotificationManager getNotificationManagerImpl(Context context, Activity activity) {
        UnityNotificationManager unityNotificationManager = mManager;
        if (unityNotificationManager != null) {
            return unityNotificationManager;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            mManager = new UnityNotificationManagerOreo(context, activity);
        } else if (Build.VERSION.SDK_INT >= 24) {
            mManager = new UnityNotificationManagerNougat(context, activity);
        } else {
            mManager = new UnityNotificationManager(context, activity);
        }
        return mManager;
    }

    protected static void notify(Context context, int i, Notification.Builder builder, Intent intent) {
        getNotificationManager(context).notify(i, builder.build());
        try {
            mNotificationCallback.onSentNotification(intent);
        } catch (RuntimeException unused) {
            Log.w("UnityNotifications", "Can not invoke OnNotificationReceived event when the app is not running!");
        }
        deleteExpiredNotificationIntent(i, context);
    }

    public static Intent prepareNotificationIntent(Intent intent, Context context, PendingIntent pendingIntent) {
        Intent intent2 = (Intent) intent.clone();
        int intExtra = intent2.getIntExtra("id", 0);
        SharedPreferences sharedPreferences = context.getSharedPreferences(UNITY_NOTIFICATION_SETTINGS, 0);
        HashSet<String> hashSet = new HashSet(sharedPreferences.getStringSet(SHARED_PREFS_NOTIFICATION_IDS, new HashSet()));
        HashSet hashSet2 = new HashSet();
        intent2.putExtra("tapIntent", pendingIntent);
        for (String str : hashSet) {
            if (PendingIntent.getBroadcast(context, Integer.valueOf(str).intValue(), intent, 536870912) != null) {
                hashSet2.add(str);
            }
        }
        if (!Build.MANUFACTURER.equals("samsung") || hashSet2.size() < 499) {
            hashSet2.add(Integer.toString(intExtra));
            intent2.setFlags(268468224);
        } else {
            Log.w("UnityNotifications", "Attempting to schedule more than 500 notifications. There is a limit of 500 concurrently scheduled Alarms on Samsung devices either wait for the currently scheduled ones to be triggered or cancel them if you wish to schedule additional notifications.");
            intent2 = null;
        }
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.clear();
        editorEdit.putStringSet(SHARED_PREFS_NOTIFICATION_IDS, hashSet2);
        editorEdit.apply();
        return intent2;
    }

    private PendingIntent retrieveScheduledNotification(int i) {
        return PendingIntent.getService(this.mContext, i, CreateNotificationIntent(this.mActivity), 0);
    }

    public static void scheduleNotificationIntentAlarm(Intent intent, Context context, PendingIntent pendingIntent) {
        long longExtra = intent.getLongExtra("repeatInterval", 0L);
        long longExtra2 = intent.getLongExtra("fireTime", 0L);
        intent.getIntExtra("id", 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        new Date(longExtra2);
        Calendar.getInstance().getTime();
        if (longExtra > 0) {
            alarmManager.setInexactRepeating(0, longExtra2, longExtra, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(0, longExtra2, pendingIntent);
        } else {
            alarmManager.set(0, longExtra2, pendingIntent);
        }
    }

    public static void sendNotification(Intent intent, Context context) {
        notify(context, intent.getIntExtra("id", -1), buildNotification(intent, context), intent);
    }

    public void cancelAllNotifications() {
        getNotificationManager().cancelAll();
    }

    public void cancelAllPendingNotificationIntents() {
        for (int i : getScheduledNotificationIDs()) {
            cancelPendingNotificationIntent(i);
        }
    }

    public void cancelPendingNotificationIntent(int i) {
        cancelPendingNotificationIntentInternal(i, this.mContext);
        if (this.reschedule_on_restart) {
            deleteExpiredNotificationIntent(i, this.mContext);
        }
    }

    public boolean checkIfPendingNotificationIsRegistered(int i) {
        return PendingIntent.getBroadcast(this.mContext, i, CreateNotificationIntent(this.mActivity), 536870912) != null;
    }

    public int checkNotificationStatus(int i) {
        if (Build.VERSION.SDK_INT < 23) {
            return -1;
        }
        for (StatusBarNotification statusBarNotification : getNotificationManager().getActiveNotifications()) {
            if (i == statusBarNotification.getId()) {
                return 2;
            }
        }
        return checkIfPendingNotificationIsRegistered(i) ? 1 : 0;
    }

    public void deleteNotificationChannel(String str) {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(UNITY_NOTIFICATION_SETTINGS, 0);
        Set<String> stringSet = sharedPreferences.getStringSet("ChannelIDs", new HashSet());
        if (stringSet.contains(str)) {
            stringSet.remove(str);
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.clear();
            editorEdit.putStringSet("ChannelIDs", stringSet);
            editorEdit.commit();
            SharedPreferences.Editor editorEdit2 = this.mContext.getSharedPreferences(String.format("unity_notification_channel_%s", str), 0).edit();
            editorEdit2.clear();
            editorEdit2.commit();
        }
    }

    public NotificationChannelWrapper getNotificationChannel(String str) {
        return getNotificationChannel(str, this.mContext);
    }

    public Object[] getNotificationChannels() {
        Set<String> stringSet = this.mContext.getSharedPreferences(UNITY_NOTIFICATION_SETTINGS, 0).getStringSet("ChannelIDs", new HashSet());
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = stringSet.iterator();
        while (it.hasNext()) {
            arrayList.add(getNotificationChannel(it.next()));
        }
        return arrayList.toArray();
    }

    public NotificationManager getNotificationManager() {
        return getNotificationManager(this.mContext);
    }

    public int[] getScheduledNotificationIDs() {
        Set<String> stringSet = this.mContext.getSharedPreferences(UNITY_NOTIFICATION_SETTINGS, 0).getStringSet(SHARED_PREFS_NOTIFICATION_IDS, new HashSet());
        String[] strArr = (String[]) stringSet.toArray(new String[stringSet.size()]);
        int[] iArr = new int[stringSet.size()];
        for (int i = 0; i < strArr.length; i++) {
            iArr[i] = Integer.valueOf(strArr[i]).intValue();
        }
        return iArr;
    }

    public void getScheduledNotifications() {
        int[] scheduledNotificationIDs = getScheduledNotificationIDs();
        ArrayList arrayList = new ArrayList();
        for (int i : scheduledNotificationIDs) {
            PendingIntent pendingIntentRetrieveScheduledNotification = retrieveScheduledNotification(i);
            if (pendingIntentRetrieveScheduledNotification != null) {
                arrayList.add(pendingIntentRetrieveScheduledNotification);
            }
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= 24) {
            UnityNotificationManagerNougat.sendNotificationNougat(intent, context);
        } else {
            sendNotification(intent, context);
        }
    }

    public void registerNotificationChannel(String str, String str2, int i, String str3, boolean z, boolean z2, boolean z3, boolean z4, long[] jArr, int i2) {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(UNITY_NOTIFICATION_SETTINGS, 0);
        Set<String> stringSet = sharedPreferences.getStringSet("ChannelIDs", new HashSet());
        stringSet.add(str);
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.clear();
        editorEdit.putStringSet("ChannelIDs", stringSet);
        editorEdit.commit();
        SharedPreferences.Editor editorEdit2 = this.mContext.getSharedPreferences(String.format("unity_notification_channel_%s", str), 0).edit();
        editorEdit2.putString(C0856j.f955a, str2);
        editorEdit2.putInt("importance", i);
        editorEdit2.putString("description", str3);
        editorEdit2.putBoolean("enableLights", z);
        editorEdit2.putBoolean("enableVibration", z2);
        editorEdit2.putBoolean("canBypassDnd", z3);
        editorEdit2.putBoolean("canShowBadge", z4);
        editorEdit2.putString("vibrationPattern", Arrays.toString(jArr));
        editorEdit2.putInt("lockscreenVisibility", i2);
        editorEdit2.commit();
    }

    public void scheduleNotificationIntent(Intent intent) {
        Intent intentDeserializeNotificationIntent = DeserializeNotificationIntent(SerializeNotificationIntent(intent), this.mContext);
        int intExtra = intentDeserializeNotificationIntent.getIntExtra("id", 0);
        Intent intentPrepareNotificationIntent = prepareNotificationIntent(intentDeserializeNotificationIntent, this.mContext, PendingIntent.getActivity(this.mContext, intExtra, buildOpenAppIntent(intentDeserializeNotificationIntent, this.mContext, this.mOpenActivity), 0));
        if (intentPrepareNotificationIntent != null) {
            if (this.reschedule_on_restart) {
                SaveNotificationIntent(intentDeserializeNotificationIntent, this.mContext);
            }
            scheduleNotificationIntentAlarm(intentPrepareNotificationIntent, this.mContext, PendingIntent.getBroadcast(this.mContext, intExtra, intentPrepareNotificationIntent, 134217728));
        }
    }

    public void setNotificationCallback(NotificationCallback notificationCallback) {
        mNotificationCallback = notificationCallback;
    }
}
