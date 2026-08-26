package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Expr extends GenericJson {

    @Key
    private String description;

    @Key
    private String expression;

    @Key
    private String location;

    @Key
    private String title;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public Expr clone() {
        return (Expr) super.clone();
    }

    public String getDescription() {
        return this.description;
    }

    public String getExpression() {
        return this.expression;
    }

    public String getLocation() {
        return this.location;
    }

    public String getTitle() {
        return this.title;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public Expr set(String str, Object obj) {
        return (Expr) super.set(str, obj);
    }

    public Expr setDescription(String str) {
        this.description = str;
        return this;
    }

    public Expr setExpression(String str) {
        this.expression = str;
        return this;
    }

    public Expr setLocation(String str) {
        this.location = str;
        return this;
    }

    public Expr setTitle(String str) {
        this.title = str;
        return this;
    }
}
