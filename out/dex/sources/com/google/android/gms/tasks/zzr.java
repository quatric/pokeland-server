package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.ArrayDeque;
import java.util.Queue;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzr<TResult> {
    private final Object mLock = new Object();

    @GuardedBy("mLock")
    private Queue<zzq<TResult>> zzt;

    @GuardedBy("mLock")
    private boolean zzu;

    zzr() {
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public final void zza(@NonNull Task<TResult> task) {
        zzq<TResult> zzqVarPoll;
        synchronized (this.mLock) {
            if (this.zzt != null && !this.zzu) {
                this.zzu = true;
                while (true) {
                    synchronized (this.mLock) {
                        zzqVarPoll = this.zzt.poll();
                        if (zzqVarPoll == null) {
                            this.zzu = false;
                            return;
                        }
                    }
                    zzqVarPoll.onComplete(task);
                }
            }
        }
    }

    public final void zza(@NonNull zzq<TResult> zzqVar) {
        synchronized (this.mLock) {
            if (this.zzt == null) {
                this.zzt = new ArrayDeque();
            }
            this.zzt.add(zzqVar);
        }
    }
}
