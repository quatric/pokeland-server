package com.unity3d.player;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

/* JADX INFO: renamed from: com.unity3d.player.i */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class FragmentC1127i extends Fragment {
    @Override // android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestPermissions(new String[]{getArguments().getString("PermissionNames")}, 96489);
    }

    @Override // android.app.Fragment
    public final void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i != 96489) {
            return;
        }
        if (strArr.length == 0) {
            requestPermissions(new String[]{getArguments().getString("PermissionNames")}, 96489);
            return;
        }
        FragmentTransaction fragmentTransactionBeginTransaction = getActivity().getFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.remove(this);
        fragmentTransactionBeginTransaction.commit();
    }
}
