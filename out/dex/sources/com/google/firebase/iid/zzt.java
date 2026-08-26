package com.google.firebase.iid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzt implements Continuation<Bundle, String> {
    private final /* synthetic */ zzs zzbs;

    zzt(zzs zzsVar) {
        this.zzbs = zzsVar;
    }

    @Override // com.google.android.gms.tasks.Continuation
    public final /* synthetic */ String then(@NonNull Task<Bundle> task) throws Exception {
        Bundle result = task.getResult(IOException.class);
        zzs zzsVar = this.zzbs;
        return zzs.zza(result);
    }
}
