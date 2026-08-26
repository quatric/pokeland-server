package com.google.api.client.util.store;

import com.google.api.client.util.Maps;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class AbstractDataStoreFactory implements DataStoreFactory {
    private static final Pattern ID_PATTERN = Pattern.compile("\\w{1,30}");
    private final Lock lock = new ReentrantLock();
    private final Map<String, DataStore<? extends Serializable>> dataStoreMap = Maps.newHashMap();

    protected abstract <V extends Serializable> DataStore<V> createDataStore(String str) throws IOException;

    @Override // com.google.api.client.util.store.DataStoreFactory
    public final <V extends Serializable> DataStore<V> getDataStore(String str) throws IOException {
        Preconditions.checkArgument(ID_PATTERN.matcher(str).matches(), "%s does not match pattern %s", str, ID_PATTERN);
        this.lock.lock();
        try {
            DataStore<V> dataStoreCreateDataStore = (DataStore) this.dataStoreMap.get(str);
            if (dataStoreCreateDataStore == null) {
                dataStoreCreateDataStore = createDataStore(str);
                this.dataStoreMap.put(str, dataStoreCreateDataStore);
            }
            return dataStoreCreateDataStore;
        } finally {
            this.lock.unlock();
        }
    }
}
