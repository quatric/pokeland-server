package com.google.api.client.util.store;

import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class DataStoreUtils {
    private DataStoreUtils() {
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static String toString(DataStore<?> dataStore) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append('{');
            boolean z = true;
            for (String str : dataStore.keySet()) {
                if (z) {
                    z = false;
                } else {
                    sb.append(", ");
                }
                sb.append(str);
                sb.append('=');
                sb.append(dataStore.get(str));
            }
            sb.append('}');
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
