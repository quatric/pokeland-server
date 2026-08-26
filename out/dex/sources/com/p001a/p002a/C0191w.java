package com.p001a.p002a;

import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.a.a.w */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0191w extends AbstractRunnableC0182n {
    @AnyThread
    C0191w(@NonNull C0181m c0181m) {
        super(c0181m, false);
    }

    /* JADX INFO: renamed from: a */
    static boolean m297a(@NonNull AbstractRunnableC0182n abstractRunnableC0182n, boolean z) {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        if (z) {
            abstractRunnableC0182n.m274a(4, jSONObject, jSONObject2);
        }
        boolean zM216a = C0178j.m216a(abstractRunnableC0182n.f126a.f110d.m237b("send_updates"), true);
        boolean z2 = C0178j.m203a(abstractRunnableC0182n.f126a.f110d.m237b("initial_data")) != null;
        boolean zM216a2 = C0178j.m216a(abstractRunnableC0182n.f126a.f110d.m237b("initial_needs_sent"), true);
        C0174f.m16a(5, "TUP", "performUpdate", "sendUpdates: " + zM216a, "gathered: " + z2, "needsSent: " + zM216a2);
        if (zM216a && (z2 || !zM216a2)) {
            if (!z) {
                abstractRunnableC0182n.m274a(4, jSONObject, jSONObject2);
            }
            if (jSONObject2.length() > 2) {
                abstractRunnableC0182n.f126a.f110d.m241c(jSONObject);
                return true;
            }
        }
        return false;
    }

    @Override // java.lang.Runnable
    public final void run() {
        C0174f.m16a(4, "TUP", "run", new Object[0]);
        m297a((AbstractRunnableC0182n) this, true);
        m281d();
        m288k();
        C0174f.m16a(4, "TUP", "run", "Complete");
    }
}
