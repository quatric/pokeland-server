package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: SetsJVM.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000$\n\u0000\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001f\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002¢\u0006\u0002\u0010\u0004\u001a+\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u00022\u0012\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\b\"\u0002H\u0002¢\u0006\u0002\u0010\t\u001aG\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u00022\u001a\u0010\n\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u000bj\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\f2\u0012\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\b\"\u0002H\u0002¢\u0006\u0002\u0010\r¨\u0006\u000e"}, m1984d2 = {"setOf", "", "T", "element", "(Ljava/lang/Object;)Ljava/util/Set;", "sortedSetOf", "Ljava/util/TreeSet;", "elements", "", "([Ljava/lang/Object;)Ljava/util/TreeSet;", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/util/Comparator;[Ljava/lang/Object;)Ljava/util/TreeSet;", "kotlin-stdlib"}, m1985k = 5, m1986mv = {1, 1, 15}, m1988xi = 1, m1989xs = "kotlin/collections/SetsKt")
public class SetsKt__SetsJVMKt {
    @NotNull
    public static final <T> Set<T> setOf(T t) {
        Set<T> setSingleton = Collections.singleton(t);
        Intrinsics.checkExpressionValueIsNotNull(setSingleton, "java.util.Collections.singleton(element)");
        return setSingleton;
    }

    @NotNull
    public static final <T> TreeSet<T> sortedSetOf(@NotNull Comparator<? super T> comparator, @NotNull T... elements) {
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return (TreeSet) ArraysKt.toCollection(elements, new TreeSet(comparator));
    }

    @NotNull
    public static final <T> TreeSet<T> sortedSetOf(@NotNull T... elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return (TreeSet) ArraysKt.toCollection(elements, new TreeSet());
    }
}
