package com.p001a.p002a;

import android.annotation.SuppressLint;
import android.app.UiModeManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Point;
import android.hardware.input.InputManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.AnyThread;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.Size;
import android.support.annotation.UiThread;
import android.support.annotation.VisibleForTesting;
import android.support.annotation.WorkerThread;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.MotionEventCompat;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.Display;
import android.view.InputDevice;
import android.view.WindowManager;
import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.android.instantapps.InstantApps;
import com.google.common.base.Ascii;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.Contract;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.a.a.i */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0177i {

    /* JADX INFO: renamed from: a */
    @NonNull
    static final C0177i[] f81a = {new C0177i("screen_brightness", 15, new int[]{1, 2, 3, 8, 6}, null), new C0177i("device_orientation", 15, new int[]{1, 2, 3, 8, 6}, null), new C0177i("network_conn_type", 30, new int[]{1, 2, 3, 8, 6}, null), new C0177i("volume", 10, new int[]{1, 2, 3, 8, 6}, null), new C0177i("ssid", 60, new int[]{1, 2, 3, 8, 6}, null), new C0177i("bssid", 60, new int[]{1, 2, 3, 8, 6}, null), new C0177i("carrier_name", 60, new int[]{1, 2, 3, 8, 6}, null), new C0177i("adid", -1, new int[]{1, 4}, null), new C0177i("fire_adid", -1, new int[]{1, 4}, null), new C0177i("platform", -1, new int[]{0}, null), new C0177i("device", -1, new int[]{1, 2, 3, 8, 6}, null), new C0177i("disp_h", 60, new int[]{1, 2, 3, 8, 6}, null), new C0177i("disp_w", 60, new int[]{1, 2, 3, 8, 6}, null), new C0177i("package", -1, new int[]{0, 1}, null), new C0177i("installed_date", -1, new int[]{1}, null), new C0177i("app_version", -1, new int[]{1, 2, 3, 4, 8, 6}, null), new C0177i("app_short_string", -1, new int[]{1, 2, 3, 4, 8, 6}, null), new C0177i("android_id", 60, new int[]{1, 4}, null), new C0177i("os_version", -1, new int[]{0, 1, 2, 3, 4, 8, 6}, null), new C0177i("device_limit_tracking", -1, new int[]{1, 4}, null), new C0177i("fb_attribution_id", -1, new int[]{1}, null), new C0177i("ids", -1, null, new int[]{1, 4}), new C0177i("is_genuine", -1, new int[]{1, 4}, null), new C0177i("language", 60, new int[]{1, 4}, null), new C0177i("screen_dpi", 60, new int[]{1, 2, 3, 8, 6}, null), new C0177i("screen_inches", 60, new int[]{1}, null), new C0177i("manufacturer", -1, new int[]{1, 2, 3, 8, 6}, null), new C0177i("product_name", -1, new int[]{1, 2, 3, 8, 6}, null), new C0177i("architecture", -1, new int[]{1, 2, 3, 8, 6}, null), new C0177i("battery_status", 60, new int[]{1, 2, 3, 8, 6}, null), new C0177i("battery_level", 60, new int[]{1, 2, 3, 8, 6}, null), new C0177i("device_cores", -1, new int[]{1}, null), new C0177i("signal_bars", 30, new int[]{1, 2, 3, 8, 6}, null), new C0177i("installer_package", -1, new int[]{1}, null), new C0177i("instant_app", -1, new int[]{1, 2, 3, 8, 6}, null), new C0177i("locale", 60, new int[]{1, 2, 3, 8, 6, 9, 10}, null), new C0177i("timezone", 60, new int[]{1, 2, 3, 8, 6, 9, 10}, null), new C0177i("bluetooth_name", 30, new int[]{1, 2, 3, 8, 6}, null), new C0177i("connected_devices", 30, new int[]{1, 2, 3, 8, 6}, null), new C0177i("capabilities", -1, new int[]{1, 2, 3, 8, 6}, null), new C0177i("ui_mode", 30, new int[]{1, 2, 3, 8, 6}, null), new C0177i("install_referrer", -1, new int[]{1}, null), new C0177i(FirebaseAnalytics.Param.LOCATION, 1, null, new int[]{1, 2, 8, 6})};

    /* JADX INFO: renamed from: b */
    @VisibleForTesting
    @NonNull
    final String f82b;

    /* JADX INFO: renamed from: c */
    @IntRange(from = -1)
    private final int f83c;

    /* JADX INFO: renamed from: d */
    @Nullable
    private final int[] f84d;

    /* JADX INFO: renamed from: e */
    @Nullable
    private final int[] f85e;

    /* JADX INFO: renamed from: com.a.a.i$a */
    @AnyThread
    static final class a implements Runnable {

        /* JADX INFO: renamed from: a */
        @NonNull
        final CountDownLatch f86a = new CountDownLatch(1);

        /* JADX INFO: renamed from: b */
        @NonNull
        final JSONObject f87b = new JSONObject();

        /* JADX INFO: renamed from: c */
        @NonNull
        private final Context f88c;

        a(@NonNull Context context) {
            this.f88c = context;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code duplicated, block: B:29:0x0060  */
        @Contract(pure = true)
        /* JADX INFO: renamed from: a */
        static int m186a(@NonNull String str) {
            switch (str) {
                case "service_disconnected":
                    return -1;
                case "ok":
                    return 0;
                case "service_unavailable":
                    return 1;
                case "feature_not_supported":
                    return 2;
                case "developer_error":
                    return 3;
                case "timed_out":
                    return 4;
                case "missing_dependency":
                    return 5;
                case "not_gathered":
                default:
                    return 6;
            }
        }

        @NonNull
        /* JADX INFO: renamed from: a */
        static C0171c m187a(@NonNull JSONObject jSONObject, boolean z, @Nullable String str) {
            int i;
            long jM219b;
            boolean z2;
            String str2;
            long j;
            String strM203a = C0178j.m203a(jSONObject.opt(NotificationCompat.CATEGORY_STATUS));
            int iM186a = z ? 4 : 6;
            if (strM203a != null) {
                iM186a = m186a(strM203a);
            }
            if (iM186a == 0) {
                String strM204a = C0178j.m204a(jSONObject.opt("referrer"), "");
                long jM219b2 = C0178j.m219b(jSONObject.opt("install_begin_time"), -1);
                i = iM186a;
                jM219b = C0178j.m219b(jSONObject.opt("referrer_click_time"), -1);
                j = jM219b2;
                z2 = false;
                str2 = strM204a;
            } else if (str == null || str.isEmpty()) {
                i = iM186a;
                jM219b = -1;
                z2 = false;
                str2 = "";
                j = -1;
            } else {
                str2 = str;
                j = 0;
                jM219b = 0;
                i = 0;
                z2 = true;
            }
            return new C0171c(str2, j, jM219b, i, z2);
        }

        @Contract(pure = true)
        @NonNull
        /* JADX INFO: renamed from: a */
        static String m188a(int i) {
            switch (i) {
                case -1:
                    return "service_disconnected";
                case 0:
                    return "ok";
                case 1:
                    return "service_unavailable";
                case 2:
                    return "feature_not_supported";
                case 3:
                    return "developer_error";
                case 4:
                    return "timed_out";
                case 5:
                    return "missing_dependency";
                case 6:
                default:
                    return "not_gathered";
            }
        }

        @NonNull
        /* JADX INFO: renamed from: a */
        static JSONObject m189a(@NonNull C0171c c0171c) {
            JSONObject jSONObject = new JSONObject();
            C0178j.m209a(NotificationCompat.CATEGORY_STATUS, m188a(c0171c.f27l), jSONObject);
            if (c0171c.m11a()) {
                C0178j.m209a("referrer", c0171c.f24i, jSONObject);
                C0178j.m209a("install_begin_time", Long.valueOf(c0171c.f25j), jSONObject);
                C0178j.m209a("referrer_click_time", Long.valueOf(c0171c.f26k), jSONObject);
            }
            return jSONObject;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: a */
        public void m190a() {
            if (C0178j.m203a(this.f87b.opt(NotificationCompat.CATEGORY_STATUS)) == null) {
                C0178j.m209a(NotificationCompat.CATEGORY_STATUS, m188a(5), this.f87b);
            }
        }

        /* JADX INFO: renamed from: b */
        final void m192b(int i) {
            try {
                this.f86a.await(i, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                C0174f.m16a(5, "IRH", "waitOnLock", e);
            }
        }

        @Override // java.lang.Runnable
        @UiThread
        public final void run() {
            try {
                final InstallReferrerClient installReferrerClientBuild = InstallReferrerClient.newBuilder(this.f88c).build();
                installReferrerClientBuild.startConnection(new InstallReferrerStateListener() { // from class: com.a.a.i.a.1
                    public final void onInstallReferrerServiceDisconnected() {
                        try {
                            C0174f.m16a(5, "IRH", "onInstallRefe", "Disconnected");
                            C0178j.m209a(NotificationCompat.CATEGORY_STATUS, a.m188a(-1), a.this.f87b);
                            installReferrerClientBuild.endConnection();
                        } catch (Throwable th) {
                            C0174f.m16a(4, "IRH", "onInstallRefe", th);
                            a.this.m190a();
                        }
                        a.this.f86a.countDown();
                    }

                    public final void onInstallReferrerSetupFinished(int i) {
                        Object objM188a;
                        JSONObject jSONObject;
                        try {
                            C0174f.m16a(5, "IRH", "onInstallRefe", "Setup Finished", "Response Code: " + i);
                            String str = NotificationCompat.CATEGORY_STATUS;
                            if (i == 0) {
                                ReferrerDetails installReferrer = installReferrerClientBuild.getInstallReferrer();
                                if (installReferrer != null) {
                                    C0178j.m209a(NotificationCompat.CATEGORY_STATUS, a.m188a(0), a.this.f87b);
                                    C0178j.m209a("referrer", installReferrer.getInstallReferrer(), a.this.f87b);
                                    C0178j.m209a("install_begin_time", Long.valueOf(installReferrer.getInstallBeginTimestampSeconds()), a.this.f87b);
                                    str = "referrer_click_time";
                                    objM188a = Long.valueOf(installReferrer.getReferrerClickTimestampSeconds());
                                    jSONObject = a.this.f87b;
                                    C0178j.m209a(str, objM188a, jSONObject);
                                }
                            } else {
                                if (i == 1) {
                                    objM188a = a.m188a(1);
                                    jSONObject = a.this.f87b;
                                } else if (i == 2) {
                                    objM188a = a.m188a(2);
                                    jSONObject = a.this.f87b;
                                } else if (i == 3) {
                                    objM188a = a.m188a(3);
                                    jSONObject = a.this.f87b;
                                }
                                C0178j.m209a(str, objM188a, jSONObject);
                            }
                            installReferrerClientBuild.endConnection();
                        } catch (Throwable th) {
                            C0174f.m16a(4, "IRH", "onInstallRefe", th);
                            a.this.m190a();
                        }
                        a.this.f86a.countDown();
                    }
                });
            } catch (Throwable th) {
                C0174f.m16a(5, "IRH", "run", th);
                m190a();
                this.f86a.countDown();
            }
        }
    }

    /* JADX INFO: renamed from: com.a.a.i$b */
    static class b implements LocationListener {

        /* JADX INFO: renamed from: a */
        @NonNull
        final CountDownLatch f91a = new CountDownLatch(1);

        /* JADX INFO: renamed from: b */
        @NonNull
        final JSONObject f92b = new JSONObject();

        /* JADX INFO: renamed from: c */
        @NonNull
        final JSONObject f93c = new JSONObject();

        /* JADX INFO: renamed from: d */
        @NonNull
        final String f94d;

        b(@NonNull String str) {
            this.f94d = str;
        }

        @AnyThread
        /* JADX INFO: renamed from: a */
        static void m193a(@NonNull JSONObject jSONObject, @Nullable Location location, @NonNull String str) {
            if (location != null) {
                try {
                    jSONObject.put("latitude", location.getLatitude());
                    jSONObject.put("longitude", location.getLongitude());
                    jSONObject.put("accuracy", Math.round(location.getAccuracy()));
                    jSONObject.put("time", C0178j.m200a((int) (location.getTime() / 1000), 0, (int) (C0178j.m202a() / 1000)));
                    if (location.hasAltitude()) {
                        jSONObject.put("altitude", location.getAltitude());
                    }
                    if (location.hasBearing()) {
                        jSONObject.put("direction", location.getBearing());
                    }
                    if (location.hasSpeed()) {
                        jSONObject.put("speed", location.getSpeed());
                    }
                    jSONObject.put("mode", str);
                    jSONObject.put("provider", location.getProvider());
                    if (Build.VERSION.SDK_INT >= 18) {
                        jSONObject.put("mock", location.isFromMockProvider());
                    }
                } catch (JSONException e) {
                    C0174f.m16a(4, "DPT", "toJson", e);
                }
            }
        }

        @Contract("null, _, _ -> false")
        @AnyThread
        /* JADX INFO: renamed from: a */
        static boolean m194a(@Nullable JSONObject jSONObject, @IntRange(from = 10, m1to = 10000) int i, @IntRange(from = 15, m1to = 604800) int i2) {
            if (jSONObject == null) {
                return false;
            }
            int iM202a = (int) (C0178j.m202a() / 1000);
            int iOptLong = (int) jSONObject.optLong("time", 0L);
            int iOptInt = jSONObject.optInt("accuracy");
            boolean z = iM202a - iOptLong <= i2 && iOptInt <= i;
            C0174f.m16a(4, "DPT", "WithinCriteri", "Within: ", Boolean.valueOf(z), "timeNow: " + iM202a, "timeLocation: " + iOptLong, "accuracy: " + i, "accuracyLocation: " + iOptInt);
            return z;
        }

        @NonNull
        /* JADX INFO: renamed from: a */
        final JSONObject m195a() {
            if (this.f93c.length() != 0) {
                C0174f.m16a(4, "DPT", "getLocation", "Returning New");
                return this.f93c;
            }
            if (this.f92b.length() == 0) {
                return new JSONObject();
            }
            C0174f.m16a(4, "DPT", "getLocation", "Returning Last");
            return this.f92b;
        }

        /* JADX INFO: renamed from: a */
        final void m196a(int i) {
            try {
                this.f91a.await(i, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                C0174f.m16a(4, "DPT", "waitOnLock", e);
            }
        }

        @Override // android.location.LocationListener
        public void onLocationChanged(Location location) {
            m193a(this.f93c, location, this.f94d);
            this.f91a.countDown();
        }

        @Override // android.location.LocationListener
        @AnyThread
        public final void onProviderDisabled(@Nullable String str) {
        }

        @Override // android.location.LocationListener
        @AnyThread
        public final void onProviderEnabled(@Nullable String str) {
        }

        @Override // android.location.LocationListener
        @AnyThread
        public final void onStatusChanged(@Nullable String str, int i, @Nullable Bundle bundle) {
        }
    }

    /* JADX INFO: renamed from: com.a.a.i$c */
    @VisibleForTesting
    static final class c extends b implements ResultCallback<Status>, com.google.android.gms.location.LocationListener {
        c() {
            super("googleplay");
        }

        @Override // com.google.android.gms.common.api.ResultCallback
        @AnyThread
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final void onResult(@NonNull Status status) {
            if (status.isSuccess()) {
                return;
            }
            this.f91a.countDown();
        }
    }

    private C0177i(@NonNull String str, @IntRange(from = -1) int i, @Nullable int[] iArr, @Nullable int[] iArr2) {
        C0174f.m16a(5, "DPT", "Data", str + "," + i);
        this.f82b = str;
        this.f83c = i;
        this.f84d = iArr;
        this.f85e = iArr2;
    }

    @Contract(pure = true)
    @AnyThread
    @Nullable
    /* JADX INFO: renamed from: A */
    private static String m127A(@NonNull Context context) {
        return context.getPackageManager().getInstallerPackageName(context.getPackageName());
    }

    @Contract(pure = true)
    @AnyThread
    /* JADX INFO: renamed from: B */
    private static boolean m128B(@NonNull Context context) {
        return InstantApps.isInstantApp(context);
    }

    @Contract(pure = true)
    @Nullable
    @AnyThread
    @RequiresPermission("android.permission.BLUETOOTH")
    /* JADX INFO: renamed from: C */
    private static String m129C(@NonNull Context context) {
        BluetoothAdapter defaultAdapter;
        if (C0178j.m224b(context, "android.permission.BLUETOOTH") && (defaultAdapter = BluetoothAdapter.getDefaultAdapter()) != null && defaultAdapter.isEnabled()) {
            return defaultAdapter.getName();
        }
        return null;
    }

    @WorkerThread
    @NonNull
    /* JADX INFO: renamed from: D */
    private static JSONArray m130D(@NonNull Context context) {
        BluetoothManager bluetoothManager;
        JSONArray jSONArray = new JSONArray();
        try {
            if (Build.VERSION.SDK_INT < 18 || (bluetoothManager = (BluetoothManager) context.getSystemService("bluetooth")) == null) {
                return jSONArray;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(bluetoothManager.getConnectedDevices(7));
            arrayList.addAll(bluetoothManager.getConnectedDevices(8));
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String str = "BT-LE: " + ((BluetoothDevice) it.next()).getName();
                if (!C0178j.m217a(jSONArray, str)) {
                    jSONArray.put(str);
                }
            }
        } catch (Throwable th) {
            C0174f.m16a(4, "DPT", "devicesBle", th);
        }
        return jSONArray;
    }

    @WorkerThread
    @NonNull
    /* JADX INFO: renamed from: E */
    private static JSONArray m131E(@NonNull Context context) {
        InputManager inputManager;
        JSONArray jSONArray = new JSONArray();
        try {
            if (Build.VERSION.SDK_INT >= 16 && (inputManager = (InputManager) context.getSystemService("input")) != null) {
                for (int i : inputManager.getInputDeviceIds()) {
                    InputDevice inputDevice = inputManager.getInputDevice(i);
                    if (!inputDevice.isVirtual()) {
                        int sources = inputDevice.getSources();
                        if ((sources & InputDeviceCompat.SOURCE_STYLUS) == 16386) {
                            String str = "Stylus: " + inputDevice.getName();
                            if (!C0178j.m217a(jSONArray, str)) {
                                jSONArray.put(str);
                            }
                        }
                        if ((sources & InputDeviceCompat.SOURCE_DPAD) == 513) {
                            String str2 = "D-pad: " + inputDevice.getName();
                            if (!C0178j.m217a(jSONArray, str2)) {
                                jSONArray.put(str2);
                            }
                        }
                        if ((sources & InputDeviceCompat.SOURCE_GAMEPAD) == 1025) {
                            String str3 = "GamePad: " + inputDevice.getName();
                            if (!C0178j.m217a(jSONArray, str3)) {
                                jSONArray.put(str3);
                            }
                        }
                        if ((sources & InputDeviceCompat.SOURCE_JOYSTICK) == 16777232) {
                            String str4 = "Joystick: " + inputDevice.getName();
                            if (!C0178j.m217a(jSONArray, str4)) {
                                jSONArray.put(str4);
                            }
                        }
                        if ((sources & InputDeviceCompat.SOURCE_KEYBOARD) == 257 && inputDevice.getKeyboardType() == 2) {
                            String str5 = "Keyboard: " + inputDevice.getName();
                            if (!C0178j.m217a(jSONArray, str5)) {
                                jSONArray.put(str5);
                            }
                        }
                        if ((sources & 8194) == 8194) {
                            String str6 = "Mouse: " + inputDevice.getName();
                            if (!C0178j.m217a(jSONArray, str6)) {
                                jSONArray.put(str6);
                            }
                        }
                        if ((sources & InputDeviceCompat.SOURCE_TOUCHPAD) == 1048584) {
                            String str7 = "TouchPad: " + inputDevice.getName();
                            if (!C0178j.m217a(jSONArray, str7)) {
                                jSONArray.put(str7);
                            }
                        }
                        if ((sources & InputDeviceCompat.SOURCE_TRACKBALL) == 65540) {
                            String str8 = "Trackball: " + inputDevice.getName();
                            if (!C0178j.m217a(jSONArray, str8)) {
                                jSONArray.put(str8);
                            }
                        }
                        if (Build.VERSION.SDK_INT >= 23 && (sources & 49154) == 49154) {
                            String str9 = "Bluetooth Stylus " + inputDevice.getName();
                            if (!C0178j.m217a(jSONArray, str9)) {
                                jSONArray.put(str9);
                            }
                        }
                        if (Build.VERSION.SDK_INT >= 26) {
                            if ((sources & 131076) == 131076) {
                                String str10 = "Mouse Relative: " + inputDevice.getName();
                                if (!C0178j.m217a(jSONArray, str10)) {
                                    jSONArray.put(str10);
                                }
                            }
                            if ((sources & 4194304) == 4194304) {
                                String str11 = "Rotary Encoder: " + inputDevice.getName();
                                if (!C0178j.m217a(jSONArray, str11)) {
                                    jSONArray.put(str11);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable th) {
            C0174f.m16a(4, "DPT", "devicesInput", th);
        }
        return jSONArray;
    }

    @WorkerThread
    @NonNull
    /* JADX INFO: renamed from: F */
    private static JSONArray m132F(@NonNull Context context) {
        JSONArray jSONArray = new JSONArray();
        try {
            GoogleApiClient googleApiClientBuild = new GoogleApiClient.Builder(context).addApi(Wearable.API).build();
            if (!googleApiClientBuild.blockingConnect(2L, TimeUnit.SECONDS).isSuccess()) {
                return jSONArray;
            }
            List<Node> nodes = Wearable.NodeApi.getConnectedNodes(googleApiClientBuild).await(2L, TimeUnit.SECONDS).getNodes();
            if (nodes.isEmpty()) {
                return jSONArray;
            }
            for (Node node : nodes) {
                if (node.isNearby()) {
                    jSONArray.put("Wearable: " + node.getDisplayName());
                }
            }
            googleApiClientBuild.disconnect();
        } catch (Throwable th) {
            C0174f.m16a(4, "DPT", "devicesNode", th);
        }
        return jSONArray;
    }

    @WorkerThread
    @Contract(pure = true)
    @Nullable
    /* JADX INFO: renamed from: G */
    private static JSONArray m133G(@NonNull Context context) {
        JSONArray jSONArray = new JSONArray();
        if (C0178j.m224b(context, "android.permission.BLUETOOTH")) {
            C0178j.m211a(jSONArray, m168j());
            C0178j.m211a(jSONArray, m130D(context));
        }
        C0178j.m211a(jSONArray, m131E(context));
        C0178j.m211a(jSONArray, m132F(context));
        if (jSONArray.length() != 0) {
            return jSONArray;
        }
        return null;
    }

    @WorkerThread
    @Contract(pure = true)
    @Nullable
    /* JADX INFO: renamed from: H */
    private static JSONArray m134H(@NonNull Context context) {
        return null;
    }

    @Contract(pure = true)
    @AnyThread
    @Nullable
    /* JADX INFO: renamed from: I */
    private static String m135I(@NonNull Context context) {
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService("uimode");
        if (uiModeManager == null) {
            return null;
        }
        switch (uiModeManager.getCurrentModeType()) {
            case 0:
                return "Undefined";
            case 1:
                return "Normal";
            case 2:
                return "Desk";
            case 3:
                return "Car";
            case 4:
                return "Television";
            case 5:
                return "Appliance";
            case 6:
                return "Watch";
            case 7:
                return "VR_Headset";
            default:
                return null;
        }
    }

    @Contract(pure = true)
    @AnyThread
    /* JADX INFO: renamed from: a */
    private static double m136a(@NonNull Context context) {
        double d = Settings.System.getInt(context.getContentResolver(), "screen_brightness");
        Double.isNaN(d);
        double dRound = Math.round((d / 255.0d) * 10000.0d);
        Double.isNaN(dRound);
        return C0178j.m198a(dRound / 10000.0d, 0.0d, 1.0d);
    }

    @WorkerThread
    @NonNull
    /* JADX INFO: renamed from: a */
    static C0171c m137a(@NonNull Context context, @Size(min = 1) int i, @Size(min = 1) int i2, @Size(min = 0) double d) {
        Handler handler = new Handler(Looper.getMainLooper());
        C0171c c0171c = null;
        int i3 = 0;
        while (i3 < i) {
            a aVar = new a(context);
            handler.post(aVar);
            aVar.m192b(i2);
            C0171c c0171cM187a = a.m187a(aVar.f87b, true, null);
            if (c0171cM187a.m11a() || !c0171cM187a.m12b()) {
                c0171c = c0171cM187a;
                break;
            }
            try {
                Thread.sleep(Math.round(1000.0d * d));
            } catch (InterruptedException e) {
                C0174f.m16a(4, "DPT", "getInstallRef", e);
            }
            i3++;
            c0171c = c0171cM187a;
        }
        return c0171c != null ? c0171c : new C0171c("", -1L, -1L, 4, false);
    }

    @WorkerThread
    @Nullable
    /* JADX INFO: renamed from: a */
    private Object m138a(@NonNull Context context, @NonNull C0178j c0178j, @Nullable Object obj, @Nullable Object obj2, boolean z, @NonNull List<String> list, @Nullable JSONObject jSONObject) {
        Object objM237b = obj != null ? obj : c0178j.m237b(this.f82b);
        if (obj == null || z || obj2 != null) {
            if (obj2 == null) {
                try {
                    obj2 = m139a(this, context, jSONObject, objM237b);
                } catch (Throwable th) {
                    C0174f.m16a(4, "DPT", "getValueNew", th);
                }
            }
            if (obj2 != null) {
                if (!list.contains(this.f82b)) {
                    list.add(this.f82b);
                }
                if (obj == null || !C0178j.m215a(obj2, obj)) {
                    c0178j.m236a(this.f82b, obj2);
                    c0178j.m236a(this.f82b + "_ts", (Object) Integer.valueOf((int) (C0178j.m202a() / 1000)));
                    if (!C0178j.m215a(obj2, objM237b)) {
                        c0178j.m236a(this.f82b + "_upd", (Object) true);
                    }
                }
            }
        }
        return obj2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:134:0x01fe  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @WorkerThread
    @VisibleForTesting
    @Nullable
    /* JADX INFO: renamed from: a */
    static Object m139a(@NonNull C0177i c0177i, @NonNull Context context, @Nullable JSONObject jSONObject, @Nullable Object obj) {
        byte b2;
        String str = c0177i.f82b;
        switch (str.hashCode()) {
            case -2086471997:
                if (!str.equals("instant_app")) {
                    b2 = -1;
                } else {
                    b2 = 33;
                }
                break;
            case -2076227591:
                if (!str.equals("timezone")) {
                    b2 = -1;
                } else {
                    b2 = 36;
                }
                break;
            case -1969347631:
                if (!str.equals("manufacturer")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.f283EM;
                }
                break;
            case -1958212269:
                if (!str.equals("installed_date")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.DLE;
                }
                break;
            case -1613589672:
                if (!str.equals("language")) {
                    b2 = -1;
                } else {
                    b2 = 35;
                }
                break;
            case -1487597642:
                if (!str.equals("capabilities")) {
                    b2 = -1;
                } else {
                    b2 = 39;
                }
                break;
            case -1335157162:
                if (!str.equals("device")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.f284FF;
                }
                break;
            case -1331545845:
                if (!str.equals("disp_h")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.f282CR;
                }
                break;
            case -1331545830:
                if (!str.equals("disp_w")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.f292SO;
                }
                break;
            case -1211390364:
                if (!str.equals("battery_status")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.f285FS;
                }
                break;
            case -1144512572:
                if (!str.equals("device_limit_tracking")) {
                    b2 = -1;
                } else {
                    b2 = 10;
                }
                break;
            case -1097462182:
                if (!str.equals("locale")) {
                    b2 = -1;
                } else {
                    b2 = 34;
                }
                break;
            case -901870406:
                if (!str.equals("app_version")) {
                    b2 = -1;
                } else {
                    b2 = 17;
                }
                break;
            case -877252910:
                if (!str.equals("battery_level")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.f286GS;
                }
                break;
            case -810883302:
                if (!str.equals("volume")) {
                    b2 = -1;
                } else {
                    b2 = 4;
                }
                break;
            case -807062458:
                if (!str.equals("package")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.f291SI;
                }
                break;
            case -600298101:
                if (!str.equals("device_cores")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.f290RS;
                }
                break;
            case -439099282:
                if (!str.equals("ui_mode")) {
                    b2 = -1;
                } else {
                    b2 = 40;
                }
                break;
            case -417046774:
                if (!str.equals("screen_dpi")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.ETB;
                }
                break;
            case -345765233:
                if (!str.equals("installer_package")) {
                    b2 = -1;
                } else {
                    b2 = 32;
                }
                break;
            case -286797593:
                if (!str.equals("fire_adid")) {
                    b2 = -1;
                } else {
                    b2 = 9;
                }
                break;
            case -184604772:
                if (!str.equals("network_conn_type")) {
                    b2 = -1;
                } else {
                    b2 = 3;
                }
                break;
            case 104120:
                if (!str.equals("ids")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.NAK;
                }
                break;
            case 2989182:
                if (!str.equals("adid")) {
                    b2 = -1;
                } else {
                    b2 = 8;
                }
                break;
            case 3539835:
                if (!str.equals("ssid")) {
                    b2 = -1;
                } else {
                    b2 = 5;
                }
                break;
            case 94044893:
                if (!str.equals("bssid")) {
                    b2 = -1;
                } else {
                    b2 = 6;
                }
                break;
            case 224914812:
                if (!str.equals("bluetooth_name")) {
                    b2 = -1;
                } else {
                    b2 = 37;
                }
                break;
            case 672545271:
                if (!str.equals("signal_bars")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.f294US;
                }
                break;
            case 672836989:
                if (!str.equals("os_version")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.DC4;
                }
                break;
            case 722989291:
                if (!str.equals("android_id")) {
                    b2 = -1;
                } else {
                    b2 = 19;
                }
                break;
            case 839674195:
                if (!str.equals("architecture")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.ESC;
                }
                break;
            case 1014375387:
                if (!str.equals("product_name")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.SUB;
                }
                break;
            case 1241166251:
                if (!str.equals("screen_inches")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.CAN;
                }
                break;
            case 1328981571:
                if (!str.equals("install_referrer")) {
                    b2 = -1;
                } else {
                    b2 = 41;
                }
                break;
            case 1420630150:
                if (!str.equals("is_genuine")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.SYN;
                }
                break;
            case 1735689732:
                if (!str.equals("screen_brightness")) {
                    b2 = -1;
                } else {
                    b2 = 1;
                }
                break;
            case 1741791591:
                if (!str.equals("device_orientation")) {
                    b2 = -1;
                } else {
                    b2 = 2;
                }
                break;
            case 1757114046:
                if (!str.equals("fb_attribution_id")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.f295VT;
                }
                break;
            case 1774661031:
                if (!str.equals("connected_devices")) {
                    b2 = -1;
                } else {
                    b2 = 38;
                }
                break;
            case 1874684019:
                if (!str.equals("platform")) {
                    b2 = -1;
                } else {
                    b2 = 0;
                }
                break;
            case 1901043637:
                if (!str.equals(FirebaseAnalytics.Param.LOCATION)) {
                    b2 = -1;
                } else {
                    b2 = 42;
                }
                break;
            case 1974464370:
                if (!str.equals("carrier_name")) {
                    b2 = -1;
                } else {
                    b2 = 7;
                }
                break;
            case 2118140562:
                if (!str.equals("app_short_string")) {
                    b2 = -1;
                } else {
                    b2 = Ascii.DC2;
                }
                break;
            default:
                b2 = -1;
                break;
        }
        switch (b2) {
            case 0:
                return "android";
            case 1:
                return Double.valueOf(m136a(context));
            case 2:
                return m151b(context);
            case 3:
                return m153c(context);
            case 4:
                return m155d(context);
            case 5:
                return m158e(context);
            case 6:
                return m160f(context);
            case 7:
                return m162g(context);
            case 8:
                return m164h(context);
            case 9:
                return m166i(context);
            case 10:
                return Boolean.valueOf(m175q(context));
            case 11:
                return m178t(context);
            case 12:
                return m140a();
            case 13:
                return m167j(context);
            case 14:
                return m169k(context);
            case 15:
                return m170l(context);
            case 16:
                return Integer.valueOf(m171m(context));
            case 17:
                return m172n(context);
            case 18:
                return m173o(context);
            case 19:
                return m174p(context);
            case 20:
                return m150b();
            case MotionEventCompat.AXIS_WHEEL /* 21 */:
                return m179u(context);
            case MotionEventCompat.AXIS_GAS /* 22 */:
                return Boolean.valueOf(m154c());
            case MotionEventCompat.AXIS_BRAKE /* 23 */:
                return Integer.valueOf(m180v(context));
            case MotionEventCompat.AXIS_DISTANCE /* 24 */:
                return m181w(context);
            case 25:
                return m156d();
            case MotionEventCompat.AXIS_SCROLL /* 26 */:
                return m157e();
            case MotionEventCompat.AXIS_RELATIVE_X /* 27 */:
                return m159f();
            case MotionEventCompat.AXIS_RELATIVE_Y /* 28 */:
                return m182x(context);
            case 29:
                return m183y(context);
            case 30:
                return Integer.valueOf(m161g());
            case 31:
                return m184z(context);
            case 32:
                return m127A(context);
            case 33:
                return Boolean.valueOf(m128B(context));
            case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
            case MotionEventCompat.AXIS_GENERIC_4 /* 35 */:
                return m163h();
            case MotionEventCompat.AXIS_GENERIC_5 /* 36 */:
                return m165i();
            case MotionEventCompat.AXIS_GENERIC_6 /* 37 */:
                return m129C(context);
            case MotionEventCompat.AXIS_GENERIC_7 /* 38 */:
                return m133G(context);
            case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
                return m134H(context);
            case MotionEventCompat.AXIS_GENERIC_9 /* 40 */:
                return m135I(context);
            case MotionEventCompat.AXIS_GENERIC_10 /* 41 */:
                return m143a(context, jSONObject);
            case MotionEventCompat.AXIS_GENERIC_11 /* 42 */:
                return m144a(context, jSONObject, obj);
            default:
                return null;
        }
    }

    @Contract(pure = true)
    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: a */
    private static String m140a() {
        return Build.MODEL + "-" + Build.BRAND;
    }

    @WorkerThread
    @Nullable
    /* JADX INFO: renamed from: a */
    private static JSONObject m141a(@NonNull Context context, @IntRange(from = 10, m1to = 10000) int i, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_ARTISTS, m1to = 60) int i2, @IntRange(from = 15, m1to = 604800) int i3) {
        GoogleApiClient googleApiClientBuild;
        C0174f.m16a(5, "DPT", "getWithGoogle", new Object[0]);
        try {
            googleApiClientBuild = new GoogleApiClient.Builder(context).addApi(LocationServices.API).build();
        } catch (Throwable th) {
            C0174f.m16a(5, "DPT", "getWithGoogle", th);
            googleApiClientBuild = null;
        }
        if (googleApiClientBuild == null) {
            C0174f.m16a(4, "DPT", "getWithGoogle", "Failed to connect to Google Play Services");
            return null;
        }
        ConnectionResult connectionResultBlockingConnect = googleApiClientBuild.blockingConnect(2L, TimeUnit.SECONDS);
        if (!connectionResultBlockingConnect.isSuccess()) {
            C0174f.m16a(4, "DPT", "getWithGoogle", connectionResultBlockingConnect.getErrorMessage());
            return new JSONObject();
        }
        c cVar = new c();
        b.m193a(cVar.f92b, LocationServices.FusedLocationApi.getLastLocation(googleApiClientBuild), cVar.f94d);
        if (b.m194a(cVar.f92b, i, i3)) {
            googleApiClientBuild.disconnect();
            C0174f.m16a(4, "DPT", "getWithGoogle", "Returning Last Known");
            return cVar.f92b;
        }
        try {
            LocationRequest numUpdates = new LocationRequest().setNumUpdates(1);
            if (i < 50) {
                numUpdates.setPriority(100);
            } else if (i < 1000) {
                numUpdates.setPriority(102);
            } else if (i < 10000) {
                numUpdates.setPriority(104);
            } else {
                numUpdates.setPriority(105);
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClientBuild, numUpdates, cVar).setResultCallback(cVar, i2, TimeUnit.SECONDS);
        } catch (Exception e) {
            C0174f.m16a(4, "DPT", "getWithGoogle", e);
        }
        cVar.m196a(i2);
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClientBuild, cVar);
        googleApiClientBuild.disconnect();
        return cVar.m195a();
    }

    @WorkerThread
    @Nullable
    /* JADX INFO: renamed from: a */
    private static JSONObject m142a(@NonNull Context context, @IntRange(from = 10, m1to = 10000) int i, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_ARTISTS, m1to = 60) int i2, @IntRange(from = 15, m1to = 604800) int i3, @NonNull String str) {
        C0174f.m16a(5, "DPT", "getLocation", new Object[0]);
        if (!C0178j.m214a(context, "android.permission.ACCESS_FINE_LOCATION") && !C0178j.m214a(context, "android.permission.ACCESS_COARSE_LOCATION")) {
            C0174f.m16a(3, "DPT", "getLocation", "Missing Permission: android.permission.ACCESS_FINE_LOCATION android.permission.ACCESS_COARSE_LOCATION");
            return null;
        }
        int iIsGooglePlayServicesAvailable = -1;
        if ("auto".equalsIgnoreCase(str) || "googleplay".equalsIgnoreCase(str)) {
            try {
                iIsGooglePlayServicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
            } catch (Throwable unused) {
                C0174f.m16a(4, "DPT", "getLocation", "Missing Google Play Services");
            }
        }
        JSONObject jSONObjectM141a = iIsGooglePlayServicesAvailable == 0 ? m141a(context, i, i2, i3) : null;
        if (jSONObjectM141a == null) {
            return ("auto".equalsIgnoreCase(str) || "device".equalsIgnoreCase(str)) ? m152b(context, i, i2, i3) : jSONObjectM141a;
        }
        return jSONObjectM141a;
    }

    @NonNull
    /* JADX INFO: renamed from: a */
    static JSONObject m143a(@NonNull Context context, @Nullable JSONObject jSONObject) {
        int iM200a;
        int iM200a2 = 10;
        double dM198a = 1.0d;
        if (jSONObject != null) {
            iM200a = jSONObject.has("install_referrer_attempts") ? C0178j.m200a(C0178j.m219b(jSONObject.opt("install_referrer_attempts"), 2), 1, Integer.MAX_VALUE) : 2;
            iM200a2 = jSONObject.has("install_referrer_wait") ? C0178j.m200a(C0178j.m219b(jSONObject.opt("install_referrer_wait"), 10), 1, Integer.MAX_VALUE) : 10;
            if (jSONObject.has("install_referrer_retry_wait")) {
                dM198a = C0178j.m198a(C0178j.m199a(jSONObject.opt("install_referrer_retry_wait"), 1.0d), 0.0d, Double.MAX_VALUE);
            }
        } else {
            iM200a = 2;
        }
        C0174f.m16a(4, "DPT", "getInstallRef", "Attempts: " + iM200a, "AttemptWait: " + iM200a2, "AttemptRetryWait: " + dM198a);
        return a.m189a(m137a(context, iM200a, iM200a2, dM198a));
    }

    @WorkerThread
    @Nullable
    /* JADX INFO: renamed from: a */
    private static JSONObject m144a(@NonNull Context context, @Nullable JSONObject jSONObject, @Nullable Object obj) {
        int iM200a = 50;
        int iM200a2 = 10;
        int iM200a3 = 90;
        String strM204a = "auto";
        if (jSONObject != null) {
            iM200a = jSONObject.has("accuracy") ? C0178j.m200a(C0178j.m219b(jSONObject.opt("accuracy"), 50), 0, Integer.MAX_VALUE) : 50;
            iM200a2 = jSONObject.has("timeout") ? C0178j.m200a(C0178j.m219b(jSONObject.opt("timeout"), 10), 1, Integer.MAX_VALUE) : 10;
            iM200a3 = jSONObject.has("staleness") ? C0178j.m200a(C0178j.m219b(jSONObject.opt("staleness"), 90), 0, Integer.MAX_VALUE) : 90;
            if (jSONObject.has("mode")) {
                strM204a = C0178j.m204a(jSONObject.opt("mode"), "auto");
            }
        }
        C0174f.m16a(4, "DPT", "getLocation11", "Accuracy: " + iM200a, "Timeout: " + iM200a2, "Staleness: " + iM200a3, "Mode: " + strM204a);
        JSONObject jSONObject2 = obj instanceof JSONObject ? (JSONObject) obj : null;
        if (!b.m194a(jSONObject2, iM200a, iM200a3)) {
            return m142a(context, iM200a, iM200a2, iM200a3, strM204a);
        }
        C0174f.m16a(4, "DPT", "getLocation11", "Returning Cached");
        return jSONObject2;
    }

    @WorkerThread
    /* JADX INFO: renamed from: a */
    static void m145a(@NonNull Context context, @NonNull C0178j c0178j, @NonNull JSONObject jSONObject, @NonNull JSONObject jSONObject2, @NonNull List<String> list, @Nullable JSONArray jSONArray, @Nullable JSONArray jSONArray2, int i, @NonNull JSONObject jSONObject3) {
        boolean z = i == 4;
        boolean z2 = i == 1;
        int i2 = 0;
        while (true) {
            C0177i[] c0177iArr = f81a;
            if (i2 >= c0177iArr.length) {
                return;
            }
            C0177i c0177i = c0177iArr[i2];
            if (m149a(c0177i, i, jSONArray2, jSONArray)) {
                C0174f.m16a(5, "DPT", "get", i + "," + c0177i.f82b);
                c0177i.m146a(context, c0178j, jSONObject3, z, z2, list, jSONObject2.opt(c0177i.f82b), C0178j.m231e(jSONObject.opt(c0177i.f82b)));
            }
            i2++;
        }
    }

    @WorkerThread
    /* JADX INFO: renamed from: a */
    private void m146a(@NonNull Context context, @NonNull C0178j c0178j, @NonNull JSONObject jSONObject, boolean z, boolean z2, @NonNull List<String> list, @Nullable Object obj, @Nullable JSONObject jSONObject2) {
        synchronized (this) {
            Object objM185a = m185a(c0178j, z, list.contains(this.f82b));
            Object objM138a = m138a(context, c0178j, objM185a, obj, z, list, jSONObject2);
            boolean zM216a = C0178j.m216a(c0178j.m237b(this.f82b + "_upd"), false);
            C0174f.m16a(4, "DPT", "addToPayload", this.f82b + ": " + objM185a + "," + objM138a + " hasUpdated: " + zM216a + " isEqual: " + C0178j.m215a(objM138a, objM185a));
            m147a(jSONObject, objM138a, objM185a, z, zM216a);
            if (z2 || z) {
                c0178j.m236a(this.f82b + "_upd", (Object) false);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0018  */
    @AnyThread
    /* JADX INFO: renamed from: a */
    private void m147a(@NonNull JSONObject jSONObject, @Nullable Object obj, @Nullable Object obj2, boolean z, boolean z2) {
        String str;
        if (z && obj != null) {
            if (!z2) {
                try {
                    if (C0178j.m215a(obj, obj2)) {
                        if (z) {
                        }
                        if (z) {
                        }
                        C0174f.m16a(5, "DPT", "addToData", "Skip");
                        return;
                    }
                } catch (JSONException e) {
                    C0174f.m16a(4, "DPT", "addToData", e);
                    return;
                }
            }
            str = this.f82b;
        } else {
            if (!z || obj == null) {
                if (!z || obj2 == null) {
                    C0174f.m16a(5, "DPT", "addToData", "Skip");
                    return;
                } else {
                    m148a(jSONObject, this.f82b, obj2);
                    return;
                }
            }
            str = this.f82b;
        }
        m148a(jSONObject, str, obj);
    }

    /* JADX INFO: renamed from: a */
    private void m148a(@NonNull JSONObject jSONObject, @NonNull String str, @NonNull Object obj) throws JSONException {
        if ((obj instanceof JSONObject) && ((JSONObject) obj).length() == 0) {
            return;
        }
        if ((obj instanceof JSONArray) && ((JSONArray) obj).length() == 0) {
            return;
        }
        jSONObject.put(str, obj);
    }

    @AnyThread
    /* JADX INFO: renamed from: a */
    static boolean m149a(@NonNull C0177i c0177i, int i, @Nullable JSONArray jSONArray, @Nullable JSONArray jSONArray2) {
        int[] iArr;
        if (jSONArray2 != null) {
            if (C0178j.m217a(jSONArray2, c0177i.f82b)) {
                return false;
            }
        } else if (i != 0) {
            return false;
        }
        int[] iArr2 = c0177i.f84d;
        if (iArr2 != null) {
            for (int i2 : iArr2) {
                if (i2 == i) {
                    return true;
                }
            }
        }
        if (jSONArray != null && (iArr = c0177i.f85e) != null) {
            for (int i3 : iArr) {
                if (i3 == i && C0178j.m217a(jSONArray, c0177i.f82b)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Contract(pure = true)
    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: b */
    private static String m150b() {
        return "Android " + Build.VERSION.RELEASE;
    }

    @Contract(pure = true)
    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: b */
    private static String m151b(@NonNull Context context) {
        return context.getResources().getConfiguration().orientation == 2 ? "landscape" : "portrait";
    }

    @WorkerThread
    @NonNull
    /* JADX INFO: renamed from: b */
    private static JSONObject m152b(@NonNull Context context, @IntRange(from = 10, m1to = 10000) int i, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_ARTISTS, m1to = 60) int i2, @IntRange(from = 15, m1to = 604800) int i3) {
        C0174f.m16a(5, "DPT", "getWithDevice", new Object[0]);
        b bVar = new b("device");
        LocationManager locationManager = (LocationManager) context.getSystemService(FirebaseAnalytics.Param.LOCATION);
        if (locationManager == null) {
            return bVar.m195a();
        }
        if (i < 50 && C0178j.m214a(context, "android.permission.ACCESS_FINE_LOCATION")) {
            b.m193a(bVar.f92b, locationManager.getLastKnownLocation("gps"), bVar.f94d);
        }
        if (bVar.f92b.length() == 0 && i < 10000) {
            b.m193a(bVar.f92b, locationManager.getLastKnownLocation("network"), bVar.f94d);
        }
        if (bVar.f92b.length() == 0) {
            b.m193a(bVar.f92b, locationManager.getLastKnownLocation("passive"), bVar.f94d);
        }
        if (b.m194a(bVar.f92b, i, i3)) {
            C0174f.m16a(4, "DPT", "getWithDevice", "Returning Last Known");
            return bVar.f92b;
        }
        try {
            Criteria criteria = new Criteria();
            if (i >= 50 || !C0178j.m214a(context, "android.permission.ACCESS_FINE_LOCATION")) {
                if (i < 10000) {
                    criteria.setAccuracy(2);
                    criteria.setPowerRequirement(2);
                } else {
                    criteria.setAccuracy(2);
                    criteria.setPowerRequirement(1);
                }
                criteria.setAltitudeRequired(true);
                criteria.setBearingRequired(true);
                criteria.setSpeedRequired(true);
                locationManager.requestSingleUpdate(criteria, bVar, (Looper) null);
                bVar.m196a(i2);
                locationManager.removeUpdates(bVar);
                return bVar.m195a();
            }
            criteria.setAccuracy(1);
            criteria.setPowerRequirement(3);
            criteria.setCostAllowed(true);
            criteria.setAltitudeRequired(true);
            criteria.setBearingRequired(true);
            criteria.setSpeedRequired(true);
            locationManager.requestSingleUpdate(criteria, bVar, (Looper) null);
        } catch (Exception e) {
            C0174f.m16a(4, "DPT", "getWithDevice", e);
        }
        bVar.m196a(i2);
        locationManager.removeUpdates(bVar);
        return bVar.m195a();
    }

    @Contract(pure = true)
    @Nullable
    @AnyThread
    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    /* JADX INFO: renamed from: c */
    private static String m153c(@NonNull Context context) {
        ConnectivityManager connectivityManager;
        if (!C0178j.m224b(context, "android.permission.ACCESS_NETWORK_STATE") || (connectivityManager = (ConnectivityManager) context.getSystemService("connectivity")) == null) {
            return null;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return "none";
        }
        int type = activeNetworkInfo.getType();
        if (type == 0 || type == 4 || type == 5 || type == 2 || type == 3) {
            return "cellular";
        }
        return type == 9 ? "wired" : "wifi";
    }

    @WorkerThread
    @Contract(pure = true)
    /* JADX INFO: renamed from: c */
    private static boolean m154c() {
        String str = Build.TAGS;
        if (str != null && str.contains("test-keys")) {
            return false;
        }
        for (String str2 : new String[]{"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"}) {
            if (new File(str2).exists()) {
                return false;
            }
        }
        return true;
    }

    @Contract(pure = true)
    @AnyThread
    @Nullable
    /* JADX INFO: renamed from: d */
    private static Double m155d(@NonNull Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        if (audioManager == null) {
            return null;
        }
        double streamVolume = audioManager.getStreamVolume(3);
        Double.isNaN(streamVolume);
        double streamMaxVolume = audioManager.getStreamMaxVolume(3);
        Double.isNaN(streamMaxVolume);
        double dRound = Math.round(((streamVolume * 1.0d) / streamMaxVolume) * 10000.0d);
        Double.isNaN(dRound);
        return Double.valueOf(C0178j.m198a(dRound / 10000.0d, 0.0d, 1.0d));
    }

    @Contract(pure = true)
    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: d */
    private static String m156d() {
        return Build.MANUFACTURER;
    }

    @Contract(pure = true)
    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: e */
    private static String m157e() {
        return Build.PRODUCT;
    }

    @Contract(pure = true)
    @Nullable
    @AnyThread
    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    /* JADX INFO: renamed from: e */
    private static String m158e(@NonNull Context context) {
        WifiManager wifiManager;
        if (C0178j.m224b(context, "android.permission.ACCESS_WIFI_STATE") && (wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi")) != null) {
            return wifiManager.getConnectionInfo().getSSID();
        }
        return null;
    }

    @Contract(pure = true)
    @AnyThread
    @Nullable
    /* JADX INFO: renamed from: f */
    private static String m159f() {
        return System.getProperty("os.arch");
    }

    @Contract(pure = true)
    @Nullable
    @AnyThread
    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    /* JADX INFO: renamed from: f */
    private static String m160f(@NonNull Context context) {
        WifiManager wifiManager;
        if (C0178j.m224b(context, "android.permission.ACCESS_WIFI_STATE") && (wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi")) != null) {
            return wifiManager.getConnectionInfo().getBSSID();
        }
        return null;
    }

    @Contract(pure = true)
    @AnyThread
    /* JADX INFO: renamed from: g */
    private static int m161g() {
        return C0178j.m200a(Runtime.getRuntime().availableProcessors(), 1, Integer.MAX_VALUE);
    }

    @Contract(pure = true)
    @AnyThread
    @Nullable
    /* JADX INFO: renamed from: g */
    private static String m162g(@NonNull Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            return null;
        }
        return telephonyManager.getNetworkOperatorName();
    }

    @Contract(pure = true)
    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: h */
    private static String m163h() {
        return Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry();
    }

    @WorkerThread
    @Contract(pure = true)
    @Nullable
    /* JADX INFO: renamed from: h */
    private static String m164h(@NonNull Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException, IOException {
        AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
        if (advertisingIdInfo == null) {
            return null;
        }
        return advertisingIdInfo.getId();
    }

    @Contract(pure = true)
    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: i */
    private static String m165i() {
        return TimeZone.getDefault().getID();
    }

    @Contract(pure = true)
    @AnyThread
    @Nullable
    /* JADX INFO: renamed from: i */
    private static String m166i(@NonNull Context context) {
        return Settings.Secure.getString(context.getContentResolver(), "advertising_id");
    }

    @Contract(pure = true)
    @AnyThread
    @Nullable
    /* JADX INFO: renamed from: j */
    private static Integer m167j(@NonNull Context context) {
        Display defaultDisplay;
        Point point;
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null || (defaultDisplay = windowManager.getDefaultDisplay()) == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 17) {
            point = new Point();
            defaultDisplay.getRealSize(point);
        } else {
            point = new Point();
            defaultDisplay.getSize(point);
        }
        return Integer.valueOf(point.y);
    }

    @WorkerThread
    @NonNull
    /* JADX INFO: renamed from: j */
    private static JSONArray m168j() {
        JSONArray jSONArray = new JSONArray();
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null && defaultAdapter.isEnabled()) {
                Set<BluetoothDevice> bondedDevices = defaultAdapter.getBondedDevices();
                if (bondedDevices != null) {
                    Iterator<BluetoothDevice> it = bondedDevices.iterator();
                    while (it.hasNext()) {
                        String str = "BT-PAIRED: " + it.next().getName();
                        if (!C0178j.m217a(jSONArray, str)) {
                            jSONArray.put(str);
                        }
                    }
                }
                if (Build.VERSION.SDK_INT >= 18) {
                    if (defaultAdapter.getProfileConnectionState(1) == 2 && !C0178j.m217a(jSONArray, "BT-PROFILE: HEADSET")) {
                        jSONArray.put("BT-PROFILE: HEADSET");
                    }
                    if (defaultAdapter.getProfileConnectionState(2) == 2 && !C0178j.m217a(jSONArray, "BT-PROFILE: A2DP")) {
                        jSONArray.put("BT-PROFILE: A2DP");
                    }
                    if (defaultAdapter.getProfileConnectionState(3) == 2 && !C0178j.m217a(jSONArray, "BT-PROFILE: HEALTH")) {
                        jSONArray.put("BT-PROFILE: HEALTH");
                    }
                    if (Build.VERSION.SDK_INT >= 18) {
                        if (defaultAdapter.getProfileConnectionState(7) == 2 && !C0178j.m217a(jSONArray, "BT-PROFILE: GATT")) {
                            jSONArray.put("BT-PROFILE: GATT");
                        }
                        if (defaultAdapter.getProfileConnectionState(8) == 2 && !C0178j.m217a(jSONArray, "BT-PROFILE: GATT_SERVER")) {
                            jSONArray.put("BT-PROFILE: GATT_SERVER");
                        }
                    }
                    if (Build.VERSION.SDK_INT >= 23 && defaultAdapter.getProfileConnectionState(10) == 2 && !C0178j.m217a(jSONArray, "BT-PROFILE: SAP")) {
                        jSONArray.put("BT-PROFILE: SAP");
                    }
                }
                return jSONArray;
            }
            return jSONArray;
        } catch (Throwable th) {
            C0174f.m16a(4, "DPT", "devicesBtPair", th);
        }
    }

    @Contract(pure = true)
    @AnyThread
    @Nullable
    /* JADX INFO: renamed from: k */
    private static Integer m169k(@NonNull Context context) {
        Display defaultDisplay;
        Point point;
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null || (defaultDisplay = windowManager.getDefaultDisplay()) == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 17) {
            point = new Point();
            defaultDisplay.getRealSize(point);
        } else {
            point = new Point();
            defaultDisplay.getSize(point);
        }
        return Integer.valueOf(point.x);
    }

    @Contract(pure = true)
    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: l */
    private static String m170l(@NonNull Context context) {
        return context.getPackageName();
    }

    @Contract(pure = true)
    @AnyThread
    /* JADX INFO: renamed from: m */
    private static int m171m(@NonNull Context context) {
        return (int) (context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime / 1000);
    }

    @Contract(pure = true)
    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: n */
    private static String m172n(@NonNull Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString() + " " + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
    }

    @Contract(pure = true)
    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: o */
    private static String m173o(@NonNull Context context) {
        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
    }

    @Contract(pure = true)
    @NonNull
    @SuppressLint({"HardwareIds"})
    @AnyThread
    /* JADX INFO: renamed from: p */
    private static String m174p(@NonNull Context context) {
        return Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

    @WorkerThread
    @Contract(pure = true)
    /* JADX INFO: renamed from: q */
    private static boolean m175q(@NonNull Context context) {
        try {
            return m176r(context);
        } catch (UnsupportedOperationException e) {
            C0174f.m16a(4, "DPT", "getDeviceLimi", e);
            return m177s(context);
        }
    }

    @Contract(pure = true)
    @AnyThread
    /* JADX INFO: renamed from: r */
    private static boolean m176r(@NonNull Context context) {
        try {
            int i = Settings.Secure.getInt(context.getContentResolver(), "limit_ad_tracking", -1);
            if (i >= 0) {
                C0174f.m16a(5, "DPT", "getFireDevice", "Kindle Fire");
                return i != 0;
            }
        } catch (Throwable th) {
            C0174f.m16a(4, "DPT", "getFireDevice", th);
        }
        throw new UnsupportedOperationException("Unsupported Device");
    }

    @WorkerThread
    @Contract(pure = true)
    /* JADX INFO: renamed from: s */
    private static boolean m177s(@NonNull Context context) {
        try {
            AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
            if (advertisingIdInfo != null) {
                return advertisingIdInfo.isLimitAdTrackingEnabled();
            }
        } catch (Throwable th) {
            C0174f.m16a(4, "DPT", "getGoogleDevi", th);
        }
        throw new UnsupportedOperationException("Unsupported Device");
    }

    @WorkerThread
    @Contract(pure = true)
    @Nullable
    /* JADX INFO: renamed from: t */
    private static String m178t(@NonNull Context context) {
        int columnIndex;
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.facebook.katana.provider.AttributionIdProvider"), new String[]{"aid"}, null, null, null);
        String string = null;
        if (cursorQuery == null) {
            return null;
        }
        if (cursorQuery.moveToFirst() && (columnIndex = cursorQuery.getColumnIndex("aid")) != -1) {
            string = cursorQuery.getString(columnIndex);
        }
        cursorQuery.close();
        return string;
    }

    @Contract(pure = true)
    @Nullable
    @AnyThread
    @RequiresPermission("android.permission.GET_ACCOUNTS")
    /* JADX INFO: renamed from: u */
    private static JSONObject m179u(@NonNull Context context) {
        if (!C0178j.m214a(context, "android.permission.GET_ACCOUNTS")) {
            C0174f.m16a(4, "DPT", "ids", "Missing Permission: android.permission.GET_ACCOUNTS");
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        try {
            Class<?> cls = Class.forName("android.accounts.AccountManager");
            Field field = Class.forName("android.accounts.Account").getField(AppMeasurementSdk.ConditionalUserProperty.NAME);
            Object objInvoke = cls.getMethod("get", Context.class).invoke(null, context);
            for (Object obj : (Object[]) objInvoke.getClass().getMethod("getAccounts", new Class[0]).invoke(objInvoke, new Object[0])) {
                String str = (String) field.get(obj);
                if (Patterns.EMAIL_ADDRESS.matcher(str).matches()) {
                    C0178j.m208a((Object) str, jSONArray, false);
                }
            }
        } catch (Throwable th) {
            C0174f.m16a(4, "DPT", "getIds", th);
        }
        if (jSONArray.length() <= 0) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        C0178j.m209a("email", C0178j.m206a(jSONArray).replaceAll("\"", ""), jSONObject);
        return jSONObject;
    }

    @Contract(pure = true)
    @AnyThread
    /* JADX INFO: renamed from: v */
    private static int m180v(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    @Contract(pure = true)
    @AnyThread
    @Nullable
    /* JADX INFO: renamed from: w */
    private static Double m181w(@NonNull Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (displayMetrics == null) {
            return null;
        }
        double dRound = Math.round(Math.sqrt(Math.pow(displayMetrics.widthPixels / displayMetrics.xdpi, 2.0d) + Math.pow(displayMetrics.heightPixels / displayMetrics.ydpi, 2.0d)) * 10.0d);
        Double.isNaN(dRound);
        return Double.valueOf(dRound / 10.0d);
    }

    @Contract(pure = true)
    @AnyThread
    @Nullable
    /* JADX INFO: renamed from: x */
    private static String m182x(@NonNull Context context) {
        Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (intentRegisterReceiver == null || !intentRegisterReceiver.hasExtra(NotificationCompat.CATEGORY_STATUS)) {
            return null;
        }
        int intExtra = intentRegisterReceiver.getIntExtra(NotificationCompat.CATEGORY_STATUS, -1);
        if (intExtra == 2) {
            return "charging";
        }
        if (intExtra == 3) {
            return "discharging";
        }
        if (intExtra != 4) {
            return intExtra != 5 ? EnvironmentCompat.MEDIA_UNKNOWN : "full";
        }
        return "not_charging";
    }

    @Contract(pure = true)
    @AnyThread
    @Nullable
    /* JADX INFO: renamed from: y */
    private static Integer m183y(@NonNull Context context) {
        Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (intentRegisterReceiver == null || !intentRegisterReceiver.hasExtra(FirebaseAnalytics.Param.LEVEL)) {
            return null;
        }
        return Integer.valueOf(C0178j.m200a(intentRegisterReceiver.getIntExtra(FirebaseAnalytics.Param.LEVEL, -1), 0, 100));
    }

    /* JADX WARN: Code duplicated, block: B:43:0x008f A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:44:0x0090  */
    @Contract(pure = true)
    @AnyThread
    @Nullable
    /* JADX INFO: renamed from: z */
    private static Integer m184z(@NonNull Context context) {
        TelephonyManager telephonyManager;
        List<CellInfo> allCellInfo;
        int level;
        if (!(C0178j.m214a(context, "android.permission.ACCESS_COARSE_LOCATION") || C0178j.m214a(context, "android.permission.ACCESS_FINE_LOCATION")) || Build.VERSION.SDK_INT < 17 || (telephonyManager = (TelephonyManager) context.getSystemService("phone")) == null || (allCellInfo = telephonyManager.getAllCellInfo()) == null) {
            return null;
        }
        for (CellInfo cellInfo : allCellInfo) {
            if (cellInfo.isRegistered()) {
                if (cellInfo instanceof CellInfoGsm) {
                    level = ((CellInfoGsm) cellInfo).getCellSignalStrength().getLevel();
                } else if (cellInfo instanceof CellInfoCdma) {
                    level = ((CellInfoCdma) cellInfo).getCellSignalStrength().getLevel();
                } else if (cellInfo instanceof CellInfoLte) {
                    level = ((CellInfoLte) cellInfo).getCellSignalStrength().getLevel();
                } else if (Build.VERSION.SDK_INT >= 18 && (cellInfo instanceof CellInfoWcdma)) {
                    level = ((CellInfoWcdma) cellInfo).getCellSignalStrength().getLevel();
                }
                if (level == -1) {
                    return null;
                }
                return Integer.valueOf(C0178j.m200a(level * 25, 0, 100));
            }
        }
        level = -1;
        if (level == -1) {
            return null;
        }
        return Integer.valueOf(C0178j.m200a(level * 25, 0, 100));
    }

    @AnyThread
    @Nullable
    /* JADX INFO: renamed from: a */
    final Object m185a(@NonNull C0178j c0178j, boolean z, boolean z2) {
        Object objM237b = c0178j.m237b(this.f82b);
        if (objM237b == null) {
            return null;
        }
        if (z) {
            return objM237b;
        }
        if (this.f83c == -1) {
            if (z2) {
                return objM237b;
            }
            return null;
        }
        Integer numM226c = C0178j.m226c(c0178j.m237b(this.f82b + "_ts"));
        if (numM226c == null || numM226c.intValue() + this.f83c < C0178j.m202a() / 1000) {
            return null;
        }
        return objM237b;
    }
}
