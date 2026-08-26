package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.GwtCompatible;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
abstract class CommonPattern {
    CommonPattern() {
    }

    public abstract boolean equals(Object obj);

    abstract int flags();

    public abstract int hashCode();

    abstract CommonMatcher matcher(CharSequence charSequence);

    abstract String pattern();

    public abstract String toString();
}
