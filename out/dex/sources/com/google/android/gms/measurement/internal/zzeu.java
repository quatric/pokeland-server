package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.PackageManagerWrapper;
import com.google.android.gms.common.wrappers.Wrappers;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzeu {
    final zzfj zzj;

    zzeu(zzfj zzfjVar) {
        this.zzj = zzfjVar;
    }

    @VisibleForTesting
    private final boolean zzhn() {
        try {
            PackageManagerWrapper packageManagerWrapperPackageManager = Wrappers.packageManager(this.zzj.getContext());
            if (packageManagerWrapperPackageManager != null) {
                return packageManagerWrapperPackageManager.getPackageInfo("com.android.vending", 128).versionCode >= 80837300;
            }
            this.zzj.zzab().zzgq().zzao("Failed to retrieve Package Manager to check Play Store compatibility");
            return false;
        } catch (Exception e) {
            this.zzj.zzab().zzgq().zza("Failed to retrieve Play Store version", e);
            return false;
        }
    }

    @WorkerThread
    @VisibleForTesting
    @Nullable
    final Bundle zza(String str, com.google.android.gms.internal.measurement.zzf zzfVar) {
        this.zzj.zzaa().zzo();
        if (zzfVar == null) {
            this.zzj.zzab().zzgn().zzao("Attempting to use Install Referrer Service while it is not initialized");
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString("package_name", str);
        try {
            Bundle bundleZza = zzfVar.zza(bundle);
            if (bundleZza != null) {
                return bundleZza;
            }
            this.zzj.zzab().zzgk().zzao("Install Referrer Service returned a null response");
            return null;
        } catch (Exception e) {
            this.zzj.zzab().zzgk().zza("Exception occurred while retrieving the Install Referrer", e.getMessage());
            return null;
        }
    }

    @WorkerThread
    protected final void zzat(String str) {
        if (str == null || str.isEmpty()) {
            this.zzj.zzab().zzgq().zzao("Install Referrer Reporter was called with invalid app package name");
            return;
        }
        this.zzj.zzaa().zzo();
        if (!zzhn()) {
            this.zzj.zzab().zzgq().zzao("Install Referrer Reporter is not available");
            return;
        }
        this.zzj.zzab().zzgq().zzao("Install Referrer Reporter is initializing");
        zzex zzexVar = new zzex(this, str);
        this.zzj.zzaa().zzo();
        Intent intent = new Intent("com.google.android.finsky.BIND_GET_INSTALL_REFERRER_SERVICE");
        intent.setComponent(new ComponentName("com.android.vending", "com.google.android.finsky.externalreferrer.GetInstallReferrerService"));
        PackageManager packageManager = this.zzj.getContext().getPackageManager();
        if (packageManager == null) {
            this.zzj.zzab().zzgn().zzao("Failed to obtain Package Manager to verify binding conditions");
            return;
        }
        List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, 0);
        if (listQueryIntentServices == null || listQueryIntentServices.isEmpty()) {
            this.zzj.zzab().zzgq().zzao("Play Service for fetching Install Referrer is unavailable on device");
            return;
        }
        ResolveInfo resolveInfo = listQueryIntentServices.get(0);
        if (resolveInfo.serviceInfo != null) {
            String str2 = resolveInfo.serviceInfo.packageName;
            if (resolveInfo.serviceInfo.name == null || !"com.android.vending".equals(str2) || !zzhn()) {
                this.zzj.zzab().zzgq().zzao("Play Store missing or incompatible. Version 8.3.73 or later required");
                return;
            }
            try {
                this.zzj.zzab().zzgq().zza("Install Referrer Service is", ConnectionTracker.getInstance().bindService(this.zzj.getContext(), new Intent(intent), zzexVar, 1) ? "available" : "not available");
            } catch (Exception e) {
                this.zzj.zzab().zzgk().zza("Exception occurred while binding to Install Referrer Service", e.getMessage());
            }
        }
    }
}
