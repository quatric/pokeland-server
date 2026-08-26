package com.google.firebase.analytics.connector.internal;

import com.google.firebase.analytics.connector.AnalyticsConnector;
import java.util.Set;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface zza {
    void registerEventNames(Set<String> set);

    void unregisterEventNames();

    AnalyticsConnector.AnalyticsConnectorListener zzrr();
}
