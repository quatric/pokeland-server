package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzhq extends zzg {

    @VisibleForTesting
    protected zzhr zzqo;
    private volatile zzhr zzqp;
    private zzhr zzqq;
    private final Map<Activity, zzhr> zzqr;
    private zzhr zzqs;
    private String zzqt;

    public zzhq(zzfj zzfjVar) {
        super(zzfjVar);
        this.zzqr = new ArrayMap();
    }

    @MainThread
    private final zzhr zza(@NonNull Activity activity) {
        Preconditions.checkNotNull(activity);
        zzhr zzhrVar = this.zzqr.get(activity);
        if (zzhrVar != null) {
            return zzhrVar;
        }
        zzhr zzhrVar2 = new zzhr(null, zzbh(activity.getClass().getCanonicalName()), zzz().zzjv());
        this.zzqr.put(activity, zzhrVar2);
        return zzhrVar2;
    }

    @MainThread
    private final void zza(Activity activity, zzhr zzhrVar, boolean z) {
        zzhr zzhrVar2 = this.zzqp == null ? this.zzqq : this.zzqp;
        if (zzhrVar.zzqv == null) {
            zzhrVar = new zzhr(zzhrVar.zzqu, zzbh(activity.getClass().getCanonicalName()), zzhrVar.zzqw);
        }
        this.zzqq = this.zzqp;
        this.zzqp = zzhrVar;
        zzaa().zza(new zzht(this, z, zzhrVar2, zzhrVar));
    }

    public static void zza(zzhr zzhrVar, Bundle bundle, boolean z) {
        if (bundle != null && zzhrVar != null && (!bundle.containsKey("_sc") || z)) {
            if (zzhrVar.zzqu != null) {
                bundle.putString("_sn", zzhrVar.zzqu);
            } else {
                bundle.remove("_sn");
            }
            bundle.putString("_sc", zzhrVar.zzqv);
            bundle.putLong("_si", zzhrVar.zzqw);
            return;
        }
        if (bundle != null && zzhrVar == null && z) {
            bundle.remove("_sn");
            bundle.remove("_sc");
            bundle.remove("_si");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zza(@NonNull zzhr zzhrVar, boolean z) {
        zzp().zzc(zzx().elapsedRealtime());
        if (zzv().zza(zzhrVar.zzqx, z)) {
            zzhrVar.zzqx = false;
        }
    }

    @VisibleForTesting
    private static String zzbh(String str) {
        String[] strArrSplit = str.split("\\.");
        String str2 = strArrSplit.length > 0 ? strArrSplit[strArrSplit.length - 1] : "";
        return str2.length() > 100 ? str2.substring(0, 100) : str2;
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @MainThread
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        Bundle bundle2;
        if (bundle == null || (bundle2 = bundle.getBundle("com.google.app_measurement.screen_service")) == null) {
            return;
        }
        this.zzqr.put(activity, new zzhr(bundle2.getString(AppMeasurementSdk.ConditionalUserProperty.NAME), bundle2.getString("referrer_name"), bundle2.getLong("id")));
    }

    @MainThread
    public final void onActivityDestroyed(Activity activity) {
        this.zzqr.remove(activity);
    }

    @MainThread
    public final void onActivityPaused(Activity activity) {
        zzhr zzhrVarZza = zza(activity);
        this.zzqq = this.zzqp;
        this.zzqp = null;
        zzaa().zza(new zzhs(this, zzhrVarZza));
    }

    @MainThread
    public final void onActivityResumed(Activity activity) {
        zza(activity, zza(activity), false);
        zza zzaVarZzp = zzp();
        zzaVarZzp.zzaa().zza(new zze(zzaVarZzp, zzaVarZzp.zzx().elapsedRealtime()));
    }

    @MainThread
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        zzhr zzhrVar;
        if (bundle == null || (zzhrVar = this.zzqr.get(activity)) == null) {
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putLong("id", zzhrVar.zzqw);
        bundle2.putString(AppMeasurementSdk.ConditionalUserProperty.NAME, zzhrVar.zzqu);
        bundle2.putString("referrer_name", zzhrVar.zzqv);
        bundle.putBundle("com.google.app_measurement.screen_service", bundle2);
    }

    public final void setCurrentScreen(@NonNull Activity activity, @Size(max = 36, min = 1) @Nullable String str, @Size(max = 36, min = 1) @Nullable String str2) {
        if (this.zzqp == null) {
            zzab().zzgp().zzao("setCurrentScreen cannot be called while no activity active");
            return;
        }
        if (this.zzqr.get(activity) == null) {
            zzab().zzgp().zzao("setCurrentScreen must be called with an activity in the activity lifecycle");
            return;
        }
        if (str2 == null) {
            str2 = zzbh(activity.getClass().getCanonicalName());
        }
        boolean zEquals = this.zzqp.zzqv.equals(str2);
        boolean zZzs = zzjs.zzs(this.zzqp.zzqu, str);
        if (zEquals && zZzs) {
            zzab().zzgp().zzao("setCurrentScreen cannot be called with the same class and name");
            return;
        }
        if (str != null && (str.length() <= 0 || str.length() > 100)) {
            zzab().zzgp().zza("Invalid screen name length in setCurrentScreen. Length", Integer.valueOf(str.length()));
            return;
        }
        if (str2 != null && (str2.length() <= 0 || str2.length() > 100)) {
            zzab().zzgp().zza("Invalid class name length in setCurrentScreen. Length", Integer.valueOf(str2.length()));
            return;
        }
        zzab().zzgs().zza("Setting current screen to name, class", str == null ? "null" : str, str2);
        zzhr zzhrVar = new zzhr(str, str2, zzz().zzjv());
        this.zzqr.put(activity, zzhrVar);
        zza(activity, zzhrVar, true);
    }

    @WorkerThread
    public final void zza(String str, zzhr zzhrVar) {
        zzo();
        synchronized (this) {
            if (this.zzqt == null || this.zzqt.equals(str) || zzhrVar != null) {
                this.zzqt = str;
                this.zzqs = zzhrVar;
            }
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ zzfc zzaa() {
        return super.zzaa();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ zzef zzab() {
        return super.zzab();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzeo zzac() {
        return super.zzac();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzs zzad() {
        return super.zzad();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ zzr zzae() {
        return super.zzae();
    }

    @Override // com.google.android.gms.measurement.internal.zzg
    protected final boolean zzbk() {
        return false;
    }

    @WorkerThread
    public final zzhr zzin() {
        zzbi();
        zzo();
        return this.zzqo;
    }

    public final zzhr zzio() {
        zzm();
        return this.zzqp;
    }

    @Override // com.google.android.gms.measurement.internal.zzd, com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzl() {
        super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzd, com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzm() {
        super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzd, com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzn() {
        super.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzd, com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzo() {
        super.zzo();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zza zzp() {
        return super.zzp();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzgp zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzdy zzr() {
        return super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzhv zzs() {
        return super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzhq zzt() {
        return super.zzt();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzeb zzu() {
        return super.zzu();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zziw zzv() {
        return super.zzv();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzac zzw() {
        return super.zzw();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ Clock zzx() {
        return super.zzx();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzed zzy() {
        return super.zzy();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzjs zzz() {
        return super.zzz();
    }
}
