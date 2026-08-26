package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Iterator;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
public interface PeekingIterator<E> extends Iterator<E> {
    @CanIgnoreReturnValue
    E next();

    E peek();

    @Override // java.util.Iterator
    void remove();
}
