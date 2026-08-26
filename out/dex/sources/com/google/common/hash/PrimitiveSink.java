package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@CanIgnoreReturnValue
@Beta
public interface PrimitiveSink {
    PrimitiveSink putBoolean(boolean z);

    PrimitiveSink putByte(byte b);

    PrimitiveSink putBytes(byte[] bArr);

    PrimitiveSink putBytes(byte[] bArr, int i, int i2);

    PrimitiveSink putChar(char c);

    PrimitiveSink putDouble(double d);

    PrimitiveSink putFloat(float f);

    PrimitiveSink putInt(int i);

    PrimitiveSink putLong(long j);

    PrimitiveSink putShort(short s);

    PrimitiveSink putString(CharSequence charSequence, Charset charset);

    PrimitiveSink putUnencodedChars(CharSequence charSequence);
}
