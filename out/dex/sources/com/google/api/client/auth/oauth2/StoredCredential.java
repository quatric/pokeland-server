package com.google.api.client.auth.oauth2;

import com.google.api.client.util.Beta;
import com.google.api.client.util.Objects;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public final class StoredCredential implements Serializable {
    public static final String DEFAULT_DATA_STORE_ID = "StoredCredential";
    private static final long serialVersionUID = 1;
    private String accessToken;
    private Long expirationTimeMilliseconds;
    private final Lock lock = new ReentrantLock();
    private String refreshToken;

    public StoredCredential() {
    }

    public StoredCredential(Credential credential) {
        setAccessToken(credential.getAccessToken());
        setRefreshToken(credential.getRefreshToken());
        setExpirationTimeMilliseconds(credential.getExpirationTimeMilliseconds());
    }

    public static DataStore<StoredCredential> getDefaultDataStore(DataStoreFactory dataStoreFactory) throws IOException {
        return dataStoreFactory.getDataStore(DEFAULT_DATA_STORE_ID);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StoredCredential)) {
            return false;
        }
        StoredCredential storedCredential = (StoredCredential) obj;
        return Objects.equal(getAccessToken(), storedCredential.getAccessToken()) && Objects.equal(getRefreshToken(), storedCredential.getRefreshToken()) && Objects.equal(getExpirationTimeMilliseconds(), storedCredential.getExpirationTimeMilliseconds());
    }

    public String getAccessToken() {
        this.lock.lock();
        try {
            return this.accessToken;
        } finally {
            this.lock.unlock();
        }
    }

    public Long getExpirationTimeMilliseconds() {
        this.lock.lock();
        try {
            return this.expirationTimeMilliseconds;
        } finally {
            this.lock.unlock();
        }
    }

    public String getRefreshToken() {
        this.lock.lock();
        try {
            return this.refreshToken;
        } finally {
            this.lock.unlock();
        }
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{getAccessToken(), getRefreshToken(), getExpirationTimeMilliseconds()});
    }

    public StoredCredential setAccessToken(String str) {
        this.lock.lock();
        try {
            this.accessToken = str;
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public StoredCredential setExpirationTimeMilliseconds(Long l) {
        this.lock.lock();
        try {
            this.expirationTimeMilliseconds = l;
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public StoredCredential setRefreshToken(String str) {
        this.lock.lock();
        try {
            this.refreshToken = str;
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public String toString() {
        return Objects.toStringHelper(StoredCredential.class).add("accessToken", getAccessToken()).add("refreshToken", getRefreshToken()).add("expirationTimeMilliseconds", getExpirationTimeMilliseconds()).toString();
    }
}
