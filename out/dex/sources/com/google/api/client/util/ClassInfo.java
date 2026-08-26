package com.google.api.client.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class ClassInfo {
    private static final Map<Class<?>, ClassInfo> CACHE = new WeakHashMap();
    private static final Map<Class<?>, ClassInfo> CACHE_IGNORE_CASE = new WeakHashMap();
    private final Class<?> clazz;
    private final boolean ignoreCase;
    private final IdentityHashMap<String, FieldInfo> nameToFieldInfoMap = new IdentityHashMap<>();
    final List<String> names;

    private ClassInfo(Class<?> cls, boolean z) {
        this.clazz = cls;
        this.ignoreCase = z;
        Preconditions.checkArgument((z && cls.isEnum()) ? false : true, "cannot ignore case on an enum: " + cls);
        TreeSet treeSet = new TreeSet(new Comparator<String>() { // from class: com.google.api.client.util.ClassInfo.1
            @Override // java.util.Comparator
            public int compare(String str, String str2) {
                if (Objects.equal(str, str2)) {
                    return 0;
                }
                if (str == null) {
                    return -1;
                }
                if (str2 == null) {
                    return 1;
                }
                return str.compareTo(str2);
            }
        });
        for (Field field : cls.getDeclaredFields()) {
            FieldInfo fieldInfoM458of = FieldInfo.m458of(field);
            if (fieldInfoM458of != null) {
                String name = fieldInfoM458of.getName();
                name = z ? name.toLowerCase(Locale.US).intern() : name;
                FieldInfo fieldInfo = this.nameToFieldInfoMap.get(name);
                boolean z2 = fieldInfo == null;
                Object[] objArr = new Object[4];
                objArr[0] = z ? "case-insensitive " : "";
                objArr[1] = name;
                objArr[2] = field;
                objArr[3] = fieldInfo == null ? null : fieldInfo.getField();
                Preconditions.checkArgument(z2, "two fields have the same %sname <%s>: %s and %s", objArr);
                this.nameToFieldInfoMap.put(name, fieldInfoM458of);
                treeSet.add(name);
            }
        }
        Class<? super Object> superclass = cls.getSuperclass();
        if (superclass != null) {
            ClassInfo classInfoM456of = m456of(superclass, z);
            treeSet.addAll(classInfoM456of.names);
            for (Map.Entry<String, FieldInfo> entry : classInfoM456of.nameToFieldInfoMap.entrySet()) {
                String key = entry.getKey();
                if (!this.nameToFieldInfoMap.containsKey(key)) {
                    this.nameToFieldInfoMap.put(key, entry.getValue());
                }
            }
        }
        this.names = treeSet.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList(treeSet));
    }

    /* JADX INFO: renamed from: of */
    public static ClassInfo m455of(Class<?> cls) {
        return m456of(cls, false);
    }

    /* JADX INFO: renamed from: of */
    public static ClassInfo m456of(Class<?> cls, boolean z) {
        ClassInfo classInfo;
        if (cls == null) {
            return null;
        }
        Map<Class<?>, ClassInfo> map = z ? CACHE_IGNORE_CASE : CACHE;
        synchronized (map) {
            classInfo = map.get(cls);
            if (classInfo == null) {
                classInfo = new ClassInfo(cls, z);
                map.put(cls, classInfo);
            }
        }
        return classInfo;
    }

    public Field getField(String str) {
        FieldInfo fieldInfo = getFieldInfo(str);
        if (fieldInfo == null) {
            return null;
        }
        return fieldInfo.getField();
    }

    public FieldInfo getFieldInfo(String str) {
        if (str != null) {
            if (this.ignoreCase) {
                str = str.toLowerCase(Locale.US);
            }
            str = str.intern();
        }
        return this.nameToFieldInfoMap.get(str);
    }

    public Collection<FieldInfo> getFieldInfos() {
        return Collections.unmodifiableCollection(this.nameToFieldInfoMap.values());
    }

    public final boolean getIgnoreCase() {
        return this.ignoreCase;
    }

    public Collection<String> getNames() {
        return this.names;
    }

    public Class<?> getUnderlyingClass() {
        return this.clazz;
    }

    public boolean isEnum() {
        return this.clazz.isEnum();
    }
}
