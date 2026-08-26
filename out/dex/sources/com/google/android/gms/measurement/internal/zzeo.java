package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.util.VisibleForTesting;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzeo extends zzge {

    @VisibleForTesting
    static final Pair<String, Long> zzlg = new Pair<>("", 0L);
    private SharedPreferences zzlh;
    public zzes zzli;
    public final zzet zzlj;
    public final zzet zzlk;
    public final zzet zzll;
    public final zzet zzlm;
    public final zzet zzln;
    public final zzet zzlo;
    public final zzet zzlp;
    public final zzev zzlq;
    private String zzlr;
    private boolean zzls;
    private long zzlt;
    public final zzet zzlu;
    public final zzet zzlv;
    public final zzeq zzlw;
    public final zzev zzlx;
    public final zzeq zzly;
    public final zzeq zzlz;
    public final zzet zzma;
    public final zzet zzmb;
    public boolean zzmc;
    public zzeq zzmd;
    public zzet zzme;

    zzeo(zzfj zzfjVar) {
        super(zzfjVar);
        this.zzlj = new zzet(this, "last_upload", 0L);
        this.zzlk = new zzet(this, "last_upload_attempt", 0L);
        this.zzll = new zzet(this, "backoff", 0L);
        this.zzlm = new zzet(this, "last_delete_stale", 0L);
        this.zzlu = new zzet(this, "time_before_start", 10000L);
        this.zzlv = new zzet(this, "session_timeout", 1800000L);
        this.zzlw = new zzeq(this, "start_new_session", true);
        this.zzma = new zzet(this, "last_pause_time", 0L);
        this.zzmb = new zzet(this, "time_active", 0L);
        this.zzlx = new zzev(this, "non_personalized_ads", null);
        this.zzly = new zzeq(this, "use_dynamite_api", false);
        this.zzlz = new zzeq(this, "allow_remote_dynamite", false);
        this.zzln = new zzet(this, "midnight_offset", 0L);
        this.zzlo = new zzet(this, "first_open_time", 0L);
        this.zzlp = new zzet(this, "app_install_time", 0L);
        this.zzlq = new zzev(this, "app_instance_id", null);
        this.zzmd = new zzeq(this, "app_backgrounded", false);
        this.zzme = new zzet(this, "deep_link_last_retrieved", -1L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final SharedPreferences zzhb() {
        zzo();
        zzbi();
        return this.zzlh;
    }

    @WorkerThread
    final void setMeasurementEnabled(boolean z) {
        zzo();
        zzab().zzgs().zza("Setting measurementEnabled", Boolean.valueOf(z));
        SharedPreferences.Editor editorEdit = zzhb().edit();
        editorEdit.putBoolean("measurement_enabled", z);
        editorEdit.apply();
    }

    @WorkerThread
    @NonNull
    final Pair<String, Boolean> zzap(String str) {
        zzo();
        long jElapsedRealtime = zzx().elapsedRealtime();
        String str2 = this.zzlr;
        if (str2 != null && jElapsedRealtime < this.zzlt) {
            return new Pair<>(str2, Boolean.valueOf(this.zzls));
        }
        this.zzlt = jElapsedRealtime + zzad().zza(str, zzak.zzgg);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        try {
            AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(getContext());
            if (advertisingIdInfo != null) {
                this.zzlr = advertisingIdInfo.getId();
                this.zzls = advertisingIdInfo.isLimitAdTrackingEnabled();
            }
            if (this.zzlr == null) {
                this.zzlr = "";
            }
        } catch (Exception e) {
            zzab().zzgr().zza("Unable to get advertising id", e);
            this.zzlr = "";
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair<>(this.zzlr, Boolean.valueOf(this.zzls));
    }

    @WorkerThread
    final String zzaq(String str) {
        zzo();
        String str2 = (String) zzap(str).first;
        MessageDigest messageDigest = zzjs.getMessageDigest();
        if (messageDigest == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, messageDigest.digest(str2.getBytes())));
    }

    @WorkerThread
    final void zzar(String str) {
        zzo();
        SharedPreferences.Editor editorEdit = zzhb().edit();
        editorEdit.putString("gmp_app_id", str);
        editorEdit.apply();
    }

    @WorkerThread
    final void zzas(String str) {
        zzo();
        SharedPreferences.Editor editorEdit = zzhb().edit();
        editorEdit.putString("admob_app_id", str);
        editorEdit.apply();
    }

    @Override // com.google.android.gms.measurement.internal.zzge
    protected final boolean zzbk() {
        return true;
    }

    @Override // com.google.android.gms.measurement.internal.zzge
    @WorkerThread
    protected final void zzbl() {
        this.zzlh = getContext().getSharedPreferences("com.google.android.gms.measurement.prefs", 0);
        this.zzmc = this.zzlh.getBoolean("has_been_opened", false);
        if (!this.zzmc) {
            SharedPreferences.Editor editorEdit = this.zzlh.edit();
            editorEdit.putBoolean("has_been_opened", true);
            editorEdit.apply();
        }
        this.zzli = new zzes(this, "health_monitor", Math.max(0L, zzak.zzgh.get(null).longValue()));
    }

    @WorkerThread
    final void zzd(boolean z) {
        zzo();
        zzab().zzgs().zza("Setting useService", Boolean.valueOf(z));
        SharedPreferences.Editor editorEdit = zzhb().edit();
        editorEdit.putBoolean("use_service", z);
        editorEdit.apply();
    }

    @WorkerThread
    final boolean zze(boolean z) {
        zzo();
        return zzhb().getBoolean("measurement_enabled", z);
    }

    @WorkerThread
    final void zzf(boolean z) {
        zzo();
        zzab().zzgs().zza("Updating deferred analytics collection", Boolean.valueOf(z));
        SharedPreferences.Editor editorEdit = zzhb().edit();
        editorEdit.putBoolean("deferred_analytics_collection", z);
        editorEdit.apply();
    }

    @WorkerThread
    final String zzhc() {
        zzo();
        return zzhb().getString("gmp_app_id", null);
    }

    @WorkerThread
    final String zzhd() {
        zzo();
        return zzhb().getString("admob_app_id", null);
    }

    @WorkerThread
    final Boolean zzhe() {
        zzo();
        if (zzhb().contains("use_service")) {
            return Boolean.valueOf(zzhb().getBoolean("use_service", false));
        }
        return null;
    }

    @WorkerThread
    final void zzhf() {
        zzo();
        zzab().zzgs().zzao("Clearing collection preferences.");
        if (zzad().zza(zzak.zzil)) {
            Boolean boolZzhg = zzhg();
            SharedPreferences.Editor editorEdit = zzhb().edit();
            editorEdit.clear();
            editorEdit.apply();
            if (boolZzhg != null) {
                setMeasurementEnabled(boolZzhg.booleanValue());
                return;
            }
            return;
        }
        boolean zContains = zzhb().contains("measurement_enabled");
        boolean zZze = zContains ? zze(true) : true;
        SharedPreferences.Editor editorEdit2 = zzhb().edit();
        editorEdit2.clear();
        editorEdit2.apply();
        if (zContains) {
            setMeasurementEnabled(zZze);
        }
    }

    @WorkerThread
    final Boolean zzhg() {
        zzo();
        if (zzhb().contains("measurement_enabled")) {
            return Boolean.valueOf(zzhb().getBoolean("measurement_enabled", true));
        }
        return null;
    }

    @WorkerThread
    protected final String zzhh() {
        zzo();
        String string = zzhb().getString("previous_os_version", null);
        zzw().zzbi();
        String str = Build.VERSION.RELEASE;
        if (!TextUtils.isEmpty(str) && !str.equals(string)) {
            SharedPreferences.Editor editorEdit = zzhb().edit();
            editorEdit.putString("previous_os_version", str);
            editorEdit.apply();
        }
        return string;
    }

    @WorkerThread
    final boolean zzhi() {
        zzo();
        return zzhb().getBoolean("deferred_analytics_collection", false);
    }

    @WorkerThread
    final boolean zzhj() {
        return this.zzlh.contains("deferred_analytics_collection");
    }

    final boolean zzx(long j) {
        return j - this.zzlv.get() > this.zzma.get();
    }
}
