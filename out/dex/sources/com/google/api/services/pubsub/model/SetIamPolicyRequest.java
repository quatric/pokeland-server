package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class SetIamPolicyRequest extends GenericJson {

    @Key
    private Policy policy;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public SetIamPolicyRequest clone() {
        return (SetIamPolicyRequest) super.clone();
    }

    public Policy getPolicy() {
        return this.policy;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public SetIamPolicyRequest set(String str, Object obj) {
        return (SetIamPolicyRequest) super.set(str, obj);
    }

    public SetIamPolicyRequest setPolicy(Policy policy) {
        this.policy = policy;
        return this;
    }
}
