package com.google.android.gms.measurement.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzdz extends com.google.android.gms.internal.measurement.zzb implements zzdx {
    zzdz(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.measurement.internal.IMeasurementService");
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    public final List<zzjn> zza(zzn zznVar, boolean z) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.measurement.zzd.zza(parcelObtainAndWriteInterfaceToken, zznVar);
        com.google.android.gms.internal.measurement.zzd.writeBoolean(parcelObtainAndWriteInterfaceToken, z);
        Parcel parcelTransactAndReadException = transactAndReadException(7, parcelObtainAndWriteInterfaceToken);
        ArrayList arrayListCreateTypedArrayList = parcelTransactAndReadException.createTypedArrayList(zzjn.CREATOR);
        parcelTransactAndReadException.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    public final List<zzq> zza(String str, String str2, zzn zznVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        com.google.android.gms.internal.measurement.zzd.zza(parcelObtainAndWriteInterfaceToken, zznVar);
        Parcel parcelTransactAndReadException = transactAndReadException(16, parcelObtainAndWriteInterfaceToken);
        ArrayList arrayListCreateTypedArrayList = parcelTransactAndReadException.createTypedArrayList(zzq.CREATOR);
        parcelTransactAndReadException.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    public final List<zzjn> zza(String str, String str2, String str3, boolean z) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        parcelObtainAndWriteInterfaceToken.writeString(str3);
        com.google.android.gms.internal.measurement.zzd.writeBoolean(parcelObtainAndWriteInterfaceToken, z);
        Parcel parcelTransactAndReadException = transactAndReadException(15, parcelObtainAndWriteInterfaceToken);
        ArrayList arrayListCreateTypedArrayList = parcelTransactAndReadException.createTypedArrayList(zzjn.CREATOR);
        parcelTransactAndReadException.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    public final List<zzjn> zza(String str, String str2, boolean z, zzn zznVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        com.google.android.gms.internal.measurement.zzd.writeBoolean(parcelObtainAndWriteInterfaceToken, z);
        com.google.android.gms.internal.measurement.zzd.zza(parcelObtainAndWriteInterfaceToken, zznVar);
        Parcel parcelTransactAndReadException = transactAndReadException(14, parcelObtainAndWriteInterfaceToken);
        ArrayList arrayListCreateTypedArrayList = parcelTransactAndReadException.createTypedArrayList(zzjn.CREATOR);
        parcelTransactAndReadException.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    public final void zza(long j, String str, String str2, String str3) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        parcelObtainAndWriteInterfaceToken.writeString(str3);
        zza(10, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    public final void zza(zzai zzaiVar, zzn zznVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.measurement.zzd.zza(parcelObtainAndWriteInterfaceToken, zzaiVar);
        com.google.android.gms.internal.measurement.zzd.zza(parcelObtainAndWriteInterfaceToken, zznVar);
        zza(1, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    public final void zza(zzai zzaiVar, String str, String str2) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.measurement.zzd.zza(parcelObtainAndWriteInterfaceToken, zzaiVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        zza(5, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    public final void zza(zzjn zzjnVar, zzn zznVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.measurement.zzd.zza(parcelObtainAndWriteInterfaceToken, zzjnVar);
        com.google.android.gms.internal.measurement.zzd.zza(parcelObtainAndWriteInterfaceToken, zznVar);
        zza(2, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    public final void zza(zzn zznVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.measurement.zzd.zza(parcelObtainAndWriteInterfaceToken, zznVar);
        zza(4, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    public final void zza(zzq zzqVar, zzn zznVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.measurement.zzd.zza(parcelObtainAndWriteInterfaceToken, zzqVar);
        com.google.android.gms.internal.measurement.zzd.zza(parcelObtainAndWriteInterfaceToken, zznVar);
        zza(12, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    public final byte[] zza(zzai zzaiVar, String str) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.measurement.zzd.zza(parcelObtainAndWriteInterfaceToken, zzaiVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        Parcel parcelTransactAndReadException = transactAndReadException(9, parcelObtainAndWriteInterfaceToken);
        byte[] bArrCreateByteArray = parcelTransactAndReadException.createByteArray();
        parcelTransactAndReadException.recycle();
        return bArrCreateByteArray;
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    public final void zzb(zzn zznVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.measurement.zzd.zza(parcelObtainAndWriteInterfaceToken, zznVar);
        zza(6, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    public final void zzb(zzq zzqVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.measurement.zzd.zza(parcelObtainAndWriteInterfaceToken, zzqVar);
        zza(13, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    public final String zzc(zzn zznVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.measurement.zzd.zza(parcelObtainAndWriteInterfaceToken, zznVar);
        Parcel parcelTransactAndReadException = transactAndReadException(11, parcelObtainAndWriteInterfaceToken);
        String string = parcelTransactAndReadException.readString();
        parcelTransactAndReadException.recycle();
        return string;
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    public final List<zzq> zzc(String str, String str2, String str3) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeString(str2);
        parcelObtainAndWriteInterfaceToken.writeString(str3);
        Parcel parcelTransactAndReadException = transactAndReadException(17, parcelObtainAndWriteInterfaceToken);
        ArrayList arrayListCreateTypedArrayList = parcelTransactAndReadException.createTypedArrayList(zzq.CREATOR);
        parcelTransactAndReadException.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    public final void zzd(zzn zznVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.measurement.zzd.zza(parcelObtainAndWriteInterfaceToken, zznVar);
        zza(18, parcelObtainAndWriteInterfaceToken);
    }
}
