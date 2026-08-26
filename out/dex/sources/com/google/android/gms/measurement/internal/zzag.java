package com.google.android.gms.measurement.internal;

import java.util.Iterator;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzag implements Iterator<String> {
    private Iterator<String> zzfr;
    private final /* synthetic */ zzah zzfs;

    zzag(zzah zzahVar) {
        this.zzfs = zzahVar;
        this.zzfr = this.zzfs.zzft.keySet().iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzfr.hasNext();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ String next() {
        return this.zzfr.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Remove not supported");
    }
}
