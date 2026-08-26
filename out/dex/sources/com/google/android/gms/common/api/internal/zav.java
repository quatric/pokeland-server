package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zav implements zabt {
    private final /* synthetic */ zas zaep;

    private zav(zas zasVar) {
        this.zaep = zasVar;
    }

    /* synthetic */ zav(zas zasVar, zat zatVar) {
        this(zasVar);
    }

    @Override // com.google.android.gms.common.api.internal.zabt
    public final void zab(int i, boolean z) {
        this.zaep.zaen.lock();
        try {
            if (this.zaep.zaem) {
                this.zaep.zaem = false;
                this.zaep.zaa(i, z);
            } else {
                this.zaep.zaem = true;
                this.zaep.zaee.onConnectionSuspended(i);
            }
        } finally {
            this.zaep.zaen.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabt
    public final void zab(@Nullable Bundle bundle) {
        this.zaep.zaen.lock();
        try {
            this.zaep.zael = ConnectionResult.RESULT_SUCCESS;
            this.zaep.zax();
        } finally {
            this.zaep.zaen.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabt
    public final void zac(@NonNull ConnectionResult connectionResult) {
        this.zaep.zaen.lock();
        try {
            this.zaep.zael = connectionResult;
            this.zaep.zax();
        } finally {
            this.zaep.zaen.unlock();
        }
    }
}
