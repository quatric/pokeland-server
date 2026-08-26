package com.google.android.gms.internal.measurement;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzhu extends AbstractList<String> implements zzfp, RandomAccess {
    private final zzfp zzamd;

    public zzhu(zzfp zzfpVar) {
        this.zzamd = zzfpVar;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        return (String) this.zzamd.get(i);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator<String> iterator() {
        return new zzhw(this);
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator<String> listIterator(int i) {
        return new zzht(this, i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzamd.size();
    }

    @Override // com.google.android.gms.internal.measurement.zzfp
    public final Object zzbw(int i) {
        return this.zzamd.zzbw(i);
    }

    @Override // com.google.android.gms.internal.measurement.zzfp
    public final void zzc(zzdp zzdpVar) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.internal.measurement.zzfp
    public final List<?> zzvf() {
        return this.zzamd.zzvf();
    }

    @Override // com.google.android.gms.internal.measurement.zzfp
    public final zzfp zzvg() {
        return this;
    }
}
