package com.p001a.p002a;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.a.a.q */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0185q extends AbstractRunnableC0182n {

    /* JADX INFO: renamed from: b */
    @Nullable
    private JSONObject f143b;

    @AnyThread
    C0185q(@NonNull C0181m c0181m) {
        super(c0181m, false);
        this.f143b = null;
    }

    @NonNull
    /* JADX INFO: renamed from: a */
    static String m290a(@Nullable String str) {
        JSONObject jSONObjectM231e;
        String strM207a = (str == null || "false".equalsIgnoreCase(str) || (jSONObjectM231e = C0178j.m231e(str)) == null) ? null : C0178j.m207a(jSONObjectM231e);
        return strM207a == null ? "{\"attribution\":\"false\"}" : strM207a;
    }

    @Override // java.lang.Runnable
    @WorkerThread
    public final void run() {
        C0174f.m16a(4, "TGA", "run", "run");
        final String strM203a = C0178j.m203a(this.f126a.f110d.m237b("attribution"));
        int iM202a = (int) (C0178j.m202a() / 1000);
        int iM219b = C0178j.m219b(this.f126a.f110d.m237b("attribution_time"), iM202a);
        int iM219b2 = C0178j.m219b(this.f126a.f110d.m237b("attribution_staleness"), -1);
        Object[] objArr = new Object[1];
        StringBuilder sb = new StringBuilder();
        sb.append("Now: ");
        sb.append(iM202a);
        sb.append(" Last: ");
        sb.append(iM219b);
        sb.append(" Staleness: ");
        sb.append(iM219b2);
        sb.append(" HasCache: ");
        sb.append(strM203a != null);
        objArr[0] = sb.toString();
        C0174f.m16a(4, "TGA", "run", objArr);
        if (iM219b == iM202a) {
            this.f126a.f110d.m236a("attribution_time", (Object) Integer.valueOf(iM202a));
        }
        boolean z = iM219b2 == -1 || iM219b + iM219b2 >= iM202a;
        if ((this.f126a.f111e == null && this.f126a.f112f == null) || ((strM203a != null && z) || (strM203a != null && this.f126a.f112f == null))) {
            C0174f.m16a(4, "TGA", "run", "Skip");
            m281d();
            m288k();
            return;
        }
        if (!m286i() && strM203a == null) {
            m285h();
            m273a(C0178j.m219b(this.f126a.f110d.m237b("getattribution_wait"), 7));
            return;
        }
        if (this.f143b == null) {
            C0174f.m16a(5, "TGA", "run", "Gather");
            this.f143b = new JSONObject();
            m274a(5, this.f143b, new JSONObject());
        }
        JSONObject jSONObjectA = m272a(5, (Object) this.f143b);
        if (m276a(jSONObjectA, true)) {
            return;
        }
        JSONObject jSONObjectM231e = C0178j.m231e(jSONObjectA.opt("data"));
        final String strM290a = m290a(jSONObjectM231e != null ? jSONObjectM231e.optString("attribution") : null);
        this.f126a.f110d.m236a("attribution", (Object) strM290a);
        if (C0178j.m215a((Object) strM290a, (Object) strM203a)) {
            C0174f.m16a(4, "TGA", "run", "Attribution Refresh Did Not Change");
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.a.a.q.1
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        if (C0185q.this.f126a.f112f != null) {
                            C0185q.this.f126a.f112f.m10a(strM290a);
                        }
                        if (strM203a != null || C0185q.this.f126a.f111e == null) {
                            return;
                        }
                        C0185q.this.f126a.f111e.mo9a(strM290a);
                    } catch (Throwable th) {
                        C0174f.m16a(2, "TGA", "run", "Exception in Host App", th);
                    }
                }
            });
        }
        this.f126a.f110d.m236a("attribution_time", (Object) Integer.valueOf((int) (C0178j.m202a() / 1000)));
        m281d();
        C0174f.m16a(3, "TGA", "Attribution", "Complete");
        C0174f.m16a(4, "TGA", "run", "Complete");
    }
}
