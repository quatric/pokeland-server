package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class AbstractStreamingHashFunction implements HashFunction {

    @CanIgnoreReturnValue
    protected static abstract class AbstractStreamingHasher extends AbstractHasher {
        private final ByteBuffer buffer;
        private final int bufferSize;
        private final int chunkSize;

        protected AbstractStreamingHasher(int i) {
            this(i, i);
        }

        protected AbstractStreamingHasher(int i, int i2) {
            Preconditions.checkArgument(i2 % i == 0);
            this.buffer = ByteBuffer.allocate(i2 + 7).order(ByteOrder.LITTLE_ENDIAN);
            this.bufferSize = i2;
            this.chunkSize = i;
        }

        private void munch() {
            this.buffer.flip();
            while (this.buffer.remaining() >= this.chunkSize) {
                process(this.buffer);
            }
            this.buffer.compact();
        }

        private void munchIfFull() {
            if (this.buffer.remaining() < 8) {
                munch();
            }
        }

        private Hasher putBytes(ByteBuffer byteBuffer) {
            if (byteBuffer.remaining() <= this.buffer.remaining()) {
                this.buffer.put(byteBuffer);
                munchIfFull();
                return this;
            }
            int iPosition = this.bufferSize - this.buffer.position();
            for (int i = 0; i < iPosition; i++) {
                this.buffer.put(byteBuffer.get());
            }
            munch();
            while (byteBuffer.remaining() >= this.chunkSize) {
                process(byteBuffer);
            }
            this.buffer.put(byteBuffer);
            return this;
        }

        @Override // com.google.common.hash.Hasher
        public final HashCode hash() {
            munch();
            this.buffer.flip();
            if (this.buffer.remaining() > 0) {
                processRemaining(this.buffer);
            }
            return makeHash();
        }

        abstract HashCode makeHash();

        protected abstract void process(ByteBuffer byteBuffer);

        protected void processRemaining(ByteBuffer byteBuffer) {
            byteBuffer.position(byteBuffer.limit());
            byteBuffer.limit(this.chunkSize + 7);
            while (true) {
                int iPosition = byteBuffer.position();
                int i = this.chunkSize;
                if (iPosition >= i) {
                    byteBuffer.limit(i);
                    byteBuffer.flip();
                    process(byteBuffer);
                    return;
                }
                byteBuffer.putLong(0L);
            }
        }

        @Override // com.google.common.hash.PrimitiveSink
        public final Hasher putByte(byte b) {
            this.buffer.put(b);
            munchIfFull();
            return this;
        }

        @Override // com.google.common.hash.PrimitiveSink
        public final Hasher putBytes(byte[] bArr) {
            return putBytes(bArr, 0, bArr.length);
        }

        @Override // com.google.common.hash.PrimitiveSink
        public final Hasher putBytes(byte[] bArr, int i, int i2) {
            return putBytes(ByteBuffer.wrap(bArr, i, i2).order(ByteOrder.LITTLE_ENDIAN));
        }

        @Override // com.google.common.hash.PrimitiveSink
        public final Hasher putChar(char c) {
            this.buffer.putChar(c);
            munchIfFull();
            return this;
        }

        @Override // com.google.common.hash.PrimitiveSink
        public final Hasher putInt(int i) {
            this.buffer.putInt(i);
            munchIfFull();
            return this;
        }

        @Override // com.google.common.hash.PrimitiveSink
        public final Hasher putLong(long j) {
            this.buffer.putLong(j);
            munchIfFull();
            return this;
        }

        @Override // com.google.common.hash.Hasher
        public final <T> Hasher putObject(T t, Funnel<? super T> funnel) {
            funnel.funnel(t, this);
            return this;
        }

        @Override // com.google.common.hash.PrimitiveSink
        public final Hasher putShort(short s) {
            this.buffer.putShort(s);
            munchIfFull();
            return this;
        }

        @Override // com.google.common.hash.AbstractHasher, com.google.common.hash.PrimitiveSink
        public final Hasher putUnencodedChars(CharSequence charSequence) {
            for (int i = 0; i < charSequence.length(); i++) {
                putChar(charSequence.charAt(i));
            }
            return this;
        }
    }

    AbstractStreamingHashFunction() {
    }

    @Override // com.google.common.hash.HashFunction
    public HashCode hashBytes(byte[] bArr) {
        return newHasher().putBytes(bArr).hash();
    }

    @Override // com.google.common.hash.HashFunction
    public HashCode hashBytes(byte[] bArr, int i, int i2) {
        return newHasher().putBytes(bArr, i, i2).hash();
    }

    @Override // com.google.common.hash.HashFunction
    public HashCode hashInt(int i) {
        return newHasher().putInt(i).hash();
    }

    @Override // com.google.common.hash.HashFunction
    public HashCode hashLong(long j) {
        return newHasher().putLong(j).hash();
    }

    @Override // com.google.common.hash.HashFunction
    public <T> HashCode hashObject(T t, Funnel<? super T> funnel) {
        return newHasher().putObject(t, funnel).hash();
    }

    @Override // com.google.common.hash.HashFunction
    public HashCode hashString(CharSequence charSequence, Charset charset) {
        return newHasher().putString(charSequence, charset).hash();
    }

    @Override // com.google.common.hash.HashFunction
    public HashCode hashUnencodedChars(CharSequence charSequence) {
        return newHasher().putUnencodedChars(charSequence).hash();
    }

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher(int i) {
        Preconditions.checkArgument(i >= 0);
        return newHasher();
    }
}
