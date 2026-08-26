package com.nintendo.npf.sdk.internal.p022d;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.os.EnvironmentCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.deploygate.service.DeployGateEvent;
import com.metaps.common.C0854h;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p021c.C0932d;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.d.b */
/* JADX INFO: compiled from: Capabilities.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0947b {

    /* JADX INFO: renamed from: a */
    private static final String f1237a = "b";

    /* JADX INFO: renamed from: b */
    private static final C0932d f1238b = new C0932d(10000);

    /* JADX INFO: renamed from: c */
    private String f1239c;

    /* JADX INFO: renamed from: d */
    private boolean f1240d;

    /* JADX INFO: renamed from: e */
    private boolean f1241e;

    /* JADX INFO: renamed from: f */
    private boolean f1242f;

    /* JADX INFO: renamed from: g */
    private String f1243g;

    /* JADX INFO: renamed from: h */
    private String f1244h;

    /* JADX INFO: renamed from: i */
    private String f1245i;

    /* JADX INFO: renamed from: j */
    private boolean f1246j;

    /* JADX INFO: renamed from: k */
    private boolean f1247k;

    /* JADX INFO: renamed from: l */
    private int f1248l;

    /* JADX INFO: renamed from: m */
    private int f1249m;

    /* JADX INFO: renamed from: n */
    private String f1250n;

    /* JADX INFO: renamed from: o */
    private String f1251o;

    /* JADX INFO: renamed from: p */
    private String f1252p;

    /* JADX INFO: renamed from: q */
    private String f1253q;

    /* JADX INFO: renamed from: r */
    private String f1254r;

    /* JADX INFO: renamed from: s */
    private String f1255s;

    /* JADX INFO: renamed from: t */
    private String f1256t;

    /* JADX INFO: renamed from: u */
    private String f1257u;

    /* JADX INFO: renamed from: v */
    private String f1258v;

    /* JADX INFO: renamed from: w */
    private int f1259w;

    /* JADX INFO: renamed from: x */
    private Context f1260x;

    /* JADX INFO: renamed from: y */
    private boolean f1261y;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.d.b$a */
    /* JADX INFO: compiled from: Capabilities.java */
    private static class a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1262a = InterfaceC0875a.a.m1072b();
    }

    public C0947b() {
        this.f1241e = false;
        this.f1242f = false;
        this.f1246j = false;
        this.f1247k = false;
        this.f1248l = 10000;
        this.f1249m = 10000;
        this.f1259w = 180000;
        this.f1261y = false;
    }

    public C0947b(String str, boolean z, boolean z2, String str2, String str3, String str4, boolean z3, String str5, String str6, String str7, String str8, int i, boolean z4, int i2, int i3) {
        this.f1241e = false;
        this.f1242f = false;
        this.f1246j = false;
        this.f1247k = false;
        this.f1248l = 10000;
        this.f1249m = 10000;
        this.f1259w = 180000;
        this.f1261y = false;
        this.f1239c = str;
        this.f1241e = z;
        this.f1242f = z2;
        this.f1243g = str2;
        this.f1244h = str3;
        this.f1245i = str4;
        this.f1246j = z3;
        this.f1255s = str5;
        this.f1256t = str6;
        this.f1257u = str7;
        this.f1258v = str8;
        this.f1259w = i;
        this.f1247k = z4;
        this.f1248l = i2;
        this.f1249m = i3;
    }

    @SuppressLint({"PackageManagerGetSignatures"})
    /* JADX INFO: renamed from: H */
    private void m1305H() throws JSONException, IllegalAccessException, NoSuchMethodException, PackageManager.NameNotFoundException, NoSuchAlgorithmException, IOException, IllegalArgumentException, InvocationTargetException {
        C0947b c0947bM1266a = f1238b.mo1260b(new JSONObject(m1306I()));
        if (c0947bM1266a != null) {
            this.f1239c = c0947bM1266a.f1239c;
            this.f1241e = c0947bM1266a.f1241e;
            this.f1242f = c0947bM1266a.f1242f;
            this.f1243g = c0947bM1266a.f1243g;
            this.f1244h = c0947bM1266a.f1244h;
            this.f1245i = c0947bM1266a.f1245i;
            this.f1246j = c0947bM1266a.f1246j;
            this.f1255s = c0947bM1266a.f1255s;
            this.f1256t = c0947bM1266a.f1256t;
            this.f1257u = c0947bM1266a.f1257u;
            this.f1258v = c0947bM1266a.f1258v;
            this.f1259w = c0947bM1266a.f1259w;
            this.f1247k = c0947bM1266a.f1247k;
            this.f1248l = c0947bM1266a.f1248l;
            this.f1249m = c0947bM1266a.f1249m;
        }
        String str = this.f1256t;
        if (str == null || str.isEmpty()) {
            this.f1256t = "accounts.nintendo.com";
        }
        String str2 = this.f1257u;
        if (str2 == null || str2.isEmpty()) {
            this.f1257u = "api.accounts.nintendo.com";
        }
        if (this.f1259w < 180000) {
            this.f1259w = 180000;
        }
        this.f1240d = this.f1239c.contains("-sb");
        this.f1250n = this.f1260x.getPackageName();
        PackageManager packageManager = this.f1260x.getPackageManager();
        this.f1253q = packageManager.getPackageInfo(this.f1250n, 1).versionName;
        this.f1251o = m1309a(packageManager.getPackageInfo(this.f1250n, 64).signatures[0].toByteArray());
    }

    /* JADX INFO: renamed from: I */
    private String m1306I() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(this.f1260x.getResources().getAssets().open("npf.json"));
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            sb.append(line);
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: J */
    private boolean m1307J() {
        return this.f1260x != null;
    }

    /* JADX INFO: renamed from: K */
    private String m1308K() {
        Locale locale = Locale.getDefault();
        return m1318a(locale.getLanguage(), locale.getCountry());
    }

    /* JADX INFO: renamed from: a */
    private String m1309a(byte[] bArr) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.reset();
        messageDigest.update(bArr);
        return String.format("%040x", new BigInteger(1, messageDigest.digest()));
    }

    /* JADX INFO: renamed from: A */
    public String m1310A() {
        return this.f1254r;
    }

    /* JADX INFO: renamed from: B */
    public void m1311B() {
        C0948c c0948cMo1064r = a.f1262a.mo1064r();
        if (c0948cMo1064r.m1356a() == null || c0948cMo1064r.m1356a().isEmpty() || !m1307J()) {
            return;
        }
        this.f1254r = c0948cMo1064r.m1356a() + "-" + Calendar.getInstance().getTimeInMillis();
    }

    /* JADX INFO: renamed from: C */
    public boolean m1312C() {
        return this.f1240d;
    }

    /* JADX INFO: renamed from: D */
    public String m1313D() {
        return this.f1255s;
    }

    /* JADX INFO: renamed from: E */
    public int m1314E() {
        return this.f1259w;
    }

    /* JADX INFO: renamed from: F */
    public boolean m1315F() {
        return this.f1247k;
    }

    /* JADX INFO: renamed from: G */
    public JSONObject m1316G() {
        return f1238b.mo1259a(this);
    }

    /* JADX INFO: renamed from: a */
    public String m1317a() {
        return this.f1239c;
    }

    /* JADX INFO: renamed from: a */
    String m1318a(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        if (str.length() == 0 && str2.length() == 0) {
            return "en-US";
        }
        sb.append(str);
        if (str2.length() != 0) {
            sb.append("-");
            sb.append(str2);
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: a */
    public void m1319a(int i) {
        this.f1248l = i;
    }

    /* JADX INFO: renamed from: a */
    public void m1320a(Context context) {
        this.f1260x = context;
        try {
            m1305H();
            C0955e.m1393b(f1237a, "Finished Capabilities.init()");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException("Application is not managed in package manager.");
        } catch (IOException e2) {
            e2.printStackTrace();
            throw new IllegalStateException("npf.json file can't be found");
        } catch (IllegalAccessException e3) {
            e = e3;
            e.printStackTrace();
            throw new IllegalStateException("Failed to reflect the methods in old android version.");
        } catch (IllegalArgumentException e4) {
            e = e4;
            e.printStackTrace();
            throw new IllegalStateException("Failed to reflect the methods in old android version.");
        } catch (NoSuchMethodException e5) {
            e = e5;
            e.printStackTrace();
            throw new IllegalStateException("Failed to reflect the methods in old android version.");
        } catch (InvocationTargetException e6) {
            e = e6;
            e.printStackTrace();
            throw new IllegalStateException("Failed to reflect the methods in old android version.");
        } catch (NoSuchAlgorithmException e7) {
            e7.printStackTrace();
            throw new IllegalStateException("SHA-1 algorithm is not supported.");
        } catch (JSONException e8) {
            e8.printStackTrace();
            throw new IllegalStateException("npf.json is invalid JSON file.");
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1321a(String str) {
        this.f1252p = str;
    }

    /* JADX INFO: renamed from: a */
    public void m1322a(JSONObject jSONObject) throws JSONException {
        if (m1307J()) {
            C0947b c0947bM1266a = f1238b.mo1260b(jSONObject);
            this.f1256t = c0947bM1266a.f1256t;
            this.f1257u = c0947bM1266a.f1257u;
            this.f1258v = c0947bM1266a.f1258v;
            this.f1259w = c0947bM1266a.f1259w;
            if (this.f1259w < 180000) {
                this.f1259w = 180000;
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1323a(boolean z) {
        this.f1261y = z;
    }

    /* JADX INFO: renamed from: b */
    public void m1324b(int i) {
        this.f1249m = i;
    }

    /* JADX INFO: renamed from: b */
    public void m1325b(String str) {
        SharedPreferences.Editor editorEdit = this.f1260x.getSharedPreferences("npfDefaultLanguage", 0).edit();
        editorEdit.putString("language", str);
        editorEdit.apply();
    }

    /* JADX INFO: renamed from: b */
    public boolean m1326b() {
        return this.f1241e;
    }

    /* JADX INFO: renamed from: c */
    public void m1327c(String str) {
        this.f1254r = str;
    }

    /* JADX INFO: renamed from: c */
    public boolean m1328c() {
        return this.f1242f;
    }

    /* JADX INFO: renamed from: d */
    public String m1329d() {
        return this.f1243g;
    }

    /* JADX INFO: renamed from: e */
    public String m1330e() {
        return this.f1256t;
    }

    /* JADX INFO: renamed from: f */
    public String m1331f() {
        return this.f1257u;
    }

    /* JADX INFO: renamed from: g */
    public String m1332g() {
        return this.f1258v;
    }

    /* JADX INFO: renamed from: h */
    public String m1333h() {
        return this.f1244h;
    }

    /* JADX INFO: renamed from: i */
    public String m1334i() {
        return this.f1245i;
    }

    /* JADX INFO: renamed from: j */
    public boolean m1335j() {
        return this.f1240d && this.f1246j;
    }

    /* JADX INFO: renamed from: k */
    public boolean m1336k() {
        return this.f1261y;
    }

    /* JADX INFO: renamed from: l */
    public String m1337l() {
        return this.f1250n;
    }

    /* JADX INFO: renamed from: m */
    public String m1338m() {
        return this.f1251o;
    }

    /* JADX INFO: renamed from: n */
    public String m1339n() {
        return this.f1253q;
    }

    /* JADX INFO: renamed from: o */
    public String m1340o() {
        return C0950e.m1383a();
    }

    /* JADX INFO: renamed from: p */
    public String m1341p() {
        return this.f1252p;
    }

    /* JADX INFO: renamed from: q */
    public int m1342q() {
        return this.f1248l;
    }

    /* JADX INFO: renamed from: r */
    public int m1343r() {
        return this.f1249m;
    }

    /* JADX INFO: renamed from: s */
    public String m1344s() {
        return Build.VERSION.RELEASE;
    }

    /* JADX INFO: renamed from: t */
    public String m1345t() {
        return Build.MODEL;
    }

    /* JADX INFO: renamed from: u */
    public String m1346u() {
        return Build.MANUFACTURER;
    }

    /* JADX INFO: renamed from: v */
    public String m1347v() {
        String string = this.f1260x.getSharedPreferences("npfDefaultLanguage", 0).getString("language", m1308K());
        return TextUtils.isEmpty(string) ? m1308K() : string;
    }

    /* JADX INFO: renamed from: w */
    public String m1348w() {
        String networkOperatorName = ((TelephonyManager) this.f1260x.getSystemService("phone")).getNetworkOperatorName();
        return (networkOperatorName == null || "".equals(networkOperatorName)) ? "UNKNOWN" : networkOperatorName;
    }

    /* JADX INFO: renamed from: x */
    public String m1349x() {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) this.f1260x.getSystemService("connectivity");
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        return activeNetworkInfo.getType() == 1 ? "wifi" : "wwan";
    }

    /* JADX INFO: renamed from: y */
    public String m1350y() {
        return TimeZone.getDefault().getID();
    }

    /* JADX INFO: renamed from: z */
    public JSONObject m1351z() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("timeZone", m1350y());
        jSONObject.put("timeZoneOffset", TimeZone.getDefault().getRawOffset() + TimeZone.getDefault().getDSTSavings());
        if (m1310A() != null && !m1310A().isEmpty()) {
            jSONObject.put("sessionId", m1310A());
        }
        if (m1341p() != null && !m1341p().isEmpty()) {
            jSONObject.put("advertisingId", m1341p());
        }
        jSONObject.put("appVersion", m1339n());
        jSONObject.put(DeployGateEvent.EXTRA_SDK_VERSION, C0950e.m1383a());
        jSONObject.put("manufacturer", m1346u());
        jSONObject.put("deviceName", m1345t());
        jSONObject.put("osType", C0854h.f926h);
        jSONObject.put("osVersion", m1344s());
        jSONObject.put("locale", m1347v());
        jSONObject.put("networkType", m1349x());
        jSONObject.put("carrier", m1348w());
        return jSONObject;
    }
}
