package com.unity3d.player;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/* JADX INFO: renamed from: com.unity3d.player.h */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C1126h implements InterfaceC1123e {
    /* JADX INFO: renamed from: a */
    private static boolean m1929a(PackageItemInfo packageItemInfo) {
        try {
            return packageItemInfo.metaData.getBoolean("unityplayer.SkipPermissionsDialog");
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // com.unity3d.player.InterfaceC1123e
    /* JADX INFO: renamed from: a */
    public final void mo1927a(Activity activity, String str) {
        if (activity == null || str == null) {
            return;
        }
        FragmentManager fragmentManager = activity.getFragmentManager();
        if (fragmentManager.findFragmentByTag("96489") == null) {
            FragmentC1127i fragmentC1127i = new FragmentC1127i();
            Bundle bundle = new Bundle();
            bundle.putString("PermissionNames", str);
            fragmentC1127i.setArguments(bundle);
            FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction();
            fragmentTransactionBeginTransaction.add(0, fragmentC1127i, "96489");
            fragmentTransactionBeginTransaction.commit();
        }
    }

    @Override // com.unity3d.player.InterfaceC1123e
    /* JADX INFO: renamed from: a */
    public final boolean mo1928a(Activity activity) {
        try {
            PackageManager packageManager = activity.getPackageManager();
            return m1929a(packageManager.getActivityInfo(activity.getComponentName(), 128)) || m1929a(packageManager.getApplicationInfo(activity.getPackageName(), 128));
        } catch (Exception unused) {
            return false;
        }
    }
}
