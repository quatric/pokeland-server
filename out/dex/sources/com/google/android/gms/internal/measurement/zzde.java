package com.google.android.gms.internal.measurement;

import java.io.Serializable;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzde<T> implements zzdb<T>, Serializable {

    @NullableDecl
    private final T zzaby;

    zzde(@NullableDecl T t) {
        this.zzaby = t;
    }

    public final boolean equals(@NullableDecl Object obj) {
        if (!(obj instanceof zzde)) {
            return false;
        }
        T t = this.zzaby;
        T t2 = ((zzde) obj).zzaby;
        if (t != t2) {
            return t != null && t.equals(t2);
        }
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final T get() {
        return this.zzaby;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzaby});
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzaby);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 22);
        sb.append("Suppliers.ofInstance(");
        sb.append(strValueOf);
        sb.append(")");
        return sb.toString();
    }
}
