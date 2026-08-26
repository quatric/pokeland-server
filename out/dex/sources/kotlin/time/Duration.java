package kotlin.time;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Duration.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SinceKotlin(version = "1.3")
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b&\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0012\b\u0087@\u0018\u0000 s2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001sB\u0014\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020\u0000H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b'\u0010(J\u001b\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u0003H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b+\u0010,J\u001b\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\tH\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b+\u0010-J\u001b\u0010)\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b.\u0010,J\u0013\u0010/\u001a\u0002002\b\u0010&\u001a\u0004\u0018\u000101HÖ\u0003J\t\u00102\u001a\u00020\tHÖ\u0001J\r\u00103\u001a\u000200¢\u0006\u0004\b4\u00105J\r\u00106\u001a\u000200¢\u0006\u0004\b7\u00105J\r\u00108\u001a\u000200¢\u0006\u0004\b9\u00105J\r\u0010:\u001a\u000200¢\u0006\u0004\b;\u00105J\u001b\u0010<\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b=\u0010,J\u001b\u0010>\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b?\u0010,J\u0017\u0010@\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002¢\u0006\u0004\bA\u0010(J\u001b\u0010B\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u0003H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\bC\u0010,J\u001b\u0010B\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\tH\u0086\u0002ø\u0001\u0000¢\u0006\u0004\bC\u0010-J\u008d\u0001\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E2u\u0010F\u001aq\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(J\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(K\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0GH\u0086\b¢\u0006\u0004\bO\u0010PJx\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E2`\u0010F\u001a\\\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(K\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0QH\u0086\b¢\u0006\u0004\bO\u0010RJc\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E2K\u0010F\u001aG\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0SH\u0086\b¢\u0006\u0004\bO\u0010TJN\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E26\u0010F\u001a2\u0012\u0013\u0012\u00110V¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0UH\u0086\b¢\u0006\u0004\bO\u0010WJ\u0019\u0010X\u001a\u00020\u00032\n\u0010Y\u001a\u00060Zj\u0002`[¢\u0006\u0004\b\\\u0010]J\u0019\u0010^\u001a\u00020\t2\n\u0010Y\u001a\u00060Zj\u0002`[¢\u0006\u0004\b_\u0010`J\r\u0010a\u001a\u00020b¢\u0006\u0004\bc\u0010dJ\u0019\u0010e\u001a\u00020V2\n\u0010Y\u001a\u00060Zj\u0002`[¢\u0006\u0004\bf\u0010gJ\r\u0010h\u001a\u00020V¢\u0006\u0004\bi\u0010jJ\r\u0010k\u001a\u00020V¢\u0006\u0004\bl\u0010jJ\u000f\u0010m\u001a\u00020bH\u0016¢\u0006\u0004\bn\u0010dJ#\u0010m\u001a\u00020b2\n\u0010Y\u001a\u00060Zj\u0002`[2\b\b\u0002\u0010o\u001a\u00020\t¢\u0006\u0004\bn\u0010pJ\u0013\u0010q\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\br\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00008Fø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u001a\u0010\b\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005R\u0011\u0010\u0010\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0005R\u0011\u0010\u0012\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0005R\u0011\u0010\u0014\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0005R\u0011\u0010\u0016\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0005R\u0011\u0010\u0018\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0005R\u0011\u0010\u001a\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0005R\u001a\u0010\u001c\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b\u001d\u0010\u000b\u001a\u0004\b\u001e\u0010\rR\u001a\u0010\u001f\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b \u0010\u000b\u001a\u0004\b!\u0010\rR\u001a\u0010\"\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b#\u0010\u000b\u001a\u0004\b$\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\u0002\n\u0000ø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006t"}, m1984d2 = {"Lkotlin/time/Duration;", "", "value", "", "constructor-impl", "(D)D", "absoluteValue", "getAbsoluteValue-impl", "hoursComponent", "", "hoursComponent$annotations", "()V", "getHoursComponent-impl", "(D)I", "inDays", "getInDays-impl", "inHours", "getInHours-impl", "inMicroseconds", "getInMicroseconds-impl", "inMilliseconds", "getInMilliseconds-impl", "inMinutes", "getInMinutes-impl", "inNanoseconds", "getInNanoseconds-impl", "inSeconds", "getInSeconds-impl", "minutesComponent", "minutesComponent$annotations", "getMinutesComponent-impl", "nanosecondsComponent", "nanosecondsComponent$annotations", "getNanosecondsComponent-impl", "secondsComponent", "secondsComponent$annotations", "getSecondsComponent-impl", "compareTo", "other", "compareTo-LRDsOJo", "(DD)I", "div", "scale", "div-impl", "(DD)D", "(DI)D", "div-LRDsOJo", "equals", "", "", "hashCode", "isFinite", "isFinite-impl", "(D)Z", "isInfinite", "isInfinite-impl", "isNegative", "isNegative-impl", "isPositive", "isPositive-impl", "minus", "minus-LRDsOJo", "plus", "plus-LRDsOJo", "precision", "precision-impl", "times", "times-impl", "toComponents", "T", "action", "Lkotlin/Function5;", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "days", "hours", "minutes", "seconds", "nanoseconds", "toComponents-impl", "(DLkotlin/jvm/functions/Function5;)Ljava/lang/Object;", "Lkotlin/Function4;", "(DLkotlin/jvm/functions/Function4;)Ljava/lang/Object;", "Lkotlin/Function3;", "(DLkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "Lkotlin/Function2;", "", "(DLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "toDouble", "unit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "toDouble-impl", "(DLjava/util/concurrent/TimeUnit;)D", "toInt", "toInt-impl", "(DLjava/util/concurrent/TimeUnit;)I", "toIsoString", "", "toIsoString-impl", "(D)Ljava/lang/String;", "toLong", "toLong-impl", "(DLjava/util/concurrent/TimeUnit;)J", "toLongMilliseconds", "toLongMilliseconds-impl", "(D)J", "toLongNanoseconds", "toLongNanoseconds-impl", "toString", "toString-impl", "decimals", "(DLjava/util/concurrent/TimeUnit;I)Ljava/lang/String;", "unaryMinus", "unaryMinus-impl", "Companion", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
@ExperimentalTime
public final class Duration implements Comparable<Duration> {
    private final double value;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final double ZERO = m2937constructorimpl(0.0d);
    private static final double INFINITE = m2937constructorimpl(DoubleCompanionObject.INSTANCE.getPOSITIVE_INFINITY());

    /* JADX INFO: compiled from: Duration.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\n\u0010\u0010\u001a\u00060\u000ej\u0002`\u000fR\u0016\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\b\u001a\u00020\u0004ø\u0001\u0000¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\t\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0011"}, m1984d2 = {"Lkotlin/time/Duration$Companion;", "", "()V", "INFINITE", "Lkotlin/time/Duration;", "getINFINITE", "()D", "D", "ZERO", "getZERO", "convert", "", "value", "sourceUnit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "targetUnit", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final double convert(double value, @NotNull TimeUnit sourceUnit, @NotNull TimeUnit targetUnit) {
            Intrinsics.checkParameterIsNotNull(sourceUnit, "sourceUnit");
            Intrinsics.checkParameterIsNotNull(targetUnit, "targetUnit");
            return DurationUnitKt.convertDurationUnit(value, sourceUnit, targetUnit);
        }

        public final double getINFINITE() {
            return Duration.INFINITE;
        }

        public final double getZERO() {
            return Duration.ZERO;
        }
    }

    private /* synthetic */ Duration(double d) {
        this.value = d;
    }

    @NotNull
    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ Duration m2935boximpl(double d) {
        return new Duration(d);
    }

    /* JADX INFO: renamed from: compareTo-LRDsOJo, reason: not valid java name */
    public static int m2936compareToLRDsOJo(double d, double d2) {
        return Double.compare(d, d2);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static double m2937constructorimpl(double d) {
        return d;
    }

    /* JADX INFO: renamed from: div-LRDsOJo, reason: not valid java name */
    public static final double m2938divLRDsOJo(double d, double d2) {
        return d / d2;
    }

    /* JADX INFO: renamed from: div-impl, reason: not valid java name */
    public static final double m2939divimpl(double d, double d2) {
        return m2937constructorimpl(d / d2);
    }

    /* JADX INFO: renamed from: div-impl, reason: not valid java name */
    public static final double m2940divimpl(double d, int i) {
        double d2 = i;
        Double.isNaN(d2);
        return m2937constructorimpl(d / d2);
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m2941equalsimpl(double d, @Nullable Object obj) {
        return (obj instanceof Duration) && Double.compare(d, ((Duration) obj).getValue()) == 0;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2942equalsimpl0(double d, double d2) {
        throw null;
    }

    /* JADX INFO: renamed from: getAbsoluteValue-impl, reason: not valid java name */
    public static final double m2943getAbsoluteValueimpl(double d) {
        return m2958isNegativeimpl(d) ? m2978unaryMinusimpl(d) : d;
    }

    /* JADX INFO: renamed from: getHoursComponent-impl, reason: not valid java name */
    public static final int m2944getHoursComponentimpl(double d) {
        double dM2946getInHoursimpl = m2946getInHoursimpl(d);
        double d2 = 24;
        Double.isNaN(d2);
        return (int) (dM2946getInHoursimpl % d2);
    }

    /* JADX INFO: renamed from: getInDays-impl, reason: not valid java name */
    public static final double m2945getInDaysimpl(double d) {
        return m2969toDoubleimpl(d, TimeUnit.DAYS);
    }

    /* JADX INFO: renamed from: getInHours-impl, reason: not valid java name */
    public static final double m2946getInHoursimpl(double d) {
        return m2969toDoubleimpl(d, TimeUnit.HOURS);
    }

    /* JADX INFO: renamed from: getInMicroseconds-impl, reason: not valid java name */
    public static final double m2947getInMicrosecondsimpl(double d) {
        return m2969toDoubleimpl(d, TimeUnit.MICROSECONDS);
    }

    /* JADX INFO: renamed from: getInMilliseconds-impl, reason: not valid java name */
    public static final double m2948getInMillisecondsimpl(double d) {
        return m2969toDoubleimpl(d, TimeUnit.MILLISECONDS);
    }

    /* JADX INFO: renamed from: getInMinutes-impl, reason: not valid java name */
    public static final double m2949getInMinutesimpl(double d) {
        return m2969toDoubleimpl(d, TimeUnit.MINUTES);
    }

    /* JADX INFO: renamed from: getInNanoseconds-impl, reason: not valid java name */
    public static final double m2950getInNanosecondsimpl(double d) {
        return m2969toDoubleimpl(d, TimeUnit.NANOSECONDS);
    }

    /* JADX INFO: renamed from: getInSeconds-impl, reason: not valid java name */
    public static final double m2951getInSecondsimpl(double d) {
        return m2969toDoubleimpl(d, TimeUnit.SECONDS);
    }

    /* JADX INFO: renamed from: getMinutesComponent-impl, reason: not valid java name */
    public static final int m2952getMinutesComponentimpl(double d) {
        double dM2949getInMinutesimpl = m2949getInMinutesimpl(d);
        double d2 = 60;
        Double.isNaN(d2);
        return (int) (dM2949getInMinutesimpl % d2);
    }

    /* JADX INFO: renamed from: getNanosecondsComponent-impl, reason: not valid java name */
    public static final int m2953getNanosecondsComponentimpl(double d) {
        return (int) (m2950getInNanosecondsimpl(d) % 1.0E9d);
    }

    /* JADX INFO: renamed from: getSecondsComponent-impl, reason: not valid java name */
    public static final int m2954getSecondsComponentimpl(double d) {
        double dM2951getInSecondsimpl = m2951getInSecondsimpl(d);
        double d2 = 60;
        Double.isNaN(d2);
        return (int) (dM2951getInSecondsimpl % d2);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m2955hashCodeimpl(double d) {
        long jDoubleToLongBits = Double.doubleToLongBits(d);
        return (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
    }

    @PublishedApi
    public static /* synthetic */ void hoursComponent$annotations() {
    }

    /* JADX INFO: renamed from: isFinite-impl, reason: not valid java name */
    public static final boolean m2956isFiniteimpl(double d) {
        return (Double.isInfinite(d) || Double.isNaN(d)) ? false : true;
    }

    /* JADX INFO: renamed from: isInfinite-impl, reason: not valid java name */
    public static final boolean m2957isInfiniteimpl(double d) {
        return Double.isInfinite(d);
    }

    /* JADX INFO: renamed from: isNegative-impl, reason: not valid java name */
    public static final boolean m2958isNegativeimpl(double d) {
        return d < ((double) 0);
    }

    /* JADX INFO: renamed from: isPositive-impl, reason: not valid java name */
    public static final boolean m2959isPositiveimpl(double d) {
        return d > ((double) 0);
    }

    /* JADX INFO: renamed from: minus-LRDsOJo, reason: not valid java name */
    public static final double m2960minusLRDsOJo(double d, double d2) {
        return m2937constructorimpl(d - d2);
    }

    @PublishedApi
    public static /* synthetic */ void minutesComponent$annotations() {
    }

    @PublishedApi
    public static /* synthetic */ void nanosecondsComponent$annotations() {
    }

    /* JADX INFO: renamed from: plus-LRDsOJo, reason: not valid java name */
    public static final double m2961plusLRDsOJo(double d, double d2) {
        return m2937constructorimpl(d + d2);
    }

    /* JADX INFO: renamed from: precision-impl, reason: not valid java name */
    private static final int m2962precisionimpl(double d, double d2) {
        if (d2 < 1) {
            return 3;
        }
        if (d2 < 10) {
            return 2;
        }
        return d2 < ((double) 100) ? 1 : 0;
    }

    @PublishedApi
    public static /* synthetic */ void secondsComponent$annotations() {
    }

    /* JADX INFO: renamed from: times-impl, reason: not valid java name */
    public static final double m2963timesimpl(double d, double d2) {
        return m2937constructorimpl(d * d2);
    }

    /* JADX INFO: renamed from: times-impl, reason: not valid java name */
    public static final double m2964timesimpl(double d, int i) {
        double d2 = i;
        Double.isNaN(d2);
        return m2937constructorimpl(d * d2);
    }

    /* JADX INFO: renamed from: toComponents-impl, reason: not valid java name */
    public static final <T> T m2965toComponentsimpl(double d, @NotNull Function2<? super Long, ? super Integer, ? extends T> action) {
        Intrinsics.checkParameterIsNotNull(action, "action");
        return action.invoke(Long.valueOf((long) m2951getInSecondsimpl(d)), Integer.valueOf(m2953getNanosecondsComponentimpl(d)));
    }

    /* JADX INFO: renamed from: toComponents-impl, reason: not valid java name */
    public static final <T> T m2966toComponentsimpl(double d, @NotNull Function3<? super Integer, ? super Integer, ? super Integer, ? extends T> action) {
        Intrinsics.checkParameterIsNotNull(action, "action");
        return action.invoke(Integer.valueOf((int) m2949getInMinutesimpl(d)), Integer.valueOf(m2954getSecondsComponentimpl(d)), Integer.valueOf(m2953getNanosecondsComponentimpl(d)));
    }

    /* JADX INFO: renamed from: toComponents-impl, reason: not valid java name */
    public static final <T> T m2967toComponentsimpl(double d, @NotNull Function4<? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends T> action) {
        Intrinsics.checkParameterIsNotNull(action, "action");
        return action.invoke(Integer.valueOf((int) m2946getInHoursimpl(d)), Integer.valueOf(m2952getMinutesComponentimpl(d)), Integer.valueOf(m2954getSecondsComponentimpl(d)), Integer.valueOf(m2953getNanosecondsComponentimpl(d)));
    }

    /* JADX INFO: renamed from: toComponents-impl, reason: not valid java name */
    public static final <T> T m2968toComponentsimpl(double d, @NotNull Function5<? super Integer, ? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends T> action) {
        Intrinsics.checkParameterIsNotNull(action, "action");
        return action.invoke(Integer.valueOf((int) m2945getInDaysimpl(d)), Integer.valueOf(m2944getHoursComponentimpl(d)), Integer.valueOf(m2952getMinutesComponentimpl(d)), Integer.valueOf(m2954getSecondsComponentimpl(d)), Integer.valueOf(m2953getNanosecondsComponentimpl(d)));
    }

    /* JADX INFO: renamed from: toDouble-impl, reason: not valid java name */
    public static final double m2969toDoubleimpl(double d, @NotNull TimeUnit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        return DurationUnitKt.convertDurationUnit(d, DurationKt.getStorageUnit(), unit);
    }

    /* JADX INFO: renamed from: toInt-impl, reason: not valid java name */
    public static final int m2970toIntimpl(double d, @NotNull TimeUnit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        return (int) m2969toDoubleimpl(d, unit);
    }

    @NotNull
    /* JADX INFO: renamed from: toIsoString-impl, reason: not valid java name */
    public static final String m2971toIsoStringimpl(double d) {
        StringBuilder sb = new StringBuilder();
        if (m2958isNegativeimpl(d)) {
            sb.append('-');
        }
        sb.append("PT");
        double dM2943getAbsoluteValueimpl = m2943getAbsoluteValueimpl(d);
        int iM2946getInHoursimpl = (int) m2946getInHoursimpl(dM2943getAbsoluteValueimpl);
        int iM2952getMinutesComponentimpl = m2952getMinutesComponentimpl(dM2943getAbsoluteValueimpl);
        int iM2954getSecondsComponentimpl = m2954getSecondsComponentimpl(dM2943getAbsoluteValueimpl);
        int iM2953getNanosecondsComponentimpl = m2953getNanosecondsComponentimpl(dM2943getAbsoluteValueimpl);
        boolean z = true;
        boolean z2 = iM2946getInHoursimpl != 0;
        boolean z3 = (iM2954getSecondsComponentimpl == 0 && iM2953getNanosecondsComponentimpl == 0) ? false : true;
        if (iM2952getMinutesComponentimpl == 0 && (!z3 || !z2)) {
            z = false;
        }
        if (z2) {
            sb.append(iM2946getInHoursimpl);
            sb.append('H');
        }
        if (z) {
            sb.append(iM2952getMinutesComponentimpl);
            sb.append('M');
        }
        if (z3 || (!z2 && !z)) {
            sb.append(iM2954getSecondsComponentimpl);
            if (iM2953getNanosecondsComponentimpl != 0) {
                sb.append('.');
                String strPadStart = StringsKt.padStart(String.valueOf(iM2953getNanosecondsComponentimpl), 9, '0');
                if (iM2953getNanosecondsComponentimpl % 1000000 == 0) {
                    sb.append((CharSequence) strPadStart, 0, 3);
                } else if (iM2953getNanosecondsComponentimpl % 1000 == 0) {
                    sb.append((CharSequence) strPadStart, 0, 6);
                } else {
                    sb.append(strPadStart);
                }
            }
            sb.append('S');
        }
        String string = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    /* JADX INFO: renamed from: toLong-impl, reason: not valid java name */
    public static final long m2972toLongimpl(double d, @NotNull TimeUnit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        return (long) m2969toDoubleimpl(d, unit);
    }

    /* JADX INFO: renamed from: toLongMilliseconds-impl, reason: not valid java name */
    public static final long m2973toLongMillisecondsimpl(double d) {
        return m2972toLongimpl(d, TimeUnit.MILLISECONDS);
    }

    /* JADX INFO: renamed from: toLongNanoseconds-impl, reason: not valid java name */
    public static final long m2974toLongNanosecondsimpl(double d) {
        return m2972toLongimpl(d, TimeUnit.NANOSECONDS);
    }

    /* JADX WARN: Code duplicated, block: B:40:0x009a  */
    /* JADX WARN: Code duplicated, block: B:41:0x009f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:42:0x00a1  */
    /* JADX WARN: Code duplicated, block: B:43:0x00a6  */
    @NotNull
    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m2975toStringimpl(double d) {
        TimeUnit timeUnit;
        int i;
        double dM2969toDoubleimpl;
        String toExactDecimals;
        if (m2957isInfiniteimpl(d)) {
            return String.valueOf(d);
        }
        if (d == 0.0d) {
            return "0s";
        }
        double dM2950getInNanosecondsimpl = m2950getInNanosecondsimpl(m2943getAbsoluteValueimpl(d));
        boolean z = false;
        if (dM2950getInNanosecondsimpl >= 1.0E-6d) {
            if (dM2950getInNanosecondsimpl < 1) {
                timeUnit = TimeUnit.NANOSECONDS;
                i = 7;
            } else {
                if (dM2950getInNanosecondsimpl < 1000.0d) {
                    timeUnit = TimeUnit.NANOSECONDS;
                } else if (dM2950getInNanosecondsimpl < 1000000.0d) {
                    timeUnit = TimeUnit.MICROSECONDS;
                } else if (dM2950getInNanosecondsimpl < 1.0E9d) {
                    timeUnit = TimeUnit.MILLISECONDS;
                } else if (dM2950getInNanosecondsimpl < 1.0E12d) {
                    timeUnit = TimeUnit.SECONDS;
                } else if (dM2950getInNanosecondsimpl < 6.0E13d) {
                    timeUnit = TimeUnit.MINUTES;
                } else if (dM2950getInNanosecondsimpl < 3.6E15d) {
                    timeUnit = TimeUnit.HOURS;
                } else if (dM2950getInNanosecondsimpl < 8.64E20d) {
                    timeUnit = TimeUnit.DAYS;
                } else {
                    timeUnit = TimeUnit.DAYS;
                }
                i = 0;
            }
            dM2969toDoubleimpl = m2969toDoubleimpl(d, timeUnit);
            StringBuilder sb = new StringBuilder();
            if (z) {
                toExactDecimals = FormatToDecimalsKt.formatScientific(dM2969toDoubleimpl);
            } else if (i > 0) {
                toExactDecimals = FormatToDecimalsKt.formatUpToDecimals(dM2969toDoubleimpl, i);
            } else {
                toExactDecimals = FormatToDecimalsKt.formatToExactDecimals(dM2969toDoubleimpl, m2962precisionimpl(d, Math.abs(dM2969toDoubleimpl)));
            }
            sb.append(toExactDecimals);
            sb.append(DurationUnitKt.shortName(timeUnit));
            return sb.toString();
        }
        timeUnit = TimeUnit.SECONDS;
        i = 0;
        z = true;
        dM2969toDoubleimpl = m2969toDoubleimpl(d, timeUnit);
        StringBuilder sb2 = new StringBuilder();
        if (z) {
            toExactDecimals = FormatToDecimalsKt.formatScientific(dM2969toDoubleimpl);
        } else if (i > 0) {
            toExactDecimals = FormatToDecimalsKt.formatUpToDecimals(dM2969toDoubleimpl, i);
        } else {
            toExactDecimals = FormatToDecimalsKt.formatToExactDecimals(dM2969toDoubleimpl, m2962precisionimpl(d, Math.abs(dM2969toDoubleimpl)));
        }
        sb2.append(toExactDecimals);
        sb2.append(DurationUnitKt.shortName(timeUnit));
        return sb2.toString();
    }

    @NotNull
    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static final String m2976toStringimpl(double d, @NotNull TimeUnit unit, int i) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("decimals must be not negative, but was " + i).toString());
        }
        if (m2957isInfiniteimpl(d)) {
            return String.valueOf(d);
        }
        double dM2969toDoubleimpl = m2969toDoubleimpl(d, unit);
        StringBuilder sb = new StringBuilder();
        sb.append(Math.abs(dM2969toDoubleimpl) < 1.0E14d ? FormatToDecimalsKt.formatToExactDecimals(dM2969toDoubleimpl, RangesKt.coerceAtMost(i, 12)) : FormatToDecimalsKt.formatScientific(dM2969toDoubleimpl));
        sb.append(DurationUnitKt.shortName(unit));
        return sb.toString();
    }

    /* JADX INFO: renamed from: toString-impl$default, reason: not valid java name */
    public static /* synthetic */ String m2977toStringimpl$default(double d, TimeUnit timeUnit, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return m2976toStringimpl(d, timeUnit, i);
    }

    /* JADX INFO: renamed from: unaryMinus-impl, reason: not valid java name */
    public static final double m2978unaryMinusimpl(double d) {
        return m2937constructorimpl(-d);
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Duration duration) {
        return m2979compareToLRDsOJo(duration.getValue());
    }

    /* JADX INFO: renamed from: compareTo-LRDsOJo, reason: not valid java name */
    public int m2979compareToLRDsOJo(double d) {
        return m2936compareToLRDsOJo(this.value, d);
    }

    public boolean equals(Object other) {
        return m2941equalsimpl(this.value, other);
    }

    public int hashCode() {
        return m2955hashCodeimpl(this.value);
    }

    @NotNull
    public String toString() {
        return m2975toStringimpl(this.value);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ double getValue() {
        return this.value;
    }
}
