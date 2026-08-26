package kotlin.sequences;

import java.util.Iterator;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: _USequences.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00070\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\n0\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"}, m1984d2 = {"sum", "Lkotlin/UInt;", "Lkotlin/sequences/Sequence;", "Lkotlin/UByte;", "sumOfUByte", "(Lkotlin/sequences/Sequence;)I", "sumOfUInt", "Lkotlin/ULong;", "sumOfULong", "(Lkotlin/sequences/Sequence;)J", "Lkotlin/UShort;", "sumOfUShort", "kotlin-stdlib"}, m1985k = 5, m1986mv = {1, 1, 15}, m1988xi = 1, m1989xs = "kotlin/sequences/USequencesKt")
class USequencesKt___USequencesKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @JvmName(name = "sumOfUByte")
    public static final int sumOfUByte(@NotNull Sequence<UByte> sum) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        Iterator<UByte> it = sum.iterator();
        int iM2100constructorimpl = 0;
        while (it.hasNext()) {
            iM2100constructorimpl = UInt.m2100constructorimpl(iM2100constructorimpl + UInt.m2100constructorimpl(it.next().getData() & 255));
        }
        return iM2100constructorimpl;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @JvmName(name = "sumOfUInt")
    public static final int sumOfUInt(@NotNull Sequence<UInt> sum) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        Iterator<UInt> it = sum.iterator();
        int iM2100constructorimpl = 0;
        while (it.hasNext()) {
            iM2100constructorimpl = UInt.m2100constructorimpl(iM2100constructorimpl + it.next().getData());
        }
        return iM2100constructorimpl;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @JvmName(name = "sumOfULong")
    public static final long sumOfULong(@NotNull Sequence<ULong> sum) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        Iterator<ULong> it = sum.iterator();
        long jM2169constructorimpl = 0;
        while (it.hasNext()) {
            jM2169constructorimpl = ULong.m2169constructorimpl(jM2169constructorimpl + it.next().getData());
        }
        return jM2169constructorimpl;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @JvmName(name = "sumOfUShort")
    public static final int sumOfUShort(@NotNull Sequence<UShort> sum) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        Iterator<UShort> it = sum.iterator();
        int iM2100constructorimpl = 0;
        while (it.hasNext()) {
            iM2100constructorimpl = UInt.m2100constructorimpl(iM2100constructorimpl + UInt.m2100constructorimpl(it.next().getData() & UShort.MAX_VALUE));
        }
        return iM2100constructorimpl;
    }
}
