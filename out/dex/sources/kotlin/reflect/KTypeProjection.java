package kotlin.reflect;

import com.android.billingclient.BuildConfig;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: KType.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SinceKotlin(version = BuildConfig.VERSION_NAME)
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J!\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, m1984d2 = {"Lkotlin/reflect/KTypeProjection;", "", "variance", "Lkotlin/reflect/KVariance;", "type", "Lkotlin/reflect/KType;", "(Lkotlin/reflect/KVariance;Lkotlin/reflect/KType;)V", "getType", "()Lkotlin/reflect/KType;", "getVariance", "()Lkotlin/reflect/KVariance;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "Companion", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
public final /* data */ class KTypeProjection {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final KTypeProjection STAR = new KTypeProjection(null, null);

    @Nullable
    private final KType type;

    @Nullable
    private final KVariance variance;

    /* JADX INFO: compiled from: KType.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"}, m1984d2 = {"Lkotlin/reflect/KTypeProjection$Companion;", "", "()V", "STAR", "Lkotlin/reflect/KTypeProjection;", "getSTAR", "()Lkotlin/reflect/KTypeProjection;", "contravariant", "type", "Lkotlin/reflect/KType;", "covariant", "invariant", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KTypeProjection contravariant(@NotNull KType type) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            return new KTypeProjection(KVariance.IN, type);
        }

        @NotNull
        public final KTypeProjection covariant(@NotNull KType type) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            return new KTypeProjection(KVariance.OUT, type);
        }

        @NotNull
        public final KTypeProjection getSTAR() {
            return KTypeProjection.STAR;
        }

        @NotNull
        public final KTypeProjection invariant(@NotNull KType type) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            return new KTypeProjection(KVariance.INVARIANT, type);
        }
    }

    public KTypeProjection(@Nullable KVariance kVariance, @Nullable KType kType) {
        this.variance = kVariance;
        this.type = kType;
    }

    public static /* synthetic */ KTypeProjection copy$default(KTypeProjection kTypeProjection, KVariance kVariance, KType kType, int i, Object obj) {
        if ((i & 1) != 0) {
            kVariance = kTypeProjection.variance;
        }
        if ((i & 2) != 0) {
            kType = kTypeProjection.type;
        }
        return kTypeProjection.copy(kVariance, kType);
    }

    @Nullable
    /* JADX INFO: renamed from: component1, reason: from getter */
    public final KVariance getVariance() {
        return this.variance;
    }

    @Nullable
    /* JADX INFO: renamed from: component2, reason: from getter */
    public final KType getType() {
        return this.type;
    }

    @NotNull
    public final KTypeProjection copy(@Nullable KVariance variance, @Nullable KType type) {
        return new KTypeProjection(variance, type);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KTypeProjection)) {
            return false;
        }
        KTypeProjection kTypeProjection = (KTypeProjection) other;
        return Intrinsics.areEqual(this.variance, kTypeProjection.variance) && Intrinsics.areEqual(this.type, kTypeProjection.type);
    }

    @Nullable
    public final KType getType() {
        return this.type;
    }

    @Nullable
    public final KVariance getVariance() {
        return this.variance;
    }

    public int hashCode() {
        KVariance kVariance = this.variance;
        int iHashCode = (kVariance != null ? kVariance.hashCode() : 0) * 31;
        KType kType = this.type;
        return iHashCode + (kType != null ? kType.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "KTypeProjection(variance=" + this.variance + ", type=" + this.type + ")";
    }
}
