package kotlin.text;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: _StringsJvm.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\f\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0010\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006*\u00020\u0002¨\u0006\u0007"}, m1984d2 = {"elementAt", "", "", FirebaseAnalytics.Param.INDEX, "", "toSortedSet", "Ljava/util/SortedSet;", "kotlin-stdlib"}, m1985k = 5, m1986mv = {1, 1, 15}, m1988xi = 1, m1989xs = "kotlin/text/StringsKt")
class StringsKt___StringsJvmKt extends StringsKt__StringsKt {
    @InlineOnly
    private static final char elementAt(@NotNull CharSequence charSequence, int i) {
        return charSequence.charAt(i);
    }

    @NotNull
    public static final SortedSet<Character> toSortedSet(@NotNull CharSequence toSortedSet) {
        Intrinsics.checkParameterIsNotNull(toSortedSet, "$this$toSortedSet");
        return (SortedSet) StringsKt.toCollection(toSortedSet, new TreeSet());
    }
}
