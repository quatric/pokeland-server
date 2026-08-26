package com.p001a.p002a;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnyThread;
import android.support.annotation.CheckResult;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.json.Json;
import com.google.common.net.HttpHeaders;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.jetbrains.annotations.Contract;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.a.a.j */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0178j extends SQLiteOpenHelper {

    /* JADX INFO: renamed from: b */
    @NonNull
    private static final Object f95b = new Object();

    /* JADX INFO: renamed from: c */
    @NonNull
    private static final Object f96c = new Object();

    /* JADX INFO: renamed from: a */
    @Nullable
    SQLiteDatabase f97a;

    /* JADX INFO: renamed from: d */
    @NonNull
    private final SharedPreferences f98d;

    /* JADX INFO: renamed from: e */
    private int f99e;

    /* JADX INFO: renamed from: f */
    private int f100f;

    @AnyThread
    C0178j(@NonNull Context context) {
        super(context, "kodb", (SQLiteDatabase.CursorFactory) null, 5);
        this.f97a = null;
        this.f99e = -1;
        this.f100f = -1;
        C0174f.m16a(5, "DAB", "Database", new Object[0]);
        this.f98d = context.getSharedPreferences("kosp", 0);
        m228c(context);
    }

    @Contract(pure = true)
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: a */
    static double m198a(double d, double d2, double d3) {
        if (d < d2) {
            return d2;
        }
        return d > d3 ? d3 : d;
    }

    @Contract(pure = true)
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: a */
    static double m199a(@Nullable Object obj, double d) {
        Double dM230d = m230d(obj);
        return dM230d != null ? dM230d.doubleValue() : d;
    }

    @Contract(pure = true)
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: a */
    static int m200a(int i, int i2, int i3) {
        if (i < i2) {
            return i2;
        }
        return i > i3 ? i3 : i;
    }

    @Contract(pure = true)
    @AnyThread
    /* JADX INFO: renamed from: a */
    static int m201a(@Nullable Object obj, int i) {
        if (obj instanceof Integer) {
            int iIntValue = ((Integer) obj).intValue();
            if (iIntValue >= 0 && iIntValue <= 5) {
                return iIntValue;
            }
        } else if (obj instanceof String) {
            String str = (String) obj;
            if ("NONE".equalsIgnoreCase(str)) {
                return 0;
            }
            if ("ERROR".equalsIgnoreCase(str)) {
                return 1;
            }
            if ("WARN".equalsIgnoreCase(str)) {
                return 2;
            }
            if ("INFO".equalsIgnoreCase(str)) {
                return 3;
            }
            if ("DEBUG".equalsIgnoreCase(str)) {
                return 4;
            }
            if (HttpMethods.TRACE.equalsIgnoreCase(str)) {
                return 5;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: a */
    static long m202a() {
        return System.currentTimeMillis();
    }

    @Contract(pure = true, value = "null -> null")
    @Nullable
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: a */
    static String m203a(@Nullable Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if ((obj instanceof JSONObject) || (obj instanceof JSONArray)) {
            return obj.toString();
        }
        return null;
    }

    @Contract(pure = true)
    @NonNull
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: a */
    static String m204a(@Nullable Object obj, @NonNull String str) {
        String strM203a = m203a(obj);
        return strM203a != null ? strM203a : str;
    }

    @WorkerThread
    @NonNull
    /* JADX INFO: renamed from: a */
    static String m205a(@NonNull String str, @NonNull String str2) throws IOException {
        String property;
        BufferedReader bufferedReader;
        StringBuilder sb;
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        try {
            property = System.getProperty("http.agent");
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    bufferedReader.close();
                    String string = sb.toString();
                    C0174f.m16a(4, "DAB", "post", "RECEIVE>", property, str, string, "<RECEIVE");
                    httpURLConnection.disconnect();
                    return string;
                }
                sb.append(line);
            }
        } catch (Exception e) {
            C0174f.m16a(4, "DAB", "post", e);
            property = null;
        }
        if (property != null && !property.trim().isEmpty()) {
            httpURLConnection.setRequestProperty(HttpHeaders.USER_AGENT, property);
        }
        Object[] objArr = new Object[5];
        objArr[0] = "SEND>";
        objArr[1] = property != null ? property : "Unable to gather UserAgent";
        objArr[2] = str;
        objArr[3] = str2;
        objArr[4] = "<SEND";
        C0174f.m16a(4, "DAB", "post", objArr);
        httpURLConnection.setRequestProperty(HttpHeaders.CONTENT_TYPE, Json.MEDIA_TYPE);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(30000);
        httpURLConnection.setReadTimeout(30000);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.connect();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8");
        outputStreamWriter.write(str2);
        outputStreamWriter.close();
        bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
        sb = new StringBuilder();
    }

    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: a */
    static String m206a(@NonNull JSONArray jSONArray) {
        String string;
        try {
            string = jSONArray.toString();
        } catch (Throwable th) {
            C0174f.m16a(2, "DAB", "jsonArrayToSt", th);
            string = null;
        }
        return string == null ? "[]" : string;
    }

    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: a */
    static String m207a(@NonNull JSONObject jSONObject) {
        String string;
        try {
            string = jSONObject.toString();
        } catch (Throwable th) {
            C0174f.m16a(2, "DAB", "jsonObjectToS", th);
            string = null;
        }
        return string == null ? "{}" : string;
    }

    @AnyThread
    /* JADX INFO: renamed from: a */
    static void m208a(@Nullable Object obj, @NonNull JSONArray jSONArray, boolean z) {
        if (obj == null) {
            return;
        }
        if (!z) {
            for (int i = 0; i < jSONArray.length(); i++) {
                try {
                    if (obj.equals(jSONArray.opt(i))) {
                        return;
                    }
                } catch (Throwable th) {
                    C0174f.m16a(4, "DAB", "putJsonObject", obj, th);
                    return;
                }
            }
        }
        jSONArray.put(obj);
    }

    @AnyThread
    /* JADX INFO: renamed from: a */
    static void m209a(@Nullable String str, @Nullable Object obj, @NonNull JSONObject jSONObject) {
        m210a(str, obj, jSONObject, 2);
    }

    @AnyThread
    /* JADX INFO: renamed from: a */
    static void m210a(@Nullable String str, @Nullable Object obj, @NonNull JSONObject jSONObject, int i) {
        Object objM231e;
        if (str == null || obj == null || str.trim().isEmpty()) {
            C0174f.m16a(i, "DAB", "putJsonObject", "Invalid: " + str + " " + obj);
            return;
        }
        try {
            if (!(obj instanceof Boolean) && !(obj instanceof Number) && !(obj instanceof JSONObject) && !(obj instanceof JSONArray) && !(obj instanceof String)) {
                if (obj instanceof Date) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    objM231e = simpleDateFormat.format((Date) obj);
                } else if ((obj instanceof Bundle) || (obj instanceof Map)) {
                    objM231e = m231e(obj);
                } else {
                    objM231e = ((obj instanceof Collection) || obj.getClass().isArray()) ? m232f(obj) : obj.toString();
                }
                jSONObject.put(str, objM231e);
                return;
            }
            jSONObject.put(str, obj);
        } catch (Throwable th) {
            C0174f.m16a(i, "DAB", "putJsonObject", str + " " + obj, th);
        }
    }

    @AnyThread
    /* JADX INFO: renamed from: a */
    static void m211a(@NonNull JSONArray jSONArray, @NonNull JSONArray jSONArray2) {
        for (int i = 0; i < jSONArray2.length(); i++) {
            try {
                jSONArray.put(jSONArray2.opt(i));
            } catch (Throwable th) {
                C0174f.m16a(5, "DAB", "mergeJsonArra", th);
            }
        }
    }

    @AnyThread
    /* JADX INFO: renamed from: a */
    private void m212a(boolean z, @NonNull String str, @Nullable String str2) {
        C0174f.m16a(4, "DAB", "applySdkUpgra", Boolean.valueOf(z), str, str2);
        if (z) {
            m236a("initial_ever_sent", (Object) true);
            m236a("initial_needs_sent", (Object) false);
        }
        m236a("kochava_device_id", (Object) str);
        if (str2 != null) {
            m236a("attribution", (Object) C0185q.m290a(str2));
        }
    }

    /* JADX INFO: renamed from: a */
    static boolean m213a(@NonNull Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        String packageName = context.getPackageName();
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        if (activityManager == null || packageName == null || (runningAppProcesses = activityManager.getRunningAppProcesses()) == null || runningAppProcesses.size() == 0) {
            return true;
        }
        Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
        while (true) {
            if (!it.hasNext()) {
                return false;
            }
            ActivityManager.RunningAppProcessInfo next = it.next();
            if (next != null && next.importance == 100) {
                for (String str : next.pkgList) {
                    if (packageName.equals(str)) {
                        return true;
                    }
                }
            }
        }
    }

    @Contract(pure = true)
    @AnyThread
    /* JADX INFO: renamed from: a */
    static boolean m214a(@NonNull Context context, @NonNull String str) {
        if (m224b(context, str)) {
            return Build.VERSION.SDK_INT < 23 || context.checkSelfPermission(str) == 0;
        }
        return false;
    }

    @Contract(pure = true, value = "null, _ -> false; _ , null -> false")
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: a */
    static boolean m215a(@Nullable Object obj, @Nullable Object obj2) {
        if ((obj instanceof Boolean) && (obj2 instanceof Boolean)) {
            return obj.equals(obj2);
        }
        if ((obj instanceof Integer) && (obj2 instanceof Integer)) {
            return obj.equals(obj2);
        }
        if ((obj instanceof Long) && (obj2 instanceof Long)) {
            return obj.equals(obj2);
        }
        if ((obj instanceof Float) && (obj2 instanceof Float)) {
            return obj.equals(obj2);
        }
        if ((obj instanceof Double) && (obj2 instanceof Double)) {
            return Double.compare(((Double) obj).doubleValue(), ((Double) obj2).doubleValue()) == 0;
        }
        if ((obj instanceof String) && (obj2 instanceof String)) {
            return obj.equals(obj2);
        }
        if ((obj instanceof JSONObject) && (obj2 instanceof JSONObject)) {
            return m229c((JSONObject) obj, (JSONObject) obj2);
        }
        if ((obj instanceof JSONArray) && (obj2 instanceof JSONArray)) {
            return m225b((JSONArray) obj, (JSONArray) obj2);
        }
        return false;
    }

    @Contract(pure = true)
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: a */
    static boolean m216a(@Nullable Object obj, boolean z) {
        Boolean boolM220b = m220b(obj);
        return boolM220b != null ? boolM220b.booleanValue() : z;
    }

    @Contract(pure = true, value = "null, _ -> false; _ , null -> false")
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: a */
    static boolean m217a(@Nullable JSONArray jSONArray, @Nullable String str) {
        if (jSONArray != null && str != null) {
            for (int i = 0; i < jSONArray.length(); i++) {
                if (str.equalsIgnoreCase(m203a(jSONArray.opt(i)))) {
                    return true;
                }
            }
        }
        return false;
    }

    @AnyThread
    /* JADX INFO: renamed from: a */
    static boolean m218a(@NonNull JSONObject jSONObject, @NonNull JSONObject jSONObject2) {
        Iterator<String> itKeys = jSONObject2.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            if (!m215a(jSONObject2.opt(next), jSONObject.opt(next))) {
                return false;
            }
        }
        return true;
    }

    @Contract(pure = true)
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: b */
    static int m219b(@Nullable Object obj, int i) {
        Integer numM226c = m226c(obj);
        return numM226c != null ? numM226c.intValue() : i;
    }

    @Contract(pure = true, value = "null -> null")
    @Nullable
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: b */
    static Boolean m220b(@Nullable Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (Boolean.toString(true).equalsIgnoreCase(str) || Integer.toString(1).equalsIgnoreCase(str)) {
                return true;
            }
            if (Boolean.toString(false).equalsIgnoreCase(str) || Integer.toString(0).equalsIgnoreCase(str)) {
                return false;
            }
        }
        if (!(obj instanceof Integer)) {
            return null;
        }
        Integer num = (Integer) obj;
        if (1 == num.intValue()) {
            return true;
        }
        return num.intValue() == 0 ? false : null;
    }

    @Contract(pure = true, value = "_,true -> !null; null,false -> null")
    @Nullable
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: b */
    static JSONObject m221b(@Nullable Object obj, boolean z) {
        JSONObject jSONObjectM231e = m231e(obj);
        return (jSONObjectM231e == null && z) ? new JSONObject() : jSONObjectM231e;
    }

    @AnyThread
    /* JADX INFO: renamed from: b */
    static void m222b(@NonNull JSONObject jSONObject, @NonNull JSONObject jSONObject2) {
        Iterator<String> itKeys = jSONObject2.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            Object objOpt = jSONObject2.opt(next);
            if (objOpt != null) {
                try {
                    jSONObject.put(next, objOpt);
                } catch (Throwable th) {
                    C0174f.m16a(5, "DAB", "mergeJsonObje", th);
                }
            }
        }
    }

    @AnyThread
    /* JADX INFO: renamed from: b */
    static boolean m223b(@NonNull Context context) {
        NetworkInfo activeNetworkInfo;
        if (!m224b(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return true;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnectedOrConnecting()) ? false : true;
        } catch (Throwable th) {
            C0174f.m16a(4, "DAB", "hasNetworkCon", th);
            return true;
        }
    }

    @Contract(pure = true)
    @AnyThread
    /* JADX INFO: renamed from: b */
    static boolean m224b(@NonNull Context context, @NonNull String str) {
        return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
    }

    @Contract(pure = true, value = "null, _ -> false; _ , null -> false")
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: b */
    static boolean m225b(@Nullable JSONArray jSONArray, @Nullable JSONArray jSONArray2) {
        boolean z;
        if (jSONArray == null || jSONArray2 == null || jSONArray.length() != jSONArray2.length()) {
            return false;
        }
        if (jSONArray.length() == 0) {
            return true;
        }
        boolean[] zArr = new boolean[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            Object objOpt = jSONArray.opt(i);
            int i2 = 0;
            while (true) {
                if (i2 >= jSONArray2.length()) {
                    z = false;
                    break;
                }
                if (!zArr[i2] && m215a(objOpt, jSONArray2.opt(i2))) {
                    zArr[i2] = true;
                    z = true;
                    break;
                }
                i2++;
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    @Contract(pure = true, value = "null -> null")
    @Nullable
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: c */
    static Integer m226c(@Nullable Object obj) {
        if (obj instanceof Number) {
            return Integer.valueOf(((Number) obj).intValue());
        }
        if (!(obj instanceof String)) {
            return null;
        }
        try {
            return Integer.valueOf(Integer.parseInt((String) obj));
        } catch (Throwable unused) {
            return null;
        }
    }

    @Contract(pure = true, value = "_,true -> !null; null,false -> null")
    @Nullable
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: c */
    static JSONArray m227c(@Nullable Object obj, boolean z) {
        JSONArray jSONArrayM232f = m232f(obj);
        return (jSONArrayM232f == null && z) ? new JSONArray() : jSONArrayM232f;
    }

    @AnyThread
    /* JADX INFO: renamed from: c */
    private void m228c(@NonNull Context context) {
        int i;
        int i2;
        if (m216a(m237b("has_upgraded"), false)) {
            C0174f.m16a(4, "DAB", "upgradeSdk", "Skip");
            return;
        }
        m236a("has_upgraded", (Object) true);
        int i3 = 2;
        try {
            if (context.getPackageManager().getReceiverInfo(new ComponentName(context, "com.kochava.android.tracker.ReferralCapture"), 0) != null) {
                C0174f.m16a(1, "DAB", "upgradeSdk", "Legacy Broadcast Receiver found. Remove the following from your manifest!", "<receiver android:name=\"com.kochava.android.tracker.ReferralCapture\"\n    android:exported=\"true\">\n    <intent-filter>\n        <action android:name=\"com.android.vending.INSTALL_REFERRER\" />\n    </intent-filter>\n</receiver>");
            }
        } catch (Throwable unused) {
            C0174f.m16a(5, "DAB", "upgradeSdk", "Legacy broadcast receiver correctly removed");
        }
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("ko.tr", 0);
            try {
                SharedPreferences sharedPreferences2 = context.getSharedPreferences("ko.dt.pt", 0);
                boolean z = sharedPreferences.getBoolean("initial_sent", false) && !sharedPreferences.contains("initial");
                String string = sharedPreferences.getString("attribution_data", null);
                String string2 = sharedPreferences2.getString("kochava_device_id", null);
                if (string2 != null) {
                    C0174f.m16a(4, "DAB", "upgradeSdk", "2017");
                    m212a(z, string2.replace("STR::", ""), string);
                    return;
                }
                i = 0;
            } catch (Exception e) {
                e = e;
                i3 = 2;
                Object[] objArr = new Object[i3];
                i = 0;
                objArr[0] = "2017";
                objArr[1] = e;
                C0174f.m16a(i3, "DAB", "upgradeSdk", objArr);
            }
        } catch (Exception e2) {
            e = e2;
        }
        try {
            SharedPreferences sharedPreferences3 = context.getSharedPreferences("initPrefs", i);
            SharedPreferences sharedPreferences4 = context.getSharedPreferences("attributionPref", i);
            boolean zEqualsIgnoreCase = "true".equalsIgnoreCase(sharedPreferences3.getString("initBool", ""));
            String string3 = sharedPreferences4.getString("attributionData", null);
            String string4 = sharedPreferences3.getString("kochava_app_id_generated", null);
            if (string4 != null) {
                C0174f.m16a(4, "DAB", "upgradeSdk", "2016");
                m212a(zEqualsIgnoreCase, string4, string3);
                return;
            }
            i2 = 0;
        } catch (Exception e3) {
            i2 = 0;
            C0174f.m16a(2, "DAB", "upgradeSdk", "2016", e3);
        }
        try {
            SharedPreferences sharedPreferences5 = context.getSharedPreferences(context.getPackageName(), i2);
            boolean zContains = sharedPreferences5.contains("watchlistProperties");
            String string5 = sharedPreferences5.getString("kochava_queue_storage", null);
            boolean z2 = zContains && (string5 == null || !string5.contains("initial"));
            String string6 = sharedPreferences5.getString("attribution", null);
            String string7 = sharedPreferences5.getString("kochava_device_id", null);
            if (string7 != null) {
                C0174f.m16a(4, "DAB", "upgradeSdk", "unityV1_1");
                m212a(z2, string7, string6);
                return;
            }
        } catch (Exception e4) {
            C0174f.m16a(2, "DAB", "upgradeSdk", "unityV1_1", e4);
        }
        try {
            SharedPreferences sharedPreferences6 = context.getSharedPreferences(context.getPackageName() + ".v2.playerprefs", 0);
            boolean zContains2 = sharedPreferences6.contains("watchlistProperties");
            String string8 = sharedPreferences6.getString("kochava_queue_storage", null);
            boolean z3 = zContains2 && (string8 == null || !string8.contains("initial"));
            String string9 = sharedPreferences6.getString("attribution", null);
            String string10 = sharedPreferences6.getString("kochava_device_id", null);
            if (string10 != null) {
                C0174f.m16a(4, "DAB", "upgradeSdk", "unityV1_2");
                m212a(z3, string10, string9);
            }
        } catch (Exception e5) {
            C0174f.m16a(2, "DAB", "upgradeSdk", "unityV1_2", e5);
        }
    }

    @Contract(pure = true, value = "null, _ -> false; _ , null -> false")
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: c */
    static boolean m229c(@Nullable JSONObject jSONObject, @Nullable JSONObject jSONObject2) {
        if (jSONObject == null || jSONObject2 == null || jSONObject.length() != jSONObject2.length()) {
            return false;
        }
        if (jSONObject.length() == 0) {
            return true;
        }
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            if (!m215a(jSONObject.opt(next), jSONObject2.opt(next))) {
                return false;
            }
        }
        return true;
    }

    @Contract(pure = true, value = "null -> null")
    @Nullable
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: d */
    static Double m230d(@Nullable Object obj) {
        if (obj instanceof Number) {
            return Double.valueOf(((Number) obj).doubleValue());
        }
        if (!(obj instanceof String)) {
            return null;
        }
        try {
            return Double.valueOf(Double.parseDouble((String) obj));
        } catch (Throwable unused) {
            return null;
        }
    }

    @Contract(pure = true, value = "null -> null")
    @Nullable
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: e */
    static JSONObject m231e(@Nullable Object obj) {
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        if (!(obj instanceof Bundle)) {
            try {
                if (obj instanceof String) {
                    return new JSONObject((String) obj);
                }
                if (obj instanceof Map) {
                    return new JSONObject((Map) obj);
                }
                return null;
            } catch (Throwable unused) {
                return null;
            }
        }
        Bundle bundle = (Bundle) obj;
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            m209a(str, bundle.get(str), jSONObject);
        }
        return jSONObject;
    }

    @Contract(pure = true, value = "null -> null")
    @Nullable
    @CheckResult
    @AnyThread
    /* JADX INFO: renamed from: f */
    static JSONArray m232f(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        if (obj instanceof Collection) {
            return new JSONArray((Collection) obj);
        }
        try {
            if (obj instanceof String) {
                return new JSONArray((String) obj);
            }
            if (obj.getClass().isArray()) {
                JSONArray jSONArray = new JSONArray();
                int length = Array.getLength(obj);
                for (int i = 0; i < length; i++) {
                    jSONArray.put(Array.get(obj, i));
                }
                return jSONArray;
            }
            return null;
        } catch (Throwable unused) {
        }
    }

    @WorkerThread
    @CheckResult
    @NonNull
    /* JADX INFO: renamed from: h */
    private SQLiteDatabase m233h() {
        SQLiteDatabase sQLiteDatabase = this.f97a;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            C0174f.m16a(4, "DAB", "openDb", "Opening");
            this.f97a = getWritableDatabase();
            if (Build.VERSION.SDK_INT <= 16) {
                this.f97a.setLockingEnabled(false);
            }
            this.f100f = (int) DatabaseUtils.queryNumEntries(this.f97a, "events");
            this.f99e = (int) DatabaseUtils.queryNumEntries(this.f97a, "updates");
        } else {
            C0174f.m16a(5, "DAB", "openDb", "Already Open");
        }
        return this.f97a;
    }

    @WorkerThread
    /* JADX INFO: renamed from: a */
    final void m234a(@IntRange(from = 1) int i) {
        synchronized (f95b) {
            C0174f.m16a(4, "DAB", "removeEvent", Integer.toString(i));
            if (this.f100f > 0) {
                try {
                    int iDelete = m233h().delete("events", "_id IN (SELECT _id FROM events ORDER BY _id ASC LIMIT ?)", new String[]{Integer.toString(m200a(i, 1, m219b(m237b("batch_max_quantity"), 25)))});
                    if (iDelete > 0) {
                        this.f100f -= iDelete;
                    }
                } catch (SQLiteException e) {
                    C0174f.m16a(4, "DAB", "removeEvent", e);
                }
            } else {
                C0174f.m16a(2, "DAB", "removeEvent", "No events to remove");
            }
        }
    }

    @AnyThread
    /* JADX INFO: renamed from: a */
    final void m235a(@NonNull String str) {
        synchronized (f96c) {
            this.f98d.edit().remove(str).apply();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @AnyThread
    /* JADX INFO: renamed from: a */
    final void m236a(@NonNull String str, @NonNull Object obj) {
        SharedPreferences.Editor editorPutString;
        synchronized (f96c) {
            if (obj instanceof Boolean) {
                editorPutString = this.f98d.edit().putBoolean(str, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Integer) {
                editorPutString = this.f98d.edit().putInt(str, ((Integer) obj).intValue());
            } else if (obj instanceof Float) {
                editorPutString = this.f98d.edit().putFloat(str, ((Float) obj).floatValue());
            } else if (obj instanceof Double) {
                editorPutString = this.f98d.edit().putLong(str, Double.doubleToRawLongBits(((Double) obj).doubleValue()));
            } else if (obj instanceof String) {
                editorPutString = this.f98d.edit().putString(str, "STR::" + obj);
            } else if (obj instanceof JSONObject) {
                editorPutString = this.f98d.edit().putString(str, "JSO::" + obj.toString());
            } else if (obj instanceof JSONArray) {
                editorPutString = this.f98d.edit().putString(str, "JSA::" + obj.toString());
            } else {
                C0174f.m16a(2, "DAB", "put", str + " Unrecognized Type");
            }
            editorPutString.apply();
        }
    }

    @Contract(pure = true)
    @AnyThread
    @Nullable
    /* JADX INFO: renamed from: b */
    final Object m237b(@NonNull String str) {
        synchronized (f96c) {
            Object obj = this.f98d.getAll().get(str);
            if (obj instanceof Boolean) {
                return obj;
            }
            if (obj instanceof Integer) {
                return obj;
            }
            if (obj instanceof Float) {
                return obj;
            }
            if (obj instanceof Long) {
                return Double.valueOf(Double.longBitsToDouble(((Long) obj).longValue()));
            }
            if (obj instanceof String) {
                String str2 = (String) obj;
                if (str2.startsWith("STR::")) {
                    return str2.substring(5);
                }
                try {
                    if (str2.startsWith("JSO::")) {
                        return new JSONObject(str2.substring(5));
                    }
                    if (str2.startsWith("JSA::")) {
                        return new JSONArray(str2.substring(5));
                    }
                } catch (JSONException e) {
                    C0174f.m16a(4, "DAB", "get", e);
                }
            }
            if (obj != null) {
                C0174f.m16a(2, "DAB", "get", str + " Unrecognized Type");
            }
            return null;
        }
    }

    @WorkerThread
    /* JADX INFO: renamed from: b */
    final void m238b() {
        synchronized (f95b) {
            if (this.f97a == null || !this.f97a.isOpen()) {
                C0174f.m16a(5, "DAB", "closeDb", "Already Closed");
            } else {
                C0174f.m16a(4, "DAB", "closeDb", new Object[0]);
                try {
                    this.f97a.execSQL("VACUUM");
                } catch (SQLiteException e) {
                    C0174f.m16a(4, "DAB", "closeDb", e);
                }
                close();
                this.f97a = null;
            }
        }
    }

    @WorkerThread
    /* JADX INFO: renamed from: b */
    final void m239b(@NonNull JSONObject jSONObject) {
        synchronized (f95b) {
            C0174f.m16a(5, "DAB", "putEvent", jSONObject);
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("data", m207a(jSONObject));
                if (m233h().insert("events", null, contentValues) != -1) {
                    this.f100f++;
                }
            } catch (SQLiteException e) {
                C0174f.m16a(4, "DAB", "putEvent", e);
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @WorkerThread
    @Contract(pure = true)
    @NonNull
    @CheckResult
    /* JADX INFO: renamed from: c */
    final JSONArray m240c() {
        synchronized (f95b) {
            C0174f.m16a(5, "DAB", "takeEvent", new Object[0]);
            JSONArray jSONArray = new JSONArray();
            if (this.f100f == 0) {
                return jSONArray;
            }
            Cursor cursorRawQuery = null;
            try {
                try {
                    cursorRawQuery = m233h().rawQuery("SELECT data FROM events ORDER BY _id ASC LIMIT ?", new String[]{Integer.toString(m219b(m237b("batch_max_quantity"), 25))});
                    while (cursorRawQuery.moveToNext()) {
                        jSONArray.put(m231e(cursorRawQuery.getString(0)));
                    }
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                } catch (SQLiteException e) {
                    C0174f.m16a(4, "DAB", "takeEvent", e);
                    if (cursorRawQuery != null) {
                    }
                }
                C0174f.m16a(5, "DAB", "takeEvent", jSONArray);
                return jSONArray;
            } catch (Throwable th) {
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                throw th;
            }
        }
    }

    @WorkerThread
    /* JADX INFO: renamed from: c */
    final void m241c(@NonNull JSONObject jSONObject) {
        synchronized (f95b) {
            C0174f.m16a(5, "DAB", "putUpdate", jSONObject);
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("data", m207a(jSONObject));
                if (m233h().insert("updates", null, contentValues) != -1) {
                    this.f99e++;
                }
            } catch (SQLiteException e) {
                C0174f.m16a(4, "DAB", "putUpdate", e);
            }
        }
    }

    @WorkerThread
    @Contract(pure = true)
    @SuppressLint({"CheckResult"})
    @CheckResult
    /* JADX INFO: renamed from: d */
    final int m242d() {
        int i;
        synchronized (f95b) {
            m233h();
            C0174f.m16a(4, "DAB", "getEventCount", Integer.valueOf(this.f100f));
            i = this.f100f;
        }
        return i;
    }

    @WorkerThread
    /* JADX INFO: renamed from: e */
    final void m243e() {
        synchronized (f95b) {
            C0174f.m16a(4, "DAB", "removeUpdate", new Object[0]);
            if (this.f99e > 0) {
                try {
                    if (m233h().delete("updates", "_id IN (SELECT _id FROM updates ORDER BY _id ASC LIMIT 1)", null) > 0) {
                        this.f99e--;
                    }
                } catch (SQLiteException e) {
                    C0174f.m16a(4, "DAB", "removeUpdate", e);
                }
            } else {
                C0174f.m16a(2, "DAB", "removeUpdate", "No updates to remove");
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0057 A[Catch: all -> 0x0065, TryCatch #3 {, blocks: (B:4:0x0003, B:13:0x002e, B:25:0x0048, B:28:0x0057, B:29:0x005a, B:33:0x0063, B:32:0x005e), top: B:40:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x005d  */
    /* JADX WARN: Code duplicated, block: B:32:0x005e A[Catch: all -> 0x0065, TryCatch #3 {, blocks: (B:4:0x0003, B:13:0x002e, B:25:0x0048, B:28:0x0057, B:29:0x005a, B:33:0x0063, B:32:0x005e), top: B:40:0x0003 }] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v3, types: [android.database.Cursor] */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @WorkerThread
    @Contract(pure = true)
    @NonNull
    @CheckResult
    /* JADX INFO: renamed from: f */
    final JSONObject m244f() {
        JSONObject jSONObject;
        Cursor cursorRawQuery;
        synchronized (f95b) {
            ?? r4 = new Object[0];
            C0174f.m16a(5, "DAB", "takeUpdate", r4);
            jSONObject = null;
            if (this.f99e > 0) {
                try {
                    try {
                        cursorRawQuery = m233h().rawQuery("SELECT data FROM updates ORDER BY _id ASC LIMIT 1", null);
                        try {
                            jSONObject = cursorRawQuery.moveToFirst() ? m231e(cursorRawQuery.getString(0)) : null;
                            if (cursorRawQuery != null) {
                                cursorRawQuery.close();
                            }
                        } catch (SQLiteException e) {
                            e = e;
                            C0174f.m16a(4, "DAB", "takeUpdate", e);
                            if (cursorRawQuery != null) {
                            }
                            C0174f.m16a(5, "DAB", "takeUpdate", jSONObject);
                            if (jSONObject != null) {
                                jSONObject = new JSONObject();
                            }
                            return jSONObject;
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (r4 != 0) {
                            r4.close();
                        }
                        throw th;
                    }
                } catch (SQLiteException e2) {
                    e = e2;
                    cursorRawQuery = null;
                } catch (Throwable th2) {
                    th = th2;
                    r4 = 0;
                    if (r4 != 0) {
                        r4.close();
                    }
                    throw th;
                }
                C0174f.m16a(5, "DAB", "takeUpdate", jSONObject);
            }
            if (jSONObject != null) {
                jSONObject = new JSONObject();
            }
        }
        return jSONObject;
    }

    @WorkerThread
    @Contract(pure = true)
    @SuppressLint({"CheckResult"})
    @CheckResult
    /* JADX INFO: renamed from: g */
    final int m245g() {
        int i;
        synchronized (f95b) {
            m233h();
            C0174f.m16a(4, "DAB", "getUpdateCoun", Integer.valueOf(this.f99e));
            i = this.f99e;
        }
        return i;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    @WorkerThread
    public final void onCreate(@NonNull SQLiteDatabase sQLiteDatabase) {
        synchronized (f95b) {
            C0174f.m16a(5, "DAB", "onCreate", new Object[0]);
            sQLiteDatabase.execSQL("CREATE TABLE events (_id INTEGER PRIMARY KEY AUTOINCREMENT, data TEXT NOT NULL);");
            sQLiteDatabase.execSQL("CREATE TABLE updates (_id INTEGER PRIMARY KEY AUTOINCREMENT, data TEXT NOT NULL);");
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    @WorkerThread
    public final void onUpgrade(@NonNull SQLiteDatabase sQLiteDatabase, @IntRange(from = 0) int i, @IntRange(from = 0) int i2) {
        synchronized (f95b) {
            C0174f.m16a(5, "DAB", "onUpgrade", Integer.toString(i) + "," + Integer.toString(i2));
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS events");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS updates");
            onCreate(sQLiteDatabase);
            m238b();
        }
    }
}
