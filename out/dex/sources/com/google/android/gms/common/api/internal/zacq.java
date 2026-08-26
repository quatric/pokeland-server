package com.google.android.gms.common.api.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zacq implements zacs {
    private final /* synthetic */ zacp zala;

    zacq(zacp zacpVar) {
        this.zala = zacpVar;
    }

    @Override // com.google.android.gms.common.api.internal.zacs
    public final void zac(BasePendingResult<?> basePendingResult) {
        this.zala.zaky.remove(basePendingResult);
    }
}
