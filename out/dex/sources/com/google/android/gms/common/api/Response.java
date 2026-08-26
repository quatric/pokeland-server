package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Result;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class Response<T extends Result> {
    private T zzap;

    public Response() {
    }

    protected Response(@NonNull T t) {
        this.zzap = t;
    }

    @NonNull
    protected T getResult() {
        return this.zzap;
    }

    public void setResult(@NonNull T t) {
        this.zzap = t;
    }
}
