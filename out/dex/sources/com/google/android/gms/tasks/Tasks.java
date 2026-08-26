package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Tasks {

    private static final class zza implements zzb {
        private final CountDownLatch zzaf;

        private zza() {
            this.zzaf = new CountDownLatch(1);
        }

        /* synthetic */ zza(zzv zzvVar) {
            this();
        }

        public final void await() throws InterruptedException {
            this.zzaf.await();
        }

        public final boolean await(long j, TimeUnit timeUnit) throws InterruptedException {
            return this.zzaf.await(j, timeUnit);
        }

        @Override // com.google.android.gms.tasks.OnCanceledListener
        public final void onCanceled() {
            this.zzaf.countDown();
        }

        @Override // com.google.android.gms.tasks.OnFailureListener
        public final void onFailure(@NonNull Exception exc) {
            this.zzaf.countDown();
        }

        @Override // com.google.android.gms.tasks.OnSuccessListener
        public final void onSuccess(Object obj) {
            this.zzaf.countDown();
        }
    }

    interface zzb extends OnCanceledListener, OnFailureListener, OnSuccessListener<Object> {
    }

    private static final class zzc implements zzb {
        private final Object mLock = new Object();
        private final zzu<Void> zza;

        @GuardedBy("mLock")
        private Exception zzab;
        private final int zzag;

        @GuardedBy("mLock")
        private int zzah;

        @GuardedBy("mLock")
        private int zzai;

        @GuardedBy("mLock")
        private int zzaj;

        @GuardedBy("mLock")
        private boolean zzak;

        public zzc(int i, zzu<Void> zzuVar) {
            this.zzag = i;
            this.zza = zzuVar;
        }

        @GuardedBy("mLock")
        private final void zzf() {
            int i = this.zzah;
            int i2 = this.zzai;
            int i3 = i + i2 + this.zzaj;
            int i4 = this.zzag;
            if (i3 == i4) {
                if (this.zzab == null) {
                    if (this.zzak) {
                        this.zza.zza();
                        return;
                    } else {
                        this.zza.setResult(null);
                        return;
                    }
                }
                zzu<Void> zzuVar = this.zza;
                StringBuilder sb = new StringBuilder(54);
                sb.append(i2);
                sb.append(" out of ");
                sb.append(i4);
                sb.append(" underlying tasks failed");
                zzuVar.setException(new ExecutionException(sb.toString(), this.zzab));
            }
        }

        @Override // com.google.android.gms.tasks.OnCanceledListener
        public final void onCanceled() {
            synchronized (this.mLock) {
                this.zzaj++;
                this.zzak = true;
                zzf();
            }
        }

        @Override // com.google.android.gms.tasks.OnFailureListener
        public final void onFailure(@NonNull Exception exc) {
            synchronized (this.mLock) {
                this.zzai++;
                this.zzab = exc;
                zzf();
            }
        }

        @Override // com.google.android.gms.tasks.OnSuccessListener
        public final void onSuccess(Object obj) {
            synchronized (this.mLock) {
                this.zzah++;
                zzf();
            }
        }
    }

    private Tasks() {
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task) throws ExecutionException, InterruptedException {
        Preconditions.checkNotMainThread();
        Preconditions.checkNotNull(task, "Task must not be null");
        if (task.isComplete()) {
            return (TResult) zzb(task);
        }
        zza zzaVar = new zza(null);
        zza(task, zzaVar);
        zzaVar.await();
        return (TResult) zzb(task);
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task, long j, @NonNull TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        Preconditions.checkNotMainThread();
        Preconditions.checkNotNull(task, "Task must not be null");
        Preconditions.checkNotNull(timeUnit, "TimeUnit must not be null");
        if (task.isComplete()) {
            return (TResult) zzb(task);
        }
        zza zzaVar = new zza(null);
        zza(task, zzaVar);
        if (zzaVar.await(j, timeUnit)) {
            return (TResult) zzb(task);
        }
        throw new TimeoutException("Timed out waiting for Task");
    }

    public static <TResult> Task<TResult> call(@NonNull Callable<TResult> callable) {
        return call(TaskExecutors.MAIN_THREAD, callable);
    }

    public static <TResult> Task<TResult> call(@NonNull Executor executor, @NonNull Callable<TResult> callable) {
        Preconditions.checkNotNull(executor, "Executor must not be null");
        Preconditions.checkNotNull(callable, "Callback must not be null");
        zzu zzuVar = new zzu();
        executor.execute(new zzv(zzuVar, callable));
        return zzuVar;
    }

    public static <TResult> Task<TResult> forCanceled() {
        zzu zzuVar = new zzu();
        zzuVar.zza();
        return zzuVar;
    }

    public static <TResult> Task<TResult> forException(@NonNull Exception exc) {
        zzu zzuVar = new zzu();
        zzuVar.setException(exc);
        return zzuVar;
    }

    public static <TResult> Task<TResult> forResult(TResult tresult) {
        zzu zzuVar = new zzu();
        zzuVar.setResult(tresult);
        return zzuVar;
    }

    public static Task<Void> whenAll(Collection<? extends Task<?>> collection) {
        if (collection.isEmpty()) {
            return forResult(null);
        }
        Iterator<? extends Task<?>> it = collection.iterator();
        while (it.hasNext()) {
            if (it.next() == null) {
                throw new NullPointerException("null tasks are not accepted");
            }
        }
        zzu zzuVar = new zzu();
        zzc zzcVar = new zzc(collection.size(), zzuVar);
        Iterator<? extends Task<?>> it2 = collection.iterator();
        while (it2.hasNext()) {
            zza(it2.next(), zzcVar);
        }
        return zzuVar;
    }

    public static Task<Void> whenAll(Task<?>... taskArr) {
        return taskArr.length == 0 ? forResult(null) : whenAll(Arrays.asList(taskArr));
    }

    public static Task<List<Task<?>>> whenAllComplete(Collection<? extends Task<?>> collection) {
        return whenAll(collection).continueWithTask(new zzx(collection));
    }

    public static Task<List<Task<?>>> whenAllComplete(Task<?>... taskArr) {
        return whenAllComplete(Arrays.asList(taskArr));
    }

    public static <TResult> Task<List<TResult>> whenAllSuccess(Collection<? extends Task<?>> collection) {
        return (Task<List<TResult>>) whenAll(collection).continueWith(new zzw(collection));
    }

    public static <TResult> Task<List<TResult>> whenAllSuccess(Task<?>... taskArr) {
        return whenAllSuccess(Arrays.asList(taskArr));
    }

    private static void zza(Task<?> task, zzb zzbVar) {
        task.addOnSuccessListener(TaskExecutors.zzw, zzbVar);
        task.addOnFailureListener(TaskExecutors.zzw, zzbVar);
        task.addOnCanceledListener(TaskExecutors.zzw, zzbVar);
    }

    private static <TResult> TResult zzb(Task<TResult> task) throws ExecutionException {
        if (task.isSuccessful()) {
            return task.getResult();
        }
        if (task.isCanceled()) {
            throw new CancellationException("Task is already canceled");
        }
        throw new ExecutionException(task.getException());
    }
}
