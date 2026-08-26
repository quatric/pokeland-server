package com.google.android.gms.measurement.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfh<V> extends FutureTask<V> implements Comparable<zzfh> {
    private final String zzns;
    private final /* synthetic */ zzfc zznt;
    private final long zznw;
    final boolean zznx;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzfh(zzfc zzfcVar, Runnable runnable, boolean z, String str) {
        super(runnable, null);
        this.zznt = zzfcVar;
        Preconditions.checkNotNull(str);
        this.zznw = zzfc.zznj.getAndIncrement();
        this.zzns = str;
        this.zznx = false;
        if (this.zznw == LongCompanionObject.MAX_VALUE) {
            zzfcVar.zzab().zzgk().zzao("Tasks index overflow");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzfh(zzfc zzfcVar, Callable<V> callable, boolean z, String str) {
        super(callable);
        this.zznt = zzfcVar;
        Preconditions.checkNotNull(str);
        this.zznw = zzfc.zznj.getAndIncrement();
        this.zzns = str;
        this.zznx = z;
        if (this.zznw == LongCompanionObject.MAX_VALUE) {
            zzfcVar.zzab().zzgk().zzao("Tasks index overflow");
        }
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(@NonNull zzfh zzfhVar) {
        zzfh zzfhVar2 = zzfhVar;
        boolean z = this.zznx;
        if (z != zzfhVar2.zznx) {
            return z ? -1 : 1;
        }
        long j = this.zznw;
        long j2 = zzfhVar2.zznw;
        if (j < j2) {
            return -1;
        }
        if (j > j2) {
            return 1;
        }
        this.zznt.zzab().zzgl().zza("Two tasks share the same index. index", Long.valueOf(this.zznw));
        return 0;
    }

    @Override // java.util.concurrent.FutureTask
    protected final void setException(Throwable th) {
        this.zznt.zzab().zzgk().zza(this.zzns, th);
        if (th instanceof zzff) {
            Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), th);
        }
        super.setException(th);
    }
}
