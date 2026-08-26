package com.deploygate.sdk;

import android.util.Log;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class DeployGateUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "DeployGateUncaughtExceptionHandler";
    private final Thread.UncaughtExceptionHandler mParentHandler;

    public DeployGateUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.mParentHandler = uncaughtExceptionHandler;
    }

    private void sendExceptionToService(Throwable th) {
        DeployGate deployGate = DeployGate.getInstance();
        if (deployGate != null) {
            deployGate.sendCrashReport(th);
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        Log.v(TAG, "DeployGate caught exception, trying to send to service");
        sendExceptionToService(th);
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.mParentHandler;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }
}
