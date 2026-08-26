package com.metaps.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;
import com.metaps.analytics.C0832u;
import com.metaps.analytics.C0833v;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.common.g */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0853g {

    /* JADX INFO: renamed from: a */
    private static final String f892a = "ana.fq7.value";

    /* JADX INFO: renamed from: b */
    private static final String f893b = "ana.fq7.next";

    /* JADX INFO: renamed from: c */
    private static final String f894c = "ana.fq30.value";

    /* JADX INFO: renamed from: d */
    private static final String f895d = "ana.fq30.next";

    /* JADX INFO: renamed from: e */
    private static final String f896e = "ana.user.data";

    /* JADX INFO: renamed from: f */
    private static final String f897f = "ana.location.data";

    /* JADX INFO: renamed from: g */
    private static final String f898g = "ana.user.token";

    /* JADX INFO: renamed from: h */
    private static final String f899h = "ana.event.last.id";

    /* JADX INFO: renamed from: i */
    private static final String f900i = "ana.event.last.time";

    /* JADX INFO: renamed from: j */
    private static final String f901j = "retry.event.list";

    /* JADX INFO: renamed from: k */
    private static final String f902k = "install.referrer";

    /* JADX INFO: renamed from: l */
    private static final String f903l = "ana.attributes.new";

    /* JADX INFO: renamed from: m */
    private static final String f904m = "ana.attributes.list";

    /* JADX INFO: renamed from: n */
    private static final String f905n = "latest.session.time";

    /* JADX INFO: renamed from: o */
    private static final String f906o = "latest.serial.session.id";

    /* JADX INFO: renamed from: p */
    private static final String f907p = "push.notification.enabled";

    /* JADX INFO: renamed from: q */
    private static final String f908q = "ana.gdpr.country";

    /* JADX INFO: renamed from: r */
    private static final String f909r = "ana.gdpr.user";

    /* JADX INFO: renamed from: s */
    private static C0853g f910s;

    /* JADX INFO: renamed from: t */
    private final SharedPreferences f911t;

    /* JADX INFO: renamed from: u */
    private final AdvertisingIdHandler f912u;

    /* JADX INFO: renamed from: v */
    private final C0856j f913v;

    /* JADX INFO: renamed from: w */
    private long f914w;

    private C0853g(Context context) {
        this.f911t = context.getSharedPreferences(C0854h.f931m, 0);
        this.f912u = AdvertisingIdHandler.m897a(context);
        this.f913v = C0856j.m992a(context);
        this.f914w = C0854h.m969a(context);
    }

    /* JADX INFO: renamed from: a */
    public static C0853g m936a(Context context) {
        if (f910s == null) {
            f910s = new C0853g(context);
        }
        return f910s;
    }

    /* JADX INFO: renamed from: a */
    public static synchronized void m937a(Context context, String str) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(C0854h.f931m, 0).edit();
        if (str != null) {
            editorEdit.putString(f902k, str);
        } else {
            editorEdit.remove(f902k);
        }
        editorEdit.commit();
    }

    /* JADX INFO: renamed from: a */
    private synchronized void m938a(JSONObject jSONObject) {
        this.f911t.edit().putString(f904m, jSONObject.toString()).commit();
    }

    /* JADX INFO: renamed from: a */
    public static synchronized boolean m939a(Context context, int i) {
        SharedPreferences.Editor editorEdit;
        editorEdit = context.getSharedPreferences(C0854h.f931m, 0).edit();
        editorEdit.putInt(f908q, i);
        return editorEdit.commit();
    }

    /* JADX INFO: renamed from: a */
    public static synchronized boolean m940a(Context context, long j) {
        SharedPreferences.Editor editorEdit;
        editorEdit = context.getSharedPreferences(C0854h.f931m, 0).edit();
        editorEdit.putLong(f905n, j);
        return editorEdit.commit();
    }

    /* JADX INFO: renamed from: a */
    public static synchronized boolean m941a(Context context, boolean z) {
        SharedPreferences.Editor editorEdit;
        editorEdit = context.getSharedPreferences(C0854h.f931m, 0).edit();
        editorEdit.putBoolean(f907p, z);
        return editorEdit.commit();
    }

    /* JADX INFO: renamed from: b */
    public static synchronized String m942b(Context context) {
        return context.getSharedPreferences(C0854h.f931m, 0).getString(f902k, null);
    }

    /* JADX INFO: renamed from: b */
    public static synchronized boolean m943b(Context context, int i) {
        SharedPreferences.Editor editorEdit;
        editorEdit = context.getSharedPreferences(C0854h.f931m, 0).edit();
        editorEdit.putInt(f909r, i);
        return editorEdit.commit();
    }

    /* JADX INFO: renamed from: b */
    public static synchronized boolean m944b(Context context, String str) {
        SharedPreferences.Editor editorEdit;
        editorEdit = context.getSharedPreferences(C0854h.f931m, 0).edit();
        editorEdit.putString(f906o, str);
        return editorEdit.commit();
    }

    /* JADX INFO: renamed from: c */
    public static synchronized long m945c(Context context) {
        return context.getSharedPreferences(C0854h.f931m, 0).getLong(f905n, System.currentTimeMillis() / 1000);
    }

    /* JADX INFO: renamed from: d */
    public static synchronized String m946d(Context context) {
        return context.getSharedPreferences(C0854h.f931m, 0).getString(f906o, null);
    }

    /* JADX INFO: renamed from: e */
    public static synchronized boolean m947e(Context context) {
        return context.getSharedPreferences(C0854h.f931m, 0).getBoolean(f907p, true);
    }

    /* JADX INFO: renamed from: f */
    public static synchronized int m948f(Context context) {
        return context.getSharedPreferences(C0854h.f931m, 0).getInt(f908q, 0);
    }

    /* JADX INFO: renamed from: g */
    public static synchronized int m949g(Context context) {
        return context.getSharedPreferences(C0854h.f931m, 0).getInt(f909r, 0);
    }

    /* JADX INFO: renamed from: a */
    public synchronized long m950a() {
        return this.f914w;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0057 A[Catch: all -> 0x0130, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0034, B:8:0x003a, B:11:0x0045, B:14:0x0057, B:16:0x005e, B:17:0x0068, B:40:0x00c0, B:43:0x00d7, B:45:0x00e9, B:48:0x00fa, B:50:0x0100, B:53:0x0111, B:52:0x010a, B:47:0x00f3, B:19:0x0071, B:21:0x0079, B:31:0x0098, B:34:0x00a1, B:37:0x00a8, B:38:0x00ae, B:24:0x0082, B:27:0x0088, B:28:0x008e, B:39:0x00b2), top: B:60:0x0003, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:16:0x005e A[Catch: all -> 0x0130, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0034, B:8:0x003a, B:11:0x0045, B:14:0x0057, B:16:0x005e, B:17:0x0068, B:40:0x00c0, B:43:0x00d7, B:45:0x00e9, B:48:0x00fa, B:50:0x0100, B:53:0x0111, B:52:0x010a, B:47:0x00f3, B:19:0x0071, B:21:0x0079, B:31:0x0098, B:34:0x00a1, B:37:0x00a8, B:38:0x00ae, B:24:0x0082, B:27:0x0088, B:28:0x008e, B:39:0x00b2), top: B:60:0x0003, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:18:0x006f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:19:0x0071 A[Catch: all -> 0x0130, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0034, B:8:0x003a, B:11:0x0045, B:14:0x0057, B:16:0x005e, B:17:0x0068, B:40:0x00c0, B:43:0x00d7, B:45:0x00e9, B:48:0x00fa, B:50:0x0100, B:53:0x0111, B:52:0x010a, B:47:0x00f3, B:19:0x0071, B:21:0x0079, B:31:0x0098, B:34:0x00a1, B:37:0x00a8, B:38:0x00ae, B:24:0x0082, B:27:0x0088, B:28:0x008e, B:39:0x00b2), top: B:60:0x0003, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:21:0x0079 A[Catch: all -> 0x0130, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0034, B:8:0x003a, B:11:0x0045, B:14:0x0057, B:16:0x005e, B:17:0x0068, B:40:0x00c0, B:43:0x00d7, B:45:0x00e9, B:48:0x00fa, B:50:0x0100, B:53:0x0111, B:52:0x010a, B:47:0x00f3, B:19:0x0071, B:21:0x0079, B:31:0x0098, B:34:0x00a1, B:37:0x00a8, B:38:0x00ae, B:24:0x0082, B:27:0x0088, B:28:0x008e, B:39:0x00b2), top: B:60:0x0003, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:22:0x007e  */
    /* JADX WARN: Code duplicated, block: B:24:0x0082 A[Catch: all -> 0x0130, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0034, B:8:0x003a, B:11:0x0045, B:14:0x0057, B:16:0x005e, B:17:0x0068, B:40:0x00c0, B:43:0x00d7, B:45:0x00e9, B:48:0x00fa, B:50:0x0100, B:53:0x0111, B:52:0x010a, B:47:0x00f3, B:19:0x0071, B:21:0x0079, B:31:0x0098, B:34:0x00a1, B:37:0x00a8, B:38:0x00ae, B:24:0x0082, B:27:0x0088, B:28:0x008e, B:39:0x00b2), top: B:60:0x0003, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:27:0x0088 A[Catch: all -> 0x0130, LOOP:1: B:25:0x0083->B:27:0x0088, LOOP_END, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0034, B:8:0x003a, B:11:0x0045, B:14:0x0057, B:16:0x005e, B:17:0x0068, B:40:0x00c0, B:43:0x00d7, B:45:0x00e9, B:48:0x00fa, B:50:0x0100, B:53:0x0111, B:52:0x010a, B:47:0x00f3, B:19:0x0071, B:21:0x0079, B:31:0x0098, B:34:0x00a1, B:37:0x00a8, B:38:0x00ae, B:24:0x0082, B:27:0x0088, B:28:0x008e, B:39:0x00b2), top: B:60:0x0003, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x0098 A[Catch: all -> 0x0130, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0034, B:8:0x003a, B:11:0x0045, B:14:0x0057, B:16:0x005e, B:17:0x0068, B:40:0x00c0, B:43:0x00d7, B:45:0x00e9, B:48:0x00fa, B:50:0x0100, B:53:0x0111, B:52:0x010a, B:47:0x00f3, B:19:0x0071, B:21:0x0079, B:31:0x0098, B:34:0x00a1, B:37:0x00a8, B:38:0x00ae, B:24:0x0082, B:27:0x0088, B:28:0x008e, B:39:0x00b2), top: B:60:0x0003, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:32:0x009d  */
    /* JADX WARN: Code duplicated, block: B:34:0x00a1 A[Catch: all -> 0x0130, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0034, B:8:0x003a, B:11:0x0045, B:14:0x0057, B:16:0x005e, B:17:0x0068, B:40:0x00c0, B:43:0x00d7, B:45:0x00e9, B:48:0x00fa, B:50:0x0100, B:53:0x0111, B:52:0x010a, B:47:0x00f3, B:19:0x0071, B:21:0x0079, B:31:0x0098, B:34:0x00a1, B:37:0x00a8, B:38:0x00ae, B:24:0x0082, B:27:0x0088, B:28:0x008e, B:39:0x00b2), top: B:60:0x0003, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:37:0x00a8 A[Catch: all -> 0x0130, LOOP:0: B:35:0x00a2->B:37:0x00a8, LOOP_END, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0034, B:8:0x003a, B:11:0x0045, B:14:0x0057, B:16:0x005e, B:17:0x0068, B:40:0x00c0, B:43:0x00d7, B:45:0x00e9, B:48:0x00fa, B:50:0x0100, B:53:0x0111, B:52:0x010a, B:47:0x00f3, B:19:0x0071, B:21:0x0079, B:31:0x0098, B:34:0x00a1, B:37:0x00a8, B:38:0x00ae, B:24:0x0082, B:27:0x0088, B:28:0x008e, B:39:0x00b2), top: B:60:0x0003, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:42:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:47:0x00f3 A[Catch: all -> 0x0130, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0034, B:8:0x003a, B:11:0x0045, B:14:0x0057, B:16:0x005e, B:17:0x0068, B:40:0x00c0, B:43:0x00d7, B:45:0x00e9, B:48:0x00fa, B:50:0x0100, B:53:0x0111, B:52:0x010a, B:47:0x00f3, B:19:0x0071, B:21:0x0079, B:31:0x0098, B:34:0x00a1, B:37:0x00a8, B:38:0x00ae, B:24:0x0082, B:27:0x0088, B:28:0x008e, B:39:0x00b2), top: B:60:0x0003, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:52:0x010a A[Catch: all -> 0x0130, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0034, B:8:0x003a, B:11:0x0045, B:14:0x0057, B:16:0x005e, B:17:0x0068, B:40:0x00c0, B:43:0x00d7, B:45:0x00e9, B:48:0x00fa, B:50:0x0100, B:53:0x0111, B:52:0x010a, B:47:0x00f3, B:19:0x0071, B:21:0x0079, B:31:0x0098, B:34:0x00a1, B:37:0x00a8, B:38:0x00ae, B:24:0x0082, B:27:0x0088, B:28:0x008e, B:39:0x00b2), top: B:60:0x0003, inners: #1 }] */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: a */
    public synchronized C0833v m951a(boolean z) {
        C0833v c0833v;
        long jCurrentTimeMillis;
        c0833v = null;
        String string = this.f911t.getString(f896e, null);
        String string2 = this.f911t.getString(f892a, "");
        long j = 0;
        long jCurrentTimeMillis2 = this.f911t.getLong(f893b, 0L);
        String string3 = this.f911t.getString(f894c, "");
        long j2 = this.f911t.getLong(f895d, 0L);
        if (string == null || string.length() <= 0) {
            if (c0833v == null) {
                c0833v = new C0833v();
                if (z) {
                    string2 = "1";
                    jCurrentTimeMillis2 = System.currentTimeMillis() + C0854h.f927i;
                    string3 = "1";
                    j2 = jCurrentTimeMillis2;
                }
                c0833v.m825a(1);
                c0833v.m832b(1);
            } else {
                if (z) {
                    jCurrentTimeMillis = System.currentTimeMillis();
                    if (jCurrentTimeMillis2 == 0) {
                        string2 = "1";
                        jCurrentTimeMillis2 = jCurrentTimeMillis + C0854h.f927i;
                    } else if (jCurrentTimeMillis > jCurrentTimeMillis2) {
                        jCurrentTimeMillis2 += C0854h.f927i;
                        while (jCurrentTimeMillis > jCurrentTimeMillis2) {
                            jCurrentTimeMillis2 += C0854h.f927i;
                            string2 = C0860n.m1038a(string2, 0, 7);
                        }
                        string2 = C0860n.m1038a(string2, 1, 7);
                        j = 0;
                    }
                    if (j2 == j) {
                        string3 = "1";
                        j2 = jCurrentTimeMillis + C0854h.f927i;
                    } else if (jCurrentTimeMillis > j2) {
                        j2 += C0854h.f927i;
                        while (jCurrentTimeMillis > j2) {
                            j2 += C0854h.f927i;
                            string3 = C0860n.m1038a(string3, 0, 30);
                        }
                        string3 = C0860n.m1038a(string3, 1, 30);
                    }
                }
                c0833v.m825a(C0860n.m1037a(string2));
                c0833v.m832b(C0860n.m1037a(string3));
            }
            c0833v.m826a(this.f914w);
            c0833v.m833b(this.f912u.m898a());
            c0833v.m829a(this.f912u.m899b() ? false : true);
            c0833v.m836c(this.f913v.m1009a());
            if (c0833v.m834c() != null || c0833v.m834c().length() == 0) {
                c0833v.m828a(m960c());
            }
            if (c0833v.m837d() != null || c0833v.m837d().length() == 0) {
                Log.e(C0847a.f855a, "Failed to get Google Advertising Id");
            }
            SharedPreferences.Editor editorEdit = this.f911t.edit();
            editorEdit.putString(f892a, string2);
            editorEdit.putLong(f893b, jCurrentTimeMillis2);
            editorEdit.putString(f894c, string3);
            editorEdit.putLong(f895d, j2);
            editorEdit.commit();
        } else {
            try {
                c0833v = C0833v.m822a(new JSONObject(string));
            } catch (JSONException e) {
                C0847a.m905a(C0853g.class.toString(), "Failed to load PartUser from shared preferences", e);
            }
            if (c0833v == null) {
                c0833v = new C0833v();
                if (z) {
                    string2 = "1";
                    jCurrentTimeMillis2 = System.currentTimeMillis() + C0854h.f927i;
                    string3 = "1";
                    j2 = jCurrentTimeMillis2;
                }
                c0833v.m825a(1);
                c0833v.m832b(1);
            } else {
                if (z) {
                    jCurrentTimeMillis = System.currentTimeMillis();
                    if (jCurrentTimeMillis2 == 0) {
                        string2 = "1";
                        jCurrentTimeMillis2 = jCurrentTimeMillis + C0854h.f927i;
                    } else if (jCurrentTimeMillis > jCurrentTimeMillis2) {
                        jCurrentTimeMillis2 += C0854h.f927i;
                        while (jCurrentTimeMillis > jCurrentTimeMillis2) {
                            jCurrentTimeMillis2 += C0854h.f927i;
                            string2 = C0860n.m1038a(string2, 0, 7);
                        }
                        string2 = C0860n.m1038a(string2, 1, 7);
                        j = 0;
                    }
                    if (j2 == j) {
                        string3 = "1";
                        j2 = jCurrentTimeMillis + C0854h.f927i;
                    } else if (jCurrentTimeMillis > j2) {
                        j2 += C0854h.f927i;
                        while (jCurrentTimeMillis > j2) {
                            j2 += C0854h.f927i;
                            string3 = C0860n.m1038a(string3, 0, 30);
                        }
                        string3 = C0860n.m1038a(string3, 1, 30);
                    }
                }
                c0833v.m825a(C0860n.m1037a(string2));
                c0833v.m832b(C0860n.m1037a(string3));
            }
            c0833v.m826a(this.f914w);
            c0833v.m833b(this.f912u.m898a());
            c0833v.m829a(this.f912u.m899b() ? false : true);
            c0833v.m836c(this.f913v.m1009a());
            if (c0833v.m834c() != null) {
                c0833v.m828a(m960c());
            } else {
                c0833v.m828a(m960c());
            }
            if (c0833v.m837d() != null) {
                Log.e(C0847a.f855a, "Failed to get Google Advertising Id");
            } else {
                Log.e(C0847a.f855a, "Failed to get Google Advertising Id");
            }
            SharedPreferences.Editor editorEdit2 = this.f911t.edit();
            editorEdit2.putString(f892a, string2);
            editorEdit2.putLong(f893b, jCurrentTimeMillis2);
            editorEdit2.putString(f894c, string3);
            editorEdit2.putLong(f895d, j2);
            editorEdit2.commit();
        }
        throw th;
        return c0833v;
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m952a(long j) {
        this.f914w = j;
        SharedPreferences.Editor editorEdit = this.f911t.edit();
        editorEdit.putLong(C0854h.f932n, j);
        editorEdit.commit();
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m953a(C0832u c0832u) {
        try {
            SharedPreferences.Editor editorEdit = this.f911t.edit();
            editorEdit.putString(f897f, c0832u.m815a().toString());
            editorEdit.commit();
        } catch (JSONException e) {
            C0847a.m905a(C0853g.class.toString(), "Failed to save partLocation to shared preferences", e);
        }
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m954a(C0833v c0833v) {
        try {
            SharedPreferences.Editor editorEdit = this.f911t.edit();
            editorEdit.putString(f896e, c0833v.m831b(true).toString());
            editorEdit.commit();
        } catch (JSONException e) {
            C0847a.m905a(C0853g.class.toString(), "Failed to save PartUser to shared preferences", e);
        }
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m955a(String str) {
        SharedPreferences.Editor editorEdit = this.f911t.edit();
        editorEdit.putString(f901j, str);
        editorEdit.commit();
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m956a(String str, long j) {
        SharedPreferences.Editor editorEdit = this.f911t.edit();
        editorEdit.putString(f899h, str);
        editorEdit.putLong(f900i, j);
        editorEdit.commit();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: a */
    public synchronized void m957a(String str, String str2) {
        JSONObject jSONObjectM966i = m966i();
        if (jSONObjectM966i == null) {
            jSONObjectM966i = new JSONObject();
        }
        try {
            if (jSONObjectM966i.has(str)) {
                String string = jSONObjectM966i.getString(str);
                if (str2 == null) {
                    jSONObjectM966i.remove(str);
                    m959b(true);
                } else if (!string.equals(str2)) {
                    jSONObjectM966i.put(str, str2);
                    m959b(true);
                }
                m938a(jSONObjectM966i);
            } else if (str2 != null) {
                jSONObjectM966i.put(str, str2);
                m959b(true);
                m938a(jSONObjectM966i);
            }
        } catch (JSONException e) {
            C0847a.m905a(C0853g.class.toString(), "Failed to get attribute for key " + str, e);
        }
    }

    /* JADX WARN: Code duplicated, block: B:12:0x002a A[Catch: all -> 0x0068, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x000c, B:7:0x0012, B:10:0x001d, B:12:0x002a, B:13:0x002f, B:15:0x0039), top: B:21:0x0001, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:15:0x0039 A[Catch: all -> 0x0068, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x000c, B:7:0x0012, B:10:0x001d, B:12:0x002a, B:13:0x002f, B:15:0x0039), top: B:21:0x0001, inners: #1 }] */
    /* JADX INFO: renamed from: b */
    public synchronized C0832u m958b() {
        C0832u c0832u;
        Location locationM988b;
        c0832u = null;
        String string = this.f911t.getString(f897f, null);
        if (string == null || string.length() <= 0) {
            if (c0832u == null) {
                c0832u = new C0832u();
            }
            locationM988b = C0855i.m978a().m988b();
            if (locationM988b != null) {
                c0832u.m816a(locationM988b.getLatitude());
                c0832u.m820b(locationM988b.getLongitude());
                c0832u.m821c(locationM988b.getAltitude());
                c0832u.m818a(locationM988b.getTime() / 1000);
                c0832u.m817a(locationM988b.getAccuracy());
                c0832u.m819a(locationM988b.getProvider());
            }
        } else {
            try {
                c0832u = C0832u.m814a(new JSONObject(string));
            } catch (JSONException e) {
                C0847a.m905a(C0853g.class.toString(), "Failed to load PartLocation from shared preferences", e);
            }
            if (c0832u == null) {
                c0832u = new C0832u();
            }
            locationM988b = C0855i.m978a().m988b();
            if (locationM988b != null) {
                c0832u.m816a(locationM988b.getLatitude());
                c0832u.m820b(locationM988b.getLongitude());
                c0832u.m821c(locationM988b.getAltitude());
                c0832u.m818a(locationM988b.getTime() / 1000);
                c0832u.m817a(locationM988b.getAccuracy());
                c0832u.m819a(locationM988b.getProvider());
            }
        }
        throw th;
        return c0832u;
    }

    /* JADX INFO: renamed from: b */
    public synchronized void m959b(boolean z) {
        this.f911t.edit().putBoolean(f903l, z).commit();
    }

    /* JADX INFO: renamed from: c */
    public synchronized String m960c() {
        String string;
        string = this.f911t.getString(f898g, null);
        if (string == null || string.length() == 0) {
            string = UUID.randomUUID().toString();
            SharedPreferences.Editor editorEdit = this.f911t.edit();
            editorEdit.putString(f898g, string);
            editorEdit.commit();
        }
        return string;
    }

    /* JADX INFO: renamed from: d */
    public synchronized String m961d() {
        return this.f911t.getString(f899h, null);
    }

    /* JADX INFO: renamed from: e */
    public synchronized long m962e() {
        return this.f911t.getLong(f900i, 0L);
    }

    /* JADX INFO: renamed from: f */
    public synchronized String m963f() {
        return this.f911t.getString(f901j, "[]");
    }

    /* JADX INFO: renamed from: g */
    public synchronized void m964g() {
        SharedPreferences.Editor editorEdit = this.f911t.edit();
        editorEdit.remove(f901j);
        editorEdit.commit();
    }

    /* JADX INFO: renamed from: h */
    public synchronized boolean m965h() {
        return this.f911t.getBoolean(f903l, false);
    }

    /* JADX INFO: renamed from: i */
    public synchronized JSONObject m966i() {
        JSONObject jSONObject;
        String string = this.f911t.getString(f904m, null);
        if (string != null) {
            try {
                jSONObject = new JSONObject(string);
            } catch (JSONException e) {
                C0847a.m905a(C0853g.class.toString(), "Failed to load attributes for SharedPreferences", e);
                jSONObject = null;
                return jSONObject;
            }
        } else {
            jSONObject = null;
        }
        return jSONObject;
    }

    /* JADX INFO: renamed from: j */
    public synchronized int m967j() {
        return this.f911t.getInt(f909r, 0);
    }
}
