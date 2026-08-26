package com.google.firebase.iid;

import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzv implements zzw {
    private final IBinder zzbt;

    zzv(IBinder iBinder) {
        this.zzbt = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.zzbt;
    }

    @Override // com.google.firebase.iid.zzw
    public final void send(Message message) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.writeInterfaceToken("com.google.android.gms.iid.IMessengerCompat");
        parcelObtain.writeInt(1);
        message.writeToParcel(parcelObtain, 0);
        try {
            this.zzbt.transact(1, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }
}
