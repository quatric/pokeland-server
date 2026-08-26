package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@GwtCompatible
public final class ArrayBasedEscaperMap {
    private static final char[][] EMPTY_REPLACEMENT_ARRAY = (char[][]) Array.newInstance((Class<?>) char.class, 0, 0);
    private final char[][] replacementArray;

    private ArrayBasedEscaperMap(char[][] cArr) {
        this.replacementArray = cArr;
    }

    public static ArrayBasedEscaperMap create(Map<Character, String> map) {
        return new ArrayBasedEscaperMap(createReplacementArray(map));
    }

    @VisibleForTesting
    static char[][] createReplacementArray(Map<Character, String> map) {
        Preconditions.checkNotNull(map);
        if (map.isEmpty()) {
            return EMPTY_REPLACEMENT_ARRAY;
        }
        char[][] cArr = new char[((Character) Collections.max(map.keySet())).charValue() + 1][];
        Iterator<Character> it = map.keySet().iterator();
        while (it.hasNext()) {
            char cCharValue = it.next().charValue();
            cArr[cCharValue] = map.get(Character.valueOf(cCharValue)).toCharArray();
        }
        return cArr;
    }

    char[][] getReplacementArray() {
        return this.replacementArray;
    }
}
