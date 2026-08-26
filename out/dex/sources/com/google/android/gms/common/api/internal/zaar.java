package com.google.android.gms.common.api.internal;

import android.support.annotation.BinderThread;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zaar extends com.google.android.gms.signin.internal.zac {
    private final WeakReference<zaak> zagj;

    zaar(zaak zaakVar) {
        this.zagj = new WeakReference<>(zaakVar);
    }

    @Override // com.google.android.gms.signin.internal.zac, com.google.android.gms.signin.internal.zad
    @BinderThread
    public final void zab(com.google.android.gms.signin.internal.zaj zajVar) {
        zaak zaakVar = this.zagj.get();
        if (zaakVar == null) {
            return;
        }
        zaakVar.zafs.zaa(new zaas(this, zaakVar, zaakVar, zajVar));
    }
}
