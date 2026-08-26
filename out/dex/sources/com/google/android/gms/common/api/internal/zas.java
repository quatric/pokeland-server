package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.signin.SignInOptions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zas implements zabs {
    private final Context mContext;
    private final Looper zabj;
    private final zaaw zaed;
    private final zabe zaee;
    private final zabe zaef;
    private final Map<Api.AnyClientKey<?>, zabe> zaeg;
    private final Api.Client zaei;
    private Bundle zaej;
    private final Lock zaen;
    private final Set<SignInConnectionListener> zaeh = Collections.newSetFromMap(new WeakHashMap());
    private ConnectionResult zaek = null;
    private ConnectionResult zael = null;
    private boolean zaem = false;

    @GuardedBy("mLock")
    private int zaeo = 0;

    private zas(Context context, zaaw zaawVar, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<Api.AnyClientKey<?>, Api.Client> map, Map<Api.AnyClientKey<?>, Api.Client> map2, ClientSettings clientSettings, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> abstractClientBuilder, Api.Client client, ArrayList<zaq> arrayList, ArrayList<zaq> arrayList2, Map<Api<?>, Boolean> map3, Map<Api<?>, Boolean> map4) {
        this.mContext = context;
        this.zaed = zaawVar;
        this.zaen = lock;
        this.zabj = looper;
        this.zaei = client;
        this.zaee = new zabe(context, this.zaed, lock, looper, googleApiAvailabilityLight, map2, null, map4, null, arrayList2, new zau(this, null));
        this.zaef = new zabe(context, this.zaed, lock, looper, googleApiAvailabilityLight, map, clientSettings, map3, abstractClientBuilder, arrayList, new zav(this, null));
        ArrayMap arrayMap = new ArrayMap();
        Iterator<Api.AnyClientKey<?>> it = map2.keySet().iterator();
        while (it.hasNext()) {
            arrayMap.put(it.next(), this.zaee);
        }
        Iterator<Api.AnyClientKey<?>> it2 = map.keySet().iterator();
        while (it2.hasNext()) {
            arrayMap.put(it2.next(), this.zaef);
        }
        this.zaeg = Collections.unmodifiableMap(arrayMap);
    }

    public static zas zaa(Context context, zaaw zaawVar, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<Api.AnyClientKey<?>, Api.Client> map, ClientSettings clientSettings, Map<Api<?>, Boolean> map2, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> abstractClientBuilder, ArrayList<zaq> arrayList) {
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        Api.Client client = null;
        for (Map.Entry<Api.AnyClientKey<?>, Api.Client> entry : map.entrySet()) {
            Api.Client value = entry.getValue();
            if (value.providesSignIn()) {
                client = value;
            }
            if (value.requiresSignIn()) {
                arrayMap.put(entry.getKey(), value);
            } else {
                arrayMap2.put(entry.getKey(), value);
            }
        }
        Preconditions.checkState(!arrayMap.isEmpty(), "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        ArrayMap arrayMap3 = new ArrayMap();
        ArrayMap arrayMap4 = new ArrayMap();
        for (Api<?> api : map2.keySet()) {
            Api.AnyClientKey<?> clientKey = api.getClientKey();
            if (arrayMap.containsKey(clientKey)) {
                arrayMap3.put(api, map2.get(api));
            } else {
                if (!arrayMap2.containsKey(clientKey)) {
                    throw new IllegalStateException("Each API in the isOptionalMap must have a corresponding client in the clients map.");
                }
                arrayMap4.put(api, map2.get(api));
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList<zaq> arrayList4 = arrayList;
        int size = arrayList4.size();
        int i = 0;
        while (i < size) {
            zaq zaqVar = arrayList4.get(i);
            i++;
            zaq zaqVar2 = zaqVar;
            if (arrayMap3.containsKey(zaqVar2.mApi)) {
                arrayList2.add(zaqVar2);
            } else {
                if (!arrayMap4.containsKey(zaqVar2.mApi)) {
                    throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the isOptionalMap");
                }
                arrayList3.add(zaqVar2);
            }
        }
        return new zas(context, zaawVar, lock, looper, googleApiAvailabilityLight, arrayMap, arrayMap2, clientSettings, abstractClientBuilder, client, arrayList2, arrayList3, arrayMap3, arrayMap4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zaa(int i, boolean z) {
        this.zaed.zab(i, z);
        this.zael = null;
        this.zaek = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zaa(Bundle bundle) {
        Bundle bundle2 = this.zaej;
        if (bundle2 == null) {
            this.zaej = bundle;
        } else if (bundle != null) {
            bundle2.putAll(bundle);
        }
    }

    @GuardedBy("mLock")
    private final void zaa(ConnectionResult connectionResult) {
        int i = this.zaeo;
        if (i == 1) {
            zay();
        } else if (i != 2) {
            Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
        } else {
            this.zaed.zac(connectionResult);
            zay();
        }
        this.zaeo = 0;
    }

    private final boolean zaa(BaseImplementation.ApiMethodImpl<? extends Result, ? extends Api.AnyClient> apiMethodImpl) {
        Object clientKey = apiMethodImpl.getClientKey();
        Preconditions.checkArgument(this.zaeg.containsKey(clientKey), "GoogleApiClient is not configured to use the API required for this call.");
        return this.zaeg.get(clientKey).equals(this.zaef);
    }

    @Nullable
    private final PendingIntent zaaa() {
        if (this.zaei == null) {
            return null;
        }
        return PendingIntent.getActivity(this.mContext, System.identityHashCode(this.zaed), this.zaei.getSignInIntent(), 134217728);
    }

    private static boolean zab(ConnectionResult connectionResult) {
        return connectionResult != null && connectionResult.isSuccess();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zax() {
        if (!zab(this.zaek)) {
            if (this.zaek != null && zab(this.zael)) {
                this.zaef.disconnect();
                zaa(this.zaek);
                return;
            }
            ConnectionResult connectionResult = this.zaek;
            if (connectionResult == null || this.zael == null) {
                return;
            }
            if (this.zaef.zahr < this.zaee.zahr) {
                connectionResult = this.zael;
            }
            zaa(connectionResult);
            return;
        }
        if (zab(this.zael) || zaz()) {
            int i = this.zaeo;
            if (i == 1) {
                zay();
            } else if (i != 2) {
                Log.wtf("CompositeGAC", "Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new AssertionError());
            } else {
                this.zaed.zab(this.zaej);
                zay();
            }
            this.zaeo = 0;
            return;
        }
        ConnectionResult connectionResult2 = this.zael;
        if (connectionResult2 != null) {
            if (this.zaeo == 1) {
                zay();
            } else {
                zaa(connectionResult2);
                this.zaee.disconnect();
            }
        }
    }

    @GuardedBy("mLock")
    private final void zay() {
        Iterator<SignInConnectionListener> it = this.zaeh.iterator();
        while (it.hasNext()) {
            it.next().onComplete();
        }
        this.zaeh.clear();
    }

    @GuardedBy("mLock")
    private final boolean zaz() {
        ConnectionResult connectionResult = this.zael;
        return connectionResult != null && connectionResult.getErrorCode() == 4;
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final ConnectionResult blockingConnect() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final void connect() {
        this.zaeo = 2;
        this.zaem = false;
        this.zael = null;
        this.zaek = null;
        this.zaee.connect();
        this.zaef.connect();
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final void disconnect() {
        this.zael = null;
        this.zaek = null;
        this.zaeo = 0;
        this.zaee.disconnect();
        this.zaef.disconnect();
        zay();
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append((CharSequence) str).append("authClient").println(":");
        this.zaef.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
        printWriter.append((CharSequence) str).append("anonClient").println(":");
        this.zaee.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(@NonNull T t) {
        if (!zaa((BaseImplementation.ApiMethodImpl<? extends Result, ? extends Api.AnyClient>) t)) {
            return (T) this.zaee.enqueue(t);
        }
        if (!zaz()) {
            return (T) this.zaef.enqueue(t);
        }
        t.setFailedResult(new Status(4, null, zaaa()));
        return t;
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t) {
        if (!zaa((BaseImplementation.ApiMethodImpl<? extends Result, ? extends Api.AnyClient>) t)) {
            return (T) this.zaee.execute(t);
        }
        if (!zaz()) {
            return (T) this.zaef.execute(t);
        }
        t.setFailedResult(new Status(4, null, zaaa()));
        return t;
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        if (this.zaeg.get(api.getClientKey()).equals(this.zaef)) {
            return zaz() ? new ConnectionResult(4, zaaa()) : this.zaef.getConnectionResult(api);
        }
        return this.zaee.getConnectionResult(api);
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final boolean isConnected() {
        this.zaen.lock();
        try {
            boolean z = true;
            if (!this.zaee.isConnected() || (!this.zaef.isConnected() && !zaz() && this.zaeo != 1)) {
                z = false;
            }
            return z;
        } finally {
            this.zaen.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final boolean isConnecting() {
        this.zaen.lock();
        try {
            return this.zaeo == 2;
        } finally {
            this.zaen.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        this.zaen.lock();
        try {
            if ((!isConnecting() && !isConnected()) || this.zaef.isConnected()) {
                return false;
            }
            this.zaeh.add(signInConnectionListener);
            if (this.zaeo == 0) {
                this.zaeo = 1;
            }
            this.zael = null;
            this.zaef.connect();
            return true;
        } finally {
            this.zaen.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final void maybeSignOut() {
        this.zaen.lock();
        try {
            boolean zIsConnecting = isConnecting();
            this.zaef.disconnect();
            this.zael = new ConnectionResult(4);
            if (zIsConnecting) {
                new com.google.android.gms.internal.base.zal(this.zabj).post(new zat(this));
            } else {
                zay();
            }
        } finally {
            this.zaen.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final void zaw() {
        this.zaee.zaw();
        this.zaef.zaw();
    }
}
