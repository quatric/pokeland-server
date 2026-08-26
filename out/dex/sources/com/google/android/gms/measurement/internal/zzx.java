package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzx extends zzjh {
    private static final String[] zzek = {"last_bundled_timestamp", "ALTER TABLE events ADD COLUMN last_bundled_timestamp INTEGER;", "last_bundled_day", "ALTER TABLE events ADD COLUMN last_bundled_day INTEGER;", "last_sampled_complex_event_id", "ALTER TABLE events ADD COLUMN last_sampled_complex_event_id INTEGER;", "last_sampling_rate", "ALTER TABLE events ADD COLUMN last_sampling_rate INTEGER;", "last_exempt_from_sampling", "ALTER TABLE events ADD COLUMN last_exempt_from_sampling INTEGER;", "current_session_count", "ALTER TABLE events ADD COLUMN current_session_count INTEGER;"};
    private static final String[] zzel = {"origin", "ALTER TABLE user_attributes ADD COLUMN origin TEXT;"};
    private static final String[] zzem = {"app_version", "ALTER TABLE apps ADD COLUMN app_version TEXT;", "app_store", "ALTER TABLE apps ADD COLUMN app_store TEXT;", "gmp_version", "ALTER TABLE apps ADD COLUMN gmp_version INTEGER;", "dev_cert_hash", "ALTER TABLE apps ADD COLUMN dev_cert_hash INTEGER;", "measurement_enabled", "ALTER TABLE apps ADD COLUMN measurement_enabled INTEGER;", "last_bundle_start_timestamp", "ALTER TABLE apps ADD COLUMN last_bundle_start_timestamp INTEGER;", "day", "ALTER TABLE apps ADD COLUMN day INTEGER;", "daily_public_events_count", "ALTER TABLE apps ADD COLUMN daily_public_events_count INTEGER;", "daily_events_count", "ALTER TABLE apps ADD COLUMN daily_events_count INTEGER;", "daily_conversions_count", "ALTER TABLE apps ADD COLUMN daily_conversions_count INTEGER;", "remote_config", "ALTER TABLE apps ADD COLUMN remote_config BLOB;", "config_fetched_time", "ALTER TABLE apps ADD COLUMN config_fetched_time INTEGER;", "failed_config_fetch_time", "ALTER TABLE apps ADD COLUMN failed_config_fetch_time INTEGER;", "app_version_int", "ALTER TABLE apps ADD COLUMN app_version_int INTEGER;", "firebase_instance_id", "ALTER TABLE apps ADD COLUMN firebase_instance_id TEXT;", "daily_error_events_count", "ALTER TABLE apps ADD COLUMN daily_error_events_count INTEGER;", "daily_realtime_events_count", "ALTER TABLE apps ADD COLUMN daily_realtime_events_count INTEGER;", "health_monitor_sample", "ALTER TABLE apps ADD COLUMN health_monitor_sample TEXT;", "android_id", "ALTER TABLE apps ADD COLUMN android_id INTEGER;", "adid_reporting_enabled", "ALTER TABLE apps ADD COLUMN adid_reporting_enabled INTEGER;", "ssaid_reporting_enabled", "ALTER TABLE apps ADD COLUMN ssaid_reporting_enabled INTEGER;", "admob_app_id", "ALTER TABLE apps ADD COLUMN admob_app_id TEXT;", "linked_admob_app_id", "ALTER TABLE apps ADD COLUMN linked_admob_app_id TEXT;", "dynamite_version", "ALTER TABLE apps ADD COLUMN dynamite_version INTEGER;", "safelisted_events", "ALTER TABLE apps ADD COLUMN safelisted_events TEXT;"};
    private static final String[] zzen = {"realtime", "ALTER TABLE raw_events ADD COLUMN realtime INTEGER;"};
    private static final String[] zzeo = {"has_realtime", "ALTER TABLE queue ADD COLUMN has_realtime INTEGER;", "retry_count", "ALTER TABLE queue ADD COLUMN retry_count INTEGER;"};
    private static final String[] zzep = {"session_scoped", "ALTER TABLE event_filters ADD COLUMN session_scoped BOOLEAN;"};
    private static final String[] zzeq = {"session_scoped", "ALTER TABLE property_filters ADD COLUMN session_scoped BOOLEAN;"};
    private static final String[] zzer = {"previous_install_count", "ALTER TABLE app2 ADD COLUMN previous_install_count INTEGER;"};
    private final zzy zzes;
    private final zzjd zzet;

    zzx(zzjg zzjgVar) {
        super(zzjgVar);
        this.zzet = new zzjd(zzx());
        this.zzes = new zzy(this, getContext(), "google_app_measurement.db");
    }

    @WorkerThread
    private final long zza(String str, String[] strArr) {
        Cursor cursor = null;
        try {
            try {
                Cursor cursorRawQuery = getWritableDatabase().rawQuery(str, strArr);
                if (!cursorRawQuery.moveToFirst()) {
                    throw new SQLiteException("Database returned empty set");
                }
                long j = cursorRawQuery.getLong(0);
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return j;
            } catch (SQLiteException e) {
                zzab().zzgk().zza("Database error", str, e);
                throw e;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    @WorkerThread
    private final long zza(String str, String[] strArr, long j) {
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = getWritableDatabase().rawQuery(str, strArr);
                if (!cursorRawQuery.moveToFirst()) {
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    return j;
                }
                long j2 = cursorRawQuery.getLong(0);
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return j2;
            } catch (SQLiteException e) {
                zzab().zzgk().zza("Database error", str, e);
                throw e;
            }
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    @WorkerThread
    @VisibleForTesting
    private final Object zza(Cursor cursor, int i) {
        int type = cursor.getType(i);
        if (type == 0) {
            zzab().zzgk().zzao("Loaded invalid null value from database");
            return null;
        }
        if (type == 1) {
            return Long.valueOf(cursor.getLong(i));
        }
        if (type == 2) {
            return Double.valueOf(cursor.getDouble(i));
        }
        if (type == 3) {
            return cursor.getString(i);
        }
        if (type != 4) {
            zzab().zzgk().zza("Loaded invalid unknown value type, ignoring it", Integer.valueOf(type));
            return null;
        }
        zzab().zzgk().zzao("Loaded invalid blob type value, ignoring it");
        return null;
    }

    @WorkerThread
    private static void zza(ContentValues contentValues, String str, Object obj) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(obj);
        if (obj instanceof String) {
            contentValues.put(str, (String) obj);
        } else if (obj instanceof Long) {
            contentValues.put(str, (Long) obj);
        } else {
            if (!(obj instanceof Double)) {
                throw new IllegalArgumentException("Invalid value type");
            }
            contentValues.put(str, (Double) obj);
        }
    }

    @WorkerThread
    private final boolean zza(String str, int i, com.google.android.gms.internal.measurement.zzbk.zza zzaVar) {
        zzbi();
        zzo();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzaVar);
        if (TextUtils.isEmpty(zzaVar.zzjz())) {
            zzab().zzgn().zza("Event filter had no event name. Audience definition ignored. appId, audienceId, filterId", zzef.zzam(str), Integer.valueOf(i), String.valueOf(zzaVar.zzkb() ? Integer.valueOf(zzaVar.getId()) : null));
            return false;
        }
        byte[] byteArray = zzaVar.toByteArray();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("audience_id", Integer.valueOf(i));
        contentValues.put("filter_id", zzaVar.zzkb() ? Integer.valueOf(zzaVar.getId()) : null);
        contentValues.put("event_name", zzaVar.zzjz());
        if (zzad().zze(str, zzak.zziy)) {
            contentValues.put("session_scoped", zzaVar.zzkh() ? Boolean.valueOf(zzaVar.zzki()) : null);
        }
        contentValues.put("data", byteArray);
        try {
            if (getWritableDatabase().insertWithOnConflict("event_filters", null, contentValues, 5) != -1) {
                return true;
            }
            zzab().zzgk().zza("Failed to insert event filter (got -1). appId", zzef.zzam(str));
            return true;
        } catch (SQLiteException e) {
            zzab().zzgk().zza("Error storing event filter. appId", zzef.zzam(str), e);
            return false;
        }
    }

    @WorkerThread
    private final boolean zza(String str, int i, com.google.android.gms.internal.measurement.zzbk.zzd zzdVar) {
        zzbi();
        zzo();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzdVar);
        if (TextUtils.isEmpty(zzdVar.getPropertyName())) {
            zzab().zzgn().zza("Property filter had no property name. Audience definition ignored. appId, audienceId, filterId", zzef.zzam(str), Integer.valueOf(i), String.valueOf(zzdVar.zzkb() ? Integer.valueOf(zzdVar.getId()) : null));
            return false;
        }
        byte[] byteArray = zzdVar.toByteArray();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("audience_id", Integer.valueOf(i));
        contentValues.put("filter_id", zzdVar.zzkb() ? Integer.valueOf(zzdVar.getId()) : null);
        contentValues.put("property_name", zzdVar.getPropertyName());
        if (zzad().zze(str, zzak.zziy)) {
            contentValues.put("session_scoped", zzdVar.zzkh() ? Boolean.valueOf(zzdVar.zzki()) : null);
        }
        contentValues.put("data", byteArray);
        try {
            if (getWritableDatabase().insertWithOnConflict("property_filters", null, contentValues, 5) != -1) {
                return true;
            }
            zzab().zzgk().zza("Failed to insert property filter (got -1). appId", zzef.zzam(str));
            return false;
        } catch (SQLiteException e) {
            zzab().zzgk().zza("Error storing property filter. appId", zzef.zzam(str), e);
            return false;
        }
    }

    private final boolean zza(String str, List<Integer> list) {
        Preconditions.checkNotEmpty(str);
        zzbi();
        zzo();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            long jZza = zza("select count(1) from audience_filter_values where app_id=?", new String[]{str});
            int iMax = Math.max(0, Math.min(2000, zzad().zzb(str, zzak.zzhk)));
            if (jZza <= iMax) {
                return false;
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                Integer num = list.get(i);
                if (num == null || !(num instanceof Integer)) {
                    return false;
                }
                arrayList.add(Integer.toString(num.intValue()));
            }
            String strJoin = TextUtils.join(",", arrayList);
            StringBuilder sb = new StringBuilder(String.valueOf(strJoin).length() + 2);
            sb.append("(");
            sb.append(strJoin);
            sb.append(")");
            String string = sb.toString();
            StringBuilder sb2 = new StringBuilder(String.valueOf(string).length() + 140);
            sb2.append("audience_id in (select audience_id from audience_filter_values where app_id=? and audience_id not in ");
            sb2.append(string);
            sb2.append(" order by rowid desc limit -1 offset ?)");
            return writableDatabase.delete("audience_filter_values", sb2.toString(), new String[]{str, Integer.toString(iMax)}) > 0;
        } catch (SQLiteException e) {
            zzab().zzgk().zza("Database error querying filters. appId", zzef.zzam(str), e);
            return false;
        }
    }

    private final boolean zzcg() {
        return getContext().getDatabasePath("google_app_measurement.db").exists();
    }

    @WorkerThread
    public final void beginTransaction() {
        zzbi();
        getWritableDatabase().beginTransaction();
    }

    @WorkerThread
    public final void endTransaction() {
        zzbi();
        getWritableDatabase().endTransaction();
    }

    @WorkerThread
    @VisibleForTesting
    final SQLiteDatabase getWritableDatabase() {
        zzo();
        try {
            return this.zzes.getWritableDatabase();
        } catch (SQLiteException e) {
            zzab().zzgn().zza("Error opening database", e);
            throw e;
        }
    }

    @WorkerThread
    public final void setTransactionSuccessful() {
        zzbi();
        getWritableDatabase().setTransactionSuccessful();
    }

    public final long zza(com.google.android.gms.internal.measurement.zzbs.zzg zzgVar) throws IOException {
        zzo();
        zzbi();
        Preconditions.checkNotNull(zzgVar);
        Preconditions.checkNotEmpty(zzgVar.zzag());
        byte[] byteArray = zzgVar.toByteArray();
        long jZza = zzgw().zza(byteArray);
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzgVar.zzag());
        contentValues.put("metadata_fingerprint", Long.valueOf(jZza));
        contentValues.put("metadata", byteArray);
        try {
            getWritableDatabase().insertWithOnConflict("raw_events_metadata", null, contentValues, 4);
            return jZza;
        } catch (SQLiteException e) {
            zzab().zzgk().zza("Error storing raw event metadata. appId", zzef.zzam(zzgVar.zzag()), e);
            throw e;
        }
    }

    /* JADX WARN: Code duplicated, block: B:33:0x008a  */
    public final Pair<com.google.android.gms.internal.measurement.zzbs.zzc, Long> zza(String str, Long l) {
        Cursor cursorRawQuery;
        zzo();
        zzbi();
        Cursor cursor = null;
        try {
            try {
                cursorRawQuery = getWritableDatabase().rawQuery("select main_event, children_to_process from main_event_params where app_id=? and event_id=?", new String[]{str, String.valueOf(l)});
                try {
                    if (!cursorRawQuery.moveToFirst()) {
                        zzab().zzgs().zzao("Main event not found");
                        if (cursorRawQuery != null) {
                            cursorRawQuery.close();
                        }
                        return null;
                    }
                    try {
                        Pair<com.google.android.gms.internal.measurement.zzbs.zzc, Long> pairCreate = Pair.create(com.google.android.gms.internal.measurement.zzbs.zzc.zzc(cursorRawQuery.getBlob(0), com.google.android.gms.internal.measurement.zzel.zztq()), Long.valueOf(cursorRawQuery.getLong(1)));
                        if (cursorRawQuery != null) {
                            cursorRawQuery.close();
                        }
                        return pairCreate;
                    } catch (IOException e) {
                        zzab().zzgk().zza("Failed to merge main event. appId, eventId", zzef.zzam(str), l, e);
                        if (cursorRawQuery != null) {
                            cursorRawQuery.close();
                        }
                        return null;
                    }
                } catch (SQLiteException e2) {
                    e = e2;
                    zzab().zzgk().zza("Error selecting main event", e);
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                if (0 != 0) {
                    cursor.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursorRawQuery = null;
        } catch (Throwable th2) {
            th = th2;
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final zzw zza(long j, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        Preconditions.checkNotEmpty(str);
        zzo();
        zzbi();
        String[] strArr = {str};
        zzw zzwVar = new zzw();
        Cursor cursor = null;
        try {
            try {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                Cursor cursorQuery = writableDatabase.query("apps", new String[]{"day", "daily_events_count", "daily_public_events_count", "daily_conversions_count", "daily_error_events_count", "daily_realtime_events_count"}, "app_id=?", new String[]{str}, null, null, null);
                if (!cursorQuery.moveToFirst()) {
                    zzab().zzgn().zza("Not updating daily counts, app is not known. appId", zzef.zzam(str));
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return zzwVar;
                }
                if (cursorQuery.getLong(0) == j) {
                    zzwVar.zzeg = cursorQuery.getLong(1);
                    zzwVar.zzef = cursorQuery.getLong(2);
                    zzwVar.zzeh = cursorQuery.getLong(3);
                    zzwVar.zzei = cursorQuery.getLong(4);
                    zzwVar.zzej = cursorQuery.getLong(5);
                }
                if (z) {
                    zzwVar.zzeg++;
                }
                if (z2) {
                    zzwVar.zzef++;
                }
                if (z3) {
                    zzwVar.zzeh++;
                }
                if (z4) {
                    zzwVar.zzei++;
                }
                if (z5) {
                    zzwVar.zzej++;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("day", Long.valueOf(j));
                contentValues.put("daily_public_events_count", Long.valueOf(zzwVar.zzef));
                contentValues.put("daily_events_count", Long.valueOf(zzwVar.zzeg));
                contentValues.put("daily_conversions_count", Long.valueOf(zzwVar.zzeh));
                contentValues.put("daily_error_events_count", Long.valueOf(zzwVar.zzei));
                contentValues.put("daily_realtime_events_count", Long.valueOf(zzwVar.zzej));
                writableDatabase.update("apps", contentValues, "app_id=?", strArr);
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return zzwVar;
            } catch (SQLiteException e) {
                zzab().zzgk().zza("Error updating daily counts. appId", zzef.zzam(str), e);
                if (0 != 0) {
                    cursor.close();
                }
                return zzwVar;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @WorkerThread
    public final List<Pair<com.google.android.gms.internal.measurement.zzbs.zzg, Long>> zza(String str, int i, int i2) {
        zzo();
        zzbi();
        Preconditions.checkArgument(i > 0);
        Preconditions.checkArgument(i2 > 0);
        Preconditions.checkNotEmpty(str);
        Cursor cursor = null;
        try {
            try {
                Cursor cursorQuery = getWritableDatabase().query("queue", new String[]{"rowid", "data", "retry_count"}, "app_id=?", new String[]{str}, null, null, "rowid", String.valueOf(i));
                if (!cursorQuery.moveToFirst()) {
                    List<Pair<com.google.android.gms.internal.measurement.zzbs.zzg, Long>> listEmptyList = Collections.emptyList();
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return listEmptyList;
                }
                ArrayList arrayList = new ArrayList();
                int length = 0;
                do {
                    long j = cursorQuery.getLong(0);
                    try {
                        byte[] bArrZzb = zzgw().zzb(cursorQuery.getBlob(1));
                        if (!arrayList.isEmpty() && bArrZzb.length + length > i2) {
                            break;
                        }
                        try {
                            com.google.android.gms.internal.measurement.zzbs.zzg.zza zzaVar = (com.google.android.gms.internal.measurement.zzbs.zzg.zza) com.google.android.gms.internal.measurement.zzbs.zzg.zzpr().zzf(bArrZzb, com.google.android.gms.internal.measurement.zzel.zztq());
                            if (!cursorQuery.isNull(2)) {
                                zzaVar.zzw(cursorQuery.getInt(2));
                            }
                            length += bArrZzb.length;
                            arrayList.add(Pair.create((com.google.android.gms.internal.measurement.zzbs.zzg) ((com.google.android.gms.internal.measurement.zzey) zzaVar.zzug()), Long.valueOf(j)));
                        } catch (IOException e) {
                            zzab().zzgk().zza("Failed to merge queued bundle. appId", zzef.zzam(str), e);
                        }
                        if (!cursorQuery.moveToNext()) {
                            break;
                        }
                    } catch (IOException e2) {
                        zzab().zzgk().zza("Failed to unzip queued bundle. appId", zzef.zzam(str), e2);
                    }
                } while (length <= i2);
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return arrayList;
            } catch (Throwable th) {
                if (0 != 0) {
                    cursor.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            zzab().zzgk().zza("Error querying bundles. appId", zzef.zzam(str), e3);
            List<Pair<com.google.android.gms.internal.measurement.zzbs.zzg, Long>> listEmptyList2 = Collections.emptyList();
            if (0 != 0) {
                cursor.close();
            }
            return listEmptyList2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:54:0x011f  */
    /* JADX WARN: Code duplicated, block: B:59:0x0127  */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @WorkerThread
    public final List<zzjp> zza(String str, String str2, String str3) throws Throwable {
        String str4;
        Cursor cursorQuery;
        Preconditions.checkNotEmpty(str);
        zzo();
        zzbi();
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            try {
                try {
                    ArrayList arrayList2 = new ArrayList(3);
                    try {
                        arrayList2.add(str);
                        StringBuilder sb = new StringBuilder("app_id=?");
                        if (TextUtils.isEmpty(str2)) {
                            str4 = str2;
                        } else {
                            str4 = str2;
                            try {
                                arrayList2.add(str4);
                                sb.append(" and origin=?");
                            } catch (SQLiteException e) {
                                e = e;
                                cursorQuery = null;
                            }
                        }
                        if (!TextUtils.isEmpty(str3)) {
                            arrayList2.add(String.valueOf(str3).concat("*"));
                            sb.append(" and name glob ?");
                        }
                        cursorQuery = getWritableDatabase().query("user_attributes", new String[]{AppMeasurementSdk.ConditionalUserProperty.NAME, "set_timestamp", "value", "origin"}, sb.toString(), (String[]) arrayList2.toArray(new String[arrayList2.size()]), null, null, "rowid", "1001");
                        try {
                            if (!cursorQuery.moveToFirst()) {
                                if (cursorQuery != null) {
                                    cursorQuery.close();
                                }
                                return arrayList;
                            }
                            while (true) {
                                if (arrayList.size() >= 1000) {
                                    zzab().zzgk().zza("Read more than the max allowed user properties, ignoring excess", 1000);
                                    break;
                                }
                                String string = cursorQuery.getString(0);
                                long j = cursorQuery.getLong(1);
                                try {
                                    Object objZza = zza(cursorQuery, 2);
                                    String string2 = cursorQuery.getString(3);
                                    if (objZza == null) {
                                        try {
                                            zzab().zzgk().zza("(2)Read invalid user property value, ignoring it", zzef.zzam(str), string2, str3);
                                        } catch (SQLiteException e2) {
                                            e = e2;
                                            str4 = string2;
                                        }
                                    } else {
                                        arrayList.add(new zzjp(str, string2, string, j, objZza));
                                    }
                                    if (!cursorQuery.moveToNext()) {
                                        break;
                                    }
                                    str4 = string2;
                                } catch (SQLiteException e3) {
                                    e = e3;
                                }
                            }
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            return arrayList;
                        } catch (SQLiteException e4) {
                            e = e4;
                        } catch (Throwable th) {
                            th = th;
                            cursor = cursorQuery;
                            if (cursor != null) {
                                cursor.close();
                            }
                            throw th;
                        }
                    } catch (SQLiteException e5) {
                        e = e5;
                        str4 = str2;
                        cursorQuery = null;
                        zzab().zzgk().zza("(2)Error querying user properties", zzef.zzam(str), str4, e);
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return null;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (SQLiteException e6) {
                e = e6;
            }
            zzab().zzgk().zza("(2)Error querying user properties", zzef.zzam(str), str4, e);
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    @WorkerThread
    public final void zza(zzae zzaeVar) {
        Preconditions.checkNotNull(zzaeVar);
        zzo();
        zzbi();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzaeVar.zzce);
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.NAME, zzaeVar.name);
        contentValues.put("lifetime_count", Long.valueOf(zzaeVar.zzfg));
        contentValues.put("current_bundle_count", Long.valueOf(zzaeVar.zzfh));
        contentValues.put("last_fire_timestamp", Long.valueOf(zzaeVar.zzfj));
        contentValues.put("last_bundled_timestamp", Long.valueOf(zzaeVar.zzfk));
        contentValues.put("last_bundled_day", zzaeVar.zzfl);
        contentValues.put("last_sampled_complex_event_id", zzaeVar.zzfm);
        contentValues.put("last_sampling_rate", zzaeVar.zzfn);
        if (zzad().zze(zzaeVar.zzce, zzak.zziz)) {
            contentValues.put("current_session_count", Long.valueOf(zzaeVar.zzfi));
        }
        contentValues.put("last_exempt_from_sampling", (zzaeVar.zzfo == null || !zzaeVar.zzfo.booleanValue()) ? null : 1L);
        try {
            if (getWritableDatabase().insertWithOnConflict("events", null, contentValues, 5) == -1) {
                zzab().zzgk().zza("Failed to insert/update event aggregates (got -1). appId", zzef.zzam(zzaeVar.zzce));
            }
        } catch (SQLiteException e) {
            zzab().zzgk().zza("Error storing event aggregates. appId", zzef.zzam(zzaeVar.zzce), e);
        }
    }

    @WorkerThread
    public final void zza(zzf zzfVar) {
        Preconditions.checkNotNull(zzfVar);
        zzo();
        zzbi();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzfVar.zzag());
        contentValues.put("app_instance_id", zzfVar.getAppInstanceId());
        contentValues.put("gmp_app_id", zzfVar.getGmpAppId());
        contentValues.put("resettable_device_id_hash", zzfVar.zzai());
        contentValues.put("last_bundle_index", Long.valueOf(zzfVar.zzar()));
        contentValues.put("last_bundle_start_timestamp", Long.valueOf(zzfVar.zzaj()));
        contentValues.put("last_bundle_end_timestamp", Long.valueOf(zzfVar.zzak()));
        contentValues.put("app_version", zzfVar.zzal());
        contentValues.put("app_store", zzfVar.zzan());
        contentValues.put("gmp_version", Long.valueOf(zzfVar.zzao()));
        contentValues.put("dev_cert_hash", Long.valueOf(zzfVar.zzap()));
        contentValues.put("measurement_enabled", Boolean.valueOf(zzfVar.isMeasurementEnabled()));
        contentValues.put("day", Long.valueOf(zzfVar.zzav()));
        contentValues.put("daily_public_events_count", Long.valueOf(zzfVar.zzaw()));
        contentValues.put("daily_events_count", Long.valueOf(zzfVar.zzax()));
        contentValues.put("daily_conversions_count", Long.valueOf(zzfVar.zzay()));
        contentValues.put("config_fetched_time", Long.valueOf(zzfVar.zzas()));
        contentValues.put("failed_config_fetch_time", Long.valueOf(zzfVar.zzat()));
        contentValues.put("app_version_int", Long.valueOf(zzfVar.zzam()));
        contentValues.put("firebase_instance_id", zzfVar.getFirebaseInstanceId());
        contentValues.put("daily_error_events_count", Long.valueOf(zzfVar.zzba()));
        contentValues.put("daily_realtime_events_count", Long.valueOf(zzfVar.zzaz()));
        contentValues.put("health_monitor_sample", zzfVar.zzbb());
        contentValues.put("android_id", Long.valueOf(zzfVar.zzbd()));
        contentValues.put("adid_reporting_enabled", Boolean.valueOf(zzfVar.zzbe()));
        contentValues.put("ssaid_reporting_enabled", Boolean.valueOf(zzfVar.zzbf()));
        contentValues.put("admob_app_id", zzfVar.zzah());
        contentValues.put("dynamite_version", Long.valueOf(zzfVar.zzaq()));
        if (zzfVar.zzbh() != null) {
            if (zzfVar.zzbh().size() == 0) {
                zzab().zzgn().zza("Safelisted events should not be an empty list. appId", zzfVar.zzag());
            } else {
                contentValues.put("safelisted_events", TextUtils.join(",", zzfVar.zzbh()));
            }
        }
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase.update("apps", contentValues, "app_id = ?", new String[]{zzfVar.zzag()}) == 0 && writableDatabase.insertWithOnConflict("apps", null, contentValues, 5) == -1) {
                zzab().zzgk().zza("Failed to insert/update app (got -1). appId", zzef.zzam(zzfVar.zzag()));
            }
        } catch (SQLiteException e) {
            zzab().zzgk().zza("Error storing app. appId", zzef.zzam(zzfVar.zzag()), e);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @WorkerThread
    final void zza(String str, com.google.android.gms.internal.measurement.zzbv[] zzbvVarArr) {
        boolean z;
        zzbi();
        zzo();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzbvVarArr);
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            zzbi();
            zzo();
            Preconditions.checkNotEmpty(str);
            SQLiteDatabase writableDatabase2 = getWritableDatabase();
            writableDatabase2.delete("property_filters", "app_id=?", new String[]{str});
            writableDatabase2.delete("event_filters", "app_id=?", new String[]{str});
            for (com.google.android.gms.internal.measurement.zzbv zzbvVar : zzbvVarArr) {
                zzbi();
                zzo();
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotNull(zzbvVar);
                Preconditions.checkNotNull(zzbvVar.zzzh);
                Preconditions.checkNotNull(zzbvVar.zzzg);
                if (zzbvVar.zzzf == null) {
                    zzab().zzgn().zza("Audience with no ID. appId", zzef.zzam(str));
                } else {
                    int iIntValue = zzbvVar.zzzf.intValue();
                    com.google.android.gms.internal.measurement.zzbk.zza[] zzaVarArr = zzbvVar.zzzh;
                    int length = zzaVarArr.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            com.google.android.gms.internal.measurement.zzbk.zzd[] zzdVarArr = zzbvVar.zzzg;
                            int length2 = zzdVarArr.length;
                            int i2 = 0;
                            while (true) {
                                if (i2 >= length2) {
                                    com.google.android.gms.internal.measurement.zzbk.zza[] zzaVarArr2 = zzbvVar.zzzh;
                                    int length3 = zzaVarArr2.length;
                                    int i3 = 0;
                                    while (true) {
                                        if (i3 >= length3) {
                                            z = true;
                                            break;
                                        } else {
                                            if (!zza(str, iIntValue, zzaVarArr2[i3])) {
                                                z = false;
                                                break;
                                            }
                                            i3++;
                                        }
                                    }
                                    if (z) {
                                        for (com.google.android.gms.internal.measurement.zzbk.zzd zzdVar : zzbvVar.zzzg) {
                                            if (!zza(str, iIntValue, zzdVar)) {
                                                z = false;
                                                break;
                                            }
                                        }
                                    }
                                    if (!z) {
                                        zzbi();
                                        zzo();
                                        Preconditions.checkNotEmpty(str);
                                        SQLiteDatabase writableDatabase3 = getWritableDatabase();
                                        writableDatabase3.delete("property_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(iIntValue)});
                                        writableDatabase3.delete("event_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(iIntValue)});
                                        break;
                                    }
                                } else if (zzdVarArr[i2].zzkb()) {
                                    i2++;
                                } else {
                                    zzab().zzgn().zza("Property filter with no ID. Audience definition ignored. appId, audienceId", zzef.zzam(str), zzbvVar.zzzf);
                                }
                            }
                        } else if (zzaVarArr[i].zzkb()) {
                            i++;
                        } else {
                            zzab().zzgn().zza("Event filter with no ID. Audience definition ignored. appId, audienceId", zzef.zzam(str), zzbvVar.zzzf);
                        }
                    }
                }
            }
            ArrayList arrayList = new ArrayList();
            for (com.google.android.gms.internal.measurement.zzbv zzbvVar2 : zzbvVarArr) {
                arrayList.add(zzbvVar2.zzzf);
            }
            zza(str, arrayList);
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    @WorkerThread
    public final boolean zza(com.google.android.gms.internal.measurement.zzbs.zzg zzgVar, boolean z) {
        zzo();
        zzbi();
        Preconditions.checkNotNull(zzgVar);
        Preconditions.checkNotEmpty(zzgVar.zzag());
        Preconditions.checkState(zzgVar.zzof());
        zzca();
        long jCurrentTimeMillis = zzx().currentTimeMillis();
        if (zzgVar.zznr() < jCurrentTimeMillis - zzs.zzbs() || zzgVar.zznr() > zzs.zzbs() + jCurrentTimeMillis) {
            zzab().zzgn().zza("Storing bundle outside of the max uploading time span. appId, now, timestamp", zzef.zzam(zzgVar.zzag()), Long.valueOf(jCurrentTimeMillis), Long.valueOf(zzgVar.zznr()));
        }
        try {
            byte[] bArrZzc = zzgw().zzc(zzgVar.toByteArray());
            zzab().zzgs().zza("Saving bundle, size", Integer.valueOf(bArrZzc.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzgVar.zzag());
            contentValues.put("bundle_end_timestamp", Long.valueOf(zzgVar.zznr()));
            contentValues.put("data", bArrZzc);
            contentValues.put("has_realtime", Integer.valueOf(z ? 1 : 0));
            if (zzgVar.zzpn()) {
                contentValues.put("retry_count", Integer.valueOf(zzgVar.zzpo()));
            }
            try {
                if (getWritableDatabase().insert("queue", null, contentValues) != -1) {
                    return true;
                }
                zzab().zzgk().zza("Failed to insert bundle (got -1). appId", zzef.zzam(zzgVar.zzag()));
                return false;
            } catch (SQLiteException e) {
                zzab().zzgk().zza("Error storing bundle. appId", zzef.zzam(zzgVar.zzag()), e);
                return false;
            }
        } catch (IOException e2) {
            zzab().zzgk().zza("Data loss. Failed to serialize bundle. appId", zzef.zzam(zzgVar.zzag()), e2);
            return false;
        }
    }

    public final boolean zza(zzaf zzafVar, long j, boolean z) {
        zzo();
        zzbi();
        Preconditions.checkNotNull(zzafVar);
        Preconditions.checkNotEmpty(zzafVar.zzce);
        com.google.android.gms.internal.measurement.zzbs.zzc.zza zzaVarZzah = com.google.android.gms.internal.measurement.zzbs.zzc.zzmq().zzah(zzafVar.zzfp);
        for (String str : zzafVar.zzfq) {
            com.google.android.gms.internal.measurement.zzbs.zze.zza zzaVarZzbz = com.google.android.gms.internal.measurement.zzbs.zze.zzng().zzbz(str);
            zzgw().zza(zzaVarZzbz, zzafVar.zzfq.get(str));
            zzaVarZzah.zza(zzaVarZzbz);
        }
        byte[] byteArray = ((com.google.android.gms.internal.measurement.zzbs.zzc) ((com.google.android.gms.internal.measurement.zzey) zzaVarZzah.zzug())).toByteArray();
        zzab().zzgs().zza("Saving event, name, data size", zzy().zzaj(zzafVar.name), Integer.valueOf(byteArray.length));
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzafVar.zzce);
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.NAME, zzafVar.name);
        contentValues.put("timestamp", Long.valueOf(zzafVar.timestamp));
        contentValues.put("metadata_fingerprint", Long.valueOf(j));
        contentValues.put("data", byteArray);
        contentValues.put("realtime", Integer.valueOf(z ? 1 : 0));
        try {
            if (getWritableDatabase().insert("raw_events", null, contentValues) != -1) {
                return true;
            }
            zzab().zzgk().zza("Failed to insert raw event (got -1). appId", zzef.zzam(zzafVar.zzce));
            return false;
        } catch (SQLiteException e) {
            zzab().zzgk().zza("Error storing raw event. appId", zzef.zzam(zzafVar.zzce), e);
            return false;
        }
    }

    @WorkerThread
    public final boolean zza(zzjp zzjpVar) {
        Preconditions.checkNotNull(zzjpVar);
        zzo();
        zzbi();
        if (zze(zzjpVar.zzce, zzjpVar.name) == null) {
            if (zzjs.zzbk(zzjpVar.name)) {
                if (zza("select count(1) from user_attributes where app_id=? and name not like '!_%' escape '!'", new String[]{zzjpVar.zzce}) >= 25) {
                    return false;
                }
            } else if (zzad().zze(zzjpVar.zzce, zzak.zzij)) {
                if (!"_npa".equals(zzjpVar.name) && zza("select count(1) from user_attributes where app_id=? and origin=? AND name like '!_%' escape '!'", new String[]{zzjpVar.zzce, zzjpVar.origin}) >= 25) {
                    return false;
                }
            } else if (zza("select count(1) from user_attributes where app_id=? and origin=? AND name like '!_%' escape '!'", new String[]{zzjpVar.zzce, zzjpVar.origin}) >= 25) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzjpVar.zzce);
        contentValues.put("origin", zzjpVar.origin);
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.NAME, zzjpVar.name);
        contentValues.put("set_timestamp", Long.valueOf(zzjpVar.zztr));
        zza(contentValues, "value", zzjpVar.value);
        try {
            if (getWritableDatabase().insertWithOnConflict("user_attributes", null, contentValues, 5) == -1) {
                zzab().zzgk().zza("Failed to insert/update user property (got -1). appId", zzef.zzam(zzjpVar.zzce));
            }
        } catch (SQLiteException e) {
            zzab().zzgk().zza("Error storing user property. appId", zzef.zzam(zzjpVar.zzce), e);
        }
        return true;
    }

    @WorkerThread
    public final boolean zza(zzq zzqVar) {
        Preconditions.checkNotNull(zzqVar);
        zzo();
        zzbi();
        if (zze(zzqVar.packageName, zzqVar.zzdw.name) == null && zza("SELECT COUNT(1) FROM conditional_properties WHERE app_id=?", new String[]{zzqVar.packageName}) >= 1000) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzqVar.packageName);
        contentValues.put("origin", zzqVar.origin);
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.NAME, zzqVar.zzdw.name);
        zza(contentValues, "value", zzqVar.zzdw.getValue());
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.ACTIVE, Boolean.valueOf(zzqVar.active));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, zzqVar.triggerEventName);
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, Long.valueOf(zzqVar.triggerTimeout));
        zzz();
        contentValues.put("timed_out_event", zzjs.zza(zzqVar.zzdx));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, Long.valueOf(zzqVar.creationTimestamp));
        zzz();
        contentValues.put("triggered_event", zzjs.zza(zzqVar.zzdy));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, Long.valueOf(zzqVar.zzdw.zztr));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, Long.valueOf(zzqVar.timeToLive));
        zzz();
        contentValues.put("expired_event", zzjs.zza(zzqVar.zzdz));
        try {
            if (getWritableDatabase().insertWithOnConflict("conditional_properties", null, contentValues, 5) == -1) {
                zzab().zzgk().zza("Failed to insert/update conditional user property (got -1)", zzef.zzam(zzqVar.packageName));
            }
        } catch (SQLiteException e) {
            zzab().zzgk().zza("Error storing conditional user property", zzef.zzam(zzqVar.packageName), e);
        }
        return true;
    }

    public final boolean zza(String str, Long l, long j, com.google.android.gms.internal.measurement.zzbs.zzc zzcVar) {
        zzo();
        zzbi();
        Preconditions.checkNotNull(zzcVar);
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(l);
        byte[] byteArray = zzcVar.toByteArray();
        zzab().zzgs().zza("Saving complex main event, appId, data size", zzy().zzaj(str), Integer.valueOf(byteArray.length));
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("event_id", l);
        contentValues.put("children_to_process", Long.valueOf(j));
        contentValues.put("main_event", byteArray);
        try {
            if (getWritableDatabase().insertWithOnConflict("main_event_params", null, contentValues, 5) != -1) {
                return true;
            }
            zzab().zzgk().zza("Failed to insert complex main event (got -1). appId", zzef.zzam(str));
            return false;
        } catch (SQLiteException e) {
            zzab().zzgk().zza("Error storing complex main event. appId", zzef.zzam(str), e);
            return false;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @WorkerThread
    public final List<zzjp> zzaa(String str) throws Throwable {
        Cursor cursorQuery;
        Preconditions.checkNotEmpty(str);
        zzo();
        zzbi();
        ArrayList arrayList = new ArrayList();
        try {
            cursorQuery = getWritableDatabase().query("user_attributes", new String[]{AppMeasurementSdk.ConditionalUserProperty.NAME, "origin", "set_timestamp", "value"}, "app_id=?", new String[]{str}, null, null, "rowid", "1000");
            try {
                try {
                    if (!cursorQuery.moveToFirst()) {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return arrayList;
                    }
                    do {
                        String string = cursorQuery.getString(0);
                        String string2 = cursorQuery.getString(1);
                        if (string2 == null) {
                            string2 = "";
                        }
                        String str2 = string2;
                        long j = cursorQuery.getLong(2);
                        Object objZza = zza(cursorQuery, 3);
                        if (objZza == null) {
                            zzab().zzgk().zza("Read invalid user property value, ignoring it. appId", zzef.zzam(str));
                        } else {
                            arrayList.add(new zzjp(str, str2, string, j, objZza));
                        }
                    } while (cursorQuery.moveToNext());
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return arrayList;
                } catch (SQLiteException e) {
                    e = e;
                    zzab().zzgk().zza("Error querying user properties. appId", zzef.zzam(str), e);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery = null;
        }
        th = th;
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        throw th;
    }

    @WorkerThread
    public final zzf zzab(String str) {
        Cursor cursorQuery;
        Preconditions.checkNotEmpty(str);
        zzo();
        zzbi();
        try {
            try {
                boolean z = true;
                cursorQuery = getWritableDatabase().query("apps", new String[]{"app_instance_id", "gmp_app_id", "resettable_device_id_hash", "last_bundle_index", "last_bundle_start_timestamp", "last_bundle_end_timestamp", "app_version", "app_store", "gmp_version", "dev_cert_hash", "measurement_enabled", "day", "daily_public_events_count", "daily_events_count", "daily_conversions_count", "config_fetched_time", "failed_config_fetch_time", "app_version_int", "firebase_instance_id", "daily_error_events_count", "daily_realtime_events_count", "health_monitor_sample", "android_id", "adid_reporting_enabled", "ssaid_reporting_enabled", "admob_app_id", "dynamite_version", "safelisted_events"}, "app_id=?", new String[]{str}, null, null, null);
                try {
                    if (!cursorQuery.moveToFirst()) {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return null;
                    }
                    try {
                        zzf zzfVar = new zzf(this.zzkz.zzjt(), str);
                        zzfVar.zza(cursorQuery.getString(0));
                        zzfVar.zzb(cursorQuery.getString(1));
                        zzfVar.zzd(cursorQuery.getString(2));
                        zzfVar.zzk(cursorQuery.getLong(3));
                        zzfVar.zze(cursorQuery.getLong(4));
                        zzfVar.zzf(cursorQuery.getLong(5));
                        zzfVar.zzf(cursorQuery.getString(6));
                        zzfVar.zzg(cursorQuery.getString(7));
                        zzfVar.zzh(cursorQuery.getLong(8));
                        zzfVar.zzi(cursorQuery.getLong(9));
                        zzfVar.setMeasurementEnabled(cursorQuery.isNull(10) || cursorQuery.getInt(10) != 0);
                        zzfVar.zzn(cursorQuery.getLong(11));
                        zzfVar.zzo(cursorQuery.getLong(12));
                        zzfVar.zzp(cursorQuery.getLong(13));
                        zzfVar.zzq(cursorQuery.getLong(14));
                        zzfVar.zzl(cursorQuery.getLong(15));
                        zzfVar.zzm(cursorQuery.getLong(16));
                        zzfVar.zzg(cursorQuery.isNull(17) ? -2147483648L : cursorQuery.getInt(17));
                        zzfVar.zze(cursorQuery.getString(18));
                        zzfVar.zzs(cursorQuery.getLong(19));
                        zzfVar.zzr(cursorQuery.getLong(20));
                        zzfVar.zzh(cursorQuery.getString(21));
                        long j = 0;
                        zzfVar.zzt(cursorQuery.isNull(22) ? 0L : cursorQuery.getLong(22));
                        zzfVar.zzb(cursorQuery.isNull(23) || cursorQuery.getInt(23) != 0);
                        if (!cursorQuery.isNull(24) && cursorQuery.getInt(24) == 0) {
                            z = false;
                        }
                        zzfVar.zzc(z);
                        zzfVar.zzc(cursorQuery.getString(25));
                        if (!cursorQuery.isNull(26)) {
                            j = cursorQuery.getLong(26);
                        }
                        zzfVar.zzj(j);
                        if (!cursorQuery.isNull(27)) {
                            zzfVar.zza(Arrays.asList(cursorQuery.getString(27).split(",", -1)));
                        }
                        zzfVar.zzaf();
                        if (cursorQuery.moveToNext()) {
                            zzab().zzgk().zza("Got multiple records for app, expected one. appId", zzef.zzam(str));
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return zzfVar;
                    } catch (SQLiteException e) {
                        e = e;
                    }
                } catch (SQLiteException e2) {
                    e = e2;
                } catch (Throwable th) {
                    th = th;
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursorQuery = null;
        } catch (Throwable th3) {
            th = th3;
            cursorQuery = null;
        }
        zzab().zzgk().zza("Error querying app. appId", zzef.zzam(str), e);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return null;
    }

    public final long zzac(String str) {
        Preconditions.checkNotEmpty(str);
        zzo();
        zzbi();
        try {
            return getWritableDatabase().delete("raw_events", "rowid in (select rowid from raw_events where app_id=? order by rowid desc limit -1 offset ?)", new String[]{str, String.valueOf(Math.max(0, Math.min(1000000, zzad().zzb(str, zzak.zzgu))))});
        } catch (SQLiteException e) {
            zzab().zzgk().zza("Error deleting over the limit events. appId", zzef.zzam(str), e);
            return 0L;
        }
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0073  */
    @WorkerThread
    public final byte[] zzad(String str) {
        Cursor cursorQuery;
        Preconditions.checkNotEmpty(str);
        zzo();
        zzbi();
        Cursor cursor = null;
        try {
            try {
                cursorQuery = getWritableDatabase().query("apps", new String[]{"remote_config"}, "app_id=?", new String[]{str}, null, null, null);
                try {
                    if (!cursorQuery.moveToFirst()) {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return null;
                    }
                    byte[] blob = cursorQuery.getBlob(0);
                    if (cursorQuery.moveToNext()) {
                        zzab().zzgk().zza("Got multiple records for app config, expected one. appId", zzef.zzam(str));
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return blob;
                } catch (SQLiteException e) {
                    e = e;
                    zzab().zzgk().zza("Error querying remote config. appId", zzef.zzam(str), e);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                if (0 != 0) {
                    cursor.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0086  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v3, types: [android.database.Cursor] */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    final Map<Integer, List<Integer>> zzae(String str) {
        Cursor cursorRawQuery;
        zzbi();
        zzo();
        Preconditions.checkNotEmpty(str);
        ArrayMap arrayMap = new ArrayMap();
        ?? writableDatabase = getWritableDatabase();
        try {
            try {
                cursorRawQuery = writableDatabase.rawQuery("select audience_id, filter_id from event_filters where app_id = ? and session_scoped = 1 UNION select audience_id, filter_id from property_filters where app_id = ? and session_scoped = 1;", new String[]{str, str});
                try {
                    if (!cursorRawQuery.moveToFirst()) {
                        Map<Integer, List<Integer>> mapEmptyMap = Collections.emptyMap();
                        if (cursorRawQuery != null) {
                            cursorRawQuery.close();
                        }
                        return mapEmptyMap;
                    }
                    do {
                        int i = cursorRawQuery.getInt(0);
                        List arrayList = (List) arrayMap.get(Integer.valueOf(i));
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                            arrayMap.put(Integer.valueOf(i), arrayList);
                        }
                        arrayList.add(Integer.valueOf(cursorRawQuery.getInt(1)));
                    } while (cursorRawQuery.moveToNext());
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    return arrayMap;
                } catch (SQLiteException e) {
                    e = e;
                    zzab().zzgk().zza("Database error querying scoped filters. appId", zzef.zzam(str), e);
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                if (writableDatabase != 0) {
                    writableDatabase.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursorRawQuery = null;
        } catch (Throwable th2) {
            th = th2;
            writableDatabase = 0;
            if (writableDatabase != 0) {
                writableDatabase.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:32:0x0093  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v3, types: [android.database.Cursor] */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    final Map<Integer, com.google.android.gms.internal.measurement.zzbs.zzi> zzaf(String str) {
        Cursor cursorQuery;
        zzbi();
        zzo();
        Preconditions.checkNotEmpty(str);
        ?? writableDatabase = getWritableDatabase();
        try {
            try {
                cursorQuery = writableDatabase.query("audience_filter_values", new String[]{"audience_id", "current_results"}, "app_id=?", new String[]{str}, null, null, null);
                try {
                    if (!cursorQuery.moveToFirst()) {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return null;
                    }
                    ArrayMap arrayMap = new ArrayMap();
                    do {
                        int i = cursorQuery.getInt(0);
                        try {
                            arrayMap.put(Integer.valueOf(i), com.google.android.gms.internal.measurement.zzbs.zzi.zze(cursorQuery.getBlob(1), com.google.android.gms.internal.measurement.zzel.zztq()));
                        } catch (IOException e) {
                            zzab().zzgk().zza("Failed to merge filter results. appId, audienceId, error", zzef.zzam(str), Integer.valueOf(i), e);
                        }
                    } while (cursorQuery.moveToNext());
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return arrayMap;
                } catch (SQLiteException e2) {
                    e = e2;
                    zzab().zzgk().zza("Database error querying filter results. appId", zzef.zzam(str), e);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                if (writableDatabase != 0) {
                    writableDatabase.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
            writableDatabase = 0;
            if (writableDatabase != 0) {
                writableDatabase.close();
            }
            throw th;
        }
    }

    public final long zzag(String str) {
        Preconditions.checkNotEmpty(str);
        return zza("select count(1) from events where app_id=? and name not like '!_%' escape '!'", new String[]{str}, 0L);
    }

    @WorkerThread
    public final List<zzq> zzb(String str, String str2, String str3) {
        Preconditions.checkNotEmpty(str);
        zzo();
        zzbi();
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(str);
        StringBuilder sb = new StringBuilder("app_id=?");
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
            sb.append(" and origin=?");
        }
        if (!TextUtils.isEmpty(str3)) {
            arrayList.add(String.valueOf(str3).concat("*"));
            sb.append(" and name glob ?");
        }
        return zzb(sb.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0124 A[FINALLY_INSNS] */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public final List<zzq> zzb(String str, String[] strArr) {
        zzo();
        zzbi();
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = getWritableDatabase().query("conditional_properties", new String[]{"app_id", "origin", AppMeasurementSdk.ConditionalUserProperty.NAME, "value", AppMeasurementSdk.ConditionalUserProperty.ACTIVE, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, "timed_out_event", AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, "triggered_event", AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, "expired_event"}, str, strArr, null, null, "rowid", "1001");
                if (!cursorQuery.moveToFirst()) {
                    return arrayList;
                }
                do {
                    if (arrayList.size() >= 1000) {
                        zzab().zzgk().zza("Read more than the max allowed conditional properties, ignoring extra", 1000);
                        break;
                    }
                    String string = cursorQuery.getString(0);
                    String string2 = cursorQuery.getString(1);
                    String string3 = cursorQuery.getString(2);
                    Object objZza = zza(cursorQuery, 3);
                    boolean z = cursorQuery.getInt(4) != 0;
                    String string4 = cursorQuery.getString(5);
                    long j = cursorQuery.getLong(6);
                    zzai zzaiVar = (zzai) zzgw().zza(cursorQuery.getBlob(7), zzai.CREATOR);
                    arrayList.add(new zzq(string, string2, new zzjn(string3, cursorQuery.getLong(10), objZza, string2), cursorQuery.getLong(8), z, string4, zzaiVar, j, (zzai) zzgw().zza(cursorQuery.getBlob(9), zzai.CREATOR), cursorQuery.getLong(11), (zzai) zzgw().zza(cursorQuery.getBlob(12), zzai.CREATOR)));
                } while (cursorQuery.moveToNext());
                return arrayList;
            } catch (SQLiteException e) {
                zzab().zzgk().zza("Error querying conditional user property value", e);
                return Collections.emptyList();
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
    }

    @WorkerThread
    @VisibleForTesting
    final void zzb(List<Long> list) {
        zzo();
        zzbi();
        Preconditions.checkNotNull(list);
        Preconditions.checkNotZero(list.size());
        if (zzcg()) {
            String strJoin = TextUtils.join(",", list);
            StringBuilder sb = new StringBuilder(String.valueOf(strJoin).length() + 2);
            sb.append("(");
            sb.append(strJoin);
            sb.append(")");
            String string = sb.toString();
            StringBuilder sb2 = new StringBuilder(String.valueOf(string).length() + 80);
            sb2.append("SELECT COUNT(1) FROM queue WHERE rowid IN ");
            sb2.append(string);
            sb2.append(" AND retry_count =  2147483647 LIMIT 1");
            if (zza(sb2.toString(), (String[]) null) > 0) {
                zzab().zzgn().zzao("The number of upload retries exceeds the limit. Will remain unchanged.");
            }
            try {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                StringBuilder sb3 = new StringBuilder(String.valueOf(string).length() + 127);
                sb3.append("UPDATE queue SET retry_count = IFNULL(retry_count, 0) + 1 WHERE rowid IN ");
                sb3.append(string);
                sb3.append(" AND (retry_count IS NULL OR retry_count < 2147483647)");
                writableDatabase.execSQL(sb3.toString());
            } catch (SQLiteException e) {
                zzab().zzgk().zza("Error incrementing retry count. error", e);
            }
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzjh
    protected final boolean zzbk() {
        return false;
    }

    @WorkerThread
    public final String zzby() throws Throwable {
        Throwable th;
        Cursor cursorRawQuery;
        try {
            cursorRawQuery = getWritableDatabase().rawQuery("select app_id from queue order by has_realtime desc, rowid asc limit 1;", null);
            try {
                try {
                    if (!cursorRawQuery.moveToFirst()) {
                        if (cursorRawQuery != null) {
                            cursorRawQuery.close();
                        }
                        return null;
                    }
                    String string = cursorRawQuery.getString(0);
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    return string;
                } catch (SQLiteException e) {
                    e = e;
                    zzab().zzgk().zza("Database error getting next bundle app id", e);
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    return null;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursorRawQuery = null;
        } catch (Throwable th3) {
            th = th3;
            cursorRawQuery = null;
        }
        th = th2;
        if (cursorRawQuery != null) {
            cursorRawQuery.close();
        }
        throw th;
    }

    public final boolean zzbz() {
        return zza("select count(1) > 0 from queue where has_realtime = 1", (String[]) null) != 0;
    }

    /* JADX WARN: Code duplicated, block: B:59:0x0154  */
    /* JADX WARN: Code duplicated, block: B:63:0x015b  */
    @WorkerThread
    public final zzae zzc(String str, String str2) {
        Cursor cursor;
        Boolean boolValueOf;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzo();
        zzbi();
        boolean zZze = zzad().zze(str, zzak.zziz);
        ArrayList arrayList = new ArrayList(Arrays.asList("lifetime_count", "current_bundle_count", "last_fire_timestamp", "last_bundled_timestamp", "last_bundled_day", "last_sampled_complex_event_id", "last_sampling_rate", "last_exempt_from_sampling"));
        if (zZze) {
            arrayList.add("current_session_count");
        }
        try {
            Cursor cursorQuery = getWritableDatabase().query("events", (String[]) arrayList.toArray(new String[0]), "app_id=? and name=?", new String[]{str, str2}, null, null, null);
            try {
                if (!cursorQuery.moveToFirst()) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
                long j = cursorQuery.getLong(0);
                long j2 = cursorQuery.getLong(1);
                long j3 = cursorQuery.getLong(2);
                long j4 = 0;
                long j5 = cursorQuery.isNull(3) ? 0L : cursorQuery.getLong(3);
                Long lValueOf = cursorQuery.isNull(4) ? null : Long.valueOf(cursorQuery.getLong(4));
                Long lValueOf2 = cursorQuery.isNull(5) ? null : Long.valueOf(cursorQuery.getLong(5));
                Long lValueOf3 = cursorQuery.isNull(6) ? null : Long.valueOf(cursorQuery.getLong(6));
                if (cursorQuery.isNull(7)) {
                    boolValueOf = null;
                } else {
                    boolValueOf = Boolean.valueOf(cursorQuery.getLong(7) == 1);
                }
                if (zZze && !cursorQuery.isNull(8)) {
                    j4 = cursorQuery.getLong(8);
                }
                cursor = cursorQuery;
                try {
                    try {
                        zzae zzaeVar = new zzae(str, str2, j, j2, j4, j3, j5, lValueOf, lValueOf2, lValueOf3, boolValueOf);
                        if (cursor.moveToNext()) {
                            zzab().zzgk().zza("Got multiple records for event aggregates, expected one. appId", zzef.zzam(str));
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                        return zzaeVar;
                    } catch (SQLiteException e) {
                        e = e;
                        zzab().zzgk().zza("Error querying events. appId", zzef.zzam(str), zzy().zzaj(str2), e);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return null;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (SQLiteException e2) {
                e = e2;
                cursor = cursorQuery;
            } catch (Throwable th2) {
                th = th2;
                cursor = cursorQuery;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
        }
        zzab().zzgk().zza("Error querying events. appId", zzef.zzam(str), zzy().zzaj(str2), e);
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    @WorkerThread
    final void zzca() {
        int iDelete;
        zzo();
        zzbi();
        if (zzcg()) {
            long j = zzac().zzlm.get();
            long jElapsedRealtime = zzx().elapsedRealtime();
            if (Math.abs(jElapsedRealtime - j) > zzak.zzhd.get(null).longValue()) {
                zzac().zzlm.set(jElapsedRealtime);
                zzo();
                zzbi();
                if (!zzcg() || (iDelete = getWritableDatabase().delete("queue", "abs(bundle_end_timestamp - ?) > cast(? as integer)", new String[]{String.valueOf(zzx().currentTimeMillis()), String.valueOf(zzs.zzbs())})) <= 0) {
                    return;
                }
                zzab().zzgs().zza("Deleted stale rows. rowsDeleted", Integer.valueOf(iDelete));
            }
        }
    }

    @WorkerThread
    public final long zzcb() {
        return zza("select max(bundle_end_timestamp) from queue", (String[]) null, 0L);
    }

    @WorkerThread
    public final long zzcc() {
        return zza("select max(timestamp) from raw_events", (String[]) null, 0L);
    }

    public final boolean zzcd() {
        return zza("select count(1) > 0 from raw_events", (String[]) null) != 0;
    }

    public final boolean zzce() {
        return zza("select count(1) > 0 from raw_events where realtime = 1", (String[]) null) != 0;
    }

    public final long zzcf() {
        Cursor cursorRawQuery = null;
        try {
            cursorRawQuery = getWritableDatabase().rawQuery("select rowid from raw_events order by rowid desc limit 1;", null);
            if (cursorRawQuery.moveToFirst()) {
                return cursorRawQuery.getLong(0);
            }
            return -1L;
        } catch (SQLiteException e) {
            zzab().zzgk().zza("Error querying raw events", e);
            return -1L;
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        }
    }

    @WorkerThread
    public final void zzd(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzo();
        zzbi();
        try {
            zzab().zzgs().zza("Deleted user attribute rows", Integer.valueOf(getWritableDatabase().delete("user_attributes", "app_id=? and name=?", new String[]{str, str2})));
        } catch (SQLiteException e) {
            zzab().zzgk().zza("Error deleting user attribute. appId", zzef.zzam(str), zzy().zzal(str2), e);
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x00a2  */
    /* JADX WARN: Code duplicated, block: B:33:0x00a9  */
    @WorkerThread
    public final zzjp zze(String str, String str2) {
        Cursor cursorQuery;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzo();
        zzbi();
        try {
            cursorQuery = getWritableDatabase().query("user_attributes", new String[]{"set_timestamp", "value", "origin"}, "app_id=? and name=?", new String[]{str, str2}, null, null, null);
            try {
                if (!cursorQuery.moveToFirst()) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
                try {
                    try {
                        zzjp zzjpVar = new zzjp(str, cursorQuery.getString(2), str2, cursorQuery.getLong(0), zza(cursorQuery, 1));
                        if (cursorQuery.moveToNext()) {
                            zzab().zzgk().zza("Got multiple records for user property, expected one. appId", zzef.zzam(str));
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return zzjpVar;
                    } catch (SQLiteException e) {
                        e = e;
                        zzab().zzgk().zza("Error querying user property. appId", zzef.zzam(str), zzy().zzal(str2), e);
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return null;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    throw th;
                }
            } catch (SQLiteException e2) {
                e = e2;
            } catch (Throwable th2) {
                th = th2;
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursorQuery = null;
        } catch (Throwable th3) {
            th = th3;
            cursorQuery = null;
        }
        zzab().zzgk().zza("Error querying user property. appId", zzef.zzam(str), zzy().zzal(str2), e);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return null;
    }

    @WorkerThread
    public final zzq zzf(String str, String str2) throws Throwable {
        Cursor cursorQuery;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzo();
        zzbi();
        try {
            try {
                cursorQuery = getWritableDatabase().query("conditional_properties", new String[]{"origin", "value", AppMeasurementSdk.ConditionalUserProperty.ACTIVE, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, "timed_out_event", AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, "triggered_event", AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, "expired_event"}, "app_id=? and name=?", new String[]{str, str2}, null, null, null);
                try {
                    if (!cursorQuery.moveToFirst()) {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return null;
                    }
                    String string = cursorQuery.getString(0);
                    try {
                        Object objZza = zza(cursorQuery, 1);
                        boolean z = cursorQuery.getInt(2) != 0;
                        zzq zzqVar = new zzq(str, string, new zzjn(str2, cursorQuery.getLong(8), objZza, string), cursorQuery.getLong(6), z, cursorQuery.getString(3), (zzai) zzgw().zza(cursorQuery.getBlob(5), zzai.CREATOR), cursorQuery.getLong(4), (zzai) zzgw().zza(cursorQuery.getBlob(7), zzai.CREATOR), cursorQuery.getLong(9), (zzai) zzgw().zza(cursorQuery.getBlob(10), zzai.CREATOR));
                        if (cursorQuery.moveToNext()) {
                            zzab().zzgk().zza("Got multiple records for conditional property, expected one", zzef.zzam(str), zzy().zzal(str2));
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return zzqVar;
                    } catch (SQLiteException e) {
                        e = e;
                    }
                } catch (SQLiteException e2) {
                    e = e2;
                } catch (Throwable th) {
                    th = th;
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursorQuery = null;
        } catch (Throwable th3) {
            th = th3;
            cursorQuery = null;
        }
        zzab().zzgk().zza("Error querying conditional property", zzef.zzam(str), zzy().zzal(str2), e);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return null;
    }

    @WorkerThread
    public final int zzg(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzo();
        zzbi();
        try {
            return getWritableDatabase().delete("conditional_properties", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e) {
            zzab().zzgk().zza("Error deleting conditional property", zzef.zzam(str), zzy().zzal(str2), e);
            return 0;
        }
    }

    /* JADX WARN: Code duplicated, block: B:35:0x00ad  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v3, types: [android.database.Cursor] */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    final Map<Integer, List<com.google.android.gms.internal.measurement.zzbk.zza>> zzh(String str, String str2) {
        Cursor cursorQuery;
        zzbi();
        zzo();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        ArrayMap arrayMap = new ArrayMap();
        try {
            try {
                cursorQuery = getWritableDatabase().query("event_filters", new String[]{"audience_id", "data"}, "app_id=? AND event_name=?", new String[]{str, str2}, null, null, null);
                try {
                    if (!cursorQuery.moveToFirst()) {
                        Map<Integer, List<com.google.android.gms.internal.measurement.zzbk.zza>> mapEmptyMap = Collections.emptyMap();
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return mapEmptyMap;
                    }
                    do {
                        try {
                            com.google.android.gms.internal.measurement.zzbk.zza zzaVarZza = com.google.android.gms.internal.measurement.zzbk.zza.zza(cursorQuery.getBlob(1), com.google.android.gms.internal.measurement.zzel.zztq());
                            int i = cursorQuery.getInt(0);
                            List arrayList = (List) arrayMap.get(Integer.valueOf(i));
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                                arrayMap.put(Integer.valueOf(i), arrayList);
                            }
                            arrayList.add(zzaVarZza);
                        } catch (IOException e) {
                            zzab().zzgk().zza("Failed to merge filter. appId", zzef.zzam(str), e);
                        }
                    } while (cursorQuery.moveToNext());
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return arrayMap;
                } catch (SQLiteException e2) {
                    e = e2;
                    zzab().zzgk().zza("Database error querying filters. appId", zzef.zzam(str), e);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                if (str2 != 0) {
                    str2.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
            str2 = 0;
            if (str2 != 0) {
                str2.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:35:0x00ad  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v3, types: [android.database.Cursor] */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    final Map<Integer, List<com.google.android.gms.internal.measurement.zzbk.zzd>> zzi(String str, String str2) {
        Cursor cursorQuery;
        zzbi();
        zzo();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        ArrayMap arrayMap = new ArrayMap();
        try {
            try {
                cursorQuery = getWritableDatabase().query("property_filters", new String[]{"audience_id", "data"}, "app_id=? AND property_name=?", new String[]{str, str2}, null, null, null);
                try {
                    if (!cursorQuery.moveToFirst()) {
                        Map<Integer, List<com.google.android.gms.internal.measurement.zzbk.zzd>> mapEmptyMap = Collections.emptyMap();
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return mapEmptyMap;
                    }
                    do {
                        try {
                            com.google.android.gms.internal.measurement.zzbk.zzd zzdVarZzb = com.google.android.gms.internal.measurement.zzbk.zzd.zzb(cursorQuery.getBlob(1), com.google.android.gms.internal.measurement.zzel.zztq());
                            int i = cursorQuery.getInt(0);
                            List arrayList = (List) arrayMap.get(Integer.valueOf(i));
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                                arrayMap.put(Integer.valueOf(i), arrayList);
                            }
                            arrayList.add(zzdVarZzb);
                        } catch (IOException e) {
                            zzab().zzgk().zza("Failed to merge filter", zzef.zzam(str), e);
                        }
                    } while (cursorQuery.moveToNext());
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return arrayMap;
                } catch (SQLiteException e2) {
                    e = e2;
                    zzab().zzgk().zza("Database error querying filters. appId", zzef.zzam(str), e);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                if (str2 != 0) {
                    str2.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
            str2 = 0;
            if (str2 != 0) {
                str2.close();
            }
            throw th;
        }
    }

    @WorkerThread
    @VisibleForTesting
    protected final long zzj(String str, String str2) throws Throwable {
        long jZza;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzo();
        zzbi();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 32);
            sb.append("select ");
            sb.append(str2);
            sb.append(" from app2 where app_id=?");
            try {
                try {
                    jZza = zza(sb.toString(), new String[]{str}, -1L);
                    try {
                        if (jZza == -1) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("app_id", str);
                            contentValues.put("first_open_count", (Integer) 0);
                            contentValues.put("previous_install_count", (Integer) 0);
                            if (writableDatabase.insertWithOnConflict("app2", null, contentValues, 5) == -1) {
                                zzab().zzgk().zza("Failed to insert column (got -1). appId", zzef.zzam(str), str2);
                                writableDatabase.endTransaction();
                                return -1L;
                            }
                            jZza = 0;
                            zzab().zzgk().zza("Error inserting column. appId", zzef.zzam(str), str2, e);
                            writableDatabase.endTransaction();
                            return jZza;
                        }
                        ContentValues contentValues2 = new ContentValues();
                        contentValues2.put("app_id", str);
                        contentValues2.put(str2, Long.valueOf(1 + jZza));
                        if (writableDatabase.update("app2", contentValues2, "app_id = ?", new String[]{str}) == 0) {
                            zzab().zzgk().zza("Failed to update column (got 0). appId", zzef.zzam(str), str2);
                            writableDatabase.endTransaction();
                            return -1L;
                        }
                        writableDatabase.setTransactionSuccessful();
                        writableDatabase.endTransaction();
                        return jZza;
                    } catch (SQLiteException e) {
                        e = e;
                        zzab().zzgk().zza("Error inserting column. appId", zzef.zzam(str), str2, e);
                        writableDatabase.endTransaction();
                    }
                } catch (SQLiteException e2) {
                    e = e2;
                    jZza = 0;
                }
            } catch (Throwable th) {
                th = th;
                writableDatabase.endTransaction();
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:26:0x005b  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [long] */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v3, types: [android.database.Cursor] */
    public final String zzu(long j) throws Throwable {
        Cursor cursorRawQuery;
        zzo();
        zzbi();
        try {
            try {
                cursorRawQuery = getWritableDatabase().rawQuery("select app_id from apps where app_id in (select distinct app_id from raw_events) and config_fetched_time < ? order by failed_config_fetch_time limit 1;", new String[]{String.valueOf((long) j)});
                try {
                    if (cursorRawQuery.moveToFirst()) {
                        String string = cursorRawQuery.getString(0);
                        if (cursorRawQuery != null) {
                            cursorRawQuery.close();
                        }
                        return string;
                    }
                    zzab().zzgs().zzao("No expired configs for apps with pending events");
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    return null;
                } catch (SQLiteException e) {
                    e = e;
                    zzab().zzgk().zza("Error selecting expired configs", e);
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                if (j != 0) {
                    j.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursorRawQuery = null;
        } catch (Throwable th2) {
            th = th2;
            j = 0;
            if (j != 0) {
                j.close();
            }
            throw th;
        }
    }
}
