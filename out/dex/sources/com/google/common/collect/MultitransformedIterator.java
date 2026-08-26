package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
abstract class MultitransformedIterator<F, T> implements Iterator<T> {
    final Iterator<? extends F> backingIterator;
    private Iterator<? extends T> current = Iterators.emptyIterator();
    private Iterator<? extends T> removeFrom;

    MultitransformedIterator(Iterator<? extends F> it) {
        this.backingIterator = (Iterator) Preconditions.checkNotNull(it);
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        Preconditions.checkNotNull(this.current);
        if (this.current.hasNext()) {
            return true;
        }
        while (this.backingIterator.hasNext()) {
            Iterator<? extends T> itTransform = transform(this.backingIterator.next());
            this.current = itTransform;
            Preconditions.checkNotNull(itTransform);
            if (this.current.hasNext()) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Iterator
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Iterator<? extends T> it = this.current;
        this.removeFrom = it;
        return it.next();
    }

    @Override // java.util.Iterator
    public void remove() {
        CollectPreconditions.checkRemove(this.removeFrom != null);
        this.removeFrom.remove();
        this.removeFrom = null;
    }

    abstract Iterator<? extends T> transform(F f);
}
