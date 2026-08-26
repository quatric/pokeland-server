package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzc<TResult, TContinuationResult> implements zzq<TResult> {
    private final Executor zzd;
    private final Continuation<TResult, TContinuationResult> zze;
    private final zzu<TContinuationResult> zzf;

    public zzc(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation, @NonNull zzu<TContinuationResult> zzuVar) {
        this.zzd = executor;
        this.zze = continuation;
        this.zzf = zzuVar;
    }

    @Override // com.google.android.gms.tasks.zzq
    public final void cancel() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.tasks.zzq
    public final void onComplete(@NonNull Task<TResult> task) {
        this.zzd.execute(new zzd(this, task));
    }
}
