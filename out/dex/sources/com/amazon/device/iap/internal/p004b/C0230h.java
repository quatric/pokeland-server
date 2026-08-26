package com.amazon.device.iap.internal.p004b;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.h */
/* JADX INFO: compiled from: KiwiRequestContext.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0230h {

    /* JADX INFO: renamed from: b */
    static final /* synthetic */ boolean f199b = !C0230h.class.desiredAssertionStatus();

    /* JADX INFO: renamed from: a */
    public final Map<String, Object> f200a = new HashMap();

    /* JADX INFO: renamed from: a */
    public Object m346a() {
        return this.f200a.get("RESPONSE");
    }

    /* JADX INFO: renamed from: a */
    public Object m347a(String str) {
        return this.f200a.get(str);
    }

    /* JADX INFO: renamed from: a */
    public void m348a(Object obj) {
        if (!f199b && obj == null) {
            throw new AssertionError();
        }
        this.f200a.put("RESPONSE", obj);
    }

    /* JADX INFO: renamed from: a */
    public void m349a(String str, Object obj) {
        this.f200a.put(str, obj);
    }

    /* JADX INFO: renamed from: b */
    public void m350b() {
        this.f200a.remove("RESPONSE");
    }
}
