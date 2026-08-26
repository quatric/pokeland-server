package com.google.api.client.repackaged.org.apache.commons.codec.binary;

import com.google.common.base.Ascii;
import java.math.BigInteger;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class Base64 extends BaseNCodec {
    private static final int BITS_PER_ENCODED_BYTE = 6;
    private static final int BYTES_PER_ENCODED_BLOCK = 4;
    private static final int BYTES_PER_UNENCODED_BLOCK = 3;
    private static final int MASK_6BITS = 63;
    private int bitWorkArea;
    private final int decodeSize;
    private final byte[] decodeTable;
    private final int encodeSize;
    private final byte[] encodeTable;
    private final byte[] lineSeparator;
    static final byte[] CHUNK_SEPARATOR = {Ascii.f282CR, 10};
    private static final byte[] STANDARD_ENCODE_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] URL_SAFE_ENCODE_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    private static final byte[] DECODE_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Ascii.f295VT, Ascii.f284FF, Ascii.f282CR, Ascii.f292SO, Ascii.f291SI, Ascii.DLE, 17, Ascii.DC2, 19, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.f283EM, -1, -1, -1, -1, 63, -1, Ascii.SUB, Ascii.ESC, Ascii.f285FS, Ascii.f286GS, Ascii.f290RS, Ascii.f294US, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51};

    public Base64() {
        this(0);
    }

    public Base64(int i) {
        this(i, CHUNK_SEPARATOR);
    }

    public Base64(int i, byte[] bArr) {
        this(i, bArr, false);
    }

    public Base64(int i, byte[] bArr, boolean z) {
        super(3, 4, i, bArr == null ? 0 : bArr.length);
        this.decodeTable = DECODE_TABLE;
        if (bArr == null) {
            this.encodeSize = 4;
            this.lineSeparator = null;
        } else {
            if (containsAlphabetOrPad(bArr)) {
                throw new IllegalArgumentException("lineSeparator must not contain base64 characters: [" + StringUtils.newStringUtf8(bArr) + "]");
            }
            if (i > 0) {
                this.encodeSize = bArr.length + 4;
                this.lineSeparator = new byte[bArr.length];
                System.arraycopy(bArr, 0, this.lineSeparator, 0, bArr.length);
            } else {
                this.encodeSize = 4;
                this.lineSeparator = null;
            }
        }
        this.decodeSize = this.encodeSize - 1;
        this.encodeTable = z ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE;
    }

    public Base64(boolean z) {
        this(76, CHUNK_SEPARATOR, z);
    }

    public static byte[] decodeBase64(String str) {
        return new Base64().decode(str);
    }

    public static byte[] decodeBase64(byte[] bArr) {
        return new Base64().decode(bArr);
    }

    public static BigInteger decodeInteger(byte[] bArr) {
        return new BigInteger(1, decodeBase64(bArr));
    }

    public static byte[] encodeBase64(byte[] bArr) {
        return encodeBase64(bArr, false);
    }

    public static byte[] encodeBase64(byte[] bArr, boolean z) {
        return encodeBase64(bArr, z, false);
    }

    public static byte[] encodeBase64(byte[] bArr, boolean z, boolean z2) {
        return encodeBase64(bArr, z, z2, Integer.MAX_VALUE);
    }

    public static byte[] encodeBase64(byte[] bArr, boolean z, boolean z2, int i) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        Base64 base64 = z ? new Base64(z2) : new Base64(0, CHUNK_SEPARATOR, z2);
        long encodedLength = base64.getEncodedLength(bArr);
        if (encodedLength <= i) {
            return base64.encode(bArr);
        }
        throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + encodedLength + ") than the specified maximum size of " + i);
    }

    public static byte[] encodeBase64Chunked(byte[] bArr) {
        return encodeBase64(bArr, true);
    }

    public static String encodeBase64String(byte[] bArr) {
        return StringUtils.newStringUtf8(encodeBase64(bArr, false));
    }

    public static byte[] encodeBase64URLSafe(byte[] bArr) {
        return encodeBase64(bArr, false, true);
    }

    public static String encodeBase64URLSafeString(byte[] bArr) {
        return StringUtils.newStringUtf8(encodeBase64(bArr, false, true));
    }

    public static byte[] encodeInteger(BigInteger bigInteger) {
        if (bigInteger != null) {
            return encodeBase64(toIntegerBytes(bigInteger), false);
        }
        throw new NullPointerException("encodeInteger called with null parameter");
    }

    public static boolean isArrayByteBase64(byte[] bArr) {
        return isBase64(bArr);
    }

    public static boolean isBase64(byte b) {
        if (b != 61) {
            if (b >= 0) {
                byte[] bArr = DECODE_TABLE;
                if (b >= bArr.length || bArr[b] == -1) {
                }
            }
            return false;
        }
        return true;
    }

    public static boolean isBase64(String str) {
        return isBase64(StringUtils.getBytesUtf8(str));
    }

    public static boolean isBase64(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            if (!isBase64(bArr[i]) && !isWhiteSpace(bArr[i])) {
                return false;
            }
        }
        return true;
    }

    static byte[] toIntegerBytes(BigInteger bigInteger) {
        int iBitLength = ((bigInteger.bitLength() + 7) >> 3) << 3;
        byte[] byteArray = bigInteger.toByteArray();
        if (bigInteger.bitLength() % 8 != 0 && (bigInteger.bitLength() / 8) + 1 == iBitLength / 8) {
            return byteArray;
        }
        int i = 0;
        int length = byteArray.length;
        if (bigInteger.bitLength() % 8 == 0) {
            length--;
            i = 1;
        }
        int i2 = iBitLength / 8;
        int i3 = i2 - length;
        byte[] bArr = new byte[i2];
        System.arraycopy(byteArray, i, bArr, i3, length);
        return bArr;
    }

    @Override // com.google.api.client.repackaged.org.apache.commons.codec.binary.BaseNCodec
    void decode(byte[] bArr, int i, int i2) {
        byte b;
        if (this.eof) {
            return;
        }
        if (i2 < 0) {
            this.eof = true;
        }
        int i3 = 0;
        while (i3 < i2) {
            ensureBufferSize(this.decodeSize);
            int i4 = i + 1;
            byte b2 = bArr[i];
            if (b2 == 61) {
                this.eof = true;
                break;
            }
            if (b2 >= 0) {
                byte[] bArr2 = DECODE_TABLE;
                if (b2 < bArr2.length && (b = bArr2[b2]) >= 0) {
                    this.modulus = (this.modulus + 1) % 4;
                    this.bitWorkArea = (this.bitWorkArea << 6) + b;
                    if (this.modulus == 0) {
                        byte[] bArr3 = this.buffer;
                        int i5 = this.pos;
                        this.pos = i5 + 1;
                        bArr3[i5] = (byte) ((this.bitWorkArea >> 16) & 255);
                        byte[] bArr4 = this.buffer;
                        int i6 = this.pos;
                        this.pos = i6 + 1;
                        bArr4[i6] = (byte) ((this.bitWorkArea >> 8) & 255);
                        byte[] bArr5 = this.buffer;
                        int i7 = this.pos;
                        this.pos = i7 + 1;
                        bArr5[i7] = (byte) (this.bitWorkArea & 255);
                    }
                }
            }
            i3++;
            i = i4;
        }
        if (!this.eof || this.modulus == 0) {
            return;
        }
        ensureBufferSize(this.decodeSize);
        int i8 = this.modulus;
        if (i8 == 2) {
            this.bitWorkArea >>= 4;
            byte[] bArr6 = this.buffer;
            int i9 = this.pos;
            this.pos = i9 + 1;
            bArr6[i9] = (byte) (this.bitWorkArea & 255);
            return;
        }
        if (i8 != 3) {
            return;
        }
        this.bitWorkArea >>= 2;
        byte[] bArr7 = this.buffer;
        int i10 = this.pos;
        this.pos = i10 + 1;
        bArr7[i10] = (byte) ((this.bitWorkArea >> 8) & 255);
        byte[] bArr8 = this.buffer;
        int i11 = this.pos;
        this.pos = i11 + 1;
        bArr8[i11] = (byte) (this.bitWorkArea & 255);
    }

    @Override // com.google.api.client.repackaged.org.apache.commons.codec.binary.BaseNCodec
    void encode(byte[] bArr, int i, int i2) {
        if (this.eof) {
            return;
        }
        if (i2 >= 0) {
            int i3 = i;
            int i4 = 0;
            while (i4 < i2) {
                ensureBufferSize(this.encodeSize);
                this.modulus = (this.modulus + 1) % 3;
                int i5 = i3 + 1;
                int i6 = bArr[i3];
                if (i6 < 0) {
                    i6 += 256;
                }
                this.bitWorkArea = (this.bitWorkArea << 8) + i6;
                if (this.modulus == 0) {
                    byte[] bArr2 = this.buffer;
                    int i7 = this.pos;
                    this.pos = i7 + 1;
                    bArr2[i7] = this.encodeTable[(this.bitWorkArea >> 18) & MASK_6BITS];
                    byte[] bArr3 = this.buffer;
                    int i8 = this.pos;
                    this.pos = i8 + 1;
                    bArr3[i8] = this.encodeTable[(this.bitWorkArea >> 12) & MASK_6BITS];
                    byte[] bArr4 = this.buffer;
                    int i9 = this.pos;
                    this.pos = i9 + 1;
                    bArr4[i9] = this.encodeTable[(this.bitWorkArea >> 6) & MASK_6BITS];
                    byte[] bArr5 = this.buffer;
                    int i10 = this.pos;
                    this.pos = i10 + 1;
                    bArr5[i10] = this.encodeTable[this.bitWorkArea & MASK_6BITS];
                    this.currentLinePos += 4;
                    if (this.lineLength > 0 && this.lineLength <= this.currentLinePos) {
                        System.arraycopy(this.lineSeparator, 0, this.buffer, this.pos, this.lineSeparator.length);
                        this.pos += this.lineSeparator.length;
                        this.currentLinePos = 0;
                    }
                }
                i4++;
                i3 = i5;
            }
            return;
        }
        this.eof = true;
        if (this.modulus == 0 && this.lineLength == 0) {
            return;
        }
        ensureBufferSize(this.encodeSize);
        int i11 = this.pos;
        int i12 = this.modulus;
        if (i12 == 1) {
            byte[] bArr6 = this.buffer;
            int i13 = this.pos;
            this.pos = i13 + 1;
            bArr6[i13] = this.encodeTable[(this.bitWorkArea >> 2) & MASK_6BITS];
            byte[] bArr7 = this.buffer;
            int i14 = this.pos;
            this.pos = i14 + 1;
            byte[] bArr8 = this.encodeTable;
            bArr7[i14] = bArr8[(this.bitWorkArea << 4) & MASK_6BITS];
            if (bArr8 == STANDARD_ENCODE_TABLE) {
                byte[] bArr9 = this.buffer;
                int i15 = this.pos;
                this.pos = i15 + 1;
                bArr9[i15] = 61;
                byte[] bArr10 = this.buffer;
                int i16 = this.pos;
                this.pos = i16 + 1;
                bArr10[i16] = 61;
            }
        } else if (i12 == 2) {
            byte[] bArr11 = this.buffer;
            int i17 = this.pos;
            this.pos = i17 + 1;
            bArr11[i17] = this.encodeTable[(this.bitWorkArea >> 10) & MASK_6BITS];
            byte[] bArr12 = this.buffer;
            int i18 = this.pos;
            this.pos = i18 + 1;
            bArr12[i18] = this.encodeTable[(this.bitWorkArea >> 4) & MASK_6BITS];
            byte[] bArr13 = this.buffer;
            int i19 = this.pos;
            this.pos = i19 + 1;
            byte[] bArr14 = this.encodeTable;
            bArr13[i19] = bArr14[(this.bitWorkArea << 2) & MASK_6BITS];
            if (bArr14 == STANDARD_ENCODE_TABLE) {
                byte[] bArr15 = this.buffer;
                int i20 = this.pos;
                this.pos = i20 + 1;
                bArr15[i20] = 61;
            }
        }
        this.currentLinePos += this.pos - i11;
        if (this.lineLength <= 0 || this.currentLinePos <= 0) {
            return;
        }
        System.arraycopy(this.lineSeparator, 0, this.buffer, this.pos, this.lineSeparator.length);
        this.pos += this.lineSeparator.length;
    }

    @Override // com.google.api.client.repackaged.org.apache.commons.codec.binary.BaseNCodec
    protected boolean isInAlphabet(byte b) {
        if (b >= 0) {
            byte[] bArr = this.decodeTable;
            if (b < bArr.length && bArr[b] != -1) {
                return true;
            }
        }
        return false;
    }

    public boolean isUrlSafe() {
        return this.encodeTable == URL_SAFE_ENCODE_TABLE;
    }
}
