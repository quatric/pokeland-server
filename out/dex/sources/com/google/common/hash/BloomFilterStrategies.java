package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.math.RoundingMode;
import java.util.Arrays;
import javax.annotation.Nullable;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
enum BloomFilterStrategies implements BloomFilter.Strategy {
    MURMUR128_MITZ_32 { // from class: com.google.common.hash.BloomFilterStrategies.1
        @Override // com.google.common.hash.BloomFilter.Strategy
        public <T> boolean mightContain(T t, Funnel<? super T> funnel, int i, BitArray bitArray) {
            long jBitSize = bitArray.bitSize();
            long jAsLong = Hashing.murmur3_128().hashObject(t, funnel).asLong();
            int i2 = (int) jAsLong;
            int i3 = (int) (jAsLong >>> 32);
            for (int i4 = 1; i4 <= i; i4++) {
                int i5 = (i4 * i3) + i2;
                if (i5 < 0) {
                    i5 ^= -1;
                }
                if (!bitArray.get(((long) i5) % jBitSize)) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.common.hash.BloomFilter.Strategy
        public <T> boolean put(T t, Funnel<? super T> funnel, int i, BitArray bitArray) {
            long jBitSize = bitArray.bitSize();
            long jAsLong = Hashing.murmur3_128().hashObject(t, funnel).asLong();
            int i2 = (int) jAsLong;
            int i3 = (int) (jAsLong >>> 32);
            boolean z = false;
            for (int i4 = 1; i4 <= i; i4++) {
                int i5 = (i4 * i3) + i2;
                if (i5 < 0) {
                    i5 ^= -1;
                }
                z |= bitArray.set(((long) i5) % jBitSize);
            }
            return z;
        }
    },
    MURMUR128_MITZ_64 { // from class: com.google.common.hash.BloomFilterStrategies.2
        private long lowerEight(byte[] bArr) {
            return Longs.fromBytes(bArr[7], bArr[6], bArr[5], bArr[4], bArr[3], bArr[2], bArr[1], bArr[0]);
        }

        private long upperEight(byte[] bArr) {
            return Longs.fromBytes(bArr[15], bArr[14], bArr[13], bArr[12], bArr[11], bArr[10], bArr[9], bArr[8]);
        }

        @Override // com.google.common.hash.BloomFilter.Strategy
        public <T> boolean mightContain(T t, Funnel<? super T> funnel, int i, BitArray bitArray) {
            long jBitSize = bitArray.bitSize();
            byte[] bytesInternal = Hashing.murmur3_128().hashObject(t, funnel).getBytesInternal();
            long jLowerEight = lowerEight(bytesInternal);
            long jUpperEight = upperEight(bytesInternal);
            long j = jLowerEight;
            for (int i2 = 0; i2 < i; i2++) {
                if (!bitArray.get((LongCompanionObject.MAX_VALUE & j) % jBitSize)) {
                    return false;
                }
                j += jUpperEight;
            }
            return true;
        }

        @Override // com.google.common.hash.BloomFilter.Strategy
        public <T> boolean put(T t, Funnel<? super T> funnel, int i, BitArray bitArray) {
            long jBitSize = bitArray.bitSize();
            byte[] bytesInternal = Hashing.murmur3_128().hashObject(t, funnel).getBytesInternal();
            long jLowerEight = lowerEight(bytesInternal);
            long jUpperEight = upperEight(bytesInternal);
            long j = jLowerEight;
            boolean z = false;
            for (int i2 = 0; i2 < i; i2++) {
                z |= bitArray.set((LongCompanionObject.MAX_VALUE & j) % jBitSize);
                j += jUpperEight;
            }
            return z;
        }
    };

    static final class BitArray {
        long bitCount;
        final long[] data;

        BitArray(long j) {
            this(new long[Ints.checkedCast(LongMath.divide(j, 64L, RoundingMode.CEILING))]);
        }

        BitArray(long[] jArr) {
            Preconditions.checkArgument(jArr.length > 0, "data length is zero!");
            this.data = jArr;
            long jBitCount = 0;
            for (long j : jArr) {
                jBitCount += (long) Long.bitCount(j);
            }
            this.bitCount = jBitCount;
        }

        long bitCount() {
            return this.bitCount;
        }

        long bitSize() {
            return ((long) this.data.length) * 64;
        }

        BitArray copy() {
            return new BitArray((long[]) this.data.clone());
        }

        public boolean equals(@Nullable Object obj) {
            if (obj instanceof BitArray) {
                return Arrays.equals(this.data, ((BitArray) obj).data);
            }
            return false;
        }

        boolean get(long j) {
            return ((1 << ((int) j)) & this.data[(int) (j >>> 6)]) != 0;
        }

        public int hashCode() {
            return Arrays.hashCode(this.data);
        }

        void putAll(BitArray bitArray) {
            int i = 0;
            Preconditions.checkArgument(this.data.length == bitArray.data.length, "BitArrays must be of equal length (%s != %s)", this.data.length, bitArray.data.length);
            this.bitCount = 0L;
            while (true) {
                long[] jArr = this.data;
                if (i >= jArr.length) {
                    return;
                }
                jArr[i] = jArr[i] | bitArray.data[i];
                this.bitCount += (long) Long.bitCount(jArr[i]);
                i++;
            }
        }

        boolean set(long j) {
            if (get(j)) {
                return false;
            }
            long[] jArr = this.data;
            int i = (int) (j >>> 6);
            jArr[i] = (1 << ((int) j)) | jArr[i];
            this.bitCount++;
            return true;
        }
    }
}
