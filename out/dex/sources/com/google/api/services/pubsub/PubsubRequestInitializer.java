package com.google.api.services.pubsub;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.googleapis.services.json.CommonGoogleJsonClientRequestInitializer;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class PubsubRequestInitializer extends CommonGoogleJsonClientRequestInitializer {
    public PubsubRequestInitializer() {
    }

    public PubsubRequestInitializer(String str) {
        super(str);
    }

    public PubsubRequestInitializer(String str, String str2) {
        super(str, str2);
    }

    @Override // com.google.api.client.googleapis.services.json.CommonGoogleJsonClientRequestInitializer
    public final void initializeJsonRequest(AbstractGoogleJsonClientRequest<?> abstractGoogleJsonClientRequest) throws IOException {
        super.initializeJsonRequest(abstractGoogleJsonClientRequest);
        initializePubsubRequest((PubsubRequest) abstractGoogleJsonClientRequest);
    }

    protected void initializePubsubRequest(PubsubRequest<?> pubsubRequest) throws IOException {
    }
}
