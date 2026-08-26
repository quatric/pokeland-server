package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
public final class Objects extends ExtraObjectsMethodsForWeb {

    @Deprecated
    public static final class ToStringHelper {
        private final String className;
        private final ValueHolder holderHead;
        private ValueHolder holderTail;
        private boolean omitNullValues;

        private static final class ValueHolder {
            String name;
            ValueHolder next;
            Object value;

            private ValueHolder() {
            }
        }

        private ToStringHelper(String str) {
            this.holderHead = new ValueHolder();
            this.holderTail = this.holderHead;
            this.omitNullValues = false;
            this.className = (String) Preconditions.checkNotNull(str);
        }

        private ValueHolder addHolder() {
            ValueHolder valueHolder = new ValueHolder();
            this.holderTail.next = valueHolder;
            this.holderTail = valueHolder;
            return valueHolder;
        }

        private ToStringHelper addHolder(@Nullable Object obj) {
            addHolder().value = obj;
            return this;
        }

        private ToStringHelper addHolder(String str, @Nullable Object obj) {
            ValueHolder valueHolderAddHolder = addHolder();
            valueHolderAddHolder.value = obj;
            valueHolderAddHolder.name = (String) Preconditions.checkNotNull(str);
            return this;
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, char c) {
            return addHolder(str, String.valueOf(c));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, double d) {
            return addHolder(str, String.valueOf(d));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, float f) {
            return addHolder(str, String.valueOf(f));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, int i) {
            return addHolder(str, String.valueOf(i));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, long j) {
            return addHolder(str, String.valueOf(j));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, @Nullable Object obj) {
            return addHolder(str, obj);
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, boolean z) {
            return addHolder(str, String.valueOf(z));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(char c) {
            return addHolder(String.valueOf(c));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(double d) {
            return addHolder(String.valueOf(d));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(float f) {
            return addHolder(String.valueOf(f));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(int i) {
            return addHolder(String.valueOf(i));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(long j) {
            return addHolder(String.valueOf(j));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(@Nullable Object obj) {
            return addHolder(obj);
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(boolean z) {
            return addHolder(String.valueOf(z));
        }

        @CanIgnoreReturnValue
        public ToStringHelper omitNullValues() {
            this.omitNullValues = true;
            return this;
        }

        public String toString() {
            boolean z = this.omitNullValues;
            StringBuilder sb = new StringBuilder(32);
            sb.append(this.className);
            sb.append('{');
            String str = "";
            for (ValueHolder valueHolder = this.holderHead.next; valueHolder != null; valueHolder = valueHolder.next) {
                if (!z || valueHolder.value != null) {
                    sb.append(str);
                    if (valueHolder.name != null) {
                        sb.append(valueHolder.name);
                        sb.append('=');
                    }
                    sb.append(valueHolder.value);
                    str = ", ";
                }
            }
            sb.append('}');
            return sb.toString();
        }
    }

    private Objects() {
    }

    public static boolean equal(@Nullable Object obj, @Nullable Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    @Deprecated
    public static <T> T firstNonNull(@Nullable T t, @Nullable T t2) {
        return (T) MoreObjects.firstNonNull(t, t2);
    }

    public static int hashCode(@Nullable Object... objArr) {
        return Arrays.hashCode(objArr);
    }

    @Deprecated
    public static ToStringHelper toStringHelper(Class<?> cls) {
        return new ToStringHelper(cls.getSimpleName());
    }

    @Deprecated
    public static ToStringHelper toStringHelper(Object obj) {
        return new ToStringHelper(obj.getClass().getSimpleName());
    }

    @Deprecated
    public static ToStringHelper toStringHelper(String str) {
        return new ToStringHelper(str);
    }
}
