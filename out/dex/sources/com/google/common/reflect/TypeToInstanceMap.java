package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Map;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public interface TypeToInstanceMap<B> extends Map<TypeToken<? extends B>, B> {
    @Nullable
    <T extends B> T getInstance(TypeToken<T> typeToken);

    @Nullable
    <T extends B> T getInstance(Class<T> cls);

    @CanIgnoreReturnValue
    @Nullable
    <T extends B> T putInstance(TypeToken<T> typeToken, @Nullable T t);

    @CanIgnoreReturnValue
    @Nullable
    <T extends B> T putInstance(Class<T> cls, @Nullable T t);
}
