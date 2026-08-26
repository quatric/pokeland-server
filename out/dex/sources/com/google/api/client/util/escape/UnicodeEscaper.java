package com.google.api.client.util.escape;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class UnicodeEscaper extends Escaper {
    private static final int DEST_PAD = 32;

    protected static int codePointAt(CharSequence charSequence, int i, int i2) {
        if (i >= i2) {
            throw new IndexOutOfBoundsException("Index exceeds specified range");
        }
        int i3 = i + 1;
        char cCharAt = charSequence.charAt(i);
        if (cCharAt < 55296 || cCharAt > 57343) {
            return cCharAt;
        }
        if (cCharAt > 56319) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unexpected low surrogate character '");
            sb.append(cCharAt);
            sb.append("' with value ");
            sb.append((int) cCharAt);
            sb.append(" at index ");
            sb.append(i3 - 1);
            throw new IllegalArgumentException(sb.toString());
        }
        if (i3 == i2) {
            return -cCharAt;
        }
        char cCharAt2 = charSequence.charAt(i3);
        if (Character.isLowSurrogate(cCharAt2)) {
            return Character.toCodePoint(cCharAt, cCharAt2);
        }
        throw new IllegalArgumentException("Expected low surrogate but got char '" + cCharAt2 + "' with value " + ((int) cCharAt2) + " at index " + i3);
    }

    private static char[] growBuffer(char[] cArr, int i, int i2) {
        char[] cArr2 = new char[i2];
        if (i > 0) {
            System.arraycopy(cArr, 0, cArr2, 0, i);
        }
        return cArr2;
    }

    @Override // com.google.api.client.util.escape.Escaper
    public abstract String escape(String str);

    protected abstract char[] escape(int i);

    protected final String escapeSlow(String str, int i) {
        int i2;
        int length = str.length();
        char[] cArrCharBufferFromThreadLocal = Platform.charBufferFromThreadLocal();
        int i3 = 0;
        int length2 = 0;
        while (i < length) {
            int iCodePointAt = codePointAt(str, i, length);
            if (iCodePointAt < 0) {
                throw new IllegalArgumentException("Trailing high surrogate at end of input");
            }
            char[] cArrEscape = escape(iCodePointAt);
            int i4 = (Character.isSupplementaryCodePoint(iCodePointAt) ? 2 : 1) + i;
            if (cArrEscape != null) {
                int i5 = i - i3;
                int i6 = length2 + i5;
                int length3 = cArrEscape.length + i6;
                if (cArrCharBufferFromThreadLocal.length < length3) {
                    cArrCharBufferFromThreadLocal = growBuffer(cArrCharBufferFromThreadLocal, length2, ((length3 + length) - i) + 32);
                }
                if (i5 > 0) {
                    str.getChars(i3, i, cArrCharBufferFromThreadLocal, length2);
                    length2 = i6;
                }
                if (cArrEscape.length > 0) {
                    System.arraycopy(cArrEscape, 0, cArrCharBufferFromThreadLocal, length2, cArrEscape.length);
                    length2 += cArrEscape.length;
                }
                i3 = i4;
            }
            i = nextEscapeIndex(str, i4, length);
        }
        int i7 = length - i3;
        if (i7 > 0) {
            i2 = i7 + length2;
            if (cArrCharBufferFromThreadLocal.length < i2) {
                cArrCharBufferFromThreadLocal = growBuffer(cArrCharBufferFromThreadLocal, length2, i2);
            }
            str.getChars(i3, length, cArrCharBufferFromThreadLocal, length2);
        } else {
            i2 = length2;
        }
        return new String(cArrCharBufferFromThreadLocal, 0, i2);
    }

    protected abstract int nextEscapeIndex(CharSequence charSequence, int i, int i2);
}
