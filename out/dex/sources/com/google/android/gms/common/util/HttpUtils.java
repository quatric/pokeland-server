package com.google.android.gms.common.util;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
public class HttpUtils {
    private static final Pattern zzha = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final Pattern zzhb = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
    private static final Pattern zzhc = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    private HttpUtils() {
    }

    private static String decode(String str, String str2) {
        if (str2 == null) {
            str2 = "ISO-8859-1";
        }
        try {
            return URLDecoder.decode(str, str2);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @KeepForSdk
    public static Map<String, String> parse(URI uri, String str) {
        Map<String, String> mapEmptyMap = Collections.emptyMap();
        String rawQuery = uri.getRawQuery();
        if (rawQuery != null && rawQuery.length() > 0) {
            mapEmptyMap = new HashMap<>();
            Scanner scanner = new Scanner(rawQuery);
            scanner.useDelimiter("&");
            while (scanner.hasNext()) {
                String[] strArrSplit = scanner.next().split("=");
                if (strArrSplit.length == 0 || strArrSplit.length > 2) {
                    throw new IllegalArgumentException("bad parameter");
                }
                String strDecode = decode(strArrSplit[0], str);
                String strDecode2 = null;
                if (strArrSplit.length == 2) {
                    strDecode2 = decode(strArrSplit[1], str);
                }
                mapEmptyMap.put(strDecode, strDecode2);
            }
        }
        return mapEmptyMap;
    }
}
