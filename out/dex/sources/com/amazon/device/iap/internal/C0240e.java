package com.amazon.device.iap.internal;

import android.util.Log;
import com.amazon.device.iap.internal.p003a.C0196d;
import com.amazon.device.iap.internal.p004b.C0227g;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.e */
/* JADX INFO: compiled from: ImplementationFactory.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0240e {

    /* JADX INFO: renamed from: a */
    private static final String f234a = "com.amazon.device.iap.internal.e";

    /* JADX INFO: renamed from: b */
    private static volatile boolean f235b;

    /* JADX INFO: renamed from: c */
    private static volatile boolean f236c;

    /* JADX INFO: renamed from: d */
    private static volatile InterfaceC0233c f237d;

    /* JADX INFO: renamed from: e */
    private static volatile InterfaceC0192a f238e;

    /* JADX INFO: renamed from: f */
    private static volatile InterfaceC0197b f239f;

    /* JADX INFO: renamed from: a */
    private static <T> T m392a(Class<T> cls) {
        try {
            return m396d().mo324a(cls).newInstance();
        } catch (Exception e) {
            Log.e(f234a, "error getting instance for " + cls, e);
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    public static boolean m393a() {
        if (f236c) {
            return f235b;
        }
        synchronized (C0240e.class) {
            if (f236c) {
                return f235b;
            }
            try {
                C0240e.class.getClassLoader().loadClass("com.amazon.android.Kiwi");
                f235b = false;
            } catch (Throwable unused) {
                f235b = true;
            }
            f236c = true;
            return f235b;
        }
    }

    /* JADX INFO: renamed from: b */
    public static InterfaceC0233c m394b() {
        if (f237d == null) {
            synchronized (C0240e.class) {
                if (f237d == null) {
                    f237d = (InterfaceC0233c) m392a(InterfaceC0233c.class);
                }
            }
        }
        return f237d;
    }

    /* JADX INFO: renamed from: c */
    public static InterfaceC0192a m395c() {
        if (f238e == null) {
            synchronized (C0240e.class) {
                if (f238e == null) {
                    f238e = (InterfaceC0192a) m392a(InterfaceC0192a.class);
                }
            }
        }
        return f238e;
    }

    /* JADX INFO: renamed from: d */
    private static InterfaceC0197b m396d() {
        if (f239f == null) {
            synchronized (C0240e.class) {
                if (f239f == null) {
                    if (m393a()) {
                        f239f = new C0196d();
                    } else {
                        f239f = new C0227g();
                    }
                }
            }
        }
        return f239f;
    }
}
