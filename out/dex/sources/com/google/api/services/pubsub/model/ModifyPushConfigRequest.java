package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class ModifyPushConfigRequest extends GenericJson {

    @Key
    private PushConfig pushConfig;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public ModifyPushConfigRequest clone() {
        return (ModifyPushConfigRequest) super.clone();
    }

    public PushConfig getPushConfig() {
        return this.pushConfig;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public ModifyPushConfigRequest set(String str, Object obj) {
        return (ModifyPushConfigRequest) super.set(str, obj);
    }

    public ModifyPushConfigRequest setPushConfig(PushConfig pushConfig) {
        this.pushConfig = pushConfig;
        return this;
    }
}
