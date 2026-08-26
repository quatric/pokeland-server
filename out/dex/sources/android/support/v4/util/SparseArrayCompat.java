package android.support.v4.util;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class SparseArrayCompat<E> implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private int[] mKeys;
    private int mSize;
    private Object[] mValues;

    public SparseArrayCompat() {
        this(10);
    }

    public SparseArrayCompat(int i) {
        this.mGarbage = false;
        if (i == 0) {
            this.mKeys = ContainerHelpers.EMPTY_INTS;
            this.mValues = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            int iIdealIntArraySize = ContainerHelpers.idealIntArraySize(i);
            this.mKeys = new int[iIdealIntArraySize];
            this.mValues = new Object[iIdealIntArraySize];
        }
        this.mSize = 0;
    }

    /* JADX INFO: renamed from: gc */
    private void m3gc() {
        int i = this.mSize;
        int[] iArr = this.mKeys;
        Object[] objArr = this.mValues;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != DELETED) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.mGarbage = false;
        this.mSize = i2;
    }

    public void append(int i, E e) {
        int i2 = this.mSize;
        if (i2 != 0 && i <= this.mKeys[i2 - 1]) {
            put(i, e);
            return;
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            m3gc();
        }
        int i3 = this.mSize;
        if (i3 >= this.mKeys.length) {
            int iIdealIntArraySize = ContainerHelpers.idealIntArraySize(i3 + 1);
            int[] iArr = new int[iIdealIntArraySize];
            Object[] objArr = new Object[iIdealIntArraySize];
            int[] iArr2 = this.mKeys;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr2 = this.mValues;
            System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
            this.mKeys = iArr;
            this.mValues = objArr;
        }
        this.mKeys[i3] = i;
        this.mValues[i3] = e;
        this.mSize = i3 + 1;
    }

    public void clear() {
        int i = this.mSize;
        Object[] objArr = this.mValues;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.mSize = 0;
        this.mGarbage = false;
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SparseArrayCompat<E> m2011clone() {
        try {
            SparseArrayCompat<E> sparseArrayCompat = (SparseArrayCompat) super.clone();
            try {
                sparseArrayCompat.mKeys = (int[]) this.mKeys.clone();
                sparseArrayCompat.mValues = (Object[]) this.mValues.clone();
                return sparseArrayCompat;
            } catch (CloneNotSupportedException unused) {
                return sparseArrayCompat;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public void delete(int i) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (iBinarySearch >= 0) {
            Object[] objArr = this.mValues;
            Object obj = objArr[iBinarySearch];
            Object obj2 = DELETED;
            if (obj != obj2) {
                objArr[iBinarySearch] = obj2;
                this.mGarbage = true;
            }
        }
    }

    public E get(int i) {
        return get(i, null);
    }

    public E get(int i, E e) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (iBinarySearch >= 0) {
            Object[] objArr = this.mValues;
            if (objArr[iBinarySearch] != DELETED) {
                return (E) objArr[iBinarySearch];
            }
        }
        return e;
    }

    public int indexOfKey(int i) {
        if (this.mGarbage) {
            m3gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
    }

    public int indexOfValue(E e) {
        if (this.mGarbage) {
            m3gc();
        }
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == e) {
                return i;
            }
        }
        return -1;
    }

    public int keyAt(int i) {
        if (this.mGarbage) {
            m3gc();
        }
        return this.mKeys[i];
    }

    public void put(int i, E e) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (iBinarySearch >= 0) {
            this.mValues[iBinarySearch] = e;
            return;
        }
        int iBinarySearch2 = iBinarySearch ^ (-1);
        if (iBinarySearch2 < this.mSize) {
            Object[] objArr = this.mValues;
            if (objArr[iBinarySearch2] == DELETED) {
                this.mKeys[iBinarySearch2] = i;
                objArr[iBinarySearch2] = e;
                return;
            }
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            m3gc();
            iBinarySearch2 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i) ^ (-1);
        }
        int i2 = this.mSize;
        if (i2 >= this.mKeys.length) {
            int iIdealIntArraySize = ContainerHelpers.idealIntArraySize(i2 + 1);
            int[] iArr = new int[iIdealIntArraySize];
            Object[] objArr2 = new Object[iIdealIntArraySize];
            int[] iArr2 = this.mKeys;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr3 = this.mValues;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.mKeys = iArr;
            this.mValues = objArr2;
        }
        int i3 = this.mSize;
        if (i3 - iBinarySearch2 != 0) {
            int[] iArr3 = this.mKeys;
            int i4 = iBinarySearch2 + 1;
            System.arraycopy(iArr3, iBinarySearch2, iArr3, i4, i3 - iBinarySearch2);
            Object[] objArr4 = this.mValues;
            System.arraycopy(objArr4, iBinarySearch2, objArr4, i4, this.mSize - iBinarySearch2);
        }
        this.mKeys[iBinarySearch2] = i;
        this.mValues[iBinarySearch2] = e;
        this.mSize++;
    }

    public void remove(int i) {
        delete(i);
    }

    public void removeAt(int i) {
        Object[] objArr = this.mValues;
        Object obj = objArr[i];
        Object obj2 = DELETED;
        if (obj != obj2) {
            objArr[i] = obj2;
            this.mGarbage = true;
        }
    }

    public void removeAtRange(int i, int i2) {
        int iMin = Math.min(this.mSize, i2 + i);
        while (i < iMin) {
            removeAt(i);
            i++;
        }
    }

    public void setValueAt(int i, E e) {
        if (this.mGarbage) {
            m3gc();
        }
        this.mValues[i] = e;
    }

    public int size() {
        if (this.mGarbage) {
            m3gc();
        }
        return this.mSize;
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 28);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt(i));
            sb.append('=');
            E eValueAt = valueAt(i);
            if (eValueAt != this) {
                sb.append(eValueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public E valueAt(int i) {
        if (this.mGarbage) {
            m3gc();
        }
        return (E) this.mValues[i];
    }
}
