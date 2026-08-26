package com.google.api.client.util;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Throwables {
    private Throwables() {
    }

    public static RuntimeException propagate(Throwable th) {
        return com.google.api.client.repackaged.com.google.common.base.Throwables.propagate(th);
    }

    public static void propagateIfPossible(Throwable th) {
        if (th != null) {
            com.google.api.client.repackaged.com.google.common.base.Throwables.throwIfUnchecked(th);
        }
    }

    public static <X extends Throwable> void propagateIfPossible(Throwable th, Class<X> cls) throws Throwable {
        com.google.api.client.repackaged.com.google.common.base.Throwables.propagateIfPossible(th, cls);
    }
}
