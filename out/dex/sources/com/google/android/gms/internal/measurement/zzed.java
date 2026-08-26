package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzed extends zzeb {
    private final byte[] buffer;
    private int limit;
    private int pos;
    private final boolean zzadx;
    private int zzady;
    private int zzadz;
    private int zzaea;
    private int zzaeb;

    private zzed(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzaeb = Integer.MAX_VALUE;
        this.buffer = bArr;
        this.limit = i2 + i;
        this.pos = i;
        this.zzadz = this.pos;
        this.zzadx = z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0066, code lost:
    
        if (r2[r3] >= 0) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int zzta() throws java.io.IOException {
        /*
            r5 = this;
            int r0 = r5.pos
            int r1 = r5.limit
            if (r1 == r0) goto L6b
            byte[] r2 = r5.buffer
            int r3 = r0 + 1
            r0 = r2[r0]
            if (r0 < 0) goto L11
            r5.pos = r3
            return r0
        L11:
            int r1 = r1 - r3
            r4 = 9
            if (r1 < r4) goto L6b
            int r1 = r3 + 1
            r3 = r2[r3]
            int r3 = r3 << 7
            r0 = r0 ^ r3
            if (r0 >= 0) goto L22
            r0 = r0 ^ (-128(0xffffffffffffff80, float:NaN))
            goto L68
        L22:
            int r3 = r1 + 1
            r1 = r2[r1]
            int r1 = r1 << 14
            r0 = r0 ^ r1
            if (r0 < 0) goto L2f
            r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
        L2d:
            r1 = r3
            goto L68
        L2f:
            int r1 = r3 + 1
            r3 = r2[r3]
            int r3 = r3 << 21
            r0 = r0 ^ r3
            if (r0 >= 0) goto L3d
            r2 = -2080896(0xffffffffffe03f80, float:NaN)
            r0 = r0 ^ r2
            goto L68
        L3d:
            int r3 = r1 + 1
            r1 = r2[r1]
            int r4 = r1 << 28
            r0 = r0 ^ r4
            r4 = 266354560(0xfe03f80, float:2.2112565E-29)
            r0 = r0 ^ r4
            if (r1 >= 0) goto L2d
            int r1 = r3 + 1
            r3 = r2[r3]
            if (r3 >= 0) goto L68
            int r3 = r1 + 1
            r1 = r2[r1]
            if (r1 >= 0) goto L2d
            int r1 = r3 + 1
            r3 = r2[r3]
            if (r3 >= 0) goto L68
            int r3 = r1 + 1
            r1 = r2[r1]
            if (r1 >= 0) goto L2d
            int r1 = r3 + 1
            r2 = r2[r3]
            if (r2 < 0) goto L6b
        L68:
            r5.pos = r1
            return r0
        L6b:
            long r0 = r5.zzsv()
            int r1 = (int) r0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzed.zzta():int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00b0, code lost:
    
        if (r2[r0] >= 0) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final long zztb() throws java.io.IOException {
        /*
            r11 = this;
            int r0 = r11.pos
            int r1 = r11.limit
            if (r1 == r0) goto Lb5
            byte[] r2 = r11.buffer
            int r3 = r0 + 1
            r0 = r2[r0]
            if (r0 < 0) goto L12
            r11.pos = r3
            long r0 = (long) r0
            return r0
        L12:
            int r1 = r1 - r3
            r4 = 9
            if (r1 < r4) goto Lb5
            int r1 = r3 + 1
            r3 = r2[r3]
            int r3 = r3 << 7
            r0 = r0 ^ r3
            if (r0 >= 0) goto L26
            r0 = r0 ^ (-128(0xffffffffffffff80, float:NaN))
        L22:
            long r2 = (long) r0
            r3 = r2
            goto Lb2
        L26:
            int r3 = r1 + 1
            r1 = r2[r1]
            int r1 = r1 << 14
            r0 = r0 ^ r1
            if (r0 < 0) goto L37
            r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
            long r0 = (long) r0
            r9 = r0
            r1 = r3
            r3 = r9
            goto Lb2
        L37:
            int r1 = r3 + 1
            r3 = r2[r3]
            int r3 = r3 << 21
            r0 = r0 ^ r3
            if (r0 >= 0) goto L45
            r2 = -2080896(0xffffffffffe03f80, float:NaN)
            r0 = r0 ^ r2
            goto L22
        L45:
            long r3 = (long) r0
            int r0 = r1 + 1
            r1 = r2[r1]
            long r5 = (long) r1
            r1 = 28
            long r5 = r5 << r1
            long r3 = r3 ^ r5
            r5 = 0
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 < 0) goto L5c
            r1 = 266354560(0xfe03f80, double:1.315966377E-315)
        L58:
            long r1 = r1 ^ r3
            r3 = r1
        L5a:
            r1 = r0
            goto Lb2
        L5c:
            int r1 = r0 + 1
            r0 = r2[r0]
            long r7 = (long) r0
            r0 = 35
            long r7 = r7 << r0
            long r3 = r3 ^ r7
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 >= 0) goto L70
            r5 = -34093383808(0xfffffff80fe03f80, double:NaN)
        L6e:
            long r3 = r3 ^ r5
            goto Lb2
        L70:
            int r0 = r1 + 1
            r1 = r2[r1]
            long r7 = (long) r1
            r1 = 42
            long r7 = r7 << r1
            long r3 = r3 ^ r7
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 < 0) goto L83
            r1 = 4363953127296(0x3f80fe03f80, double:2.1560793202584E-311)
            goto L58
        L83:
            int r1 = r0 + 1
            r0 = r2[r0]
            long r7 = (long) r0
            r0 = 49
            long r7 = r7 << r0
            long r3 = r3 ^ r7
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 >= 0) goto L96
            r5 = -558586000294016(0xfffe03f80fe03f80, double:NaN)
            goto L6e
        L96:
            int r0 = r1 + 1
            r1 = r2[r1]
            long r7 = (long) r1
            r1 = 56
            long r7 = r7 << r1
            long r3 = r3 ^ r7
            r7 = 71499008037633920(0xfe03f80fe03f80, double:6.838959413692434E-304)
            long r3 = r3 ^ r7
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 >= 0) goto L5a
            int r1 = r0 + 1
            r0 = r2[r0]
            long r7 = (long) r0
            int r0 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r0 < 0) goto Lb5
        Lb2:
            r11.pos = r1
            return r3
        Lb5:
            long r0 = r11.zzsv()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzed.zztb():long");
    }

    private final int zztc() throws IOException {
        int i = this.pos;
        if (this.limit - i < 4) {
            throw zzfi.zzut();
        }
        byte[] bArr = this.buffer;
        this.pos = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    private final long zztd() throws IOException {
        int i = this.pos;
        if (this.limit - i < 8) {
            throw zzfi.zzut();
        }
        byte[] bArr = this.buffer;
        this.pos = i + 8;
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    private final void zzte() {
        this.limit += this.zzady;
        int i = this.limit;
        int i2 = i - this.zzadz;
        int i3 = this.zzaeb;
        if (i2 <= i3) {
            this.zzady = 0;
        } else {
            this.zzady = i2 - i3;
            this.limit = i - this.zzady;
        }
    }

    private final byte zztf() throws IOException {
        int i = this.pos;
        if (i == this.limit) {
            throw zzfi.zzut();
        }
        byte[] bArr = this.buffer;
        this.pos = i + 1;
        return bArr[i];
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(zztd());
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(zztc());
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final String readString() throws IOException {
        int iZzta = zzta();
        if (iZzta > 0) {
            int i = this.limit;
            int i2 = this.pos;
            if (iZzta <= i - i2) {
                String str = new String(this.buffer, i2, iZzta, zzez.UTF_8);
                this.pos += iZzta;
                return str;
            }
        }
        if (iZzta == 0) {
            return "";
        }
        if (iZzta < 0) {
            throw zzfi.zzuu();
        }
        throw zzfi.zzut();
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final <T extends zzgi> T zza(zzgr<T> zzgrVar, zzel zzelVar) throws IOException {
        int iZzta = zzta();
        if (this.zzadp >= this.zzadq) {
            throw zzfi.zzuz();
        }
        int iZzaw = zzaw(iZzta);
        this.zzadp++;
        T tZzc = zzgrVar.zzc(this, zzelVar);
        zzat(0);
        this.zzadp--;
        zzax(iZzaw);
        return tZzc;
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final void zzat(int i) throws zzfi {
        if (this.zzaea != i) {
            throw zzfi.zzux();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.internal.measurement.zzeb
    public final boolean zzau(int i) throws IOException {
        int iZzsg;
        int i2 = i & 7;
        int i3 = 0;
        if (i2 == 0) {
            if (this.limit - this.pos < 10) {
                while (i3 < 10) {
                    if (zztf() < 0) {
                        i3++;
                    }
                }
                throw zzfi.zzuv();
            }
            while (i3 < 10) {
                byte[] bArr = this.buffer;
                int i4 = this.pos;
                this.pos = i4 + 1;
                if (bArr[i4] < 0) {
                    i3++;
                }
            }
            throw zzfi.zzuv();
            return true;
        }
        if (i2 == 1) {
            zzay(8);
            return true;
        }
        if (i2 == 2) {
            zzay(zzta());
            return true;
        }
        if (i2 != 3) {
            if (i2 == 4) {
                return false;
            }
            if (i2 != 5) {
                throw zzfi.zzuy();
            }
            zzay(4);
            return true;
        }
        do {
            iZzsg = zzsg();
            if (iZzsg == 0) {
                break;
            }
        } while (zzau(iZzsg));
        zzat(((i >>> 3) << 3) | 4);
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final int zzaw(int i) throws zzfi {
        if (i < 0) {
            throw zzfi.zzuu();
        }
        int iZzsx = i + zzsx();
        int i2 = this.zzaeb;
        if (iZzsx > i2) {
            throw zzfi.zzut();
        }
        this.zzaeb = iZzsx;
        zzte();
        return i2;
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final void zzax(int i) {
        this.zzaeb = i;
        zzte();
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final void zzay(int i) throws IOException {
        if (i >= 0) {
            int i2 = this.limit;
            int i3 = this.pos;
            if (i <= i2 - i3) {
                this.pos = i3 + i;
                return;
            }
        }
        if (i >= 0) {
            throw zzfi.zzut();
        }
        throw zzfi.zzuu();
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final int zzsg() throws IOException {
        if (zzsw()) {
            this.zzaea = 0;
            return 0;
        }
        this.zzaea = zzta();
        int i = this.zzaea;
        if ((i >>> 3) != 0) {
            return i;
        }
        throw zzfi.zzuw();
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final long zzsh() throws IOException {
        return zztb();
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final long zzsi() throws IOException {
        return zztb();
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final int zzsj() throws IOException {
        return zzta();
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final long zzsk() throws IOException {
        return zztd();
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final int zzsl() throws IOException {
        return zztc();
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final boolean zzsm() throws IOException {
        return zztb() != 0;
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final String zzsn() throws IOException {
        int iZzta = zzta();
        if (iZzta > 0) {
            int i = this.limit;
            int i2 = this.pos;
            if (iZzta <= i - i2) {
                String strZzh = zzhy.zzh(this.buffer, i2, iZzta);
                this.pos += iZzta;
                return strZzh;
            }
        }
        if (iZzta == 0) {
            return "";
        }
        if (iZzta <= 0) {
            throw zzfi.zzuu();
        }
        throw zzfi.zzut();
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0033 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:16:0x0035 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:17:0x0037  */
    /* JADX WARN: Code duplicated, block: B:20:0x003e  */
    /* JADX WARN: Code duplicated, block: B:22:0x0043  */
    @Override // com.google.android.gms.internal.measurement.zzeb
    public final zzdp zzso() throws IOException {
        byte[] bArrCopyOfRange;
        int iZzta = zzta();
        if (iZzta > 0) {
            int i = this.limit;
            int i2 = this.pos;
            if (iZzta <= i - i2) {
                zzdp zzdpVarZzb = zzdp.zzb(this.buffer, i2, iZzta);
                this.pos += iZzta;
                return zzdpVarZzb;
            }
        }
        if (iZzta == 0) {
            return zzdp.zzadh;
        }
        if (iZzta > 0) {
            int i3 = this.limit;
            int i4 = this.pos;
            if (iZzta <= i3 - i4) {
                this.pos = iZzta + i4;
                bArrCopyOfRange = Arrays.copyOfRange(this.buffer, i4, this.pos);
            } else {
                if (iZzta <= 0) {
                    throw zzfi.zzut();
                }
                if (iZzta == 0) {
                    throw zzfi.zzuu();
                }
                bArrCopyOfRange = zzez.zzair;
            }
        } else {
            if (iZzta <= 0) {
                throw zzfi.zzut();
            }
            if (iZzta == 0) {
                throw zzfi.zzuu();
            }
            bArrCopyOfRange = zzez.zzair;
        }
        return zzdp.zze(bArrCopyOfRange);
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final int zzsp() throws IOException {
        return zzta();
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final int zzsq() throws IOException {
        return zzta();
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final int zzsr() throws IOException {
        return zztc();
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final long zzss() throws IOException {
        return zztd();
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final int zzst() throws IOException {
        return zzaz(zzta());
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final long zzsu() throws IOException {
        return zzbm(zztb());
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.internal.measurement.zzeb
    final long zzsv() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte bZztf = zztf();
            j |= ((long) (bZztf & 127)) << i;
            if ((bZztf & 128) == 0) {
                return j;
            }
        }
        throw zzfi.zzuv();
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final boolean zzsw() throws IOException {
        return this.pos == this.limit;
    }

    @Override // com.google.android.gms.internal.measurement.zzeb
    public final int zzsx() {
        return this.pos - this.zzadz;
    }
}
