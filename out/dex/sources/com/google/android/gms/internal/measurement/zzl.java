package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzl extends zzo {
    private final AtomicReference<Bundle> zzp = new AtomicReference<>();
    private boolean zzq;

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0003, code lost:
    
        r4 = r4.get("r");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static <T> T zza(android.os.Bundle r4, java.lang.Class<T> r5) {
        /*
            r0 = 0
            if (r4 == 0) goto L38
            java.lang.String r1 = "r"
            java.lang.Object r4 = r4.get(r1)
            if (r4 == 0) goto L38
            java.lang.Object r4 = r5.cast(r4)     // Catch: java.lang.ClassCastException -> L10
            return r4
        L10:
            r0 = move-exception
            java.lang.String r5 = r5.getCanonicalName()
            java.lang.Class r4 = r4.getClass()
            java.lang.String r4 = r4.getCanonicalName()
            java.lang.String r1 = "Unexpected object type. Expected, Received"
            java.lang.String r2 = ": %s, %s"
            java.lang.String r1 = r1.concat(r2)
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            r2[r3] = r5
            r5 = 1
            r2[r5] = r4
            java.lang.String r4 = java.lang.String.format(r1, r2)
            java.lang.String r5 = "AM"
            android.util.Log.w(r5, r4, r0)
            throw r0
        L38:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzl.zza(android.os.Bundle, java.lang.Class):java.lang.Object");
    }

    public final String zza(long j) {
        return (String) zza(zzb(j), String.class);
    }

    public final Bundle zzb(long j) {
        Bundle bundle;
        synchronized (this.zzp) {
            if (!this.zzq) {
                try {
                    this.zzp.wait(j);
                } catch (InterruptedException unused) {
                    return null;
                }
            }
            bundle = this.zzp.get();
        }
        return bundle;
    }

    @Override // com.google.android.gms.internal.measurement.zzp
    public final void zzb(Bundle bundle) {
        synchronized (this.zzp) {
            try {
                this.zzp.set(bundle);
                this.zzq = true;
                this.zzp.notify();
            } catch (Throwable th) {
                this.zzp.notify();
                throw th;
            }
        }
    }
}
