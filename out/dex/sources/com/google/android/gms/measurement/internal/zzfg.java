package com.google.android.gms.measurement.internal;

import android.os.Process;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.BlockingQueue;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfg extends Thread {
    private final /* synthetic */ zzfc zznt;
    private final Object zznu;
    private final BlockingQueue<zzfh<?>> zznv;

    public zzfg(zzfc zzfcVar, String str, BlockingQueue<zzfh<?>> blockingQueue) {
        this.zznt = zzfcVar;
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(blockingQueue);
        this.zznu = new Object();
        this.zznv = blockingQueue;
        setName(str);
    }

    private final void zza(InterruptedException interruptedException) {
        this.zznt.zzab().zzgn().zza(String.valueOf(getName()).concat(" was interrupted"), interruptedException);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        boolean z = false;
        while (!z) {
            try {
                this.zznt.zznh.acquire();
                z = true;
            } catch (InterruptedException e) {
                zza(e);
            }
        }
        try {
            int threadPriority = Process.getThreadPriority(Process.myTid());
            while (true) {
                zzfh<?> zzfhVarPoll = this.zznv.poll();
                if (zzfhVarPoll == null) {
                    synchronized (this.zznu) {
                        if (this.zznv.peek() == null && !this.zznt.zzni) {
                            try {
                                this.zznu.wait(30000L);
                            } catch (InterruptedException e2) {
                                zza(e2);
                            }
                        }
                    }
                    synchronized (this.zznt.zzng) {
                        if (this.zznv.peek() == null) {
                            break;
                        }
                    }
                } else {
                    Process.setThreadPriority(zzfhVarPoll.zznx ? threadPriority : 10);
                    zzfhVarPoll.run();
                }
            }
            synchronized (this.zznt.zzng) {
                this.zznt.zznh.release();
                this.zznt.zzng.notifyAll();
                if (this == this.zznt.zzna) {
                    zzfc.zza(this.zznt, null);
                } else if (this == this.zznt.zznb) {
                    zzfc.zzb(this.zznt, null);
                } else {
                    this.zznt.zzab().zzgk().zzao("Current scheduler thread is neither worker nor network");
                }
            }
        } catch (Throwable th) {
            synchronized (this.zznt.zzng) {
                this.zznt.zznh.release();
                this.zznt.zzng.notifyAll();
                if (this == this.zznt.zzna) {
                    zzfc.zza(this.zznt, null);
                } else if (this == this.zznt.zznb) {
                    zzfc.zzb(this.zznt, null);
                } else {
                    this.zznt.zzab().zzgk().zzao("Current scheduler thread is neither worker nor network");
                }
                throw th;
            }
        }
    }

    public final void zzhr() {
        synchronized (this.zznu) {
            this.zznu.notifyAll();
        }
    }
}
