package com.google.android.gms.internal.measurement;

import android.support.v4.media.session.PlaybackStateCompat;
import com.metaps.common.C0854h;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzjp implements zzjq {
    private static final zzcm<Long> zzapx;
    private static final zzcm<Long> zzapy;
    private static final zzcm<String> zzapz;
    private static final zzcm<String> zzaqa;
    private static final zzcm<String> zzaqb;
    private static final zzcm<Long> zzaqc;
    private static final zzcm<Long> zzaqd;
    private static final zzcm<Long> zzaqe;
    private static final zzcm<Long> zzaqf;
    private static final zzcm<Long> zzaqg;
    private static final zzcm<Long> zzaqh;
    private static final zzcm<Long> zzaqi;
    private static final zzcm<Long> zzaqj;
    private static final zzcm<Long> zzaqk;
    private static final zzcm<Long> zzaql;
    private static final zzcm<Long> zzaqm;
    private static final zzcm<Long> zzaqn;
    private static final zzcm<String> zzaqo;
    private static final zzcm<Long> zzaqp;
    private static final zzcm<Long> zzaqq;
    private static final zzcm<Long> zzaqr;
    private static final zzcm<Long> zzaqs;
    private static final zzcm<Long> zzaqt;
    private static final zzcm<Long> zzaqu;
    private static final zzcm<Long> zzaqv;
    private static final zzcm<Long> zzaqw;
    private static final zzcm<Long> zzaqx;
    private static final zzcm<Long> zzaqy;
    private static final zzcm<Long> zzaqz;
    private static final zzcm<Long> zzara;
    private static final zzcm<Long> zzarb;
    private static final zzcm<Long> zzarc;
    private static final zzcm<Long> zzard;
    private static final zzcm<Long> zzare;
    private static final zzcm<String> zzarf;
    private static final zzcm<Long> zzarg;

    static {
        zzct zzctVar = new zzct(zzcn.zzdh("com.google.android.gms.measurement"));
        zzapx = zzctVar.zze("measurement.ad_id_cache_time", 10000L);
        zzapy = zzctVar.zze("measurement.config.cache_time", 3600000L);
        zzapz = zzctVar.zzt("measurement.log_tag", "FA");
        zzaqa = zzctVar.zzt("measurement.config.url_authority", "app-measurement.com");
        zzaqb = zzctVar.zzt("measurement.config.url_scheme", "https");
        zzaqc = zzctVar.zze("measurement.upload.debug_upload_interval", 1000L);
        zzaqd = zzctVar.zze("measurement.lifetimevalue.max_currency_tracked", 4L);
        zzaqe = zzctVar.zze("measurement.store.max_stored_events_per_app", 100000L);
        zzaqf = zzctVar.zze("measurement.experiment.max_ids", 50L);
        zzaqg = zzctVar.zze("measurement.audience.filter_result_max_count", 200L);
        zzaqh = zzctVar.zze("measurement.alarm_manager.minimum_interval", 60000L);
        zzaqi = zzctVar.zze("measurement.upload.minimum_delay", 500L);
        zzaqj = zzctVar.zze("measurement.monitoring.sample_period_millis", C0854h.f927i);
        zzaqk = zzctVar.zze("measurement.upload.realtime_upload_interval", 10000L);
        zzaql = zzctVar.zze("measurement.upload.refresh_blacklisted_config_interval", 604800000L);
        zzaqm = zzctVar.zze("measurement.config.cache_time.service", C0854h.f927i);
        zzaqn = zzctVar.zze("measurement.service_client.idle_disconnect_millis", 5000L);
        zzaqo = zzctVar.zzt("measurement.log_tag.service", "FA-SVC");
        zzaqp = zzctVar.zze("measurement.upload.stale_data_deletion_interval", C0854h.f927i);
        zzaqq = zzctVar.zze("measurement.upload.backoff_period", 43200000L);
        zzaqr = zzctVar.zze("measurement.upload.initial_upload_delay_time", 15000L);
        zzaqs = zzctVar.zze("measurement.upload.interval", 3600000L);
        zzaqt = zzctVar.zze("measurement.upload.max_bundle_size", PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH);
        zzaqu = zzctVar.zze("measurement.upload.max_bundles", 100L);
        zzaqv = zzctVar.zze("measurement.upload.max_conversions_per_day", 500L);
        zzaqw = zzctVar.zze("measurement.upload.max_error_events_per_day", 1000L);
        zzaqx = zzctVar.zze("measurement.upload.max_events_per_bundle", 1000L);
        zzaqy = zzctVar.zze("measurement.upload.max_events_per_day", 100000L);
        zzaqz = zzctVar.zze("measurement.upload.max_public_events_per_day", 50000L);
        zzara = zzctVar.zze("measurement.upload.max_queue_time", 2419200000L);
        zzarb = zzctVar.zze("measurement.upload.max_realtime_events_per_day", 10L);
        zzarc = zzctVar.zze("measurement.upload.max_batch_size", PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH);
        zzard = zzctVar.zze("measurement.upload.retry_count", 6L);
        zzare = zzctVar.zze("measurement.upload.retry_time", 1800000L);
        zzarf = zzctVar.zzt("measurement.upload.url", "https://app-measurement.com/a");
        zzarg = zzctVar.zze("measurement.upload.window_interval", 3600000L);
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzxn() {
        return zzapx.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzxo() {
        return zzapy.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final String zzxp() {
        return zzapz.get();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final String zzxq() {
        return zzaqa.get();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final String zzxr() {
        return zzaqb.get();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzxs() {
        return zzaqc.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzxt() {
        return zzaqd.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzxu() {
        return zzaqe.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzxv() {
        return zzaqf.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzxw() {
        return zzaqg.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzxx() {
        return zzaqh.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzxy() {
        return zzaqi.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzxz() {
        return zzaqj.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzya() {
        return zzaqk.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyb() {
        return zzaql.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyc() {
        return zzaqm.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyd() {
        return zzaqn.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final String zzye() {
        return zzaqo.get();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyf() {
        return zzaqp.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyg() {
        return zzaqq.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyh() {
        return zzaqr.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyi() {
        return zzaqs.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyj() {
        return zzaqt.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyk() {
        return zzaqu.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyl() {
        return zzaqv.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzym() {
        return zzaqw.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyn() {
        return zzaqx.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyo() {
        return zzaqy.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyp() {
        return zzaqz.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyq() {
        return zzara.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyr() {
        return zzarb.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzys() {
        return zzarc.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyt() {
        return zzard.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyu() {
        return zzare.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final String zzyv() {
        return zzarf.get();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final long zzyw() {
        return zzarg.get().longValue();
    }
}
