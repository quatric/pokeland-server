package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zabw {
    public final RegisterListenerMethod<Api.AnyClient, ?> zajw;
    public final UnregisterListenerMethod<Api.AnyClient, ?> zajx;

    public zabw(@NonNull RegisterListenerMethod<Api.AnyClient, ?> registerListenerMethod, @NonNull UnregisterListenerMethod<Api.AnyClient, ?> unregisterListenerMethod) {
        this.zajw = registerListenerMethod;
        this.zajx = unregisterListenerMethod;
    }
}
