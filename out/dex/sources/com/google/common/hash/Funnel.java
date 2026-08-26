package com.google.common.hash;

import com.google.common.annotations.Beta;
import java.io.Serializable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public interface Funnel<T> extends Serializable {
    void funnel(T t, PrimitiveSink primitiveSink);
}
