package com.google.api.client.auth.oauth2;

import com.google.api.client.util.Beta;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@Deprecated
public interface CredentialStore {
    void delete(String str, Credential credential) throws IOException;

    boolean load(String str, Credential credential) throws IOException;

    void store(String str, Credential credential) throws IOException;
}
