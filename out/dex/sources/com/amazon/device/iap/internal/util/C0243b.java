package com.amazon.device.iap.internal.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.amazon.device.iap.internal.C0239d;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.util.b */
/* JADX INFO: compiled from: CursorUtil.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0243b {

    /* JADX INFO: renamed from: a */
    private static final String f245a = C0243b.class.getName() + "_PREFS";

    /* JADX INFO: renamed from: a */
    public static String m405a(String str) {
        C0245d.m408a((Object) str, "userId");
        Context contextM390b = C0239d.m381d().m390b();
        C0245d.m408a(contextM390b, "context");
        return contextM390b.getSharedPreferences(f245a, 0).getString(str, null);
    }

    /* JADX INFO: renamed from: a */
    public static void m406a(String str, String str2) {
        C0245d.m408a((Object) str, "userId");
        Context contextM390b = C0239d.m381d().m390b();
        C0245d.m408a(contextM390b, "context");
        SharedPreferences.Editor editorEdit = contextM390b.getSharedPreferences(f245a, 0).edit();
        editorEdit.putString(str, str2);
        editorEdit.commit();
    }
}
