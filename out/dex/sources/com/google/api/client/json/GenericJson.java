package com.google.api.client.json;

import com.google.api.client.util.GenericData;
import com.google.api.client.util.Throwables;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class GenericJson extends GenericData implements Cloneable {
    private JsonFactory jsonFactory;

    @Override // com.google.api.client.util.GenericData, java.util.AbstractMap
    public GenericJson clone() {
        return (GenericJson) super.clone();
    }

    public final JsonFactory getFactory() {
        return this.jsonFactory;
    }

    @Override // com.google.api.client.util.GenericData
    public GenericJson set(String str, Object obj) {
        return (GenericJson) super.set(str, obj);
    }

    public final void setFactory(JsonFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
    }

    public String toPrettyString() throws IOException {
        JsonFactory jsonFactory = this.jsonFactory;
        return jsonFactory != null ? jsonFactory.toPrettyString(this) : super.toString();
    }

    @Override // java.util.AbstractMap
    public String toString() {
        JsonFactory jsonFactory = this.jsonFactory;
        if (jsonFactory == null) {
            return super.toString();
        }
        try {
            return jsonFactory.toString(this);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
