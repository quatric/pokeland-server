package com.google.api.client.util.store;

import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.Serializable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class AbstractDataStore<V extends Serializable> implements DataStore<V> {
    private final DataStoreFactory dataStoreFactory;

    /* JADX INFO: renamed from: id */
    private final String f280id;

    protected AbstractDataStore(DataStoreFactory dataStoreFactory, String str) {
        this.dataStoreFactory = (DataStoreFactory) Preconditions.checkNotNull(dataStoreFactory);
        this.f280id = (String) Preconditions.checkNotNull(str);
    }

    @Override // com.google.api.client.util.store.DataStore
    public boolean containsKey(String str) throws IOException {
        return get(str) != null;
    }

    @Override // com.google.api.client.util.store.DataStore
    public boolean containsValue(V v) throws IOException {
        return values().contains(v);
    }

    @Override // com.google.api.client.util.store.DataStore
    public DataStoreFactory getDataStoreFactory() {
        return this.dataStoreFactory;
    }

    @Override // com.google.api.client.util.store.DataStore
    public final String getId() {
        return this.f280id;
    }

    @Override // com.google.api.client.util.store.DataStore
    public boolean isEmpty() throws IOException {
        return size() == 0;
    }

    @Override // com.google.api.client.util.store.DataStore
    public int size() throws IOException {
        return keySet().size();
    }
}
