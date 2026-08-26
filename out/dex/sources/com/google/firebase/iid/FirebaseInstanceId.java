package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.util.concurrent.NamedThreadFactory;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.FirebaseApp;
import com.google.firebase.events.Event;
import com.google.firebase.events.EventHandler;
import com.google.firebase.events.Subscriber;
import com.google.firebase.messaging.BuildConfig;
import com.google.firebase.platforminfo.UserAgentPublisher;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class FirebaseInstanceId {
    private static final long zzaq = TimeUnit.HOURS.toSeconds(8);
    private static zzav zzar;

    @VisibleForTesting
    @GuardedBy("FirebaseInstanceId.class")
    private static ScheduledExecutorService zzas;
    private final Executor zzat;
    private final FirebaseApp zzau;
    private final zzan zzav;
    private MessagingChannel zzaw;
    private final zzaq zzax;
    private final zzaz zzay;

    @GuardedBy("this")
    private boolean zzaz;
    private final zza zzba;

    /* JADX INFO: Access modifiers changed from: private */
    class zza {
        private final Subscriber zzbh;

        @GuardedBy("this")
        @Nullable
        private EventHandler<DataCollectionDefaultChange> zzbi;
        private final boolean zzbg = zzu();

        @GuardedBy("this")
        @Nullable
        private Boolean zzbj = zzt();

        zza(Subscriber subscriber) {
            this.zzbh = subscriber;
            if (this.zzbj == null && this.zzbg) {
                this.zzbi = new EventHandler(this) { // from class: com.google.firebase.iid.zzq
                    private final FirebaseInstanceId.zza zzbm;

                    {
                        this.zzbm = this;
                    }

                    @Override // com.google.firebase.events.EventHandler
                    public final void handle(Event event) {
                        FirebaseInstanceId.zza zzaVar = this.zzbm;
                        synchronized (zzaVar) {
                            if (zzaVar.isEnabled()) {
                                FirebaseInstanceId.this.zzh();
                            }
                        }
                    }
                };
                subscriber.subscribe(DataCollectionDefaultChange.class, this.zzbi);
            }
        }

        @Nullable
        private final Boolean zzt() {
            ApplicationInfo applicationInfo;
            Context applicationContext = FirebaseInstanceId.this.zzau.getApplicationContext();
            SharedPreferences sharedPreferences = applicationContext.getSharedPreferences(BuildConfig.APPLICATION_ID, 0);
            if (sharedPreferences.contains("auto_init")) {
                return Boolean.valueOf(sharedPreferences.getBoolean("auto_init", false));
            }
            try {
                PackageManager packageManager = applicationContext.getPackageManager();
                if (packageManager == null || (applicationInfo = packageManager.getApplicationInfo(applicationContext.getPackageName(), 128)) == null || applicationInfo.metaData == null || !applicationInfo.metaData.containsKey("firebase_messaging_auto_init_enabled")) {
                    return null;
                }
                return Boolean.valueOf(applicationInfo.metaData.getBoolean("firebase_messaging_auto_init_enabled"));
            } catch (PackageManager.NameNotFoundException unused) {
                return null;
            }
        }

        private final boolean zzu() {
            try {
                Class.forName("com.google.firebase.messaging.FirebaseMessaging");
                return true;
            } catch (ClassNotFoundException unused) {
                Context applicationContext = FirebaseInstanceId.this.zzau.getApplicationContext();
                Intent intent = new Intent("com.google.firebase.MESSAGING_EVENT");
                intent.setPackage(applicationContext.getPackageName());
                ResolveInfo resolveInfoResolveService = applicationContext.getPackageManager().resolveService(intent, 0);
                return (resolveInfoResolveService == null || resolveInfoResolveService.serviceInfo == null) ? false : true;
            }
        }

        final synchronized boolean isEnabled() {
            if (this.zzbj != null) {
                return this.zzbj.booleanValue();
            }
            return this.zzbg && FirebaseInstanceId.this.zzau.isDataCollectionDefaultEnabled();
        }

        final synchronized void setEnabled(boolean z) {
            if (this.zzbi != null) {
                this.zzbh.unsubscribe(DataCollectionDefaultChange.class, this.zzbi);
                this.zzbi = null;
            }
            SharedPreferences.Editor editorEdit = FirebaseInstanceId.this.zzau.getApplicationContext().getSharedPreferences(BuildConfig.APPLICATION_ID, 0).edit();
            editorEdit.putBoolean("auto_init", z);
            editorEdit.apply();
            if (z) {
                FirebaseInstanceId.this.zzh();
            }
            this.zzbj = Boolean.valueOf(z);
        }
    }

    FirebaseInstanceId(FirebaseApp firebaseApp, Subscriber subscriber, UserAgentPublisher userAgentPublisher) {
        this(firebaseApp, new zzan(firebaseApp.getApplicationContext()), zzh.zze(), zzh.zze(), subscriber, userAgentPublisher);
    }

    private FirebaseInstanceId(FirebaseApp firebaseApp, zzan zzanVar, Executor executor, Executor executor2, Subscriber subscriber, UserAgentPublisher userAgentPublisher) {
        this.zzaz = false;
        if (zzan.zza(firebaseApp) == null) {
            throw new IllegalStateException("FirebaseInstanceId failed to initialize, FirebaseApp is missing project ID");
        }
        synchronized (FirebaseInstanceId.class) {
            if (zzar == null) {
                zzar = new zzav(firebaseApp.getApplicationContext());
            }
        }
        this.zzau = firebaseApp;
        this.zzav = zzanVar;
        if (this.zzaw == null) {
            MessagingChannel messagingChannel = (MessagingChannel) firebaseApp.get(MessagingChannel.class);
            if (messagingChannel == null || !messagingChannel.isAvailable()) {
                this.zzaw = new zzs(firebaseApp, zzanVar, executor, userAgentPublisher);
            } else {
                this.zzaw = messagingChannel;
            }
        }
        this.zzaw = this.zzaw;
        this.zzat = executor2;
        this.zzay = new zzaz(zzar);
        this.zzba = new zza(subscriber);
        this.zzax = new zzaq(executor);
        if (this.zzba.isEnabled()) {
            zzh();
        }
    }

    public static FirebaseInstanceId getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    @Keep
    public static FirebaseInstanceId getInstance(@NonNull FirebaseApp firebaseApp) {
        return (FirebaseInstanceId) firebaseApp.get(FirebaseInstanceId.class);
    }

    private final synchronized void startSync() {
        if (!this.zzaz) {
            zza(0L);
        }
    }

    private final Task<InstanceIdResult> zza(final String str, String str2) {
        final String strZzd = zzd(str2);
        return Tasks.forResult(null).continueWithTask(this.zzat, new Continuation(this, str, strZzd) { // from class: com.google.firebase.iid.zzo
            private final FirebaseInstanceId zzbb;
            private final String zzbc;
            private final String zzbd;

            {
                this.zzbb = this;
                this.zzbc = str;
                this.zzbd = strZzd;
            }

            @Override // com.google.android.gms.tasks.Continuation
            public final Object then(Task task) {
                return this.zzbb.zza(this.zzbc, this.zzbd, task);
            }
        });
    }

    private final <T> T zza(Task<T> task) throws IOException {
        try {
            return (T) Tasks.await(task, 30000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | TimeoutException unused) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IOException) {
                if ("INSTANCE_ID_RESET".equals(cause.getMessage())) {
                    zzn();
                }
                throw ((IOException) cause);
            }
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            throw new IOException(e);
        }
    }

    static void zza(Runnable runnable, long j) {
        synchronized (FirebaseInstanceId.class) {
            if (zzas == null) {
                zzas = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("FirebaseInstanceId"));
            }
            zzas.schedule(runnable, j, TimeUnit.SECONDS);
        }
    }

    @VisibleForTesting
    @Nullable
    private static zzay zzb(String str, String str2) {
        return zzar.zzb("", str, str2);
    }

    private static String zzd(String str) {
        return (str.isEmpty() || str.equalsIgnoreCase("fcm") || str.equalsIgnoreCase("gcm")) ? "*" : str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzh() {
        zzay zzayVarZzk = zzk();
        if (zzr() || zza(zzayVarZzk) || this.zzay.zzao()) {
            startSync();
        }
    }

    private static String zzj() {
        return zzan.zza(zzar.zzg("").getKeyPair());
    }

    static boolean zzm() {
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            return true;
        }
        return Build.VERSION.SDK_INT == 23 && Log.isLoggable("FirebaseInstanceId", 3);
    }

    @WorkerThread
    public void deleteInstanceId() throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        zza(this.zzaw.deleteInstanceId(zzj()));
        zzn();
    }

    @WorkerThread
    public void deleteToken(String str, String str2) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        String strZzd = zzd(str2);
        zza(this.zzaw.deleteToken(zzj(), zzay.zzb(zzb(str, strZzd)), str, strZzd));
        zzar.zzc("", str, strZzd);
    }

    public long getCreationTime() {
        return zzar.zzg("").getCreationTime();
    }

    @WorkerThread
    public String getId() {
        zzh();
        return zzj();
    }

    @NonNull
    public Task<InstanceIdResult> getInstanceId() {
        return zza(zzan.zza(this.zzau), "*");
    }

    @Nullable
    @Deprecated
    public String getToken() {
        zzay zzayVarZzk = zzk();
        if (this.zzaw.needsRefresh() || zza(zzayVarZzk)) {
            startSync();
        }
        return zzay.zzb(zzayVarZzk);
    }

    @WorkerThread
    public String getToken(String str, String str2) throws IOException {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            return ((InstanceIdResult) zza(zza(str, str2))).getToken();
        }
        throw new IOException("MAIN_THREAD");
    }

    public final synchronized Task<Void> zza(String str) {
        Task<Void> taskZza;
        taskZza = this.zzay.zza(str);
        startSync();
        return taskZza;
    }

    final /* synthetic */ Task zza(final String str, final String str2, Task task) throws Exception {
        final String strZzj = zzj();
        zzay zzayVarZzb = zzb(str, str2);
        if (!this.zzaw.needsRefresh() && !zza(zzayVarZzb)) {
            return Tasks.forResult(new zzx(strZzj, zzayVarZzb.zzbv));
        }
        final String strZzb = zzay.zzb(zzayVarZzb);
        return this.zzax.zza(str, str2, new zzar(this, strZzj, strZzb, str, str2) { // from class: com.google.firebase.iid.zzn
            private final FirebaseInstanceId zzbb;
            private final String zzbc;
            private final String zzbd;
            private final String zzbe;
            private final String zzbf;

            {
                this.zzbb = this;
                this.zzbc = strZzj;
                this.zzbd = strZzb;
                this.zzbe = str;
                this.zzbf = str2;
            }

            @Override // com.google.firebase.iid.zzar
            public final Task zzs() {
                return this.zzbb.zza(this.zzbc, this.zzbd, this.zzbe, this.zzbf);
            }
        });
    }

    final /* synthetic */ Task zza(final String str, String str2, final String str3, final String str4) {
        return this.zzaw.getToken(str, str2, str3, str4).onSuccessTask(this.zzat, new SuccessContinuation(this, str3, str4, str) { // from class: com.google.firebase.iid.zzp
            private final FirebaseInstanceId zzbb;
            private final String zzbc;
            private final String zzbd;
            private final String zzbe;

            {
                this.zzbb = this;
                this.zzbc = str3;
                this.zzbd = str4;
                this.zzbe = str;
            }

            @Override // com.google.android.gms.tasks.SuccessContinuation
            public final Task then(Object obj) {
                return this.zzbb.zzb(this.zzbc, this.zzbd, this.zzbe, (String) obj);
            }
        });
    }

    final synchronized void zza(long j) {
        zza(new zzax(this, this.zzav, this.zzay, Math.min(Math.max(30L, j << 1), zzaq)), j);
        this.zzaz = true;
    }

    final synchronized void zza(boolean z) {
        this.zzaz = z;
    }

    final boolean zza(@Nullable zzay zzayVar) {
        return zzayVar == null || zzayVar.zzj(this.zzav.zzad());
    }

    final /* synthetic */ Task zzb(String str, String str2, String str3, String str4) throws Exception {
        zzar.zza("", str, str2, str4, this.zzav.zzad());
        return Tasks.forResult(new zzx(str3, str4));
    }

    final void zzb(String str) throws IOException {
        zzay zzayVarZzk = zzk();
        if (zza(zzayVarZzk)) {
            throw new IOException("token not available");
        }
        zza(this.zzaw.subscribeToTopic(zzj(), zzayVarZzk.zzbv, str));
    }

    @VisibleForTesting
    public final void zzb(boolean z) {
        this.zzba.setEnabled(z);
    }

    final void zzc(String str) throws IOException {
        zzay zzayVarZzk = zzk();
        if (zza(zzayVarZzk)) {
            throw new IOException("token not available");
        }
        zza(this.zzaw.unsubscribeFromTopic(zzj(), zzayVarZzk.zzbv, str));
    }

    final FirebaseApp zzi() {
        return this.zzau;
    }

    @Nullable
    final zzay zzk() {
        return zzb(zzan.zza(this.zzau), "*");
    }

    final String zzl() throws IOException {
        return getToken(zzan.zza(this.zzau), "*");
    }

    final synchronized void zzn() {
        zzar.zzaj();
        if (this.zzba.isEnabled()) {
            startSync();
        }
    }

    final boolean zzo() {
        return this.zzaw.isAvailable();
    }

    final void zzp() {
        zzar.zzh("");
        startSync();
    }

    @VisibleForTesting
    public final boolean zzq() {
        return this.zzba.isEnabled();
    }

    final boolean zzr() {
        return this.zzaw.needsRefresh();
    }
}
