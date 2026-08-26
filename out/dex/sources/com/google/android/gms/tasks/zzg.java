package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzg<TResult> implements zzq<TResult> {
    private final Object mLock = new Object();
    private final Executor zzd;

    @GuardedBy("mLock")
    private OnCanceledListener zzj;

    public zzg(@NonNull Executor executor, @NonNull OnCanceledListener onCanceledListener) {
        this.zzd = executor;
        this.zzj = onCanceledListener;
    }

    @Override // com.google.android.gms.tasks.zzq
    public final void cancel() {
        synchronized (this.mLock) {
            this.zzj = null;
        }
    }

    @Override // com.google.android.gms.tasks.zzq
    public final void onComplete(@NonNull Task task) {
        if (task.isCanceled()) {
            synchronized (this.mLock) {
                if (this.zzj == null) {
                    return;
                }
                this.zzd.execute(new zzh(this));
            }
        }
    }
}
