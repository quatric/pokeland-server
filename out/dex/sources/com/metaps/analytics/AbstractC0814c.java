package com.metaps.analytics;

import com.metaps.common.C0849c;
import com.metaps.common.C0859m;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.c */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class AbstractC0814c implements Comparable<AbstractC0814c> {

    /* JADX INFO: renamed from: a */
    protected static final String f601a = "event_id";

    /* JADX INFO: renamed from: b */
    protected static final String f602b = "event_time";

    /* JADX INFO: renamed from: c */
    protected static final String f603c = "session_incr_value";

    /* JADX INFO: renamed from: d */
    protected static final String f604d = "time_not_sync_with_server";

    /* JADX INFO: renamed from: f */
    private static final String f605f = "event_seq";

    /* JADX INFO: renamed from: e */
    protected a f606e;

    /* JADX INFO: renamed from: i */
    private int f609i;

    /* JADX INFO: renamed from: h */
    private int f608h = 0;

    /* JADX INFO: renamed from: j */
    private long f610j = System.currentTimeMillis() / 1000;

    /* JADX INFO: renamed from: g */
    private String f607g = UUID.randomUUID().toString();

    /* JADX INFO: renamed from: k */
    private boolean f611k = true;

    /* JADX INFO: renamed from: com.metaps.analytics.c$a */
    protected enum a {
        INSTALL,
        BOOTUP,
        PURCHASE,
        SESSION,
        REFERRER,
        CUSTOM,
        SPEND,
        ATTRIBUTES,
        ACTION,
        CUSTOM_LOG,
        READ_NOTIFICATION,
        HOUSE_AD_IMP,
        HOUSE_AD_CLICK,
        PROMOTION_IMP,
        PROMOTION_CLICK
    }

    protected AbstractC0814c(a aVar) {
        this.f606e = aVar;
    }

    /* JADX INFO: renamed from: a */
    protected int m761a() {
        return this.f608h;
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(AbstractC0814c abstractC0814c) {
        return this.f608h - abstractC0814c.f608h;
    }

    /* JADX INFO: renamed from: a */
    protected void m763a(int i) {
        this.f608h = i;
    }

    /* JADX INFO: renamed from: a */
    protected void mo764a(C0833v c0833v) {
    }

    /* JADX INFO: renamed from: a */
    protected abstract void mo694a(JSONObject jSONObject) throws JSONException;

    /* JADX INFO: renamed from: a */
    protected void m765a(boolean z) {
        this.f611k = z;
    }

    /* JADX INFO: renamed from: a */
    protected boolean mo766a(C0833v c0833v, C0849c.a aVar) {
        return false;
    }

    /* JADX INFO: renamed from: b */
    protected int m767b() {
        return this.f609i;
    }

    /* JADX INFO: renamed from: b */
    protected void m768b(int i) {
        this.f609i = i;
    }

    /* JADX INFO: renamed from: c */
    protected a m769c() {
        return this.f606e;
    }

    /* JADX INFO: renamed from: d */
    protected String mo770d() {
        return this.f607g;
    }

    /* JADX INFO: renamed from: e */
    protected long mo771e() {
        return C0859m.m1035c(this.f610j);
    }

    /* JADX INFO: renamed from: f */
    protected boolean mo772f() {
        return true;
    }

    /* JADX INFO: renamed from: g */
    protected void mo773g() {
    }

    /* JADX INFO: renamed from: h */
    protected boolean mo774h() {
        return false;
    }

    /* JADX INFO: renamed from: i */
    protected JSONObject mo775i() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        mo694a(jSONObject);
        int i = this.f609i;
        if (i > 0) {
            jSONObject.put(f605f, i);
        }
        jSONObject.put(f601a, this.f607g);
        jSONObject.put(f602b, mo771e());
        boolean z = this.f611k;
        if (z) {
            jSONObject.put(f604d, z);
        }
        return jSONObject;
    }

    /* JADX INFO: renamed from: j */
    protected boolean m776j() {
        return this.f611k;
    }
}
