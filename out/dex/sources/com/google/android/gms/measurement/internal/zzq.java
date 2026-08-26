package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SafeParcelable.Class(creator = "ConditionalUserPropertyParcelCreator")
public final class zzq extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzq> CREATOR = new zzt();

    @SafeParcelable.Field(m430id = 6)
    public boolean active;

    @SafeParcelable.Field(m430id = 5)
    public long creationTimestamp;

    @SafeParcelable.Field(m430id = 3)
    public String origin;

    @SafeParcelable.Field(m430id = 2)
    public String packageName;

    @SafeParcelable.Field(m430id = 11)
    public long timeToLive;

    @SafeParcelable.Field(m430id = 7)
    public String triggerEventName;

    @SafeParcelable.Field(m430id = 9)
    public long triggerTimeout;

    @SafeParcelable.Field(m430id = 4)
    public zzjn zzdw;

    @SafeParcelable.Field(m430id = 8)
    public zzai zzdx;

    @SafeParcelable.Field(m430id = 10)
    public zzai zzdy;

    @SafeParcelable.Field(m430id = 12)
    public zzai zzdz;

    zzq(zzq zzqVar) {
        Preconditions.checkNotNull(zzqVar);
        this.packageName = zzqVar.packageName;
        this.origin = zzqVar.origin;
        this.zzdw = zzqVar.zzdw;
        this.creationTimestamp = zzqVar.creationTimestamp;
        this.active = zzqVar.active;
        this.triggerEventName = zzqVar.triggerEventName;
        this.zzdx = zzqVar.zzdx;
        this.triggerTimeout = zzqVar.triggerTimeout;
        this.zzdy = zzqVar.zzdy;
        this.timeToLive = zzqVar.timeToLive;
        this.zzdz = zzqVar.zzdz;
    }

    @SafeParcelable.Constructor
    zzq(@SafeParcelable.Param(m431id = 2) String str, @SafeParcelable.Param(m431id = 3) String str2, @SafeParcelable.Param(m431id = 4) zzjn zzjnVar, @SafeParcelable.Param(m431id = 5) long j, @SafeParcelable.Param(m431id = 6) boolean z, @SafeParcelable.Param(m431id = 7) String str3, @SafeParcelable.Param(m431id = 8) zzai zzaiVar, @SafeParcelable.Param(m431id = 9) long j2, @SafeParcelable.Param(m431id = 10) zzai zzaiVar2, @SafeParcelable.Param(m431id = 11) long j3, @SafeParcelable.Param(m431id = 12) zzai zzaiVar3) {
        this.packageName = str;
        this.origin = str2;
        this.zzdw = zzjnVar;
        this.creationTimestamp = j;
        this.active = z;
        this.triggerEventName = str3;
        this.zzdx = zzaiVar;
        this.triggerTimeout = j2;
        this.zzdy = zzaiVar2;
        this.timeToLive = j3;
        this.zzdz = zzaiVar3;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.packageName, false);
        SafeParcelWriter.writeString(parcel, 3, this.origin, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzdw, i, false);
        SafeParcelWriter.writeLong(parcel, 5, this.creationTimestamp);
        SafeParcelWriter.writeBoolean(parcel, 6, this.active);
        SafeParcelWriter.writeString(parcel, 7, this.triggerEventName, false);
        SafeParcelWriter.writeParcelable(parcel, 8, this.zzdx, i, false);
        SafeParcelWriter.writeLong(parcel, 9, this.triggerTimeout);
        SafeParcelWriter.writeParcelable(parcel, 10, this.zzdy, i, false);
        SafeParcelWriter.writeLong(parcel, 11, this.timeToLive);
        SafeParcelWriter.writeParcelable(parcel, 12, this.zzdz, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
