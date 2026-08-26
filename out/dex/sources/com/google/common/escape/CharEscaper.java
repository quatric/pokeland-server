package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@GwtCompatible
public abstract class CharEscaper extends Escaper {
    private static final int DEST_PAD_MULTIPLIER = 2;

    protected CharEscaper() {
    }

    private static char[] growBuffer(char[] cArr, int i, int i2) {
        char[] cArr2 = new char[i2];
        if (i > 0) {
            System.arraycopy(cArr, 0, cArr2, 0, i);
        }
        return cArr2;
    }

    @Override // com.google.common.escape.Escaper
    public String escape(String str) {
        Preconditions.checkNotNull(str);
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (escape(str.charAt(i)) != null) {
                return escapeSlow(str, i);
            }
        }
        return str;
    }

    protected abstract char[] escape(char c);

    protected final String escapeSlow(String str, int i) {
        int i2;
        int length = str.length();
        char[] cArrCharBufferFromThreadLocal = Platform.charBufferFromThreadLocal();
        char[] cArrGrowBuffer = cArrCharBufferFromThreadLocal;
        int length2 = cArrCharBufferFromThreadLocal.length;
        int i3 = 0;
        int i4 = 0;
        while (i < length) {
            char[] cArrEscape = escape(str.charAt(i));
            if (cArrEscape != null) {
                int length3 = cArrEscape.length;
                int i5 = i - i3;
                int i6 = i4 + i5;
                int i7 = i6 + length3;
                if (length2 < i7) {
                    length2 = ((length - i) * 2) + i7;
                    cArrGrowBuffer = growBuffer(cArrGrowBuffer, i4, length2);
                }
                if (i5 > 0) {
                    str.getChars(i3, i, cArrGrowBuffer, i4);
                    i4 = i6;
                }
                if (length3 > 0) {
                    System.arraycopy(cArrEscape, 0, cArrGrowBuffer, i4, length3);
                    i4 += length3;
                }
                i3 = i + 1;
            }
            i++;
        }
        int i8 = length - i3;
        if (i8 > 0) {
            i2 = i8 + i4;
            if (length2 < i2) {
                cArrGrowBuffer = growBuffer(cArrGrowBuffer, i4, i2);
            }
            str.getChars(i3, length, cArrGrowBuffer, i4);
        } else {
            i2 = i4;
        }
        return new String(cArrGrowBuffer, 0, i2);
    }
}
