package com.p001a.p002a;

import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.deploygate.service.DeployGateEvent;
import com.google.firebase.analytics.FirebaseAnalytics;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.a.a.s */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0187s extends AbstractRunnableC0182n {

    /* JADX INFO: renamed from: b */
    @Nullable
    private JSONObject f148b;

    @AnyThread
    C0187s(@NonNull C0181m c0181m) {
        super(c0181m, false);
        this.f148b = null;
    }

    @VisibleForTesting
    /* JADX INFO: renamed from: a */
    static void m291a(@NonNull C0181m c0181m, @NonNull JSONObject jSONObject) {
        c0181m.f110d.m235a("blacklist");
        c0181m.f110d.m235a("whitelist");
        c0181m.f110d.m235a("eventname_blacklist");
        c0181m.f110d.m236a("session_tracking", (Object) true);
        c0181m.f110d.m236a("session_minimum", (Object) 45);
        c0181m.f110d.m236a("session_window", (Object) 600);
        c0181m.f110d.m236a("send_updates", (Object) true);
        c0181m.f110d.m236a("kvinit_wait", (Object) 60);
        c0181m.f110d.m236a("kvinit_staleness", (Object) 2592000);
        c0181m.f110d.m236a("initial_wait", (Object) 0);
        c0181m.f110d.m236a("kvtracker_wait", (Object) 5);
        c0181m.f110d.m236a("getattribution_wait", (Object) 7);
        c0181m.f110d.m236a("attribution_staleness", (Object) (-1));
        c0181m.f110d.m236a("batch_max_quantity", (Object) 25);
        c0181m.f110d.m236a("push", (Object) false);
        c0181m.f110d.m236a("dp_options", (Object) new JSONObject());
        m292b(c0181m, new JSONObject());
        JSONArray jSONArrayM232f = C0178j.m232f(jSONObject.opt("blacklist"));
        if (jSONArrayM232f != null) {
            if (C0178j.m217a(jSONArrayM232f, "email")) {
                jSONArrayM232f.put("ids");
            }
            c0181m.f110d.m236a("blacklist", (Object) jSONArrayM232f);
        } else {
            c0181m.f110d.m236a("blacklist", (Object) new JSONArray());
        }
        JSONArray jSONArrayM232f2 = C0178j.m232f(jSONObject.opt("whitelist"));
        if (jSONArrayM232f2 != null) {
            if (C0178j.m217a(jSONArrayM232f2, "email")) {
                jSONArrayM232f2.put("ids");
            }
            c0181m.f110d.m236a("whitelist", (Object) jSONArrayM232f2);
        } else {
            c0181m.f110d.m236a("whitelist", (Object) new JSONArray());
        }
        JSONArray jSONArrayM232f3 = C0178j.m232f(jSONObject.opt("eventname_blacklist"));
        if (jSONArrayM232f3 != null) {
            c0181m.f110d.m236a("eventname_blacklist", (Object) jSONArrayM232f3);
        } else {
            c0181m.f110d.m236a("eventname_blacklist", (Object) new JSONArray());
        }
        JSONObject jSONObjectM231e = C0178j.m231e(jSONObject.opt("flags"));
        if (jSONObjectM231e != null) {
            String strM203a = C0178j.m203a(jSONObjectM231e.opt("kochava_app_id"));
            if (strM203a != null && !strM203a.isEmpty()) {
                c0181m.f110d.m236a("kochava_app_id_override", (Object) strM203a);
            }
            String strM203a2 = C0178j.m203a(jSONObjectM231e.opt("kochava_device_id"));
            if (strM203a2 != null && !strM203a2.isEmpty()) {
                c0181m.f110d.m236a("kochava_device_id", (Object) strM203a2);
            }
            if (C0178j.m216a(jSONObjectM231e.opt("resend_initial"), false)) {
                c0181m.f110d.m236a("initial_needs_sent", (Object) true);
            }
            c0181m.f110d.m236a("session_tracking", (Object) Boolean.valueOf(C0178j.m216a(jSONObjectM231e.opt("session_tracking"), true) && ("NONE".equalsIgnoreCase(C0178j.m203a(jSONObjectM231e.opt("session_tracking"))) ^ true)));
            c0181m.f110d.m236a("push", (Object) Boolean.valueOf(C0178j.m216a(jSONObjectM231e.opt("push"), false)));
            c0181m.f110d.m236a("send_updates", (Object) Boolean.valueOf(C0178j.m216a(jSONObjectM231e.opt("send_updates"), true)));
            c0181m.f110d.m236a("session_minimum", (Object) Integer.valueOf(C0178j.m200a(C0178j.m219b(jSONObjectM231e.opt("session_minimum"), 45), 0, Integer.MAX_VALUE)));
            c0181m.f110d.m236a("session_window", (Object) Integer.valueOf(C0178j.m200a(C0178j.m219b(jSONObjectM231e.opt("session_window"), 600), 0, Integer.MAX_VALUE)));
            int iM200a = C0178j.m200a(C0178j.m219b(jSONObjectM231e.opt("kvinit_wait"), 60), 0, Integer.MAX_VALUE);
            c0181m.f110d.m236a("kvinit_wait", (Object) Integer.valueOf(iM200a));
            c0181m.f110d.m236a("kvinit_staleness", (Object) Integer.valueOf(C0178j.m200a(C0178j.m219b(jSONObjectM231e.opt("kvinit_staleness"), 2592000), iM200a, Integer.MAX_VALUE)));
            c0181m.f110d.m236a("initial_wait", (Object) Integer.valueOf(C0178j.m200a(C0178j.m219b(jSONObjectM231e.opt("initial_wait"), 0), 0, Integer.MAX_VALUE)));
            c0181m.f110d.m236a("kvtracker_wait", (Object) Integer.valueOf(C0178j.m200a(C0178j.m219b(jSONObjectM231e.opt("kvtracker_wait"), 5), 0, Integer.MAX_VALUE)));
            c0181m.f110d.m236a("getattribution_wait", (Object) Integer.valueOf(C0178j.m200a(C0178j.m219b(jSONObjectM231e.opt("getattribution_wait"), 7), 0, Integer.MAX_VALUE)));
            c0181m.f110d.m236a("attribution_staleness", (Object) Integer.valueOf(C0178j.m200a(C0178j.m219b(jSONObjectM231e.opt("attribution_staleness"), -1), -1, Integer.MAX_VALUE)));
            c0181m.f110d.m236a("batch_max_quantity", (Object) Integer.valueOf(C0178j.m200a(C0178j.m219b(jSONObjectM231e.opt("batch_max_quantity"), 25), 1, Integer.MAX_VALUE)));
            m292b(c0181m, jSONObjectM231e);
        }
        m293b(jSONObject);
    }

    /* JADX INFO: renamed from: b */
    private static void m292b(@NonNull C0181m c0181m, @NonNull JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        C0178j.m209a("accuracy", Integer.valueOf(C0178j.m200a(C0178j.m219b(jSONObject.opt("location_accuracy"), 50), 0, Integer.MAX_VALUE)), jSONObject3);
        C0178j.m209a("timeout", Integer.valueOf(C0178j.m200a(C0178j.m219b(jSONObject.opt("location_timeout"), 10), 1, Integer.MAX_VALUE)), jSONObject3);
        C0178j.m209a("staleness", Integer.valueOf(C0178j.m200a(C0178j.m219b(jSONObject.opt("location_staleness"), 90), 0, Integer.MAX_VALUE)), jSONObject3);
        C0178j.m209a("mode", C0178j.m204a(jSONObject.opt("location_mode"), "auto"), jSONObject3);
        C0178j.m209a(FirebaseAnalytics.Param.LOCATION, jSONObject3, jSONObject2);
        JSONObject jSONObject4 = new JSONObject();
        C0178j.m209a("install_referrer_attempts", Integer.valueOf(C0178j.m200a(C0178j.m219b(jSONObject.opt("install_referrer_attempts"), 2), 1, Integer.MAX_VALUE)), jSONObject4);
        C0178j.m209a("install_referrer_wait", Integer.valueOf(C0178j.m200a(C0178j.m219b(jSONObject.opt("install_referrer_wait"), 10), 1, Integer.MAX_VALUE)), jSONObject4);
        C0178j.m209a("install_referrer_retry_wait", Double.valueOf(C0178j.m198a(C0178j.m199a(jSONObject.opt("install_referrer_retry_wait"), 1.0d), 0.0d, Double.MAX_VALUE)), jSONObject4);
        C0178j.m209a("install_referrer", jSONObject4, jSONObject2);
        c0181m.f110d.m236a("dp_options", (Object) jSONObject2);
    }

    /* JADX INFO: renamed from: b */
    private static void m293b(@NonNull JSONObject jSONObject) {
        JSONArray jSONArrayM232f = C0178j.m232f(jSONObject.opt("log_messages"));
        if (jSONArrayM232f == null) {
            return;
        }
        for (int i = 0; i < jSONArrayM232f.length(); i++) {
            JSONObject jSONObjectM231e = C0178j.m231e(jSONArrayM232f.opt(i));
            if (jSONObjectM231e != null) {
                String strM203a = C0178j.m203a(jSONObjectM231e.opt("text"));
                int iM201a = C0178j.m201a((Object) C0178j.m203a(jSONObjectM231e.opt(FirebaseAnalytics.Param.LEVEL)), 0);
                if (iM201a != 0 && strM203a != null && !strM203a.isEmpty()) {
                    C0174f.m16a(iM201a, "TIN", "decodeLogMess", strM203a);
                }
            }
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        C0174f.m16a(4, "TIN", "run", new Object[0]);
        int iM219b = C0178j.m219b(this.f126a.f110d.m237b("init_last_sent"), 0);
        int iM219b2 = C0178j.m219b(this.f126a.f110d.m237b("kvinit_wait"), 60);
        C0174f.m16a(5, "TIN", "run", "LastSent: " + iM219b, "InitWait: " + iM219b2);
        if (iM219b2 + iM219b >= C0178j.m202a() / 1000) {
            C0174f.m16a(4, "TIN", "run", "Skip");
            m281d();
            m288k();
            return;
        }
        if (this.f148b == null) {
            C0174f.m16a(5, "TIN", "run", "Gather");
            this.f148b = new JSONObject();
            m274a(0, this.f148b, new JSONObject());
        }
        C0174f.m16a(5, "TIN", "run", "Send");
        JSONObject jSONObjectA = m272a(0, (Object) this.f148b);
        if (m276a(jSONObjectA, iM219b == 0)) {
            if (iM219b == 0) {
                C0174f.m16a(5, "TIN", "run", "Retry");
                return;
            }
            C0174f.m16a(5, "TIN", "run", "Failed. Skip");
            m281d();
            m288k();
            return;
        }
        C0174f.m16a(5, "TIN", "run", jSONObjectA);
        if (!C0178j.m215a((Object) C0178j.m203a(this.f148b.opt("nt_id")), (Object) C0178j.m203a(jSONObjectA.opt("nt_id")))) {
            C0174f.m16a(4, "TIN", "run", "nt_id mismatch");
        }
        m291a(this.f126a, jSONObjectA);
        this.f126a.f110d.m236a("init_last_sent", (Object) Integer.valueOf((int) (C0178j.m202a() / 1000)));
        m281d();
        C0174f.m16a(3, "TIN", DeployGateEvent.ACTION_INIT, "Complete");
        C0174f.m16a(4, "TIN", "run", "Complete");
        m288k();
    }
}
