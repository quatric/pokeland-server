package android.arch.lifecycle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class Lifecycling {
    private static Map<Class, Constructor<? extends GenericLifecycleObserver>> sCallbackCache;
    private static Constructor<? extends GenericLifecycleObserver> sREFLECTIVE;

    static {
        try {
            sREFLECTIVE = ReflectiveGenericLifecycleObserver.class.getDeclaredConstructor(Object.class);
        } catch (NoSuchMethodException unused) {
        }
        sCallbackCache = new HashMap();
    }

    Lifecycling() {
    }

    static String getAdapterName(String str) {
        return str.replace(".", "_") + "_LifecycleAdapter";
    }

    @NonNull
    static GenericLifecycleObserver getCallback(Object obj) {
        if (obj instanceof GenericLifecycleObserver) {
            return (GenericLifecycleObserver) obj;
        }
        try {
            Class<?> cls = obj.getClass();
            Constructor<? extends GenericLifecycleObserver> constructor = sCallbackCache.get(cls);
            if (constructor != null) {
                return constructor.newInstance(obj);
            }
            Constructor<? extends GenericLifecycleObserver> generatedAdapterConstructor = getGeneratedAdapterConstructor(cls);
            if (generatedAdapterConstructor == null) {
                generatedAdapterConstructor = sREFLECTIVE;
            } else if (!generatedAdapterConstructor.isAccessible()) {
                generatedAdapterConstructor.setAccessible(true);
            }
            sCallbackCache.put(cls, generatedAdapterConstructor);
            return generatedAdapterConstructor.newInstance(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e2) {
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException(e3);
        }
    }

    @Nullable
    private static Constructor<? extends GenericLifecycleObserver> getGeneratedAdapterConstructor(Class<?> cls) {
        Package r0 = cls.getPackage();
        String name = r0 != null ? r0.getName() : "";
        String canonicalName = cls.getCanonicalName();
        if (canonicalName == null) {
            return null;
        }
        if (!name.isEmpty()) {
            canonicalName = canonicalName.substring(name.length() + 1);
        }
        String adapterName = getAdapterName(canonicalName);
        try {
            if (!name.isEmpty()) {
                adapterName = name + "." + adapterName;
            }
            return Class.forName(adapterName).getDeclaredConstructor(cls);
        } catch (ClassNotFoundException unused) {
            Class<? super Object> superclass = cls.getSuperclass();
            if (superclass != null) {
                return getGeneratedAdapterConstructor(superclass);
            }
            return null;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
