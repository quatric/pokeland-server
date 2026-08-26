package com.p001a.p002a;

import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import com.deploygate.service.DeployGateEvent;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.a.a.v */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0190v extends AbstractRunnableC0182n {
    @AnyThread
    C0190v(@NonNull C0181m c0181m) {
        super(c0181m, false);
    }

    @NonNull
    /* JADX INFO: renamed from: a */
    final JSONArray m294a(@NonNull JSONArray jSONArray, @Nullable JSONArray jSONArray2, @Nullable JSONArray jSONArray3, boolean z) {
        JSONArray jSONArray4 = new JSONArray();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObjectM221b = C0178j.m221b(jSONArray.opt(i), true);
            if (jSONObjectM221b.has("action") && jSONObjectM221b.has("sdk_version")) {
                JSONObject jSONObjectM221b2 = C0178j.m221b(jSONObjectM221b.opt("data"), true);
                int iA = m256a(jSONObjectM221b);
                String strM203a = C0178j.m203a(jSONObjectM221b2.opt("event_name"));
                if ((jSONArray3 == null || strM203a == null || iA != 6 || !C0178j.m217a(jSONArray3, strM203a)) && (z || (iA != 3 && iA != 2))) {
                    m263a(jSONObjectM221b, this.f126a.f110d);
                    boolean zM216a = C0178j.m216a(jSONObjectM221b.opt("backfilled"), false);
                    jSONObjectM221b.remove("backfilled");
                    if (zM216a) {
                        C0178j.m209a("data", m277b(iA, jSONObjectM221b2), jSONObjectM221b);
                    }
                    m296b(jSONObjectM221b, jSONArray2);
                    jSONArray4.put(jSONObjectM221b);
                }
            }
        }
        return jSONArray4;
    }

    /* JADX INFO: renamed from: a */
    final boolean m295a(@NonNull JSONObject jSONObject, @Nullable JSONArray jSONArray) {
        if (!jSONObject.has("action") || !jSONObject.has("sdk_version")) {
            return false;
        }
        int iA = m256a(jSONObject);
        m263a(jSONObject, this.f126a.f110d);
        boolean zM216a = C0178j.m216a(this.f126a.f110d.m237b("push"), false);
        if ((iA == 9 || iA == 10) && !zM216a) {
            return false;
        }
        if (iA != 4) {
            return true;
        }
        m296b(jSONObject, jSONArray);
        return true;
    }

    /* JADX INFO: renamed from: b */
    final void m296b(@NonNull JSONObject jSONObject, @Nullable JSONArray jSONArray) {
        if (jSONArray == null) {
            return;
        }
        JSONObject jSONObjectM221b = C0178j.m221b(jSONObject.opt("data"), true);
        JSONArray jSONArrayM227c = C0178j.m227c((Object) jSONObjectM221b.names(), true);
        for (int i = 0; i < jSONArrayM227c.length(); i++) {
            String strM203a = C0178j.m203a(jSONArrayM227c.opt(i));
            if (strM203a != null && C0178j.m217a(jSONArray, strM203a)) {
                jSONObjectM221b.remove(strM203a);
            }
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        C0174f.m16a(4, "TQU", "run", new Object[0]);
        C0174f.m16a(5, "TQU", "run", DeployGateEvent.ACTION_UPDATE_AVAILABLE);
        JSONArray jSONArrayM232f = C0178j.m232f(this.f126a.f110d.m237b("blacklist"));
        while (this.f126a.f110d.m245g() > 0) {
            JSONObject jSONObjectM244f = this.f126a.f110d.m244f();
            if (m295a(jSONObjectM244f, jSONArrayM232f) && m276a(m272a(m256a(jSONObjectM244f), (Object) jSONObjectM244f), true)) {
                return;
            } else {
                this.f126a.f110d.m243e();
            }
        }
        C0174f.m16a(5, "TQU", "run", NotificationCompat.CATEGORY_EVENT);
        JSONArray jSONArrayM232f2 = C0178j.m232f(this.f126a.f110d.m237b("eventname_blacklist"));
        boolean zM216a = C0178j.m216a(this.f126a.f110d.m237b("session_tracking"), true);
        while (this.f126a.f110d.m242d() > 0) {
            JSONArray jSONArrayM240c = this.f126a.f110d.m240c();
            JSONArray jSONArrayM294a = m294a(jSONArrayM240c, jSONArrayM232f, jSONArrayM232f2, zM216a);
            if (jSONArrayM294a.length() > 0 && m276a(m272a(6, jSONArrayM294a), true)) {
                return;
            } else {
                this.f126a.f110d.m234a(jSONArrayM240c.length());
            }
        }
        this.f126a.f110d.m238b();
        m287j();
        C0174f.m16a(4, "TQU", "run", "Complete");
    }
}
