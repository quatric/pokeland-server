package com.google.firebase.iid;

import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzah {
    private final Messenger zzao;
    private final zzm zzci;

    zzah(IBinder iBinder) throws RemoteException {
        String interfaceDescriptor = iBinder.getInterfaceDescriptor();
        if ("android.os.IMessenger".equals(interfaceDescriptor)) {
            this.zzao = new Messenger(iBinder);
            this.zzci = null;
        } else if ("com.google.android.gms.iid.IMessengerCompat".equals(interfaceDescriptor)) {
            this.zzci = new zzm(iBinder);
            this.zzao = null;
        } else {
            String strValueOf = String.valueOf(interfaceDescriptor);
            Log.w("MessengerIpcClient", strValueOf.length() != 0 ? "Invalid interface descriptor: ".concat(strValueOf) : new String("Invalid interface descriptor: "));
            throw new RemoteException();
        }
    }

    final void send(Message message) throws RemoteException {
        Messenger messenger = this.zzao;
        if (messenger != null) {
            messenger.send(message);
            return;
        }
        zzm zzmVar = this.zzci;
        if (zzmVar == null) {
            throw new IllegalStateException("Both messengers are null");
        }
        zzmVar.send(message);
    }
}
