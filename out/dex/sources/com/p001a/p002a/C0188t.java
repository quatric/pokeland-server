package com.p001a.p002a;

import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.a.a.t */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0188t extends AbstractRunnableC0182n {
    @AnyThread
    C0188t(@NonNull C0181m c0181m) {
        super(c0181m, true);
    }

    @Override // java.lang.Runnable
    public final void run() {
        C0174f.m16a(4, "TIL", "run", new Object[0]);
        if (!C0178j.m216a(this.f126a.f110d.m237b("initial_needs_sent"), true)) {
            C0174f.m16a(4, "TIL", "run", "Skip");
            m281d();
            m288k();
            return;
        }
        if (!m286i()) {
            m285h();
            m273a(C0178j.m219b(this.f126a.f110d.m237b("initial_wait"), 0));
            return;
        }
        JSONObject jSONObjectM231e = C0178j.m231e(this.f126a.f110d.m237b("initial_data"));
        if (jSONObjectM231e == null) {
            C0174f.m16a(5, "TIL", "run", "Gather");
            jSONObjectM231e = new JSONObject();
            m274a(1, jSONObjectM231e, new JSONObject());
            this.f126a.f110d.m236a("initial_data", (Object) jSONObjectM231e);
        }
        JSONObject jSONObjectA = m272a(1, (Object) jSONObjectM231e);
        if (m276a(jSONObjectA, true)) {
            return;
        }
        C0174f.m16a(5, "TIL", "run", jSONObjectA);
        if (!C0178j.m216a(this.f126a.f110d.m237b("initial_ever_sent"), false)) {
            this.f126a.f110d.m236a("session_resume_time", (Object) Integer.valueOf((int) (C0178j.m202a() / 1000)));
            this.f126a.f110d.m236a("session_state_active_count", (Object) 1);
        }
        this.f126a.f110d.m235a("initial_data");
        this.f126a.f110d.m236a("initial_ever_sent", (Object) true);
        this.f126a.f110d.m236a("initial_needs_sent", (Object) false);
        m281d();
        C0174f.m16a(3, "TIL", "initial", "Complete");
        C0174f.m16a(4, "TIL", "run", "Complete");
        m288k();
    }
}
