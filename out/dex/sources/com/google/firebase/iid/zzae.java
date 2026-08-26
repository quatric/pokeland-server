package com.google.firebase.iid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.stats.ConnectionTracker;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzae implements ServiceConnection {

    @GuardedBy("this")
    int state;
    final Messenger zzcd;
    zzah zzce;

    @GuardedBy("this")
    final Queue<zzaj<?>> zzcf;

    @GuardedBy("this")
    final SparseArray<zzaj<?>> zzcg;
    final /* synthetic */ zzac zzch;

    private zzae(zzac zzacVar) {
        this.zzch = zzacVar;
        this.state = 0;
        this.zzcd = new Messenger(new com.google.android.gms.internal.firebase_messaging.zze(Looper.getMainLooper(), new Handler.Callback(this) { // from class: com.google.firebase.iid.zzad
            private final zzae zzcc;

            {
                this.zzcc = this;
            }

            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return this.zzcc.zza(message);
            }
        }));
        this.zzcf = new ArrayDeque();
        this.zzcg = new SparseArray<>();
    }

    private final void zzy() {
        this.zzch.zzbz.execute(new Runnable(this) { // from class: com.google.firebase.iid.zzaf
            private final zzae zzcc;

            {
                this.zzcc = this;
            }

            /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
            @Override // java.lang.Runnable
            public final void run() {
                final zzaj<?> zzajVarPoll;
                final zzae zzaeVar = this.zzcc;
                while (true) {
                    synchronized (zzaeVar) {
                        if (zzaeVar.state != 2) {
                            return;
                        }
                        if (zzaeVar.zzcf.isEmpty()) {
                            zzaeVar.zzz();
                            return;
                        } else {
                            zzajVarPoll = zzaeVar.zzcf.poll();
                            zzaeVar.zzcg.put(zzajVarPoll.zzck, zzajVarPoll);
                            zzaeVar.zzch.zzbz.schedule(new Runnable(zzaeVar, zzajVarPoll) { // from class: com.google.firebase.iid.zzai
                                private final zzae zzcc;
                                private final zzaj zzcj;

                                {
                                    this.zzcc = zzaeVar;
                                    this.zzcj = zzajVarPoll;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.zzcc.zza(this.zzcj.zzck);
                                }
                            }, 30L, TimeUnit.SECONDS);
                        }
                    }
                    if (Log.isLoggable("MessengerIpcClient", 3)) {
                        String strValueOf = String.valueOf(zzajVarPoll);
                        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 8);
                        sb.append("Sending ");
                        sb.append(strValueOf);
                        Log.d("MessengerIpcClient", sb.toString());
                    }
                    Context context = zzaeVar.zzch.zzag;
                    Messenger messenger = zzaeVar.zzcd;
                    Message messageObtain = Message.obtain();
                    messageObtain.what = zzajVarPoll.what;
                    messageObtain.arg1 = zzajVarPoll.zzck;
                    messageObtain.replyTo = messenger;
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("oneWay", zzajVarPoll.zzab());
                    bundle.putString("pkg", context.getPackageName());
                    bundle.putBundle("data", zzajVarPoll.zzcm);
                    messageObtain.setData(bundle);
                    try {
                        zzaeVar.zzce.send(messageObtain);
                    } catch (RemoteException e) {
                        zzaeVar.zza(2, e.getMessage());
                    }
                }
            }
        });
    }

    @Override // android.content.ServiceConnection
    public final synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (Log.isLoggable("MessengerIpcClient", 2)) {
            Log.v("MessengerIpcClient", "Service connected");
        }
        if (iBinder == null) {
            zza(0, "Null service connection");
            return;
        }
        try {
            this.zzce = new zzah(iBinder);
            this.state = 2;
            zzy();
        } catch (RemoteException e) {
            zza(0, e.getMessage());
        }
    }

    @Override // android.content.ServiceConnection
    public final synchronized void onServiceDisconnected(ComponentName componentName) {
        if (Log.isLoggable("MessengerIpcClient", 2)) {
            Log.v("MessengerIpcClient", "Service disconnected");
        }
        zza(2, "Service disconnected");
    }

    final synchronized void zza(int i) {
        zzaj<?> zzajVar = this.zzcg.get(i);
        if (zzajVar != null) {
            StringBuilder sb = new StringBuilder(31);
            sb.append("Timing out request: ");
            sb.append(i);
            Log.w("MessengerIpcClient", sb.toString());
            this.zzcg.remove(i);
            zzajVar.zza(new zzam(3, "Timed out waiting for response"));
            zzz();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    final synchronized void zza(int i, String str) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String strValueOf = String.valueOf(str);
            Log.d("MessengerIpcClient", strValueOf.length() != 0 ? "Disconnected: ".concat(strValueOf) : new String("Disconnected: "));
        }
        int i2 = this.state;
        if (i2 == 0) {
            throw new IllegalStateException();
        }
        if (i2 != 1 && i2 != 2) {
            if (i2 == 3) {
                this.state = 4;
                return;
            } else {
                if (i2 == 4) {
                    return;
                }
                int i3 = this.state;
                StringBuilder sb = new StringBuilder(26);
                sb.append("Unknown state: ");
                sb.append(i3);
                throw new IllegalStateException(sb.toString());
            }
        }
        if (Log.isLoggable("MessengerIpcClient", 2)) {
            Log.v("MessengerIpcClient", "Unbinding service");
        }
        this.state = 4;
        ConnectionTracker.getInstance().unbindService(this.zzch.zzag, this);
        zzam zzamVar = new zzam(i, str);
        Iterator<zzaj<?>> it = this.zzcf.iterator();
        while (it.hasNext()) {
            it.next().zza(zzamVar);
        }
        this.zzcf.clear();
        for (int i4 = 0; i4 < this.zzcg.size(); i4++) {
            this.zzcg.valueAt(i4).zza(zzamVar);
        }
        this.zzcg.clear();
    }

    final boolean zza(Message message) {
        int i = message.arg1;
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            StringBuilder sb = new StringBuilder(41);
            sb.append("Received response to request: ");
            sb.append(i);
            Log.d("MessengerIpcClient", sb.toString());
        }
        synchronized (this) {
            zzaj<?> zzajVar = this.zzcg.get(i);
            if (zzajVar == null) {
                StringBuilder sb2 = new StringBuilder(50);
                sb2.append("Received response for unknown request: ");
                sb2.append(i);
                Log.w("MessengerIpcClient", sb2.toString());
                return true;
            }
            this.zzcg.remove(i);
            zzz();
            Bundle data = message.getData();
            if (data.getBoolean("unsupported", false)) {
                zzajVar.zza(new zzam(4, "Not supported by GmsCore"));
            } else {
                zzajVar.zzb(data);
            }
            return true;
        }
    }

    final synchronized void zzaa() {
        if (this.state == 1) {
            zza(1, "Timed out while binding");
        }
    }

    final synchronized boolean zzb(zzaj zzajVar) {
        int i = this.state;
        if (i == 0) {
            this.zzcf.add(zzajVar);
            Preconditions.checkState(this.state == 0);
            if (Log.isLoggable("MessengerIpcClient", 2)) {
                Log.v("MessengerIpcClient", "Starting bind to GmsCore");
            }
            this.state = 1;
            Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
            intent.setPackage("com.google.android.gms");
            if (ConnectionTracker.getInstance().bindService(this.zzch.zzag, intent, this, 1)) {
                this.zzch.zzbz.schedule(new Runnable(this) { // from class: com.google.firebase.iid.zzag
                    private final zzae zzcc;

                    {
                        this.zzcc = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        this.zzcc.zzaa();
                    }
                }, 30L, TimeUnit.SECONDS);
            } else {
                zza(0, "Unable to bind to service");
            }
            return true;
        }
        if (i == 1) {
            this.zzcf.add(zzajVar);
            return true;
        }
        if (i == 2) {
            this.zzcf.add(zzajVar);
            zzy();
            return true;
        }
        if (i != 3 && i != 4) {
            int i2 = this.state;
            StringBuilder sb = new StringBuilder(26);
            sb.append("Unknown state: ");
            sb.append(i2);
            throw new IllegalStateException(sb.toString());
        }
        return false;
    }

    final synchronized void zzz() {
        if (this.state == 2 && this.zzcf.isEmpty() && this.zzcg.size() == 0) {
            if (Log.isLoggable("MessengerIpcClient", 2)) {
                Log.v("MessengerIpcClient", "Finished handling requests, unbinding");
            }
            this.state = 3;
            ConnectionTracker.getInstance().unbindService(this.zzch.zzag, this);
        }
    }
}
