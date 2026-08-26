package com.google.firebase.components;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: com.google.firebase:firebase-common@@17.0.0 */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class ComponentDiscovery<T> {
    private static final String COMPONENT_KEY_PREFIX = "com.google.firebase.components:";
    private static final String COMPONENT_SENTINEL_VALUE = "com.google.firebase.components.ComponentRegistrar";
    private static final String TAG = "ComponentDiscovery";
    private final T context;
    private final RegistrarNameRetriever<T> retriever;

    /* JADX INFO: compiled from: com.google.firebase:firebase-common@@17.0.0 */
    private static class MetadataRegistrarNameRetriever implements RegistrarNameRetriever<Context> {
        private MetadataRegistrarNameRetriever() {
        }

        private static Bundle getMetadata(Context context) {
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager == null) {
                    Log.w(ComponentDiscovery.TAG, "Context has no PackageManager.");
                    return null;
                }
                ServiceInfo serviceInfo = packageManager.getServiceInfo(new ComponentName(context, (Class<?>) ComponentDiscoveryService.class), 128);
                if (serviceInfo != null) {
                    return serviceInfo.metaData;
                }
                Log.w(ComponentDiscovery.TAG, "ComponentDiscoveryService has no service info.");
                return null;
            } catch (PackageManager.NameNotFoundException unused) {
                Log.w(ComponentDiscovery.TAG, "Application info not found.");
                return null;
            }
        }

        @Override // com.google.firebase.components.ComponentDiscovery.RegistrarNameRetriever
        public List<String> retrieve(Context context) {
            Bundle metadata = getMetadata(context);
            if (metadata == null) {
                Log.w(ComponentDiscovery.TAG, "Could not retrieve metadata, returning empty list of registrars.");
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            for (String str : metadata.keySet()) {
                if (ComponentDiscovery.COMPONENT_SENTINEL_VALUE.equals(metadata.get(str)) && str.startsWith(ComponentDiscovery.COMPONENT_KEY_PREFIX)) {
                    arrayList.add(str.substring(31));
                }
            }
            return arrayList;
        }
    }

    /* JADX INFO: compiled from: com.google.firebase:firebase-common@@17.0.0 */
    @VisibleForTesting
    interface RegistrarNameRetriever<T> {
        List<String> retrieve(T t);
    }

    @VisibleForTesting
    ComponentDiscovery(T t, RegistrarNameRetriever<T> registrarNameRetriever) {
        this.context = t;
        this.retriever = registrarNameRetriever;
    }

    public static ComponentDiscovery<Context> forContext(Context context) {
        return new ComponentDiscovery<>(context, new MetadataRegistrarNameRetriever());
    }

    private static List<ComponentRegistrar> instantiate(List<String> list) {
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            try {
                Class<?> cls = Class.forName(str);
                if (ComponentRegistrar.class.isAssignableFrom(cls)) {
                    arrayList.add((ComponentRegistrar) cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
                } else {
                    Log.w(TAG, String.format("Class %s is not an instance of %s", str, COMPONENT_SENTINEL_VALUE));
                }
            } catch (ClassNotFoundException e) {
                Log.w(TAG, String.format("Class %s is not an found.", str), e);
            } catch (IllegalAccessException e2) {
                Log.w(TAG, String.format("Could not instantiate %s.", str), e2);
            } catch (InstantiationException e3) {
                Log.w(TAG, String.format("Could not instantiate %s.", str), e3);
            } catch (NoSuchMethodException e4) {
                Log.w(TAG, String.format("Could not instantiate %s", str), e4);
            } catch (InvocationTargetException e5) {
                Log.w(TAG, String.format("Could not instantiate %s", str), e5);
            }
        }
        return arrayList;
    }

    public List<ComponentRegistrar> discover() {
        return instantiate(this.retriever.retrieve(this.context));
    }
}
