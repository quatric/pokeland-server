package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzeu extends zzdj<Float> implements zzff<Float>, zzgu, RandomAccess {
    private static final zzeu zzahm;
    private int size;
    private float[] zzahn;

    static {
        zzeu zzeuVar = new zzeu(new float[0], 0);
        zzahm = zzeuVar;
        zzeuVar.zzry();
    }

    zzeu() {
        this(new float[10], 0);
    }

    private zzeu(float[] fArr, int i) {
        this.zzahn = fArr;
        this.size = i;
    }

    private final void zzan(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzao(i));
        }
    }

    private final String zzao(int i) {
        int i2 = this.size;
        StringBuilder sb = new StringBuilder(35);
        sb.append("Index:");
        sb.append(i);
        sb.append(", Size:");
        sb.append(i2);
        return sb.toString();
    }

    private final void zzc(int i, float f) {
        int i2;
        zzrz();
        if (i < 0 || i > (i2 = this.size)) {
            throw new IndexOutOfBoundsException(zzao(i));
        }
        float[] fArr = this.zzahn;
        if (i2 < fArr.length) {
            System.arraycopy(fArr, i, fArr, i + 1, i2 - i);
        } else {
            float[] fArr2 = new float[((i2 * 3) / 2) + 1];
            System.arraycopy(fArr, 0, fArr2, 0, i);
            System.arraycopy(this.zzahn, i, fArr2, i + 1, this.size - i);
            this.zzahn = fArr2;
        }
        this.zzahn[i] = f;
        this.size++;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        zzc(i, ((Float) obj).floatValue());
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Float> collection) {
        zzrz();
        zzez.checkNotNull(collection);
        if (!(collection instanceof zzeu)) {
            return super.addAll(collection);
        }
        zzeu zzeuVar = (zzeu) collection;
        int i = zzeuVar.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        float[] fArr = this.zzahn;
        if (i3 > fArr.length) {
            this.zzahn = Arrays.copyOf(fArr, i3);
        }
        System.arraycopy(zzeuVar.zzahn, 0, this.zzahn, this.size, zzeuVar.size);
        this.size = i3;
        this.modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzeu)) {
            return super.equals(obj);
        }
        zzeu zzeuVar = (zzeu) obj;
        if (this.size != zzeuVar.size) {
            return false;
        }
        float[] fArr = zzeuVar.zzahn;
        for (int i = 0; i < this.size; i++) {
            if (Float.floatToIntBits(this.zzahn[i]) != Float.floatToIntBits(fArr[i])) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        zzan(i);
        return Float.valueOf(this.zzahn[i]);
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int iFloatToIntBits = 1;
        for (int i = 0; i < this.size; i++) {
            iFloatToIntBits = (iFloatToIntBits * 31) + Float.floatToIntBits(this.zzahn[i]);
        }
        return iFloatToIntBits;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzrz();
        zzan(i);
        float[] fArr = this.zzahn;
        float f = fArr[i];
        int i2 = this.size;
        if (i < i2 - 1) {
            System.arraycopy(fArr, i + 1, fArr, i, (i2 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Float.valueOf(f);
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzrz();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Float.valueOf(this.zzahn[i]))) {
                float[] fArr = this.zzahn;
                System.arraycopy(fArr, i + 1, fArr, i, (this.size - i) - 1);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractList
    protected final void removeRange(int i, int i2) {
        zzrz();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        float[] fArr = this.zzahn;
        System.arraycopy(fArr, i2, fArr, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i, Object obj) {
        float fFloatValue = ((Float) obj).floatValue();
        zzrz();
        zzan(i);
        float[] fArr = this.zzahn;
        float f = fArr[i];
        fArr[i] = fFloatValue;
        return Float.valueOf(f);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.measurement.zzff
    public final /* synthetic */ zzff<Float> zzap(int i) {
        if (i >= this.size) {
            return new zzeu(Arrays.copyOf(this.zzahn, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final void zzc(float f) {
        zzc(this.size, f);
    }
}
