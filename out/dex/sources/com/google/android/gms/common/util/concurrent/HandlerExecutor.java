package com.google.android.gms.common.util.concurrent;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.internal.common.zze;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
public class HandlerExecutor implements Executor {
    private final Handler handler;

    @KeepForSdk
    public HandlerExecutor(Looper looper) {
        this.handler = new zze(looper);
    }

    @Override // java.util.concurrent.Executor
    public void execute(@NonNull Runnable runnable) {
        this.handler.post(runnable);
    }
}
