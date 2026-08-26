package com.amazon.device.iap.internal.p013c;

import android.content.Context;
import android.content.SharedPreferences;
import com.amazon.device.iap.internal.C0239d;
import com.amazon.device.iap.internal.util.C0245d;
import com.amazon.device.iap.internal.util.C0246e;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.c.c */
/* JADX INFO: compiled from: EntitlementTracker.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0236c {

    /* JADX INFO: renamed from: a */
    private static C0236c f221a = new C0236c();

    /* JADX INFO: renamed from: b */
    private static final String f222b = C0236c.class.getSimpleName();

    /* JADX INFO: renamed from: c */
    private static final String f223c = C0236c.class.getName() + "_PREFS_";

    /* JADX INFO: renamed from: a */
    public static C0236c m373a() {
        return f221a;
    }

    /* JADX INFO: renamed from: a */
    public String m374a(String str, String str2) {
        C0246e.m412a(f222b, "enter getReceiptIdFromSku for sku [" + str2 + "], user [" + str + "]");
        String string = null;
        try {
            C0245d.m409a(str, "userId");
            C0245d.m409a(str2, "sku");
            Context contextM390b = C0239d.m381d().m390b();
            C0245d.m408a(contextM390b, "context");
            string = contextM390b.getSharedPreferences(f223c + str, 0).getString(str2, null);
        } catch (Throwable th) {
            C0246e.m412a(f222b, "error in saving v1 Entitlement:" + str2 + ":" + th.getMessage());
        }
        C0246e.m412a(f222b, "leaving saveEntitlementRecord for sku [" + str2 + "], user [" + str + "]");
        return string;
    }

    /* JADX INFO: renamed from: a */
    public void m375a(String str, String str2, String str3) {
        C0246e.m412a(f222b, "enter saveEntitlementRecord for v1 Entitlement [" + str2 + "/" + str3 + "], user [" + str + "]");
        try {
            C0245d.m409a(str, "userId");
            C0245d.m409a(str2, "receiptId");
            C0245d.m409a(str3, "sku");
            Context contextM390b = C0239d.m381d().m390b();
            C0245d.m408a(contextM390b, "context");
            SharedPreferences.Editor editorEdit = contextM390b.getSharedPreferences(f223c + str, 0).edit();
            editorEdit.putString(str3, str2);
            editorEdit.commit();
        } catch (Throwable th) {
            C0246e.m412a(f222b, "error in saving v1 Entitlement:" + str2 + "/" + str3 + ":" + th.getMessage());
        }
        C0246e.m412a(f222b, "leaving saveEntitlementRecord for v1 Entitlement [" + str2 + "/" + str3 + "], user [" + str + "]");
    }
}
