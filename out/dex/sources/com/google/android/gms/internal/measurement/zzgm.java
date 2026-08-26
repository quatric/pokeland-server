package com.google.android.gms.internal.measurement;

import android.support.v4.view.MotionEventCompat;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzgm<T> implements zzgx<T> {
    private static final int[] zzakh = new int[0];
    private static final Unsafe zzaki = zzhv.zzwv();
    private final int[] zzakj;
    private final Object[] zzakk;
    private final int zzakl;
    private final int zzakm;
    private final zzgi zzakn;
    private final boolean zzako;
    private final boolean zzakp;
    private final boolean zzakq;
    private final boolean zzakr;
    private final int[] zzaks;
    private final int zzakt;
    private final int zzaku;
    private final zzgq zzakv;
    private final zzfs zzakw;
    private final zzhp<?, ?> zzakx;
    private final zzen<?> zzaky;
    private final zzgb zzakz;

    private zzgm(int[] iArr, Object[] objArr, int i, int i2, zzgi zzgiVar, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzgq zzgqVar, zzfs zzfsVar, zzhp<?, ?> zzhpVar, zzen<?> zzenVar, zzgb zzgbVar) {
        this.zzakj = iArr;
        this.zzakk = objArr;
        this.zzakl = i;
        this.zzakm = i2;
        this.zzakp = zzgiVar instanceof zzey;
        this.zzakq = z;
        this.zzako = zzenVar != null && zzenVar.zze(zzgiVar);
        this.zzakr = false;
        this.zzaks = iArr2;
        this.zzakt = i3;
        this.zzaku = i4;
        this.zzakv = zzgqVar;
        this.zzakw = zzfsVar;
        this.zzakx = zzhpVar;
        this.zzaky = zzenVar;
        this.zzakn = zzgiVar;
        this.zzakz = zzgbVar;
    }

    private static <UT, UB> int zza(zzhp<UT, UB> zzhpVar, T t) {
        return zzhpVar.zzt(zzhpVar.zzx(t));
    }

    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzdk zzdkVar) throws IOException {
        int iZzb;
        Unsafe unsafe = zzaki;
        long j2 = this.zzakj[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 != 1) {
                    return i;
                }
                unsafe.putObject(t, j, Double.valueOf(zzdl.zzc(bArr, i)));
                iZzb = i + 8;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 52:
                if (i5 != 5) {
                    return i;
                }
                unsafe.putObject(t, j, Float.valueOf(zzdl.zzd(bArr, i)));
                iZzb = i + 4;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 53:
            case 54:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzdl.zzb(bArr, i, zzdkVar);
                unsafe.putObject(t, j, Long.valueOf(zzdkVar.zzadb));
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 55:
            case 62:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzdl.zza(bArr, i, zzdkVar);
                unsafe.putObject(t, j, Integer.valueOf(zzdkVar.zzada));
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 56:
            case 65:
                if (i5 != 1) {
                    return i;
                }
                unsafe.putObject(t, j, Long.valueOf(zzdl.zzb(bArr, i)));
                iZzb = i + 8;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 57:
            case 64:
                if (i5 != 5) {
                    return i;
                }
                unsafe.putObject(t, j, Integer.valueOf(zzdl.zza(bArr, i)));
                iZzb = i + 4;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 58:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzdl.zzb(bArr, i, zzdkVar);
                unsafe.putObject(t, j, Boolean.valueOf(zzdkVar.zzadb != 0));
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 59:
                if (i5 != 2) {
                    return i;
                }
                int iZza = zzdl.zza(bArr, i, zzdkVar);
                int i9 = zzdkVar.zzada;
                if (i9 == 0) {
                    unsafe.putObject(t, j, "");
                } else {
                    if ((i6 & 536870912) != 0 && !zzhy.zzf(bArr, iZza, iZza + i9)) {
                        throw zzfi.zzvb();
                    }
                    unsafe.putObject(t, j, new String(bArr, iZza, i9, zzez.UTF_8));
                    iZza += i9;
                }
                unsafe.putInt(t, j2, i4);
                return iZza;
            case 60:
                if (i5 != 2) {
                    return i;
                }
                int iZza2 = zzdl.zza(zzbx(i8), bArr, i, i2, zzdkVar);
                Object object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                if (object == null) {
                    unsafe.putObject(t, j, zzdkVar.zzadc);
                } else {
                    unsafe.putObject(t, j, zzez.zza(object, zzdkVar.zzadc));
                }
                unsafe.putInt(t, j2, i4);
                return iZza2;
            case 61:
                if (i5 != 2) {
                    return i;
                }
                iZzb = zzdl.zze(bArr, i, zzdkVar);
                unsafe.putObject(t, j, zzdkVar.zzadc);
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 63:
                if (i5 != 0) {
                    return i;
                }
                int iZza3 = zzdl.zza(bArr, i, zzdkVar);
                int i10 = zzdkVar.zzada;
                zzfe zzfeVarZzbz = zzbz(i8);
                if (zzfeVarZzbz != null && !zzfeVarZzbz.zzg(i10)) {
                    zzu(t).zzb(i3, Long.valueOf(i10));
                    return iZza3;
                }
                unsafe.putObject(t, j, Integer.valueOf(i10));
                iZzb = iZza3;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 66:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzdl.zza(bArr, i, zzdkVar);
                unsafe.putObject(t, j, Integer.valueOf(zzeb.zzaz(zzdkVar.zzada)));
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 67:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzdl.zzb(bArr, i, zzdkVar);
                unsafe.putObject(t, j, Long.valueOf(zzeb.zzbm(zzdkVar.zzadb)));
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 68:
                if (i5 != 3) {
                    return i;
                }
                iZzb = zzdl.zza(zzbx(i8), bArr, i, i2, (i3 & (-8)) | 4, zzdkVar);
                Object object2 = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                if (object2 == null) {
                    unsafe.putObject(t, j, zzdkVar.zzadc);
                } else {
                    unsafe.putObject(t, j, zzez.zza(object2, zzdkVar.zzadc));
                }
                unsafe.putInt(t, j2, i4);
                return iZzb;
            default:
                return i;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzdk zzdkVar) throws IOException {
        int iZza;
        int iZza2 = i;
        zzff zzffVarZzap = (zzff) zzaki.getObject(t, j2);
        if (!zzffVarZzap.zzrx()) {
            int size = zzffVarZzap.size();
            zzffVarZzap = zzffVarZzap.zzap(size == 0 ? 10 : size << 1);
            zzaki.putObject(t, j2, zzffVarZzap);
        }
        switch (i7) {
            case 18:
            case MotionEventCompat.AXIS_GENERIC_4 /* 35 */:
                if (i5 == 2) {
                    zzeh zzehVar = (zzeh) zzffVarZzap;
                    int iZza3 = zzdl.zza(bArr, iZza2, zzdkVar);
                    int i8 = zzdkVar.zzada + iZza3;
                    while (iZza3 < i8) {
                        zzehVar.zzf(zzdl.zzc(bArr, iZza3));
                        iZza3 += 8;
                    }
                    if (iZza3 == i8) {
                        return iZza3;
                    }
                    throw zzfi.zzut();
                }
                if (i5 == 1) {
                    zzeh zzehVar2 = (zzeh) zzffVarZzap;
                    zzehVar2.zzf(zzdl.zzc(bArr, i));
                    while (true) {
                        int i9 = iZza2 + 8;
                        if (i9 >= i2) {
                            return i9;
                        }
                        iZza2 = zzdl.zza(bArr, i9, zzdkVar);
                        if (i3 != zzdkVar.zzada) {
                            return i9;
                        }
                        zzehVar2.zzf(zzdl.zzc(bArr, iZza2));
                    }
                }
                return iZza2;
            case 19:
            case MotionEventCompat.AXIS_GENERIC_5 /* 36 */:
                if (i5 == 2) {
                    zzeu zzeuVar = (zzeu) zzffVarZzap;
                    int iZza4 = zzdl.zza(bArr, iZza2, zzdkVar);
                    int i10 = zzdkVar.zzada + iZza4;
                    while (iZza4 < i10) {
                        zzeuVar.zzc(zzdl.zzd(bArr, iZza4));
                        iZza4 += 4;
                    }
                    if (iZza4 == i10) {
                        return iZza4;
                    }
                    throw zzfi.zzut();
                }
                if (i5 == 5) {
                    zzeu zzeuVar2 = (zzeu) zzffVarZzap;
                    zzeuVar2.zzc(zzdl.zzd(bArr, i));
                    while (true) {
                        int i11 = iZza2 + 4;
                        if (i11 >= i2) {
                            return i11;
                        }
                        iZza2 = zzdl.zza(bArr, i11, zzdkVar);
                        if (i3 != zzdkVar.zzada) {
                            return i11;
                        }
                        zzeuVar2.zzc(zzdl.zzd(bArr, iZza2));
                    }
                }
                return iZza2;
            case 20:
            case MotionEventCompat.AXIS_WHEEL /* 21 */:
            case MotionEventCompat.AXIS_GENERIC_6 /* 37 */:
            case MotionEventCompat.AXIS_GENERIC_7 /* 38 */:
                if (i5 == 2) {
                    zzfw zzfwVar = (zzfw) zzffVarZzap;
                    int iZza5 = zzdl.zza(bArr, iZza2, zzdkVar);
                    int i12 = zzdkVar.zzada + iZza5;
                    while (iZza5 < i12) {
                        iZza5 = zzdl.zzb(bArr, iZza5, zzdkVar);
                        zzfwVar.zzby(zzdkVar.zzadb);
                    }
                    if (iZza5 == i12) {
                        return iZza5;
                    }
                    throw zzfi.zzut();
                }
                if (i5 == 0) {
                    zzfw zzfwVar2 = (zzfw) zzffVarZzap;
                    int iZzb = zzdl.zzb(bArr, iZza2, zzdkVar);
                    zzfwVar2.zzby(zzdkVar.zzadb);
                    while (iZzb < i2) {
                        int iZza6 = zzdl.zza(bArr, iZzb, zzdkVar);
                        if (i3 != zzdkVar.zzada) {
                            return iZzb;
                        }
                        iZzb = zzdl.zzb(bArr, iZza6, zzdkVar);
                        zzfwVar2.zzby(zzdkVar.zzadb);
                    }
                    return iZzb;
                }
                return iZza2;
            case MotionEventCompat.AXIS_GAS /* 22 */:
            case 29:
            case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
            case MotionEventCompat.AXIS_GENERIC_12 /* 43 */:
                if (i5 == 2) {
                    return zzdl.zza(bArr, iZza2, (zzff<?>) zzffVarZzap, zzdkVar);
                }
                if (i5 == 0) {
                    return zzdl.zza(i3, bArr, i, i2, (zzff<?>) zzffVarZzap, zzdkVar);
                }
                return iZza2;
            case MotionEventCompat.AXIS_BRAKE /* 23 */:
            case 32:
            case MotionEventCompat.AXIS_GENERIC_9 /* 40 */:
            case MotionEventCompat.AXIS_GENERIC_15 /* 46 */:
                if (i5 == 2) {
                    zzfw zzfwVar3 = (zzfw) zzffVarZzap;
                    int iZza7 = zzdl.zza(bArr, iZza2, zzdkVar);
                    int i13 = zzdkVar.zzada + iZza7;
                    while (iZza7 < i13) {
                        zzfwVar3.zzby(zzdl.zzb(bArr, iZza7));
                        iZza7 += 8;
                    }
                    if (iZza7 == i13) {
                        return iZza7;
                    }
                    throw zzfi.zzut();
                }
                if (i5 == 1) {
                    zzfw zzfwVar4 = (zzfw) zzffVarZzap;
                    zzfwVar4.zzby(zzdl.zzb(bArr, i));
                    while (true) {
                        int i14 = iZza2 + 8;
                        if (i14 >= i2) {
                            return i14;
                        }
                        iZza2 = zzdl.zza(bArr, i14, zzdkVar);
                        if (i3 != zzdkVar.zzada) {
                            return i14;
                        }
                        zzfwVar4.zzby(zzdl.zzb(bArr, iZza2));
                    }
                }
                return iZza2;
            case MotionEventCompat.AXIS_DISTANCE /* 24 */:
            case 31:
            case MotionEventCompat.AXIS_GENERIC_10 /* 41 */:
            case MotionEventCompat.AXIS_GENERIC_14 /* 45 */:
                if (i5 == 2) {
                    zzfa zzfaVar = (zzfa) zzffVarZzap;
                    int iZza8 = zzdl.zza(bArr, iZza2, zzdkVar);
                    int i15 = zzdkVar.zzada + iZza8;
                    while (iZza8 < i15) {
                        zzfaVar.zzbu(zzdl.zza(bArr, iZza8));
                        iZza8 += 4;
                    }
                    if (iZza8 == i15) {
                        return iZza8;
                    }
                    throw zzfi.zzut();
                }
                if (i5 == 5) {
                    zzfa zzfaVar2 = (zzfa) zzffVarZzap;
                    zzfaVar2.zzbu(zzdl.zza(bArr, i));
                    while (true) {
                        int i16 = iZza2 + 4;
                        if (i16 >= i2) {
                            return i16;
                        }
                        iZza2 = zzdl.zza(bArr, i16, zzdkVar);
                        if (i3 != zzdkVar.zzada) {
                            return i16;
                        }
                        zzfaVar2.zzbu(zzdl.zza(bArr, iZza2));
                    }
                }
                return iZza2;
            case 25:
            case MotionEventCompat.AXIS_GENERIC_11 /* 42 */:
                if (i5 == 2) {
                    zzdn zzdnVar = (zzdn) zzffVarZzap;
                    iZza = zzdl.zza(bArr, iZza2, zzdkVar);
                    int i17 = zzdkVar.zzada + iZza;
                    while (iZza < i17) {
                        iZza = zzdl.zzb(bArr, iZza, zzdkVar);
                        zzdnVar.addBoolean(zzdkVar.zzadb != 0);
                    }
                    if (iZza != i17) {
                        throw zzfi.zzut();
                    }
                    return iZza;
                }
                if (i5 == 0) {
                    zzdn zzdnVar2 = (zzdn) zzffVarZzap;
                    iZza2 = zzdl.zzb(bArr, iZza2, zzdkVar);
                    zzdnVar2.addBoolean(zzdkVar.zzadb != 0);
                    while (iZza2 < i2) {
                        int iZza9 = zzdl.zza(bArr, iZza2, zzdkVar);
                        if (i3 == zzdkVar.zzada) {
                            iZza2 = zzdl.zzb(bArr, iZza9, zzdkVar);
                            zzdnVar2.addBoolean(zzdkVar.zzadb != 0);
                        }
                    }
                }
                return iZza2;
            case MotionEventCompat.AXIS_SCROLL /* 26 */:
                if (i5 == 2) {
                    if ((j & 536870912) == 0) {
                        iZza2 = zzdl.zza(bArr, iZza2, zzdkVar);
                        int i18 = zzdkVar.zzada;
                        if (i18 < 0) {
                            throw zzfi.zzuu();
                        }
                        if (i18 == 0) {
                            zzffVarZzap.add("");
                        } else {
                            zzffVarZzap.add(new String(bArr, iZza2, i18, zzez.UTF_8));
                            iZza2 += i18;
                        }
                        while (iZza2 < i2) {
                            int iZza10 = zzdl.zza(bArr, iZza2, zzdkVar);
                            if (i3 == zzdkVar.zzada) {
                                iZza2 = zzdl.zza(bArr, iZza10, zzdkVar);
                                int i19 = zzdkVar.zzada;
                                if (i19 < 0) {
                                    throw zzfi.zzuu();
                                }
                                if (i19 == 0) {
                                    zzffVarZzap.add("");
                                } else {
                                    zzffVarZzap.add(new String(bArr, iZza2, i19, zzez.UTF_8));
                                    iZza2 += i19;
                                }
                            }
                        }
                    } else {
                        iZza2 = zzdl.zza(bArr, iZza2, zzdkVar);
                        int i20 = zzdkVar.zzada;
                        if (i20 < 0) {
                            throw zzfi.zzuu();
                        }
                        if (i20 == 0) {
                            zzffVarZzap.add("");
                        } else {
                            int i21 = iZza2 + i20;
                            if (!zzhy.zzf(bArr, iZza2, i21)) {
                                throw zzfi.zzvb();
                            }
                            zzffVarZzap.add(new String(bArr, iZza2, i20, zzez.UTF_8));
                            iZza2 = i21;
                        }
                        while (iZza2 < i2) {
                            int iZza11 = zzdl.zza(bArr, iZza2, zzdkVar);
                            if (i3 == zzdkVar.zzada) {
                                iZza2 = zzdl.zza(bArr, iZza11, zzdkVar);
                                int i22 = zzdkVar.zzada;
                                if (i22 < 0) {
                                    throw zzfi.zzuu();
                                }
                                if (i22 == 0) {
                                    zzffVarZzap.add("");
                                } else {
                                    int i23 = iZza2 + i22;
                                    if (!zzhy.zzf(bArr, iZza2, i23)) {
                                        throw zzfi.zzvb();
                                    }
                                    zzffVarZzap.add(new String(bArr, iZza2, i22, zzez.UTF_8));
                                    iZza2 = i23;
                                }
                            }
                        }
                    }
                }
                return iZza2;
            case MotionEventCompat.AXIS_RELATIVE_X /* 27 */:
                if (i5 == 2) {
                    return zzdl.zza(zzbx(i6), i3, bArr, i, i2, zzffVarZzap, zzdkVar);
                }
                return iZza2;
            case MotionEventCompat.AXIS_RELATIVE_Y /* 28 */:
                if (i5 == 2) {
                    int iZza12 = zzdl.zza(bArr, iZza2, zzdkVar);
                    int i24 = zzdkVar.zzada;
                    if (i24 < 0) {
                        throw zzfi.zzuu();
                    }
                    if (i24 > bArr.length - iZza12) {
                        throw zzfi.zzut();
                    }
                    if (i24 == 0) {
                        zzffVarZzap.add(zzdp.zzadh);
                    } else {
                        zzffVarZzap.add(zzdp.zzb(bArr, iZza12, i24));
                        iZza12 += i24;
                    }
                    while (iZza12 < i2) {
                        int iZza13 = zzdl.zza(bArr, iZza12, zzdkVar);
                        if (i3 != zzdkVar.zzada) {
                            return iZza12;
                        }
                        iZza12 = zzdl.zza(bArr, iZza13, zzdkVar);
                        int i25 = zzdkVar.zzada;
                        if (i25 < 0) {
                            throw zzfi.zzuu();
                        }
                        if (i25 > bArr.length - iZza12) {
                            throw zzfi.zzut();
                        }
                        if (i25 == 0) {
                            zzffVarZzap.add(zzdp.zzadh);
                        } else {
                            zzffVarZzap.add(zzdp.zzb(bArr, iZza12, i25));
                            iZza12 += i25;
                        }
                    }
                    return iZza12;
                }
                return iZza2;
            case 30:
            case MotionEventCompat.AXIS_GENERIC_13 /* 44 */:
                if (i5 != 2) {
                    if (i5 == 0) {
                        iZza = zzdl.zza(i3, bArr, i, i2, (zzff<?>) zzffVarZzap, zzdkVar);
                    }
                    return iZza2;
                }
                iZza = zzdl.zza(bArr, iZza2, (zzff<?>) zzffVarZzap, zzdkVar);
                zzey zzeyVar = (zzey) t;
                zzhs zzhsVar = zzeyVar.zzahz;
                if (zzhsVar == zzhs.zzwq()) {
                    zzhsVar = null;
                }
                zzhs zzhsVar2 = (zzhs) zzgz.zza(i4, zzffVarZzap, zzbz(i6), zzhsVar, this.zzakx);
                if (zzhsVar2 != null) {
                    zzeyVar.zzahz = zzhsVar2;
                }
                return iZza;
            case 33:
            case MotionEventCompat.AXIS_GENERIC_16 /* 47 */:
                if (i5 == 2) {
                    zzfa zzfaVar3 = (zzfa) zzffVarZzap;
                    int iZza14 = zzdl.zza(bArr, iZza2, zzdkVar);
                    int i26 = zzdkVar.zzada + iZza14;
                    while (iZza14 < i26) {
                        iZza14 = zzdl.zza(bArr, iZza14, zzdkVar);
                        zzfaVar3.zzbu(zzeb.zzaz(zzdkVar.zzada));
                    }
                    if (iZza14 == i26) {
                        return iZza14;
                    }
                    throw zzfi.zzut();
                }
                if (i5 == 0) {
                    zzfa zzfaVar4 = (zzfa) zzffVarZzap;
                    int iZza15 = zzdl.zza(bArr, iZza2, zzdkVar);
                    zzfaVar4.zzbu(zzeb.zzaz(zzdkVar.zzada));
                    while (iZza15 < i2) {
                        int iZza16 = zzdl.zza(bArr, iZza15, zzdkVar);
                        if (i3 != zzdkVar.zzada) {
                            return iZza15;
                        }
                        iZza15 = zzdl.zza(bArr, iZza16, zzdkVar);
                        zzfaVar4.zzbu(zzeb.zzaz(zzdkVar.zzada));
                    }
                    return iZza15;
                }
                return iZza2;
            case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
            case 48:
                if (i5 == 2) {
                    zzfw zzfwVar5 = (zzfw) zzffVarZzap;
                    int iZza17 = zzdl.zza(bArr, iZza2, zzdkVar);
                    int i27 = zzdkVar.zzada + iZza17;
                    while (iZza17 < i27) {
                        iZza17 = zzdl.zzb(bArr, iZza17, zzdkVar);
                        zzfwVar5.zzby(zzeb.zzbm(zzdkVar.zzadb));
                    }
                    if (iZza17 == i27) {
                        return iZza17;
                    }
                    throw zzfi.zzut();
                }
                if (i5 == 0) {
                    zzfw zzfwVar6 = (zzfw) zzffVarZzap;
                    int iZzb2 = zzdl.zzb(bArr, iZza2, zzdkVar);
                    zzfwVar6.zzby(zzeb.zzbm(zzdkVar.zzadb));
                    while (iZzb2 < i2) {
                        int iZza18 = zzdl.zza(bArr, iZzb2, zzdkVar);
                        if (i3 != zzdkVar.zzada) {
                            return iZzb2;
                        }
                        iZzb2 = zzdl.zzb(bArr, iZza18, zzdkVar);
                        zzfwVar6.zzby(zzeb.zzbm(zzdkVar.zzadb));
                    }
                    return iZzb2;
                }
                return iZza2;
            case 49:
                if (i5 == 3) {
                    zzgx zzgxVarZzbx = zzbx(i6);
                    int i28 = (i3 & (-8)) | 4;
                    iZza2 = zzdl.zza(zzgxVarZzbx, bArr, i, i2, i28, zzdkVar);
                    zzffVarZzap.add(zzdkVar.zzadc);
                    while (iZza2 < i2) {
                        int iZza19 = zzdl.zza(bArr, iZza2, zzdkVar);
                        if (i3 == zzdkVar.zzada) {
                            iZza2 = zzdl.zza(zzgxVarZzbx, bArr, iZza19, i2, i28, zzdkVar);
                            zzffVarZzap.add(zzdkVar.zzadc);
                        }
                    }
                }
                return iZza2;
            default:
                return iZza2;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private final <K, V> int zza(T t, byte[] bArr, int i, int i2, int i3, long j, zzdk zzdkVar) throws IOException {
        Unsafe unsafe = zzaki;
        Object objZzby = zzby(i3);
        Object object = unsafe.getObject(t, j);
        if (this.zzakz.zzo(object)) {
            Object objZzq = this.zzakz.zzq(objZzby);
            this.zzakz.zzb(objZzq, object);
            unsafe.putObject(t, j, objZzq);
            object = objZzq;
        }
        zzfz<?, ?> zzfzVarZzr = this.zzakz.zzr(objZzby);
        Map<?, ?> mapZzm = this.zzakz.zzm(object);
        int iZza = zzdl.zza(bArr, i, zzdkVar);
        int i4 = zzdkVar.zzada;
        if (i4 < 0 || i4 > i2 - iZza) {
            throw zzfi.zzut();
        }
        int i5 = i4 + iZza;
        K k = zzfzVarZzr.zzakc;
        V v = zzfzVarZzr.zzaba;
        while (iZza < i5) {
            int iZza2 = iZza + 1;
            int i6 = bArr[iZza];
            if (i6 < 0) {
                iZza2 = zzdl.zza(i6, bArr, iZza2, zzdkVar);
                i6 = zzdkVar.zzada;
            }
            int i7 = iZza2;
            int i8 = i6 >>> 3;
            int i9 = i6 & 7;
            if (i8 != 1) {
                if (i8 == 2 && i9 == zzfzVarZzr.zzakd.zzxa()) {
                    iZza = zza(bArr, i7, i2, zzfzVarZzr.zzakd, zzfzVarZzr.zzaba.getClass(), zzdkVar);
                    v = zzdkVar.zzadc;
                } else {
                    iZza = zzdl.zza(i6, bArr, i7, i2, zzdkVar);
                }
            } else if (i9 == zzfzVarZzr.zzakb.zzxa()) {
                iZza = zza(bArr, i7, i2, zzfzVarZzr.zzakb, (Class<?>) null, zzdkVar);
                k = (K) zzdkVar.zzadc;
            } else {
                iZza = zzdl.zza(i6, bArr, i7, i2, zzdkVar);
            }
        }
        if (iZza != i5) {
            throw zzfi.zzva();
        }
        mapZzm.put(k, v);
        return i5;
    }

    private static int zza(byte[] bArr, int i, int i2, zzig zzigVar, Class<?> cls, zzdk zzdkVar) throws IOException {
        switch (zzgl.zzaee[zzigVar.ordinal()]) {
            case 1:
                int iZzb = zzdl.zzb(bArr, i, zzdkVar);
                zzdkVar.zzadc = Boolean.valueOf(zzdkVar.zzadb != 0);
                return iZzb;
            case 2:
                return zzdl.zze(bArr, i, zzdkVar);
            case 3:
                zzdkVar.zzadc = Double.valueOf(zzdl.zzc(bArr, i));
                return i + 8;
            case 4:
            case 5:
                zzdkVar.zzadc = Integer.valueOf(zzdl.zza(bArr, i));
                return i + 4;
            case 6:
            case 7:
                zzdkVar.zzadc = Long.valueOf(zzdl.zzb(bArr, i));
                return i + 8;
            case 8:
                zzdkVar.zzadc = Float.valueOf(zzdl.zzd(bArr, i));
                return i + 4;
            case 9:
            case 10:
            case 11:
                int iZza = zzdl.zza(bArr, i, zzdkVar);
                zzdkVar.zzadc = Integer.valueOf(zzdkVar.zzada);
                return iZza;
            case 12:
            case 13:
                int iZzb2 = zzdl.zzb(bArr, i, zzdkVar);
                zzdkVar.zzadc = Long.valueOf(zzdkVar.zzadb);
                return iZzb2;
            case 14:
                return zzdl.zza(zzgt.zzvy().zzf(cls), bArr, i, i2, zzdkVar);
            case 15:
                int iZza2 = zzdl.zza(bArr, i, zzdkVar);
                zzdkVar.zzadc = Integer.valueOf(zzeb.zzaz(zzdkVar.zzada));
                return iZza2;
            case 16:
                int iZzb3 = zzdl.zzb(bArr, i, zzdkVar);
                zzdkVar.zzadc = Long.valueOf(zzeb.zzbm(zzdkVar.zzadb));
                return iZzb3;
            case 17:
                return zzdl.zzd(bArr, i, zzdkVar);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    /* JADX WARN: Code duplicated, block: B:172:0x0379  */
    /* JADX WARN: Code duplicated, block: B:186:0x03c9  */
    /* JADX WARN: Code duplicated, block: B:189:0x03d7  */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static <T> zzgm<T> zza(Class<T> cls, zzgg zzggVar, zzgq zzgqVar, zzfs zzfsVar, zzhp<?, ?> zzhpVar, zzen<?> zzenVar, zzgb zzgbVar) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int iCharAt;
        int i6;
        int iCharAt2;
        int iCharAt3;
        int i7;
        int[] iArr;
        int i8;
        int i9;
        int i10;
        int i11;
        char cCharAt;
        int i12;
        char cCharAt2;
        int i13;
        char cCharAt3;
        int i14;
        char cCharAt4;
        char cCharAt5;
        char cCharAt6;
        char cCharAt7;
        char cCharAt8;
        int i15;
        int i16;
        int i17;
        int i18;
        int iObjectFieldOffset;
        int i19;
        int i20;
        int iObjectFieldOffset2;
        Field fieldZza;
        int i21;
        char cCharAt9;
        int i22;
        int i23;
        Field fieldZza2;
        Field fieldZza3;
        int i24;
        char cCharAt10;
        int i25;
        char cCharAt11;
        int i26;
        char cCharAt12;
        char cCharAt13;
        char cCharAt14;
        if (!(zzggVar instanceof zzgv)) {
            ((zzhm) zzggVar).zzvr();
            int i27 = zzey.zzd.zzaim;
            throw new NoSuchMethodError();
        }
        zzgv zzgvVar = (zzgv) zzggVar;
        int iCharAt4 = 0;
        boolean z = zzgvVar.zzvr() == zzey.zzd.zzaim;
        String strZzvz = zzgvVar.zzvz();
        int length = strZzvz.length();
        int iCharAt5 = strZzvz.charAt(0);
        if (iCharAt5 >= 55296) {
            int i28 = iCharAt5 & 8191;
            int i29 = 1;
            int i30 = 13;
            while (true) {
                i = i29 + 1;
                cCharAt14 = strZzvz.charAt(i29);
                if (cCharAt14 < 55296) {
                    break;
                }
                i28 |= (cCharAt14 & 8191) << i30;
                i30 += 13;
                i29 = i;
            }
            iCharAt5 = (cCharAt14 << i30) | i28;
        } else {
            i = 1;
        }
        int i31 = i + 1;
        int iCharAt6 = strZzvz.charAt(i);
        if (iCharAt6 >= 55296) {
            int i32 = iCharAt6 & 8191;
            int i33 = 13;
            while (true) {
                i2 = i31 + 1;
                cCharAt13 = strZzvz.charAt(i31);
                if (cCharAt13 < 55296) {
                    break;
                }
                i32 |= (cCharAt13 & 8191) << i33;
                i33 += 13;
                i31 = i2;
            }
            iCharAt6 = i32 | (cCharAt13 << i33);
        } else {
            i2 = i31;
        }
        if (iCharAt6 == 0) {
            iArr = zzakh;
            iCharAt3 = 0;
            i10 = 0;
            i8 = 0;
            iCharAt = 0;
            iCharAt2 = 0;
            i9 = 0;
        } else {
            int i34 = i2 + 1;
            int iCharAt7 = strZzvz.charAt(i2);
            if (iCharAt7 >= 55296) {
                int i35 = iCharAt7 & 8191;
                int i36 = 13;
                while (true) {
                    i3 = i34 + 1;
                    cCharAt8 = strZzvz.charAt(i34);
                    if (cCharAt8 < 55296) {
                        break;
                    }
                    i35 |= (cCharAt8 & 8191) << i36;
                    i36 += 13;
                    i34 = i3;
                }
                iCharAt7 = (cCharAt8 << i36) | i35;
            } else {
                i3 = i34;
            }
            int i37 = i3 + 1;
            int iCharAt8 = strZzvz.charAt(i3);
            if (iCharAt8 >= 55296) {
                int i38 = iCharAt8 & 8191;
                int i39 = 13;
                while (true) {
                    i4 = i37 + 1;
                    cCharAt7 = strZzvz.charAt(i37);
                    if (cCharAt7 < 55296) {
                        break;
                    }
                    i38 |= (cCharAt7 & 8191) << i39;
                    i39 += 13;
                    i37 = i4;
                }
                iCharAt8 = i38 | (cCharAt7 << i39);
            } else {
                i4 = i37;
            }
            int i40 = i4 + 1;
            int iCharAt9 = strZzvz.charAt(i4);
            if (iCharAt9 >= 55296) {
                int i41 = iCharAt9 & 8191;
                int i42 = 13;
                while (true) {
                    i5 = i40 + 1;
                    cCharAt6 = strZzvz.charAt(i40);
                    if (cCharAt6 < 55296) {
                        break;
                    }
                    i41 |= (cCharAt6 & 8191) << i42;
                    i42 += 13;
                    i40 = i5;
                }
                iCharAt9 = (cCharAt6 << i42) | i41;
            } else {
                i5 = i40;
            }
            int i43 = i5 + 1;
            iCharAt = strZzvz.charAt(i5);
            if (iCharAt >= 55296) {
                int i44 = iCharAt & 8191;
                int i45 = 13;
                while (true) {
                    i6 = i43 + 1;
                    cCharAt5 = strZzvz.charAt(i43);
                    if (cCharAt5 < 55296) {
                        break;
                    }
                    i44 |= (cCharAt5 & 8191) << i45;
                    i45 += 13;
                    i43 = i6;
                }
                iCharAt = (cCharAt5 << i45) | i44;
            } else {
                i6 = i43;
            }
            int i46 = i6 + 1;
            iCharAt2 = strZzvz.charAt(i6);
            if (iCharAt2 >= 55296) {
                int i47 = iCharAt2 & 8191;
                int i48 = 13;
                while (true) {
                    i14 = i46 + 1;
                    cCharAt4 = strZzvz.charAt(i46);
                    if (cCharAt4 < 55296) {
                        break;
                    }
                    i47 |= (cCharAt4 & 8191) << i48;
                    i48 += 13;
                    i46 = i14;
                }
                iCharAt2 = (cCharAt4 << i48) | i47;
                i46 = i14;
            }
            int i49 = i46 + 1;
            iCharAt3 = strZzvz.charAt(i46);
            if (iCharAt3 >= 55296) {
                int i50 = iCharAt3 & 8191;
                int i51 = 13;
                while (true) {
                    i13 = i49 + 1;
                    cCharAt3 = strZzvz.charAt(i49);
                    if (cCharAt3 < 55296) {
                        break;
                    }
                    i50 |= (cCharAt3 & 8191) << i51;
                    i51 += 13;
                    i49 = i13;
                }
                iCharAt3 = i50 | (cCharAt3 << i51);
                i49 = i13;
            }
            int i52 = i49 + 1;
            int iCharAt10 = strZzvz.charAt(i49);
            if (iCharAt10 >= 55296) {
                int i53 = 13;
                int i54 = iCharAt10 & 8191;
                int i55 = i52;
                while (true) {
                    i12 = i55 + 1;
                    cCharAt2 = strZzvz.charAt(i55);
                    if (cCharAt2 < 55296) {
                        break;
                    }
                    i54 |= (cCharAt2 & 8191) << i53;
                    i53 += 13;
                    i55 = i12;
                }
                iCharAt10 = i54 | (cCharAt2 << i53);
                i7 = i12;
            } else {
                i7 = i52;
            }
            int i56 = i7 + 1;
            iCharAt4 = strZzvz.charAt(i7);
            if (iCharAt4 >= 55296) {
                int i57 = 13;
                int i58 = iCharAt4 & 8191;
                int i59 = i56;
                while (true) {
                    i11 = i59 + 1;
                    cCharAt = strZzvz.charAt(i59);
                    if (cCharAt < 55296) {
                        break;
                    }
                    i58 |= (cCharAt & 8191) << i57;
                    i57 += 13;
                    i59 = i11;
                }
                iCharAt4 = i58 | (cCharAt << i57);
                i56 = i11;
            }
            iArr = new int[iCharAt4 + iCharAt3 + iCharAt10];
            i8 = (iCharAt7 << 1) + iCharAt8;
            int i60 = i56;
            i9 = iCharAt7;
            i10 = iCharAt9;
            i2 = i60;
        }
        Unsafe unsafe = zzaki;
        Object[] objArrZzwa = zzgvVar.zzwa();
        Class<?> cls2 = zzgvVar.zzvt().getClass();
        int i61 = i8;
        int[] iArr2 = new int[iCharAt2 * 3];
        Object[] objArr = new Object[iCharAt2 << 1];
        int i62 = iCharAt4 + iCharAt3;
        int i63 = iCharAt4;
        int i64 = i62;
        int i65 = 0;
        int i66 = 0;
        while (i2 < length) {
            int i67 = i2 + 1;
            int iCharAt11 = strZzvz.charAt(i2);
            char c = 55296;
            if (iCharAt11 >= 55296) {
                int i68 = 13;
                int i69 = iCharAt11 & 8191;
                int i70 = i67;
                while (true) {
                    i26 = i70 + 1;
                    cCharAt12 = strZzvz.charAt(i70);
                    if (cCharAt12 < c) {
                        break;
                    }
                    i69 |= (cCharAt12 & 8191) << i68;
                    i68 += 13;
                    i70 = i26;
                    c = 55296;
                }
                iCharAt11 = i69 | (cCharAt12 << i68);
                i15 = i26;
            } else {
                i15 = i67;
            }
            int i71 = i15 + 1;
            int iCharAt12 = strZzvz.charAt(i15);
            int i72 = length;
            char c2 = 55296;
            if (iCharAt12 >= 55296) {
                int i73 = 13;
                int i74 = iCharAt12 & 8191;
                int i75 = i71;
                while (true) {
                    i25 = i75 + 1;
                    cCharAt11 = strZzvz.charAt(i75);
                    if (cCharAt11 < c2) {
                        break;
                    }
                    i74 |= (cCharAt11 & 8191) << i73;
                    i73 += 13;
                    i75 = i25;
                    c2 = 55296;
                }
                iCharAt12 = i74 | (cCharAt11 << i73);
                i16 = i25;
            } else {
                i16 = i71;
            }
            int i76 = iCharAt4;
            int i77 = iCharAt12 & 255;
            boolean z2 = z;
            if ((iCharAt12 & 1024) != 0) {
                iArr[i65] = i66;
                i65++;
            }
            int i78 = i65;
            if (i77 >= 51) {
                int i79 = i16 + 1;
                int iCharAt13 = strZzvz.charAt(i16);
                char c3 = 55296;
                if (iCharAt13 >= 55296) {
                    int i80 = iCharAt13 & 8191;
                    int i81 = 13;
                    while (true) {
                        i24 = i79 + 1;
                        cCharAt10 = strZzvz.charAt(i79);
                        if (cCharAt10 < c3) {
                            break;
                        }
                        i80 |= (cCharAt10 & 8191) << i81;
                        i81 += 13;
                        i79 = i24;
                        c3 = 55296;
                    }
                    iCharAt13 = i80 | (cCharAt10 << i81);
                    i79 = i24;
                }
                int i82 = i77 - 51;
                int i83 = i79;
                if (i82 == 9 || i82 == 17) {
                    i23 = 1;
                    objArr[((i66 / 3) << 1) + 1] = objArrZzwa[i61];
                    i61++;
                } else {
                    if (i82 == 12 && (iCharAt5 & 1) == 1) {
                        objArr[((i66 / 3) << 1) + 1] = objArrZzwa[i61];
                        i61++;
                    }
                    i23 = 1;
                }
                int i84 = iCharAt13 << i23;
                Object obj = objArrZzwa[i84];
                if (obj instanceof Field) {
                    fieldZza2 = (Field) obj;
                } else {
                    fieldZza2 = zza(cls2, (String) obj);
                    objArrZzwa[i84] = fieldZza2;
                }
                int i85 = i10;
                int iObjectFieldOffset3 = (int) unsafe.objectFieldOffset(fieldZza2);
                int i86 = i84 + 1;
                Object obj2 = objArrZzwa[i86];
                if (obj2 instanceof Field) {
                    fieldZza3 = (Field) obj2;
                } else {
                    fieldZza3 = zza(cls2, (String) obj2);
                    objArrZzwa[i86] = fieldZza3;
                }
                strZzvz = strZzvz;
                iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZza3);
                cls2 = cls2;
                i17 = i61;
                iObjectFieldOffset = iObjectFieldOffset3;
                i20 = 0;
                i18 = i85;
                iCharAt11 = iCharAt11;
                i2 = i83;
            } else {
                int i87 = i10;
                i17 = i61 + 1;
                Field fieldZza4 = zza(cls2, (String) objArrZzwa[i61]);
                if (i77 == 9 || i77 == 17) {
                    i18 = i87;
                    objArr[((i66 / 3) << 1) + 1] = fieldZza4.getType();
                } else {
                    if (i77 == 27 || i77 == 49) {
                        i18 = i87;
                        i22 = i17 + 1;
                        objArr[((i66 / 3) << 1) + 1] = objArrZzwa[i17];
                    } else if (i77 == 12 || i77 == 30 || i77 == 44) {
                        i18 = i87;
                        if ((iCharAt5 & 1) == 1) {
                            i22 = i17 + 1;
                            objArr[((i66 / 3) << 1) + 1] = objArrZzwa[i17];
                        }
                        iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZza4);
                        if ((iCharAt5 & 1) == 1 || i77 > 17) {
                            i19 = i16;
                            i20 = 0;
                            iObjectFieldOffset2 = 0;
                        } else {
                            i19 = i16 + 1;
                            int iCharAt14 = strZzvz.charAt(i16);
                            if (iCharAt14 >= 55296) {
                                int i88 = iCharAt14 & 8191;
                                int i89 = 13;
                                while (true) {
                                    i21 = i19 + 1;
                                    cCharAt9 = strZzvz.charAt(i19);
                                    if (cCharAt9 < 55296) {
                                        break;
                                    }
                                    i88 |= (cCharAt9 & 8191) << i89;
                                    i89 += 13;
                                    i19 = i21;
                                }
                                iCharAt14 = i88 | (cCharAt9 << i89);
                                i19 = i21;
                            }
                            int i90 = (i9 << 1) + (iCharAt14 / 32);
                            Object obj3 = objArrZzwa[i90];
                            if (obj3 instanceof Field) {
                                fieldZza = (Field) obj3;
                            } else {
                                fieldZza = zza(cls2, (String) obj3);
                                objArrZzwa[i90] = fieldZza;
                            }
                            iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZza);
                            i20 = iCharAt14 % 32;
                        }
                        if (i77 >= 18 && i77 <= 49) {
                            iArr[i64] = iObjectFieldOffset;
                            i64++;
                        }
                        i2 = i19;
                    } else if (i77 == 50) {
                        int i91 = i63 + 1;
                        iArr[i63] = i66;
                        int i92 = (i66 / 3) << 1;
                        int i93 = i17 + 1;
                        objArr[i92] = objArrZzwa[i17];
                        if ((iCharAt12 & 2048) != 0) {
                            i17 = i93 + 1;
                            objArr[i92 + 1] = objArrZzwa[i93];
                            i18 = i87;
                            i63 = i91;
                        } else {
                            i63 = i91;
                            i17 = i93;
                            i18 = i87;
                        }
                    } else {
                        i18 = i87;
                    }
                    i17 = i22;
                    iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZza4);
                    if ((iCharAt5 & 1) == 1) {
                        i19 = i16;
                        i20 = 0;
                        iObjectFieldOffset2 = 0;
                    } else {
                        i19 = i16;
                        i20 = 0;
                        iObjectFieldOffset2 = 0;
                    }
                    if (i77 >= 18) {
                        iArr[i64] = iObjectFieldOffset;
                        i64++;
                    }
                    i2 = i19;
                }
                iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZza4);
                if ((iCharAt5 & 1) == 1) {
                    i19 = i16;
                    i20 = 0;
                    iObjectFieldOffset2 = 0;
                } else {
                    i19 = i16;
                    i20 = 0;
                    iObjectFieldOffset2 = 0;
                }
                if (i77 >= 18) {
                    iArr[i64] = iObjectFieldOffset;
                    i64++;
                }
                i2 = i19;
            }
            int i94 = i66 + 1;
            iArr2[i66] = iCharAt11;
            int i95 = i94 + 1;
            iArr2[i94] = (i77 << 20) | ((iCharAt12 & 256) != 0 ? 268435456 : 0) | ((iCharAt12 & 512) != 0 ? 536870912 : 0) | iObjectFieldOffset;
            i66 = i95 + 1;
            iArr2[i95] = (i20 << 20) | iObjectFieldOffset2;
            cls2 = cls2;
            iCharAt = iCharAt;
            iCharAt4 = i76;
            i61 = i17;
            length = i72;
            z = z2;
            i10 = i18;
            i65 = i78;
            strZzvz = strZzvz;
        }
        return new zzgm<>(iArr2, objArr, i10, iCharAt, zzgvVar.zzvt(), z, false, iArr, iCharAt4, i62, zzgqVar, zzfsVar, zzhpVar, zzenVar, zzgbVar);
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzfe zzfeVar, UB ub, zzhp<UT, UB> zzhpVar) {
        zzfz<?, ?> zzfzVarZzr = this.zzakz.zzr(zzby(i));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (!zzfeVar.zzg(((Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = zzhpVar.zzwp();
                }
                zzdx zzdxVarZzas = zzdp.zzas(zzga.zza(zzfzVarZzr, next.getKey(), next.getValue()));
                try {
                    zzga.zza(zzdxVarZzas.zzsf(), zzfzVarZzr, next.getKey(), next.getValue());
                    zzhpVar.zza(ub, i2, zzdxVarZzas.zzse());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    private final <UT, UB> UB zza(Object obj, int i, UB ub, zzhp<UT, UB> zzhpVar) {
        zzfe zzfeVarZzbz;
        int i2 = this.zzakj[i];
        Object objZzp = zzhv.zzp(obj, zzca(i) & 1048575);
        return (objZzp == null || (zzfeVarZzbz = zzbz(i)) == null) ? ub : (UB) zza(i, i2, this.zzakz.zzm(objZzp), zzfeVarZzbz, ub, zzhpVar);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static Field zza(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String string = Arrays.toString(declaredFields);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(name).length() + String.valueOf(string).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(string);
            throw new RuntimeException(sb.toString());
        }
    }

    private static void zza(int i, Object obj, zzim zzimVar) throws IOException {
        if (obj instanceof String) {
            zzimVar.zzb(i, (String) obj);
        } else {
            zzimVar.zza(i, (zzdp) obj);
        }
    }

    private static <UT, UB> void zza(zzhp<UT, UB> zzhpVar, T t, zzim zzimVar) throws IOException {
        zzhpVar.zza(zzhpVar.zzx(t), zzimVar);
    }

    private final <K, V> void zza(zzim zzimVar, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzimVar.zza(i, this.zzakz.zzr(zzby(i2)), this.zzakz.zzn(obj));
        }
    }

    private final void zza(Object obj, int i, zzgy zzgyVar) throws IOException {
        if (zzcc(i)) {
            zzhv.zza(obj, i & 1048575, zzgyVar.zzsn());
        } else if (this.zzakp) {
            zzhv.zza(obj, i & 1048575, zzgyVar.readString());
        } else {
            zzhv.zza(obj, i & 1048575, zzgyVar.zzso());
        }
    }

    private final void zza(T t, T t2, int i) {
        long jZzca = zzca(i) & 1048575;
        if (zza(t2, i)) {
            Object objZzp = zzhv.zzp(t, jZzca);
            Object objZzp2 = zzhv.zzp(t2, jZzca);
            if (objZzp != null && objZzp2 != null) {
                zzhv.zza(t, jZzca, zzez.zza(objZzp, objZzp2));
                zzb(t, i);
            } else if (objZzp2 != null) {
                zzhv.zza(t, jZzca, objZzp2);
                zzb(t, i);
            }
        }
    }

    private final boolean zza(T t, int i) {
        if (!this.zzakq) {
            int iZzcb = zzcb(i);
            return (zzhv.zzk(t, (long) (iZzcb & 1048575)) & (1 << (iZzcb >>> 20))) != 0;
        }
        int iZzca = zzca(i);
        long j = iZzca & 1048575;
        switch ((iZzca & 267386880) >>> 20) {
            case 0:
                return zzhv.zzo(t, j) != 0.0d;
            case 1:
                return zzhv.zzn(t, j) != 0.0f;
            case 2:
                return zzhv.zzl(t, j) != 0;
            case 3:
                return zzhv.zzl(t, j) != 0;
            case 4:
                return zzhv.zzk(t, j) != 0;
            case 5:
                return zzhv.zzl(t, j) != 0;
            case 6:
                return zzhv.zzk(t, j) != 0;
            case 7:
                return zzhv.zzm(t, j);
            case 8:
                Object objZzp = zzhv.zzp(t, j);
                if (objZzp instanceof String) {
                    return !((String) objZzp).isEmpty();
                }
                if (objZzp instanceof zzdp) {
                    return !zzdp.zzadh.equals(objZzp);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzhv.zzp(t, j) != null;
            case 10:
                return !zzdp.zzadh.equals(zzhv.zzp(t, j));
            case 11:
                return zzhv.zzk(t, j) != 0;
            case 12:
                return zzhv.zzk(t, j) != 0;
            case 13:
                return zzhv.zzk(t, j) != 0;
            case 14:
                return zzhv.zzl(t, j) != 0;
            case 15:
                return zzhv.zzk(t, j) != 0;
            case 16:
                return zzhv.zzl(t, j) != 0;
            case 17:
                return zzhv.zzp(t, j) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zza(T t, int i, int i2) {
        return zzhv.zzk(t, (long) (zzcb(i2) & 1048575)) == i;
    }

    private final boolean zza(T t, int i, int i2, int i3) {
        if (this.zzakq) {
            return zza(t, i);
        }
        return (i2 & i3) != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zza(Object obj, int i, zzgx zzgxVar) {
        return zzgxVar.zzv(zzhv.zzp(obj, i & 1048575));
    }

    private final void zzb(T t, int i) {
        if (this.zzakq) {
            return;
        }
        int iZzcb = zzcb(i);
        long j = iZzcb & 1048575;
        zzhv.zzb(t, j, zzhv.zzk(t, j) | (1 << (iZzcb >>> 20)));
    }

    private final void zzb(T t, int i, int i2) {
        zzhv.zzb(t, zzcb(i2) & 1048575, i);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0023  */
    private final void zzb(T t, zzim zzimVar) throws IOException {
        Iterator it;
        Map.Entry<?, ?> entry;
        int i;
        if (this.zzako) {
            zzeo<T> zzeoVarZzh = this.zzaky.zzh(t);
            if (zzeoVarZzh.zzaex.isEmpty()) {
                it = null;
                entry = null;
            } else {
                it = zzeoVarZzh.iterator();
                entry = (Map.Entry) it.next();
            }
        } else {
            it = null;
            entry = null;
        }
        int i2 = -1;
        int length = this.zzakj.length;
        Unsafe unsafe = zzaki;
        Map.Entry<?, ?> entry2 = entry;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4 += 3) {
            int iZzca = zzca(i4);
            int[] iArr = this.zzakj;
            int i5 = iArr[i4];
            int i6 = (267386880 & iZzca) >>> 20;
            if (this.zzakq || i6 > 17) {
                entry2 = entry2;
                i = 0;
            } else {
                int i7 = iArr[i4 + 2];
                int i8 = i7 & 1048575;
                if (i8 != i2) {
                    i3 = unsafe.getInt(t, i8);
                } else {
                    i8 = i2;
                }
                i = 1 << (i7 >>> 20);
                i2 = i8;
                entry2 = entry2;
            }
            while (entry2 != null && this.zzaky.zza(entry2) <= i5) {
                this.zzaky.zza(zzimVar, entry2);
                entry2 = it.hasNext() ? (Map.Entry) it.next() : null;
            }
            long j = iZzca & 1048575;
            switch (i6) {
                case 0:
                    if ((i3 & i) != 0) {
                        zzimVar.zza(i5, zzhv.zzo(t, j));
                        continue;
                    }
                    break;
                case 1:
                    if ((i3 & i) != 0) {
                        zzimVar.zza(i5, zzhv.zzn(t, j));
                    } else {
                        continue;
                    }
                    break;
                case 2:
                    if ((i3 & i) != 0) {
                        zzimVar.zzi(i5, unsafe.getLong(t, j));
                    } else {
                        continue;
                    }
                    break;
                case 3:
                    if ((i3 & i) != 0) {
                        zzimVar.zza(i5, unsafe.getLong(t, j));
                    } else {
                        continue;
                    }
                    break;
                case 4:
                    if ((i3 & i) != 0) {
                        zzimVar.zzc(i5, unsafe.getInt(t, j));
                    } else {
                        continue;
                    }
                    break;
                case 5:
                    if ((i3 & i) != 0) {
                        zzimVar.zzc(i5, unsafe.getLong(t, j));
                    } else {
                        continue;
                    }
                    break;
                case 6:
                    if ((i3 & i) != 0) {
                        zzimVar.zzf(i5, unsafe.getInt(t, j));
                    } else {
                        continue;
                    }
                    break;
                case 7:
                    if ((i3 & i) != 0) {
                        zzimVar.zzb(i5, zzhv.zzm(t, j));
                    } else {
                        continue;
                    }
                    break;
                case 8:
                    if ((i3 & i) != 0) {
                        zza(i5, unsafe.getObject(t, j), zzimVar);
                    } else {
                        continue;
                    }
                    break;
                case 9:
                    if ((i3 & i) != 0) {
                        zzimVar.zza(i5, unsafe.getObject(t, j), zzbx(i4));
                    } else {
                        continue;
                    }
                    break;
                case 10:
                    if ((i3 & i) != 0) {
                        zzimVar.zza(i5, (zzdp) unsafe.getObject(t, j));
                    } else {
                        continue;
                    }
                    break;
                case 11:
                    if ((i3 & i) != 0) {
                        zzimVar.zzd(i5, unsafe.getInt(t, j));
                    } else {
                        continue;
                    }
                    break;
                case 12:
                    if ((i3 & i) != 0) {
                        zzimVar.zzn(i5, unsafe.getInt(t, j));
                    } else {
                        continue;
                    }
                    break;
                case 13:
                    if ((i3 & i) != 0) {
                        zzimVar.zzm(i5, unsafe.getInt(t, j));
                    } else {
                        continue;
                    }
                    break;
                case 14:
                    if ((i3 & i) != 0) {
                        zzimVar.zzj(i5, unsafe.getLong(t, j));
                    } else {
                        continue;
                    }
                    break;
                case 15:
                    if ((i3 & i) != 0) {
                        zzimVar.zze(i5, unsafe.getInt(t, j));
                    } else {
                        continue;
                    }
                    break;
                case 16:
                    if ((i3 & i) != 0) {
                        zzimVar.zzb(i5, unsafe.getLong(t, j));
                    } else {
                        continue;
                    }
                    break;
                case 17:
                    if ((i3 & i) != 0) {
                        zzimVar.zzb(i5, unsafe.getObject(t, j), zzbx(i4));
                    } else {
                        continue;
                    }
                    break;
                case 18:
                    zzgz.zza(this.zzakj[i4], (List<Double>) unsafe.getObject(t, j), zzimVar, false);
                    continue;
                    break;
                case 19:
                    zzgz.zzb(this.zzakj[i4], (List<Float>) unsafe.getObject(t, j), zzimVar, false);
                    continue;
                    break;
                case 20:
                    zzgz.zzc(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    continue;
                    break;
                case MotionEventCompat.AXIS_WHEEL /* 21 */:
                    zzgz.zzd(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    continue;
                    break;
                case MotionEventCompat.AXIS_GAS /* 22 */:
                    zzgz.zzh(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    continue;
                    break;
                case MotionEventCompat.AXIS_BRAKE /* 23 */:
                    zzgz.zzf(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    continue;
                    break;
                case MotionEventCompat.AXIS_DISTANCE /* 24 */:
                    zzgz.zzk(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    continue;
                    break;
                case 25:
                    zzgz.zzn(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    continue;
                    break;
                case MotionEventCompat.AXIS_SCROLL /* 26 */:
                    zzgz.zza(this.zzakj[i4], (List<String>) unsafe.getObject(t, j), zzimVar);
                    break;
                case MotionEventCompat.AXIS_RELATIVE_X /* 27 */:
                    zzgz.zza(this.zzakj[i4], (List<?>) unsafe.getObject(t, j), zzimVar, zzbx(i4));
                    break;
                case MotionEventCompat.AXIS_RELATIVE_Y /* 28 */:
                    zzgz.zzb(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar);
                    break;
                case 29:
                    zzgz.zzi(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 30:
                    zzgz.zzm(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 31:
                    zzgz.zzl(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 32:
                    zzgz.zzg(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 33:
                    zzgz.zzj(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
                    zzgz.zze(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case MotionEventCompat.AXIS_GENERIC_4 /* 35 */:
                    zzgz.zza(this.zzakj[i4], (List<Double>) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_5 /* 36 */:
                    zzgz.zzb(this.zzakj[i4], (List<Float>) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_6 /* 37 */:
                    zzgz.zzc(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_7 /* 38 */:
                    zzgz.zzd(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
                    zzgz.zzh(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_9 /* 40 */:
                    zzgz.zzf(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_10 /* 41 */:
                    zzgz.zzk(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_11 /* 42 */:
                    zzgz.zzn(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_12 /* 43 */:
                    zzgz.zzi(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_13 /* 44 */:
                    zzgz.zzm(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_14 /* 45 */:
                    zzgz.zzl(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_15 /* 46 */:
                    zzgz.zzg(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_16 /* 47 */:
                    zzgz.zzj(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case 48:
                    zzgz.zze(this.zzakj[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case 49:
                    zzgz.zzb(this.zzakj[i4], (List<?>) unsafe.getObject(t, j), zzimVar, zzbx(i4));
                    break;
                case 50:
                    zza(zzimVar, i5, unsafe.getObject(t, j), i4);
                    break;
                case 51:
                    if (zza(t, i5, i4)) {
                        zzimVar.zza(i5, zzf(t, j));
                    }
                    break;
                case 52:
                    if (zza(t, i5, i4)) {
                        zzimVar.zza(i5, zzg(t, j));
                    }
                    break;
                case 53:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzi(i5, zzi(t, j));
                    }
                    break;
                case 54:
                    if (zza(t, i5, i4)) {
                        zzimVar.zza(i5, zzi(t, j));
                    }
                    break;
                case 55:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzc(i5, zzh(t, j));
                    }
                    break;
                case 56:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzc(i5, zzi(t, j));
                    }
                    break;
                case 57:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzf(i5, zzh(t, j));
                    }
                    break;
                case 58:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzb(i5, zzj(t, j));
                    }
                    break;
                case 59:
                    if (zza(t, i5, i4)) {
                        zza(i5, unsafe.getObject(t, j), zzimVar);
                    }
                    break;
                case 60:
                    if (zza(t, i5, i4)) {
                        zzimVar.zza(i5, unsafe.getObject(t, j), zzbx(i4));
                    }
                    break;
                case 61:
                    if (zza(t, i5, i4)) {
                        zzimVar.zza(i5, (zzdp) unsafe.getObject(t, j));
                    }
                    break;
                case 62:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzd(i5, zzh(t, j));
                    }
                    break;
                case 63:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzn(i5, zzh(t, j));
                    }
                    break;
                case 64:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzm(i5, zzh(t, j));
                    }
                    break;
                case 65:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzj(i5, zzi(t, j));
                    }
                    break;
                case 66:
                    if (zza(t, i5, i4)) {
                        zzimVar.zze(i5, zzh(t, j));
                    }
                    break;
                case 67:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzb(i5, zzi(t, j));
                    }
                    break;
                case 68:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzb(i5, unsafe.getObject(t, j), zzbx(i4));
                    }
                    break;
            }
        }
        Map.Entry<?, ?> entry3 = entry2;
        while (entry3 != null) {
            this.zzaky.zza(zzimVar, entry3);
            entry3 = it.hasNext() ? (Map.Entry) it.next() : null;
        }
        zza(this.zzakx, t, zzimVar);
    }

    private final void zzb(T t, T t2, int i) {
        int iZzca = zzca(i);
        int i2 = this.zzakj[i];
        long j = iZzca & 1048575;
        if (zza(t2, i2, i)) {
            Object objZzp = zzhv.zzp(t, j);
            Object objZzp2 = zzhv.zzp(t2, j);
            if (objZzp != null && objZzp2 != null) {
                zzhv.zza(t, j, zzez.zza(objZzp, objZzp2));
                zzb(t, i2, i);
            } else if (objZzp2 != null) {
                zzhv.zza(t, j, objZzp2);
                zzb(t, i2, i);
            }
        }
    }

    private final zzgx zzbx(int i) {
        int i2 = (i / 3) << 1;
        zzgx zzgxVar = (zzgx) this.zzakk[i2];
        if (zzgxVar != null) {
            return zzgxVar;
        }
        zzgx<T> zzgxVarZzf = zzgt.zzvy().zzf((Class) this.zzakk[i2 + 1]);
        this.zzakk[i2] = zzgxVarZzf;
        return zzgxVarZzf;
    }

    private final Object zzby(int i) {
        return this.zzakk[(i / 3) << 1];
    }

    private final zzfe zzbz(int i) {
        return (zzfe) this.zzakk[((i / 3) << 1) + 1];
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza(t, i) == zza(t2, i);
    }

    private final int zzca(int i) {
        return this.zzakj[i + 1];
    }

    private final int zzcb(int i) {
        return this.zzakj[i + 2];
    }

    private static boolean zzcc(int i) {
        return (i & 536870912) != 0;
    }

    private final int zzcd(int i) {
        if (i < this.zzakl || i > this.zzakm) {
            return -1;
        }
        return zzq(i, 0);
    }

    private static List<?> zze(Object obj, long j) {
        return (List) zzhv.zzp(obj, j);
    }

    private static <T> double zzf(T t, long j) {
        return ((Double) zzhv.zzp(t, j)).doubleValue();
    }

    private static <T> float zzg(T t, long j) {
        return ((Float) zzhv.zzp(t, j)).floatValue();
    }

    private static <T> int zzh(T t, long j) {
        return ((Integer) zzhv.zzp(t, j)).intValue();
    }

    private static <T> long zzi(T t, long j) {
        return ((Long) zzhv.zzp(t, j)).longValue();
    }

    private static <T> boolean zzj(T t, long j) {
        return ((Boolean) zzhv.zzp(t, j)).booleanValue();
    }

    private final int zzp(int i, int i2) {
        if (i < this.zzakl || i > this.zzakm) {
            return -1;
        }
        return zzq(i, i2);
    }

    private final int zzq(int i, int i2) {
        int length = (this.zzakj.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = this.zzakj[i4];
            if (i == i5) {
                return i4;
            }
            if (i < i5) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }

    private static zzhs zzu(Object obj) {
        zzey zzeyVar = (zzey) obj;
        zzhs zzhsVar = zzeyVar.zzahz;
        if (zzhsVar != zzhs.zzwq()) {
            return zzhsVar;
        }
        zzhs zzhsVarZzwr = zzhs.zzwr();
        zzeyVar.zzahz = zzhsVarZzwr;
        return zzhsVarZzwr;
    }

    /* JADX WARN: Code duplicated, block: B:104:0x01c1  */
    @Override // com.google.android.gms.internal.measurement.zzgx
    public final boolean equals(T t, T t2) {
        int length = this.zzakj.length;
        int i = 0;
        while (true) {
            boolean zZzd = true;
            if (i >= length) {
                if (!this.zzakx.zzx(t).equals(this.zzakx.zzx(t2))) {
                    return false;
                }
                if (this.zzako) {
                    return this.zzaky.zzh(t).equals(this.zzaky.zzh(t2));
                }
                return true;
            }
            int iZzca = zzca(i);
            long j = iZzca & 1048575;
            switch ((iZzca & 267386880) >>> 20) {
                case 0:
                    if (!zzc(t, t2, i) || Double.doubleToLongBits(zzhv.zzo(t, j)) != Double.doubleToLongBits(zzhv.zzo(t2, j))) {
                        zZzd = false;
                    }
                    break;
                case 1:
                    if (!zzc(t, t2, i) || Float.floatToIntBits(zzhv.zzn(t, j)) != Float.floatToIntBits(zzhv.zzn(t2, j))) {
                        zZzd = false;
                    }
                    break;
                case 2:
                    if (!zzc(t, t2, i) || zzhv.zzl(t, j) != zzhv.zzl(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 3:
                    if (!zzc(t, t2, i) || zzhv.zzl(t, j) != zzhv.zzl(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 4:
                    if (!zzc(t, t2, i) || zzhv.zzk(t, j) != zzhv.zzk(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 5:
                    if (!zzc(t, t2, i) || zzhv.zzl(t, j) != zzhv.zzl(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 6:
                    if (!zzc(t, t2, i) || zzhv.zzk(t, j) != zzhv.zzk(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 7:
                    if (!zzc(t, t2, i) || zzhv.zzm(t, j) != zzhv.zzm(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 8:
                    if (!zzc(t, t2, i) || !zzgz.zzd(zzhv.zzp(t, j), zzhv.zzp(t2, j))) {
                        zZzd = false;
                    }
                    break;
                case 9:
                    if (!zzc(t, t2, i) || !zzgz.zzd(zzhv.zzp(t, j), zzhv.zzp(t2, j))) {
                        zZzd = false;
                    }
                    break;
                case 10:
                    if (!zzc(t, t2, i) || !zzgz.zzd(zzhv.zzp(t, j), zzhv.zzp(t2, j))) {
                        zZzd = false;
                    }
                    break;
                case 11:
                    if (!zzc(t, t2, i) || zzhv.zzk(t, j) != zzhv.zzk(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 12:
                    if (!zzc(t, t2, i) || zzhv.zzk(t, j) != zzhv.zzk(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 13:
                    if (!zzc(t, t2, i) || zzhv.zzk(t, j) != zzhv.zzk(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 14:
                    if (!zzc(t, t2, i) || zzhv.zzl(t, j) != zzhv.zzl(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 15:
                    if (!zzc(t, t2, i) || zzhv.zzk(t, j) != zzhv.zzk(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 16:
                    if (!zzc(t, t2, i) || zzhv.zzl(t, j) != zzhv.zzl(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 17:
                    if (!zzc(t, t2, i) || !zzgz.zzd(zzhv.zzp(t, j), zzhv.zzp(t2, j))) {
                        zZzd = false;
                    }
                    break;
                case 18:
                case 19:
                case 20:
                case MotionEventCompat.AXIS_WHEEL /* 21 */:
                case MotionEventCompat.AXIS_GAS /* 22 */:
                case MotionEventCompat.AXIS_BRAKE /* 23 */:
                case MotionEventCompat.AXIS_DISTANCE /* 24 */:
                case 25:
                case MotionEventCompat.AXIS_SCROLL /* 26 */:
                case MotionEventCompat.AXIS_RELATIVE_X /* 27 */:
                case MotionEventCompat.AXIS_RELATIVE_Y /* 28 */:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
                case MotionEventCompat.AXIS_GENERIC_4 /* 35 */:
                case MotionEventCompat.AXIS_GENERIC_5 /* 36 */:
                case MotionEventCompat.AXIS_GENERIC_6 /* 37 */:
                case MotionEventCompat.AXIS_GENERIC_7 /* 38 */:
                case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
                case MotionEventCompat.AXIS_GENERIC_9 /* 40 */:
                case MotionEventCompat.AXIS_GENERIC_10 /* 41 */:
                case MotionEventCompat.AXIS_GENERIC_11 /* 42 */:
                case MotionEventCompat.AXIS_GENERIC_12 /* 43 */:
                case MotionEventCompat.AXIS_GENERIC_13 /* 44 */:
                case MotionEventCompat.AXIS_GENERIC_14 /* 45 */:
                case MotionEventCompat.AXIS_GENERIC_15 /* 46 */:
                case MotionEventCompat.AXIS_GENERIC_16 /* 47 */:
                case 48:
                case 49:
                    zZzd = zzgz.zzd(zzhv.zzp(t, j), zzhv.zzp(t2, j));
                    break;
                case 50:
                    zZzd = zzgz.zzd(zzhv.zzp(t, j), zzhv.zzp(t2, j));
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                    long jZzcb = zzcb(i) & 1048575;
                    if (zzhv.zzk(t, jZzcb) != zzhv.zzk(t2, jZzcb) || !zzgz.zzd(zzhv.zzp(t, j), zzhv.zzp(t2, j))) {
                        zZzd = false;
                    }
                    break;
            }
            if (!zZzd) {
                return false;
            }
            i += 3;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzgx
    public final int hashCode(T t) {
        int i;
        int iZzbx;
        int length = this.zzakj.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int iZzca = zzca(i3);
            int i4 = this.zzakj[i3];
            long j = 1048575 & iZzca;
            int iHashCode = 37;
            switch ((iZzca & 267386880) >>> 20) {
                case 0:
                    i = i2 * 53;
                    iZzbx = zzez.zzbx(Double.doubleToLongBits(zzhv.zzo(t, j)));
                    i2 = i + iZzbx;
                    break;
                case 1:
                    i = i2 * 53;
                    iZzbx = Float.floatToIntBits(zzhv.zzn(t, j));
                    i2 = i + iZzbx;
                    break;
                case 2:
                    i = i2 * 53;
                    iZzbx = zzez.zzbx(zzhv.zzl(t, j));
                    i2 = i + iZzbx;
                    break;
                case 3:
                    i = i2 * 53;
                    iZzbx = zzez.zzbx(zzhv.zzl(t, j));
                    i2 = i + iZzbx;
                    break;
                case 4:
                    i = i2 * 53;
                    iZzbx = zzhv.zzk(t, j);
                    i2 = i + iZzbx;
                    break;
                case 5:
                    i = i2 * 53;
                    iZzbx = zzez.zzbx(zzhv.zzl(t, j));
                    i2 = i + iZzbx;
                    break;
                case 6:
                    i = i2 * 53;
                    iZzbx = zzhv.zzk(t, j);
                    i2 = i + iZzbx;
                    break;
                case 7:
                    i = i2 * 53;
                    iZzbx = zzez.zzs(zzhv.zzm(t, j));
                    i2 = i + iZzbx;
                    break;
                case 8:
                    i = i2 * 53;
                    iZzbx = ((String) zzhv.zzp(t, j)).hashCode();
                    i2 = i + iZzbx;
                    break;
                case 9:
                    Object objZzp = zzhv.zzp(t, j);
                    if (objZzp != null) {
                        iHashCode = objZzp.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 10:
                    i = i2 * 53;
                    iZzbx = zzhv.zzp(t, j).hashCode();
                    i2 = i + iZzbx;
                    break;
                case 11:
                    i = i2 * 53;
                    iZzbx = zzhv.zzk(t, j);
                    i2 = i + iZzbx;
                    break;
                case 12:
                    i = i2 * 53;
                    iZzbx = zzhv.zzk(t, j);
                    i2 = i + iZzbx;
                    break;
                case 13:
                    i = i2 * 53;
                    iZzbx = zzhv.zzk(t, j);
                    i2 = i + iZzbx;
                    break;
                case 14:
                    i = i2 * 53;
                    iZzbx = zzez.zzbx(zzhv.zzl(t, j));
                    i2 = i + iZzbx;
                    break;
                case 15:
                    i = i2 * 53;
                    iZzbx = zzhv.zzk(t, j);
                    i2 = i + iZzbx;
                    break;
                case 16:
                    i = i2 * 53;
                    iZzbx = zzez.zzbx(zzhv.zzl(t, j));
                    i2 = i + iZzbx;
                    break;
                case 17:
                    Object objZzp2 = zzhv.zzp(t, j);
                    if (objZzp2 != null) {
                        iHashCode = objZzp2.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 18:
                case 19:
                case 20:
                case MotionEventCompat.AXIS_WHEEL /* 21 */:
                case MotionEventCompat.AXIS_GAS /* 22 */:
                case MotionEventCompat.AXIS_BRAKE /* 23 */:
                case MotionEventCompat.AXIS_DISTANCE /* 24 */:
                case 25:
                case MotionEventCompat.AXIS_SCROLL /* 26 */:
                case MotionEventCompat.AXIS_RELATIVE_X /* 27 */:
                case MotionEventCompat.AXIS_RELATIVE_Y /* 28 */:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
                case MotionEventCompat.AXIS_GENERIC_4 /* 35 */:
                case MotionEventCompat.AXIS_GENERIC_5 /* 36 */:
                case MotionEventCompat.AXIS_GENERIC_6 /* 37 */:
                case MotionEventCompat.AXIS_GENERIC_7 /* 38 */:
                case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
                case MotionEventCompat.AXIS_GENERIC_9 /* 40 */:
                case MotionEventCompat.AXIS_GENERIC_10 /* 41 */:
                case MotionEventCompat.AXIS_GENERIC_11 /* 42 */:
                case MotionEventCompat.AXIS_GENERIC_12 /* 43 */:
                case MotionEventCompat.AXIS_GENERIC_13 /* 44 */:
                case MotionEventCompat.AXIS_GENERIC_14 /* 45 */:
                case MotionEventCompat.AXIS_GENERIC_15 /* 46 */:
                case MotionEventCompat.AXIS_GENERIC_16 /* 47 */:
                case 48:
                case 49:
                    i = i2 * 53;
                    iZzbx = zzhv.zzp(t, j).hashCode();
                    i2 = i + iZzbx;
                    break;
                case 50:
                    i = i2 * 53;
                    iZzbx = zzhv.zzp(t, j).hashCode();
                    i2 = i + iZzbx;
                    break;
                case 51:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = zzez.zzbx(Double.doubleToLongBits(zzf(t, j)));
                        i2 = i + iZzbx;
                    }
                    break;
                case 52:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = Float.floatToIntBits(zzg(t, j));
                        i2 = i + iZzbx;
                    }
                    break;
                case 53:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = zzez.zzbx(zzi(t, j));
                        i2 = i + iZzbx;
                    }
                    break;
                case 54:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = zzez.zzbx(zzi(t, j));
                        i2 = i + iZzbx;
                    }
                    break;
                case 55:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = zzh(t, j);
                        i2 = i + iZzbx;
                    }
                    break;
                case 56:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = zzez.zzbx(zzi(t, j));
                        i2 = i + iZzbx;
                    }
                    break;
                case 57:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = zzh(t, j);
                        i2 = i + iZzbx;
                    }
                    break;
                case 58:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = zzez.zzs(zzj(t, j));
                        i2 = i + iZzbx;
                    }
                    break;
                case 59:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = ((String) zzhv.zzp(t, j)).hashCode();
                        i2 = i + iZzbx;
                    }
                    break;
                case 60:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = zzhv.zzp(t, j).hashCode();
                        i2 = i + iZzbx;
                    }
                    break;
                case 61:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = zzhv.zzp(t, j).hashCode();
                        i2 = i + iZzbx;
                    }
                    break;
                case 62:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = zzh(t, j);
                        i2 = i + iZzbx;
                    }
                    break;
                case 63:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = zzh(t, j);
                        i2 = i + iZzbx;
                    }
                    break;
                case 64:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = zzh(t, j);
                        i2 = i + iZzbx;
                    }
                    break;
                case 65:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = zzez.zzbx(zzi(t, j));
                        i2 = i + iZzbx;
                    }
                    break;
                case 66:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = zzh(t, j);
                        i2 = i + iZzbx;
                    }
                    break;
                case 67:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = zzez.zzbx(zzi(t, j));
                        i2 = i + iZzbx;
                    }
                    break;
                case 68:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzbx = zzhv.zzp(t, j).hashCode();
                        i2 = i + iZzbx;
                    }
                    break;
            }
        }
        int iHashCode2 = (i2 * 53) + this.zzakx.zzx(t).hashCode();
        return this.zzako ? (iHashCode2 * 53) + this.zzaky.zzh(t).hashCode() : iHashCode2;
    }

    @Override // com.google.android.gms.internal.measurement.zzgx
    public final T newInstance() {
        return (T) this.zzakv.newInstance(this.zzakn);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached with updateSeq = 12701. Try increasing type updates limit count.
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:79)
        */
    final int zza(T r30, byte[] r31, int r32, int r33, int r34, com.google.android.gms.internal.measurement.zzdk r35) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 1270
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzgm.zza(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.measurement.zzdk):int");
    }

    /* JADX WARN: Code duplicated, block: B:165:0x05cb A[LOOP:5: B:163:0x05c7->B:165:0x05cb, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:167:0x05d8  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.internal.measurement.zzgx
    public final void zza(T t, zzgy zzgyVar, zzel zzelVar) throws IOException {
        int i;
        if (zzelVar == null) {
            throw new NullPointerException();
        }
        zzhp<?, ?> zzhpVar = this.zzakx;
        zzen<?> zzenVar = this.zzaky;
        zzeo zzeoVarZzi = null;
        Object objZza = null;
        while (true) {
            try {
                int iZzsy = zzgyVar.zzsy();
                int iZzcd = zzcd(iZzsy);
                if (iZzcd >= 0) {
                    int iZzca = zzca(iZzcd);
                    switch ((267386880 & iZzca) >>> 20) {
                        case 0:
                            zzhv.zza(t, iZzca & 1048575, zzgyVar.readDouble());
                            zzb(t, iZzcd);
                            continue;
                        case 1:
                            zzhv.zza((Object) t, iZzca & 1048575, zzgyVar.readFloat());
                            zzb(t, iZzcd);
                            continue;
                        case 2:
                            zzhv.zza((Object) t, iZzca & 1048575, zzgyVar.zzsi());
                            zzb(t, iZzcd);
                            continue;
                        case 3:
                            zzhv.zza((Object) t, iZzca & 1048575, zzgyVar.zzsh());
                            zzb(t, iZzcd);
                            continue;
                        case 4:
                            zzhv.zzb(t, iZzca & 1048575, zzgyVar.zzsj());
                            zzb(t, iZzcd);
                            continue;
                        case 5:
                            zzhv.zza((Object) t, iZzca & 1048575, zzgyVar.zzsk());
                            zzb(t, iZzcd);
                            continue;
                        case 6:
                            zzhv.zzb(t, iZzca & 1048575, zzgyVar.zzsl());
                            zzb(t, iZzcd);
                            continue;
                        case 7:
                            zzhv.zza(t, iZzca & 1048575, zzgyVar.zzsm());
                            zzb(t, iZzcd);
                            continue;
                        case 8:
                            zza(t, iZzca, zzgyVar);
                            zzb(t, iZzcd);
                            continue;
                        case 9:
                            if (zza(t, iZzcd)) {
                                long j = iZzca & 1048575;
                                zzhv.zza(t, j, zzez.zza(zzhv.zzp(t, j), zzgyVar.zza(zzbx(iZzcd), zzelVar)));
                            } else {
                                zzhv.zza(t, iZzca & 1048575, zzgyVar.zza(zzbx(iZzcd), zzelVar));
                                zzb(t, iZzcd);
                                continue;
                            }
                            break;
                        case 10:
                            zzhv.zza(t, iZzca & 1048575, zzgyVar.zzso());
                            zzb(t, iZzcd);
                            continue;
                        case 11:
                            zzhv.zzb(t, iZzca & 1048575, zzgyVar.zzsp());
                            zzb(t, iZzcd);
                            continue;
                        case 12:
                            int iZzsq = zzgyVar.zzsq();
                            zzfe zzfeVarZzbz = zzbz(iZzcd);
                            if (zzfeVarZzbz == null || zzfeVarZzbz.zzg(iZzsq)) {
                                zzhv.zzb(t, iZzca & 1048575, iZzsq);
                                zzb(t, iZzcd);
                                continue;
                            } else {
                                objZza = zzgz.zza(iZzsy, iZzsq, objZza, (zzhp<UT, Object>) zzhpVar);
                            }
                            break;
                        case 13:
                            zzhv.zzb(t, iZzca & 1048575, zzgyVar.zzsr());
                            zzb(t, iZzcd);
                            continue;
                        case 14:
                            zzhv.zza((Object) t, iZzca & 1048575, zzgyVar.zzss());
                            zzb(t, iZzcd);
                            continue;
                        case 15:
                            zzhv.zzb(t, iZzca & 1048575, zzgyVar.zzst());
                            zzb(t, iZzcd);
                            continue;
                        case 16:
                            zzhv.zza((Object) t, iZzca & 1048575, zzgyVar.zzsu());
                            zzb(t, iZzcd);
                            continue;
                        case 17:
                            if (zza(t, iZzcd)) {
                                long j2 = iZzca & 1048575;
                                zzhv.zza(t, j2, zzez.zza(zzhv.zzp(t, j2), zzgyVar.zzb(zzbx(iZzcd), zzelVar)));
                            } else {
                                zzhv.zza(t, iZzca & 1048575, zzgyVar.zzb(zzbx(iZzcd), zzelVar));
                                zzb(t, iZzcd);
                                continue;
                            }
                            break;
                        case 18:
                            zzgyVar.zze(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case 19:
                            zzgyVar.zzf(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case 20:
                            zzgyVar.zzh(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_WHEEL /* 21 */:
                            zzgyVar.zzg(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_GAS /* 22 */:
                            zzgyVar.zzi(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_BRAKE /* 23 */:
                            zzgyVar.zzj(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_DISTANCE /* 24 */:
                            zzgyVar.zzk(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case 25:
                            zzgyVar.zzl(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_SCROLL /* 26 */:
                            if (zzcc(iZzca)) {
                                zzgyVar.zzm(this.zzakw.zza(t, iZzca & 1048575));
                            } else {
                                zzgyVar.readStringList(this.zzakw.zza(t, iZzca & 1048575));
                                continue;
                            }
                            break;
                        case MotionEventCompat.AXIS_RELATIVE_X /* 27 */:
                            zzgyVar.zza(this.zzakw.zza(t, iZzca & 1048575), zzbx(iZzcd), zzelVar);
                            continue;
                        case MotionEventCompat.AXIS_RELATIVE_Y /* 28 */:
                            zzgyVar.zzn(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case 29:
                            zzgyVar.zzo(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case 30:
                            List<Integer> listZza = this.zzakw.zza(t, iZzca & 1048575);
                            zzgyVar.zzp(listZza);
                            objZza = zzgz.zza(iZzsy, listZza, zzbz(iZzcd), objZza, zzhpVar);
                            continue;
                        case 31:
                            zzgyVar.zzq(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case 32:
                            zzgyVar.zzr(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case 33:
                            zzgyVar.zzs(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
                            zzgyVar.zzt(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_GENERIC_4 /* 35 */:
                            zzgyVar.zze(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_GENERIC_5 /* 36 */:
                            zzgyVar.zzf(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_GENERIC_6 /* 37 */:
                            zzgyVar.zzh(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_GENERIC_7 /* 38 */:
                            zzgyVar.zzg(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
                            zzgyVar.zzi(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_GENERIC_9 /* 40 */:
                            zzgyVar.zzj(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_GENERIC_10 /* 41 */:
                            zzgyVar.zzk(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_GENERIC_11 /* 42 */:
                            zzgyVar.zzl(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_GENERIC_12 /* 43 */:
                            zzgyVar.zzo(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_GENERIC_13 /* 44 */:
                            List<Integer> listZza2 = this.zzakw.zza(t, iZzca & 1048575);
                            zzgyVar.zzp(listZza2);
                            objZza = zzgz.zza(iZzsy, listZza2, zzbz(iZzcd), objZza, zzhpVar);
                            continue;
                        case MotionEventCompat.AXIS_GENERIC_14 /* 45 */:
                            zzgyVar.zzq(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_GENERIC_15 /* 46 */:
                            zzgyVar.zzr(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case MotionEventCompat.AXIS_GENERIC_16 /* 47 */:
                            zzgyVar.zzs(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case 48:
                            zzgyVar.zzt(this.zzakw.zza(t, iZzca & 1048575));
                            continue;
                        case 49:
                            zzgyVar.zzb(this.zzakw.zza(t, iZzca & 1048575), zzbx(iZzcd), zzelVar);
                            continue;
                        case 50:
                            Object objZzby = zzby(iZzcd);
                            long jZzca = zzca(iZzcd) & 1048575;
                            Object objZzp = zzhv.zzp(t, jZzca);
                            if (objZzp == null) {
                                objZzp = this.zzakz.zzq(objZzby);
                                zzhv.zza(t, jZzca, objZzp);
                            } else if (this.zzakz.zzo(objZzp)) {
                                Object objZzq = this.zzakz.zzq(objZzby);
                                this.zzakz.zzb(objZzq, objZzp);
                                zzhv.zza(t, jZzca, objZzq);
                                objZzp = objZzq;
                            }
                            zzgyVar.zza(this.zzakz.zzm(objZzp), this.zzakz.zzr(objZzby), zzelVar);
                            continue;
                        case 51:
                            zzhv.zza(t, iZzca & 1048575, Double.valueOf(zzgyVar.readDouble()));
                            zzb(t, iZzsy, iZzcd);
                            continue;
                        case 52:
                            zzhv.zza(t, iZzca & 1048575, Float.valueOf(zzgyVar.readFloat()));
                            zzb(t, iZzsy, iZzcd);
                            continue;
                        case 53:
                            zzhv.zza(t, iZzca & 1048575, Long.valueOf(zzgyVar.zzsi()));
                            zzb(t, iZzsy, iZzcd);
                            continue;
                        case 54:
                            zzhv.zza(t, iZzca & 1048575, Long.valueOf(zzgyVar.zzsh()));
                            zzb(t, iZzsy, iZzcd);
                            continue;
                        case 55:
                            zzhv.zza(t, iZzca & 1048575, Integer.valueOf(zzgyVar.zzsj()));
                            zzb(t, iZzsy, iZzcd);
                            continue;
                        case 56:
                            zzhv.zza(t, iZzca & 1048575, Long.valueOf(zzgyVar.zzsk()));
                            zzb(t, iZzsy, iZzcd);
                            continue;
                        case 57:
                            zzhv.zza(t, iZzca & 1048575, Integer.valueOf(zzgyVar.zzsl()));
                            zzb(t, iZzsy, iZzcd);
                            continue;
                        case 58:
                            zzhv.zza(t, iZzca & 1048575, Boolean.valueOf(zzgyVar.zzsm()));
                            zzb(t, iZzsy, iZzcd);
                            continue;
                        case 59:
                            zza(t, iZzca, zzgyVar);
                            zzb(t, iZzsy, iZzcd);
                            continue;
                        case 60:
                            if (zza(t, iZzsy, iZzcd)) {
                                long j3 = iZzca & 1048575;
                                zzhv.zza(t, j3, zzez.zza(zzhv.zzp(t, j3), zzgyVar.zza(zzbx(iZzcd), zzelVar)));
                            } else {
                                zzhv.zza(t, iZzca & 1048575, zzgyVar.zza(zzbx(iZzcd), zzelVar));
                                zzb(t, iZzcd);
                            }
                            zzb(t, iZzsy, iZzcd);
                            continue;
                        case 61:
                            zzhv.zza(t, iZzca & 1048575, zzgyVar.zzso());
                            zzb(t, iZzsy, iZzcd);
                            continue;
                        case 62:
                            zzhv.zza(t, iZzca & 1048575, Integer.valueOf(zzgyVar.zzsp()));
                            zzb(t, iZzsy, iZzcd);
                            continue;
                        case 63:
                            int iZzsq2 = zzgyVar.zzsq();
                            zzfe zzfeVarZzbz2 = zzbz(iZzcd);
                            if (zzfeVarZzbz2 == null || zzfeVarZzbz2.zzg(iZzsq2)) {
                                zzhv.zza(t, iZzca & 1048575, Integer.valueOf(iZzsq2));
                                zzb(t, iZzsy, iZzcd);
                                continue;
                            } else {
                                objZza = zzgz.zza(iZzsy, iZzsq2, objZza, (zzhp<UT, Object>) zzhpVar);
                            }
                            break;
                        case 64:
                            zzhv.zza(t, iZzca & 1048575, Integer.valueOf(zzgyVar.zzsr()));
                            zzb(t, iZzsy, iZzcd);
                            continue;
                        case 65:
                            zzhv.zza(t, iZzca & 1048575, Long.valueOf(zzgyVar.zzss()));
                            zzb(t, iZzsy, iZzcd);
                            continue;
                        case 66:
                            zzhv.zza(t, iZzca & 1048575, Integer.valueOf(zzgyVar.zzst()));
                            zzb(t, iZzsy, iZzcd);
                            continue;
                        case 67:
                            zzhv.zza(t, iZzca & 1048575, Long.valueOf(zzgyVar.zzsu()));
                            zzb(t, iZzsy, iZzcd);
                            continue;
                        case 68:
                            zzhv.zza(t, iZzca & 1048575, zzgyVar.zzb(zzbx(iZzcd), zzelVar));
                            zzb(t, iZzsy, iZzcd);
                            continue;
                        default:
                            if (objZza == null) {
                                try {
                                    objZza = zzhpVar.zzwp();
                                } catch (zzfh unused) {
                                    zzhpVar.zza(zzgyVar);
                                    if (objZza == null) {
                                        objZza = zzhpVar.zzy(t);
                                    }
                                    if (!zzhpVar.zza((Object) objZza, zzgyVar)) {
                                        for (int i2 = this.zzakt; i2 < this.zzaku; i2++) {
                                            objZza = zza((Object) t, this.zzaks[i2], objZza, (zzhp<UT, Object>) zzhpVar);
                                        }
                                        if (objZza != null) {
                                            zzhpVar.zzf(t, (Object) objZza);
                                            return;
                                        }
                                        return;
                                    }
                                }
                                break;
                            }
                            if (!zzhpVar.zza((Object) objZza, zzgyVar)) {
                                for (int i3 = this.zzakt; i3 < this.zzaku; i3++) {
                                    objZza = zza((Object) t, this.zzaks[i3], objZza, (zzhp<UT, Object>) zzhpVar);
                                }
                                if (objZza != null) {
                                    zzhpVar.zzf(t, (Object) objZza);
                                    return;
                                }
                                return;
                            }
                            break;
                            break;
                    }
                    for (i = this.zzakt; i < this.zzaku; i++) {
                        objZza = zza((Object) t, this.zzaks[i], objZza, (zzhp<UT, Object>) zzhpVar);
                    }
                    if (objZza != null) {
                        zzhpVar.zzf(t, (Object) objZza);
                    }
                    throw th;
                }
                if (iZzsy == Integer.MAX_VALUE) {
                    for (int i4 = this.zzakt; i4 < this.zzaku; i4++) {
                        objZza = zza((Object) t, this.zzaks[i4], objZza, (zzhp<UT, Object>) zzhpVar);
                    }
                    if (objZza != null) {
                        zzhpVar.zzf(t, (Object) objZza);
                        return;
                    }
                    return;
                }
                Object objZza2 = !this.zzako ? null : zzenVar.zza(zzelVar, this.zzakn, iZzsy);
                if (objZza2 != null) {
                    if (zzeoVarZzi == null) {
                        zzeoVarZzi = zzenVar.zzi(t);
                    }
                    zzeo zzeoVar = zzeoVarZzi;
                    objZza = zzenVar.zza(zzgyVar, objZza2, zzelVar, zzeoVar, objZza, zzhpVar);
                    zzeoVarZzi = zzeoVar;
                } else {
                    zzhpVar.zza(zzgyVar);
                    if (objZza == null) {
                        objZza = zzhpVar.zzy(t);
                    }
                    if (!zzhpVar.zza((Object) objZza, zzgyVar)) {
                        for (int i5 = this.zzakt; i5 < this.zzaku; i5++) {
                            objZza = zza((Object) t, this.zzaks[i5], objZza, (zzhp<UT, Object>) zzhpVar);
                        }
                        if (objZza != null) {
                            zzhpVar.zzf(t, (Object) objZza);
                            return;
                        }
                        return;
                    }
                }
            } catch (Throwable th) {
                while (i < this.zzaku) {
                    objZza = zza((Object) t, this.zzaks[i], objZza, (zzhp<UT, Object>) zzhpVar);
                }
                if (objZza != null) {
                    zzhpVar.zzf(t, (Object) objZza);
                }
                throw th;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:178:0x054a  */
    /* JADX WARN: Code duplicated, block: B:9:0x0032  */
    @Override // com.google.android.gms.internal.measurement.zzgx
    public final void zza(T t, zzim zzimVar) throws IOException {
        Iterator it;
        Map.Entry<?, ?> entry;
        Iterator itDescendingIterator;
        Map.Entry<?, ?> entry2;
        if (zzimVar.zztk() == zzey.zzd.zzaip) {
            zza(this.zzakx, t, zzimVar);
            if (this.zzako) {
                zzeo<T> zzeoVarZzh = this.zzaky.zzh(t);
                if (zzeoVarZzh.zzaex.isEmpty()) {
                    itDescendingIterator = null;
                    entry2 = null;
                } else {
                    itDescendingIterator = zzeoVarZzh.descendingIterator();
                    entry2 = (Map.Entry) itDescendingIterator.next();
                }
            } else {
                itDescendingIterator = null;
                entry2 = null;
            }
            for (int length = this.zzakj.length - 3; length >= 0; length -= 3) {
                int iZzca = zzca(length);
                int i = this.zzakj[length];
                while (entry2 != null && this.zzaky.zza(entry2) > i) {
                    this.zzaky.zza(zzimVar, entry2);
                    entry2 = itDescendingIterator.hasNext() ? (Map.Entry) itDescendingIterator.next() : null;
                }
                switch ((iZzca & 267386880) >>> 20) {
                    case 0:
                        if (zza(t, length)) {
                            zzimVar.zza(i, zzhv.zzo(t, iZzca & 1048575));
                        }
                        break;
                    case 1:
                        if (zza(t, length)) {
                            zzimVar.zza(i, zzhv.zzn(t, iZzca & 1048575));
                        }
                        break;
                    case 2:
                        if (zza(t, length)) {
                            zzimVar.zzi(i, zzhv.zzl(t, iZzca & 1048575));
                        }
                        break;
                    case 3:
                        if (zza(t, length)) {
                            zzimVar.zza(i, zzhv.zzl(t, iZzca & 1048575));
                        }
                        break;
                    case 4:
                        if (zza(t, length)) {
                            zzimVar.zzc(i, zzhv.zzk(t, iZzca & 1048575));
                        }
                        break;
                    case 5:
                        if (zza(t, length)) {
                            zzimVar.zzc(i, zzhv.zzl(t, iZzca & 1048575));
                        }
                        break;
                    case 6:
                        if (zza(t, length)) {
                            zzimVar.zzf(i, zzhv.zzk(t, iZzca & 1048575));
                        }
                        break;
                    case 7:
                        if (zza(t, length)) {
                            zzimVar.zzb(i, zzhv.zzm(t, iZzca & 1048575));
                        }
                        break;
                    case 8:
                        if (zza(t, length)) {
                            zza(i, zzhv.zzp(t, iZzca & 1048575), zzimVar);
                        }
                        break;
                    case 9:
                        if (zza(t, length)) {
                            zzimVar.zza(i, zzhv.zzp(t, iZzca & 1048575), zzbx(length));
                        }
                        break;
                    case 10:
                        if (zza(t, length)) {
                            zzimVar.zza(i, (zzdp) zzhv.zzp(t, iZzca & 1048575));
                        }
                        break;
                    case 11:
                        if (zza(t, length)) {
                            zzimVar.zzd(i, zzhv.zzk(t, iZzca & 1048575));
                        }
                        break;
                    case 12:
                        if (zza(t, length)) {
                            zzimVar.zzn(i, zzhv.zzk(t, iZzca & 1048575));
                        }
                        break;
                    case 13:
                        if (zza(t, length)) {
                            zzimVar.zzm(i, zzhv.zzk(t, iZzca & 1048575));
                        }
                        break;
                    case 14:
                        if (zza(t, length)) {
                            zzimVar.zzj(i, zzhv.zzl(t, iZzca & 1048575));
                        }
                        break;
                    case 15:
                        if (zza(t, length)) {
                            zzimVar.zze(i, zzhv.zzk(t, iZzca & 1048575));
                        }
                        break;
                    case 16:
                        if (zza(t, length)) {
                            zzimVar.zzb(i, zzhv.zzl(t, iZzca & 1048575));
                        }
                        break;
                    case 17:
                        if (zza(t, length)) {
                            zzimVar.zzb(i, zzhv.zzp(t, iZzca & 1048575), zzbx(length));
                        }
                        break;
                    case 18:
                        zzgz.zza(this.zzakj[length], (List<Double>) zzhv.zzp(t, iZzca & 1048575), zzimVar, false);
                        break;
                    case 19:
                        zzgz.zzb(this.zzakj[length], (List<Float>) zzhv.zzp(t, iZzca & 1048575), zzimVar, false);
                        break;
                    case 20:
                        zzgz.zzc(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, false);
                        break;
                    case MotionEventCompat.AXIS_WHEEL /* 21 */:
                        zzgz.zzd(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, false);
                        break;
                    case MotionEventCompat.AXIS_GAS /* 22 */:
                        zzgz.zzh(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, false);
                        break;
                    case MotionEventCompat.AXIS_BRAKE /* 23 */:
                        zzgz.zzf(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, false);
                        break;
                    case MotionEventCompat.AXIS_DISTANCE /* 24 */:
                        zzgz.zzk(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, false);
                        break;
                    case 25:
                        zzgz.zzn(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, false);
                        break;
                    case MotionEventCompat.AXIS_SCROLL /* 26 */:
                        zzgz.zza(this.zzakj[length], (List<String>) zzhv.zzp(t, iZzca & 1048575), zzimVar);
                        break;
                    case MotionEventCompat.AXIS_RELATIVE_X /* 27 */:
                        zzgz.zza(this.zzakj[length], (List<?>) zzhv.zzp(t, iZzca & 1048575), zzimVar, zzbx(length));
                        break;
                    case MotionEventCompat.AXIS_RELATIVE_Y /* 28 */:
                        zzgz.zzb(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar);
                        break;
                    case 29:
                        zzgz.zzi(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, false);
                        break;
                    case 30:
                        zzgz.zzm(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, false);
                        break;
                    case 31:
                        zzgz.zzl(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, false);
                        break;
                    case 32:
                        zzgz.zzg(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, false);
                        break;
                    case 33:
                        zzgz.zzj(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, false);
                        break;
                    case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
                        zzgz.zze(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, false);
                        break;
                    case MotionEventCompat.AXIS_GENERIC_4 /* 35 */:
                        zzgz.zza(this.zzakj[length], (List<Double>) zzhv.zzp(t, iZzca & 1048575), zzimVar, true);
                        break;
                    case MotionEventCompat.AXIS_GENERIC_5 /* 36 */:
                        zzgz.zzb(this.zzakj[length], (List<Float>) zzhv.zzp(t, iZzca & 1048575), zzimVar, true);
                        break;
                    case MotionEventCompat.AXIS_GENERIC_6 /* 37 */:
                        zzgz.zzc(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, true);
                        break;
                    case MotionEventCompat.AXIS_GENERIC_7 /* 38 */:
                        zzgz.zzd(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, true);
                        break;
                    case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
                        zzgz.zzh(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, true);
                        break;
                    case MotionEventCompat.AXIS_GENERIC_9 /* 40 */:
                        zzgz.zzf(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, true);
                        break;
                    case MotionEventCompat.AXIS_GENERIC_10 /* 41 */:
                        zzgz.zzk(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, true);
                        break;
                    case MotionEventCompat.AXIS_GENERIC_11 /* 42 */:
                        zzgz.zzn(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, true);
                        break;
                    case MotionEventCompat.AXIS_GENERIC_12 /* 43 */:
                        zzgz.zzi(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, true);
                        break;
                    case MotionEventCompat.AXIS_GENERIC_13 /* 44 */:
                        zzgz.zzm(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, true);
                        break;
                    case MotionEventCompat.AXIS_GENERIC_14 /* 45 */:
                        zzgz.zzl(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, true);
                        break;
                    case MotionEventCompat.AXIS_GENERIC_15 /* 46 */:
                        zzgz.zzg(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, true);
                        break;
                    case MotionEventCompat.AXIS_GENERIC_16 /* 47 */:
                        zzgz.zzj(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, true);
                        break;
                    case 48:
                        zzgz.zze(this.zzakj[length], (List) zzhv.zzp(t, iZzca & 1048575), zzimVar, true);
                        break;
                    case 49:
                        zzgz.zzb(this.zzakj[length], (List<?>) zzhv.zzp(t, iZzca & 1048575), zzimVar, zzbx(length));
                        break;
                    case 50:
                        zza(zzimVar, i, zzhv.zzp(t, iZzca & 1048575), length);
                        break;
                    case 51:
                        if (zza(t, i, length)) {
                            zzimVar.zza(i, zzf(t, iZzca & 1048575));
                        }
                        break;
                    case 52:
                        if (zza(t, i, length)) {
                            zzimVar.zza(i, zzg(t, iZzca & 1048575));
                        }
                        break;
                    case 53:
                        if (zza(t, i, length)) {
                            zzimVar.zzi(i, zzi(t, iZzca & 1048575));
                        }
                        break;
                    case 54:
                        if (zza(t, i, length)) {
                            zzimVar.zza(i, zzi(t, iZzca & 1048575));
                        }
                        break;
                    case 55:
                        if (zza(t, i, length)) {
                            zzimVar.zzc(i, zzh(t, iZzca & 1048575));
                        }
                        break;
                    case 56:
                        if (zza(t, i, length)) {
                            zzimVar.zzc(i, zzi(t, iZzca & 1048575));
                        }
                        break;
                    case 57:
                        if (zza(t, i, length)) {
                            zzimVar.zzf(i, zzh(t, iZzca & 1048575));
                        }
                        break;
                    case 58:
                        if (zza(t, i, length)) {
                            zzimVar.zzb(i, zzj(t, iZzca & 1048575));
                        }
                        break;
                    case 59:
                        if (zza(t, i, length)) {
                            zza(i, zzhv.zzp(t, iZzca & 1048575), zzimVar);
                        }
                        break;
                    case 60:
                        if (zza(t, i, length)) {
                            zzimVar.zza(i, zzhv.zzp(t, iZzca & 1048575), zzbx(length));
                        }
                        break;
                    case 61:
                        if (zza(t, i, length)) {
                            zzimVar.zza(i, (zzdp) zzhv.zzp(t, iZzca & 1048575));
                        }
                        break;
                    case 62:
                        if (zza(t, i, length)) {
                            zzimVar.zzd(i, zzh(t, iZzca & 1048575));
                        }
                        break;
                    case 63:
                        if (zza(t, i, length)) {
                            zzimVar.zzn(i, zzh(t, iZzca & 1048575));
                        }
                        break;
                    case 64:
                        if (zza(t, i, length)) {
                            zzimVar.zzm(i, zzh(t, iZzca & 1048575));
                        }
                        break;
                    case 65:
                        if (zza(t, i, length)) {
                            zzimVar.zzj(i, zzi(t, iZzca & 1048575));
                        }
                        break;
                    case 66:
                        if (zza(t, i, length)) {
                            zzimVar.zze(i, zzh(t, iZzca & 1048575));
                        }
                        break;
                    case 67:
                        if (zza(t, i, length)) {
                            zzimVar.zzb(i, zzi(t, iZzca & 1048575));
                        }
                        break;
                    case 68:
                        if (zza(t, i, length)) {
                            zzimVar.zzb(i, zzhv.zzp(t, iZzca & 1048575), zzbx(length));
                        }
                        break;
                }
            }
            while (entry2 != null) {
                this.zzaky.zza(zzimVar, entry2);
                entry2 = itDescendingIterator.hasNext() ? (Map.Entry) itDescendingIterator.next() : null;
            }
            return;
        }
        if (!this.zzakq) {
            zzb(t, zzimVar);
            return;
        }
        if (this.zzako) {
            zzeo<T> zzeoVarZzh2 = this.zzaky.zzh(t);
            if (zzeoVarZzh2.zzaex.isEmpty()) {
                it = null;
                entry = null;
            } else {
                it = zzeoVarZzh2.iterator();
                entry = (Map.Entry) it.next();
            }
        } else {
            it = null;
            entry = null;
        }
        int length2 = this.zzakj.length;
        Map.Entry<?, ?> entry3 = entry;
        for (int i2 = 0; i2 < length2; i2 += 3) {
            int iZzca2 = zzca(i2);
            int i3 = this.zzakj[i2];
            while (entry3 != null && this.zzaky.zza(entry3) <= i3) {
                this.zzaky.zza(zzimVar, entry3);
                entry3 = it.hasNext() ? (Map.Entry) it.next() : null;
            }
            switch ((iZzca2 & 267386880) >>> 20) {
                case 0:
                    if (zza(t, i2)) {
                        zzimVar.zza(i3, zzhv.zzo(t, iZzca2 & 1048575));
                    }
                    break;
                case 1:
                    if (zza(t, i2)) {
                        zzimVar.zza(i3, zzhv.zzn(t, iZzca2 & 1048575));
                    }
                    break;
                case 2:
                    if (zza(t, i2)) {
                        zzimVar.zzi(i3, zzhv.zzl(t, iZzca2 & 1048575));
                    }
                    break;
                case 3:
                    if (zza(t, i2)) {
                        zzimVar.zza(i3, zzhv.zzl(t, iZzca2 & 1048575));
                    }
                    break;
                case 4:
                    if (zza(t, i2)) {
                        zzimVar.zzc(i3, zzhv.zzk(t, iZzca2 & 1048575));
                    }
                    break;
                case 5:
                    if (zza(t, i2)) {
                        zzimVar.zzc(i3, zzhv.zzl(t, iZzca2 & 1048575));
                    }
                    break;
                case 6:
                    if (zza(t, i2)) {
                        zzimVar.zzf(i3, zzhv.zzk(t, iZzca2 & 1048575));
                    }
                    break;
                case 7:
                    if (zza(t, i2)) {
                        zzimVar.zzb(i3, zzhv.zzm(t, iZzca2 & 1048575));
                    }
                    break;
                case 8:
                    if (zza(t, i2)) {
                        zza(i3, zzhv.zzp(t, iZzca2 & 1048575), zzimVar);
                    }
                    break;
                case 9:
                    if (zza(t, i2)) {
                        zzimVar.zza(i3, zzhv.zzp(t, iZzca2 & 1048575), zzbx(i2));
                    }
                    break;
                case 10:
                    if (zza(t, i2)) {
                        zzimVar.zza(i3, (zzdp) zzhv.zzp(t, iZzca2 & 1048575));
                    }
                    break;
                case 11:
                    if (zza(t, i2)) {
                        zzimVar.zzd(i3, zzhv.zzk(t, iZzca2 & 1048575));
                    }
                    break;
                case 12:
                    if (zza(t, i2)) {
                        zzimVar.zzn(i3, zzhv.zzk(t, iZzca2 & 1048575));
                    }
                    break;
                case 13:
                    if (zza(t, i2)) {
                        zzimVar.zzm(i3, zzhv.zzk(t, iZzca2 & 1048575));
                    }
                    break;
                case 14:
                    if (zza(t, i2)) {
                        zzimVar.zzj(i3, zzhv.zzl(t, iZzca2 & 1048575));
                    }
                    break;
                case 15:
                    if (zza(t, i2)) {
                        zzimVar.zze(i3, zzhv.zzk(t, iZzca2 & 1048575));
                    }
                    break;
                case 16:
                    if (zza(t, i2)) {
                        zzimVar.zzb(i3, zzhv.zzl(t, iZzca2 & 1048575));
                    }
                    break;
                case 17:
                    if (zza(t, i2)) {
                        zzimVar.zzb(i3, zzhv.zzp(t, iZzca2 & 1048575), zzbx(i2));
                    }
                    break;
                case 18:
                    zzgz.zza(this.zzakj[i2], (List<Double>) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, false);
                    break;
                case 19:
                    zzgz.zzb(this.zzakj[i2], (List<Float>) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, false);
                    break;
                case 20:
                    zzgz.zzc(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, false);
                    break;
                case MotionEventCompat.AXIS_WHEEL /* 21 */:
                    zzgz.zzd(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, false);
                    break;
                case MotionEventCompat.AXIS_GAS /* 22 */:
                    zzgz.zzh(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, false);
                    break;
                case MotionEventCompat.AXIS_BRAKE /* 23 */:
                    zzgz.zzf(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, false);
                    break;
                case MotionEventCompat.AXIS_DISTANCE /* 24 */:
                    zzgz.zzk(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, false);
                    break;
                case 25:
                    zzgz.zzn(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, false);
                    break;
                case MotionEventCompat.AXIS_SCROLL /* 26 */:
                    zzgz.zza(this.zzakj[i2], (List<String>) zzhv.zzp(t, iZzca2 & 1048575), zzimVar);
                    break;
                case MotionEventCompat.AXIS_RELATIVE_X /* 27 */:
                    zzgz.zza(this.zzakj[i2], (List<?>) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, zzbx(i2));
                    break;
                case MotionEventCompat.AXIS_RELATIVE_Y /* 28 */:
                    zzgz.zzb(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar);
                    break;
                case 29:
                    zzgz.zzi(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, false);
                    break;
                case 30:
                    zzgz.zzm(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, false);
                    break;
                case 31:
                    zzgz.zzl(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, false);
                    break;
                case 32:
                    zzgz.zzg(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, false);
                    break;
                case 33:
                    zzgz.zzj(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, false);
                    break;
                case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
                    zzgz.zze(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, false);
                    break;
                case MotionEventCompat.AXIS_GENERIC_4 /* 35 */:
                    zzgz.zza(this.zzakj[i2], (List<Double>) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_5 /* 36 */:
                    zzgz.zzb(this.zzakj[i2], (List<Float>) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_6 /* 37 */:
                    zzgz.zzc(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_7 /* 38 */:
                    zzgz.zzd(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
                    zzgz.zzh(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_9 /* 40 */:
                    zzgz.zzf(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_10 /* 41 */:
                    zzgz.zzk(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_11 /* 42 */:
                    zzgz.zzn(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_12 /* 43 */:
                    zzgz.zzi(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_13 /* 44 */:
                    zzgz.zzm(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_14 /* 45 */:
                    zzgz.zzl(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_15 /* 46 */:
                    zzgz.zzg(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, true);
                    break;
                case MotionEventCompat.AXIS_GENERIC_16 /* 47 */:
                    zzgz.zzj(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, true);
                    break;
                case 48:
                    zzgz.zze(this.zzakj[i2], (List) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, true);
                    break;
                case 49:
                    zzgz.zzb(this.zzakj[i2], (List<?>) zzhv.zzp(t, iZzca2 & 1048575), zzimVar, zzbx(i2));
                    break;
                case 50:
                    zza(zzimVar, i3, zzhv.zzp(t, iZzca2 & 1048575), i2);
                    break;
                case 51:
                    if (zza(t, i3, i2)) {
                        zzimVar.zza(i3, zzf(t, iZzca2 & 1048575));
                    }
                    break;
                case 52:
                    if (zza(t, i3, i2)) {
                        zzimVar.zza(i3, zzg(t, iZzca2 & 1048575));
                    }
                    break;
                case 53:
                    if (zza(t, i3, i2)) {
                        zzimVar.zzi(i3, zzi(t, iZzca2 & 1048575));
                    }
                    break;
                case 54:
                    if (zza(t, i3, i2)) {
                        zzimVar.zza(i3, zzi(t, iZzca2 & 1048575));
                    }
                    break;
                case 55:
                    if (zza(t, i3, i2)) {
                        zzimVar.zzc(i3, zzh(t, iZzca2 & 1048575));
                    }
                    break;
                case 56:
                    if (zza(t, i3, i2)) {
                        zzimVar.zzc(i3, zzi(t, iZzca2 & 1048575));
                    }
                    break;
                case 57:
                    if (zza(t, i3, i2)) {
                        zzimVar.zzf(i3, zzh(t, iZzca2 & 1048575));
                    }
                    break;
                case 58:
                    if (zza(t, i3, i2)) {
                        zzimVar.zzb(i3, zzj(t, iZzca2 & 1048575));
                    }
                    break;
                case 59:
                    if (zza(t, i3, i2)) {
                        zza(i3, zzhv.zzp(t, iZzca2 & 1048575), zzimVar);
                    }
                    break;
                case 60:
                    if (zza(t, i3, i2)) {
                        zzimVar.zza(i3, zzhv.zzp(t, iZzca2 & 1048575), zzbx(i2));
                    }
                    break;
                case 61:
                    if (zza(t, i3, i2)) {
                        zzimVar.zza(i3, (zzdp) zzhv.zzp(t, iZzca2 & 1048575));
                    }
                    break;
                case 62:
                    if (zza(t, i3, i2)) {
                        zzimVar.zzd(i3, zzh(t, iZzca2 & 1048575));
                    }
                    break;
                case 63:
                    if (zza(t, i3, i2)) {
                        zzimVar.zzn(i3, zzh(t, iZzca2 & 1048575));
                    }
                    break;
                case 64:
                    if (zza(t, i3, i2)) {
                        zzimVar.zzm(i3, zzh(t, iZzca2 & 1048575));
                    }
                    break;
                case 65:
                    if (zza(t, i3, i2)) {
                        zzimVar.zzj(i3, zzi(t, iZzca2 & 1048575));
                    }
                    break;
                case 66:
                    if (zza(t, i3, i2)) {
                        zzimVar.zze(i3, zzh(t, iZzca2 & 1048575));
                    }
                    break;
                case 67:
                    if (zza(t, i3, i2)) {
                        zzimVar.zzb(i3, zzi(t, iZzca2 & 1048575));
                    }
                    break;
                case 68:
                    if (zza(t, i3, i2)) {
                        zzimVar.zzb(i3, zzhv.zzp(t, iZzca2 & 1048575), zzbx(i2));
                    }
                    break;
            }
        }
        while (entry3 != null) {
            this.zzaky.zza(zzimVar, entry3);
            entry3 = it.hasNext() ? (Map.Entry) it.next() : null;
        }
        zza(this.zzakx, t, zzimVar);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:87:0x01a4  */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x022e, code lost:
    
        if (r0 == r15) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0230, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x01e2, code lost:
    
        if (r0 == r15) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x020f, code lost:
    
        if (r0 == r15) goto L104;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:20:0x0061. Please report as an issue. */
    @Override // com.google.android.gms.internal.measurement.zzgx
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r28, byte[] r29, int r30, int r31, com.google.android.gms.internal.measurement.zzdk r32) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 662
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzgm.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.measurement.zzdk):void");
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.internal.measurement.zzgx
    public final void zzc(T t, T t2) {
        if (t2 == null) {
            throw new NullPointerException();
        }
        for (int i = 0; i < this.zzakj.length; i += 3) {
            int iZzca = zzca(i);
            long j = 1048575 & iZzca;
            int i2 = this.zzakj[i];
            switch ((iZzca & 267386880) >>> 20) {
                case 0:
                    if (zza(t2, i)) {
                        zzhv.zza(t, j, zzhv.zzo(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 1:
                    if (zza(t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zzn(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 2:
                    if (zza(t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zzl(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 3:
                    if (zza(t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zzl(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 4:
                    if (zza(t2, i)) {
                        zzhv.zzb(t, j, zzhv.zzk(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 5:
                    if (zza(t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zzl(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 6:
                    if (zza(t2, i)) {
                        zzhv.zzb(t, j, zzhv.zzk(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 7:
                    if (zza(t2, i)) {
                        zzhv.zza(t, j, zzhv.zzm(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 8:
                    if (zza(t2, i)) {
                        zzhv.zza(t, j, zzhv.zzp(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 9:
                    zza(t, t2, i);
                    break;
                case 10:
                    if (zza(t2, i)) {
                        zzhv.zza(t, j, zzhv.zzp(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 11:
                    if (zza(t2, i)) {
                        zzhv.zzb(t, j, zzhv.zzk(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 12:
                    if (zza(t2, i)) {
                        zzhv.zzb(t, j, zzhv.zzk(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 13:
                    if (zza(t2, i)) {
                        zzhv.zzb(t, j, zzhv.zzk(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 14:
                    if (zza(t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zzl(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 15:
                    if (zza(t2, i)) {
                        zzhv.zzb(t, j, zzhv.zzk(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 16:
                    if (zza(t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zzl(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 17:
                    zza(t, t2, i);
                    break;
                case 18:
                case 19:
                case 20:
                case MotionEventCompat.AXIS_WHEEL /* 21 */:
                case MotionEventCompat.AXIS_GAS /* 22 */:
                case MotionEventCompat.AXIS_BRAKE /* 23 */:
                case MotionEventCompat.AXIS_DISTANCE /* 24 */:
                case 25:
                case MotionEventCompat.AXIS_SCROLL /* 26 */:
                case MotionEventCompat.AXIS_RELATIVE_X /* 27 */:
                case MotionEventCompat.AXIS_RELATIVE_Y /* 28 */:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
                case MotionEventCompat.AXIS_GENERIC_4 /* 35 */:
                case MotionEventCompat.AXIS_GENERIC_5 /* 36 */:
                case MotionEventCompat.AXIS_GENERIC_6 /* 37 */:
                case MotionEventCompat.AXIS_GENERIC_7 /* 38 */:
                case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
                case MotionEventCompat.AXIS_GENERIC_9 /* 40 */:
                case MotionEventCompat.AXIS_GENERIC_10 /* 41 */:
                case MotionEventCompat.AXIS_GENERIC_11 /* 42 */:
                case MotionEventCompat.AXIS_GENERIC_12 /* 43 */:
                case MotionEventCompat.AXIS_GENERIC_13 /* 44 */:
                case MotionEventCompat.AXIS_GENERIC_14 /* 45 */:
                case MotionEventCompat.AXIS_GENERIC_15 /* 46 */:
                case MotionEventCompat.AXIS_GENERIC_16 /* 47 */:
                case 48:
                case 49:
                    this.zzakw.zza(t, t2, j);
                    break;
                case 50:
                    zzgz.zza(this.zzakz, t, t2, j);
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (zza(t2, i2, i)) {
                        zzhv.zza(t, j, zzhv.zzp(t2, j));
                        zzb(t, i2, i);
                    }
                    break;
                case 60:
                    zzb(t, t2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zza(t2, i2, i)) {
                        zzhv.zza(t, j, zzhv.zzp(t2, j));
                        zzb(t, i2, i);
                    }
                    break;
                case 68:
                    zzb(t, t2, i);
                    break;
            }
        }
        if (this.zzakq) {
            return;
        }
        zzgz.zza(this.zzakx, t, t2);
        if (this.zzako) {
            zzgz.zza(this.zzaky, t, t2);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzgx
    public final void zzj(T t) {
        int i;
        int i2 = this.zzakt;
        while (true) {
            i = this.zzaku;
            if (i2 >= i) {
                break;
            }
            long jZzca = zzca(this.zzaks[i2]) & 1048575;
            Object objZzp = zzhv.zzp(t, jZzca);
            if (objZzp != null) {
                zzhv.zza(t, jZzca, this.zzakz.zzp(objZzp));
            }
            i2++;
        }
        int length = this.zzaks.length;
        while (i < length) {
            this.zzakw.zzb(t, this.zzaks[i]);
            i++;
        }
        this.zzakx.zzj(t);
        if (this.zzako) {
            this.zzaky.zzj(t);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:423:0x0911 A[PHI: r5
      0x0911: PHI (r5v4 int) = 
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v16 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v17 int)
      (r5v1 int)
     binds: [B:257:0x0548, B:461:0x09b5, B:455:0x0999, B:452:0x0987, B:449:0x0978, B:446:0x096b, B:443:0x095e, B:439:0x0953, B:436:0x0948, B:433:0x093b, B:430:0x092e, B:427:0x091b, B:397:0x0822, B:391:0x0805, B:385:0x07e8, B:379:0x07cb, B:373:0x07ad, B:367:0x078f, B:361:0x0771, B:355:0x0753, B:349:0x0735, B:343:0x0717, B:337:0x06f9, B:331:0x06db, B:325:0x06bd, B:319:0x069f, B:314:0x066b, B:311:0x065e, B:308:0x064e, B:305:0x063e, B:302:0x062e, B:299:0x0620, B:296:0x0613, B:293:0x0606, B:287:0x05e8, B:284:0x05d4, B:281:0x05c2, B:278:0x05b2, B:275:0x05a2, B:441:0x095a, B:272:0x0595, B:269:0x0587, B:266:0x0577, B:263:0x0567, B:422:0x0910, B:260:0x0551] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.google.android.gms.internal.measurement.zzgx
    public final int zzt(T t) {
        int i;
        int i2;
        long j;
        int iZzd;
        int iZzc;
        int iZzk;
        int iZzv;
        int iZzac;
        int iZzbi;
        int iZzbk;
        int iZzb;
        int iZzac2;
        int iZzbi2;
        int iZzbk2;
        int i3 = 267386880;
        int i4 = 1048575;
        int i5 = 1;
        if (!this.zzakq) {
            Unsafe unsafe = zzaki;
            int i6 = 0;
            int iZzb2 = 0;
            int i7 = -1;
            int i8 = 0;
            while (i6 < this.zzakj.length) {
                int iZzca = zzca(i6);
                int[] iArr = this.zzakj;
                int i9 = iArr[i6];
                int i10 = (iZzca & 267386880) >>> 20;
                if (i10 <= 17) {
                    i = iArr[i6 + 2];
                    int i11 = i & i4;
                    i2 = i5 << (i >>> 20);
                    if (i11 != i7) {
                        i8 = unsafe.getInt(t, i11);
                    } else {
                        i11 = i7;
                    }
                    i7 = i11;
                } else {
                    i = (!this.zzakr || i10 < zzet.DOUBLE_LIST_PACKED.m439id() || i10 > zzet.SINT64_LIST_PACKED.m439id()) ? 0 : this.zzakj[i6 + 2] & i4;
                    i2 = 0;
                }
                long j2 = iZzca & i4;
                switch (i10) {
                    case 0:
                        j = 0;
                        if ((i8 & i2) != 0) {
                            iZzb2 += zzee.zzb(i9, 0.0d);
                        }
                        break;
                    case 1:
                        j = 0;
                        if ((i8 & i2) != 0) {
                            iZzb2 += zzee.zzb(i9, 0.0f);
                        }
                        break;
                    case 2:
                        j = 0;
                        if ((i8 & i2) != 0) {
                            iZzd = zzee.zzd(i9, unsafe.getLong(t, j2));
                            iZzb2 += iZzd;
                        }
                        break;
                    case 3:
                        j = 0;
                        if ((i8 & i2) != 0) {
                            iZzd = zzee.zze(i9, unsafe.getLong(t, j2));
                            iZzb2 += iZzd;
                        }
                        break;
                    case 4:
                        j = 0;
                        if ((i8 & i2) != 0) {
                            iZzd = zzee.zzg(i9, unsafe.getInt(t, j2));
                            iZzb2 += iZzd;
                        }
                        break;
                    case 5:
                        if ((i8 & i2) != 0) {
                            j = 0;
                            iZzd = zzee.zzg(i9, 0L);
                            iZzb2 += iZzd;
                        } else {
                            j = 0;
                        }
                        break;
                    case 6:
                        if ((i8 & i2) != 0) {
                            iZzb2 += zzee.zzj(i9, 0);
                        }
                        j = 0;
                        break;
                    case 7:
                        if ((i8 & i2) != 0) {
                            iZzb2 += zzee.zzc(i9, true);
                        }
                        j = 0;
                        break;
                    case 8:
                        if ((i8 & i2) != 0) {
                            Object object = unsafe.getObject(t, j2);
                            iZzc = object instanceof zzdp ? zzee.zzc(i9, (zzdp) object) : zzee.zzc(i9, (String) object);
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 9:
                        if ((i8 & i2) != 0) {
                            iZzc = zzgz.zzc(i9, unsafe.getObject(t, j2), zzbx(i6));
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 10:
                        if ((i8 & i2) != 0) {
                            iZzc = zzee.zzc(i9, (zzdp) unsafe.getObject(t, j2));
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 11:
                        if ((i8 & i2) != 0) {
                            iZzc = zzee.zzh(i9, unsafe.getInt(t, j2));
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 12:
                        if ((i8 & i2) != 0) {
                            iZzc = zzee.zzl(i9, unsafe.getInt(t, j2));
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 13:
                        if ((i8 & i2) != 0) {
                            iZzk = zzee.zzk(i9, 0);
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case 14:
                        if ((i8 & i2) != 0) {
                            iZzc = zzee.zzh(i9, 0L);
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 15:
                        if ((i8 & i2) != 0) {
                            iZzc = zzee.zzi(i9, unsafe.getInt(t, j2));
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 16:
                        if ((i8 & i2) != 0) {
                            iZzc = zzee.zzf(i9, unsafe.getLong(t, j2));
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 17:
                        if ((i8 & i2) != 0) {
                            iZzc = zzee.zzc(i9, (zzgi) unsafe.getObject(t, j2), zzbx(i6));
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 18:
                        iZzc = zzgz.zzw(i9, (List) unsafe.getObject(t, j2), false);
                        iZzb2 += iZzc;
                        j = 0;
                        break;
                    case 19:
                        iZzv = zzgz.zzv(i9, (List) unsafe.getObject(t, j2), false);
                        iZzb2 += iZzv;
                        j = 0;
                        break;
                    case 20:
                        iZzv = zzgz.zzo(i9, (List) unsafe.getObject(t, j2), false);
                        iZzb2 += iZzv;
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_WHEEL /* 21 */:
                        iZzv = zzgz.zzp(i9, (List) unsafe.getObject(t, j2), false);
                        iZzb2 += iZzv;
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_GAS /* 22 */:
                        iZzv = zzgz.zzs(i9, (List) unsafe.getObject(t, j2), false);
                        iZzb2 += iZzv;
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_BRAKE /* 23 */:
                        iZzv = zzgz.zzw(i9, (List) unsafe.getObject(t, j2), false);
                        iZzb2 += iZzv;
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_DISTANCE /* 24 */:
                        iZzv = zzgz.zzv(i9, (List) unsafe.getObject(t, j2), false);
                        iZzb2 += iZzv;
                        j = 0;
                        break;
                    case 25:
                        iZzv = zzgz.zzx(i9, (List) unsafe.getObject(t, j2), false);
                        iZzb2 += iZzv;
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_SCROLL /* 26 */:
                        iZzc = zzgz.zzc(i9, (List) unsafe.getObject(t, j2));
                        iZzb2 += iZzc;
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_RELATIVE_X /* 27 */:
                        iZzc = zzgz.zzc(i9, (List<?>) unsafe.getObject(t, j2), zzbx(i6));
                        iZzb2 += iZzc;
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_RELATIVE_Y /* 28 */:
                        iZzc = zzgz.zzd(i9, (List<zzdp>) unsafe.getObject(t, j2));
                        iZzb2 += iZzc;
                        j = 0;
                        break;
                    case 29:
                        iZzc = zzgz.zzt(i9, (List) unsafe.getObject(t, j2), false);
                        iZzb2 += iZzc;
                        j = 0;
                        break;
                    case 30:
                        iZzv = zzgz.zzr(i9, (List) unsafe.getObject(t, j2), false);
                        iZzb2 += iZzv;
                        j = 0;
                        break;
                    case 31:
                        iZzv = zzgz.zzv(i9, (List) unsafe.getObject(t, j2), false);
                        iZzb2 += iZzv;
                        j = 0;
                        break;
                    case 32:
                        iZzv = zzgz.zzw(i9, (List) unsafe.getObject(t, j2), false);
                        iZzb2 += iZzv;
                        j = 0;
                        break;
                    case 33:
                        iZzv = zzgz.zzu(i9, (List) unsafe.getObject(t, j2), false);
                        iZzb2 += iZzv;
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
                        iZzv = zzgz.zzq(i9, (List) unsafe.getObject(t, j2), false);
                        iZzb2 += iZzv;
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_GENERIC_4 /* 35 */:
                        iZzac = zzgz.zzac((List) unsafe.getObject(t, j2));
                        if (iZzac > 0) {
                            if (this.zzakr) {
                                unsafe.putInt(t, i, iZzac);
                            }
                            iZzbi = zzee.zzbi(i9);
                            iZzbk = zzee.zzbk(iZzac);
                            iZzk = iZzbi + iZzbk + iZzac;
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_GENERIC_5 /* 36 */:
                        iZzac = zzgz.zzab((List) unsafe.getObject(t, j2));
                        if (iZzac > 0) {
                            if (this.zzakr) {
                                unsafe.putInt(t, i, iZzac);
                            }
                            iZzbi = zzee.zzbi(i9);
                            iZzbk = zzee.zzbk(iZzac);
                            iZzk = iZzbi + iZzbk + iZzac;
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_GENERIC_6 /* 37 */:
                        iZzac = zzgz.zzu((List) unsafe.getObject(t, j2));
                        if (iZzac > 0) {
                            if (this.zzakr) {
                                unsafe.putInt(t, i, iZzac);
                            }
                            iZzbi = zzee.zzbi(i9);
                            iZzbk = zzee.zzbk(iZzac);
                            iZzk = iZzbi + iZzbk + iZzac;
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_GENERIC_7 /* 38 */:
                        iZzac = zzgz.zzv((List) unsafe.getObject(t, j2));
                        if (iZzac > 0) {
                            if (this.zzakr) {
                                unsafe.putInt(t, i, iZzac);
                            }
                            iZzbi = zzee.zzbi(i9);
                            iZzbk = zzee.zzbk(iZzac);
                            iZzk = iZzbi + iZzbk + iZzac;
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
                        iZzac = zzgz.zzy((List) unsafe.getObject(t, j2));
                        if (iZzac > 0) {
                            if (this.zzakr) {
                                unsafe.putInt(t, i, iZzac);
                            }
                            iZzbi = zzee.zzbi(i9);
                            iZzbk = zzee.zzbk(iZzac);
                            iZzk = iZzbi + iZzbk + iZzac;
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_GENERIC_9 /* 40 */:
                        iZzac = zzgz.zzac((List) unsafe.getObject(t, j2));
                        if (iZzac > 0) {
                            if (this.zzakr) {
                                unsafe.putInt(t, i, iZzac);
                            }
                            iZzbi = zzee.zzbi(i9);
                            iZzbk = zzee.zzbk(iZzac);
                            iZzk = iZzbi + iZzbk + iZzac;
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_GENERIC_10 /* 41 */:
                        iZzac = zzgz.zzab((List) unsafe.getObject(t, j2));
                        if (iZzac > 0) {
                            if (this.zzakr) {
                                unsafe.putInt(t, i, iZzac);
                            }
                            iZzbi = zzee.zzbi(i9);
                            iZzbk = zzee.zzbk(iZzac);
                            iZzk = iZzbi + iZzbk + iZzac;
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_GENERIC_11 /* 42 */:
                        iZzac = zzgz.zzad((List) unsafe.getObject(t, j2));
                        if (iZzac > 0) {
                            if (this.zzakr) {
                                unsafe.putInt(t, i, iZzac);
                            }
                            iZzbi = zzee.zzbi(i9);
                            iZzbk = zzee.zzbk(iZzac);
                            iZzk = iZzbi + iZzbk + iZzac;
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_GENERIC_12 /* 43 */:
                        iZzac = zzgz.zzz((List) unsafe.getObject(t, j2));
                        if (iZzac > 0) {
                            if (this.zzakr) {
                                unsafe.putInt(t, i, iZzac);
                            }
                            iZzbi = zzee.zzbi(i9);
                            iZzbk = zzee.zzbk(iZzac);
                            iZzk = iZzbi + iZzbk + iZzac;
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_GENERIC_13 /* 44 */:
                        iZzac = zzgz.zzx((List) unsafe.getObject(t, j2));
                        if (iZzac > 0) {
                            if (this.zzakr) {
                                unsafe.putInt(t, i, iZzac);
                            }
                            iZzbi = zzee.zzbi(i9);
                            iZzbk = zzee.zzbk(iZzac);
                            iZzk = iZzbi + iZzbk + iZzac;
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_GENERIC_14 /* 45 */:
                        iZzac = zzgz.zzab((List) unsafe.getObject(t, j2));
                        if (iZzac > 0) {
                            if (this.zzakr) {
                                unsafe.putInt(t, i, iZzac);
                            }
                            iZzbi = zzee.zzbi(i9);
                            iZzbk = zzee.zzbk(iZzac);
                            iZzk = iZzbi + iZzbk + iZzac;
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_GENERIC_15 /* 46 */:
                        iZzac = zzgz.zzac((List) unsafe.getObject(t, j2));
                        if (iZzac > 0) {
                            if (this.zzakr) {
                                unsafe.putInt(t, i, iZzac);
                            }
                            iZzbi = zzee.zzbi(i9);
                            iZzbk = zzee.zzbk(iZzac);
                            iZzk = iZzbi + iZzbk + iZzac;
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case MotionEventCompat.AXIS_GENERIC_16 /* 47 */:
                        iZzac = zzgz.zzaa((List) unsafe.getObject(t, j2));
                        if (iZzac > 0) {
                            if (this.zzakr) {
                                unsafe.putInt(t, i, iZzac);
                            }
                            iZzbi = zzee.zzbi(i9);
                            iZzbk = zzee.zzbk(iZzac);
                            iZzk = iZzbi + iZzbk + iZzac;
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case 48:
                        iZzac = zzgz.zzw((List) unsafe.getObject(t, j2));
                        if (iZzac > 0) {
                            if (this.zzakr) {
                                unsafe.putInt(t, i, iZzac);
                            }
                            iZzbi = zzee.zzbi(i9);
                            iZzbk = zzee.zzbk(iZzac);
                            iZzk = iZzbi + iZzbk + iZzac;
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case 49:
                        iZzc = zzgz.zzd(i9, (List) unsafe.getObject(t, j2), zzbx(i6));
                        iZzb2 += iZzc;
                        j = 0;
                        break;
                    case 50:
                        iZzc = this.zzakz.zzb(i9, unsafe.getObject(t, j2), zzby(i6));
                        iZzb2 += iZzc;
                        j = 0;
                        break;
                    case 51:
                        if (zza(t, i9, i6)) {
                            iZzc = zzee.zzb(i9, 0.0d);
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 52:
                        if (zza(t, i9, i6)) {
                            iZzk = zzee.zzb(i9, 0.0f);
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case 53:
                        if (zza(t, i9, i6)) {
                            iZzc = zzee.zzd(i9, zzi(t, j2));
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 54:
                        if (zza(t, i9, i6)) {
                            iZzc = zzee.zze(i9, zzi(t, j2));
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 55:
                        if (zza(t, i9, i6)) {
                            iZzc = zzee.zzg(i9, zzh(t, j2));
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 56:
                        if (zza(t, i9, i6)) {
                            iZzc = zzee.zzg(i9, 0L);
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 57:
                        if (zza(t, i9, i6)) {
                            iZzk = zzee.zzj(i9, 0);
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case 58:
                        if (zza(t, i9, i6)) {
                            iZzk = zzee.zzc(i9, true);
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case 59:
                        if (zza(t, i9, i6)) {
                            Object object2 = unsafe.getObject(t, j2);
                            iZzc = object2 instanceof zzdp ? zzee.zzc(i9, (zzdp) object2) : zzee.zzc(i9, (String) object2);
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 60:
                        if (zza(t, i9, i6)) {
                            iZzc = zzgz.zzc(i9, unsafe.getObject(t, j2), zzbx(i6));
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 61:
                        if (zza(t, i9, i6)) {
                            iZzc = zzee.zzc(i9, (zzdp) unsafe.getObject(t, j2));
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 62:
                        if (zza(t, i9, i6)) {
                            iZzc = zzee.zzh(i9, zzh(t, j2));
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 63:
                        if (zza(t, i9, i6)) {
                            iZzc = zzee.zzl(i9, zzh(t, j2));
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 64:
                        if (zza(t, i9, i6)) {
                            iZzk = zzee.zzk(i9, 0);
                            iZzb2 += iZzk;
                        }
                        j = 0;
                        break;
                    case 65:
                        if (zza(t, i9, i6)) {
                            iZzc = zzee.zzh(i9, 0L);
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 66:
                        if (zza(t, i9, i6)) {
                            iZzc = zzee.zzi(i9, zzh(t, j2));
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 67:
                        if (zza(t, i9, i6)) {
                            iZzc = zzee.zzf(i9, zzi(t, j2));
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    case 68:
                        if (zza(t, i9, i6)) {
                            iZzc = zzee.zzc(i9, (zzgi) unsafe.getObject(t, j2), zzbx(i6));
                            iZzb2 += iZzc;
                        }
                        j = 0;
                        break;
                    default:
                        j = 0;
                        break;
                }
                i6 += 3;
                i4 = 1048575;
                i5 = 1;
            }
            int iZza = iZzb2 + zza(this.zzakx, t);
            if (!this.zzako) {
                return iZza;
            }
            zzeo<T> zzeoVarZzh = this.zzaky.zzh(t);
            int iZzb3 = 0;
            for (int i12 = 0; i12 < zzeoVarZzh.zzaex.zzwh(); i12++) {
                Map.Entry entryZzcf = zzeoVarZzh.zzaex.zzcf(i12);
                iZzb3 += zzeo.zzb((zzeq<?>) entryZzcf.getKey(), entryZzcf.getValue());
            }
            for (Map.Entry entry : zzeoVarZzh.zzaex.zzwi()) {
                iZzb3 += zzeo.zzb((zzeq<?>) entry.getKey(), entry.getValue());
            }
            return iZza + iZzb3;
        }
        Unsafe unsafe2 = zzaki;
        int i13 = 0;
        int i14 = 0;
        while (i13 < this.zzakj.length) {
            int iZzca2 = zzca(i13);
            int i15 = (iZzca2 & i3) >>> 20;
            int i16 = this.zzakj[i13];
            long j3 = iZzca2 & 1048575;
            int i17 = (i15 < zzet.DOUBLE_LIST_PACKED.m439id() || i15 > zzet.SINT64_LIST_PACKED.m439id()) ? 0 : this.zzakj[i13 + 2] & 1048575;
            switch (i15) {
                case 0:
                    if (zza(t, i13)) {
                        iZzb = zzee.zzb(i16, 0.0d);
                        i14 += iZzb;
                    }
                    break;
                case 1:
                    if (zza(t, i13)) {
                        iZzb = zzee.zzb(i16, 0.0f);
                        i14 += iZzb;
                    }
                    break;
                case 2:
                    if (zza(t, i13)) {
                        iZzb = zzee.zzd(i16, zzhv.zzl(t, j3));
                        i14 += iZzb;
                    }
                    break;
                case 3:
                    if (zza(t, i13)) {
                        iZzb = zzee.zze(i16, zzhv.zzl(t, j3));
                        i14 += iZzb;
                    }
                    break;
                case 4:
                    if (zza(t, i13)) {
                        iZzb = zzee.zzg(i16, zzhv.zzk(t, j3));
                        i14 += iZzb;
                    }
                    break;
                case 5:
                    if (zza(t, i13)) {
                        iZzb = zzee.zzg(i16, 0L);
                        i14 += iZzb;
                    }
                    break;
                case 6:
                    if (zza(t, i13)) {
                        iZzb = zzee.zzj(i16, 0);
                        i14 += iZzb;
                    }
                    break;
                case 7:
                    if (zza(t, i13)) {
                        iZzb = zzee.zzc(i16, true);
                        i14 += iZzb;
                    }
                    break;
                case 8:
                    if (zza(t, i13)) {
                        Object objZzp = zzhv.zzp(t, j3);
                        iZzb = objZzp instanceof zzdp ? zzee.zzc(i16, (zzdp) objZzp) : zzee.zzc(i16, (String) objZzp);
                        i14 += iZzb;
                    }
                    break;
                case 9:
                    if (zza(t, i13)) {
                        iZzb = zzgz.zzc(i16, zzhv.zzp(t, j3), zzbx(i13));
                        i14 += iZzb;
                    }
                    break;
                case 10:
                    if (zza(t, i13)) {
                        iZzb = zzee.zzc(i16, (zzdp) zzhv.zzp(t, j3));
                        i14 += iZzb;
                    }
                    break;
                case 11:
                    if (zza(t, i13)) {
                        iZzb = zzee.zzh(i16, zzhv.zzk(t, j3));
                        i14 += iZzb;
                    }
                    break;
                case 12:
                    if (zza(t, i13)) {
                        iZzb = zzee.zzl(i16, zzhv.zzk(t, j3));
                        i14 += iZzb;
                    }
                    break;
                case 13:
                    if (zza(t, i13)) {
                        iZzb = zzee.zzk(i16, 0);
                        i14 += iZzb;
                    }
                    break;
                case 14:
                    if (zza(t, i13)) {
                        iZzb = zzee.zzh(i16, 0L);
                        i14 += iZzb;
                    }
                    break;
                case 15:
                    if (zza(t, i13)) {
                        iZzb = zzee.zzi(i16, zzhv.zzk(t, j3));
                        i14 += iZzb;
                    }
                    break;
                case 16:
                    if (zza(t, i13)) {
                        iZzb = zzee.zzf(i16, zzhv.zzl(t, j3));
                        i14 += iZzb;
                    }
                    break;
                case 17:
                    if (zza(t, i13)) {
                        iZzb = zzee.zzc(i16, (zzgi) zzhv.zzp(t, j3), zzbx(i13));
                        i14 += iZzb;
                    }
                    break;
                case 18:
                    iZzb = zzgz.zzw(i16, zze(t, j3), false);
                    i14 += iZzb;
                    break;
                case 19:
                    iZzb = zzgz.zzv(i16, zze(t, j3), false);
                    i14 += iZzb;
                    break;
                case 20:
                    iZzb = zzgz.zzo(i16, zze(t, j3), false);
                    i14 += iZzb;
                    break;
                case MotionEventCompat.AXIS_WHEEL /* 21 */:
                    iZzb = zzgz.zzp(i16, zze(t, j3), false);
                    i14 += iZzb;
                    break;
                case MotionEventCompat.AXIS_GAS /* 22 */:
                    iZzb = zzgz.zzs(i16, zze(t, j3), false);
                    i14 += iZzb;
                    break;
                case MotionEventCompat.AXIS_BRAKE /* 23 */:
                    iZzb = zzgz.zzw(i16, zze(t, j3), false);
                    i14 += iZzb;
                    break;
                case MotionEventCompat.AXIS_DISTANCE /* 24 */:
                    iZzb = zzgz.zzv(i16, zze(t, j3), false);
                    i14 += iZzb;
                    break;
                case 25:
                    iZzb = zzgz.zzx(i16, zze(t, j3), false);
                    i14 += iZzb;
                    break;
                case MotionEventCompat.AXIS_SCROLL /* 26 */:
                    iZzb = zzgz.zzc(i16, zze(t, j3));
                    i14 += iZzb;
                    break;
                case MotionEventCompat.AXIS_RELATIVE_X /* 27 */:
                    iZzb = zzgz.zzc(i16, zze(t, j3), zzbx(i13));
                    i14 += iZzb;
                    break;
                case MotionEventCompat.AXIS_RELATIVE_Y /* 28 */:
                    iZzb = zzgz.zzd(i16, (List<zzdp>) zze(t, j3));
                    i14 += iZzb;
                    break;
                case 29:
                    iZzb = zzgz.zzt(i16, zze(t, j3), false);
                    i14 += iZzb;
                    break;
                case 30:
                    iZzb = zzgz.zzr(i16, zze(t, j3), false);
                    i14 += iZzb;
                    break;
                case 31:
                    iZzb = zzgz.zzv(i16, zze(t, j3), false);
                    i14 += iZzb;
                    break;
                case 32:
                    iZzb = zzgz.zzw(i16, zze(t, j3), false);
                    i14 += iZzb;
                    break;
                case 33:
                    iZzb = zzgz.zzu(i16, zze(t, j3), false);
                    i14 += iZzb;
                    break;
                case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
                    iZzb = zzgz.zzq(i16, zze(t, j3), false);
                    i14 += iZzb;
                    break;
                case MotionEventCompat.AXIS_GENERIC_4 /* 35 */:
                    iZzac2 = zzgz.zzac((List) unsafe2.getObject(t, j3));
                    if (iZzac2 > 0) {
                        if (this.zzakr) {
                            unsafe2.putInt(t, i17, iZzac2);
                        }
                        iZzbi2 = zzee.zzbi(i16);
                        iZzbk2 = zzee.zzbk(iZzac2);
                        iZzb = iZzbi2 + iZzbk2 + iZzac2;
                        i14 += iZzb;
                    }
                    break;
                case MotionEventCompat.AXIS_GENERIC_5 /* 36 */:
                    iZzac2 = zzgz.zzab((List) unsafe2.getObject(t, j3));
                    if (iZzac2 > 0) {
                        if (this.zzakr) {
                            unsafe2.putInt(t, i17, iZzac2);
                        }
                        iZzbi2 = zzee.zzbi(i16);
                        iZzbk2 = zzee.zzbk(iZzac2);
                        iZzb = iZzbi2 + iZzbk2 + iZzac2;
                        i14 += iZzb;
                    }
                    break;
                case MotionEventCompat.AXIS_GENERIC_6 /* 37 */:
                    iZzac2 = zzgz.zzu((List) unsafe2.getObject(t, j3));
                    if (iZzac2 > 0) {
                        if (this.zzakr) {
                            unsafe2.putInt(t, i17, iZzac2);
                        }
                        iZzbi2 = zzee.zzbi(i16);
                        iZzbk2 = zzee.zzbk(iZzac2);
                        iZzb = iZzbi2 + iZzbk2 + iZzac2;
                        i14 += iZzb;
                    }
                    break;
                case MotionEventCompat.AXIS_GENERIC_7 /* 38 */:
                    iZzac2 = zzgz.zzv((List) unsafe2.getObject(t, j3));
                    if (iZzac2 > 0) {
                        if (this.zzakr) {
                            unsafe2.putInt(t, i17, iZzac2);
                        }
                        iZzbi2 = zzee.zzbi(i16);
                        iZzbk2 = zzee.zzbk(iZzac2);
                        iZzb = iZzbi2 + iZzbk2 + iZzac2;
                        i14 += iZzb;
                    }
                    break;
                case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
                    iZzac2 = zzgz.zzy((List) unsafe2.getObject(t, j3));
                    if (iZzac2 > 0) {
                        if (this.zzakr) {
                            unsafe2.putInt(t, i17, iZzac2);
                        }
                        iZzbi2 = zzee.zzbi(i16);
                        iZzbk2 = zzee.zzbk(iZzac2);
                        iZzb = iZzbi2 + iZzbk2 + iZzac2;
                        i14 += iZzb;
                    }
                    break;
                case MotionEventCompat.AXIS_GENERIC_9 /* 40 */:
                    iZzac2 = zzgz.zzac((List) unsafe2.getObject(t, j3));
                    if (iZzac2 > 0) {
                        if (this.zzakr) {
                            unsafe2.putInt(t, i17, iZzac2);
                        }
                        iZzbi2 = zzee.zzbi(i16);
                        iZzbk2 = zzee.zzbk(iZzac2);
                        iZzb = iZzbi2 + iZzbk2 + iZzac2;
                        i14 += iZzb;
                    }
                    break;
                case MotionEventCompat.AXIS_GENERIC_10 /* 41 */:
                    iZzac2 = zzgz.zzab((List) unsafe2.getObject(t, j3));
                    if (iZzac2 > 0) {
                        if (this.zzakr) {
                            unsafe2.putInt(t, i17, iZzac2);
                        }
                        iZzbi2 = zzee.zzbi(i16);
                        iZzbk2 = zzee.zzbk(iZzac2);
                        iZzb = iZzbi2 + iZzbk2 + iZzac2;
                        i14 += iZzb;
                    }
                    break;
                case MotionEventCompat.AXIS_GENERIC_11 /* 42 */:
                    iZzac2 = zzgz.zzad((List) unsafe2.getObject(t, j3));
                    if (iZzac2 > 0) {
                        if (this.zzakr) {
                            unsafe2.putInt(t, i17, iZzac2);
                        }
                        iZzbi2 = zzee.zzbi(i16);
                        iZzbk2 = zzee.zzbk(iZzac2);
                        iZzb = iZzbi2 + iZzbk2 + iZzac2;
                        i14 += iZzb;
                    }
                    break;
                case MotionEventCompat.AXIS_GENERIC_12 /* 43 */:
                    iZzac2 = zzgz.zzz((List) unsafe2.getObject(t, j3));
                    if (iZzac2 > 0) {
                        if (this.zzakr) {
                            unsafe2.putInt(t, i17, iZzac2);
                        }
                        iZzbi2 = zzee.zzbi(i16);
                        iZzbk2 = zzee.zzbk(iZzac2);
                        iZzb = iZzbi2 + iZzbk2 + iZzac2;
                        i14 += iZzb;
                    }
                    break;
                case MotionEventCompat.AXIS_GENERIC_13 /* 44 */:
                    iZzac2 = zzgz.zzx((List) unsafe2.getObject(t, j3));
                    if (iZzac2 > 0) {
                        if (this.zzakr) {
                            unsafe2.putInt(t, i17, iZzac2);
                        }
                        iZzbi2 = zzee.zzbi(i16);
                        iZzbk2 = zzee.zzbk(iZzac2);
                        iZzb = iZzbi2 + iZzbk2 + iZzac2;
                        i14 += iZzb;
                    }
                    break;
                case MotionEventCompat.AXIS_GENERIC_14 /* 45 */:
                    iZzac2 = zzgz.zzab((List) unsafe2.getObject(t, j3));
                    if (iZzac2 > 0) {
                        if (this.zzakr) {
                            unsafe2.putInt(t, i17, iZzac2);
                        }
                        iZzbi2 = zzee.zzbi(i16);
                        iZzbk2 = zzee.zzbk(iZzac2);
                        iZzb = iZzbi2 + iZzbk2 + iZzac2;
                        i14 += iZzb;
                    }
                    break;
                case MotionEventCompat.AXIS_GENERIC_15 /* 46 */:
                    iZzac2 = zzgz.zzac((List) unsafe2.getObject(t, j3));
                    if (iZzac2 > 0) {
                        if (this.zzakr) {
                            unsafe2.putInt(t, i17, iZzac2);
                        }
                        iZzbi2 = zzee.zzbi(i16);
                        iZzbk2 = zzee.zzbk(iZzac2);
                        iZzb = iZzbi2 + iZzbk2 + iZzac2;
                        i14 += iZzb;
                    }
                    break;
                case MotionEventCompat.AXIS_GENERIC_16 /* 47 */:
                    iZzac2 = zzgz.zzaa((List) unsafe2.getObject(t, j3));
                    if (iZzac2 > 0) {
                        if (this.zzakr) {
                            unsafe2.putInt(t, i17, iZzac2);
                        }
                        iZzbi2 = zzee.zzbi(i16);
                        iZzbk2 = zzee.zzbk(iZzac2);
                        iZzb = iZzbi2 + iZzbk2 + iZzac2;
                        i14 += iZzb;
                    }
                    break;
                case 48:
                    iZzac2 = zzgz.zzw((List) unsafe2.getObject(t, j3));
                    if (iZzac2 > 0) {
                        if (this.zzakr) {
                            unsafe2.putInt(t, i17, iZzac2);
                        }
                        iZzbi2 = zzee.zzbi(i16);
                        iZzbk2 = zzee.zzbk(iZzac2);
                        iZzb = iZzbi2 + iZzbk2 + iZzac2;
                        i14 += iZzb;
                    }
                    break;
                case 49:
                    iZzb = zzgz.zzd(i16, zze(t, j3), zzbx(i13));
                    i14 += iZzb;
                    break;
                case 50:
                    iZzb = this.zzakz.zzb(i16, zzhv.zzp(t, j3), zzby(i13));
                    i14 += iZzb;
                    break;
                case 51:
                    if (zza(t, i16, i13)) {
                        iZzb = zzee.zzb(i16, 0.0d);
                        i14 += iZzb;
                    }
                    break;
                case 52:
                    if (zza(t, i16, i13)) {
                        iZzb = zzee.zzb(i16, 0.0f);
                        i14 += iZzb;
                    }
                    break;
                case 53:
                    if (zza(t, i16, i13)) {
                        iZzb = zzee.zzd(i16, zzi(t, j3));
                        i14 += iZzb;
                    }
                    break;
                case 54:
                    if (zza(t, i16, i13)) {
                        iZzb = zzee.zze(i16, zzi(t, j3));
                        i14 += iZzb;
                    }
                    break;
                case 55:
                    if (zza(t, i16, i13)) {
                        iZzb = zzee.zzg(i16, zzh(t, j3));
                        i14 += iZzb;
                    }
                    break;
                case 56:
                    if (zza(t, i16, i13)) {
                        iZzb = zzee.zzg(i16, 0L);
                        i14 += iZzb;
                    }
                    break;
                case 57:
                    if (zza(t, i16, i13)) {
                        iZzb = zzee.zzj(i16, 0);
                        i14 += iZzb;
                    }
                    break;
                case 58:
                    if (zza(t, i16, i13)) {
                        iZzb = zzee.zzc(i16, true);
                        i14 += iZzb;
                    }
                    break;
                case 59:
                    if (zza(t, i16, i13)) {
                        Object objZzp2 = zzhv.zzp(t, j3);
                        iZzb = objZzp2 instanceof zzdp ? zzee.zzc(i16, (zzdp) objZzp2) : zzee.zzc(i16, (String) objZzp2);
                        i14 += iZzb;
                    }
                    break;
                case 60:
                    if (zza(t, i16, i13)) {
                        iZzb = zzgz.zzc(i16, zzhv.zzp(t, j3), zzbx(i13));
                        i14 += iZzb;
                    }
                    break;
                case 61:
                    if (zza(t, i16, i13)) {
                        iZzb = zzee.zzc(i16, (zzdp) zzhv.zzp(t, j3));
                        i14 += iZzb;
                    }
                    break;
                case 62:
                    if (zza(t, i16, i13)) {
                        iZzb = zzee.zzh(i16, zzh(t, j3));
                        i14 += iZzb;
                    }
                    break;
                case 63:
                    if (zza(t, i16, i13)) {
                        iZzb = zzee.zzl(i16, zzh(t, j3));
                        i14 += iZzb;
                    }
                    break;
                case 64:
                    if (zza(t, i16, i13)) {
                        iZzb = zzee.zzk(i16, 0);
                        i14 += iZzb;
                    }
                    break;
                case 65:
                    if (zza(t, i16, i13)) {
                        iZzb = zzee.zzh(i16, 0L);
                        i14 += iZzb;
                    }
                    break;
                case 66:
                    if (zza(t, i16, i13)) {
                        iZzb = zzee.zzi(i16, zzh(t, j3));
                        i14 += iZzb;
                    }
                    break;
                case 67:
                    if (zza(t, i16, i13)) {
                        iZzb = zzee.zzf(i16, zzi(t, j3));
                        i14 += iZzb;
                    }
                    break;
                case 68:
                    if (zza(t, i16, i13)) {
                        iZzb = zzee.zzc(i16, (zzgi) zzhv.zzp(t, j3), zzbx(i13));
                        i14 += iZzb;
                    }
                    break;
            }
            i13 += 3;
            i3 = 267386880;
        }
        return i14 + zza(this.zzakx, t);
    }

    /* JADX WARN: Code duplicated, block: B:54:0x00cc  */
    /* JADX WARN: Code duplicated, block: B:56:0x00db  */
    /* JADX WARN: Code duplicated, block: B:59:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:62:0x00f2 A[LOOP:2: B:57:0x00e0->B:62:0x00f2, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:81:0x00f7 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:86:0x0109 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:94:0x00f0 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v14, types: [com.google.android.gms.internal.measurement.zzgx] */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v5, types: [com.google.android.gms.internal.measurement.zzgx] */
    @Override // com.google.android.gms.internal.measurement.zzgx
    public final boolean zzv(T t) {
        int i;
        List list;
        ?? Zzbx;
        int i2;
        int i3 = 0;
        int i4 = -1;
        int i5 = 0;
        while (true) {
            boolean z = true;
            if (i3 >= this.zzakt) {
                return !this.zzako || this.zzaky.zzh(t).isInitialized();
            }
            int i6 = this.zzaks[i3];
            int i7 = this.zzakj[i6];
            int iZzca = zzca(i6);
            if (this.zzakq) {
                i = 0;
            } else {
                int i8 = this.zzakj[i6 + 2];
                int i9 = i8 & 1048575;
                i = 1 << (i8 >>> 20);
                if (i9 != i4) {
                    i5 = zzaki.getInt(t, i9);
                    i4 = i9;
                }
            }
            if (((268435456 & iZzca) != 0) && !zza(t, i6, i5, i)) {
                return false;
            }
            int i10 = (267386880 & iZzca) >>> 20;
            if (i10 == 9 || i10 == 17) {
                if (zza(t, i6, i5, i) && !zza(t, iZzca, zzbx(i6))) {
                    return false;
                }
            } else if (i10 == 27) {
                list = (List) zzhv.zzp(t, iZzca & 1048575);
                if (!list.isEmpty()) {
                    Zzbx = zzbx(i6);
                    for (i2 = 0; i2 < list.size(); i2++) {
                        if (!Zzbx.zzv(list.get(i2))) {
                            z = false;
                            break;
                        }
                    }
                }
                if (!z) {
                    return false;
                }
            } else if (i10 == 60 || i10 == 68) {
                if (zza(t, i7, i6) && !zza(t, iZzca, zzbx(i6))) {
                    return false;
                }
            } else if (i10 == 49) {
                list = (List) zzhv.zzp(t, iZzca & 1048575);
                if (!list.isEmpty()) {
                    Zzbx = zzbx(i6);
                    while (i2 < list.size()) {
                        if (!Zzbx.zzv(list.get(i2))) {
                            z = false;
                            break;
                        }
                    }
                }
                if (!z) {
                    return false;
                }
            } else if (i10 != 50) {
                continue;
            } else {
                Map<?, ?> mapZzn = this.zzakz.zzn(zzhv.zzp(t, iZzca & 1048575));
                if (!mapZzn.isEmpty()) {
                    if (this.zzakz.zzr(zzby(i6)).zzakd.zzwz() == zzij.MESSAGE) {
                        ?? Zzf = 0;
                        for (Object obj : mapZzn.values()) {
                            if (Zzf == 0) {
                                Zzf = Zzf;
                                Zzf = zzgt.zzvy().zzf(obj.getClass());
                            }
                            Zzf = Zzf;
                            if (!Zzf.zzv(obj)) {
                                z = false;
                                break;
                            }
                        }
                    }
                }
                if (!z) {
                    return false;
                }
            }
            i3++;
        }
    }
}
