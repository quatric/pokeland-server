package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public final class Reflection {
    private Reflection() {
    }

    public static String getPackageName(Class<?> cls) {
        return getPackageName(cls.getName());
    }

    public static String getPackageName(String str) {
        int iLastIndexOf = str.lastIndexOf(46);
        return iLastIndexOf < 0 ? "" : str.substring(0, iLastIndexOf);
    }

    public static void initialize(Class<?>... clsArr) {
        for (Class<?> cls : clsArr) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
            } catch (ClassNotFoundException e) {
                throw new AssertionError(e);
            }
        }
    }

    public static <T> T newProxy(Class<T> cls, InvocationHandler invocationHandler) {
        Preconditions.checkNotNull(invocationHandler);
        Preconditions.checkArgument(cls.isInterface(), "%s is not an interface", cls);
        return cls.cast(Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, invocationHandler));
    }
}
