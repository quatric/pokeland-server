package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
class ConsumingQueueIterator<T> extends AbstractIterator<T> {
    private final Queue<T> queue;

    ConsumingQueueIterator(Queue<T> queue) {
        this.queue = (Queue) Preconditions.checkNotNull(queue);
    }

    ConsumingQueueIterator(T... tArr) {
        this.queue = new ArrayDeque(tArr.length);
        Collections.addAll(this.queue, tArr);
    }

    @Override // com.google.common.collect.AbstractIterator
    public T computeNext() {
        return this.queue.isEmpty() ? endOfData() : this.queue.remove();
    }
}
