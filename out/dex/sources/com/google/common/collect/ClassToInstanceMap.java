package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Map;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
public interface ClassToInstanceMap<B> extends Map<Class<? extends B>, B> {
    @CanIgnoreReturnValue
    <T extends B> T getInstance(Class<T> cls);

    @CanIgnoreReturnValue
    <T extends B> T putInstance(Class<T> cls, @Nullable T t);
}
