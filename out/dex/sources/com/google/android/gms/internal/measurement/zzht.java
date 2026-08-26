package com.google.android.gms.internal.measurement;

import java.util.ListIterator;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzht implements ListIterator<String> {
    private ListIterator<String> zzama;
    private final /* synthetic */ int zzamb;
    private final /* synthetic */ zzhu zzamc;

    zzht(zzhu zzhuVar, int i) {
        this.zzamc = zzhuVar;
        this.zzamb = i;
        this.zzama = this.zzamc.zzamd.listIterator(this.zzamb);
    }

    @Override // java.util.ListIterator
    public final /* synthetic */ void add(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final boolean hasNext() {
        return this.zzama.hasNext();
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.zzama.hasPrevious();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final /* synthetic */ Object next() {
        return this.zzama.next();
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.zzama.nextIndex();
    }

    @Override // java.util.ListIterator
    public final /* synthetic */ String previous() {
        return this.zzama.previous();
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.zzama.previousIndex();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    public final /* synthetic */ void set(String str) {
        throw new UnsupportedOperationException();
    }
}
