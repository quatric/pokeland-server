package com.google.firebase.iid;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.api.client.googleapis.notifications.ResourceStates;
import com.google.api.client.http.HttpStatusCodes;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class FirebaseInstanceIdReceiver extends WakefulBroadcastReceiver {

    @GuardedBy("FirebaseInstanceIdReceiver.class")
    private static zzi zzbl;

    @ShowFirstParty
    @SuppressLint({"InlinedApi"})
    public static int zza(BroadcastReceiver broadcastReceiver, Context context, Intent intent) {
        boolean z = PlatformVersion.isAtLeastO() && context.getApplicationInfo().targetSdkVersion >= 26;
        boolean z2 = (intent.getFlags() & 268435456) != 0;
        if (z && !z2) {
            return zzb(broadcastReceiver, context, intent);
        }
        int iZzc = zzaw.zzak().zzc(context, intent);
        if (!PlatformVersion.isAtLeastO() || iZzc != 402) {
            return iZzc;
        }
        zzb(broadcastReceiver, context, intent);
        return HttpStatusCodes.STATUS_CODE_FORBIDDEN;
    }

    private static synchronized zzi zza(Context context, String str) {
        if (zzbl == null) {
            zzbl = new zzi(context, str);
        }
        return zzbl;
    }

    private final void zza(Context context, Intent intent) {
        int iZza;
        intent.setComponent(null);
        intent.setPackage(context.getPackageName());
        if (Build.VERSION.SDK_INT <= 18) {
            intent.removeCategory(context.getPackageName());
        }
        if ("google.com/iid".equals(intent.getStringExtra("from"))) {
            String stringExtra = intent.getStringExtra("CMD");
            if (stringExtra != null) {
                if (Log.isLoggable("FirebaseInstanceId", 3)) {
                    String strValueOf = String.valueOf(intent.getExtras());
                    StringBuilder sb = new StringBuilder(String.valueOf(stringExtra).length() + 21 + String.valueOf(strValueOf).length());
                    sb.append("Received command: ");
                    sb.append(stringExtra);
                    sb.append(" - ");
                    sb.append(strValueOf);
                    Log.d("FirebaseInstanceId", sb.toString());
                }
                if ("RST".equals(stringExtra) || "RST_FULL".equals(stringExtra)) {
                    FirebaseInstanceId.getInstance().zzn();
                } else if (ResourceStates.SYNC.equals(stringExtra)) {
                    FirebaseInstanceId.getInstance().zzp();
                }
            }
            iZza = -1;
        } else {
            String stringExtra2 = intent.getStringExtra("gcm.rawData64");
            if (stringExtra2 != null) {
                intent.putExtra("rawData", Base64.decode(stringExtra2, 0));
                intent.removeExtra("gcm.rawData64");
            }
            iZza = zza(this, context, intent);
        }
        if (isOrderedBroadcast()) {
            setResultCode(iZza);
        }
    }

    private static int zzb(BroadcastReceiver broadcastReceiver, Context context, Intent intent) {
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "Binding to service");
        }
        if (broadcastReceiver.isOrderedBroadcast()) {
            broadcastReceiver.setResultCode(-1);
        }
        zza(context, "com.google.firebase.MESSAGING_EVENT").zza(intent, broadcastReceiver.goAsync());
        return -1;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        Parcelable parcelableExtra = intent.getParcelableExtra("wrapped_intent");
        Intent intent2 = parcelableExtra instanceof Intent ? (Intent) parcelableExtra : null;
        if (intent2 != null) {
            zza(context, intent2);
        } else {
            zza(context, intent);
        }
    }
}
