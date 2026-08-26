package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Base64;
import com.google.api.client.util.Key;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class PubsubMessage extends GenericJson {

    @Key
    private Map<String, String> attributes;

    @Key
    private String data;

    @Key
    private String messageId;

    @Key
    private String publishTime;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public PubsubMessage clone() {
        return (PubsubMessage) super.clone();
    }

    public byte[] decodeData() {
        return Base64.decodeBase64(this.data);
    }

    public PubsubMessage encodeData(byte[] bArr) {
        this.data = Base64.encodeBase64URLSafeString(bArr);
        return this;
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public String getData() {
        return this.data;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public String getPublishTime() {
        return this.publishTime;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public PubsubMessage set(String str, Object obj) {
        return (PubsubMessage) super.set(str, obj);
    }

    public PubsubMessage setAttributes(Map<String, String> map) {
        this.attributes = map;
        return this;
    }

    public PubsubMessage setData(String str) {
        this.data = str;
        return this;
    }

    public PubsubMessage setMessageId(String str) {
        this.messageId = str;
        return this;
    }

    public PubsubMessage setPublishTime(String str) {
        this.publishTime = str;
        return this;
    }
}
