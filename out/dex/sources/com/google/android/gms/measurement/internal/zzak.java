package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.api.client.http.HttpStatusCodes;
import com.metaps.common.C0854h;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@VisibleForTesting
public final class zzak {
    static zzr zzfv;

    @VisibleForTesting
    private static Boolean zzfz;
    public static zzdu<Long> zzgh;
    public static zzdu<Long> zzgi;
    public static zzdu<String> zzgj;
    public static zzdu<String> zzgk;
    public static zzdu<Integer> zzgl;
    public static zzdu<Integer> zzgm;
    public static zzdu<Integer> zzgn;
    public static zzdu<Integer> zzgo;
    public static zzdu<Integer> zzgp;
    public static zzdu<Integer> zzgq;
    public static zzdu<Integer> zzgr;
    public static zzdu<Integer> zzgs;
    public static zzdu<Integer> zzgt;
    public static zzdu<Integer> zzgu;
    public static zzdu<String> zzgv;
    public static zzdu<Long> zzgw;
    public static zzdu<Long> zzgx;
    public static zzdu<Long> zzgy;
    public static zzdu<Long> zzgz;
    public static zzdu<Long> zzha;
    public static zzdu<Long> zzhb;
    public static zzdu<Long> zzhc;
    public static zzdu<Long> zzhd;
    public static zzdu<Long> zzhe;
    public static zzdu<Long> zzhf;
    public static zzdu<Long> zzhg;
    public static zzdu<Integer> zzhh;
    public static zzdu<Long> zzhi;
    public static zzdu<Integer> zzhj;
    public static zzdu<Integer> zzhk;
    public static zzdu<Long> zzhl;
    public static zzdu<Boolean> zzhm;
    public static zzdu<String> zzhn;
    public static zzdu<Long> zzho;
    public static zzdu<Integer> zzhp;
    public static zzdu<Double> zzhq;
    public static zzdu<Integer> zzhr;
    public static zzdu<Boolean> zzhs;
    public static zzdu<Boolean> zzht;
    public static zzdu<Boolean> zzhu;
    public static zzdu<Boolean> zzhv;
    public static zzdu<Boolean> zzhw;
    public static zzdu<Boolean> zzhx;
    public static zzdu<Boolean> zzhy;
    public static zzdu<Boolean> zzhz;
    public static zzdu<Boolean> zzia;
    public static zzdu<Boolean> zzib;
    public static zzdu<Boolean> zzic;
    public static zzdu<Boolean> zzid;
    public static zzdu<Boolean> zzie;
    public static zzdu<Boolean> zzif;
    public static zzdu<Boolean> zzig;
    public static zzdu<Boolean> zzih;
    public static zzdu<Boolean> zzii;
    public static zzdu<Boolean> zzij;
    public static zzdu<Boolean> zzik;
    public static zzdu<Boolean> zzil;
    public static zzdu<Boolean> zzim;
    public static zzdu<Boolean> zzin;
    public static zzdu<Boolean> zzio;
    public static zzdu<Boolean> zzip;
    public static zzdu<Boolean> zziq;
    private static zzdu<Boolean> zzir;
    public static zzdu<Boolean> zzis;
    public static zzdu<Boolean> zzit;
    public static zzdu<Boolean> zziu;
    public static zzdu<Boolean> zziv;
    public static zzdu<Boolean> zziw;
    public static zzdu<Boolean> zzix;
    public static zzdu<Boolean> zziy;
    public static zzdu<Boolean> zziz;
    private static volatile zzfj zzj;
    public static zzdu<Boolean> zzja;
    public static zzdu<Boolean> zzjb;
    public static zzdu<Boolean> zzjc;
    public static zzdu<Boolean> zzjd;
    public static zzdu<Boolean> zzje;
    private static zzdu<Boolean> zzjf;
    public static zzdu<Boolean> zzjg;
    public static zzdu<Boolean> zzjh;
    private static List<zzdu<?>> zzfw = Collections.synchronizedList(new ArrayList());
    private static Set<zzdu<?>> zzfx = Collections.synchronizedSet(new HashSet());
    private static final com.google.android.gms.internal.measurement.zzct zzfy = new com.google.android.gms.internal.measurement.zzct(com.google.android.gms.internal.measurement.zzcn.zzdh("com.google.android.gms.measurement"));
    private static zzdu<Boolean> zzga = zza("measurement.log_third_party_store_events_enabled", false, false, zzan.zzji);
    private static zzdu<Boolean> zzgb = zza("measurement.log_installs_enabled", false, false, zzam.zzji);
    private static zzdu<Boolean> zzgc = zza("measurement.log_upgrades_enabled", false, false, zzaz.zzji);
    public static zzdu<Boolean> zzgd = zza("measurement.log_androidId_enabled", false, false, zzbi.zzji);
    public static zzdu<Boolean> zzge = zza("measurement.upload_dsid_enabled", false, false, zzbv.zzji);
    public static zzdu<String> zzgf = zza("measurement.log_tag", "FA", "FA-SVC", zzce.zzji);
    public static zzdu<Long> zzgg = zza("measurement.ad_id_cache_time", 10000L, 10000L, zzcr.zzji);

    static {
        Long lValueOf = Long.valueOf(C0854h.f927i);
        zzgh = zza("measurement.monitoring.sample_period_millis", lValueOf, lValueOf, zzda.zzji);
        zzgi = zza("measurement.config.cache_time", lValueOf, 3600000L, zzdn.zzji);
        zzgj = zza("measurement.config.url_scheme", "https", "https", zzdt.zzji);
        zzgk = zza("measurement.config.url_authority", "app-measurement.com", "app-measurement.com", zzap.zzji);
        zzgl = zza("measurement.upload.max_bundles", 100, 100, zzao.zzji);
        zzgm = zza("measurement.upload.max_batch_size", 65536, 65536, zzar.zzji);
        zzgn = zza("measurement.upload.max_bundle_size", 65536, 65536, zzaq.zzji);
        zzgo = zza("measurement.upload.max_events_per_bundle", 1000, 1000, zzat.zzji);
        zzgp = zza("measurement.upload.max_events_per_day", 100000, 100000, zzas.zzji);
        zzgq = zza("measurement.upload.max_error_events_per_day", 1000, 1000, zzav.zzji);
        zzgr = zza("measurement.upload.max_public_events_per_day", 50000, 50000, zzau.zzji);
        zzgs = zza("measurement.upload.max_conversions_per_day", 500, 500, zzax.zzji);
        zzgt = zza("measurement.upload.max_realtime_events_per_day", 10, 10, zzaw.zzji);
        zzgu = zza("measurement.store.max_stored_events_per_app", 100000, 100000, zzay.zzji);
        zzgv = zza("measurement.upload.url", "https://app-measurement.com/a", "https://app-measurement.com/a", zzbb.zzji);
        zzgw = zza("measurement.upload.backoff_period", 43200000L, 43200000L, zzba.zzji);
        zzgx = zza("measurement.upload.window_interval", 3600000L, 3600000L, zzbd.zzji);
        zzgy = zza("measurement.upload.interval", 3600000L, 3600000L, zzbc.zzji);
        zzgz = zza("measurement.upload.realtime_upload_interval", 10000L, 10000L, zzbf.zzji);
        zzha = zza("measurement.upload.debug_upload_interval", 1000L, 1000L, zzbe.zzji);
        zzhb = zza("measurement.upload.minimum_delay", 500L, 500L, zzbh.zzji);
        zzhc = zza("measurement.alarm_manager.minimum_interval", 60000L, 60000L, zzbg.zzji);
        zzhd = zza("measurement.upload.stale_data_deletion_interval", lValueOf, lValueOf, zzbj.zzji);
        zzhe = zza("measurement.upload.refresh_blacklisted_config_interval", 604800000L, 604800000L, zzbl.zzji);
        zzhf = zza("measurement.upload.initial_upload_delay_time", 15000L, 15000L, zzbk.zzji);
        zzhg = zza("measurement.upload.retry_time", 1800000L, 1800000L, zzbn.zzji);
        zzhh = zza("measurement.upload.retry_count", 6, 6, zzbm.zzji);
        zzhi = zza("measurement.upload.max_queue_time", 2419200000L, 2419200000L, zzbp.zzji);
        zzhj = zza("measurement.lifetimevalue.max_currency_tracked", 4, 4, zzbo.zzji);
        Integer numValueOf = Integer.valueOf(HttpStatusCodes.STATUS_CODE_OK);
        zzhk = zza("measurement.audience.filter_result_max_count", numValueOf, numValueOf, zzbr.zzji);
        zzhl = zza("measurement.service_client.idle_disconnect_millis", 5000L, 5000L, zzbq.zzji);
        zzhm = zza("measurement.test.boolean_flag", false, false, zzbt.zzji);
        zzhn = zza("measurement.test.string_flag", "---", "---", zzbs.zzji);
        zzho = zza("measurement.test.long_flag", -1L, -1L, zzbu.zzji);
        zzhp = zza("measurement.test.int_flag", -2, -2, zzbx.zzji);
        Double dValueOf = Double.valueOf(-3.0d);
        zzhq = zza("measurement.test.double_flag", dValueOf, dValueOf, zzbw.zzji);
        zzhr = zza("measurement.experiment.max_ids", 50, 50, zzbz.zzji);
        zzhs = zza("measurement.validation.internal_limits_internal_event_params", false, false, zzby.zzji);
        zzht = zza("measurement.audience.dynamic_filters", true, true, zzcb.zzji);
        zzhu = zza("measurement.reset_analytics.persist_time", false, false, zzca.zzji);
        zzhv = zza("measurement.validation.value_and_currency_params", true, true, zzcd.zzji);
        zzhw = zza("measurement.sampling.time_zone_offset_enabled", false, false, zzcc.zzji);
        zzhx = zza("measurement.referrer.enable_logging_install_referrer_cmp_from_apk", false, false, zzcf.zzji);
        zzhy = zza("measurement.fetch_config_with_admob_app_id", true, true, zzch.zzji);
        zzhz = zza("measurement.client.sessions.session_id_enabled", false, false, zzcg.zzji);
        zzia = zza("measurement.service.sessions.session_number_enabled", false, false, zzcj.zzji);
        zzib = zza("measurement.client.sessions.immediate_start_enabled_foreground", false, false, zzci.zzji);
        zzic = zza("measurement.client.sessions.background_sessions_enabled", false, false, zzcl.zzji);
        zzid = zza("measurement.client.sessions.remove_expired_session_properties_enabled", false, false, zzck.zzji);
        zzie = zza("measurement.service.sessions.session_number_backfill_enabled", false, false, zzcn.zzji);
        zzif = zza("measurement.service.sessions.remove_disabled_session_number", false, false, zzcm.zzji);
        zzig = zza("measurement.collection.firebase_global_collection_flag_enabled", true, true, zzcp.zzji);
        zzih = zza("measurement.collection.efficient_engagement_reporting_enabled", false, false, zzco.zzji);
        zzii = zza("measurement.collection.redundant_engagement_removal_enabled", false, false, zzcq.zzji);
        zzij = zza("measurement.personalized_ads_signals_collection_enabled", true, true, zzct.zzji);
        zzik = zza("measurement.personalized_ads_property_translation_enabled", true, true, zzcs.zzji);
        zzil = zza("measurement.collection.init_params_control_enabled", true, true, zzcv.zzji);
        zzim = zza("measurement.upload.disable_is_uploader", true, true, zzcu.zzji);
        zzin = zza("measurement.experiment.enable_experiment_reporting", true, true, zzcx.zzji);
        zzio = zza("measurement.collection.log_event_and_bundle_v2", true, true, zzcw.zzji);
        zzip = zza("measurement.collection.null_empty_event_name_fix", true, true, zzcz.zzji);
        zziq = zza("measurement.audience.sequence_filters", false, false, zzcy.zzji);
        zzir = zza("measurement.audience.sequence_filters_bundle_timestamp", false, false, zzdb.zzji);
        zzis = zza("measurement.quality.checksum", false, false, null);
        zzit = zza("measurement.module.collection.conditionally_omit_admob_app_id", true, true, zzdd.zzji);
        zziu = zza("measurement.sdk.dynamite.use_dynamite2", false, false, zzdc.zzji);
        zziv = zza("measurement.sdk.dynamite.allow_remote_dynamite", false, false, zzdf.zzji);
        zziw = zza("measurement.sdk.collection.validate_param_names_alphabetical", false, false, zzde.zzji);
        zzix = zza("measurement.collection.event_safelist", false, false, zzdh.zzji);
        zziy = zza("measurement.service.audience.scoped_filters_v27", false, false, zzdg.zzji);
        zziz = zza("measurement.service.audience.session_scoped_event_aggregates", false, false, zzdj.zzji);
        zzja = zza("measurement.service.audience.session_scoped_user_engagement", false, false, zzdi.zzji);
        zzjb = zza("measurement.service.audience.remove_disabled_session_scoped_user_engagement", false, false, zzdl.zzji);
        zzjc = zza("measurement.sdk.collection.retrieve_deeplink_from_bow", false, false, zzdk.zzji);
        zzjd = zza("measurement.app_launch.event_ordering_fix", false, false, zzdm.zzji);
        zzje = zza("measurement.sdk.collection.last_deep_link_referrer", false, false, zzdp.zzji);
        zzjf = zza("measurement.sdk.collection.last_deep_link_referrer_campaign", false, false, zzdo.zzji);
        zzjg = zza("measurement.sdk.collection.last_gclid_from_referrer", false, false, zzdr.zzji);
        zzjh = zza("measurement.upload.file_lock_state_check", false, false, zzdq.zzji);
    }

    private static boolean isPackageSide() {
        if (zzfv != null) {
        }
        return false;
    }

    @VisibleForTesting
    private static <V> zzdu<V> zza(@Nullable String str, @Nullable V v, @Nullable V v2, @Nullable zzdv<V> zzdvVar) {
        zzdu<V> zzduVar = new zzdu<>(str, v, v2, zzdvVar);
        zzfw.add(zzduVar);
        return zzduVar;
    }

    static void zza(zzfj zzfjVar) {
        zzj = zzfjVar;
    }

    static void zza(zzr zzrVar) {
        zzfv = zzrVar;
    }

    @VisibleForTesting
    static void zza(Exception exc) {
        if (zzj == null) {
            return;
        }
        Context context = zzj.getContext();
        if (zzfz == null) {
            zzfz = Boolean.valueOf(GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(context, 12451000) == 0);
        }
        if (zzfz.booleanValue()) {
            zzj.zzab().zzgk().zza("Got Exception on PhenotypeFlag.get on Play device", exc);
        }
    }

    static final /* synthetic */ String zzfx() {
        return isPackageSide() ? com.google.android.gms.internal.measurement.zzjn.zzye() : com.google.android.gms.internal.measurement.zzjn.zzxp();
    }

    public static Map<String, String> zzk(Context context) {
        com.google.android.gms.internal.measurement.zzca zzcaVarZza = com.google.android.gms.internal.measurement.zzca.zza(context.getContentResolver(), com.google.android.gms.internal.measurement.zzcn.zzdh("com.google.android.gms.measurement"));
        return zzcaVarZza == null ? Collections.emptyMap() : zzcaVarZza.zzre();
    }
}
