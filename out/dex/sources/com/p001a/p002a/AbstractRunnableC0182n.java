package com.p001a.p002a;

import android.support.annotation.AnyThread;
import android.support.annotation.CheckResult;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.app.NotificationCompat;
import com.deploygate.service.DeployGateEvent;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.Contract;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.a.a.n */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class AbstractRunnableC0182n implements Runnable {

    /* JADX INFO: renamed from: b */
    @NonNull
    private static final Object f125b = new Object();

    /* JADX INFO: renamed from: a */
    @NonNull
    final C0181m f126a;

    /* JADX INFO: renamed from: h */
    private final boolean f132h;

    /* JADX INFO: renamed from: i */
    private final long f133i;

    /* JADX INFO: renamed from: j */
    private final boolean f134j;

    /* JADX INFO: renamed from: c */
    private int f127c = 0;

    /* JADX INFO: renamed from: d */
    private int f128d = 0;

    /* JADX INFO: renamed from: e */
    private boolean f129e = false;

    /* JADX INFO: renamed from: f */
    private boolean f130f = false;

    /* JADX INFO: renamed from: g */
    private boolean f131g = false;

    /* JADX INFO: renamed from: k */
    private final long f135k = C0178j.m202a();

    @AnyThread
    AbstractRunnableC0182n(@NonNull C0181m c0181m, boolean z) {
        this.f126a = c0181m;
        this.f132h = z;
        this.f134j = c0181m.f114h.mo126d();
        this.f133i = c0181m.f121o;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:35:0x0087  */
    /* JADX INFO: renamed from: a */
    static int m256a(@NonNull JSONObject jSONObject) {
        switch (C0178j.m204a(jSONObject.opt("action"), "")) {
            case "init":
                return 0;
            case "initial":
                return 1;
            case "session":
                JSONObject jSONObjectM231e = C0178j.m231e(jSONObject.opt("data"));
                return (jSONObjectM231e == null || !"pause".equalsIgnoreCase(C0178j.m204a(jSONObjectM231e.opt("state"), ""))) ? 2 : 3;
            case "update":
                return 4;
            case "get_attribution":
                return 5;
            case "event":
                return 6;
            case "identityLink":
                return 7;
            case "deeplink":
                return 8;
            case "push_token_add":
                return 9;
            case "push_token_remove":
                return 10;
            default:
                return 6;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NonNull
    /* JADX INFO: renamed from: a */
    private String m257a(@NonNull Object obj) throws IOException {
        if (obj instanceof JSONObject) {
            JSONObject jSONObject = (JSONObject) obj;
            m269b(jSONObject);
            return C0178j.m207a(jSONObject);
        }
        if (!(obj instanceof JSONArray)) {
            throw new IOException("Invalid Payload Type");
        }
        JSONArray jSONArray = (JSONArray) obj;
        for (int i = 0; i < jSONArray.length(); i++) {
            m269b(C0178j.m221b(jSONArray.opt(i), true));
        }
        return C0178j.m206a(jSONArray);
    }

    /* JADX INFO: renamed from: a */
    static void m258a(int i, @NonNull JSONObject jSONObject) {
        String str;
        switch (i) {
            case 0:
                str = DeployGateEvent.ACTION_INIT;
                break;
            case 1:
                str = "initial";
                break;
            case 2:
            case 3:
                str = "session";
                break;
            case 4:
                str = DeployGateEvent.ACTION_UPDATE_AVAILABLE;
                break;
            case 5:
                str = "get_attribution";
                break;
            case 6:
                str = NotificationCompat.CATEGORY_EVENT;
                break;
            case 7:
                str = "identityLink";
                break;
            case 8:
                str = "deeplink";
                break;
            case 9:
                str = "push_token_add";
                break;
            case 10:
                str = "push_token_remove";
                break;
            default:
                return;
        }
        C0178j.m209a("action", str, jSONObject);
    }

    /* JADX INFO: renamed from: a */
    private static void m259a(@NonNull C0178j c0178j, @NonNull JSONObject jSONObject) {
        String str;
        String strM203a = C0178j.m203a(c0178j.m237b("ext_date"));
        StringBuilder sb = new StringBuilder();
        sb.append("2018-02-09T18:47:10Z");
        if (strM203a != null) {
            str = " (" + strM203a + ")";
        } else {
            str = "";
        }
        sb.append(str);
        C0178j.m209a("sdk_build_date", sb.toString(), jSONObject);
    }

    /* JADX INFO: renamed from: a */
    private static void m260a(@NonNull C0178j c0178j, @NonNull JSONObject jSONObject, @Nullable JSONArray jSONArray) {
        JSONObject jSONObjectM231e;
        if (jSONArray == null || C0178j.m217a(jSONArray, "identity_link") || (jSONObjectM231e = C0178j.m231e(c0178j.m237b("identity_link_all"))) == null) {
            return;
        }
        c0178j.m235a("identity_link");
        C0178j.m209a("identity_link", jSONObjectM231e, jSONObject);
    }

    /* JADX INFO: renamed from: a */
    private static void m261a(@NonNull C0178j c0178j, @NonNull JSONObject jSONObject, @Nullable JSONArray jSONArray, @Nullable JSONArray jSONArray2) {
        JSONObject jSONObjectM231e = C0178j.m231e(c0178j.m237b("custom"));
        if (jSONObjectM231e == null || jSONObjectM231e.length() <= 0) {
            return;
        }
        Iterator<String> itKeys = jSONObjectM231e.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            String strM203a = C0178j.m203a(jSONObjectM231e.opt(next));
            if (strM203a == null || !C0178j.m217a(jSONArray2, next) || C0178j.m217a(jSONArray, next)) {
                C0174f.m16a(4, "TSK", "addCustomItem", "Custom item not in whitelist. Ignoring.", next, strM203a);
            } else {
                C0178j.m209a(next, strM203a, jSONObject);
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private static void m262a(@NonNull C0178j c0178j, @NonNull JSONObject jSONObject, @Nullable JSONArray jSONArray, boolean z) {
        if (jSONArray == null || C0178j.m217a(jSONArray, "app_limit_tracking")) {
            return;
        }
        if (!z || C0178j.m216a(c0178j.m237b("app_limit_trackingupd"), false)) {
            c0178j.m236a("app_limit_trackingupd", (Object) false);
            boolean zM220b = C0178j.m220b(c0178j.m237b("app_limit_tracking"));
            if (zM220b == null) {
                if (z) {
                    return;
                } else {
                    zM220b = false;
                }
            }
            C0178j.m209a("app_limit_tracking", zM220b, jSONObject);
        }
    }

    /* JADX INFO: renamed from: a */
    static void m263a(@NonNull JSONObject jSONObject, @NonNull C0178j c0178j) {
        String strM203a = C0178j.m203a(c0178j.m237b("kochava_app_id_override"));
        if (strM203a != null || (strM203a = C0178j.m203a(c0178j.m237b("kochava_app_id"))) != null) {
            C0178j.m209a("kochava_app_id", strM203a, jSONObject);
        }
        String strM203a2 = C0178j.m203a(c0178j.m237b("kochava_device_id"));
        if (strM203a2 != null) {
            C0178j.m209a("kochava_device_id", strM203a2, jSONObject);
        }
    }

    /* JADX INFO: renamed from: a */
    private static void m264a(@NonNull JSONObject jSONObject, @Nullable JSONArray jSONArray, int i) {
        if (jSONArray == null || C0178j.m217a(jSONArray, "state_active_count")) {
            return;
        }
        C0178j.m209a("state_active_count", Integer.valueOf(i), jSONObject);
    }

    /* JADX INFO: renamed from: a */
    private static void m265a(@NonNull JSONObject jSONObject, @Nullable JSONArray jSONArray, boolean z) {
        if (jSONArray == null || C0178j.m217a(jSONArray, "state_active")) {
            return;
        }
        C0178j.m209a("state_active", Boolean.valueOf(z), jSONObject);
    }

    @Contract(pure = true)
    @NonNull
    /* JADX INFO: renamed from: b */
    private String m266b(int i) {
        switch (i) {
            case 0:
                String strOptString = this.f126a.f109c.optString("url_init", null);
                return strOptString == null ? "https://kvinit-prod.api.kochava.com/track/kvinit" : strOptString;
            case 1:
                String strOptString2 = this.f126a.f109c.optString("url_initial", null);
                return strOptString2 == null ? "https://control.kochava.com/track/json" : strOptString2;
            case 2:
            case 3:
            case 6:
            case 8:
                String strOptString3 = this.f126a.f109c.optString("url_event", null);
                return strOptString3 == null ? "https://control.kochava.com/track/json" : strOptString3;
            case 4:
                String strOptString4 = this.f126a.f109c.optString("url_update", null);
                return strOptString4 == null ? "https://control.kochava.com/track/json" : strOptString4;
            case 5:
                String strOptString5 = this.f126a.f109c.optString("url_get_attribution", null);
                return strOptString5 == null ? "https://control.kochava.com/track/kvquery" : strOptString5;
            case 7:
                String strOptString6 = this.f126a.f109c.optString("url_identity_link", null);
                return strOptString6 == null ? "https://control.kochava.com/track/json" : strOptString6;
            case 9:
                String strOptString7 = this.f126a.f109c.optString("url_push_token_add", null);
                return strOptString7 == null ? "https://token.api.kochava.com/token/add" : strOptString7;
            case 10:
                String strOptString8 = this.f126a.f109c.optString("url_push_token_remove", null);
                return strOptString8 == null ? "https://token.api.kochava.com/token/remove" : strOptString8;
            default:
                return "https://control.kochava.com/track/json";
        }
    }

    /* JADX INFO: renamed from: b */
    private static void m267b(@NonNull C0178j c0178j, @NonNull JSONObject jSONObject) {
        String strM203a = C0178j.m203a(c0178j.m237b("partner_name"));
        if (strM203a != null) {
            C0178j.m209a("partner_name", strM203a, jSONObject);
        }
    }

    @WorkerThread
    /* JADX INFO: renamed from: b */
    private static void m268b(@NonNull C0178j c0178j, @NonNull JSONObject jSONObject, @Nullable JSONArray jSONArray) {
        if (C0178j.m217a(jSONArray, "conversion_type") || C0178j.m217a(jSONArray, "conversion_data")) {
            return;
        }
        String strM203a = C0178j.m203a(c0178j.m237b("referrer"));
        if (strM203a == null) {
            String strM204a = C0178j.m204a(jSONObject.opt("installer_package"), "");
            boolean z = C0178j.m231e(jSONObject.opt("install_referrer")) != null && "ok".equals(C0178j.m204a(jSONObject.opt(NotificationCompat.CATEGORY_STATUS), ""));
            if ("com.android.vending".equalsIgnoreCase(strM204a) && !z) {
                try {
                    C0173e.f29a.await(10L, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    C0174f.m16a(4, "TSK", "addConversion", e);
                }
            }
            strM203a = C0178j.m203a(c0178j.m237b("referrer"));
        }
        String strM203a2 = C0178j.m203a(c0178j.m237b("referrer_source"));
        if (strM203a == null || strM203a2 == null) {
            return;
        }
        C0178j.m209a("conversion_type", strM203a2, jSONObject);
        C0178j.m209a("conversion_data", strM203a, jSONObject);
    }

    /* JADX INFO: renamed from: b */
    private void m269b(@NonNull JSONObject jSONObject) {
        byte[] bytes;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String str = simpleDateFormat.format(new Date(C0178j.m202a()));
        StringBuilder sb = new StringBuilder();
        sb.append(C0178j.m204a(jSONObject.opt("nt_id"), ""));
        sb.append(C0178j.m204a(jSONObject.opt("kochava_app_id"), ""));
        sb.append(C0178j.m204a(jSONObject.opt("kochava_device_id"), ""));
        sb.append(C0178j.m204a(jSONObject.opt("sdk_version"), ""));
        sb.append(str);
        JSONObject jSONObjectM221b = C0178j.m221b(jSONObject.opt("data"), true);
        for (String str2 : new String[]{"adid", "android_id", "fire_adid", "fb_attribution_id", "custom", "custom_id", "conversion_data"}) {
            sb.append(C0178j.m204a(jSONObjectM221b.opt(str2), ""));
        }
        for (String str3 : new String[]{"usertime"}) {
            sb.append(Integer.toString(C0178j.m219b(jSONObjectM221b.opt(str3), 0)));
        }
        JSONObject jSONObjectM231e = C0178j.m231e(jSONObjectM221b.opt("ids"));
        if (jSONObjectM231e != null) {
            sb.append(C0178j.m204a(jSONObjectM231e.opt("email"), ""));
        }
        JSONObject jSONObjectM231e2 = C0178j.m231e(jSONObjectM221b.opt("install_referrer"));
        if (jSONObjectM231e2 != null) {
            sb.append(C0178j.m204a(jSONObjectM231e2.opt("referrer"), ""));
            sb.append(C0178j.m204a(jSONObjectM231e2.opt(NotificationCompat.CATEGORY_STATUS), ""));
            Integer numM226c = C0178j.m226c(jSONObjectM231e2.opt("install_begin_time"));
            if (numM226c != null) {
                sb.append(Integer.toString(numM226c.intValue()));
            }
            Integer numM226c2 = C0178j.m226c(jSONObjectM231e2.opt("referrer_click_time"));
            if (numM226c2 != null) {
                sb.append(Integer.toString(numM226c2.intValue()));
            }
        }
        try {
            bytes = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            C0174f.m16a(4, "TSK", "processPayloa", e);
            bytes = new byte[0];
        }
        long j = 0;
        for (byte b : bytes) {
            j += (long) (b & 255);
        }
        C0178j.m209a("send_date", str + "." + String.format(Locale.US, "%03d", Long.valueOf(j % 1000)) + "Z", jSONObject);
    }

    /* JADX INFO: renamed from: c */
    private static void m270c(@NonNull C0178j c0178j, @NonNull JSONObject jSONObject) {
        JSONObject jSONObjectM231e = C0178j.m231e(c0178j.m237b("identity_link"));
        if (jSONObjectM231e != null) {
            c0178j.m235a("identity_link");
            C0178j.m222b(jSONObject, jSONObjectM231e);
        }
    }

    @Contract(pure = true)
    /* JADX INFO: renamed from: a */
    final long m271a() {
        return this.f132h ? this.f133i : this.f126a.f121o;
    }

    @WorkerThread
    @CheckResult
    @Nullable
    /* JADX INFO: renamed from: a */
    final JSONObject m272a(int i, @NonNull Object obj) {
        JSONObject jSONObject = null;
        try {
            if (C0178j.m223b(this.f126a.f107a)) {
                jSONObject = new JSONObject(C0178j.m205a(m266b(i), m257a(obj)));
            } else {
                C0174f.m16a(4, "TSK", "post", "Error: No Network Connection");
            }
        } catch (Throwable th) {
            C0174f.m16a(4, "TSK", "post", th);
        }
        return jSONObject;
    }

    @WorkerThread
    /* JADX INFO: renamed from: a */
    final void m273a(@IntRange(from = 0, m1to = 2147483647L) int i) {
        C0174f.m16a(4, "TSK", "wakeSelf", Integer.toString(i));
        this.f126a.m251a(this, C0178j.m200a(i, 0, Integer.MAX_VALUE) * 1000);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @WorkerThread
    /* JADX INFO: renamed from: a */
    final void m274a(int i, @NonNull JSONObject jSONObject, @NonNull JSONObject jSONObject2) {
        String str;
        Double dValueOf;
        String str2;
        String str3;
        boolean zM279b;
        JSONObject jSONObjectM221b = C0178j.m221b(this.f126a.f110d.m237b("dp_options"), true);
        JSONObject jSONObjectM221b2 = C0178j.m221b(this.f126a.f110d.m237b("dp_override"), true);
        JSONArray jSONArrayM232f = C0178j.m232f(this.f126a.f110d.m237b("blacklist"));
        JSONArray jSONArrayM232f2 = C0178j.m232f(this.f126a.f110d.m237b("whitelist"));
        C0174f.m16a(5, "TSK", "buildPayload", "Main");
        C0177i.m145a(this.f126a.f107a, this.f126a.f110d, jSONObjectM221b, jSONObjectM221b2, this.f126a.f108b, jSONArrayM232f, jSONArrayM232f2, i, jSONObject2);
        synchronized (f125b) {
            C0174f.m16a(5, "TSK", "buildPayload", "Extra in lock");
            if (jSONArrayM232f == null && (i == 6 || i == 8 || i == 2 || i == 3)) {
                C0178j.m209a("backfilled", (Object) true, jSONObject);
            }
            m258a(i, jSONObject);
            m263a(jSONObject, this.f126a.f110d);
            C0178j.m209a("sdk_protocol", Integer.toString(10), jSONObject);
            C0178j.m209a("sdk_version", C0178j.m204a(this.f126a.f110d.m237b("sdk_version"), ""), jSONObject);
            C0178j.m209a("nt_id", this.f126a.f119m + "-" + UUID.randomUUID().toString(), jSONObject);
            C0178j.m209a("data", jSONObject2, jSONObject);
            C0178j.m209a("usertime", Long.valueOf(m280c() / 1000), jSONObject2);
            if (m279b() || i == 3 || i == 2) {
                int iM219b = i != 2 ? C0178j.m219b(this.f126a.f110d.m237b("session_window_uptime"), 0) : 0;
                str = "uptime";
                double dM280c = (m280c() - m271a()) * 10;
                Double.isNaN(dM280c);
                double d = iM219b;
                Double.isNaN(d);
                dValueOf = Double.valueOf((dM280c / 10000.0d) + d);
            } else {
                str = "uptime";
                double dM280c2 = (m280c() - this.f126a.f122p) * 10;
                Double.isNaN(dM280c2);
                dValueOf = Double.valueOf(dM280c2 / 10000.0d);
            }
            C0178j.m209a(str, dValueOf, jSONObject2);
            switch (i) {
                case 0:
                    m259a(this.f126a.f110d, jSONObject);
                    m267b(this.f126a.f110d, jSONObject2);
                    break;
                case 1:
                    m265a(jSONObject2, jSONArrayM232f, m279b());
                    m262a(this.f126a.f110d, jSONObject2, jSONArrayM232f, false);
                    m260a(this.f126a.f110d, jSONObject2, jSONArrayM232f);
                    m268b(this.f126a.f110d, jSONObject2, jSONArrayM232f);
                    m261a(this.f126a.f110d, jSONObject2, jSONArrayM232f, jSONArrayM232f2);
                    break;
                case 2:
                    m265a(jSONObject2, jSONArrayM232f, true);
                    str2 = "state";
                    str3 = "resume";
                    C0178j.m209a(str2, str3, jSONObject2);
                    break;
                case 3:
                    m265a(jSONObject2, jSONArrayM232f, true);
                    m264a(jSONObject2, jSONArrayM232f, C0178j.m219b(this.f126a.f110d.m237b("session_state_active_count"), 1));
                    str2 = "state";
                    str3 = "pause";
                    C0178j.m209a(str2, str3, jSONObject2);
                    break;
                case 4:
                    m262a(this.f126a.f110d, jSONObject2, jSONArrayM232f, true);
                    break;
                case 6:
                    zM279b = m279b();
                    m265a(jSONObject2, jSONArrayM232f, zM279b);
                    break;
                case 7:
                    m270c(this.f126a.f110d, jSONObject2);
                    break;
                case 8:
                    zM279b = m279b();
                    m265a(jSONObject2, jSONArrayM232f, zM279b);
                    break;
            }
        }
        C0174f.m16a(5, "TSK", "buildPayload", jSONObject);
    }

    @WorkerThread
    /* JADX INFO: renamed from: a */
    final void m275a(boolean z) {
        if (!this.f126a.f114h.mo125c()) {
            C0174f.m16a(4, "TSK", "wakeControlle", "Controller Busy. Returning.");
            return;
        }
        if (z) {
            m288k();
        } else {
            if (this.f126a.m253a()) {
                return;
            }
            C0181m c0181m = this.f126a;
            c0181m.m255b(c0181m.f113g, C0178j.m219b(this.f126a.f110d.m237b("kvtracker_wait"), 5) * 1000);
        }
    }

    @WorkerThread
    @Contract("null, _ -> true")
    /* JADX INFO: renamed from: a */
    final boolean m276a(@Nullable JSONObject jSONObject, boolean z) {
        int iM219b;
        if (jSONObject == null) {
            C0174f.m16a(4, "TSK", "checkErrorAnd", "Network Error");
            if (z) {
                m278b(true);
            }
            return true;
        }
        String strM204a = C0178j.m204a(jSONObject.opt("error"), "");
        if (!strM204a.isEmpty()) {
            C0174f.m16a(2, "TSK", "checkErrorAnd", "Error: " + strM204a);
        }
        if (!C0178j.m216a(jSONObject.opt(FirebaseAnalytics.Param.SUCCESS), false)) {
            C0174f.m16a(4, "TSK", "checkErrorAnd", "Success False");
            if (z) {
                m278b(false);
            }
            return true;
        }
        JSONObject jSONObjectM231e = C0178j.m231e(jSONObject.opt("data"));
        if (jSONObjectM231e != null && (iM219b = C0178j.m219b(jSONObjectM231e.opt("retry"), -1)) != -1) {
            C0174f.m16a(4, "TSK", "checkErrorAnd", "Retry Time");
            if (z) {
                m273a(C0178j.m200a(iM219b, 0, Integer.MAX_VALUE));
            }
            return true;
        }
        JSONArray jSONArrayM232f = C0178j.m232f(jSONObject.opt(NotificationCompat.CATEGORY_MESSAGE));
        if (jSONArrayM232f != null) {
            for (int i = 0; i < jSONArrayM232f.length(); i++) {
                if ("resonance_cascade".equalsIgnoreCase(C0178j.m203a(jSONArrayM232f.opt(i)))) {
                    if (z) {
                        m278b(false);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @WorkerThread
    @NonNull
    /* JADX INFO: renamed from: b */
    final JSONObject m277b(int i, @NonNull JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        m274a(i, jSONObject2, jSONObject3);
        C0178j.m222b(jSONObject3, jSONObject);
        return jSONObject3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0041, code lost:
    
        if (r7 != 4) goto L15;
     */
    @android.support.annotation.WorkerThread
    /* JADX INFO: renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final void m278b(boolean r7) {
        /*
            r6 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.String r2 = java.lang.Boolean.toString(r7)
            r3 = 0
            r1[r3] = r2
            r2 = 4
            java.lang.String r3 = "TSK"
            java.lang.String r4 = "retry"
            com.p001a.p002a.C0174f.m16a(r2, r3, r4, r1)
            r1 = 60
            r3 = 10
            r4 = 3
            r5 = 2
            if (r7 == 0) goto L32
            int r7 = r6.f128d
            int r7 = r7 + r0
            int r7 = com.p001a.p002a.C0178j.m200a(r7, r0, r2)
            r6.f128d = r7
            int r7 = r6.f128d
            if (r7 == r5) goto L47
            if (r7 == r4) goto L2f
            if (r7 == r2) goto L2c
            goto L43
        L2c:
            r7 = 3600(0xe10, float:5.045E-42)
            goto L50
        L2f:
            r7 = 300(0x12c, float:4.2E-43)
            goto L50
        L32:
            int r7 = r6.f127c
            int r7 = r7 + r0
            int r7 = com.p001a.p002a.C0178j.m200a(r7, r0, r2)
            r6.f127c = r7
            int r7 = r6.f127c
            if (r7 == r5) goto L4e
            if (r7 == r4) goto L4b
            if (r7 == r2) goto L47
        L43:
            r6.m273a(r3)
            goto L53
        L47:
            r6.m273a(r1)
            goto L53
        L4b:
            r7 = 30
            goto L50
        L4e:
            r7 = 20
        L50:
            r6.m273a(r7)
        L53:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p001a.p002a.AbstractRunnableC0182n.m278b(boolean):void");
    }

    @Contract(pure = true)
    /* JADX INFO: renamed from: b */
    final boolean m279b() {
        return this.f132h ? this.f134j : this.f126a.f114h.mo126d();
    }

    @Contract(pure = true)
    /* JADX INFO: renamed from: c */
    final long m280c() {
        return this.f132h ? this.f135k : C0178j.m202a();
    }

    @WorkerThread
    /* JADX INFO: renamed from: d */
    final void m281d() {
        m287j();
        this.f129e = true;
    }

    @WorkerThread
    @Contract(pure = true)
    /* JADX INFO: renamed from: e */
    final boolean m282e() {
        return this.f129e;
    }

    @WorkerThread
    /* JADX INFO: renamed from: f */
    final void m283f() {
        this.f130f = true;
    }

    @WorkerThread
    @Contract(pure = true)
    /* JADX INFO: renamed from: g */
    final boolean m284g() {
        return this.f130f;
    }

    @WorkerThread
    /* JADX INFO: renamed from: h */
    final void m285h() {
        this.f131g = true;
    }

    @WorkerThread
    @Contract(pure = true)
    /* JADX INFO: renamed from: i */
    final boolean m286i() {
        return this.f131g;
    }

    @WorkerThread
    /* JADX INFO: renamed from: j */
    final void m287j() {
        this.f129e = false;
        this.f130f = false;
        this.f127c = 0;
        this.f128d = 0;
        this.f131g = false;
    }

    @WorkerThread
    /* JADX INFO: renamed from: k */
    final void m288k() {
        C0181m c0181m = this.f126a;
        c0181m.m252a(c0181m.f113g, false);
    }
}
