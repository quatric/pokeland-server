package com.unity3d.player;

import android.os.Build;

/* JADX INFO: renamed from: com.unity3d.player.j */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C1128j {

    /* JADX INFO: renamed from: a */
    static final boolean f2041a;

    /* JADX INFO: renamed from: b */
    static final boolean f2042b;

    /* JADX INFO: renamed from: c */
    static final boolean f2043c;

    /* JADX INFO: renamed from: d */
    static final InterfaceC1123e f2044d;

    static {
        f2041a = Build.VERSION.SDK_INT >= 19;
        f2042b = Build.VERSION.SDK_INT >= 21;
        boolean z = Build.VERSION.SDK_INT >= 23;
        f2043c = z;
        f2044d = z ? new C1126h() : null;
    }
}
