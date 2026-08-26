package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Preconditions;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class zaj extends zal {
    private final SparseArray<zaa> zacv;

    private class zaa implements GoogleApiClient.OnConnectionFailedListener {
        public final int zacw;
        public final GoogleApiClient zacx;
        public final GoogleApiClient.OnConnectionFailedListener zacy;

        public zaa(int i, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.zacw = i;
            this.zacx = googleApiClient;
            this.zacy = onConnectionFailedListener;
            googleApiClient.registerConnectionFailedListener(this);
        }

        @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
        public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            String strValueOf = String.valueOf(connectionResult);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 27);
            sb.append("beginFailureResolution for ");
            sb.append(strValueOf);
            Log.d("AutoManageHelper", sb.toString());
            zaj.this.zab(connectionResult, this.zacw);
        }
    }

    private zaj(LifecycleFragment lifecycleFragment) {
        super(lifecycleFragment);
        this.zacv = new SparseArray<>();
        this.mLifecycleFragment.addCallback("AutoManageHelper", this);
    }

    public static zaj zaa(LifecycleActivity lifecycleActivity) {
        LifecycleFragment fragment = getFragment(lifecycleActivity);
        zaj zajVar = (zaj) fragment.getCallbackOrNull("AutoManageHelper", zaj.class);
        return zajVar != null ? zajVar : new zaj(fragment);
    }

    @Nullable
    private final zaa zab(int i) {
        if (this.zacv.size() <= i) {
            return null;
        }
        SparseArray<zaa> sparseArray = this.zacv;
        return sparseArray.get(sparseArray.keyAt(i));
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleCallback
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (int i = 0; i < this.zacv.size(); i++) {
            zaa zaaVarZab = zab(i);
            if (zaaVarZab != null) {
                printWriter.append((CharSequence) str).append("GoogleApiClient #").print(zaaVarZab.zacw);
                printWriter.println(":");
                zaaVarZab.zacx.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
            }
        }
    }

    @Override // com.google.android.gms.common.api.internal.zal, com.google.android.gms.common.api.internal.LifecycleCallback
    public void onStart() {
        super.onStart();
        boolean z = this.mStarted;
        String strValueOf = String.valueOf(this.zacv);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 14);
        sb.append("onStart ");
        sb.append(z);
        sb.append(" ");
        sb.append(strValueOf);
        Log.d("AutoManageHelper", sb.toString());
        if (this.zade.get() == null) {
            for (int i = 0; i < this.zacv.size(); i++) {
                zaa zaaVarZab = zab(i);
                if (zaaVarZab != null) {
                    zaaVarZab.zacx.connect();
                }
            }
        }
    }

    @Override // com.google.android.gms.common.api.internal.zal, com.google.android.gms.common.api.internal.LifecycleCallback
    public void onStop() {
        super.onStop();
        for (int i = 0; i < this.zacv.size(); i++) {
            zaa zaaVarZab = zab(i);
            if (zaaVarZab != null) {
                zaaVarZab.zacx.disconnect();
            }
        }
    }

    public final void zaa(int i) {
        zaa zaaVar = this.zacv.get(i);
        this.zacv.remove(i);
        if (zaaVar != null) {
            zaaVar.zacx.unregisterConnectionFailedListener(zaaVar);
            zaaVar.zacx.disconnect();
        }
    }

    public final void zaa(int i, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        Preconditions.checkNotNull(googleApiClient, "GoogleApiClient instance cannot be null");
        boolean z = this.zacv.indexOfKey(i) < 0;
        StringBuilder sb = new StringBuilder(54);
        sb.append("Already managing a GoogleApiClient with id ");
        sb.append(i);
        Preconditions.checkState(z, sb.toString());
        zam zamVar = this.zade.get();
        boolean z2 = this.mStarted;
        String strValueOf = String.valueOf(zamVar);
        StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf).length() + 49);
        sb2.append("starting AutoManage for client ");
        sb2.append(i);
        sb2.append(" ");
        sb2.append(z2);
        sb2.append(" ");
        sb2.append(strValueOf);
        Log.d("AutoManageHelper", sb2.toString());
        this.zacv.put(i, new zaa(i, googleApiClient, onConnectionFailedListener));
        if (this.mStarted && zamVar == null) {
            String strValueOf2 = String.valueOf(googleApiClient);
            StringBuilder sb3 = new StringBuilder(String.valueOf(strValueOf2).length() + 11);
            sb3.append("connecting ");
            sb3.append(strValueOf2);
            Log.d("AutoManageHelper", sb3.toString());
            googleApiClient.connect();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zal
    protected final void zaa(ConnectionResult connectionResult, int i) {
        Log.w("AutoManageHelper", "Unresolved error while connecting client. Stopping auto-manage.");
        if (i < 0) {
            Log.wtf("AutoManageHelper", "AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", new Exception());
            return;
        }
        zaa zaaVar = this.zacv.get(i);
        if (zaaVar != null) {
            zaa(i);
            GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = zaaVar.zacy;
            if (onConnectionFailedListener != null) {
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
        }
    }

    @Override // com.google.android.gms.common.api.internal.zal
    protected final void zao() {
        for (int i = 0; i < this.zacv.size(); i++) {
            zaa zaaVarZab = zab(i);
            if (zaaVarZab != null) {
                zaaVarZab.zacx.connect();
            }
        }
    }
}
