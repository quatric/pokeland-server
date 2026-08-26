package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class UpdateTopicRequest extends GenericJson {

    @Key
    private Topic topic;

    @Key
    private String updateMask;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public UpdateTopicRequest clone() {
        return (UpdateTopicRequest) super.clone();
    }

    public Topic getTopic() {
        return this.topic;
    }

    public String getUpdateMask() {
        return this.updateMask;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public UpdateTopicRequest set(String str, Object obj) {
        return (UpdateTopicRequest) super.set(str, obj);
    }

    public UpdateTopicRequest setTopic(Topic topic) {
        this.topic = topic;
        return this;
    }

    public UpdateTopicRequest setUpdateMask(String str) {
        this.updateMask = str;
        return this;
    }
}
