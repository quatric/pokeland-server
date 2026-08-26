package com.google.firebase.iid;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.platforminfo.UserAgentPublisher;
import java.io.IOException;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzs implements MessagingChannel {
    private final Executor executor;
    private final FirebaseApp zzau;
    private final zzan zzav;
    private final zzau zzbq;
    private final UserAgentPublisher zzbr;

    @VisibleForTesting
    private zzs(FirebaseApp firebaseApp, zzan zzanVar, Executor executor, zzau zzauVar, UserAgentPublisher userAgentPublisher) {
        this.zzau = firebaseApp;
        this.zzav = zzanVar;
        this.zzbq = zzauVar;
        this.executor = executor;
        this.zzbr = userAgentPublisher;
    }

    zzs(FirebaseApp firebaseApp, zzan zzanVar, Executor executor, UserAgentPublisher userAgentPublisher) {
        this(firebaseApp, zzanVar, executor, new zzau(firebaseApp.getApplicationContext(), zzanVar), userAgentPublisher);
    }

    private final Task<Bundle> zza(String str, String str2, String str3, final Bundle bundle) {
        bundle.putString("scope", str3);
        bundle.putString("sender", str2);
        bundle.putString("subtype", str2);
        bundle.putString("appid", str);
        bundle.putString("gmp_app_id", this.zzau.getOptions().getApplicationId());
        bundle.putString("gmsv", Integer.toString(this.zzav.zzaf()));
        bundle.putString("osv", Integer.toString(Build.VERSION.SDK_INT));
        bundle.putString("app_ver", this.zzav.zzad());
        bundle.putString("app_ver_name", this.zzav.zzae());
        bundle.putString("cliv", "fiid-12451000");
        bundle.putString("Firebase-Client", this.zzbr.getUserAgent());
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.executor.execute(new Runnable(this, bundle, taskCompletionSource) { // from class: com.google.firebase.iid.zzr
            private final zzs zzbn;
            private final Bundle zzbo;
            private final TaskCompletionSource zzbp;

            {
                this.zzbn = this;
                this.zzbo = bundle;
                this.zzbp = taskCompletionSource;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zzbn.zza(this.zzbo, this.zzbp);
            }
        });
        return taskCompletionSource.getTask();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String zza(Bundle bundle) throws IOException {
        if (bundle == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        String string = bundle.getString("registration_id");
        if (string != null) {
            return string;
        }
        String string2 = bundle.getString("unregistered");
        if (string2 != null) {
            return string2;
        }
        String string3 = bundle.getString("error");
        if ("RST".equals(string3)) {
            throw new IOException("INSTANCE_ID_RESET");
        }
        if (string3 != null) {
            throw new IOException(string3);
        }
        String strValueOf = String.valueOf(bundle);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 21);
        sb.append("Unexpected response: ");
        sb.append(strValueOf);
        Log.w("FirebaseInstanceId", sb.toString(), new Throwable());
        throw new IOException("SERVICE_NOT_AVAILABLE");
    }

    private final <T> Task<Void> zzb(Task<T> task) {
        return task.continueWith(zzh.zzd(), new zzu(this));
    }

    private final Task<String> zzc(Task<Bundle> task) {
        return task.continueWith(this.executor, new zzt(this));
    }

    @Override // com.google.firebase.iid.MessagingChannel
    public final Task<Void> ackMessage(String str) {
        return null;
    }

    @Override // com.google.firebase.iid.MessagingChannel
    public final Task<Void> buildChannel(String str, String str2) {
        return Tasks.forResult(null);
    }

    @Override // com.google.firebase.iid.MessagingChannel
    public final Task<Void> deleteInstanceId(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("iid-operation", "delete");
        bundle.putString("delete", "1");
        return zzb(zzc(zza(str, "*", "*", bundle)));
    }

    @Override // com.google.firebase.iid.MessagingChannel
    public final Task<Void> deleteToken(String str, String str2, String str3, String str4) {
        Bundle bundle = new Bundle();
        bundle.putString("delete", "1");
        return zzb(zzc(zza(str, str3, str4, bundle)));
    }

    @Override // com.google.firebase.iid.MessagingChannel
    public final Task<String> getToken(String str, String str2, String str3, String str4) {
        return zzc(zza(str, str3, str4, new Bundle()));
    }

    @Override // com.google.firebase.iid.MessagingChannel
    public final boolean isAvailable() {
        return this.zzav.zzac() != 0;
    }

    @Override // com.google.firebase.iid.MessagingChannel
    public final boolean isChannelBuilt() {
        return true;
    }

    @Override // com.google.firebase.iid.MessagingChannel
    public final boolean needsRefresh() {
        return false;
    }

    @Override // com.google.firebase.iid.MessagingChannel
    public final Task<Void> subscribeToTopic(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        String strValueOf = String.valueOf(str3);
        bundle.putString("gcm.topic", strValueOf.length() != 0 ? "/topics/".concat(strValueOf) : new String("/topics/"));
        String strValueOf2 = String.valueOf(str3);
        return zzb(zzc(zza(str, str2, strValueOf2.length() != 0 ? "/topics/".concat(strValueOf2) : new String("/topics/"), bundle)));
    }

    @Override // com.google.firebase.iid.MessagingChannel
    public final Task<Void> unsubscribeFromTopic(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        String strValueOf = String.valueOf(str3);
        bundle.putString("gcm.topic", strValueOf.length() != 0 ? "/topics/".concat(strValueOf) : new String("/topics/"));
        bundle.putString("delete", "1");
        String strValueOf2 = String.valueOf(str3);
        return zzb(zzc(zza(str, str2, strValueOf2.length() != 0 ? "/topics/".concat(strValueOf2) : new String("/topics/"), bundle)));
    }

    final /* synthetic */ void zza(Bundle bundle, TaskCompletionSource taskCompletionSource) {
        try {
            taskCompletionSource.setResult(this.zzbq.zzc(bundle));
        } catch (IOException e) {
            taskCompletionSource.setException(e);
        }
    }
}
