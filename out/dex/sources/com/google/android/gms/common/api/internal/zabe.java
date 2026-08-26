package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.signin.SignInOptions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zabe implements zabs, zar {
    private final Context mContext;
    private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> zacd;
    final zaaw zaed;
    private final Lock zaen;
    private final ClientSettings zaes;
    private final Map<Api<?>, Boolean> zaev;
    private final GoogleApiAvailabilityLight zaex;
    final Map<Api.AnyClientKey<?>, Api.Client> zagy;
    private final Condition zahm;
    private final zabg zahn;
    private volatile zabd zahp;
    int zahr;
    final zabt zahs;
    final Map<Api.AnyClientKey<?>, ConnectionResult> zaho = new HashMap();
    private ConnectionResult zahq = null;

    public zabe(Context context, zaaw zaawVar, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<Api.AnyClientKey<?>, Api.Client> map, ClientSettings clientSettings, Map<Api<?>, Boolean> map2, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> abstractClientBuilder, ArrayList<zaq> arrayList, zabt zabtVar) {
        this.mContext = context;
        this.zaen = lock;
        this.zaex = googleApiAvailabilityLight;
        this.zagy = map;
        this.zaes = clientSettings;
        this.zaev = map2;
        this.zacd = abstractClientBuilder;
        this.zaed = zaawVar;
        this.zahs = zabtVar;
        ArrayList<zaq> arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            zaq zaqVar = arrayList2.get(i);
            i++;
            zaqVar.zaa(this);
        }
        this.zahn = new zabg(this, looper);
        this.zahm = lock.newCondition();
        this.zahp = new zaav(this);
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final ConnectionResult blockingConnect() {
        connect();
        while (isConnecting()) {
            try {
                this.zahm.await();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        if (isConnected()) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        ConnectionResult connectionResult = this.zahq;
        return connectionResult != null ? connectionResult : new ConnectionResult(13, null);
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final ConnectionResult blockingConnect(long j, TimeUnit timeUnit) {
        connect();
        long nanos = timeUnit.toNanos(j);
        while (isConnecting()) {
            if (nanos <= 0) {
                disconnect();
                return new ConnectionResult(14, null);
            }
            try {
                nanos = this.zahm.awaitNanos(nanos);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
            Thread.currentThread().interrupt();
            return new ConnectionResult(15, null);
        }
        if (isConnected()) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        ConnectionResult connectionResult = this.zahq;
        return connectionResult != null ? connectionResult : new ConnectionResult(13, null);
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final void connect() {
        this.zahp.connect();
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final void disconnect() {
        if (this.zahp.disconnect()) {
            this.zaho.clear();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String strConcat = String.valueOf(str).concat("  ");
        printWriter.append((CharSequence) str).append("mState=").println(this.zahp);
        for (Api<?> api : this.zaev.keySet()) {
            printWriter.append((CharSequence) str).append((CharSequence) api.getName()).println(":");
            this.zagy.get(api.getClientKey()).dump(strConcat, fileDescriptor, printWriter, strArr);
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(@NonNull T t) {
        t.zau();
        return (T) this.zahp.enqueue(t);
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t) {
        t.zau();
        return (T) this.zahp.execute(t);
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        Api.AnyClientKey<?> clientKey = api.getClientKey();
        if (!this.zagy.containsKey(clientKey)) {
            return null;
        }
        if (this.zagy.get(clientKey).isConnected()) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        if (this.zaho.containsKey(clientKey)) {
            return this.zaho.get(clientKey);
        }
        return null;
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final boolean isConnected() {
        return this.zahp instanceof zaah;
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final boolean isConnecting() {
        return this.zahp instanceof zaak;
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        return false;
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final void maybeSignOut() {
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
    public final void onConnected(@Nullable Bundle bundle) {
        this.zaen.lock();
        try {
            this.zahp.onConnected(bundle);
        } finally {
            this.zaen.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
    public final void onConnectionSuspended(int i) {
        this.zaen.lock();
        try {
            this.zahp.onConnectionSuspended(i);
        } finally {
            this.zaen.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zar
    public final void zaa(@NonNull ConnectionResult connectionResult, @NonNull Api<?> api, boolean z) {
        this.zaen.lock();
        try {
            this.zahp.zaa(connectionResult, api, z);
        } finally {
            this.zaen.unlock();
        }
    }

    final void zaa(zabf zabfVar) {
        this.zahn.sendMessage(this.zahn.obtainMessage(1, zabfVar));
    }

    final void zaaz() {
        this.zaen.lock();
        try {
            this.zahp = new zaak(this, this.zaes, this.zaev, this.zaex, this.zacd, this.zaen, this.mContext);
            this.zahp.begin();
            this.zahm.signalAll();
        } finally {
            this.zaen.unlock();
        }
    }

    final void zab(RuntimeException runtimeException) {
        this.zahn.sendMessage(this.zahn.obtainMessage(2, runtimeException));
    }

    final void zaba() {
        this.zaen.lock();
        try {
            this.zaed.zaaw();
            this.zahp = new zaah(this);
            this.zahp.begin();
            this.zahm.signalAll();
        } finally {
            this.zaen.unlock();
        }
    }

    final void zaf(ConnectionResult connectionResult) {
        this.zaen.lock();
        try {
            this.zahq = connectionResult;
            this.zahp = new zaav(this);
            this.zahp.begin();
            this.zahm.signalAll();
        } finally {
            this.zaen.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final void zaw() {
        if (isConnected()) {
            ((zaah) this.zahp).zaam();
        }
    }
}
