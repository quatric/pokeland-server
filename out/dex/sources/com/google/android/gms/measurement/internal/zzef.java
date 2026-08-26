package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.support.annotation.GuardedBy;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzef extends zzge {
    private char zzkg;

    @GuardedBy("this")
    private String zzkh;
    private final zzeh zzki;
    private final zzeh zzkj;
    private final zzeh zzkk;
    private final zzeh zzkl;
    private final zzeh zzkm;
    private final zzeh zzkn;
    private final zzeh zzko;
    private final zzeh zzkp;
    private final zzeh zzkq;
    private long zzr;

    zzef(zzfj zzfjVar) {
        super(zzfjVar);
        this.zzkg = (char) 0;
        this.zzr = -1L;
        this.zzki = new zzeh(this, 6, false, false);
        this.zzkj = new zzeh(this, 6, true, false);
        this.zzkk = new zzeh(this, 6, false, true);
        this.zzkl = new zzeh(this, 5, false, false);
        this.zzkm = new zzeh(this, 5, true, false);
        this.zzkn = new zzeh(this, 5, false, true);
        this.zzko = new zzeh(this, 4, false, false);
        this.zzkp = new zzeh(this, 3, false, false);
        this.zzkq = new zzeh(this, 2, false, false);
    }

    @VisibleForTesting
    private static String zza(boolean z, Object obj) {
        String className;
        if (obj == null) {
            return "";
        }
        if (obj instanceof Integer) {
            obj = Long.valueOf(((Integer) obj).intValue());
        }
        if (obj instanceof Long) {
            if (!z) {
                return String.valueOf(obj);
            }
            Long l = (Long) obj;
            if (Math.abs(l.longValue()) < 100) {
                return String.valueOf(obj);
            }
            String str = String.valueOf(obj).charAt(0) == '-' ? "-" : "";
            String strValueOf = String.valueOf(Math.abs(l.longValue()));
            long jRound = Math.round(Math.pow(10.0d, strValueOf.length() - 1));
            long jRound2 = Math.round(Math.pow(10.0d, strValueOf.length()) - 1.0d);
            StringBuilder sb = new StringBuilder(str.length() + 43 + str.length());
            sb.append(str);
            sb.append(jRound);
            sb.append("...");
            sb.append(str);
            sb.append(jRound2);
            return sb.toString();
        }
        if (obj instanceof Boolean) {
            return String.valueOf(obj);
        }
        if (!(obj instanceof Throwable)) {
            if (obj instanceof zzeg) {
                return ((zzeg) obj).zzkr;
            }
            return z ? "-" : String.valueOf(obj);
        }
        Throwable th = (Throwable) obj;
        StringBuilder sb2 = new StringBuilder(z ? th.getClass().getName() : th.toString());
        String strZzan = zzan(zzfj.class.getCanonicalName());
        for (StackTraceElement stackTraceElement : th.getStackTrace()) {
            if (!stackTraceElement.isNativeMethod() && (className = stackTraceElement.getClassName()) != null && zzan(className).equals(strZzan)) {
                sb2.append(": ");
                sb2.append(stackTraceElement);
                break;
            }
        }
        return sb2.toString();
    }

    static String zza(boolean z, String str, Object obj, Object obj2, Object obj3) {
        String str2 = "";
        if (str == null) {
            str = "";
        }
        String strZza = zza(z, obj);
        String strZza2 = zza(z, obj2);
        String strZza3 = zza(z, obj3);
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
            str2 = ": ";
        }
        if (!TextUtils.isEmpty(strZza)) {
            sb.append(str2);
            sb.append(strZza);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(strZza2)) {
            sb.append(str2);
            sb.append(strZza2);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(strZza3)) {
            sb.append(str2);
            sb.append(strZza3);
        }
        return sb.toString();
    }

    protected static Object zzam(String str) {
        if (str == null) {
            return null;
        }
        return new zzeg(str);
    }

    private static String zzan(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int iLastIndexOf = str.lastIndexOf(46);
        return iLastIndexOf == -1 ? str : str.substring(0, iLastIndexOf);
    }

    @VisibleForTesting
    private final String zzgt() {
        String str;
        synchronized (this) {
            if (this.zzkh == null) {
                if (this.zzj.zzhz() != null) {
                    this.zzkh = this.zzj.zzhz();
                } else {
                    this.zzkh = zzs.zzbm();
                }
            }
            str = this.zzkh;
        }
        return str;
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @VisibleForTesting
    protected final boolean isLoggable(int i) {
        return Log.isLoggable(zzgt(), i);
    }

    @VisibleForTesting
    protected final void zza(int i, String str) {
        Log.println(i, zzgt(), str);
    }

    protected final void zza(int i, boolean z, boolean z2, String str, Object obj, Object obj2, Object obj3) {
        if (!z && isLoggable(i)) {
            zza(i, zza(false, str, obj, obj2, obj3));
        }
        if (z2 || i < 5) {
            return;
        }
        Preconditions.checkNotNull(str);
        zzfc zzfcVarZzhu = this.zzj.zzhu();
        if (zzfcVarZzhu == null) {
            zza(6, "Scheduler not set. Not logging error/warn");
        } else {
            if (!zzfcVarZzhu.isInitialized()) {
                zza(6, "Scheduler not initialized. Not logging error/warn");
                return;
            }
            if (i < 0) {
                i = 0;
            }
            zzfcVarZzhu.zza(new zzee(this, i >= 9 ? 8 : i, str, obj, obj2, obj3));
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

    @Override // com.google.android.gms.measurement.internal.zzge
    protected final boolean zzbk() {
        return false;
    }

    public final zzeh zzgk() {
        return this.zzki;
    }

    public final zzeh zzgl() {
        return this.zzkj;
    }

    public final zzeh zzgm() {
        return this.zzkk;
    }

    public final zzeh zzgn() {
        return this.zzkl;
    }

    public final zzeh zzgo() {
        return this.zzkm;
    }

    public final zzeh zzgp() {
        return this.zzkn;
    }

    public final zzeh zzgq() {
        return this.zzko;
    }

    public final zzeh zzgr() {
        return this.zzkp;
    }

    public final zzeh zzgs() {
        return this.zzkq;
    }

    public final String zzgu() {
        Pair<String, Long> pairZzhl = zzac().zzli.zzhl();
        if (pairZzhl == null || pairZzhl == zzeo.zzlg) {
            return null;
        }
        String strValueOf = String.valueOf(pairZzhl.second);
        String str = (String) pairZzhl.first;
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 1 + String.valueOf(str).length());
        sb.append(strValueOf);
        sb.append(":");
        sb.append(str);
        return sb.toString();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzl() {
        super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzm() {
        super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzn() {
        super.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzo() {
        super.zzo();
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
