package com.google.android.gms.measurement.internal;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class zzgf implements zzgh {
    protected final zzfj zzj;

    zzgf(zzfj zzfjVar) {
        Preconditions.checkNotNull(zzfjVar);
        this.zzj = zzfjVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzgh
    public Context getContext() {
        return this.zzj.getContext();
    }

    @Override // com.google.android.gms.measurement.internal.zzgh
    public zzfc zzaa() {
        return this.zzj.zzaa();
    }

    @Override // com.google.android.gms.measurement.internal.zzgh
    public zzef zzab() {
        return this.zzj.zzab();
    }

    public zzeo zzac() {
        return this.zzj.zzac();
    }

    public zzs zzad() {
        return this.zzj.zzad();
    }

    @Override // com.google.android.gms.measurement.internal.zzgh
    public zzr zzae() {
        return this.zzj.zzae();
    }

    public void zzl() {
        this.zzj.zzl();
    }

    public void zzm() {
        this.zzj.zzm();
    }

    public void zzn() {
        this.zzj.zzaa().zzn();
    }

    public void zzo() {
        this.zzj.zzaa().zzo();
    }

    public zzac zzw() {
        return this.zzj.zzw();
    }

    @Override // com.google.android.gms.measurement.internal.zzgh
    public Clock zzx() {
        return this.zzj.zzx();
    }

    public zzed zzy() {
        return this.zzj.zzy();
    }

    public zzjs zzz() {
        return this.zzj.zzz();
    }
}
