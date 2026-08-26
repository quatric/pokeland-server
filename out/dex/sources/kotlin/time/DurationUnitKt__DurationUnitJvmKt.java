package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: DurationUnitJvm.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u00052\n\u0010\u0006\u001a\u00060\u0004j\u0002`\u0005H\u0001*\u001e\b\u0007\u0010\u0007\"\u00020\u00042\u00020\u0004B\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\nB\u0002\b\u000b¨\u0006\f"}, m1984d2 = {"convertDurationUnit", "", "value", "sourceUnit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "targetUnit", "DurationUnit", "Lkotlin/SinceKotlin;", "version", "1.3", "Lkotlin/time/ExperimentalTime;", "kotlin-stdlib"}, m1985k = 5, m1986mv = {1, 1, 15}, m1988xi = 1, m1989xs = "kotlin/time/DurationUnitKt")
class DurationUnitKt__DurationUnitJvmKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void DurationUnit$annotations() {
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static final double convertDurationUnit(double d, @NotNull TimeUnit sourceUnit, @NotNull TimeUnit targetUnit) {
        Intrinsics.checkParameterIsNotNull(sourceUnit, "sourceUnit");
        Intrinsics.checkParameterIsNotNull(targetUnit, "targetUnit");
        long jConvert = targetUnit.convert(1L, sourceUnit);
        if (jConvert > 0) {
            double d2 = jConvert;
            Double.isNaN(d2);
            return d * d2;
        }
        double dConvert = sourceUnit.convert(1L, targetUnit);
        Double.isNaN(dConvert);
        return d / dConvert;
    }
}
