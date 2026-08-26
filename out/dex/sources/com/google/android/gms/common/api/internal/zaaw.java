package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClientEventManager;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.common.util.ClientLibraryUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.signin.SignInOptions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zaaw extends GoogleApiClient implements zabt {
    private final Context mContext;
    private final Looper zabj;
    private final int zaca;
    private final GoogleApiAvailability zacc;
    private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> zacd;
    private boolean zacg;
    private final Lock zaen;
    private final ClientSettings zaes;
    private final Map<Api<?>, Boolean> zaev;
    private final GmsClientEventManager zagr;
    private volatile boolean zagt;
    private long zagu;
    private long zagv;
    private final zabb zagw;

    @VisibleForTesting
    private zabq zagx;
    final Map<Api.AnyClientKey<?>, Api.Client> zagy;
    Set<Scope> zagz;
    private final ListenerHolders zaha;
    private final ArrayList<zaq> zahb;
    private Integer zahc;
    Set<zacm> zahd;
    final zacp zahe;
    private final GmsClientEventManager.GmsClientEventState zahf;
    private zabs zags = null;

    @VisibleForTesting
    final Queue<BaseImplementation.ApiMethodImpl<?, ?>> zafb = new LinkedList();

    public zaaw(Context context, Lock lock, Looper looper, ClientSettings clientSettings, GoogleApiAvailability googleApiAvailability, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> abstractClientBuilder, Map<Api<?>, Boolean> map, List<GoogleApiClient.ConnectionCallbacks> list, List<GoogleApiClient.OnConnectionFailedListener> list2, Map<Api.AnyClientKey<?>, Api.Client> map2, int i, int i2, ArrayList<zaq> arrayList, boolean z) {
        this.zagu = ClientLibraryUtils.isPackageSide() ? 10000L : 120000L;
        this.zagv = 5000L;
        this.zagz = new HashSet();
        this.zaha = new ListenerHolders();
        this.zahc = null;
        this.zahd = null;
        this.zahf = new zaax(this);
        this.mContext = context;
        this.zaen = lock;
        this.zacg = false;
        this.zagr = new GmsClientEventManager(looper, this.zahf);
        this.zabj = looper;
        this.zagw = new zabb(this, looper);
        this.zacc = googleApiAvailability;
        this.zaca = i;
        if (this.zaca >= 0) {
            this.zahc = Integer.valueOf(i2);
        }
        this.zaev = map;
        this.zagy = map2;
        this.zahb = arrayList;
        this.zahe = new zacp(this.zagy);
        Iterator<GoogleApiClient.ConnectionCallbacks> it = list.iterator();
        while (it.hasNext()) {
            this.zagr.registerConnectionCallbacks(it.next());
        }
        Iterator<GoogleApiClient.OnConnectionFailedListener> it2 = list2.iterator();
        while (it2.hasNext()) {
            this.zagr.registerConnectionFailedListener(it2.next());
        }
        this.zaes = clientSettings;
        this.zacd = abstractClientBuilder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void resume() {
        this.zaen.lock();
        try {
            if (this.zagt) {
                zaau();
            }
        } finally {
            this.zaen.unlock();
        }
    }

    public static int zaa(Iterable<Api.Client> iterable, boolean z) {
        boolean z2 = false;
        boolean z3 = false;
        for (Api.Client client : iterable) {
            if (client.requiresSignIn()) {
                z2 = true;
            }
            if (client.providesSignIn()) {
                z3 = true;
            }
        }
        if (z2) {
            return (z3 && z) ? 2 : 1;
        }
        return 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zaa(GoogleApiClient googleApiClient, StatusPendingResult statusPendingResult, boolean z) {
        Common.zaph.zaa(googleApiClient).setResultCallback(new zaba(this, statusPendingResult, z, googleApiClient));
    }

    @GuardedBy("mLock")
    private final void zaau() {
        this.zagr.enableCallbacks();
        this.zags.connect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zaav() {
        this.zaen.lock();
        try {
            if (zaaw()) {
                zaau();
            }
        } finally {
            this.zaen.unlock();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private final void zae(int i) {
        Integer num = this.zahc;
        if (num == null) {
            this.zahc = Integer.valueOf(i);
        } else if (num.intValue() != i) {
            String strZaf = zaf(i);
            String strZaf2 = zaf(this.zahc.intValue());
            StringBuilder sb = new StringBuilder(String.valueOf(strZaf).length() + 51 + String.valueOf(strZaf2).length());
            sb.append("Cannot use sign-in mode: ");
            sb.append(strZaf);
            sb.append(". Mode was already set to ");
            sb.append(strZaf2);
            throw new IllegalStateException(sb.toString());
        }
        if (this.zags != null) {
            return;
        }
        boolean z = false;
        boolean z2 = false;
        for (Api.Client client : this.zagy.values()) {
            if (client.requiresSignIn()) {
                z = true;
            }
            if (client.providesSignIn()) {
                z2 = true;
            }
        }
        int iIntValue = this.zahc.intValue();
        if (iIntValue == 1) {
            if (!z) {
                throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
            }
            if (z2) {
                throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
        } else if (iIntValue == 2 && z) {
            if (this.zacg) {
                this.zags = new zax(this.mContext, this.zaen, this.zabj, this.zacc, this.zagy, this.zaes, this.zaev, this.zacd, this.zahb, this, true);
                return;
            } else {
                this.zags = zas.zaa(this.mContext, this, this.zaen, this.zabj, this.zacc, this.zagy, this.zaes, this.zaev, this.zacd, this.zahb);
                return;
            }
        }
        if (!this.zacg || z2) {
            this.zags = new zabe(this.mContext, this, this.zaen, this.zabj, this.zacc, this.zagy, this.zaes, this.zaev, this.zacd, this.zahb, this);
        } else {
            this.zags = new zax(this.mContext, this.zaen, this.zabj, this.zacc, this.zagy, this.zaes, this.zaev, this.zacd, this.zahb, this, false);
        }
    }

    private static String zaf(int i) {
        if (i == 1) {
            return "SIGN_IN_MODE_REQUIRED";
        }
        if (i != 2) {
            return i != 3 ? "UNKNOWN" : "SIGN_IN_MODE_NONE";
        }
        return "SIGN_IN_MODE_OPTIONAL";
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final ConnectionResult blockingConnect() {
        boolean z = true;
        Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper(), "blockingConnect must not be called on the UI thread");
        this.zaen.lock();
        try {
            if (this.zaca >= 0) {
                if (this.zahc == null) {
                    z = false;
                }
                Preconditions.checkState(z, "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zahc == null) {
                this.zahc = Integer.valueOf(zaa(this.zagy.values(), false));
            } else if (this.zahc.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zae(this.zahc.intValue());
            this.zagr.enableCallbacks();
            ConnectionResult connectionResultBlockingConnect = this.zags.blockingConnect();
            this.zaen.unlock();
            return connectionResultBlockingConnect;
        } catch (Throwable th) {
            this.zaen.unlock();
            throw th;
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper(), "blockingConnect must not be called on the UI thread");
        Preconditions.checkNotNull(timeUnit, "TimeUnit must not be null");
        this.zaen.lock();
        try {
            if (this.zahc == null) {
                this.zahc = Integer.valueOf(zaa(this.zagy.values(), false));
            } else if (this.zahc.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zae(this.zahc.intValue());
            this.zagr.enableCallbacks();
            ConnectionResult connectionResultBlockingConnect = this.zags.blockingConnect(j, timeUnit);
            this.zaen.unlock();
            return connectionResultBlockingConnect;
        } catch (Throwable th) {
            this.zaen.unlock();
            throw th;
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final PendingResult<Status> clearDefaultAccountAndReconnect() {
        Preconditions.checkState(isConnected(), "GoogleApiClient is not connected yet.");
        Preconditions.checkState(this.zahc.intValue() != 2, "Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
        StatusPendingResult statusPendingResult = new StatusPendingResult(this);
        if (this.zagy.containsKey(Common.CLIENT_KEY)) {
            zaa(this, statusPendingResult, false);
        } else {
            AtomicReference atomicReference = new AtomicReference();
            GoogleApiClient googleApiClientBuild = new GoogleApiClient.Builder(this.mContext).addApi(Common.API).addConnectionCallbacks(new zaay(this, atomicReference, statusPendingResult)).addOnConnectionFailedListener(new zaaz(this, statusPendingResult)).setHandler(this.zagw).build();
            atomicReference.set(googleApiClientBuild);
            googleApiClientBuild.connect();
        }
        return statusPendingResult;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void connect() {
        this.zaen.lock();
        try {
            if (this.zaca >= 0) {
                Preconditions.checkState(this.zahc != null, "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zahc == null) {
                this.zahc = Integer.valueOf(zaa(this.zagy.values(), false));
            } else if (this.zahc.intValue() == 2) {
                throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            connect(this.zahc.intValue());
            this.zaen.unlock();
        } catch (Throwable th) {
            this.zaen.unlock();
            throw th;
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void connect(int i) {
        this.zaen.lock();
        boolean z = true;
        if (i != 3 && i != 1 && i != 2) {
            z = false;
        }
        try {
            StringBuilder sb = new StringBuilder(33);
            sb.append("Illegal sign-in mode: ");
            sb.append(i);
            Preconditions.checkArgument(z, sb.toString());
            zae(i);
            zaau();
        } finally {
            this.zaen.unlock();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void disconnect() {
        this.zaen.lock();
        try {
            this.zahe.release();
            if (this.zags != null) {
                this.zags.disconnect();
            }
            this.zaha.release();
            for (BaseImplementation.ApiMethodImpl<?, ?> apiMethodImpl : this.zafb) {
                apiMethodImpl.zaa((zacs) null);
                apiMethodImpl.cancel();
            }
            this.zafb.clear();
            if (this.zags == null) {
                return;
            }
            zaaw();
            this.zagr.disableCallbacks();
        } finally {
            this.zaen.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append((CharSequence) str).append("mContext=").println(this.mContext);
        printWriter.append((CharSequence) str).append("mResuming=").print(this.zagt);
        printWriter.append(" mWorkQueue.size()=").print(this.zafb.size());
        printWriter.append(" mUnconsumedApiCalls.size()=").println(this.zahe.zaky.size());
        zabs zabsVar = this.zags;
        if (zabsVar != null) {
            zabsVar.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(@NonNull T t) {
        Preconditions.checkArgument(t.getClientKey() != null, "This task can not be enqueued (it's probably a Batch or malformed)");
        boolean zContainsKey = this.zagy.containsKey(t.getClientKey());
        String name = t.getApi() != null ? t.getApi().getName() : "the API";
        StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 65);
        sb.append("GoogleApiClient is not configured to use ");
        sb.append(name);
        sb.append(" required for this call.");
        Preconditions.checkArgument(zContainsKey, sb.toString());
        this.zaen.lock();
        try {
            if (this.zags != null) {
                return (T) this.zags.enqueue(t);
            }
            this.zafb.add(t);
            return t;
        } finally {
            this.zaen.unlock();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t) {
        Preconditions.checkArgument(t.getClientKey() != null, "This task can not be executed (it's probably a Batch or malformed)");
        boolean zContainsKey = this.zagy.containsKey(t.getClientKey());
        String name = t.getApi() != null ? t.getApi().getName() : "the API";
        StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 65);
        sb.append("GoogleApiClient is not configured to use ");
        sb.append(name);
        sb.append(" required for this call.");
        Preconditions.checkArgument(zContainsKey, sb.toString());
        this.zaen.lock();
        try {
            if (this.zags == null) {
                throw new IllegalStateException("GoogleApiClient is not connected yet.");
            }
            if (!this.zagt) {
                T t2 = (T) this.zags.execute(t);
                this.zaen.unlock();
                return t2;
            }
            this.zafb.add(t);
            while (!this.zafb.isEmpty()) {
                BaseImplementation.ApiMethodImpl<?, ?> apiMethodImplRemove = this.zafb.remove();
                this.zahe.zab(apiMethodImplRemove);
                apiMethodImplRemove.setFailedResult(Status.RESULT_INTERNAL_ERROR);
            }
            this.zaen.unlock();
            return t;
        } catch (Throwable th) {
            this.zaen.unlock();
            throw th;
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    @NonNull
    public final <C extends Api.Client> C getClient(@NonNull Api.AnyClientKey<C> anyClientKey) {
        C c = (C) this.zagy.get(anyClientKey);
        Preconditions.checkNotNull(c, "Appropriate Api was not requested.");
        return c;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    @NonNull
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        this.zaen.lock();
        try {
            if (!isConnected() && !this.zagt) {
                throw new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
            }
            if (!this.zagy.containsKey(api.getClientKey())) {
                throw new IllegalArgumentException(String.valueOf(api.getName()).concat(" was never registered with GoogleApiClient"));
            }
            ConnectionResult connectionResult = this.zags.getConnectionResult(api);
            if (connectionResult != null) {
                this.zaen.unlock();
                return connectionResult;
            }
            if (this.zagt) {
                ConnectionResult connectionResult2 = ConnectionResult.RESULT_SUCCESS;
                this.zaen.unlock();
                return connectionResult2;
            }
            Log.w("GoogleApiClientImpl", zaay());
            Log.wtf("GoogleApiClientImpl", String.valueOf(api.getName()).concat(" requested in getConnectionResult is not connected but is not present in the failed  connections map"), new Exception());
            ConnectionResult connectionResult3 = new ConnectionResult(8, null);
            this.zaen.unlock();
            return connectionResult3;
        } catch (Throwable th) {
            this.zaen.unlock();
            throw th;
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final Looper getLooper() {
        return this.zabj;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean hasApi(@NonNull Api<?> api) {
        return this.zagy.containsKey(api.getClientKey());
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean hasConnectedApi(@NonNull Api<?> api) {
        Api.Client client;
        return isConnected() && (client = this.zagy.get(api.getClientKey())) != null && client.isConnected();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean isConnected() {
        zabs zabsVar = this.zags;
        return zabsVar != null && zabsVar.isConnected();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean isConnecting() {
        zabs zabsVar = this.zags;
        return zabsVar != null && zabsVar.isConnecting();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean isConnectionCallbacksRegistered(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        return this.zagr.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean isConnectionFailedListenerRegistered(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.zagr.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        zabs zabsVar = this.zags;
        return zabsVar != null && zabsVar.maybeSignIn(signInConnectionListener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void maybeSignOut() {
        zabs zabsVar = this.zags;
        if (zabsVar != null) {
            zabsVar.maybeSignOut();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void reconnect() {
        disconnect();
        connect();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void registerConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zagr.registerConnectionCallbacks(connectionCallbacks);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void registerConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zagr.registerConnectionFailedListener(onConnectionFailedListener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final <L> ListenerHolder<L> registerListener(@NonNull L l) {
        this.zaen.lock();
        try {
            return this.zaha.zaa(l, this.zabj, "NO_TYPE");
        } finally {
            this.zaen.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        LifecycleActivity lifecycleActivity = new LifecycleActivity((Activity) fragmentActivity);
        if (this.zaca < 0) {
            throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
        }
        zaj.zaa(lifecycleActivity).zaa(this.zaca);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void unregisterConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zagr.unregisterConnectionCallbacks(connectionCallbacks);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void unregisterConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zagr.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void zaa(zacm zacmVar) {
        this.zaen.lock();
        try {
            if (this.zahd == null) {
                this.zahd = new HashSet();
            }
            this.zahd.add(zacmVar);
        } finally {
            this.zaen.unlock();
        }
    }

    @GuardedBy("mLock")
    final boolean zaaw() {
        if (!this.zagt) {
            return false;
        }
        this.zagt = false;
        this.zagw.removeMessages(2);
        this.zagw.removeMessages(1);
        zabq zabqVar = this.zagx;
        if (zabqVar != null) {
            zabqVar.unregister();
            this.zagx = null;
        }
        return true;
    }

    final boolean zaax() {
        Lock lock;
        this.zaen.lock();
        try {
            if (this.zahd == null) {
                return false;
            }
            return !this.zahd.isEmpty();
        } finally {
            this.zaen.unlock();
        }
    }

    final String zaay() {
        StringWriter stringWriter = new StringWriter();
        dump("", null, new PrintWriter(stringWriter), null);
        return stringWriter.toString();
    }

    @Override // com.google.android.gms.common.api.internal.zabt
    @GuardedBy("mLock")
    public final void zab(int i, boolean z) {
        if (i == 1 && !z && !this.zagt) {
            this.zagt = true;
            if (this.zagx == null && !ClientLibraryUtils.isPackageSide()) {
                this.zagx = this.zacc.zaa(this.mContext.getApplicationContext(), new zabc(this));
            }
            zabb zabbVar = this.zagw;
            zabbVar.sendMessageDelayed(zabbVar.obtainMessage(1), this.zagu);
            zabb zabbVar2 = this.zagw;
            zabbVar2.sendMessageDelayed(zabbVar2.obtainMessage(2), this.zagv);
        }
        this.zahe.zabx();
        this.zagr.onUnintentionalDisconnection(i);
        this.zagr.disableCallbacks();
        if (i == 2) {
            zaau();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabt
    @GuardedBy("mLock")
    public final void zab(Bundle bundle) {
        while (!this.zafb.isEmpty()) {
            execute(this.zafb.remove());
        }
        this.zagr.onConnectionSuccess(bundle);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void zab(zacm zacmVar) {
        this.zaen.lock();
        try {
            if (this.zahd == null) {
                Log.wtf("GoogleApiClientImpl", "Attempted to remove pending transform when no transforms are registered.", new Exception());
            } else if (!this.zahd.remove(zacmVar)) {
                Log.wtf("GoogleApiClientImpl", "Failed to remove pending transform - this may lead to memory leaks!", new Exception());
            } else if (!zaax()) {
                this.zags.zaw();
            }
        } finally {
            this.zaen.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabt
    @GuardedBy("mLock")
    public final void zac(ConnectionResult connectionResult) {
        if (!this.zacc.isPlayServicesPossiblyUpdating(this.mContext, connectionResult.getErrorCode())) {
            zaaw();
        }
        if (this.zagt) {
            return;
        }
        this.zagr.onConnectionFailure(connectionResult);
        this.zagr.disableCallbacks();
    }
}
