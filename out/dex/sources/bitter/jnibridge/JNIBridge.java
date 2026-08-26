package bitter.jnibridge;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class JNIBridge {

    /* JADX INFO: renamed from: bitter.jnibridge.JNIBridge$a */
    private static class C0168a implements InvocationHandler {

        /* JADX INFO: renamed from: a */
        private Object f13a = new Object[0];

        /* JADX INFO: renamed from: b */
        private long f14b;

        /* JADX INFO: renamed from: c */
        private Constructor f15c;

        public C0168a(long j) {
            this.f14b = j;
            try {
                this.f15c = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE);
                this.f15c.setAccessible(true);
            } catch (NoClassDefFoundError unused) {
                this.f15c = null;
            } catch (NoSuchMethodException unused2) {
                this.f15c = null;
            }
        }

        /* JADX INFO: renamed from: a */
        private Object m7a(Object obj, Method method, Object[] objArr) {
            if (objArr == null) {
                objArr = new Object[0];
            }
            Class<?> declaringClass = method.getDeclaringClass();
            return ((MethodHandles.Lookup) this.f15c.newInstance(declaringClass, 2)).in(declaringClass).unreflectSpecial(method, declaringClass).bindTo(obj).invokeWithArguments(objArr);
        }

        /* JADX INFO: renamed from: a */
        public final void m8a() {
            synchronized (this.f13a) {
                this.f14b = 0L;
            }
        }

        public final void finalize() {
            synchronized (this.f13a) {
                if (this.f14b == 0) {
                    return;
                }
                JNIBridge.delete(this.f14b);
            }
        }

        @Override // java.lang.reflect.InvocationHandler
        public final Object invoke(Object obj, Method method, Object[] objArr) {
            synchronized (this.f13a) {
                if (this.f14b == 0) {
                    return null;
                }
                try {
                    return JNIBridge.invoke(this.f14b, method.getDeclaringClass(), method, objArr);
                } catch (NoSuchMethodError e) {
                    if (this.f15c == null) {
                        System.err.println("JNIBridge error: Java interface default methods are only supported since Android Oreo");
                        throw e;
                    }
                    if ((method.getModifiers() & 1024) == 0) {
                        return m7a(obj, method, objArr);
                    }
                    throw e;
                }
            }
        }
    }

    static native void delete(long j);

    static void disableInterfaceProxy(Object obj) {
        if (obj != null) {
            ((C0168a) Proxy.getInvocationHandler(obj)).m8a();
        }
    }

    static native Object invoke(long j, Class cls, Method method, Object[] objArr);

    static Object newInterfaceProxy(long j, Class[] clsArr) {
        return Proxy.newProxyInstance(JNIBridge.class.getClassLoader(), clsArr, new C0168a(j));
    }
}
