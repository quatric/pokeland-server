package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class ModifyAckDeadlineRequest extends GenericJson {

    @Key
    private Integer ackDeadlineSeconds;

    @Key
    private List<String> ackIds;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public ModifyAckDeadlineRequest clone() {
        return (ModifyAckDeadlineRequest) super.clone();
    }

    public Integer getAckDeadlineSeconds() {
        return this.ackDeadlineSeconds;
    }

    public List<String> getAckIds() {
        return this.ackIds;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public ModifyAckDeadlineRequest set(String str, Object obj) {
        return (ModifyAckDeadlineRequest) super.set(str, obj);
    }

    public ModifyAckDeadlineRequest setAckDeadlineSeconds(Integer num) {
        this.ackDeadlineSeconds = num;
        return this;
    }

    public ModifyAckDeadlineRequest setAckIds(List<String> list) {
        this.ackIds = list;
        return this;
    }
}
