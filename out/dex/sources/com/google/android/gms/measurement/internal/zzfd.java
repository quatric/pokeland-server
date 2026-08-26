package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzfd extends zzjh implements zzu {

    @VisibleForTesting
    private static int zznk = 65535;

    @VisibleForTesting
    private static int zznl = 2;
    private final Map<String, Map<String, String>> zznm;
    private final Map<String, Map<String, Boolean>> zznn;
    private final Map<String, Map<String, Boolean>> zzno;
    private final Map<String, com.google.android.gms.internal.measurement.zzbw> zznp;
    private final Map<String, Map<String, Integer>> zznq;
    private final Map<String, String> zznr;

    zzfd(zzjg zzjgVar) {
        super(zzjgVar);
        this.zznm = new ArrayMap();
        this.zznn = new ArrayMap();
        this.zzno = new ArrayMap();
        this.zznp = new ArrayMap();
        this.zznr = new ArrayMap();
        this.zznq = new ArrayMap();
    }

    @WorkerThread
    private final com.google.android.gms.internal.measurement.zzbw zza(String str, byte[] bArr) {
        if (bArr == null) {
            return new com.google.android.gms.internal.measurement.zzbw();
        }
        com.google.android.gms.internal.measurement.zzil zzilVarZzj = com.google.android.gms.internal.measurement.zzil.zzj(bArr, 0, bArr.length);
        com.google.android.gms.internal.measurement.zzbw zzbwVar = new com.google.android.gms.internal.measurement.zzbw();
        try {
            zzbwVar.zza(zzilVarZzj);
            zzab().zzgs().zza("Parsed config. version, gmp_app_id", zzbwVar.zzzk, zzbwVar.zzcg);
            return zzbwVar;
        } catch (IOException e) {
            zzab().zzgn().zza("Unable to merge remote config. appId", zzef.zzam(str), e);
            return new com.google.android.gms.internal.measurement.zzbw();
        }
    }

    private static Map<String, String> zza(com.google.android.gms.internal.measurement.zzbw zzbwVar) {
        ArrayMap arrayMap = new ArrayMap();
        if (zzbwVar != null && zzbwVar.zzzm != null) {
            for (com.google.android.gms.internal.measurement.zzbq.zza zzaVar : zzbwVar.zzzm) {
                if (zzaVar != null) {
                    arrayMap.put(zzaVar.getKey(), zzaVar.getValue());
                }
            }
        }
        return arrayMap;
    }

    private final void zza(String str, com.google.android.gms.internal.measurement.zzbw zzbwVar) {
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        ArrayMap arrayMap3 = new ArrayMap();
        if (zzbwVar != null && zzbwVar.zzzn != null) {
            for (com.google.android.gms.internal.measurement.zzbx zzbxVar : zzbwVar.zzzn) {
                if (TextUtils.isEmpty(zzbxVar.name)) {
                    zzab().zzgn().zzao("EventConfig contained null event name");
                } else {
                    String strZzbe = zzgj.zzbe(zzbxVar.name);
                    if (!TextUtils.isEmpty(strZzbe)) {
                        zzbxVar.name = strZzbe;
                    }
                    arrayMap.put(zzbxVar.name, zzbxVar.zzzs);
                    arrayMap2.put(zzbxVar.name, zzbxVar.zzzt);
                    if (zzbxVar.zzzu != null) {
                        if (zzbxVar.zzzu.intValue() < zznl || zzbxVar.zzzu.intValue() > zznk) {
                            zzab().zzgn().zza("Invalid sampling rate. Event name, sample rate", zzbxVar.name, zzbxVar.zzzu);
                        } else {
                            arrayMap3.put(zzbxVar.name, zzbxVar.zzzu);
                        }
                    }
                }
            }
        }
        this.zznn.put(str, arrayMap);
        this.zzno.put(str, arrayMap2);
        this.zznq.put(str, arrayMap3);
    }

    @WorkerThread
    private final void zzav(String str) {
        zzbi();
        zzo();
        Preconditions.checkNotEmpty(str);
        if (this.zznp.get(str) == null) {
            byte[] bArrZzad = zzgy().zzad(str);
            if (bArrZzad != null) {
                com.google.android.gms.internal.measurement.zzbw zzbwVarZza = zza(str, bArrZzad);
                this.zznm.put(str, zza(zzbwVarZza));
                zza(str, zzbwVarZza);
                this.zznp.put(str, zzbwVarZza);
                this.zznr.put(str, null);
                return;
            }
            this.zznm.put(str, null);
            this.zznn.put(str, null);
            this.zzno.put(str, null);
            this.zznp.put(str, null);
            this.zznr.put(str, null);
            this.zznq.put(str, null);
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @WorkerThread
    protected final boolean zza(String str, byte[] bArr, String str2) {
        byte[] bArr2;
        boolean z;
        zzbi();
        zzo();
        Preconditions.checkNotEmpty(str);
        com.google.android.gms.internal.measurement.zzbw zzbwVarZza = zza(str, bArr);
        if (zzbwVarZza == null) {
            return false;
        }
        zza(str, zzbwVarZza);
        this.zznp.put(str, zzbwVarZza);
        this.zznr.put(str, str2);
        this.zznm.put(str, zza(zzbwVarZza));
        zzp zzpVarZzgx = zzgx();
        com.google.android.gms.internal.measurement.zzbv[] zzbvVarArr = zzbwVarZza.zzzo;
        Preconditions.checkNotNull(zzbvVarArr);
        for (com.google.android.gms.internal.measurement.zzbv zzbvVar : zzbvVarArr) {
            if (zzbvVar.zzzh != null) {
                for (int i = 0; i < zzbvVar.zzzh.length; i++) {
                    com.google.android.gms.internal.measurement.zzbk.zza.C1274zza c1274zzaZzuj = zzbvVar.zzzh[i].zzuj();
                    com.google.android.gms.internal.measurement.zzbk.zza.C1274zza c1274zza = (com.google.android.gms.internal.measurement.zzbk.zza.C1274zza) ((com.google.android.gms.internal.measurement.zzey.zza) c1274zzaZzuj.clone());
                    String strZzbe = zzgj.zzbe(c1274zzaZzuj.zzjz());
                    if (strZzbe != null) {
                        c1274zza.zzbs(strZzbe);
                        z = true;
                    } else {
                        z = false;
                    }
                    boolean z2 = z;
                    for (int i2 = 0; i2 < c1274zzaZzuj.zzka(); i2++) {
                        com.google.android.gms.internal.measurement.zzbk.zzb zzbVarZze = c1274zzaZzuj.zze(i2);
                        String strZzbe2 = zzgi.zzbe(zzbVarZze.zzkr());
                        if (strZzbe2 != null) {
                            c1274zza.zza(i2, (com.google.android.gms.internal.measurement.zzbk.zzb) ((com.google.android.gms.internal.measurement.zzey) zzbVarZze.zzuj().zzbu(strZzbe2).zzug()));
                            z2 = true;
                        }
                    }
                    if (z2) {
                        zzbvVar.zzzh[i] = (com.google.android.gms.internal.measurement.zzbk.zza) ((com.google.android.gms.internal.measurement.zzey) c1274zza.zzug());
                    }
                }
            }
            if (zzbvVar.zzzg != null) {
                for (int i3 = 0; i3 < zzbvVar.zzzg.length; i3++) {
                    com.google.android.gms.internal.measurement.zzbk.zzd zzdVar = zzbvVar.zzzg[i3];
                    String strZzbe3 = zzgl.zzbe(zzdVar.getPropertyName());
                    if (strZzbe3 != null) {
                        zzbvVar.zzzg[i3] = (com.google.android.gms.internal.measurement.zzbk.zzd) ((com.google.android.gms.internal.measurement.zzey) zzdVar.zzuj().zzbw(strZzbe3).zzug());
                    }
                }
            }
        }
        zzpVarZzgx.zzgy().zza(str, zzbvVarArr);
        try {
            zzbwVarZza.zzzo = null;
            bArr2 = new byte[zzbwVarZza.zzuk()];
            zzbwVarZza.zza(com.google.android.gms.internal.measurement.zzio.zzk(bArr2, 0, bArr2.length));
        } catch (IOException e) {
            zzab().zzgn().zza("Unable to serialize reduced-size config. Storing full config instead. appId", zzef.zzam(str), e);
            bArr2 = bArr;
        }
        zzx zzxVarZzgy = zzgy();
        Preconditions.checkNotEmpty(str);
        zzxVarZzgy.zzo();
        zzxVarZzgy.zzbi();
        ContentValues contentValues = new ContentValues();
        contentValues.put("remote_config", bArr2);
        try {
            if (zzxVarZzgy.getWritableDatabase().update("apps", contentValues, "app_id = ?", new String[]{str}) == 0) {
                zzxVarZzgy.zzab().zzgk().zza("Failed to update remote config (got 0). appId", zzef.zzam(str));
            }
        } catch (SQLiteException e2) {
            zzxVarZzgy.zzab().zzgk().zza("Error storing remote config. appId", zzef.zzam(str), e2);
        }
        return true;
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

    @WorkerThread
    protected final com.google.android.gms.internal.measurement.zzbw zzaw(String str) {
        zzbi();
        zzo();
        Preconditions.checkNotEmpty(str);
        zzav(str);
        return this.zznp.get(str);
    }

    @WorkerThread
    protected final String zzax(String str) {
        zzo();
        return this.zznr.get(str);
    }

    @WorkerThread
    protected final void zzay(String str) {
        zzo();
        this.zznr.put(str, null);
    }

    @WorkerThread
    final void zzaz(String str) {
        zzo();
        this.zznp.remove(str);
    }

    @Override // com.google.android.gms.measurement.internal.zzu
    @WorkerThread
    public final String zzb(String str, String str2) {
        zzo();
        zzav(str);
        Map<String, String> map = this.zznm.get(str);
        if (map != null) {
            return map.get(str2);
        }
        return null;
    }

    @WorkerThread
    final boolean zzba(String str) {
        Boolean bool;
        zzo();
        com.google.android.gms.internal.measurement.zzbw zzbwVarZzaw = zzaw(str);
        if (zzbwVarZzaw == null || (bool = zzbwVarZzaw.zzzq) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @WorkerThread
    final long zzbb(String str) {
        String strZzb = zzb(str, "measurement.account.time_zone_offset_minutes");
        if (TextUtils.isEmpty(strZzb)) {
            return 0L;
        }
        try {
            return Long.parseLong(strZzb);
        } catch (NumberFormatException e) {
            zzab().zzgn().zza("Unable to parse timezone offset. appId", zzef.zzam(str), e);
            return 0L;
        }
    }

    final boolean zzbc(String str) {
        return "1".equals(zzb(str, "measurement.upload.blacklist_internal"));
    }

    final boolean zzbd(String str) {
        return "1".equals(zzb(str, "measurement.upload.blacklist_public"));
    }

    @Override // com.google.android.gms.measurement.internal.zzjh
    protected final boolean zzbk() {
        return false;
    }

    @Override // com.google.android.gms.measurement.internal.zzje
    public final /* bridge */ /* synthetic */ zzjo zzgw() {
        return super.zzgw();
    }

    @Override // com.google.android.gms.measurement.internal.zzje
    public final /* bridge */ /* synthetic */ zzp zzgx() {
        return super.zzgx();
    }

    @Override // com.google.android.gms.measurement.internal.zzje
    public final /* bridge */ /* synthetic */ zzx zzgy() {
        return super.zzgy();
    }

    @Override // com.google.android.gms.measurement.internal.zzje
    public final /* bridge */ /* synthetic */ zzfd zzgz() {
        return super.zzgz();
    }

    @WorkerThread
    final boolean zzk(String str, String str2) {
        Boolean bool;
        zzo();
        zzav(str);
        if (zzbc(str) && zzjs.zzbq(str2)) {
            return true;
        }
        if (zzbd(str) && zzjs.zzbk(str2)) {
            return true;
        }
        Map<String, Boolean> map = this.zznn.get(str);
        if (map == null || (bool = map.get(str2)) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzl() {
        super.zzl();
    }

    @WorkerThread
    final boolean zzl(String str, String str2) {
        Boolean bool;
        zzo();
        zzav(str);
        if (FirebaseAnalytics.Event.ECOMMERCE_PURCHASE.equals(str2)) {
            return true;
        }
        Map<String, Boolean> map = this.zzno.get(str);
        if (map == null || (bool = map.get(str2)) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @WorkerThread
    final int zzm(String str, String str2) {
        Integer num;
        zzo();
        zzav(str);
        Map<String, Integer> map = this.zznq.get(str);
        if (map == null || (num = map.get(str2)) == null) {
            return 1;
        }
        return num.intValue();
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
