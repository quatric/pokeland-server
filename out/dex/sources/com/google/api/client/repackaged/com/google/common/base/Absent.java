package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.GwtCompatible;
import java.util.Collections;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
final class Absent<T> extends Optional<T> {
    static final Absent<Object> INSTANCE = new Absent<>();
    private static final long serialVersionUID = 0;

    private Absent() {
    }

    private Object readResolve() {
        return INSTANCE;
    }

    static <T> Optional<T> withType() {
        return INSTANCE;
    }

    @Override // com.google.api.client.repackaged.com.google.common.base.Optional
    public Set<T> asSet() {
        return Collections.emptySet();
    }

    @Override // com.google.api.client.repackaged.com.google.common.base.Optional
    public boolean equals(@Nullable Object obj) {
        return obj == this;
    }

    @Override // com.google.api.client.repackaged.com.google.common.base.Optional
    public T get() {
        throw new IllegalStateException("Optional.get() cannot be called on an absent value");
    }

    @Override // com.google.api.client.repackaged.com.google.common.base.Optional
    public int hashCode() {
        return 2040732332;
    }

    @Override // com.google.api.client.repackaged.com.google.common.base.Optional
    public boolean isPresent() {
        return false;
    }

    @Override // com.google.api.client.repackaged.com.google.common.base.Optional
    /* JADX INFO: renamed from: or */
    public Optional<T> mo441or(Optional<? extends T> optional) {
        return (Optional) Preconditions.checkNotNull(optional);
    }

    @Override // com.google.api.client.repackaged.com.google.common.base.Optional
    /* JADX INFO: renamed from: or */
    public T mo442or(Supplier<? extends T> supplier) {
        return (T) Preconditions.checkNotNull(supplier.get(), "use Optional.orNull() instead of a Supplier that returns null");
    }

    @Override // com.google.api.client.repackaged.com.google.common.base.Optional
    /* JADX INFO: renamed from: or */
    public T mo443or(T t) {
        return (T) Preconditions.checkNotNull(t, "use Optional.orNull() instead of Optional.or(null)");
    }

    @Override // com.google.api.client.repackaged.com.google.common.base.Optional
    @Nullable
    public T orNull() {
        return null;
    }

    @Override // com.google.api.client.repackaged.com.google.common.base.Optional
    public String toString() {
        return "Optional.absent()";
    }

    @Override // com.google.api.client.repackaged.com.google.common.base.Optional
    public <V> Optional<V> transform(Function<? super T, V> function) {
        Preconditions.checkNotNull(function);
        return Optional.absent();
    }
}
