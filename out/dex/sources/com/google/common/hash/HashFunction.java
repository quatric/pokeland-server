package com.google.common.hash;

import com.google.common.annotations.Beta;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public interface HashFunction {
    int bits();

    HashCode hashBytes(byte[] bArr);

    HashCode hashBytes(byte[] bArr, int i, int i2);

    HashCode hashInt(int i);

    HashCode hashLong(long j);

    <T> HashCode hashObject(T t, Funnel<? super T> funnel);

    HashCode hashString(CharSequence charSequence, Charset charset);

    HashCode hashUnencodedChars(CharSequence charSequence);

    Hasher newHasher();

    Hasher newHasher(int i);
}
