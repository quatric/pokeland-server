package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.signin.SignInOptions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zaak implements zabd {
    private final Context mContext;
    private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> zacd;
    private final Lock zaen;
    private final ClientSettings zaes;
    private final Map<Api<?>, Boolean> zaev;
    private final GoogleApiAvailabilityLight zaex;
    private ConnectionResult zafg;
    private final zabe zafs;
    private int zafv;
    private int zafx;
    private com.google.android.gms.signin.zad zaga;
    private boolean zagb;
    private boolean zagc;
    private boolean zagd;
    private IAccountAccessor zage;
    private boolean zagf;
    private boolean zagg;
    private int zafw = 0;
    private final Bundle zafy = new Bundle();
    private final Set<Api.AnyClientKey> zafz = new HashSet();
    private ArrayList<Future<?>> zagh = new ArrayList<>();

    public zaak(zabe zabeVar, ClientSettings clientSettings, Map<Api<?>, Boolean> map, GoogleApiAvailabilityLight googleApiAvailabilityLight, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> abstractClientBuilder, Lock lock, Context context) {
        this.zafs = zabeVar;
        this.zaes = clientSettings;
        this.zaev = map;
        this.zaex = googleApiAvailabilityLight;
        this.zacd = abstractClientBuilder;
        this.zaen = lock;
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zaa(com.google.android.gms.signin.internal.zaj zajVar) {
        if (zac(0)) {
            ConnectionResult connectionResult = zajVar.getConnectionResult();
            if (!connectionResult.isSuccess()) {
                if (!zad(connectionResult)) {
                    zae(connectionResult);
                    return;
                } else {
                    zaar();
                    zaap();
                    return;
                }
            }
            ResolveAccountResponse resolveAccountResponseZacw = zajVar.zacw();
            ConnectionResult connectionResult2 = resolveAccountResponseZacw.getConnectionResult();
            if (connectionResult2.isSuccess()) {
                this.zagd = true;
                this.zage = resolveAccountResponseZacw.getAccountAccessor();
                this.zagf = resolveAccountResponseZacw.getSaveDefaultAccount();
                this.zagg = resolveAccountResponseZacw.isFromCrossClientAuth();
                zaap();
                return;
            }
            String strValueOf = String.valueOf(connectionResult2);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 48);
            sb.append("Sign-in succeeded with resolve account failure: ");
            sb.append(strValueOf);
            Log.wtf("GoogleApiClientConnecting", sb.toString(), new Exception());
            zae(connectionResult2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final boolean zaao() {
        this.zafx--;
        int i = this.zafx;
        if (i > 0) {
            return false;
        }
        if (i < 0) {
            Log.w("GoogleApiClientConnecting", this.zafs.zaed.zaay());
            Log.wtf("GoogleApiClientConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
            zae(new ConnectionResult(8, null));
            return false;
        }
        ConnectionResult connectionResult = this.zafg;
        if (connectionResult == null) {
            return true;
        }
        this.zafs.zahr = this.zafv;
        zae(connectionResult);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zaap() {
        if (this.zafx != 0) {
            return;
        }
        if (!this.zagc || this.zagd) {
            ArrayList arrayList = new ArrayList();
            this.zafw = 1;
            this.zafx = this.zafs.zagy.size();
            for (Api.AnyClientKey<?> anyClientKey : this.zafs.zagy.keySet()) {
                if (!this.zafs.zaho.containsKey(anyClientKey)) {
                    arrayList.add(this.zafs.zagy.get(anyClientKey));
                } else if (zaao()) {
                    zaaq();
                }
            }
            if (arrayList.isEmpty()) {
                return;
            }
            this.zagh.add(zabh.zabb().submit(new zaaq(this, arrayList)));
        }
    }

    @GuardedBy("mLock")
    private final void zaaq() {
        this.zafs.zaba();
        zabh.zabb().execute(new zaal(this));
        com.google.android.gms.signin.zad zadVar = this.zaga;
        if (zadVar != null) {
            if (this.zagf) {
                zadVar.zaa(this.zage, this.zagg);
            }
            zab(false);
        }
        Iterator<Api.AnyClientKey<?>> it = this.zafs.zaho.keySet().iterator();
        while (it.hasNext()) {
            this.zafs.zagy.get(it.next()).disconnect();
        }
        this.zafs.zahs.zab(this.zafy.isEmpty() ? null : this.zafy);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zaar() {
        this.zagc = false;
        this.zafs.zaed.zagz = Collections.emptySet();
        for (Api.AnyClientKey<?> anyClientKey : this.zafz) {
            if (!this.zafs.zaho.containsKey(anyClientKey)) {
                this.zafs.zaho.put(anyClientKey, new ConnectionResult(17, null));
            }
        }
    }

    private final void zaas() {
        ArrayList<Future<?>> arrayList = this.zagh;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Future<?> future = arrayList.get(i);
            i++;
            future.cancel(true);
        }
        this.zagh.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Set<Scope> zaat() {
        ClientSettings clientSettings = this.zaes;
        if (clientSettings == null) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet(clientSettings.getRequiredScopes());
        Map<Api<?>, ClientSettings.OptionalApiSettings> optionalApiSettings = this.zaes.getOptionalApiSettings();
        for (Api<?> api : optionalApiSettings.keySet()) {
            if (!this.zafs.zaho.containsKey(api.getClientKey())) {
                hashSet.addAll(optionalApiSettings.get(api).mScopes);
            }
        }
        return hashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:12:0x0024  */
    /* JADX WARN: Code duplicated, block: B:16:0x002c  */
    @GuardedBy("mLock")
    public final void zab(ConnectionResult connectionResult, Api<?> api, boolean z) {
        int priority = api.zah().getPriority();
        boolean z2 = false;
        if (z) {
            if (connectionResult.hasResolution() || this.zaex.getErrorResolutionIntent(connectionResult.getErrorCode()) != null) {
                if (this.zafg != null) {
                    z2 = true;
                } else {
                    z2 = true;
                }
            }
        } else if (this.zafg != null || priority < this.zafv) {
            z2 = true;
        }
        if (z2) {
            this.zafg = connectionResult;
            this.zafv = priority;
        }
        this.zafs.zaho.put(api.getClientKey(), connectionResult);
    }

    private final void zab(boolean z) {
        com.google.android.gms.signin.zad zadVar = this.zaga;
        if (zadVar != null) {
            if (zadVar.isConnected() && z) {
                this.zaga.zacv();
            }
            this.zaga.disconnect();
            this.zage = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final boolean zac(int i) {
        if (this.zafw == i) {
            return true;
        }
        Log.w("GoogleApiClientConnecting", this.zafs.zaed.zaay());
        String strValueOf = String.valueOf(this);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 23);
        sb.append("Unexpected callback in ");
        sb.append(strValueOf);
        Log.w("GoogleApiClientConnecting", sb.toString());
        int i2 = this.zafx;
        StringBuilder sb2 = new StringBuilder(33);
        sb2.append("mRemainingConnections=");
        sb2.append(i2);
        Log.w("GoogleApiClientConnecting", sb2.toString());
        String strZad = zad(this.zafw);
        String strZad2 = zad(i);
        StringBuilder sb3 = new StringBuilder(String.valueOf(strZad).length() + 70 + String.valueOf(strZad2).length());
        sb3.append("GoogleApiClient connecting is in step ");
        sb3.append(strZad);
        sb3.append(" but received callback for step ");
        sb3.append(strZad2);
        Log.wtf("GoogleApiClientConnecting", sb3.toString(), new Exception());
        zae(new ConnectionResult(8, null));
        return false;
    }

    private static String zad(int i) {
        if (i != 0) {
            return i != 1 ? "UNKNOWN" : "STEP_GETTING_REMOTE_SERVICE";
        }
        return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final boolean zad(ConnectionResult connectionResult) {
        return this.zagb && !connectionResult.hasResolution();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zae(ConnectionResult connectionResult) {
        zaas();
        zab(!connectionResult.hasResolution());
        this.zafs.zaf(connectionResult);
        this.zafs.zahs.zac(connectionResult);
    }

    @Override // com.google.android.gms.common.api.internal.zabd
    public final void begin() {
        this.zafs.zaho.clear();
        this.zagc = false;
        zaal zaalVar = null;
        this.zafg = null;
        this.zafw = 0;
        this.zagb = true;
        this.zagd = false;
        this.zagf = false;
        HashMap map = new HashMap();
        boolean z = false;
        for (Api<?> api : this.zaev.keySet()) {
            Api.Client client = this.zafs.zagy.get(api.getClientKey());
            z |= api.zah().getPriority() == 1;
            boolean zBooleanValue = this.zaev.get(api).booleanValue();
            if (client.requiresSignIn()) {
                this.zagc = true;
                if (zBooleanValue) {
                    this.zafz.add(api.getClientKey());
                } else {
                    this.zagb = false;
                }
            }
            map.put(client, new zaam(this, api, zBooleanValue));
        }
        if (z) {
            this.zagc = false;
        }
        if (this.zagc) {
            this.zaes.setClientSessionId(Integer.valueOf(System.identityHashCode(this.zafs.zaed)));
            zaat zaatVar = new zaat(this, zaalVar);
            Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> abstractClientBuilder = this.zacd;
            Context context = this.mContext;
            Looper looper = this.zafs.zaed.getLooper();
            ClientSettings clientSettings = this.zaes;
            this.zaga = (com.google.android.gms.signin.zad) abstractClientBuilder.buildClient(context, looper, clientSettings, clientSettings.getSignInOptions(), zaatVar, zaatVar);
        }
        this.zafx = this.zafs.zagy.size();
        this.zagh.add(zabh.zabb().submit(new zaan(this, map)));
    }

    @Override // com.google.android.gms.common.api.internal.zabd
    public final void connect() {
    }

    @Override // com.google.android.gms.common.api.internal.zabd
    public final boolean disconnect() {
        zaas();
        zab(true);
        this.zafs.zaf(null);
        return true;
    }

    @Override // com.google.android.gms.common.api.internal.zabd
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(T t) {
        this.zafs.zaed.zafb.add(t);
        return t;
    }

    @Override // com.google.android.gms.common.api.internal.zabd
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }

    @Override // com.google.android.gms.common.api.internal.zabd
    @GuardedBy("mLock")
    public final void onConnected(Bundle bundle) {
        if (zac(1)) {
            if (bundle != null) {
                this.zafy.putAll(bundle);
            }
            if (zaao()) {
                zaaq();
            }
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabd
    @GuardedBy("mLock")
    public final void onConnectionSuspended(int i) {
        zae(new ConnectionResult(8, null));
    }

    @Override // com.google.android.gms.common.api.internal.zabd
    @GuardedBy("mLock")
    public final void zaa(ConnectionResult connectionResult, Api<?> api, boolean z) {
        if (zac(1)) {
            zab(connectionResult, api, z);
            if (zaao()) {
                zaaq();
            }
        }
    }
}
