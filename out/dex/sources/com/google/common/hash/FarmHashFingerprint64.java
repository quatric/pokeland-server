package com.google.common.hash;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class FarmHashFingerprint64 extends AbstractNonStreamingHashFunction {

    /* JADX INFO: renamed from: K0 */
    private static final long f323K0 = -4348849565147123417L;

    /* JADX INFO: renamed from: K1 */
    private static final long f324K1 = -5435081209227447693L;

    /* JADX INFO: renamed from: K2 */
    private static final long f325K2 = -7286425919675154353L;

    FarmHashFingerprint64() {
    }

    @VisibleForTesting
    static long fingerprint(byte[] bArr, int i, int i2) {
        if (i2 <= 32) {
            return i2 <= 16 ? hashLength0to16(bArr, i, i2) : hashLength17to32(bArr, i, i2);
        }
        return i2 <= 64 ? hashLength33To64(bArr, i, i2) : hashLength65Plus(bArr, i, i2);
    }

    private static long hashLength0to16(byte[] bArr, int i, int i2) {
        if (i2 >= 8) {
            long j = ((long) (i2 * 2)) + f325K2;
            long jLoad64 = LittleEndianByteArray.load64(bArr, i) + f325K2;
            long jLoad65 = LittleEndianByteArray.load64(bArr, (i + i2) - 8);
            return hashLength16((Long.rotateRight(jLoad65, 37) * j) + jLoad64, (Long.rotateRight(jLoad64, 25) + jLoad65) * j, j);
        }
        if (i2 >= 4) {
            return hashLength16(((long) i2) + ((((long) LittleEndianByteArray.load32(bArr, i)) & 4294967295L) << 3), ((long) LittleEndianByteArray.load32(bArr, (i + i2) - 4)) & 4294967295L, ((long) (i2 * 2)) + f325K2);
        }
        if (i2 <= 0) {
            return f325K2;
        }
        return shiftMix((((long) ((bArr[i] & 255) + ((bArr[(i2 >> 1) + i] & 255) << 8))) * f325K2) ^ (((long) (i2 + ((bArr[i + (i2 - 1)] & 255) << 2))) * f323K0)) * f325K2;
    }

    private static long hashLength16(long j, long j2, long j3) {
        long j4 = (j ^ j2) * j3;
        long j5 = ((j4 ^ (j4 >>> 47)) ^ j2) * j3;
        return (j5 ^ (j5 >>> 47)) * j3;
    }

    private static long hashLength17to32(byte[] bArr, int i, int i2) {
        long j = ((long) (i2 * 2)) + f325K2;
        long jLoad64 = LittleEndianByteArray.load64(bArr, i) * f324K1;
        long jLoad65 = LittleEndianByteArray.load64(bArr, i + 8);
        int i3 = i + i2;
        long jLoad66 = LittleEndianByteArray.load64(bArr, i3 - 8) * j;
        return hashLength16((LittleEndianByteArray.load64(bArr, i3 - 16) * f325K2) + Long.rotateRight(jLoad64 + jLoad65, 43) + Long.rotateRight(jLoad66, 30), jLoad64 + Long.rotateRight(jLoad65 + f325K2, 18) + jLoad66, j);
    }

    private static long hashLength33To64(byte[] bArr, int i, int i2) {
        long j = ((long) (i2 * 2)) + f325K2;
        long jLoad64 = LittleEndianByteArray.load64(bArr, i) * f325K2;
        long jLoad65 = LittleEndianByteArray.load64(bArr, i + 8);
        int i3 = i + i2;
        long jLoad66 = LittleEndianByteArray.load64(bArr, i3 - 8) * j;
        long jRotateRight = Long.rotateRight(jLoad64 + jLoad65, 43) + Long.rotateRight(jLoad66, 30) + (LittleEndianByteArray.load64(bArr, i3 - 16) * f325K2);
        long jHashLength16 = hashLength16(jRotateRight, jLoad66 + Long.rotateRight(jLoad65 + f325K2, 18) + jLoad64, j);
        long jLoad67 = LittleEndianByteArray.load64(bArr, i + 16) * j;
        long jLoad68 = LittleEndianByteArray.load64(bArr, i + 24);
        long jLoad69 = (jRotateRight + LittleEndianByteArray.load64(bArr, i3 - 32)) * j;
        return hashLength16(((jHashLength16 + LittleEndianByteArray.load64(bArr, i3 - 24)) * j) + Long.rotateRight(jLoad67 + jLoad68, 43) + Long.rotateRight(jLoad69, 30), jLoad67 + Long.rotateRight(jLoad68 + jLoad64, 18) + jLoad69, j);
    }

    private static long hashLength65Plus(byte[] bArr, int i, int i2) {
        long jShiftMix = shiftMix(-7956866745689871395L) * f325K2;
        long[] jArr = new long[2];
        long[] jArr2 = new long[2];
        long jLoad64 = 95310865018149119L + LittleEndianByteArray.load64(bArr, i);
        int i3 = i2 - 1;
        int i4 = i + ((i3 / 64) * 64);
        int i5 = i3 & 63;
        int i6 = (i4 + i5) - 63;
        long j = 2480279821605975764L;
        int i7 = i;
        while (true) {
            long jRotateRight = Long.rotateRight(jLoad64 + j + jArr[0] + LittleEndianByteArray.load64(bArr, i7 + 8), 37) * f324K1;
            long jRotateRight2 = Long.rotateRight(j + jArr[1] + LittleEndianByteArray.load64(bArr, i7 + 48), 42) * f324K1;
            long j2 = jRotateRight ^ jArr2[1];
            long jLoad65 = jRotateRight2 + jArr[0] + LittleEndianByteArray.load64(bArr, i7 + 40);
            long jRotateRight3 = Long.rotateRight(jShiftMix + jArr2[0], 33) * f324K1;
            weakHashLength32WithSeeds(bArr, i7, jArr[1] * f324K1, j2 + jArr2[0], jArr);
            weakHashLength32WithSeeds(bArr, i7 + 32, jRotateRight3 + jArr2[1], jLoad65 + LittleEndianByteArray.load64(bArr, i7 + 16), jArr2);
            i7 += 64;
            if (i7 == i4) {
                long j3 = ((j2 & 255) << 1) + f324K1;
                jArr2[0] = jArr2[0] + ((long) i5);
                jArr[0] = jArr[0] + jArr2[0];
                jArr2[0] = jArr2[0] + jArr[0];
                long jRotateRight4 = Long.rotateRight(jRotateRight3 + jLoad65 + jArr[0] + LittleEndianByteArray.load64(bArr, i6 + 8), 37) * j3;
                long jRotateRight5 = Long.rotateRight(jLoad65 + jArr[1] + LittleEndianByteArray.load64(bArr, i6 + 48), 42) * j3;
                long j4 = jRotateRight4 ^ (jArr2[1] * 9);
                long jLoad66 = jRotateRight5 + (jArr[0] * 9) + LittleEndianByteArray.load64(bArr, i6 + 40);
                long jRotateRight6 = Long.rotateRight(j2 + jArr2[0], 33) * j3;
                weakHashLength32WithSeeds(bArr, i6, jArr[1] * j3, j4 + jArr2[0], jArr);
                weakHashLength32WithSeeds(bArr, i6 + 32, jRotateRight6 + jArr2[1], LittleEndianByteArray.load64(bArr, i6 + 16) + jLoad66, jArr2);
                return hashLength16(hashLength16(jArr[0], jArr2[0], j3) + (shiftMix(jLoad66) * f323K0) + j4, hashLength16(jArr[1], jArr2[1], j3) + jRotateRight6, j3);
            }
            jShiftMix = j2;
            j = jLoad65;
            jLoad64 = jRotateRight3;
        }
    }

    private static long shiftMix(long j) {
        return j ^ (j >>> 47);
    }

    private static void weakHashLength32WithSeeds(byte[] bArr, int i, long j, long j2, long[] jArr) {
        long jLoad64 = LittleEndianByteArray.load64(bArr, i);
        long jLoad65 = LittleEndianByteArray.load64(bArr, i + 8);
        long jLoad66 = LittleEndianByteArray.load64(bArr, i + 16);
        long jLoad67 = LittleEndianByteArray.load64(bArr, i + 24);
        long j3 = j + jLoad64;
        long j4 = jLoad65 + j3 + jLoad66;
        long jRotateRight = Long.rotateRight(j2 + j3 + jLoad67, 21) + Long.rotateRight(j4, 44);
        jArr[0] = j4 + jLoad67;
        jArr[1] = jRotateRight + j3;
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return 64;
    }

    @Override // com.google.common.hash.HashFunction
    public HashCode hashBytes(byte[] bArr, int i, int i2) {
        Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
        return HashCode.fromLong(fingerprint(bArr, i, i2));
    }

    public String toString() {
        return "Hashing.farmHashFingerprint64()";
    }
}
