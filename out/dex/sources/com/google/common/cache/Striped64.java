package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Random;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtIncompatible
abstract class Striped64 extends Number {
    private static final Unsafe UNSAFE;
    private static final long baseOffset;
    private static final long busyOffset;
    volatile transient long base;
    volatile transient int busy;
    volatile transient Cell[] cells;
    static final ThreadLocal<int[]> threadHashCode = new ThreadLocal<>();
    static final Random rng = new Random();
    static final int NCPU = Runtime.getRuntime().availableProcessors();

    static final class Cell {
        private static final Unsafe UNSAFE;
        private static final long valueOffset;

        /* JADX INFO: renamed from: p0 */
        volatile long f302p0;

        /* JADX INFO: renamed from: p1 */
        volatile long f303p1;

        /* JADX INFO: renamed from: p2 */
        volatile long f304p2;

        /* JADX INFO: renamed from: p3 */
        volatile long f305p3;

        /* JADX INFO: renamed from: p4 */
        volatile long f306p4;

        /* JADX INFO: renamed from: p5 */
        volatile long f307p5;

        /* JADX INFO: renamed from: p6 */
        volatile long f308p6;

        /* JADX INFO: renamed from: q0 */
        volatile long f309q0;

        /* JADX INFO: renamed from: q1 */
        volatile long f310q1;

        /* JADX INFO: renamed from: q2 */
        volatile long f311q2;

        /* JADX INFO: renamed from: q3 */
        volatile long f312q3;

        /* JADX INFO: renamed from: q4 */
        volatile long f313q4;

        /* JADX INFO: renamed from: q5 */
        volatile long f314q5;

        /* JADX INFO: renamed from: q6 */
        volatile long f315q6;
        volatile long value;

        static {
            try {
                UNSAFE = Striped64.getUnsafe();
                valueOffset = UNSAFE.objectFieldOffset(Cell.class.getDeclaredField("value"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }

        Cell(long j) {
            this.value = j;
        }

        final boolean cas(long j, long j2) {
            return UNSAFE.compareAndSwapLong(this, valueOffset, j, j2);
        }
    }

    static {
        try {
            UNSAFE = getUnsafe();
            baseOffset = UNSAFE.objectFieldOffset(Striped64.class.getDeclaredField("base"));
            busyOffset = UNSAFE.objectFieldOffset(Striped64.class.getDeclaredField("busy"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    Striped64() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Unsafe getUnsafe() {
        try {
            try {
                return Unsafe.getUnsafe();
            } catch (PrivilegedActionException e) {
                throw new RuntimeException("Could not initialize intrinsics", e.getCause());
            }
        } catch (SecurityException unused) {
            return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: com.google.common.cache.Striped64.1
                /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
                @Override // java.security.PrivilegedExceptionAction
                public Unsafe run() throws Exception {
                    for (Field field : Unsafe.class.getDeclaredFields()) {
                        field.setAccessible(true);
                        Object obj = field.get(null);
                        if (Unsafe.class.isInstance(obj)) {
                            return (Unsafe) Unsafe.class.cast(obj);
                        }
                    }
                    throw new NoSuchFieldError("the Unsafe");
                }
            });
        }
    }

    final boolean casBase(long j, long j2) {
        return UNSAFE.compareAndSwapLong(this, baseOffset, j, j2);
    }

    final boolean casBusy() {
        return UNSAFE.compareAndSwapInt(this, busyOffset, 0, 1);
    }

    /* JADX INFO: renamed from: fn */
    abstract long mo478fn(long j, long j2);

    final void internalReset(long j) {
        Cell[] cellArr = this.cells;
        this.base = j;
        if (cellArr != null) {
            for (Cell cell : cellArr) {
                if (cell != null) {
                    cell.value = j;
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0055  */
    final void retryUpdate(long j, int[] iArr, boolean z) {
        int iNextInt;
        int[] iArr2;
        Cell[] cellArr;
        boolean z2;
        int length;
        boolean z3;
        int length2;
        if (iArr == null) {
            iArr2 = new int[1];
            threadHashCode.set(iArr2);
            iNextInt = rng.nextInt();
            if (iNextInt == 0) {
                iNextInt = 1;
            }
            iArr2[0] = iNextInt;
        } else {
            iNextInt = iArr[0];
            iArr2 = iArr;
        }
        boolean z4 = z;
        while (true) {
            boolean z5 = false;
            while (true) {
                cellArr = this.cells;
                if (cellArr != null && (length = cellArr.length) > 0) {
                    Cell cell = cellArr[(length - 1) & iNextInt];
                    if (cell != null) {
                        if (z4) {
                            long j2 = cell.value;
                            if (cell.cas(j2, mo478fn(j2, j))) {
                                return;
                            }
                            if (length < NCPU && this.cells == cellArr) {
                                if (z5) {
                                    if (this.busy == 0 && casBusy()) {
                                        break;
                                    }
                                } else {
                                    z5 = true;
                                }
                            }
                        } else {
                            z4 = true;
                        }
                        int i = iNextInt ^ (iNextInt << 13);
                        int i2 = i ^ (i >>> 17);
                        iNextInt = i2 ^ (i2 << 5);
                        iArr2[0] = iNextInt;
                    } else if (this.busy == 0) {
                        Cell cell2 = new Cell(j);
                        if (this.busy == 0 && casBusy()) {
                            try {
                                Cell[] cellArr2 = this.cells;
                                if (cellArr2 == null || (length2 = cellArr2.length) <= 0) {
                                    z3 = false;
                                } else {
                                    int i3 = (length2 - 1) & iNextInt;
                                    if (cellArr2[i3] == null) {
                                        cellArr2[i3] = cell2;
                                        z3 = true;
                                    } else {
                                        z3 = false;
                                    }
                                }
                                this.busy = 0;
                                if (z3) {
                                    return;
                                }
                            } catch (Throwable th) {
                                this.busy = 0;
                                throw th;
                            }
                        }
                    }
                    z5 = false;
                    int i4 = iNextInt ^ (iNextInt << 13);
                    int i5 = i4 ^ (i4 >>> 17);
                    iNextInt = i5 ^ (i5 << 5);
                    iArr2[0] = iNextInt;
                } else if (this.busy == 0 && this.cells == cellArr && casBusy()) {
                    try {
                        if (this.cells == cellArr) {
                            Cell[] cellArr3 = new Cell[2];
                            cellArr3[iNextInt & 1] = new Cell(j);
                            this.cells = cellArr3;
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        this.busy = 0;
                        if (z2) {
                            return;
                        }
                    } catch (Throwable th2) {
                        this.busy = 0;
                        throw th2;
                    }
                } else {
                    long j3 = this.base;
                    if (casBase(j3, mo478fn(j3, j))) {
                        return;
                    }
                }
            }
            try {
                if (this.cells == cellArr) {
                    Cell[] cellArr4 = new Cell[length << 1];
                    for (int i6 = 0; i6 < length; i6++) {
                        cellArr4[i6] = cellArr[i6];
                    }
                    this.cells = cellArr4;
                }
                this.busy = 0;
            } catch (Throwable th3) {
                this.busy = 0;
                throw th3;
            }
        }
    }
}
