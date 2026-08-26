package com.nintendo.npf.sdk.internal.impl;

import android.content.SharedPreferences;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.c */
/* JADX INFO: compiled from: AnalyticsCache.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class C1005c {

    /* JADX INFO: renamed from: b */
    private final InterfaceC0875a f1502b = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: a */
    private SharedPreferences f1501a = this.f1502b.mo1047a().getSharedPreferences("AnalyticsEvent", 0);

    C1005c() {
    }

    /* JADX INFO: renamed from: a */
    synchronized Map<String, ?> m1569a() {
        return this.f1501a.getAll();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: a */
    synchronized void m1570a(Set<String> set) {
        SharedPreferences.Editor editorEdit = this.f1501a.edit();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            editorEdit.remove(it.next());
        }
        editorEdit.apply();
    }

    /* JADX INFO: renamed from: a */
    synchronized void m1571a(JSONObject jSONObject) {
        Map<String, ?> all = this.f1501a.getAll();
        if (all == null || all.keySet().size() <= 1000) {
            SharedPreferences.Editor editorEdit = this.f1501a.edit();
            editorEdit.putString(UUID.randomUUID().toString(), jSONObject.toString());
            editorEdit.apply();
        }
    }
}
