package com.google.android.gms.measurement.internal;

import android.os.Binder;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.GoogleSignatureVerifier;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzfk extends zzdw {
    private final zzjg zzkz;
    private Boolean zzpb;

    @Nullable
    private String zzpc;

    public zzfk(zzjg zzjgVar) {
        this(zzjgVar, null);
    }

    private zzfk(zzjg zzjgVar, @Nullable String str) {
        Preconditions.checkNotNull(zzjgVar);
        this.zzkz = zzjgVar;
        this.zzpc = null;
    }

    @BinderThread
    private final void zza(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            this.zzkz.zzab().zzgk().zzao("Measurement Service called without app package");
            throw new SecurityException("Measurement Service called without app package");
        }
        if (z) {
            try {
                if (this.zzpb == null) {
                    this.zzpb = Boolean.valueOf("com.google.android.gms".equals(this.zzpc) || UidVerifier.isGooglePlayServicesUid(this.zzkz.getContext(), Binder.getCallingUid()) || GoogleSignatureVerifier.getInstance(this.zzkz.getContext()).isUidGoogleSigned(Binder.getCallingUid()));
                }
                if (this.zzpb.booleanValue()) {
                    return;
                }
            } catch (SecurityException e) {
                this.zzkz.zzab().zzgk().zza("Measurement Service called with invalid calling package. appId", zzef.zzam(str));
                throw e;
            }
        }
        if (this.zzpc == null && GooglePlayServicesUtilLight.uidHasPackageName(this.zzkz.getContext(), Binder.getCallingUid(), str)) {
            this.zzpc = str;
        }
        if (str.equals(this.zzpc)) {
        } else {
            throw new SecurityException(String.format("Unknown calling package name '%s'.", str));
        }
    }

    @BinderThread
    private final void zzb(zzn zznVar, boolean z) {
        Preconditions.checkNotNull(zznVar);
        zza(zznVar.packageName, false);
        this.zzkz.zzz().zzr(zznVar.zzcg, zznVar.zzcu);
    }

    @VisibleForTesting
    private final void zzc(Runnable runnable) {
        Preconditions.checkNotNull(runnable);
        if (this.zzkz.zzaa().zzhp()) {
            runnable.run();
        } else {
            this.zzkz.zzaa().zza(runnable);
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    @BinderThread
    public final List<zzjn> zza(zzn zznVar, boolean z) {
        zzb(zznVar, false);
        try {
            List<zzjp> list = (List) this.zzkz.zzaa().zza(new zzga(this, zznVar)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzjp zzjpVar : list) {
                if (z || !zzjs.zzbq(zzjpVar.name)) {
                    arrayList.add(new zzjn(zzjpVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zzkz.zzab().zzgk().zza("Failed to get user attributes. appId", zzef.zzam(zznVar.packageName), e);
            return null;
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    @BinderThread
    public final List<zzq> zza(String str, String str2, zzn zznVar) {
        zzb(zznVar, false);
        try {
            return (List) this.zzkz.zzaa().zza(new zzfs(this, zznVar, str, str2)).get();
        } catch (InterruptedException | ExecutionException e) {
            this.zzkz.zzab().zzgk().zza("Failed to get conditional user properties", e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    @BinderThread
    public final List<zzjn> zza(String str, String str2, String str3, boolean z) {
        zza(str, true);
        try {
            List<zzjp> list = (List) this.zzkz.zzaa().zza(new zzft(this, str, str2, str3)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzjp zzjpVar : list) {
                if (z || !zzjs.zzbq(zzjpVar.name)) {
                    arrayList.add(new zzjn(zzjpVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zzkz.zzab().zzgk().zza("Failed to get user attributes. appId", zzef.zzam(str), e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    @BinderThread
    public final List<zzjn> zza(String str, String str2, boolean z, zzn zznVar) {
        zzb(zznVar, false);
        try {
            List<zzjp> list = (List) this.zzkz.zzaa().zza(new zzfq(this, zznVar, str, str2)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzjp zzjpVar : list) {
                if (z || !zzjs.zzbq(zzjpVar.name)) {
                    arrayList.add(new zzjn(zzjpVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zzkz.zzab().zzgk().zza("Failed to get user attributes. appId", zzef.zzam(zznVar.packageName), e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    @BinderThread
    public final void zza(long j, String str, String str2, String str3) {
        zzc(new zzgc(this, str2, str3, str, j));
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    @BinderThread
    public final void zza(zzai zzaiVar, zzn zznVar) {
        Preconditions.checkNotNull(zzaiVar);
        zzb(zznVar, false);
        zzc(new zzfx(this, zzaiVar, zznVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    @BinderThread
    public final void zza(zzai zzaiVar, String str, String str2) {
        Preconditions.checkNotNull(zzaiVar);
        Preconditions.checkNotEmpty(str);
        zza(str, true);
        zzc(new zzfw(this, zzaiVar, str));
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    @BinderThread
    public final void zza(zzjn zzjnVar, zzn zznVar) {
        Preconditions.checkNotNull(zzjnVar);
        zzb(zznVar, false);
        if (zzjnVar.getValue() == null) {
            zzc(new zzfy(this, zzjnVar, zznVar));
        } else {
            zzc(new zzgb(this, zzjnVar, zznVar));
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    @BinderThread
    public final void zza(zzn zznVar) {
        zzb(zznVar, false);
        zzc(new zzgd(this, zznVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    @BinderThread
    public final void zza(zzq zzqVar, zzn zznVar) {
        Preconditions.checkNotNull(zzqVar);
        Preconditions.checkNotNull(zzqVar.zzdw);
        zzb(zznVar, false);
        zzq zzqVar2 = new zzq(zzqVar);
        zzqVar2.packageName = zznVar.packageName;
        if (zzqVar.zzdw.getValue() == null) {
            zzc(new zzfm(this, zzqVar2, zznVar));
        } else {
            zzc(new zzfp(this, zzqVar2, zznVar));
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    @BinderThread
    public final byte[] zza(zzai zzaiVar, String str) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzaiVar);
        zza(str, true);
        this.zzkz.zzab().zzgr().zza("Log and bundle. event", this.zzkz.zzy().zzaj(zzaiVar.name));
        long jNanoTime = this.zzkz.zzx().nanoTime() / 1000000;
        try {
            byte[] bArr = (byte[]) this.zzkz.zzaa().zzb(new zzfz(this, zzaiVar, str)).get();
            if (bArr == null) {
                this.zzkz.zzab().zzgk().zza("Log and bundle returned null. appId", zzef.zzam(str));
                bArr = new byte[0];
            }
            this.zzkz.zzab().zzgr().zza("Log and bundle processed. event, size, time_ms", this.zzkz.zzy().zzaj(zzaiVar.name), Integer.valueOf(bArr.length), Long.valueOf((this.zzkz.zzx().nanoTime() / 1000000) - jNanoTime));
            return bArr;
        } catch (InterruptedException | ExecutionException e) {
            this.zzkz.zzab().zzgk().zza("Failed to log and bundle. appId, event, error", zzef.zzam(str), this.zzkz.zzy().zzaj(zzaiVar.name), e);
            return null;
        }
    }

    @VisibleForTesting
    final zzai zzb(zzai zzaiVar, zzn zznVar) {
        boolean z = false;
        if ("_cmp".equals(zzaiVar.name) && zzaiVar.zzfq != null && zzaiVar.zzfq.size() != 0) {
            String string = zzaiVar.zzfq.getString("_cis");
            if (!TextUtils.isEmpty(string) && (("referrer broadcast".equals(string) || "referrer API".equals(string)) && this.zzkz.zzad().zzt(zznVar.packageName))) {
                z = true;
            }
        }
        if (!z) {
            return zzaiVar;
        }
        this.zzkz.zzab().zzgq().zza("Event has been filtered ", zzaiVar.toString());
        return new zzai("_cmpx", zzaiVar.zzfq, zzaiVar.origin, zzaiVar.zzfu);
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    @BinderThread
    public final void zzb(zzn zznVar) {
        zzb(zznVar, false);
        zzc(new zzfn(this, zznVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    @BinderThread
    public final void zzb(zzq zzqVar) {
        Preconditions.checkNotNull(zzqVar);
        Preconditions.checkNotNull(zzqVar.zzdw);
        zza(zzqVar.packageName, true);
        zzq zzqVar2 = new zzq(zzqVar);
        if (zzqVar.zzdw.getValue() == null) {
            zzc(new zzfo(this, zzqVar2));
        } else {
            zzc(new zzfr(this, zzqVar2));
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    @BinderThread
    public final String zzc(zzn zznVar) {
        zzb(zznVar, false);
        return this.zzkz.zzh(zznVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    @BinderThread
    public final List<zzq> zzc(String str, String str2, String str3) {
        zza(str, true);
        try {
            return (List) this.zzkz.zzaa().zza(new zzfv(this, str, str2, str3)).get();
        } catch (InterruptedException | ExecutionException e) {
            this.zzkz.zzab().zzgk().zza("Failed to get conditional user properties", e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzdx
    @BinderThread
    public final void zzd(zzn zznVar) {
        zza(zznVar.packageName, false);
        zzc(new zzfu(this, zznVar));
    }
}
