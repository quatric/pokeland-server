package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@GwtCompatible
public final class Verify {
    private Verify() {
    }

    public static void verify(boolean z) {
        if (!z) {
            throw new VerifyException();
        }
    }

    public static void verify(boolean z, @Nullable String str, @Nullable Object... objArr) {
        if (!z) {
            throw new VerifyException(Preconditions.format(str, objArr));
        }
    }

    @CanIgnoreReturnValue
    public static <T> T verifyNotNull(@Nullable T t) {
        return (T) verifyNotNull(t, "expected a non-null reference", new Object[0]);
    }

    @CanIgnoreReturnValue
    public static <T> T verifyNotNull(@Nullable T t, @Nullable String str, @Nullable Object... objArr) {
        verify(t != null, str, objArr);
        return t;
    }
}
