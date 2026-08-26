package com.google.firebase.events;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: com.google.firebase:firebase-common@@17.0.0 */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
public interface Subscriber {
    @KeepForSdk
    <T> void subscribe(Class<T> cls, EventHandler<? super T> eventHandler);

    @KeepForSdk
    <T> void subscribe(Class<T> cls, Executor executor, EventHandler<? super T> eventHandler);

    @KeepForSdk
    <T> void unsubscribe(Class<T> cls, EventHandler<? super T> eventHandler);
}
