package com.google.android.gms.internal.measurement;

import java.util.Iterator;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhw implements Iterator<String> {
    private final /* synthetic */ zzhu zzamc;
    private Iterator<String> zzamy;

    zzhw(zzhu zzhuVar) {
        this.zzamc = zzhuVar;
        this.zzamy = this.zzamc.zzamd.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzamy.hasNext();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ String next() {
        return this.zzamy.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
