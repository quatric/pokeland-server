package com.google.api.client.util;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Joiner {
    private final com.google.api.client.repackaged.com.google.common.base.Joiner wrapped;

    private Joiner(com.google.api.client.repackaged.com.google.common.base.Joiner joiner) {
        this.wrapped = joiner;
    }

    /* JADX INFO: renamed from: on */
    public static Joiner m459on(char c) {
        return new Joiner(com.google.api.client.repackaged.com.google.common.base.Joiner.m446on(c));
    }

    public final String join(Iterable<?> iterable) {
        return this.wrapped.join(iterable);
    }
}
