package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SafeParcelable.Class(creator = "AppMetadataCreator")
@SafeParcelable.Reserved({1, 20})
public final class zzn extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzn> CREATOR = new zzm();

    @SafeParcelable.Field(m430id = 2)
    public final String packageName;

    @SafeParcelable.Field(m430id = 3)
    public final String zzcg;

    @SafeParcelable.Field(m430id = 12)
    public final String zzci;

    @SafeParcelable.Field(m430id = 4)
    public final String zzcm;

    @SafeParcelable.Field(defaultValueUnchecked = "Integer.MIN_VALUE", m430id = 11)
    public final long zzcn;

    @SafeParcelable.Field(m430id = 5)
    public final String zzco;

    @SafeParcelable.Field(m430id = 7)
    public final long zzcp;

    @SafeParcelable.Field(defaultValue = "true", m430id = 9)
    public final boolean zzcq;

    @SafeParcelable.Field(m430id = 13)
    public final long zzcr;

    @SafeParcelable.Field(defaultValue = "true", m430id = 16)
    public final boolean zzcs;

    @SafeParcelable.Field(defaultValue = "true", m430id = 17)
    public final boolean zzct;

    @SafeParcelable.Field(m430id = 19)
    public final String zzcu;

    @SafeParcelable.Field(m430id = MotionEventCompat.AXIS_WHEEL)
    public final Boolean zzcv;

    @SafeParcelable.Field(m430id = MotionEventCompat.AXIS_BRAKE)
    public final List<String> zzcw;

    @SafeParcelable.Field(m430id = 8)
    public final String zzdp;

    @SafeParcelable.Field(m430id = 10)
    public final boolean zzdq;

    @SafeParcelable.Field(m430id = 14)
    public final long zzdr;

    @SafeParcelable.Field(m430id = 15)
    public final int zzds;

    @SafeParcelable.Field(m430id = 18)
    public final boolean zzdt;

    @SafeParcelable.Field(m430id = 6)
    public final long zzr;

    @SafeParcelable.Field(m430id = MotionEventCompat.AXIS_GAS)
    public final long zzs;

    zzn(String str, String str2, String str3, long j, String str4, long j2, long j3, String str5, boolean z, boolean z2, String str6, long j4, long j5, int i, boolean z3, boolean z4, boolean z5, String str7, Boolean bool, long j6, List<String> list) {
        Preconditions.checkNotEmpty(str);
        this.packageName = str;
        this.zzcg = TextUtils.isEmpty(str2) ? null : str2;
        this.zzcm = str3;
        this.zzcn = j;
        this.zzco = str4;
        this.zzr = j2;
        this.zzcp = j3;
        this.zzdp = str5;
        this.zzcq = z;
        this.zzdq = z2;
        this.zzci = str6;
        this.zzcr = j4;
        this.zzdr = j5;
        this.zzds = i;
        this.zzcs = z3;
        this.zzct = z4;
        this.zzdt = z5;
        this.zzcu = str7;
        this.zzcv = bool;
        this.zzs = j6;
        this.zzcw = list;
    }

    @SafeParcelable.Constructor
    zzn(@SafeParcelable.Param(m431id = 2) String str, @SafeParcelable.Param(m431id = 3) String str2, @SafeParcelable.Param(m431id = 4) String str3, @SafeParcelable.Param(m431id = 5) String str4, @SafeParcelable.Param(m431id = 6) long j, @SafeParcelable.Param(m431id = 7) long j2, @SafeParcelable.Param(m431id = 8) String str5, @SafeParcelable.Param(m431id = 9) boolean z, @SafeParcelable.Param(m431id = 10) boolean z2, @SafeParcelable.Param(m431id = 11) long j3, @SafeParcelable.Param(m431id = 12) String str6, @SafeParcelable.Param(m431id = 13) long j4, @SafeParcelable.Param(m431id = 14) long j5, @SafeParcelable.Param(m431id = 15) int i, @SafeParcelable.Param(m431id = 16) boolean z3, @SafeParcelable.Param(m431id = 17) boolean z4, @SafeParcelable.Param(m431id = 18) boolean z5, @SafeParcelable.Param(m431id = 19) String str7, @SafeParcelable.Param(m431id = MotionEventCompat.AXIS_WHEEL) Boolean bool, @SafeParcelable.Param(m431id = MotionEventCompat.AXIS_GAS) long j6, @SafeParcelable.Param(m431id = MotionEventCompat.AXIS_BRAKE) List<String> list) {
        this.packageName = str;
        this.zzcg = str2;
        this.zzcm = str3;
        this.zzcn = j3;
        this.zzco = str4;
        this.zzr = j;
        this.zzcp = j2;
        this.zzdp = str5;
        this.zzcq = z;
        this.zzdq = z2;
        this.zzci = str6;
        this.zzcr = j4;
        this.zzdr = j5;
        this.zzds = i;
        this.zzcs = z3;
        this.zzct = z4;
        this.zzdt = z5;
        this.zzcu = str7;
        this.zzcv = bool;
        this.zzs = j6;
        this.zzcw = list;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.packageName, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzcg, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzcm, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzco, false);
        SafeParcelWriter.writeLong(parcel, 6, this.zzr);
        SafeParcelWriter.writeLong(parcel, 7, this.zzcp);
        SafeParcelWriter.writeString(parcel, 8, this.zzdp, false);
        SafeParcelWriter.writeBoolean(parcel, 9, this.zzcq);
        SafeParcelWriter.writeBoolean(parcel, 10, this.zzdq);
        SafeParcelWriter.writeLong(parcel, 11, this.zzcn);
        SafeParcelWriter.writeString(parcel, 12, this.zzci, false);
        SafeParcelWriter.writeLong(parcel, 13, this.zzcr);
        SafeParcelWriter.writeLong(parcel, 14, this.zzdr);
        SafeParcelWriter.writeInt(parcel, 15, this.zzds);
        SafeParcelWriter.writeBoolean(parcel, 16, this.zzcs);
        SafeParcelWriter.writeBoolean(parcel, 17, this.zzct);
        SafeParcelWriter.writeBoolean(parcel, 18, this.zzdt);
        SafeParcelWriter.writeString(parcel, 19, this.zzcu, false);
        SafeParcelWriter.writeBooleanObject(parcel, 21, this.zzcv, false);
        SafeParcelWriter.writeLong(parcel, 22, this.zzs);
        SafeParcelWriter.writeStringList(parcel, 23, this.zzcw, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
