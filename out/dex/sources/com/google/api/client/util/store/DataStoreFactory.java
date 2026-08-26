package com.google.api.client.util.store;

import java.io.IOException;
import java.io.Serializable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface DataStoreFactory {
    <V extends Serializable> DataStore<V> getDataStore(String str) throws IOException;
}
