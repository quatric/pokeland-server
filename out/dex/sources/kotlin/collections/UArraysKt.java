package kotlin.collections;

import java.util.Arrays;
import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: UArraysKt.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Deprecated(level = DeprecationLevel.HIDDEN, message = "Provided for binary compatibility")
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\t\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b\u0007\u0010\bJ\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\t2\u0006\u0010\u0006\u001a\u00020\tH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\f2\u0006\u0010\u0006\u001a\u00020\fH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000eJ\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u000fH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\tH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0016\u0010\u0017J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\fH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0019J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u001bJ\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\tH\u0007ø\u0001\u0000¢\u0006\u0004\b \u0010!J\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\fH\u0007ø\u0001\u0000¢\u0006\u0004\b\"\u0010#J\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0004\b$\u0010%J\u001e\u0010&\u001a\u00020'*\u00020\u00052\u0006\u0010&\u001a\u00020(H\u0007ø\u0001\u0000¢\u0006\u0004\b)\u0010*J\u001e\u0010&\u001a\u00020+*\u00020\t2\u0006\u0010&\u001a\u00020(H\u0007ø\u0001\u0000¢\u0006\u0004\b,\u0010-J\u001e\u0010&\u001a\u00020.*\u00020\f2\u0006\u0010&\u001a\u00020(H\u0007ø\u0001\u0000¢\u0006\u0004\b/\u00100J\u001e\u0010&\u001a\u000201*\u00020\u000f2\u0006\u0010&\u001a\u00020(H\u0007ø\u0001\u0000¢\u0006\u0004\b2\u00103J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020'05*\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b6\u00107J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020+05*\u00020\tH\u0007ø\u0001\u0000¢\u0006\u0004\b8\u00109J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020.05*\u00020\fH\u0007ø\u0001\u0000¢\u0006\u0004\b:\u0010;J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020105*\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0004\b<\u0010=\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006>"}, m1984d2 = {"Lkotlin/collections/UArraysKt;", "", "()V", "contentEquals", "", "Lkotlin/UByteArray;", "other", "contentEquals-kdPth3s", "([B[B)Z", "Lkotlin/UIntArray;", "contentEquals-ctEhBpI", "([I[I)Z", "Lkotlin/ULongArray;", "contentEquals-us8wMrg", "([J[J)Z", "Lkotlin/UShortArray;", "contentEquals-mazbYpA", "([S[S)Z", "contentHashCode", "", "contentHashCode-GBYM_sE", "([B)I", "contentHashCode--ajY-9A", "([I)I", "contentHashCode-QwZRm1k", "([J)I", "contentHashCode-rL5Bavg", "([S)I", "contentToString", "", "contentToString-GBYM_sE", "([B)Ljava/lang/String;", "contentToString--ajY-9A", "([I)Ljava/lang/String;", "contentToString-QwZRm1k", "([J)Ljava/lang/String;", "contentToString-rL5Bavg", "([S)Ljava/lang/String;", "random", "Lkotlin/UByte;", "Lkotlin/random/Random;", "random-oSF2wD8", "([BLkotlin/random/Random;)B", "Lkotlin/UInt;", "random-2D5oskM", "([ILkotlin/random/Random;)I", "Lkotlin/ULong;", "random-JzugnMA", "([JLkotlin/random/Random;)J", "Lkotlin/UShort;", "random-s5X_as8", "([SLkotlin/random/Random;)S", "toTypedArray", "", "toTypedArray-GBYM_sE", "([B)[Lkotlin/UByte;", "toTypedArray--ajY-9A", "([I)[Lkotlin/UInt;", "toTypedArray-QwZRm1k", "([J)[Lkotlin/ULong;", "toTypedArray-rL5Bavg", "([S)[Lkotlin/UShort;", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
public final class UArraysKt {
    public static final UArraysKt INSTANCE = new UArraysKt();

    private UArraysKt() {
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contentEquals-ctEhBpI, reason: not valid java name */
    public static final boolean m2346contentEqualsctEhBpI(@NotNull int[] contentEquals, @NotNull int[] other) {
        Intrinsics.checkParameterIsNotNull(contentEquals, "$this$contentEquals");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Arrays.equals(contentEquals, other);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contentEquals-kdPth3s, reason: not valid java name */
    public static final boolean m2347contentEqualskdPth3s(@NotNull byte[] contentEquals, @NotNull byte[] other) {
        Intrinsics.checkParameterIsNotNull(contentEquals, "$this$contentEquals");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Arrays.equals(contentEquals, other);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contentEquals-mazbYpA, reason: not valid java name */
    public static final boolean m2348contentEqualsmazbYpA(@NotNull short[] contentEquals, @NotNull short[] other) {
        Intrinsics.checkParameterIsNotNull(contentEquals, "$this$contentEquals");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Arrays.equals(contentEquals, other);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contentEquals-us8wMrg, reason: not valid java name */
    public static final boolean m2349contentEqualsus8wMrg(@NotNull long[] contentEquals, @NotNull long[] other) {
        Intrinsics.checkParameterIsNotNull(contentEquals, "$this$contentEquals");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Arrays.equals(contentEquals, other);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contentHashCode--ajY-9A, reason: not valid java name */
    public static final int m2350contentHashCodeajY9A(@NotNull int[] contentHashCode) {
        Intrinsics.checkParameterIsNotNull(contentHashCode, "$this$contentHashCode");
        return Arrays.hashCode(contentHashCode);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contentHashCode-GBYM_sE, reason: not valid java name */
    public static final int m2351contentHashCodeGBYM_sE(@NotNull byte[] contentHashCode) {
        Intrinsics.checkParameterIsNotNull(contentHashCode, "$this$contentHashCode");
        return Arrays.hashCode(contentHashCode);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contentHashCode-QwZRm1k, reason: not valid java name */
    public static final int m2352contentHashCodeQwZRm1k(@NotNull long[] contentHashCode) {
        Intrinsics.checkParameterIsNotNull(contentHashCode, "$this$contentHashCode");
        return Arrays.hashCode(contentHashCode);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contentHashCode-rL5Bavg, reason: not valid java name */
    public static final int m2353contentHashCoderL5Bavg(@NotNull short[] contentHashCode) {
        Intrinsics.checkParameterIsNotNull(contentHashCode, "$this$contentHashCode");
        return Arrays.hashCode(contentHashCode);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: contentToString--ajY-9A, reason: not valid java name */
    public static final String m2354contentToStringajY9A(@NotNull int[] contentToString) {
        Intrinsics.checkParameterIsNotNull(contentToString, "$this$contentToString");
        return CollectionsKt.joinToString$default(UIntArray.m2144boximpl(contentToString), ", ", "[", "]", 0, null, null, 56, null);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: contentToString-GBYM_sE, reason: not valid java name */
    public static final String m2355contentToStringGBYM_sE(@NotNull byte[] contentToString) {
        Intrinsics.checkParameterIsNotNull(contentToString, "$this$contentToString");
        return CollectionsKt.joinToString$default(UByteArray.m2075boximpl(contentToString), ", ", "[", "]", 0, null, null, 56, null);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: contentToString-QwZRm1k, reason: not valid java name */
    public static final String m2356contentToStringQwZRm1k(@NotNull long[] contentToString) {
        Intrinsics.checkParameterIsNotNull(contentToString, "$this$contentToString");
        return CollectionsKt.joinToString$default(ULongArray.m2213boximpl(contentToString), ", ", "[", "]", 0, null, null, 56, null);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: contentToString-rL5Bavg, reason: not valid java name */
    public static final String m2357contentToStringrL5Bavg(@NotNull short[] contentToString) {
        Intrinsics.checkParameterIsNotNull(contentToString, "$this$contentToString");
        return CollectionsKt.joinToString$default(UShortArray.m2308boximpl(contentToString), ", ", "[", "]", 0, null, null, 56, null);
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: random-2D5oskM, reason: not valid java name */
    public static final int m2358random2D5oskM(@NotNull int[] random, @NotNull Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        if (UIntArray.m2154isEmptyimpl(random)) {
            throw new NoSuchElementException("Array is empty.");
        }
        return UIntArray.m2151getimpl(random, random2.nextInt(UIntArray.m2152getSizeimpl(random)));
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: random-JzugnMA, reason: not valid java name */
    public static final long m2359randomJzugnMA(@NotNull long[] random, @NotNull Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        if (ULongArray.m2223isEmptyimpl(random)) {
            throw new NoSuchElementException("Array is empty.");
        }
        return ULongArray.m2220getimpl(random, random2.nextInt(ULongArray.m2221getSizeimpl(random)));
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: random-oSF2wD8, reason: not valid java name */
    public static final byte m2360randomoSF2wD8(@NotNull byte[] random, @NotNull Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        if (UByteArray.m2085isEmptyimpl(random)) {
            throw new NoSuchElementException("Array is empty.");
        }
        return UByteArray.m2082getimpl(random, random2.nextInt(UByteArray.m2083getSizeimpl(random)));
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: random-s5X_as8, reason: not valid java name */
    public static final short m2361randoms5X_as8(@NotNull short[] random, @NotNull Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        if (UShortArray.m2318isEmptyimpl(random)) {
            throw new NoSuchElementException("Array is empty.");
        }
        return UShortArray.m2315getimpl(random, random2.nextInt(UShortArray.m2316getSizeimpl(random)));
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: toTypedArray--ajY-9A, reason: not valid java name */
    public static final UInt[] m2362toTypedArrayajY9A(@NotNull int[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        int iM2152getSizeimpl = UIntArray.m2152getSizeimpl(toTypedArray);
        UInt[] uIntArr = new UInt[iM2152getSizeimpl];
        for (int i = 0; i < iM2152getSizeimpl; i++) {
            uIntArr[i] = UInt.m2094boximpl(UIntArray.m2151getimpl(toTypedArray, i));
        }
        return uIntArr;
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: toTypedArray-GBYM_sE, reason: not valid java name */
    public static final UByte[] m2363toTypedArrayGBYM_sE(@NotNull byte[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        int iM2083getSizeimpl = UByteArray.m2083getSizeimpl(toTypedArray);
        UByte[] uByteArr = new UByte[iM2083getSizeimpl];
        for (int i = 0; i < iM2083getSizeimpl; i++) {
            uByteArr[i] = UByte.m2027boximpl(UByteArray.m2082getimpl(toTypedArray, i));
        }
        return uByteArr;
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: toTypedArray-QwZRm1k, reason: not valid java name */
    public static final ULong[] m2364toTypedArrayQwZRm1k(@NotNull long[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        int iM2221getSizeimpl = ULongArray.m2221getSizeimpl(toTypedArray);
        ULong[] uLongArr = new ULong[iM2221getSizeimpl];
        for (int i = 0; i < iM2221getSizeimpl; i++) {
            uLongArr[i] = ULong.m2163boximpl(ULongArray.m2220getimpl(toTypedArray, i));
        }
        return uLongArr;
    }

    @JvmStatic
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: toTypedArray-rL5Bavg, reason: not valid java name */
    public static final UShort[] m2365toTypedArrayrL5Bavg(@NotNull short[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        int iM2316getSizeimpl = UShortArray.m2316getSizeimpl(toTypedArray);
        UShort[] uShortArr = new UShort[iM2316getSizeimpl];
        for (int i = 0; i < iM2316getSizeimpl; i++) {
            uShortArr[i] = UShort.m2260boximpl(UShortArray.m2315getimpl(toTypedArray, i));
        }
        return uShortArr;
    }
}
