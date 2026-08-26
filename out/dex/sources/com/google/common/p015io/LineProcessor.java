package com.google.common.p015io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@GwtIncompatible
public interface LineProcessor<T> {
    T getResult();

    @CanIgnoreReturnValue
    boolean processLine(String str) throws IOException;
}
