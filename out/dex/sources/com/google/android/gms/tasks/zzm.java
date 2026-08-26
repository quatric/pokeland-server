package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzm<TResult> implements zzq<TResult> {
    private final Object mLock = new Object();
    private final Executor zzd;

    @GuardedBy("mLock")
    private OnSuccessListener<? super TResult> zzp;

    public zzm(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzd = executor;
        this.zzp = onSuccessListener;
    }

    @Override // com.google.android.gms.tasks.zzq
    public final void cancel() {
        synchronized (this.mLock) {
            this.zzp = null;
        }
    }

    @Override // com.google.android.gms.tasks.zzq
    public final void onComplete(@NonNull Task<TResult> task) {
        if (task.isSuccessful()) {
            synchronized (this.mLock) {
                if (this.zzp == null) {
                    return;
                }
                this.zzd.execute(new zzn(this, task));
            }
        }
    }
}
