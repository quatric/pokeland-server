package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.annotation.KeepName;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
@KeepName
public final class BinderWrapper implements Parcelable {
    public static final Parcelable.Creator<BinderWrapper> CREATOR = new zza();
    private IBinder zzcz;

    public BinderWrapper() {
        this.zzcz = null;
    }

    @KeepForSdk
    public BinderWrapper(IBinder iBinder) {
        this.zzcz = null;
        this.zzcz = iBinder;
    }

    private BinderWrapper(Parcel parcel) {
        this.zzcz = null;
        this.zzcz = parcel.readStrongBinder();
    }

    /* synthetic */ BinderWrapper(Parcel parcel, zza zzaVar) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.zzcz);
    }
}
