package com.google.firebase.analytics;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzb implements Callable<String> {
    private final /* synthetic */ FirebaseAnalytics zzaca;

    zzb(FirebaseAnalytics firebaseAnalytics) {
        this.zzaca = firebaseAnalytics;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ String call() throws Exception {
        String strZzi = this.zzaca.zzi();
        if (strZzi != null) {
            return strZzi;
        }
        String appInstanceId = this.zzaca.zzl ? this.zzaca.zzabu.getAppInstanceId() : this.zzaca.zzj.zzq().zzy(120000L);
        if (appInstanceId == null) {
            throw new TimeoutException();
        }
        this.zzaca.zzbg(appInstanceId);
        return appInstanceId;
    }
}
