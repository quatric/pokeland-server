package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Snapshot extends GenericJson {

    @Key
    private String expireTime;

    @Key
    private Map<String, String> labels;

    @Key
    private String name;

    @Key
    private String topic;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public Snapshot clone() {
        return (Snapshot) super.clone();
    }

    public String getExpireTime() {
        return this.expireTime;
    }

    public Map<String, String> getLabels() {
        return this.labels;
    }

    public String getName() {
        return this.name;
    }

    public String getTopic() {
        return this.topic;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public Snapshot set(String str, Object obj) {
        return (Snapshot) super.set(str, obj);
    }

    public Snapshot setExpireTime(String str) {
        this.expireTime = str;
        return this;
    }

    public Snapshot setLabels(Map<String, String> map) {
        this.labels = map;
        return this;
    }

    public Snapshot setName(String str) {
        this.name = str;
        return this;
    }

    public Snapshot setTopic(String str) {
        this.topic = str;
        return this;
    }
}
