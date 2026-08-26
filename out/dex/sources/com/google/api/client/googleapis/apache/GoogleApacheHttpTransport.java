package com.google.api.client.googleapis.apache;

import com.google.api.client.googleapis.GoogleUtils;
import com.google.api.client.http.apache.ApacheHttpTransport;
import java.io.IOException;
import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class GoogleApacheHttpTransport {
    private GoogleApacheHttpTransport() {
    }

    public static ApacheHttpTransport newTrustedTransport() throws GeneralSecurityException, IOException {
        return new ApacheHttpTransport.Builder().trustCertificates(GoogleUtils.getCertificateTrustStore()).build();
    }
}
