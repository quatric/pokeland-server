package com.google.firebase.components;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: compiled from: com.google.firebase:firebase-common@@17.0.0 */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
public class DependencyCycleException extends DependencyException {
    private final List<Component<?>> componentsInCycle;

    @KeepForSdk
    public DependencyCycleException(List<Component<?>> list) {
        super("Dependency cycle detected: " + Arrays.toString(list.toArray()));
        this.componentsInCycle = list;
    }

    @KeepForSdk
    public List<Component<?>> getComponentsInCycle() {
        return this.componentsInCycle;
    }
}
