package kotlin.experimental;

import com.android.billingclient.BuildConfig;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;

/* JADX INFO: compiled from: bitwiseOperations.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\n\n\u0002\b\u0004\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\u0015\u0010\u0000\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\f\u001a\r\u0010\u0004\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\r\u0010\u0004\u001a\u00020\u0003*\u00020\u0003H\u0087\b\u001a\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\u0015\u0010\u0005\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\f\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\u0015\u0010\u0006\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\f¨\u0006\u0007"}, m1984d2 = {"and", "", "other", "", "inv", "or", "xor", "kotlin-stdlib"}, m1985k = 2, m1986mv = {1, 1, 15})
public final class BitwiseOperationsKt {
    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final byte and(byte b, byte b2) {
        return (byte) (b & b2);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final short and(short s, short s2) {
        return (short) (s & s2);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final byte inv(byte b) {
        return (byte) (b ^ (-1));
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final short inv(short s) {
        return (short) (s ^ (-1));
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    /* JADX INFO: renamed from: or */
    private static final byte m2000or(byte b, byte b2) {
        return (byte) (b | b2);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    /* JADX INFO: renamed from: or */
    private static final short m2001or(short s, short s2) {
        return (short) (s | s2);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final byte xor(byte b, byte b2) {
        return (byte) (b ^ b2);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final short xor(short s, short s2) {
        return (short) (s ^ s2);
    }
}
