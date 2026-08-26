package kotlin.system;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: Timing.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a\u0017\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0086\b\u001a\u0017\u0010\u0005\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0086\b¨\u0006\u0006"}, m1984d2 = {"measureNanoTime", "", "block", "Lkotlin/Function0;", "", "measureTimeMillis", "kotlin-stdlib"}, m1985k = 2, m1986mv = {1, 1, 15})
@JvmName(name = "TimingKt")
public final class TimingKt {
    public static final long measureNanoTime(@NotNull Function0<Unit> block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        long jNanoTime = System.nanoTime();
        block.invoke();
        return System.nanoTime() - jNanoTime;
    }

    public static final long measureTimeMillis(@NotNull Function0<Unit> block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        long jCurrentTimeMillis = System.currentTimeMillis();
        block.invoke();
        return System.currentTimeMillis() - jCurrentTimeMillis;
    }
}
