package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class SeekRequest extends GenericJson {

    @Key
    private String snapshot;

    @Key
    private String time;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public SeekRequest clone() {
        return (SeekRequest) super.clone();
    }

    public String getSnapshot() {
        return this.snapshot;
    }

    public String getTime() {
        return this.time;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public SeekRequest set(String str, Object obj) {
        return (SeekRequest) super.set(str, obj);
    }

    public SeekRequest setSnapshot(String str) {
        this.snapshot = str;
        return this;
    }

    public SeekRequest setTime(String str) {
        this.time = str;
        return this;
    }
}
