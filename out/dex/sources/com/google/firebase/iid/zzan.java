package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.common.base.Ascii;
import com.google.firebase.FirebaseApp;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzan {
    private final Context zzag;

    @GuardedBy("this")
    private String zzcn;

    @GuardedBy("this")
    private String zzco;

    @GuardedBy("this")
    private int zzcp;

    @GuardedBy("this")
    private int zzcq = 0;

    public zzan(Context context) {
        this.zzag = context;
    }

    public static String zza(FirebaseApp firebaseApp) {
        String gcmSenderId = firebaseApp.getOptions().getGcmSenderId();
        if (gcmSenderId != null) {
            return gcmSenderId;
        }
        String applicationId = firebaseApp.getOptions().getApplicationId();
        if (!applicationId.startsWith("1:")) {
            return applicationId;
        }
        String[] strArrSplit = applicationId.split(":");
        if (strArrSplit.length < 2) {
            return null;
        }
        String str = strArrSplit[1];
        if (str.isEmpty()) {
            return null;
        }
        return str;
    }

    public static String zza(KeyPair keyPair) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance("SHA1").digest(keyPair.getPublic().getEncoded());
            bArrDigest[0] = (byte) ((bArrDigest[0] & Ascii.f291SI) + 112);
            return Base64.encodeToString(bArrDigest, 0, 8, 11);
        } catch (NoSuchAlgorithmException unused) {
            Log.w("FirebaseInstanceId", "Unexpected error, device missing required algorithms");
            return null;
        }
    }

    private final synchronized void zzag() {
        PackageInfo packageInfoZze = zze(this.zzag.getPackageName());
        if (packageInfoZze != null) {
            this.zzcn = Integer.toString(packageInfoZze.versionCode);
            this.zzco = packageInfoZze.versionName;
        }
    }

    private final PackageInfo zze(String str) {
        try {
            return this.zzag.getPackageManager().getPackageInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            String strValueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 23);
            sb.append("Failed to find package ");
            sb.append(strValueOf);
            Log.w("FirebaseInstanceId", sb.toString());
            return null;
        }
    }

    public final synchronized int zzac() {
        if (this.zzcq != 0) {
            return this.zzcq;
        }
        PackageManager packageManager = this.zzag.getPackageManager();
        if (packageManager.checkPermission("com.google.android.c2dm.permission.SEND", "com.google.android.gms") == -1) {
            Log.e("FirebaseInstanceId", "Google Play services missing or without correct permission.");
            return 0;
        }
        if (!PlatformVersion.isAtLeastO()) {
            Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
            intent.setPackage("com.google.android.gms");
            List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, 0);
            if (listQueryIntentServices != null && listQueryIntentServices.size() > 0) {
                this.zzcq = 1;
                return this.zzcq;
            }
        }
        Intent intent2 = new Intent("com.google.iid.TOKEN_REQUEST");
        intent2.setPackage("com.google.android.gms");
        List<ResolveInfo> listQueryBroadcastReceivers = packageManager.queryBroadcastReceivers(intent2, 0);
        if (listQueryBroadcastReceivers != null && listQueryBroadcastReceivers.size() > 0) {
            this.zzcq = 2;
            return this.zzcq;
        }
        Log.w("FirebaseInstanceId", "Failed to resolve IID implementation package, falling back");
        if (PlatformVersion.isAtLeastO()) {
            this.zzcq = 2;
        } else {
            this.zzcq = 1;
        }
        return this.zzcq;
    }

    public final synchronized String zzad() {
        if (this.zzcn == null) {
            zzag();
        }
        return this.zzcn;
    }

    public final synchronized String zzae() {
        if (this.zzco == null) {
            zzag();
        }
        return this.zzco;
    }

    public final synchronized int zzaf() {
        PackageInfo packageInfoZze;
        if (this.zzcp == 0 && (packageInfoZze = zze("com.google.android.gms")) != null) {
            this.zzcp = packageInfoZze.versionCode;
        }
        return this.zzcp;
    }
}
