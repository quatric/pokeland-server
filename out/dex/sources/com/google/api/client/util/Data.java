package com.google.api.client.util;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class Data {
    public static final Boolean NULL_BOOLEAN = new Boolean(true);
    public static final String NULL_STRING = new String();
    public static final Character NULL_CHARACTER = new Character(0);
    public static final Byte NULL_BYTE = new Byte((byte) 0);
    public static final Short NULL_SHORT = new Short((short) 0);
    public static final Integer NULL_INTEGER = new Integer(0);
    public static final Float NULL_FLOAT = new Float(0.0f);
    public static final Long NULL_LONG = new Long(0);
    public static final Double NULL_DOUBLE = new Double(0.0d);
    public static final BigInteger NULL_BIG_INTEGER = new BigInteger("0");
    public static final BigDecimal NULL_BIG_DECIMAL = new BigDecimal("0");
    public static final DateTime NULL_DATE_TIME = new DateTime(0);
    private static final ConcurrentHashMap<Class<?>, Object> NULL_CACHE = new ConcurrentHashMap<>();

    static {
        NULL_CACHE.put(Boolean.class, NULL_BOOLEAN);
        NULL_CACHE.put(String.class, NULL_STRING);
        NULL_CACHE.put(Character.class, NULL_CHARACTER);
        NULL_CACHE.put(Byte.class, NULL_BYTE);
        NULL_CACHE.put(Short.class, NULL_SHORT);
        NULL_CACHE.put(Integer.class, NULL_INTEGER);
        NULL_CACHE.put(Float.class, NULL_FLOAT);
        NULL_CACHE.put(Long.class, NULL_LONG);
        NULL_CACHE.put(Double.class, NULL_DOUBLE);
        NULL_CACHE.put(BigInteger.class, NULL_BIG_INTEGER);
        NULL_CACHE.put(BigDecimal.class, NULL_BIG_DECIMAL);
        NULL_CACHE.put(DateTime.class, NULL_DATE_TIME);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T clone(T t) {
        T t2;
        if (t == 0 || isPrimitive(t.getClass())) {
            return t;
        }
        if (t instanceof GenericData) {
            return (T) ((GenericData) t).clone();
        }
        Class<?> cls = t.getClass();
        if (cls.isArray()) {
            t2 = (T) Array.newInstance(cls.getComponentType(), Array.getLength(t));
        } else if (t instanceof ArrayMap) {
            t2 = (T) ((ArrayMap) t).clone();
        } else {
            if ("java.util.Arrays$ArrayList".equals(cls.getName())) {
                Object[] array = ((List) t).toArray();
                deepCopy(array, array);
                return (T) Arrays.asList(array);
            }
            t2 = (T) Types.newInstance(cls);
        }
        deepCopy(t, t2);
        return t2;
    }

    public static void deepCopy(Object obj, Object obj2) {
        Class<?> cls = obj.getClass();
        int i = 0;
        Preconditions.checkArgument(cls == obj2.getClass());
        if (cls.isArray()) {
            Preconditions.checkArgument(Array.getLength(obj) == Array.getLength(obj2));
            Iterator it = Types.iterableOf(obj).iterator();
            while (it.hasNext()) {
                Array.set(obj2, i, clone(it.next()));
                i++;
            }
            return;
        }
        if (Collection.class.isAssignableFrom(cls)) {
            Collection collection = (Collection) obj;
            if (ArrayList.class.isAssignableFrom(cls)) {
                ((ArrayList) obj2).ensureCapacity(collection.size());
            }
            Collection collection2 = (Collection) obj2;
            Iterator it2 = collection.iterator();
            while (it2.hasNext()) {
                collection2.add(clone(it2.next()));
            }
            return;
        }
        boolean zIsAssignableFrom = GenericData.class.isAssignableFrom(cls);
        if (zIsAssignableFrom || !Map.class.isAssignableFrom(cls)) {
            ClassInfo classInfoM455of = zIsAssignableFrom ? ((GenericData) obj).classInfo : ClassInfo.m455of(cls);
            Iterator<String> it3 = classInfoM455of.names.iterator();
            while (it3.hasNext()) {
                FieldInfo fieldInfo = classInfoM455of.getFieldInfo(it3.next());
                if (!fieldInfo.isFinal() && (!zIsAssignableFrom || !fieldInfo.isPrimitive())) {
                    Object value = fieldInfo.getValue(obj);
                    if (value != null) {
                        fieldInfo.setValue(obj2, clone(value));
                    }
                }
            }
            return;
        }
        if (!ArrayMap.class.isAssignableFrom(cls)) {
            Map map = (Map) obj2;
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                map.put(entry.getKey(), clone(entry.getValue()));
            }
            return;
        }
        ArrayMap arrayMap = (ArrayMap) obj2;
        ArrayMap arrayMap2 = (ArrayMap) obj;
        int size = arrayMap2.size();
        while (i < size) {
            arrayMap.set(i, clone(arrayMap2.getValue(i)));
            i++;
        }
    }

    public static boolean isNull(Object obj) {
        return obj != null && obj == NULL_CACHE.get(obj.getClass());
    }

    public static boolean isPrimitive(Type type) {
        if (type instanceof WildcardType) {
            type = Types.getBound((WildcardType) type);
        }
        if (!(type instanceof Class)) {
            return false;
        }
        Class cls = (Class) type;
        return cls.isPrimitive() || cls == Character.class || cls == String.class || cls == Integer.class || cls == Long.class || cls == Short.class || cls == Byte.class || cls == Float.class || cls == Double.class || cls == BigInteger.class || cls == BigDecimal.class || cls == DateTime.class || cls == Boolean.class;
    }

    public static boolean isValueOfPrimitiveType(Object obj) {
        return obj == null || isPrimitive(obj.getClass());
    }

    public static Map<String, Object> mapOf(Object obj) {
        if (obj == null || isNull(obj)) {
            return Collections.emptyMap();
        }
        return obj instanceof Map ? (Map) obj : new DataMap(obj, false);
    }

    public static Collection<Object> newCollectionInstance(Type type) {
        if (type instanceof WildcardType) {
            type = Types.getBound((WildcardType) type);
        }
        if (type instanceof ParameterizedType) {
            type = ((ParameterizedType) type).getRawType();
        }
        Class cls = type instanceof Class ? (Class) type : null;
        if (type == null || (type instanceof GenericArrayType) || (cls != null && (cls.isArray() || cls.isAssignableFrom(ArrayList.class)))) {
            return new ArrayList();
        }
        if (cls != null) {
            if (cls.isAssignableFrom(HashSet.class)) {
                return new HashSet();
            }
            return cls.isAssignableFrom(TreeSet.class) ? new TreeSet() : (Collection) Types.newInstance(cls);
        }
        throw new IllegalArgumentException("unable to create new instance of type: " + type);
    }

    public static Map<String, Object> newMapInstance(Class<?> cls) {
        if (cls == null || cls.isAssignableFrom(ArrayMap.class)) {
            return ArrayMap.create();
        }
        return cls.isAssignableFrom(TreeMap.class) ? new TreeMap() : (Map) Types.newInstance(cls);
    }

    public static <T> T nullOf(Class<?> cls) {
        Object objNewInstance = (T) NULL_CACHE.get(cls);
        if (objNewInstance == null) {
            synchronized (NULL_CACHE) {
                objNewInstance = NULL_CACHE.get(cls);
                if (objNewInstance == null) {
                    int i = 0;
                    if (cls.isArray()) {
                        Class<?> componentType = cls;
                        do {
                            componentType = componentType.getComponentType();
                            i++;
                        } while (componentType.isArray());
                        objNewInstance = (T) Array.newInstance(componentType, new int[i]);
                    } else if (cls.isEnum()) {
                        FieldInfo fieldInfo = ClassInfo.m455of(cls).getFieldInfo(null);
                        Preconditions.checkNotNull(fieldInfo, "enum missing constant with @NullValue annotation: %s", cls);
                        objNewInstance = fieldInfo.enumValue();
                    } else {
                        objNewInstance = Types.newInstance(cls);
                    }
                    NULL_CACHE.put(cls, objNewInstance);
                }
            }
        }
        return (T) objNewInstance;
    }

    public static Object parsePrimitiveValue(Type type, String str) {
        Class cls = type instanceof Class ? (Class) type : null;
        if (type == null || cls != null) {
            if (cls == Void.class) {
                return null;
            }
            if (str == null || cls == null || cls.isAssignableFrom(String.class)) {
                return str;
            }
            if (cls == Character.class || cls == Character.TYPE) {
                if (str.length() == 1) {
                    return Character.valueOf(str.charAt(0));
                }
                throw new IllegalArgumentException("expected type Character/char but got " + cls);
            }
            if (cls == Boolean.class || cls == Boolean.TYPE) {
                return Boolean.valueOf(str);
            }
            if (cls == Byte.class || cls == Byte.TYPE) {
                return Byte.valueOf(str);
            }
            if (cls == Short.class || cls == Short.TYPE) {
                return Short.valueOf(str);
            }
            if (cls == Integer.class || cls == Integer.TYPE) {
                return Integer.valueOf(str);
            }
            if (cls == Long.class || cls == Long.TYPE) {
                return Long.valueOf(str);
            }
            if (cls == Float.class || cls == Float.TYPE) {
                return Float.valueOf(str);
            }
            if (cls == Double.class || cls == Double.TYPE) {
                return Double.valueOf(str);
            }
            if (cls == DateTime.class) {
                return DateTime.parseRfc3339(str);
            }
            if (cls == BigInteger.class) {
                return new BigInteger(str);
            }
            if (cls == BigDecimal.class) {
                return new BigDecimal(str);
            }
            if (cls.isEnum()) {
                return ClassInfo.m455of(cls).getFieldInfo(str).enumValue();
            }
        }
        throw new IllegalArgumentException("expected primitive class, but got: " + type);
    }

    public static Type resolveWildcardTypeOrTypeVariable(List<Type> list, Type type) {
        if (type instanceof WildcardType) {
            type = Types.getBound((WildcardType) type);
        }
        while (type instanceof TypeVariable) {
            Type typeResolveTypeVariable = Types.resolveTypeVariable(list, (TypeVariable) type);
            if (typeResolveTypeVariable != null) {
                type = typeResolveTypeVariable;
            }
            if (type instanceof TypeVariable) {
                type = ((TypeVariable) type).getBounds()[0];
            }
        }
        return type;
    }
}
