package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.GuardedBy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzcs implements zzce {

    @GuardedBy("SharedPreferencesLoader.class")
    static final Map<String, zzcs> zzabd = new HashMap();
    private volatile Map<String, ?> zzaak;
    private final SharedPreferences zzabe;
    private final SharedPreferences.OnSharedPreferenceChangeListener zzabf = new SharedPreferences.OnSharedPreferenceChangeListener(this) { // from class: com.google.android.gms.internal.measurement.zzcv
        private final zzcs zzabq;

        {
            this.zzabq = this;
        }

        @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
        public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
            this.zzabq.zza(sharedPreferences, str);
        }
    };
    private final Object zzaaj = new Object();

    @GuardedBy("this")
    private final List<zzcf> zzaal = new ArrayList();

    private zzcs(SharedPreferences sharedPreferences) {
        this.zzabe = sharedPreferences;
        this.zzabe.registerOnSharedPreferenceChangeListener(this.zzabf);
    }

    static zzcs zze(Context context, String str) {
        zzcs zzcsVar;
        SharedPreferences sharedPreferences;
        if (!((!zzcb.zzri() || str.startsWith("direct_boot:")) ? true : zzcb.isUserUnlocked(context))) {
            return null;
        }
        synchronized (zzcs.class) {
            zzcsVar = zzabd.get(str);
            if (zzcsVar == null) {
                if (str.startsWith("direct_boot:")) {
                    if (zzcb.zzri()) {
                        context = context.createDeviceProtectedStorageContext();
                    }
                    sharedPreferences = context.getSharedPreferences(str.substring(12), 0);
                } else {
                    sharedPreferences = context.getSharedPreferences(str, 0);
                }
                zzcsVar = new zzcs(sharedPreferences);
                zzabd.put(str, zzcsVar);
            }
        }
        return zzcsVar;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    final /* synthetic */ void zza(SharedPreferences sharedPreferences, String str) {
        synchronized (this.zzaaj) {
            this.zzaak = null;
            zzcm.zzrl();
        }
        synchronized (this) {
            Iterator<zzcf> it = this.zzaal.iterator();
            while (it.hasNext()) {
                it.next().zzrk();
            }
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzce
    public final Object zzdd(String str) {
        Map<String, ?> all = this.zzaak;
        if (all == null) {
            synchronized (this.zzaaj) {
                all = this.zzaak;
                if (all == null) {
                    all = this.zzabe.getAll();
                    this.zzaak = all;
                }
            }
        }
        if (all != null) {
            return all.get(str);
        }
        return null;
    }
}
