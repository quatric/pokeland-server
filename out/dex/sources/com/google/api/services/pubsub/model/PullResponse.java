package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class PullResponse extends GenericJson {

    @Key
    private List<ReceivedMessage> receivedMessages;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public PullResponse clone() {
        return (PullResponse) super.clone();
    }

    public List<ReceivedMessage> getReceivedMessages() {
        return this.receivedMessages;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public PullResponse set(String str, Object obj) {
        return (PullResponse) super.set(str, obj);
    }

    public PullResponse setReceivedMessages(List<ReceivedMessage> list) {
        this.receivedMessages = list;
        return this;
    }
}
