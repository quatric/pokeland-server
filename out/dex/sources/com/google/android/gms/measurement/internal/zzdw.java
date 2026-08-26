package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class zzdw extends com.google.android.gms.internal.measurement.zza implements zzdx {
    public zzdw() {
        super("com.google.android.gms.measurement.internal.IMeasurementService");
    }

    @Override // com.google.android.gms.internal.measurement.zza
    protected final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        switch (i) {
            case 1:
                zza((zzai) com.google.android.gms.internal.measurement.zzd.zza(parcel, zzai.CREATOR), (zzn) com.google.android.gms.internal.measurement.zzd.zza(parcel, zzn.CREATOR));
                parcel2.writeNoException();
                return true;
            case 2:
                zza((zzjn) com.google.android.gms.internal.measurement.zzd.zza(parcel, zzjn.CREATOR), (zzn) com.google.android.gms.internal.measurement.zzd.zza(parcel, zzn.CREATOR));
                parcel2.writeNoException();
                return true;
            case 3:
            case 8:
            default:
                return false;
            case 4:
                zza((zzn) com.google.android.gms.internal.measurement.zzd.zza(parcel, zzn.CREATOR));
                parcel2.writeNoException();
                return true;
            case 5:
                zza((zzai) com.google.android.gms.internal.measurement.zzd.zza(parcel, zzai.CREATOR), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 6:
                zzb((zzn) com.google.android.gms.internal.measurement.zzd.zza(parcel, zzn.CREATOR));
                parcel2.writeNoException();
                return true;
            case 7:
                List<zzjn> listZza = zza((zzn) com.google.android.gms.internal.measurement.zzd.zza(parcel, zzn.CREATOR), com.google.android.gms.internal.measurement.zzd.zza(parcel));
                parcel2.writeNoException();
                parcel2.writeTypedList(listZza);
                return true;
            case 9:
                byte[] bArrZza = zza((zzai) com.google.android.gms.internal.measurement.zzd.zza(parcel, zzai.CREATOR), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeByteArray(bArrZza);
                return true;
            case 10:
                zza(parcel.readLong(), parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 11:
                String strZzc = zzc((zzn) com.google.android.gms.internal.measurement.zzd.zza(parcel, zzn.CREATOR));
                parcel2.writeNoException();
                parcel2.writeString(strZzc);
                return true;
            case 12:
                zza((zzq) com.google.android.gms.internal.measurement.zzd.zza(parcel, zzq.CREATOR), (zzn) com.google.android.gms.internal.measurement.zzd.zza(parcel, zzn.CREATOR));
                parcel2.writeNoException();
                return true;
            case 13:
                zzb((zzq) com.google.android.gms.internal.measurement.zzd.zza(parcel, zzq.CREATOR));
                parcel2.writeNoException();
                return true;
            case 14:
                List<zzjn> listZza2 = zza(parcel.readString(), parcel.readString(), com.google.android.gms.internal.measurement.zzd.zza(parcel), (zzn) com.google.android.gms.internal.measurement.zzd.zza(parcel, zzn.CREATOR));
                parcel2.writeNoException();
                parcel2.writeTypedList(listZza2);
                return true;
            case 15:
                List<zzjn> listZza3 = zza(parcel.readString(), parcel.readString(), parcel.readString(), com.google.android.gms.internal.measurement.zzd.zza(parcel));
                parcel2.writeNoException();
                parcel2.writeTypedList(listZza3);
                return true;
            case 16:
                List<zzq> listZza4 = zza(parcel.readString(), parcel.readString(), (zzn) com.google.android.gms.internal.measurement.zzd.zza(parcel, zzn.CREATOR));
                parcel2.writeNoException();
                parcel2.writeTypedList(listZza4);
                return true;
            case 17:
                List<zzq> listZzc = zzc(parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeTypedList(listZzc);
                return true;
            case 18:
                zzd((zzn) com.google.android.gms.internal.measurement.zzd.zza(parcel, zzn.CREATOR));
                parcel2.writeNoException();
                return true;
        }
    }
}
