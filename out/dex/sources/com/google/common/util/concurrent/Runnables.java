package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@GwtCompatible
public final class Runnables {
    private static final Runnable EMPTY_RUNNABLE = new Runnable() { // from class: com.google.common.util.concurrent.Runnables.1
        @Override // java.lang.Runnable
        public void run() {
        }
    };

    private Runnables() {
    }

    public static Runnable doNothing() {
        return EMPTY_RUNNABLE;
    }
}
