package com.unity3d.player;

import java.lang.reflect.Method;
import java.util.HashMap;

/* JADX INFO: renamed from: com.unity3d.player.o */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C1133o {

    /* JADX INFO: renamed from: a */
    private HashMap f2069a = new HashMap();

    /* JADX INFO: renamed from: b */
    private Class f2070b;

    /* JADX INFO: renamed from: c */
    private Object f2071c;

    /* JADX INFO: renamed from: com.unity3d.player.o$a */
    class a {

        /* JADX INFO: renamed from: a */
        public Class[] f2072a;

        /* JADX INFO: renamed from: b */
        public Method f2073b = null;

        public a(Class[] clsArr) {
            this.f2072a = clsArr;
        }
    }

    public C1133o(Class cls, Object obj) {
        this.f2070b = null;
        this.f2071c = null;
        this.f2070b = cls;
        this.f2071c = obj;
    }

    /* JADX INFO: renamed from: a */
    private void m1955a(String str, a aVar) {
        try {
            aVar.f2073b = this.f2070b.getMethod(str, aVar.f2072a);
        } catch (Exception e) {
            C1125g.Log(6, "Exception while trying to get method " + str + ". " + e.getLocalizedMessage());
            aVar.f2073b = null;
        }
    }

    /* JADX INFO: renamed from: a */
    public final Object m1956a(String str, Object... objArr) {
        StringBuilder sb;
        Object objInvoke = null;
        if (this.f2069a.containsKey(str)) {
            a aVar = (a) this.f2069a.get(str);
            if (aVar.f2073b == null) {
                m1955a(str, aVar);
            }
            if (aVar.f2073b != null) {
                try {
                    objInvoke = objArr.length == 0 ? aVar.f2073b.invoke(this.f2071c, new Object[0]) : aVar.f2073b.invoke(this.f2071c, objArr);
                } catch (Exception e) {
                    C1125g.Log(6, "Error trying to call delegated method " + str + ". " + e.getLocalizedMessage());
                }
                return objInvoke;
            }
            sb = new StringBuilder("Unable to create method: ");
        } else {
            sb = new StringBuilder("No definition for method ");
            sb.append(str);
            str = " can be found";
        }
        sb.append(str);
        C1125g.Log(6, sb.toString());
        return null;
    }

    /* JADX INFO: renamed from: a */
    public final void m1957a(String str, Class[] clsArr) {
        this.f2069a.put(str, new a(clsArr));
    }
}
