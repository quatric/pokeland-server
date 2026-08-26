package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;

/* JADX INFO: Add missing generic type declarations: [R, T] */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zak<R, T> implements PendingResultUtil.ResultConverter<R, T> {
    private final /* synthetic */ Response zaoy;

    zak(Response response) {
        this.zaoy = response;
    }

    @Override // com.google.android.gms.common.internal.PendingResultUtil.ResultConverter
    public final /* synthetic */ Object convert(Result result) {
        this.zaoy.setResult(result);
        return this.zaoy;
    }
}
