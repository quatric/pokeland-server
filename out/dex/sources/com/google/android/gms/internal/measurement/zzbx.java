package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzbx extends zziq<zzbx> {
    private static volatile zzbx[] zzzr;
    public String name = null;
    public Boolean zzzs = null;
    public Boolean zzzt = null;
    public Integer zzzu = null;

    public zzbx() {
        this.zzaoo = null;
        this.zzaow = -1;
    }

    public static zzbx[] zzrc() {
        if (zzzr == null) {
            synchronized (zziu.zzaov) {
                if (zzzr == null) {
                    zzzr = new zzbx[0];
                }
            }
        }
        return zzzr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbx)) {
            return false;
        }
        zzbx zzbxVar = (zzbx) obj;
        String str = this.name;
        if (str == null) {
            if (zzbxVar.name != null) {
                return false;
            }
        } else if (!str.equals(zzbxVar.name)) {
            return false;
        }
        Boolean bool = this.zzzs;
        if (bool == null) {
            if (zzbxVar.zzzs != null) {
                return false;
            }
        } else if (!bool.equals(zzbxVar.zzzs)) {
            return false;
        }
        Boolean bool2 = this.zzzt;
        if (bool2 == null) {
            if (zzbxVar.zzzt != null) {
                return false;
            }
        } else if (!bool2.equals(zzbxVar.zzzt)) {
            return false;
        }
        Integer num = this.zzzu;
        if (num == null) {
            if (zzbxVar.zzzu != null) {
                return false;
            }
        } else if (!num.equals(zzbxVar.zzzu)) {
            return false;
        }
        if (this.zzaoo == null || this.zzaoo.isEmpty()) {
            return zzbxVar.zzaoo == null || zzbxVar.zzaoo.isEmpty();
        }
        return this.zzaoo.equals(zzbxVar.zzaoo);
    }

    public final int hashCode() {
        int iHashCode = (getClass().getName().hashCode() + 527) * 31;
        String str = this.name;
        int iHashCode2 = 0;
        int iHashCode3 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        Boolean bool = this.zzzs;
        int iHashCode4 = (iHashCode3 + (bool == null ? 0 : bool.hashCode())) * 31;
        Boolean bool2 = this.zzzt;
        int iHashCode5 = (iHashCode4 + (bool2 == null ? 0 : bool2.hashCode())) * 31;
        Integer num = this.zzzu;
        int iHashCode6 = (iHashCode5 + (num == null ? 0 : num.hashCode())) * 31;
        if (this.zzaoo != null && !this.zzaoo.isEmpty()) {
            iHashCode2 = this.zzaoo.hashCode();
        }
        return iHashCode6 + iHashCode2;
    }

    @Override // com.google.android.gms.internal.measurement.zziw
    public final /* synthetic */ zziw zza(zzil zzilVar) throws IOException {
        while (true) {
            int iZzsg = zzilVar.zzsg();
            if (iZzsg == 0) {
                return this;
            }
            if (iZzsg == 10) {
                this.name = zzilVar.readString();
            } else if (iZzsg == 16) {
                this.zzzs = Boolean.valueOf(zzilVar.zzsm());
            } else if (iZzsg == 24) {
                this.zzzt = Boolean.valueOf(zzilVar.zzsm());
            } else if (iZzsg == 32) {
                this.zzzu = Integer.valueOf(zzilVar.zzta());
            } else if (!super.zza(zzilVar, iZzsg)) {
                return this;
            }
        }
    }

    @Override // com.google.android.gms.internal.measurement.zziq, com.google.android.gms.internal.measurement.zziw
    public final void zza(zzio zzioVar) throws IOException {
        String str = this.name;
        if (str != null) {
            zzioVar.zzb(1, str);
        }
        Boolean bool = this.zzzs;
        if (bool != null) {
            zzioVar.zzb(2, bool.booleanValue());
        }
        Boolean bool2 = this.zzzt;
        if (bool2 != null) {
            zzioVar.zzb(3, bool2.booleanValue());
        }
        Integer num = this.zzzu;
        if (num != null) {
            zzioVar.zzc(4, num.intValue());
        }
        super.zza(zzioVar);
    }

    @Override // com.google.android.gms.internal.measurement.zziq, com.google.android.gms.internal.measurement.zziw
    protected final int zzqy() {
        int iZzqy = super.zzqy();
        String str = this.name;
        if (str != null) {
            iZzqy += zzio.zzc(1, str);
        }
        Boolean bool = this.zzzs;
        if (bool != null) {
            bool.booleanValue();
            iZzqy += zzio.zzbi(2) + 1;
        }
        Boolean bool2 = this.zzzt;
        if (bool2 != null) {
            bool2.booleanValue();
            iZzqy += zzio.zzbi(3) + 1;
        }
        Integer num = this.zzzu;
        return num != null ? iZzqy + zzio.zzg(4, num.intValue()) : iZzqy;
    }
}
