package com.google.android.gms.internal.measurement;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class zzbz {
    private static Object zzaae;
    private static boolean zzaaf;
    private static HashMap<String, String> zzzz;
    public static final Uri CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
    private static final Uri zzzv = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    public static final Pattern zzzw = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    public static final Pattern zzzx = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    private static final AtomicBoolean zzzy = new AtomicBoolean();
    private static final HashMap<String, Boolean> zzaaa = new HashMap<>();
    private static final HashMap<String, Integer> zzaab = new HashMap<>();
    private static final HashMap<String, Long> zzaac = new HashMap<>();
    private static final HashMap<String, Float> zzaad = new HashMap<>();
    private static String[] zzaag = new String[0];

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static String zza(ContentResolver contentResolver, String str, String str2) {
        synchronized (zzbz.class) {
            zza(contentResolver);
            Object obj = zzaae;
            if (zzzz.containsKey(str)) {
                String str3 = zzzz.get(str);
                if (str3 == null) {
                    str3 = null;
                }
                return str3;
            }
            for (String str4 : zzaag) {
                if (str.startsWith(str4)) {
                    if (!zzaaf || zzzz.isEmpty()) {
                        zzzz.putAll(zza(contentResolver, zzaag));
                        zzaaf = true;
                        if (zzzz.containsKey(str)) {
                            String str5 = zzzz.get(str);
                            if (str5 == null) {
                                str5 = null;
                            }
                            return str5;
                        }
                    }
                    return null;
                }
            }
            Cursor cursorQuery = contentResolver.query(CONTENT_URI, null, null, new String[]{str}, null);
            if (cursorQuery == null) {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return null;
            }
            try {
                if (!cursorQuery.moveToFirst()) {
                    zza(obj, str, (String) null);
                    return null;
                }
                String string = cursorQuery.getString(1);
                if (string != null && string.equals(null)) {
                    string = null;
                }
                zza(obj, str, string);
                if (string == null) {
                    string = null;
                }
                return string;
            } finally {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static Map<String, String> zza(ContentResolver contentResolver, String... strArr) {
        Cursor cursorQuery = contentResolver.query(zzzv, null, null, strArr, null);
        TreeMap treeMap = new TreeMap();
        if (cursorQuery == null) {
            return treeMap;
        }
        while (cursorQuery.moveToNext()) {
            try {
                treeMap.put(cursorQuery.getString(0), cursorQuery.getString(1));
            } catch (Throwable th) {
                cursorQuery.close();
                throw th;
            }
        }
        cursorQuery.close();
        return treeMap;
    }

    private static void zza(ContentResolver contentResolver) {
        if (zzzz == null) {
            zzzy.set(false);
            zzzz = new HashMap<>();
            zzaae = new Object();
            zzaaf = false;
            contentResolver.registerContentObserver(CONTENT_URI, true, new zzby(null));
            return;
        }
        if (zzzy.getAndSet(false)) {
            zzzz.clear();
            zzaaa.clear();
            zzaab.clear();
            zzaac.clear();
            zzaad.clear();
            zzaae = new Object();
            zzaaf = false;
        }
    }

    private static void zza(Object obj, String str, String str2) {
        synchronized (zzbz.class) {
            if (obj == zzaae) {
                zzzz.put(str, str2);
            }
        }
    }
}
