package com.google.android.gms.internal.firebase_messaging;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzo extends WeakReference<Throwable> {
    private final int zzm;

    public zzo(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
        super(th, referenceQueue);
        if (th == null) {
            throw new NullPointerException("The referent cannot be null");
        }
        this.zzm = System.identityHashCode(th);
    }

    public final boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            if (this == obj) {
                return true;
            }
            zzo zzoVar = (zzo) obj;
            if (this.zzm == zzoVar.zzm && get() == zzoVar.get()) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.zzm;
    }
}
