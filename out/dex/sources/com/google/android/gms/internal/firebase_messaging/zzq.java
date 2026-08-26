package com.google.android.gms.internal.firebase_messaging;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzq extends zzm {
    private final zzp zzp = new zzp();

    zzq() {
    }

    @Override // com.google.android.gms.internal.firebase_messaging.zzm
    public final void zza(Throwable th, Throwable th2) {
        if (th2 == th) {
            throw new IllegalArgumentException("Self suppression is not allowed.", th2);
        }
        if (th2 == null) {
            throw new NullPointerException("The suppressed exception cannot be null.");
        }
        this.zzp.zza(th, true).add(th2);
    }
}
