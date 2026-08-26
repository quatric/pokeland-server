package com.google.common.hash;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@CanIgnoreReturnValue
abstract class AbstractHasher implements Hasher {
    AbstractHasher() {
    }

    @Override // com.google.common.hash.PrimitiveSink
    public final Hasher putBoolean(boolean z) {
        return putByte(z ? (byte) 1 : (byte) 0);
    }

    @Override // com.google.common.hash.PrimitiveSink
    public final Hasher putDouble(double d) {
        return putLong(Double.doubleToRawLongBits(d));
    }

    @Override // com.google.common.hash.PrimitiveSink
    public final Hasher putFloat(float f) {
        return putInt(Float.floatToRawIntBits(f));
    }

    @Override // com.google.common.hash.PrimitiveSink
    public Hasher putString(CharSequence charSequence, Charset charset) {
        return putBytes(charSequence.toString().getBytes(charset));
    }

    @Override // com.google.common.hash.PrimitiveSink
    public Hasher putUnencodedChars(CharSequence charSequence) {
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            putChar(charSequence.charAt(i));
        }
        return this;
    }
}
