package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
public abstract class ForwardingObject {
    protected ForwardingObject() {
    }

    protected abstract Object delegate();

    public String toString() {
        return delegate().toString();
    }
}
