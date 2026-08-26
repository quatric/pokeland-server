package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class ReceivedMessage extends GenericJson {

    @Key
    private String ackId;

    @Key
    private PubsubMessage message;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public ReceivedMessage clone() {
        return (ReceivedMessage) super.clone();
    }

    public String getAckId() {
        return this.ackId;
    }

    public PubsubMessage getMessage() {
        return this.message;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public ReceivedMessage set(String str, Object obj) {
        return (ReceivedMessage) super.set(str, obj);
    }

    public ReceivedMessage setAckId(String str) {
        this.ackId = str;
        return this;
    }

    public ReceivedMessage setMessage(PubsubMessage pubsubMessage) {
        this.message = pubsubMessage;
        return this;
    }
}
