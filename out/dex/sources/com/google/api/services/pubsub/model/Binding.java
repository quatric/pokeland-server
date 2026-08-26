package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Binding extends GenericJson {

    @Key
    private Expr condition;

    @Key
    private List<String> members;

    @Key
    private String role;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public Binding clone() {
        return (Binding) super.clone();
    }

    public Expr getCondition() {
        return this.condition;
    }

    public List<String> getMembers() {
        return this.members;
    }

    public String getRole() {
        return this.role;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public Binding set(String str, Object obj) {
        return (Binding) super.set(str, obj);
    }

    public Binding setCondition(Expr expr) {
        this.condition = expr;
        return this;
    }

    public Binding setMembers(List<String> list) {
        this.members = list;
        return this;
    }

    public Binding setRole(String str) {
        this.role = str;
        return this;
    }
}
