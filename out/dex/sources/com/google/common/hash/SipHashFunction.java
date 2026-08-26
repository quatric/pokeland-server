package com.google.common.hash;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class SipHashFunction extends AbstractStreamingHashFunction implements Serializable {
    private static final long serialVersionUID = 0;

    /* JADX INFO: renamed from: c */
    private final int f333c;

    /* JADX INFO: renamed from: d */
    private final int f334d;

    /* JADX INFO: renamed from: k0 */
    private final long f335k0;

    /* JADX INFO: renamed from: k1 */
    private final long f336k1;

    private static final class SipHasher extends AbstractStreamingHashFunction.AbstractStreamingHasher {
        private static final int CHUNK_SIZE = 8;

        /* JADX INFO: renamed from: b */
        private long f337b;

        /* JADX INFO: renamed from: c */
        private final int f338c;

        /* JADX INFO: renamed from: d */
        private final int f339d;
        private long finalM;

        /* JADX INFO: renamed from: v0 */
        private long f340v0;

        /* JADX INFO: renamed from: v1 */
        private long f341v1;

        /* JADX INFO: renamed from: v2 */
        private long f342v2;

        /* JADX INFO: renamed from: v3 */
        private long f343v3;

        SipHasher(int i, int i2, long j, long j2) {
            super(8);
            this.f340v0 = 8317987319222330741L;
            this.f341v1 = 7237128888997146477L;
            this.f342v2 = 7816392313619706465L;
            this.f343v3 = 8387220255154660723L;
            this.f337b = 0L;
            this.finalM = 0L;
            this.f338c = i;
            this.f339d = i2;
            this.f340v0 ^= j;
            this.f341v1 ^= j2;
            this.f342v2 ^= j;
            this.f343v3 ^= j2;
        }

        private void processM(long j) {
            this.f343v3 ^= j;
            sipRound(this.f338c);
            this.f340v0 = j ^ this.f340v0;
        }

        private void sipRound(int i) {
            for (int i2 = 0; i2 < i; i2++) {
                long j = this.f340v0;
                long j2 = this.f341v1;
                this.f340v0 = j + j2;
                this.f342v2 += this.f343v3;
                this.f341v1 = Long.rotateLeft(j2, 13);
                this.f343v3 = Long.rotateLeft(this.f343v3, 16);
                long j3 = this.f341v1;
                long j4 = this.f340v0;
                this.f341v1 = j3 ^ j4;
                this.f343v3 ^= this.f342v2;
                this.f340v0 = Long.rotateLeft(j4, 32);
                long j5 = this.f342v2;
                long j6 = this.f341v1;
                this.f342v2 = j5 + j6;
                this.f340v0 += this.f343v3;
                this.f341v1 = Long.rotateLeft(j6, 17);
                this.f343v3 = Long.rotateLeft(this.f343v3, 21);
                long j7 = this.f341v1;
                long j8 = this.f342v2;
                this.f341v1 = j7 ^ j8;
                this.f343v3 ^= this.f340v0;
                this.f342v2 = Long.rotateLeft(j8, 32);
            }
        }

        @Override // com.google.common.hash.AbstractStreamingHashFunction.AbstractStreamingHasher
        public HashCode makeHash() {
            this.finalM ^= this.f337b << 56;
            processM(this.finalM);
            this.f342v2 ^= 255;
            sipRound(this.f339d);
            return HashCode.fromLong(((this.f340v0 ^ this.f341v1) ^ this.f342v2) ^ this.f343v3);
        }

        @Override // com.google.common.hash.AbstractStreamingHashFunction.AbstractStreamingHasher
        protected void process(ByteBuffer byteBuffer) {
            this.f337b += 8;
            processM(byteBuffer.getLong());
        }

        @Override // com.google.common.hash.AbstractStreamingHashFunction.AbstractStreamingHasher
        protected void processRemaining(ByteBuffer byteBuffer) {
            this.f337b += (long) byteBuffer.remaining();
            int i = 0;
            while (byteBuffer.hasRemaining()) {
                this.finalM ^= (((long) byteBuffer.get()) & 255) << i;
                i += 8;
            }
        }
    }

    SipHashFunction(int i, int i2, long j, long j2) {
        Preconditions.checkArgument(i > 0, "The number of SipRound iterations (c=%s) during Compression must be positive.", i);
        Preconditions.checkArgument(i2 > 0, "The number of SipRound iterations (d=%s) during Finalization must be positive.", i2);
        this.f333c = i;
        this.f334d = i2;
        this.f335k0 = j;
        this.f336k1 = j2;
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return 64;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof SipHashFunction)) {
            return false;
        }
        SipHashFunction sipHashFunction = (SipHashFunction) obj;
        return this.f333c == sipHashFunction.f333c && this.f334d == sipHashFunction.f334d && this.f335k0 == sipHashFunction.f335k0 && this.f336k1 == sipHashFunction.f336k1;
    }

    public int hashCode() {
        return (int) ((((long) ((getClass().hashCode() ^ this.f333c) ^ this.f334d)) ^ this.f335k0) ^ this.f336k1);
    }

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher() {
        return new SipHasher(this.f333c, this.f334d, this.f335k0, this.f336k1);
    }

    public String toString() {
        return "Hashing.sipHash" + this.f333c + "" + this.f334d + "(" + this.f335k0 + ", " + this.f336k1 + ")";
    }
}
