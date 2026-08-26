package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SafeParcelable.Class(creator = "WakeLockEventCreator")
public final class WakeLockEvent extends StatsEvent {
    public static final Parcelable.Creator<WakeLockEvent> CREATOR = new zza();
    private long durationMillis;

    @SafeParcelable.VersionField(m432id = 1)
    private final int versionCode;

    @SafeParcelable.Field(getter = "getTimeMillis", m430id = 2)
    private final long zzfo;

    @SafeParcelable.Field(getter = "getEventType", m430id = 11)
    private int zzfp;

    @SafeParcelable.Field(getter = "getWakeLockName", m430id = 4)
    private final String zzfq;

    @SafeParcelable.Field(getter = "getSecondaryWakeLockName", m430id = 10)
    private final String zzfr;

    @SafeParcelable.Field(getter = "getCodePackage", m430id = 17)
    private final String zzfs;

    @SafeParcelable.Field(getter = "getWakeLockType", m430id = 5)
    private final int zzft;

    @SafeParcelable.Field(getter = "getCallingPackages", m430id = 6)
    private final List<String> zzfu;

    @SafeParcelable.Field(getter = "getEventKey", m430id = 12)
    private final String zzfv;

    @SafeParcelable.Field(getter = "getElapsedRealtime", m430id = 8)
    private final long zzfw;

    @SafeParcelable.Field(getter = "getDeviceState", m430id = 14)
    private int zzfx;

    @SafeParcelable.Field(getter = "getHostPackage", m430id = 13)
    private final String zzfy;

    @SafeParcelable.Field(getter = "getBeginPowerPercentage", m430id = 15)
    private final float zzfz;

    @SafeParcelable.Field(getter = "getTimeout", m430id = 16)
    private final long zzga;

    @SafeParcelable.Field(getter = "getAcquiredWithTimeout", m430id = 18)
    private final boolean zzgb;

    @SafeParcelable.Constructor
    WakeLockEvent(@SafeParcelable.Param(m431id = 1) int i, @SafeParcelable.Param(m431id = 2) long j, @SafeParcelable.Param(m431id = 11) int i2, @SafeParcelable.Param(m431id = 4) String str, @SafeParcelable.Param(m431id = 5) int i3, @SafeParcelable.Param(m431id = 6) List<String> list, @SafeParcelable.Param(m431id = 12) String str2, @SafeParcelable.Param(m431id = 8) long j2, @SafeParcelable.Param(m431id = 14) int i4, @SafeParcelable.Param(m431id = 10) String str3, @SafeParcelable.Param(m431id = 13) String str4, @SafeParcelable.Param(m431id = 15) float f, @SafeParcelable.Param(m431id = 16) long j3, @SafeParcelable.Param(m431id = 17) String str5, @SafeParcelable.Param(m431id = 18) boolean z) {
        this.versionCode = i;
        this.zzfo = j;
        this.zzfp = i2;
        this.zzfq = str;
        this.zzfr = str3;
        this.zzfs = str5;
        this.zzft = i3;
        this.durationMillis = -1L;
        this.zzfu = list;
        this.zzfv = str2;
        this.zzfw = j2;
        this.zzfx = i4;
        this.zzfy = str4;
        this.zzfz = f;
        this.zzga = j3;
        this.zzgb = z;
    }

    public WakeLockEvent(long j, int i, String str, int i2, List<String> list, String str2, long j2, int i3, String str3, String str4, float f, long j3, String str5, boolean z) {
        this(2, j, i, str, i2, list, str2, j2, i3, str3, str4, f, j3, str5, z);
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final int getEventType() {
        return this.zzfp;
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final long getTimeMillis() {
        return this.zzfo;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeLong(parcel, 2, getTimeMillis());
        SafeParcelWriter.writeString(parcel, 4, this.zzfq, false);
        SafeParcelWriter.writeInt(parcel, 5, this.zzft);
        SafeParcelWriter.writeStringList(parcel, 6, this.zzfu, false);
        SafeParcelWriter.writeLong(parcel, 8, this.zzfw);
        SafeParcelWriter.writeString(parcel, 10, this.zzfr, false);
        SafeParcelWriter.writeInt(parcel, 11, getEventType());
        SafeParcelWriter.writeString(parcel, 12, this.zzfv, false);
        SafeParcelWriter.writeString(parcel, 13, this.zzfy, false);
        SafeParcelWriter.writeInt(parcel, 14, this.zzfx);
        SafeParcelWriter.writeFloat(parcel, 15, this.zzfz);
        SafeParcelWriter.writeLong(parcel, 16, this.zzga);
        SafeParcelWriter.writeString(parcel, 17, this.zzfs, false);
        SafeParcelWriter.writeBoolean(parcel, 18, this.zzgb);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final long zzu() {
        return this.durationMillis;
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final String zzv() {
        String str = this.zzfq;
        int i = this.zzft;
        List<String> list = this.zzfu;
        String strJoin = list == null ? "" : TextUtils.join(",", list);
        int i2 = this.zzfx;
        String str2 = this.zzfr;
        if (str2 == null) {
            str2 = "";
        }
        String str3 = this.zzfy;
        if (str3 == null) {
            str3 = "";
        }
        float f = this.zzfz;
        String str4 = this.zzfs;
        String str5 = str4 != null ? str4 : "";
        boolean z = this.zzgb;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 51 + String.valueOf(strJoin).length() + String.valueOf(str2).length() + String.valueOf(str3).length() + String.valueOf(str5).length());
        sb.append("\t");
        sb.append(str);
        sb.append("\t");
        sb.append(i);
        sb.append("\t");
        sb.append(strJoin);
        sb.append("\t");
        sb.append(i2);
        sb.append("\t");
        sb.append(str2);
        sb.append("\t");
        sb.append(str3);
        sb.append("\t");
        sb.append(f);
        sb.append("\t");
        sb.append(str5);
        sb.append("\t");
        sb.append(z);
        return sb.toString();
    }
}
