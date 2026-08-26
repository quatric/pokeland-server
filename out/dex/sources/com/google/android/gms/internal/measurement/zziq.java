package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zziq;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class zziq<M extends zziq<M>> extends zziw {
    protected zzis zzaoo;

    @Override // com.google.android.gms.internal.measurement.zziw
    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        zziq zziqVar = (zziq) super.clone();
        zziu.zza(this, zziqVar);
        return zziqVar;
    }

    @Override // com.google.android.gms.internal.measurement.zziw
    public void zza(zzio zzioVar) throws IOException {
        if (this.zzaoo == null) {
            return;
        }
        for (int i = 0; i < this.zzaoo.size(); i++) {
            this.zzaoo.zzcm(i).zza(zzioVar);
        }
    }

    protected final boolean zza(zzil zzilVar, int i) throws IOException {
        int position = zzilVar.getPosition();
        if (!zzilVar.zzau(i)) {
            return false;
        }
        int i2 = i >>> 3;
        zziy zziyVar = new zziy(i, zzilVar.zzt(position, zzilVar.getPosition() - position));
        zzir zzirVarZzcl = null;
        zzis zzisVar = this.zzaoo;
        if (zzisVar == null) {
            this.zzaoo = new zzis();
        } else {
            zzirVarZzcl = zzisVar.zzcl(i2);
        }
        if (zzirVarZzcl == null) {
            zzirVarZzcl = new zzir();
            this.zzaoo.zza(i2, zzirVarZzcl);
        }
        zzirVarZzcl.zza(zziyVar);
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zziw
    protected int zzqy() {
        if (this.zzaoo == null) {
            return 0;
        }
        int iZzqy = 0;
        for (int i = 0; i < this.zzaoo.size(); i++) {
            iZzqy += this.zzaoo.zzcm(i).zzqy();
        }
        return iZzqy;
    }

    @Override // com.google.android.gms.internal.measurement.zziw
    /* JADX INFO: renamed from: zzxb */
    public final /* synthetic */ zziw clone() throws CloneNotSupportedException {
        return (zziq) clone();
    }
}
