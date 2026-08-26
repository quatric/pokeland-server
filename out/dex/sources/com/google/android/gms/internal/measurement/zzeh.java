package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzeh extends zzdj<Double> implements zzff<Double>, zzgu, RandomAccess {
    private static final zzeh zzaeo;
    private int size;
    private double[] zzaep;

    static {
        zzeh zzehVar = new zzeh(new double[0], 0);
        zzaeo = zzehVar;
        zzehVar.zzry();
    }

    zzeh() {
        this(new double[10], 0);
    }

    private zzeh(double[] dArr, int i) {
        this.zzaep = dArr;
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

    private final void zzc(int i, double d) {
        int i2;
        zzrz();
        if (i < 0 || i > (i2 = this.size)) {
            throw new IndexOutOfBoundsException(zzao(i));
        }
        double[] dArr = this.zzaep;
        if (i2 < dArr.length) {
            System.arraycopy(dArr, i, dArr, i + 1, i2 - i);
        } else {
            double[] dArr2 = new double[((i2 * 3) / 2) + 1];
            System.arraycopy(dArr, 0, dArr2, 0, i);
            System.arraycopy(this.zzaep, i, dArr2, i + 1, this.size - i);
            this.zzaep = dArr2;
        }
        this.zzaep[i] = d;
        this.size++;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        zzc(i, ((Double) obj).doubleValue());
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Double> collection) {
        zzrz();
        zzez.checkNotNull(collection);
        if (!(collection instanceof zzeh)) {
            return super.addAll(collection);
        }
        zzeh zzehVar = (zzeh) collection;
        int i = zzehVar.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        double[] dArr = this.zzaep;
        if (i3 > dArr.length) {
            this.zzaep = Arrays.copyOf(dArr, i3);
        }
        System.arraycopy(zzehVar.zzaep, 0, this.zzaep, this.size, zzehVar.size);
        this.size = i3;
        this.modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzeh)) {
            return super.equals(obj);
        }
        zzeh zzehVar = (zzeh) obj;
        if (this.size != zzehVar.size) {
            return false;
        }
        double[] dArr = zzehVar.zzaep;
        for (int i = 0; i < this.size; i++) {
            if (Double.doubleToLongBits(this.zzaep[i]) != Double.doubleToLongBits(dArr[i])) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        zzan(i);
        return Double.valueOf(this.zzaep[i]);
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int iZzbx = 1;
        for (int i = 0; i < this.size; i++) {
            iZzbx = (iZzbx * 31) + zzez.zzbx(Double.doubleToLongBits(this.zzaep[i]));
        }
        return iZzbx;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzrz();
        zzan(i);
        double[] dArr = this.zzaep;
        double d = dArr[i];
        int i2 = this.size;
        if (i < i2 - 1) {
            System.arraycopy(dArr, i + 1, dArr, i, (i2 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Double.valueOf(d);
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzrz();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Double.valueOf(this.zzaep[i]))) {
                double[] dArr = this.zzaep;
                System.arraycopy(dArr, i + 1, dArr, i, (this.size - i) - 1);
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
        double[] dArr = this.zzaep;
        System.arraycopy(dArr, i2, dArr, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i, Object obj) {
        double dDoubleValue = ((Double) obj).doubleValue();
        zzrz();
        zzan(i);
        double[] dArr = this.zzaep;
        double d = dArr[i];
        dArr[i] = dDoubleValue;
        return Double.valueOf(d);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.measurement.zzff
    public final /* synthetic */ zzff<Double> zzap(int i) {
        if (i >= this.size) {
            return new zzeh(Arrays.copyOf(this.zzaep, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final void zzf(double d) {
        zzc(this.size, d);
    }
}
