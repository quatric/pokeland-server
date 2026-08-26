package com.p001a.p002a;

import android.support.annotation.NonNull;

/* JADX INFO: renamed from: com.a.a.o */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0183o extends AbstractRunnableC0182n {

    /* JADX INFO: renamed from: b */
    private final boolean f136b;

    C0183o(@NonNull C0181m c0181m, boolean z) {
        super(c0181m, true);
        this.f136b = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Object[] objArr;
        C0174f.m16a(4, "TAL", "run", new Object[0]);
        Boolean boolM220b = C0178j.m220b(this.f126a.f110d.m237b("app_limit_tracking"));
        boolean zM215a = C0178j.m215a(Boolean.valueOf(this.f136b), boolM220b);
        C0174f.m16a(5, "TAL", "run", "cachedAppLimitAdTracking: " + boolM220b, "isEqual: " + zM215a);
        if (boolM220b == null || !zM215a) {
            this.f126a.f110d.m236a("app_limit_tracking", (Object) Boolean.valueOf(this.f136b));
            this.f126a.f110d.m236a("app_limit_trackingupd", (Object) true);
            if (C0191w.m297a((AbstractRunnableC0182n) this, false) && this.f126a.f114h.mo125c()) {
                m288k();
            }
            objArr = new Object[]{"Complete"};
        } else {
            objArr = new Object[]{"Skip"};
        }
        C0174f.m16a(4, "TAL", "run", objArr);
    }
}
