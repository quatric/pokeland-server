package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: compiled from: Clocks.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SinceKotlin(version = "1.3")
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002ø\u0001\u0000¢\u0006\u0004\b\t\u0010\nJ\u001b\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\nJ\b\u0010\r\u001a\u00020\u0004H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, m1984d2 = {"Lkotlin/time/TestClock;", "Lkotlin/time/AbstractLongClock;", "()V", "reading", "", "overflow", "", "duration", "Lkotlin/time/Duration;", "overflow-LRDsOJo", "(D)V", "plusAssign", "plusAssign-LRDsOJo", "read", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
@ExperimentalTime
public final class TestClock extends AbstractLongClock {
    private long reading;

    public TestClock() {
        super(TimeUnit.NANOSECONDS);
    }

    /* JADX INFO: renamed from: overflow-LRDsOJo, reason: not valid java name */
    private final void m2983overflowLRDsOJo(double duration) {
        throw new IllegalStateException("TestClock will overflow if its reading " + this.reading + "ns is advanced by " + Duration.m2975toStringimpl(duration) + '.');
    }

    /* JADX INFO: renamed from: plusAssign-LRDsOJo, reason: not valid java name */
    public final void m2984plusAssignLRDsOJo(double duration) {
        long j;
        double dM2969toDoubleimpl = Duration.m2969toDoubleimpl(duration, getUnit());
        long j2 = (long) dM2969toDoubleimpl;
        if (j2 == Long.MIN_VALUE || j2 == LongCompanionObject.MAX_VALUE) {
            double d = this.reading;
            Double.isNaN(d);
            double d2 = d + dM2969toDoubleimpl;
            if (d2 > LongCompanionObject.MAX_VALUE || d2 < Long.MIN_VALUE) {
                m2983overflowLRDsOJo(duration);
            }
            j = (long) d2;
        } else {
            long j3 = this.reading;
            j = j3 + j2;
            if ((j2 ^ j3) >= 0 && (j3 ^ j) < 0) {
                m2983overflowLRDsOJo(duration);
            }
        }
        this.reading = j;
    }

    @Override // kotlin.time.AbstractLongClock
    /* JADX INFO: renamed from: read, reason: from getter */
    protected long getReading() {
        return this.reading;
    }
}
