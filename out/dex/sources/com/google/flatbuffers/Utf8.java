package com.google.flatbuffers;

import com.fasterxml.jackson.core.base.GeneratorBase;
import com.google.common.base.Ascii;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class Utf8 {
    private static Utf8 DEFAULT;

    static class DecodeUtil {
        DecodeUtil() {
        }

        static void handleFourBytes(byte b, byte b2, byte b3, byte b4, char[] cArr, int i) throws IllegalArgumentException {
            if (isNotTrailingByte(b2) || (((b << Ascii.f285FS) + (b2 + 112)) >> 30) != 0 || isNotTrailingByte(b3) || isNotTrailingByte(b4)) {
                throw new IllegalArgumentException("Invalid UTF-8");
            }
            int iTrailingByteValue = ((b & 7) << 18) | (trailingByteValue(b2) << 12) | (trailingByteValue(b3) << 6) | trailingByteValue(b4);
            cArr[i] = highSurrogate(iTrailingByteValue);
            cArr[i + 1] = lowSurrogate(iTrailingByteValue);
        }

        static void handleOneByte(byte b, char[] cArr, int i) {
            cArr[i] = (char) b;
        }

        static void handleThreeBytes(byte b, byte b2, byte b3, char[] cArr, int i) throws IllegalArgumentException {
            if (isNotTrailingByte(b2) || ((b == -32 && b2 < -96) || ((b == -19 && b2 >= -96) || isNotTrailingByte(b3)))) {
                throw new IllegalArgumentException("Invalid UTF-8");
            }
            cArr[i] = (char) (((b & Ascii.f291SI) << 12) | (trailingByteValue(b2) << 6) | trailingByteValue(b3));
        }

        static void handleTwoBytes(byte b, byte b2, char[] cArr, int i) throws IllegalArgumentException {
            if (b < -62 || isNotTrailingByte(b2)) {
                throw new IllegalArgumentException("Invalid UTF-8");
            }
            cArr[i] = (char) (((b & Ascii.f294US) << 6) | trailingByteValue(b2));
        }

        private static char highSurrogate(int i) {
            return (char) ((i >>> 10) + 55232);
        }

        private static boolean isNotTrailingByte(byte b) {
            return b > -65;
        }

        static boolean isOneByte(byte b) {
            return b >= 0;
        }

        static boolean isThreeBytes(byte b) {
            return b < -16;
        }

        static boolean isTwoBytes(byte b) {
            return b < -32;
        }

        private static char lowSurrogate(int i) {
            return (char) ((i & 1023) + GeneratorBase.SURR2_FIRST);
        }

        private static int trailingByteValue(byte b) {
            return b & 63;
        }
    }

    static class UnpairedSurrogateException extends IllegalArgumentException {
        UnpairedSurrogateException(int i, int i2) {
            StringBuilder sb = new StringBuilder(54);
            sb.append("Unpaired surrogate at index ");
            sb.append(i);
            sb.append(" of ");
            sb.append(i2);
            super(sb.toString());
        }
    }

    public static Utf8 getDefault() {
        if (DEFAULT == null) {
            DEFAULT = new Utf8Safe();
        }
        return DEFAULT;
    }

    public static void setDefault(Utf8 utf8) {
        DEFAULT = utf8;
    }

    public abstract String decodeUtf8(ByteBuffer byteBuffer, int i, int i2);

    public abstract void encodeUtf8(CharSequence charSequence, ByteBuffer byteBuffer);

    public abstract int encodedLength(CharSequence charSequence);
}
