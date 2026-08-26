package com.fasterxml.jackson.core.json;

import android.support.v4.internal.view.SupportMenu;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.p014io.CharTypes;
import com.fasterxml.jackson.core.p014io.CharacterEscapes;
import com.fasterxml.jackson.core.p014io.IOContext;
import com.fasterxml.jackson.core.p014io.NumberOutput;
import com.google.common.base.Ascii;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import kotlin.text.Typography;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class WriterBasedJsonGenerator extends JsonGeneratorImpl {
    protected static final char[] HEX_CHARS = CharTypes.copyHexChars();
    protected static final int SHORT_WRITE = 32;
    protected char[] _charBuffer;
    protected SerializableString _currentEscape;
    protected char[] _entityBuffer;
    protected char[] _outputBuffer;
    protected int _outputEnd;
    protected int _outputHead;
    protected int _outputTail;
    protected char _quoteChar;
    protected final Writer _writer;

    public WriterBasedJsonGenerator(IOContext iOContext, int i, ObjectCodec objectCodec, Writer writer) {
        super(iOContext, i, objectCodec);
        this._quoteChar = Typography.quote;
        this._writer = writer;
        this._outputBuffer = iOContext.allocConcatBuffer();
        this._outputEnd = this._outputBuffer.length;
    }

    private char[] _allocateCopyBuffer() {
        if (this._charBuffer == null) {
            this._charBuffer = this._ioContext.allocNameCopyBuffer(2000);
        }
        return this._charBuffer;
    }

    private char[] _allocateEntityBuffer() {
        char[] cArr = {'\\', 0, '\\', 'u', '0', '0', 0, 0, '\\', 'u', 0, 0, 0, 0};
        this._entityBuffer = cArr;
        return cArr;
    }

    private void _appendCharacterEscape(char c, int i) throws IOException {
        String value;
        int i2;
        if (i >= 0) {
            if (this._outputTail + 2 > this._outputEnd) {
                _flushBuffer();
            }
            char[] cArr = this._outputBuffer;
            int i3 = this._outputTail;
            this._outputTail = i3 + 1;
            cArr[i3] = '\\';
            int i4 = this._outputTail;
            this._outputTail = i4 + 1;
            cArr[i4] = (char) i;
            return;
        }
        if (i == -2) {
            SerializableString serializableString = this._currentEscape;
            if (serializableString == null) {
                value = this._characterEscapes.getEscapeSequence(c).getValue();
            } else {
                value = serializableString.getValue();
                this._currentEscape = null;
            }
            int length = value.length();
            if (this._outputTail + length > this._outputEnd) {
                _flushBuffer();
                if (length > this._outputEnd) {
                    this._writer.write(value);
                    return;
                }
            }
            value.getChars(0, length, this._outputBuffer, this._outputTail);
            this._outputTail += length;
            return;
        }
        if (this._outputTail + 5 >= this._outputEnd) {
            _flushBuffer();
        }
        int i5 = this._outputTail;
        char[] cArr2 = this._outputBuffer;
        int i6 = i5 + 1;
        cArr2[i5] = '\\';
        int i7 = i6 + 1;
        cArr2[i6] = 'u';
        if (c > 255) {
            int i8 = 255 & (c >> '\b');
            int i9 = i7 + 1;
            char[] cArr3 = HEX_CHARS;
            cArr2[i7] = cArr3[i8 >> 4];
            i2 = i9 + 1;
            cArr2[i9] = cArr3[i8 & 15];
            c = (char) (c & 255);
        } else {
            int i10 = i7 + 1;
            cArr2[i7] = '0';
            i2 = i10 + 1;
            cArr2[i10] = '0';
        }
        int i11 = i2 + 1;
        char[] cArr4 = HEX_CHARS;
        cArr2[i2] = cArr4[c >> 4];
        cArr2[i11] = cArr4[c & 15];
        this._outputTail = i11 + 1;
    }

    private int _prependOrWriteCharacterEscape(char[] cArr, int i, int i2, char c, int i3) throws IOException {
        String value;
        int i4;
        if (i3 >= 0) {
            if (i > 1 && i < i2) {
                int i5 = i - 2;
                cArr[i5] = '\\';
                cArr[i5 + 1] = (char) i3;
                return i5;
            }
            char[] cArr_allocateEntityBuffer = this._entityBuffer;
            if (cArr_allocateEntityBuffer == null) {
                cArr_allocateEntityBuffer = _allocateEntityBuffer();
            }
            cArr_allocateEntityBuffer[1] = (char) i3;
            this._writer.write(cArr_allocateEntityBuffer, 0, 2);
            return i;
        }
        if (i3 == -2) {
            SerializableString serializableString = this._currentEscape;
            if (serializableString == null) {
                value = this._characterEscapes.getEscapeSequence(c).getValue();
            } else {
                value = serializableString.getValue();
                this._currentEscape = null;
            }
            int length = value.length();
            if (i < length || i >= i2) {
                this._writer.write(value);
                return i;
            }
            int i6 = i - length;
            value.getChars(0, length, cArr, i6);
            return i6;
        }
        if (i <= 5 || i >= i2) {
            char[] cArr_allocateEntityBuffer2 = this._entityBuffer;
            if (cArr_allocateEntityBuffer2 == null) {
                cArr_allocateEntityBuffer2 = _allocateEntityBuffer();
            }
            this._outputHead = this._outputTail;
            if (c <= 255) {
                char[] cArr2 = HEX_CHARS;
                cArr_allocateEntityBuffer2[6] = cArr2[c >> 4];
                cArr_allocateEntityBuffer2[7] = cArr2[c & 15];
                this._writer.write(cArr_allocateEntityBuffer2, 2, 6);
                return i;
            }
            int i7 = (c >> '\b') & 255;
            int i8 = c & 255;
            char[] cArr3 = HEX_CHARS;
            cArr_allocateEntityBuffer2[10] = cArr3[i7 >> 4];
            cArr_allocateEntityBuffer2[11] = cArr3[i7 & 15];
            cArr_allocateEntityBuffer2[12] = cArr3[i8 >> 4];
            cArr_allocateEntityBuffer2[13] = cArr3[i8 & 15];
            this._writer.write(cArr_allocateEntityBuffer2, 8, 6);
            return i;
        }
        int i9 = i - 6;
        int i10 = i9 + 1;
        cArr[i9] = '\\';
        int i11 = i10 + 1;
        cArr[i10] = 'u';
        if (c > 255) {
            int i12 = (c >> '\b') & 255;
            int i13 = i11 + 1;
            char[] cArr4 = HEX_CHARS;
            cArr[i11] = cArr4[i12 >> 4];
            i4 = i13 + 1;
            cArr[i13] = cArr4[i12 & 15];
            c = (char) (c & 255);
        } else {
            int i14 = i11 + 1;
            cArr[i11] = '0';
            i4 = i14 + 1;
            cArr[i14] = '0';
        }
        int i15 = i4 + 1;
        char[] cArr5 = HEX_CHARS;
        cArr[i4] = cArr5[c >> 4];
        cArr[i15] = cArr5[c & 15];
        return i15 - 5;
    }

    private void _prependOrWriteCharacterEscape(char c, int i) throws IOException {
        String value;
        int i2;
        if (i >= 0) {
            int i3 = this._outputTail;
            if (i3 >= 2) {
                int i4 = i3 - 2;
                this._outputHead = i4;
                char[] cArr = this._outputBuffer;
                cArr[i4] = '\\';
                cArr[i4 + 1] = (char) i;
                return;
            }
            char[] cArr_allocateEntityBuffer = this._entityBuffer;
            if (cArr_allocateEntityBuffer == null) {
                cArr_allocateEntityBuffer = _allocateEntityBuffer();
            }
            this._outputHead = this._outputTail;
            cArr_allocateEntityBuffer[1] = (char) i;
            this._writer.write(cArr_allocateEntityBuffer, 0, 2);
            return;
        }
        if (i == -2) {
            SerializableString serializableString = this._currentEscape;
            if (serializableString == null) {
                value = this._characterEscapes.getEscapeSequence(c).getValue();
            } else {
                value = serializableString.getValue();
                this._currentEscape = null;
            }
            int length = value.length();
            int i5 = this._outputTail;
            if (i5 < length) {
                this._outputHead = i5;
                this._writer.write(value);
                return;
            } else {
                int i6 = i5 - length;
                this._outputHead = i6;
                value.getChars(0, length, this._outputBuffer, i6);
                return;
            }
        }
        int i7 = this._outputTail;
        if (i7 < 6) {
            char[] cArr_allocateEntityBuffer2 = this._entityBuffer;
            if (cArr_allocateEntityBuffer2 == null) {
                cArr_allocateEntityBuffer2 = _allocateEntityBuffer();
            }
            this._outputHead = this._outputTail;
            if (c <= 255) {
                char[] cArr2 = HEX_CHARS;
                cArr_allocateEntityBuffer2[6] = cArr2[c >> 4];
                cArr_allocateEntityBuffer2[7] = cArr2[c & 15];
                this._writer.write(cArr_allocateEntityBuffer2, 2, 6);
                return;
            }
            int i8 = (c >> '\b') & 255;
            int i9 = c & 255;
            char[] cArr3 = HEX_CHARS;
            cArr_allocateEntityBuffer2[10] = cArr3[i8 >> 4];
            cArr_allocateEntityBuffer2[11] = cArr3[i8 & 15];
            cArr_allocateEntityBuffer2[12] = cArr3[i9 >> 4];
            cArr_allocateEntityBuffer2[13] = cArr3[i9 & 15];
            this._writer.write(cArr_allocateEntityBuffer2, 8, 6);
            return;
        }
        char[] cArr4 = this._outputBuffer;
        int i10 = i7 - 6;
        this._outputHead = i10;
        cArr4[i10] = '\\';
        int i11 = i10 + 1;
        cArr4[i11] = 'u';
        if (c > 255) {
            int i12 = (c >> '\b') & 255;
            int i13 = i11 + 1;
            char[] cArr5 = HEX_CHARS;
            cArr4[i13] = cArr5[i12 >> 4];
            i2 = i13 + 1;
            cArr4[i2] = cArr5[i12 & 15];
            c = (char) (c & 255);
        } else {
            int i14 = i11 + 1;
            cArr4[i14] = '0';
            i2 = i14 + 1;
            cArr4[i2] = '0';
        }
        int i15 = i2 + 1;
        char[] cArr6 = HEX_CHARS;
        cArr4[i15] = cArr6[c >> 4];
        cArr4[i15 + 1] = cArr6[c & 15];
    }

    private int _readMore(InputStream inputStream, byte[] bArr, int i, int i2, int i3) throws IOException {
        int i4 = 0;
        while (i < i2) {
            bArr[i4] = bArr[i];
            i4++;
            i++;
        }
        int iMin = Math.min(i3, bArr.length);
        do {
            int i5 = iMin - i4;
            if (i5 == 0) {
                break;
            }
            int i6 = inputStream.read(bArr, i4, i5);
            if (i6 < 0) {
                return i4;
            }
            i4 += i6;
        } while (i4 < 3);
        return i4;
    }

    private void _writeLongString(String str) throws IOException {
        _flushBuffer();
        int length = str.length();
        int i = 0;
        while (true) {
            int i2 = this._outputEnd;
            if (i + i2 > length) {
                i2 = length - i;
            }
            int i3 = i + i2;
            str.getChars(i, i3, this._outputBuffer, 0);
            if (this._characterEscapes != null) {
                _writeSegmentCustom(i2);
            } else if (this._maximumNonEscapedChar != 0) {
                _writeSegmentASCII(i2, this._maximumNonEscapedChar);
            } else {
                _writeSegment(i2);
            }
            if (i3 >= length) {
                return;
            } else {
                i = i3;
            }
        }
    }

    private final void _writeNull() throws IOException {
        if (this._outputTail + 4 >= this._outputEnd) {
            _flushBuffer();
        }
        int i = this._outputTail;
        char[] cArr = this._outputBuffer;
        cArr[i] = 'n';
        int i2 = i + 1;
        cArr[i2] = 'u';
        int i3 = i2 + 1;
        cArr[i3] = 'l';
        int i4 = i3 + 1;
        cArr[i4] = 'l';
        this._outputTail = i4 + 1;
    }

    private void _writeQuotedInt(int i) throws IOException {
        if (this._outputTail + 13 >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        cArr[i2] = this._quoteChar;
        this._outputTail = NumberOutput.outputInt(i, cArr, this._outputTail);
        char[] cArr2 = this._outputBuffer;
        int i3 = this._outputTail;
        this._outputTail = i3 + 1;
        cArr2[i3] = this._quoteChar;
    }

    private void _writeQuotedLong(long j) throws IOException {
        if (this._outputTail + 23 >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        cArr[i] = this._quoteChar;
        this._outputTail = NumberOutput.outputLong(j, cArr, this._outputTail);
        char[] cArr2 = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        cArr2[i2] = this._quoteChar;
    }

    private void _writeQuotedRaw(String str) throws IOException {
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        cArr[i] = this._quoteChar;
        writeRaw(str);
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr2 = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        cArr2[i2] = this._quoteChar;
    }

    private void _writeQuotedShort(short s) throws IOException {
        if (this._outputTail + 8 >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        cArr[i] = this._quoteChar;
        this._outputTail = NumberOutput.outputInt(s, cArr, this._outputTail);
        char[] cArr2 = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        cArr2[i2] = this._quoteChar;
    }

    private void _writeSegment(int i) throws IOException {
        char c;
        int[] iArr = this._outputEscapes;
        int length = iArr.length;
        int i2 = 0;
        int i_prependOrWriteCharacterEscape = 0;
        while (i2 < i) {
            do {
                c = this._outputBuffer[i2];
                if (c < length && iArr[c] != 0) {
                    break;
                } else {
                    i2++;
                }
            } while (i2 < i);
            int i3 = i2 - i_prependOrWriteCharacterEscape;
            if (i3 > 0) {
                this._writer.write(this._outputBuffer, i_prependOrWriteCharacterEscape, i3);
                if (i2 >= i) {
                    return;
                }
            }
            i2++;
            i_prependOrWriteCharacterEscape = _prependOrWriteCharacterEscape(this._outputBuffer, i2, i, c, iArr[c]);
        }
    }

    /* JADX WARN: Code duplicated, block: B:11:0x001d A[PHI: r4
      0x001d: PHI (r4v5 int) = (r4v2 int), (r4v6 int) binds: [B:9:0x0019, B:7:0x0016] A[DONT_GENERATE, DONT_INLINE]] */
    private void _writeSegmentASCII(int i, int i2) throws IOException {
        char c;
        int[] iArr = this._outputEscapes;
        int iMin = Math.min(iArr.length, i2 + 1);
        int i3 = 0;
        int i_prependOrWriteCharacterEscape = 0;
        int i4 = 0;
        while (i3 < i) {
            do {
                c = this._outputBuffer[i3];
                if (c < iMin) {
                    i4 = iArr[c];
                    if (i4 != 0) {
                        break;
                    } else {
                        i3++;
                    }
                } else {
                    if (c > i2) {
                        i4 = -1;
                        break;
                    }
                    i3++;
                }
            } while (i3 < i);
            int i5 = i3 - i_prependOrWriteCharacterEscape;
            if (i5 > 0) {
                this._writer.write(this._outputBuffer, i_prependOrWriteCharacterEscape, i5);
                if (i3 >= i) {
                    return;
                }
            }
            i3++;
            i_prependOrWriteCharacterEscape = _prependOrWriteCharacterEscape(this._outputBuffer, i3, i, c, i4);
        }
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0034 A[PHI: r6
      0x0034: PHI (r6v6 int) = (r6v2 int), (r6v7 int) binds: [B:16:0x0030, B:11:0x0023] A[DONT_GENERATE, DONT_INLINE]] */
    private void _writeSegmentCustom(int i) throws IOException {
        char c;
        int[] iArr = this._outputEscapes;
        int i2 = this._maximumNonEscapedChar < 1 ? SupportMenu.USER_MASK : this._maximumNonEscapedChar;
        int iMin = Math.min(iArr.length, i2 + 1);
        CharacterEscapes characterEscapes = this._characterEscapes;
        int i3 = 0;
        int i_prependOrWriteCharacterEscape = 0;
        int i4 = 0;
        while (i3 < i) {
            do {
                c = this._outputBuffer[i3];
                if (c < iMin) {
                    i4 = iArr[c];
                    if (i4 != 0) {
                        break;
                    } else {
                        i3++;
                    }
                } else {
                    if (c > i2) {
                        i4 = -1;
                        break;
                    }
                    SerializableString escapeSequence = characterEscapes.getEscapeSequence(c);
                    this._currentEscape = escapeSequence;
                    if (escapeSequence != null) {
                        i4 = -2;
                        break;
                    }
                    i3++;
                }
            } while (i3 < i);
            int i5 = i3 - i_prependOrWriteCharacterEscape;
            if (i5 > 0) {
                this._writer.write(this._outputBuffer, i_prependOrWriteCharacterEscape, i5);
                if (i3 >= i) {
                    return;
                }
            }
            i3++;
            i_prependOrWriteCharacterEscape = _prependOrWriteCharacterEscape(this._outputBuffer, i3, i, c, i4);
        }
    }

    private void _writeString(String str) throws IOException {
        int length = str.length();
        int i = this._outputEnd;
        if (length > i) {
            _writeLongString(str);
            return;
        }
        if (this._outputTail + length > i) {
            _flushBuffer();
        }
        str.getChars(0, length, this._outputBuffer, this._outputTail);
        if (this._characterEscapes != null) {
            _writeStringCustom(length);
        } else if (this._maximumNonEscapedChar != 0) {
            _writeStringASCII(length, this._maximumNonEscapedChar);
        } else {
            _writeString2(length);
        }
    }

    private void _writeString(char[] cArr, int i, int i2) throws IOException {
        if (this._characterEscapes != null) {
            _writeStringCustom(cArr, i, i2);
            return;
        }
        if (this._maximumNonEscapedChar != 0) {
            _writeStringASCII(cArr, i, i2, this._maximumNonEscapedChar);
            return;
        }
        int i3 = i2 + i;
        int[] iArr = this._outputEscapes;
        int length = iArr.length;
        while (i < i3) {
            int i4 = i;
            do {
                char c = cArr[i4];
                if (c < length && iArr[c] != 0) {
                    break;
                } else {
                    i4++;
                }
            } while (i4 < i3);
            int i5 = i4 - i;
            if (i5 < 32) {
                if (this._outputTail + i5 > this._outputEnd) {
                    _flushBuffer();
                }
                if (i5 > 0) {
                    System.arraycopy(cArr, i, this._outputBuffer, this._outputTail, i5);
                    this._outputTail += i5;
                }
            } else {
                _flushBuffer();
                this._writer.write(cArr, i, i5);
            }
            if (i4 >= i3) {
                return;
            }
            i = i4 + 1;
            char c2 = cArr[i4];
            _appendCharacterEscape(c2, iArr[c2]);
        }
    }

    private void _writeString2(int i) throws IOException {
        int i2;
        int i3 = this._outputTail + i;
        int[] iArr = this._outputEscapes;
        int length = iArr.length;
        while (this._outputTail < i3) {
            do {
                char[] cArr = this._outputBuffer;
                int i4 = this._outputTail;
                char c = cArr[i4];
                if (c >= length || iArr[c] == 0) {
                    i2 = this._outputTail + 1;
                    this._outputTail = i2;
                } else {
                    int i5 = this._outputHead;
                    int i6 = i4 - i5;
                    if (i6 > 0) {
                        this._writer.write(cArr, i5, i6);
                    }
                    char[] cArr2 = this._outputBuffer;
                    int i7 = this._outputTail;
                    this._outputTail = i7 + 1;
                    char c2 = cArr2[i7];
                    _prependOrWriteCharacterEscape(c2, iArr[c2]);
                }
            } while (i2 < i3);
            return;
        }
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0027  */
    /* JADX WARN: Code duplicated, block: B:22:0x002e A[SYNTHETIC] */
    private void _writeStringASCII(int i, int i2) throws IOException {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7 = this._outputTail + i;
        int[] iArr = this._outputEscapes;
        int iMin = Math.min(iArr.length, i2 + 1);
        while (this._outputTail < i7) {
            do {
                char c = this._outputBuffer[this._outputTail];
                if (c < iMin) {
                    i3 = iArr[c];
                    if (i3 != 0) {
                        int i8 = this._outputTail;
                        i4 = this._outputHead;
                        i5 = i8 - i4;
                        if (i5 > 0) {
                            this._writer.write(this._outputBuffer, i4, i5);
                        }
                        this._outputTail++;
                        _prependOrWriteCharacterEscape(c, i3);
                    }
                    i6 = this._outputTail + 1;
                    this._outputTail = i6;
                } else {
                    if (c > i2) {
                        i3 = -1;
                        int i9 = this._outputTail;
                        i4 = this._outputHead;
                        i5 = i9 - i4;
                        if (i5 > 0) {
                            this._writer.write(this._outputBuffer, i4, i5);
                        }
                        this._outputTail++;
                        _prependOrWriteCharacterEscape(c, i3);
                    }
                    i6 = this._outputTail + 1;
                    this._outputTail = i6;
                }
            } while (i6 < i7);
            return;
        }
    }

    /* JADX WARN: Code duplicated, block: B:12:0x001c A[PHI: r3
      0x001c: PHI (r3v4 int) = (r3v1 int), (r3v5 int) binds: [B:10:0x0018, B:8:0x0015] A[DONT_GENERATE, DONT_INLINE]] */
    private void _writeStringASCII(char[] cArr, int i, int i2, int i3) throws IOException {
        char c;
        int i4 = i2 + i;
        int[] iArr = this._outputEscapes;
        int iMin = Math.min(iArr.length, i3 + 1);
        int i5 = 0;
        while (i < i4) {
            int i6 = i5;
            int i7 = i;
            do {
                c = cArr[i7];
                if (c < iMin) {
                    i6 = iArr[c];
                    if (i6 != 0) {
                        break;
                    } else {
                        i7++;
                    }
                } else {
                    if (c > i3) {
                        i6 = -1;
                        break;
                    }
                    i7++;
                }
            } while (i7 < i4);
            int i8 = i7 - i;
            if (i8 < 32) {
                if (this._outputTail + i8 > this._outputEnd) {
                    _flushBuffer();
                }
                if (i8 > 0) {
                    System.arraycopy(cArr, i, this._outputBuffer, this._outputTail, i8);
                    this._outputTail += i8;
                }
            } else {
                _flushBuffer();
                this._writer.write(cArr, i, i8);
            }
            if (i7 >= i4) {
                return;
            }
            i = i7 + 1;
            _appendCharacterEscape(c, i6);
            i5 = i6;
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x003e  */
    /* JADX WARN: Code duplicated, block: B:29:0x0045 A[SYNTHETIC] */
    private void _writeStringCustom(int i) throws IOException {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = this._outputTail + i;
        int[] iArr = this._outputEscapes;
        int i7 = this._maximumNonEscapedChar < 1 ? SupportMenu.USER_MASK : this._maximumNonEscapedChar;
        int iMin = Math.min(iArr.length, i7 + 1);
        CharacterEscapes characterEscapes = this._characterEscapes;
        while (this._outputTail < i6) {
            do {
                char c = this._outputBuffer[this._outputTail];
                if (c < iMin) {
                    i2 = iArr[c];
                    if (i2 != 0) {
                        int i8 = this._outputTail;
                        i3 = this._outputHead;
                        i4 = i8 - i3;
                        if (i4 > 0) {
                            this._writer.write(this._outputBuffer, i3, i4);
                        }
                        this._outputTail++;
                        _prependOrWriteCharacterEscape(c, i2);
                    }
                    i5 = this._outputTail + 1;
                    this._outputTail = i5;
                } else {
                    if (c > i7) {
                        i2 = -1;
                    } else {
                        SerializableString escapeSequence = characterEscapes.getEscapeSequence(c);
                        this._currentEscape = escapeSequence;
                        if (escapeSequence != null) {
                            i2 = -2;
                        }
                        i5 = this._outputTail + 1;
                        this._outputTail = i5;
                    }
                    int i9 = this._outputTail;
                    i3 = this._outputHead;
                    i4 = i9 - i3;
                    if (i4 > 0) {
                        this._writer.write(this._outputBuffer, i3, i4);
                    }
                    this._outputTail++;
                    _prependOrWriteCharacterEscape(c, i2);
                }
            } while (i5 < i6);
            return;
        }
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0033 A[PHI: r5
      0x0033: PHI (r5v5 int) = (r5v1 int), (r5v6 int) binds: [B:17:0x002f, B:12:0x0022] A[DONT_GENERATE, DONT_INLINE]] */
    private void _writeStringCustom(char[] cArr, int i, int i2) throws IOException {
        char c;
        int i3 = i2 + i;
        int[] iArr = this._outputEscapes;
        int i4 = this._maximumNonEscapedChar < 1 ? SupportMenu.USER_MASK : this._maximumNonEscapedChar;
        int iMin = Math.min(iArr.length, i4 + 1);
        CharacterEscapes characterEscapes = this._characterEscapes;
        int i5 = 0;
        while (i < i3) {
            int i6 = i5;
            int i7 = i;
            do {
                c = cArr[i7];
                if (c < iMin) {
                    i6 = iArr[c];
                    if (i6 != 0) {
                        break;
                    } else {
                        i7++;
                    }
                } else {
                    if (c > i4) {
                        i6 = -1;
                        break;
                    }
                    SerializableString escapeSequence = characterEscapes.getEscapeSequence(c);
                    this._currentEscape = escapeSequence;
                    if (escapeSequence != null) {
                        i6 = -2;
                        break;
                    }
                    i7++;
                }
            } while (i7 < i3);
            int i8 = i7 - i;
            if (i8 < 32) {
                if (this._outputTail + i8 > this._outputEnd) {
                    _flushBuffer();
                }
                if (i8 > 0) {
                    System.arraycopy(cArr, i, this._outputBuffer, this._outputTail, i8);
                    this._outputTail += i8;
                }
            } else {
                _flushBuffer();
                this._writer.write(cArr, i, i8);
            }
            if (i7 >= i3) {
                return;
            }
            i = i7 + 1;
            _appendCharacterEscape(c, i6);
            i5 = i6;
        }
    }

    private void writeRawLong(String str) throws IOException {
        int i = this._outputEnd;
        int i2 = this._outputTail;
        int i3 = i - i2;
        str.getChars(0, i3, this._outputBuffer, i2);
        this._outputTail += i3;
        _flushBuffer();
        int length = str.length() - i3;
        while (true) {
            int i4 = this._outputEnd;
            if (length <= i4) {
                str.getChars(i3, i3 + length, this._outputBuffer, 0);
                this._outputHead = 0;
                this._outputTail = length;
                return;
            } else {
                int i5 = i3 + i4;
                str.getChars(i3, i5, this._outputBuffer, 0);
                this._outputHead = 0;
                this._outputTail = i4;
                _flushBuffer();
                length -= i4;
                i3 = i5;
            }
        }
    }

    protected void _flushBuffer() throws IOException {
        int i = this._outputTail;
        int i2 = this._outputHead;
        int i3 = i - i2;
        if (i3 > 0) {
            this._outputHead = 0;
            this._outputTail = 0;
            this._writer.write(this._outputBuffer, i2, i3);
        }
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase
    protected void _releaseBuffers() {
        char[] cArr = this._outputBuffer;
        if (cArr != null) {
            this._outputBuffer = null;
            this._ioContext.releaseConcatBuffer(cArr);
        }
        char[] cArr2 = this._charBuffer;
        if (cArr2 != null) {
            this._charBuffer = null;
            this._ioContext.releaseNameCopyBuffer(cArr2);
        }
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase
    protected final void _verifyValueWrite(String str) throws IOException {
        char c;
        int iWriteValue = this._writeContext.writeValue();
        if (this._cfgPrettyPrinter != null) {
            _verifyPrettyValueWrite(str, iWriteValue);
            return;
        }
        if (iWriteValue == 1) {
            c = ',';
        } else {
            if (iWriteValue != 2) {
                if (iWriteValue != 3) {
                    if (iWriteValue != 5) {
                        return;
                    }
                    _reportCantWriteValueExpectName(str);
                    return;
                } else {
                    if (this._rootValueSeparator != null) {
                        writeRaw(this._rootValueSeparator.getValue());
                        return;
                    }
                    return;
                }
            }
            c = ':';
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        cArr[i] = c;
    }

    protected final int _writeBinary(Base64Variant base64Variant, InputStream inputStream, byte[] bArr) throws IOException {
        int i_readMore;
        int i = this._outputEnd - 6;
        int i2 = -3;
        int maxLineLength = base64Variant.getMaxLineLength() >> 2;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i4 > i2) {
                i_readMore = _readMore(inputStream, bArr, i4, i5, bArr.length);
                if (i_readMore < 3) {
                    break;
                }
                i5 = i_readMore;
                i2 = i_readMore - 3;
                i4 = 0;
            }
            if (this._outputTail > i) {
                _flushBuffer();
            }
            int i6 = i4 + 1;
            int i7 = bArr[i4] << 8;
            int i8 = i6 + 1;
            i4 = i8 + 1;
            i3 += 3;
            this._outputTail = base64Variant.encodeBase64Chunk((((bArr[i6] & 255) | i7) << 8) | (bArr[i8] & 255), this._outputBuffer, this._outputTail);
            maxLineLength--;
            if (maxLineLength <= 0) {
                char[] cArr = this._outputBuffer;
                int i9 = this._outputTail;
                this._outputTail = i9 + 1;
                cArr[i9] = '\\';
                int i10 = this._outputTail;
                this._outputTail = i10 + 1;
                cArr[i10] = 'n';
                maxLineLength = base64Variant.getMaxLineLength() >> 2;
            }
        }
        if (i_readMore <= 0) {
            return i3;
        }
        if (this._outputTail > i) {
            _flushBuffer();
        }
        int i11 = bArr[0] << Ascii.DLE;
        int i12 = 1;
        if (1 < i_readMore) {
            i11 |= (bArr[1] & 255) << 8;
            i12 = 2;
        }
        int i13 = i3 + i12;
        this._outputTail = base64Variant.encodeBase64Partial(i11, i12, this._outputBuffer, this._outputTail);
        return i13;
    }

    protected final int _writeBinary(Base64Variant base64Variant, InputStream inputStream, byte[] bArr, int i) throws IOException {
        int i_readMore;
        int i2 = this._outputEnd - 6;
        int maxLineLength = base64Variant.getMaxLineLength() >> 2;
        int i3 = -3;
        int i4 = 0;
        int i5 = 0;
        while (i > 2) {
            if (i4 > i3) {
                int i_readMore2 = _readMore(inputStream, bArr, i4, i5, i);
                if (i_readMore2 < 3) {
                    i5 = i_readMore2;
                    i4 = 0;
                    break;
                }
                i5 = i_readMore2;
                i3 = i_readMore2 - 3;
                i4 = 0;
            }
            if (this._outputTail > i2) {
                _flushBuffer();
            }
            int i6 = i4 + 1;
            int i7 = bArr[i4] << 8;
            int i8 = i6 + 1;
            i4 = i8 + 1;
            i -= 3;
            this._outputTail = base64Variant.encodeBase64Chunk((((bArr[i6] & 255) | i7) << 8) | (bArr[i8] & 255), this._outputBuffer, this._outputTail);
            maxLineLength--;
            if (maxLineLength <= 0) {
                char[] cArr = this._outputBuffer;
                int i9 = this._outputTail;
                this._outputTail = i9 + 1;
                cArr[i9] = '\\';
                int i10 = this._outputTail;
                this._outputTail = i10 + 1;
                cArr[i10] = 'n';
                maxLineLength = base64Variant.getMaxLineLength() >> 2;
            }
        }
        if (i <= 0 || (i_readMore = _readMore(inputStream, bArr, i4, i5, i)) <= 0) {
            return i;
        }
        if (this._outputTail > i2) {
            _flushBuffer();
        }
        int i11 = bArr[0] << Ascii.DLE;
        int i12 = 1;
        if (1 < i_readMore) {
            i11 |= (bArr[1] & 255) << 8;
            i12 = 2;
        }
        this._outputTail = base64Variant.encodeBase64Partial(i11, i12, this._outputBuffer, this._outputTail);
        return i - i12;
    }

    protected final void _writeBinary(Base64Variant base64Variant, byte[] bArr, int i, int i2) throws IOException {
        int i3 = i2 - 3;
        int i4 = this._outputEnd - 6;
        int maxLineLength = base64Variant.getMaxLineLength() >> 2;
        while (i <= i3) {
            if (this._outputTail > i4) {
                _flushBuffer();
            }
            int i5 = i + 1;
            int i6 = i5 + 1;
            int i7 = ((bArr[i] << 8) | (bArr[i5] & 255)) << 8;
            int i8 = i6 + 1;
            this._outputTail = base64Variant.encodeBase64Chunk(i7 | (bArr[i6] & 255), this._outputBuffer, this._outputTail);
            maxLineLength--;
            if (maxLineLength <= 0) {
                char[] cArr = this._outputBuffer;
                int i9 = this._outputTail;
                this._outputTail = i9 + 1;
                cArr[i9] = '\\';
                int i10 = this._outputTail;
                this._outputTail = i10 + 1;
                cArr[i10] = 'n';
                maxLineLength = base64Variant.getMaxLineLength() >> 2;
            }
            i = i8;
        }
        int i11 = i2 - i;
        if (i11 > 0) {
            if (this._outputTail > i4) {
                _flushBuffer();
            }
            int i12 = i + 1;
            int i13 = bArr[i] << Ascii.DLE;
            if (i11 == 2) {
                i13 |= (bArr[i12] & 255) << 8;
            }
            this._outputTail = base64Variant.encodeBase64Partial(i13, i11, this._outputBuffer, this._outputTail);
        }
    }

    protected final void _writeFieldName(SerializableString serializableString, boolean z) throws IOException {
        if (this._cfgPrettyPrinter != null) {
            _writePPFieldName(serializableString, z);
            return;
        }
        if (this._outputTail + 1 >= this._outputEnd) {
            _flushBuffer();
        }
        if (z) {
            char[] cArr = this._outputBuffer;
            int i = this._outputTail;
            this._outputTail = i + 1;
            cArr[i] = ',';
        }
        char[] cArrAsQuotedChars = serializableString.asQuotedChars();
        if (this._cfgUnqNames) {
            writeRaw(cArrAsQuotedChars, 0, cArrAsQuotedChars.length);
            return;
        }
        char[] cArr2 = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        cArr2[i2] = this._quoteChar;
        int length = cArrAsQuotedChars.length;
        int i3 = this._outputTail;
        if (i3 + length + 1 < this._outputEnd) {
            System.arraycopy(cArrAsQuotedChars, 0, cArr2, i3, length);
            this._outputTail += length;
            char[] cArr3 = this._outputBuffer;
            int i4 = this._outputTail;
            this._outputTail = i4 + 1;
            cArr3[i4] = this._quoteChar;
            return;
        }
        writeRaw(cArrAsQuotedChars, 0, length);
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr4 = this._outputBuffer;
        int i5 = this._outputTail;
        this._outputTail = i5 + 1;
        cArr4[i5] = this._quoteChar;
    }

    protected final void _writeFieldName(String str, boolean z) throws IOException {
        if (this._cfgPrettyPrinter != null) {
            _writePPFieldName(str, z);
            return;
        }
        if (this._outputTail + 1 >= this._outputEnd) {
            _flushBuffer();
        }
        if (z) {
            char[] cArr = this._outputBuffer;
            int i = this._outputTail;
            this._outputTail = i + 1;
            cArr[i] = ',';
        }
        if (this._cfgUnqNames) {
            _writeString(str);
            return;
        }
        char[] cArr2 = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        cArr2[i2] = this._quoteChar;
        _writeString(str);
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr3 = this._outputBuffer;
        int i3 = this._outputTail;
        this._outputTail = i3 + 1;
        cArr3[i3] = this._quoteChar;
    }

    protected final void _writePPFieldName(SerializableString serializableString, boolean z) throws IOException {
        if (z) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        } else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        char[] cArrAsQuotedChars = serializableString.asQuotedChars();
        if (this._cfgUnqNames) {
            writeRaw(cArrAsQuotedChars, 0, cArrAsQuotedChars.length);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        cArr[i] = this._quoteChar;
        writeRaw(cArrAsQuotedChars, 0, cArrAsQuotedChars.length);
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr2 = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        cArr2[i2] = this._quoteChar;
    }

    protected final void _writePPFieldName(String str, boolean z) throws IOException {
        if (z) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        } else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        if (this._cfgUnqNames) {
            _writeString(str);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        cArr[i] = this._quoteChar;
        _writeString(str);
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr2 = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        cArr2[i2] = this._quoteChar;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public boolean canWriteFormattedNumbers() {
        return true;
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        if (this._outputBuffer != null && isEnabled(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT)) {
            while (true) {
                JsonStreamContext outputContext = getOutputContext();
                if (!outputContext.inArray()) {
                    if (!outputContext.inObject()) {
                        break;
                    } else {
                        writeEndObject();
                    }
                } else {
                    writeEndArray();
                }
            }
        }
        _flushBuffer();
        this._outputHead = 0;
        this._outputTail = 0;
        if (this._writer != null) {
            if (this._ioContext.isResourceManaged() || isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET)) {
                this._writer.close();
            } else if (isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
                this._writer.flush();
            }
        }
        _releaseBuffers();
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator, java.io.Flushable
    public void flush() throws IOException {
        _flushBuffer();
        if (this._writer == null || !isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
            return;
        }
        this._writer.flush();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public int getOutputBuffered() {
        return Math.max(0, this._outputTail - this._outputHead);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public Object getOutputTarget() {
        return this._writer;
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator
    public int writeBinary(Base64Variant base64Variant, InputStream inputStream, int i) throws IOException {
        _verifyValueWrite("write a binary value");
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        cArr[i2] = this._quoteChar;
        byte[] bArrAllocBase64Buffer = this._ioContext.allocBase64Buffer();
        try {
            if (i < 0) {
                i = _writeBinary(base64Variant, inputStream, bArrAllocBase64Buffer);
            } else {
                int i_writeBinary = _writeBinary(base64Variant, inputStream, bArrAllocBase64Buffer, i);
                if (i_writeBinary > 0) {
                    _reportError("Too few bytes available: missing " + i_writeBinary + " bytes (out of " + i + ")");
                }
            }
            this._ioContext.releaseBase64Buffer(bArrAllocBase64Buffer);
            if (this._outputTail >= this._outputEnd) {
                _flushBuffer();
            }
            char[] cArr2 = this._outputBuffer;
            int i3 = this._outputTail;
            this._outputTail = i3 + 1;
            cArr2[i3] = this._quoteChar;
            return i;
        } catch (Throwable th) {
            this._ioContext.releaseBase64Buffer(bArrAllocBase64Buffer);
            throw th;
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeBinary(Base64Variant base64Variant, byte[] bArr, int i, int i2) throws IOException {
        _verifyValueWrite("write a binary value");
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr = this._outputBuffer;
        int i3 = this._outputTail;
        this._outputTail = i3 + 1;
        cArr[i3] = this._quoteChar;
        _writeBinary(base64Variant, bArr, i, i2 + i);
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr2 = this._outputBuffer;
        int i4 = this._outputTail;
        this._outputTail = i4 + 1;
        cArr2[i4] = this._quoteChar;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeBoolean(boolean z) throws IOException {
        int i;
        _verifyValueWrite("write a boolean value");
        if (this._outputTail + 5 >= this._outputEnd) {
            _flushBuffer();
        }
        int i2 = this._outputTail;
        char[] cArr = this._outputBuffer;
        if (z) {
            cArr[i2] = 't';
            int i3 = i2 + 1;
            cArr[i3] = 'r';
            int i4 = i3 + 1;
            cArr[i4] = 'u';
            i = i4 + 1;
            cArr[i] = 'e';
        } else {
            cArr[i2] = 'f';
            int i5 = i2 + 1;
            cArr[i5] = 'a';
            int i6 = i5 + 1;
            cArr[i6] = 'l';
            int i7 = i6 + 1;
            cArr[i7] = 's';
            i = i7 + 1;
            cArr[i] = 'e';
        }
        this._outputTail = i + 1;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeEndArray() throws IOException {
        if (!this._writeContext.inArray()) {
            _reportError("Current context not Array but " + this._writeContext.typeDesc());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
        } else {
            if (this._outputTail >= this._outputEnd) {
                _flushBuffer();
            }
            char[] cArr = this._outputBuffer;
            int i = this._outputTail;
            this._outputTail = i + 1;
            cArr[i] = ']';
        }
        this._writeContext = this._writeContext.clearAndGetParent();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeEndObject() throws IOException {
        if (!this._writeContext.inObject()) {
            _reportError("Current context not Object but " + this._writeContext.typeDesc());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
        } else {
            if (this._outputTail >= this._outputEnd) {
                _flushBuffer();
            }
            char[] cArr = this._outputBuffer;
            int i = this._outputTail;
            this._outputTail = i + 1;
            cArr[i] = '}';
        }
        this._writeContext = this._writeContext.clearAndGetParent();
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator
    public void writeFieldName(SerializableString serializableString) throws IOException {
        int iWriteFieldName = this._writeContext.writeFieldName(serializableString.getValue());
        if (iWriteFieldName == 4) {
            _reportError("Can not write a field name, expecting a value");
        }
        _writeFieldName(serializableString, iWriteFieldName == 1);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeFieldName(String str) throws IOException {
        int iWriteFieldName = this._writeContext.writeFieldName(str);
        if (iWriteFieldName == 4) {
            _reportError("Can not write a field name, expecting a value");
        }
        _writeFieldName(str, iWriteFieldName == 1);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNull() throws IOException {
        _verifyValueWrite("write a null");
        _writeNull();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(double d) throws IOException {
        if (this._cfgNumbersAsStrings || (isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS) && (Double.isNaN(d) || Double.isInfinite(d)))) {
            writeString(String.valueOf(d));
        } else {
            _verifyValueWrite("write a number");
            writeRaw(String.valueOf(d));
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(float f) throws IOException {
        if (this._cfgNumbersAsStrings || (isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS) && (Float.isNaN(f) || Float.isInfinite(f)))) {
            writeString(String.valueOf(f));
        } else {
            _verifyValueWrite("write a number");
            writeRaw(String.valueOf(f));
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(int i) throws IOException {
        _verifyValueWrite("write a number");
        if (this._cfgNumbersAsStrings) {
            _writeQuotedInt(i);
            return;
        }
        if (this._outputTail + 11 >= this._outputEnd) {
            _flushBuffer();
        }
        this._outputTail = NumberOutput.outputInt(i, this._outputBuffer, this._outputTail);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(long j) throws IOException {
        _verifyValueWrite("write a number");
        if (this._cfgNumbersAsStrings) {
            _writeQuotedLong(j);
            return;
        }
        if (this._outputTail + 21 >= this._outputEnd) {
            _flushBuffer();
        }
        this._outputTail = NumberOutput.outputLong(j, this._outputBuffer, this._outputTail);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(String str) throws IOException {
        _verifyValueWrite("write a number");
        if (this._cfgNumbersAsStrings) {
            _writeQuotedRaw(str);
        } else {
            writeRaw(str);
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(BigDecimal bigDecimal) throws IOException {
        _verifyValueWrite("write a number");
        if (bigDecimal == null) {
            _writeNull();
        } else if (this._cfgNumbersAsStrings) {
            _writeQuotedRaw(_asString(bigDecimal));
        } else {
            writeRaw(_asString(bigDecimal));
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(BigInteger bigInteger) throws IOException {
        _verifyValueWrite("write a number");
        if (bigInteger == null) {
            _writeNull();
        } else if (this._cfgNumbersAsStrings) {
            _writeQuotedRaw(bigInteger.toString());
        } else {
            writeRaw(bigInteger.toString());
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(short s) throws IOException {
        _verifyValueWrite("write a number");
        if (this._cfgNumbersAsStrings) {
            _writeQuotedShort(s);
            return;
        }
        if (this._outputTail + 6 >= this._outputEnd) {
            _flushBuffer();
        }
        this._outputTail = NumberOutput.outputInt(s, this._outputBuffer, this._outputTail);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(char c) throws IOException {
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        cArr[i] = c;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(SerializableString serializableString) throws IOException {
        writeRaw(serializableString.getValue());
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(String str) throws IOException {
        int length = str.length();
        int i = this._outputEnd - this._outputTail;
        if (i == 0) {
            _flushBuffer();
            i = this._outputEnd - this._outputTail;
        }
        if (i < length) {
            writeRawLong(str);
        } else {
            str.getChars(0, length, this._outputBuffer, this._outputTail);
            this._outputTail += length;
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(String str, int i, int i2) throws IOException {
        int i3 = this._outputEnd - this._outputTail;
        if (i3 < i2) {
            _flushBuffer();
            i3 = this._outputEnd - this._outputTail;
        }
        if (i3 < i2) {
            writeRawLong(str.substring(i, i2 + i));
        } else {
            str.getChars(i, i + i2, this._outputBuffer, this._outputTail);
            this._outputTail += i2;
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(char[] cArr, int i, int i2) throws IOException {
        if (i2 >= 32) {
            _flushBuffer();
            this._writer.write(cArr, i, i2);
        } else {
            if (i2 > this._outputEnd - this._outputTail) {
                _flushBuffer();
            }
            System.arraycopy(cArr, i, this._outputBuffer, this._outputTail, i2);
            this._outputTail += i2;
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRawUTF8String(byte[] bArr, int i, int i2) throws IOException {
        _reportUnsupportedOperation();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeStartArray() throws IOException {
        _verifyValueWrite("start an array");
        this._writeContext = this._writeContext.createChildArrayContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartArray(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        cArr[i] = '[';
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeStartObject() throws IOException {
        _verifyValueWrite("start an object");
        this._writeContext = this._writeContext.createChildObjectContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        cArr[i] = '{';
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator
    public void writeStartObject(Object obj) throws IOException {
        _verifyValueWrite("start an object");
        JsonWriteContext jsonWriteContextCreateChildObjectContext = this._writeContext.createChildObjectContext();
        this._writeContext = jsonWriteContextCreateChildObjectContext;
        if (obj != null) {
            jsonWriteContextCreateChildObjectContext.setCurrentValue(obj);
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        cArr[i] = '{';
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator
    public void writeString(SerializableString serializableString) throws IOException {
        _verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        cArr[i] = this._quoteChar;
        char[] cArrAsQuotedChars = serializableString.asQuotedChars();
        int length = cArrAsQuotedChars.length;
        if (length < 32) {
            if (length > this._outputEnd - this._outputTail) {
                _flushBuffer();
            }
            System.arraycopy(cArrAsQuotedChars, 0, this._outputBuffer, this._outputTail, length);
            this._outputTail += length;
        } else {
            _flushBuffer();
            this._writer.write(cArrAsQuotedChars, 0, length);
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr2 = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        cArr2[i2] = this._quoteChar;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeString(Reader reader, int i) throws IOException {
        _verifyValueWrite("write a string");
        if (reader == null) {
            _reportError("null reader");
        }
        int i2 = i >= 0 ? i : Integer.MAX_VALUE;
        char[] cArr_allocateCopyBuffer = _allocateCopyBuffer();
        if (this._outputTail + i >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr = this._outputBuffer;
        int i3 = this._outputTail;
        this._outputTail = i3 + 1;
        cArr[i3] = this._quoteChar;
        while (i2 > 0) {
            int i4 = reader.read(cArr_allocateCopyBuffer, 0, Math.min(i2, cArr_allocateCopyBuffer.length));
            if (i4 <= 0) {
                break;
            }
            if (this._outputTail + i >= this._outputEnd) {
                _flushBuffer();
            }
            _writeString(cArr_allocateCopyBuffer, 0, i4);
            i2 -= i4;
        }
        if (this._outputTail + i >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr2 = this._outputBuffer;
        int i5 = this._outputTail;
        this._outputTail = i5 + 1;
        cArr2[i5] = this._quoteChar;
        if (i2 <= 0 || i < 0) {
            return;
        }
        _reportError("Didn't read enough from reader");
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeString(String str) throws IOException {
        _verifyValueWrite("write a string");
        if (str == null) {
            _writeNull();
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        cArr[i] = this._quoteChar;
        _writeString(str);
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr2 = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        cArr2[i2] = this._quoteChar;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeString(char[] cArr, int i, int i2) throws IOException {
        _verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr2 = this._outputBuffer;
        int i3 = this._outputTail;
        this._outputTail = i3 + 1;
        cArr2[i3] = this._quoteChar;
        _writeString(cArr, i, i2);
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        char[] cArr3 = this._outputBuffer;
        int i4 = this._outputTail;
        this._outputTail = i4 + 1;
        cArr3[i4] = this._quoteChar;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeUTF8String(byte[] bArr, int i, int i2) throws IOException {
        _reportUnsupportedOperation();
    }
}
