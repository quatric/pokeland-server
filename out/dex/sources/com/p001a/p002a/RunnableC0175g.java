package com.p001a.p002a;

import android.app.Application;
import android.content.Context;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.annotation.WorkerThread;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import org.jetbrains.annotations.Contract;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.a.a.g */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class RunnableC0175g implements InterfaceC0176h, InterfaceC0179k, Runnable {

    /* JADX INFO: renamed from: a */
    @VisibleForTesting
    @NonNull
    final C0181m f74a;

    /* JADX INFO: renamed from: b */
    @VisibleForTesting
    @NonNull
    final C0187s f75b;

    /* JADX INFO: renamed from: c */
    @VisibleForTesting
    @NonNull
    final C0191w f76c;

    /* JADX INFO: renamed from: d */
    @VisibleForTesting
    @NonNull
    final C0188t f77d;

    /* JADX INFO: renamed from: e */
    @VisibleForTesting
    @NonNull
    final C0185q f78e;

    /* JADX INFO: renamed from: f */
    @VisibleForTesting
    @NonNull
    final C0190v f79f;

    /* JADX INFO: renamed from: g */
    @Nullable
    private final ComponentCallbacks2C0180l f80g;

    @AnyThread
    RunnableC0175g(@NonNull Context context, @NonNull String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable InterfaceC0169a interfaceC0169a, @Nullable InterfaceC0170b interfaceC0170b, @NonNull JSONObject jSONObject, @Nullable JSONObject jSONObject2) {
        Object[] objArr = new Object[6];
        objArr[0] = "version: " + str;
        objArr[1] = "extensionDate: " + str2;
        objArr[2] = "appGuid: " + str3;
        objArr[3] = "partnerName: " + str4;
        StringBuilder sb = new StringBuilder();
        sb.append("attributionListener: ");
        sb.append(interfaceC0169a != null);
        objArr[4] = sb.toString();
        objArr[5] = "custom: " + jSONObject2;
        C0174f.m16a(4, "CTR", "Controller", objArr);
        this.f74a = new C0181m(context, this, this, jSONObject, interfaceC0169a, interfaceC0170b);
        this.f75b = new C0187s(this.f74a);
        this.f76c = new C0191w(this.f74a);
        this.f77d = new C0188t(this.f74a);
        this.f78e = new C0185q(this.f74a);
        this.f79f = new C0190v(this.f74a);
        this.f74a.f110d.m236a("sdk_version", (Object) str);
        if (str3 != null) {
            this.f74a.f110d.m236a("kochava_app_id", (Object) str3);
        } else {
            this.f74a.f110d.m235a("kochava_app_id");
        }
        if (str4 != null) {
            this.f74a.f110d.m236a("partner_name", (Object) str4);
        } else {
            this.f74a.f110d.m235a("partner_name");
        }
        if (jSONObject2 != null) {
            this.f74a.f110d.m236a("custom", (Object) jSONObject2);
        } else {
            this.f74a.f110d.m235a("custom");
        }
        if (str2 != null) {
            this.f74a.f110d.m236a("ext_date", (Object) str2);
        } else {
            this.f74a.f110d.m235a("ext_date");
        }
        if (C0178j.m203a(this.f74a.f110d.m237b("kochava_device_id")) == null) {
            String strReplace = "3.3.1".replace(".", "");
            String str5 = new SimpleDateFormat("yyyyMMdd", Locale.US).format(new Date(C0178j.m202a()));
            String strReplaceAll = UUID.randomUUID().toString().replaceAll("-", "");
            this.f74a.f110d.m236a("kochava_device_id", (Object) ("KA" + strReplace + str5 + strReplaceAll));
        }
        C0181m c0181m = this.f74a;
        c0181m.m251a(c0181m.f113g, 50L);
        this.f80g = new ComponentCallbacks2C0180l(this.f74a.f107a, this.f74a.f115i, this);
    }

    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: a */
    final String m115a(@NonNull String str) {
        return C0178j.m204a(this.f74a.f110d.m237b(str), "");
    }

    /* JADX INFO: renamed from: a */
    final void m116a() {
        C0181m c0181m = this.f74a;
        if (c0181m == null) {
            return;
        }
        if (c0181m.f115i != null) {
            this.f74a.f115i.removeCallbacksAndMessages(null);
        }
        if (this.f74a.f116j != null) {
            this.f74a.f116j.removeCallbacksAndMessages(null);
        }
        if (this.f74a.f117k != null) {
            this.f74a.f117k.quit();
        }
        if (this.f74a.f118l != null) {
            this.f74a.f118l.quit();
        }
        if (this.f74a.f110d != null) {
            this.f74a.f110d.m238b();
        }
        if (this.f80g == null || this.f74a.f107a == null) {
            return;
        }
        ((Application) this.f74a.f107a).unregisterActivityLifecycleCallbacks(this.f80g);
        this.f74a.f107a.unregisterComponentCallbacks(this.f80g);
    }

    @AnyThread
    /* JADX INFO: renamed from: a */
    final void m117a(int i, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5) {
        C0174f.m16a(5, "CTR", "sendEvent", new Object[0]);
        C0181m c0181m = this.f74a;
        c0181m.m250a(new C0184p(c0181m, i, str, str2, str3, str4, str5));
    }

    @AnyThread
    /* JADX INFO: renamed from: a */
    final void m118a(@NonNull C0174f.c cVar) {
        C0181m c0181m = this.f74a;
        c0181m.m252a((Runnable) new C0186r(c0181m, cVar), false);
    }

    @AnyThread
    /* JADX INFO: renamed from: a */
    final void m119a(@NonNull String str, boolean z) {
        C0181m c0181m = this.f74a;
        c0181m.m252a((Runnable) new C0189u(c0181m, str, z, false), false);
    }

    /* JADX INFO: renamed from: a */
    final void m120a(boolean z) {
        if (this.f74a.f120n == z) {
            C0174f.m16a(4, "CTR", "setSleep", "Rejecting same as current");
            return;
        }
        C0181m c0181m = this.f74a;
        c0181m.f120n = z;
        C0174f.m16a(3, "CTR", "setSleep", Boolean.valueOf(c0181m.f120n));
        if (this.f74a.f120n) {
            return;
        }
        C0181m c0181m2 = this.f74a;
        c0181m2.m252a(c0181m2.f113g, true);
    }

    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: b */
    final JSONObject m121b(@NonNull String str) {
        return C0178j.m221b(this.f74a.f110d.m237b(str), true);
    }

    @AnyThread
    /* JADX INFO: renamed from: b */
    final void m122b(boolean z) {
        C0181m c0181m = this.f74a;
        c0181m.m252a((Runnable) new C0183o(c0181m, z), false);
    }

    @Contract(pure = true)
    /* JADX INFO: renamed from: b */
    final boolean m123b() {
        return this.f74a.f120n;
    }

    @Override // com.p001a.p002a.InterfaceC0179k
    @AnyThread
    /* JADX INFO: renamed from: c */
    public final void mo124c(boolean z) {
        C0181m c0181m;
        C0184p c0184p;
        boolean zM216a = C0178j.m216a(this.f74a.f110d.m237b("session_tracking"), true);
        boolean z2 = C0178j.m203a(this.f74a.f110d.m237b("initial_data")) != null;
        boolean zM216a2 = C0178j.m216a(this.f74a.f110d.m237b("initial_ever_sent"), false);
        if (zM216a) {
            if (z2 || zM216a2) {
                if (z) {
                    this.f74a.f121o = C0178j.m202a();
                    c0181m = this.f74a;
                    c0184p = new C0184p(c0181m, 2, null, null, null, null, null);
                } else {
                    c0181m = this.f74a;
                    c0184p = new C0184p(c0181m, 3, null, null, null, null, null);
                }
                c0181m.m252a((Runnable) c0184p, false);
            }
        }
    }

    @Override // com.p001a.p002a.InterfaceC0176h
    @Contract(pure = true)
    /* JADX INFO: renamed from: c */
    public final boolean mo125c() {
        return this.f75b.m282e() && this.f76c.m282e() && this.f77d.m282e() && this.f78e.m282e();
    }

    @Override // com.p001a.p002a.InterfaceC0176h
    @Contract(pure = true)
    /* JADX INFO: renamed from: d */
    public final boolean mo126d() {
        ComponentCallbacks2C0180l componentCallbacks2C0180l = this.f80g;
        return componentCallbacks2C0180l == null || componentCallbacks2C0180l.m249a();
    }

    @Override // java.lang.Runnable
    @WorkerThread
    public final void run() {
        C0174f.m16a(5, "CTR", "Controller", "WAKE");
        if (this.f74a.f120n) {
            C0174f.m16a(5, "CTR", "Controller", "SLEEP: SKIP");
            return;
        }
        this.f74a.m254b();
        if (this.f75b.m282e()) {
            int iM219b = C0178j.m219b(this.f74a.f110d.m237b("kvinit_staleness"), 2592000);
            int iM219b2 = C0178j.m219b(this.f74a.f110d.m237b("init_last_sent"), (int) (C0178j.m202a() / 1000));
            C0174f.m16a(5, "CTR", "Controller", "initLastSent: " + iM219b2, "initStaleness: " + iM219b, "now: " + ((int) (C0178j.m202a() / 1000)));
            if (iM219b2 + iM219b <= ((int) (C0178j.m202a() / 1000))) {
                this.f75b.m287j();
            }
        }
        if (!this.f75b.m282e()) {
            C0174f.m16a(5, "CTR", "Controller", "INIT");
            if (this.f75b.m284g()) {
                C0174f.m16a(5, "CTR", "Controller", "INIT SKIP");
                return;
            } else {
                this.f75b.m283f();
                this.f74a.m252a((Runnable) this.f75b, true);
                return;
            }
        }
        if (C0178j.m216a(this.f74a.f110d.m237b("push"), false)) {
            String strM203a = C0178j.m203a(this.f74a.f110d.m237b("push_token"));
            Boolean boolM220b = C0178j.m220b(this.f74a.f110d.m237b("push_token_enable"));
            boolean zM216a = C0178j.m216a(this.f74a.f110d.m237b("push_token_sent"), false);
            if (strM203a != null && boolM220b != null && !zM216a) {
                C0181m c0181m = this.f74a;
                c0181m.m252a((Runnable) new C0189u(c0181m, strM203a, boolM220b.booleanValue(), true), true);
            }
        }
        if (!this.f76c.m282e()) {
            C0174f.m16a(5, "CTR", "Controller", "UPDATE");
            if (this.f76c.m284g()) {
                C0174f.m16a(5, "CTR", "Controller", "UPDATE SKIP");
                return;
            } else {
                this.f76c.m283f();
                this.f74a.m252a((Runnable) this.f76c, true);
                return;
            }
        }
        if (!this.f77d.m282e()) {
            C0174f.m16a(5, "CTR", "Controller", "INITIAL");
            if (this.f77d.m284g()) {
                C0174f.m16a(5, "CTR", "Controller", "INITIAL SKIP");
                return;
            } else {
                this.f77d.m283f();
                this.f74a.m252a((Runnable) this.f77d, true);
                return;
            }
        }
        if (!this.f78e.m282e()) {
            C0174f.m16a(5, "CTR", "Controller", "GET_ATTRIBUTION");
            if (this.f78e.m284g()) {
                C0174f.m16a(5, "CTR", "Controller", "GET_ATTRIBUTION SKIP");
            } else {
                this.f78e.m283f();
                this.f74a.m252a((Runnable) this.f78e, true);
            }
        }
        if (this.f79f.m282e()) {
            return;
        }
        C0174f.m16a(5, "CTR", "Controller", "QUEUE");
        if (this.f79f.m284g()) {
            C0174f.m16a(5, "CTR", "Controller", "QUEUE SKIP");
        } else {
            this.f79f.m283f();
            this.f74a.m252a((Runnable) this.f79f, false);
        }
    }
}
