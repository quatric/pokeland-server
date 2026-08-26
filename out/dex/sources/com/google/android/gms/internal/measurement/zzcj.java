package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.support.annotation.GuardedBy;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzcj implements zzce {

    @GuardedBy("GservicesLoader.class")
    static zzcj zzaau;
    private final Context zzob;

    private zzcj() {
        this.zzob = null;
    }

    private zzcj(Context context) {
        this.zzob = context;
        this.zzob.getContentResolver().registerContentObserver(zzbz.CONTENT_URI, true, new zzcl(this, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.measurement.zzce
    /* JADX INFO: renamed from: zzde, reason: merged with bridge method [inline-methods] */
    public final String zzdd(final String str) {
        if (this.zzob == null) {
            return null;
        }
        try {
            return (String) zzch.zza(new zzcg(this, str) { // from class: com.google.android.gms.internal.measurement.zzci
                private final zzcj zzaas;
                private final String zzaat;

                {
                    this.zzaas = this;
                    this.zzaat = str;
                }

                @Override // com.google.android.gms.internal.measurement.zzcg
                public final Object zzrj() {
                    return this.zzaas.zzdf(this.zzaat);
                }
            });
        } catch (SecurityException e) {
            String strValueOf = String.valueOf(str);
            Log.e("GservicesLoader", strValueOf.length() != 0 ? "Unable to read GServices for: ".concat(strValueOf) : new String("Unable to read GServices for: "), e);
            return null;
        }
    }

    static zzcj zzp(Context context) {
        zzcj zzcjVar;
        synchronized (zzcj.class) {
            if (zzaau == null) {
                zzaau = PermissionChecker.checkSelfPermission(context, "com.google.android.providers.gsf.permission.READ_GSERVICES") == 0 ? new zzcj(context) : new zzcj();
            }
            zzcjVar = zzaau;
        }
        return zzcjVar;
    }

    final /* synthetic */ String zzdf(String str) {
        return zzbz.zza(this.zzob.getContentResolver(), str, (String) null);
    }
}
