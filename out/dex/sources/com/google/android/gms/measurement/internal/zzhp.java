package com.google.android.gms.measurement.internal;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhp extends zzjh {
    public zzhp(zzjg zzjgVar) {
        super(zzjgVar);
    }

    private static String zzo(String str, String str2) {
        throw new SecurityException("This implementation should not be used.");
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @WorkerThread
    public final byte[] zzb(@NonNull zzai zzaiVar, @Size(min = 1) String str) {
        zzjp next;
        byte[] bArr;
        long j;
        zzae zzaeVarZzw;
        zzo();
        this.zzj.zzl();
        Preconditions.checkNotNull(zzaiVar);
        Preconditions.checkNotEmpty(str);
        if (!zzad().zze(str, zzak.zzio)) {
            zzab().zzgr().zza("Generating ScionPayload disabled. packageName", str);
            return new byte[0];
        }
        if (!"_iap".equals(zzaiVar.name) && !"_iapx".equals(zzaiVar.name)) {
            zzab().zzgr().zza("Generating a payload for this event is not available. package_name, event_name", str, zzaiVar.name);
            return null;
        }
        com.google.android.gms.internal.measurement.zzbs.zzf.zza zzaVarZznj = com.google.android.gms.internal.measurement.zzbs.zzf.zznj();
        zzgy().beginTransaction();
        try {
            zzf zzfVarZzab = zzgy().zzab(str);
            if (zzfVarZzab == null) {
                zzab().zzgr().zza("Log and bundle not available. package_name", str);
                byte[] bArr2 = new byte[0];
                zzgy().endTransaction();
                return bArr2;
            }
            if (!zzfVarZzab.isMeasurementEnabled()) {
                zzab().zzgr().zza("Log and bundle disabled. package_name", str);
                byte[] bArr3 = new byte[0];
                zzgy().endTransaction();
                return bArr3;
            }
            com.google.android.gms.internal.measurement.zzbs.zzg.zza zzaVarZzcc = com.google.android.gms.internal.measurement.zzbs.zzg.zzpr().zzp(1).zzcc("android");
            if (!TextUtils.isEmpty(zzfVarZzab.zzag())) {
                zzaVarZzcc.zzch(zzfVarZzab.zzag());
            }
            if (!TextUtils.isEmpty(zzfVarZzab.zzan())) {
                zzaVarZzcc.zzcg(zzfVarZzab.zzan());
            }
            if (!TextUtils.isEmpty(zzfVarZzab.zzal())) {
                zzaVarZzcc.zzci(zzfVarZzab.zzal());
            }
            if (zzfVarZzab.zzam() != -2147483648L) {
                zzaVarZzcc.zzv((int) zzfVarZzab.zzam());
            }
            zzaVarZzcc.zzas(zzfVarZzab.zzao()).zzax(zzfVarZzab.zzaq());
            if (!TextUtils.isEmpty(zzfVarZzab.getGmpAppId())) {
                zzaVarZzcc.zzcm(zzfVarZzab.getGmpAppId());
            } else if (!TextUtils.isEmpty(zzfVarZzab.zzah())) {
                zzaVarZzcc.zzcq(zzfVarZzab.zzah());
            }
            zzaVarZzcc.zzau(zzfVarZzab.zzap());
            if (this.zzj.isEnabled() && zzs.zzbv() && zzad().zzl(zzaVarZzcc.zzag())) {
                zzaVarZzcc.zzag();
                if (!TextUtils.isEmpty(null)) {
                    zzaVarZzcc.zzcp(null);
                }
            }
            Pair<String, Boolean> pairZzap = zzac().zzap(zzfVarZzab.zzag());
            if (zzfVarZzab.zzbe() && pairZzap != null && !TextUtils.isEmpty((CharSequence) pairZzap.first)) {
                try {
                    zzaVarZzcc.zzcj(zzo((String) pairZzap.first, Long.toString(zzaiVar.zzfu)));
                    if (pairZzap.second != null) {
                        zzaVarZzcc.zzm(((Boolean) pairZzap.second).booleanValue());
                    }
                } catch (SecurityException e) {
                    zzab().zzgr().zza("Resettable device id encryption failed", e.getMessage());
                    byte[] bArr4 = new byte[0];
                    zzgy().endTransaction();
                    return bArr4;
                }
            }
            zzw().zzbi();
            com.google.android.gms.internal.measurement.zzbs.zzg.zza zzaVarZzce = zzaVarZzcc.zzce(Build.MODEL);
            zzw().zzbi();
            zzaVarZzce.zzcd(Build.VERSION.RELEASE).zzt((int) zzw().zzcq()).zzcf(zzw().zzcr());
            try {
                zzaVarZzcc.zzck(zzo(zzfVarZzab.getAppInstanceId(), Long.toString(zzaiVar.zzfu)));
                if (!TextUtils.isEmpty(zzfVarZzab.getFirebaseInstanceId())) {
                    zzaVarZzcc.zzcn(zzfVarZzab.getFirebaseInstanceId());
                }
                String strZzag = zzfVarZzab.zzag();
                List<zzjp> listZzaa = zzgy().zzaa(strZzag);
                Iterator<zzjp> it = listZzaa.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!"_lte".equals(next.name));
                if (next == null || next.value == null) {
                    zzjp zzjpVar = new zzjp(strZzag, "auto", "_lte", zzx().currentTimeMillis(), 0L);
                    listZzaa.add(zzjpVar);
                    zzgy().zza(zzjpVar);
                }
                if (zzad().zze(strZzag, zzak.zzij)) {
                    zzjo zzjoVarZzgw = zzgw();
                    zzjoVarZzgw.zzab().zzgs().zzao("Checking account type status for ad personalization signals");
                    if (zzjoVarZzgw.zzw().zzcu()) {
                        String strZzag2 = zzfVarZzab.zzag();
                        if (zzfVarZzab.zzbe() && zzjoVarZzgw.zzgz().zzba(strZzag2)) {
                            zzjoVarZzgw.zzab().zzgr().zzao("Turning off ad personalization due to account type");
                            Iterator<zzjp> it2 = listZzaa.iterator();
                            while (it2.hasNext()) {
                                if ("_npa".equals(it2.next().name)) {
                                    it2.remove();
                                    break;
                                }
                            }
                            listZzaa.add(new zzjp(strZzag2, "auto", "_npa", zzjoVarZzgw.zzx().currentTimeMillis(), 1L));
                        }
                    }
                }
                com.google.android.gms.internal.measurement.zzbs.zzk[] zzkVarArr = new com.google.android.gms.internal.measurement.zzbs.zzk[listZzaa.size()];
                for (int i = 0; i < listZzaa.size(); i++) {
                    com.google.android.gms.internal.measurement.zzbs.zzk.zza zzaVarZzbk = com.google.android.gms.internal.measurement.zzbs.zzk.zzqu().zzdb(listZzaa.get(i).name).zzbk(listZzaa.get(i).zztr);
                    zzgw().zza(zzaVarZzbk, listZzaa.get(i).value);
                    zzkVarArr[i] = (com.google.android.gms.internal.measurement.zzbs.zzk) ((com.google.android.gms.internal.measurement.zzey) zzaVarZzbk.zzug());
                }
                zzaVarZzcc.zzb(Arrays.asList(zzkVarArr));
                Bundle bundleZzcv = zzaiVar.zzfq.zzcv();
                bundleZzcv.putLong("_c", 1L);
                zzab().zzgr().zzao("Marking in-app purchase as real-time");
                bundleZzcv.putLong("_r", 1L);
                bundleZzcv.putString("_o", zzaiVar.origin);
                if (zzz().zzbr(zzaVarZzcc.zzag())) {
                    zzz().zza(bundleZzcv, "_dbg", (Object) 1L);
                    zzz().zza(bundleZzcv, "_r", (Object) 1L);
                }
                zzae zzaeVarZzc = zzgy().zzc(str, zzaiVar.name);
                if (zzaeVarZzc == null) {
                    bArr = null;
                    zzaeVarZzw = new zzae(str, zzaiVar.name, 0L, 0L, zzaiVar.zzfu, 0L, null, null, null, null);
                    j = 0;
                } else {
                    bArr = null;
                    j = zzaeVarZzc.zzfj;
                    zzaeVarZzw = zzaeVarZzc.zzw(zzaiVar.zzfu);
                }
                zzgy().zza(zzaeVarZzw);
                zzaf zzafVar = new zzaf(this.zzj, zzaiVar.origin, str, zzaiVar.name, zzaiVar.zzfu, j, bundleZzcv);
                com.google.android.gms.internal.measurement.zzbs.zzc.zza zzaVarZzah = com.google.android.gms.internal.measurement.zzbs.zzc.zzmq().zzag(zzafVar.timestamp).zzbx(zzafVar.name).zzah(zzafVar.zzfp);
                for (String str2 : zzafVar.zzfq) {
                    com.google.android.gms.internal.measurement.zzbs.zze.zza zzaVarZzbz = com.google.android.gms.internal.measurement.zzbs.zze.zzng().zzbz(str2);
                    zzgw().zza(zzaVarZzbz, zzafVar.zzfq.get(str2));
                    zzaVarZzah.zza(zzaVarZzbz);
                }
                zzaVarZzcc.zza(zzaVarZzah).zza(com.google.android.gms.internal.measurement.zzbs.zzh.zzpt().zza(com.google.android.gms.internal.measurement.zzbs.zzd.zzms().zzak(zzaeVarZzw.zzfg).zzby(zzaiVar.name)));
                zzaVarZzcc.zzc(zzgx().zza(zzfVarZzab.zzag(), Collections.emptyList(), zzaVarZzcc.zzno()));
                if (zzaVarZzah.zzml()) {
                    zzaVarZzcc.zzao(zzaVarZzah.getTimestampMillis()).zzap(zzaVarZzah.getTimestampMillis());
                }
                long jZzak = zzfVarZzab.zzak();
                if (jZzak != 0) {
                    zzaVarZzcc.zzar(jZzak);
                }
                long jZzaj = zzfVarZzab.zzaj();
                if (jZzaj != 0) {
                    zzaVarZzcc.zzaq(jZzaj);
                } else if (jZzak != 0) {
                    zzaVarZzcc.zzaq(jZzak);
                }
                zzfVarZzab.zzau();
                zzaVarZzcc.zzu((int) zzfVarZzab.zzar()).zzat(zzad().zzao()).zzan(zzx().currentTimeMillis()).zzn(Boolean.TRUE.booleanValue());
                zzaVarZznj.zza(zzaVarZzcc);
                zzfVarZzab.zze(zzaVarZzcc.zznq());
                zzfVarZzab.zzf(zzaVarZzcc.zznr());
                zzgy().zza(zzfVarZzab);
                zzgy().setTransactionSuccessful();
                zzgy().endTransaction();
                try {
                    return zzgw().zzc(((com.google.android.gms.internal.measurement.zzbs.zzf) ((com.google.android.gms.internal.measurement.zzey) zzaVarZznj.zzug())).toByteArray());
                } catch (IOException e2) {
                    zzab().zzgk().zza("Data loss. Failed to bundle and serialize. appId", zzef.zzam(str), e2);
                    return bArr;
                }
            } catch (SecurityException e3) {
                zzab().zzgr().zza("app instance id encryption failed", e3.getMessage());
                byte[] bArr5 = new byte[0];
                zzgy().endTransaction();
                return bArr5;
            }
        } catch (Throwable th) {
            zzgy().endTransaction();
            throw th;
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzjh
    protected final boolean zzbk() {
        return false;
    }
}
