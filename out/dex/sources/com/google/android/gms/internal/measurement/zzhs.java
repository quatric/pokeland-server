package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzhs {
    private static final zzhs zzaly = new zzhs(0, new int[0], new Object[0], false);
    private int count;
    private boolean zzacz;
    private int zzaia;
    private Object[] zzakk;
    private int[] zzalz;

    private zzhs() {
        this(0, new int[8], new Object[8], true);
    }

    private zzhs(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zzaia = -1;
        this.count = i;
        this.zzalz = iArr;
        this.zzakk = objArr;
        this.zzacz = z;
    }

    static zzhs zza(zzhs zzhsVar, zzhs zzhsVar2) {
        int i = zzhsVar.count + zzhsVar2.count;
        int[] iArrCopyOf = Arrays.copyOf(zzhsVar.zzalz, i);
        System.arraycopy(zzhsVar2.zzalz, 0, iArrCopyOf, zzhsVar.count, zzhsVar2.count);
        Object[] objArrCopyOf = Arrays.copyOf(zzhsVar.zzakk, i);
        System.arraycopy(zzhsVar2.zzakk, 0, objArrCopyOf, zzhsVar.count, zzhsVar2.count);
        return new zzhs(i, iArrCopyOf, objArrCopyOf, true);
    }

    private static void zzb(int i, Object obj, zzim zzimVar) throws IOException {
        int i2 = i >>> 3;
        int i3 = i & 7;
        if (i3 == 0) {
            zzimVar.zzi(i2, ((Long) obj).longValue());
            return;
        }
        if (i3 == 1) {
            zzimVar.zzc(i2, ((Long) obj).longValue());
            return;
        }
        if (i3 == 2) {
            zzimVar.zza(i2, (zzdp) obj);
            return;
        }
        if (i3 != 3) {
            if (i3 != 5) {
                throw new RuntimeException(zzfi.zzuy());
            }
            zzimVar.zzf(i2, ((Integer) obj).intValue());
        } else if (zzimVar.zztk() == zzey.zzd.zzaio) {
            zzimVar.zzbr(i2);
            ((zzhs) obj).zzb(zzimVar);
            zzimVar.zzbs(i2);
        } else {
            zzimVar.zzbs(i2);
            ((zzhs) obj).zzb(zzimVar);
            zzimVar.zzbr(i2);
        }
    }

    public static zzhs zzwq() {
        return zzaly;
    }

    static zzhs zzwr() {
        return new zzhs();
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0046 A[RETURN] */
    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzhs)) {
            return false;
        }
        zzhs zzhsVar = (zzhs) obj;
        int i = this.count;
        if (i == zzhsVar.count) {
            int[] iArr = this.zzalz;
            int[] iArr2 = zzhsVar.zzalz;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    z = true;
                    break;
                }
                if (iArr[i2] != iArr2[i2]) {
                    z = false;
                    break;
                }
                i2++;
            }
            if (z) {
                Object[] objArr = this.zzakk;
                Object[] objArr2 = zzhsVar.zzakk;
                int i3 = this.count;
                for (int i4 = 0; i4 < i3; i4++) {
                    if (!objArr[i4].equals(objArr2[i4])) {
                        z2 = false;
                        if (!z2) {
                            return true;
                        }
                    }
                }
                z2 = true;
                if (!z2) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = this.count;
        int i2 = (i + 527) * 31;
        int[] iArr = this.zzalz;
        int iHashCode = 17;
        int i3 = 17;
        for (int i4 = 0; i4 < i; i4++) {
            i3 = (i3 * 31) + iArr[i4];
        }
        int i5 = (i2 + i3) * 31;
        Object[] objArr = this.zzakk;
        int i6 = this.count;
        for (int i7 = 0; i7 < i6; i7++) {
            iHashCode = (iHashCode * 31) + objArr[i7].hashCode();
        }
        return i5 + iHashCode;
    }

    final void zza(zzim zzimVar) throws IOException {
        if (zzimVar.zztk() == zzey.zzd.zzaip) {
            for (int i = this.count - 1; i >= 0; i--) {
                zzimVar.zza(this.zzalz[i] >>> 3, this.zzakk[i]);
            }
            return;
        }
        for (int i2 = 0; i2 < this.count; i2++) {
            zzimVar.zza(this.zzalz[i2] >>> 3, this.zzakk[i2]);
        }
    }

    final void zzb(int i, Object obj) {
        if (!this.zzacz) {
            throw new UnsupportedOperationException();
        }
        int i2 = this.count;
        if (i2 == this.zzalz.length) {
            int i3 = this.count + (i2 < 4 ? 8 : i2 >> 1);
            this.zzalz = Arrays.copyOf(this.zzalz, i3);
            this.zzakk = Arrays.copyOf(this.zzakk, i3);
        }
        int[] iArr = this.zzalz;
        int i4 = this.count;
        iArr[i4] = i;
        this.zzakk[i4] = obj;
        this.count = i4 + 1;
    }

    public final void zzb(zzim zzimVar) throws IOException {
        if (this.count == 0) {
            return;
        }
        if (zzimVar.zztk() == zzey.zzd.zzaio) {
            for (int i = 0; i < this.count; i++) {
                zzb(this.zzalz[i], this.zzakk[i], zzimVar);
            }
            return;
        }
        for (int i2 = this.count - 1; i2 >= 0; i2--) {
            zzb(this.zzalz[i2], this.zzakk[i2], zzimVar);
        }
    }

    final void zzb(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.count; i2++) {
            zzgj.zzb(sb, i, String.valueOf(this.zzalz[i2] >>> 3), this.zzakk[i2]);
        }
    }

    public final void zzry() {
        this.zzacz = false;
    }

    public final int zzuk() {
        int iZze;
        int i = this.zzaia;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.count; i3++) {
            int i4 = this.zzalz[i3];
            int i5 = i4 >>> 3;
            int i6 = i4 & 7;
            if (i6 == 0) {
                iZze = zzee.zze(i5, ((Long) this.zzakk[i3]).longValue());
            } else if (i6 == 1) {
                iZze = zzee.zzg(i5, ((Long) this.zzakk[i3]).longValue());
            } else if (i6 == 2) {
                iZze = zzee.zzc(i5, (zzdp) this.zzakk[i3]);
            } else if (i6 == 3) {
                iZze = (zzee.zzbi(i5) << 1) + ((zzhs) this.zzakk[i3]).zzuk();
            } else {
                if (i6 != 5) {
                    throw new IllegalStateException(zzfi.zzuy());
                }
                iZze = zzee.zzj(i5, ((Integer) this.zzakk[i3]).intValue());
            }
            i2 += iZze;
        }
        this.zzaia = i2;
        return i2;
    }

    public final int zzws() {
        int i = this.zzaia;
        if (i != -1) {
            return i;
        }
        int iZzd = 0;
        for (int i2 = 0; i2 < this.count; i2++) {
            iZzd += zzee.zzd(this.zzalz[i2] >>> 3, (zzdp) this.zzakk[i2]);
        }
        this.zzaia = iZzd;
        return iZzd;
    }
}
