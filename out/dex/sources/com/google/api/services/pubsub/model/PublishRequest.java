package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class PublishRequest extends GenericJson {

    @Key
    private List<PubsubMessage> messages;

    static {
        Data.nullOf(PubsubMessage.class);
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public PublishRequest clone() {
        return (PublishRequest) super.clone();
    }

    public List<PubsubMessage> getMessages() {
        return this.messages;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public PublishRequest set(String str, Object obj) {
        return (PublishRequest) super.set(str, obj);
    }

    public PublishRequest setMessages(List<PubsubMessage> list) {
        this.messages = list;
        return this;
    }
}
