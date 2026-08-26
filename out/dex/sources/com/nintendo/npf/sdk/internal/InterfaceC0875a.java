package com.nintendo.npf.sdk.internal;

import android.app.Application;
import com.nintendo.npf.sdk.internal.impl.C0998a;
import com.nintendo.npf.sdk.internal.impl.C0999aa;
import com.nintendo.npf.sdk.internal.impl.C1000ab;
import com.nintendo.npf.sdk.internal.impl.C1001ac;
import com.nintendo.npf.sdk.internal.impl.C1002ad;
import com.nintendo.npf.sdk.internal.impl.C1003ae;
import com.nintendo.npf.sdk.internal.impl.C1014d;
import com.nintendo.npf.sdk.internal.impl.C1018h;
import com.nintendo.npf.sdk.internal.impl.C1023m;
import com.nintendo.npf.sdk.internal.impl.C1024n;
import com.nintendo.npf.sdk.internal.impl.C1026p;
import com.nintendo.npf.sdk.internal.impl.C1027q;
import com.nintendo.npf.sdk.internal.impl.C1028r;
import com.nintendo.npf.sdk.internal.impl.C1029s;
import com.nintendo.npf.sdk.internal.impl.C1030t;
import com.nintendo.npf.sdk.internal.impl.C1032v;
import com.nintendo.npf.sdk.internal.impl.C1034x;
import com.nintendo.npf.sdk.internal.impl.C1035y;
import com.nintendo.npf.sdk.internal.impl.C1036z;
import com.nintendo.npf.sdk.internal.p016a.AbstractC0880e;
import com.nintendo.npf.sdk.internal.p022d.C0947b;
import com.nintendo.npf.sdk.internal.p022d.C0948c;
import com.nintendo.npf.sdk.internal.p023e.AbstractC0952b;
import com.nintendo.npf.sdk.internal.p024f.C0960d;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a */
/* JADX INFO: compiled from: ServiceLocator.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface InterfaceC0875a {

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a$a */
    /* JADX INFO: compiled from: ServiceLocator.java */
    public static class a {

        /* JADX INFO: renamed from: a */
        private static InterfaceC0875a f1022a;

        /* JADX INFO: renamed from: a */
        public static AbstractC0952b<InterfaceC0875a> m1070a() {
            return new AbstractC0952b<InterfaceC0875a>() { // from class: com.nintendo.npf.sdk.internal.a.a.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
                /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                public InterfaceC0875a mo1074b() {
                    return a.m1072b();
                }
            };
        }

        /* JADX INFO: renamed from: a */
        public static synchronized void m1071a(Application application) {
            if (f1022a == null) {
                f1022a = new C1034x(application);
            }
        }

        /* JADX INFO: renamed from: b */
        public static synchronized InterfaceC0875a m1072b() {
            if (f1022a == null) {
                throw new IllegalStateException();
            }
            return f1022a;
        }
    }

    /* JADX INFO: renamed from: a */
    Application mo1047a();

    /* JADX INFO: renamed from: b */
    C1026p mo1048b();

    /* JADX INFO: renamed from: c */
    C0998a mo1049c();

    /* JADX INFO: renamed from: d */
    C1018h mo1050d();

    /* JADX INFO: renamed from: e */
    C1027q mo1051e();

    /* JADX INFO: renamed from: f */
    C0999aa mo1052f();

    /* JADX INFO: renamed from: g */
    C1035y mo1053g();

    /* JADX INFO: renamed from: h */
    C1000ab mo1054h();

    /* JADX INFO: renamed from: i */
    C1036z mo1055i();

    /* JADX INFO: renamed from: j */
    C1014d mo1056j();

    /* JADX INFO: renamed from: k */
    C1029s mo1057k();

    /* JADX INFO: renamed from: l */
    C0960d mo1058l();

    /* JADX INFO: renamed from: m */
    C1001ac mo1059m();

    /* JADX INFO: renamed from: n */
    C1002ad mo1060n();

    /* JADX INFO: renamed from: o */
    C1003ae mo1061o();

    /* JADX INFO: renamed from: p */
    C1023m mo1062p();

    /* JADX INFO: renamed from: q */
    AbstractC0880e mo1063q();

    /* JADX INFO: renamed from: r */
    C0948c mo1064r();

    /* JADX INFO: renamed from: s */
    C0947b mo1065s();

    /* JADX INFO: renamed from: t */
    C1024n mo1066t();

    /* JADX INFO: renamed from: u */
    C1032v mo1067u();

    /* JADX INFO: renamed from: v */
    C1028r mo1068v();

    /* JADX INFO: renamed from: w */
    C1030t mo1069w();
}
