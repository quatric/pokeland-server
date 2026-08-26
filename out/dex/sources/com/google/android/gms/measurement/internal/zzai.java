package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SafeParcelable.Class(creator = "EventParcelCreator")
@SafeParcelable.Reserved({1})
public final class zzai extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzai> CREATOR = new zzal();

    @SafeParcelable.Field(m430id = 2)
    public final String name;

    @SafeParcelable.Field(m430id = 4)
    public final String origin;

    @SafeParcelable.Field(m430id = 3)
    public final zzah zzfq;

    @SafeParcelable.Field(m430id = 5)
    public final long zzfu;

    zzai(zzai zzaiVar, long j) {
        Preconditions.checkNotNull(zzaiVar);
        this.name = zzaiVar.name;
        this.zzfq = zzaiVar.zzfq;
        this.origin = zzaiVar.origin;
        this.zzfu = j;
    }

    @SafeParcelable.Constructor
    public zzai(@SafeParcelable.Param(m431id = 2) String str, @SafeParcelable.Param(m431id = 3) zzah zzahVar, @SafeParcelable.Param(m431id = 4) String str2, @SafeParcelable.Param(m431id = 5) long j) {
        this.name = str;
        this.zzfq = zzahVar;
        this.origin = str2;
        this.zzfu = j;
    }

    public final String toString() {
        String str = this.origin;
        String str2 = this.name;
        String strValueOf = String.valueOf(this.zzfq);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 21 + String.valueOf(str2).length() + String.valueOf(strValueOf).length());
        sb.append("origin=");
        sb.append(str);
        sb.append(",name=");
        sb.append(str2);
        sb.append(",params=");
        sb.append(strValueOf);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.name, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzfq, i, false);
        SafeParcelWriter.writeString(parcel, 4, this.origin, false);
        SafeParcelWriter.writeLong(parcel, 5, this.zzfu);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
