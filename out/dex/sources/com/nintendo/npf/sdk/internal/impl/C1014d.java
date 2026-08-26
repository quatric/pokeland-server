package com.nintendo.npf.sdk.internal.impl;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.NPFSDK;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p021c.C0929a;
import com.nintendo.npf.sdk.internal.p022d.C0946a;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.d */
/* JADX INFO: compiled from: AnalyticsImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1014d {

    /* JADX INFO: renamed from: a */
    private static final String f1549a = "d";

    /* JADX INFO: renamed from: b */
    private Timer f1550b;

    /* JADX INFO: renamed from: j */
    private final InterfaceC0875a f1558j = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: d */
    private C1005c f1552d = new C1005c();

    /* JADX INFO: renamed from: e */
    private C1004b f1553e = new C1004b();

    /* JADX INFO: renamed from: f */
    private C1016f f1554f = new C1016f();

    /* JADX INFO: renamed from: g */
    private C0946a f1555g = new C0946a();

    /* JADX INFO: renamed from: i */
    private final C0929a f1557i = new C0929a();

    /* JADX INFO: renamed from: h */
    private Set<String> f1556h = new HashSet();

    /* JADX INFO: renamed from: c */
    private Object f1551c = new Object();

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.d$3, reason: invalid class name */
    /* JADX INFO: compiled from: AnalyticsImpl.java */
    static /* synthetic */ class AnonymousClass3 {

        /* JADX INFO: renamed from: a */
        static final /* synthetic */ int[] f1561a = new int[C0946a.a.values().length];

        static {
            try {
                f1561a[C0946a.a.V1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1561a[C0946a.a.V2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public C1014d() {
        m1579i();
    }

    /* JADX INFO: renamed from: a */
    private JSONObject m1575a(BaaSUser baaSUser, String str, String str2, JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        JSONObject jSONObjectM1351z = this.f1558j.mo1065s().m1351z();
        if (baaSUser.getNintendoAccount() != null) {
            jSONObjectM1351z.put("nintendoAccountId", baaSUser.getNintendoAccount().getNintendoAccountId());
        }
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("eventTimestamp", timeInMillis);
        jSONObject3.put("eventCategory", str);
        jSONObject3.put("eventId", str2);
        jSONObject3.put("userId", baaSUser.getUserId());
        jSONObject3.put("market", NPFSDK.getMarket());
        jSONObject3.put("deviceAccount", baaSUser.getDeviceAccount());
        jSONObject3.put("playerState", jSONObject);
        jSONObject3.put("payload", jSONObject2);
        jSONObject3.put("cacheInfo", jSONObjectM1351z);
        return jSONObject3;
    }

    /* JADX INFO: renamed from: g */
    private boolean m1577g() {
        boolean z;
        synchronized (this.f1551c) {
            z = this.f1550b != null;
        }
        return z;
    }

    /* JADX INFO: renamed from: h */
    private void m1578h() {
        synchronized (this.f1551c) {
            m1581k();
        }
    }

    /* JADX INFO: renamed from: i */
    private void m1579i() {
        synchronized (this.f1551c) {
            m1582l();
        }
    }

    /* JADX INFO: renamed from: j */
    private void m1580j() {
        synchronized (this.f1551c) {
            if (this.f1550b != null) {
                m1581k();
                m1582l();
            }
        }
    }

    /* JADX INFO: renamed from: k */
    private void m1581k() {
        Timer timer = this.f1550b;
        if (timer != null) {
            timer.cancel();
            this.f1550b.purge();
            this.f1550b = null;
        }
    }

    /* JADX INFO: renamed from: l */
    private void m1582l() {
        if (this.f1550b == null) {
            TimerTask timerTask = new TimerTask() { // from class: com.nintendo.npf.sdk.internal.impl.d.2
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    C1014d.this.m1596e();
                }
            };
            this.f1550b = new Timer(true);
            this.f1550b.schedule(timerTask, this.f1555g.m1295e(), this.f1555g.m1295e());
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1583a() {
        if (m1594c()) {
            return;
        }
        m1578h();
    }

    /* JADX INFO: renamed from: a */
    public void m1584a(C0946a c0946a) {
        this.f1555g = c0946a;
        this.f1554f.m1608a(c0946a);
        m1580j();
    }

    /* JADX INFO: renamed from: a */
    void m1585a(InterfaceC1015e interfaceC1015e, Map<String, JSONObject> map, BaaSUser baaSUser) {
        if (map.size() <= 0 || !interfaceC1015e.mo1568a(map, baaSUser)) {
            return;
        }
        synchronized (this.f1556h) {
            this.f1556h.addAll(map.keySet());
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1586a(String str, String str2, JSONObject jSONObject, JSONObject jSONObject2) {
        C0955e.m1393b(f1549a, "Start reportEvent");
        if (str == null || str.isEmpty() || str2 == null || str2.isEmpty()) {
            throw new IllegalArgumentException("Please set eventCategory or eventId parameter");
        }
        BaaSUser baaSUserM1665a = this.f1558j.mo1048b().m1665a();
        if (!this.f1558j.mo1050d().m1633b(baaSUserM1665a)) {
            C0955e.m1395c(f1549a, "User is null");
            return;
        }
        try {
            this.f1552d.m1571a(m1575a(baaSUserM1665a, str, str2, jSONObject, jSONObject2));
            if (this.f1555g.m1294d()) {
                m1596e();
            }
        } catch (JSONException e) {
            C0955e.m1394b(f1549a, "reportEvent error ", e);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: a */
    void m1587a(Map<String, JSONObject> map) {
        Iterator<Map.Entry<String, JSONObject>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            try {
                JSONObject value = it.next().getValue();
                String string = value.getString("eventCategory");
                String string2 = value.getString("eventId");
                C0955e.m1391a(f1549a, "Valid event : " + string + " : " + string2);
            } catch (JSONException e) {
                C0955e.m1392a(f1549a, "processCompletedEvents Error", e);
            }
        }
        this.f1552d.m1570a(map.keySet());
        synchronized (this.f1556h) {
            this.f1556h.removeAll(map.keySet());
        }
    }

    /* JADX INFO: renamed from: a */
    void m1588a(Map<String, ?> map, BaaSUser baaSUser) {
        HashMap map2 = new HashMap();
        HashSet hashSet = new HashSet();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            synchronized (this.f1556h) {
                if (!this.f1556h.contains(entry.getKey())) {
                    try {
                        JSONObject jSONObject = new JSONObject((String) entry.getValue());
                        if (baaSUser.getUserId().equals(jSONObject.getString("userId"))) {
                            map2.put(entry.getKey(), jSONObject);
                            if (map2.size() >= 10) {
                                break;
                            }
                        } else {
                            hashSet.add(entry.getKey());
                        }
                    } catch (JSONException unused) {
                        hashSet.add(entry.getKey());
                    }
                }
            }
        }
        if (hashSet.size() > 0) {
            C0955e.m1395c(f1549a, "drainAnalytics remove " + hashSet.size() + " invalid events");
            this.f1552d.m1570a(hashSet);
        }
        m1585a(this.f1553e, map2, baaSUser);
    }

    /* JADX INFO: renamed from: a */
    void m1589a(Set<String> set) {
        C0955e.m1395c(f1549a, "drainAnalytics remove " + set.size() + " invalid events");
        this.f1552d.m1570a(set);
    }

    /* JADX INFO: renamed from: b */
    public void m1590b() {
        if (m1594c()) {
            m1579i();
            if (this.f1555g.m1294d()) {
                m1596e();
            }
        }
    }

    /* JADX INFO: renamed from: b */
    void m1591b(Map<String, JSONObject> map) {
        synchronized (this.f1556h) {
            this.f1556h.removeAll(map.keySet());
        }
    }

    /* JADX INFO: renamed from: b */
    void m1592b(Map<String, ?> map, BaaSUser baaSUser) {
        HashMap map2 = new HashMap();
        HashMap map3 = new HashMap();
        HashSet hashSet = new HashSet();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            synchronized (this.f1556h) {
                if (!this.f1556h.contains(entry.getKey())) {
                    try {
                        JSONObject jSONObject = new JSONObject((String) entry.getValue());
                        String string = jSONObject.getString("eventCategory");
                        if (baaSUser.getUserId().equals(jSONObject.getString("userId"))) {
                            if ("NPFCOMMON".equals(string) || "NPFAUDIT".equals(string)) {
                                if (map2.size() < 10) {
                                    map2.put(entry.getKey(), jSONObject);
                                }
                            } else if (map3.size() < 10) {
                                map3.put(entry.getKey(), jSONObject);
                            }
                            if (map2.size() >= 10 && map3.size() >= 10) {
                                break;
                            }
                        } else {
                            hashSet.add(entry.getKey());
                        }
                    } catch (JSONException unused) {
                        hashSet.add(entry.getKey());
                    }
                }
            }
        }
        if (hashSet.size() > 0) {
            m1589a(hashSet);
        }
        m1585a(this.f1553e, map2, baaSUser);
        m1585a(this.f1554f, map3, baaSUser);
    }

    /* JADX INFO: renamed from: c */
    void m1593c(Map<String, JSONObject> map) {
        m1589a(map.keySet());
        synchronized (this.f1556h) {
            this.f1556h.removeAll(map.keySet());
        }
    }

    /* JADX INFO: renamed from: c */
    public boolean m1594c() {
        return !m1577g();
    }

    /* JADX INFO: renamed from: d */
    public void m1595d() {
        m1584a(new C0946a());
        this.f1555g.m1285a(Calendar.getInstance().getTimeInMillis() + 60000);
        BaaSUser baaSUserM1665a = this.f1558j.mo1048b().m1665a();
        if (this.f1558j.mo1050d().m1633b(baaSUserM1665a)) {
            C0905c.m1181b().mo1188a(baaSUserM1665a, new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.d.1
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
                /* JADX INFO: renamed from: a */
                public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
                    if (nPFError != null) {
                        return;
                    }
                    try {
                        C0946a c0946aMo1260b = C1014d.this.f1557i.mo1260b(jSONObject);
                        if (c0946aMo1260b == null || !c0946aMo1260b.m1303k()) {
                            return;
                        }
                        C1014d.this.m1584a(c0946aMo1260b);
                        C1014d.this.m1596e();
                    } catch (JSONException e) {
                        C0955e.m1394b(C1014d.f1549a, "refreshConfig", e);
                    }
                }
            });
        } else {
            C0955e.m1395c(f1549a, "User is not logged in");
        }
    }

    /* JADX INFO: renamed from: e */
    synchronized void m1596e() {
        if (m1594c()) {
            return;
        }
        C0955e.m1393b(f1549a, "Start drainAnalyticsEvents");
        BaaSUser baaSUserM1665a = this.f1558j.mo1048b().m1665a();
        if (this.f1558j.mo1050d().m1633b(baaSUserM1665a)) {
            Map<String, ?> mapM1569a = this.f1552d.m1569a();
            if (mapM1569a != null && mapM1569a.size() != 0) {
                if (this.f1555g != null && this.f1555g.m1289b() >= System.currentTimeMillis()) {
                    int i = AnonymousClass3.f1561a[this.f1555g.m1283a().ordinal()];
                    if (i == 1) {
                        m1588a(mapM1569a, baaSUserM1665a);
                    } else if (i == 2) {
                        m1592b(mapM1569a, baaSUserM1665a);
                    }
                    return;
                }
                m1595d();
            }
        }
    }
}
