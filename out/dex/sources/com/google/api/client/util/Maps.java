package com.google.api.client.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Maps {
    private Maps() {
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    public static <K extends Comparable<?>, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap<>();
    }
}
