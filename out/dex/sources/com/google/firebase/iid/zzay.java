package com.google.firebase.iid;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzay {
    private static final long zzdn = TimeUnit.DAYS.toMillis(7);
    private final long timestamp;
    final String zzbv;
    private final String zzdo;

    private zzay(String str, String str2, long j) {
        this.zzbv = str;
        this.zzdo = str2;
        this.timestamp = j;
    }

    static String zza(String str, String str2, long j) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("token", str);
            jSONObject.put("appVersion", str2);
            jSONObject.put("timestamp", j);
            return jSONObject.toString();
        } catch (JSONException e) {
            String strValueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 24);
            sb.append("Failed to encode token: ");
            sb.append(strValueOf);
            Log.w("FirebaseInstanceId", sb.toString());
            return null;
        }
    }

    static String zzb(@Nullable zzay zzayVar) {
        if (zzayVar == null) {
            return null;
        }
        return zzayVar.zzbv;
    }

    static zzay zzi(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (!str.startsWith("{")) {
            return new zzay(str, null, 0L);
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            return new zzay(jSONObject.getString("token"), jSONObject.getString("appVersion"), jSONObject.getLong("timestamp"));
        } catch (JSONException e) {
            String strValueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 23);
            sb.append("Failed to parse token: ");
            sb.append(strValueOf);
            Log.w("FirebaseInstanceId", sb.toString());
            return null;
        }
    }

    final boolean zzj(String str) {
        return System.currentTimeMillis() > this.timestamp + zzdn || !str.equals(this.zzdo);
    }
}
