package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhe<K, V> implements Iterator<Map.Entry<K, V>> {
    private int pos;
    private final /* synthetic */ zzhc zzalq;
    private Iterator<Map.Entry<K, V>> zzalr;

    private zzhe(zzhc zzhcVar) {
        this.zzalq = zzhcVar;
        this.pos = this.zzalq.zzall.size();
    }

    /* synthetic */ zzhe(zzhc zzhcVar, zzhb zzhbVar) {
        this(zzhcVar);
    }

    private final Iterator<Map.Entry<K, V>> zzwm() {
        if (this.zzalr == null) {
            this.zzalr = this.zzalq.zzalo.entrySet().iterator();
        }
        return this.zzalr;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        int i = this.pos;
        return (i > 0 && i <= this.zzalq.zzall.size()) || zzwm().hasNext();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        if (zzwm().hasNext()) {
            return zzwm().next();
        }
        List list = this.zzalq.zzall;
        int i = this.pos - 1;
        this.pos = i;
        return (Map.Entry) list.get(i);
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
