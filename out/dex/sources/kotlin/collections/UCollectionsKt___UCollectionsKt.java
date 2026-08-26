package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: _UCollections.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00070\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\n0\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\u0005\u001a\u001a\u0010\f\u001a\u00020\r*\b\u0012\u0004\u0012\u00020\u00030\u000eH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a\u001a\u0010\u0010\u001a\u00020\u0011*\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a\u001a\u0010\u0013\u001a\u00020\u0014*\b\u0012\u0004\u0012\u00020\u00070\u000eH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001a\u001a\u0010\u0016\u001a\u00020\u0017*\b\u0012\u0004\u0012\u00020\n0\u000eH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0018\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0019"}, m1984d2 = {"sum", "Lkotlin/UInt;", "", "Lkotlin/UByte;", "sumOfUByte", "(Ljava/lang/Iterable;)I", "sumOfUInt", "Lkotlin/ULong;", "sumOfULong", "(Ljava/lang/Iterable;)J", "Lkotlin/UShort;", "sumOfUShort", "toUByteArray", "Lkotlin/UByteArray;", "", "(Ljava/util/Collection;)[B", "toUIntArray", "Lkotlin/UIntArray;", "(Ljava/util/Collection;)[I", "toULongArray", "Lkotlin/ULongArray;", "(Ljava/util/Collection;)[J", "toUShortArray", "Lkotlin/UShortArray;", "(Ljava/util/Collection;)[S", "kotlin-stdlib"}, m1985k = 5, m1986mv = {1, 1, 15}, m1988xi = 1, m1989xs = "kotlin/collections/UCollectionsKt")
class UCollectionsKt___UCollectionsKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @JvmName(name = "sumOfUByte")
    public static final int sumOfUByte(@NotNull Iterable<UByte> sum) {
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
    public static final int sumOfUInt(@NotNull Iterable<UInt> sum) {
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
    public static final long sumOfULong(@NotNull Iterable<ULong> sum) {
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
    public static final int sumOfUShort(@NotNull Iterable<UShort> sum) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        Iterator<UShort> it = sum.iterator();
        int iM2100constructorimpl = 0;
        while (it.hasNext()) {
            iM2100constructorimpl = UInt.m2100constructorimpl(iM2100constructorimpl + UInt.m2100constructorimpl(it.next().getData() & UShort.MAX_VALUE));
        }
        return iM2100constructorimpl;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final byte[] toUByteArray(@NotNull Collection<UByte> toUByteArray) {
        Intrinsics.checkParameterIsNotNull(toUByteArray, "$this$toUByteArray");
        byte[] bArrM2076constructorimpl = UByteArray.m2076constructorimpl(toUByteArray.size());
        Iterator<UByte> it = toUByteArray.iterator();
        int i = 0;
        while (it.hasNext()) {
            UByteArray.m2087setVurrAj0(bArrM2076constructorimpl, i, it.next().getData());
            i++;
        }
        return bArrM2076constructorimpl;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final int[] toUIntArray(@NotNull Collection<UInt> toUIntArray) {
        Intrinsics.checkParameterIsNotNull(toUIntArray, "$this$toUIntArray");
        int[] iArrM2145constructorimpl = UIntArray.m2145constructorimpl(toUIntArray.size());
        Iterator<UInt> it = toUIntArray.iterator();
        int i = 0;
        while (it.hasNext()) {
            UIntArray.m2156setVXSXFK8(iArrM2145constructorimpl, i, it.next().getData());
            i++;
        }
        return iArrM2145constructorimpl;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final long[] toULongArray(@NotNull Collection<ULong> toULongArray) {
        Intrinsics.checkParameterIsNotNull(toULongArray, "$this$toULongArray");
        long[] jArrM2214constructorimpl = ULongArray.m2214constructorimpl(toULongArray.size());
        Iterator<ULong> it = toULongArray.iterator();
        int i = 0;
        while (it.hasNext()) {
            ULongArray.m2225setk8EXiF4(jArrM2214constructorimpl, i, it.next().getData());
            i++;
        }
        return jArrM2214constructorimpl;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final short[] toUShortArray(@NotNull Collection<UShort> toUShortArray) {
        Intrinsics.checkParameterIsNotNull(toUShortArray, "$this$toUShortArray");
        short[] sArrM2309constructorimpl = UShortArray.m2309constructorimpl(toUShortArray.size());
        Iterator<UShort> it = toUShortArray.iterator();
        int i = 0;
        while (it.hasNext()) {
            UShortArray.m2320set01HTLdE(sArrM2309constructorimpl, i, it.next().getData());
            i++;
        }
        return sArrM2309constructorimpl;
    }
}
