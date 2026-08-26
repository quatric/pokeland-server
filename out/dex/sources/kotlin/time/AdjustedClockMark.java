package kotlin.time;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: Clock.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\b\u0003\u0018\u00002\u00020\u0001B\u0018\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000¢\u0006\u0002\u0010\u0005J\u0010\u0010\u000b\u001a\u00020\u0004H\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u0007J\u001b\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u0004H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0010"}, m1984d2 = {"Lkotlin/time/AdjustedClockMark;", "Lkotlin/time/ClockMark;", "mark", "adjustment", "Lkotlin/time/Duration;", "(Lkotlin/time/ClockMark;DLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAdjustment", "()D", "D", "getMark", "()Lkotlin/time/ClockMark;", "elapsedNow", "plus", "duration", "plus-LRDsOJo", "(D)Lkotlin/time/ClockMark;", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
@ExperimentalTime
final class AdjustedClockMark extends ClockMark {
    private final double adjustment;

    @NotNull
    private final ClockMark mark;

    private AdjustedClockMark(ClockMark clockMark, double d) {
        this.mark = clockMark;
        this.adjustment = d;
    }

    public /* synthetic */ AdjustedClockMark(ClockMark clockMark, double d, DefaultConstructorMarker defaultConstructorMarker) {
        this(clockMark, d);
    }

    @Override // kotlin.time.ClockMark
    public double elapsedNow() {
        return Duration.m2960minusLRDsOJo(this.mark.elapsedNow(), this.adjustment);
    }

    public final double getAdjustment() {
        return this.adjustment;
    }

    @NotNull
    public final ClockMark getMark() {
        return this.mark;
    }

    @Override // kotlin.time.ClockMark
    @NotNull
    /* JADX INFO: renamed from: plus-LRDsOJo */
    public ClockMark mo2933plusLRDsOJo(double duration) {
        return new AdjustedClockMark(this.mark, Duration.m2961plusLRDsOJo(this.adjustment, duration), null);
    }
}
