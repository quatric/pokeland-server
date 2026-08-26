package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzgo<T> implements zzgx<T> {
    private final zzgi zzakn;
    private final boolean zzako;
    private final zzhp<?, ?> zzakx;
    private final zzen<?> zzaky;

    private zzgo(zzhp<?, ?> zzhpVar, zzen<?> zzenVar, zzgi zzgiVar) {
        this.zzakx = zzhpVar;
        this.zzako = zzenVar.zze(zzgiVar);
        this.zzaky = zzenVar;
        this.zzakn = zzgiVar;
    }

    static <T> zzgo<T> zza(zzhp<?, ?> zzhpVar, zzen<?> zzenVar, zzgi zzgiVar) {
        return new zzgo<>(zzhpVar, zzenVar, zzgiVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzgx
    public final boolean equals(T t, T t2) {
        if (!this.zzakx.zzx(t).equals(this.zzakx.zzx(t2))) {
            return false;
        }
        if (this.zzako) {
            return this.zzaky.zzh(t).equals(this.zzaky.zzh(t2));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzgx
    public final int hashCode(T t) {
        int iHashCode = this.zzakx.zzx(t).hashCode();
        return this.zzako ? (iHashCode * 53) + this.zzaky.zzh(t).hashCode() : iHashCode;
    }

    @Override // com.google.android.gms.internal.measurement.zzgx
    public final T newInstance() {
        return (T) this.zzakn.zzup().zzuf();
    }

    /* JADX WARN: Code duplicated, block: B:50:0x0085 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:54:? A[LOOP:0: B:46:0x000c->B:54:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.internal.measurement.zzgx
    public final void zza(T t, zzgy zzgyVar, zzel zzelVar) throws IOException {
        boolean zZzsz;
        zzhp<?, ?> zzhpVar = this.zzakx;
        zzen<?> zzenVar = this.zzaky;
        Object objZzy = zzhpVar.zzy(t);
        zzeo<T> zzeoVarZzi = zzenVar.zzi(t);
        while (zzgyVar.zzsy() != Integer.MAX_VALUE) {
            try {
                int tag = zzgyVar.getTag();
                if (tag != 11) {
                    if ((tag & 7) == 2) {
                        Object objZza = zzenVar.zza(zzelVar, this.zzakn, tag >>> 3);
                        if (objZza != null) {
                            zzenVar.zza(zzgyVar, objZza, zzelVar, zzeoVarZzi);
                        } else {
                            zZzsz = zzhpVar.zza(objZzy, zzgyVar);
                        }
                    } else {
                        zZzsz = zzgyVar.zzsz();
                    }
                    if (!zZzsz) {
                        zzhpVar.zzf(t, objZzy);
                        return;
                    }
                } else {
                    int iZzsp = 0;
                    Object objZza2 = null;
                    zzdp zzdpVarZzso = null;
                    while (zzgyVar.zzsy() != Integer.MAX_VALUE) {
                        int tag2 = zzgyVar.getTag();
                        if (tag2 == 16) {
                            iZzsp = zzgyVar.zzsp();
                            objZza2 = zzenVar.zza(zzelVar, this.zzakn, iZzsp);
                        } else if (tag2 == 26) {
                            if (objZza2 != null) {
                                zzenVar.zza(zzgyVar, objZza2, zzelVar, zzeoVarZzi);
                            } else {
                                zzdpVarZzso = zzgyVar.zzso();
                            }
                        } else if (!zzgyVar.zzsz()) {
                            break;
                        }
                    }
                    if (zzgyVar.getTag() != 12) {
                        throw zzfi.zzux();
                    }
                    if (zzdpVarZzso != null) {
                        if (objZza2 != null) {
                            zzenVar.zza(zzdpVarZzso, objZza2, zzelVar, zzeoVarZzi);
                        } else {
                            zzhpVar.zza(objZzy, iZzsp, zzdpVarZzso);
                        }
                    }
                }
                zZzsz = true;
                if (!zZzsz) {
                    zzhpVar.zzf(t, objZzy);
                    return;
                }
            } catch (Throwable th) {
                zzhpVar.zzf(t, objZzy);
                throw th;
            }
        }
        zzhpVar.zzf(t, objZzy);
    }

    @Override // com.google.android.gms.internal.measurement.zzgx
    public final void zza(T t, zzim zzimVar) throws IOException {
        for (T t2 : this.zzaky.zzh(t)) {
            zzeq zzeqVar = (zzeq) t2.getKey();
            if (zzeqVar.zztx() != zzij.MESSAGE || zzeqVar.zzty() || zzeqVar.zztz()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            if (t2 instanceof zzfl) {
                zzimVar.zza(zzeqVar.zzlg(), (Object) ((zzfl) t2).zzve().zzrs());
            } else {
                zzimVar.zza(zzeqVar.zzlg(), t2.getValue());
            }
        }
        zzhp<?, ?> zzhpVar = this.zzakx;
        zzhpVar.zzc(zzhpVar.zzx(t), zzimVar);
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0094  */
    /* JADX WARN: Code duplicated, block: B:57:0x0099 A[EDGE_INSN: B:57:0x0099->B:34:0x0099 BREAK  A[LOOP:1: B:18:0x0053->B:62:0x0053], SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.internal.measurement.zzgx
    public final void zza(T t, byte[] bArr, int i, int i2, zzdk zzdkVar) throws IOException {
        zzey zzeyVar = (zzey) t;
        zzhs zzhsVarZzwr = zzeyVar.zzahz;
        if (zzhsVarZzwr == zzhs.zzwq()) {
            zzhsVarZzwr = zzhs.zzwr();
            zzeyVar.zzahz = zzhsVarZzwr;
        }
        ((zzey.zzb) t).zzuq();
        zzey.zze zzeVar = null;
        while (i < i2) {
            int iZza = zzdl.zza(bArr, i, zzdkVar);
            int i3 = zzdkVar.zzada;
            if (i3 == 11) {
                int i4 = 0;
                zzdp zzdpVar = null;
                while (iZza < i2) {
                    iZza = zzdl.zza(bArr, iZza, zzdkVar);
                    int i5 = zzdkVar.zzada;
                    int i6 = i5 >>> 3;
                    int i7 = i5 & 7;
                    if (i6 == 2) {
                        if (i7 != 0) {
                            if (i5 != 12) {
                                break;
                                break;
                            }
                            iZza = zzdl.zza(i5, bArr, iZza, i2, zzdkVar);
                        } else {
                            iZza = zzdl.zza(bArr, iZza, zzdkVar);
                            i4 = zzdkVar.zzada;
                            zzeVar = (zzey.zze) this.zzaky.zza(zzdkVar.zzadd, this.zzakn, i4);
                        }
                    } else {
                        if (i6 == 3) {
                            if (zzeVar != null) {
                                zzgt.zzvy();
                                throw new NoSuchMethodError();
                            }
                            if (i7 == 2) {
                                iZza = zzdl.zze(bArr, iZza, zzdkVar);
                                zzdpVar = (zzdp) zzdkVar.zzadc;
                            }
                        }
                        if (i5 != 12) {
                            break;
                        } else {
                            iZza = zzdl.zza(i5, bArr, iZza, i2, zzdkVar);
                        }
                    }
                }
                if (zzdpVar != null) {
                    zzhsVarZzwr.zzb((i4 << 3) | 2, zzdpVar);
                }
                i = iZza;
            } else if ((i3 & 7) == 2) {
                zzeVar = (zzey.zze) this.zzaky.zza(zzdkVar.zzadd, this.zzakn, i3 >>> 3);
                if (zzeVar != null) {
                    zzgt.zzvy();
                    throw new NoSuchMethodError();
                }
                i = zzdl.zza(i3, bArr, iZza, i2, zzhsVarZzwr, zzdkVar);
            } else {
                i = zzdl.zza(i3, bArr, iZza, i2, zzdkVar);
            }
        }
        if (i != i2) {
            throw zzfi.zzva();
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzgx
    public final void zzc(T t, T t2) {
        zzgz.zza(this.zzakx, t, t2);
        if (this.zzako) {
            zzgz.zza(this.zzaky, t, t2);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzgx
    public final void zzj(T t) {
        this.zzakx.zzj(t);
        this.zzaky.zzj(t);
    }

    @Override // com.google.android.gms.internal.measurement.zzgx
    public final int zzt(T t) {
        zzhp<?, ?> zzhpVar = this.zzakx;
        int iZzz = zzhpVar.zzz(zzhpVar.zzx(t)) + 0;
        return this.zzako ? iZzz + this.zzaky.zzh(t).zzts() : iZzz;
    }

    @Override // com.google.android.gms.internal.measurement.zzgx
    public final boolean zzv(T t) {
        return this.zzaky.zzh(t).isInitialized();
    }
}
