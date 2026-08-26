package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Map;
import javax.annotation.Nullable;
import kotlin.jvm.internal.CharCompanionObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@GwtCompatible
public abstract class ArrayBasedUnicodeEscaper extends UnicodeEscaper {
    private final char[][] replacements;
    private final int replacementsLength;
    private final int safeMax;
    private final char safeMaxChar;
    private final int safeMin;
    private final char safeMinChar;

    protected ArrayBasedUnicodeEscaper(ArrayBasedEscaperMap arrayBasedEscaperMap, int i, int i2, @Nullable String str) {
        Preconditions.checkNotNull(arrayBasedEscaperMap);
        this.replacements = arrayBasedEscaperMap.getReplacementArray();
        this.replacementsLength = this.replacements.length;
        if (i2 < i) {
            i2 = -1;
            i = Integer.MAX_VALUE;
        }
        this.safeMin = i;
        this.safeMax = i2;
        if (i >= 55296) {
            this.safeMinChar = CharCompanionObject.MAX_VALUE;
            this.safeMaxChar = (char) 0;
        } else {
            this.safeMinChar = (char) i;
            this.safeMaxChar = (char) Math.min(i2, 55295);
        }
    }

    protected ArrayBasedUnicodeEscaper(Map<Character, String> map, int i, int i2, @Nullable String str) {
        this(ArrayBasedEscaperMap.create(map), i, i2, str);
    }

    @Override // com.google.common.escape.UnicodeEscaper, com.google.common.escape.Escaper
    public final String escape(String str) {
        Preconditions.checkNotNull(str);
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if ((cCharAt < this.replacementsLength && this.replacements[cCharAt] != null) || cCharAt > this.safeMaxChar || cCharAt < this.safeMinChar) {
                return escapeSlow(str, i);
            }
        }
        return str;
    }

    @Override // com.google.common.escape.UnicodeEscaper
    protected final char[] escape(int i) {
        char[] cArr;
        if (i < this.replacementsLength && (cArr = this.replacements[i]) != null) {
            return cArr;
        }
        if (i < this.safeMin || i > this.safeMax) {
            return escapeUnsafe(i);
        }
        return null;
    }

    protected abstract char[] escapeUnsafe(int i);

    @Override // com.google.common.escape.UnicodeEscaper
    protected final int nextEscapeIndex(CharSequence charSequence, int i, int i2) {
        while (i < i2) {
            char cCharAt = charSequence.charAt(i);
            if ((cCharAt < this.replacementsLength && this.replacements[cCharAt] != null) || cCharAt > this.safeMaxChar || cCharAt < this.safeMinChar) {
                break;
            }
            i++;
        }
        return i;
    }
}
