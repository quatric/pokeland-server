package com.google.api.client.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class ArrayValueMap {
    private final Object destination;
    private final Map<String, ArrayValue> keyMap = ArrayMap.create();
    private final Map<Field, ArrayValue> fieldMap = ArrayMap.create();

    static class ArrayValue {
        final Class<?> componentType;
        final ArrayList<Object> values = new ArrayList<>();

        ArrayValue(Class<?> cls) {
            this.componentType = cls;
        }

        void addValue(Class<?> cls, Object obj) {
            Preconditions.checkArgument(cls == this.componentType);
            this.values.add(obj);
        }

        Object toArray() {
            return Types.toArray(this.values, this.componentType);
        }
    }

    public ArrayValueMap(Object obj) {
        this.destination = obj;
    }

    public void put(String str, Class<?> cls, Object obj) {
        ArrayValue arrayValue = this.keyMap.get(str);
        if (arrayValue == null) {
            arrayValue = new ArrayValue(cls);
            this.keyMap.put(str, arrayValue);
        }
        arrayValue.addValue(cls, obj);
    }

    public void put(Field field, Class<?> cls, Object obj) {
        ArrayValue arrayValue = this.fieldMap.get(field);
        if (arrayValue == null) {
            arrayValue = new ArrayValue(cls);
            this.fieldMap.put(field, arrayValue);
        }
        arrayValue.addValue(cls, obj);
    }

    public void setValues() {
        for (Map.Entry<String, ArrayValue> entry : this.keyMap.entrySet()) {
            ((Map) this.destination).put(entry.getKey(), entry.getValue().toArray());
        }
        for (Map.Entry<Field, ArrayValue> entry2 : this.fieldMap.entrySet()) {
            FieldInfo.setFieldValue(entry2.getKey(), this.destination, entry2.getValue().toArray());
        }
    }
}
