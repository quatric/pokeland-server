package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzir implements Cloneable {
    private Object value;
    private zzip<?, ?> zzaop;
    private List<zziy> zzaoq = new ArrayList();

    zzir() {
    }

    private final byte[] toByteArray() throws IOException {
        byte[] bArr = new byte[zzqy()];
        zza(zzio.zzj(bArr));
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: zzxc, reason: merged with bridge method [inline-methods] */
    public final zzir clone() {
        zzir zzirVar = new zzir();
        try {
            zzirVar.zzaop = this.zzaop;
            if (this.zzaoq == null) {
                zzirVar.zzaoq = null;
            } else {
                zzirVar.zzaoq.addAll(this.zzaoq);
            }
            if (this.value != null) {
                if (this.value instanceof zziw) {
                    zzirVar.value = (zziw) ((zziw) this.value).clone();
                } else if (this.value instanceof byte[]) {
                    zzirVar.value = ((byte[]) this.value).clone();
                } else {
                    int i = 0;
                    if (this.value instanceof byte[][]) {
                        byte[][] bArr = (byte[][]) this.value;
                        byte[][] bArr2 = new byte[bArr.length][];
                        zzirVar.value = bArr2;
                        while (i < bArr.length) {
                            bArr2[i] = (byte[]) bArr[i].clone();
                            i++;
                        }
                    } else if (this.value instanceof boolean[]) {
                        zzirVar.value = ((boolean[]) this.value).clone();
                    } else if (this.value instanceof int[]) {
                        zzirVar.value = ((int[]) this.value).clone();
                    } else if (this.value instanceof long[]) {
                        zzirVar.value = ((long[]) this.value).clone();
                    } else if (this.value instanceof float[]) {
                        zzirVar.value = ((float[]) this.value).clone();
                    } else if (this.value instanceof double[]) {
                        zzirVar.value = ((double[]) this.value).clone();
                    } else if (this.value instanceof zziw[]) {
                        zziw[] zziwVarArr = (zziw[]) this.value;
                        zziw[] zziwVarArr2 = new zziw[zziwVarArr.length];
                        zzirVar.value = zziwVarArr2;
                        while (i < zziwVarArr.length) {
                            zziwVarArr2[i] = (zziw) zziwVarArr[i].clone();
                            i++;
                        }
                    }
                }
            }
            return zzirVar;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        List<zziy> list;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzir)) {
            return false;
        }
        zzir zzirVar = (zzir) obj;
        if (this.value == null || zzirVar.value == null) {
            List<zziy> list2 = this.zzaoq;
            if (list2 != null && (list = zzirVar.zzaoq) != null) {
                return list2.equals(list);
            }
            try {
                return Arrays.equals(toByteArray(), zzirVar.toByteArray());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        zzip<?, ?> zzipVar = this.zzaop;
        if (zzipVar != zzirVar.zzaop) {
            return false;
        }
        if (!zzipVar.zzaon.isArray()) {
            return this.value.equals(zzirVar.value);
        }
        Object obj2 = this.value;
        if (obj2 instanceof byte[]) {
            return Arrays.equals((byte[]) obj2, (byte[]) zzirVar.value);
        }
        if (obj2 instanceof int[]) {
            return Arrays.equals((int[]) obj2, (int[]) zzirVar.value);
        }
        if (obj2 instanceof long[]) {
            return Arrays.equals((long[]) obj2, (long[]) zzirVar.value);
        }
        if (obj2 instanceof float[]) {
            return Arrays.equals((float[]) obj2, (float[]) zzirVar.value);
        }
        if (obj2 instanceof double[]) {
            return Arrays.equals((double[]) obj2, (double[]) zzirVar.value);
        }
        return obj2 instanceof boolean[] ? Arrays.equals((boolean[]) obj2, (boolean[]) zzirVar.value) : Arrays.deepEquals((Object[]) obj2, (Object[]) zzirVar.value);
    }

    public final int hashCode() {
        try {
            return Arrays.hashCode(toByteArray()) + 527;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    final void zza(zzio zzioVar) throws IOException {
        if (this.value != null) {
            throw new NoSuchMethodError();
        }
        for (zziy zziyVar : this.zzaoq) {
            zzioVar.zzck(zziyVar.tag);
            zzioVar.zzk(zziyVar.zzado);
        }
    }

    final void zza(zziy zziyVar) throws IOException {
        List<zziy> list = this.zzaoq;
        if (list != null) {
            list.add(zziyVar);
            return;
        }
        Object obj = this.value;
        if (obj instanceof zziw) {
            byte[] bArr = zziyVar.zzado;
            zzil zzilVarZzj = zzil.zzj(bArr, 0, bArr.length);
            int iZzta = zzilVarZzj.zzta();
            if (iZzta != bArr.length - zzio.zzbj(iZzta)) {
                throw zzit.zzxd();
            }
            zziw zziwVarZza = ((zziw) this.value).zza(zzilVarZzj);
            this.zzaop = this.zzaop;
            this.value = zziwVarZza;
            this.zzaoq = null;
            return;
        }
        if (obj instanceof zziw[]) {
            Collections.singletonList(zziyVar);
            throw new NoSuchMethodError();
        }
        if (obj instanceof zzgi) {
            Collections.singletonList(zziyVar);
            throw new NoSuchMethodError();
        }
        if (obj instanceof zzgi[]) {
            Collections.singletonList(zziyVar);
            throw new NoSuchMethodError();
        }
        Collections.singletonList(zziyVar);
        throw new NoSuchMethodError();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    final int zzqy() {
        if (this.value != null) {
            throw new NoSuchMethodError();
        }
        int iZzbq = 0;
        for (zziy zziyVar : this.zzaoq) {
            iZzbq += zzio.zzbq(zziyVar.tag) + 0 + zziyVar.zzado.length;
        }
        return iZzbq;
    }
}
