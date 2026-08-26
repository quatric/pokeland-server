package com.google.firebase.iid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PowerManager;
import android.util.Log;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzax implements Runnable {
    private final zzaz zzay;
    private final long zzdk;
    private final PowerManager.WakeLock zzdl = ((PowerManager) getContext().getSystemService("power")).newWakeLock(1, "fiid-sync");
    private final FirebaseInstanceId zzdm;

    @VisibleForTesting
    zzax(FirebaseInstanceId firebaseInstanceId, zzan zzanVar, zzaz zzazVar, long j) {
        this.zzdm = firebaseInstanceId;
        this.zzay = zzazVar;
        this.zzdk = j;
        this.zzdl.setReferenceCounted(false);
    }

    @VisibleForTesting
    private final boolean zzam() {
        zzay zzayVarZzk = this.zzdm.zzk();
        if (!this.zzdm.zzr() && !this.zzdm.zza(zzayVarZzk)) {
            return true;
        }
        try {
            String strZzl = this.zzdm.zzl();
            if (strZzl == null) {
                Log.e("FirebaseInstanceId", "Token retrieval failed: null");
                return false;
            }
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                Log.d("FirebaseInstanceId", "Token successfully retrieved");
            }
            if (zzayVarZzk == null || (zzayVarZzk != null && !strZzl.equals(zzayVarZzk.zzbv))) {
                Intent intent = new Intent("com.google.firebase.messaging.NEW_TOKEN");
                intent.putExtra("token", strZzl);
                Context context = getContext();
                Intent intent2 = new Intent(context, (Class<?>) FirebaseInstanceIdReceiver.class);
                intent2.setAction("com.google.firebase.MESSAGING_EVENT");
                intent2.putExtra("wrapped_intent", intent);
                context.sendBroadcast(intent2);
            }
            return true;
        } catch (IOException | SecurityException e) {
            String strValueOf = String.valueOf(e.getMessage());
            Log.e("FirebaseInstanceId", strValueOf.length() != 0 ? "Token retrieval failed: ".concat(strValueOf) : new String("Token retrieval failed: "));
            return false;
        }
    }

    final Context getContext() {
        return this.zzdm.zzi().getApplicationContext();
    }

    @Override // java.lang.Runnable
    @SuppressLint({"Wakelock"})
    public final void run() {
        boolean zZzd;
        try {
            if (zzaw.zzak().zzd(getContext())) {
                this.zzdl.acquire();
            }
            this.zzdm.zza(true);
            if (!this.zzdm.zzo()) {
                this.zzdm.zza(false);
            } else {
                if (zzaw.zzak().zze(getContext()) && !zzan()) {
                    new zzba(this).zzaq();
                    if (zZzd) {
                        return;
                    } else {
                        return;
                    }
                }
                if (zzam() && this.zzay.zzc(this.zzdm)) {
                    this.zzdm.zza(false);
                } else {
                    this.zzdm.zza(this.zzdk);
                }
            }
        } finally {
            if (zzaw.zzak().zzd(getContext())) {
                this.zzdl.release();
            }
        }
    }

    final boolean zzan() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
