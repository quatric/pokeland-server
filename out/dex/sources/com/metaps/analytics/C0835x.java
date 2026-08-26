package com.metaps.analytics;

import android.os.Process;
import com.metaps.common.C0847a;
import com.metaps.common.C0853g;
import com.metaps.common.C0858l;
import com.metaps.common.C0859m;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.x */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0835x extends Thread {

    /* JADX INFO: renamed from: a */
    private final C0853g f835a;

    /* JADX INFO: renamed from: b */
    private final C0858l f836b;

    /* JADX INFO: renamed from: c */
    private a f837c;

    /* JADX INFO: renamed from: d */
    private volatile boolean f838d = false;

    /* JADX INFO: renamed from: e */
    private AtomicInteger f839e = new AtomicInteger(0);

    /* JADX INFO: renamed from: com.metaps.analytics.x$a */
    interface a {
        /* JADX INFO: renamed from: a */
        void mo797a();

        /* JADX INFO: renamed from: a */
        void mo798a(C0824m c0824m);
    }

    protected C0835x(C0853g c0853g, C0858l c0858l, a aVar) {
        this.f835a = c0853g;
        this.f836b = c0858l;
        this.f837c = aVar;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: a */
    private synchronized List<C0824m> m883a(boolean z) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(this.f835a.m963f());
            for (int i = 0; i < jSONArray.length(); i++) {
                C0824m c0824mM799b = C0824m.m799b(jSONArray.getJSONObject(i));
                if (c0824mM799b != null) {
                    arrayList.add(c0824mM799b);
                }
            }
            if (z) {
                this.f835a.m964g();
            }
        } catch (JSONException e) {
            C0847a.m905a(C0835x.class.toString(), "Failed to load event retry list for SharedPreferences", e);
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    /* JADX INFO: renamed from: a */
    private synchronized void m884a(C0824m c0824m) {
        List<C0824m> listM883a = m883a(false);
        listM883a.add(c0824m);
        m885a(listM883a);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: a */
    private synchronized void m885a(List<C0824m> list) {
        try {
            JSONArray jSONArray = new JSONArray();
            Iterator<C0824m> it = list.iterator();
            while (it.hasNext()) {
                jSONArray.put(it.next().m801k());
            }
            this.f835a.m955a(jSONArray.toString());
        } catch (JSONException e) {
            C0847a.m905a(C0835x.class.toString(), "Failed to save event retry list in SharedPreferences", e);
        }
    }

    /* JADX INFO: renamed from: c */
    private long m886c() {
        long jM1029d = this.f836b.m1029d(C0858l.f983d);
        BigInteger bigIntegerAdd = BigInteger.valueOf(this.f836b.m1029d(C0858l.f982c)).add(BigInteger.valueOf(this.f836b.m1030e(C0858l.f984e)).multiply(BigInteger.valueOf(((int) Math.pow(2.0d, this.f839e.get())) - 1)));
        if (bigIntegerAdd.longValue() <= jM1029d) {
            jM1029d = bigIntegerAdd.intValue();
        }
        return jM1029d * 1000;
    }

    /* JADX INFO: renamed from: a */
    protected void m887a() {
        this.f838d = true;
        interrupt();
    }

    /* JADX INFO: renamed from: a */
    protected synchronized void m888a(int i) {
        try {
            if (i == 0) {
                this.f839e.set(0);
                C0847a.m903a(getClass().toString(), "reset retry count");
            } else {
                int iIncrementAndGet = this.f839e.incrementAndGet();
                C0847a.m903a(getClass().toString(), "increment retry count : " + String.valueOf(iIncrementAndGet));
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: renamed from: a */
    protected void m889a(AbstractC0814c abstractC0814c, JSONObject jSONObject) {
        long jM1033b = C0859m.m1033b();
        long jM1029d = this.f836b.m1029d(C0858l.f981b);
        if (abstractC0814c.f606e == AbstractC0814c.a.INSTALL || abstractC0814c.m776j() || jM1033b - abstractC0814c.mo771e() <= jM1029d) {
            C0847a.m903a(C0819h.class.toString(), "EVENT ACTION add to retry " + abstractC0814c.m769c() + " " + abstractC0814c.m761a());
            m884a(new C0824m(abstractC0814c.m769c(), jSONObject));
            return;
        }
        C0847a.m903a(C0819h.class.toString(), "EVENT ACTION not add to retry " + abstractC0814c.m769c() + " " + abstractC0814c.m761a() + " because has expired");
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: b */
    protected synchronized void m890b() {
        List<C0824m> listM883a = m883a(true);
        Iterator<C0824m> it = listM883a.iterator();
        while (it.hasNext()) {
            this.f837c.mo798a(it.next());
        }
        m888a(listM883a.size());
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        Process.setThreadPriority(10);
        while (true) {
            long jM1029d = this.f836b.m1029d(C0858l.f983d);
            try {
                long jM886c = m886c();
                if (jM886c > 0) {
                    jM1029d = jM886c;
                }
                C0847a.m903a(getClass().toString(), "sleep for retry. (sleep time : " + String.valueOf(jM1029d) + ")");
                Thread.sleep(jM1029d);
                this.f837c.mo797a();
                m890b();
            } catch (InterruptedException unused) {
                if (this.f838d) {
                    return;
                }
            } catch (Exception unused2) {
                return;
            }
        }
    }
}
