package com.nintendo.npf.sdk.internal.impl;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.b */
/* JADX INFO: compiled from: AnalyticsAppEnginePublisher.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1004b implements InterfaceC1015e {

    /* JADX INFO: renamed from: a */
    private static final String f1495a = "b";

    /* JADX INFO: renamed from: b */
    private boolean f1496b;

    /* JADX INFO: renamed from: c */
    private final InterfaceC0875a f1497c = InterfaceC0875a.a.m1072b();

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1565a(Map<String, JSONObject> map) {
        this.f1497c.mo1056j().m1587a(map);
        this.f1496b = false;
        new Timer().schedule(new TimerTask() { // from class: com.nintendo.npf.sdk.internal.impl.b.2
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                C1004b.this.f1497c.mo1056j().m1596e();
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public void m1567b(Map<String, JSONObject> map) {
        this.f1497c.mo1056j().m1591b(map);
        this.f1496b = false;
        C0955e.m1395c(f1495a, "drainAnalyticsEvents Error");
    }

    @Override // com.nintendo.npf.sdk.internal.impl.InterfaceC1015e
    /* JADX INFO: renamed from: a */
    public boolean mo1568a(final Map<String, JSONObject> map, BaaSUser baaSUser) {
        if (this.f1496b) {
            return false;
        }
        if (!this.f1497c.mo1050d().m1633b(baaSUser)) {
            C0955e.m1395c(f1495a, "User is not logged in");
            return false;
        }
        this.f1496b = true;
        JSONArray jSONArray = new JSONArray();
        Iterator<JSONObject> it = map.values().iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next());
        }
        C0905c.m1181b().mo1189a(baaSUser, jSONArray, new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.b.1
            @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
            /* JADX INFO: renamed from: a */
            public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
                if (nPFError == null) {
                    C1004b.this.m1565a((Map<String, JSONObject>) map);
                } else {
                    C1004b.this.m1567b(map);
                }
            }
        });
        return true;
    }
}
