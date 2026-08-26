package com.unity3d.player;

import android.util.Log;
import com.metaps.common.UnityWrapper;

/* JADX INFO: renamed from: com.unity3d.player.g */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C1125g {

    /* JADX INFO: renamed from: a */
    protected static boolean f2040a = false;

    protected static void Log(int i, String str) {
        if (f2040a) {
            return;
        }
        if (i == 6) {
            Log.e(UnityWrapper.PLATFORM, str);
        }
        if (i == 5) {
            Log.w(UnityWrapper.PLATFORM, str);
        }
    }
}
