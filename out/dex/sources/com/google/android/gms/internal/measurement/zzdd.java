package com.google.android.gms.internal.measurement;

import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzdd<T> implements zzdb<T>, Serializable {

    @NullableDecl
    private transient T value;
    private final zzdb<T> zzabs;
    private volatile transient boolean zzdh;

    zzdd(zzdb<T> zzdbVar) {
        this.zzabs = (zzdb) zzcz.checkNotNull(zzdbVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final T get() {
        if (!this.zzdh) {
            synchronized (this) {
                if (!this.zzdh) {
                    T t = this.zzabs.get();
                    this.value = t;
                    this.zzdh = true;
                    return t;
                }
            }
        }
        return this.value;
    }

    public final String toString() {
        Object string;
        if (this.zzdh) {
            String strValueOf = String.valueOf(this.value);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 25);
            sb.append("<supplier that returned ");
            sb.append(strValueOf);
            sb.append(">");
            string = sb.toString();
        } else {
            string = this.zzabs;
        }
        String strValueOf2 = String.valueOf(string);
        StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 19);
        sb2.append("Suppliers.memoize(");
        sb2.append(strValueOf2);
        sb2.append(")");
        return sb2.toString();
    }
}
