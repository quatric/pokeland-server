package com.google.android.gms.common.api.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zabn implements Runnable {
    private final /* synthetic */ zabm zaiz;

    zabn(zabm zabmVar) {
        this.zaiz = zabmVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zaiz.zaix.zain.disconnect();
    }
}
