package com.fasterxml.jackson.core.p014io;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.util.BufferRecyclers;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class SerializedString implements SerializableString, Serializable {
    private static final long serialVersionUID = 1;
    protected transient String _jdkSerializeValue;
    protected char[] _quotedChars;
    protected byte[] _quotedUTF8Ref;
    protected byte[] _unquotedUTF8Ref;
    protected final String _value;

    public SerializedString(String str) {
        if (str == null) {
            throw new IllegalStateException("Null String illegal for SerializedString");
        }
        this._value = str;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        this._jdkSerializeValue = objectInputStream.readUTF();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeUTF(this._value);
    }

    @Override // com.fasterxml.jackson.core.SerializableString
    public int appendQuoted(char[] cArr, int i) {
        char[] cArrQuoteAsJsonText = this._quotedChars;
        if (cArrQuoteAsJsonText == null) {
            cArrQuoteAsJsonText = BufferRecyclers.quoteAsJsonText(this._value);
            this._quotedChars = cArrQuoteAsJsonText;
        }
        int length = cArrQuoteAsJsonText.length;
        if (i + length > cArr.length) {
            return -1;
        }
        System.arraycopy(cArrQuoteAsJsonText, 0, cArr, i, length);
        return length;
    }

    @Override // com.fasterxml.jackson.core.SerializableString
    public int appendQuotedUTF8(byte[] bArr, int i) {
        byte[] bArrQuoteAsJsonUTF8 = this._quotedUTF8Ref;
        if (bArrQuoteAsJsonUTF8 == null) {
            bArrQuoteAsJsonUTF8 = BufferRecyclers.quoteAsJsonUTF8(this._value);
            this._quotedUTF8Ref = bArrQuoteAsJsonUTF8;
        }
        int length = bArrQuoteAsJsonUTF8.length;
        if (i + length > bArr.length) {
            return -1;
        }
        System.arraycopy(bArrQuoteAsJsonUTF8, 0, bArr, i, length);
        return length;
    }

    @Override // com.fasterxml.jackson.core.SerializableString
    public int appendUnquoted(char[] cArr, int i) {
        String str = this._value;
        int length = str.length();
        if (i + length > cArr.length) {
            return -1;
        }
        str.getChars(0, length, cArr, i);
        return length;
    }

    @Override // com.fasterxml.jackson.core.SerializableString
    public int appendUnquotedUTF8(byte[] bArr, int i) {
        byte[] bArrEncodeAsUTF8 = this._unquotedUTF8Ref;
        if (bArrEncodeAsUTF8 == null) {
            bArrEncodeAsUTF8 = BufferRecyclers.encodeAsUTF8(this._value);
            this._unquotedUTF8Ref = bArrEncodeAsUTF8;
        }
        int length = bArrEncodeAsUTF8.length;
        if (i + length > bArr.length) {
            return -1;
        }
        System.arraycopy(bArrEncodeAsUTF8, 0, bArr, i, length);
        return length;
    }

    @Override // com.fasterxml.jackson.core.SerializableString
    public final char[] asQuotedChars() {
        char[] cArr = this._quotedChars;
        if (cArr != null) {
            return cArr;
        }
        char[] cArrQuoteAsJsonText = BufferRecyclers.quoteAsJsonText(this._value);
        this._quotedChars = cArrQuoteAsJsonText;
        return cArrQuoteAsJsonText;
    }

    @Override // com.fasterxml.jackson.core.SerializableString
    public final byte[] asQuotedUTF8() {
        byte[] bArr = this._quotedUTF8Ref;
        if (bArr != null) {
            return bArr;
        }
        byte[] bArrQuoteAsJsonUTF8 = BufferRecyclers.quoteAsJsonUTF8(this._value);
        this._quotedUTF8Ref = bArrQuoteAsJsonUTF8;
        return bArrQuoteAsJsonUTF8;
    }

    @Override // com.fasterxml.jackson.core.SerializableString
    public final byte[] asUnquotedUTF8() {
        byte[] bArr = this._unquotedUTF8Ref;
        if (bArr != null) {
            return bArr;
        }
        byte[] bArrEncodeAsUTF8 = BufferRecyclers.encodeAsUTF8(this._value);
        this._unquotedUTF8Ref = bArrEncodeAsUTF8;
        return bArrEncodeAsUTF8;
    }

    @Override // com.fasterxml.jackson.core.SerializableString
    public final int charLength() {
        return this._value.length();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        return this._value.equals(((SerializedString) obj)._value);
    }

    @Override // com.fasterxml.jackson.core.SerializableString
    public final String getValue() {
        return this._value;
    }

    public final int hashCode() {
        return this._value.hashCode();
    }

    @Override // com.fasterxml.jackson.core.SerializableString
    public int putQuotedUTF8(ByteBuffer byteBuffer) {
        byte[] bArrQuoteAsJsonUTF8 = this._quotedUTF8Ref;
        if (bArrQuoteAsJsonUTF8 == null) {
            bArrQuoteAsJsonUTF8 = BufferRecyclers.quoteAsJsonUTF8(this._value);
            this._quotedUTF8Ref = bArrQuoteAsJsonUTF8;
        }
        int length = bArrQuoteAsJsonUTF8.length;
        if (length > byteBuffer.remaining()) {
            return -1;
        }
        byteBuffer.put(bArrQuoteAsJsonUTF8, 0, length);
        return length;
    }

    @Override // com.fasterxml.jackson.core.SerializableString
    public int putUnquotedUTF8(ByteBuffer byteBuffer) {
        byte[] bArrEncodeAsUTF8 = this._unquotedUTF8Ref;
        if (bArrEncodeAsUTF8 == null) {
            bArrEncodeAsUTF8 = BufferRecyclers.encodeAsUTF8(this._value);
            this._unquotedUTF8Ref = bArrEncodeAsUTF8;
        }
        int length = bArrEncodeAsUTF8.length;
        if (length > byteBuffer.remaining()) {
            return -1;
        }
        byteBuffer.put(bArrEncodeAsUTF8, 0, length);
        return length;
    }

    protected Object readResolve() {
        return new SerializedString(this._jdkSerializeValue);
    }

    public final String toString() {
        return this._value;
    }

    @Override // com.fasterxml.jackson.core.SerializableString
    public int writeQuotedUTF8(OutputStream outputStream) throws IOException {
        byte[] bArrQuoteAsJsonUTF8 = this._quotedUTF8Ref;
        if (bArrQuoteAsJsonUTF8 == null) {
            bArrQuoteAsJsonUTF8 = BufferRecyclers.quoteAsJsonUTF8(this._value);
            this._quotedUTF8Ref = bArrQuoteAsJsonUTF8;
        }
        int length = bArrQuoteAsJsonUTF8.length;
        outputStream.write(bArrQuoteAsJsonUTF8, 0, length);
        return length;
    }

    @Override // com.fasterxml.jackson.core.SerializableString
    public int writeUnquotedUTF8(OutputStream outputStream) throws IOException {
        byte[] bArrEncodeAsUTF8 = this._unquotedUTF8Ref;
        if (bArrEncodeAsUTF8 == null) {
            bArrEncodeAsUTF8 = BufferRecyclers.encodeAsUTF8(this._value);
            this._unquotedUTF8Ref = bArrEncodeAsUTF8;
        }
        int length = bArrEncodeAsUTF8.length;
        outputStream.write(bArrEncodeAsUTF8, 0, length);
        return length;
    }
}
