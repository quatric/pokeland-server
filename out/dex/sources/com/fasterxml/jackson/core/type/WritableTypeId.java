package com.fasterxml.jackson.core.type;

import com.fasterxml.jackson.core.JsonToken;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class WritableTypeId {
    public String asProperty;
    public Object extra;
    public Object forValue;
    public Class<?> forValueType;

    /* JADX INFO: renamed from: id */
    public Object f271id;
    public Inclusion include;
    public JsonToken valueShape;
    public boolean wrapperWritten;

    public enum Inclusion {
        WRAPPER_ARRAY,
        WRAPPER_OBJECT,
        METADATA_PROPERTY,
        PAYLOAD_PROPERTY,
        PARENT_PROPERTY;

        public boolean requiresObjectContext() {
            return this == METADATA_PROPERTY || this == PAYLOAD_PROPERTY;
        }
    }

    public WritableTypeId() {
    }

    public WritableTypeId(Object obj, JsonToken jsonToken) {
        this(obj, jsonToken, (Object) null);
    }

    public WritableTypeId(Object obj, JsonToken jsonToken, Object obj2) {
        this.forValue = obj;
        this.f271id = obj2;
        this.valueShape = jsonToken;
    }

    public WritableTypeId(Object obj, Class<?> cls, JsonToken jsonToken) {
        this(obj, jsonToken, (Object) null);
        this.forValueType = cls;
    }
}
