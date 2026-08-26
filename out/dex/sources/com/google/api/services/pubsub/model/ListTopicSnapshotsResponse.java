package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class ListTopicSnapshotsResponse extends GenericJson {

    @Key
    private String nextPageToken;

    @Key
    private List<String> snapshots;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public ListTopicSnapshotsResponse clone() {
        return (ListTopicSnapshotsResponse) super.clone();
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public List<String> getSnapshots() {
        return this.snapshots;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public ListTopicSnapshotsResponse set(String str, Object obj) {
        return (ListTopicSnapshotsResponse) super.set(str, obj);
    }

    public ListTopicSnapshotsResponse setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public ListTopicSnapshotsResponse setSnapshots(List<String> list) {
        this.snapshots = list;
        return this;
    }
}
