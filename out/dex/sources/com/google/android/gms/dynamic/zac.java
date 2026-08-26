package com.google.android.gms.dynamic;

import android.os.Bundle;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zac implements DeferredLifecycleHelper.zaa {
    private final /* synthetic */ DeferredLifecycleHelper zari;
    private final /* synthetic */ Bundle zark;

    zac(DeferredLifecycleHelper deferredLifecycleHelper, Bundle bundle) {
        this.zari = deferredLifecycleHelper;
        this.zark = bundle;
    }

    @Override // com.google.android.gms.dynamic.DeferredLifecycleHelper.zaa
    public final int getState() {
        return 1;
    }

    @Override // com.google.android.gms.dynamic.DeferredLifecycleHelper.zaa
    public final void zaa(LifecycleDelegate lifecycleDelegate) {
        this.zari.zare.onCreate(this.zark);
    }
}
