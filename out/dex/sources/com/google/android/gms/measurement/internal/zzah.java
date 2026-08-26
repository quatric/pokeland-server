package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Iterator;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SafeParcelable.Class(creator = "EventParamsCreator")
@SafeParcelable.Reserved({1})
public final class zzah extends AbstractSafeParcelable implements Iterable<String> {
    public static final Parcelable.Creator<zzah> CREATOR = new zzaj();

    @SafeParcelable.Field(getter = "z", m430id = 2)
    private final Bundle zzft;

    @SafeParcelable.Constructor
    zzah(@SafeParcelable.Param(m431id = 2) Bundle bundle) {
        this.zzft = bundle;
    }

    final Object get(String str) {
        return this.zzft.get(str);
    }

    final Long getLong(String str) {
        return Long.valueOf(this.zzft.getLong(str));
    }

    final String getString(String str) {
        return this.zzft.getString(str);
    }

    @Override // java.lang.Iterable
    public final Iterator<String> iterator() {
        return new zzag(this);
    }

    public final int size() {
        return this.zzft.size();
    }

    public final String toString() {
        return this.zzft.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 2, zzcv(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    final Double zzah(String str) {
        return Double.valueOf(this.zzft.getDouble(str));
    }

    public final Bundle zzcv() {
        return new Bundle(this.zzft);
    }
}
