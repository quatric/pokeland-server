package com.google.api.client.googleapis;

import com.google.api.client.util.SecurityUtils;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class GoogleUtils {
    static KeyStore certTrustStore;
    public static final Integer MAJOR_VERSION = 1;
    public static final Integer MINOR_VERSION = 25;
    public static final Integer BUGFIX_VERSION = 0;
    public static final String VERSION = (MAJOR_VERSION + "." + MINOR_VERSION + "." + BUGFIX_VERSION + "").toString();

    private GoogleUtils() {
    }

    public static synchronized KeyStore getCertificateTrustStore() throws GeneralSecurityException, IOException {
        if (certTrustStore == null) {
            certTrustStore = SecurityUtils.getJavaKeyStore();
            SecurityUtils.loadKeyStore(certTrustStore, GoogleUtils.class.getResourceAsStream("google.jks"), "notasecret");
        }
        return certTrustStore;
    }
}
