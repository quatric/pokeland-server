package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.ArraySet;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.UnsupportedApiCallException;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.SimpleClientAdapter;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
public class GoogleApiManager implements Handler.Callback {

    @GuardedBy("lock")
    private static GoogleApiManager zaib;
    private final Handler handler;
    private final Context zaic;
    private final GoogleApiAvailability zaid;
    private final GoogleApiAvailabilityCache zaie;
    public static final Status zahw = new Status(4, "Sign-out occurred while this API call was in progress.");
    private static final Status zahx = new Status(4, "The user must be signed in to make this API call.");
    private static final Object lock = new Object();
    private long zahy = 5000;
    private long zahz = 120000;
    private long zaia = 10000;
    private final AtomicInteger zaif = new AtomicInteger(1);
    private final AtomicInteger zaig = new AtomicInteger(0);
    private final Map<zai<?>, zaa<?>> zaih = new ConcurrentHashMap(5, 0.75f, 1);

    @GuardedBy("lock")
    private zaae zaii = null;

    @GuardedBy("lock")
    private final Set<zai<?>> zaij = new ArraySet();
    private final Set<zai<?>> zaik = new ArraySet();

    public class zaa<O extends Api.ApiOptions> implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, zar {
        private final zai<O> zafp;
        private final Api.Client zain;
        private final Api.AnyClient zaio;
        private final zaab zaip;
        private final int zais;
        private final zace zait;
        private boolean zaiu;
        private final Queue<com.google.android.gms.common.api.internal.zab> zaim = new LinkedList();
        private final Set<zak> zaiq = new HashSet();
        private final Map<ListenerHolder.ListenerKey<?>, zabw> zair = new HashMap();
        private final List<zab> zaiv = new ArrayList();
        private ConnectionResult zaiw = null;

        @WorkerThread
        public zaa(GoogleApi<O> googleApi) {
            this.zain = googleApi.zaa(GoogleApiManager.this.handler.getLooper(), this);
            Api.Client client = this.zain;
            if (client instanceof SimpleClientAdapter) {
                this.zaio = ((SimpleClientAdapter) client).getClient();
            } else {
                this.zaio = client;
            }
            this.zafp = googleApi.zak();
            this.zaip = new zaab();
            this.zais = googleApi.getInstanceId();
            if (this.zain.requiresSignIn()) {
                this.zait = googleApi.zaa(GoogleApiManager.this.zaic, GoogleApiManager.this.handler);
            } else {
                this.zait = null;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @WorkerThread
        @Nullable
        private final Feature zaa(@Nullable Feature[] featureArr) {
            if (featureArr != null && featureArr.length != 0) {
                Feature[] availableFeatures = this.zain.getAvailableFeatures();
                if (availableFeatures == null) {
                    availableFeatures = new Feature[0];
                }
                ArrayMap arrayMap = new ArrayMap(availableFeatures.length);
                for (Feature feature : availableFeatures) {
                    arrayMap.put(feature.getName(), Long.valueOf(feature.getVersion()));
                }
                for (Feature feature2 : featureArr) {
                    if (!arrayMap.containsKey(feature2.getName()) || ((Long) arrayMap.get(feature2.getName())).longValue() < feature2.getVersion()) {
                        return feature2;
                    }
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @WorkerThread
        public final void zaa(zab zabVar) {
            if (this.zaiv.contains(zabVar) && !this.zaiu) {
                if (this.zain.isConnected()) {
                    zabi();
                } else {
                    connect();
                }
            }
        }

        static /* synthetic */ boolean zaa(zaa zaaVar, boolean z) {
            return zaaVar.zac(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @WorkerThread
        public final void zab(zab zabVar) {
            Feature[] featureArrZab;
            if (this.zaiv.remove(zabVar)) {
                GoogleApiManager.this.handler.removeMessages(15, zabVar);
                GoogleApiManager.this.handler.removeMessages(16, zabVar);
                Feature feature = zabVar.zajb;
                ArrayList arrayList = new ArrayList(this.zaim.size());
                for (com.google.android.gms.common.api.internal.zab zabVar2 : this.zaim) {
                    if ((zabVar2 instanceof com.google.android.gms.common.api.internal.zac) && (featureArrZab = ((com.google.android.gms.common.api.internal.zac) zabVar2).zab((zaa<?>) this)) != null && ArrayUtils.contains(featureArrZab, feature)) {
                        arrayList.add(zabVar2);
                    }
                }
                ArrayList arrayList2 = arrayList;
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList2.get(i);
                    i++;
                    com.google.android.gms.common.api.internal.zab zabVar3 = (com.google.android.gms.common.api.internal.zab) obj;
                    this.zaim.remove(zabVar3);
                    zabVar3.zaa(new UnsupportedApiCallException(feature));
                }
            }
        }

        @WorkerThread
        private final boolean zab(com.google.android.gms.common.api.internal.zab zabVar) {
            if (!(zabVar instanceof com.google.android.gms.common.api.internal.zac)) {
                zac(zabVar);
                return true;
            }
            com.google.android.gms.common.api.internal.zac zacVar = (com.google.android.gms.common.api.internal.zac) zabVar;
            Feature featureZaa = zaa(zacVar.zab((zaa<?>) this));
            if (featureZaa == null) {
                zac(zabVar);
                return true;
            }
            if (!zacVar.zac(this)) {
                zacVar.zaa(new UnsupportedApiCallException(featureZaa));
                return false;
            }
            zab zabVar2 = new zab(this.zafp, featureZaa, null);
            int iIndexOf = this.zaiv.indexOf(zabVar2);
            if (iIndexOf >= 0) {
                zab zabVar3 = this.zaiv.get(iIndexOf);
                GoogleApiManager.this.handler.removeMessages(15, zabVar3);
                GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 15, zabVar3), GoogleApiManager.this.zahy);
                return false;
            }
            this.zaiv.add(zabVar2);
            GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 15, zabVar2), GoogleApiManager.this.zahy);
            GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 16, zabVar2), GoogleApiManager.this.zahz);
            ConnectionResult connectionResult = new ConnectionResult(2, null);
            if (zah(connectionResult)) {
                return false;
            }
            GoogleApiManager.this.zac(connectionResult, this.zais);
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @WorkerThread
        public final void zabg() {
            zabl();
            zai(ConnectionResult.RESULT_SUCCESS);
            zabn();
            Iterator<zabw> it = this.zair.values().iterator();
            while (it.hasNext()) {
                zabw next = it.next();
                if (zaa(next.zajw.getRequiredFeatures()) != null) {
                    it.remove();
                } else {
                    try {
                        next.zajw.registerListener(this.zaio, new TaskCompletionSource<>());
                    } catch (DeadObjectException unused) {
                        onConnectionSuspended(1);
                        this.zain.disconnect();
                    } catch (RemoteException unused2) {
                        it.remove();
                    }
                }
            }
            zabi();
            zabo();
        }

        /* JADX INFO: Access modifiers changed from: private */
        @WorkerThread
        public final void zabh() {
            zabl();
            this.zaiu = true;
            this.zaip.zaai();
            GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 9, this.zafp), GoogleApiManager.this.zahy);
            GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 11, this.zafp), GoogleApiManager.this.zahz);
            GoogleApiManager.this.zaie.flush();
        }

        @WorkerThread
        private final void zabi() {
            ArrayList arrayList = new ArrayList(this.zaim);
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                com.google.android.gms.common.api.internal.zab zabVar = (com.google.android.gms.common.api.internal.zab) obj;
                if (!this.zain.isConnected()) {
                    return;
                }
                if (zab(zabVar)) {
                    this.zaim.remove(zabVar);
                }
            }
        }

        @WorkerThread
        private final void zabn() {
            if (this.zaiu) {
                GoogleApiManager.this.handler.removeMessages(11, this.zafp);
                GoogleApiManager.this.handler.removeMessages(9, this.zafp);
                this.zaiu = false;
            }
        }

        private final void zabo() {
            GoogleApiManager.this.handler.removeMessages(12, this.zafp);
            GoogleApiManager.this.handler.sendMessageDelayed(GoogleApiManager.this.handler.obtainMessage(12, this.zafp), GoogleApiManager.this.zaia);
        }

        @WorkerThread
        private final void zac(com.google.android.gms.common.api.internal.zab zabVar) {
            zabVar.zaa(this.zaip, requiresSignIn());
            try {
                zabVar.zaa((zaa<?>) this);
            } catch (DeadObjectException unused) {
                onConnectionSuspended(1);
                this.zain.disconnect();
            }
        }

        @WorkerThread
        private final boolean zac(boolean z) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            if (!this.zain.isConnected() || this.zair.size() != 0) {
                return false;
            }
            if (!this.zaip.zaag()) {
                this.zain.disconnect();
                return true;
            }
            if (z) {
                zabo();
            }
            return false;
        }

        @WorkerThread
        private final boolean zah(@NonNull ConnectionResult connectionResult) {
            synchronized (GoogleApiManager.lock) {
                if (GoogleApiManager.this.zaii == null || !GoogleApiManager.this.zaij.contains(this.zafp)) {
                    return false;
                }
                GoogleApiManager.this.zaii.zab(connectionResult, this.zais);
                return true;
            }
        }

        @WorkerThread
        private final void zai(ConnectionResult connectionResult) {
            for (zak zakVar : this.zaiq) {
                String endpointPackageName = null;
                if (Objects.equal(connectionResult, ConnectionResult.RESULT_SUCCESS)) {
                    endpointPackageName = this.zain.getEndpointPackageName();
                }
                zakVar.zaa(this.zafp, connectionResult, endpointPackageName);
            }
            this.zaiq.clear();
        }

        @WorkerThread
        public final void connect() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            if (this.zain.isConnected() || this.zain.isConnecting()) {
                return;
            }
            int clientAvailability = GoogleApiManager.this.zaie.getClientAvailability(GoogleApiManager.this.zaic, this.zain);
            if (clientAvailability != 0) {
                onConnectionFailed(new ConnectionResult(clientAvailability, null));
                return;
            }
            zac zacVar = GoogleApiManager.this.new zac(this.zain, this.zafp);
            if (this.zain.requiresSignIn()) {
                this.zait.zaa(zacVar);
            }
            this.zain.connect(zacVar);
        }

        public final int getInstanceId() {
            return this.zais;
        }

        final boolean isConnected() {
            return this.zain.isConnected();
        }

        @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
        public final void onConnected(@Nullable Bundle bundle) {
            if (Looper.myLooper() == GoogleApiManager.this.handler.getLooper()) {
                zabg();
            } else {
                GoogleApiManager.this.handler.post(new zabj(this));
            }
        }

        @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
        @WorkerThread
        public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            zace zaceVar = this.zait;
            if (zaceVar != null) {
                zaceVar.zabs();
            }
            zabl();
            GoogleApiManager.this.zaie.flush();
            zai(connectionResult);
            if (connectionResult.getErrorCode() == 4) {
                zac(GoogleApiManager.zahx);
                return;
            }
            if (this.zaim.isEmpty()) {
                this.zaiw = connectionResult;
                return;
            }
            if (zah(connectionResult) || GoogleApiManager.this.zac(connectionResult, this.zais)) {
                return;
            }
            if (connectionResult.getErrorCode() == 18) {
                this.zaiu = true;
            }
            if (this.zaiu) {
                GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 9, this.zafp), GoogleApiManager.this.zahy);
                return;
            }
            String strZan = this.zafp.zan();
            StringBuilder sb = new StringBuilder(String.valueOf(strZan).length() + 38);
            sb.append("API: ");
            sb.append(strZan);
            sb.append(" is not available on this device.");
            zac(new Status(17, sb.toString()));
        }

        @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
        public final void onConnectionSuspended(int i) {
            if (Looper.myLooper() == GoogleApiManager.this.handler.getLooper()) {
                zabh();
            } else {
                GoogleApiManager.this.handler.post(new zabk(this));
            }
        }

        public final boolean requiresSignIn() {
            return this.zain.requiresSignIn();
        }

        @WorkerThread
        public final void resume() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            if (this.zaiu) {
                connect();
            }
        }

        @Override // com.google.android.gms.common.api.internal.zar
        public final void zaa(ConnectionResult connectionResult, Api<?> api, boolean z) {
            if (Looper.myLooper() == GoogleApiManager.this.handler.getLooper()) {
                onConnectionFailed(connectionResult);
            } else {
                GoogleApiManager.this.handler.post(new zabl(this, connectionResult));
            }
        }

        @WorkerThread
        public final void zaa(com.google.android.gms.common.api.internal.zab zabVar) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            if (this.zain.isConnected()) {
                if (zab(zabVar)) {
                    zabo();
                    return;
                } else {
                    this.zaim.add(zabVar);
                    return;
                }
            }
            this.zaim.add(zabVar);
            ConnectionResult connectionResult = this.zaiw;
            if (connectionResult == null || !connectionResult.hasResolution()) {
                connect();
            } else {
                onConnectionFailed(this.zaiw);
            }
        }

        @WorkerThread
        public final void zaa(zak zakVar) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            this.zaiq.add(zakVar);
        }

        public final Api.Client zaab() {
            return this.zain;
        }

        @WorkerThread
        public final void zaav() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            if (this.zaiu) {
                zabn();
                zac(GoogleApiManager.this.zaid.isGooglePlayServicesAvailable(GoogleApiManager.this.zaic) == 18 ? new Status(8, "Connection timed out while waiting for Google Play services update to complete.") : new Status(8, "API failed to connect while resuming due to an unknown error."));
                this.zain.disconnect();
            }
        }

        @WorkerThread
        public final void zabj() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            zac(GoogleApiManager.zahw);
            this.zaip.zaah();
            for (ListenerHolder.ListenerKey listenerKey : (ListenerHolder.ListenerKey[]) this.zair.keySet().toArray(new ListenerHolder.ListenerKey[this.zair.size()])) {
                zaa(new zah(listenerKey, new TaskCompletionSource()));
            }
            zai(new ConnectionResult(4));
            if (this.zain.isConnected()) {
                this.zain.onUserSignOut(new zabm(this));
            }
        }

        public final Map<ListenerHolder.ListenerKey<?>, zabw> zabk() {
            return this.zair;
        }

        @WorkerThread
        public final void zabl() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            this.zaiw = null;
        }

        @WorkerThread
        public final ConnectionResult zabm() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            return this.zaiw;
        }

        @WorkerThread
        public final boolean zabp() {
            return zac(true);
        }

        final com.google.android.gms.signin.zad zabq() {
            zace zaceVar = this.zait;
            if (zaceVar == null) {
                return null;
            }
            return zaceVar.zabq();
        }

        @WorkerThread
        public final void zac(Status status) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            Iterator<com.google.android.gms.common.api.internal.zab> it = this.zaim.iterator();
            while (it.hasNext()) {
                it.next().zaa(status);
            }
            this.zaim.clear();
        }

        @WorkerThread
        public final void zag(@NonNull ConnectionResult connectionResult) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            this.zain.disconnect();
            onConnectionFailed(connectionResult);
        }
    }

    private static class zab {
        private final zai<?> zaja;
        private final Feature zajb;

        private zab(zai<?> zaiVar, Feature feature) {
            this.zaja = zaiVar;
            this.zajb = feature;
        }

        /* synthetic */ zab(zai zaiVar, Feature feature, zabi zabiVar) {
            this(zaiVar, feature);
        }

        public final boolean equals(Object obj) {
            if (obj != null && (obj instanceof zab)) {
                zab zabVar = (zab) obj;
                if (Objects.equal(this.zaja, zabVar.zaja) && Objects.equal(this.zajb, zabVar.zajb)) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hashCode(this.zaja, this.zajb);
        }

        public final String toString() {
            return Objects.toStringHelper(this).add("key", this.zaja).add("feature", this.zajb).toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class zac implements zach, BaseGmsClient.ConnectionProgressReportCallbacks {
        private final zai<?> zafp;
        private final Api.Client zain;
        private IAccountAccessor zajc = null;
        private Set<Scope> zajd = null;
        private boolean zaje = false;

        public zac(Api.Client client, zai<?> zaiVar) {
            this.zain = client;
            this.zafp = zaiVar;
        }

        static /* synthetic */ boolean zaa(zac zacVar, boolean z) {
            zacVar.zaje = true;
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @WorkerThread
        public final void zabr() {
            IAccountAccessor iAccountAccessor;
            if (!this.zaje || (iAccountAccessor = this.zajc) == null) {
                return;
            }
            this.zain.getRemoteService(iAccountAccessor, this.zajd);
        }

        @Override // com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks
        public final void onReportServiceBinding(@NonNull ConnectionResult connectionResult) {
            GoogleApiManager.this.handler.post(new zabo(this, connectionResult));
        }

        @Override // com.google.android.gms.common.api.internal.zach
        @WorkerThread
        public final void zaa(IAccountAccessor iAccountAccessor, Set<Scope> set) {
            if (iAccountAccessor == null || set == null) {
                Log.wtf("GoogleApiManager", "Received null response from onSignInSuccess", new Exception());
                zag(new ConnectionResult(4));
            } else {
                this.zajc = iAccountAccessor;
                this.zajd = set;
                zabr();
            }
        }

        @Override // com.google.android.gms.common.api.internal.zach
        @WorkerThread
        public final void zag(ConnectionResult connectionResult) {
            ((zaa) GoogleApiManager.this.zaih.get(this.zafp)).zag(connectionResult);
        }
    }

    @KeepForSdk
    private GoogleApiManager(Context context, Looper looper, GoogleApiAvailability googleApiAvailability) {
        this.zaic = context;
        this.handler = new com.google.android.gms.internal.base.zal(looper, this);
        this.zaid = googleApiAvailability;
        this.zaie = new GoogleApiAvailabilityCache(googleApiAvailability);
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(6));
    }

    @KeepForSdk
    public static void reportSignOut() {
        synchronized (lock) {
            if (zaib != null) {
                GoogleApiManager googleApiManager = zaib;
                googleApiManager.zaig.incrementAndGet();
                googleApiManager.handler.sendMessageAtFrontOfQueue(googleApiManager.handler.obtainMessage(10));
            }
        }
    }

    public static GoogleApiManager zab(Context context) {
        GoogleApiManager googleApiManager;
        synchronized (lock) {
            if (zaib == null) {
                HandlerThread handlerThread = new HandlerThread("GoogleApiHandler", 9);
                handlerThread.start();
                zaib = new GoogleApiManager(context.getApplicationContext(), handlerThread.getLooper(), GoogleApiAvailability.getInstance());
            }
            googleApiManager = zaib;
        }
        return googleApiManager;
    }

    @WorkerThread
    private final void zab(GoogleApi<?> googleApi) {
        Object objZak = googleApi.zak();
        zaa<?> zaaVar = this.zaih.get(objZak);
        if (zaaVar == null) {
            zaaVar = new zaa<>(googleApi);
            this.zaih.put((zai<?>) objZak, zaaVar);
        }
        if (zaaVar.requiresSignIn()) {
            this.zaik.add((zai<?>) objZak);
        }
        zaaVar.connect();
    }

    public static GoogleApiManager zabc() {
        GoogleApiManager googleApiManager;
        synchronized (lock) {
            Preconditions.checkNotNull(zaib, "Must guarantee manager is non-null before using getInstance");
            googleApiManager = zaib;
        }
        return googleApiManager;
    }

    /* JADX WARN: Code duplicated, block: B:46:0x0172  */
    /* JADX WARN: Code duplicated, block: B:47:0x01b8  */
    /*  JADX ERROR: JadxRuntimeException in pass: IfRegionVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r3v13 com.google.android.gms.common.api.internal.GoogleApiManager$zaa<?>, still in use, count: 2, list:
          (r3v13 com.google.android.gms.common.api.internal.GoogleApiManager$zaa<?>) from 0x0168: INVOKE (r3v13 com.google.android.gms.common.api.internal.GoogleApiManager$zaa<?>) VIRTUAL call: com.google.android.gms.common.api.internal.GoogleApiManager.zaa.getInstanceId():int A[MD:():int (m), WRAPPED]
          (r3v13 com.google.android.gms.common.api.internal.GoogleApiManager$zaa<?>) from 0x0170: PHI (r3 I:??) = 
          (r3v10 com.google.android.gms.common.api.internal.GoogleApiManager$zaa<?>)
          (r3v13 com.google.android.gms.common.api.internal.GoogleApiManager$zaa<?>)
         binds: [B:44:0x016f, B:84:0x0170] A[DONT_GENERATE, DONT_INLINE]
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:164)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:129)
        	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:93)
        	at jadx.core.dex.visitors.regions.TernaryMod.makeTernaryInsn(TernaryMod.java:132)
        	at jadx.core.dex.visitors.regions.TernaryMod.processRegion(TernaryMod.java:67)
        	at jadx.core.dex.visitors.regions.TernaryMod.enterRegion(TernaryMod.java:50)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:96)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:27)
        	at jadx.core.dex.visitors.regions.TernaryMod.process(TernaryMod.java:36)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.process(IfRegionVisitor.java:44)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.visit(IfRegionVisitor.java:30)
        */
    @Override // android.os.Handler.Callback
    @android.support.annotation.WorkerThread
    public boolean handleMessage(android.os.Message r8) {
        /*
            Method dump skipped, instruction units count: 756
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.GoogleApiManager.handleMessage(android.os.Message):boolean");
    }

    final void maybeSignOut() {
        this.zaig.incrementAndGet();
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(10));
    }

    final PendingIntent zaa(zai<?> zaiVar, int i) {
        com.google.android.gms.signin.zad zadVarZabq;
        zaa<?> zaaVar = this.zaih.get(zaiVar);
        if (zaaVar == null || (zadVarZabq = zaaVar.zabq()) == null) {
            return null;
        }
        return PendingIntent.getActivity(this.zaic, i, zadVarZabq.getSignInIntent(), 134217728);
    }

    public final <O extends Api.ApiOptions> Task<Boolean> zaa(@NonNull GoogleApi<O> googleApi, @NonNull ListenerHolder.ListenerKey<?> listenerKey) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        zah zahVar = new zah(listenerKey, taskCompletionSource);
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(13, new zabv(zahVar, this.zaig.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    public final <O extends Api.ApiOptions> Task<Void> zaa(@NonNull GoogleApi<O> googleApi, @NonNull RegisterListenerMethod<Api.AnyClient, ?> registerListenerMethod, @NonNull UnregisterListenerMethod<Api.AnyClient, ?> unregisterListenerMethod) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        zaf zafVar = new zaf(new zabw(registerListenerMethod, unregisterListenerMethod), taskCompletionSource);
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(8, new zabv(zafVar, this.zaig.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    public final Task<Map<zai<?>, String>> zaa(Iterable<? extends GoogleApi<?>> iterable) {
        zak zakVar = new zak(iterable);
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(2, zakVar));
        return zakVar.getTask();
    }

    public final void zaa(ConnectionResult connectionResult, int i) {
        if (zac(connectionResult, i)) {
            return;
        }
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(5, i, 0, connectionResult));
    }

    public final void zaa(GoogleApi<?> googleApi) {
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(7, googleApi));
    }

    public final <O extends Api.ApiOptions> void zaa(GoogleApi<O> googleApi, int i, BaseImplementation.ApiMethodImpl<? extends Result, Api.AnyClient> apiMethodImpl) {
        zae zaeVar = new zae(i, apiMethodImpl);
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(4, new zabv(zaeVar, this.zaig.get(), googleApi)));
    }

    public final <O extends Api.ApiOptions, ResultT> void zaa(GoogleApi<O> googleApi, int i, TaskApiCall<Api.AnyClient, ResultT> taskApiCall, TaskCompletionSource<ResultT> taskCompletionSource, StatusExceptionMapper statusExceptionMapper) {
        zag zagVar = new zag(i, taskApiCall, taskCompletionSource, statusExceptionMapper);
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(4, new zabv(zagVar, this.zaig.get(), googleApi)));
    }

    public final void zaa(@NonNull zaae zaaeVar) {
        synchronized (lock) {
            if (this.zaii != zaaeVar) {
                this.zaii = zaaeVar;
                this.zaij.clear();
            }
            this.zaij.addAll(zaaeVar.zaaj());
        }
    }

    final void zab(@NonNull zaae zaaeVar) {
        synchronized (lock) {
            if (this.zaii == zaaeVar) {
                this.zaii = null;
                this.zaij.clear();
            }
        }
    }

    public final int zabd() {
        return this.zaif.getAndIncrement();
    }

    public final Task<Boolean> zac(GoogleApi<?> googleApi) {
        zaaf zaafVar = new zaaf(googleApi.zak());
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(14, zaafVar));
        return zaafVar.zaal().getTask();
    }

    final boolean zac(ConnectionResult connectionResult, int i) {
        return this.zaid.zaa(this.zaic, connectionResult, i);
    }

    public final void zao() {
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(3));
    }
}
