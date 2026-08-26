package com.google.api.client.util;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class StringUtils {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private StringUtils() {
    }

    public static byte[] getBytesUtf8(String str) {
        return com.google.api.client.repackaged.org.apache.commons.codec.binary.StringUtils.getBytesUtf8(str);
    }

    public static String newStringUtf8(byte[] bArr) {
        return com.google.api.client.repackaged.org.apache.commons.codec.binary.StringUtils.newStringUtf8(bArr);
    }
}
