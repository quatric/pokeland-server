package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class TestIamPermissionsResponse extends GenericJson {

    @Key
    private List<String> permissions;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public TestIamPermissionsResponse clone() {
        return (TestIamPermissionsResponse) super.clone();
    }

    public List<String> getPermissions() {
        return this.permissions;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public TestIamPermissionsResponse set(String str, Object obj) {
        return (TestIamPermissionsResponse) super.set(str, obj);
    }

    public TestIamPermissionsResponse setPermissions(List<String> list) {
        this.permissions = list;
        return this;
    }
}
