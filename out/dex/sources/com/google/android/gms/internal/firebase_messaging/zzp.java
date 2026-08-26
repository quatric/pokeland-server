package com.google.android.gms.internal.firebase_messaging;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzp {
    private final ConcurrentHashMap<zzo, List<Throwable>> zzn = new ConcurrentHashMap<>(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> zzo = new ReferenceQueue<>();

    zzp() {
    }

    public final List<Throwable> zza(Throwable th, boolean z) {
        Reference<? extends Throwable> referencePoll = this.zzo.poll();
        while (referencePoll != null) {
            this.zzn.remove(referencePoll);
            referencePoll = this.zzo.poll();
        }
        List<Throwable> list = this.zzn.get(new zzo(th, null));
        if (list != null) {
            return list;
        }
        Vector vector = new Vector(2);
        List<Throwable> listPutIfAbsent = this.zzn.putIfAbsent(new zzo(th, this.zzo), vector);
        return listPutIfAbsent == null ? vector : listPutIfAbsent;
    }
}
