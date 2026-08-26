package com.google.android.gms.internal.measurement;

import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhh<K, V> implements Comparable<zzhh>, Map.Entry<K, V> {
    private V value;
    private final /* synthetic */ zzhc zzalq;

    /* JADX INFO: Incorrect field signature: TK; */
    private final Comparable zzalu;

    /* JADX WARN: Multi-variable type inference failed */
    zzhh(zzhc zzhcVar, K k, V v) {
        this.zzalq = zzhcVar;
        this.zzalu = k;
        this.value = v;
    }

    zzhh(zzhc zzhcVar, Map.Entry<K, V> entry) {
        this(zzhcVar, (Comparable) entry.getKey(), entry.getValue());
    }

    private static boolean equals(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(zzhh zzhhVar) {
        return ((Comparable) getKey()).compareTo((Comparable) zzhhVar.getKey());
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return equals(this.zzalu, entry.getKey()) && equals(this.value, entry.getValue());
    }

    @Override // java.util.Map.Entry
    public final /* synthetic */ Object getKey() {
        return this.zzalu;
    }

    @Override // java.util.Map.Entry
    public final V getValue() {
        return this.value;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        Comparable comparable = this.zzalu;
        int iHashCode = comparable == null ? 0 : comparable.hashCode();
        V v = this.value;
        return iHashCode ^ (v != null ? v.hashCode() : 0);
    }

    @Override // java.util.Map.Entry
    public final V setValue(V v) {
        this.zzalq.zzwk();
        V v2 = this.value;
        this.value = v;
        return v2;
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzalu);
        String strValueOf2 = String.valueOf(this.value);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 1 + String.valueOf(strValueOf2).length());
        sb.append(strValueOf);
        sb.append("=");
        sb.append(strValueOf2);
        return sb.toString();
    }
}
