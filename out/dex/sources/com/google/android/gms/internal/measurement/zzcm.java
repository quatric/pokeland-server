package com.google.android.gms.internal.measurement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class zzcm<T> {
    private final String name;
    private final zzct zzaaz;
    private final T zzaba;
    private volatile int zzabc;
    private volatile T zzjq;
    private static final Object zzaax = new Object();

    @SuppressLint({"StaticFieldLeak"})
    private static Context zzob = null;
    private static boolean zzaay = false;
    private static final AtomicInteger zzabb = new AtomicInteger();

    private zzcm(zzct zzctVar, String str, T t) {
        this.zzabc = -1;
        if (zzctVar.zzabh == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        }
        this.zzaaz = zzctVar;
        this.name = str;
        this.zzaba = t;
    }

    /* synthetic */ zzcm(zzct zzctVar, String str, Object obj, zzcp zzcpVar) {
        this(zzctVar, str, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static zzcm<Double> zza(zzct zzctVar, String str, double d) {
        return new zzcr(zzctVar, str, Double.valueOf(d));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static zzcm<Long> zza(zzct zzctVar, String str, long j) {
        return new zzcp(zzctVar, str, Long.valueOf(j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static zzcm<String> zza(zzct zzctVar, String str, String str2) {
        return new zzcq(zzctVar, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static zzcm<Boolean> zza(zzct zzctVar, String str, boolean z) {
        return new zzco(zzctVar, str, Boolean.valueOf(z));
    }

    private final String zzdg(String str) {
        if (str != null && str.isEmpty()) {
            return this.name;
        }
        String strValueOf = String.valueOf(str);
        String strValueOf2 = String.valueOf(this.name);
        return strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
    }

    public static void zzr(Context context) {
        synchronized (zzaax) {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext != null) {
                context = applicationContext;
            }
            if (zzob != context) {
                synchronized (zzca.class) {
                    zzca.zzaah.clear();
                }
                synchronized (zzcs.class) {
                    zzcs.zzabd.clear();
                }
                synchronized (zzcj.class) {
                    zzcj.zzaau = null;
                }
                zzabb.incrementAndGet();
                zzob = context;
            }
        }
    }

    static void zzrl() {
        zzabb.incrementAndGet();
    }

    @Nullable
    private final T zzrn() {
        zzce zzceVarZze;
        Object objZzdd;
        zzct zzctVar = this.zzaaz;
        String str = (String) zzcj.zzp(zzob).zzdd("gms:phenotype:phenotype_flag:debug_bypass_phenotype");
        if (!(str != null && zzbz.zzzw.matcher(str).matches())) {
            if (this.zzaaz.zzabh == null) {
                Context context = zzob;
                zzct zzctVar2 = this.zzaaz;
                zzceVarZze = zzcs.zze(context, null);
            } else if (zzck.zza(zzob, this.zzaaz.zzabh)) {
                zzct zzctVar3 = this.zzaaz;
                zzceVarZze = zzca.zza(zzob.getContentResolver(), this.zzaaz.zzabh);
            } else {
                zzceVarZze = null;
            }
            if (zzceVarZze != null && (objZzdd = zzceVarZze.zzdd(zzrm())) != null) {
                return zzc(objZzdd);
            }
        } else if (Log.isLoggable("PhenotypeFlag", 3)) {
            String strValueOf = String.valueOf(zzrm());
            Log.d("PhenotypeFlag", strValueOf.length() != 0 ? "Bypass reading Phenotype values for flag: ".concat(strValueOf) : new String("Bypass reading Phenotype values for flag: "));
        }
        return null;
    }

    @Nullable
    private final T zzro() {
        zzct zzctVar = this.zzaaz;
        Object objZzdd = zzcj.zzp(zzob).zzdd(zzdg(this.zzaaz.zzabi));
        if (objZzdd != null) {
            return zzc(objZzdd);
        }
        return null;
    }

    public final T get() {
        int i = zzabb.get();
        if (this.zzabc < i) {
            synchronized (this) {
                if (this.zzabc < i) {
                    if (zzob == null) {
                        throw new IllegalStateException("Must call PhenotypeFlag.init() first");
                    }
                    zzct zzctVar = this.zzaaz;
                    T tZzrn = zzrn();
                    if (tZzrn == null && (tZzrn = zzro()) == null) {
                        tZzrn = this.zzaba;
                    }
                    this.zzjq = tZzrn;
                    this.zzabc = i;
                }
            }
        }
        return this.zzjq;
    }

    abstract T zzc(Object obj);

    public final String zzrm() {
        return zzdg(this.zzaaz.zzabj);
    }
}
