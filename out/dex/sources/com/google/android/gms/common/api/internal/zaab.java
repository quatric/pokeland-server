package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zaab {
    private final Map<BasePendingResult<?>, Boolean> zafj = Collections.synchronizedMap(new WeakHashMap());
    private final Map<TaskCompletionSource<?>, Boolean> zafk = Collections.synchronizedMap(new WeakHashMap());

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private final void zaa(boolean z, Status status) {
        HashMap map;
        HashMap map2;
        synchronized (this.zafj) {
            map = new HashMap(this.zafj);
        }
        synchronized (this.zafk) {
            map2 = new HashMap(this.zafk);
        }
        for (Map.Entry entry : map.entrySet()) {
            if (z || ((Boolean) entry.getValue()).booleanValue()) {
                ((BasePendingResult) entry.getKey()).zab(status);
            }
        }
        for (Map.Entry entry2 : map2.entrySet()) {
            if (z || ((Boolean) entry2.getValue()).booleanValue()) {
                ((TaskCompletionSource) entry2.getKey()).trySetException(new ApiException(status));
            }
        }
    }

    final void zaa(BasePendingResult<? extends Result> basePendingResult, boolean z) {
        this.zafj.put(basePendingResult, Boolean.valueOf(z));
        basePendingResult.addStatusListener(new zaac(this, basePendingResult));
    }

    final <TResult> void zaa(TaskCompletionSource<TResult> taskCompletionSource, boolean z) {
        this.zafk.put(taskCompletionSource, Boolean.valueOf(z));
        taskCompletionSource.getTask().addOnCompleteListener(new zaad(this, taskCompletionSource));
    }

    final boolean zaag() {
        return (this.zafj.isEmpty() && this.zafk.isEmpty()) ? false : true;
    }

    public final void zaah() {
        zaa(false, GoogleApiManager.zahw);
    }

    public final void zaai() {
        zaa(true, zacp.zakw);
    }
}
