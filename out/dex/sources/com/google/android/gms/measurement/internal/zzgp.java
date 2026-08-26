package com.google.android.gms.measurement.internal;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.app.NotificationCompat;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.api.internal.GoogleServices;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzgp extends zzg {

    @VisibleForTesting
    protected zzhj zzpu;
    private zzgk zzpv;
    private final Set<zzgn> zzpw;
    private boolean zzpx;
    private final AtomicReference<String> zzpy;

    @VisibleForTesting
    protected boolean zzpz;

    protected zzgp(zzfj zzfjVar) {
        super(zzfjVar);
        this.zzpw = new CopyOnWriteArraySet();
        this.zzpz = true;
        this.zzpy = new AtomicReference<>();
    }

    private final void zza(Bundle bundle, long j) {
        Preconditions.checkNotNull(bundle);
        zzgg.zza(bundle, "app_id", String.class, null);
        zzgg.zza(bundle, "origin", String.class, null);
        zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.NAME, String.class, null);
        zzgg.zza(bundle, "value", Object.class, null);
        zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, String.class, null);
        zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, Long.class, 0L);
        zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME, String.class, null);
        zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS, Bundle.class, null);
        zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME, String.class, null);
        zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS, Bundle.class, null);
        zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, Long.class, 0L);
        zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, String.class, null);
        zzgg.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, Bundle.class, null);
        Preconditions.checkNotEmpty(bundle.getString(AppMeasurementSdk.ConditionalUserProperty.NAME));
        Preconditions.checkNotEmpty(bundle.getString("origin"));
        Preconditions.checkNotNull(bundle.get("value"));
        bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, j);
        String string = bundle.getString(AppMeasurementSdk.ConditionalUserProperty.NAME);
        Object obj = bundle.get("value");
        if (zzz().zzbm(string) != 0) {
            zzab().zzgk().zza("Invalid conditional user property name", zzy().zzal(string));
            return;
        }
        if (zzz().zzc(string, obj) != 0) {
            zzab().zzgk().zza("Invalid conditional user property value", zzy().zzal(string), obj);
            return;
        }
        Object objZzd = zzz().zzd(string, obj);
        if (objZzd == null) {
            zzab().zzgk().zza("Unable to normalize conditional user property value", zzy().zzal(string), obj);
            return;
        }
        zzgg.zza(bundle, objZzd);
        long j2 = bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT);
        if (!TextUtils.isEmpty(bundle.getString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME)) && (j2 > 15552000000L || j2 < 1)) {
            zzab().zzgk().zza("Invalid conditional user property timeout", zzy().zzal(string), Long.valueOf(j2));
            return;
        }
        long j3 = bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE);
        if (j3 > 15552000000L || j3 < 1) {
            zzab().zzgk().zza("Invalid conditional user property time to live", zzy().zzal(string), Long.valueOf(j3));
        } else {
            zzaa().zza(new zzgx(this, bundle));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zza(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        String str4;
        String str5;
        zzhr zzhrVar;
        int i;
        long j2;
        ArrayList arrayList;
        String str6;
        Bundle bundle2;
        List<String> listZzbh;
        Preconditions.checkNotEmpty(str);
        if (!zzad().zze(str3, zzak.zzip)) {
            Preconditions.checkNotEmpty(str2);
        }
        Preconditions.checkNotNull(bundle);
        zzo();
        zzbi();
        if (!this.zzj.isEnabled()) {
            zzab().zzgr().zzao("Event not sent since app measurement is disabled");
            return;
        }
        if (zzad().zze(zzr().zzag(), zzak.zzix) && (listZzbh = zzr().zzbh()) != null && !listZzbh.contains(str2)) {
            zzab().zzgr().zza("Dropping non-safelisted event. event name, origin", str2, str);
            return;
        }
        if (!this.zzpx) {
            this.zzpx = true;
            try {
                try {
                    (!this.zzj.zzia() ? Class.forName("com.google.android.gms.tagmanager.TagManagerService", true, getContext().getClassLoader()) : Class.forName("com.google.android.gms.tagmanager.TagManagerService")).getDeclaredMethod("initialize", Context.class).invoke(null, getContext());
                } catch (Exception e) {
                    zzab().zzgn().zza("Failed to invoke Tag Manager's initialize() method", e);
                }
            } catch (ClassNotFoundException unused) {
                zzab().zzgq().zzao("Tag Manager is not found and thus will not be used");
            }
        }
        if (zzad().zze(zzr().zzag(), zzak.zzje) && "_cmp".equals(str2) && bundle.containsKey("gclid")) {
            zza("auto", "_lgclid", bundle.getString("gclid"), zzx().currentTimeMillis());
        }
        if (z3) {
            zzae();
            if (!"_iap".equals(str2)) {
                zzjs zzjsVarZzz = this.zzj.zzz();
                int i2 = 2;
                if (zzjsVarZzz.zzp(NotificationCompat.CATEGORY_EVENT, str2)) {
                    if (!zzjsVarZzz.zza(NotificationCompat.CATEGORY_EVENT, zzgj.zzpn, str2)) {
                        i2 = 13;
                    } else if (zzjsVarZzz.zza(NotificationCompat.CATEGORY_EVENT, 40, str2)) {
                        i2 = 0;
                    }
                }
                if (i2 != 0) {
                    zzab().zzgm().zza("Invalid public event name. Event will not be logged (FE)", zzy().zzaj(str2));
                    this.zzj.zzz();
                    this.zzj.zzz().zza(i2, "_ev", zzjs.zza(str2, 40, true), str2 != null ? str2.length() : 0);
                    return;
                }
            }
        }
        zzae();
        zzhr zzhrVarZzin = zzt().zzin();
        if (zzhrVarZzin != null && !bundle.containsKey("_sc")) {
            zzhrVarZzin.zzqx = true;
        }
        zzhq.zza(zzhrVarZzin, bundle, z && z3);
        boolean zEquals = "am".equals(str);
        boolean zZzbq = zzjs.zzbq(str2);
        if (z && this.zzpv != null && !zZzbq && !zEquals) {
            zzab().zzgr().zza("Passing event to registered event handler (FE)", zzy().zzaj(str2), zzy().zzc(bundle));
            this.zzpv.interceptEvent(str, str2, bundle, j);
            return;
        }
        if (this.zzj.zzie()) {
            int iZzbl = zzz().zzbl(str2);
            if (iZzbl != 0) {
                zzab().zzgm().zza("Invalid event name. Event will not be logged (FE)", zzy().zzaj(str2));
                zzz();
                this.zzj.zzz().zza(str3, iZzbl, "_ev", zzjs.zza(str2, 40, true), str2 != null ? str2.length() : 0);
                return;
            }
            String str7 = "_o";
            List<String> listListOf = CollectionUtils.listOf((Object[]) new String[]{"_o", "_sn", "_sc", "_si"});
            String str8 = str2;
            Bundle bundleZza = zzz().zza(str3, str2, bundle, listListOf, z3, true);
            zzhr zzhrVar2 = (bundleZza != null && bundleZza.containsKey("_sc") && bundleZza.containsKey("_si")) ? new zzhr(bundleZza.getString("_sn"), bundleZza.getString("_sc"), Long.valueOf(bundleZza.getLong("_si")).longValue()) : null;
            zzhr zzhrVar3 = zzhrVar2 == null ? zzhrVarZzin : zzhrVar2;
            String str9 = "_ae";
            if (zzad().zzz(str3)) {
                zzae();
                if (zzt().zzin() != null && "_ae".equals(str8)) {
                    long jZzjb = zzv().zzjb();
                    if (jZzjb > 0) {
                        zzz().zzb(bundleZza, jZzjb);
                    }
                }
            }
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(bundleZza);
            long jNextLong = zzz().zzjw().nextLong();
            if (zzad().zze(zzr().zzag(), zzak.zzid) && zzac().zzma.get() > 0 && zzac().zzx(j) && zzac().zzmd.get()) {
                zzab().zzgs().zzao("Current session is expired, remove the session number and Id");
                if (zzad().zze(zzr().zzag(), zzak.zzhz)) {
                    zza("auto", "_sid", (Object) null, zzx().currentTimeMillis());
                }
                if (zzad().zze(zzr().zzag(), zzak.zzia)) {
                    zza("auto", "_sno", (Object) null, zzx().currentTimeMillis());
                }
            } else {
                str7 = "_o";
            }
            if (zzad().zzy(zzr().zzag()) && bundleZza.getLong(FirebaseAnalytics.Param.EXTEND_SESSION, 0L) == 1) {
                zzab().zzgs().zzao("EXTEND_SESSION param attached: initiate a new session or extend the current active session");
                this.zzj.zzv().zza(j, true);
            }
            String[] strArr = (String[]) bundleZza.keySet().toArray(new String[bundle.size()]);
            Arrays.sort(strArr);
            int length = strArr.length;
            int i3 = 0;
            int length2 = 0;
            while (true) {
                String str10 = "_eid";
                if (i3 >= length) {
                    break;
                }
                String str11 = strArr[i3];
                Object obj = bundleZza.get(str11);
                zzz();
                String[] strArr2 = strArr;
                Bundle[] bundleArrZzb = zzjs.zzb(obj);
                if (bundleArrZzb != null) {
                    bundleZza.putInt(str11, bundleArrZzb.length);
                    int i4 = 0;
                    while (i4 < bundleArrZzb.length) {
                        Bundle bundle3 = bundleArrZzb[i4];
                        zzhq.zza(zzhrVar3, bundle3, true);
                        String str12 = str10;
                        int i5 = i4;
                        long j3 = jNextLong;
                        ArrayList arrayList3 = arrayList2;
                        Bundle bundleZza2 = zzz().zza(str3, "_ep", bundle3, listListOf, z3, false);
                        bundleZza2.putString("_en", str8);
                        bundleZza2.putLong(str12, j3);
                        bundleZza2.putString("_gn", str11);
                        bundleZza2.putInt("_ll", bundleArrZzb.length);
                        bundleZza2.putInt("_i", i5);
                        arrayList3.add(bundleZza2);
                        bundleZza = bundleZza;
                        jNextLong = j3;
                        arrayList2 = arrayList3;
                        i4 = i5 + 1;
                        i3 = i3;
                        zzhrVar3 = zzhrVar3;
                        length2 = length2;
                        str9 = str9;
                        str10 = str12;
                    }
                    zzhrVar = zzhrVar3;
                    i = i3;
                    j2 = jNextLong;
                    arrayList = arrayList2;
                    str6 = str9;
                    bundle2 = bundleZza;
                    length2 += bundleArrZzb.length;
                } else {
                    zzhrVar = zzhrVar3;
                    i = i3;
                    j2 = jNextLong;
                    arrayList = arrayList2;
                    str6 = str9;
                    bundle2 = bundleZza;
                }
                bundleZza = bundle2;
                jNextLong = j2;
                arrayList2 = arrayList;
                length = length;
                zzhrVar3 = zzhrVar;
                str9 = str6;
                i3 = i + 1;
                strArr = strArr2;
            }
            int i6 = length2;
            long j4 = jNextLong;
            ArrayList arrayList4 = arrayList2;
            String str13 = str9;
            Bundle bundle4 = bundleZza;
            if (i6 != 0) {
                bundle4.putLong("_eid", j4);
                bundle4.putInt("_epc", i6);
            }
            int i7 = 0;
            while (i7 < arrayList4.size()) {
                Bundle bundleZzg = (Bundle) arrayList4.get(i7);
                if (i7 != 0) {
                    str4 = "_ep";
                    str5 = str8;
                } else {
                    str4 = str8;
                    str5 = str4;
                }
                String str14 = str7;
                bundleZzg.putString(str14, str);
                if (z2) {
                    bundleZzg = zzz().zzg(bundleZzg);
                }
                Bundle bundle5 = bundleZzg;
                zzab().zzgr().zza("Logging event (FE)", zzy().zzaj(str5), zzy().zzc(bundle5));
                ArrayList arrayList5 = arrayList4;
                zzs().zzc(new zzai(str4, new zzah(bundle5), str, j), str3);
                if (!zEquals) {
                    Iterator<zzgn> it = this.zzpw.iterator();
                    while (it.hasNext()) {
                        it.next().onEvent(str, str2, new Bundle(bundle5), j);
                    }
                }
                i7++;
                str8 = str5;
                str7 = str14;
                arrayList4 = arrayList5;
            }
            String str15 = str8;
            zzae();
            if (zzt().zzin() == null || !str13.equals(str15)) {
                return;
            }
            zzv().zza(true, true);
        }
    }

    private final void zza(String str, String str2, long j, Object obj) {
        zzaa().zza(new zzgq(this, str, str2, obj, j));
    }

    private final void zza(String str, String str2, String str3, Bundle bundle) {
        long jCurrentTimeMillis = zzx().currentTimeMillis();
        Preconditions.checkNotEmpty(str2);
        Bundle bundle2 = new Bundle();
        if (str != null) {
            bundle2.putString("app_id", str);
        }
        bundle2.putString(AppMeasurementSdk.ConditionalUserProperty.NAME, str2);
        bundle2.putLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, jCurrentTimeMillis);
        if (str3 != null) {
            bundle2.putString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, str3);
            bundle2.putBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, bundle);
        }
        zzaa().zza(new zzgw(this, bundle2));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @VisibleForTesting
    private final Map<String, Object> zzb(String str, String str2, String str3, boolean z) {
        if (zzaa().zzhp()) {
            zzab().zzgk().zzao("Cannot get user properties from analytics worker thread");
            return Collections.emptyMap();
        }
        if (zzr.isMainThread()) {
            zzab().zzgk().zzao("Cannot get user properties from main thread");
            return Collections.emptyMap();
        }
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            this.zzj.zzaa().zza(new zzhb(this, atomicReference, str, str2, str3, z));
            try {
                atomicReference.wait(5000L);
            } catch (InterruptedException e) {
                zzab().zzgn().zza("Interrupted waiting for get user properties", e);
            }
        }
        List<zzjn> list = (List) atomicReference.get();
        if (list == null) {
            zzab().zzgn().zzao("Timed out waiting for get user properties");
            return Collections.emptyMap();
        }
        ArrayMap arrayMap = new ArrayMap(list.size());
        for (zzjn zzjnVar : list) {
            arrayMap.put(zzjnVar.name, zzjnVar.getValue());
        }
        return arrayMap;
    }

    private final void zzb(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        zzaa().zza(new zzgr(this, str, str2, j, zzjs.zzh(bundle), z, z2, z3, str3));
    }

    @VisibleForTesting
    private final ArrayList<Bundle> zze(String str, String str2, String str3) {
        if (zzaa().zzhp()) {
            zzab().zzgk().zzao("Cannot get conditional user properties from analytics worker thread");
            return new ArrayList<>(0);
        }
        if (zzr.isMainThread()) {
            zzab().zzgk().zzao("Cannot get conditional user properties from main thread");
            return new ArrayList<>(0);
        }
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            this.zzj.zzaa().zza(new zzgz(this, atomicReference, str, str2, str3));
            try {
                atomicReference.wait(5000L);
            } catch (InterruptedException e) {
                zzab().zzgn().zza("Interrupted waiting for get conditional user properties", str, e);
            }
        }
        List list = (List) atomicReference.get();
        if (list != null) {
            return zzjs.zzd((List<zzq>) list);
        }
        zzab().zzgn().zza("Timed out waiting for get conditional user properties", str);
        return new ArrayList<>();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zze(Bundle bundle) {
        zzo();
        zzbi();
        Preconditions.checkNotNull(bundle);
        Preconditions.checkNotEmpty(bundle.getString(AppMeasurementSdk.ConditionalUserProperty.NAME));
        Preconditions.checkNotEmpty(bundle.getString("origin"));
        Preconditions.checkNotNull(bundle.get("value"));
        if (!this.zzj.isEnabled()) {
            zzab().zzgr().zzao("Conditional property not sent since collection is disabled");
            return;
        }
        zzjn zzjnVar = new zzjn(bundle.getString(AppMeasurementSdk.ConditionalUserProperty.NAME), bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP), bundle.get("value"), bundle.getString("origin"));
        try {
            zzai zzaiVarZza = zzz().zza(bundle.getString("app_id"), bundle.getString(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME), bundle.getBundle(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS), bundle.getString("origin"), 0L, true, false);
            zzs().zzd(new zzq(bundle.getString("app_id"), bundle.getString("origin"), zzjnVar, bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP), false, bundle.getString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME), zzz().zza(bundle.getString("app_id"), bundle.getString(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME), bundle.getBundle(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS), bundle.getString("origin"), 0L, true, false), bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT), zzaiVarZza, bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE), zzz().zza(bundle.getString("app_id"), bundle.getString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME), bundle.getBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS), bundle.getString("origin"), 0L, true, false)));
        } catch (IllegalArgumentException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zzf(Bundle bundle) {
        zzo();
        zzbi();
        Preconditions.checkNotNull(bundle);
        Preconditions.checkNotEmpty(bundle.getString(AppMeasurementSdk.ConditionalUserProperty.NAME));
        if (!this.zzj.isEnabled()) {
            zzab().zzgr().zzao("Conditional property not cleared since collection is disabled");
            return;
        }
        try {
            zzs().zzd(new zzq(bundle.getString("app_id"), bundle.getString("origin"), new zzjn(bundle.getString(AppMeasurementSdk.ConditionalUserProperty.NAME), 0L, null, null), bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP), bundle.getBoolean(AppMeasurementSdk.ConditionalUserProperty.ACTIVE), bundle.getString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME), null, bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT), null, bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE), zzz().zza(bundle.getString("app_id"), bundle.getString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME), bundle.getBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS), bundle.getString("origin"), bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP), true, false)));
        } catch (IllegalArgumentException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zzg(boolean z) {
        zzo();
        zzm();
        zzbi();
        zzab().zzgr().zza("Setting app measurement enabled (FE)", Boolean.valueOf(z));
        zzac().setMeasurementEnabled(z);
        zzil();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zzil() {
        if (zzad().zze(zzr().zzag(), zzak.zzik)) {
            zzo();
            String strZzho = zzac().zzlx.zzho();
            if (strZzho != null) {
                if ("unset".equals(strZzho)) {
                    zza("app", "_npa", (Object) null, zzx().currentTimeMillis());
                } else {
                    zza("app", "_npa", Long.valueOf("true".equals(strZzho) ? 1L : 0L), zzx().currentTimeMillis());
                }
            }
        }
        if (this.zzj.isEnabled() && this.zzpz) {
            zzab().zzgr().zzao("Recording app launch after enabling measurement for the first time (FE)");
            zzim();
        } else {
            zzab().zzgr().zzao("Updating Scion state (FE)");
            zzs().zzip();
        }
    }

    @Nullable
    private final String zzz(long j) {
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            zzaa().zza(new zzgs(this, atomicReference));
            try {
                atomicReference.wait(j);
            } catch (InterruptedException unused) {
                zzab().zzgn().zzao("Interrupted waiting for app instance id");
                return null;
            }
        }
        return (String) atomicReference.get();
    }

    public final void clearConditionalUserProperty(String str, String str2, Bundle bundle) {
        zzm();
        zza((String) null, str, str2, bundle);
    }

    public final void clearConditionalUserPropertyAs(String str, String str2, String str3, Bundle bundle) {
        Preconditions.checkNotEmpty(str);
        zzl();
        zza(str, str2, str3, bundle);
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @Nullable
    public final String getCurrentScreenClass() {
        zzhr zzhrVarZzio = this.zzj.zzt().zzio();
        if (zzhrVarZzio != null) {
            return zzhrVarZzio.zzqv;
        }
        return null;
    }

    @Nullable
    public final String getCurrentScreenName() {
        zzhr zzhrVarZzio = this.zzj.zzt().zzio();
        if (zzhrVarZzio != null) {
            return zzhrVarZzio.zzqu;
        }
        return null;
    }

    @Nullable
    public final String getGmpAppId() {
        if (this.zzj.zzhx() != null) {
            return this.zzj.zzhx();
        }
        try {
            return GoogleServices.getGoogleAppId();
        } catch (IllegalStateException e) {
            this.zzj.zzab().zzgk().zza("getGoogleAppId failed with exception", e);
            return null;
        }
    }

    public final Map<String, Object> getUserProperties(String str, String str2, boolean z) {
        zzm();
        return zzb((String) null, str, str2, z);
    }

    public final Map<String, Object> getUserPropertiesAs(String str, String str2, String str3, boolean z) {
        Preconditions.checkNotEmpty(str);
        zzl();
        return zzb(str, str2, str3, z);
    }

    public final void logEvent(String str, String str2, Bundle bundle) {
        logEvent(str, str2, bundle, true, true, zzx().currentTimeMillis());
    }

    public final void logEvent(String str, String str2, Bundle bundle, boolean z, boolean z2, long j) {
        zzm();
        zzb(str == null ? "app" : str, str2, j, bundle == null ? new Bundle() : bundle, z2, !z2 || this.zzpv == null || zzjs.zzbq(str2), !z, null);
    }

    public final void resetAnalyticsData(long j) {
        zzbg(null);
        zzaa().zza(new zzgv(this, j));
    }

    public final void setConditionalUserProperty(Bundle bundle) {
        setConditionalUserProperty(bundle, zzx().currentTimeMillis());
    }

    public final void setConditionalUserProperty(Bundle bundle, long j) {
        Preconditions.checkNotNull(bundle);
        zzm();
        Bundle bundle2 = new Bundle(bundle);
        if (!TextUtils.isEmpty(bundle2.getString("app_id"))) {
            zzab().zzgn().zzao("Package name should be null when calling setConditionalUserProperty");
        }
        bundle2.remove("app_id");
        zza(bundle2, j);
    }

    public final void setMeasurementEnabled(boolean z) {
        zzbi();
        zzm();
        zzaa().zza(new zzhf(this, z));
    }

    public final void setMinimumSessionDuration(long j) {
        zzm();
        zzaa().zza(new zzhh(this, j));
    }

    public final void setSessionTimeoutDuration(long j) {
        zzm();
        zzaa().zza(new zzhg(this, j));
    }

    @WorkerThread
    public final void zza(zzgk zzgkVar) {
        zzgk zzgkVar2;
        zzo();
        zzm();
        zzbi();
        if (zzgkVar != null && zzgkVar != (zzgkVar2 = this.zzpv)) {
            Preconditions.checkState(zzgkVar2 == null, "EventInterceptor already set.");
        }
        this.zzpv = zzgkVar;
    }

    public final void zza(zzgn zzgnVar) {
        zzm();
        zzbi();
        Preconditions.checkNotNull(zzgnVar);
        if (this.zzpw.add(zzgnVar)) {
            return;
        }
        zzab().zzgn().zzao("OnEventListener already registered");
    }

    @WorkerThread
    final void zza(String str, String str2, long j, Bundle bundle) {
        zzm();
        zzo();
        zza(str, str2, j, bundle, true, this.zzpv == null || zzjs.zzbq(str2), false, null);
    }

    @WorkerThread
    final void zza(String str, String str2, Bundle bundle) {
        zzm();
        zzo();
        zza(str, str2, zzx().currentTimeMillis(), bundle);
    }

    public final void zza(String str, String str2, Bundle bundle, boolean z) {
        logEvent(str, str2, bundle, false, true, zzx().currentTimeMillis());
    }

    /* JADX WARN: Code duplicated, block: B:18:0x006a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:19:0x006c  */
    /* JADX WARN: Code duplicated, block: B:21:0x007a  */
    @WorkerThread
    final void zza(String str, String str2, Object obj, long j) {
        String str3;
        Object obj2;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzo();
        zzm();
        zzbi();
        if (zzad().zze(zzr().zzag(), zzak.zzik) && FirebaseAnalytics.UserProperty.ALLOW_AD_PERSONALIZATION_SIGNALS.equals(str2)) {
            if (obj instanceof String) {
                String str4 = (String) obj;
                if (!TextUtils.isEmpty(str4)) {
                    Long lValueOf = Long.valueOf("false".equals(str4.toLowerCase(Locale.ENGLISH)) ? 1L : 0L);
                    zzac().zzlx.zzau(lValueOf.longValue() == 1 ? "true" : "false");
                    obj2 = lValueOf;
                } else if (obj == null) {
                    zzac().zzlx.zzau("unset");
                    obj2 = obj;
                } else {
                    str3 = str2;
                    obj2 = obj;
                }
            } else if (obj == null) {
                zzac().zzlx.zzau("unset");
                obj2 = obj;
            } else {
                str3 = str2;
                obj2 = obj;
            }
            str3 = "_npa";
        } else {
            str3 = str2;
            obj2 = obj;
        }
        if (!this.zzj.isEnabled()) {
            zzab().zzgr().zzao("User property not set since app measurement is disabled");
        } else if (this.zzj.zzie()) {
            zzab().zzgr().zza("Setting user property (FE)", zzy().zzaj(str3), obj2);
            zzs().zzb(new zzjn(str3, j, obj2, str));
        }
    }

    public final void zza(String str, String str2, Object obj, boolean z, long j) {
        if (str == null) {
            str = "app";
        }
        String str3 = str;
        int iZzbm = 6;
        if (z) {
            iZzbm = zzz().zzbm(str2);
        } else {
            zzjs zzjsVarZzz = zzz();
            if (zzjsVarZzz.zzp("user property", str2)) {
                if (!zzjsVarZzz.zza("user property", zzgl.zzpp, str2)) {
                    iZzbm = 15;
                } else if (zzjsVarZzz.zza("user property", 24, str2)) {
                    iZzbm = 0;
                }
            }
        }
        if (iZzbm != 0) {
            zzz();
            this.zzj.zzz().zza(iZzbm, "_ev", zzjs.zza(str2, 24, true), str2 != null ? str2.length() : 0);
            return;
        }
        if (obj == null) {
            zza(str3, str2, j, (Object) null);
            return;
        }
        int iZzc = zzz().zzc(str2, obj);
        if (iZzc != 0) {
            zzz();
            this.zzj.zzz().zza(iZzc, "_ev", zzjs.zza(str2, 24, true), ((obj instanceof String) || (obj instanceof CharSequence)) ? String.valueOf(obj).length() : 0);
        } else {
            Object objZzd = zzz().zzd(str2, obj);
            if (objZzd != null) {
                zza(str3, str2, j, objZzd);
            }
        }
    }

    public final void zza(boolean z) {
        zzbi();
        zzm();
        zzaa().zza(new zzhe(this, z));
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

    public final void zzb(zzgn zzgnVar) {
        zzm();
        zzbi();
        Preconditions.checkNotNull(zzgnVar);
        if (this.zzpw.remove(zzgnVar)) {
            return;
        }
        zzab().zzgn().zzao("OnEventListener had not been registered");
    }

    public final void zzb(String str, String str2, Object obj, boolean z) {
        zza(str, str2, obj, z, zzx().currentTimeMillis());
    }

    final void zzbg(@Nullable String str) {
        this.zzpy.set(str);
    }

    @Override // com.google.android.gms.measurement.internal.zzg
    protected final boolean zzbk() {
        return false;
    }

    public final ArrayList<Bundle> zzd(String str, String str2, String str3) {
        Preconditions.checkNotEmpty(str);
        zzl();
        return zze(str, str2, str3);
    }

    public final void zzd(Bundle bundle) {
        Preconditions.checkNotNull(bundle);
        Preconditions.checkNotEmpty(bundle.getString("app_id"));
        zzl();
        zza(new Bundle(bundle), zzx().currentTimeMillis());
    }

    public final List<zzjn> zzh(boolean z) {
        zzm();
        zzbi();
        zzab().zzgr().zzao("Fetching user attributes (FE)");
        if (zzaa().zzhp()) {
            zzab().zzgk().zzao("Cannot get all user properties from analytics worker thread");
            return Collections.emptyList();
        }
        if (zzr.isMainThread()) {
            zzab().zzgk().zzao("Cannot get all user properties from main thread");
            return Collections.emptyList();
        }
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            this.zzj.zzaa().zza(new zzgt(this, atomicReference, z));
            try {
                atomicReference.wait(5000L);
            } catch (InterruptedException e) {
                zzab().zzgn().zza("Interrupted waiting for get user properties", e);
            }
        }
        List<zzjn> list = (List) atomicReference.get();
        if (list != null) {
            return list;
        }
        zzab().zzgn().zzao("Timed out waiting for get user properties");
        return Collections.emptyList();
    }

    @Nullable
    public final String zzi() {
        zzm();
        return this.zzpy.get();
    }

    public final void zzif() {
        if (getContext().getApplicationContext() instanceof Application) {
            ((Application) getContext().getApplicationContext()).unregisterActivityLifecycleCallbacks(this.zzpu);
        }
    }

    public final Boolean zzig() {
        AtomicReference atomicReference = new AtomicReference();
        return (Boolean) zzaa().zza(atomicReference, 15000L, "boolean test flag value", new zzgo(this, atomicReference));
    }

    public final String zzih() {
        AtomicReference atomicReference = new AtomicReference();
        return (String) zzaa().zza(atomicReference, 15000L, "String test flag value", new zzgy(this, atomicReference));
    }

    public final Long zzii() {
        AtomicReference atomicReference = new AtomicReference();
        return (Long) zzaa().zza(atomicReference, 15000L, "long test flag value", new zzha(this, atomicReference));
    }

    public final Integer zzij() {
        AtomicReference atomicReference = new AtomicReference();
        return (Integer) zzaa().zza(atomicReference, 15000L, "int test flag value", new zzhd(this, atomicReference));
    }

    public final Double zzik() {
        AtomicReference atomicReference = new AtomicReference();
        return (Double) zzaa().zza(atomicReference, 15000L, "double test flag value", new zzhc(this, atomicReference));
    }

    @WorkerThread
    public final void zzim() {
        zzo();
        zzm();
        zzbi();
        if (this.zzj.zzie()) {
            zzs().zzim();
            this.zzpz = false;
            String strZzhh = zzac().zzhh();
            if (TextUtils.isEmpty(strZzhh)) {
                return;
            }
            zzw().zzbi();
            if (strZzhh.equals(Build.VERSION.RELEASE)) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("_po", strZzhh);
            logEvent("auto", "_ou", bundle);
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzd, com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzl() {
        super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzd, com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzm() {
        super.zzm();
    }

    public final ArrayList<Bundle> zzn(String str, String str2) {
        zzm();
        return zze(null, str, str2);
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

    @Nullable
    public final String zzy(long j) {
        if (zzaa().zzhp()) {
            zzab().zzgk().zzao("Cannot retrieve app instance id from analytics worker thread");
            return null;
        }
        if (zzr.isMainThread()) {
            zzab().zzgk().zzao("Cannot retrieve app instance id from main thread");
            return null;
        }
        long jElapsedRealtime = zzx().elapsedRealtime();
        String strZzz = zzz(120000L);
        long jElapsedRealtime2 = zzx().elapsedRealtime() - jElapsedRealtime;
        return (strZzz != null || jElapsedRealtime2 >= 120000) ? strZzz : zzz(120000 - jElapsedRealtime2);
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzjs zzz() {
        return super.zzz();
    }
}
