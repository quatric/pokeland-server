package com.google.android.gms.measurement.internal;

import android.os.IInterface;
import android.os.RemoteException;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface zzdx extends IInterface {
    List<zzjn> zza(zzn zznVar, boolean z) throws RemoteException;

    List<zzq> zza(String str, String str2, zzn zznVar) throws RemoteException;

    List<zzjn> zza(String str, String str2, String str3, boolean z) throws RemoteException;

    List<zzjn> zza(String str, String str2, boolean z, zzn zznVar) throws RemoteException;

    void zza(long j, String str, String str2, String str3) throws RemoteException;

    void zza(zzai zzaiVar, zzn zznVar) throws RemoteException;

    void zza(zzai zzaiVar, String str, String str2) throws RemoteException;

    void zza(zzjn zzjnVar, zzn zznVar) throws RemoteException;

    void zza(zzn zznVar) throws RemoteException;

    void zza(zzq zzqVar, zzn zznVar) throws RemoteException;

    byte[] zza(zzai zzaiVar, String str) throws RemoteException;

    void zzb(zzn zznVar) throws RemoteException;

    void zzb(zzq zzqVar) throws RemoteException;

    String zzc(zzn zznVar) throws RemoteException;

    List<zzq> zzc(String str, String str2, String str3) throws RemoteException;

    void zzd(zzn zznVar) throws RemoteException;
}
