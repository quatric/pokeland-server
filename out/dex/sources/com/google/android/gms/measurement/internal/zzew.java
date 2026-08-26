package com.google.android.gms.measurement.internal;

import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.firebase.analytics.FirebaseAnalytics;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzew implements Runnable {
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzf zzmq;
    private final /* synthetic */ ServiceConnection zzmr;
    private final /* synthetic */ zzex zzms;

    zzew(zzex zzexVar, com.google.android.gms.internal.measurement.zzf zzfVar, ServiceConnection serviceConnection) {
        this.zzms = zzexVar;
        this.zzmq = zzfVar;
        this.zzmr = serviceConnection;
    }

    /* JADX WARN: Code duplicated, block: B:34:0x00e2  */
    /* JADX WARN: Code duplicated, block: B:35:0x00f7  */
    @Override // java.lang.Runnable
    public final void run() {
        zzeu zzeuVar = this.zzms.zzmt;
        String str = this.zzms.packageName;
        com.google.android.gms.internal.measurement.zzf zzfVar = this.zzmq;
        ServiceConnection serviceConnection = this.zzmr;
        Bundle bundleZza = zzeuVar.zza(str, zzfVar);
        zzeuVar.zzj.zzaa().zzo();
        if (bundleZza != null) {
            long j = bundleZza.getLong("install_begin_timestamp_seconds", 0L) * 1000;
            if (j == 0) {
                zzeuVar.zzj.zzab().zzgk().zzao("Service response is missing Install Referrer install timestamp");
            } else {
                String string = bundleZza.getString("install_referrer");
                if (string == null || string.isEmpty()) {
                    zzeuVar.zzj.zzab().zzgk().zzao("No referrer defined in install referrer response");
                } else {
                    zzeuVar.zzj.zzab().zzgs().zza("InstallReferrer API result", string);
                    zzjs zzjsVarZzz = zzeuVar.zzj.zzz();
                    String strValueOf = String.valueOf(string);
                    Bundle bundleZza2 = zzjsVarZzz.zza(Uri.parse(strValueOf.length() != 0 ? "?".concat(strValueOf) : new String("?")));
                    if (bundleZza2 == null) {
                        zzeuVar.zzj.zzab().zzgk().zzao("No campaign params defined in install referrer result");
                    } else {
                        String string2 = bundleZza2.getString(FirebaseAnalytics.Param.MEDIUM);
                        if ((string2 == null || "(not set)".equalsIgnoreCase(string2) || "organic".equalsIgnoreCase(string2)) ? false : true) {
                            long j2 = bundleZza.getLong("referrer_click_timestamp_seconds", 0L) * 1000;
                            if (j2 == 0) {
                                zzeuVar.zzj.zzab().zzgk().zzao("Install Referrer is missing click timestamp for ad campaign");
                            } else {
                                bundleZza2.putLong("click_timestamp", j2);
                                if (j == zzeuVar.zzj.zzac().zzlp.get()) {
                                    zzeuVar.zzj.zzae();
                                    zzeuVar.zzj.zzab().zzgs().zzao("Campaign has already been logged");
                                } else {
                                    zzeuVar.zzj.zzac().zzlp.set(j);
                                    zzeuVar.zzj.zzae();
                                    zzeuVar.zzj.zzab().zzgs().zza("Logging Install Referrer campaign from sdk with ", "referrer API");
                                    bundleZza2.putString("_cis", "referrer API");
                                    zzeuVar.zzj.zzq().logEvent("auto", "_cmp", bundleZza2);
                                }
                            }
                        } else if (j == zzeuVar.zzj.zzac().zzlp.get()) {
                            zzeuVar.zzj.zzae();
                            zzeuVar.zzj.zzab().zzgs().zzao("Campaign has already been logged");
                        } else {
                            zzeuVar.zzj.zzac().zzlp.set(j);
                            zzeuVar.zzj.zzae();
                            zzeuVar.zzj.zzab().zzgs().zza("Logging Install Referrer campaign from sdk with ", "referrer API");
                            bundleZza2.putString("_cis", "referrer API");
                            zzeuVar.zzj.zzq().logEvent("auto", "_cmp", bundleZza2);
                        }
                    }
                }
            }
        }
        if (serviceConnection != null) {
            ConnectionTracker.getInstance().unbindService(zzeuVar.zzj.getContext(), serviceConnection);
        }
    }
}
