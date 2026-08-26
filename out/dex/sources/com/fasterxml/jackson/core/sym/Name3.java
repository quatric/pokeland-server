package com.fasterxml.jackson.core.sym;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Name3 extends Name {

    /* JADX INFO: renamed from: q1 */
    private final int f263q1;

    /* JADX INFO: renamed from: q2 */
    private final int f264q2;

    /* JADX INFO: renamed from: q3 */
    private final int f265q3;

    Name3(String str, int i, int i2, int i3, int i4) {
        super(str, i);
        this.f263q1 = i2;
        this.f264q2 = i3;
        this.f265q3 = i4;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i) {
        return false;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i, int i2) {
        return false;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i, int i2, int i3) {
        return this.f263q1 == i && this.f264q2 == i2 && this.f265q3 == i3;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int[] iArr, int i) {
        return i == 3 && iArr[0] == this.f263q1 && iArr[1] == this.f264q2 && iArr[2] == this.f265q3;
    }
}
