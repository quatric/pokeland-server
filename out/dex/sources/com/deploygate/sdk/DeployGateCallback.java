package com.deploygate.sdk;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface DeployGateCallback {
    void onInitialized(boolean z);

    void onStatusChanged(boolean z, boolean z2, String str, boolean z3);

    void onUpdateAvailable(int i, String str, int i2);
}
