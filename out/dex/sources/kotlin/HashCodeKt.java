package kotlin;

import kotlin.internal.InlineOnly;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: HashCode.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\f\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0000\n\u0000\u001a\u000f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002H\u0087\b¨\u0006\u0003"}, m1984d2 = {"hashCode", "", "", "kotlin-stdlib"}, m1985k = 2, m1986mv = {1, 1, 15})
public final class HashCodeKt {
    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final int hashCode(@Nullable Object obj) {
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }
}
