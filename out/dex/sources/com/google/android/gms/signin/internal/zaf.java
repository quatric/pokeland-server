package com.google.android.gms.signin.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.IAccountAccessor;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface zaf extends IInterface {
    void zaa(IAccountAccessor iAccountAccessor, int i, boolean z) throws RemoteException;

    void zaa(zah zahVar, zad zadVar) throws RemoteException;

    void zam(int i) throws RemoteException;
}
