package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SafeParcelable.Class(creator = "SignInButtonConfigCreator")
public class SignInButtonConfig extends AbstractSafeParcelable {
    public static final Parcelable.Creator<SignInButtonConfig> CREATOR = new zao();

    @SafeParcelable.VersionField(m432id = 1)
    private final int zale;

    @SafeParcelable.Field(getter = "getScopes", m430id = 4)
    @Deprecated
    private final Scope[] zanx;

    @SafeParcelable.Field(getter = "getButtonSize", m430id = 2)
    private final int zapc;

    @SafeParcelable.Field(getter = "getColorScheme", m430id = 3)
    private final int zapd;

    @SafeParcelable.Constructor
    SignInButtonConfig(@SafeParcelable.Param(m431id = 1) int i, @SafeParcelable.Param(m431id = 2) int i2, @SafeParcelable.Param(m431id = 3) int i3, @SafeParcelable.Param(m431id = 4) Scope[] scopeArr) {
        this.zale = i;
        this.zapc = i2;
        this.zapd = i3;
        this.zanx = scopeArr;
    }

    public SignInButtonConfig(int i, int i2, Scope[] scopeArr) {
        this(1, i, i2, null);
    }

    public int getButtonSize() {
        return this.zapc;
    }

    public int getColorScheme() {
        return this.zapd;
    }

    @Deprecated
    public Scope[] getScopes() {
        return this.zanx;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zale);
        SafeParcelWriter.writeInt(parcel, 2, getButtonSize());
        SafeParcelWriter.writeInt(parcel, 3, getColorScheme());
        SafeParcelWriter.writeTypedArray(parcel, 4, getScopes(), i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
