package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfw extends zzdj<Long> implements zzfg, zzgu, RandomAccess {
    private static final zzfw zzajy;
    private int size;
    private long[] zzajz;

    static {
        zzfw zzfwVar = new zzfw(new long[0], 0);
        zzajy = zzfwVar;
        zzfwVar.zzry();
    }

    zzfw() {
        this(new long[10], 0);
    }

    private zzfw(long[] jArr, int i) {
        this.zzajz = jArr;
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

    private final void zzk(int i, long j) {
        int i2;
        zzrz();
        if (i < 0 || i > (i2 = this.size)) {
            throw new IndexOutOfBoundsException(zzao(i));
        }
        long[] jArr = this.zzajz;
        if (i2 < jArr.length) {
            System.arraycopy(jArr, i, jArr, i + 1, i2 - i);
        } else {
            long[] jArr2 = new long[((i2 * 3) / 2) + 1];
            System.arraycopy(jArr, 0, jArr2, 0, i);
            System.arraycopy(this.zzajz, i, jArr2, i + 1, this.size - i);
            this.zzajz = jArr2;
        }
        this.zzajz[i] = j;
        this.size++;
        this.modCount++;
    }

    public static zzfw zzvk() {
        return zzajy;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        zzk(i, ((Long) obj).longValue());
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Long> collection) {
        zzrz();
        zzez.checkNotNull(collection);
        if (!(collection instanceof zzfw)) {
            return super.addAll(collection);
        }
        zzfw zzfwVar = (zzfw) collection;
        int i = zzfwVar.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        long[] jArr = this.zzajz;
        if (i3 > jArr.length) {
            this.zzajz = Arrays.copyOf(jArr, i3);
        }
        System.arraycopy(zzfwVar.zzajz, 0, this.zzajz, this.size, zzfwVar.size);
        this.size = i3;
        this.modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfw)) {
            return super.equals(obj);
        }
        zzfw zzfwVar = (zzfw) obj;
        if (this.size != zzfwVar.size) {
            return false;
        }
        long[] jArr = zzfwVar.zzajz;
        for (int i = 0; i < this.size; i++) {
            if (this.zzajz[i] != jArr[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        return Long.valueOf(getLong(i));
    }

    @Override // com.google.android.gms.internal.measurement.zzfg
    public final long getLong(int i) {
        zzan(i);
        return this.zzajz[i];
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int iZzbx = 1;
        for (int i = 0; i < this.size; i++) {
            iZzbx = (iZzbx * 31) + zzez.zzbx(this.zzajz[i]);
        }
        return iZzbx;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzrz();
        zzan(i);
        long[] jArr = this.zzajz;
        long j = jArr[i];
        int i2 = this.size;
        if (i < i2 - 1) {
            System.arraycopy(jArr, i + 1, jArr, i, (i2 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Long.valueOf(j);
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzrz();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Long.valueOf(this.zzajz[i]))) {
                long[] jArr = this.zzajz;
                System.arraycopy(jArr, i + 1, jArr, i, (this.size - i) - 1);
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
        long[] jArr = this.zzajz;
        System.arraycopy(jArr, i2, jArr, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.measurement.zzdj, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i, Object obj) {
        long jLongValue = ((Long) obj).longValue();
        zzrz();
        zzan(i);
        long[] jArr = this.zzajz;
        long j = jArr[i];
        jArr[i] = jLongValue;
        return Long.valueOf(j);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.measurement.zzff
    /* JADX INFO: renamed from: zzbv, reason: merged with bridge method [inline-methods] */
    public final zzfg zzap(int i) {
        if (i >= this.size) {
            return new zzfw(Arrays.copyOf(this.zzajz, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    @Override // com.google.android.gms.internal.measurement.zzfg
    public final void zzby(long j) {
        zzk(this.size, j);
    }
}
