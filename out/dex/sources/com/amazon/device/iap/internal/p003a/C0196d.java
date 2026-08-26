package com.amazon.device.iap.internal.p003a;

import com.amazon.device.iap.internal.InterfaceC0192a;
import com.amazon.device.iap.internal.InterfaceC0197b;
import com.amazon.device.iap.internal.InterfaceC0233c;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.a.d */
/* JADX INFO: compiled from: SandboxImplementationRegistry.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0196d implements InterfaceC0197b {

    /* JADX INFO: renamed from: a */
    private static final Map<Class, Class> f157a = new HashMap();

    static {
        f157a.put(InterfaceC0233c.class, C0195c.class);
        f157a.put(InterfaceC0192a.class, C0193a.class);
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0197b
    /* JADX INFO: renamed from: a */
    public <T> Class<T> mo324a(Class<T> cls) {
        return f157a.get(cls);
    }
}
