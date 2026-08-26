package com.metaps.common;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.common.f */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0852f {

    /* JADX INFO: renamed from: a */
    public static final int f882a = 2;

    /* JADX INFO: renamed from: b */
    private static final String f883b = "gdpr_country";

    /* JADX INFO: renamed from: c */
    private static C0852f f884c;

    /* JADX INFO: renamed from: d */
    private boolean f885d = false;

    /* JADX INFO: renamed from: e */
    private Integer f886e = null;

    /* JADX INFO: renamed from: a */
    public static synchronized C0852f m930a() {
        if (f884c != null) {
            return f884c;
        }
        C0852f c0852f = new C0852f();
        f884c = c0852f;
        return c0852f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: b */
    public void m932b(Context context, C0849c c0849c, String str, String str2) {
        String string;
        String str3;
        try {
            try {
                C0849c.a aVarM918a = c0849c.m918a(C0854h.m971a(str, str2, C0854h.f926h), "application/x-www-form-urlencoded");
                if (aVarM918a.f871f == 200) {
                    synchronized (this) {
                        JSONObject jSONObject = aVarM918a.f876k;
                        if (jSONObject != null) {
                            this.f886e = Integer.valueOf(jSONObject.getInt(f883b));
                            C0853g.m939a(context, this.f886e.intValue());
                        }
                    }
                } else {
                    C0847a.m909b(C0852f.class.toString(), "Error while fetching Gdpr config : " + aVarM918a.f871f + " - " + aVarM918a.f873h);
                }
            } catch (C0848b e) {
                e = e;
                string = C0852f.class.toString();
                str3 = "Failed to fetch Gdpr config";
                C0847a.m905a(string, str3, e);
            } catch (JSONException e2) {
                e = e2;
                string = C0852f.class.toString();
                str3 = "Failed to decode Gdpr config";
                C0847a.m905a(string, str3, e);
            }
            this.f885d = true;
        } catch (Throwable th) {
            this.f885d = true;
            throw th;
        }
    }

    /* JADX INFO: renamed from: a */
    public void m933a(final Context context, final C0849c c0849c, final String str, final String str2) {
        new Thread() { // from class: com.metaps.common.f.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                synchronized (this) {
                    C0852f.this.m932b(context, c0849c, str, str2);
                }
            }
        }.start();
    }

    /* JADX INFO: renamed from: b */
    public Integer m934b() {
        Integer num;
        synchronized (this) {
            num = this.f886e;
        }
        return num;
    }

    /* JADX INFO: renamed from: c */
    public boolean m935c() {
        boolean z;
        synchronized (this) {
            z = this.f885d;
        }
        return z;
    }
}
