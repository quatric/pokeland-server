package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzis implements Cloneable {
    private static final zzir zzaor = new zzir();
    private int mSize;
    private boolean zzaos;
    private int[] zzaot;
    private zzir[] zzaou;

    zzis() {
        this(10);
    }

    private zzis(int i) {
        this.zzaos = false;
        int iIdealIntArraySize = idealIntArraySize(i);
        this.zzaot = new int[iIdealIntArraySize];
        this.zzaou = new zzir[iIdealIntArraySize];
        this.mSize = 0;
    }

    private static int idealIntArraySize(int i) {
        int i2 = i << 2;
        for (int i3 = 4; i3 < 32; i3++) {
            int i4 = (1 << i3) - 12;
            if (i2 <= i4) {
                i2 = i4;
                break;
            }
        }
        return i2 / 4;
    }

    private final int zzcn(int i) {
        int i2 = this.mSize - 1;
        int i3 = 0;
        while (i3 <= i2) {
            int i4 = (i3 + i2) >>> 1;
            int i5 = this.zzaot[i4];
            if (i5 < i) {
                i3 = i4 + 1;
            } else {
                if (i5 <= i) {
                    return i4;
                }
                i2 = i4 - 1;
            }
        }
        return i3 ^ (-1);
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        int i = this.mSize;
        zzis zzisVar = new zzis(i);
        System.arraycopy(this.zzaot, 0, zzisVar.zzaot, 0, i);
        for (int i2 = 0; i2 < i; i2++) {
            zzir[] zzirVarArr = this.zzaou;
            if (zzirVarArr[i2] != null) {
                zzisVar.zzaou[i2] = (zzir) zzirVarArr[i2].clone();
            }
        }
        zzisVar.mSize = i;
        return zzisVar;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0043 A[RETURN] */
    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzis)) {
            return false;
        }
        zzis zzisVar = (zzis) obj;
        int i = this.mSize;
        if (i != zzisVar.mSize) {
            return false;
        }
        int[] iArr = this.zzaot;
        int[] iArr2 = zzisVar.zzaot;
        int i2 = 0;
        while (true) {
            if (i2 >= i) {
                z = true;
                break;
            }
            if (iArr[i2] != iArr2[i2]) {
                z = false;
                break;
            }
            i2++;
        }
        if (z) {
            zzir[] zzirVarArr = this.zzaou;
            zzir[] zzirVarArr2 = zzisVar.zzaou;
            int i3 = this.mSize;
            for (int i4 = 0; i4 < i3; i4++) {
                if (!zzirVarArr[i4].equals(zzirVarArr2[i4])) {
                    z2 = false;
                    if (z2) {
                        return true;
                    }
                }
            }
            z2 = true;
            if (z2) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = 17;
        for (int i = 0; i < this.mSize; i++) {
            iHashCode = (((iHashCode * 31) + this.zzaot[i]) * 31) + this.zzaou[i].hashCode();
        }
        return iHashCode;
    }

    public final boolean isEmpty() {
        return this.mSize == 0;
    }

    final int size() {
        return this.mSize;
    }

    final void zza(int i, zzir zzirVar) {
        int iZzcn = zzcn(i);
        if (iZzcn >= 0) {
            this.zzaou[iZzcn] = zzirVar;
            return;
        }
        int i2 = iZzcn ^ (-1);
        if (i2 < this.mSize) {
            zzir[] zzirVarArr = this.zzaou;
            if (zzirVarArr[i2] == zzaor) {
                this.zzaot[i2] = i;
                zzirVarArr[i2] = zzirVar;
                return;
            }
        }
        int i3 = this.mSize;
        if (i3 >= this.zzaot.length) {
            int iIdealIntArraySize = idealIntArraySize(i3 + 1);
            int[] iArr = new int[iIdealIntArraySize];
            zzir[] zzirVarArr2 = new zzir[iIdealIntArraySize];
            int[] iArr2 = this.zzaot;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            zzir[] zzirVarArr3 = this.zzaou;
            System.arraycopy(zzirVarArr3, 0, zzirVarArr2, 0, zzirVarArr3.length);
            this.zzaot = iArr;
            this.zzaou = zzirVarArr2;
        }
        int i4 = this.mSize;
        if (i4 - i2 != 0) {
            int[] iArr3 = this.zzaot;
            int i5 = i2 + 1;
            System.arraycopy(iArr3, i2, iArr3, i5, i4 - i2);
            zzir[] zzirVarArr4 = this.zzaou;
            System.arraycopy(zzirVarArr4, i2, zzirVarArr4, i5, this.mSize - i2);
        }
        this.zzaot[i2] = i;
        this.zzaou[i2] = zzirVar;
        this.mSize++;
    }

    final zzir zzcl(int i) {
        int iZzcn = zzcn(i);
        if (iZzcn < 0) {
            return null;
        }
        zzir[] zzirVarArr = this.zzaou;
        if (zzirVarArr[iZzcn] == zzaor) {
            return null;
        }
        return zzirVarArr[iZzcn];
    }

    final zzir zzcm(int i) {
        return this.zzaou[i];
    }
}
