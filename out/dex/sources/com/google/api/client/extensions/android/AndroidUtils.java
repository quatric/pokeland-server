package com.google.api.client.extensions.android;

import android.os.Build;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Preconditions;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public class AndroidUtils {
    private AndroidUtils() {
    }

    public static void checkMinimumSdkLevel(int i) {
        Preconditions.checkArgument(isMinimumSdkLevel(i), "running on Android SDK level %s but requires minimum %s", Integer.valueOf(Build.VERSION.SDK_INT), Integer.valueOf(i));
    }

    public static boolean isMinimumSdkLevel(int i) {
        return Build.VERSION.SDK_INT >= i;
    }
}
