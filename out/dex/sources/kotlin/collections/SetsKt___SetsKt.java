package kotlin.collections;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: _Sets.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a,\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0086\u0002¢\u0006\u0002\u0010\u0004\u001a4\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0006H\u0086\u0002¢\u0006\u0002\u0010\u0007\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0086\u0002\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\tH\u0086\u0002\u001a,\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\u0004\u001a,\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0086\u0002¢\u0006\u0002\u0010\u0004\u001a4\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0006H\u0086\u0002¢\u0006\u0002\u0010\u0007\u001a-\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0086\u0002\u001a-\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\tH\u0086\u0002\u001a,\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\u0004¨\u0006\r"}, m1984d2 = {"minus", "", "T", "element", "(Ljava/util/Set;Ljava/lang/Object;)Ljava/util/Set;", "elements", "", "(Ljava/util/Set;[Ljava/lang/Object;)Ljava/util/Set;", "", "Lkotlin/sequences/Sequence;", "minusElement", "plus", "plusElement", "kotlin-stdlib"}, m1985k = 5, m1986mv = {1, 1, 15}, m1988xi = 1, m1989xs = "kotlin/collections/SetsKt")
class SetsKt___SetsKt extends SetsKt__SetsKt {
    @NotNull
    public static final <T> Set<T> minus(@NotNull Set<? extends T> minus, @NotNull Iterable<? extends T> elements) {
        Intrinsics.checkParameterIsNotNull(minus, "$this$minus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        Set<? extends T> set = minus;
        Collection<?> collectionConvertToSetForSetOperationWith = CollectionsKt.convertToSetForSetOperationWith(elements, set);
        if (collectionConvertToSetForSetOperationWith.isEmpty()) {
            return CollectionsKt.toSet(set);
        }
        if (!(collectionConvertToSetForSetOperationWith instanceof Set)) {
            LinkedHashSet linkedHashSet = new LinkedHashSet(minus);
            linkedHashSet.removeAll(collectionConvertToSetForSetOperationWith);
            return linkedHashSet;
        }
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        for (T t : set) {
            if (!collectionConvertToSetForSetOperationWith.contains(t)) {
                linkedHashSet2.add(t);
            }
        }
        return linkedHashSet2;
    }

    @NotNull
    public static final <T> Set<T> minus(@NotNull Set<? extends T> minus, T t) {
        Intrinsics.checkParameterIsNotNull(minus, "$this$minus");
        LinkedHashSet linkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(minus.size()));
        boolean z = false;
        for (T t2 : minus) {
            boolean z2 = true;
            if (!z && Intrinsics.areEqual(t2, t)) {
                z = true;
                z2 = false;
            }
            if (z2) {
                linkedHashSet.add(t2);
            }
        }
        return linkedHashSet;
    }

    @NotNull
    public static final <T> Set<T> minus(@NotNull Set<? extends T> minus, @NotNull Sequence<? extends T> elements) {
        Intrinsics.checkParameterIsNotNull(minus, "$this$minus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        LinkedHashSet linkedHashSet = new LinkedHashSet(minus);
        CollectionsKt.removeAll(linkedHashSet, elements);
        return linkedHashSet;
    }

    @NotNull
    public static final <T> Set<T> minus(@NotNull Set<? extends T> minus, @NotNull T[] elements) {
        Intrinsics.checkParameterIsNotNull(minus, "$this$minus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        LinkedHashSet linkedHashSet = new LinkedHashSet(minus);
        CollectionsKt.removeAll(linkedHashSet, elements);
        return linkedHashSet;
    }

    @InlineOnly
    private static final <T> Set<T> minusElement(@NotNull Set<? extends T> set, T t) {
        return SetsKt.minus(set, t);
    }

    @NotNull
    public static final <T> Set<T> plus(@NotNull Set<? extends T> plus, @NotNull Iterable<? extends T> elements) {
        int size;
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        Integer numCollectionSizeOrNull = CollectionsKt.collectionSizeOrNull(elements);
        if (numCollectionSizeOrNull != null) {
            size = plus.size() + numCollectionSizeOrNull.intValue();
        } else {
            size = plus.size() * 2;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(size));
        linkedHashSet.addAll(plus);
        CollectionsKt.addAll(linkedHashSet, elements);
        return linkedHashSet;
    }

    @NotNull
    public static final <T> Set<T> plus(@NotNull Set<? extends T> plus, T t) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        LinkedHashSet linkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(plus.size() + 1));
        linkedHashSet.addAll(plus);
        linkedHashSet.add(t);
        return linkedHashSet;
    }

    @NotNull
    public static final <T> Set<T> plus(@NotNull Set<? extends T> plus, @NotNull Sequence<? extends T> elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        LinkedHashSet linkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(plus.size() * 2));
        linkedHashSet.addAll(plus);
        CollectionsKt.addAll(linkedHashSet, elements);
        return linkedHashSet;
    }

    @NotNull
    public static final <T> Set<T> plus(@NotNull Set<? extends T> plus, @NotNull T[] elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        LinkedHashSet linkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(plus.size() + elements.length));
        linkedHashSet.addAll(plus);
        CollectionsKt.addAll(linkedHashSet, elements);
        return linkedHashSet;
    }

    @InlineOnly
    private static final <T> Set<T> plusElement(@NotNull Set<? extends T> set, T t) {
        return SetsKt.plus(set, t);
    }
}
