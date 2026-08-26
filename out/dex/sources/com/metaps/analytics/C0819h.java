package com.metaps.analytics;

import android.content.Context;
import android.os.Process;
import com.google.api.client.http.HttpStatusCodes;
import com.metaps.common.C0847a;
import com.metaps.common.C0848b;
import com.metaps.common.C0849c;
import com.metaps.common.C0853g;
import com.metaps.common.C0854h;
import com.metaps.common.C0858l;
import com.metaps.common.C0859m;
import com.metaps.common.Metaps;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.h */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0819h extends Thread {

    /* JADX INFO: renamed from: a */
    protected static final String f653a = "event_seq";

    /* JADX INFO: renamed from: b */
    protected static final Map<AbstractC0814c.a, String> f654b = new HashMap<AbstractC0814c.a, String>() { // from class: com.metaps.analytics.h.1

        /* JADX INFO: renamed from: a */
        private static final long f683a = 1;

        {
            put(AbstractC0814c.a.INSTALL, "install");
            put(AbstractC0814c.a.REFERRER, "referrer");
            put(AbstractC0814c.a.BOOTUP, "bootup");
            put(AbstractC0814c.a.PURCHASE, "purchase");
            put(AbstractC0814c.a.SESSION, "session");
            put(AbstractC0814c.a.CUSTOM, "custom");
            put(AbstractC0814c.a.SPEND, "spend");
            put(AbstractC0814c.a.ATTRIBUTES, "attributes");
            put(AbstractC0814c.a.ACTION, "action");
            put(AbstractC0814c.a.CUSTOM_LOG, "custom_log");
            put(AbstractC0814c.a.READ_NOTIFICATION, "read_notification");
            put(AbstractC0814c.a.HOUSE_AD_IMP, "house_ad_imp");
            put(AbstractC0814c.a.HOUSE_AD_CLICK, "house_ad_click");
            put(AbstractC0814c.a.PROMOTION_IMP, "promotion_imp");
            put(AbstractC0814c.a.PROMOTION_CLICK, "promotion_click");
        }
    };

    /* JADX INFO: renamed from: c */
    private static final String f655c = "info";

    /* JADX INFO: renamed from: d */
    private static final String f656d = "app";

    /* JADX INFO: renamed from: e */
    private static final String f657e = "user";

    /* JADX INFO: renamed from: f */
    private static final String f658f = "location";

    /* JADX INFO: renamed from: g */
    private static final String f659g = "event_prev";

    /* JADX INFO: renamed from: h */
    private static final String f660h = "type";

    /* JADX INFO: renamed from: i */
    private static final String f661i = "name";

    /* JADX INFO: renamed from: j */
    private static final String f662j = "prev_event_id";

    /* JADX INFO: renamed from: k */
    private static final String f663k = "prev_event_time";

    /* JADX INFO: renamed from: l */
    private static final String f664l = "meta";

    /* JADX INFO: renamed from: m */
    private static final String f665m = "fq7_change";

    /* JADX INFO: renamed from: n */
    private static final String f666n = "fq30_change";

    /* JADX INFO: renamed from: o */
    private static final String f667o = "req_time";

    /* JADX INFO: renamed from: p */
    private static final String f668p = "gdpr_user";

    /* JADX INFO: renamed from: q */
    private static final String f669q = "{app_key}";

    /* JADX INFO: renamed from: A */
    private final C0831t f670A;

    /* JADX INFO: renamed from: B */
    private final C0830s f671B;

    /* JADX INFO: renamed from: v */
    private C0853g f678v;

    /* JADX INFO: renamed from: w */
    private C0858l f679w;

    /* JADX INFO: renamed from: x */
    private C0835x f680x;

    /* JADX INFO: renamed from: z */
    private String f682z;

    /* JADX INFO: renamed from: y */
    private String f681y = f656d;

    /* JADX INFO: renamed from: C */
    private volatile boolean f672C = false;

    /* JADX INFO: renamed from: D */
    private Object f673D = new Object();

    /* JADX INFO: renamed from: r */
    private final AtomicInteger f674r = new AtomicInteger();

    /* JADX INFO: renamed from: s */
    private final AtomicInteger f675s = new AtomicInteger();

    /* JADX INFO: renamed from: t */
    private final BlockingQueue<AbstractC0814c> f676t = new PriorityBlockingQueue();

    /* JADX INFO: renamed from: u */
    private final C0849c f677u = new C0849c();

    protected C0819h(Context context) {
        this.f670A = new C0831t(context);
        this.f671B = new C0830s(context);
        m782a(context);
    }

    /* JADX INFO: renamed from: a */
    private void m781a(int i) {
        this.f674r.set(i);
    }

    /* JADX INFO: renamed from: a */
    private void m782a(final Context context) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread() { // from class: com.metaps.analytics.h.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                synchronized (C0819h.this.f673D) {
                    countDownLatch.countDown();
                    C0819h.this.f678v = C0853g.m936a(context);
                    C0819h.this.f679w = C0858l.m1018a(0).m1023a(C0819h.this.f677u, Metaps.getApplicationId());
                    C0819h.this.start();
                }
            }
        }.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            C0847a.m905a(C0819h.class.toString(), "separateInit process failed", e);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m783a(JSONObject jSONObject) throws JSONException {
        String str = this.f682z;
        if (str != null && str.length() > 0) {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", this.f681y);
            jSONObject2.put("name", this.f682z);
            jSONObject.put(f659g, jSONObject2);
        }
        Object objM961d = this.f678v.m961d();
        if (objM961d != null) {
            jSONObject.put(f662j, objM961d);
        }
        long jM962e = this.f678v.m962e();
        if (jM962e > 0) {
            jSONObject.put(f663k, jM962e);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m784a(JSONObject jSONObject, long j) {
        try {
            jSONObject.getJSONObject(f657e).put("installed", j);
        } catch (JSONException e) {
            C0847a.m905a(C0819h.class.toString(), "Failed to update installed time", e);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m785a(JSONObject jSONObject, C0833v c0833v) throws JSONException {
        if ((c0833v.m844i() == null || c0833v.m844i().length() <= 0) && (c0833v.m845j() == null || c0833v.m845j().length() <= 0)) {
            return;
        }
        JSONObject jSONObject2 = new JSONObject();
        if (c0833v.m844i() != null && c0833v.m844i().length() > 0) {
            jSONObject2.put(f665m, c0833v.m844i());
        }
        if (c0833v.m845j() != null && c0833v.m845j().length() > 0) {
            jSONObject2.put(f666n, c0833v.m845j());
        }
        jSONObject.put(f664l, jSONObject2);
    }

    /* JADX INFO: renamed from: b */
    private int m786b() {
        return this.f674r.getAndIncrement() + 1;
    }

    /* JADX INFO: renamed from: b */
    private void m788b(AbstractC0814c abstractC0814c) {
        int iM786b;
        boolean zM1026a = this.f679w.m1026a();
        try {
            if (abstractC0814c.m776j() && zM1026a) {
                abstractC0814c.m765a(false);
                if (!abstractC0814c.mo772f()) {
                    abstractC0814c.mo773g();
                }
            }
            JSONObject jSONObjectMo775i = abstractC0814c.mo775i();
            long jM1033b = C0859m.m1033b();
            if (jM1033b < abstractC0814c.mo771e()) {
                jM1033b = abstractC0814c.mo771e();
            }
            if (zM1026a && this.f678v.m950a() > jM1033b) {
                this.f678v.m952a(jM1033b);
            }
            jSONObjectMo775i.put(f667o, jM1033b);
            boolean z = true;
            C0833v c0833vM951a = this.f678v.m951a(abstractC0814c.mo772f() && !abstractC0814c.mo774h());
            C0832u c0832uM958b = this.f678v.m958b();
            if (abstractC0814c.mo772f()) {
                jSONObjectMo775i.put(f655c, this.f670A.m813a());
                jSONObjectMo775i.put(f656d, this.f671B.m812a());
                abstractC0814c.mo764a(c0833vM951a);
                jSONObjectMo775i.put(f657e, c0833vM951a.m831b(false));
                jSONObjectMo775i.put("location", c0832uM958b.m815a());
                if (abstractC0814c.mo774h()) {
                    iM786b = 0;
                } else {
                    m783a(jSONObjectMo775i);
                    m785a(jSONObjectMo775i, c0833vM951a);
                    iM786b = m786b();
                }
                if (!abstractC0814c.m769c().equals(AbstractC0814c.a.ATTRIBUTES)) {
                    abstractC0814c.m768b(iM786b);
                    jSONObjectMo775i.put(f653a, iM786b);
                }
                jSONObjectMo775i.put(f668p, this.f678v.m967j());
            } else {
                m784a(jSONObjectMo775i, this.f678v.m950a());
            }
            if (zM1026a) {
                try {
                    C0849c.a aVarM919a = this.f677u.m919a(this.f679w.m1025a(f654b.get(abstractC0814c.m769c())).replace(f669q, Metaps.getApplicationId()), jSONObjectMo775i.toString(), C0849c.f862b);
                    if (aVarM919a.f871f != 200 || aVarM919a.f874i == null || !aVarM919a.f874i.equals(String.valueOf(HttpStatusCodes.STATUS_CODE_OK))) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Error with event tracking ");
                        sb.append(aVarM919a.f871f);
                        sb.append(" retry? ");
                        sb.append(!aVarM919a.f877l);
                        C0847a.m911c(sb.toString());
                        C0847a.m909b(C0819h.class.toString(), "Server error when tracking event : " + aVarM919a.f871f + " - " + aVarM919a.f873h);
                        if (!aVarM919a.f877l) {
                            this.f680x.m889a(abstractC0814c, jSONObjectMo775i);
                        }
                    } else if (abstractC0814c.mo766a(c0833vM951a, aVarM919a)) {
                        m781a(1);
                    }
                    z = false;
                } catch (C0848b e) {
                    C0847a.m911c("Error with event tracking");
                    C0847a.m905a(C0819h.class.toString(), "Failed to call API", e);
                }
            } else {
                z = false;
            }
            if (z || !zM1026a) {
                this.f680x.m889a(abstractC0814c, jSONObjectMo775i);
            }
            if (abstractC0814c.mo772f() && abstractC0814c.mo774h()) {
                c0833vM951a.m825a(0);
                c0833vM951a.m832b(0);
            }
            this.f678v.m954a(c0833vM951a);
            this.f678v.m953a(c0832uM958b);
            if (!abstractC0814c.mo772f() || abstractC0814c.mo774h()) {
                return;
            }
            this.f682z = f654b.get(abstractC0814c.m769c());
            this.f678v.m956a(abstractC0814c.mo770d(), abstractC0814c.mo771e());
        } catch (JSONException e2) {
            C0847a.m911c("Error with event data");
            C0847a.m905a(C0819h.class.toString(), "Failed to build json for the event, not possible to retry", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: c */
    public int m789c() {
        return this.f675s.getAndIncrement() + 1;
    }

    /* JADX INFO: renamed from: a */
    protected void m793a() {
        this.f672C = true;
        interrupt();
    }

    /* JADX INFO: renamed from: a */
    protected void m794a(final AbstractC0814c abstractC0814c) {
        if (C0854h.m977d()) {
            C0847a.m911c("Unavailable to use Analytics SDK. In's in the zombie mode.");
            return;
        }
        C0847a.m903a(C0819h.class.toString(), "EVENT ACTION add " + abstractC0814c.m769c() + " " + hashCode());
        new Thread() { // from class: com.metaps.analytics.h.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                synchronized (C0819h.this.f673D) {
                    if (abstractC0814c.mo772f()) {
                        C0819h.this.m796a(false);
                        abstractC0814c.m763a(C0819h.this.m789c());
                        if (C0819h.this.f678v.m965h()) {
                            C0819h.this.f678v.m959b(false);
                            C0819h.this.f676t.add(new C0816e(C0819h.this.f678v.m966i()));
                        }
                    }
                    C0819h.this.f676t.add(abstractC0814c);
                }
            }
        }.start();
    }

    /* JADX INFO: renamed from: a */
    protected void m795a(final String str, final String str2) {
        new Thread() { // from class: com.metaps.analytics.h.5
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                synchronized (C0819h.this.f673D) {
                    C0819h.this.f678v.m957a(str, str2);
                }
            }
        }.start();
    }

    /* JADX INFO: renamed from: a */
    protected void m796a(boolean z) {
        C0858l c0858l = this.f679w;
        if (c0858l != null) {
            c0858l.m1024a(this.f677u, Metaps.getApplicationId(), z);
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        Process.setThreadPriority(10);
        if (this.f679w.m1027b(Metaps.getApplicationId())) {
            return;
        }
        if (C0854h.m977d()) {
            C0847a.m911c("Unavailable to use Analytics SDK. In's in the zombie mode.");
            return;
        }
        this.f680x = new C0835x(this.f678v, this.f679w, new C0835x.a() { // from class: com.metaps.analytics.h.4
            @Override // com.metaps.analytics.C0835x.a
            /* JADX INFO: renamed from: a */
            public void mo797a() {
                C0819h.this.m796a(false);
            }

            @Override // com.metaps.analytics.C0835x.a
            /* JADX INFO: renamed from: a */
            public void mo798a(C0824m c0824m) {
                C0819h.this.m794a(c0824m);
            }
        });
        this.f680x.start();
        while (true) {
            try {
                AbstractC0814c abstractC0814cTake = this.f676t.take();
                C0847a.m903a(C0819h.class.toString(), "EVENT ACTION take " + abstractC0814cTake.m769c() + " " + abstractC0814cTake.m761a() + " " + hashCode());
                m788b(abstractC0814cTake);
            } catch (InterruptedException unused) {
                C0847a.m903a(C0819h.class.toString(), "EventDispatcher has been interrupted");
                if (this.f672C) {
                    this.f680x.m887a();
                    C0847a.m903a(C0819h.class.toString(), "EventDispatcher is requested to quit");
                    return;
                }
            }
        }
    }
}
