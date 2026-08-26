package com.google.android.gms.measurement.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.text.TextUtils;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@TargetApi(14)
@MainThread
final class zzhj implements Application.ActivityLifecycleCallbacks {
    private final /* synthetic */ zzgp zzpt;

    private zzhj(zzgp zzgpVar) {
        this.zzpt = zzgpVar;
    }

    /* synthetic */ zzhj(zzgp zzgpVar, zzgo zzgoVar) {
        this(zzgpVar);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        Bundle bundle2;
        boolean z;
        Bundle bundleZza;
        try {
            try {
                this.zzpt.zzab().zzgs().zzao("onActivityCreated");
                Intent intent = activity.getIntent();
                if (intent == null) {
                    return;
                }
                Uri data = intent.getData();
                if (data != null && data.isHierarchical()) {
                    this.zzpt.zzz();
                    String str = zzjs.zzc(intent) ? "gs" : "auto";
                    String queryParameter = data.getQueryParameter("referrer");
                    if (this.zzpt.zzad().zza(zzak.zzje) || this.zzpt.zzad().zza(zzak.zzjg)) {
                        if (!TextUtils.isEmpty(queryParameter)) {
                            if (queryParameter.contains("gclid") || queryParameter.contains("utm_campaign") || queryParameter.contains("utm_source") || queryParameter.contains("utm_medium")) {
                                zzjs zzjsVarZzz = this.zzpt.zzz();
                                String strValueOf = String.valueOf(queryParameter);
                                Bundle bundleZza2 = zzjsVarZzz.zza(Uri.parse(strValueOf.length() != 0 ? "https://google.com/search?".concat(strValueOf) : new String("https://google.com/search?")));
                                if (bundleZza2 != null) {
                                    bundleZza2.putString("_cis", "referrer");
                                }
                                bundle2 = bundleZza2;
                            } else {
                                this.zzpt.zzab().zzgr().zzao("Activity created with data 'referrer' without required params");
                            }
                        }
                        bundle2 = null;
                    } else {
                        bundle2 = null;
                    }
                    if (bundle == null) {
                        bundleZza = this.zzpt.zzz().zza(data);
                        if (bundleZza != null) {
                            bundleZza.putString("_cis", "intent");
                            if (!this.zzpt.zzad().zza(zzak.zzje) || bundleZza.containsKey("gclid") || bundle2 == null || !bundle2.containsKey("gclid")) {
                                z = false;
                            } else {
                                z = false;
                                bundleZza.putString("_cer", String.format("gclid=%s", bundle2.getString("gclid")));
                            }
                            this.zzpt.logEvent(str, "_cmp", bundleZza);
                        } else {
                            z = false;
                        }
                    } else {
                        z = false;
                        bundleZza = null;
                    }
                    if (this.zzpt.zzad().zza(zzak.zzjg) && bundle2 != null && bundle2.containsKey("gclid") && (bundleZza == null || !bundleZza.containsKey("gclid"))) {
                        this.zzpt.zzb("auto", "_lgclid", (Object) bundle2.getString("gclid"), true);
                    }
                    if (TextUtils.isEmpty(queryParameter)) {
                        return;
                    }
                    if (queryParameter.contains("gclid") && (queryParameter.contains("utm_campaign") || queryParameter.contains("utm_source") || queryParameter.contains("utm_medium") || queryParameter.contains("utm_term") || queryParameter.contains("utm_content"))) {
                        z = true;
                    }
                    if (!z) {
                        this.zzpt.zzab().zzgr().zzao("Activity created with data 'referrer' without required params");
                        return;
                    }
                    this.zzpt.zzab().zzgr().zza("Activity created with referrer", queryParameter);
                    if (!TextUtils.isEmpty(queryParameter)) {
                        this.zzpt.zzb("auto", "_ldl", (Object) queryParameter, true);
                    }
                }
            } catch (Exception e) {
                this.zzpt.zzab().zzgk().zza("Throwable caught in onActivityCreated", e);
            }
        } finally {
            this.zzpt.zzt().onActivityCreated(activity, bundle);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
        this.zzpt.zzt().onActivityDestroyed(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    @MainThread
    public final void onActivityPaused(Activity activity) {
        this.zzpt.zzt().onActivityPaused(activity);
        zziw zziwVarZzv = this.zzpt.zzv();
        zziwVarZzv.zzaa().zza(new zzja(zziwVarZzv, zziwVarZzv.zzx().elapsedRealtime()));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    @MainThread
    public final void onActivityResumed(Activity activity) {
        this.zzpt.zzt().onActivityResumed(activity);
        zziw zziwVarZzv = this.zzpt.zzv();
        zziwVarZzv.zzaa().zza(new zzjb(zziwVarZzv, zziwVarZzv.zzx().elapsedRealtime()));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.zzpt.zzt().onActivitySaveInstanceState(activity, bundle);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
    }
}
