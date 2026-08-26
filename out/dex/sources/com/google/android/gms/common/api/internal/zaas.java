package com.google.android.gms.common.api.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zaas extends zabf {
    private final /* synthetic */ zaak zagp;
    private final /* synthetic */ com.google.android.gms.signin.internal.zaj zagq;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zaas(zaar zaarVar, zabd zabdVar, zaak zaakVar, com.google.android.gms.signin.internal.zaj zajVar) {
        super(zabdVar);
        this.zagp = zaakVar;
        this.zagq = zajVar;
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final void zaan() {
        this.zagp.zaa(this.zagq);
    }
}
