package com.metaps.analytics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.metaps.analytics.assist.C0803f;
import com.metaps.analytics.assist.C0805h;
import com.metaps.analytics.assist.C0806i;
import com.metaps.analytics.assist.C0807j;
import com.metaps.analytics.assist.C0808k;
import com.metaps.analytics.assist.C0809l;
import com.metaps.analytics.assist.C0811n;
import com.metaps.common.C0847a;
import com.metaps.common.C0853g;
import com.metaps.common.C0854h;
import com.metaps.common.C0855i;
import com.metaps.common.C0856j;
import com.metaps.common.C0857k;
import com.metaps.common.Metaps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: renamed from: com.metaps.analytics.a */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0785a {

    /* JADX INFO: renamed from: a */
    private static C0819h f370a;

    /* JADX INFO: renamed from: b */
    private static C0836y f371b;

    /* JADX INFO: renamed from: c */
    private static String f372c;

    /* JADX INFO: renamed from: d */
    private static String f373d;

    /* JADX INFO: renamed from: e */
    private static String f374e;

    /* JADX INFO: renamed from: f */
    private static HashMap<Integer, C0817f> f375f = new HashMap<>();

    /* JADX INFO: renamed from: g */
    private static HashMap<Integer, C0813b> f376g = new HashMap<>();

    /* JADX INFO: renamed from: h */
    private static LinkedList<C0813b> f377h = new LinkedList<>();

    /* JADX INFO: renamed from: i */
    private static HashMap<String, String> f378i = new HashMap<>();

    /* JADX INFO: renamed from: j */
    private static List<C0822k> f379j = new ArrayList();

    /* JADX INFO: renamed from: k */
    private static ArrayList<String> f380k = new ArrayList<String>() { // from class: com.metaps.analytics.a.1

        /* JADX INFO: renamed from: a */
        private static final long f381a = 1;

        {
            add(Analytics.PROFILE_KEY_ORIGINAL_ID);
            add(Analytics.PROFILE_KEY_NAME);
            add(Analytics.PROFILE_KEY_AGE);
            add(Analytics.PROFILE_KEY_AGE_GROUP);
            add(Analytics.PROFILE_KEY_BIRTHDAY);
            add(Analytics.PROFILE_KEY_GENDER);
            add(Analytics.PROFILE_KEY_LEVEL);
            add(Analytics.PROFILE_KEY_RANK);
            add(Analytics.PROFILE_KEY_FRIENDS_COUNT);
        }
    };

    private C0785a() {
    }

    /* JADX INFO: renamed from: a */
    private static String m611a(Intent intent) {
        String dataString;
        String str = f374e;
        if (str != null) {
            f374e = null;
            return str;
        }
        if (intent == null || (dataString = intent.getDataString()) == null || dataString.equals(f373d)) {
            return null;
        }
        f373d = dataString;
        return dataString;
    }

    /* JADX INFO: renamed from: a */
    protected static void m612a() {
        C0819h c0819h = f370a;
        if (c0819h == null) {
            C0847a.m911c("You must call start() before to call resetSettings()");
        } else {
            c0819h.m796a(true);
        }
    }

    /* JADX INFO: renamed from: a */
    private static void m613a(Activity activity, C0813b c0813b) {
        synchronized (f375f) {
            Context applicationContext = activity.getApplicationContext();
            int iM758a = c0813b.m758a();
            C0847a.m903a("AnalyticsCore", "sendBootupEvent() is called. syncMapKey:" + iM758a);
            long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
            long jM895b = f371b.m895b(jCurrentTimeMillis);
            String strM893a = f371b.m893a(jCurrentTimeMillis);
            C0817f c0817f = new C0817f(jM895b, strM893a, C0853g.m947e(applicationContext), c0813b.m759b(), c0813b.m760c());
            f370a.m794a(c0817f);
            f371b.m894a(strM893a);
            C0803f.m683b().m688a(jM895b);
            C0813b c0813bRemove = f376g.remove(new Integer(iM758a));
            if (c0813bRemove != null) {
                m614a(activity, c0813bRemove, c0817f);
            } else {
                C0847a.m903a("AnalyticsCore", "Item is added to bootupSessionSyncMap. syncMapKey:" + iM758a);
                f375f.put(Integer.valueOf(iM758a), c0817f);
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private static void m614a(Activity activity, C0813b c0813b, C0817f c0817f) {
        C0813b c0813bPoll;
        Context applicationContext = activity.getApplicationContext();
        C0847a.m903a("AnalyticsCore", "sendSessionEvent() is called. syncMapKey:" + c0813b.m758a());
        long jCurrentTimeMillis = System.currentTimeMillis();
        f370a.m794a(new C0825n((jCurrentTimeMillis - c0817f.m777k()) / 1000, f371b.m892a(), C0853g.m947e(applicationContext), c0813b.m759b()));
        f371b.m896c(jCurrentTimeMillis / 1000);
        synchronized (f375f) {
            if (!f377h.isEmpty() && (c0813bPoll = f377h.poll()) != null) {
                m613a(activity, c0813bPoll);
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: a */
    protected static synchronized void m615a(Activity activity, String str) {
        C0847a.m902a("You call AnalyticsCore.start(activity, oneshotCurrentPage)");
        if (activity == null) {
            C0847a.m911c("activity parameter cannot be null");
            return;
        }
        if (Metaps.getApplicationId() != null && Metaps.getApplicationId().length() != 0) {
            Context applicationContext = activity.getApplicationContext();
            boolean zM976c = C0854h.m976c(applicationContext);
            if (f370a == null) {
                f370a = new C0819h(applicationContext);
                if (!f378i.isEmpty()) {
                    for (String str2 : f378i.keySet()) {
                        f370a.m795a(str2, f378i.get(str2));
                    }
                    f378i.clear();
                }
            }
            if (f371b == null) {
                f371b = new C0836y(applicationContext);
            }
            if (zM976c) {
                f370a.m794a(new C0820i());
            }
            String strM942b = C0853g.m942b(applicationContext);
            if (strM942b != null) {
                C0853g.m937a(applicationContext, (String) null);
                m616a(applicationContext, strM942b);
            }
            C0855i.m978a().m986a(applicationContext);
            int iHashCode = activity.hashCode();
            C0813b c0813b = new C0813b(iHashCode, m632c(activity, str), m611a(activity.getIntent()));
            synchronized (f375f) {
                if (f375f.isEmpty()) {
                    m613a(activity, c0813b);
                } else {
                    C0847a.m903a("AnalyticsCore", "Item is added to waitingBootupList. syncMapKey:" + iHashCode);
                    f377h.add(c0813b);
                }
            }
            if (!f379j.isEmpty()) {
                Iterator<C0822k> it = f379j.iterator();
                while (it.hasNext()) {
                    f370a.m794a(it.next());
                }
                f379j.clear();
            }
            return;
        }
        C0847a.m911c("You must call Metaps.initialize(String applicationId) with a valid application id before to call this method.");
    }

    /* JADX INFO: renamed from: a */
    protected static synchronized void m616a(Context context, String str) {
        if (Metaps.getApplicationId() == null || Metaps.getApplicationId().length() <= 0) {
            C0853g.m937a(context, str);
        }
    }

    /* JADX INFO: renamed from: a */
    protected static synchronized void m617a(C0857k c0857k) {
        C0847a.m908b("Track app launch from notification.");
        if (!c0857k.m1012b()) {
            C0847a.m911c("notification params is invalid");
        } else {
            f379j.add(new C0822k(c0857k));
        }
    }

    /* JADX INFO: renamed from: a */
    protected static synchronized void m618a(String str) {
        C0847a.m902a("You call AnalyticsCore.trackAction(" + str + ")");
        if (f370a == null) {
            C0847a.m911c("You must call start() before to call trackAction()");
            return;
        }
        if (str != null && str.length() != 0) {
            f370a.m794a(new C0815d(str));
            return;
        }
        C0847a.m911c("Name parameter cannot be null or blank in trackAction()");
    }

    /* JADX INFO: renamed from: a */
    protected static synchronized void m619a(String str, double d, String str2) {
        C0847a.m902a("You call AnalyticsCore.trackPurchase(" + str + ", " + d + ", " + str2 + ")");
        if (f370a == null) {
            C0847a.m911c("You must call start() before to call trackPurchase()");
        } else {
            f370a.m794a(new C0821j(str, d, str2));
        }
    }

    /* JADX INFO: renamed from: a */
    public static synchronized void m620a(String str, C0809l c0809l) {
        C0847a.m902a("You call AnalyticsCore.trackHouseAdClick()");
        if (f370a == null) {
            C0847a.m911c("You must call start() before to call trackHouseAdClick()");
        } else {
            f370a.m794a(new C0805h(str, c0809l));
        }
    }

    /* JADX INFO: renamed from: a */
    public static synchronized void m621a(String str, C0811n c0811n) {
        C0847a.m902a("You call AnalyticsCore.trackPromotionClick()");
        if (f370a == null) {
            C0847a.m911c("You must call start() before to call trackPromotionClick()");
        } else {
            f370a.m794a(new C0807j(str, c0811n));
        }
    }

    /* JADX INFO: renamed from: a */
    protected static synchronized void m622a(String str, String str2) {
        C0847a.m902a("You call AnalyticsCore.trackAction(" + str + ", " + str2 + ")");
        if (f370a == null) {
            C0847a.m911c("You must call start() before to call trackAction()");
            return;
        }
        if (str != null && str.length() != 0) {
            if (str2 != null && str2.length() != 0) {
                f370a.m794a(new C0815d(str, str2));
                return;
            }
            C0847a.m911c("Value parameter cannot be null or blank in trackAction()");
            return;
        }
        C0847a.m911c("Name parameter cannot be null or blank in trackAction()");
    }

    /* JADX INFO: renamed from: a */
    protected static synchronized void m623a(String str, String str2, int i) {
        C0847a.m902a("You call AnalyticsCore.trackEvent(" + str + ", " + str2 + ", " + i + ")");
        if (f370a == null) {
            C0847a.m911c("You must call start() before to call trackEvent()");
            return;
        }
        if (str != null && str.length() != 0) {
            if (str2 != null && str2.length() != 0) {
                f370a.m794a(new C0818g(str, str2, i));
                C0803f.m683b().m689a(str, str2, i);
                return;
            }
            C0847a.m911c("Name parameter cannot be null or blank in trackEvent()");
            return;
        }
        C0847a.m911c("Category parameter cannot be null or blank in trackEvent()");
    }

    /* JADX INFO: renamed from: a */
    public static synchronized void m624a(String str, List<C0809l> list) {
        C0847a.m902a("You call AnalyticsCore.trackHouseAdImp()");
        if (f370a == null) {
            C0847a.m911c("You must call start() before to call trackHouseAdImp()");
        } else {
            f370a.m794a(new C0806i(str, list));
        }
    }

    /* JADX INFO: renamed from: a */
    protected static void m625a(boolean z) {
        C0847a.m902a("You call AnalyticsCore.setLogEnabled(" + z + ")");
        C0847a.m906a(z);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: b */
    protected static synchronized void m626b(Activity activity, String str) {
        C0817f c0817fRemove;
        C0847a.m902a("You call AnalyticsCore.stop(activity, oneshotCurrentPage)");
        int iHashCode = activity.hashCode();
        synchronized (f375f) {
            c0817fRemove = f375f.remove(Integer.valueOf(iHashCode));
        }
        if (f370a == null) {
            C0847a.m911c("You must call start() before to call stop()");
            return;
        }
        C0813b c0813b = new C0813b(iHashCode, m632c(activity, str), null);
        if (c0817fRemove == null) {
            C0847a.m911c("You must call start() before to call stop() (Please check that you are calling start() in your Activity's onStart() method)");
            synchronized (f375f) {
                C0847a.m903a("AnalyticsCore", "Item is added to waitingSessionMap. syncMapKey:" + iHashCode);
                f376g.put(Integer.valueOf(iHashCode), c0813b);
            }
            return;
        }
        m614a(activity, c0813b, c0817fRemove);
        if (!f379j.isEmpty()) {
            Iterator<C0822k> it = f379j.iterator();
            while (it.hasNext()) {
                f370a.m794a(it.next());
            }
            f379j.clear();
        }
        C0856j.m1000b();
    }

    /* JADX INFO: renamed from: b */
    protected static void m627b(String str) {
        f372c = str;
    }

    /* JADX INFO: renamed from: b */
    protected static synchronized void m628b(String str, String str2) {
        C0847a.m902a("You call AnalyticsCore.setAttribute(" + str + ", " + str2 + ")");
        if (str != null && str.length() != 0) {
            if (str.length() > 32) {
                C0847a.m911c("Key length cannot be superior to 32.");
            }
            if (str2 != null && str2.length() > 128) {
                C0847a.m911c("Value length cannot be superior to 128");
            }
            if (f370a == null) {
                f378i.put(str, str2);
            } else {
                f370a.m795a(str, str2);
            }
            return;
        }
        C0847a.m911c("Key cannot be empty");
    }

    /* JADX INFO: renamed from: b */
    protected static synchronized void m629b(String str, String str2, int i) {
        C0847a.m902a("You call AnalyticsCore.trackSpend(" + str + ", " + str2 + "," + i + ")");
        if (f370a == null) {
            C0847a.m911c("You must call start() before to call trackSpend()");
            return;
        }
        if (str != null && str.length() != 0) {
            if (str2 != null && str2.length() != 0) {
                f370a.m794a(new C0826o(str, str2, i));
                return;
            }
            C0847a.m911c("Name parameter cannot be null or blank in trackSpend()");
            return;
        }
        C0847a.m911c("Category parameter cannot be null or blank in trackSpend()");
    }

    /* JADX INFO: renamed from: b */
    public static synchronized void m630b(String str, List<C0811n> list) {
        C0847a.m902a("You call AnalyticsCore.trackPromotionImp()");
        if (f370a == null) {
            C0847a.m911c("You must call start() before to call trackPromotionImp()");
        } else {
            f370a.m794a(new C0808k(str, list));
        }
    }

    /* JADX INFO: renamed from: b */
    protected static void m631b(boolean z) {
        C0847a.m902a("You call AnalyticsCore.setLocationEnabled(" + z + ")");
        C0855i.m978a().m987a(z);
    }

    /* JADX INFO: renamed from: c */
    private static String m632c(Activity activity, String str) {
        if (str != null) {
            return str;
        }
        String str2 = f372c;
        return str2 != null ? str2 : activity.getComponentName().getClassName();
    }

    /* JADX INFO: renamed from: c */
    protected static void m633c(String str) {
        f374e = str;
    }

    /* JADX INFO: renamed from: c */
    protected static synchronized void m634c(String str, String str2) {
        if (str == null) {
            C0847a.m911c("'" + str + "' is not a valid profile key");
        } else if (f380k.contains(str)) {
            m628b(str, str2);
        } else {
            C0847a.m911c("'" + str + "' is not a valid profile key");
        }
        throw th;
    }
}
