package com.amazon.device.iap.internal.p013c;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import com.amazon.device.iap.internal.C0239d;
import com.amazon.device.iap.internal.p004b.C0213d;
import com.amazon.device.iap.internal.util.C0242a;
import com.amazon.device.iap.internal.util.C0245d;
import com.amazon.device.iap.internal.util.C0246e;
import com.amazon.device.iap.model.Receipt;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.c.a */
/* JADX INFO: compiled from: PendingReceiptsManager.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0234a {

    /* JADX INFO: renamed from: a */
    private static final String f212a = "a";

    /* JADX INFO: renamed from: b */
    private static final String f213b = C0234a.class.getName() + "_PREFS";

    /* JADX INFO: renamed from: c */
    private static final String f214c = C0234a.class.getName() + "_CLEANER_PREFS";

    /* JADX INFO: renamed from: d */
    private static int f215d = 604800000;

    /* JADX INFO: renamed from: e */
    private static final C0234a f216e = new C0234a();

    /* JADX INFO: renamed from: a */
    public static C0234a m359a() {
        return f216e;
    }

    /* JADX INFO: renamed from: a */
    private void m360a(long j) {
        Context contextM390b = C0239d.m381d().m390b();
        C0245d.m408a(contextM390b, "context");
        SharedPreferences.Editor editorEdit = contextM390b.getSharedPreferences(f214c, 0).edit();
        editorEdit.putLong("LAST_CLEANING_TIME", j);
        editorEdit.commit();
    }

    /* JADX INFO: renamed from: e */
    private void m364e() {
        C0246e.m412a(f212a, "enter old receipts cleanup! ");
        final Context contextM390b = C0239d.m381d().m390b();
        C0245d.m408a(contextM390b, "context");
        m360a(System.currentTimeMillis());
        new Handler().post(new Runnable() { // from class: com.amazon.device.iap.internal.c.a.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    C0246e.m412a(C0234a.f212a, "perform house keeping! ");
                    SharedPreferences sharedPreferences = contextM390b.getSharedPreferences(C0234a.f213b, 0);
                    for (String str : sharedPreferences.getAll().keySet()) {
                        try {
                            if (System.currentTimeMillis() - C0237d.m376a(sharedPreferences.getString(str, null)).m379c() > C0234a.f215d) {
                                C0246e.m412a(C0234a.f212a, "house keeping - try remove Receipt:" + str + " since it's too old");
                                C0234a.this.m366a(str);
                            }
                        } catch (C0238e unused) {
                            C0246e.m412a(C0234a.f212a, "house keeping - try remove Receipt:" + str + " since it's invalid ");
                            C0234a.this.m366a(str);
                        }
                    }
                } catch (Throwable th) {
                    C0246e.m412a(C0234a.f212a, "Error in running cleaning job:" + th);
                }
            }
        });
    }

    /* JADX INFO: renamed from: f */
    private long m365f() {
        Context contextM390b = C0239d.m381d().m390b();
        C0245d.m408a(contextM390b, "context");
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = contextM390b.getSharedPreferences(f214c, 0).getLong("LAST_CLEANING_TIME", 0L);
        if (j != 0) {
            return j;
        }
        m360a(jCurrentTimeMillis);
        return jCurrentTimeMillis;
    }

    /* JADX INFO: renamed from: a */
    public void m366a(String str) {
        C0246e.m412a(f212a, "enter removeReceipt for receipt[" + str + "]");
        Context contextM390b = C0239d.m381d().m390b();
        C0245d.m408a(contextM390b, "context");
        SharedPreferences.Editor editorEdit = contextM390b.getSharedPreferences(f213b, 0).edit();
        editorEdit.remove(str);
        editorEdit.commit();
        C0246e.m412a(f212a, "leave removeReceipt for receipt[" + str + "]");
    }

    /* JADX INFO: renamed from: a */
    public void m367a(String str, String str2, String str3, String str4) {
        C0246e.m412a(f212a, "enter saveReceipt for receipt [" + str4 + "]");
        try {
            C0245d.m409a(str2, "userId");
            C0245d.m409a(str3, "receiptId");
            C0245d.m409a(str4, "receiptString");
            Context contextM390b = C0239d.m381d().m390b();
            C0245d.m408a(contextM390b, "context");
            C0237d c0237d = new C0237d(str2, str4, str, System.currentTimeMillis());
            SharedPreferences.Editor editorEdit = contextM390b.getSharedPreferences(f213b, 0).edit();
            editorEdit.putString(str3, c0237d.m380d());
            editorEdit.commit();
        } catch (Throwable th) {
            C0246e.m412a(f212a, "error in saving pending receipt:" + str + "/" + str4 + ":" + th.getMessage());
        }
        C0246e.m412a(f212a, "leaving saveReceipt for receipt id [" + str3 + "]");
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: b */
    public Set<Receipt> m368b(String str) {
        Context contextM390b = C0239d.m381d().m390b();
        C0245d.m408a(contextM390b, "context");
        C0246e.m412a(f212a, "enter getLocalReceipts for user[" + str + "]");
        HashSet hashSet = new HashSet();
        if (C0245d.m411a(str)) {
            C0246e.m414b(f212a, "empty UserId: " + str);
            throw new RuntimeException("Invalid UserId:" + str);
        }
        Map<String, ?> all = contextM390b.getSharedPreferences(f213b, 0).getAll();
        for (String str2 : all.keySet()) {
            String str3 = (String) all.get(str2);
            try {
                C0237d c0237dM376a = C0237d.m376a(str3);
                hashSet.add(C0242a.m398a(new JSONObject(c0237dM376a.m378b()), str, c0237dM376a.m377a()));
            } catch (C0213d unused) {
                m366a(str2);
                C0246e.m414b(f212a, "failed to verify signature:[" + str3 + "]");
            } catch (JSONException unused2) {
                m366a(str2);
                C0246e.m414b(f212a, "failed to convert string to JSON object:[" + str3 + "]");
            } catch (Throwable unused3) {
                C0246e.m414b(f212a, "failed to load the receipt from SharedPreference:[" + str3 + "]");
            }
        }
        C0246e.m412a(f212a, "leaving getLocalReceipts for user[" + str + "], " + hashSet.size() + " local receipts found.");
        if (System.currentTimeMillis() - m365f() > f215d) {
            m364e();
        }
        return hashSet;
    }

    /* JADX INFO: renamed from: c */
    public String m369c(String str) {
        Context contextM390b = C0239d.m381d().m390b();
        C0245d.m408a(contextM390b, "context");
        if (!C0245d.m411a(str)) {
            String string = contextM390b.getSharedPreferences(f213b, 0).getString(str, null);
            if (string != null) {
                try {
                    return C0237d.m376a(string).m377a();
                } catch (C0238e unused) {
                }
            }
            return null;
        }
        C0246e.m414b(f212a, "empty receiptId: " + str);
        throw new RuntimeException("Invalid ReceiptId:" + str);
    }
}
