package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class UpdateSnapshotRequest extends GenericJson {

    @Key
    private Snapshot snapshot;

    @Key
    private String updateMask;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public UpdateSnapshotRequest clone() {
        return (UpdateSnapshotRequest) super.clone();
    }

    public Snapshot getSnapshot() {
        return this.snapshot;
    }

    public String getUpdateMask() {
        return this.updateMask;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public UpdateSnapshotRequest set(String str, Object obj) {
        return (UpdateSnapshotRequest) super.set(str, obj);
    }

    public UpdateSnapshotRequest setSnapshot(Snapshot snapshot) {
        this.snapshot = snapshot;
        return this;
    }

    public UpdateSnapshotRequest setUpdateMask(String str) {
        this.updateMask = str;
        return this;
    }
}
