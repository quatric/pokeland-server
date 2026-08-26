package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzi<TResult> implements zzq<TResult> {
    private final Object mLock = new Object();
    private final Executor zzd;

    @GuardedBy("mLock")
    private OnCompleteListener<TResult> zzl;

    public zzi(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zzd = executor;
        this.zzl = onCompleteListener;
    }

    @Override // com.google.android.gms.tasks.zzq
    public final void cancel() {
        synchronized (this.mLock) {
            this.zzl = null;
        }
    }

    @Override // com.google.android.gms.tasks.zzq
    public final void onComplete(@NonNull Task<TResult> task) {
        synchronized (this.mLock) {
            if (this.zzl == null) {
                return;
            }
            this.zzd.execute(new zzj(this, task));
        }
    }
}
