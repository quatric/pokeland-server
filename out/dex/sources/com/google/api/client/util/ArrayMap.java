package com.google.api.client.util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class ArrayMap<K, V> extends AbstractMap<K, V> implements Cloneable {
    private Object[] data;
    int size;

    final class Entry implements Map.Entry<K, V> {
        private int index;

        Entry(int i) {
            this.index = i;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return Objects.equal(getKey(), entry.getKey()) && Objects.equal(getValue(), entry.getValue());
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return (K) ArrayMap.this.getKey(this.index);
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return (V) ArrayMap.this.getValue(this.index);
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            Object key = getKey();
            Object value = getValue();
            return (key != null ? key.hashCode() : 0) ^ (value != null ? value.hashCode() : 0);
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            return (V) ArrayMap.this.set(this.index, v);
        }
    }

    final class EntryIterator implements Iterator<Map.Entry<K, V>> {
        private int nextIndex;
        private boolean removed;

        EntryIterator() {
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.nextIndex < ArrayMap.this.size;
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            int i = this.nextIndex;
            if (i == ArrayMap.this.size) {
                throw new NoSuchElementException();
            }
            this.nextIndex++;
            return new Entry(i);
        }

        @Override // java.util.Iterator
        public void remove() {
            int i = this.nextIndex - 1;
            if (this.removed || i < 0) {
                throw new IllegalArgumentException();
            }
            ArrayMap.this.remove(i);
            this.removed = true;
        }
    }

    final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        EntrySet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return ArrayMap.this.size;
        }
    }

    public static <K, V> ArrayMap<K, V> create() {
        return new ArrayMap<>();
    }

    public static <K, V> ArrayMap<K, V> create(int i) {
        ArrayMap<K, V> arrayMapCreate = create();
        arrayMapCreate.ensureCapacity(i);
        return arrayMapCreate;
    }

    private int getDataIndexOfKey(Object obj) {
        int i = this.size << 1;
        Object[] objArr = this.data;
        for (int i2 = 0; i2 < i; i2 += 2) {
            Object obj2 = objArr[i2];
            if (obj == null) {
                if (obj2 == null) {
                    return i2;
                }
            } else {
                if (obj.equals(obj2)) {
                    return i2;
                }
            }
        }
        return -2;
    }

    /* JADX INFO: renamed from: of */
    public static <K, V> ArrayMap<K, V> m454of(Object... objArr) {
        ArrayMap<K, V> arrayMapCreate = create(1);
        int length = objArr.length;
        if (1 == length % 2) {
            throw new IllegalArgumentException("missing value for last key: " + objArr[length - 1]);
        }
        arrayMapCreate.size = objArr.length / 2;
        Object[] objArr2 = new Object[length];
        ((ArrayMap) arrayMapCreate).data = objArr2;
        System.arraycopy(objArr, 0, objArr2, 0, length);
        return arrayMapCreate;
    }

    private V removeFromDataIndexOfKey(int i) {
        int i2 = this.size << 1;
        if (i < 0 || i >= i2) {
            return null;
        }
        V vValueAtDataIndex = valueAtDataIndex(i + 1);
        Object[] objArr = this.data;
        int i3 = (i2 - i) - 2;
        if (i3 != 0) {
            System.arraycopy(objArr, i + 2, objArr, i, i3);
        }
        this.size--;
        setData(i2 - 2, null, null);
        return vValueAtDataIndex;
    }

    private void setData(int i, K k, V v) {
        Object[] objArr = this.data;
        objArr[i] = k;
        objArr[i + 1] = v;
    }

    private void setDataCapacity(int i) {
        if (i == 0) {
            this.data = null;
            return;
        }
        int i2 = this.size;
        Object[] objArr = this.data;
        if (i2 == 0 || i != objArr.length) {
            Object[] objArr2 = new Object[i];
            this.data = objArr2;
            if (i2 != 0) {
                System.arraycopy(objArr, 0, objArr2, 0, i2 << 1);
            }
        }
    }

    private V valueAtDataIndex(int i) {
        if (i < 0) {
            return null;
        }
        return (V) this.data[i];
    }

    public final void add(K k, V v) {
        set(this.size, k, v);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this.size = 0;
        this.data = null;
    }

    @Override // java.util.AbstractMap
    public ArrayMap<K, V> clone() {
        try {
            ArrayMap<K, V> arrayMap = (ArrayMap) super.clone();
            Object[] objArr = this.data;
            if (objArr != null) {
                int length = objArr.length;
                Object[] objArr2 = new Object[length];
                arrayMap.data = objArr2;
                System.arraycopy(objArr, 0, objArr2, 0, length);
            }
            return arrayMap;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object obj) {
        return -2 != getDataIndexOfKey(obj);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsValue(Object obj) {
        int i = this.size << 1;
        Object[] objArr = this.data;
        for (int i2 = 1; i2 < i; i2 += 2) {
            Object obj2 = objArr[i2];
            if (obj == null) {
                if (obj2 == null) {
                    return true;
                }
            } else {
                if (obj.equals(obj2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void ensureCapacity(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException();
        }
        Object[] objArr = this.data;
        int i2 = i << 1;
        int length = objArr == null ? 0 : objArr.length;
        if (i2 > length) {
            int i3 = ((length / 2) * 3) + 1;
            if (i3 % 2 != 0) {
                i3++;
            }
            if (i3 >= i2) {
                i2 = i3;
            }
            setDataCapacity(i2);
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final V get(Object obj) {
        return valueAtDataIndex(getDataIndexOfKey(obj) + 1);
    }

    public final int getIndexOfKey(K k) {
        return getDataIndexOfKey(k) >> 1;
    }

    public final K getKey(int i) {
        if (i < 0 || i >= this.size) {
            return null;
        }
        return (K) this.data[i << 1];
    }

    public final V getValue(int i) {
        if (i < 0 || i >= this.size) {
            return null;
        }
        return valueAtDataIndex((i << 1) + 1);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final V put(K k, V v) {
        int indexOfKey = getIndexOfKey(k);
        if (indexOfKey == -1) {
            indexOfKey = this.size;
        }
        return set(indexOfKey, k, v);
    }

    public final V remove(int i) {
        return removeFromDataIndexOfKey(i << 1);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final V remove(Object obj) {
        return removeFromDataIndexOfKey(getDataIndexOfKey(obj));
    }

    public final V set(int i, V v) {
        int i2 = this.size;
        if (i < 0 || i >= i2) {
            throw new IndexOutOfBoundsException();
        }
        int i3 = (i << 1) + 1;
        V vValueAtDataIndex = valueAtDataIndex(i3);
        this.data[i3] = v;
        return vValueAtDataIndex;
    }

    public final V set(int i, K k, V v) {
        if (i < 0) {
            throw new IndexOutOfBoundsException();
        }
        int i2 = i + 1;
        ensureCapacity(i2);
        int i3 = i << 1;
        V vValueAtDataIndex = valueAtDataIndex(i3 + 1);
        setData(i3, k, v);
        if (i2 > this.size) {
            this.size = i2;
        }
        return vValueAtDataIndex;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        return this.size;
    }

    public final void trim() {
        setDataCapacity(this.size << 1);
    }
}
