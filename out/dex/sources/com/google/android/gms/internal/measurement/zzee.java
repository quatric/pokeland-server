package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class zzee extends zzdm {
    private static final Logger logger = Logger.getLogger(zzee.class.getName());
    private static final boolean zzaec = zzhv.zzwt();
    zzei zzaed;

    static class zza extends zzee {
        private final byte[] buffer;
        private final int limit;
        private final int offset;
        private int position;

        zza(byte[] bArr, int i, int i2) {
            super();
            if (bArr == null) {
                throw new NullPointerException("buffer");
            }
            int i3 = i + i2;
            if ((i | i2 | (bArr.length - i3)) < 0) {
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)));
            }
            this.buffer = bArr;
            this.offset = i;
            this.position = i;
            this.limit = i3;
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public void flush() {
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void write(byte[] bArr, int i, int i2) throws IOException {
            try {
                System.arraycopy(bArr, i, this.buffer, this.position, i2);
                this.position += i2;
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(i2)), e);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zza(int i, long j) throws IOException {
            zzb(i, 0);
            zzbn(j);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zza(int i, zzdp zzdpVar) throws IOException {
            zzb(i, 2);
            zza(zzdpVar);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zza(int i, zzgi zzgiVar) throws IOException {
            zzb(i, 2);
            zzb(zzgiVar);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        final void zza(int i, zzgi zzgiVar, zzgx zzgxVar) throws IOException {
            zzb(i, 2);
            zzdf zzdfVar = (zzdf) zzgiVar;
            int iZzrt = zzdfVar.zzrt();
            if (iZzrt == -1) {
                iZzrt = zzgxVar.zzt(zzdfVar);
                zzdfVar.zzam(iZzrt);
            }
            zzbf(iZzrt);
            zzgxVar.zza(zzgiVar, this.zzaed);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zza(zzdp zzdpVar) throws IOException {
            zzbf(zzdpVar.size());
            zzdpVar.zza(this);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        final void zza(zzgi zzgiVar, zzgx zzgxVar) throws IOException {
            zzdf zzdfVar = (zzdf) zzgiVar;
            int iZzrt = zzdfVar.zzrt();
            if (iZzrt == -1) {
                iZzrt = zzgxVar.zzt(zzdfVar);
                zzdfVar.zzam(iZzrt);
            }
            zzbf(iZzrt);
            zzgxVar.zza(zzgiVar, this.zzaed);
        }

        @Override // com.google.android.gms.internal.measurement.zzdm
        public final void zza(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(int i, int i2) throws IOException {
            zzbf((i << 3) | i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(int i, zzdp zzdpVar) throws IOException {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzdpVar);
            zzb(1, 4);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(int i, zzgi zzgiVar) throws IOException {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzgiVar);
            zzb(1, 4);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(int i, String str) throws IOException {
            zzb(i, 2);
            zzdr(str);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(int i, boolean z) throws IOException {
            zzb(i, 0);
            zzc(z ? (byte) 1 : (byte) 0);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(zzgi zzgiVar) throws IOException {
            zzbf(zzgiVar.zzuk());
            zzgiVar.zzb(this);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzbe(int i) throws IOException {
            if (i >= 0) {
                zzbf(i);
            } else {
                zzbn(i);
            }
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzbf(int i) throws IOException {
            if (!zzee.zzaec || zzdi.zzrv() || zztg() < 5) {
                while ((i & (-128)) != 0) {
                    try {
                        byte[] bArr = this.buffer;
                        int i2 = this.position;
                        this.position = i2 + 1;
                        bArr[i2] = (byte) ((i & 127) | 128);
                        i >>>= 7;
                    } catch (IndexOutOfBoundsException e) {
                        throw new zzb(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
                    }
                }
                byte[] bArr2 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr2[i3] = (byte) i;
                return;
            }
            if ((i & (-128)) == 0) {
                byte[] bArr3 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                zzhv.zza(bArr3, i4, (byte) i);
                return;
            }
            byte[] bArr4 = this.buffer;
            int i5 = this.position;
            this.position = i5 + 1;
            zzhv.zza(bArr4, i5, (byte) (i | 128));
            int i6 = i >>> 7;
            if ((i6 & (-128)) == 0) {
                byte[] bArr5 = this.buffer;
                int i7 = this.position;
                this.position = i7 + 1;
                zzhv.zza(bArr5, i7, (byte) i6);
                return;
            }
            byte[] bArr6 = this.buffer;
            int i8 = this.position;
            this.position = i8 + 1;
            zzhv.zza(bArr6, i8, (byte) (i6 | 128));
            int i9 = i6 >>> 7;
            if ((i9 & (-128)) == 0) {
                byte[] bArr7 = this.buffer;
                int i10 = this.position;
                this.position = i10 + 1;
                zzhv.zza(bArr7, i10, (byte) i9);
                return;
            }
            byte[] bArr8 = this.buffer;
            int i11 = this.position;
            this.position = i11 + 1;
            zzhv.zza(bArr8, i11, (byte) (i9 | 128));
            int i12 = i9 >>> 7;
            if ((i12 & (-128)) == 0) {
                byte[] bArr9 = this.buffer;
                int i13 = this.position;
                this.position = i13 + 1;
                zzhv.zza(bArr9, i13, (byte) i12);
                return;
            }
            byte[] bArr10 = this.buffer;
            int i14 = this.position;
            this.position = i14 + 1;
            zzhv.zza(bArr10, i14, (byte) (i12 | 128));
            byte[] bArr11 = this.buffer;
            int i15 = this.position;
            this.position = i15 + 1;
            zzhv.zza(bArr11, i15, (byte) (i12 >>> 7));
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzbh(int i) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = (byte) i;
                byte[] bArr2 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr2[i3] = (byte) (i >> 8);
                byte[] bArr3 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                bArr3[i4] = (byte) (i >> 16);
                byte[] bArr4 = this.buffer;
                int i5 = this.position;
                this.position = i5 + 1;
                bArr4[i5] = (byte) (i >>> 24);
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
            }
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzbn(long j) throws IOException {
            if (zzee.zzaec && zztg() >= 10) {
                while ((j & (-128)) != 0) {
                    byte[] bArr = this.buffer;
                    int i = this.position;
                    this.position = i + 1;
                    zzhv.zza(bArr, i, (byte) ((((int) j) & 127) | 128));
                    j >>>= 7;
                }
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                zzhv.zza(bArr2, i2, (byte) j);
                return;
            }
            while ((j & (-128)) != 0) {
                try {
                    byte[] bArr3 = this.buffer;
                    int i3 = this.position;
                    this.position = i3 + 1;
                    bArr3[i3] = (byte) ((((int) j) & 127) | 128);
                    j >>>= 7;
                } catch (IndexOutOfBoundsException e) {
                    throw new zzb(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
                }
            }
            byte[] bArr4 = this.buffer;
            int i4 = this.position;
            this.position = i4 + 1;
            bArr4[i4] = (byte) j;
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzbp(long j) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) j;
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr2[i2] = (byte) (j >> 8);
                byte[] bArr3 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr3[i3] = (byte) (j >> 16);
                byte[] bArr4 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                bArr4[i4] = (byte) (j >> 24);
                byte[] bArr5 = this.buffer;
                int i5 = this.position;
                this.position = i5 + 1;
                bArr5[i5] = (byte) (j >> 32);
                byte[] bArr6 = this.buffer;
                int i6 = this.position;
                this.position = i6 + 1;
                bArr6[i6] = (byte) (j >> 40);
                byte[] bArr7 = this.buffer;
                int i7 = this.position;
                this.position = i7 + 1;
                bArr7[i7] = (byte) (j >> 48);
                byte[] bArr8 = this.buffer;
                int i8 = this.position;
                this.position = i8 + 1;
                bArr8[i8] = (byte) (j >> 56);
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzc(byte b) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = b;
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzc(int i, int i2) throws IOException {
            zzb(i, 0);
            zzbe(i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzc(int i, long j) throws IOException {
            zzb(i, 1);
            zzbp(j);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzd(int i, int i2) throws IOException {
            zzb(i, 0);
            zzbf(i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzdr(String str) throws IOException {
            int i = this.position;
            try {
                int iZzbk = zzbk(str.length() * 3);
                int iZzbk2 = zzbk(str.length());
                if (iZzbk2 != iZzbk) {
                    zzbf(zzhy.zza(str));
                    this.position = zzhy.zza(str, this.buffer, this.position, zztg());
                    return;
                }
                this.position = i + iZzbk2;
                int iZza = zzhy.zza(str, this.buffer, this.position, zztg());
                this.position = i;
                zzbf((iZza - i) - iZzbk2);
                this.position = iZza;
            } catch (zzib e) {
                this.position = i;
                zza(str, e);
            } catch (IndexOutOfBoundsException e2) {
                throw new zzb(e2);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zze(byte[] bArr, int i, int i2) throws IOException {
            zzbf(i2);
            write(bArr, 0, i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzf(int i, int i2) throws IOException {
            zzb(i, 5);
            zzbh(i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final int zztg() {
            return this.limit - this.position;
        }

        public final int zztj() {
            return this.position - this.offset;
        }
    }

    public static class zzb extends IOException {
        zzb() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }

        /* JADX WARN: Illegal instructions before constructor call */
        zzb(String str) {
            String strValueOf = String.valueOf(str);
            super(strValueOf.length() != 0 ? "CodedOutputStream was writing to a flat byte array and ran out of space.: ".concat(strValueOf) : new String("CodedOutputStream was writing to a flat byte array and ran out of space.: "));
        }

        /* JADX WARN: Illegal instructions before constructor call */
        zzb(String str, Throwable th) {
            String strValueOf = String.valueOf(str);
            super(strValueOf.length() != 0 ? "CodedOutputStream was writing to a flat byte array and ran out of space.: ".concat(strValueOf) : new String("CodedOutputStream was writing to a flat byte array and ran out of space.: "), th);
        }

        zzb(Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
        }
    }

    static final class zzc extends zza {
        private final ByteBuffer zzaef;
        private int zzaeg;

        zzc(ByteBuffer byteBuffer) {
            super(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
            this.zzaef = byteBuffer;
            this.zzaeg = byteBuffer.position();
        }

        @Override // com.google.android.gms.internal.measurement.zzee.zza, com.google.android.gms.internal.measurement.zzee
        public final void flush() {
            this.zzaef.position(this.zzaeg + zztj());
        }
    }

    static final class zzd extends zzee {
        private final ByteBuffer zzaeh;
        private final ByteBuffer zzaei;
        private final long zzaej;
        private final long zzaek;
        private final long zzael;
        private final long zzaem;
        private long zzaen;

        zzd(ByteBuffer byteBuffer) {
            super();
            this.zzaeh = byteBuffer;
            this.zzaei = byteBuffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.zzaej = zzhv.zzb(byteBuffer);
            this.zzaek = this.zzaej + ((long) byteBuffer.position());
            this.zzael = this.zzaej + ((long) byteBuffer.limit());
            this.zzaem = this.zzael - 10;
            this.zzaen = this.zzaek;
        }

        private final void zzbw(long j) {
            this.zzaei.position((int) (j - this.zzaej));
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void flush() {
            this.zzaeh.position((int) (this.zzaen - this.zzaej));
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void write(byte[] bArr, int i, int i2) throws IOException {
            if (bArr != null && i >= 0 && i2 >= 0 && bArr.length - i2 >= i) {
                long j = i2;
                long j2 = this.zzael - j;
                long j3 = this.zzaen;
                if (j2 >= j3) {
                    zzhv.zza(bArr, i, j3, j);
                    this.zzaen += j;
                    return;
                }
            }
            if (bArr != null) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", Long.valueOf(this.zzaen), Long.valueOf(this.zzael), Integer.valueOf(i2)));
            }
            throw new NullPointerException("value");
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zza(int i, long j) throws IOException {
            zzb(i, 0);
            zzbn(j);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zza(int i, zzdp zzdpVar) throws IOException {
            zzb(i, 2);
            zza(zzdpVar);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zza(int i, zzgi zzgiVar) throws IOException {
            zzb(i, 2);
            zzb(zzgiVar);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        final void zza(int i, zzgi zzgiVar, zzgx zzgxVar) throws IOException {
            zzb(i, 2);
            zza(zzgiVar, zzgxVar);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zza(zzdp zzdpVar) throws IOException {
            zzbf(zzdpVar.size());
            zzdpVar.zza(this);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        final void zza(zzgi zzgiVar, zzgx zzgxVar) throws IOException {
            zzdf zzdfVar = (zzdf) zzgiVar;
            int iZzrt = zzdfVar.zzrt();
            if (iZzrt == -1) {
                iZzrt = zzgxVar.zzt(zzdfVar);
                zzdfVar.zzam(iZzrt);
            }
            zzbf(iZzrt);
            zzgxVar.zza(zzgiVar, this.zzaed);
        }

        @Override // com.google.android.gms.internal.measurement.zzdm
        public final void zza(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(int i, int i2) throws IOException {
            zzbf((i << 3) | i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(int i, zzdp zzdpVar) throws IOException {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzdpVar);
            zzb(1, 4);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(int i, zzgi zzgiVar) throws IOException {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzgiVar);
            zzb(1, 4);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(int i, String str) throws IOException {
            zzb(i, 2);
            zzdr(str);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(int i, boolean z) throws IOException {
            zzb(i, 0);
            zzc(z ? (byte) 1 : (byte) 0);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(zzgi zzgiVar) throws IOException {
            zzbf(zzgiVar.zzuk());
            zzgiVar.zzb(this);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzbe(int i) throws IOException {
            if (i >= 0) {
                zzbf(i);
            } else {
                zzbn(i);
            }
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzbf(int i) throws IOException {
            if (this.zzaen <= this.zzaem) {
                while ((i & (-128)) != 0) {
                    long j = this.zzaen;
                    this.zzaen = j + 1;
                    zzhv.zza(j, (byte) ((i & 127) | 128));
                    i >>>= 7;
                }
                long j2 = this.zzaen;
                this.zzaen = 1 + j2;
                zzhv.zza(j2, (byte) i);
                return;
            }
            while (true) {
                long j3 = this.zzaen;
                if (j3 >= this.zzael) {
                    throw new zzb(String.format("Pos: %d, limit: %d, len: %d", Long.valueOf(j3), Long.valueOf(this.zzael), 1));
                }
                if ((i & (-128)) == 0) {
                    this.zzaen = 1 + j3;
                    zzhv.zza(j3, (byte) i);
                    return;
                } else {
                    this.zzaen = j3 + 1;
                    zzhv.zza(j3, (byte) ((i & 127) | 128));
                    i >>>= 7;
                }
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzbh(int i) throws IOException {
            this.zzaei.putInt((int) (this.zzaen - this.zzaej), i);
            this.zzaen += 4;
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzbn(long j) throws IOException {
            if (this.zzaen <= this.zzaem) {
                while ((j & (-128)) != 0) {
                    long j2 = this.zzaen;
                    this.zzaen = j2 + 1;
                    zzhv.zza(j2, (byte) ((((int) j) & 127) | 128));
                    j >>>= 7;
                }
                long j3 = this.zzaen;
                this.zzaen = 1 + j3;
                zzhv.zza(j3, (byte) j);
                return;
            }
            while (true) {
                long j4 = this.zzaen;
                if (j4 >= this.zzael) {
                    throw new zzb(String.format("Pos: %d, limit: %d, len: %d", Long.valueOf(j4), Long.valueOf(this.zzael), 1));
                }
                if ((j & (-128)) == 0) {
                    this.zzaen = 1 + j4;
                    zzhv.zza(j4, (byte) j);
                    return;
                } else {
                    this.zzaen = j4 + 1;
                    zzhv.zza(j4, (byte) ((((int) j) & 127) | 128));
                    j >>>= 7;
                }
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzbp(long j) throws IOException {
            this.zzaei.putLong((int) (this.zzaen - this.zzaej), j);
            this.zzaen += 8;
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzc(byte b) throws IOException {
            long j = this.zzaen;
            if (j >= this.zzael) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", Long.valueOf(j), Long.valueOf(this.zzael), 1));
            }
            this.zzaen = 1 + j;
            zzhv.zza(j, b);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzc(int i, int i2) throws IOException {
            zzb(i, 0);
            zzbe(i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzc(int i, long j) throws IOException {
            zzb(i, 1);
            zzbp(j);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzd(int i, int i2) throws IOException {
            zzb(i, 0);
            zzbf(i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzdr(String str) throws IOException {
            long j = this.zzaen;
            try {
                int iZzbk = zzbk(str.length() * 3);
                int iZzbk2 = zzbk(str.length());
                if (iZzbk2 != iZzbk) {
                    int iZza = zzhy.zza(str);
                    zzbf(iZza);
                    zzbw(this.zzaen);
                    zzhy.zza(str, this.zzaei);
                    this.zzaen += (long) iZza;
                    return;
                }
                int i = ((int) (this.zzaen - this.zzaej)) + iZzbk2;
                this.zzaei.position(i);
                zzhy.zza(str, this.zzaei);
                int iPosition = this.zzaei.position() - i;
                zzbf(iPosition);
                this.zzaen += (long) iPosition;
            } catch (zzib e) {
                this.zzaen = j;
                zzbw(this.zzaen);
                zza(str, e);
            } catch (IllegalArgumentException e2) {
                throw new zzb(e2);
            } catch (IndexOutOfBoundsException e3) {
                throw new zzb(e3);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zze(byte[] bArr, int i, int i2) throws IOException {
            zzbf(i2);
            write(bArr, 0, i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzf(int i, int i2) throws IOException {
            zzb(i, 5);
            zzbh(i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final int zztg() {
            return (int) (this.zzael - this.zzaen);
        }
    }

    static final class zze extends zzee {
        private final int zzaeg;
        private final ByteBuffer zzaeh;
        private final ByteBuffer zzaei;

        zze(ByteBuffer byteBuffer) {
            super();
            this.zzaeh = byteBuffer;
            this.zzaei = byteBuffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.zzaeg = byteBuffer.position();
        }

        private final void zzdt(String str) throws IOException {
            try {
                zzhy.zza(str, this.zzaei);
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(e);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void flush() {
            this.zzaeh.position(this.zzaei.position());
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void write(byte[] bArr, int i, int i2) throws IOException {
            try {
                this.zzaei.put(bArr, i, i2);
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(e);
            } catch (BufferOverflowException e2) {
                throw new zzb(e2);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zza(int i, long j) throws IOException {
            zzb(i, 0);
            zzbn(j);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zza(int i, zzdp zzdpVar) throws IOException {
            zzb(i, 2);
            zza(zzdpVar);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zza(int i, zzgi zzgiVar) throws IOException {
            zzb(i, 2);
            zzb(zzgiVar);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        final void zza(int i, zzgi zzgiVar, zzgx zzgxVar) throws IOException {
            zzb(i, 2);
            zza(zzgiVar, zzgxVar);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zza(zzdp zzdpVar) throws IOException {
            zzbf(zzdpVar.size());
            zzdpVar.zza(this);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        final void zza(zzgi zzgiVar, zzgx zzgxVar) throws IOException {
            zzdf zzdfVar = (zzdf) zzgiVar;
            int iZzrt = zzdfVar.zzrt();
            if (iZzrt == -1) {
                iZzrt = zzgxVar.zzt(zzdfVar);
                zzdfVar.zzam(iZzrt);
            }
            zzbf(iZzrt);
            zzgxVar.zza(zzgiVar, this.zzaed);
        }

        @Override // com.google.android.gms.internal.measurement.zzdm
        public final void zza(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(int i, int i2) throws IOException {
            zzbf((i << 3) | i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(int i, zzdp zzdpVar) throws IOException {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzdpVar);
            zzb(1, 4);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(int i, zzgi zzgiVar) throws IOException {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzgiVar);
            zzb(1, 4);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(int i, String str) throws IOException {
            zzb(i, 2);
            zzdr(str);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(int i, boolean z) throws IOException {
            zzb(i, 0);
            zzc(z ? (byte) 1 : (byte) 0);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzb(zzgi zzgiVar) throws IOException {
            zzbf(zzgiVar.zzuk());
            zzgiVar.zzb(this);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzbe(int i) throws IOException {
            if (i >= 0) {
                zzbf(i);
            } else {
                zzbn(i);
            }
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzbf(int i) throws IOException {
            while ((i & (-128)) != 0) {
                try {
                    this.zzaei.put((byte) ((i & 127) | 128));
                    i >>>= 7;
                } catch (BufferOverflowException e) {
                    throw new zzb(e);
                }
            }
            this.zzaei.put((byte) i);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzbh(int i) throws IOException {
            try {
                this.zzaei.putInt(i);
            } catch (BufferOverflowException e) {
                throw new zzb(e);
            }
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzbn(long j) throws IOException {
            while (((-128) & j) != 0) {
                try {
                    this.zzaei.put((byte) ((((int) j) & 127) | 128));
                    j >>>= 7;
                } catch (BufferOverflowException e) {
                    throw new zzb(e);
                }
            }
            this.zzaei.put((byte) j);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzbp(long j) throws IOException {
            try {
                this.zzaei.putLong(j);
            } catch (BufferOverflowException e) {
                throw new zzb(e);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzc(byte b) throws IOException {
            try {
                this.zzaei.put(b);
            } catch (BufferOverflowException e) {
                throw new zzb(e);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzc(int i, int i2) throws IOException {
            zzb(i, 0);
            zzbe(i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzc(int i, long j) throws IOException {
            zzb(i, 1);
            zzbp(j);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzd(int i, int i2) throws IOException {
            zzb(i, 0);
            zzbf(i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzdr(String str) throws IOException {
            int iPosition = this.zzaei.position();
            try {
                int iZzbk = zzbk(str.length() * 3);
                int iZzbk2 = zzbk(str.length());
                if (iZzbk2 != iZzbk) {
                    zzbf(zzhy.zza(str));
                    zzdt(str);
                    return;
                }
                int iPosition2 = this.zzaei.position() + iZzbk2;
                this.zzaei.position(iPosition2);
                zzdt(str);
                int iPosition3 = this.zzaei.position();
                this.zzaei.position(iPosition);
                zzbf(iPosition3 - iPosition2);
                this.zzaei.position(iPosition3);
            } catch (zzib e) {
                this.zzaei.position(iPosition);
                zza(str, e);
            } catch (IllegalArgumentException e2) {
                throw new zzb(e2);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zze(byte[] bArr, int i, int i2) throws IOException {
            zzbf(i2);
            write(bArr, 0, i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final void zzf(int i, int i2) throws IOException {
            zzb(i, 5);
            zzbh(i2);
        }

        @Override // com.google.android.gms.internal.measurement.zzee
        public final int zztg() {
            return this.zzaei.remaining();
        }
    }

    private zzee() {
    }

    public static int zza(int i, zzfn zzfnVar) {
        int iZzbi = zzbi(i);
        int iZzuk = zzfnVar.zzuk();
        return iZzbi + zzbk(iZzuk) + iZzuk;
    }

    public static int zza(zzfn zzfnVar) {
        int iZzuk = zzfnVar.zzuk();
        return zzbk(iZzuk) + iZzuk;
    }

    public static zzee zza(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            return new zzc(byteBuffer);
        }
        if (!byteBuffer.isDirect() || byteBuffer.isReadOnly()) {
            throw new IllegalArgumentException("ByteBuffer is read-only");
        }
        return zzhv.zzwu() ? new zzd(byteBuffer) : new zze(byteBuffer);
    }

    public static int zzb(float f) {
        return 4;
    }

    public static int zzb(int i, double d) {
        return zzbi(i) + 8;
    }

    public static int zzb(int i, float f) {
        return zzbi(i) + 4;
    }

    public static int zzb(int i, zzfn zzfnVar) {
        return (zzbi(1) << 1) + zzh(2, i) + zza(3, zzfnVar);
    }

    static int zzb(int i, zzgi zzgiVar, zzgx zzgxVar) {
        return zzbi(i) + zzb(zzgiVar, zzgxVar);
    }

    public static int zzb(zzdp zzdpVar) {
        int size = zzdpVar.size();
        return zzbk(size) + size;
    }

    static int zzb(zzgi zzgiVar, zzgx zzgxVar) {
        zzdf zzdfVar = (zzdf) zzgiVar;
        int iZzrt = zzdfVar.zzrt();
        if (iZzrt == -1) {
            iZzrt = zzgxVar.zzt(zzdfVar);
            zzdfVar.zzam(iZzrt);
        }
        return zzbk(iZzrt) + iZzrt;
    }

    public static int zzbi(int i) {
        return zzbk(i << 3);
    }

    public static int zzbj(int i) {
        if (i >= 0) {
            return zzbk(i);
        }
        return 10;
    }

    public static int zzbk(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return (i & (-268435456)) == 0 ? 4 : 5;
    }

    public static int zzbl(int i) {
        return zzbk(zzbp(i));
    }

    public static int zzbm(int i) {
        return 4;
    }

    public static int zzbn(int i) {
        return 4;
    }

    public static int zzbo(int i) {
        return zzbj(i);
    }

    private static int zzbp(int i) {
        return (i >> 31) ^ (i << 1);
    }

    @Deprecated
    public static int zzbq(int i) {
        return zzbk(i);
    }

    public static int zzbq(long j) {
        return zzbr(j);
    }

    public static int zzbr(long j) {
        int i;
        if (((-128) & j) == 0) {
            return 1;
        }
        if (j < 0) {
            return 10;
        }
        if (((-34359738368L) & j) != 0) {
            i = 6;
            j >>>= 28;
        } else {
            i = 2;
        }
        if (((-2097152) & j) != 0) {
            i += 2;
            j >>>= 14;
        }
        return (j & (-16384)) != 0 ? i + 1 : i;
    }

    public static int zzbs(long j) {
        return zzbr(zzbv(j));
    }

    public static int zzbt(long j) {
        return 8;
    }

    public static int zzbu(long j) {
        return 8;
    }

    private static long zzbv(long j) {
        return (j >> 63) ^ (j << 1);
    }

    public static int zzc(int i, zzdp zzdpVar) {
        int iZzbi = zzbi(i);
        int size = zzdpVar.size();
        return iZzbi + zzbk(size) + size;
    }

    public static int zzc(int i, zzgi zzgiVar) {
        return zzbi(i) + zzc(zzgiVar);
    }

    @Deprecated
    static int zzc(int i, zzgi zzgiVar, zzgx zzgxVar) {
        int iZzbi = zzbi(i) << 1;
        zzdf zzdfVar = (zzdf) zzgiVar;
        int iZzrt = zzdfVar.zzrt();
        if (iZzrt == -1) {
            iZzrt = zzgxVar.zzt(zzdfVar);
            zzdfVar.zzam(iZzrt);
        }
        return iZzbi + iZzrt;
    }

    public static int zzc(int i, String str) {
        return zzbi(i) + zzds(str);
    }

    public static int zzc(int i, boolean z) {
        return zzbi(i) + 1;
    }

    public static int zzc(zzgi zzgiVar) {
        int iZzuk = zzgiVar.zzuk();
        return zzbk(iZzuk) + iZzuk;
    }

    public static int zzd(int i, long j) {
        return zzbi(i) + zzbr(j);
    }

    public static int zzd(int i, zzdp zzdpVar) {
        return (zzbi(1) << 1) + zzh(2, i) + zzc(3, zzdpVar);
    }

    public static int zzd(int i, zzgi zzgiVar) {
        return (zzbi(1) << 1) + zzh(2, i) + zzc(3, zzgiVar);
    }

    @Deprecated
    public static int zzd(zzgi zzgiVar) {
        return zzgiVar.zzuk();
    }

    public static int zzds(String str) {
        int length;
        try {
            length = zzhy.zza(str);
        } catch (zzib unused) {
            length = str.getBytes(zzez.UTF_8).length;
        }
        return zzbk(length) + length;
    }

    public static int zze(double d) {
        return 8;
    }

    public static int zze(int i, long j) {
        return zzbi(i) + zzbr(j);
    }

    public static int zzf(int i, long j) {
        return zzbi(i) + zzbr(zzbv(j));
    }

    public static zzee zzf(byte[] bArr) {
        return new zza(bArr, 0, bArr.length);
    }

    public static int zzg(int i, int i2) {
        return zzbi(i) + zzbj(i2);
    }

    public static int zzg(int i, long j) {
        return zzbi(i) + 8;
    }

    public static int zzg(byte[] bArr) {
        int length = bArr.length;
        return zzbk(length) + length;
    }

    public static int zzh(int i, int i2) {
        return zzbi(i) + zzbk(i2);
    }

    public static int zzh(int i, long j) {
        return zzbi(i) + 8;
    }

    public static int zzi(int i, int i2) {
        return zzbi(i) + zzbk(zzbp(i2));
    }

    public static int zzj(int i, int i2) {
        return zzbi(i) + 4;
    }

    public static int zzk(int i, int i2) {
        return zzbi(i) + 4;
    }

    public static int zzl(int i, int i2) {
        return zzbi(i) + zzbj(i2);
    }

    public static int zzr(boolean z) {
        return 1;
    }

    public abstract void flush() throws IOException;

    public abstract void write(byte[] bArr, int i, int i2) throws IOException;

    public final void zza(float f) throws IOException {
        zzbh(Float.floatToRawIntBits(f));
    }

    public final void zza(int i, double d) throws IOException {
        zzc(i, Double.doubleToRawLongBits(d));
    }

    public final void zza(int i, float f) throws IOException {
        zzf(i, Float.floatToRawIntBits(f));
    }

    public abstract void zza(int i, long j) throws IOException;

    public abstract void zza(int i, zzdp zzdpVar) throws IOException;

    public abstract void zza(int i, zzgi zzgiVar) throws IOException;

    abstract void zza(int i, zzgi zzgiVar, zzgx zzgxVar) throws IOException;

    public abstract void zza(zzdp zzdpVar) throws IOException;

    abstract void zza(zzgi zzgiVar, zzgx zzgxVar) throws IOException;

    final void zza(String str, zzib zzibVar) throws IOException {
        logger.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", (Throwable) zzibVar);
        byte[] bytes = str.getBytes(zzez.UTF_8);
        try {
            zzbf(bytes.length);
            zza(bytes, 0, bytes.length);
        } catch (zzb e) {
            throw e;
        } catch (IndexOutOfBoundsException e2) {
            throw new zzb(e2);
        }
    }

    public abstract void zzb(int i, int i2) throws IOException;

    public final void zzb(int i, long j) throws IOException {
        zza(i, zzbv(j));
    }

    public abstract void zzb(int i, zzdp zzdpVar) throws IOException;

    public abstract void zzb(int i, zzgi zzgiVar) throws IOException;

    public abstract void zzb(int i, String str) throws IOException;

    public abstract void zzb(int i, boolean z) throws IOException;

    public abstract void zzb(zzgi zzgiVar) throws IOException;

    public abstract void zzbe(int i) throws IOException;

    public abstract void zzbf(int i) throws IOException;

    public final void zzbg(int i) throws IOException {
        zzbf(zzbp(i));
    }

    public abstract void zzbh(int i) throws IOException;

    public abstract void zzbn(long j) throws IOException;

    public final void zzbo(long j) throws IOException {
        zzbn(zzbv(j));
    }

    public abstract void zzbp(long j) throws IOException;

    public abstract void zzc(byte b) throws IOException;

    public abstract void zzc(int i, int i2) throws IOException;

    public abstract void zzc(int i, long j) throws IOException;

    public final void zzd(double d) throws IOException {
        zzbp(Double.doubleToRawLongBits(d));
    }

    public abstract void zzd(int i, int i2) throws IOException;

    public abstract void zzdr(String str) throws IOException;

    public final void zze(int i, int i2) throws IOException {
        zzd(i, zzbp(i2));
    }

    abstract void zze(byte[] bArr, int i, int i2) throws IOException;

    public abstract void zzf(int i, int i2) throws IOException;

    public final void zzq(boolean z) throws IOException {
        zzc(z ? (byte) 1 : (byte) 0);
    }

    public abstract int zztg();

    public final void zzth() {
        if (zztg() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }
}
