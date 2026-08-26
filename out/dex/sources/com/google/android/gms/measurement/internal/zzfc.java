package com.google.android.gms.measurement.internal;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzfc extends zzge {
    private static final AtomicLong zznj = new AtomicLong(Long.MIN_VALUE);
    private zzfg zzna;
    private zzfg zznb;
    private final PriorityBlockingQueue<zzfh<?>> zznc;
    private final BlockingQueue<zzfh<?>> zznd;
    private final Thread.UncaughtExceptionHandler zzne;
    private final Thread.UncaughtExceptionHandler zznf;
    private final Object zzng;
    private final Semaphore zznh;
    private volatile boolean zzni;

    zzfc(zzfj zzfjVar) {
        super(zzfjVar);
        this.zzng = new Object();
        this.zznh = new Semaphore(2);
        this.zznc = new PriorityBlockingQueue<>();
        this.zznd = new LinkedBlockingQueue();
        this.zzne = new zzfe(this, "Thread death: Uncaught exception on worker thread");
        this.zznf = new zzfe(this, "Thread death: Uncaught exception on network thread");
    }

    static /* synthetic */ zzfg zza(zzfc zzfcVar, zzfg zzfgVar) {
        zzfcVar.zzna = null;
        return null;
    }

    private final void zza(zzfh<?> zzfhVar) {
        synchronized (this.zzng) {
            this.zznc.add(zzfhVar);
            if (this.zzna == null) {
                this.zzna = new zzfg(this, "Measurement Worker", this.zznc);
                this.zzna.setUncaughtExceptionHandler(this.zzne);
                this.zzna.start();
            } else {
                this.zzna.zzhr();
            }
        }
    }

    static /* synthetic */ zzfg zzb(zzfc zzfcVar, zzfg zzfgVar) {
        zzfcVar.zznb = null;
        return null;
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    final <T> T zza(AtomicReference<T> atomicReference, long j, String str, Runnable runnable) {
        synchronized (atomicReference) {
            zzaa().zza(runnable);
            try {
                atomicReference.wait(15000L);
            } catch (InterruptedException unused) {
                zzeh zzehVarZzgn = zzab().zzgn();
                String strValueOf = String.valueOf(str);
                zzehVarZzgn.zzao(strValueOf.length() != 0 ? "Interrupted waiting for ".concat(strValueOf) : new String("Interrupted waiting for "));
                return null;
            }
        }
        T t = atomicReference.get();
        if (t == null) {
            zzeh zzehVarZzgn2 = zzab().zzgn();
            String strValueOf2 = String.valueOf(str);
            zzehVarZzgn2.zzao(strValueOf2.length() != 0 ? "Timed out waiting for ".concat(strValueOf2) : new String("Timed out waiting for "));
        }
        return t;
    }

    public final <V> Future<V> zza(Callable<V> callable) throws IllegalStateException {
        zzbi();
        Preconditions.checkNotNull(callable);
        zzfh<?> zzfhVar = new zzfh<>(this, (Callable<?>) callable, false, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzna) {
            if (!this.zznc.isEmpty()) {
                zzab().zzgn().zzao("Callable skipped the worker queue.");
            }
            zzfhVar.run();
        } else {
            zza(zzfhVar);
        }
        return zzfhVar;
    }

    public final void zza(Runnable runnable) throws IllegalStateException {
        zzbi();
        Preconditions.checkNotNull(runnable);
        zza(new zzfh<>(this, runnable, false, "Task exception on worker thread"));
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ zzfc zzaa() {
        return super.zzaa();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ zzef zzab() {
        return super.zzab();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzeo zzac() {
        return super.zzac();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzs zzad() {
        return super.zzad();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ zzr zzae() {
        return super.zzae();
    }

    public final <V> Future<V> zzb(Callable<V> callable) throws IllegalStateException {
        zzbi();
        Preconditions.checkNotNull(callable);
        zzfh<?> zzfhVar = new zzfh<>(this, (Callable<?>) callable, true, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzna) {
            zzfhVar.run();
        } else {
            zza(zzfhVar);
        }
        return zzfhVar;
    }

    public final void zzb(Runnable runnable) throws IllegalStateException {
        zzbi();
        Preconditions.checkNotNull(runnable);
        zzfh<?> zzfhVar = new zzfh<>(this, runnable, false, "Task exception on network thread");
        synchronized (this.zzng) {
            this.zznd.add(zzfhVar);
            if (this.zznb == null) {
                this.zznb = new zzfg(this, "Measurement Network", this.zznd);
                this.zznb.setUncaughtExceptionHandler(this.zznf);
                this.zznb.start();
            } else {
                this.zznb.zzhr();
            }
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzge
    protected final boolean zzbk() {
        return false;
    }

    public final boolean zzhp() {
        return Thread.currentThread() == this.zzna;
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzl() {
        super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzm() {
        super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final void zzn() {
        if (Thread.currentThread() != this.zznb) {
            throw new IllegalStateException("Call expected from network thread");
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final void zzo() {
        if (Thread.currentThread() != this.zzna) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzac zzw() {
        return super.zzw();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ Clock zzx() {
        return super.zzx();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzed zzy() {
        return super.zzy();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzjs zzz() {
        return super.zzz();
    }
}
