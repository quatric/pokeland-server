package com.google.android.gms.common;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.internal.zai;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zaa implements Continuation<Map<zai<?>, String>, Void> {
    zaa(GoogleApiAvailability googleApiAvailability) {
    }

    @Override // com.google.android.gms.tasks.Continuation
    public final /* synthetic */ Void then(@NonNull Task<Map<zai<?>, String>> task) throws Exception {
        task.getResult();
        return null;
    }
}
