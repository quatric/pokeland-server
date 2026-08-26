package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class ListSubscriptionsResponse extends GenericJson {

    @Key
    private String nextPageToken;

    @Key
    private List<Subscription> subscriptions;

    static {
        Data.nullOf(Subscription.class);
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public ListSubscriptionsResponse clone() {
        return (ListSubscriptionsResponse) super.clone();
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public List<Subscription> getSubscriptions() {
        return this.subscriptions;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public ListSubscriptionsResponse set(String str, Object obj) {
        return (ListSubscriptionsResponse) super.set(str, obj);
    }

    public ListSubscriptionsResponse setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public ListSubscriptionsResponse setSubscriptions(List<Subscription> list) {
        this.subscriptions = list;
        return this;
    }
}
