package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzdn extends zzdj<Boolean> implements zzff<Boolean>, zzgu, RandomAccess {
    private static final zzdn zzade;
    private int size;
    private boolean[] zzadf;

    static {
        zzdn zzdnVar = new zzdn(new boolean[0], 0);
        zzade = zzdnVar;
        zzdnVar.zzry();
    }

    zzdn() {
        this(new boolean[10], 0);
    }

    private zzdn(boolean[] zArr, int i) {
        this.zzadf = zArr;
        this.size = i;
    }

    private final void zza(int i, boolean z) {
        int i2;
        zzrz();
        if (i < 0 || i > (i2 = this.size)) {
            throw new IndexOutOfBoundsException(zzao(i));
        }
        boolean[] zArr = this.zzadf;
        if (i2 < zArr.length) {
            System.arraycopy(zArr, i, zArr, i + 1, i2 - i);
        } else {
            boolean[] zArr2 = new boolean[((i2 * 3) / 2) + 1];
            System.arraycopy(zArr, 0, zArr2, 0, i);
            System.arraycopy(this.zzadf, i, zArr2, i + 1, this.size - i);
            this.zzadf = zArr2;
        }
        this.zzadf[i] = z;
        this.size++;
        this.modCount++;
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

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        zza(i, ((Boolean) obj).booleanValue());
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Boolean> collection) {
        zzrz();
        zzez.checkNotNull(collection);
        if (!(collection instanceof zzdn)) {
            return super.addAll(collection);
        }
        zzdn zzdnVar = (zzdn) collection;
        int i = zzdnVar.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        boolean[] zArr = this.zzadf;
        if (i3 > zArr.length) {
            this.zzadf = Arrays.copyOf(zArr, i3);
        }
        System.arraycopy(zzdnVar.zzadf, 0, this.zzadf, this.size, zzdnVar.size);
        this.size = i3;
        this.modCount++;
        return true;
    }

    public final void addBoolean(boolean z) {
        zza(this.size, z);
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzdn)) {
            return super.equals(obj);
        }
        zzdn zzdnVar = (zzdn) obj;
        if (this.size != zzdnVar.size) {
            return false;
        }
        boolean[] zArr = zzdnVar.zzadf;
        for (int i = 0; i < this.size; i++) {
            if (this.zzadf[i] != zArr[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        zzan(i);
        return Boolean.valueOf(this.zzadf[i]);
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int iZzs = 1;
        for (int i = 0; i < this.size; i++) {
            iZzs = (iZzs * 31) + zzez.zzs(this.zzadf[i]);
        }
        return iZzs;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzrz();
        zzan(i);
        boolean[] zArr = this.zzadf;
        boolean z = zArr[i];
        int i2 = this.size;
        if (i < i2 - 1) {
            System.arraycopy(zArr, i + 1, zArr, i, (i2 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Boolean.valueOf(z);
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzrz();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Boolean.valueOf(this.zzadf[i]))) {
                boolean[] zArr = this.zzadf;
                System.arraycopy(zArr, i + 1, zArr, i, (this.size - i) - 1);
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
        boolean[] zArr = this.zzadf;
        System.arraycopy(zArr, i2, zArr, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i, Object obj) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        zzrz();
        zzan(i);
        boolean[] zArr = this.zzadf;
        boolean z = zArr[i];
        zArr[i] = zBooleanValue;
        return Boolean.valueOf(z);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.measurement.zzff
    public final /* synthetic */ zzff<Boolean> zzap(int i) {
        if (i >= this.size) {
            return new zzdn(Arrays.copyOf(this.zzadf, i), this.size);
        }
        throw new IllegalArgumentException();
    }
}
