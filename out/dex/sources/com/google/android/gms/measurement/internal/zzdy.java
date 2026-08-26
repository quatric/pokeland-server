package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.os.EnvironmentCompat;
import android.text.TextUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleServices;
import com.google.android.gms.common.internal.StringResourceValueReader;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.InstantApps;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzdy extends zzg {
    private String zzce;
    private String zzcg;
    private String zzcm;
    private String zzco;
    private long zzcr;
    private String zzcu;
    private List<String> zzcw;
    private int zzds;
    private int zzjr;
    private String zzjs;
    private long zzjt;
    private long zzs;

    zzdy(zzfj zzfjVar, long j) {
        super(zzfjVar);
        this.zzs = j;
    }

    @WorkerThread
    @VisibleForTesting
    private final String zzge() {
        try {
            Class<?> clsLoadClass = getContext().getClassLoader().loadClass("com.google.firebase.analytics.FirebaseAnalytics");
            if (clsLoadClass == null) {
                return null;
            }
            try {
                Object objInvoke = clsLoadClass.getDeclaredMethod("getInstance", Context.class).invoke(null, getContext());
                if (objInvoke == null) {
                    return null;
                }
                try {
                    return (String) clsLoadClass.getDeclaredMethod("getFirebaseInstanceId", new Class[0]).invoke(objInvoke, new Object[0]);
                } catch (Exception unused) {
                    zzab().zzgp().zzao("Failed to retrieve Firebase Instance Id");
                    return null;
                }
            } catch (Exception unused2) {
                zzab().zzgo().zzao("Failed to obtain Firebase Analytics instance");
                return null;
            }
        } catch (ClassNotFoundException unused3) {
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    final String getGmpAppId() {
        zzbi();
        return this.zzcg;
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

    final String zzag() {
        zzbi();
        return this.zzce;
    }

    final String zzah() {
        zzbi();
        return this.zzcu;
    }

    @WorkerThread
    final zzn zzai(String str) {
        Boolean boolZzj;
        zzo();
        zzm();
        String strZzag = zzag();
        String gmpAppId = getGmpAppId();
        zzbi();
        String str2 = this.zzcm;
        long jZzgf = zzgf();
        zzbi();
        String str3 = this.zzco;
        long jZzao = zzad().zzao();
        zzbi();
        zzo();
        if (this.zzjt == 0) {
            this.zzjt = this.zzj.zzz().zzc(getContext(), getContext().getPackageName());
        }
        long j = this.zzjt;
        boolean zIsEnabled = this.zzj.isEnabled();
        boolean z = !zzac().zzmc;
        zzo();
        zzm();
        String strZzge = !this.zzj.isEnabled() ? null : zzge();
        zzbi();
        long j2 = this.zzcr;
        long jZzic = this.zzj.zzic();
        int iZzgg = zzgg();
        boolean zBooleanValue = zzad().zzbr().booleanValue();
        zzs zzsVarZzad = zzad();
        zzsVarZzad.zzm();
        Boolean boolZzj2 = zzsVarZzad.zzj("google_analytics_ssaid_collection_enabled");
        return new zzn(strZzag, gmpAppId, str2, jZzgf, str3, jZzao, j, str, zIsEnabled, z, strZzge, j2, jZzic, iZzgg, zBooleanValue, Boolean.valueOf(boolZzj2 == null || boolZzj2.booleanValue()).booleanValue(), zzac().zzhi(), zzah(), (!zzad().zze(zzag(), zzak.zzij) || (boolZzj = zzad().zzj("google_analytics_default_allow_ad_personalization_signals")) == null) ? null : Boolean.valueOf(!boolZzj.booleanValue()), this.zzs, zzad().zze(zzag(), zzak.zzix) ? this.zzcw : null);
    }

    @Nullable
    final List<String> zzbh() {
        return this.zzcw;
    }

    @Override // com.google.android.gms.measurement.internal.zzg
    protected final boolean zzbk() {
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:101:0x024a  */
    /* JADX WARN: Code duplicated, block: B:112:0x0211 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:113:? A[LOOP:0: B:87:0x0217->B:113:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:31:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:37:0x00cf  */
    /* JADX WARN: Code duplicated, block: B:40:0x00d3 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:41:0x00d5  */
    /* JADX WARN: Code duplicated, block: B:42:0x00e3  */
    /* JADX WARN: Code duplicated, block: B:44:0x00fe  */
    /* JADX WARN: Code duplicated, block: B:46:0x0110  */
    /* JADX WARN: Code duplicated, block: B:48:0x0118  */
    /* JADX WARN: Code duplicated, block: B:49:0x0126  */
    /* JADX WARN: Code duplicated, block: B:55:0x0144 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:56:0x0146  */
    /* JADX WARN: Code duplicated, block: B:60:0x0169  */
    /* JADX WARN: Code duplicated, block: B:68:0x019d  */
    /* JADX WARN: Code duplicated, block: B:69:0x019e  */
    /* JADX WARN: Code duplicated, block: B:72:0x01a7 A[Catch: IllegalStateException -> 0x01cc, TryCatch #3 {IllegalStateException -> 0x01cc, blocks: (B:66:0x0193, B:70:0x019f, B:72:0x01a7, B:74:0x01ba), top: B:109:0x0193 }] */
    /* JADX WARN: Code duplicated, block: B:74:0x01ba A[Catch: IllegalStateException -> 0x01cc, TRY_LEAVE, TryCatch #3 {IllegalStateException -> 0x01cc, blocks: (B:66:0x0193, B:70:0x019f, B:72:0x01a7, B:74:0x01ba), top: B:109:0x0193 }] */
    /* JADX WARN: Code duplicated, block: B:80:0x01ef  */
    /* JADX WARN: Code duplicated, block: B:82:0x01fe  */
    /* JADX WARN: Code duplicated, block: B:84:0x0204  */
    /* JADX WARN: Code duplicated, block: B:86:0x0213  */
    /* JADX WARN: Code duplicated, block: B:89:0x021d  */
    /* JADX WARN: Code duplicated, block: B:93:0x0232  */
    /* JADX WARN: Code duplicated, block: B:96:0x023a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:97:0x023c  */
    /* JADX WARN: Code duplicated, block: B:99:0x0247  */
    @Override // com.google.android.gms.measurement.internal.zzg
    protected final void zzbl() {
        String str;
        String string;
        Status statusInitialize;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        List<String> listZzk;
        Iterator<String> it;
        String googleAppId;
        Boolean boolZzbq;
        String packageName = getContext().getPackageName();
        PackageManager packageManager = getContext().getPackageManager();
        String str2 = "Unknown";
        String str3 = "";
        String installerPackageName = EnvironmentCompat.MEDIA_UNKNOWN;
        int i = Integer.MIN_VALUE;
        try {
            if (packageManager != null) {
                try {
                    installerPackageName = packageManager.getInstallerPackageName(packageName);
                } catch (IllegalArgumentException unused) {
                    zzab().zzgk().zza("Error retrieving app installer package name. appId", zzef.zzam(packageName));
                }
                if (installerPackageName == null) {
                    installerPackageName = "manual_install";
                } else if ("com.android.vending".equals(installerPackageName)) {
                    installerPackageName = "";
                }
                try {
                    PackageInfo packageInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
                    if (packageInfo != null) {
                        CharSequence applicationLabel = packageManager.getApplicationLabel(packageInfo.applicationInfo);
                        string = !TextUtils.isEmpty(applicationLabel) ? applicationLabel.toString() : "Unknown";
                        try {
                            str2 = packageInfo.versionName;
                            i = packageInfo.versionCode;
                        } catch (PackageManager.NameNotFoundException unused2) {
                            str = str2;
                            str2 = string;
                            zzab().zzgk().zza("Error retrieving package info. appId, appName", zzef.zzam(packageName), str2);
                            string = str2;
                            str2 = str;
                        }
                    }
                } catch (PackageManager.NameNotFoundException unused3) {
                    str = "Unknown";
                }
                this.zzce = packageName;
                this.zzco = installerPackageName;
                this.zzcm = str2;
                this.zzjr = i;
                this.zzjs = string;
                this.zzjt = 0L;
                zzae();
                statusInitialize = GoogleServices.initialize(getContext());
                z = true;
                if (statusInitialize == null && statusInitialize.isSuccess()) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (TextUtils.isEmpty(this.zzj.zzhx()) && "am".equals(this.zzj.zzhy())) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                z4 = z2 | z3;
                if (!z4) {
                    if (statusInitialize == null) {
                        zzab().zzgk().zzao("GoogleService failed to initialize (no status)");
                    } else {
                        zzab().zzgk().zza("GoogleService failed to initialize, status", Integer.valueOf(statusInitialize.getStatusCode()), statusInitialize.getStatusMessage());
                    }
                }
                if (z4) {
                    boolZzbq = zzad().zzbq();
                    if (zzad().zzbp()) {
                        if (this.zzj.zzhw()) {
                            zzab().zzgq().zzao("Collection disabled with firebase_analytics_collection_deactivated=1");
                        }
                    } else if (boolZzbq != null || boolZzbq.booleanValue()) {
                        if (boolZzbq == null || !GoogleServices.isMeasurementExplicitlyDisabled()) {
                            zzab().zzgs().zzao("Collection enabled");
                            z5 = true;
                        } else {
                            zzab().zzgq().zzao("Collection disabled with google_app_measurement_enable=0");
                        }
                    } else if (this.zzj.zzhw()) {
                        zzab().zzgq().zzao("Collection disabled with firebase_analytics_collection_enabled=0");
                    }
                    z5 = false;
                } else {
                    z5 = false;
                }
                this.zzcg = "";
                this.zzcu = "";
                this.zzcr = 0L;
                zzae();
                if (!TextUtils.isEmpty(this.zzj.zzhx()) && "am".equals(this.zzj.zzhy())) {
                    this.zzcu = this.zzj.zzhx();
                }
                googleAppId = GoogleServices.getGoogleAppId();
                if (TextUtils.isEmpty(googleAppId)) {
                    str3 = googleAppId;
                }
                this.zzcg = str3;
                if (!TextUtils.isEmpty(googleAppId)) {
                    this.zzcu = new StringResourceValueReader(getContext()).getString("admob_app_id");
                }
                if (z5) {
                    zzab().zzgs().zza("App package, google app id", this.zzce, this.zzcg);
                }
                this.zzcw = null;
                if (zzad().zze(this.zzce, zzak.zzix)) {
                    zzae();
                    listZzk = zzad().zzk("analytics.safelisted_events");
                    if (listZzk != null) {
                        if (listZzk.size() == 0) {
                            zzab().zzgn().zzao("Safelisted event list cannot be empty. Ignoring");
                        } else {
                            it = listZzk.iterator();
                            while (it.hasNext()) {
                                if (!zzz().zzq("safelisted event", it.next())) {
                                }
                            }
                        }
                        z = false;
                        break;
                    }
                    if (z) {
                        this.zzcw = listZzk;
                    }
                }
                if (Build.VERSION.SDK_INT >= 16) {
                    this.zzds = 0;
                } else if (packageManager != null) {
                    this.zzds = InstantApps.isInstantApp(getContext()) ? 1 : 0;
                } else {
                    this.zzds = 0;
                }
            }
            zzab().zzgk().zza("PackageManager is null, app identity information might be inaccurate. appId", zzef.zzam(packageName));
            googleAppId = GoogleServices.getGoogleAppId();
            if (TextUtils.isEmpty(googleAppId)) {
                str3 = googleAppId;
            }
            this.zzcg = str3;
            if (!TextUtils.isEmpty(googleAppId)) {
                this.zzcu = new StringResourceValueReader(getContext()).getString("admob_app_id");
            }
            if (z5) {
                zzab().zzgs().zza("App package, google app id", this.zzce, this.zzcg);
            }
        } catch (IllegalStateException e) {
            zzab().zzgk().zza("getGoogleAppId or isMeasurementEnabled failed with exception. appId", zzef.zzam(packageName), e);
        }
        string = "Unknown";
        this.zzce = packageName;
        this.zzco = installerPackageName;
        this.zzcm = str2;
        this.zzjr = i;
        this.zzjs = string;
        this.zzjt = 0L;
        zzae();
        statusInitialize = GoogleServices.initialize(getContext());
        z = true;
        if (statusInitialize == null) {
            z2 = false;
        } else {
            z2 = false;
        }
        if (TextUtils.isEmpty(this.zzj.zzhx())) {
            z3 = false;
        } else {
            z3 = false;
        }
        z4 = z2 | z3;
        if (!z4) {
            if (statusInitialize == null) {
                zzab().zzgk().zzao("GoogleService failed to initialize (no status)");
            } else {
                zzab().zzgk().zza("GoogleService failed to initialize, status", Integer.valueOf(statusInitialize.getStatusCode()), statusInitialize.getStatusMessage());
            }
        }
        if (z4) {
            boolZzbq = zzad().zzbq();
            if (zzad().zzbp()) {
                if (this.zzj.zzhw()) {
                    zzab().zzgq().zzao("Collection disabled with firebase_analytics_collection_deactivated=1");
                }
            } else if (boolZzbq != null) {
                if (boolZzbq == null) {
                }
                zzab().zzgs().zzao("Collection enabled");
                z5 = true;
            } else {
                if (boolZzbq == null) {
                }
                zzab().zzgs().zzao("Collection enabled");
                z5 = true;
            }
            z5 = false;
        } else {
            z5 = false;
        }
        this.zzcg = "";
        this.zzcu = "";
        this.zzcr = 0L;
        zzae();
        if (!TextUtils.isEmpty(this.zzj.zzhx())) {
            this.zzcu = this.zzj.zzhx();
        }
        this.zzcw = null;
        if (zzad().zze(this.zzce, zzak.zzix)) {
            zzae();
            listZzk = zzad().zzk("analytics.safelisted_events");
            if (listZzk != null) {
                if (listZzk.size() == 0) {
                    zzab().zzgn().zzao("Safelisted event list cannot be empty. Ignoring");
                } else {
                    it = listZzk.iterator();
                    while (it.hasNext()) {
                        if (!zzz().zzq("safelisted event", it.next())) {
                        }
                    }
                }
                z = false;
                break;
            }
            if (z) {
                this.zzcw = listZzk;
            }
        }
        if (Build.VERSION.SDK_INT >= 16) {
            this.zzds = 0;
        } else if (packageManager != null) {
            this.zzds = InstantApps.isInstantApp(getContext()) ? 1 : 0;
        } else {
            this.zzds = 0;
        }
    }

    final int zzgf() {
        zzbi();
        return this.zzjr;
    }

    final int zzgg() {
        zzbi();
        return this.zzds;
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
