package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Subscription extends GenericJson {

    @Key
    private Integer ackDeadlineSeconds;

    @Key
    private ExpirationPolicy expirationPolicy;

    @Key
    private Map<String, String> labels;

    @Key
    private String messageRetentionDuration;

    @Key
    private String name;

    @Key
    private PushConfig pushConfig;

    @Key
    private Boolean retainAckedMessages;

    @Key
    private String topic;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public Subscription clone() {
        return (Subscription) super.clone();
    }

    public Integer getAckDeadlineSeconds() {
        return this.ackDeadlineSeconds;
    }

    public ExpirationPolicy getExpirationPolicy() {
        return this.expirationPolicy;
    }

    public Map<String, String> getLabels() {
        return this.labels;
    }

    public String getMessageRetentionDuration() {
        return this.messageRetentionDuration;
    }

    public String getName() {
        return this.name;
    }

    public PushConfig getPushConfig() {
        return this.pushConfig;
    }

    public Boolean getRetainAckedMessages() {
        return this.retainAckedMessages;
    }

    public String getTopic() {
        return this.topic;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public Subscription set(String str, Object obj) {
        return (Subscription) super.set(str, obj);
    }

    public Subscription setAckDeadlineSeconds(Integer num) {
        this.ackDeadlineSeconds = num;
        return this;
    }

    public Subscription setExpirationPolicy(ExpirationPolicy expirationPolicy) {
        this.expirationPolicy = expirationPolicy;
        return this;
    }

    public Subscription setLabels(Map<String, String> map) {
        this.labels = map;
        return this;
    }

    public Subscription setMessageRetentionDuration(String str) {
        this.messageRetentionDuration = str;
        return this;
    }

    public Subscription setName(String str) {
        this.name = str;
        return this;
    }

    public Subscription setPushConfig(PushConfig pushConfig) {
        this.pushConfig = pushConfig;
        return this;
    }

    public Subscription setRetainAckedMessages(Boolean bool) {
        this.retainAckedMessages = bool;
        return this;
    }

    public Subscription setTopic(String str) {
        this.topic = str;
        return this;
    }
}
