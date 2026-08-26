package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: UShort.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SinceKotlin(version = "1.3")
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\n\n\u0002\b\t\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001fB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\u0097\nø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u0010J\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u0013J\u001b\u0010\u001b\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b \u0010\u0018J\u0013\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#HÖ\u0003J\t\u0010$\u001a\u00020\rHÖ\u0001J\u0013\u0010%\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b&\u0010\u0005J\u0013\u0010'\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b(\u0010\u0005J\u001b\u0010)\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b*\u0010\u0010J\u001b\u0010)\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b+\u0010\u0013J\u001b\u0010)\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b,\u0010\u001fJ\u001b\u0010)\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b-\u0010\u0018J\u001b\u0010.\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b/\u0010\u000bJ\u001b\u00100\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b1\u0010\u0010J\u001b\u00100\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b2\u0010\u0013J\u001b\u00100\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b3\u0010\u001fJ\u001b\u00100\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b4\u0010\u0018J\u001b\u00105\u001a\u0002062\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b7\u00108J\u001b\u00109\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b:\u0010\u0010J\u001b\u00109\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b;\u0010\u0013J\u001b\u00109\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b<\u0010\u001fJ\u001b\u00109\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b=\u0010\u0018J\u001b\u0010>\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b?\u0010\u0010J\u001b\u0010>\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b@\u0010\u0013J\u001b\u0010>\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\bA\u0010\u001fJ\u001b\u0010>\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bB\u0010\u0018J\u0010\u0010C\u001a\u00020DH\u0087\b¢\u0006\u0004\bE\u0010FJ\u0010\u0010G\u001a\u00020HH\u0087\b¢\u0006\u0004\bI\u0010JJ\u0010\u0010K\u001a\u00020LH\u0087\b¢\u0006\u0004\bM\u0010NJ\u0010\u0010O\u001a\u00020\rH\u0087\b¢\u0006\u0004\bP\u0010QJ\u0010\u0010R\u001a\u00020SH\u0087\b¢\u0006\u0004\bT\u0010UJ\u0010\u0010V\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bW\u0010\u0005J\u000f\u0010X\u001a\u00020YH\u0016¢\u0006\u0004\bZ\u0010[J\u0013\u0010\\\u001a\u00020\u000eH\u0087\bø\u0001\u0000¢\u0006\u0004\b]\u0010FJ\u0013\u0010^\u001a\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\b_\u0010QJ\u0013\u0010`\u001a\u00020\u0014H\u0087\bø\u0001\u0000¢\u0006\u0004\ba\u0010UJ\u0013\u0010b\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\bc\u0010\u0005J\u001b\u0010d\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\be\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006g"}, m1984d2 = {"Lkotlin/UShort;", "", "data", "", "constructor-impl", "(S)S", "data$annotations", "()V", "and", "other", "and-xj2QHRw", "(SS)S", "compareTo", "", "Lkotlin/UByte;", "compareTo-7apg3OU", "(SB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(SI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(SJ)I", "compareTo-xj2QHRw", "(SS)I", "dec", "dec-impl", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(SJ)J", "div-xj2QHRw", "equals", "", "", "hashCode", "inc", "inc-impl", "inv", "inv-impl", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "or", "or-xj2QHRw", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-xj2QHRw", "(SS)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(S)B", "toDouble", "", "toDouble-impl", "(S)D", "toFloat", "", "toFloat-impl", "(S)F", "toInt", "toInt-impl", "(S)I", "toLong", "", "toLong-impl", "(S)J", "toShort", "toShort-impl", "toString", "", "toString-impl", "(S)Ljava/lang/String;", "toUByte", "toUByte-impl", "toUInt", "toUInt-impl", "toULong", "toULong-impl", "toUShort", "toUShort-impl", "xor", "xor-xj2QHRw", "Companion", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
@ExperimentalUnsignedTypes
public final class UShort implements Comparable<UShort> {
    public static final short MAX_VALUE = -1;
    public static final short MIN_VALUE = 0;
    public static final int SIZE_BITS = 16;
    public static final int SIZE_BYTES = 2;
    private final short data;

    @PublishedApi
    private /* synthetic */ UShort(short s) {
        this.data = s;
    }

    @InlineOnly
    /* JADX INFO: renamed from: and-xj2QHRw, reason: not valid java name */
    private static final short m2259andxj2QHRw(short s, short s2) {
        return m2266constructorimpl((short) (s & s2));
    }

    @NotNull
    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ UShort m2260boximpl(short s) {
        return new UShort(s);
    }

    @InlineOnly
    /* JADX INFO: renamed from: compareTo-7apg3OU, reason: not valid java name */
    private static final int m2261compareTo7apg3OU(short s, byte b) {
        return Intrinsics.compare(s & MAX_VALUE, b & 255);
    }

    @InlineOnly
    /* JADX INFO: renamed from: compareTo-VKZWuLQ, reason: not valid java name */
    private static final int m2262compareToVKZWuLQ(short s, long j) {
        return UnsignedKt.ulongCompare(ULong.m2169constructorimpl(((long) s) & 65535), j);
    }

    @InlineOnly
    /* JADX INFO: renamed from: compareTo-WZ4Q5Ns, reason: not valid java name */
    private static final int m2263compareToWZ4Q5Ns(short s, int i) {
        return UnsignedKt.uintCompare(UInt.m2100constructorimpl(s & MAX_VALUE), i);
    }

    @InlineOnly
    /* JADX INFO: renamed from: compareTo-xj2QHRw, reason: not valid java name */
    private int m2264compareToxj2QHRw(short s) {
        return m2265compareToxj2QHRw(this.data, s);
    }

    @InlineOnly
    /* JADX INFO: renamed from: compareTo-xj2QHRw, reason: not valid java name */
    private static int m2265compareToxj2QHRw(short s, short s2) {
        return Intrinsics.compare(s & MAX_VALUE, s2 & MAX_VALUE);
    }

    @PublishedApi
    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static short m2266constructorimpl(short s) {
        return s;
    }

    @PublishedApi
    public static /* synthetic */ void data$annotations() {
    }

    @InlineOnly
    /* JADX INFO: renamed from: dec-impl, reason: not valid java name */
    private static final short m2267decimpl(short s) {
        return m2266constructorimpl((short) (s - 1));
    }

    @InlineOnly
    /* JADX INFO: renamed from: div-7apg3OU, reason: not valid java name */
    private static final int m2268div7apg3OU(short s, byte b) {
        return UnsignedKt.m2326uintDivideJ1ME1BU(UInt.m2100constructorimpl(s & MAX_VALUE), UInt.m2100constructorimpl(b & 255));
    }

    @InlineOnly
    /* JADX INFO: renamed from: div-VKZWuLQ, reason: not valid java name */
    private static final long m2269divVKZWuLQ(short s, long j) {
        return UnsignedKt.m2328ulongDivideeb3DHEI(ULong.m2169constructorimpl(((long) s) & 65535), j);
    }

    @InlineOnly
    /* JADX INFO: renamed from: div-WZ4Q5Ns, reason: not valid java name */
    private static final int m2270divWZ4Q5Ns(short s, int i) {
        return UnsignedKt.m2326uintDivideJ1ME1BU(UInt.m2100constructorimpl(s & MAX_VALUE), i);
    }

    @InlineOnly
    /* JADX INFO: renamed from: div-xj2QHRw, reason: not valid java name */
    private static final int m2271divxj2QHRw(short s, short s2) {
        return UnsignedKt.m2326uintDivideJ1ME1BU(UInt.m2100constructorimpl(s & MAX_VALUE), UInt.m2100constructorimpl(s2 & MAX_VALUE));
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m2272equalsimpl(short s, @Nullable Object obj) {
        if (obj instanceof UShort) {
            if (s == ((UShort) obj).getData()) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2273equalsimpl0(short s, short s2) {
        throw null;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m2274hashCodeimpl(short s) {
        return s;
    }

    @InlineOnly
    /* JADX INFO: renamed from: inc-impl, reason: not valid java name */
    private static final short m2275incimpl(short s) {
        return m2266constructorimpl((short) (s + 1));
    }

    @InlineOnly
    /* JADX INFO: renamed from: inv-impl, reason: not valid java name */
    private static final short m2276invimpl(short s) {
        return m2266constructorimpl((short) (s ^ (-1)));
    }

    @InlineOnly
    /* JADX INFO: renamed from: minus-7apg3OU, reason: not valid java name */
    private static final int m2277minus7apg3OU(short s, byte b) {
        return UInt.m2100constructorimpl(UInt.m2100constructorimpl(s & MAX_VALUE) - UInt.m2100constructorimpl(b & 255));
    }

    @InlineOnly
    /* JADX INFO: renamed from: minus-VKZWuLQ, reason: not valid java name */
    private static final long m2278minusVKZWuLQ(short s, long j) {
        return ULong.m2169constructorimpl(ULong.m2169constructorimpl(((long) s) & 65535) - j);
    }

    @InlineOnly
    /* JADX INFO: renamed from: minus-WZ4Q5Ns, reason: not valid java name */
    private static final int m2279minusWZ4Q5Ns(short s, int i) {
        return UInt.m2100constructorimpl(UInt.m2100constructorimpl(s & MAX_VALUE) - i);
    }

    @InlineOnly
    /* JADX INFO: renamed from: minus-xj2QHRw, reason: not valid java name */
    private static final int m2280minusxj2QHRw(short s, short s2) {
        return UInt.m2100constructorimpl(UInt.m2100constructorimpl(s & MAX_VALUE) - UInt.m2100constructorimpl(s2 & MAX_VALUE));
    }

    @InlineOnly
    /* JADX INFO: renamed from: or-xj2QHRw, reason: not valid java name */
    private static final short m2281orxj2QHRw(short s, short s2) {
        return m2266constructorimpl((short) (s | s2));
    }

    @InlineOnly
    /* JADX INFO: renamed from: plus-7apg3OU, reason: not valid java name */
    private static final int m2282plus7apg3OU(short s, byte b) {
        return UInt.m2100constructorimpl(UInt.m2100constructorimpl(s & MAX_VALUE) + UInt.m2100constructorimpl(b & 255));
    }

    @InlineOnly
    /* JADX INFO: renamed from: plus-VKZWuLQ, reason: not valid java name */
    private static final long m2283plusVKZWuLQ(short s, long j) {
        return ULong.m2169constructorimpl(ULong.m2169constructorimpl(((long) s) & 65535) + j);
    }

    @InlineOnly
    /* JADX INFO: renamed from: plus-WZ4Q5Ns, reason: not valid java name */
    private static final int m2284plusWZ4Q5Ns(short s, int i) {
        return UInt.m2100constructorimpl(UInt.m2100constructorimpl(s & MAX_VALUE) + i);
    }

    @InlineOnly
    /* JADX INFO: renamed from: plus-xj2QHRw, reason: not valid java name */
    private static final int m2285plusxj2QHRw(short s, short s2) {
        return UInt.m2100constructorimpl(UInt.m2100constructorimpl(s & MAX_VALUE) + UInt.m2100constructorimpl(s2 & MAX_VALUE));
    }

    @InlineOnly
    /* JADX INFO: renamed from: rangeTo-xj2QHRw, reason: not valid java name */
    private static final UIntRange m2286rangeToxj2QHRw(short s, short s2) {
        return new UIntRange(UInt.m2100constructorimpl(s & MAX_VALUE), UInt.m2100constructorimpl(s2 & MAX_VALUE), null);
    }

    @InlineOnly
    /* JADX INFO: renamed from: rem-7apg3OU, reason: not valid java name */
    private static final int m2287rem7apg3OU(short s, byte b) {
        return UnsignedKt.m2327uintRemainderJ1ME1BU(UInt.m2100constructorimpl(s & MAX_VALUE), UInt.m2100constructorimpl(b & 255));
    }

    @InlineOnly
    /* JADX INFO: renamed from: rem-VKZWuLQ, reason: not valid java name */
    private static final long m2288remVKZWuLQ(short s, long j) {
        return UnsignedKt.m2329ulongRemaindereb3DHEI(ULong.m2169constructorimpl(((long) s) & 65535), j);
    }

    @InlineOnly
    /* JADX INFO: renamed from: rem-WZ4Q5Ns, reason: not valid java name */
    private static final int m2289remWZ4Q5Ns(short s, int i) {
        return UnsignedKt.m2327uintRemainderJ1ME1BU(UInt.m2100constructorimpl(s & MAX_VALUE), i);
    }

    @InlineOnly
    /* JADX INFO: renamed from: rem-xj2QHRw, reason: not valid java name */
    private static final int m2290remxj2QHRw(short s, short s2) {
        return UnsignedKt.m2327uintRemainderJ1ME1BU(UInt.m2100constructorimpl(s & MAX_VALUE), UInt.m2100constructorimpl(s2 & MAX_VALUE));
    }

    @InlineOnly
    /* JADX INFO: renamed from: times-7apg3OU, reason: not valid java name */
    private static final int m2291times7apg3OU(short s, byte b) {
        return UInt.m2100constructorimpl(UInt.m2100constructorimpl(s & MAX_VALUE) * UInt.m2100constructorimpl(b & 255));
    }

    @InlineOnly
    /* JADX INFO: renamed from: times-VKZWuLQ, reason: not valid java name */
    private static final long m2292timesVKZWuLQ(short s, long j) {
        return ULong.m2169constructorimpl(ULong.m2169constructorimpl(((long) s) & 65535) * j);
    }

    @InlineOnly
    /* JADX INFO: renamed from: times-WZ4Q5Ns, reason: not valid java name */
    private static final int m2293timesWZ4Q5Ns(short s, int i) {
        return UInt.m2100constructorimpl(UInt.m2100constructorimpl(s & MAX_VALUE) * i);
    }

    @InlineOnly
    /* JADX INFO: renamed from: times-xj2QHRw, reason: not valid java name */
    private static final int m2294timesxj2QHRw(short s, short s2) {
        return UInt.m2100constructorimpl(UInt.m2100constructorimpl(s & MAX_VALUE) * UInt.m2100constructorimpl(s2 & MAX_VALUE));
    }

    @InlineOnly
    /* JADX INFO: renamed from: toByte-impl, reason: not valid java name */
    private static final byte m2295toByteimpl(short s) {
        return (byte) s;
    }

    @InlineOnly
    /* JADX INFO: renamed from: toDouble-impl, reason: not valid java name */
    private static final double m2296toDoubleimpl(short s) {
        return s & MAX_VALUE;
    }

    @InlineOnly
    /* JADX INFO: renamed from: toFloat-impl, reason: not valid java name */
    private static final float m2297toFloatimpl(short s) {
        return s & MAX_VALUE;
    }

    @InlineOnly
    /* JADX INFO: renamed from: toInt-impl, reason: not valid java name */
    private static final int m2298toIntimpl(short s) {
        return s & MAX_VALUE;
    }

    @InlineOnly
    /* JADX INFO: renamed from: toLong-impl, reason: not valid java name */
    private static final long m2299toLongimpl(short s) {
        return ((long) s) & 65535;
    }

    @InlineOnly
    /* JADX INFO: renamed from: toShort-impl, reason: not valid java name */
    private static final short m2300toShortimpl(short s) {
        return s;
    }

    @NotNull
    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m2301toStringimpl(short s) {
        return String.valueOf(s & MAX_VALUE);
    }

    @InlineOnly
    /* JADX INFO: renamed from: toUByte-impl, reason: not valid java name */
    private static final byte m2302toUByteimpl(short s) {
        return UByte.m2033constructorimpl((byte) s);
    }

    @InlineOnly
    /* JADX INFO: renamed from: toUInt-impl, reason: not valid java name */
    private static final int m2303toUIntimpl(short s) {
        return UInt.m2100constructorimpl(s & MAX_VALUE);
    }

    @InlineOnly
    /* JADX INFO: renamed from: toULong-impl, reason: not valid java name */
    private static final long m2304toULongimpl(short s) {
        return ULong.m2169constructorimpl(((long) s) & 65535);
    }

    @InlineOnly
    /* JADX INFO: renamed from: toUShort-impl, reason: not valid java name */
    private static final short m2305toUShortimpl(short s) {
        return s;
    }

    @InlineOnly
    /* JADX INFO: renamed from: xor-xj2QHRw, reason: not valid java name */
    private static final short m2306xorxj2QHRw(short s, short s2) {
        return m2266constructorimpl((short) (s ^ s2));
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(UShort uShort) {
        return m2264compareToxj2QHRw(uShort.getData());
    }

    public boolean equals(Object other) {
        return m2272equalsimpl(this.data, other);
    }

    public int hashCode() {
        return m2274hashCodeimpl(this.data);
    }

    @NotNull
    public String toString() {
        return m2301toStringimpl(this.data);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ short getData() {
        return this.data;
    }
}
