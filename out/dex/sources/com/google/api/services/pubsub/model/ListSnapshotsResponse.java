package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class ListSnapshotsResponse extends GenericJson {

    @Key
    private String nextPageToken;

    @Key
    private List<Snapshot> snapshots;

    static {
        Data.nullOf(Snapshot.class);
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public ListSnapshotsResponse clone() {
        return (ListSnapshotsResponse) super.clone();
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public List<Snapshot> getSnapshots() {
        return this.snapshots;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public ListSnapshotsResponse set(String str, Object obj) {
        return (ListSnapshotsResponse) super.set(str, obj);
    }

    public ListSnapshotsResponse setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public ListSnapshotsResponse setSnapshots(List<Snapshot> list) {
        this.snapshots = list;
        return this;
    }
}
