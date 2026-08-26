package com.google.android.gms.tasks;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzu<TResult> extends Task<TResult> {

    @GuardedBy("mLock")
    private TResult zzaa;

    @GuardedBy("mLock")
    private Exception zzab;

    @GuardedBy("mLock")
    private boolean zzy;
    private volatile boolean zzz;
    private final Object mLock = new Object();
    private final zzr<TResult> zzx = new zzr<>();

    private static class zza extends LifecycleCallback {
        private final List<WeakReference<zzq<?>>> zzac;

        private zza(LifecycleFragment lifecycleFragment) {
            super(lifecycleFragment);
            this.zzac = new ArrayList();
            this.mLifecycleFragment.addCallback("TaskOnStopCallback", this);
        }

        public static zza zza(Activity activity) {
            LifecycleFragment fragment = getFragment(activity);
            zza zzaVar = (zza) fragment.getCallbackOrNull("TaskOnStopCallback", zza.class);
            return zzaVar == null ? new zza(fragment) : zzaVar;
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        @Override // com.google.android.gms.common.api.internal.LifecycleCallback
        @MainThread
        public void onStop() {
            synchronized (this.zzac) {
                Iterator<WeakReference<zzq<?>>> it = this.zzac.iterator();
                while (it.hasNext()) {
                    zzq<?> zzqVar = it.next().get();
                    if (zzqVar != null) {
                        zzqVar.cancel();
                    }
                }
                this.zzac.clear();
            }
        }

        public final <T> void zzb(zzq<T> zzqVar) {
            synchronized (this.zzac) {
                this.zzac.add(new WeakReference<>(zzqVar));
            }
        }
    }

    zzu() {
    }

    @GuardedBy("mLock")
    private final void zzb() {
        Preconditions.checkState(this.zzy, "Task is not yet complete");
    }

    @GuardedBy("mLock")
    private final void zzc() {
        Preconditions.checkState(!this.zzy, "Task is already complete");
    }

    @GuardedBy("mLock")
    private final void zzd() {
        if (this.zzz) {
            throw new CancellationException("Task is already canceled.");
        }
    }

    private final void zze() {
        synchronized (this.mLock) {
            if (this.zzy) {
                this.zzx.zza(this);
            }
        }
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnCanceledListener(@NonNull Activity activity, @NonNull OnCanceledListener onCanceledListener) {
        zzg zzgVar = new zzg(TaskExecutors.MAIN_THREAD, onCanceledListener);
        this.zzx.zza(zzgVar);
        zza.zza(activity).zzb(zzgVar);
        zze();
        return this;
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnCanceledListener(@NonNull OnCanceledListener onCanceledListener) {
        return addOnCanceledListener(TaskExecutors.MAIN_THREAD, onCanceledListener);
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnCanceledListener(@NonNull Executor executor, @NonNull OnCanceledListener onCanceledListener) {
        this.zzx.zza(new zzg(executor, onCanceledListener));
        zze();
        return this;
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Activity activity, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        zzi zziVar = new zzi(TaskExecutors.MAIN_THREAD, onCompleteListener);
        this.zzx.zza(zziVar);
        zza.zza(activity).zzb(zziVar);
        zze();
        return this;
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull OnCompleteListener<TResult> onCompleteListener) {
        return addOnCompleteListener(TaskExecutors.MAIN_THREAD, onCompleteListener);
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zzx.zza(new zzi(executor, onCompleteListener));
        zze();
        return this;
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        zzk zzkVar = new zzk(TaskExecutors.MAIN_THREAD, onFailureListener);
        this.zzx.zza(zzkVar);
        zza.zza(activity).zzb(zzkVar);
        zze();
        return this;
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return addOnFailureListener(TaskExecutors.MAIN_THREAD, onFailureListener);
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzx.zza(new zzk(executor, onFailureListener));
        zze();
        return this;
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        zzm zzmVar = new zzm(TaskExecutors.MAIN_THREAD, onSuccessListener);
        this.zzx.zza(zzmVar);
        zza.zza(activity).zzb(zzmVar);
        zze();
        return this;
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        return addOnSuccessListener(TaskExecutors.MAIN_THREAD, onSuccessListener);
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzx.zza(new zzm(executor, onSuccessListener));
        zze();
        return this;
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Continuation<TResult, TContinuationResult> continuation) {
        return continueWith(TaskExecutors.MAIN_THREAD, continuation);
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation) {
        zzu zzuVar = new zzu();
        this.zzx.zza(new zzc(executor, continuation, zzuVar));
        zze();
        return zzuVar;
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        return continueWithTask(TaskExecutors.MAIN_THREAD, continuation);
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        zzu zzuVar = new zzu();
        this.zzx.zza(new zze(executor, continuation, zzuVar));
        zze();
        return zzuVar;
    }

    @Override // com.google.android.gms.tasks.Task
    @Nullable
    public final Exception getException() {
        Exception exc;
        synchronized (this.mLock) {
            exc = this.zzab;
        }
        return exc;
    }

    @Override // com.google.android.gms.tasks.Task
    public final TResult getResult() {
        TResult tresult;
        synchronized (this.mLock) {
            zzb();
            zzd();
            if (this.zzab != null) {
                throw new RuntimeExecutionException(this.zzab);
            }
            tresult = this.zzaa;
        }
        return tresult;
    }

    @Override // com.google.android.gms.tasks.Task
    public final <X extends Throwable> TResult getResult(@NonNull Class<X> cls) throws Throwable {
        TResult tresult;
        synchronized (this.mLock) {
            zzb();
            zzd();
            if (cls.isInstance(this.zzab)) {
                throw cls.cast(this.zzab);
            }
            if (this.zzab != null) {
                throw new RuntimeExecutionException(this.zzab);
            }
            tresult = this.zzaa;
        }
        return tresult;
    }

    @Override // com.google.android.gms.tasks.Task
    public final boolean isCanceled() {
        return this.zzz;
    }

    @Override // com.google.android.gms.tasks.Task
    public final boolean isComplete() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzy;
        }
        return z;
    }

    @Override // com.google.android.gms.tasks.Task
    public final boolean isSuccessful() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzy && !this.zzz && this.zzab == null;
        }
        return z;
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> onSuccessTask(@NonNull SuccessContinuation<TResult, TContinuationResult> successContinuation) {
        return onSuccessTask(TaskExecutors.MAIN_THREAD, successContinuation);
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> onSuccessTask(Executor executor, SuccessContinuation<TResult, TContinuationResult> successContinuation) {
        zzu zzuVar = new zzu();
        this.zzx.zza(new zzo(executor, successContinuation, zzuVar));
        zze();
        return zzuVar;
    }

    public final void setException(@NonNull Exception exc) {
        Preconditions.checkNotNull(exc, "Exception must not be null");
        synchronized (this.mLock) {
            zzc();
            this.zzy = true;
            this.zzab = exc;
        }
        this.zzx.zza(this);
    }

    public final void setResult(TResult tresult) {
        synchronized (this.mLock) {
            zzc();
            this.zzy = true;
            this.zzaa = tresult;
        }
        this.zzx.zza(this);
    }

    public final boolean trySetException(@NonNull Exception exc) {
        Preconditions.checkNotNull(exc, "Exception must not be null");
        synchronized (this.mLock) {
            if (this.zzy) {
                return false;
            }
            this.zzy = true;
            this.zzab = exc;
            this.zzx.zza(this);
            return true;
        }
    }

    public final boolean trySetResult(TResult tresult) {
        synchronized (this.mLock) {
            if (this.zzy) {
                return false;
            }
            this.zzy = true;
            this.zzaa = tresult;
            this.zzx.zza(this);
            return true;
        }
    }

    public final boolean zza() {
        synchronized (this.mLock) {
            if (this.zzy) {
                return false;
            }
            this.zzy = true;
            this.zzz = true;
            this.zzx.zza(this);
            return true;
        }
    }
}
