package com.google.android.gms.internal.measurement;

import android.database.ContentObserver;
import android.os.Handler;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzcc extends ContentObserver {
    private final /* synthetic */ zzca zzaaq;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcc(zzca zzcaVar, Handler handler) {
        super(null);
        this.zzaaq = zzcaVar;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        this.zzaaq.zzrf();
    }
}
