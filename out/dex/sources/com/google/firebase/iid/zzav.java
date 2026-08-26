package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.GuardedBy;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzav {
    private final Context zzag;
    private final SharedPreferences zzdc;
    private final zzz zzdd;

    @GuardedBy("this")
    private final Map<String, zzy> zzde;

    public zzav(Context context) {
        this(context, new zzz());
    }

    private zzav(Context context, zzz zzzVar) {
        this.zzde = new ArrayMap();
        this.zzag = context;
        this.zzdc = context.getSharedPreferences("com.google.android.gms.appid", 0);
        this.zzdd = zzzVar;
        File file = new File(ContextCompat.getNoBackupFilesDir(this.zzag), "com.google.android.gms.appid-no-backup");
        if (file.exists()) {
            return;
        }
        try {
            if (!file.createNewFile() || isEmpty()) {
                return;
            }
            Log.i("FirebaseInstanceId", "App restored, clearing state");
            zzaj();
            FirebaseInstanceId.getInstance().zzn();
        } catch (IOException e) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String strValueOf = String.valueOf(e.getMessage());
                Log.d("FirebaseInstanceId", strValueOf.length() != 0 ? "Error creating file in no backup dir: ".concat(strValueOf) : new String("Error creating file in no backup dir: "));
            }
        }
    }

    private final synchronized boolean isEmpty() {
        return this.zzdc.getAll().isEmpty();
    }

    private static String zza(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 4 + String.valueOf(str2).length() + String.valueOf(str3).length());
        sb.append(str);
        sb.append("|T|");
        sb.append(str2);
        sb.append("|");
        sb.append(str3);
        return sb.toString();
    }

    static String zzd(String str, String str2) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 3 + String.valueOf(str2).length());
        sb.append(str);
        sb.append("|S|");
        sb.append(str2);
        return sb.toString();
    }

    public final synchronized void zza(String str, String str2, String str3, String str4, String str5) {
        String strZza = zzay.zza(str4, str5, System.currentTimeMillis());
        if (strZza == null) {
            return;
        }
        SharedPreferences.Editor editorEdit = this.zzdc.edit();
        editorEdit.putString(zza(str, str2, str3), strZza);
        editorEdit.commit();
    }

    public final synchronized String zzai() {
        return this.zzdc.getString("topic_operation_queue", "");
    }

    public final synchronized void zzaj() {
        this.zzde.clear();
        zzz.zza(this.zzag);
        this.zzdc.edit().clear().commit();
    }

    public final synchronized zzay zzb(String str, String str2, String str3) {
        return zzay.zzi(this.zzdc.getString(zza(str, str2, str3), null));
    }

    public final synchronized void zzc(String str, String str2, String str3) {
        String strZza = zza(str, str2, str3);
        SharedPreferences.Editor editorEdit = this.zzdc.edit();
        editorEdit.remove(strZza);
        editorEdit.commit();
    }

    public final synchronized void zzf(String str) {
        this.zzdc.edit().putString("topic_operation_queue", str).apply();
    }

    public final synchronized zzy zzg(String str) {
        zzy zzyVarZzc;
        zzy zzyVar = this.zzde.get(str);
        if (zzyVar != null) {
            return zzyVar;
        }
        try {
            zzyVarZzc = this.zzdd.zzb(this.zzag, str);
        } catch (zzaa unused) {
            Log.w("FirebaseInstanceId", "Stored data is corrupt, generating new identity");
            FirebaseInstanceId.getInstance().zzn();
            zzyVarZzc = this.zzdd.zzc(this.zzag, str);
        }
        this.zzde.put(str, zzyVarZzc);
        return zzyVarZzc;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public final synchronized void zzh(String str) {
        String strConcat = String.valueOf(str).concat("|T|");
        SharedPreferences.Editor editorEdit = this.zzdc.edit();
        for (String str2 : this.zzdc.getAll().keySet()) {
            if (str2.startsWith(strConcat)) {
                editorEdit.remove(str2);
            }
        }
        editorEdit.commit();
    }
}
