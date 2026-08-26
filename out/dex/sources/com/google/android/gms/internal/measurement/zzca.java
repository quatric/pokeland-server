package com.google.android.gms.internal.measurement;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.GuardedBy;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzca implements zzce {

    @GuardedBy("ConfigurationContentLoader.class")
    static final Map<Uri, zzca> zzaah = new ArrayMap();
    private static final String[] zzaam = {"key", "value"};
    private final Uri uri;
    private final ContentResolver zzaai;
    private volatile Map<String, String> zzaak;
    private final Object zzaaj = new Object();

    @GuardedBy("this")
    private final List<zzcf> zzaal = new ArrayList();

    private zzca(ContentResolver contentResolver, Uri uri) {
        this.zzaai = contentResolver;
        this.uri = uri;
        this.zzaai.registerContentObserver(uri, false, new zzcc(this, null));
    }

    public static zzca zza(ContentResolver contentResolver, Uri uri) {
        zzca zzcaVar;
        synchronized (zzca.class) {
            zzcaVar = zzaah.get(uri);
            if (zzcaVar == null) {
                try {
                    zzca zzcaVar2 = new zzca(contentResolver, uri);
                    try {
                        zzaah.put(uri, zzcaVar2);
                    } catch (SecurityException unused) {
                    }
                    zzcaVar = zzcaVar2;
                } catch (SecurityException unused2) {
                }
            }
        }
        return zzcaVar;
    }

    private final Map<String, String> zzrg() {
        try {
            return (Map) zzch.zza(new zzcg(this) { // from class: com.google.android.gms.internal.measurement.zzcd
                private final zzca zzaar;

                {
                    this.zzaar = this;
                }

                @Override // com.google.android.gms.internal.measurement.zzcg
                public final Object zzrj() {
                    return this.zzaar.zzrh();
                }
            });
        } catch (SQLiteException | IllegalStateException | SecurityException unused) {
            Log.e("ConfigurationContentLoader", "PhenotypeFlag unable to load ContentProvider, using default values");
            return null;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzce
    public final /* synthetic */ Object zzdd(String str) {
        return zzre().get(str);
    }

    public final Map<String, String> zzre() {
        Map<String, String> mapZzrg = this.zzaak;
        if (mapZzrg == null) {
            synchronized (this.zzaaj) {
                mapZzrg = this.zzaak;
                if (mapZzrg == null) {
                    mapZzrg = zzrg();
                    this.zzaak = mapZzrg;
                }
            }
        }
        return mapZzrg != null ? mapZzrg : Collections.emptyMap();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public final void zzrf() {
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

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    final /* synthetic */ Map zzrh() {
        Cursor cursorQuery = this.zzaai.query(this.uri, zzaam, null, null, null);
        if (cursorQuery == null) {
            return Collections.emptyMap();
        }
        try {
            int count = cursorQuery.getCount();
            if (count == 0) {
                return Collections.emptyMap();
            }
            Map arrayMap = count <= 256 ? new ArrayMap(count) : new HashMap(count, 1.0f);
            while (cursorQuery.moveToNext()) {
                arrayMap.put(cursorQuery.getString(0), cursorQuery.getString(1));
            }
            return arrayMap;
        } finally {
            cursorQuery.close();
        }
    }
}
