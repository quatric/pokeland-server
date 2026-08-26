package com.nintendo.npf.sdk.internal.impl;

import android.app.Application;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p016a.AbstractC0880e;
import com.nintendo.npf.sdk.internal.p016a.C0881f;
import com.nintendo.npf.sdk.internal.p016a.ServiceConnectionC0882g;
import com.nintendo.npf.sdk.internal.p022d.C0947b;
import com.nintendo.npf.sdk.internal.p022d.C0948c;
import com.nintendo.npf.sdk.internal.p023e.AbstractC0952b;
import com.nintendo.npf.sdk.internal.p024f.C0960d;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.x */
/* JADX INFO: compiled from: ServiceLocatorImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1034x implements InterfaceC0875a {

    /* JADX INFO: renamed from: a */
    private final Application f1698a;

    /* JADX INFO: renamed from: b */
    private final AbstractC0952b<C1026p> f1699b;

    /* JADX INFO: renamed from: c */
    private final AbstractC0952b<C0998a> f1700c;

    /* JADX INFO: renamed from: d */
    private final AbstractC0952b<C1018h> f1701d;

    /* JADX INFO: renamed from: e */
    private final AbstractC0952b<C1027q> f1702e;

    /* JADX INFO: renamed from: f */
    private final AbstractC0952b<C0999aa> f1703f;

    /* JADX INFO: renamed from: g */
    private final AbstractC0952b<C1035y> f1704g;

    /* JADX INFO: renamed from: h */
    private final AbstractC0952b<C1000ab> f1705h;

    /* JADX INFO: renamed from: i */
    private final AbstractC0952b<C1036z> f1706i;

    /* JADX INFO: renamed from: j */
    private final AbstractC0952b<C1014d> f1707j;

    /* JADX INFO: renamed from: k */
    private final AbstractC0952b<C1029s> f1708k;

    /* JADX INFO: renamed from: l */
    private final AbstractC0952b<C1023m> f1709l;

    /* JADX INFO: renamed from: m */
    private final AbstractC0952b<AbstractC0880e> f1710m;

    /* JADX INFO: renamed from: n */
    private final AbstractC0952b<C0948c> f1711n;

    /* JADX INFO: renamed from: o */
    private final AbstractC0952b<C0947b> f1712o;

    /* JADX INFO: renamed from: p */
    private final AbstractC0952b<C1024n> f1713p;

    /* JADX INFO: renamed from: q */
    private final AbstractC0952b<C1032v> f1714q;

    /* JADX INFO: renamed from: r */
    private final AbstractC0952b<C1028r> f1715r;

    /* JADX INFO: renamed from: s */
    private final AbstractC0952b<C0960d> f1716s;

    /* JADX INFO: renamed from: t */
    private final AbstractC0952b<C1001ac> f1717t;

    /* JADX INFO: renamed from: u */
    private final AbstractC0952b<C1002ad> f1718u;

    /* JADX INFO: renamed from: v */
    private final AbstractC0952b<C1003ae> f1719v;

    /* JADX INFO: renamed from: w */
    private final AbstractC0952b<C1030t> f1720w;

    public C1034x(final Application application) {
        if (application == null) {
            throw new IllegalArgumentException();
        }
        this.f1698a = application;
        this.f1699b = new AbstractC0952b<C1026p>() { // from class: com.nintendo.npf.sdk.internal.impl.x.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C1026p mo1074b() {
                return new C1026p(C1034x.this.f1698a);
            }
        };
        this.f1700c = new AbstractC0952b<C0998a>() { // from class: com.nintendo.npf.sdk.internal.impl.x.12
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C0998a mo1074b() {
                return new C0998a();
            }
        };
        this.f1701d = new AbstractC0952b<C1018h>() { // from class: com.nintendo.npf.sdk.internal.impl.x.16
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C1018h mo1074b() {
                return new C1018h();
            }
        };
        this.f1702e = new AbstractC0952b<C1027q>() { // from class: com.nintendo.npf.sdk.internal.impl.x.17
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C1027q mo1074b() {
                return new C1027q();
            }
        };
        this.f1703f = new AbstractC0952b<C0999aa>() { // from class: com.nintendo.npf.sdk.internal.impl.x.18
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C0999aa mo1074b() {
                return new C0999aa();
            }
        };
        this.f1704g = new AbstractC0952b<C1035y>() { // from class: com.nintendo.npf.sdk.internal.impl.x.19
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C1035y mo1074b() {
                return new C1035y();
            }
        };
        this.f1705h = new AbstractC0952b<C1000ab>() { // from class: com.nintendo.npf.sdk.internal.impl.x.20
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C1000ab mo1074b() {
                return new C1000ab();
            }
        };
        this.f1706i = new AbstractC0952b<C1036z>() { // from class: com.nintendo.npf.sdk.internal.impl.x.21
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C1036z mo1074b() {
                return new C1036z();
            }
        };
        this.f1707j = new AbstractC0952b<C1014d>() { // from class: com.nintendo.npf.sdk.internal.impl.x.22
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C1014d mo1074b() {
                return new C1014d();
            }
        };
        this.f1708k = new AbstractC0952b<C1029s>() { // from class: com.nintendo.npf.sdk.internal.impl.x.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C1029s mo1074b() {
                return new C1029s();
            }
        };
        this.f1709l = new AbstractC0952b<C1023m>() { // from class: com.nintendo.npf.sdk.internal.impl.x.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C1023m mo1074b() {
                return new C1023m();
            }
        };
        this.f1710m = new AbstractC0952b<AbstractC0880e>() { // from class: com.nintendo.npf.sdk.internal.impl.x.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public AbstractC0880e mo1074b() {
                return AbstractC0880e.m1122a().equals("AMAZON") ? new C0881f() : new ServiceConnectionC0882g();
            }
        };
        this.f1711n = new AbstractC0952b<C0948c>() { // from class: com.nintendo.npf.sdk.internal.impl.x.5
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C0948c mo1074b() {
                return new C0948c();
            }
        };
        this.f1712o = new AbstractC0952b<C0947b>() { // from class: com.nintendo.npf.sdk.internal.impl.x.6
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C0947b mo1074b() {
                C0947b c0947b = new C0947b();
                c0947b.m1320a(application);
                return c0947b;
            }
        };
        this.f1713p = new AbstractC0952b<C1024n>() { // from class: com.nintendo.npf.sdk.internal.impl.x.7
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C1024n mo1074b() {
                return new C1024n();
            }
        };
        this.f1714q = new AbstractC0952b<C1032v>() { // from class: com.nintendo.npf.sdk.internal.impl.x.8
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C1032v mo1074b() {
                return new C1032v();
            }
        };
        this.f1715r = new AbstractC0952b<C1028r>() { // from class: com.nintendo.npf.sdk.internal.impl.x.9
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C1028r mo1074b() {
                return new C1028r();
            }
        };
        this.f1716s = new AbstractC0952b<C0960d>() { // from class: com.nintendo.npf.sdk.internal.impl.x.10
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C0960d mo1074b() {
                return new C0960d();
            }
        };
        this.f1717t = new AbstractC0952b<C1001ac>() { // from class: com.nintendo.npf.sdk.internal.impl.x.11
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C1001ac mo1074b() {
                return new C1001ac();
            }
        };
        this.f1718u = new AbstractC0952b<C1002ad>() { // from class: com.nintendo.npf.sdk.internal.impl.x.13
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C1002ad mo1074b() {
                return new C1002ad();
            }
        };
        this.f1719v = new AbstractC0952b<C1003ae>() { // from class: com.nintendo.npf.sdk.internal.impl.x.14
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C1003ae mo1074b() {
                return new C1003ae();
            }
        };
        this.f1720w = new AbstractC0952b<C1030t>() { // from class: com.nintendo.npf.sdk.internal.impl.x.15
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C1030t mo1074b() {
                return new C1030t();
            }
        };
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: a */
    public Application mo1047a() {
        return this.f1698a;
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: b */
    public C1026p mo1048b() {
        return this.f1699b.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: c */
    public C0998a mo1049c() {
        return this.f1700c.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: d */
    public C1018h mo1050d() {
        return this.f1701d.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: e */
    public C1027q mo1051e() {
        return this.f1702e.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: f */
    public C0999aa mo1052f() {
        return this.f1703f.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: g */
    public C1035y mo1053g() {
        return this.f1704g.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: h */
    public C1000ab mo1054h() {
        return this.f1705h.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: i */
    public C1036z mo1055i() {
        return this.f1706i.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: j */
    public C1014d mo1056j() {
        return this.f1707j.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: k */
    public C1029s mo1057k() {
        return this.f1708k.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: l */
    public C0960d mo1058l() {
        return this.f1716s.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: m */
    public C1001ac mo1059m() {
        return this.f1717t.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: n */
    public C1002ad mo1060n() {
        return this.f1718u.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: o */
    public C1003ae mo1061o() {
        return this.f1719v.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: p */
    public C1023m mo1062p() {
        return this.f1709l.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: q */
    public AbstractC0880e mo1063q() {
        return this.f1710m.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: r */
    public C0948c mo1064r() {
        return this.f1711n.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: s */
    public C0947b mo1065s() {
        return this.f1712o.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: t */
    public C1024n mo1066t() {
        return this.f1713p.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: u */
    public C1032v mo1067u() {
        return this.f1714q.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: v */
    public C1028r mo1068v() {
        return this.f1715r.m1386c();
    }

    @Override // com.nintendo.npf.sdk.internal.InterfaceC0875a
    /* JADX INFO: renamed from: w */
    public C1030t mo1069w() {
        return this.f1720w.m1386c();
    }
}
