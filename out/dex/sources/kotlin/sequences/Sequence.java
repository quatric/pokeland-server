package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: Sequence.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010(\n\u0000\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H¦\u0002¨\u0006\u0005"}, m1984d2 = {"Lkotlin/sequences/Sequence;", "T", "", "iterator", "", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
public interface Sequence<T> {
    @NotNull
    Iterator<T> iterator();
}
