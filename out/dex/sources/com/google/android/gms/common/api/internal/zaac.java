package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zaac implements PendingResult.StatusListener {
    private final /* synthetic */ BasePendingResult zafl;
    private final /* synthetic */ zaab zafm;

    zaac(zaab zaabVar, BasePendingResult basePendingResult) {
        this.zafm = zaabVar;
        this.zafl = basePendingResult;
    }

    @Override // com.google.android.gms.common.api.PendingResult.StatusListener
    public final void onComplete(Status status) {
        this.zafm.zafj.remove(this.zafl);
    }
}
