package com.amazon.device.iap.internal.p003a;

import android.util.Log;
import com.amazon.device.iap.internal.InterfaceC0192a;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.a.a */
/* JADX INFO: compiled from: SandboxLogHandler.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0193a implements InterfaceC0192a {
    /* JADX INFO: renamed from: a */
    private static String m302a(String str) {
        return "In App Purchasing SDK - Sandbox Mode: " + str;
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0192a
    /* JADX INFO: renamed from: a */
    public void mo298a(String str, String str2) {
        Log.d(str, m302a(str2));
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0192a
    /* JADX INFO: renamed from: a */
    public boolean mo299a() {
        return true;
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0192a
    /* JADX INFO: renamed from: b */
    public void mo300b(String str, String str2) {
        Log.e(str, m302a(str2));
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0192a
    /* JADX INFO: renamed from: b */
    public boolean mo301b() {
        return true;
    }
}
