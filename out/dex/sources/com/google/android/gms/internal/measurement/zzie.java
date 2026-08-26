package com.google.android.gms.internal.measurement;

import com.google.common.base.Ascii;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzie extends zzhz {
    zzie() {
    }

    private static int zza(byte[] bArr, int i, long j, int i2) {
        if (i2 == 0) {
            return zzhy.zzch(i);
        }
        if (i2 == 1) {
            return zzhy.zzr(i, zzhv.zza(bArr, j));
        }
        if (i2 == 2) {
            return zzhy.zzc(i, zzhv.zza(bArr, j), zzhv.zza(bArr, j + 1));
        }
        throw new AssertionError();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.internal.measurement.zzhz
    final int zzb(int i, byte[] bArr, int i2, int i3) {
        int i4;
        long j;
        long j2;
        if ((i2 | i3 | (bArr.length - i3)) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("Array length=%d, index=%d, limit=%d", Integer.valueOf(bArr.length), Integer.valueOf(i2), Integer.valueOf(i3)));
        }
        long j3 = i2;
        int i5 = (int) (((long) i3) - j3);
        if (i5 >= 16) {
            long j4 = j3;
            i4 = 0;
            while (true) {
                if (i4 >= i5) {
                    i4 = i5;
                    break;
                }
                long j5 = j4 + 1;
                if (zzhv.zza(bArr, j4) < 0) {
                    break;
                }
                i4++;
                j4 = j5;
            }
        } else {
            i4 = 0;
        }
        int i6 = i5 - i4;
        long j6 = j3 + ((long) i4);
        while (true) {
            byte bZza = 0;
            while (true) {
                if (i6 <= 0) {
                    j = j6;
                    break;
                }
                j = j6 + 1;
                bZza = zzhv.zza(bArr, j6);
                if (bZza < 0) {
                    break;
                }
                i6--;
                j6 = j;
            }
            if (i6 == 0) {
                return 0;
            }
            int i7 = i6 - 1;
            if (bZza < -32) {
                if (i7 == 0) {
                    return bZza;
                }
                i6 = i7 - 1;
                if (bZza >= -62) {
                    j2 = j + 1;
                    if (zzhv.zza(bArr, j) > -65) {
                    }
                }
                return -1;
            }
            if (bZza >= -16) {
                if (i7 < 3) {
                    return zza(bArr, bZza, j, i7);
                }
                i6 = i7 - 3;
                long j7 = j + 1;
                byte bZza2 = zzhv.zza(bArr, j);
                if (bZza2 <= -65 && (((bZza << Ascii.f285FS) + (bZza2 + 112)) >> 30) == 0) {
                    long j8 = j7 + 1;
                    if (zzhv.zza(bArr, j7) <= -65) {
                        j2 = j8 + 1;
                        if (zzhv.zza(bArr, j8) > -65) {
                        }
                    }
                }
                return -1;
            }
            if (i7 < 2) {
                return zza(bArr, bZza, j, i7);
            }
            i6 = i7 - 2;
            long j9 = j + 1;
            byte bZza3 = zzhv.zza(bArr, j);
            if (bZza3 <= -65 && ((bZza != -32 || bZza3 >= -96) && (bZza != -19 || bZza3 < -96))) {
                long j10 = j9 + 1;
                if (zzhv.zza(bArr, j9) <= -65) {
                    j6 = j10;
                }
            }
            return -1;
            j6 = j2;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.internal.measurement.zzhz
    final int zzb(CharSequence charSequence, byte[] bArr, int i, int i2) {
        char c;
        long j;
        long j2;
        long j3;
        int i3;
        char cCharAt;
        long j4 = i;
        long j5 = ((long) i2) + j4;
        int length = charSequence.length();
        if (length > i2 || bArr.length - i2 < i) {
            char cCharAt2 = charSequence.charAt(length - 1);
            StringBuilder sb = new StringBuilder(37);
            sb.append("Failed writing ");
            sb.append(cCharAt2);
            sb.append(" at index ");
            sb.append(i + i2);
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }
        int i4 = 0;
        while (true) {
            c = 128;
            j = 1;
            if (i4 >= length || (cCharAt = charSequence.charAt(i4)) >= 128) {
                break;
            }
            zzhv.zza(bArr, j4, (byte) cCharAt);
            i4++;
            j4 = 1 + j4;
        }
        if (i4 == length) {
            return (int) j4;
        }
        while (i4 < length) {
            char cCharAt3 = charSequence.charAt(i4);
            if (cCharAt3 >= c || j4 >= j5) {
                if (cCharAt3 < 2048 && j4 <= j5 - 2) {
                    long j6 = j4 + j;
                    zzhv.zza(bArr, j4, (byte) ((cCharAt3 >>> 6) | 960));
                    zzhv.zza(bArr, j6, (byte) ((cCharAt3 & '?') | 128));
                    j2 = j6 + j;
                    j3 = j;
                } else {
                    if ((cCharAt3 >= 55296 && 57343 >= cCharAt3) || j4 > j5 - 3) {
                        if (j4 > j5 - 4) {
                            if (55296 <= cCharAt3 && cCharAt3 <= 57343 && ((i3 = i4 + 1) == length || !Character.isSurrogatePair(cCharAt3, charSequence.charAt(i3)))) {
                                throw new zzib(i4, length);
                            }
                            StringBuilder sb2 = new StringBuilder(46);
                            sb2.append("Failed writing ");
                            sb2.append(cCharAt3);
                            sb2.append(" at index ");
                            sb2.append(j4);
                            throw new ArrayIndexOutOfBoundsException(sb2.toString());
                        }
                        int i5 = i4 + 1;
                        if (i5 != length) {
                            char cCharAt4 = charSequence.charAt(i5);
                            if (Character.isSurrogatePair(cCharAt3, cCharAt4)) {
                                int codePoint = Character.toCodePoint(cCharAt3, cCharAt4);
                                long j7 = j4 + 1;
                                zzhv.zza(bArr, j4, (byte) ((codePoint >>> 18) | 240));
                                long j8 = j7 + 1;
                                zzhv.zza(bArr, j7, (byte) (((codePoint >>> 12) & 63) | 128));
                                long j9 = j8 + 1;
                                zzhv.zza(bArr, j8, (byte) (((codePoint >>> 6) & 63) | 128));
                                j3 = 1;
                                j2 = j9 + 1;
                                zzhv.zza(bArr, j9, (byte) ((codePoint & 63) | 128));
                                i4 = i5;
                            } else {
                                i4 = i5;
                            }
                        }
                        throw new zzib(i4 - 1, length);
                    }
                    long j10 = j4 + j;
                    zzhv.zza(bArr, j4, (byte) ((cCharAt3 >>> '\f') | 480));
                    long j11 = j10 + j;
                    zzhv.zza(bArr, j10, (byte) (((cCharAt3 >>> 6) & 63) | 128));
                    zzhv.zza(bArr, j11, (byte) ((cCharAt3 & '?') | 128));
                    j2 = j11 + 1;
                    j3 = 1;
                }
                i4++;
                c = 128;
                long j12 = j3;
                j4 = j2;
                j = j12;
            } else {
                long j13 = j4 + j;
                zzhv.zza(bArr, j4, (byte) cCharAt3);
                j3 = j;
                j2 = j13;
            }
            i4++;
            c = 128;
            long j14 = j3;
            j4 = j2;
            j = j14;
        }
        return (int) j4;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.internal.measurement.zzhz
    final void zzb(CharSequence charSequence, ByteBuffer byteBuffer) {
        char c;
        long j;
        int i;
        char cCharAt;
        long jZzb = zzhv.zzb(byteBuffer);
        long jPosition = ((long) byteBuffer.position()) + jZzb;
        long jLimit = ((long) byteBuffer.limit()) + jZzb;
        int length = charSequence.length();
        if (length > jLimit - jPosition) {
            char cCharAt2 = charSequence.charAt(length - 1);
            int iLimit = byteBuffer.limit();
            StringBuilder sb = new StringBuilder(37);
            sb.append("Failed writing ");
            sb.append(cCharAt2);
            sb.append(" at index ");
            sb.append(iLimit);
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }
        int i2 = 0;
        while (true) {
            c = 128;
            if (i2 >= length || (cCharAt = charSequence.charAt(i2)) >= 128) {
                break;
            }
            zzhv.zza(jPosition, (byte) cCharAt);
            i2++;
            jPosition = 1 + jPosition;
        }
        if (i2 == length) {
            byteBuffer.position((int) (jPosition - jZzb));
            return;
        }
        while (i2 < length) {
            char cCharAt3 = charSequence.charAt(i2);
            if (cCharAt3 < c && jPosition < jLimit) {
                zzhv.zza(jPosition, (byte) cCharAt3);
                jPosition++;
                j = jZzb;
            } else if (cCharAt3 >= 2048 || jPosition > jLimit - 2) {
                j = jZzb;
                if ((cCharAt3 >= 55296 && 57343 >= cCharAt3) || jPosition > jLimit - 3) {
                    if (jPosition > jLimit - 4) {
                        if (55296 <= cCharAt3 && cCharAt3 <= 57343 && ((i = i2 + 1) == length || !Character.isSurrogatePair(cCharAt3, charSequence.charAt(i)))) {
                            throw new zzib(i2, length);
                        }
                        StringBuilder sb2 = new StringBuilder(46);
                        sb2.append("Failed writing ");
                        sb2.append(cCharAt3);
                        sb2.append(" at index ");
                        sb2.append(jPosition);
                        throw new ArrayIndexOutOfBoundsException(sb2.toString());
                    }
                    int i3 = i2 + 1;
                    if (i3 != length) {
                        char cCharAt4 = charSequence.charAt(i3);
                        if (Character.isSurrogatePair(cCharAt3, cCharAt4)) {
                            int codePoint = Character.toCodePoint(cCharAt3, cCharAt4);
                            long j2 = jPosition + 1;
                            zzhv.zza(jPosition, (byte) ((codePoint >>> 18) | 240));
                            long j3 = j2 + 1;
                            zzhv.zza(j2, (byte) (((codePoint >>> 12) & 63) | 128));
                            long j4 = j3 + 1;
                            zzhv.zza(j3, (byte) (((codePoint >>> 6) & 63) | 128));
                            long j5 = j4 + 1;
                            zzhv.zza(j4, (byte) ((codePoint & 63) | 128));
                            i2 = i3;
                            jPosition = j5;
                        }
                    } else {
                        i3 = i2;
                    }
                    throw new zzib(i3 - 1, length);
                }
                long j6 = jPosition + 1;
                zzhv.zza(jPosition, (byte) ((cCharAt3 >>> '\f') | 480));
                long j7 = j6 + 1;
                zzhv.zza(j6, (byte) (((cCharAt3 >>> 6) & 63) | 128));
                zzhv.zza(j7, (byte) ((cCharAt3 & '?') | 128));
                jPosition = j7 + 1;
            } else {
                j = jZzb;
                long j8 = jPosition + 1;
                zzhv.zza(jPosition, (byte) ((cCharAt3 >>> 6) | 960));
                zzhv.zza(j8, (byte) ((cCharAt3 & '?') | 128));
                jPosition = j8 + 1;
            }
            i2++;
            jZzb = j;
            c = 128;
        }
        byteBuffer.position((int) (jPosition - jZzb));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.internal.measurement.zzhz
    final String zzh(byte[] bArr, int i, int i2) throws zzfi {
        if ((i | i2 | ((bArr.length - i) - i2)) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)));
        }
        int i3 = i + i2;
        char[] cArr = new char[i2];
        int i4 = 0;
        while (i < i3) {
            byte bZza = zzhv.zza(bArr, i);
            if (!zzia.zzd(bZza)) {
                break;
            }
            i++;
            zzia.zza(bZza, cArr, i4);
            i4++;
        }
        int i5 = i4;
        while (i < i3) {
            int i6 = i + 1;
            byte bZza2 = zzhv.zza(bArr, i);
            if (zzia.zzd(bZza2)) {
                int i7 = i5 + 1;
                zzia.zza(bZza2, cArr, i5);
                while (i6 < i3) {
                    byte bZza3 = zzhv.zza(bArr, i6);
                    if (!zzia.zzd(bZza3)) {
                        break;
                    }
                    i6++;
                    zzia.zza(bZza3, cArr, i7);
                    i7++;
                }
                i = i6;
                i5 = i7;
            } else if (zzia.zze(bZza2)) {
                if (i6 >= i3) {
                    throw zzfi.zzvb();
                }
                zzia.zza(bZza2, zzhv.zza(bArr, i6), cArr, i5);
                i = i6 + 1;
                i5++;
            } else if (zzia.zzf(bZza2)) {
                if (i6 >= i3 - 1) {
                    throw zzfi.zzvb();
                }
                int i8 = i6 + 1;
                zzia.zza(bZza2, zzhv.zza(bArr, i6), zzhv.zza(bArr, i8), cArr, i5);
                i = i8 + 1;
                i5++;
            } else {
                if (i6 >= i3 - 2) {
                    throw zzfi.zzvb();
                }
                int i9 = i6 + 1;
                byte bZza4 = zzhv.zza(bArr, i6);
                int i10 = i9 + 1;
                zzia.zza(bZza2, bZza4, zzhv.zza(bArr, i9), zzhv.zza(bArr, i10), cArr, i5);
                i = i10 + 1;
                i5 = i5 + 1 + 1;
            }
        }
        return new String(cArr, 0, i5);
    }
}
