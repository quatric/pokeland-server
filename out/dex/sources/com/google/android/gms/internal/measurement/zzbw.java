package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzbw extends zziq<zzbw> {
    public Long zzzk = null;
    public String zzcg = null;
    private Integer zzzl = null;
    public zzbq.zza[] zzzm = new zzbq.zza[0];
    public zzbx[] zzzn = zzbx.zzrc();
    public zzbv[] zzzo = zzbv.zzqx();
    private String zzzp = null;
    public Boolean zzzq = null;

    public zzbw() {
        this.zzaoo = null;
        this.zzaow = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbw)) {
            return false;
        }
        zzbw zzbwVar = (zzbw) obj;
        Long l = this.zzzk;
        if (l == null) {
            if (zzbwVar.zzzk != null) {
                return false;
            }
        } else if (!l.equals(zzbwVar.zzzk)) {
            return false;
        }
        String str = this.zzcg;
        if (str == null) {
            if (zzbwVar.zzcg != null) {
                return false;
            }
        } else if (!str.equals(zzbwVar.zzcg)) {
            return false;
        }
        Integer num = this.zzzl;
        if (num == null) {
            if (zzbwVar.zzzl != null) {
                return false;
            }
        } else if (!num.equals(zzbwVar.zzzl)) {
            return false;
        }
        if (!zziu.equals(this.zzzm, zzbwVar.zzzm) || !zziu.equals(this.zzzn, zzbwVar.zzzn) || !zziu.equals(this.zzzo, zzbwVar.zzzo)) {
            return false;
        }
        String str2 = this.zzzp;
        if (str2 == null) {
            if (zzbwVar.zzzp != null) {
                return false;
            }
        } else if (!str2.equals(zzbwVar.zzzp)) {
            return false;
        }
        Boolean bool = this.zzzq;
        if (bool == null) {
            if (zzbwVar.zzzq != null) {
                return false;
            }
        } else if (!bool.equals(zzbwVar.zzzq)) {
            return false;
        }
        if (this.zzaoo == null || this.zzaoo.isEmpty()) {
            return zzbwVar.zzaoo == null || zzbwVar.zzaoo.isEmpty();
        }
        return this.zzaoo.equals(zzbwVar.zzaoo);
    }

    public final int hashCode() {
        int iHashCode = (getClass().getName().hashCode() + 527) * 31;
        Long l = this.zzzk;
        int iHashCode2 = 0;
        int iHashCode3 = (iHashCode + (l == null ? 0 : l.hashCode())) * 31;
        String str = this.zzcg;
        int iHashCode4 = (iHashCode3 + (str == null ? 0 : str.hashCode())) * 31;
        Integer num = this.zzzl;
        int iHashCode5 = (((((((iHashCode4 + (num == null ? 0 : num.hashCode())) * 31) + zziu.hashCode(this.zzzm)) * 31) + zziu.hashCode(this.zzzn)) * 31) + zziu.hashCode(this.zzzo)) * 31;
        String str2 = this.zzzp;
        int iHashCode6 = (iHashCode5 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Boolean bool = this.zzzq;
        int iHashCode7 = (iHashCode6 + (bool == null ? 0 : bool.hashCode())) * 31;
        if (this.zzaoo != null && !this.zzaoo.isEmpty()) {
            iHashCode2 = this.zzaoo.hashCode();
        }
        return iHashCode7 + iHashCode2;
    }

    @Override // com.google.android.gms.internal.measurement.zziw
    public final /* synthetic */ zziw zza(zzil zzilVar) throws IOException {
        while (true) {
            int iZzsg = zzilVar.zzsg();
            if (iZzsg == 0) {
                return this;
            }
            if (iZzsg == 8) {
                this.zzzk = Long.valueOf(zzilVar.zztb());
            } else if (iZzsg == 18) {
                this.zzcg = zzilVar.readString();
            } else if (iZzsg == 24) {
                this.zzzl = Integer.valueOf(zzilVar.zzta());
            } else if (iZzsg == 34) {
                int iZzb = zzix.zzb(zzilVar, 34);
                zzbq.zza[] zzaVarArr = this.zzzm;
                int length = zzaVarArr == null ? 0 : zzaVarArr.length;
                zzbq.zza[] zzaVarArr2 = new zzbq.zza[iZzb + length];
                if (length != 0) {
                    System.arraycopy(this.zzzm, 0, zzaVarArr2, 0, length);
                }
                while (length < zzaVarArr2.length - 1) {
                    zzaVarArr2[length] = (zzbq.zza) zzilVar.zza(zzbq.zza.zzkj());
                    zzilVar.zzsg();
                    length++;
                }
                zzaVarArr2[length] = (zzbq.zza) zzilVar.zza(zzbq.zza.zzkj());
                this.zzzm = zzaVarArr2;
            } else if (iZzsg == 42) {
                int iZzb2 = zzix.zzb(zzilVar, 42);
                zzbx[] zzbxVarArr = this.zzzn;
                int length2 = zzbxVarArr == null ? 0 : zzbxVarArr.length;
                zzbx[] zzbxVarArr2 = new zzbx[iZzb2 + length2];
                if (length2 != 0) {
                    System.arraycopy(this.zzzn, 0, zzbxVarArr2, 0, length2);
                }
                while (length2 < zzbxVarArr2.length - 1) {
                    zzbxVarArr2[length2] = new zzbx();
                    zzilVar.zza(zzbxVarArr2[length2]);
                    zzilVar.zzsg();
                    length2++;
                }
                zzbxVarArr2[length2] = new zzbx();
                zzilVar.zza(zzbxVarArr2[length2]);
                this.zzzn = zzbxVarArr2;
            } else if (iZzsg == 50) {
                int iZzb3 = zzix.zzb(zzilVar, 50);
                zzbv[] zzbvVarArr = this.zzzo;
                int length3 = zzbvVarArr == null ? 0 : zzbvVarArr.length;
                zzbv[] zzbvVarArr2 = new zzbv[iZzb3 + length3];
                if (length3 != 0) {
                    System.arraycopy(this.zzzo, 0, zzbvVarArr2, 0, length3);
                }
                while (length3 < zzbvVarArr2.length - 1) {
                    zzbvVarArr2[length3] = new zzbv();
                    zzilVar.zza(zzbvVarArr2[length3]);
                    zzilVar.zzsg();
                    length3++;
                }
                zzbvVarArr2[length3] = new zzbv();
                zzilVar.zza(zzbvVarArr2[length3]);
                this.zzzo = zzbvVarArr2;
            } else if (iZzsg == 58) {
                this.zzzp = zzilVar.readString();
            } else if (iZzsg == 64) {
                this.zzzq = Boolean.valueOf(zzilVar.zzsm());
            } else if (!super.zza(zzilVar, iZzsg)) {
                return this;
            }
        }
    }

    @Override // com.google.android.gms.internal.measurement.zziq, com.google.android.gms.internal.measurement.zziw
    public final void zza(zzio zzioVar) throws IOException {
        Long l = this.zzzk;
        int i = 0;
        if (l != null) {
            long jLongValue = l.longValue();
            zzioVar.zzb(1, 0);
            zzioVar.zzbz(jLongValue);
        }
        String str = this.zzcg;
        if (str != null) {
            zzioVar.zzb(2, str);
        }
        Integer num = this.zzzl;
        if (num != null) {
            zzioVar.zzc(3, num.intValue());
        }
        zzbq.zza[] zzaVarArr = this.zzzm;
        if (zzaVarArr != null && zzaVarArr.length > 0) {
            int i2 = 0;
            while (true) {
                zzbq.zza[] zzaVarArr2 = this.zzzm;
                if (i2 >= zzaVarArr2.length) {
                    break;
                }
                zzbq.zza zzaVar = zzaVarArr2[i2];
                if (zzaVar != null) {
                    zzioVar.zze(4, zzaVar);
                }
                i2++;
            }
        }
        zzbx[] zzbxVarArr = this.zzzn;
        if (zzbxVarArr != null && zzbxVarArr.length > 0) {
            int i3 = 0;
            while (true) {
                zzbx[] zzbxVarArr2 = this.zzzn;
                if (i3 >= zzbxVarArr2.length) {
                    break;
                }
                zzbx zzbxVar = zzbxVarArr2[i3];
                if (zzbxVar != null) {
                    zzioVar.zza(5, zzbxVar);
                }
                i3++;
            }
        }
        zzbv[] zzbvVarArr = this.zzzo;
        if (zzbvVarArr != null && zzbvVarArr.length > 0) {
            while (true) {
                zzbv[] zzbvVarArr2 = this.zzzo;
                if (i >= zzbvVarArr2.length) {
                    break;
                }
                zzbv zzbvVar = zzbvVarArr2[i];
                if (zzbvVar != null) {
                    zzioVar.zza(6, zzbvVar);
                }
                i++;
            }
        }
        String str2 = this.zzzp;
        if (str2 != null) {
            zzioVar.zzb(7, str2);
        }
        Boolean bool = this.zzzq;
        if (bool != null) {
            zzioVar.zzb(8, bool.booleanValue());
        }
        super.zza(zzioVar);
    }

    @Override // com.google.android.gms.internal.measurement.zziq, com.google.android.gms.internal.measurement.zziw
    protected final int zzqy() {
        int i;
        int iZzqy = super.zzqy();
        Long l = this.zzzk;
        if (l != null) {
            long jLongValue = l.longValue();
            int iZzbi = zzio.zzbi(1);
            if (((-128) & jLongValue) == 0) {
                i = 1;
            } else if (((-16384) & jLongValue) == 0) {
                i = 2;
            } else if (((-2097152) & jLongValue) == 0) {
                i = 3;
            } else if (((-268435456) & jLongValue) == 0) {
                i = 4;
            } else if (((-34359738368L) & jLongValue) == 0) {
                i = 5;
            } else if (((-4398046511104L) & jLongValue) == 0) {
                i = 6;
            } else if (((-562949953421312L) & jLongValue) == 0) {
                i = 7;
            } else if (((-72057594037927936L) & jLongValue) == 0) {
                i = 8;
            } else {
                i = (jLongValue & Long.MIN_VALUE) == 0 ? 9 : 10;
            }
            iZzqy += iZzbi + i;
        }
        String str = this.zzcg;
        if (str != null) {
            iZzqy += zzio.zzc(2, str);
        }
        Integer num = this.zzzl;
        if (num != null) {
            iZzqy += zzio.zzg(3, num.intValue());
        }
        zzbq.zza[] zzaVarArr = this.zzzm;
        int i2 = 0;
        if (zzaVarArr != null && zzaVarArr.length > 0) {
            int iZzc = iZzqy;
            int i3 = 0;
            while (true) {
                zzbq.zza[] zzaVarArr2 = this.zzzm;
                if (i3 >= zzaVarArr2.length) {
                    break;
                }
                zzbq.zza zzaVar = zzaVarArr2[i3];
                if (zzaVar != null) {
                    iZzc += zzee.zzc(4, zzaVar);
                }
                i3++;
            }
            iZzqy = iZzc;
        }
        zzbx[] zzbxVarArr = this.zzzn;
        if (zzbxVarArr != null && zzbxVarArr.length > 0) {
            int iZzb = iZzqy;
            int i4 = 0;
            while (true) {
                zzbx[] zzbxVarArr2 = this.zzzn;
                if (i4 >= zzbxVarArr2.length) {
                    break;
                }
                zzbx zzbxVar = zzbxVarArr2[i4];
                if (zzbxVar != null) {
                    iZzb += zzio.zzb(5, zzbxVar);
                }
                i4++;
            }
            iZzqy = iZzb;
        }
        zzbv[] zzbvVarArr = this.zzzo;
        if (zzbvVarArr != null && zzbvVarArr.length > 0) {
            while (true) {
                zzbv[] zzbvVarArr2 = this.zzzo;
                if (i2 >= zzbvVarArr2.length) {
                    break;
                }
                zzbv zzbvVar = zzbvVarArr2[i2];
                if (zzbvVar != null) {
                    iZzqy += zzio.zzb(6, zzbvVar);
                }
                i2++;
            }
        }
        String str2 = this.zzzp;
        if (str2 != null) {
            iZzqy += zzio.zzc(7, str2);
        }
        Boolean bool = this.zzzq;
        if (bool == null) {
            return iZzqy;
        }
        bool.booleanValue();
        return iZzqy + zzio.zzbi(8) + 1;
    }
}
