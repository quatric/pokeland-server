package com.google.android.gms.internal.measurement;

import com.google.common.base.Ascii;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzil {
    private final byte[] buffer;
    private int zzadp;
    private int zzady;
    private int zzaea;
    private final int zzaoh;
    private int zzaoi;
    private zzeb zzaok;
    private int zzaeb = Integer.MAX_VALUE;
    private int zzadq = 64;
    private int zzadr = 67108864;
    private final int zzaog = 0;
    private int zzaoj = 0;

    private zzil(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        int i3 = i2 + 0;
        this.zzaoi = i3;
        this.zzaoh = i3;
    }

    private final void zzat(int i) throws zzit {
        if (this.zzaea != i) {
            throw new zzit("Protocol message end-group tag did not match expected tag.");
        }
    }

    private final void zzay(int i) throws IOException {
        if (i < 0) {
            throw zzit.zzxe();
        }
        int i2 = this.zzaoj;
        int i3 = i2 + i;
        int i4 = this.zzaeb;
        if (i3 > i4) {
            zzay(i4 - i2);
            throw zzit.zzxd();
        }
        if (i > this.zzaoi - i2) {
            throw zzit.zzxd();
        }
        this.zzaoj = i2 + i;
    }

    public static zzil zzj(byte[] bArr, int i, int i2) {
        return new zzil(bArr, 0, i2);
    }

    private final void zzte() {
        this.zzaoi += this.zzady;
        int i = this.zzaoi;
        int i2 = this.zzaeb;
        if (i <= i2) {
            this.zzady = 0;
        } else {
            this.zzady = i - i2;
            this.zzaoi = i - this.zzady;
        }
    }

    private final byte zztf() throws IOException {
        int i = this.zzaoj;
        if (i == this.zzaoi) {
            throw zzit.zzxd();
        }
        byte[] bArr = this.buffer;
        this.zzaoj = i + 1;
        return bArr[i];
    }

    public final int getPosition() {
        return this.zzaoj - this.zzaog;
    }

    public final String readString() throws IOException {
        int iZzta = zzta();
        if (iZzta < 0) {
            throw zzit.zzxe();
        }
        int i = this.zzaoi;
        int i2 = this.zzaoj;
        if (iZzta > i - i2) {
            throw zzit.zzxd();
        }
        String str = new String(this.buffer, i2, iZzta, zziu.UTF_8);
        this.zzaoj += iZzta;
        return str;
    }

    public final <T extends zzey<T, ?>> T zza(zzgr<T> zzgrVar) throws IOException {
        try {
            if (this.zzaok == null) {
                this.zzaok = zzeb.zzd(this.buffer, this.zzaog, this.zzaoh);
            }
            int iZzsx = this.zzaok.zzsx();
            int i = this.zzaoj - this.zzaog;
            if (iZzsx > i) {
                throw new IOException(String.format("CodedInputStream read ahead of CodedInputByteBufferNano: %s > %s", Integer.valueOf(iZzsx), Integer.valueOf(i)));
            }
            this.zzaok.zzay(i - iZzsx);
            this.zzaok.zzav(this.zzadq - this.zzadp);
            T t = (T) this.zzaok.zza(zzgrVar, zzel.zztq());
            zzau(this.zzaea);
            return t;
        } catch (zzfi e) {
            throw new zzit("", e);
        }
    }

    public final void zza(zziw zziwVar) throws IOException {
        int iZzta = zzta();
        if (this.zzadp >= this.zzadq) {
            throw new zzit("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
        }
        if (iZzta < 0) {
            throw zzit.zzxe();
        }
        int i = iZzta + this.zzaoj;
        int i2 = this.zzaeb;
        if (i > i2) {
            throw zzit.zzxd();
        }
        this.zzaeb = i;
        zzte();
        this.zzadp++;
        zziwVar.zza(this);
        zzat(0);
        this.zzadp--;
        this.zzaeb = i2;
        zzte();
    }

    public final boolean zzau(int i) throws IOException {
        int iZzsg;
        int i2 = i & 7;
        if (i2 == 0) {
            zzta();
            return true;
        }
        if (i2 == 1) {
            zztf();
            zztf();
            zztf();
            zztf();
            zztf();
            zztf();
            zztf();
            zztf();
            return true;
        }
        if (i2 == 2) {
            zzay(zzta());
            return true;
        }
        if (i2 == 3) {
            do {
                iZzsg = zzsg();
                if (iZzsg == 0) {
                    break;
                }
            } while (zzau(iZzsg));
            zzat(((i >>> 3) << 3) | 4);
            return true;
        }
        if (i2 == 4) {
            return false;
        }
        if (i2 != 5) {
            throw new zzit("Protocol message tag had invalid wire type.");
        }
        zztf();
        zztf();
        zztf();
        zztf();
        return true;
    }

    public final int zzsg() throws IOException {
        if (this.zzaoj == this.zzaoi) {
            this.zzaea = 0;
            return 0;
        }
        this.zzaea = zzta();
        int i = this.zzaea;
        if (i != 0) {
            return i;
        }
        throw new zzit("Protocol message contained an invalid tag (zero).");
    }

    public final boolean zzsm() throws IOException {
        return zzta() != 0;
    }

    public final byte[] zzt(int i, int i2) {
        if (i2 == 0) {
            return zzix.zzaph;
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(this.buffer, this.zzaog + i, bArr, 0, i2);
        return bArr;
    }

    public final int zzta() throws IOException {
        int i;
        byte bZztf = zztf();
        if (bZztf >= 0) {
            return bZztf;
        }
        int i2 = bZztf & 127;
        byte bZztf2 = zztf();
        if (bZztf2 >= 0) {
            i = bZztf2 << 7;
        } else {
            i2 |= (bZztf2 & 127) << 7;
            byte bZztf3 = zztf();
            if (bZztf3 >= 0) {
                i = bZztf3 << Ascii.f292SO;
            } else {
                i2 |= (bZztf3 & 127) << 14;
                byte bZztf4 = zztf();
                if (bZztf4 < 0) {
                    int i3 = i2 | ((bZztf4 & 127) << 21);
                    byte bZztf5 = zztf();
                    int i4 = i3 | (bZztf5 << Ascii.f285FS);
                    if (bZztf5 >= 0) {
                        return i4;
                    }
                    for (int i5 = 0; i5 < 5; i5++) {
                        if (zztf() >= 0) {
                            return i4;
                        }
                    }
                    throw zzit.zzxf();
                }
                i = bZztf4 << Ascii.NAK;
            }
        }
        return i2 | i;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public final long zztb() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte bZztf = zztf();
            j |= ((long) (bZztf & 127)) << i;
            if ((bZztf & 128) == 0) {
                return j;
            }
        }
        throw zzit.zzxf();
    }

    final void zzu(int i, int i2) {
        int i3 = this.zzaoj;
        int i4 = this.zzaog;
        if (i > i3 - i4) {
            StringBuilder sb = new StringBuilder(50);
            sb.append("Position ");
            sb.append(i);
            sb.append(" is beyond current ");
            sb.append(i3 - i4);
            throw new IllegalArgumentException(sb.toString());
        }
        if (i >= 0) {
            this.zzaoj = i4 + i;
            this.zzaea = i2;
        } else {
            StringBuilder sb2 = new StringBuilder(24);
            sb2.append("Bad position ");
            sb2.append(i);
            throw new IllegalArgumentException(sb2.toString());
        }
    }
}
