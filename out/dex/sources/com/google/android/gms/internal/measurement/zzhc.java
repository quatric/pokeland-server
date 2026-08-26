package com.google.android.gms.internal.measurement;

import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class zzhc<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private boolean zzaey;
    private final int zzalk;
    private List<zzhh> zzall;
    private Map<K, V> zzalm;
    private volatile zzhj zzaln;
    private Map<K, V> zzalo;
    private volatile zzhd zzalp;

    private zzhc(int i) {
        this.zzalk = i;
        this.zzall = Collections.emptyList();
        this.zzalm = Collections.emptyMap();
        this.zzalo = Collections.emptyMap();
    }

    /* synthetic */ zzhc(int i, zzhb zzhbVar) {
        this(i);
    }

    private final int zza(K k) {
        int size = this.zzall.size() - 1;
        if (size >= 0) {
            int iCompareTo = k.compareTo((Comparable) this.zzall.get(size).getKey());
            if (iCompareTo > 0) {
                return -(size + 2);
            }
            if (iCompareTo == 0) {
                return size;
            }
        }
        int i = 0;
        while (i <= size) {
            int i2 = (i + size) / 2;
            int iCompareTo2 = k.compareTo((Comparable) this.zzall.get(i2).getKey());
            if (iCompareTo2 < 0) {
                size = i2 - 1;
            } else {
                if (iCompareTo2 <= 0) {
                    return i2;
                }
                i = i2 + 1;
            }
        }
        return -(i + 1);
    }

    static <FieldDescriptorType extends zzeq<FieldDescriptorType>> zzhc<FieldDescriptorType, Object> zzce(int i) {
        return new zzhb(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final V zzcg(int i) {
        zzwk();
        V v = (V) this.zzall.remove(i).getValue();
        if (!this.zzalm.isEmpty()) {
            Iterator<Map.Entry<K, V>> it = zzwl().entrySet().iterator();
            this.zzall.add(new zzhh(this, it.next()));
            it.remove();
        }
        return v;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzwk() {
        if (this.zzaey) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zzwl() {
        zzwk();
        if (this.zzalm.isEmpty() && !(this.zzalm instanceof TreeMap)) {
            this.zzalm = new TreeMap();
            this.zzalo = ((TreeMap) this.zzalm).descendingMap();
        }
        return (SortedMap) this.zzalm;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        zzwk();
        if (!this.zzall.isEmpty()) {
            this.zzall.clear();
        }
        if (this.zzalm.isEmpty()) {
            return;
        }
        this.zzalm.clear();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza(comparable) >= 0 || this.zzalm.containsKey(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.zzaln == null) {
            this.zzaln = new zzhj(this, null);
        }
        return this.zzaln;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzhc)) {
            return super.equals(obj);
        }
        zzhc zzhcVar = (zzhc) obj;
        int size = size();
        if (size != zzhcVar.size()) {
            return false;
        }
        int iZzwh = zzwh();
        if (iZzwh != zzhcVar.zzwh()) {
            return entrySet().equals(zzhcVar.entrySet());
        }
        for (int i = 0; i < iZzwh; i++) {
            if (!zzcf(i).equals(zzhcVar.zzcf(i))) {
                return false;
            }
        }
        if (iZzwh != size) {
            return this.zzalm.equals(zzhcVar.zzalm);
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int iZza = zza(comparable);
        return iZza >= 0 ? (V) this.zzall.get(iZza).getValue() : this.zzalm.get(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int hashCode() {
        int iZzwh = zzwh();
        int iHashCode = 0;
        for (int i = 0; i < iZzwh; i++) {
            iHashCode += this.zzall.get(i).hashCode();
        }
        return this.zzalm.size() > 0 ? iHashCode + this.zzalm.hashCode() : iHashCode;
    }

    public final boolean isImmutable() {
        return this.zzaey;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        zzwk();
        Comparable comparable = (Comparable) obj;
        int iZza = zza(comparable);
        if (iZza >= 0) {
            return zzcg(iZza);
        }
        if (this.zzalm.isEmpty()) {
            return null;
        }
        return this.zzalm.remove(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.zzall.size() + this.zzalm.size();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
    public final V put(K k, V v) {
        zzwk();
        int iZza = zza(k);
        if (iZza >= 0) {
            return (V) this.zzall.get(iZza).setValue(v);
        }
        zzwk();
        if (this.zzall.isEmpty() && !(this.zzall instanceof ArrayList)) {
            this.zzall = new ArrayList(this.zzalk);
        }
        int i = -(iZza + 1);
        if (i >= this.zzalk) {
            return zzwl().put(k, v);
        }
        int size = this.zzall.size();
        int i2 = this.zzalk;
        if (size == i2) {
            zzhh zzhhVarRemove = this.zzall.remove(i2 - 1);
            zzwl().put((Comparable) zzhhVarRemove.getKey(), zzhhVarRemove.getValue());
        }
        this.zzall.add(i, new zzhh(this, k, v));
        return null;
    }

    public final Map.Entry<K, V> zzcf(int i) {
        return this.zzall.get(i);
    }

    public void zzry() {
        if (this.zzaey) {
            return;
        }
        this.zzalm = this.zzalm.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzalm);
        this.zzalo = this.zzalo.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzalo);
        this.zzaey = true;
    }

    public final int zzwh() {
        return this.zzall.size();
    }

    public final Iterable<Map.Entry<K, V>> zzwi() {
        return this.zzalm.isEmpty() ? zzhg.zzwn() : this.zzalm.entrySet();
    }

    final Set<Map.Entry<K, V>> zzwj() {
        if (this.zzalp == null) {
            this.zzalp = new zzhd(this, null);
        }
        return this.zzalp;
    }
}
