package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SafeParcelable.Class(creator = "UserAttributeParcelCreator")
public final class zzjn extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzjn> CREATOR = new zzjq();

    @SafeParcelable.Field(m430id = 2)
    public final String name;

    @SafeParcelable.Field(m430id = 7)
    public final String origin;

    @SafeParcelable.Field(m430id = 1)
    private final int versionCode;

    @SafeParcelable.Field(m430id = 6)
    public final String zzkr;

    @SafeParcelable.Field(m430id = 3)
    public final long zztr;

    @SafeParcelable.Field(m430id = 4)
    public final Long zzts;

    @SafeParcelable.Field(m430id = 5)
    private final Float zztt;

    @SafeParcelable.Field(m430id = 8)
    public final Double zztu;

    @SafeParcelable.Constructor
    zzjn(@SafeParcelable.Param(m431id = 1) int i, @SafeParcelable.Param(m431id = 2) String str, @SafeParcelable.Param(m431id = 3) long j, @SafeParcelable.Param(m431id = 4) Long l, @SafeParcelable.Param(m431id = 5) Float f, @SafeParcelable.Param(m431id = 6) String str2, @SafeParcelable.Param(m431id = 7) String str3, @SafeParcelable.Param(m431id = 8) Double d) {
        this.versionCode = i;
        this.name = str;
        this.zztr = j;
        this.zzts = l;
        this.zztt = null;
        if (i == 1) {
            this.zztu = f != null ? Double.valueOf(f.doubleValue()) : null;
        } else {
            this.zztu = d;
        }
        this.zzkr = str2;
        this.origin = str3;
    }

    zzjn(zzjp zzjpVar) {
        this(zzjpVar.name, zzjpVar.zztr, zzjpVar.value, zzjpVar.origin);
    }

    zzjn(String str, long j, Object obj, String str2) {
        Preconditions.checkNotEmpty(str);
        this.versionCode = 2;
        this.name = str;
        this.zztr = j;
        this.origin = str2;
        if (obj == null) {
            this.zzts = null;
            this.zztt = null;
            this.zztu = null;
            this.zzkr = null;
            return;
        }
        if (obj instanceof Long) {
            this.zzts = (Long) obj;
            this.zztt = null;
            this.zztu = null;
            this.zzkr = null;
            return;
        }
        if (obj instanceof String) {
            this.zzts = null;
            this.zztt = null;
            this.zztu = null;
            this.zzkr = (String) obj;
            return;
        }
        if (!(obj instanceof Double)) {
            throw new IllegalArgumentException("User attribute given of un-supported type");
        }
        this.zzts = null;
        this.zztt = null;
        this.zztu = (Double) obj;
        this.zzkr = null;
    }

    zzjn(String str, long j, String str2) {
        Preconditions.checkNotEmpty(str);
        this.versionCode = 2;
        this.name = str;
        this.zztr = 0L;
        this.zzts = null;
        this.zztt = null;
        this.zztu = null;
        this.zzkr = null;
        this.origin = null;
    }

    public final Object getValue() {
        Long l = this.zzts;
        if (l != null) {
            return l;
        }
        Double d = this.zztu;
        if (d != null) {
            return d;
        }
        String str = this.zzkr;
        if (str != null) {
            return str;
        }
        return null;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeString(parcel, 2, this.name, false);
        SafeParcelWriter.writeLong(parcel, 3, this.zztr);
        SafeParcelWriter.writeLongObject(parcel, 4, this.zzts, false);
        SafeParcelWriter.writeFloatObject(parcel, 5, null, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzkr, false);
        SafeParcelWriter.writeString(parcel, 7, this.origin, false);
        SafeParcelWriter.writeDoubleObject(parcel, 8, this.zztu, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
