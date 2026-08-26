package com.google.firebase.iid;

import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzaq {
    private final Executor executor;

    @GuardedBy("this")
    private final Map<Pair<String, String>, Task<InstanceIdResult>> zzcs = new ArrayMap();

    zzaq(Executor executor) {
        this.executor = executor;
    }

    final /* synthetic */ Task zza(Pair pair, Task task) throws Exception {
        synchronized (this) {
            this.zzcs.remove(pair);
        }
        return task;
    }

    final synchronized Task<InstanceIdResult> zza(String str, String str2, zzar zzarVar) {
        final Pair<String, String> pair = new Pair<>(str, str2);
        Task<InstanceIdResult> task = this.zzcs.get(pair);
        if (task != null) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String strValueOf = String.valueOf(pair);
                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 29);
                sb.append("Joining ongoing request for: ");
                sb.append(strValueOf);
                Log.d("FirebaseInstanceId", sb.toString());
            }
            return task;
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String strValueOf2 = String.valueOf(pair);
            StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 24);
            sb2.append("Making new request for: ");
            sb2.append(strValueOf2);
            Log.d("FirebaseInstanceId", sb2.toString());
        }
        Task taskContinueWithTask = zzarVar.zzs().continueWithTask(this.executor, new Continuation(this, pair) { // from class: com.google.firebase.iid.zzas
            private final zzaq zzcu;
            private final Pair zzcv;

            {
                this.zzcu = this;
                this.zzcv = pair;
            }

            @Override // com.google.android.gms.tasks.Continuation
            public final Object then(Task task2) {
                return this.zzcu.zza(this.zzcv, task2);
            }
        });
        this.zzcs.put(pair, (Task<InstanceIdResult>) taskContinueWithTask);
        return taskContinueWithTask;
    }
}
