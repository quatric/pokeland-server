package com.google.common.hash;

import com.google.common.primitives.UnsignedBytes;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class Murmur3_128HashFunction extends AbstractStreamingHashFunction implements Serializable {
    private static final long serialVersionUID = 0;
    private final int seed;

    private static final class Murmur3_128Hasher extends AbstractStreamingHashFunction.AbstractStreamingHasher {

        /* JADX INFO: renamed from: C1 */
        private static final long f326C1 = -8663945395140668459L;

        /* JADX INFO: renamed from: C2 */
        private static final long f327C2 = 5545529020109919103L;
        private static final int CHUNK_SIZE = 16;

        /* JADX INFO: renamed from: h1 */
        private long f328h1;

        /* JADX INFO: renamed from: h2 */
        private long f329h2;
        private int length;

        Murmur3_128Hasher(int i) {
            super(16);
            long j = i;
            this.f328h1 = j;
            this.f329h2 = j;
            this.length = 0;
        }

        private void bmix64(long j, long j2) {
            this.f328h1 = mixK1(j) ^ this.f328h1;
            this.f328h1 = Long.rotateLeft(this.f328h1, 27);
            long j3 = this.f328h1;
            long j4 = this.f329h2;
            this.f328h1 = j3 + j4;
            this.f328h1 = (this.f328h1 * 5) + 1390208809;
            this.f329h2 = mixK2(j2) ^ j4;
            this.f329h2 = Long.rotateLeft(this.f329h2, 31);
            this.f329h2 += this.f328h1;
            this.f329h2 = (this.f329h2 * 5) + 944331445;
        }

        private static long fmix64(long j) {
            long j2 = (j ^ (j >>> 33)) * (-49064778989728563L);
            long j3 = (j2 ^ (j2 >>> 33)) * (-4265267296055464877L);
            return j3 ^ (j3 >>> 33);
        }

        private static long mixK1(long j) {
            return Long.rotateLeft(j * f326C1, 31) * f327C2;
        }

        private static long mixK2(long j) {
            return Long.rotateLeft(j * f327C2, 33) * f326C1;
        }

        @Override // com.google.common.hash.AbstractStreamingHashFunction.AbstractStreamingHasher
        public HashCode makeHash() {
            long j = this.f328h1;
            int i = this.length;
            this.f328h1 = j ^ ((long) i);
            this.f329h2 ^= (long) i;
            long j2 = this.f328h1;
            long j3 = this.f329h2;
            this.f328h1 = j2 + j3;
            long j4 = this.f328h1;
            this.f329h2 = j3 + j4;
            this.f328h1 = fmix64(j4);
            this.f329h2 = fmix64(this.f329h2);
            long j5 = this.f328h1;
            long j6 = this.f329h2;
            this.f328h1 = j5 + j6;
            this.f329h2 = j6 + this.f328h1;
            return HashCode.fromBytesNoCopy(ByteBuffer.wrap(new byte[16]).order(ByteOrder.LITTLE_ENDIAN).putLong(this.f328h1).putLong(this.f329h2).array());
        }

        @Override // com.google.common.hash.AbstractStreamingHashFunction.AbstractStreamingHasher
        protected void process(ByteBuffer byteBuffer) {
            bmix64(byteBuffer.getLong(), byteBuffer.getLong());
            this.length += 16;
        }

        @Override // com.google.common.hash.AbstractStreamingHashFunction.AbstractStreamingHasher
        protected void processRemaining(ByteBuffer byteBuffer) {
            long j;
            long j2;
            long j3;
            long j4;
            long j5;
            long j6;
            long j7;
            long j8;
            long j9;
            long j10;
            long j11;
            long j12;
            long j13;
            long j14;
            this.length += byteBuffer.remaining();
            switch (byteBuffer.remaining()) {
                case 1:
                    j = 0;
                    j7 = ((long) UnsignedBytes.toInt(byteBuffer.get(0))) ^ j;
                    j8 = 0;
                    this.f328h1 = mixK1(j7) ^ this.f328h1;
                    this.f329h2 = mixK2(j8) ^ this.f329h2;
                    return;
                case 2:
                    j2 = 0;
                    j = j2 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(1))) << 8);
                    j7 = ((long) UnsignedBytes.toInt(byteBuffer.get(0))) ^ j;
                    j8 = 0;
                    this.f328h1 = mixK1(j7) ^ this.f328h1;
                    this.f329h2 = mixK2(j8) ^ this.f329h2;
                    return;
                case 3:
                    j3 = 0;
                    j2 = j3 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(2))) << 16);
                    j = j2 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(1))) << 8);
                    j7 = ((long) UnsignedBytes.toInt(byteBuffer.get(0))) ^ j;
                    j8 = 0;
                    this.f328h1 = mixK1(j7) ^ this.f328h1;
                    this.f329h2 = mixK2(j8) ^ this.f329h2;
                    return;
                case 4:
                    j4 = 0;
                    j3 = j4 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(3))) << 24);
                    j2 = j3 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(2))) << 16);
                    j = j2 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(1))) << 8);
                    j7 = ((long) UnsignedBytes.toInt(byteBuffer.get(0))) ^ j;
                    j8 = 0;
                    this.f328h1 = mixK1(j7) ^ this.f328h1;
                    this.f329h2 = mixK2(j8) ^ this.f329h2;
                    return;
                case 5:
                    j5 = 0;
                    j4 = j5 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(4))) << 32);
                    j3 = j4 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(3))) << 24);
                    j2 = j3 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(2))) << 16);
                    j = j2 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(1))) << 8);
                    j7 = ((long) UnsignedBytes.toInt(byteBuffer.get(0))) ^ j;
                    j8 = 0;
                    this.f328h1 = mixK1(j7) ^ this.f328h1;
                    this.f329h2 = mixK2(j8) ^ this.f329h2;
                    return;
                case 6:
                    j6 = 0;
                    j5 = j6 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(5))) << 40);
                    j4 = j5 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(4))) << 32);
                    j3 = j4 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(3))) << 24);
                    j2 = j3 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(2))) << 16);
                    j = j2 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(1))) << 8);
                    j7 = ((long) UnsignedBytes.toInt(byteBuffer.get(0))) ^ j;
                    j8 = 0;
                    this.f328h1 = mixK1(j7) ^ this.f328h1;
                    this.f329h2 = mixK2(j8) ^ this.f329h2;
                    return;
                case 7:
                    j6 = (((long) UnsignedBytes.toInt(byteBuffer.get(6))) << 48) ^ 0;
                    j5 = j6 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(5))) << 40);
                    j4 = j5 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(4))) << 32);
                    j3 = j4 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(3))) << 24);
                    j2 = j3 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(2))) << 16);
                    j = j2 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(1))) << 8);
                    j7 = ((long) UnsignedBytes.toInt(byteBuffer.get(0))) ^ j;
                    j8 = 0;
                    this.f328h1 = mixK1(j7) ^ this.f328h1;
                    this.f329h2 = mixK2(j8) ^ this.f329h2;
                    return;
                case 8:
                    j8 = 0;
                    j7 = byteBuffer.getLong() ^ 0;
                    this.f328h1 = mixK1(j7) ^ this.f328h1;
                    this.f329h2 = mixK2(j8) ^ this.f329h2;
                    return;
                case 9:
                    j9 = 0;
                    j8 = j9 ^ ((long) UnsignedBytes.toInt(byteBuffer.get(8)));
                    j7 = byteBuffer.getLong() ^ 0;
                    this.f328h1 = mixK1(j7) ^ this.f328h1;
                    this.f329h2 = mixK2(j8) ^ this.f329h2;
                    return;
                case 10:
                    j10 = 0;
                    j9 = j10 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(9))) << 8);
                    j8 = j9 ^ ((long) UnsignedBytes.toInt(byteBuffer.get(8)));
                    j7 = byteBuffer.getLong() ^ 0;
                    this.f328h1 = mixK1(j7) ^ this.f328h1;
                    this.f329h2 = mixK2(j8) ^ this.f329h2;
                    return;
                case 11:
                    j11 = 0;
                    j10 = j11 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(10))) << 16);
                    j9 = j10 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(9))) << 8);
                    j8 = j9 ^ ((long) UnsignedBytes.toInt(byteBuffer.get(8)));
                    j7 = byteBuffer.getLong() ^ 0;
                    this.f328h1 = mixK1(j7) ^ this.f328h1;
                    this.f329h2 = mixK2(j8) ^ this.f329h2;
                    return;
                case 12:
                    j12 = 0;
                    j11 = j12 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(11))) << 24);
                    j10 = j11 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(10))) << 16);
                    j9 = j10 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(9))) << 8);
                    j8 = j9 ^ ((long) UnsignedBytes.toInt(byteBuffer.get(8)));
                    j7 = byteBuffer.getLong() ^ 0;
                    this.f328h1 = mixK1(j7) ^ this.f328h1;
                    this.f329h2 = mixK2(j8) ^ this.f329h2;
                    return;
                case 13:
                    j13 = 0;
                    j12 = j13 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(12))) << 32);
                    j11 = j12 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(11))) << 24);
                    j10 = j11 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(10))) << 16);
                    j9 = j10 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(9))) << 8);
                    j8 = j9 ^ ((long) UnsignedBytes.toInt(byteBuffer.get(8)));
                    j7 = byteBuffer.getLong() ^ 0;
                    this.f328h1 = mixK1(j7) ^ this.f328h1;
                    this.f329h2 = mixK2(j8) ^ this.f329h2;
                    return;
                case 14:
                    j14 = 0;
                    j13 = j14 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(13))) << 40);
                    j12 = j13 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(12))) << 32);
                    j11 = j12 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(11))) << 24);
                    j10 = j11 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(10))) << 16);
                    j9 = j10 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(9))) << 8);
                    j8 = j9 ^ ((long) UnsignedBytes.toInt(byteBuffer.get(8)));
                    j7 = byteBuffer.getLong() ^ 0;
                    this.f328h1 = mixK1(j7) ^ this.f328h1;
                    this.f329h2 = mixK2(j8) ^ this.f329h2;
                    return;
                case 15:
                    j14 = (((long) UnsignedBytes.toInt(byteBuffer.get(14))) << 48) ^ 0;
                    j13 = j14 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(13))) << 40);
                    j12 = j13 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(12))) << 32);
                    j11 = j12 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(11))) << 24);
                    j10 = j11 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(10))) << 16);
                    j9 = j10 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(9))) << 8);
                    j8 = j9 ^ ((long) UnsignedBytes.toInt(byteBuffer.get(8)));
                    j7 = byteBuffer.getLong() ^ 0;
                    this.f328h1 = mixK1(j7) ^ this.f328h1;
                    this.f329h2 = mixK2(j8) ^ this.f329h2;
                    return;
                default:
                    throw new AssertionError("Should never get here.");
            }
        }
    }

    Murmur3_128HashFunction(int i) {
        this.seed = i;
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return 128;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof Murmur3_128HashFunction) && this.seed == ((Murmur3_128HashFunction) obj).seed;
    }

    public int hashCode() {
        return getClass().hashCode() ^ this.seed;
    }

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher() {
        return new Murmur3_128Hasher(this.seed);
    }

    public String toString() {
        return "Hashing.murmur3_128(" + this.seed + ")";
    }
}
