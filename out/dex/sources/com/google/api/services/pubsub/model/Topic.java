package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Topic extends GenericJson {

    @Key
    private Map<String, String> labels;

    @Key
    private String name;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public Topic clone() {
        return (Topic) super.clone();
    }

    public Map<String, String> getLabels() {
        return this.labels;
    }

    public String getName() {
        return this.name;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public Topic set(String str, Object obj) {
        return (Topic) super.set(str, obj);
    }

    public Topic setLabels(Map<String, String> map) {
        this.labels = map;
        return this;
    }

    public Topic setName(String str) {
        this.name = str;
        return this;
    }
}
