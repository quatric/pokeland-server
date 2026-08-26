package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class PullRequest extends GenericJson {

    @Key
    private Integer maxMessages;

    @Key
    private Boolean returnImmediately;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public PullRequest clone() {
        return (PullRequest) super.clone();
    }

    public Integer getMaxMessages() {
        return this.maxMessages;
    }

    public Boolean getReturnImmediately() {
        return this.returnImmediately;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public PullRequest set(String str, Object obj) {
        return (PullRequest) super.set(str, obj);
    }

    public PullRequest setMaxMessages(Integer num) {
        this.maxMessages = num;
        return this;
    }

    public PullRequest setReturnImmediately(Boolean bool) {
        this.returnImmediately = bool;
        return this;
    }
}
