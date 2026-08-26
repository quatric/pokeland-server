package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: MonoClock.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SinceKotlin(version = "1.3")
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\bÇ\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0014J\b\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, m1984d2 = {"Lkotlin/time/MonoClock;", "Lkotlin/time/AbstractLongClock;", "Lkotlin/time/Clock;", "()V", "read", "", "toString", "", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
@ExperimentalTime
public final class MonoClock extends AbstractLongClock implements Clock {
    public static final MonoClock INSTANCE = new MonoClock();

    private MonoClock() {
        super(TimeUnit.NANOSECONDS);
    }

    @Override // kotlin.time.AbstractLongClock
    protected long read() {
        return System.nanoTime();
    }

    @NotNull
    public String toString() {
        return "Clock(System.nanoTime())";
    }
}
