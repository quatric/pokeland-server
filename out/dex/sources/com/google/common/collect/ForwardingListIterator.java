package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ListIterator;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
public abstract class ForwardingListIterator<E> extends ForwardingIterator<E> implements ListIterator<E> {
    protected ForwardingListIterator() {
    }

    public void add(E e) {
        delegate().add(e);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingIterator, com.google.common.collect.ForwardingObject
    public abstract ListIterator<E> delegate();

    @Override // java.util.ListIterator
    public boolean hasPrevious() {
        return delegate().hasPrevious();
    }

    @Override // java.util.ListIterator
    public int nextIndex() {
        return delegate().nextIndex();
    }

    @Override // java.util.ListIterator
    @CanIgnoreReturnValue
    public E previous() {
        return delegate().previous();
    }

    @Override // java.util.ListIterator
    public int previousIndex() {
        return delegate().previousIndex();
    }

    public void set(E e) {
        delegate().set(e);
    }
}
