package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.net.Uri;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzck {
    private static volatile zzcw<Boolean> zzaav = zzcw.zzrp();
    private static final Object zzaaw = new Object();

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static boolean zza(Context context, Uri uri) {
        ProviderInfo providerInfoResolveContentProvider;
        String authority = uri.getAuthority();
        if (!"com.google.android.gms.phenotype".equals(authority)) {
            StringBuilder sb = new StringBuilder(String.valueOf(authority).length() + 91);
            sb.append(authority);
            sb.append(" is an unsupported authority. Only com.google.android.gms.phenotype authority is supported.");
            throw new IllegalArgumentException(sb.toString());
        }
        if (zzaav.isPresent()) {
            return zzaav.get().booleanValue();
        }
        synchronized (zzaaw) {
            if (zzaav.isPresent()) {
                return zzaav.get().booleanValue();
            }
            boolean z = false;
            if (("com.google.android.gms".equals(context.getPackageName()) || ((providerInfoResolveContentProvider = context.getPackageManager().resolveContentProvider("com.google.android.gms.phenotype", 0)) != null && "com.google.android.gms".equals(providerInfoResolveContentProvider.packageName))) && zzq(context)) {
                z = true;
            }
            zzaav = zzcw.zzf(Boolean.valueOf(z));
            return zzaav.get().booleanValue();
        }
    }

    private static boolean zzq(Context context) {
        try {
            return (context.getPackageManager().getApplicationInfo("com.google.android.gms", 0).flags & 129) != 0;
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }
}
