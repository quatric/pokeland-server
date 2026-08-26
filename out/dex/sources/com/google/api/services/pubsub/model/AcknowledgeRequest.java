package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class AcknowledgeRequest extends GenericJson {

    @Key
    private List<String> ackIds;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public AcknowledgeRequest clone() {
        return (AcknowledgeRequest) super.clone();
    }

    public List<String> getAckIds() {
        return this.ackIds;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public AcknowledgeRequest set(String str, Object obj) {
        return (AcknowledgeRequest) super.set(str, obj);
    }

    public AcknowledgeRequest setAckIds(List<String> list) {
        this.ackIds = list;
        return this;
    }
}
