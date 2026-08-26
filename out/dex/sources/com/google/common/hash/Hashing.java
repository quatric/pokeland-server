package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import javax.annotation.Nullable;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public final class Hashing {
    private static final int GOOD_FAST_HASH_SEED = (int) System.currentTimeMillis();

    private static class Adler32Holder {
        static final HashFunction ADLER_32 = Hashing.checksumHashFunction(ChecksumType.ADLER_32, "Hashing.adler32()");

        private Adler32Holder() {
        }
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'CRC_32' uses external variables
    	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:399)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:364)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:349)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:153)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    static abstract class ChecksumType implements Supplier<Checksum> {
        private static final /* synthetic */ ChecksumType[] $VALUES;
        public static final ChecksumType ADLER_32;
        public static final ChecksumType CRC_32;
        private final int bits;

        static {
            int i = 32;
            CRC_32 = new ChecksumType("CRC_32", 0, i) { // from class: com.google.common.hash.Hashing.ChecksumType.1
                @Override // com.google.common.hash.Hashing.ChecksumType, com.google.common.base.Supplier
                public Checksum get() {
                    return new CRC32();
                }
            };
            ADLER_32 = new ChecksumType("ADLER_32", 1, i) { // from class: com.google.common.hash.Hashing.ChecksumType.2
                @Override // com.google.common.hash.Hashing.ChecksumType, com.google.common.base.Supplier
                public Checksum get() {
                    return new Adler32();
                }
            };
            $VALUES = new ChecksumType[]{CRC_32, ADLER_32};
        }

        private ChecksumType(String str, int i, int i2) {
            super(str, i);
            this.bits = i2;
        }

        public static ChecksumType valueOf(String str) {
            return (ChecksumType) Enum.valueOf(ChecksumType.class, str);
        }

        public static ChecksumType[] values() {
            return (ChecksumType[]) $VALUES.clone();
        }

        @Override // com.google.common.base.Supplier
        public abstract Checksum get();
    }

    private static final class ConcatenatedHashFunction extends AbstractCompositeHashFunction {
        private final int bits;

        private ConcatenatedHashFunction(HashFunction... hashFunctionArr) {
            super(hashFunctionArr);
            int iBits = 0;
            for (HashFunction hashFunction : hashFunctionArr) {
                iBits += hashFunction.bits();
                Preconditions.checkArgument(hashFunction.bits() % 8 == 0, "the number of bits (%s) in hashFunction (%s) must be divisible by 8", hashFunction.bits(), (Object) hashFunction);
            }
            this.bits = iBits;
        }

        @Override // com.google.common.hash.HashFunction
        public int bits() {
            return this.bits;
        }

        public boolean equals(@Nullable Object obj) {
            if (obj instanceof ConcatenatedHashFunction) {
                return Arrays.equals(this.functions, ((ConcatenatedHashFunction) obj).functions);
            }
            return false;
        }

        public int hashCode() {
            return (Arrays.hashCode(this.functions) * 31) + this.bits;
        }

        @Override // com.google.common.hash.AbstractCompositeHashFunction
        HashCode makeHash(Hasher[] hasherArr) {
            byte[] bArr = new byte[this.bits / 8];
            int iWriteBytesTo = 0;
            for (Hasher hasher : hasherArr) {
                HashCode hashCodeHash = hasher.hash();
                iWriteBytesTo += hashCodeHash.writeBytesTo(bArr, iWriteBytesTo, hashCodeHash.bits() / 8);
            }
            return HashCode.fromBytesNoCopy(bArr);
        }
    }

    private static class Crc32Holder {
        static final HashFunction CRC_32 = Hashing.checksumHashFunction(ChecksumType.CRC_32, "Hashing.crc32()");

        private Crc32Holder() {
        }
    }

    private static final class Crc32cHolder {
        static final HashFunction CRC_32_C = new Crc32cHashFunction();

        private Crc32cHolder() {
        }
    }

    private static class FarmHashFingerprint64Holder {
        static final HashFunction FARMHASH_FINGERPRINT_64 = new FarmHashFingerprint64();

        private FarmHashFingerprint64Holder() {
        }
    }

    private static final class LinearCongruentialGenerator {
        private long state;

        public LinearCongruentialGenerator(long j) {
            this.state = j;
        }

        public double nextDouble() {
            this.state = (this.state * 2862933555777941757L) + 1;
            double d = ((int) (this.state >>> 33)) + 1;
            Double.isNaN(d);
            return d / 2.147483648E9d;
        }
    }

    private static class Md5Holder {
        static final HashFunction MD5 = new MessageDigestHashFunction("MD5", "Hashing.md5()");

        private Md5Holder() {
        }
    }

    private static class Murmur3_128Holder {
        static final HashFunction MURMUR3_128 = new Murmur3_128HashFunction(0);
        static final HashFunction GOOD_FAST_HASH_FUNCTION_128 = Hashing.murmur3_128(Hashing.GOOD_FAST_HASH_SEED);

        private Murmur3_128Holder() {
        }
    }

    private static class Murmur3_32Holder {
        static final HashFunction MURMUR3_32 = new Murmur3_32HashFunction(0);
        static final HashFunction GOOD_FAST_HASH_FUNCTION_32 = Hashing.murmur3_32(Hashing.GOOD_FAST_HASH_SEED);

        private Murmur3_32Holder() {
        }
    }

    private static class Sha1Holder {
        static final HashFunction SHA_1 = new MessageDigestHashFunction("SHA-1", "Hashing.sha1()");

        private Sha1Holder() {
        }
    }

    private static class Sha256Holder {
        static final HashFunction SHA_256 = new MessageDigestHashFunction("SHA-256", "Hashing.sha256()");

        private Sha256Holder() {
        }
    }

    private static class Sha384Holder {
        static final HashFunction SHA_384 = new MessageDigestHashFunction("SHA-384", "Hashing.sha384()");

        private Sha384Holder() {
        }
    }

    private static class Sha512Holder {
        static final HashFunction SHA_512 = new MessageDigestHashFunction("SHA-512", "Hashing.sha512()");

        private Sha512Holder() {
        }
    }

    private static class SipHash24Holder {
        static final HashFunction SIP_HASH_24 = new SipHashFunction(2, 4, 506097522914230528L, 1084818905618843912L);

        private SipHash24Holder() {
        }
    }

    private Hashing() {
    }

    public static HashFunction adler32() {
        return Adler32Holder.ADLER_32;
    }

    static int checkPositiveAndMakeMultipleOf32(int i) {
        Preconditions.checkArgument(i > 0, "Number of bits must be positive");
        return (i + 31) & (-32);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static HashFunction checksumHashFunction(ChecksumType checksumType, String str) {
        return new ChecksumHashFunction(checksumType, checksumType.bits, str);
    }

    public static HashCode combineOrdered(Iterable<HashCode> iterable) {
        Iterator<HashCode> it = iterable.iterator();
        Preconditions.checkArgument(it.hasNext(), "Must be at least 1 hash code to combine.");
        byte[] bArr = new byte[it.next().bits() / 8];
        Iterator<HashCode> it2 = iterable.iterator();
        while (it2.hasNext()) {
            byte[] bArrAsBytes = it2.next().asBytes();
            Preconditions.checkArgument(bArrAsBytes.length == bArr.length, "All hashcodes must have the same bit length.");
            for (int i = 0; i < bArrAsBytes.length; i++) {
                bArr[i] = (byte) ((bArr[i] * 37) ^ bArrAsBytes[i]);
            }
        }
        return HashCode.fromBytesNoCopy(bArr);
    }

    public static HashCode combineUnordered(Iterable<HashCode> iterable) {
        Iterator<HashCode> it = iterable.iterator();
        Preconditions.checkArgument(it.hasNext(), "Must be at least 1 hash code to combine.");
        byte[] bArr = new byte[it.next().bits() / 8];
        Iterator<HashCode> it2 = iterable.iterator();
        while (it2.hasNext()) {
            byte[] bArrAsBytes = it2.next().asBytes();
            Preconditions.checkArgument(bArrAsBytes.length == bArr.length, "All hashcodes must have the same bit length.");
            for (int i = 0; i < bArrAsBytes.length; i++) {
                bArr[i] = (byte) (bArr[i] + bArrAsBytes[i]);
            }
        }
        return HashCode.fromBytesNoCopy(bArr);
    }

    public static HashFunction concatenating(HashFunction hashFunction, HashFunction hashFunction2, HashFunction... hashFunctionArr) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(hashFunction);
        arrayList.add(hashFunction2);
        for (HashFunction hashFunction3 : hashFunctionArr) {
            arrayList.add(hashFunction3);
        }
        return new ConcatenatedHashFunction((HashFunction[]) arrayList.toArray(new HashFunction[0]));
    }

    public static HashFunction concatenating(Iterable<HashFunction> iterable) {
        Preconditions.checkNotNull(iterable);
        ArrayList arrayList = new ArrayList();
        Iterator<HashFunction> it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        Preconditions.checkArgument(arrayList.size() > 0, "number of hash functions (%s) must be > 0", arrayList.size());
        return new ConcatenatedHashFunction((HashFunction[]) arrayList.toArray(new HashFunction[0]));
    }

    public static int consistentHash(long j, int i) {
        int i2 = 0;
        Preconditions.checkArgument(i > 0, "buckets must be positive: %s", i);
        LinearCongruentialGenerator linearCongruentialGenerator = new LinearCongruentialGenerator(j);
        while (true) {
            double d = i2 + 1;
            double dNextDouble = linearCongruentialGenerator.nextDouble();
            Double.isNaN(d);
            int i3 = (int) (d / dNextDouble);
            if (i3 < 0 || i3 >= i) {
                break;
            }
            i2 = i3;
        }
        return i2;
    }

    public static int consistentHash(HashCode hashCode, int i) {
        return consistentHash(hashCode.padToLong(), i);
    }

    public static HashFunction crc32() {
        return Crc32Holder.CRC_32;
    }

    public static HashFunction crc32c() {
        return Crc32cHolder.CRC_32_C;
    }

    public static HashFunction farmHashFingerprint64() {
        return FarmHashFingerprint64Holder.FARMHASH_FINGERPRINT_64;
    }

    public static HashFunction goodFastHash(int i) {
        int iCheckPositiveAndMakeMultipleOf32 = checkPositiveAndMakeMultipleOf32(i);
        if (iCheckPositiveAndMakeMultipleOf32 == 32) {
            return Murmur3_32Holder.GOOD_FAST_HASH_FUNCTION_32;
        }
        if (iCheckPositiveAndMakeMultipleOf32 <= 128) {
            return Murmur3_128Holder.GOOD_FAST_HASH_FUNCTION_128;
        }
        int i2 = (iCheckPositiveAndMakeMultipleOf32 + 127) / 128;
        HashFunction[] hashFunctionArr = new HashFunction[i2];
        hashFunctionArr[0] = Murmur3_128Holder.GOOD_FAST_HASH_FUNCTION_128;
        int i3 = GOOD_FAST_HASH_SEED;
        for (int i4 = 1; i4 < i2; i4++) {
            i3 += 1500450271;
            hashFunctionArr[i4] = murmur3_128(i3);
        }
        return new ConcatenatedHashFunction(hashFunctionArr);
    }

    public static HashFunction hmacMd5(Key key) {
        return new MacHashFunction("HmacMD5", key, hmacToString("hmacMd5", key));
    }

    public static HashFunction hmacMd5(byte[] bArr) {
        return hmacMd5(new SecretKeySpec((byte[]) Preconditions.checkNotNull(bArr), "HmacMD5"));
    }

    public static HashFunction hmacSha1(Key key) {
        return new MacHashFunction("HmacSHA1", key, hmacToString("hmacSha1", key));
    }

    public static HashFunction hmacSha1(byte[] bArr) {
        return hmacSha1(new SecretKeySpec((byte[]) Preconditions.checkNotNull(bArr), "HmacSHA1"));
    }

    public static HashFunction hmacSha256(Key key) {
        return new MacHashFunction("HmacSHA256", key, hmacToString("hmacSha256", key));
    }

    public static HashFunction hmacSha256(byte[] bArr) {
        return hmacSha256(new SecretKeySpec((byte[]) Preconditions.checkNotNull(bArr), "HmacSHA256"));
    }

    public static HashFunction hmacSha512(Key key) {
        return new MacHashFunction("HmacSHA512", key, hmacToString("hmacSha512", key));
    }

    public static HashFunction hmacSha512(byte[] bArr) {
        return hmacSha512(new SecretKeySpec((byte[]) Preconditions.checkNotNull(bArr), "HmacSHA512"));
    }

    private static String hmacToString(String str, Key key) {
        return String.format("Hashing.%s(Key[algorithm=%s, format=%s])", str, key.getAlgorithm(), key.getFormat());
    }

    public static HashFunction md5() {
        return Md5Holder.MD5;
    }

    public static HashFunction murmur3_128() {
        return Murmur3_128Holder.MURMUR3_128;
    }

    public static HashFunction murmur3_128(int i) {
        return new Murmur3_128HashFunction(i);
    }

    public static HashFunction murmur3_32() {
        return Murmur3_32Holder.MURMUR3_32;
    }

    public static HashFunction murmur3_32(int i) {
        return new Murmur3_32HashFunction(i);
    }

    public static HashFunction sha1() {
        return Sha1Holder.SHA_1;
    }

    public static HashFunction sha256() {
        return Sha256Holder.SHA_256;
    }

    public static HashFunction sha384() {
        return Sha384Holder.SHA_384;
    }

    public static HashFunction sha512() {
        return Sha512Holder.SHA_512;
    }

    public static HashFunction sipHash24() {
        return SipHash24Holder.SIP_HASH_24;
    }

    public static HashFunction sipHash24(long j, long j2) {
        return new SipHashFunction(2, 4, j, j2);
    }
}
