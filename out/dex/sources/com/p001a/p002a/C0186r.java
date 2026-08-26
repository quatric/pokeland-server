package com.p001a.p002a;

import android.support.annotation.NonNull;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.a.a.r */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0186r extends AbstractRunnableC0182n {

    /* JADX INFO: renamed from: b */
    @NonNull
    private final C0174f.c f147b;

    C0186r(@NonNull C0181m c0181m, @NonNull C0174f.c cVar) {
        super(c0181m, true);
        this.f147b = cVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Object[] objArr;
        C0174f.m16a(4, "TIL", "run", new Object[0]);
        JSONObject jSONObjectM221b = C0178j.m221b(this.f126a.f110d.m237b("identity_link_all"), true);
        JSONObject jSONObjectM221b2 = C0178j.m221b(this.f126a.f110d.m237b("identity_link"), true);
        boolean z = jSONObjectM221b2.length() != 0;
        C0178j.m222b(jSONObjectM221b2, this.f147b.f73a);
        if (z || !C0178j.m218a(jSONObjectM221b, jSONObjectM221b2)) {
            C0178j.m222b(jSONObjectM221b, jSONObjectM221b2);
            if (jSONObjectM221b.length() > 250) {
                jSONObjectM221b = new JSONObject();
                C0174f.m16a(4, "TIL", "run", "Max Size Exceeded. Resetting Saved List.");
            }
            this.f126a.f110d.m236a("identity_link_all", (Object) jSONObjectM221b);
            this.f126a.f110d.m236a("identity_link", (Object) jSONObjectM221b2);
            boolean z2 = C0178j.m203a(this.f126a.f110d.m237b("initial_data")) != null;
            boolean zM216a = C0178j.m216a(this.f126a.f110d.m237b("initial_needs_sent"), true);
            if (z2 || !zM216a) {
                JSONObject jSONObject = new JSONObject();
                m274a(7, jSONObject, new JSONObject());
                this.f126a.f110d.m241c(jSONObject);
                if (this.f126a.f114h.mo125c()) {
                    m288k();
                }
            }
            m281d();
            objArr = new Object[]{"Complete"};
        } else {
            this.f126a.f110d.m235a("identity_link");
            objArr = new Object[]{"Skip"};
        }
        C0174f.m16a(4, "TIL", "run", objArr);
    }
}
