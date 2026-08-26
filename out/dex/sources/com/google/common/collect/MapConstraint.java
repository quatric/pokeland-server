package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@GwtCompatible
@Deprecated
public interface MapConstraint<K, V> {
    void checkKeyValue(@Nullable K k, @Nullable V v);

    String toString();
}
