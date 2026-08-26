package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.util.concurrent.HandlerExecutor;
import com.google.android.gms.signin.SignInOptions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zax implements zabs {
    private final Looper zabj;
    private final GoogleApiManager zabm;
    private final Lock zaen;
    private final ClientSettings zaes;
    private final Map<Api<?>, Boolean> zaev;
    private final zaaw zaew;
    private final GoogleApiAvailabilityLight zaex;
    private final Condition zaey;
    private final boolean zaez;
    private final boolean zafa;

    @GuardedBy("mLock")
    private boolean zafc;

    @GuardedBy("mLock")
    private Map<zai<?>, ConnectionResult> zafd;

    @GuardedBy("mLock")
    private Map<zai<?>, ConnectionResult> zafe;

    @GuardedBy("mLock")
    private zaaa zaff;

    @GuardedBy("mLock")
    private ConnectionResult zafg;
    private final Map<Api.AnyClientKey<?>, zaw<?>> zaet = new HashMap();
    private final Map<Api.AnyClientKey<?>, zaw<?>> zaeu = new HashMap();
    private final Queue<BaseImplementation.ApiMethodImpl<?, ?>> zafb = new LinkedList();

    public zax(Context context, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<Api.AnyClientKey<?>, Api.Client> map, ClientSettings clientSettings, Map<Api<?>, Boolean> map2, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> abstractClientBuilder, ArrayList<zaq> arrayList, zaaw zaawVar, boolean z) {
        boolean z2;
        boolean z3;
        this.zaen = lock;
        this.zabj = looper;
        this.zaey = lock.newCondition();
        this.zaex = googleApiAvailabilityLight;
        this.zaew = zaawVar;
        this.zaev = map2;
        this.zaes = clientSettings;
        this.zaez = z;
        HashMap map3 = new HashMap();
        for (Api<?> api : map2.keySet()) {
            map3.put(api.getClientKey(), api);
        }
        HashMap map4 = new HashMap();
        ArrayList<zaq> arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            zaq zaqVar = arrayList2.get(i);
            i++;
            zaq zaqVar2 = zaqVar;
            map4.put(zaqVar2.mApi, zaqVar2);
        }
        boolean z4 = false;
        boolean z5 = true;
        boolean z6 = false;
        for (Map.Entry<Api.AnyClientKey<?>, Api.Client> entry : map.entrySet()) {
            Api api2 = (Api) map3.get(entry.getKey());
            Api.Client value = entry.getValue();
            if (value.requiresGooglePlayServices()) {
                z3 = !this.zaev.get(api2).booleanValue() ? true : z6;
                z2 = true;
            } else {
                z2 = z4;
                z3 = z6;
                z5 = false;
            }
            zaw<?> zawVar = new zaw<>(context, api2, looper, value, (zaq) map4.get(api2), clientSettings, abstractClientBuilder);
            this.zaet.put(entry.getKey(), zawVar);
            if (value.requiresSignIn()) {
                this.zaeu.put(entry.getKey(), zawVar);
            }
            z6 = z3;
            z5 = z5;
            z4 = z2;
        }
        this.zafa = (!z4 || z5 || z6) ? false : true;
        this.zabm = GoogleApiManager.zabc();
    }

    @Nullable
    private final ConnectionResult zaa(@NonNull Api.AnyClientKey<?> anyClientKey) {
        this.zaen.lock();
        try {
            zaw<?> zawVar = this.zaet.get(anyClientKey);
            if (this.zafd == null || zawVar == null) {
                return null;
            }
            return this.zafd.get(zawVar.zak());
        } finally {
            this.zaen.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean zaa(zaw<?> zawVar, ConnectionResult connectionResult) {
        return !connectionResult.isSuccess() && !connectionResult.hasResolution() && this.zaev.get(zawVar.getApi()).booleanValue() && zawVar.zaab().requiresGooglePlayServices() && this.zaex.isUserResolvableError(connectionResult.getErrorCode());
    }

    static /* synthetic */ boolean zaa(zax zaxVar, boolean z) {
        zaxVar.zafc = false;
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private final boolean zaac() {
        this.zaen.lock();
        try {
            if (this.zafc && this.zaez) {
                Iterator<Api.AnyClientKey<?>> it = this.zaeu.keySet().iterator();
                while (it.hasNext()) {
                    ConnectionResult connectionResultZaa = zaa(it.next());
                    if (connectionResultZaa == null || !connectionResultZaa.isSuccess()) {
                        this.zaen.unlock();
                        return false;
                    }
                }
                this.zaen.unlock();
                return true;
            }
            this.zaen.unlock();
            return false;
        } catch (Throwable th) {
            this.zaen.unlock();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zaad() {
        ClientSettings clientSettings = this.zaes;
        if (clientSettings == null) {
            this.zaew.zagz = Collections.emptySet();
            return;
        }
        HashSet hashSet = new HashSet(clientSettings.getRequiredScopes());
        Map<Api<?>, ClientSettings.OptionalApiSettings> optionalApiSettings = this.zaes.getOptionalApiSettings();
        for (Api<?> api : optionalApiSettings.keySet()) {
            ConnectionResult connectionResult = getConnectionResult(api);
            if (connectionResult != null && connectionResult.isSuccess()) {
                hashSet.addAll(optionalApiSettings.get(api).mScopes);
            }
        }
        this.zaew.zagz = hashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zaae() {
        while (!this.zafb.isEmpty()) {
            execute(this.zafb.remove());
        }
        this.zaew.zab((Bundle) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    @Nullable
    public final ConnectionResult zaaf() {
        ConnectionResult connectionResult = null;
        ConnectionResult connectionResult2 = null;
        int i = 0;
        int i2 = 0;
        for (zaw<?> zawVar : this.zaet.values()) {
            Api<?> api = zawVar.getApi();
            ConnectionResult connectionResult3 = this.zafd.get(zawVar.zak());
            if (!connectionResult3.isSuccess() && (!this.zaev.get(api).booleanValue() || connectionResult3.hasResolution() || this.zaex.isUserResolvableError(connectionResult3.getErrorCode()))) {
                if (connectionResult3.getErrorCode() == 4 && this.zaez) {
                    int priority = api.zah().getPriority();
                    if (connectionResult2 == null || i2 > priority) {
                        connectionResult2 = connectionResult3;
                        i2 = priority;
                    }
                } else {
                    int priority2 = api.zah().getPriority();
                    if (connectionResult == null || i > priority2) {
                        connectionResult = connectionResult3;
                        i = priority2;
                    }
                }
            }
        }
        return (connectionResult == null || connectionResult2 == null || i <= i2) ? connectionResult : connectionResult2;
    }

    private final <T extends BaseImplementation.ApiMethodImpl<? extends Result, ? extends Api.AnyClient>> boolean zab(@NonNull T t) {
        Api.AnyClientKey<?> clientKey = t.getClientKey();
        ConnectionResult connectionResultZaa = zaa(clientKey);
        if (connectionResultZaa == null || connectionResultZaa.getErrorCode() != 4) {
            return false;
        }
        t.setFailedResult(new Status(4, null, this.zabm.zaa(this.zaet.get(clientKey).zak(), System.identityHashCode(this.zaew))));
        return true;
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final ConnectionResult blockingConnect() {
        connect();
        while (isConnecting()) {
            try {
                this.zaey.await();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        if (isConnected()) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        ConnectionResult connectionResult = this.zafg;
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
                nanos = this.zaey.awaitNanos(nanos);
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
        ConnectionResult connectionResult = this.zafg;
        return connectionResult != null ? connectionResult : new ConnectionResult(13, null);
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final void connect() {
        this.zaen.lock();
        try {
            if (this.zafc) {
                return;
            }
            this.zafc = true;
            this.zafd = null;
            this.zafe = null;
            this.zaff = null;
            this.zafg = null;
            this.zabm.zao();
            this.zabm.zaa(this.zaet.values()).addOnCompleteListener(new HandlerExecutor(this.zabj), new zaz(this));
        } finally {
            this.zaen.unlock();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.common.api.internal.zabs
    public final void disconnect() {
        this.zaen.lock();
        try {
            this.zafc = false;
            this.zafd = null;
            this.zafe = null;
            if (this.zaff != null) {
                this.zaff.cancel();
                this.zaff = null;
            }
            this.zafg = null;
            while (!this.zafb.isEmpty()) {
                BaseImplementation.ApiMethodImpl<?, ?> apiMethodImplRemove = this.zafb.remove();
                apiMethodImplRemove.zaa((zacs) null);
                apiMethodImplRemove.cancel();
            }
            this.zaey.signalAll();
        } finally {
            this.zaen.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(@NonNull T t) {
        if (this.zaez && zab(t)) {
            return t;
        }
        if (isConnected()) {
            this.zaew.zahe.zab(t);
            return (T) this.zaet.get(t.getClientKey()).doRead(t);
        }
        this.zafb.add(t);
        return t;
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t) {
        Api.AnyClientKey<A> clientKey = t.getClientKey();
        if (this.zaez && zab(t)) {
            return t;
        }
        this.zaew.zahe.zab(t);
        return (T) this.zaet.get(clientKey).doWrite(t);
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        return zaa(api.getClientKey());
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final boolean isConnected() {
        this.zaen.lock();
        try {
            return this.zafd != null && this.zafg == null;
        } finally {
            this.zaen.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final boolean isConnecting() {
        this.zaen.lock();
        try {
            return this.zafd == null && this.zafc;
        } finally {
            this.zaen.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        Lock lock;
        this.zaen.lock();
        try {
            if (!this.zafc || zaac()) {
                return false;
            }
            this.zabm.zao();
            this.zaff = new zaaa(this, signInConnectionListener);
            this.zabm.zaa(this.zaeu.values()).addOnCompleteListener(new HandlerExecutor(this.zabj), this.zaff);
            return true;
        } finally {
            this.zaen.unlock();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.common.api.internal.zabs
    public final void maybeSignOut() {
        this.zaen.lock();
        try {
            this.zabm.maybeSignOut();
            if (this.zaff != null) {
                this.zaff.cancel();
                this.zaff = null;
            }
            if (this.zafe == null) {
                this.zafe = new ArrayMap(this.zaeu.size());
            }
            ConnectionResult connectionResult = new ConnectionResult(4);
            Iterator<zaw<?>> it = this.zaeu.values().iterator();
            while (it.hasNext()) {
                this.zafe.put(it.next().zak(), connectionResult);
            }
            if (this.zafd != null) {
                this.zafd.putAll(this.zafe);
            }
        } finally {
            this.zaen.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final void zaw() {
    }
}
