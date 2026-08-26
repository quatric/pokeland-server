package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.base.GeneratorBase;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.p014io.CharTypes;
import com.fasterxml.jackson.core.p014io.IOContext;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Arrays;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class UTF8DataInputJsonParser extends ParserBase {
    static final byte BYTE_LF = 10;
    protected DataInput _inputData;
    protected int _nextByte;
    protected ObjectCodec _objectCodec;
    private int _quad1;
    protected int[] _quadBuffer;
    protected final ByteQuadsCanonicalizer _symbols;
    protected boolean _tokenIncomplete;
    private static final int[] _icUTF8 = CharTypes.getInputCodeUtf8();
    protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();

    public UTF8DataInputJsonParser(IOContext iOContext, int i, DataInput dataInput, ObjectCodec objectCodec, ByteQuadsCanonicalizer byteQuadsCanonicalizer, int i2) {
        super(iOContext, i);
        this._quadBuffer = new int[16];
        this._nextByte = -1;
        this._objectCodec = objectCodec;
        this._symbols = byteQuadsCanonicalizer;
        this._inputData = dataInput;
        this._nextByte = i2;
    }

    private final void _checkMatchEnd(String str, int i, int i2) throws IOException {
        char c_decodeCharForError = (char) _decodeCharForError(i2);
        if (Character.isJavaIdentifierPart(c_decodeCharForError)) {
            _reportInvalidToken(c_decodeCharForError, str.substring(0, i));
        }
    }

    private void _closeScope(int i) throws JsonParseException {
        if (i == 93) {
            if (!this._parsingContext.inArray()) {
                _reportMismatchedEndMarker(i, '}');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_ARRAY;
        }
        if (i == 125) {
            if (!this._parsingContext.inObject()) {
                _reportMismatchedEndMarker(i, ']');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_OBJECT;
        }
    }

    private final int _decodeUtf8_2(int i) throws IOException {
        int unsignedByte = this._inputData.readUnsignedByte();
        if ((unsignedByte & 192) != 128) {
            _reportInvalidOther(unsignedByte & 255);
        }
        return ((i & 31) << 6) | (unsignedByte & 63);
    }

    private final int _decodeUtf8_3(int i) throws IOException {
        int i2 = i & 15;
        int unsignedByte = this._inputData.readUnsignedByte();
        if ((unsignedByte & 192) != 128) {
            _reportInvalidOther(unsignedByte & 255);
        }
        int i3 = (i2 << 6) | (unsignedByte & 63);
        int unsignedByte2 = this._inputData.readUnsignedByte();
        if ((unsignedByte2 & 192) != 128) {
            _reportInvalidOther(unsignedByte2 & 255);
        }
        return (i3 << 6) | (unsignedByte2 & 63);
    }

    private final int _decodeUtf8_4(int i) throws IOException {
        int unsignedByte = this._inputData.readUnsignedByte();
        if ((unsignedByte & 192) != 128) {
            _reportInvalidOther(unsignedByte & 255);
        }
        int i2 = ((i & 7) << 6) | (unsignedByte & 63);
        int unsignedByte2 = this._inputData.readUnsignedByte();
        if ((unsignedByte2 & 192) != 128) {
            _reportInvalidOther(unsignedByte2 & 255);
        }
        int i3 = (i2 << 6) | (unsignedByte2 & 63);
        int unsignedByte3 = this._inputData.readUnsignedByte();
        if ((unsignedByte3 & 192) != 128) {
            _reportInvalidOther(unsignedByte3 & 255);
        }
        return ((i3 << 6) | (unsignedByte3 & 63)) - 65536;
    }

    private String _finishAndReturnString() throws IOException {
        char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int[] iArr = _icUTF8;
        int length = cArrEmptyAndGetCurrentSegment.length;
        int i = 0;
        while (true) {
            int unsignedByte = this._inputData.readUnsignedByte();
            if (iArr[unsignedByte] != 0) {
                if (unsignedByte == 34) {
                    return this._textBuffer.setCurrentAndReturn(i);
                }
                _finishString2(cArrEmptyAndGetCurrentSegment, i, unsignedByte);
                return this._textBuffer.contentsAsString();
            }
            int i2 = i + 1;
            cArrEmptyAndGetCurrentSegment[i] = (char) unsignedByte;
            if (i2 >= length) {
                _finishString2(cArrEmptyAndGetCurrentSegment, i2, this._inputData.readUnsignedByte());
                return this._textBuffer.contentsAsString();
            }
            i = i2;
        }
    }

    private final void _finishString2(char[] cArr, int i, int i2) throws IOException {
        int i3;
        int[] iArr = _icUTF8;
        int length = cArr.length;
        while (true) {
            if (iArr[i2] == 0) {
                if (i >= length) {
                    cArr = this._textBuffer.finishCurrentSegment();
                    length = cArr.length;
                    i = 0;
                }
                i3 = i + 1;
                cArr[i] = (char) i2;
                i2 = this._inputData.readUnsignedByte();
            } else {
                if (i2 == 34) {
                    this._textBuffer.setCurrentLength(i);
                    return;
                }
                int i4 = iArr[i2];
                if (i4 == 1) {
                    i2 = _decodeEscaped();
                } else if (i4 == 2) {
                    i2 = _decodeUtf8_2(i2);
                } else if (i4 == 3) {
                    i2 = _decodeUtf8_3(i2);
                } else if (i4 == 4) {
                    int i_decodeUtf8_4 = _decodeUtf8_4(i2);
                    int i5 = i + 1;
                    cArr[i] = (char) (55296 | (i_decodeUtf8_4 >> 10));
                    if (i5 >= cArr.length) {
                        cArr = this._textBuffer.finishCurrentSegment();
                        length = cArr.length;
                        i = 0;
                    } else {
                        i = i5;
                    }
                    i2 = (i_decodeUtf8_4 & 1023) | GeneratorBase.SURR2_FIRST;
                } else if (i2 < 32) {
                    _throwUnquotedSpace(i2, "string value");
                } else {
                    _reportInvalidChar(i2);
                }
                if (i >= cArr.length) {
                    cArr = this._textBuffer.finishCurrentSegment();
                    length = cArr.length;
                    i = 0;
                }
                i3 = i + 1;
                cArr[i] = (char) i2;
                i2 = this._inputData.readUnsignedByte();
            }
            i = i3;
        }
    }

    private static int[] _growArrayBy(int[] iArr, int i) {
        return iArr == null ? new int[i] : Arrays.copyOf(iArr, iArr.length + i);
    }

    private final int _handleLeadingZeroes() throws IOException {
        int unsignedByte = this._inputData.readUnsignedByte();
        if (unsignedByte >= 48 && unsignedByte <= 57) {
            if (!isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
                reportInvalidNumber("Leading zeroes not allowed");
            }
            while (unsignedByte == 48) {
                unsignedByte = this._inputData.readUnsignedByte();
            }
        }
        return unsignedByte;
    }

    private final JsonToken _nextAfterName() {
        this._nameCopied = false;
        JsonToken jsonToken = this._nextToken;
        this._nextToken = null;
        if (jsonToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        } else if (jsonToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        this._currToken = jsonToken;
        return jsonToken;
    }

    private final JsonToken _nextTokenNotInObject(int i) throws IOException {
        if (i == 34) {
            this._tokenIncomplete = true;
            JsonToken jsonToken = JsonToken.VALUE_STRING;
            this._currToken = jsonToken;
            return jsonToken;
        }
        if (i == 45) {
            JsonToken jsonToken_parseNegNumber = _parseNegNumber();
            this._currToken = jsonToken_parseNegNumber;
            return jsonToken_parseNegNumber;
        }
        if (i == 91) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            JsonToken jsonToken2 = JsonToken.START_ARRAY;
            this._currToken = jsonToken2;
            return jsonToken2;
        }
        if (i == 102) {
            _matchToken("false", 1);
            JsonToken jsonToken3 = JsonToken.VALUE_FALSE;
            this._currToken = jsonToken3;
            return jsonToken3;
        }
        if (i == 110) {
            _matchToken("null", 1);
            JsonToken jsonToken4 = JsonToken.VALUE_NULL;
            this._currToken = jsonToken4;
            return jsonToken4;
        }
        if (i == 116) {
            _matchToken("true", 1);
            JsonToken jsonToken5 = JsonToken.VALUE_TRUE;
            this._currToken = jsonToken5;
            return jsonToken5;
        }
        if (i == 123) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            JsonToken jsonToken6 = JsonToken.START_OBJECT;
            this._currToken = jsonToken6;
            return jsonToken6;
        }
        switch (i) {
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
                JsonToken jsonToken_parsePosNumber = _parsePosNumber(i);
                this._currToken = jsonToken_parsePosNumber;
                return jsonToken_parsePosNumber;
            default:
                JsonToken jsonToken_handleUnexpectedValue = _handleUnexpectedValue(i);
                this._currToken = jsonToken_handleUnexpectedValue;
                return jsonToken_handleUnexpectedValue;
        }
    }

    private final JsonToken _parseFloat(char[] cArr, int i, int i2, boolean z, int i3) throws IOException {
        int i4;
        int i5;
        int unsignedByte;
        if (i2 == 46) {
            cArr[i] = (char) i2;
            char[] cArrFinishCurrentSegment = cArr;
            i++;
            int i6 = 0;
            while (true) {
                unsignedByte = this._inputData.readUnsignedByte();
                if (unsignedByte < 48 || unsignedByte > 57) {
                    break;
                }
                i6++;
                if (i >= cArrFinishCurrentSegment.length) {
                    cArrFinishCurrentSegment = this._textBuffer.finishCurrentSegment();
                    i = 0;
                }
                cArrFinishCurrentSegment[i] = (char) unsignedByte;
                i++;
            }
            if (i6 == 0) {
                reportUnexpectedNumberChar(unsignedByte, "Decimal point not followed by a digit");
            }
            i4 = i6;
            cArr = cArrFinishCurrentSegment;
            i2 = unsignedByte;
        } else {
            i4 = 0;
        }
        if (i2 == 101 || i2 == 69) {
            if (i >= cArr.length) {
                cArr = this._textBuffer.finishCurrentSegment();
                i = 0;
            }
            int i7 = i + 1;
            cArr[i] = (char) i2;
            int unsignedByte2 = this._inputData.readUnsignedByte();
            if (unsignedByte2 == 45 || unsignedByte2 == 43) {
                if (i7 >= cArr.length) {
                    cArr = this._textBuffer.finishCurrentSegment();
                    i7 = 0;
                }
                cArr[i7] = (char) unsignedByte2;
                unsignedByte2 = this._inputData.readUnsignedByte();
                i7++;
            }
            i2 = unsignedByte2;
            char[] cArrFinishCurrentSegment2 = cArr;
            i5 = 0;
            while (i2 <= 57 && i2 >= 48) {
                i5++;
                if (i7 >= cArrFinishCurrentSegment2.length) {
                    cArrFinishCurrentSegment2 = this._textBuffer.finishCurrentSegment();
                    i7 = 0;
                }
                cArrFinishCurrentSegment2[i7] = (char) i2;
                i2 = this._inputData.readUnsignedByte();
                i7++;
            }
            if (i5 == 0) {
                reportUnexpectedNumberChar(i2, "Exponent indicator not followed by a digit");
            }
            i = i7;
        } else {
            i5 = 0;
        }
        this._nextByte = i2;
        if (this._parsingContext.inRoot()) {
            _verifyRootSpace();
        }
        this._textBuffer.setCurrentLength(i);
        return resetFloat(z, i3, i4, i5);
    }

    private final String _parseLongName(int i, int i2, int i3) throws IOException {
        int[] iArr = this._quadBuffer;
        iArr[0] = this._quad1;
        iArr[1] = i2;
        iArr[2] = i3;
        int[] iArr2 = _icLatin1;
        int i4 = i;
        int i5 = 3;
        while (true) {
            int unsignedByte = this._inputData.readUnsignedByte();
            if (iArr2[unsignedByte] != 0) {
                return unsignedByte == 34 ? findName(this._quadBuffer, i5, i4, 1) : parseEscapedName(this._quadBuffer, i5, i4, unsignedByte, 1);
            }
            int i6 = (i4 << 8) | unsignedByte;
            int unsignedByte2 = this._inputData.readUnsignedByte();
            if (iArr2[unsignedByte2] != 0) {
                return unsignedByte2 == 34 ? findName(this._quadBuffer, i5, i6, 2) : parseEscapedName(this._quadBuffer, i5, i6, unsignedByte2, 2);
            }
            int i7 = (i6 << 8) | unsignedByte2;
            int unsignedByte3 = this._inputData.readUnsignedByte();
            if (iArr2[unsignedByte3] != 0) {
                return unsignedByte3 == 34 ? findName(this._quadBuffer, i5, i7, 3) : parseEscapedName(this._quadBuffer, i5, i7, unsignedByte3, 3);
            }
            int i8 = (i7 << 8) | unsignedByte3;
            int unsignedByte4 = this._inputData.readUnsignedByte();
            if (iArr2[unsignedByte4] != 0) {
                return unsignedByte4 == 34 ? findName(this._quadBuffer, i5, i8, 4) : parseEscapedName(this._quadBuffer, i5, i8, unsignedByte4, 4);
            }
            int[] iArr3 = this._quadBuffer;
            if (i5 >= iArr3.length) {
                this._quadBuffer = _growArrayBy(iArr3, i5);
            }
            this._quadBuffer[i5] = i8;
            i5++;
            i4 = unsignedByte4;
        }
    }

    private final String _parseMediumName(int i) throws IOException {
        int[] iArr = _icLatin1;
        int unsignedByte = this._inputData.readUnsignedByte();
        if (iArr[unsignedByte] != 0) {
            return unsignedByte == 34 ? findName(this._quad1, i, 1) : parseName(this._quad1, i, unsignedByte, 1);
        }
        int i2 = (i << 8) | unsignedByte;
        int unsignedByte2 = this._inputData.readUnsignedByte();
        if (iArr[unsignedByte2] != 0) {
            return unsignedByte2 == 34 ? findName(this._quad1, i2, 2) : parseName(this._quad1, i2, unsignedByte2, 2);
        }
        int i3 = (i2 << 8) | unsignedByte2;
        int unsignedByte3 = this._inputData.readUnsignedByte();
        if (iArr[unsignedByte3] != 0) {
            return unsignedByte3 == 34 ? findName(this._quad1, i3, 3) : parseName(this._quad1, i3, unsignedByte3, 3);
        }
        int i4 = (i3 << 8) | unsignedByte3;
        int unsignedByte4 = this._inputData.readUnsignedByte();
        if (iArr[unsignedByte4] != 0) {
            return unsignedByte4 == 34 ? findName(this._quad1, i4, 4) : parseName(this._quad1, i4, unsignedByte4, 4);
        }
        return _parseMediumName2(unsignedByte4, i4);
    }

    private final String _parseMediumName2(int i, int i2) throws IOException {
        int[] iArr = _icLatin1;
        int unsignedByte = this._inputData.readUnsignedByte();
        if (iArr[unsignedByte] != 0) {
            return unsignedByte == 34 ? findName(this._quad1, i2, i, 1) : parseName(this._quad1, i2, i, unsignedByte, 1);
        }
        int i3 = (i << 8) | unsignedByte;
        int unsignedByte2 = this._inputData.readUnsignedByte();
        if (iArr[unsignedByte2] != 0) {
            return unsignedByte2 == 34 ? findName(this._quad1, i2, i3, 2) : parseName(this._quad1, i2, i3, unsignedByte2, 2);
        }
        int i4 = (i3 << 8) | unsignedByte2;
        int unsignedByte3 = this._inputData.readUnsignedByte();
        if (iArr[unsignedByte3] != 0) {
            return unsignedByte3 == 34 ? findName(this._quad1, i2, i4, 3) : parseName(this._quad1, i2, i4, unsignedByte3, 3);
        }
        int i5 = (i4 << 8) | unsignedByte3;
        int unsignedByte4 = this._inputData.readUnsignedByte();
        if (iArr[unsignedByte4] != 0) {
            return unsignedByte4 == 34 ? findName(this._quad1, i2, i5, 4) : parseName(this._quad1, i2, i5, unsignedByte4, 4);
        }
        return _parseLongName(unsignedByte4, i2, i5);
    }

    private void _reportInvalidOther(int i) throws JsonParseException {
        _reportError("Invalid UTF-8 middle byte 0x" + Integer.toHexString(i));
    }

    private final void _skipCComment() throws IOException {
        int[] inputCodeComment = CharTypes.getInputCodeComment();
        int unsignedByte = this._inputData.readUnsignedByte();
        while (true) {
            int i = inputCodeComment[unsignedByte];
            if (i != 0) {
                if (i == 2) {
                    _skipUtf8_2();
                } else if (i == 3) {
                    _skipUtf8_3();
                } else if (i == 4) {
                    _skipUtf8_4();
                } else if (i == 10 || i == 13) {
                    this._currInputRow++;
                } else if (i != 42) {
                    _reportInvalidChar(unsignedByte);
                } else {
                    unsignedByte = this._inputData.readUnsignedByte();
                    if (unsignedByte == 47) {
                        return;
                    }
                }
            }
            unsignedByte = this._inputData.readUnsignedByte();
        }
    }

    private final int _skipColon() throws IOException {
        int unsignedByte = this._nextByte;
        if (unsignedByte < 0) {
            unsignedByte = this._inputData.readUnsignedByte();
        } else {
            this._nextByte = -1;
        }
        if (unsignedByte == 58) {
            int unsignedByte2 = this._inputData.readUnsignedByte();
            if (unsignedByte2 > 32) {
                return (unsignedByte2 == 47 || unsignedByte2 == 35) ? _skipColon2(unsignedByte2, true) : unsignedByte2;
            }
            if ((unsignedByte2 == 32 || unsignedByte2 == 9) && (unsignedByte2 = this._inputData.readUnsignedByte()) > 32) {
                return (unsignedByte2 == 47 || unsignedByte2 == 35) ? _skipColon2(unsignedByte2, true) : unsignedByte2;
            }
            return _skipColon2(unsignedByte2, true);
        }
        if (unsignedByte == 32 || unsignedByte == 9) {
            unsignedByte = this._inputData.readUnsignedByte();
        }
        if (unsignedByte != 58) {
            return _skipColon2(unsignedByte, false);
        }
        int unsignedByte3 = this._inputData.readUnsignedByte();
        if (unsignedByte3 > 32) {
            return (unsignedByte3 == 47 || unsignedByte3 == 35) ? _skipColon2(unsignedByte3, true) : unsignedByte3;
        }
        if ((unsignedByte3 == 32 || unsignedByte3 == 9) && (unsignedByte3 = this._inputData.readUnsignedByte()) > 32) {
            return (unsignedByte3 == 47 || unsignedByte3 == 35) ? _skipColon2(unsignedByte3, true) : unsignedByte3;
        }
        return _skipColon2(unsignedByte3, true);
    }

    private final int _skipColon2(int i, boolean z) throws IOException {
        while (true) {
            if (i > 32) {
                if (i == 47) {
                    _skipComment();
                } else if (i != 35 || !_skipYAMLComment()) {
                    if (z) {
                        return i;
                    }
                    if (i != 58) {
                        _reportUnexpectedChar(i, "was expecting a colon to separate field name and value");
                    }
                    z = true;
                }
            } else if (i == 13 || i == 10) {
                this._currInputRow++;
            }
            i = this._inputData.readUnsignedByte();
        }
    }

    private final void _skipComment() throws IOException {
        if (!isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
            _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        int unsignedByte = this._inputData.readUnsignedByte();
        if (unsignedByte == 47) {
            _skipLine();
        } else if (unsignedByte == 42) {
            _skipCComment();
        } else {
            _reportUnexpectedChar(unsignedByte, "was expecting either '*' or '/' for a comment");
        }
    }

    private final void _skipLine() throws IOException {
        int[] inputCodeComment = CharTypes.getInputCodeComment();
        while (true) {
            int unsignedByte = this._inputData.readUnsignedByte();
            int i = inputCodeComment[unsignedByte];
            if (i != 0) {
                if (i == 2) {
                    _skipUtf8_2();
                } else if (i == 3) {
                    _skipUtf8_3();
                } else if (i == 4) {
                    _skipUtf8_4();
                } else if (i == 10 || i == 13) {
                    break;
                } else if (i != 42 && i < 0) {
                    _reportInvalidChar(unsignedByte);
                }
            }
        }
        this._currInputRow++;
    }

    private final void _skipUtf8_2() throws IOException {
        int unsignedByte = this._inputData.readUnsignedByte();
        if ((unsignedByte & 192) != 128) {
            _reportInvalidOther(unsignedByte & 255);
        }
    }

    private final void _skipUtf8_3() throws IOException {
        int unsignedByte = this._inputData.readUnsignedByte();
        if ((unsignedByte & 192) != 128) {
            _reportInvalidOther(unsignedByte & 255);
        }
        int unsignedByte2 = this._inputData.readUnsignedByte();
        if ((unsignedByte2 & 192) != 128) {
            _reportInvalidOther(unsignedByte2 & 255);
        }
    }

    private final void _skipUtf8_4() throws IOException {
        int unsignedByte = this._inputData.readUnsignedByte();
        if ((unsignedByte & 192) != 128) {
            _reportInvalidOther(unsignedByte & 255);
        }
        int unsignedByte2 = this._inputData.readUnsignedByte();
        if ((unsignedByte2 & 192) != 128) {
            _reportInvalidOther(unsignedByte2 & 255);
        }
        int unsignedByte3 = this._inputData.readUnsignedByte();
        if ((unsignedByte3 & 192) != 128) {
            _reportInvalidOther(unsignedByte3 & 255);
        }
    }

    private final int _skipWS() throws IOException {
        int unsignedByte = this._nextByte;
        if (unsignedByte < 0) {
            unsignedByte = this._inputData.readUnsignedByte();
        } else {
            this._nextByte = -1;
        }
        while (unsignedByte <= 32) {
            if (unsignedByte == 13 || unsignedByte == 10) {
                this._currInputRow++;
            }
            unsignedByte = this._inputData.readUnsignedByte();
        }
        return (unsignedByte == 47 || unsignedByte == 35) ? _skipWSComment(unsignedByte) : unsignedByte;
    }

    private final int _skipWSComment(int i) throws IOException {
        while (true) {
            if (i > 32) {
                if (i == 47) {
                    _skipComment();
                } else if (i != 35 || !_skipYAMLComment()) {
                    break;
                }
            } else if (i == 13 || i == 10) {
                this._currInputRow++;
            }
            i = this._inputData.readUnsignedByte();
        }
        return i;
    }

    private final int _skipWSOrEnd() throws IOException {
        int unsignedByte = this._nextByte;
        if (unsignedByte < 0) {
            try {
                unsignedByte = this._inputData.readUnsignedByte();
            } catch (EOFException unused) {
                return _eofAsNextChar();
            }
        } else {
            this._nextByte = -1;
        }
        while (unsignedByte <= 32) {
            if (unsignedByte == 13 || unsignedByte == 10) {
                this._currInputRow++;
            }
            try {
                unsignedByte = this._inputData.readUnsignedByte();
            } catch (EOFException unused2) {
                return _eofAsNextChar();
            }
        }
        return (unsignedByte == 47 || unsignedByte == 35) ? _skipWSComment(unsignedByte) : unsignedByte;
    }

    private final boolean _skipYAMLComment() throws IOException {
        if (!isEnabled(JsonParser.Feature.ALLOW_YAML_COMMENTS)) {
            return false;
        }
        _skipLine();
        return true;
    }

    private final void _verifyRootSpace() throws IOException {
        int i = this._nextByte;
        if (i > 32) {
            _reportMissingRootWS(i);
            return;
        }
        this._nextByte = -1;
        if (i == 13 || i == 10) {
            this._currInputRow++;
        }
    }

    /* JADX WARN: Code duplicated, block: B:40:0x00c2  */
    /* JADX WARN: Code duplicated, block: B:42:0x00c8  */
    /* JADX WARN: Code duplicated, block: B:44:0x00e3  */
    private final String addName(int[] iArr, int i, int i2) throws JsonParseException {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8 = ((i << 2) - 4) + i2;
        if (i2 < 4) {
            int i9 = i - 1;
            i3 = iArr[i9];
            iArr[i9] = i3 << ((4 - i2) << 3);
        } else {
            i3 = 0;
        }
        char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int i10 = 0;
        int i11 = 0;
        while (i10 < i8) {
            int i12 = (iArr[i10 >> 2] >> ((3 - (i10 & 3)) << 3)) & 255;
            i10++;
            if (i12 > 127) {
                if ((i12 & 224) == 192) {
                    i5 = i12 & 31;
                    i4 = 1;
                } else if ((i12 & 240) == 224) {
                    i5 = i12 & 15;
                    i4 = 2;
                } else if ((i12 & 248) == 240) {
                    i5 = i12 & 7;
                    i4 = 3;
                } else {
                    _reportInvalidInitial(i12);
                    i4 = 1;
                    i5 = 1;
                }
                if (i10 + i4 > i8) {
                    _reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
                }
                int i13 = iArr[i10 >> 2] >> ((3 - (i10 & 3)) << 3);
                i10++;
                if ((i13 & 192) != 128) {
                    _reportInvalidOther(i13);
                }
                int i14 = (i13 & 63) | (i5 << 6);
                if (i4 > 1) {
                    int i15 = iArr[i10 >> 2] >> ((3 - (i10 & 3)) << 3);
                    i10++;
                    if ((i15 & 192) != 128) {
                        _reportInvalidOther(i15);
                    }
                    i6 = (i15 & 63) | (i14 << 6);
                    i7 = 2;
                    if (i4 > 2) {
                        int i16 = iArr[i10 >> 2] >> ((3 - (i10 & 3)) << 3);
                        i10++;
                        if ((i16 & 192) != 128) {
                            _reportInvalidOther(i16 & 255);
                        }
                        i6 = (i6 << 6) | (i16 & 63);
                    } else if (i4 > i7) {
                        int i17 = i6 - 65536;
                        if (i11 >= cArrEmptyAndGetCurrentSegment.length) {
                            cArrEmptyAndGetCurrentSegment = this._textBuffer.expandCurrentSegment();
                        }
                        cArrEmptyAndGetCurrentSegment[i11] = (char) ((i17 >> 10) + GeneratorBase.SURR1_FIRST);
                        i12 = (i17 & 1023) | GeneratorBase.SURR2_FIRST;
                        i11++;
                    } else {
                        i12 = i6;
                    }
                } else {
                    i6 = i14;
                }
                i7 = 2;
                if (i4 > i7) {
                    int i18 = i6 - 65536;
                    if (i11 >= cArrEmptyAndGetCurrentSegment.length) {
                        cArrEmptyAndGetCurrentSegment = this._textBuffer.expandCurrentSegment();
                    }
                    cArrEmptyAndGetCurrentSegment[i11] = (char) ((i18 >> 10) + GeneratorBase.SURR1_FIRST);
                    i12 = (i18 & 1023) | GeneratorBase.SURR2_FIRST;
                    i11++;
                } else {
                    i12 = i6;
                }
            }
            if (i11 >= cArrEmptyAndGetCurrentSegment.length) {
                cArrEmptyAndGetCurrentSegment = this._textBuffer.expandCurrentSegment();
            }
            cArrEmptyAndGetCurrentSegment[i11] = (char) i12;
            i11++;
        }
        String str = new String(cArrEmptyAndGetCurrentSegment, 0, i11);
        if (i2 < 4) {
            iArr[i - 1] = i3;
        }
        return this._symbols.addName(str, iArr, i);
    }

    private final String findName(int i, int i2) throws JsonParseException {
        int iPad = pad(i, i2);
        String strFindName = this._symbols.findName(iPad);
        if (strFindName != null) {
            return strFindName;
        }
        int[] iArr = this._quadBuffer;
        iArr[0] = iPad;
        return addName(iArr, 1, i2);
    }

    private final String findName(int i, int i2, int i3) throws JsonParseException {
        int iPad = pad(i2, i3);
        String strFindName = this._symbols.findName(i, iPad);
        if (strFindName != null) {
            return strFindName;
        }
        int[] iArr = this._quadBuffer;
        iArr[0] = i;
        iArr[1] = iPad;
        return addName(iArr, 2, i3);
    }

    private final String findName(int i, int i2, int i3, int i4) throws JsonParseException {
        int iPad = pad(i3, i4);
        String strFindName = this._symbols.findName(i, i2, iPad);
        if (strFindName != null) {
            return strFindName;
        }
        int[] iArr = this._quadBuffer;
        iArr[0] = i;
        iArr[1] = i2;
        iArr[2] = pad(iPad, i4);
        return addName(iArr, 3, i4);
    }

    private final String findName(int[] iArr, int i, int i2, int i3) throws JsonParseException {
        if (i >= iArr.length) {
            iArr = _growArrayBy(iArr, iArr.length);
            this._quadBuffer = iArr;
        }
        int i4 = i + 1;
        iArr[i] = pad(i2, i3);
        String strFindName = this._symbols.findName(iArr, i4);
        return strFindName == null ? addName(iArr, i4, i3) : strFindName;
    }

    private static final int pad(int i, int i2) {
        return i2 == 4 ? i : i | ((-1) << (i2 << 3));
    }

    private final String parseName(int i, int i2, int i3) throws IOException {
        return parseEscapedName(this._quadBuffer, 0, i, i2, i3);
    }

    private final String parseName(int i, int i2, int i3, int i4) throws IOException {
        int[] iArr = this._quadBuffer;
        iArr[0] = i;
        return parseEscapedName(iArr, 1, i2, i3, i4);
    }

    private final String parseName(int i, int i2, int i3, int i4, int i5) throws IOException {
        int[] iArr = this._quadBuffer;
        iArr[0] = i;
        iArr[1] = i2;
        return parseEscapedName(iArr, 2, i3, i4, i5);
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase
    protected void _closeInput() throws IOException {
    }

    protected final byte[] _decodeBase64(Base64Variant base64Variant) throws IOException {
        ByteArrayBuilder byteArrayBuilder_getByteArrayBuilder = _getByteArrayBuilder();
        while (true) {
            int unsignedByte = this._inputData.readUnsignedByte();
            if (unsignedByte > 32) {
                int iDecodeBase64Char = base64Variant.decodeBase64Char(unsignedByte);
                if (iDecodeBase64Char < 0) {
                    if (unsignedByte == 34) {
                        return byteArrayBuilder_getByteArrayBuilder.toByteArray();
                    }
                    iDecodeBase64Char = _decodeBase64Escape(base64Variant, unsignedByte, 0);
                    if (iDecodeBase64Char < 0) {
                        continue;
                    }
                }
                int unsignedByte2 = this._inputData.readUnsignedByte();
                int iDecodeBase64Char2 = base64Variant.decodeBase64Char(unsignedByte2);
                if (iDecodeBase64Char2 < 0) {
                    iDecodeBase64Char2 = _decodeBase64Escape(base64Variant, unsignedByte2, 1);
                }
                int i = (iDecodeBase64Char << 6) | iDecodeBase64Char2;
                int unsignedByte3 = this._inputData.readUnsignedByte();
                int iDecodeBase64Char3 = base64Variant.decodeBase64Char(unsignedByte3);
                if (iDecodeBase64Char3 < 0) {
                    if (iDecodeBase64Char3 != -2) {
                        if (unsignedByte3 == 34 && !base64Variant.usesPadding()) {
                            byteArrayBuilder_getByteArrayBuilder.append(i >> 4);
                            return byteArrayBuilder_getByteArrayBuilder.toByteArray();
                        }
                        iDecodeBase64Char3 = _decodeBase64Escape(base64Variant, unsignedByte3, 2);
                    }
                    if (iDecodeBase64Char3 == -2) {
                        int unsignedByte4 = this._inputData.readUnsignedByte();
                        if (!base64Variant.usesPaddingChar(unsignedByte4)) {
                            throw reportInvalidBase64Char(base64Variant, unsignedByte4, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                        }
                        byteArrayBuilder_getByteArrayBuilder.append(i >> 4);
                    }
                }
                int i2 = (i << 6) | iDecodeBase64Char3;
                int unsignedByte5 = this._inputData.readUnsignedByte();
                int iDecodeBase64Char4 = base64Variant.decodeBase64Char(unsignedByte5);
                if (iDecodeBase64Char4 < 0) {
                    if (iDecodeBase64Char4 != -2) {
                        if (unsignedByte5 == 34 && !base64Variant.usesPadding()) {
                            byteArrayBuilder_getByteArrayBuilder.appendTwoBytes(i2 >> 2);
                            return byteArrayBuilder_getByteArrayBuilder.toByteArray();
                        }
                        iDecodeBase64Char4 = _decodeBase64Escape(base64Variant, unsignedByte5, 3);
                    }
                    if (iDecodeBase64Char4 == -2) {
                        byteArrayBuilder_getByteArrayBuilder.appendTwoBytes(i2 >> 2);
                    }
                }
                byteArrayBuilder_getByteArrayBuilder.appendThreeBytes((i2 << 6) | iDecodeBase64Char4);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0038  */
    /* JADX WARN: Code duplicated, block: B:20:0x0044  */
    /* JADX WARN: Code duplicated, block: B:22:0x004e  */
    /* JADX WARN: Code duplicated, block: B:25:0x005a  */
    /* JADX WARN: Code duplicated, block: B:27:0x0064  */
    /* JADX WARN: Code duplicated, block: B:31:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:32:? A[RETURN, SYNTHETIC] */
    protected int _decodeCharForError(int i) throws IOException {
        char c;
        int unsignedByte;
        int i2;
        int unsignedByte2;
        int i3;
        int unsignedByte3;
        int i4 = i & 255;
        if (i4 <= 127) {
            return i4;
        }
        if ((i4 & 224) != 192) {
            if ((i4 & 240) == 224) {
                i4 &= 15;
                c = 2;
            } else if ((i4 & 248) == 240) {
                i4 &= 7;
                c = 3;
            } else {
                _reportInvalidInitial(i4 & 255);
            }
            unsignedByte = this._inputData.readUnsignedByte();
            if ((unsignedByte & 192) != 128) {
                _reportInvalidOther(unsignedByte & 255);
            }
            i2 = (i4 << 6) | (unsignedByte & 63);
            if (c > 1) {
                return i2;
            }
            unsignedByte2 = this._inputData.readUnsignedByte();
            if ((unsignedByte2 & 192) != 128) {
                _reportInvalidOther(unsignedByte2 & 255);
            }
            i3 = (i2 << 6) | (unsignedByte2 & 63);
            if (c > 2) {
                return i3;
            }
            unsignedByte3 = this._inputData.readUnsignedByte();
            if ((unsignedByte3 & 192) != 128) {
                _reportInvalidOther(unsignedByte3 & 255);
            }
            return (i3 << 6) | (unsignedByte3 & 63);
        }
        i4 &= 31;
        c = 1;
        unsignedByte = this._inputData.readUnsignedByte();
        if ((unsignedByte & 192) != 128) {
            _reportInvalidOther(unsignedByte & 255);
        }
        i2 = (i4 << 6) | (unsignedByte & 63);
        if (c > 1) {
            return i2;
        }
        unsignedByte2 = this._inputData.readUnsignedByte();
        if ((unsignedByte2 & 192) != 128) {
            _reportInvalidOther(unsignedByte2 & 255);
        }
        i3 = (i2 << 6) | (unsignedByte2 & 63);
        if (c > 2) {
            return i3;
        }
        unsignedByte3 = this._inputData.readUnsignedByte();
        if ((unsignedByte3 & 192) != 128) {
            _reportInvalidOther(unsignedByte3 & 255);
        }
        return (i3 << 6) | (unsignedByte3 & 63);
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase
    protected char _decodeEscaped() throws IOException {
        int unsignedByte = this._inputData.readUnsignedByte();
        if (unsignedByte == 34 || unsignedByte == 47 || unsignedByte == 92) {
            return (char) unsignedByte;
        }
        if (unsignedByte == 98) {
            return '\b';
        }
        if (unsignedByte == 102) {
            return '\f';
        }
        if (unsignedByte == 110) {
            return '\n';
        }
        if (unsignedByte == 114) {
            return '\r';
        }
        if (unsignedByte == 116) {
            return '\t';
        }
        if (unsignedByte != 117) {
            return _handleUnrecognizedCharacterEscape((char) _decodeCharForError(unsignedByte));
        }
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            int unsignedByte2 = this._inputData.readUnsignedByte();
            int iCharToHex = CharTypes.charToHex(unsignedByte2);
            if (iCharToHex < 0) {
                _reportUnexpectedChar(unsignedByte2, "expected a hex-digit for character escape sequence");
            }
            i = (i << 4) | iCharToHex;
        }
        return (char) i;
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase
    protected void _finishString() throws IOException {
        char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int[] iArr = _icUTF8;
        int length = cArrEmptyAndGetCurrentSegment.length;
        int i = 0;
        while (true) {
            int unsignedByte = this._inputData.readUnsignedByte();
            if (iArr[unsignedByte] != 0) {
                if (unsignedByte == 34) {
                    this._textBuffer.setCurrentLength(i);
                    return;
                } else {
                    _finishString2(cArrEmptyAndGetCurrentSegment, i, unsignedByte);
                    return;
                }
            }
            int i2 = i + 1;
            cArrEmptyAndGetCurrentSegment[i] = (char) unsignedByte;
            if (i2 >= length) {
                _finishString2(cArrEmptyAndGetCurrentSegment, i2, this._inputData.readUnsignedByte());
                return;
            }
            i = i2;
        }
    }

    protected final String _getText2(JsonToken jsonToken) {
        if (jsonToken == null) {
            return null;
        }
        int iM416id = jsonToken.m416id();
        if (iM416id != 5) {
            return (iM416id == 6 || iM416id == 7 || iM416id == 8) ? this._textBuffer.contentsAsString() : jsonToken.asString();
        }
        return this._parsingContext.getCurrentName();
    }

    protected JsonToken _handleApos() throws IOException {
        char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int[] iArr = _icUTF8;
        int i = 0;
        while (true) {
            int length = cArrEmptyAndGetCurrentSegment.length;
            if (i >= cArrEmptyAndGetCurrentSegment.length) {
                cArrEmptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                length = cArrEmptyAndGetCurrentSegment.length;
                i = 0;
            }
            while (true) {
                int unsignedByte = this._inputData.readUnsignedByte();
                if (unsignedByte == 39) {
                    this._textBuffer.setCurrentLength(i);
                    return JsonToken.VALUE_STRING;
                }
                if (iArr[unsignedByte] != 0) {
                    int i2 = iArr[unsignedByte];
                    if (i2 == 1) {
                        unsignedByte = _decodeEscaped();
                    } else if (i2 == 2) {
                        unsignedByte = _decodeUtf8_2(unsignedByte);
                    } else if (i2 == 3) {
                        unsignedByte = _decodeUtf8_3(unsignedByte);
                    } else if (i2 != 4) {
                        if (unsignedByte < 32) {
                            _throwUnquotedSpace(unsignedByte, "string value");
                        }
                        _reportInvalidChar(unsignedByte);
                    } else {
                        int i_decodeUtf8_4 = _decodeUtf8_4(unsignedByte);
                        int i3 = i + 1;
                        cArrEmptyAndGetCurrentSegment[i] = (char) (55296 | (i_decodeUtf8_4 >> 10));
                        if (i3 >= cArrEmptyAndGetCurrentSegment.length) {
                            cArrEmptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                            i3 = 0;
                        }
                        int i4 = i3;
                        unsignedByte = 56320 | (i_decodeUtf8_4 & 1023);
                        i = i4;
                    }
                    if (i >= cArrEmptyAndGetCurrentSegment.length) {
                        cArrEmptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                        i = 0;
                    }
                    cArrEmptyAndGetCurrentSegment[i] = (char) unsignedByte;
                    i++;
                    break;
                }
                int i5 = i + 1;
                cArrEmptyAndGetCurrentSegment[i] = (char) unsignedByte;
                if (i5 >= length) {
                    i = i5;
                    break;
                }
                i = i5;
            }
        }
    }

    protected JsonToken _handleInvalidNumberStart(int i, boolean z) throws IOException {
        String str;
        while (i == 73) {
            i = this._inputData.readUnsignedByte();
            if (i != 78) {
                if (i != 110) {
                    break;
                }
                str = z ? "-Infinity" : "+Infinity";
            } else {
                str = z ? "-INF" : "+INF";
            }
            _matchToken(str, 3);
            if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                return resetAsNaN(str, z ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
            }
            _reportError("Non-standard token '" + str + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
        }
        reportUnexpectedNumberChar(i, "expected digit (0-9) to follow minus sign, for valid numeric value");
        return null;
    }

    protected String _handleOddName(int i) throws IOException {
        if (i == 39 && isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
            return _parseAposName();
        }
        if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            _reportUnexpectedChar((char) _decodeCharForError(i), "was expecting double-quote to start field name");
        }
        int[] inputCodeUtf8JsNames = CharTypes.getInputCodeUtf8JsNames();
        if (inputCodeUtf8JsNames[i] != 0) {
            _reportUnexpectedChar(i, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        int[] iArr_growArrayBy = this._quadBuffer;
        int i2 = 0;
        int unsignedByte = i;
        int i3 = 0;
        int i4 = 0;
        do {
            if (i2 < 4) {
                i2++;
                i4 = (i4 << 8) | unsignedByte;
            } else {
                if (i3 >= iArr_growArrayBy.length) {
                    iArr_growArrayBy = _growArrayBy(iArr_growArrayBy, iArr_growArrayBy.length);
                    this._quadBuffer = iArr_growArrayBy;
                }
                iArr_growArrayBy[i3] = i4;
                i3++;
                i4 = unsignedByte;
                i2 = 1;
            }
            unsignedByte = this._inputData.readUnsignedByte();
        } while (inputCodeUtf8JsNames[unsignedByte] == 0);
        this._nextByte = unsignedByte;
        if (i2 > 0) {
            if (i3 >= iArr_growArrayBy.length) {
                int[] iArr_growArrayBy2 = _growArrayBy(iArr_growArrayBy, iArr_growArrayBy.length);
                this._quadBuffer = iArr_growArrayBy2;
                iArr_growArrayBy = iArr_growArrayBy2;
            }
            iArr_growArrayBy[i3] = i4;
            i3++;
        }
        String strFindName = this._symbols.findName(iArr_growArrayBy, i3);
        return strFindName == null ? addName(iArr_growArrayBy, i3, i2) : strFindName;
    }

    /* JADX WARN: Code duplicated, block: B:39:0x0082  */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x001b, code lost:
    
        if (r4 != 44) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0030, code lost:
    
        if (r3._parsingContext.inArray() == false) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0039, code lost:
    
        if (isEnabled(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_MISSING_VALUES) == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x003b, code lost:
    
        r3._nextByte = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x003f, code lost:
    
        return com.fasterxml.jackson.core.JsonToken.VALUE_NULL;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected com.fasterxml.jackson.core.JsonToken _handleUnexpectedValue(int r4) throws java.io.IOException {
        /*
            r3 = this;
            r0 = 39
            if (r4 == r0) goto L7a
            r0 = 73
            r1 = 1
            if (r4 == r0) goto L60
            r0 = 78
            if (r4 == r0) goto L46
            r0 = 93
            if (r4 == r0) goto L2a
            r0 = 125(0x7d, float:1.75E-43)
            if (r4 == r0) goto L40
            r0 = 43
            if (r4 == r0) goto L1e
            r0 = 44
            if (r4 == r0) goto L33
            goto L87
        L1e:
            java.io.DataInput r4 = r3._inputData
            int r4 = r4.readUnsignedByte()
            r0 = 0
            com.fasterxml.jackson.core.JsonToken r4 = r3._handleInvalidNumberStart(r4, r0)
            return r4
        L2a:
            com.fasterxml.jackson.core.json.JsonReadContext r0 = r3._parsingContext
            boolean r0 = r0.inArray()
            if (r0 != 0) goto L33
            goto L87
        L33:
            com.fasterxml.jackson.core.JsonParser$Feature r0 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_MISSING_VALUES
            boolean r0 = r3.isEnabled(r0)
            if (r0 == 0) goto L40
            r3._nextByte = r4
            com.fasterxml.jackson.core.JsonToken r4 = com.fasterxml.jackson.core.JsonToken.VALUE_NULL
            return r4
        L40:
            java.lang.String r0 = "expected a value"
            r3._reportUnexpectedChar(r4, r0)
            goto L7a
        L46:
            java.lang.String r0 = "NaN"
            r3._matchToken(r0, r1)
            com.fasterxml.jackson.core.JsonParser$Feature r1 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS
            boolean r1 = r3.isEnabled(r1)
            if (r1 == 0) goto L5a
            r1 = 9221120237041090560(0x7ff8000000000000, double:NaN)
            com.fasterxml.jackson.core.JsonToken r4 = r3.resetAsNaN(r0, r1)
            return r4
        L5a:
            java.lang.String r0 = "Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow"
            r3._reportError(r0)
            goto L87
        L60:
            java.lang.String r0 = "Infinity"
            r3._matchToken(r0, r1)
            com.fasterxml.jackson.core.JsonParser$Feature r1 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS
            boolean r1 = r3.isEnabled(r1)
            if (r1 == 0) goto L74
            r1 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
            com.fasterxml.jackson.core.JsonToken r4 = r3.resetAsNaN(r0, r1)
            return r4
        L74:
            java.lang.String r0 = "Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow"
            r3._reportError(r0)
            goto L87
        L7a:
            com.fasterxml.jackson.core.JsonParser$Feature r0 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES
            boolean r0 = r3.isEnabled(r0)
            if (r0 == 0) goto L87
            com.fasterxml.jackson.core.JsonToken r4 = r3._handleApos()
            return r4
        L87:
            boolean r0 = java.lang.Character.isJavaIdentifierStart(r4)
            if (r0 == 0) goto La4
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = ""
            r0.append(r1)
            char r1 = (char) r4
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "('true', 'false' or 'null')"
            r3._reportInvalidToken(r4, r0, r1)
        La4:
            java.lang.String r0 = "expected a valid value (number, String, array, object, 'true', 'false' or 'null')"
            r3._reportUnexpectedChar(r4, r0)
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.UTF8DataInputJsonParser._handleUnexpectedValue(int):com.fasterxml.jackson.core.JsonToken");
    }

    protected final void _matchToken(String str, int i) throws IOException {
        int length = str.length();
        do {
            int unsignedByte = this._inputData.readUnsignedByte();
            if (unsignedByte != str.charAt(i)) {
                _reportInvalidToken(unsignedByte, str.substring(0, i));
            }
            i++;
        } while (i < length);
        int unsignedByte2 = this._inputData.readUnsignedByte();
        if (unsignedByte2 >= 48 && unsignedByte2 != 93 && unsignedByte2 != 125) {
            _checkMatchEnd(str, i, unsignedByte2);
        }
        this._nextByte = unsignedByte2;
    }

    protected String _parseAposName() throws IOException {
        int i;
        int unsignedByte = this._inputData.readUnsignedByte();
        if (unsignedByte == 39) {
            return "";
        }
        int[] iArr = this._quadBuffer;
        int[] iArr2 = _icLatin1;
        int[] iArr_growArrayBy = iArr;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (unsignedByte != 39) {
            if (unsignedByte != 34 && iArr2[unsignedByte] != 0) {
                if (unsignedByte != 92) {
                    _throwUnquotedSpace(unsignedByte, AppMeasurementSdk.ConditionalUserProperty.NAME);
                } else {
                    unsignedByte = _decodeEscaped();
                }
                if (unsignedByte > 127) {
                    if (i2 >= 4) {
                        if (i3 >= iArr_growArrayBy.length) {
                            iArr_growArrayBy = _growArrayBy(iArr_growArrayBy, iArr_growArrayBy.length);
                            this._quadBuffer = iArr_growArrayBy;
                        }
                        iArr_growArrayBy[i3] = i4;
                        i3++;
                        i2 = 0;
                        i4 = 0;
                    }
                    if (unsignedByte < 2048) {
                        i4 = (i4 << 8) | (unsignedByte >> 6) | 192;
                        i2++;
                    } else {
                        int i5 = (i4 << 8) | (unsignedByte >> 12) | 224;
                        int i6 = i2 + 1;
                        if (i6 >= 4) {
                            if (i3 >= iArr_growArrayBy.length) {
                                int[] iArr_growArrayBy2 = _growArrayBy(iArr_growArrayBy, iArr_growArrayBy.length);
                                this._quadBuffer = iArr_growArrayBy2;
                                iArr_growArrayBy = iArr_growArrayBy2;
                            }
                            iArr_growArrayBy[i3] = i5;
                            i3++;
                            i6 = 0;
                            i5 = 0;
                        }
                        i4 = (i5 << 8) | ((unsignedByte >> 6) & 63) | 128;
                        i2 = i6 + 1;
                    }
                    unsignedByte = (unsignedByte & 63) | 128;
                }
            }
            if (i2 < 4) {
                i2++;
                i4 = unsignedByte | (i4 << 8);
            } else {
                if (i3 >= iArr_growArrayBy.length) {
                    iArr_growArrayBy = _growArrayBy(iArr_growArrayBy, iArr_growArrayBy.length);
                    this._quadBuffer = iArr_growArrayBy;
                }
                iArr_growArrayBy[i3] = i4;
                i4 = unsignedByte;
                i3++;
                i2 = 1;
            }
            unsignedByte = this._inputData.readUnsignedByte();
        }
        if (i2 > 0) {
            if (i3 >= iArr_growArrayBy.length) {
                int[] iArr_growArrayBy3 = _growArrayBy(iArr_growArrayBy, iArr_growArrayBy.length);
                this._quadBuffer = iArr_growArrayBy3;
                iArr_growArrayBy = iArr_growArrayBy3;
            }
            i = i3 + 1;
            iArr_growArrayBy[i3] = pad(i4, i2);
        } else {
            i = i3;
        }
        String strFindName = this._symbols.findName(iArr_growArrayBy, i);
        return strFindName == null ? addName(iArr_growArrayBy, i, i2) : strFindName;
    }

    protected final String _parseName(int i) throws IOException {
        if (i != 34) {
            return _handleOddName(i);
        }
        int[] iArr = _icLatin1;
        int unsignedByte = this._inputData.readUnsignedByte();
        if (iArr[unsignedByte] != 0) {
            return unsignedByte == 34 ? "" : parseName(0, unsignedByte, 0);
        }
        int unsignedByte2 = this._inputData.readUnsignedByte();
        if (iArr[unsignedByte2] != 0) {
            return unsignedByte2 == 34 ? findName(unsignedByte, 1) : parseName(unsignedByte, unsignedByte2, 1);
        }
        int i2 = (unsignedByte << 8) | unsignedByte2;
        int unsignedByte3 = this._inputData.readUnsignedByte();
        if (iArr[unsignedByte3] != 0) {
            return unsignedByte3 == 34 ? findName(i2, 2) : parseName(i2, unsignedByte3, 2);
        }
        int i3 = (i2 << 8) | unsignedByte3;
        int unsignedByte4 = this._inputData.readUnsignedByte();
        if (iArr[unsignedByte4] != 0) {
            return unsignedByte4 == 34 ? findName(i3, 3) : parseName(i3, unsignedByte4, 3);
        }
        int i4 = (i3 << 8) | unsignedByte4;
        int unsignedByte5 = this._inputData.readUnsignedByte();
        if (iArr[unsignedByte5] != 0) {
            return unsignedByte5 == 34 ? findName(i4, 4) : parseName(i4, unsignedByte5, 4);
        }
        this._quad1 = i4;
        return _parseMediumName(unsignedByte5);
    }

    protected JsonToken _parseNegNumber() throws IOException {
        int unsignedByte;
        char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        cArrEmptyAndGetCurrentSegment[0] = '-';
        int unsignedByte2 = this._inputData.readUnsignedByte();
        cArrEmptyAndGetCurrentSegment[1] = (char) unsignedByte2;
        if (unsignedByte2 <= 48) {
            if (unsignedByte2 != 48) {
                return _handleInvalidNumberStart(unsignedByte2, true);
            }
            unsignedByte = _handleLeadingZeroes();
        } else {
            if (unsignedByte2 > 57) {
                return _handleInvalidNumberStart(unsignedByte2, true);
            }
            unsignedByte = this._inputData.readUnsignedByte();
        }
        int i = 2;
        int i2 = 1;
        while (unsignedByte <= 57 && unsignedByte >= 48) {
            i2++;
            cArrEmptyAndGetCurrentSegment[i] = (char) unsignedByte;
            unsignedByte = this._inputData.readUnsignedByte();
            i++;
        }
        if (unsignedByte == 46 || unsignedByte == 101 || unsignedByte == 69) {
            return _parseFloat(cArrEmptyAndGetCurrentSegment, i, unsignedByte, true, i2);
        }
        this._textBuffer.setCurrentLength(i);
        this._nextByte = unsignedByte;
        if (this._parsingContext.inRoot()) {
            _verifyRootSpace();
        }
        return resetInt(true, i2);
    }

    protected JsonToken _parsePosNumber(int i) throws IOException {
        int unsignedByte;
        char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int i2 = 1;
        if (i == 48) {
            unsignedByte = _handleLeadingZeroes();
            if (unsignedByte > 57 || unsignedByte < 48) {
                cArrEmptyAndGetCurrentSegment[0] = '0';
            } else {
                i2 = 0;
            }
        } else {
            cArrEmptyAndGetCurrentSegment[0] = (char) i;
            unsignedByte = this._inputData.readUnsignedByte();
        }
        int i3 = i2;
        int i4 = i3;
        while (unsignedByte <= 57 && unsignedByte >= 48) {
            i4++;
            cArrEmptyAndGetCurrentSegment[i3] = (char) unsignedByte;
            unsignedByte = this._inputData.readUnsignedByte();
            i3++;
        }
        if (unsignedByte == 46 || unsignedByte == 101 || unsignedByte == 69) {
            return _parseFloat(cArrEmptyAndGetCurrentSegment, i3, unsignedByte, false, i4);
        }
        this._textBuffer.setCurrentLength(i3);
        if (this._parsingContext.inRoot()) {
            _verifyRootSpace();
        } else {
            this._nextByte = unsignedByte;
        }
        return resetInt(false, i4);
    }

    /* JADX WARN: Code duplicated, block: B:31:0x009a A[PHI: r8
      0x009a: PHI (r8v3 int) = (r8v2 int), (r8v8 int) binds: [B:18:0x004a, B:25:0x0064] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:33:0x00a9  */
    /* JADX WARN: Code duplicated, block: B:34:0x00ab  */
    /* JADX WARN: Code duplicated, block: B:54:0x00d1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:56:0x00e1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:57:0x00e1 A[SYNTHETIC] */
    protected int _readBinary(Base64Variant base64Variant, OutputStream outputStream, byte[] bArr) throws IOException {
        int i;
        int unsignedByte;
        int iDecodeBase64Char;
        int i2;
        int length = bArr.length - 3;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int unsignedByte2 = this._inputData.readUnsignedByte();
            if (unsignedByte2 > 32) {
                int iDecodeBase64Char2 = base64Variant.decodeBase64Char(unsignedByte2);
                if (iDecodeBase64Char2 < 0) {
                    if (unsignedByte2 == 34) {
                        break;
                    }
                    iDecodeBase64Char2 = _decodeBase64Escape(base64Variant, unsignedByte2, 0);
                    if (iDecodeBase64Char2 < 0) {
                        continue;
                    }
                }
                if (i4 > length) {
                    i3 += i4;
                    outputStream.write(bArr, 0, i4);
                    i4 = 0;
                }
                int unsignedByte3 = this._inputData.readUnsignedByte();
                int iDecodeBase64Char3 = base64Variant.decodeBase64Char(unsignedByte3);
                if (iDecodeBase64Char3 < 0) {
                    iDecodeBase64Char3 = _decodeBase64Escape(base64Variant, unsignedByte3, 1);
                }
                int i5 = (iDecodeBase64Char2 << 6) | iDecodeBase64Char3;
                int unsignedByte4 = this._inputData.readUnsignedByte();
                int iDecodeBase64Char4 = base64Variant.decodeBase64Char(unsignedByte4);
                if (iDecodeBase64Char4 >= 0) {
                    i = (i5 << 6) | iDecodeBase64Char4;
                    unsignedByte = this._inputData.readUnsignedByte();
                    iDecodeBase64Char = base64Variant.decodeBase64Char(unsignedByte);
                    if (iDecodeBase64Char >= 0) {
                        if (iDecodeBase64Char != -2) {
                            if (unsignedByte != 34 && !base64Variant.usesPadding()) {
                                int i6 = i >> 2;
                                int i7 = i4 + 1;
                                bArr[i4] = (byte) (i6 >> 8);
                                i4 = i7 + 1;
                                bArr[i7] = (byte) i6;
                                break;
                            }
                            iDecodeBase64Char = _decodeBase64Escape(base64Variant, unsignedByte, 3);
                        }
                        if (iDecodeBase64Char == -2) {
                            int i8 = i >> 2;
                            int i9 = i4 + 1;
                            bArr[i4] = (byte) (i8 >> 8);
                            i4 = i9 + 1;
                            bArr[i9] = (byte) i8;
                        }
                    }
                    int i10 = (i << 6) | iDecodeBase64Char;
                    int i11 = i4 + 1;
                    bArr[i4] = (byte) (i10 >> 16);
                    int i12 = i11 + 1;
                    bArr[i11] = (byte) (i10 >> 8);
                    i2 = i12 + 1;
                    bArr[i12] = (byte) i10;
                    i4 = i2;
                } else {
                    if (iDecodeBase64Char4 != -2) {
                        if (unsignedByte4 == 34 && !base64Variant.usesPadding()) {
                            bArr[i4] = (byte) (i5 >> 4);
                            i4++;
                            break;
                        }
                        iDecodeBase64Char4 = _decodeBase64Escape(base64Variant, unsignedByte4, 2);
                    }
                    if (iDecodeBase64Char4 == -2) {
                        int unsignedByte5 = this._inputData.readUnsignedByte();
                        if (!base64Variant.usesPaddingChar(unsignedByte5)) {
                            throw reportInvalidBase64Char(base64Variant, unsignedByte5, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                        }
                        i2 = i4 + 1;
                        bArr[i4] = (byte) (i5 >> 4);
                    } else {
                        i = (i5 << 6) | iDecodeBase64Char4;
                        unsignedByte = this._inputData.readUnsignedByte();
                        iDecodeBase64Char = base64Variant.decodeBase64Char(unsignedByte);
                        if (iDecodeBase64Char >= 0) {
                            if (iDecodeBase64Char != -2) {
                                if (unsignedByte != 34) {
                                }
                                iDecodeBase64Char = _decodeBase64Escape(base64Variant, unsignedByte, 3);
                            }
                            if (iDecodeBase64Char == -2) {
                                int i13 = i >> 2;
                                int i14 = i4 + 1;
                                bArr[i4] = (byte) (i13 >> 8);
                                i4 = i14 + 1;
                                bArr[i14] = (byte) i13;
                            }
                        }
                        int i15 = (i << 6) | iDecodeBase64Char;
                        int i16 = i4 + 1;
                        bArr[i4] = (byte) (i15 >> 16);
                        int i17 = i16 + 1;
                        bArr[i16] = (byte) (i15 >> 8);
                        i2 = i17 + 1;
                        bArr[i17] = (byte) i15;
                    }
                    i4 = i2;
                }
            }
        }
        this._tokenIncomplete = false;
        if (i4 <= 0) {
            return i3;
        }
        int i18 = i3 + i4;
        outputStream.write(bArr, 0, i4);
        return i18;
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase
    protected void _releaseBuffers() throws IOException {
        super._releaseBuffers();
        this._symbols.release();
    }

    protected void _reportInvalidChar(int i) throws JsonParseException {
        if (i < 32) {
            _throwInvalidSpace(i);
        }
        _reportInvalidInitial(i);
    }

    protected void _reportInvalidInitial(int i) throws JsonParseException {
        _reportError("Invalid UTF-8 start byte 0x" + Integer.toHexString(i));
    }

    protected void _reportInvalidToken(int i, String str) throws IOException {
        _reportInvalidToken(i, str, "'null', 'true', 'false' or NaN");
    }

    protected void _reportInvalidToken(int i, String str, String str2) throws IOException {
        StringBuilder sb = new StringBuilder(str);
        while (true) {
            char c_decodeCharForError = (char) _decodeCharForError(i);
            if (!Character.isJavaIdentifierPart(c_decodeCharForError)) {
                _reportError("Unrecognized token '" + sb.toString() + "': was expecting " + str2);
                return;
            }
            sb.append(c_decodeCharForError);
            i = this._inputData.readUnsignedByte();
        }
    }

    protected void _skipString() throws IOException {
        this._tokenIncomplete = false;
        int[] iArr = _icUTF8;
        while (true) {
            int unsignedByte = this._inputData.readUnsignedByte();
            if (iArr[unsignedByte] != 0) {
                if (unsignedByte == 34) {
                    return;
                }
                int i = iArr[unsignedByte];
                if (i == 1) {
                    _decodeEscaped();
                } else if (i == 2) {
                    _skipUtf8_2();
                } else if (i == 3) {
                    _skipUtf8_3();
                } else if (i == 4) {
                    _skipUtf8_4();
                } else if (unsignedByte < 32) {
                    _throwUnquotedSpace(unsignedByte, "string value");
                } else {
                    _reportInvalidChar(unsignedByte);
                }
            }
        }
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public void finishToken() throws IOException {
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            _finishString();
        }
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase, com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
        if (this._currToken != JsonToken.VALUE_STRING && (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT || this._binaryValue == null)) {
            _reportError("Current token (" + this._currToken + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
        }
        if (this._tokenIncomplete) {
            try {
                this._binaryValue = _decodeBase64(base64Variant);
                this._tokenIncomplete = false;
            } catch (IllegalArgumentException e) {
                throw _constructError("Failed to decode VALUE_STRING as base64 (" + base64Variant + "): " + e.getMessage());
            }
        } else if (this._binaryValue == null) {
            ByteArrayBuilder byteArrayBuilder_getByteArrayBuilder = _getByteArrayBuilder();
            _decodeBase64(getText(), byteArrayBuilder_getByteArrayBuilder, base64Variant);
            this._binaryValue = byteArrayBuilder_getByteArrayBuilder.toByteArray();
        }
        return this._binaryValue;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase, com.fasterxml.jackson.core.JsonParser
    public JsonLocation getCurrentLocation() {
        return new JsonLocation(_getSourceReference(), -1L, -1L, this._currInputRow, -1);
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public Object getInputSource() {
        return this._inputData;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public int getText(Writer writer) throws IOException {
        JsonToken jsonToken = this._currToken;
        if (jsonToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                _finishString();
            }
            return this._textBuffer.contentsToWriter(writer);
        }
        if (jsonToken == JsonToken.FIELD_NAME) {
            String currentName = this._parsingContext.getCurrentName();
            writer.write(currentName);
            return currentName.length();
        }
        if (jsonToken == null) {
            return 0;
        }
        if (jsonToken.isNumeric()) {
            return this._textBuffer.contentsToWriter(writer);
        }
        char[] cArrAsCharArray = jsonToken.asCharArray();
        writer.write(cArrAsCharArray);
        return cArrAsCharArray.length;
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public String getText() throws IOException {
        if (this._currToken != JsonToken.VALUE_STRING) {
            return _getText2(this._currToken);
        }
        if (!this._tokenIncomplete) {
            return this._textBuffer.contentsAsString();
        }
        this._tokenIncomplete = false;
        return _finishAndReturnString();
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public char[] getTextCharacters() throws IOException {
        if (this._currToken == null) {
            return null;
        }
        int iM416id = this._currToken.m416id();
        if (iM416id != 5) {
            if (iM416id != 6) {
                if (iM416id != 7 && iM416id != 8) {
                    return this._currToken.asCharArray();
                }
            } else if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                _finishString();
            }
            return this._textBuffer.getTextBuffer();
        }
        if (!this._nameCopied) {
            String currentName = this._parsingContext.getCurrentName();
            int length = currentName.length();
            if (this._nameCopyBuffer == null) {
                this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(length);
            } else if (this._nameCopyBuffer.length < length) {
                this._nameCopyBuffer = new char[length];
            }
            currentName.getChars(0, length, this._nameCopyBuffer, 0);
            this._nameCopied = true;
        }
        return this._nameCopyBuffer;
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public int getTextLength() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                _finishString();
            }
            return this._textBuffer.size();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this._parsingContext.getCurrentName().length();
        }
        if (this._currToken != null) {
            return this._currToken.isNumeric() ? this._textBuffer.size() : this._currToken.asCharArray().length;
        }
        return 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0016, code lost:
    
        if (r0 != 8) goto L18;
     */
    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int getTextOffset() throws java.io.IOException {
        /*
            r3 = this;
            com.fasterxml.jackson.core.JsonToken r0 = r3._currToken
            r1 = 0
            if (r0 == 0) goto L29
            com.fasterxml.jackson.core.JsonToken r0 = r3._currToken
            int r0 = r0.m416id()
            r2 = 5
            if (r0 == r2) goto L29
            r2 = 6
            if (r0 == r2) goto L19
            r2 = 7
            if (r0 == r2) goto L22
            r2 = 8
            if (r0 == r2) goto L22
            goto L29
        L19:
            boolean r0 = r3._tokenIncomplete
            if (r0 == 0) goto L22
            r3._tokenIncomplete = r1
            r3._finishString()
        L22:
            com.fasterxml.jackson.core.util.TextBuffer r0 = r3._textBuffer
            int r0 = r0.getTextOffset()
            return r0
        L29:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.UTF8DataInputJsonParser.getTextOffset():int");
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase, com.fasterxml.jackson.core.JsonParser
    public JsonLocation getTokenLocation() {
        return new JsonLocation(_getSourceReference(), -1L, -1L, this._tokenInputRow, -1);
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public int getValueAsInt() throws IOException {
        JsonToken jsonToken = this._currToken;
        if (jsonToken != JsonToken.VALUE_NUMBER_INT && jsonToken != JsonToken.VALUE_NUMBER_FLOAT) {
            return super.getValueAsInt(0);
        }
        if ((this._numTypesValid & 1) == 0) {
            if (this._numTypesValid == 0) {
                return _parseIntValue();
            }
            if ((this._numTypesValid & 1) == 0) {
                convertNumberToInt();
            }
        }
        return this._numberInt;
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public int getValueAsInt(int i) throws IOException {
        JsonToken jsonToken = this._currToken;
        if (jsonToken != JsonToken.VALUE_NUMBER_INT && jsonToken != JsonToken.VALUE_NUMBER_FLOAT) {
            return super.getValueAsInt(i);
        }
        if ((this._numTypesValid & 1) == 0) {
            if (this._numTypesValid == 0) {
                return _parseIntValue();
            }
            if ((this._numTypesValid & 1) == 0) {
                convertNumberToInt();
            }
        }
        return this._numberInt;
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public String getValueAsString() throws IOException {
        if (this._currToken != JsonToken.VALUE_STRING) {
            return this._currToken == JsonToken.FIELD_NAME ? getCurrentName() : super.getValueAsString(null);
        }
        if (!this._tokenIncomplete) {
            return this._textBuffer.contentsAsString();
        }
        this._tokenIncomplete = false;
        return _finishAndReturnString();
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public String getValueAsString(String str) throws IOException {
        if (this._currToken != JsonToken.VALUE_STRING) {
            return this._currToken == JsonToken.FIELD_NAME ? getCurrentName() : super.getValueAsString(str);
        }
        if (!this._tokenIncomplete) {
            return this._textBuffer.contentsAsString();
        }
        this._tokenIncomplete = false;
        return _finishAndReturnString();
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public Boolean nextBooleanValue() throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            JsonToken jsonTokenNextToken = nextToken();
            if (jsonTokenNextToken == JsonToken.VALUE_TRUE) {
                return Boolean.TRUE;
            }
            if (jsonTokenNextToken == JsonToken.VALUE_FALSE) {
                return Boolean.FALSE;
            }
            return null;
        }
        this._nameCopied = false;
        JsonToken jsonToken = this._nextToken;
        this._nextToken = null;
        this._currToken = jsonToken;
        if (jsonToken == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
        }
        if (jsonToken == JsonToken.VALUE_FALSE) {
            return Boolean.FALSE;
        }
        if (jsonToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        } else if (jsonToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return null;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public String nextFieldName() throws IOException {
        JsonToken jsonToken_parseNegNumber;
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            _nextAfterName();
            return null;
        }
        if (this._tokenIncomplete) {
            _skipString();
        }
        int i_skipWS = _skipWS();
        this._binaryValue = null;
        this._tokenInputRow = this._currInputRow;
        if (i_skipWS == 93 || i_skipWS == 125) {
            _closeScope(i_skipWS);
            return null;
        }
        if (this._parsingContext.expectComma()) {
            if (i_skipWS != 44) {
                _reportUnexpectedChar(i_skipWS, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
            }
            i_skipWS = _skipWS();
            if (JsonParser.Feature.ALLOW_TRAILING_COMMA.enabledIn(this._features) && (i_skipWS == 93 || i_skipWS == 125)) {
                _closeScope(i_skipWS);
                return null;
            }
        }
        if (!this._parsingContext.inObject()) {
            _nextTokenNotInObject(i_skipWS);
            return null;
        }
        String str_parseName = _parseName(i_skipWS);
        this._parsingContext.setCurrentName(str_parseName);
        this._currToken = JsonToken.FIELD_NAME;
        int i_skipColon = _skipColon();
        if (i_skipColon == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return str_parseName;
        }
        if (i_skipColon == 45) {
            jsonToken_parseNegNumber = _parseNegNumber();
        } else if (i_skipColon == 91) {
            jsonToken_parseNegNumber = JsonToken.START_ARRAY;
        } else if (i_skipColon == 102) {
            _matchToken("false", 1);
            jsonToken_parseNegNumber = JsonToken.VALUE_FALSE;
        } else if (i_skipColon == 110) {
            _matchToken("null", 1);
            jsonToken_parseNegNumber = JsonToken.VALUE_NULL;
        } else if (i_skipColon == 116) {
            _matchToken("true", 1);
            jsonToken_parseNegNumber = JsonToken.VALUE_TRUE;
        } else if (i_skipColon != 123) {
            switch (i_skipColon) {
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                    jsonToken_parseNegNumber = _parsePosNumber(i_skipColon);
                    break;
                default:
                    jsonToken_parseNegNumber = _handleUnexpectedValue(i_skipColon);
                    break;
            }
        } else {
            jsonToken_parseNegNumber = JsonToken.START_OBJECT;
        }
        this._nextToken = jsonToken_parseNegNumber;
        return str_parseName;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public int nextIntValue(int i) throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return nextToken() == JsonToken.VALUE_NUMBER_INT ? getIntValue() : i;
        }
        this._nameCopied = false;
        JsonToken jsonToken = this._nextToken;
        this._nextToken = null;
        this._currToken = jsonToken;
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
            return getIntValue();
        }
        if (jsonToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        } else if (jsonToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return i;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public long nextLongValue(long j) throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return nextToken() == JsonToken.VALUE_NUMBER_INT ? getLongValue() : j;
        }
        this._nameCopied = false;
        JsonToken jsonToken = this._nextToken;
        this._nextToken = null;
        this._currToken = jsonToken;
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
            return getLongValue();
        }
        if (jsonToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        } else if (jsonToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return j;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public String nextTextValue() throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            if (nextToken() == JsonToken.VALUE_STRING) {
                return getText();
            }
            return null;
        }
        this._nameCopied = false;
        JsonToken jsonToken = this._nextToken;
        this._nextToken = null;
        this._currToken = jsonToken;
        if (jsonToken == JsonToken.VALUE_STRING) {
            if (!this._tokenIncomplete) {
                return this._textBuffer.contentsAsString();
            }
            this._tokenIncomplete = false;
            return _finishAndReturnString();
        }
        if (jsonToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        } else if (jsonToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return null;
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public JsonToken nextToken() throws IOException {
        JsonToken jsonToken_parseNegNumber;
        if (this._closed) {
            return null;
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return _nextAfterName();
        }
        this._numTypesValid = 0;
        if (this._tokenIncomplete) {
            _skipString();
        }
        int i_skipWSOrEnd = _skipWSOrEnd();
        if (i_skipWSOrEnd < 0) {
            close();
            this._currToken = null;
            return null;
        }
        this._binaryValue = null;
        this._tokenInputRow = this._currInputRow;
        if (i_skipWSOrEnd == 93 || i_skipWSOrEnd == 125) {
            _closeScope(i_skipWSOrEnd);
            return this._currToken;
        }
        if (this._parsingContext.expectComma()) {
            if (i_skipWSOrEnd != 44) {
                _reportUnexpectedChar(i_skipWSOrEnd, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
            }
            i_skipWSOrEnd = _skipWS();
            if (JsonParser.Feature.ALLOW_TRAILING_COMMA.enabledIn(this._features) && (i_skipWSOrEnd == 93 || i_skipWSOrEnd == 125)) {
                _closeScope(i_skipWSOrEnd);
                return this._currToken;
            }
        }
        if (!this._parsingContext.inObject()) {
            return _nextTokenNotInObject(i_skipWSOrEnd);
        }
        this._parsingContext.setCurrentName(_parseName(i_skipWSOrEnd));
        this._currToken = JsonToken.FIELD_NAME;
        int i_skipColon = _skipColon();
        if (i_skipColon == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return this._currToken;
        }
        if (i_skipColon == 45) {
            jsonToken_parseNegNumber = _parseNegNumber();
        } else if (i_skipColon == 91) {
            jsonToken_parseNegNumber = JsonToken.START_ARRAY;
        } else if (i_skipColon == 102) {
            _matchToken("false", 1);
            jsonToken_parseNegNumber = JsonToken.VALUE_FALSE;
        } else if (i_skipColon == 110) {
            _matchToken("null", 1);
            jsonToken_parseNegNumber = JsonToken.VALUE_NULL;
        } else if (i_skipColon == 116) {
            _matchToken("true", 1);
            jsonToken_parseNegNumber = JsonToken.VALUE_TRUE;
        } else if (i_skipColon != 123) {
            switch (i_skipColon) {
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                    jsonToken_parseNegNumber = _parsePosNumber(i_skipColon);
                    break;
                default:
                    jsonToken_parseNegNumber = _handleUnexpectedValue(i_skipColon);
                    break;
            }
        } else {
            jsonToken_parseNegNumber = JsonToken.START_OBJECT;
        }
        this._nextToken = jsonToken_parseNegNumber;
        return this._currToken;
    }

    protected final String parseEscapedName(int[] iArr, int i, int i2, int i3, int i4) throws IOException {
        int[] iArr2 = _icLatin1;
        while (true) {
            if (iArr2[i3] != 0) {
                if (i3 == 34) {
                    break;
                }
                if (i3 != 92) {
                    _throwUnquotedSpace(i3, AppMeasurementSdk.ConditionalUserProperty.NAME);
                } else {
                    i3 = _decodeEscaped();
                }
                if (i3 > 127) {
                    if (i4 >= 4) {
                        if (i >= iArr.length) {
                            iArr = _growArrayBy(iArr, iArr.length);
                            this._quadBuffer = iArr;
                        }
                        iArr[i] = i2;
                        i++;
                        i2 = 0;
                        i4 = 0;
                    }
                    if (i3 < 2048) {
                        i2 = (i2 << 8) | (i3 >> 6) | 192;
                        i4++;
                    } else {
                        int i5 = (i2 << 8) | (i3 >> 12) | 224;
                        int i6 = i4 + 1;
                        if (i6 >= 4) {
                            if (i >= iArr.length) {
                                iArr = _growArrayBy(iArr, iArr.length);
                                this._quadBuffer = iArr;
                            }
                            iArr[i] = i5;
                            i++;
                            i5 = 0;
                            i6 = 0;
                        }
                        i2 = (i5 << 8) | ((i3 >> 6) & 63) | 128;
                        i4 = i6 + 1;
                    }
                    i3 = (i3 & 63) | 128;
                }
            }
            if (i4 < 4) {
                i4++;
                i2 = (i2 << 8) | i3;
            } else {
                if (i >= iArr.length) {
                    iArr = _growArrayBy(iArr, iArr.length);
                    this._quadBuffer = iArr;
                }
                iArr[i] = i2;
                i2 = i3;
                i++;
                i4 = 1;
            }
            i3 = this._inputData.readUnsignedByte();
        }
        if (i4 > 0) {
            if (i >= iArr.length) {
                iArr = _growArrayBy(iArr, iArr.length);
                this._quadBuffer = iArr;
            }
            iArr[i] = pad(i2, i4);
            i++;
        }
        String strFindName = this._symbols.findName(iArr, i);
        return strFindName == null ? addName(iArr, i, i4) : strFindName;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException {
        if (!this._tokenIncomplete || this._currToken != JsonToken.VALUE_STRING) {
            byte[] binaryValue = getBinaryValue(base64Variant);
            outputStream.write(binaryValue);
            return binaryValue.length;
        }
        byte[] bArrAllocBase64Buffer = this._ioContext.allocBase64Buffer();
        try {
            return _readBinary(base64Variant, outputStream, bArrAllocBase64Buffer);
        } finally {
            this._ioContext.releaseBase64Buffer(bArrAllocBase64Buffer);
        }
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public int releaseBuffered(OutputStream outputStream) throws IOException {
        return 0;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public void setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
    }
}
