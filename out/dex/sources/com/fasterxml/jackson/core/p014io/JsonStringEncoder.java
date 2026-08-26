package com.fasterxml.jackson.core.p014io;

import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.BufferRecyclers;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class JsonStringEncoder {
    private static final int SURR1_FIRST = 55296;
    private static final int SURR1_LAST = 56319;
    private static final int SURR2_FIRST = 56320;
    private static final int SURR2_LAST = 57343;
    protected ByteArrayBuilder _bytes;
    protected final char[] _qbuf = new char[6];
    protected TextBuffer _text;

    /* JADX INFO: renamed from: HC */
    private static final char[] f257HC = CharTypes.copyHexChars();

    /* JADX INFO: renamed from: HB */
    private static final byte[] f256HB = CharTypes.copyHexBytes();

    public JsonStringEncoder() {
        char[] cArr = this._qbuf;
        cArr[0] = '\\';
        cArr[2] = '0';
        cArr[3] = '0';
    }

    private int _appendByte(int i, int i2, ByteArrayBuilder byteArrayBuilder, int i3) {
        byteArrayBuilder.setCurrentSegmentLength(i3);
        byteArrayBuilder.append(92);
        if (i2 < 0) {
            byteArrayBuilder.append(117);
            if (i > 255) {
                int i4 = i >> 8;
                byteArrayBuilder.append(f256HB[i4 >> 4]);
                byteArrayBuilder.append(f256HB[i4 & 15]);
                i &= 255;
            } else {
                byteArrayBuilder.append(48);
                byteArrayBuilder.append(48);
            }
            byteArrayBuilder.append(f256HB[i >> 4]);
            byteArrayBuilder.append(f256HB[i & 15]);
        } else {
            byteArrayBuilder.append((byte) i2);
        }
        return byteArrayBuilder.getCurrentSegmentLength();
    }

    private int _appendNamed(int i, char[] cArr) {
        cArr[1] = (char) i;
        return 2;
    }

    private int _appendNumeric(int i, char[] cArr) {
        cArr[1] = 'u';
        char[] cArr2 = f257HC;
        cArr[4] = cArr2[i >> 4];
        cArr[5] = cArr2[i & 15];
        return 6;
    }

    private static int _convert(int i, int i2) {
        if (i2 >= 56320 && i2 <= 57343) {
            return ((i - 55296) << 10) + 65536 + (i2 - 56320);
        }
        throw new IllegalArgumentException("Broken surrogate pair: first char 0x" + Integer.toHexString(i) + ", second 0x" + Integer.toHexString(i2) + "; illegal combination");
    }

    private static void _illegal(int i) {
        throw new IllegalArgumentException(UTF8Writer.illegalSurrogateDesc(i));
    }

    @Deprecated
    public static JsonStringEncoder getInstance() {
        return BufferRecyclers.getJsonStringEncoder();
    }

    public byte[] encodeAsUTF8(String str) {
        int i;
        ByteArrayBuilder byteArrayBuilder = this._bytes;
        if (byteArrayBuilder == null) {
            byteArrayBuilder = new ByteArrayBuilder((BufferRecycler) null);
            this._bytes = byteArrayBuilder;
        }
        int length = str.length();
        byte[] bArrResetAndGetFirstSegment = byteArrayBuilder.resetAndGetFirstSegment();
        int length2 = bArrResetAndGetFirstSegment.length;
        byte[] bArrFinishCurrentSegment = bArrResetAndGetFirstSegment;
        int i2 = 0;
        int i3 = 0;
        loop0: while (i2 < length) {
            int i4 = i2 + 1;
            int iCharAt = str.charAt(i2);
            while (iCharAt <= 127) {
                if (i3 >= length2) {
                    byte[] bArrFinishCurrentSegment2 = byteArrayBuilder.finishCurrentSegment();
                    i3 = 0;
                    bArrFinishCurrentSegment = bArrFinishCurrentSegment2;
                    length2 = bArrFinishCurrentSegment2.length;
                }
                int i5 = i3 + 1;
                bArrFinishCurrentSegment[i3] = (byte) iCharAt;
                if (i4 >= length) {
                    i3 = i5;
                    break loop0;
                }
                char cCharAt = str.charAt(i4);
                i4++;
                iCharAt = cCharAt;
                i3 = i5;
            }
            if (i3 >= length2) {
                bArrFinishCurrentSegment = byteArrayBuilder.finishCurrentSegment();
                length2 = bArrFinishCurrentSegment.length;
                i3 = 0;
            }
            if (iCharAt < 2048) {
                bArrFinishCurrentSegment[i3] = (byte) ((iCharAt >> 6) | 192);
                i = i3 + 1;
            } else if (iCharAt < 55296 || iCharAt > 57343) {
                int i6 = i3 + 1;
                bArrFinishCurrentSegment[i3] = (byte) ((iCharAt >> 12) | 224);
                if (i6 >= length2) {
                    bArrFinishCurrentSegment = byteArrayBuilder.finishCurrentSegment();
                    length2 = bArrFinishCurrentSegment.length;
                    i6 = 0;
                }
                i = i6 + 1;
                bArrFinishCurrentSegment[i6] = (byte) (((iCharAt >> 6) & 63) | 128);
            } else {
                if (iCharAt > 56319) {
                    _illegal(iCharAt);
                }
                if (i4 >= length) {
                    _illegal(iCharAt);
                }
                int i7 = i4 + 1;
                iCharAt = _convert(iCharAt, str.charAt(i4));
                if (iCharAt > 1114111) {
                    _illegal(iCharAt);
                }
                int i8 = i3 + 1;
                bArrFinishCurrentSegment[i3] = (byte) ((iCharAt >> 18) | 240);
                if (i8 >= length2) {
                    bArrFinishCurrentSegment = byteArrayBuilder.finishCurrentSegment();
                    length2 = bArrFinishCurrentSegment.length;
                    i8 = 0;
                }
                int i9 = i8 + 1;
                bArrFinishCurrentSegment[i8] = (byte) (((iCharAt >> 12) & 63) | 128);
                if (i9 >= length2) {
                    byte[] bArrFinishCurrentSegment3 = byteArrayBuilder.finishCurrentSegment();
                    i9 = 0;
                    bArrFinishCurrentSegment = bArrFinishCurrentSegment3;
                    length2 = bArrFinishCurrentSegment3.length;
                }
                bArrFinishCurrentSegment[i9] = (byte) (((iCharAt >> 6) & 63) | 128);
                i = i9 + 1;
                i4 = i7;
            }
            if (i >= length2) {
                byte[] bArrFinishCurrentSegment4 = byteArrayBuilder.finishCurrentSegment();
                i = 0;
                bArrFinishCurrentSegment = bArrFinishCurrentSegment4;
                length2 = bArrFinishCurrentSegment4.length;
            }
            bArrFinishCurrentSegment[i] = (byte) ((iCharAt & 63) | 128);
            i2 = i4;
            i3 = i + 1;
        }
        return this._bytes.completeAndCoalesce(i3);
    }

    public void quoteAsString(CharSequence charSequence, StringBuilder sb) {
        int[] iArr = CharTypes.get7BitOutputEscapes();
        int length = iArr.length;
        int length2 = charSequence.length();
        int i = 0;
        while (i < length2) {
            do {
                char cCharAt = charSequence.charAt(i);
                if (cCharAt >= length || iArr[cCharAt] == 0) {
                    sb.append(cCharAt);
                    i++;
                } else {
                    int i2 = i + 1;
                    char cCharAt2 = charSequence.charAt(i);
                    int i3 = iArr[cCharAt2];
                    sb.append(this._qbuf, 0, i3 < 0 ? _appendNumeric(cCharAt2, this._qbuf) : _appendNamed(i3, this._qbuf));
                    i = i2;
                }
            } while (i < length2);
            return;
        }
    }

    public char[] quoteAsString(String str) {
        TextBuffer textBuffer = this._text;
        if (textBuffer == null) {
            textBuffer = new TextBuffer(null);
            this._text = textBuffer;
        }
        char[] cArrEmptyAndGetCurrentSegment = textBuffer.emptyAndGetCurrentSegment();
        int[] iArr = CharTypes.get7BitOutputEscapes();
        int length = iArr.length;
        int length2 = str.length();
        char[] cArrFinishCurrentSegment = cArrEmptyAndGetCurrentSegment;
        int i = 0;
        int i2 = 0;
        loop0: while (i < length2) {
            while (true) {
                char cCharAt = str.charAt(i);
                if (cCharAt >= length || iArr[cCharAt] == 0) {
                    if (i2 >= cArrFinishCurrentSegment.length) {
                        cArrFinishCurrentSegment = textBuffer.finishCurrentSegment();
                        i2 = 0;
                    }
                    int i3 = i2 + 1;
                    cArrFinishCurrentSegment[i2] = cCharAt;
                    i++;
                    if (i >= length2) {
                        i2 = i3;
                        break loop0;
                    }
                    i2 = i3;
                }
            }
            int i4 = i + 1;
            char cCharAt2 = str.charAt(i);
            int i5 = iArr[cCharAt2];
            int i_appendNumeric = i5 < 0 ? _appendNumeric(cCharAt2, this._qbuf) : _appendNamed(i5, this._qbuf);
            int i6 = i2 + i_appendNumeric;
            if (i6 > cArrFinishCurrentSegment.length) {
                int length3 = cArrFinishCurrentSegment.length - i2;
                if (length3 > 0) {
                    System.arraycopy(this._qbuf, 0, cArrFinishCurrentSegment, i2, length3);
                }
                cArrFinishCurrentSegment = textBuffer.finishCurrentSegment();
                int i7 = i_appendNumeric - length3;
                System.arraycopy(this._qbuf, length3, cArrFinishCurrentSegment, 0, i7);
                i2 = i7;
            } else {
                System.arraycopy(this._qbuf, 0, cArrFinishCurrentSegment, i2, i_appendNumeric);
                i2 = i6;
            }
            i = i4;
        }
        textBuffer.setCurrentLength(i2);
        return textBuffer.contentsAsArray();
    }

    public byte[] quoteAsUTF8(String str) {
        int i;
        int i2;
        ByteArrayBuilder byteArrayBuilder = this._bytes;
        if (byteArrayBuilder == null) {
            byteArrayBuilder = new ByteArrayBuilder((BufferRecycler) null);
            this._bytes = byteArrayBuilder;
        }
        int length = str.length();
        byte[] bArrResetAndGetFirstSegment = byteArrayBuilder.resetAndGetFirstSegment();
        int i3 = 0;
        int i_appendByte = 0;
        loop0: while (i3 < length) {
            int[] iArr = CharTypes.get7BitOutputEscapes();
            while (true) {
                char cCharAt = str.charAt(i3);
                if (cCharAt > 127 || iArr[cCharAt] != 0) {
                    break;
                }
                if (i_appendByte >= bArrResetAndGetFirstSegment.length) {
                    bArrResetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                    i_appendByte = 0;
                }
                int i4 = i_appendByte + 1;
                bArrResetAndGetFirstSegment[i_appendByte] = (byte) cCharAt;
                i3++;
                if (i3 >= length) {
                    i_appendByte = i4;
                    break loop0;
                }
                i_appendByte = i4;
            }
            if (i_appendByte >= bArrResetAndGetFirstSegment.length) {
                bArrResetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                i_appendByte = 0;
            }
            int i5 = i3 + 1;
            char cCharAt2 = str.charAt(i3);
            if (cCharAt2 <= 127) {
                i_appendByte = _appendByte(cCharAt2, iArr[cCharAt2], byteArrayBuilder, i_appendByte);
                bArrResetAndGetFirstSegment = byteArrayBuilder.getCurrentSegment();
            } else {
                if (cCharAt2 <= 2047) {
                    bArrResetAndGetFirstSegment[i_appendByte] = (byte) ((cCharAt2 >> 6) | 192);
                    i2 = (cCharAt2 & '?') | 128;
                    i = i_appendByte + 1;
                } else if (cCharAt2 < 55296 || cCharAt2 > 57343) {
                    int i6 = i_appendByte + 1;
                    bArrResetAndGetFirstSegment[i_appendByte] = (byte) ((cCharAt2 >> '\f') | 224);
                    if (i6 >= bArrResetAndGetFirstSegment.length) {
                        bArrResetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                        i6 = 0;
                    }
                    i = i6 + 1;
                    bArrResetAndGetFirstSegment[i6] = (byte) (((cCharAt2 >> 6) & 63) | 128);
                    i2 = (cCharAt2 & '?') | 128;
                } else {
                    if (cCharAt2 > 56319) {
                        _illegal(cCharAt2);
                    }
                    if (i5 >= length) {
                        _illegal(cCharAt2);
                    }
                    int i7 = i5 + 1;
                    int i_convert = _convert(cCharAt2, str.charAt(i5));
                    if (i_convert > 1114111) {
                        _illegal(i_convert);
                    }
                    int i8 = i_appendByte + 1;
                    bArrResetAndGetFirstSegment[i_appendByte] = (byte) ((i_convert >> 18) | 240);
                    if (i8 >= bArrResetAndGetFirstSegment.length) {
                        bArrResetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                        i8 = 0;
                    }
                    int i9 = i8 + 1;
                    bArrResetAndGetFirstSegment[i8] = (byte) (((i_convert >> 12) & 63) | 128);
                    if (i9 >= bArrResetAndGetFirstSegment.length) {
                        bArrResetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                        i9 = 0;
                    }
                    bArrResetAndGetFirstSegment[i9] = (byte) (((i_convert >> 6) & 63) | 128);
                    i2 = (i_convert & 63) | 128;
                    i = i9 + 1;
                    i5 = i7;
                }
                if (i >= bArrResetAndGetFirstSegment.length) {
                    bArrResetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                    i = 0;
                }
                bArrResetAndGetFirstSegment[i] = (byte) i2;
                i_appendByte = i + 1;
            }
            i3 = i5;
        }
        return this._bytes.completeAndCoalesce(i_appendByte);
    }
}
