package com.p001a.p002a;

import android.support.annotation.NonNull;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.a.a.u */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0189u extends AbstractRunnableC0182n {

    /* JADX INFO: renamed from: b */
    @NonNull
    private final String f149b;

    /* JADX INFO: renamed from: c */
    private final boolean f150c;

    /* JADX INFO: renamed from: d */
    private final boolean f151d;

    C0189u(@NonNull C0181m c0181m, @NonNull String str, boolean z, boolean z2) {
        super(c0181m, true);
        this.f149b = str;
        this.f150c = z;
        this.f151d = z2;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x004a  */
    /* JADX WARN: Code duplicated, block: B:13:0x007d  */
    /* JADX WARN: Code duplicated, block: B:14:0x0084  */
    /* JADX WARN: Code duplicated, block: B:21:0x00a3  */
    /* JADX WARN: Code duplicated, block: B:22:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:25:0x00c7  */
    @Override // java.lang.Runnable
    public final void run() {
        int i;
        Object[] objArr;
        C0174f.m16a(4, "TPT", "run", new Object[0]);
        if (this.f151d) {
            this.f126a.f110d.m236a("push_token", (Object) this.f149b);
            this.f126a.f110d.m236a("push_token_enable", (Object) Boolean.valueOf(this.f150c));
            this.f126a.f110d.m236a("push_token_sent", (Object) false);
            if (C0178j.m216a(this.f126a.f110d.m237b("push"), false)) {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("token", this.f149b);
                if (this.f150c) {
                    i = 9;
                } else {
                    i = 10;
                }
                m274a(i, jSONObject, jSONObject2);
                this.f126a.f110d.m241c(jSONObject);
                this.f126a.f110d.m236a("push_token_sent", (Object) true);
                if (this.f126a.f114h.mo125c()) {
                    m288k();
                }
                objArr = new Object[]{"Complete"};
            } else {
                objArr = new Object[]{"Push Disabled: Skip"};
            }
        } else {
            String strM203a = C0178j.m203a(this.f126a.f110d.m237b("push_token"));
            Boolean boolM220b = C0178j.m220b(this.f126a.f110d.m237b("push_token_enable"));
            if (C0178j.m215a((Object) this.f149b, (Object) strM203a) && C0178j.m215a(Boolean.valueOf(this.f150c), boolM220b)) {
                objArr = new Object[]{"Skip"};
            } else {
                this.f126a.f110d.m236a("push_token", (Object) this.f149b);
                this.f126a.f110d.m236a("push_token_enable", (Object) Boolean.valueOf(this.f150c));
                this.f126a.f110d.m236a("push_token_sent", (Object) false);
                if (C0178j.m216a(this.f126a.f110d.m237b("push"), false)) {
                    objArr = new Object[]{"Push Disabled: Skip"};
                } else {
                    JSONObject jSONObject3 = new JSONObject();
                    JSONObject jSONObject4 = new JSONObject();
                    try {
                        jSONObject4.put("token", this.f149b);
                    } catch (JSONException e) {
                        C0174f.m16a(2, "TPT", "run", e);
                    }
                    if (this.f150c) {
                        i = 9;
                    } else {
                        i = 10;
                    }
                    m274a(i, jSONObject3, jSONObject4);
                    this.f126a.f110d.m241c(jSONObject3);
                    this.f126a.f110d.m236a("push_token_sent", (Object) true);
                    if (this.f126a.f114h.mo125c()) {
                        m288k();
                    }
                    objArr = new Object[]{"Complete"};
                }
            }
        }
        C0174f.m16a(4, "TPT", "run", objArr);
    }
}
