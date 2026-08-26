package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.ProcessUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzs extends zzgf {
    private Boolean zzeb;

    @NonNull
    private zzu zzec;
    private Boolean zzed;

    zzs(zzfj zzfjVar) {
        super(zzfjVar);
        this.zzec = zzv.zzee;
        zzak.zza(zzfjVar);
    }

    static String zzbm() {
        return zzak.zzgf.get(null);
    }

    @VisibleForTesting
    @Nullable
    private final Bundle zzbo() {
        try {
            if (getContext().getPackageManager() == null) {
                zzab().zzgk().zzao("Failed to load metadata: PackageManager is null");
                return null;
            }
            ApplicationInfo applicationInfo = Wrappers.packageManager(getContext()).getApplicationInfo(getContext().getPackageName(), 128);
            if (applicationInfo != null) {
                return applicationInfo.metaData;
            }
            zzab().zzgk().zzao("Failed to load metadata: ApplicationInfo is null");
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            zzab().zzgk().zza("Failed to load metadata: Package name not found", e);
            return null;
        }
    }

    public static long zzbs() {
        return zzak.zzhi.get(null).longValue();
    }

    public static long zzbt() {
        return zzak.zzgi.get(null).longValue();
    }

    public static boolean zzbv() {
        return zzak.zzge.get(null).booleanValue();
    }

    @WorkerThread
    static boolean zzbx() {
        return zzak.zzhy.get(null).booleanValue();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @WorkerThread
    public final long zza(String str, @NonNull zzdu<Long> zzduVar) {
        if (str == null) {
            return zzduVar.get(null).longValue();
        }
        String strZzb = this.zzec.zzb(str, zzduVar.getKey());
        if (TextUtils.isEmpty(strZzb)) {
            return zzduVar.get(null).longValue();
        }
        try {
            return zzduVar.get(Long.valueOf(Long.parseLong(strZzb))).longValue();
        } catch (NumberFormatException unused) {
            return zzduVar.get(null).longValue();
        }
    }

    final void zza(@NonNull zzu zzuVar) {
        this.zzec = zzuVar;
    }

    public final boolean zza(zzdu<Boolean> zzduVar) {
        return zzd(null, zzduVar);
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

    public final long zzao() {
        zzae();
        return 16250L;
    }

    @WorkerThread
    public final int zzb(String str, @NonNull zzdu<Integer> zzduVar) {
        if (str == null) {
            return zzduVar.get(null).intValue();
        }
        String strZzb = this.zzec.zzb(str, zzduVar.getKey());
        if (TextUtils.isEmpty(strZzb)) {
            return zzduVar.get(null).intValue();
        }
        try {
            return zzduVar.get(Integer.valueOf(Integer.parseInt(strZzb))).intValue();
        } catch (NumberFormatException unused) {
            return zzduVar.get(null).intValue();
        }
    }

    public final boolean zzbn() {
        if (this.zzed == null) {
            synchronized (this) {
                if (this.zzed == null) {
                    ApplicationInfo applicationInfo = getContext().getApplicationInfo();
                    String myProcessName = ProcessUtils.getMyProcessName();
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        this.zzed = Boolean.valueOf(str != null && str.equals(myProcessName));
                    }
                    if (this.zzed == null) {
                        this.zzed = Boolean.TRUE;
                        zzab().zzgk().zzao("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzed.booleanValue();
    }

    public final boolean zzbp() {
        zzae();
        Boolean boolZzj = zzj("firebase_analytics_collection_deactivated");
        return boolZzj != null && boolZzj.booleanValue();
    }

    public final Boolean zzbq() {
        zzae();
        return zzj("firebase_analytics_collection_enabled");
    }

    public final Boolean zzbr() {
        zzm();
        Boolean boolZzj = zzj("google_analytics_adid_collection_enabled");
        return Boolean.valueOf(boolZzj == null || boolZzj.booleanValue());
    }

    public final String zzbu() {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class, String.class).invoke(null, "debug.firebase.analytics.app", "");
        } catch (ClassNotFoundException e) {
            zzab().zzgk().zza("Could not find SystemProperties class", e);
            return "";
        } catch (IllegalAccessException e2) {
            zzab().zzgk().zza("Could not access SystemProperties.get()", e2);
            return "";
        } catch (NoSuchMethodException e3) {
            zzab().zzgk().zza("Could not find SystemProperties.get() method", e3);
            return "";
        } catch (InvocationTargetException e4) {
            zzab().zzgk().zza("SystemProperties.get() threw an exception", e4);
            return "";
        }
    }

    @WorkerThread
    final boolean zzbw() {
        if (this.zzeb == null) {
            this.zzeb = zzj("app_measurement_lite");
            if (this.zzeb == null) {
                this.zzeb = false;
            }
        }
        return this.zzeb.booleanValue() || !this.zzj.zzia();
    }

    @WorkerThread
    public final double zzc(String str, @NonNull zzdu<Double> zzduVar) {
        if (str == null) {
            return zzduVar.get(null).doubleValue();
        }
        String strZzb = this.zzec.zzb(str, zzduVar.getKey());
        if (TextUtils.isEmpty(strZzb)) {
            return zzduVar.get(null).doubleValue();
        }
        try {
            return zzduVar.get(Double.valueOf(Double.parseDouble(strZzb))).doubleValue();
        } catch (NumberFormatException unused) {
            return zzduVar.get(null).doubleValue();
        }
    }

    @WorkerThread
    public final boolean zzd(String str, @NonNull zzdu<Boolean> zzduVar) {
        if (str == null) {
            return zzduVar.get(null).booleanValue();
        }
        String strZzb = this.zzec.zzb(str, zzduVar.getKey());
        return TextUtils.isEmpty(strZzb) ? zzduVar.get(null).booleanValue() : zzduVar.get(Boolean.valueOf(Boolean.parseBoolean(strZzb))).booleanValue();
    }

    public final boolean zze(String str, zzdu<Boolean> zzduVar) {
        return zzd(str, zzduVar);
    }

    @WorkerThread
    public final int zzi(@Size(min = 1) String str) {
        return zzb(str, zzak.zzgt);
    }

    @VisibleForTesting
    @Nullable
    final Boolean zzj(@Size(min = 1) String str) {
        Preconditions.checkNotEmpty(str);
        Bundle bundleZzbo = zzbo();
        if (bundleZzbo == null) {
            zzab().zzgk().zzao("Failed to load metadata: Metadata bundle is null");
            return null;
        }
        if (bundleZzbo.containsKey(str)) {
            return Boolean.valueOf(bundleZzbo.getBoolean(str));
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x002a A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:14:0x003d A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:15:0x003e A[Catch: NotFoundException -> 0x0043, TRY_LEAVE, TryCatch #0 {NotFoundException -> 0x0043, blocks: (B:12:0x002b, B:15:0x003e), top: B:20:0x002b }] */
    /* JADX WARN: Code duplicated, block: B:20:0x002b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @VisibleForTesting
    @Nullable
    final List<String> zzk(@Size(min = 1) String str) {
        Integer numValueOf;
        String[] stringArray;
        Preconditions.checkNotEmpty(str);
        Bundle bundleZzbo = zzbo();
        if (bundleZzbo != null) {
            if (bundleZzbo.containsKey(str)) {
                numValueOf = Integer.valueOf(bundleZzbo.getInt(str));
            }
            if (numValueOf == null) {
                return null;
            }
            try {
                stringArray = getContext().getResources().getStringArray(numValueOf.intValue());
                if (stringArray == null) {
                    return null;
                }
                return Arrays.asList(stringArray);
            } catch (Resources.NotFoundException e) {
                zzab().zzgk().zza("Failed to load string array from metadata: resource not found", e);
                return null;
            }
        }
        zzab().zzgk().zzao("Failed to load metadata: Metadata bundle is null");
        numValueOf = null;
        if (numValueOf == null) {
            return null;
        }
        stringArray = getContext().getResources().getStringArray(numValueOf.intValue());
        if (stringArray == null) {
            return null;
        }
        return Arrays.asList(stringArray);
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzl() {
        super.zzl();
    }

    public final boolean zzl(String str) {
        return "1".equals(this.zzec.zzb(str, "gaia_collection_enabled"));
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzm() {
        super.zzm();
    }

    public final boolean zzm(String str) {
        return "1".equals(this.zzec.zzb(str, "measurement.event_sampling_enabled"));
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzn() {
        super.zzn();
    }

    @WorkerThread
    final boolean zzn(String str) {
        return zzd(str, zzak.zzhs);
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzo() {
        super.zzo();
    }

    @WorkerThread
    final boolean zzo(String str) {
        return zzd(str, zzak.zzhm);
    }

    @WorkerThread
    final String zzp(String str) {
        zzdu<String> zzduVar = zzak.zzhn;
        return str == null ? zzduVar.get(null) : zzduVar.get(this.zzec.zzb(str, zzduVar.getKey()));
    }

    final boolean zzq(String str) {
        return zzd(str, zzak.zzht);
    }

    @WorkerThread
    final boolean zzr(String str) {
        return zzd(str, zzak.zzhu);
    }

    @WorkerThread
    final boolean zzs(String str) {
        return zzd(str, zzak.zzhv);
    }

    @WorkerThread
    final boolean zzt(String str) {
        return zzd(str, zzak.zzhx);
    }

    @WorkerThread
    final boolean zzu(String str) {
        return zzd(str, zzak.zzhw);
    }

    @WorkerThread
    final boolean zzv(String str) {
        return zzd(str, zzak.zzhz);
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzac zzw() {
        return super.zzw();
    }

    @WorkerThread
    final boolean zzw(String str) {
        return zzd(str, zzak.zzia);
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ Clock zzx() {
        return super.zzx();
    }

    @WorkerThread
    final boolean zzx(String str) {
        return zzd(str, zzak.zzib);
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzed zzy() {
        return super.zzy();
    }

    @WorkerThread
    final boolean zzy(String str) {
        return zzd(str, zzak.zzic);
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzjs zzz() {
        return super.zzz();
    }

    @WorkerThread
    final boolean zzz(String str) {
        return zzd(str, zzak.zzih);
    }
}
