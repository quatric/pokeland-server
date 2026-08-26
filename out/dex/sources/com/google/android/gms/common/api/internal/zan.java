package com.google.android.gms.common.api.internal;

import android.support.annotation.MainThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zan implements Runnable {
    private final zam zadi;
    final /* synthetic */ zal zadj;

    zan(zal zalVar, zam zamVar) {
        this.zadj = zalVar;
        this.zadi = zamVar;
    }

    @Override // java.lang.Runnable
    @MainThread
    public final void run() {
        if (this.zadj.mStarted) {
            ConnectionResult connectionResult = this.zadi.getConnectionResult();
            if (connectionResult.hasResolution()) {
                this.zadj.mLifecycleFragment.startActivityForResult(GoogleApiActivity.zaa(this.zadj.getActivity(), connectionResult.getResolution(), this.zadi.zar(), false), 1);
                return;
            }
            if (this.zadj.zacc.isUserResolvableError(connectionResult.getErrorCode())) {
                this.zadj.zacc.zaa(this.zadj.getActivity(), this.zadj.mLifecycleFragment, connectionResult.getErrorCode(), 2, this.zadj);
            } else if (connectionResult.getErrorCode() != 18) {
                this.zadj.zaa(connectionResult, this.zadi.zar());
            } else {
                this.zadj.zacc.zaa(this.zadj.getActivity().getApplicationContext(), new zao(this, GoogleApiAvailability.zaa(this.zadj.getActivity(), this.zadj)));
            }
        }
    }
}
