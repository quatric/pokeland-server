package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
final class Hashing {

    /* JADX INFO: renamed from: C1 */
    private static final int f319C1 = -862048943;

    /* JADX INFO: renamed from: C2 */
    private static final int f320C2 = 461845907;
    private static final int MAX_TABLE_SIZE = 1073741824;

    private Hashing() {
    }

    static int closedTableSize(int i, double d) {
        int iMax = Math.max(i, 2);
        int iHighestOneBit = Integer.highestOneBit(iMax);
        double d2 = iHighestOneBit;
        Double.isNaN(d2);
        if (iMax <= ((int) (d * d2))) {
            return iHighestOneBit;
        }
        int i2 = iHighestOneBit << 1;
        if (i2 > 0) {
            return i2;
        }
        return 1073741824;
    }

    static boolean needsResizing(int i, int i2, double d) {
        double d2 = i;
        double d3 = i2;
        Double.isNaN(d3);
        return d2 > d * d3 && i2 < 1073741824;
    }

    static int smear(int i) {
        return Integer.rotateLeft(i * f319C1, 15) * f320C2;
    }

    static int smearedHash(@Nullable Object obj) {
        return smear(obj == null ? 0 : obj.hashCode());
    }
}
