package com.google.android.gms.measurement.internal;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.MainThread;
import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzez {
    private final zzfa zzmw;

    public zzez(zzfa zzfaVar) {
        Preconditions.checkNotNull(zzfaVar);
        this.zzmw = zzfaVar;
    }

    public static boolean zzl(Context context) {
        ActivityInfo receiverInfo;
        Preconditions.checkNotNull(context);
        try {
            PackageManager packageManager = context.getPackageManager();
            return (packageManager == null || (receiverInfo = packageManager.getReceiverInfo(new ComponentName(context, "com.google.android.gms.measurement.AppMeasurementReceiver"), 0)) == null || !receiverInfo.enabled) ? false : true;
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    @MainThread
    public final void onReceive(Context context, Intent intent) {
        zzfj zzfjVarZza = zzfj.zza(context, (com.google.android.gms.internal.measurement.zzx) null);
        zzef zzefVarZzab = zzfjVarZza.zzab();
        if (intent == null) {
            zzefVarZzab.zzgn().zzao("Receiver called with null intent");
            return;
        }
        zzfjVarZza.zzae();
        String action = intent.getAction();
        zzefVarZzab.zzgs().zza("Local receiver got", action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            Intent className = new Intent().setClassName(context, "com.google.android.gms.measurement.AppMeasurementService");
            className.setAction("com.google.android.gms.measurement.UPLOAD");
            zzefVarZzab.zzgs().zzao("Starting wakeful intent.");
            this.zzmw.doStartService(context, className);
            return;
        }
        if ("com.android.vending.INSTALL_REFERRER".equals(action)) {
            try {
                zzfjVarZza.zzaa().zza(new zzey(this, zzfjVarZza, zzefVarZzab));
            } catch (Exception e) {
                zzefVarZzab.zzgn().zza("Install Referrer Reporter encountered a problem", e);
            }
            BroadcastReceiver.PendingResult pendingResultDoGoAsync = this.zzmw.doGoAsync();
            String stringExtra = intent.getStringExtra("referrer");
            if (stringExtra == null) {
                zzefVarZzab.zzgs().zzao("Install referrer extras are null");
                if (pendingResultDoGoAsync != null) {
                    pendingResultDoGoAsync.finish();
                    return;
                }
                return;
            }
            zzefVarZzab.zzgq().zza("Install referrer extras are", stringExtra);
            if (!stringExtra.contains("?")) {
                String strValueOf = String.valueOf(stringExtra);
                stringExtra = strValueOf.length() != 0 ? "?".concat(strValueOf) : new String("?");
            }
            Bundle bundleZza = zzfjVarZza.zzz().zza(Uri.parse(stringExtra));
            if (bundleZza == null) {
                zzefVarZzab.zzgs().zzao("No campaign defined in install referrer broadcast");
                if (pendingResultDoGoAsync != null) {
                    pendingResultDoGoAsync.finish();
                    return;
                }
                return;
            }
            long longExtra = intent.getLongExtra("referrer_timestamp_seconds", 0L) * 1000;
            if (longExtra == 0) {
                zzefVarZzab.zzgn().zzao("Install referrer is missing timestamp");
            }
            zzfjVarZza.zzaa().zza(new zzfb(this, zzfjVarZza, longExtra, bundleZza, context, zzefVarZzab, pendingResultDoGoAsync));
        }
    }
}
