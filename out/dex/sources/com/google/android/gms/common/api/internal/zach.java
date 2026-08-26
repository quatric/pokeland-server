package com.google.android.gms.common.api.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.IAccountAccessor;
import java.util.Set;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@WorkerThread
public interface zach {
    void zaa(IAccountAccessor iAccountAccessor, Set<Scope> set);

    void zag(ConnectionResult connectionResult);
}
