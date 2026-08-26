package com.google.api.client.googleapis.testing;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.text.Typography;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class TestUtils {
    private static final String UTF_8 = "UTF-8";

    private TestUtils() {
    }

    public static Map<String, String> parseQuery(String str) throws IOException {
        HashMap map = new HashMap();
        Iterator<String> it = Splitter.m473on(Typography.amp).split(str).iterator();
        while (it.hasNext()) {
            ArrayList arrayListNewArrayList = Lists.newArrayList(Splitter.m473on('=').split(it.next()));
            if (arrayListNewArrayList.size() != 2) {
                throw new IOException("Invalid Query String");
            }
            map.put(URLDecoder.decode((String) arrayListNewArrayList.get(0), UTF_8), URLDecoder.decode((String) arrayListNewArrayList.get(1), UTF_8));
        }
        return map;
    }
}
