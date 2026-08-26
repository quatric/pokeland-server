package com.google.common.p015io;

import android.support.v4.media.session.PlaybackStateCompat;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@GwtIncompatible
public final class ByteStreams {
    private static final OutputStream NULL_OUTPUT_STREAM = new OutputStream() { // from class: com.google.common.io.ByteStreams.1
        public String toString() {
            return "ByteStreams.nullOutputStream()";
        }

        @Override // java.io.OutputStream
        public void write(int i) {
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) {
            Preconditions.checkNotNull(bArr);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) {
            Preconditions.checkNotNull(bArr);
        }
    };
    private static final int ZERO_COPY_CHUNK_SIZE = 524288;

    private static class ByteArrayDataInputStream implements ByteArrayDataInput {
        final DataInput input;

        ByteArrayDataInputStream(ByteArrayInputStream byteArrayInputStream) {
            this.input = new DataInputStream(byteArrayInputStream);
        }

        @Override // com.google.common.p015io.ByteArrayDataInput, java.io.DataInput
        public boolean readBoolean() {
            try {
                return this.input.readBoolean();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataInput, java.io.DataInput
        public byte readByte() {
            try {
                return this.input.readByte();
            } catch (EOFException e) {
                throw new IllegalStateException(e);
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataInput, java.io.DataInput
        public char readChar() {
            try {
                return this.input.readChar();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataInput, java.io.DataInput
        public double readDouble() {
            try {
                return this.input.readDouble();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataInput, java.io.DataInput
        public float readFloat() {
            try {
                return this.input.readFloat();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataInput, java.io.DataInput
        public void readFully(byte[] bArr) {
            try {
                this.input.readFully(bArr);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataInput, java.io.DataInput
        public void readFully(byte[] bArr, int i, int i2) {
            try {
                this.input.readFully(bArr, i, i2);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataInput, java.io.DataInput
        public int readInt() {
            try {
                return this.input.readInt();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataInput, java.io.DataInput
        public String readLine() {
            try {
                return this.input.readLine();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataInput, java.io.DataInput
        public long readLong() {
            try {
                return this.input.readLong();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataInput, java.io.DataInput
        public short readShort() {
            try {
                return this.input.readShort();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataInput, java.io.DataInput
        public String readUTF() {
            try {
                return this.input.readUTF();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataInput, java.io.DataInput
        public int readUnsignedByte() {
            try {
                return this.input.readUnsignedByte();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataInput, java.io.DataInput
        public int readUnsignedShort() {
            try {
                return this.input.readUnsignedShort();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataInput, java.io.DataInput
        public int skipBytes(int i) {
            try {
                return this.input.skipBytes(i);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static class ByteArrayDataOutputStream implements ByteArrayDataOutput {
        final ByteArrayOutputStream byteArrayOutputSteam;
        final DataOutput output;

        ByteArrayDataOutputStream(ByteArrayOutputStream byteArrayOutputStream) {
            this.byteArrayOutputSteam = byteArrayOutputStream;
            this.output = new DataOutputStream(byteArrayOutputStream);
        }

        @Override // com.google.common.p015io.ByteArrayDataOutput
        public byte[] toByteArray() {
            return this.byteArrayOutputSteam.toByteArray();
        }

        @Override // com.google.common.p015io.ByteArrayDataOutput, java.io.DataOutput
        public void write(int i) {
            try {
                this.output.write(i);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataOutput, java.io.DataOutput
        public void write(byte[] bArr) {
            try {
                this.output.write(bArr);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataOutput, java.io.DataOutput
        public void write(byte[] bArr, int i, int i2) {
            try {
                this.output.write(bArr, i, i2);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataOutput, java.io.DataOutput
        public void writeBoolean(boolean z) {
            try {
                this.output.writeBoolean(z);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataOutput, java.io.DataOutput
        public void writeByte(int i) {
            try {
                this.output.writeByte(i);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataOutput, java.io.DataOutput
        public void writeBytes(String str) {
            try {
                this.output.writeBytes(str);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataOutput, java.io.DataOutput
        public void writeChar(int i) {
            try {
                this.output.writeChar(i);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataOutput, java.io.DataOutput
        public void writeChars(String str) {
            try {
                this.output.writeChars(str);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataOutput, java.io.DataOutput
        public void writeDouble(double d) {
            try {
                this.output.writeDouble(d);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataOutput, java.io.DataOutput
        public void writeFloat(float f) {
            try {
                this.output.writeFloat(f);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataOutput, java.io.DataOutput
        public void writeInt(int i) {
            try {
                this.output.writeInt(i);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataOutput, java.io.DataOutput
        public void writeLong(long j) {
            try {
                this.output.writeLong(j);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataOutput, java.io.DataOutput
        public void writeShort(int i) {
            try {
                this.output.writeShort(i);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.common.p015io.ByteArrayDataOutput, java.io.DataOutput
        public void writeUTF(String str) {
            try {
                this.output.writeUTF(str);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
    }

    private static final class FastByteArrayOutputStream extends ByteArrayOutputStream {
        private FastByteArrayOutputStream() {
        }

        void writeTo(byte[] bArr, int i) {
            System.arraycopy(this.buf, 0, bArr, i, this.count);
        }
    }

    private static final class LimitedInputStream extends FilterInputStream {
        private long left;
        private long mark;

        LimitedInputStream(InputStream inputStream, long j) {
            super(inputStream);
            this.mark = -1L;
            Preconditions.checkNotNull(inputStream);
            Preconditions.checkArgument(j >= 0, "limit must be non-negative");
            this.left = j;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int available() throws IOException {
            return (int) Math.min(this.in.available(), this.left);
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public synchronized void mark(int i) {
            this.in.mark(i);
            this.mark = this.left;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read() throws IOException {
            if (this.left == 0) {
                return -1;
            }
            int i = this.in.read();
            if (i != -1) {
                this.left--;
            }
            return i;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            long j = this.left;
            if (j == 0) {
                return -1;
            }
            int i3 = this.in.read(bArr, i, (int) Math.min(i2, j));
            if (i3 != -1) {
                this.left -= (long) i3;
            }
            return i3;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public synchronized void reset() throws IOException {
            if (!this.in.markSupported()) {
                throw new IOException("Mark not supported");
            }
            if (this.mark == -1) {
                throw new IOException("Mark not set");
            }
            this.in.reset();
            this.left = this.mark;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public long skip(long j) throws IOException {
            long jSkip = this.in.skip(Math.min(j, this.left));
            this.left -= jSkip;
            return jSkip;
        }
    }

    private ByteStreams() {
    }

    @CanIgnoreReturnValue
    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(outputStream);
        byte[] bArrCreateBuffer = createBuffer();
        long j = 0;
        while (true) {
            int i = inputStream.read(bArrCreateBuffer);
            if (i == -1) {
                return j;
            }
            outputStream.write(bArrCreateBuffer, 0, i);
            j += (long) i;
        }
    }

    @CanIgnoreReturnValue
    public static long copy(ReadableByteChannel readableByteChannel, WritableByteChannel writableByteChannel) throws IOException {
        Preconditions.checkNotNull(readableByteChannel);
        Preconditions.checkNotNull(writableByteChannel);
        long jWrite = 0;
        if (!(readableByteChannel instanceof FileChannel)) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(createBuffer());
            while (readableByteChannel.read(byteBufferWrap) != -1) {
                byteBufferWrap.flip();
                while (byteBufferWrap.hasRemaining()) {
                    jWrite += (long) writableByteChannel.write(byteBufferWrap);
                }
                byteBufferWrap.clear();
            }
            return jWrite;
        }
        FileChannel fileChannel = (FileChannel) readableByteChannel;
        long jPosition = fileChannel.position();
        long j = jPosition;
        while (true) {
            long jTransferTo = fileChannel.transferTo(j, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED, writableByteChannel);
            j += jTransferTo;
            fileChannel.position(j);
            if (jTransferTo <= 0 && j >= fileChannel.size()) {
                return j - jPosition;
            }
        }
    }

    static byte[] createBuffer() {
        return new byte[8192];
    }

    @CanIgnoreReturnValue
    public static long exhaust(InputStream inputStream) throws IOException {
        byte[] bArrCreateBuffer = createBuffer();
        long j = 0;
        while (true) {
            long j2 = inputStream.read(bArrCreateBuffer);
            if (j2 == -1) {
                return j;
            }
            j += j2;
        }
    }

    public static InputStream limit(InputStream inputStream, long j) {
        return new LimitedInputStream(inputStream, j);
    }

    public static ByteArrayDataInput newDataInput(ByteArrayInputStream byteArrayInputStream) {
        return new ByteArrayDataInputStream((ByteArrayInputStream) Preconditions.checkNotNull(byteArrayInputStream));
    }

    public static ByteArrayDataInput newDataInput(byte[] bArr) {
        return newDataInput(new ByteArrayInputStream(bArr));
    }

    public static ByteArrayDataInput newDataInput(byte[] bArr, int i) {
        Preconditions.checkPositionIndex(i, bArr.length);
        return newDataInput(new ByteArrayInputStream(bArr, i, bArr.length - i));
    }

    public static ByteArrayDataOutput newDataOutput() {
        return newDataOutput(new ByteArrayOutputStream());
    }

    public static ByteArrayDataOutput newDataOutput(int i) {
        if (i >= 0) {
            return newDataOutput(new ByteArrayOutputStream(i));
        }
        throw new IllegalArgumentException(String.format("Invalid size: %s", Integer.valueOf(i)));
    }

    public static ByteArrayDataOutput newDataOutput(ByteArrayOutputStream byteArrayOutputStream) {
        return new ByteArrayDataOutputStream((ByteArrayOutputStream) Preconditions.checkNotNull(byteArrayOutputStream));
    }

    public static OutputStream nullOutputStream() {
        return NULL_OUTPUT_STREAM;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @CanIgnoreReturnValue
    public static int read(InputStream inputStream, byte[] bArr, int i, int i2) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(bArr);
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("len is negative");
        }
        int i3 = 0;
        while (i3 < i2) {
            int i4 = inputStream.read(bArr, i + i3, i2 - i3);
            if (i4 == -1) {
                break;
            }
            i3 += i4;
        }
        return i3;
    }

    @CanIgnoreReturnValue
    public static <T> T readBytes(InputStream inputStream, ByteProcessor<T> byteProcessor) throws IOException {
        int i;
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(byteProcessor);
        byte[] bArrCreateBuffer = createBuffer();
        do {
            i = inputStream.read(bArrCreateBuffer);
            if (i == -1) {
                break;
            }
        } while (byteProcessor.processBytes(bArrCreateBuffer, 0, i));
        return byteProcessor.getResult();
    }

    public static void readFully(InputStream inputStream, byte[] bArr) throws IOException {
        readFully(inputStream, bArr, 0, bArr.length);
    }

    public static void readFully(InputStream inputStream, byte[] bArr, int i, int i2) throws IOException {
        int i3 = read(inputStream, bArr, i, i2);
        if (i3 == i2) {
            return;
        }
        throw new EOFException("reached end of stream after reading " + i3 + " bytes; " + i2 + " bytes expected");
    }

    public static void skipFully(InputStream inputStream, long j) throws IOException {
        long jSkipUpTo = skipUpTo(inputStream, j);
        if (jSkipUpTo >= j) {
            return;
        }
        throw new EOFException("reached end of stream after skipping " + jSkipUpTo + " bytes; " + j + " bytes expected");
    }

    private static long skipSafely(InputStream inputStream, long j) throws IOException {
        int iAvailable = inputStream.available();
        if (iAvailable == 0) {
            return 0L;
        }
        return inputStream.skip(Math.min(iAvailable, j));
    }

    static long skipUpTo(InputStream inputStream, long j) throws IOException {
        byte[] bArrCreateBuffer = createBuffer();
        long j2 = 0;
        while (j2 < j) {
            long j3 = j - j2;
            long jSkipSafely = skipSafely(inputStream, j3);
            if (jSkipSafely == 0) {
                jSkipSafely = inputStream.read(bArrCreateBuffer, 0, (int) Math.min(j3, bArrCreateBuffer.length));
                if (jSkipSafely == -1) {
                    break;
                }
            }
            j2 += jSkipSafely;
        }
        return j2;
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(Math.max(32, inputStream.available()));
        copy(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    static byte[] toByteArray(InputStream inputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        int i2 = i;
        while (i2 > 0) {
            int i3 = i - i2;
            int i4 = inputStream.read(bArr, i3, i2);
            if (i4 == -1) {
                return Arrays.copyOf(bArr, i3);
            }
            i2 -= i4;
        }
        int i5 = inputStream.read();
        if (i5 == -1) {
            return bArr;
        }
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        fastByteArrayOutputStream.write(i5);
        copy(inputStream, fastByteArrayOutputStream);
        byte[] bArr2 = new byte[bArr.length + fastByteArrayOutputStream.size()];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        fastByteArrayOutputStream.writeTo(bArr2, bArr.length);
        return bArr2;
    }
}
