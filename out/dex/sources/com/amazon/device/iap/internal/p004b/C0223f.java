package com.amazon.device.iap.internal.p004b;

import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.device.iap.internal.InterfaceC0192a;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.f */
/* JADX INFO: compiled from: KiwiLogHandler.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0223f implements InterfaceC0192a {

    /* JADX INFO: renamed from: a */
    private static KiwiLogger f193a = new KiwiLogger("In App Purchasing SDK - Production Mode");

    /* JADX INFO: renamed from: c */
    private static String m344c(String str, String str2) {
        return str + ": " + str2;
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0192a
    /* JADX INFO: renamed from: a */
    public void mo298a(String str, String str2) {
        f193a.trace(m344c(str, str2));
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0192a
    /* JADX INFO: renamed from: a */
    public boolean mo299a() {
        return KiwiLogger.TRACE_ON;
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0192a
    /* JADX INFO: renamed from: b */
    public void mo300b(String str, String str2) {
        f193a.error(m344c(str, str2));
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0192a
    /* JADX INFO: renamed from: b */
    public boolean mo301b() {
        return KiwiLogger.ERROR_ON;
    }
}
