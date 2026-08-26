package com.google.api.client.repackaged.org.apache.commons.codec.binary;

import com.google.api.client.repackaged.org.apache.commons.codec.BinaryDecoder;
import com.google.api.client.repackaged.org.apache.commons.codec.BinaryEncoder;
import com.google.api.client.repackaged.org.apache.commons.codec.DecoderException;
import com.google.api.client.repackaged.org.apache.commons.codec.EncoderException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class BaseNCodec implements BinaryEncoder, BinaryDecoder {
    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    protected static final int MASK_8BITS = 255;
    public static final int MIME_CHUNK_SIZE = 76;
    protected static final byte PAD_DEFAULT = 61;
    public static final int PEM_CHUNK_SIZE = 64;
    protected final byte PAD = PAD_DEFAULT;
    protected byte[] buffer;
    private final int chunkSeparatorLength;
    protected int currentLinePos;
    private final int encodedBlockSize;
    protected boolean eof;
    protected final int lineLength;
    protected int modulus;
    protected int pos;
    private int readPos;
    private final int unencodedBlockSize;

    protected BaseNCodec(int i, int i2, int i3, int i4) {
        this.unencodedBlockSize = i;
        this.encodedBlockSize = i2;
        this.lineLength = (i3 <= 0 || i4 <= 0) ? 0 : (i3 / i2) * i2;
        this.chunkSeparatorLength = i4;
    }

    protected static boolean isWhiteSpace(byte b) {
        return b == 9 || b == 10 || b == 13 || b == 32;
    }

    private void reset() {
        this.buffer = null;
        this.pos = 0;
        this.readPos = 0;
        this.currentLinePos = 0;
        this.modulus = 0;
        this.eof = false;
    }

    private void resizeBuffer() {
        byte[] bArr = this.buffer;
        if (bArr == null) {
            this.buffer = new byte[getDefaultBufferSize()];
            this.pos = 0;
            this.readPos = 0;
        } else {
            byte[] bArr2 = new byte[bArr.length * 2];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            this.buffer = bArr2;
        }
    }

    int available() {
        if (this.buffer != null) {
            return this.pos - this.readPos;
        }
        return 0;
    }

    protected boolean containsAlphabetOrPad(byte[] bArr) {
        if (bArr == null) {
            return false;
        }
        for (byte b : bArr) {
            if (61 == b || isInAlphabet(b)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.api.client.repackaged.org.apache.commons.codec.Decoder
    public Object decode(Object obj) throws DecoderException {
        if (obj instanceof byte[]) {
            return decode((byte[]) obj);
        }
        if (obj instanceof String) {
            return decode((String) obj);
        }
        throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
    }

    abstract void decode(byte[] bArr, int i, int i2);

    public byte[] decode(String str) {
        return decode(StringUtils.getBytesUtf8(str));
    }

    @Override // com.google.api.client.repackaged.org.apache.commons.codec.BinaryDecoder
    public byte[] decode(byte[] bArr) {
        reset();
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        decode(bArr, 0, bArr.length);
        decode(bArr, 0, -1);
        byte[] bArr2 = new byte[this.pos];
        readResults(bArr2, 0, bArr2.length);
        return bArr2;
    }

    @Override // com.google.api.client.repackaged.org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof byte[]) {
            return encode((byte[]) obj);
        }
        throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
    }

    abstract void encode(byte[] bArr, int i, int i2);

    @Override // com.google.api.client.repackaged.org.apache.commons.codec.BinaryEncoder
    public byte[] encode(byte[] bArr) {
        reset();
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        encode(bArr, 0, bArr.length);
        encode(bArr, 0, -1);
        byte[] bArr2 = new byte[this.pos - this.readPos];
        readResults(bArr2, 0, bArr2.length);
        return bArr2;
    }

    public String encodeAsString(byte[] bArr) {
        return StringUtils.newStringUtf8(encode(bArr));
    }

    public String encodeToString(byte[] bArr) {
        return StringUtils.newStringUtf8(encode(bArr));
    }

    protected void ensureBufferSize(int i) {
        byte[] bArr = this.buffer;
        if (bArr == null || bArr.length < this.pos + i) {
            resizeBuffer();
        }
    }

    protected int getDefaultBufferSize() {
        return 8192;
    }

    public long getEncodedLength(byte[] bArr) {
        int length = bArr.length;
        int i = this.unencodedBlockSize;
        long j = ((long) (((length + i) - 1) / i)) * ((long) this.encodedBlockSize);
        int i2 = this.lineLength;
        return i2 > 0 ? j + ((((((long) i2) + j) - 1) / ((long) i2)) * ((long) this.chunkSeparatorLength)) : j;
    }

    boolean hasData() {
        return this.buffer != null;
    }

    protected abstract boolean isInAlphabet(byte b);

    public boolean isInAlphabet(String str) {
        return isInAlphabet(StringUtils.getBytesUtf8(str), true);
    }

    public boolean isInAlphabet(byte[] bArr, boolean z) {
        for (int i = 0; i < bArr.length; i++) {
            if (!isInAlphabet(bArr[i]) && (!z || (bArr[i] != 61 && !isWhiteSpace(bArr[i])))) {
                return false;
            }
        }
        return true;
    }

    int readResults(byte[] bArr, int i, int i2) {
        if (this.buffer == null) {
            return this.eof ? -1 : 0;
        }
        int iMin = Math.min(available(), i2);
        System.arraycopy(this.buffer, this.readPos, bArr, i, iMin);
        this.readPos += iMin;
        if (this.readPos >= this.pos) {
            this.buffer = null;
        }
        return iMin;
    }
}
