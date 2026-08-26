package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzgc<K, V> extends LinkedHashMap<K, V> {
    private static final zzgc zzake;
    private boolean zzacz;

    static {
        zzgc zzgcVar = new zzgc();
        zzake = zzgcVar;
        zzgcVar.zzacz = false;
    }

    private zzgc() {
        this.zzacz = true;
    }

    private zzgc(Map<K, V> map) {
        super(map);
        this.zzacz = true;
    }

    private static int zzs(Object obj) {
        if (obj instanceof byte[]) {
            return zzez.hashCode((byte[]) obj);
        }
        if (obj instanceof zzfc) {
            throw new UnsupportedOperationException();
        }
        return obj.hashCode();
    }

    public static <K, V> zzgc<K, V> zzvl() {
        return zzake;
    }

    private final void zzvn() {
        if (!this.zzacz) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final void clear() {
        zzvn();
        super.clear();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final Set<Map.Entry<K, V>> entrySet() {
        return isEmpty() ? Collections.emptySet() : super.entrySet();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean equals(Object obj) {
        boolean z;
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (this == map) {
                z = true;
            } else {
                if (size() == map.size()) {
                    Iterator<Map.Entry<K, V>> it = entrySet().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Map.Entry<K, V> next = it.next();
                            if (map.containsKey(next.getKey())) {
                                V value = next.getValue();
                                Object obj2 = map.get(next.getKey());
                                if (!(((value instanceof byte[]) && (obj2 instanceof byte[])) ? Arrays.equals((byte[]) value, (byte[]) obj2) : value.equals(obj2))) {
                                }
                            }
                        } else {
                            z = true;
                        }
                    }
                }
                z = false;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int hashCode() {
        int iZzs = 0;
        for (Map.Entry<K, V> entry : entrySet()) {
            iZzs += zzs(entry.getValue()) ^ zzs(entry.getKey());
        }
        return iZzs;
    }

    public final boolean isMutable() {
        return this.zzacz;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final V put(K k, V v) {
        zzvn();
        zzez.checkNotNull(k);
        zzez.checkNotNull(v);
        return (V) super.put(k, v);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final void putAll(Map<? extends K, ? extends V> map) {
        zzvn();
        for (K k : map.keySet()) {
            zzez.checkNotNull(k);
            zzez.checkNotNull(map.get(k));
        }
        super.putAll(map);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final V remove(Object obj) {
        zzvn();
        return (V) super.remove(obj);
    }

    public final void zza(zzgc<K, V> zzgcVar) {
        zzvn();
        if (zzgcVar.isEmpty()) {
            return;
        }
        putAll(zzgcVar);
    }

    public final void zzry() {
        this.zzacz = false;
    }

    public final zzgc<K, V> zzvm() {
        return isEmpty() ? new zzgc<>() : new zzgc<>(this);
    }
}
