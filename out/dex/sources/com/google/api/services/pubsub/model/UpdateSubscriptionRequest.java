package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class UpdateSubscriptionRequest extends GenericJson {

    @Key
    private Subscription subscription;

    @Key
    private String updateMask;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public UpdateSubscriptionRequest clone() {
        return (UpdateSubscriptionRequest) super.clone();
    }

    public Subscription getSubscription() {
        return this.subscription;
    }

    public String getUpdateMask() {
        return this.updateMask;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public UpdateSubscriptionRequest set(String str, Object obj) {
        return (UpdateSubscriptionRequest) super.set(str, obj);
    }

    public UpdateSubscriptionRequest setSubscription(Subscription subscription) {
        this.subscription = subscription;
        return this;
    }

    public UpdateSubscriptionRequest setUpdateMask(String str) {
        this.updateMask = str;
        return this;
    }
}
