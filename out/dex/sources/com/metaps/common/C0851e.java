package com.metaps.common;

import com.metaps.analytics.C0819h;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: renamed from: com.metaps.common.e */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0851e extends Thread {

    /* JADX INFO: renamed from: b */
    private volatile boolean f880b;

    /* JADX INFO: renamed from: c */
    private AtomicLong f881c = new AtomicLong(0);

    /* JADX INFO: renamed from: a */
    private final PriorityBlockingQueue<AbstractC0850d> f879a = new PriorityBlockingQueue<>();

    /* JADX INFO: renamed from: a */
    public boolean m929a(AbstractC0850d abstractC0850d) {
        abstractC0850d.m927a(this.f881c.incrementAndGet());
        return this.f879a.add(abstractC0850d);
    }

    @Override // java.lang.Thread
    public void interrupt() {
        this.f880b = true;
        super.interrupt();
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        while (true) {
            try {
                this.f879a.take().m928b();
            } catch (InterruptedException unused) {
                C0847a.m903a(C0819h.class.toString(), "EventDispatcher has been interrupted");
                if (this.f880b) {
                    C0847a.m903a(C0819h.class.toString(), "EventDispatcher is requested to quit");
                    return;
                }
            }
        }
    }
}
