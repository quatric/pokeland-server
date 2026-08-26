package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SafeParcelable.Class(creator = "SignInResponseCreator")
public final class zaj extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zaj> CREATOR = new zak();

    @SafeParcelable.Field(getter = "getConnectionResult", m430id = 2)
    private final ConnectionResult zadh;

    @SafeParcelable.VersionField(m432id = 1)
    private final int zale;

    @SafeParcelable.Field(getter = "getResolveAccountResponse", m430id = 3)
    private final ResolveAccountResponse zasb;

    public zaj(int i) {
        this(new ConnectionResult(8, null), null);
    }

    @SafeParcelable.Constructor
    zaj(@SafeParcelable.Param(m431id = 1) int i, @SafeParcelable.Param(m431id = 2) ConnectionResult connectionResult, @SafeParcelable.Param(m431id = 3) ResolveAccountResponse resolveAccountResponse) {
        this.zale = i;
        this.zadh = connectionResult;
        this.zasb = resolveAccountResponse;
    }

    private zaj(ConnectionResult connectionResult, ResolveAccountResponse resolveAccountResponse) {
        this(1, connectionResult, null);
    }

    public final ConnectionResult getConnectionResult() {
        return this.zadh;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zale);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zadh, i, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zasb, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final ResolveAccountResponse zacw() {
        return this.zasb;
    }
}
