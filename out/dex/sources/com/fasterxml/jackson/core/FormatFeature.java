package com.fasterxml.jackson.core;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface FormatFeature {
    boolean enabledByDefault();

    boolean enabledIn(int i);

    int getMask();
}
