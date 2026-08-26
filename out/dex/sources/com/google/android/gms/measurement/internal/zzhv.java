package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@VisibleForTesting
public final class zzhv extends zzg {
    private final zzin zzre;
    private zzdx zzrf;
    private volatile Boolean zzrg;
    private final zzaa zzrh;
    private final zzjd zzri;
    private final List<Runnable> zzrj;
    private final zzaa zzrk;

    protected zzhv(zzfj zzfjVar) {
        super(zzfjVar);
        this.zzrj = new ArrayList();
        this.zzri = new zzjd(zzfjVar.zzx());
        this.zzre = new zzin(this);
        this.zzrh = new zzhu(this, zzfjVar);
        this.zzrk = new zzif(this, zzfjVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void onServiceDisconnected(ComponentName componentName) {
        zzo();
        if (this.zzrf != null) {
            this.zzrf = null;
            zzab().zzgs().zza("Disconnected from device MeasurementService", componentName);
            zzo();
            zzis();
        }
    }

    static /* synthetic */ zzdx zza(zzhv zzhvVar, zzdx zzdxVar) {
        zzhvVar.zzrf = null;
        return null;
    }

    @WorkerThread
    private final void zzd(Runnable runnable) throws IllegalStateException {
        zzo();
        if (isConnected()) {
            runnable.run();
        } else {
            if (this.zzrj.size() >= 1000) {
                zzab().zzgk().zzao("Discarding data. Max runnable queue size reached");
                return;
            }
            this.zzrj.add(runnable);
            this.zzrk.zzv(60000L);
            zzis();
        }
    }

    @WorkerThread
    @Nullable
    private final zzn zzi(boolean z) {
        zzae();
        return zzr().zzai(z ? zzab().zzgu() : null);
    }

    private final boolean zziq() {
        zzae();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zzir() {
        zzo();
        this.zzri.start();
        this.zzrh.zzv(zzak.zzhl.get(null).longValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zziu() {
        zzo();
        if (isConnected()) {
            zzab().zzgs().zzao("Inactivity, disconnecting from the service");
            disconnect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zziv() {
        zzo();
        zzab().zzgs().zza("Processing queued up service tasks", Integer.valueOf(this.zzrj.size()));
        Iterator<Runnable> it = this.zzrj.iterator();
        while (it.hasNext()) {
            try {
                it.next().run();
            } catch (Exception e) {
                zzab().zzgk().zza("Task exception while flushing queue", e);
            }
        }
        this.zzrj.clear();
        this.zzrk.cancel();
    }

    @WorkerThread
    public final void disconnect() {
        zzo();
        zzbi();
        this.zzre.zziw();
        try {
            ConnectionTracker.getInstance().unbindService(getContext(), this.zzre);
        } catch (IllegalArgumentException | IllegalStateException unused) {
        }
        this.zzrf = null;
    }

    @WorkerThread
    public final void getAppInstanceId(com.google.android.gms.internal.measurement.zzp zzpVar) {
        zzo();
        zzbi();
        zzd(new zzib(this, zzi(false), zzpVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @WorkerThread
    public final boolean isConnected() {
        zzo();
        zzbi();
        return this.zzrf != null;
    }

    @WorkerThread
    protected final void resetAnalyticsData() {
        zzo();
        zzm();
        zzbi();
        zzn zznVarZzi = zzi(false);
        if (zziq()) {
            zzu().resetAnalyticsData();
        }
        zzd(new zzhz(this, zznVarZzi));
    }

    @WorkerThread
    public final void zza(com.google.android.gms.internal.measurement.zzp zzpVar, zzai zzaiVar, String str) {
        zzo();
        zzbi();
        if (zzz().zzd(12451000) == 0) {
            zzd(new zzic(this, zzaiVar, str, zzpVar));
        } else {
            zzab().zzgn().zzao("Not bundling data. Service unavailable or out of date");
            zzz().zza(zzpVar, new byte[0]);
        }
    }

    @WorkerThread
    protected final void zza(com.google.android.gms.internal.measurement.zzp zzpVar, String str, String str2) {
        zzo();
        zzbi();
        zzd(new zzii(this, str, str2, zzi(false), zzpVar));
    }

    @WorkerThread
    protected final void zza(com.google.android.gms.internal.measurement.zzp zzpVar, String str, String str2, boolean z) {
        zzo();
        zzbi();
        zzd(new zzik(this, str, str2, z, zzi(false), zzpVar));
    }

    @WorkerThread
    @VisibleForTesting
    protected final void zza(zzdx zzdxVar) {
        zzo();
        Preconditions.checkNotNull(zzdxVar);
        this.zzrf = zzdxVar;
        zzir();
        zziv();
    }

    @WorkerThread
    @VisibleForTesting
    final void zza(zzdx zzdxVar, AbstractSafeParcelable abstractSafeParcelable, zzn zznVar) {
        int size;
        List<AbstractSafeParcelable> listZzc;
        zzo();
        zzm();
        zzbi();
        boolean zZziq = zziq();
        int i = 0;
        int i2 = 100;
        while (i < 1001 && i2 == 100) {
            ArrayList arrayList = new ArrayList();
            if (!zZziq || (listZzc = zzu().zzc(100)) == null) {
                size = 0;
            } else {
                arrayList.addAll(listZzc);
                size = listZzc.size();
            }
            if (abstractSafeParcelable != null && size < 100) {
                arrayList.add(abstractSafeParcelable);
            }
            ArrayList arrayList2 = arrayList;
            int size2 = arrayList2.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj = arrayList2.get(i3);
                i3++;
                AbstractSafeParcelable abstractSafeParcelable2 = (AbstractSafeParcelable) obj;
                if (abstractSafeParcelable2 instanceof zzai) {
                    try {
                        zzdxVar.zza((zzai) abstractSafeParcelable2, zznVar);
                    } catch (RemoteException e) {
                        zzab().zzgk().zza("Failed to send event to the service", e);
                    }
                } else if (abstractSafeParcelable2 instanceof zzjn) {
                    try {
                        zzdxVar.zza((zzjn) abstractSafeParcelable2, zznVar);
                    } catch (RemoteException e2) {
                        zzab().zzgk().zza("Failed to send attribute to the service", e2);
                    }
                } else if (abstractSafeParcelable2 instanceof zzq) {
                    try {
                        zzdxVar.zza((zzq) abstractSafeParcelable2, zznVar);
                    } catch (RemoteException e3) {
                        zzab().zzgk().zza("Failed to send conditional property to the service", e3);
                    }
                } else {
                    zzab().zzgk().zzao("Discarding data. Unrecognized parcel type.");
                }
            }
            i++;
            i2 = size;
        }
    }

    @WorkerThread
    protected final void zza(zzhr zzhrVar) {
        zzo();
        zzbi();
        zzd(new zzid(this, zzhrVar));
    }

    @WorkerThread
    public final void zza(AtomicReference<String> atomicReference) {
        zzo();
        zzbi();
        zzd(new zzhy(this, atomicReference, zzi(false)));
    }

    @WorkerThread
    protected final void zza(AtomicReference<List<zzq>> atomicReference, String str, String str2, String str3) {
        zzo();
        zzbi();
        zzd(new zzij(this, atomicReference, str, str2, str3, zzi(false)));
    }

    @WorkerThread
    protected final void zza(AtomicReference<List<zzjn>> atomicReference, String str, String str2, String str3, boolean z) {
        zzo();
        zzbi();
        zzd(new zzil(this, atomicReference, str, str2, str3, z, zzi(false)));
    }

    @WorkerThread
    protected final void zza(AtomicReference<List<zzjn>> atomicReference, boolean z) {
        zzo();
        zzbi();
        zzd(new zzhw(this, atomicReference, zzi(false), z));
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
    protected final void zzb(zzjn zzjnVar) {
        zzo();
        zzbi();
        zzd(new zzhx(this, zziq() && zzu().zza(zzjnVar), zzjnVar, zzi(true)));
    }

    @Override // com.google.android.gms.measurement.internal.zzg
    protected final boolean zzbk() {
        return false;
    }

    @WorkerThread
    protected final void zzc(zzai zzaiVar, String str) {
        Preconditions.checkNotNull(zzaiVar);
        zzo();
        zzbi();
        boolean zZziq = zziq();
        zzd(new zzih(this, zZziq, zZziq && zzu().zza(zzaiVar), zzaiVar, zzi(true), str));
    }

    @WorkerThread
    protected final void zzd(zzq zzqVar) {
        Preconditions.checkNotNull(zzqVar);
        zzo();
        zzbi();
        zzae();
        zzd(new zzig(this, true, zzu().zzc(zzqVar), new zzq(zzqVar), zzi(true), zzqVar));
    }

    @WorkerThread
    protected final void zzim() {
        zzo();
        zzbi();
        zzn zznVarZzi = zzi(true);
        boolean zZza = zzad().zza(zzak.zzjd);
        if (zZza) {
            zzu().zzgh();
        }
        zzd(new zzia(this, zznVarZzi, zZza));
    }

    @WorkerThread
    protected final void zzip() {
        zzo();
        zzbi();
        zzd(new zzie(this, zzi(true)));
    }

    @WorkerThread
    final void zzis() {
        boolean z;
        boolean z2;
        zzo();
        zzbi();
        if (isConnected()) {
            return;
        }
        boolean z3 = false;
        if (this.zzrg == null) {
            zzo();
            zzbi();
            Boolean boolZzhe = zzac().zzhe();
            if (boolZzhe == null || !boolZzhe.booleanValue()) {
                zzae();
                if (zzr().zzgg() == 1) {
                    z = true;
                    z2 = true;
                } else {
                    zzab().zzgs().zzao("Checking service availability");
                    int iZzd = zzz().zzd(12451000);
                    if (iZzd != 0) {
                        if (iZzd != 1) {
                            if (iZzd == 2) {
                                zzab().zzgr().zzao("Service container out of date");
                                if (zzz().zzjx() >= 15300) {
                                    Boolean boolZzhe2 = zzac().zzhe();
                                    z = boolZzhe2 == null || boolZzhe2.booleanValue();
                                    z2 = false;
                                }
                            } else if (iZzd == 3) {
                                zzab().zzgn().zzao("Service disabled");
                            } else if (iZzd == 9) {
                                zzab().zzgn().zzao("Service invalid");
                            } else if (iZzd != 18) {
                                zzab().zzgn().zza("Unexpected service status", Integer.valueOf(iZzd));
                            } else {
                                zzab().zzgn().zzao("Service updating");
                            }
                            z2 = false;
                        } else {
                            zzab().zzgs().zzao("Service missing");
                        }
                        z = false;
                        z2 = true;
                    } else {
                        zzab().zzgs().zzao("Service available");
                    }
                    z = true;
                    z2 = true;
                }
                if (!z && zzad().zzbw()) {
                    zzab().zzgk().zzao("No way to upload. Consider using the full version of Analytics");
                    z2 = false;
                }
                if (z2) {
                    zzac().zzd(z);
                }
            } else {
                z = true;
            }
            this.zzrg = Boolean.valueOf(z);
        }
        if (this.zzrg.booleanValue()) {
            this.zzre.zzix();
            return;
        }
        if (zzad().zzbw()) {
            return;
        }
        zzae();
        List<ResolveInfo> listQueryIntentServices = getContext().getPackageManager().queryIntentServices(new Intent().setClassName(getContext(), "com.google.android.gms.measurement.AppMeasurementService"), 65536);
        if (listQueryIntentServices != null && listQueryIntentServices.size() > 0) {
            z3 = true;
        }
        if (!z3) {
            zzab().zzgk().zzao("Unable to use remote or local measurement implementation. Please register the AppMeasurementService service in the app manifest");
            return;
        }
        Intent intent = new Intent("com.google.android.gms.measurement.START");
        Context context = getContext();
        zzae();
        intent.setComponent(new ComponentName(context, "com.google.android.gms.measurement.AppMeasurementService"));
        this.zzre.zzb(intent);
    }

    final Boolean zzit() {
        return this.zzrg;
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
