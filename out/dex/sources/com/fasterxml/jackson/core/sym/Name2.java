package com.fasterxml.jackson.core.sym;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Name2 extends Name {

    /* JADX INFO: renamed from: q1 */
    private final int f261q1;

    /* JADX INFO: renamed from: q2 */
    private final int f262q2;

    Name2(String str, int i, int i2, int i3) {
        super(str, i);
        this.f261q1 = i2;
        this.f262q2 = i3;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i) {
        return false;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i, int i2) {
        return i == this.f261q1 && i2 == this.f262q2;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i, int i2, int i3) {
        return false;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int[] iArr, int i) {
        return i == 2 && iArr[0] == this.f261q1 && iArr[1] == this.f262q2;
    }
}
