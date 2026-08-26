package com.google.android.gms.common.api.internal;

import android.support.annotation.WorkerThread;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class zaau implements Runnable {
    private final /* synthetic */ zaak zagi;

    private zaau(zaak zaakVar) {
        this.zagi = zaakVar;
    }

    /* synthetic */ zaau(zaak zaakVar, zaal zaalVar) {
        this(zaakVar);
    }

    @Override // java.lang.Runnable
    @WorkerThread
    public void run() {
        this.zagi.zaen.lock();
        try {
            if (Thread.interrupted()) {
                return;
            }
            zaan();
            return;
        } catch (RuntimeException e) {
            this.zagi.zafs.zab(e);
            return;
        } finally {
            this.zagi.zaen.unlock();
        }
        this.zagi.zaen.unlock();
    }

    @WorkerThread
    protected abstract void zaan();
}
