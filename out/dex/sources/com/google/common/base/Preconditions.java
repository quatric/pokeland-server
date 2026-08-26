package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.firebase.analytics.FirebaseAnalytics;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
public final class Preconditions {
    private Preconditions() {
    }

    private static String badElementIndex(int i, int i2, String str) {
        if (i < 0) {
            return format("%s (%s) must not be negative", str, Integer.valueOf(i));
        }
        if (i2 >= 0) {
            return format("%s (%s) must be less than size (%s)", str, Integer.valueOf(i), Integer.valueOf(i2));
        }
        throw new IllegalArgumentException("negative size: " + i2);
    }

    private static String badPositionIndex(int i, int i2, String str) {
        if (i < 0) {
            return format("%s (%s) must not be negative", str, Integer.valueOf(i));
        }
        if (i2 >= 0) {
            return format("%s (%s) must not be greater than size (%s)", str, Integer.valueOf(i), Integer.valueOf(i2));
        }
        throw new IllegalArgumentException("negative size: " + i2);
    }

    private static String badPositionIndexes(int i, int i2, int i3) {
        if (i < 0 || i > i3) {
            return badPositionIndex(i, i3, "start index");
        }
        return (i2 < 0 || i2 > i3) ? badPositionIndex(i2, i3, "end index") : format("end index (%s) must not be less than start index (%s)", Integer.valueOf(i2), Integer.valueOf(i));
    }

    public static void checkArgument(boolean z) {
        if (!z) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean z, @Nullable Object obj) {
        if (!z) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, char c) {
        if (!z) {
            throw new IllegalArgumentException(format(str, Character.valueOf(c)));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, char c, char c2) {
        if (!z) {
            throw new IllegalArgumentException(format(str, Character.valueOf(c), Character.valueOf(c2)));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, char c, int i) {
        if (!z) {
            throw new IllegalArgumentException(format(str, Character.valueOf(c), Integer.valueOf(i)));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, char c, long j) {
        if (!z) {
            throw new IllegalArgumentException(format(str, Character.valueOf(c), Long.valueOf(j)));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, char c, @Nullable Object obj) {
        if (!z) {
            throw new IllegalArgumentException(format(str, Character.valueOf(c), obj));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, int i) {
        if (!z) {
            throw new IllegalArgumentException(format(str, Integer.valueOf(i)));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, int i, char c) {
        if (!z) {
            throw new IllegalArgumentException(format(str, Integer.valueOf(i), Character.valueOf(c)));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, int i, int i2) {
        if (!z) {
            throw new IllegalArgumentException(format(str, Integer.valueOf(i), Integer.valueOf(i2)));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, int i, long j) {
        if (!z) {
            throw new IllegalArgumentException(format(str, Integer.valueOf(i), Long.valueOf(j)));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, int i, @Nullable Object obj) {
        if (!z) {
            throw new IllegalArgumentException(format(str, Integer.valueOf(i), obj));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, long j) {
        if (!z) {
            throw new IllegalArgumentException(format(str, Long.valueOf(j)));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, long j, char c) {
        if (!z) {
            throw new IllegalArgumentException(format(str, Long.valueOf(j), Character.valueOf(c)));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, long j, int i) {
        if (!z) {
            throw new IllegalArgumentException(format(str, Long.valueOf(j), Integer.valueOf(i)));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, long j, long j2) {
        if (!z) {
            throw new IllegalArgumentException(format(str, Long.valueOf(j), Long.valueOf(j2)));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, long j, @Nullable Object obj) {
        if (!z) {
            throw new IllegalArgumentException(format(str, Long.valueOf(j), obj));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, @Nullable Object obj) {
        if (!z) {
            throw new IllegalArgumentException(format(str, obj));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, @Nullable Object obj, char c) {
        if (!z) {
            throw new IllegalArgumentException(format(str, obj, Character.valueOf(c)));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, @Nullable Object obj, int i) {
        if (!z) {
            throw new IllegalArgumentException(format(str, obj, Integer.valueOf(i)));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, @Nullable Object obj, long j) {
        if (!z) {
            throw new IllegalArgumentException(format(str, obj, Long.valueOf(j)));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, @Nullable Object obj, @Nullable Object obj2) {
        if (!z) {
            throw new IllegalArgumentException(format(str, obj, obj2));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, @Nullable Object obj, @Nullable Object obj2, @Nullable Object obj3) {
        if (!z) {
            throw new IllegalArgumentException(format(str, obj, obj2, obj3));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, @Nullable Object obj, @Nullable Object obj2, @Nullable Object obj3, @Nullable Object obj4) {
        if (!z) {
            throw new IllegalArgumentException(format(str, obj, obj2, obj3, obj4));
        }
    }

    public static void checkArgument(boolean z, @Nullable String str, @Nullable Object... objArr) {
        if (!z) {
            throw new IllegalArgumentException(format(str, objArr));
        }
    }

    @CanIgnoreReturnValue
    public static int checkElementIndex(int i, int i2) {
        return checkElementIndex(i, i2, FirebaseAnalytics.Param.INDEX);
    }

    @CanIgnoreReturnValue
    public static int checkElementIndex(int i, int i2, @Nullable String str) {
        if (i < 0 || i >= i2) {
            throw new IndexOutOfBoundsException(badElementIndex(i, i2, str));
        }
        return i;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.valueOf(obj));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, char c) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Character.valueOf(c)));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, char c, char c2) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Character.valueOf(c), Character.valueOf(c2)));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, char c, int i) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Character.valueOf(c), Integer.valueOf(i)));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, char c, long j) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Character.valueOf(c), Long.valueOf(j)));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, char c, @Nullable Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Character.valueOf(c), obj));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, int i) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Integer.valueOf(i)));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, int i, char c) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Integer.valueOf(i), Character.valueOf(c)));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, int i, int i2) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Integer.valueOf(i), Integer.valueOf(i2)));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, int i, long j) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Integer.valueOf(i), Long.valueOf(j)));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, int i, @Nullable Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Integer.valueOf(i), obj));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, long j) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Long.valueOf(j)));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, long j, char c) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Long.valueOf(j), Character.valueOf(c)));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, long j, int i) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Long.valueOf(j), Integer.valueOf(i)));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, long j, long j2) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Long.valueOf(j), Long.valueOf(j2)));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, long j, @Nullable Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Long.valueOf(j), obj));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, @Nullable Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, obj));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, @Nullable Object obj, char c) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, obj, Character.valueOf(c)));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, @Nullable Object obj, int i) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, obj, Integer.valueOf(i)));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, @Nullable Object obj, long j) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, obj, Long.valueOf(j)));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, @Nullable Object obj, @Nullable Object obj2) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, obj, obj2));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, @Nullable Object obj, @Nullable Object obj2, @Nullable Object obj3) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, obj, obj2, obj3));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, @Nullable Object obj, @Nullable Object obj2, @Nullable Object obj3, @Nullable Object obj4) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, obj, obj2, obj3, obj4));
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String str, @Nullable Object... objArr) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, objArr));
    }

    @CanIgnoreReturnValue
    public static int checkPositionIndex(int i, int i2) {
        return checkPositionIndex(i, i2, FirebaseAnalytics.Param.INDEX);
    }

    @CanIgnoreReturnValue
    public static int checkPositionIndex(int i, int i2, @Nullable String str) {
        if (i < 0 || i > i2) {
            throw new IndexOutOfBoundsException(badPositionIndex(i, i2, str));
        }
        return i;
    }

    public static void checkPositionIndexes(int i, int i2, int i3) {
        if (i < 0 || i2 < i || i2 > i3) {
            throw new IndexOutOfBoundsException(badPositionIndexes(i, i2, i3));
        }
    }

    public static void checkState(boolean z) {
        if (!z) {
            throw new IllegalStateException();
        }
    }

    public static void checkState(boolean z, @Nullable Object obj) {
        if (!z) {
            throw new IllegalStateException(String.valueOf(obj));
        }
    }

    public static void checkState(boolean z, @Nullable String str, char c) {
        if (!z) {
            throw new IllegalStateException(format(str, Character.valueOf(c)));
        }
    }

    public static void checkState(boolean z, @Nullable String str, char c, char c2) {
        if (!z) {
            throw new IllegalStateException(format(str, Character.valueOf(c), Character.valueOf(c2)));
        }
    }

    public static void checkState(boolean z, @Nullable String str, char c, int i) {
        if (!z) {
            throw new IllegalStateException(format(str, Character.valueOf(c), Integer.valueOf(i)));
        }
    }

    public static void checkState(boolean z, @Nullable String str, char c, long j) {
        if (!z) {
            throw new IllegalStateException(format(str, Character.valueOf(c), Long.valueOf(j)));
        }
    }

    public static void checkState(boolean z, @Nullable String str, char c, @Nullable Object obj) {
        if (!z) {
            throw new IllegalStateException(format(str, Character.valueOf(c), obj));
        }
    }

    public static void checkState(boolean z, @Nullable String str, int i) {
        if (!z) {
            throw new IllegalStateException(format(str, Integer.valueOf(i)));
        }
    }

    public static void checkState(boolean z, @Nullable String str, int i, char c) {
        if (!z) {
            throw new IllegalStateException(format(str, Integer.valueOf(i), Character.valueOf(c)));
        }
    }

    public static void checkState(boolean z, @Nullable String str, int i, int i2) {
        if (!z) {
            throw new IllegalStateException(format(str, Integer.valueOf(i), Integer.valueOf(i2)));
        }
    }

    public static void checkState(boolean z, @Nullable String str, int i, long j) {
        if (!z) {
            throw new IllegalStateException(format(str, Integer.valueOf(i), Long.valueOf(j)));
        }
    }

    public static void checkState(boolean z, @Nullable String str, int i, @Nullable Object obj) {
        if (!z) {
            throw new IllegalStateException(format(str, Integer.valueOf(i), obj));
        }
    }

    public static void checkState(boolean z, @Nullable String str, long j) {
        if (!z) {
            throw new IllegalStateException(format(str, Long.valueOf(j)));
        }
    }

    public static void checkState(boolean z, @Nullable String str, long j, char c) {
        if (!z) {
            throw new IllegalStateException(format(str, Long.valueOf(j), Character.valueOf(c)));
        }
    }

    public static void checkState(boolean z, @Nullable String str, long j, int i) {
        if (!z) {
            throw new IllegalStateException(format(str, Long.valueOf(j), Integer.valueOf(i)));
        }
    }

    public static void checkState(boolean z, @Nullable String str, long j, long j2) {
        if (!z) {
            throw new IllegalStateException(format(str, Long.valueOf(j), Long.valueOf(j2)));
        }
    }

    public static void checkState(boolean z, @Nullable String str, long j, @Nullable Object obj) {
        if (!z) {
            throw new IllegalStateException(format(str, Long.valueOf(j), obj));
        }
    }

    public static void checkState(boolean z, @Nullable String str, @Nullable Object obj) {
        if (!z) {
            throw new IllegalStateException(format(str, obj));
        }
    }

    public static void checkState(boolean z, @Nullable String str, @Nullable Object obj, char c) {
        if (!z) {
            throw new IllegalStateException(format(str, obj, Character.valueOf(c)));
        }
    }

    public static void checkState(boolean z, @Nullable String str, @Nullable Object obj, int i) {
        if (!z) {
            throw new IllegalStateException(format(str, obj, Integer.valueOf(i)));
        }
    }

    public static void checkState(boolean z, @Nullable String str, @Nullable Object obj, long j) {
        if (!z) {
            throw new IllegalStateException(format(str, obj, Long.valueOf(j)));
        }
    }

    public static void checkState(boolean z, @Nullable String str, @Nullable Object obj, @Nullable Object obj2) {
        if (!z) {
            throw new IllegalStateException(format(str, obj, obj2));
        }
    }

    public static void checkState(boolean z, @Nullable String str, @Nullable Object obj, @Nullable Object obj2, @Nullable Object obj3) {
        if (!z) {
            throw new IllegalStateException(format(str, obj, obj2, obj3));
        }
    }

    public static void checkState(boolean z, @Nullable String str, @Nullable Object obj, @Nullable Object obj2, @Nullable Object obj3, @Nullable Object obj4) {
        if (!z) {
            throw new IllegalStateException(format(str, obj, obj2, obj3, obj4));
        }
    }

    public static void checkState(boolean z, @Nullable String str, @Nullable Object... objArr) {
        if (!z) {
            throw new IllegalStateException(format(str, objArr));
        }
    }

    static String format(String str, @Nullable Object... objArr) {
        int iIndexOf;
        String strValueOf = String.valueOf(str);
        StringBuilder sb = new StringBuilder(strValueOf.length() + (objArr.length * 16));
        int i = 0;
        int i2 = 0;
        while (i < objArr.length && (iIndexOf = strValueOf.indexOf("%s", i2)) != -1) {
            sb.append((CharSequence) strValueOf, i2, iIndexOf);
            sb.append(objArr[i]);
            i2 = iIndexOf + 2;
            i++;
        }
        sb.append((CharSequence) strValueOf, i2, strValueOf.length());
        if (i < objArr.length) {
            sb.append(" [");
            sb.append(objArr[i]);
            for (int i3 = i + 1; i3 < objArr.length; i3++) {
                sb.append(", ");
                sb.append(objArr[i3]);
            }
            sb.append(']');
        }
        return sb.toString();
    }
}
