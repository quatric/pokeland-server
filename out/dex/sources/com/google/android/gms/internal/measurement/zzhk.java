package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhk<K, V> implements Iterator<Map.Entry<K, V>> {
    private int pos;
    private final /* synthetic */ zzhc zzalq;
    private Iterator<Map.Entry<K, V>> zzalr;
    private boolean zzalv;

    private zzhk(zzhc zzhcVar) {
        this.zzalq = zzhcVar;
        this.pos = -1;
    }

    /* synthetic */ zzhk(zzhc zzhcVar, zzhb zzhbVar) {
        this(zzhcVar);
    }

    private final Iterator<Map.Entry<K, V>> zzwm() {
        if (this.zzalr == null) {
            this.zzalr = this.zzalq.zzalm.entrySet().iterator();
        }
        return this.zzalr;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.pos + 1 < this.zzalq.zzall.size() || (!this.zzalq.zzalm.isEmpty() && zzwm().hasNext());
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        this.zzalv = true;
        int i = this.pos + 1;
        this.pos = i;
        return i < this.zzalq.zzall.size() ? (Map.Entry) this.zzalq.zzall.get(this.pos) : zzwm().next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.zzalv) {
            throw new IllegalStateException("remove() was called before next()");
        }
        this.zzalv = false;
        this.zzalq.zzwk();
        if (this.pos >= this.zzalq.zzall.size()) {
            zzwm().remove();
            return;
        }
        zzhc zzhcVar = this.zzalq;
        int i = this.pos;
        this.pos = i - 1;
        zzhcVar.zzcg(i);
    }
}
