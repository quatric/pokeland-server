package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
@SafeParcelable.Class(creator = "FeatureCreator")
public class Feature extends AbstractSafeParcelable {
    public static final Parcelable.Creator<Feature> CREATOR = new zzb();

    @SafeParcelable.Field(getter = "getName", m430id = 1)
    private final String name;

    @SafeParcelable.Field(getter = "getOldVersion", m430id = 2)
    @Deprecated
    private final int zzk;

    @SafeParcelable.Field(defaultValue = "-1", getter = "getVersion", m430id = 3)
    private final long zzl;

    @SafeParcelable.Constructor
    public Feature(@SafeParcelable.Param(m431id = 1) String str, @SafeParcelable.Param(m431id = 2) int i, @SafeParcelable.Param(m431id = 3) long j) {
        this.name = str;
        this.zzk = i;
        this.zzl = j;
    }

    @KeepForSdk
    public Feature(String str, long j) {
        this.name = str;
        this.zzl = j;
        this.zzk = -1;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Feature) {
            Feature feature = (Feature) obj;
            if (((getName() != null && getName().equals(feature.getName())) || (getName() == null && feature.getName() == null)) && getVersion() == feature.getVersion()) {
                return true;
            }
        }
        return false;
    }

    @KeepForSdk
    public String getName() {
        return this.name;
    }

    @KeepForSdk
    public long getVersion() {
        long j = this.zzl;
        return j == -1 ? this.zzk : j;
    }

    public int hashCode() {
        return Objects.hashCode(getName(), Long.valueOf(getVersion()));
    }

    public String toString() {
        return Objects.toStringHelper(this).add(AppMeasurementSdk.ConditionalUserProperty.NAME, getName()).add("version", Long.valueOf(getVersion())).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getName(), false);
        SafeParcelWriter.writeInt(parcel, 2, this.zzk);
        SafeParcelWriter.writeLong(parcel, 3, getVersion());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
