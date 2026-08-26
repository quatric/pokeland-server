package com.google.api.client.auth.oauth;

import com.google.api.client.util.Beta;
import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public interface OAuthSigner {
    String computeSignature(String str) throws GeneralSecurityException;

    String getSignatureMethod();
}
