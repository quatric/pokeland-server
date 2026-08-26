package com.nintendo.npf.sdk.internal.p022d;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.d.a */
/* JADX INFO: compiled from: AnalyticsConfig.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0946a {

    /* JADX INFO: renamed from: a */
    private static int f1222a = 10000;

    /* JADX INFO: renamed from: d */
    private String f1225d;

    /* JADX INFO: renamed from: g */
    private String f1228g;

    /* JADX INFO: renamed from: h */
    private String f1229h;

    /* JADX INFO: renamed from: i */
    private String f1230i;

    /* JADX INFO: renamed from: j */
    private String f1231j;

    /* JADX INFO: renamed from: k */
    private String f1232k;

    /* JADX INFO: renamed from: b */
    private a f1223b = a.NONE;

    /* JADX INFO: renamed from: c */
    private long f1224c = 0;

    /* JADX INFO: renamed from: e */
    private boolean f1226e = true;

    /* JADX INFO: renamed from: f */
    private int f1227f = 60000;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.d.a$a */
    /* JADX INFO: compiled from: AnalyticsConfig.java */
    public enum a {
        NONE("NONE"),
        V1("V1"),
        V2("V2");


        /* JADX INFO: renamed from: a */
        private final String f1236a;

        a(String str) {
            this.f1236a = str;
        }

        /* JADX INFO: renamed from: a */
        public static a m1304a(String str) {
            for (a aVar : values()) {
                if (aVar.f1236a.equals(str)) {
                    return aVar;
                }
            }
            return NONE;
        }
    }

    /* JADX INFO: renamed from: a */
    public a m1283a() {
        return this.f1223b;
    }

    /* JADX INFO: renamed from: a */
    public void m1284a(int i) {
        this.f1227f = i;
        int i2 = this.f1227f;
        int i3 = f1222a;
        if (i2 < i3) {
            this.f1227f = i3;
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1285a(long j) {
        this.f1224c = j;
    }

    /* JADX INFO: renamed from: a */
    public void m1286a(a aVar) {
        this.f1223b = aVar;
    }

    /* JADX INFO: renamed from: a */
    public void m1287a(String str) {
        this.f1225d = str;
    }

    /* JADX INFO: renamed from: a */
    public void m1288a(boolean z) {
        this.f1226e = z;
    }

    /* JADX INFO: renamed from: b */
    public long m1289b() {
        return this.f1224c;
    }

    /* JADX INFO: renamed from: b */
    public void m1290b(String str) {
        this.f1228g = str;
    }

    /* JADX INFO: renamed from: c */
    public String m1291c() {
        return this.f1225d;
    }

    /* JADX INFO: renamed from: c */
    public void m1292c(String str) {
        this.f1229h = str;
    }

    /* JADX INFO: renamed from: d */
    public void m1293d(String str) {
        this.f1230i = str;
    }

    /* JADX INFO: renamed from: d */
    public boolean m1294d() {
        return this.f1226e;
    }

    /* JADX INFO: renamed from: e */
    public int m1295e() {
        return this.f1227f;
    }

    /* JADX INFO: renamed from: e */
    public void m1296e(String str) {
        this.f1231j = str;
    }

    /* JADX INFO: renamed from: f */
    public String m1297f() {
        return this.f1228g;
    }

    /* JADX INFO: renamed from: f */
    public void m1298f(String str) {
        this.f1232k = str;
    }

    /* JADX INFO: renamed from: g */
    public String m1299g() {
        return this.f1229h;
    }

    /* JADX INFO: renamed from: h */
    public String m1300h() {
        return this.f1230i;
    }

    /* JADX INFO: renamed from: i */
    public String m1301i() {
        return this.f1231j;
    }

    /* JADX INFO: renamed from: j */
    public String m1302j() {
        return this.f1232k;
    }

    /* JADX INFO: renamed from: k */
    public boolean m1303k() {
        String str;
        String str2;
        if (this.f1223b == null || this.f1224c < System.currentTimeMillis()) {
            return false;
        }
        if (this.f1223b != a.V2) {
            return true;
        }
        String str3 = this.f1228g;
        return (str3 == null || str3.isEmpty() || (str = this.f1229h) == null || str.isEmpty() || (str2 = this.f1225d) == null || str2.isEmpty()) ? false : true;
    }
}
