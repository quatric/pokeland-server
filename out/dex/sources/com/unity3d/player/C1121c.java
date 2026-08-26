package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* JADX INFO: renamed from: com.unity3d.player.c */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class C1121c {

    /* JADX INFO: renamed from: b */
    protected InterfaceC1124f f2033b;

    /* JADX INFO: renamed from: e */
    protected String f2036e;

    /* JADX INFO: renamed from: a */
    protected C1133o f2032a = null;

    /* JADX INFO: renamed from: c */
    protected Context f2034c = null;

    /* JADX INFO: renamed from: d */
    protected String f2035d = null;

    C1121c(String str, InterfaceC1124f interfaceC1124f) {
        this.f2033b = null;
        this.f2036e = "";
        this.f2036e = str;
        this.f2033b = interfaceC1124f;
    }

    protected void reportError(String str) {
        InterfaceC1124f interfaceC1124f = this.f2033b;
        if (interfaceC1124f != null) {
            interfaceC1124f.reportError(this.f2036e + " Error [" + this.f2035d + "]", str);
            return;
        }
        C1125g.Log(6, this.f2036e + " Error [" + this.f2035d + "]: " + str);
    }

    protected void runOnUiThread(Runnable runnable) {
        Context context = this.f2034c;
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(runnable);
            return;
        }
        C1125g.Log(5, "Not running " + this.f2036e + " from an Activity; Ignoring execution request...");
    }

    protected boolean runOnUiThreadWithSync(final Runnable runnable) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            runnable.run();
            return true;
        }
        final Semaphore semaphore = new Semaphore(0);
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.c.1
            /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    try {
                        runnable.run();
                    } catch (Exception e) {
                        C1121c.this.reportError("Exception unloading Google VR on UI Thread. " + e.getLocalizedMessage());
                    }
                } finally {
                    semaphore.release();
                }
            }
        });
        try {
            if (semaphore.tryAcquire(4L, TimeUnit.SECONDS)) {
                return true;
            }
            reportError("Timeout waiting for vr state change!");
            return false;
        } catch (InterruptedException e) {
            reportError("Interrupted while trying to acquire sync lock. " + e.getLocalizedMessage());
            return false;
        }
    }
}
