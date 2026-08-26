package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SafeParcelable.Class(creator = "InitializationParamsCreator")
public final class zzx extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzx> CREATOR = new zzw();

    @SafeParcelable.Field(m430id = 5)
    public final String origin;

    @SafeParcelable.Field(m430id = 1)
    public final long zzr;

    @SafeParcelable.Field(m430id = 2)
    public final long zzs;

    @SafeParcelable.Field(m430id = 3)
    public final boolean zzt;

    @SafeParcelable.Field(m430id = 4)
    public final String zzu;

    @SafeParcelable.Field(m430id = 6)
    public final String zzv;

    @SafeParcelable.Field(m430id = 7)
    public final Bundle zzw;

    @SafeParcelable.Constructor
    public zzx(@SafeParcelable.Param(m431id = 1) long j, @SafeParcelable.Param(m431id = 2) long j2, @SafeParcelable.Param(m431id = 3) boolean z, @SafeParcelable.Param(m431id = 4) String str, @SafeParcelable.Param(m431id = 5) String str2, @SafeParcelable.Param(m431id = 6) String str3, @SafeParcelable.Param(m431id = 7) Bundle bundle) {
        this.zzr = j;
        this.zzs = j2;
        this.zzt = z;
        this.zzu = str;
        this.origin = str2;
        this.zzv = str3;
        this.zzw = bundle;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 1, this.zzr);
        SafeParcelWriter.writeLong(parcel, 2, this.zzs);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzt);
        SafeParcelWriter.writeString(parcel, 4, this.zzu, false);
        SafeParcelWriter.writeString(parcel, 5, this.origin, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzv, false);
        SafeParcelWriter.writeBundle(parcel, 7, this.zzw, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
