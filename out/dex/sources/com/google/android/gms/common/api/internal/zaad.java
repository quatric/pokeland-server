package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: Add missing generic type declarations: [TResult] */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zaad<TResult> implements OnCompleteListener<TResult> {
    private final /* synthetic */ zaab zafm;
    private final /* synthetic */ TaskCompletionSource zafn;

    zaad(zaab zaabVar, TaskCompletionSource taskCompletionSource) {
        this.zafm = zaabVar;
        this.zafn = taskCompletionSource;
    }

    @Override // com.google.android.gms.tasks.OnCompleteListener
    public final void onComplete(@NonNull Task<TResult> task) {
        this.zafm.zafk.remove(this.zafn);
    }
}
