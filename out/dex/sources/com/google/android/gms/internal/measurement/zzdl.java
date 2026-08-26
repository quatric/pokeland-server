package com.google.android.gms.internal.measurement;

import com.google.common.base.Ascii;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzdl {
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static int zza(int i, byte[] bArr, int i2, int i3, zzdk zzdkVar) throws zzfi {
        if ((i >>> 3) == 0) {
            throw zzfi.zzuw();
        }
        int i4 = i & 7;
        if (i4 == 0) {
            return zzb(bArr, i2, zzdkVar);
        }
        if (i4 == 1) {
            return i2 + 8;
        }
        if (i4 == 2) {
            return zza(bArr, i2, zzdkVar) + zzdkVar.zzada;
        }
        if (i4 != 3) {
            if (i4 == 5) {
                return i2 + 4;
            }
            throw zzfi.zzuw();
        }
        int i5 = (i & (-8)) | 4;
        int i6 = 0;
        while (i2 < i3) {
            i2 = zza(bArr, i2, zzdkVar);
            i6 = zzdkVar.zzada;
            if (i6 == i5) {
                break;
            }
            i2 = zza(i6, bArr, i2, i3, zzdkVar);
        }
        if (i2 > i3 || i6 != i5) {
            throw zzfi.zzva();
        }
        return i2;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzff<?> zzffVar, zzdk zzdkVar) {
        zzfa zzfaVar = (zzfa) zzffVar;
        int iZza = zza(bArr, i2, zzdkVar);
        zzfaVar.zzbu(zzdkVar.zzada);
        while (iZza < i3) {
            int iZza2 = zza(bArr, iZza, zzdkVar);
            if (i != zzdkVar.zzada) {
                break;
            }
            iZza = zza(bArr, iZza2, zzdkVar);
            zzfaVar.zzbu(zzdkVar.zzada);
        }
        return iZza;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static int zza(int i, byte[] bArr, int i2, int i3, zzhs zzhsVar, zzdk zzdkVar) throws zzfi {
        if ((i >>> 3) == 0) {
            throw zzfi.zzuw();
        }
        int i4 = i & 7;
        if (i4 == 0) {
            int iZzb = zzb(bArr, i2, zzdkVar);
            zzhsVar.zzb(i, Long.valueOf(zzdkVar.zzadb));
            return iZzb;
        }
        if (i4 == 1) {
            zzhsVar.zzb(i, Long.valueOf(zzb(bArr, i2)));
            return i2 + 8;
        }
        if (i4 == 2) {
            int iZza = zza(bArr, i2, zzdkVar);
            int i5 = zzdkVar.zzada;
            if (i5 < 0) {
                throw zzfi.zzuu();
            }
            if (i5 > bArr.length - iZza) {
                throw zzfi.zzut();
            }
            if (i5 == 0) {
                zzhsVar.zzb(i, zzdp.zzadh);
            } else {
                zzhsVar.zzb(i, zzdp.zzb(bArr, iZza, i5));
            }
            return iZza + i5;
        }
        if (i4 != 3) {
            if (i4 != 5) {
                throw zzfi.zzuw();
            }
            zzhsVar.zzb(i, Integer.valueOf(zza(bArr, i2)));
            return i2 + 4;
        }
        zzhs zzhsVarZzwr = zzhs.zzwr();
        int i6 = (i & (-8)) | 4;
        int i7 = 0;
        while (i2 < i3) {
            int iZza2 = zza(bArr, i2, zzdkVar);
            int i8 = zzdkVar.zzada;
            if (i8 == i6) {
                i7 = i8;
                i2 = iZza2;
                break;
            }
            i7 = i8;
            i2 = zza(i8, bArr, iZza2, i3, zzhsVarZzwr, zzdkVar);
        }
        if (i2 > i3 || i7 != i6) {
            throw zzfi.zzva();
        }
        zzhsVar.zzb(i, zzhsVarZzwr);
        return i2;
    }

    static int zza(int i, byte[] bArr, int i2, zzdk zzdkVar) {
        int i3 = i & 127;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            zzdkVar.zzada = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & 127) << 7);
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            zzdkVar.zzada = i5 | (b2 << Ascii.f292SO);
            return i6;
        }
        int i7 = i5 | ((b2 & 127) << 14);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzdkVar.zzada = i7 | (b3 << Ascii.NAK);
            return i8;
        }
        int i9 = i7 | ((b3 & 127) << 21);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzdkVar.zzada = i9 | (b4 << Ascii.f285FS);
            return i10;
        }
        int i11 = i9 | ((b4 & 127) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzdkVar.zzada = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int zza(zzgx<?> zzgxVar, int i, byte[] bArr, int i2, int i3, zzff<?> zzffVar, zzdk zzdkVar) throws IOException {
        int iZza = zza(zzgxVar, bArr, i2, i3, zzdkVar);
        zzffVar.add(zzdkVar.zzadc);
        while (iZza < i3) {
            int iZza2 = zza(bArr, iZza, zzdkVar);
            if (i != zzdkVar.zzada) {
                break;
            }
            iZza = zza(zzgxVar, bArr, iZza2, i3, zzdkVar);
            zzffVar.add(zzdkVar.zzadc);
        }
        return iZza;
    }

    static int zza(zzgx zzgxVar, byte[] bArr, int i, int i2, int i3, zzdk zzdkVar) throws IOException {
        zzgm zzgmVar = (zzgm) zzgxVar;
        Object objNewInstance = zzgmVar.newInstance();
        int iZza = zzgmVar.zza(objNewInstance, bArr, i, i2, i3, zzdkVar);
        zzgmVar.zzj(objNewInstance);
        zzdkVar.zzadc = objNewInstance;
        return iZza;
    }

    static int zza(zzgx zzgxVar, byte[] bArr, int i, int i2, zzdk zzdkVar) throws IOException {
        int iZza = i + 1;
        int i3 = bArr[i];
        if (i3 < 0) {
            iZza = zza(i3, bArr, iZza, zzdkVar);
            i3 = zzdkVar.zzada;
        }
        int i4 = iZza;
        if (i3 < 0 || i3 > i2 - i4) {
            throw zzfi.zzut();
        }
        Object objNewInstance = zzgxVar.newInstance();
        int i5 = i3 + i4;
        zzgxVar.zza(objNewInstance, bArr, i4, i5, zzdkVar);
        zzgxVar.zzj(objNewInstance);
        zzdkVar.zzadc = objNewInstance;
        return i5;
    }

    static int zza(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    static int zza(byte[] bArr, int i, zzdk zzdkVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zza(b, bArr, i2, zzdkVar);
        }
        zzdkVar.zzada = b;
        return i2;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static int zza(byte[] bArr, int i, zzff<?> zzffVar, zzdk zzdkVar) throws IOException {
        zzfa zzfaVar = (zzfa) zzffVar;
        int iZza = zza(bArr, i, zzdkVar);
        int i2 = zzdkVar.zzada + iZza;
        while (iZza < i2) {
            iZza = zza(bArr, iZza, zzdkVar);
            zzfaVar.zzbu(zzdkVar.zzada);
        }
        if (iZza == i2) {
            return iZza;
        }
        throw zzfi.zzut();
    }

    static int zzb(byte[] bArr, int i, zzdk zzdkVar) {
        int i2 = i + 1;
        long j = bArr[i];
        if (j >= 0) {
            zzdkVar.zzadb = j;
            return i2;
        }
        int i3 = i2 + 1;
        byte b = bArr[i2];
        long j2 = (j & 127) | (((long) (b & 127)) << 7);
        int i4 = 7;
        while (b < 0) {
            int i5 = i3 + 1;
            byte b2 = bArr[i3];
            i4 += 7;
            j2 |= ((long) (b2 & 127)) << i4;
            b = b2;
            i3 = i5;
        }
        zzdkVar.zzadb = j2;
        return i3;
    }

    static long zzb(byte[] bArr, int i) {
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    static double zzc(byte[] bArr, int i) {
        return Double.longBitsToDouble(zzb(bArr, i));
    }

    static int zzc(byte[] bArr, int i, zzdk zzdkVar) throws zzfi {
        int iZza = zza(bArr, i, zzdkVar);
        int i2 = zzdkVar.zzada;
        if (i2 < 0) {
            throw zzfi.zzuu();
        }
        if (i2 == 0) {
            zzdkVar.zzadc = "";
            return iZza;
        }
        zzdkVar.zzadc = new String(bArr, iZza, i2, zzez.UTF_8);
        return iZza + i2;
    }

    static float zzd(byte[] bArr, int i) {
        return Float.intBitsToFloat(zza(bArr, i));
    }

    static int zzd(byte[] bArr, int i, zzdk zzdkVar) throws zzfi {
        int iZza = zza(bArr, i, zzdkVar);
        int i2 = zzdkVar.zzada;
        if (i2 < 0) {
            throw zzfi.zzuu();
        }
        if (i2 == 0) {
            zzdkVar.zzadc = "";
            return iZza;
        }
        zzdkVar.zzadc = zzhy.zzh(bArr, iZza, i2);
        return iZza + i2;
    }

    static int zze(byte[] bArr, int i, zzdk zzdkVar) throws zzfi {
        int iZza = zza(bArr, i, zzdkVar);
        int i2 = zzdkVar.zzada;
        if (i2 < 0) {
            throw zzfi.zzuu();
        }
        if (i2 > bArr.length - iZza) {
            throw zzfi.zzut();
        }
        if (i2 == 0) {
            zzdkVar.zzadc = zzdp.zzadh;
            return iZza;
        }
        zzdkVar.zzadc = zzdp.zzb(bArr, iZza, i2);
        return iZza + i2;
    }
}
