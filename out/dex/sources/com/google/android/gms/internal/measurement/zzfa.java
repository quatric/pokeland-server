package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfa extends zzdj<Integer> implements zzfd, zzgu, RandomAccess {
    private static final zzfa zzaiu;
    private int size;
    private int[] zzaiv;

    static {
        zzfa zzfaVar = new zzfa(new int[0], 0);
        zzaiu = zzfaVar;
        zzfaVar.zzry();
    }

    zzfa() {
        this(new int[10], 0);
    }

    private zzfa(int[] iArr, int i) {
        this.zzaiv = iArr;
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

    private final void zzo(int i, int i2) {
        int i3;
        zzrz();
        if (i < 0 || i > (i3 = this.size)) {
            throw new IndexOutOfBoundsException(zzao(i));
        }
        int[] iArr = this.zzaiv;
        if (i3 < iArr.length) {
            System.arraycopy(iArr, i, iArr, i + 1, i3 - i);
        } else {
            int[] iArr2 = new int[((i3 * 3) / 2) + 1];
            System.arraycopy(iArr, 0, iArr2, 0, i);
            System.arraycopy(this.zzaiv, i, iArr2, i + 1, this.size - i);
            this.zzaiv = iArr2;
        }
        this.zzaiv[i] = i2;
        this.size++;
        this.modCount++;
    }

    public static zzfa zzus() {
        return zzaiu;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        zzo(i, ((Integer) obj).intValue());
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Integer> collection) {
        zzrz();
        zzez.checkNotNull(collection);
        if (!(collection instanceof zzfa)) {
            return super.addAll(collection);
        }
        zzfa zzfaVar = (zzfa) collection;
        int i = zzfaVar.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        int[] iArr = this.zzaiv;
        if (i3 > iArr.length) {
            this.zzaiv = Arrays.copyOf(iArr, i3);
        }
        System.arraycopy(zzfaVar.zzaiv, 0, this.zzaiv, this.size, zzfaVar.size);
        this.size = i3;
        this.modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfa)) {
            return super.equals(obj);
        }
        zzfa zzfaVar = (zzfa) obj;
        if (this.size != zzfaVar.size) {
            return false;
        }
        int[] iArr = zzfaVar.zzaiv;
        for (int i = 0; i < this.size; i++) {
            if (this.zzaiv[i] != iArr[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        return Integer.valueOf(getInt(i));
    }

    public final int getInt(int i) {
        zzan(i);
        return this.zzaiv[i];
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + this.zzaiv[i2];
        }
        return i;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzrz();
        zzan(i);
        int[] iArr = this.zzaiv;
        int i2 = iArr[i];
        int i3 = this.size;
        if (i < i3 - 1) {
            System.arraycopy(iArr, i + 1, iArr, i, (i3 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Integer.valueOf(i2);
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzrz();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Integer.valueOf(this.zzaiv[i]))) {
                int[] iArr = this.zzaiv;
                System.arraycopy(iArr, i + 1, iArr, i, (this.size - i) - 1);
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
        int[] iArr = this.zzaiv;
        System.arraycopy(iArr, i2, iArr, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i, Object obj) {
        int iIntValue = ((Integer) obj).intValue();
        zzrz();
        zzan(i);
        int[] iArr = this.zzaiv;
        int i2 = iArr[i];
        iArr[i] = iIntValue;
        return Integer.valueOf(i2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.measurement.zzff
    /* JADX INFO: renamed from: zzbt, reason: merged with bridge method [inline-methods] */
    public final zzfd zzap(int i) {
        if (i >= this.size) {
            return new zzfa(Arrays.copyOf(this.zzaiv, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final void zzbu(int i) {
        zzo(this.size, i);
    }
}
