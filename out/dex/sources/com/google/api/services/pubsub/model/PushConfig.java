package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class PushConfig extends GenericJson {

    @Key
    private Map<String, String> attributes;

    @Key
    private String pushEndpoint;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public PushConfig clone() {
        return (PushConfig) super.clone();
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public String getPushEndpoint() {
        return this.pushEndpoint;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public PushConfig set(String str, Object obj) {
        return (PushConfig) super.set(str, obj);
    }

    public PushConfig setAttributes(Map<String, String> map) {
        this.attributes = map;
        return this;
    }

    public PushConfig setPushEndpoint(String str) {
        this.pushEndpoint = str;
        return this;
    }
}
