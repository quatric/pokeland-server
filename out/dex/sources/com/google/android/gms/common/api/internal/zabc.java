package com.google.android.gms.common.api.internal;

import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zabc extends zabr {
    private WeakReference<zaaw> zahl;

    zabc(zaaw zaawVar) {
        this.zahl = new WeakReference<>(zaawVar);
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final void zas() {
        zaaw zaawVar = this.zahl.get();
        if (zaawVar == null) {
            return;
        }
        zaawVar.resume();
    }
}
