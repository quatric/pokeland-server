package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: Clock.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SinceKotlin(version = "1.3")
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\b'\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u0004H&ø\u0001\u0000¢\u0006\u0002\u0010\u0005J\u0006\u0010\u0006\u001a\u00020\u0007J\u0006\u0010\b\u001a\u00020\u0007J\u001b\u0010\t\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0004H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\fJ\u001b\u0010\r\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0004H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\f\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"}, m1984d2 = {"Lkotlin/time/ClockMark;", "", "()V", "elapsedNow", "Lkotlin/time/Duration;", "()D", "hasNotPassedNow", "", "hasPassedNow", "minus", "duration", "minus-LRDsOJo", "(D)Lkotlin/time/ClockMark;", "plus", "plus-LRDsOJo", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
@ExperimentalTime
public abstract class ClockMark {
    public abstract double elapsedNow();

    public final boolean hasNotPassedNow() {
        return Duration.m2958isNegativeimpl(elapsedNow());
    }

    public final boolean hasPassedNow() {
        return !Duration.m2958isNegativeimpl(elapsedNow());
    }

    @NotNull
    /* JADX INFO: renamed from: minus-LRDsOJo, reason: not valid java name */
    public ClockMark m2934minusLRDsOJo(double duration) {
        return mo2933plusLRDsOJo(Duration.m2978unaryMinusimpl(duration));
    }

    @NotNull
    /* JADX INFO: renamed from: plus-LRDsOJo */
    public ClockMark mo2933plusLRDsOJo(double duration) {
        return new AdjustedClockMark(this, duration, null);
    }
}
