package kotlin.jvm.internal;

import java.lang.annotation.Annotation;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.SinceKotlin;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.KVariance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: TypeReference.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SinceKotlin(version = "1.4")
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u0017\u001a\u00020\u0013H\u0002J\u0013\u0010\u0018\u001a\u00020\b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0096\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0013H\u0016J\f\u0010\u0017\u001a\u00020\u0013*\u00020\u0006H\u0002R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0011R\u001c\u0010\u0012\u001a\u00020\u0013*\u0006\u0012\u0002\b\u00030\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001e"}, m1984d2 = {"Lkotlin/jvm/internal/TypeReference;", "Lkotlin/reflect/KType;", "classifier", "Lkotlin/reflect/KClassifier;", "arguments", "", "Lkotlin/reflect/KTypeProjection;", "isMarkedNullable", "", "(Lkotlin/reflect/KClassifier;Ljava/util/List;Z)V", "annotations", "", "getAnnotations", "()Ljava/util/List;", "getArguments", "getClassifier", "()Lkotlin/reflect/KClassifier;", "()Z", "arrayClassName", "", "Ljava/lang/Class;", "getArrayClassName", "(Ljava/lang/Class;)Ljava/lang/String;", "asString", "equals", "other", "", "hashCode", "", "toString", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
public final class TypeReference implements KType {

    @NotNull
    private final List<KTypeProjection> arguments;

    @NotNull
    private final KClassifier classifier;
    private final boolean isMarkedNullable;

    @Metadata(m1982bv = {1, 0, 3}, m1985k = 3, m1986mv = {1, 1, 15})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[KVariance.values().length];

        static {
            $EnumSwitchMapping$0[KVariance.INVARIANT.ordinal()] = 1;
            $EnumSwitchMapping$0[KVariance.IN.ordinal()] = 2;
            $EnumSwitchMapping$0[KVariance.OUT.ordinal()] = 3;
        }
    }

    public TypeReference(@NotNull KClassifier classifier, @NotNull List<KTypeProjection> arguments, boolean z) {
        Intrinsics.checkParameterIsNotNull(classifier, "classifier");
        Intrinsics.checkParameterIsNotNull(arguments, "arguments");
        this.classifier = classifier;
        this.arguments = arguments;
        this.isMarkedNullable = z;
    }

    private final String asString() {
        String arrayClassName;
        KClassifier classifier = getClassifier();
        if (!(classifier instanceof KClass)) {
            classifier = null;
        }
        KClass kClass = (KClass) classifier;
        Class<?> javaClass = kClass != null ? JvmClassMappingKt.getJavaClass(kClass) : null;
        if (javaClass == null) {
            arrayClassName = getClassifier().toString();
        } else {
            arrayClassName = javaClass.isArray() ? getArrayClassName(javaClass) : javaClass.getName();
        }
        return arrayClassName + (getArguments().isEmpty() ? "" : CollectionsKt.joinToString$default(getArguments(), ", ", "<", ">", 0, null, new Function1<KTypeProjection, String>() { // from class: kotlin.jvm.internal.TypeReference$asString$args$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final String invoke(@NotNull KTypeProjection it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return this.this$0.asString(it);
            }
        }, 24, null)) + (getIsMarkedNullable() ? "?" : "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String asString(@NotNull KTypeProjection kTypeProjection) {
        String strValueOf;
        if (kTypeProjection.getVariance() == null) {
            return "*";
        }
        KType type = kTypeProjection.getType();
        if (!(type instanceof TypeReference)) {
            type = null;
        }
        TypeReference typeReference = (TypeReference) type;
        if (typeReference == null || (strValueOf = typeReference.asString()) == null) {
            strValueOf = String.valueOf(kTypeProjection.getType());
        }
        KVariance variance = kTypeProjection.getVariance();
        if (variance != null) {
            int i = WhenMappings.$EnumSwitchMapping$0[variance.ordinal()];
            if (i == 1) {
                return strValueOf;
            }
            if (i == 2) {
                return "in " + strValueOf;
            }
            if (i == 3) {
                return "out " + strValueOf;
            }
        }
        throw new NoWhenBranchMatchedException();
    }

    private final String getArrayClassName(@NotNull Class<?> cls) {
        if (Intrinsics.areEqual(cls, boolean[].class)) {
            return "kotlin.BooleanArray";
        }
        if (Intrinsics.areEqual(cls, char[].class)) {
            return "kotlin.CharArray";
        }
        if (Intrinsics.areEqual(cls, byte[].class)) {
            return "kotlin.ByteArray";
        }
        if (Intrinsics.areEqual(cls, short[].class)) {
            return "kotlin.ShortArray";
        }
        if (Intrinsics.areEqual(cls, int[].class)) {
            return "kotlin.IntArray";
        }
        if (Intrinsics.areEqual(cls, float[].class)) {
            return "kotlin.FloatArray";
        }
        if (Intrinsics.areEqual(cls, long[].class)) {
            return "kotlin.LongArray";
        }
        return Intrinsics.areEqual(cls, double[].class) ? "kotlin.DoubleArray" : "kotlin.Array";
    }

    public boolean equals(@Nullable Object other) {
        if (other instanceof TypeReference) {
            TypeReference typeReference = (TypeReference) other;
            if (Intrinsics.areEqual(getClassifier(), typeReference.getClassifier()) && Intrinsics.areEqual(getArguments(), typeReference.getArguments()) && getIsMarkedNullable() == typeReference.getIsMarkedNullable()) {
                return true;
            }
        }
        return false;
    }

    @Override // kotlin.reflect.KAnnotatedElement
    @NotNull
    public List<Annotation> getAnnotations() {
        return CollectionsKt.emptyList();
    }

    @Override // kotlin.reflect.KType
    @NotNull
    public List<KTypeProjection> getArguments() {
        return this.arguments;
    }

    @Override // kotlin.reflect.KType
    @NotNull
    public KClassifier getClassifier() {
        return this.classifier;
    }

    public int hashCode() {
        return (((getClassifier().hashCode() * 31) + getArguments().hashCode()) * 31) + Boolean.valueOf(getIsMarkedNullable()).hashCode();
    }

    @Override // kotlin.reflect.KType
    /* JADX INFO: renamed from: isMarkedNullable, reason: from getter */
    public boolean getIsMarkedNullable() {
        return this.isMarkedNullable;
    }

    @NotNull
    public String toString() {
        return asString() + " (Kotlin reflection is not available)";
    }
}
