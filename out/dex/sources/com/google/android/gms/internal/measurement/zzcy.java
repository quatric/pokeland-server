package com.google.android.gms.internal.measurement;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzcy<T> extends zzcw<T> {
    private final T zzabr;

    zzcy(T t) {
        this.zzabr = t;
    }

    public final boolean equals(@NullableDecl Object obj) {
        if (obj instanceof zzcy) {
            return this.zzabr.equals(((zzcy) obj).zzabr);
        }
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzcw
    public final T get() {
        return this.zzabr;
    }

    public final int hashCode() {
        return this.zzabr.hashCode() + 1502476572;
    }

    @Override // com.google.android.gms.internal.measurement.zzcw
    public final boolean isPresent() {
        return true;
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzabr);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 13);
        sb.append("Optional.of(");
        sb.append(strValueOf);
        sb.append(")");
        return sb.toString();
    }
}
