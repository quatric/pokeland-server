package com.amazon.device.iap.internal.p013c;

import com.amazon.device.iap.internal.util.C0245d;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.c.b */
/* JADX INFO: compiled from: PurchaseRequestTracker.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0235b {

    /* JADX INFO: renamed from: b */
    private static final C0235b f219b = new C0235b();

    /* JADX INFO: renamed from: a */
    private final Set<String> f220a = new ConcurrentSkipListSet();

    /* JADX INFO: renamed from: a */
    public static C0235b m370a() {
        return f219b;
    }

    /* JADX INFO: renamed from: a */
    public boolean m371a(String str) {
        if (C0245d.m411a(str)) {
            return false;
        }
        return this.f220a.remove(str);
    }

    /* JADX INFO: renamed from: b */
    public void m372b(String str) {
        if (C0245d.m411a(str)) {
            return;
        }
        this.f220a.add(str);
    }
}
