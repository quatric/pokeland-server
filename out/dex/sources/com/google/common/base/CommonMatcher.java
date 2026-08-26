package com.google.common.base;

import com.google.common.annotations.GwtCompatible;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
abstract class CommonMatcher {
    CommonMatcher() {
    }

    abstract int end();

    abstract boolean find();

    abstract boolean find(int i);

    abstract boolean matches();

    abstract String replaceAll(String str);

    abstract int start();
}
