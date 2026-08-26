package com.google.api.services.pubsub;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class PubsubScopes {
    public static final String CLOUD_PLATFORM = "https://www.googleapis.com/auth/cloud-platform";
    public static final String PUBSUB = "https://www.googleapis.com/auth/pubsub";

    private PubsubScopes() {
    }

    public static Set<String> all() {
        HashSet hashSet = new HashSet();
        hashSet.add(CLOUD_PLATFORM);
        hashSet.add(PUBSUB);
        return Collections.unmodifiableSet(hashSet);
    }
}
