package com.google.firebase.iid;

import android.support.annotation.GuardedBy;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzaz {

    @GuardedBy("itself")
    private final zzav zzar;

    @GuardedBy("this")
    private int zzdp = 0;

    @GuardedBy("this")
    private final Map<Integer, TaskCompletionSource<Void>> zzdq = new ArrayMap();

    zzaz(zzav zzavVar) {
        this.zzar = zzavVar;
    }

    @WorkerThread
    private static boolean zza(FirebaseInstanceId firebaseInstanceId, String str) {
        String[] strArrSplit = str.split("!");
        if (strArrSplit.length == 2) {
            String str2 = strArrSplit[0];
            String str3 = strArrSplit[1];
            byte b = -1;
            try {
                int iHashCode = str2.hashCode();
                if (iHashCode != 83) {
                    if (iHashCode == 85 && str2.equals("U")) {
                        b = 1;
                    }
                } else if (str2.equals("S")) {
                    b = 0;
                }
                if (b == 0) {
                    firebaseInstanceId.zzb(str3);
                    if (FirebaseInstanceId.zzm()) {
                        Log.d("FirebaseInstanceId", "subscribe operation succeeded");
                    }
                } else if (b == 1) {
                    firebaseInstanceId.zzc(str3);
                    if (FirebaseInstanceId.zzm()) {
                        Log.d("FirebaseInstanceId", "unsubscribe operation succeeded");
                    }
                }
            } catch (IOException e) {
                String strValueOf = String.valueOf(e.getMessage());
                Log.e("FirebaseInstanceId", strValueOf.length() != 0 ? "Topic sync failed: ".concat(strValueOf) : new String("Topic sync failed: "));
                return false;
            }
        }
        return true;
    }

    @GuardedBy("this")
    @Nullable
    private final String zzap() {
        String strZzai;
        synchronized (this.zzar) {
            strZzai = this.zzar.zzai();
        }
        if (TextUtils.isEmpty(strZzai)) {
            return null;
        }
        String[] strArrSplit = strZzai.split(",");
        if (strArrSplit.length <= 1 || TextUtils.isEmpty(strArrSplit[1])) {
            return null;
        }
        return strArrSplit[1];
    }

    private final synchronized boolean zzk(String str) {
        synchronized (this.zzar) {
            String strZzai = this.zzar.zzai();
            String strValueOf = String.valueOf(str);
            if (!strZzai.startsWith(strValueOf.length() != 0 ? ",".concat(strValueOf) : new String(","))) {
                return false;
            }
            String strValueOf2 = String.valueOf(str);
            this.zzar.zzf(strZzai.substring((strValueOf2.length() != 0 ? ",".concat(strValueOf2) : new String(",")).length()));
            return true;
        }
    }

    final synchronized Task<Void> zza(String str) {
        String strZzai;
        TaskCompletionSource<Void> taskCompletionSource;
        synchronized (this.zzar) {
            strZzai = this.zzar.zzai();
            zzav zzavVar = this.zzar;
            StringBuilder sb = new StringBuilder(String.valueOf(strZzai).length() + 1 + String.valueOf(str).length());
            sb.append(strZzai);
            sb.append(",");
            sb.append(str);
            zzavVar.zzf(sb.toString());
        }
        taskCompletionSource = new TaskCompletionSource<>();
        this.zzdq.put(Integer.valueOf(this.zzdp + (TextUtils.isEmpty(strZzai) ? 0 : strZzai.split(",").length - 1)), taskCompletionSource);
        return taskCompletionSource.getTask();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    final synchronized boolean zzao() {
        return zzap() != null;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @WorkerThread
    final boolean zzc(FirebaseInstanceId firebaseInstanceId) {
        TaskCompletionSource<Void> taskCompletionSourceRemove;
        while (true) {
            synchronized (this) {
                String strZzap = zzap();
                if (strZzap == null) {
                    if (FirebaseInstanceId.zzm()) {
                        Log.d("FirebaseInstanceId", "topic sync succeeded");
                    }
                    return true;
                }
                if (!zza(firebaseInstanceId, strZzap)) {
                    return false;
                }
                synchronized (this) {
                    taskCompletionSourceRemove = this.zzdq.remove(Integer.valueOf(this.zzdp));
                    zzk(strZzap);
                    this.zzdp++;
                }
                if (taskCompletionSourceRemove != null) {
                    taskCompletionSourceRemove.setResult(null);
                }
            }
        }
    }
}
