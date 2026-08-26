package com.amazon.device.iap.internal.p004b;

import com.amazon.device.iap.internal.InterfaceC0192a;
import com.amazon.device.iap.internal.InterfaceC0197b;
import com.amazon.device.iap.internal.InterfaceC0233c;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.g */
/* JADX INFO: compiled from: KiwiImplementationRegistry.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0227g implements InterfaceC0197b {

    /* JADX INFO: renamed from: a */
    private static final Map<Class, Class> f194a = new HashMap();

    static {
        f194a.put(InterfaceC0233c.class, C0208c.class);
        f194a.put(InterfaceC0192a.class, C0223f.class);
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0197b
    /* JADX INFO: renamed from: a */
    public <T> Class<T> mo324a(Class<T> cls) {
        return f194a.get(cls);
    }
}
