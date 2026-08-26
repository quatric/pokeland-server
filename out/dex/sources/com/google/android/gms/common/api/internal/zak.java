package com.google.android.gms.common.api.internal;

import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zak {
    private int zadc;
    private final ArrayMap<zai<?>, String> zada = new ArrayMap<>();
    private final TaskCompletionSource<Map<zai<?>, String>> zadb = new TaskCompletionSource<>();
    private boolean zadd = false;
    private final ArrayMap<zai<?>, ConnectionResult> zaay = new ArrayMap<>();

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public zak(Iterable<? extends GoogleApi<?>> iterable) {
        Iterator<? extends GoogleApi<?>> it = iterable.iterator();
        while (it.hasNext()) {
            this.zaay.put(it.next().zak(), null);
        }
        this.zadc = this.zaay.keySet().size();
    }

    public final Task<Map<zai<?>, String>> getTask() {
        return this.zadb.getTask();
    }

    public final void zaa(zai<?> zaiVar, ConnectionResult connectionResult, @Nullable String str) {
        this.zaay.put(zaiVar, connectionResult);
        this.zada.put(zaiVar, str);
        this.zadc--;
        if (!connectionResult.isSuccess()) {
            this.zadd = true;
        }
        if (this.zadc == 0) {
            if (!this.zadd) {
                this.zadb.setResult(this.zada);
            } else {
                this.zadb.setException(new AvailabilityException(this.zaay));
            }
        }
    }

    public final Set<zai<?>> zap() {
        return this.zaay.keySet();
    }
}
