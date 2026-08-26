package com.p001a.p002a;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.a.a.p */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0184p extends AbstractRunnableC0182n {

    /* JADX INFO: renamed from: b */
    private final int f137b;

    /* JADX INFO: renamed from: c */
    @Nullable
    private final String f138c;

    /* JADX INFO: renamed from: d */
    @Nullable
    private final String f139d;

    /* JADX INFO: renamed from: e */
    @Nullable
    private final String f140e;

    /* JADX INFO: renamed from: f */
    @Nullable
    private final String f141f;

    /* JADX INFO: renamed from: g */
    @Nullable
    private final String f142g;

    C0184p(@NonNull C0181m c0181m, int i, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5) {
        super(c0181m, true);
        this.f137b = i;
        this.f138c = str;
        this.f139d = str2;
        this.f140e = str3;
        this.f141f = str4;
        this.f142g = str5;
    }

    @CheckResult
    /* JADX INFO: renamed from: b */
    private boolean m289b(@NonNull JSONObject jSONObject) {
        int i = this.f137b;
        if (i != 2 && i != 3) {
            return true;
        }
        int iM219b = C0178j.m219b(this.f126a.f110d.m237b("session_minimum"), 45);
        int iM219b2 = C0178j.m219b(this.f126a.f110d.m237b("session_window"), 600);
        if (iM219b2 == 0 || iM219b == 0) {
            return true;
        }
        int iC = (int) (m280c() / 1000);
        int iM219b3 = C0178j.m219b(this.f126a.f110d.m237b("session_resume_time"), 0);
        boolean zM216a = C0178j.m216a(this.f126a.f110d.m237b("session_pause_sent_this_window"), false);
        boolean zM216a2 = C0178j.m216a(this.f126a.f110d.m237b("session_pause_ever_sent"), false);
        if (this.f137b == 2) {
            this.f126a.f110d.m236a("session_state_active_count", (Object) Integer.valueOf(C0178j.m219b(this.f126a.f110d.m237b("session_state_active_count"), 0) + 1));
            if (iC < iM219b3 + iM219b2) {
                return false;
            }
            C0174f.m16a(4, "TBE", "processSessio", "Resume: Sufficient Time");
            JSONObject jSONObjectM231e = C0178j.m231e(this.f126a.f110d.m237b("session_pause"));
            if (jSONObjectM231e != null) {
                C0174f.m16a(4, "TBE", "processSessio", "Resume: Queuing Cached Pause");
                this.f126a.f110d.m239b(jSONObjectM231e);
                this.f126a.f110d.m235a("session_pause");
                this.f126a.f110d.m236a("session_pause_ever_sent", (Object) true);
                this.f126a.f110d.m236a("session_state_active_count", (Object) 1);
            }
            this.f126a.f110d.m236a("session_resume_time", (Object) Integer.valueOf(iC));
            this.f126a.f110d.m236a("session_window_uptime", (Object) 0);
            this.f126a.f110d.m236a("session_pause_sent_this_window", (Object) false);
            return true;
        }
        int iM219b4 = (int) (((long) C0178j.m219b(this.f126a.f110d.m237b("session_window_uptime"), 0)) + ((m280c() - m271a()) / 1000));
        this.f126a.f110d.m236a("session_window_uptime", (Object) Integer.valueOf(iM219b4));
        if (!zM216a2 || (!zM216a && (iM219b4 >= iM219b || iC >= iM219b3 + iM219b2))) {
            C0174f.m16a(4, "TBE", "processSessio", "Pause: Sending");
            this.f126a.f110d.m235a("session_pause");
            this.f126a.f110d.m236a("session_pause_ever_sent", (Object) true);
            this.f126a.f110d.m236a("session_pause_sent_this_window", (Object) true);
            this.f126a.f110d.m236a("session_state_active_count", (Object) 0);
            return true;
        }
        if (zM216a) {
            C0174f.m16a(4, "TBE", "processSessio", "Pause: Not Updating");
            return false;
        }
        C0174f.m16a(4, "TBE", "processSessio", "Pause: Updating");
        this.f126a.f110d.m236a("session_pause", (Object) jSONObject);
        return false;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str;
        C0174f.m16a(4, "TBE", "run", new Object[0]);
        if (this.f126a.f110d.m242d() >= 1000) {
            C0174f.m16a(2, "TBE", "run", "Database Full. Dropping: " + this.f138c);
            return;
        }
        JSONArray jSONArrayM232f = C0178j.m232f(this.f126a.f110d.m237b("eventname_blacklist"));
        if (jSONArrayM232f != null && (str = this.f138c) != null && C0178j.m217a(jSONArrayM232f, str)) {
            C0174f.m16a(3, "TBE", "run", this.f138c + " blacklisted");
            return;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            if (this.f138c != null) {
                jSONObject.put("event_name", this.f138c);
            }
            if (this.f139d != null) {
                Object objM231e = C0178j.m231e(this.f139d);
                if (objM231e == null) {
                    objM231e = this.f139d;
                }
                jSONObject.put("event_data", objM231e);
            }
            if (this.f140e != null && this.f141f != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("purchaseData", this.f140e);
                jSONObject2.put("dataSignature", this.f141f);
                jSONObject.put("receipt", jSONObject2);
            }
            if (this.f142g != null) {
                jSONObject.put("uri", this.f142g);
            }
        } catch (JSONException e) {
            C0174f.m16a(2, "TBE", "run", e);
        }
        JSONObject jSONObject3 = new JSONObject();
        m274a(this.f137b, jSONObject3, jSONObject);
        boolean zM289b = m289b(jSONObject3);
        if (zM289b) {
            this.f126a.f110d.m239b(jSONObject3);
        } else {
            C0174f.m16a(4, "TBE", "run", "Not sending deferred/dropped event.");
        }
        if (zM289b || this.f137b == 3) {
            m275a(this.f126a.f110d.m242d() >= C0178j.m219b(this.f126a.f110d.m237b("batch_max_quantity"), 25) || this.f137b == 3);
        }
        C0174f.m16a(4, "TBE", "run", "Complete");
    }
}
