package com.nintendo.npf.sdk;

import android.app.Activity;
import android.app.Application;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1026p;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.user.NintendoAccount;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class NPFSDK {

    /* JADX INFO: renamed from: a */
    private static final String f1004a = "NPFSDK";

    /* JADX INFO: renamed from: b */
    private static final Object[] f1005b = new Object[0];

    /* JADX INFO: renamed from: c */
    private static C1026p f1006c;

    public interface EventHandler {
        void onBaaSAuthError(NPFError nPFError);

        void onBaaSAuthStart();

        void onBaaSAuthUpdate(BaaSUser baaSUser);

        void onNintendoAccountAuthError(NPFError nPFError);

        void onPendingAuthorizationByNintendoAccount2();

        void onPendingSwitchByNintendoAccount2();

        void onVirtualCurrencyPurchaseProcessError(NPFError nPFError);

        void onVirtualCurrencyPurchaseProcessSuccess(Map<String, VirtualCurrencyWallet> map);
    }

    public interface NPFErrorCallback {
        void onComplete(NPFError nPFError);
    }

    public static void authorizeByNintendoAccount(Activity activity, List<String> list, Map<String, String> map, final NintendoAccount.AuthorizationCallback authorizationCallback) {
        f1006c.m1667a(activity, list, new NintendoAccount.AuthorizationCallback() { // from class: com.nintendo.npf.sdk.NPFSDK.4
            @Override // com.nintendo.npf.sdk.user.NintendoAccount.AuthorizationCallback
            public void onComplete(NintendoAccount nintendoAccount, NPFError nPFError) {
                NintendoAccount.AuthorizationCallback authorizationCallback2 = authorizationCallback;
                if (authorizationCallback2 != null) {
                    authorizationCallback2.onComplete(nintendoAccount, nPFError);
                }
            }
        });
    }

    public static void authorizeByNintendoAccount2(Activity activity, List<String> list, Map<String, String> map, final NintendoAccount.AuthorizationCallback authorizationCallback) {
        f1006c.m1675b(activity, list, new NintendoAccount.AuthorizationCallback() { // from class: com.nintendo.npf.sdk.NPFSDK.5
            @Override // com.nintendo.npf.sdk.user.NintendoAccount.AuthorizationCallback
            public void onComplete(NintendoAccount nintendoAccount, NPFError nPFError) {
                NintendoAccount.AuthorizationCallback authorizationCallback2 = authorizationCallback;
                if (authorizationCallback2 != null) {
                    authorizationCallback2.onComplete(nintendoAccount, nPFError);
                }
            }
        });
    }

    public static void enableCommunicationStatistics() {
        f1006c.m1684k();
    }

    public static String getCapabilities() {
        return f1006c.m1687n();
    }

    public static BaaSUser getCurrentBaaSUser() {
        return f1006c.m1665a();
    }

    public static String getLanguage() {
        return f1006c.m1682i();
    }

    public static String getMarket() {
        return f1006c.m1683j();
    }

    public static String getNintendoAccountFAQURL() {
        return f1006c.m1676c();
    }

    public static int getReadTimeout() {
        return f1006c.m1677d();
    }

    public static int getRequestTimeout() {
        return f1006c.m1678e();
    }

    public static String getSDKVersion() {
        return f1006c.m1679f();
    }

    public static long getTotalRequestDataSize() {
        return f1006c.m1685l();
    }

    public static long getTotalResponseDataSize() {
        return f1006c.m1686m();
    }

    public static void init(Application application, final EventHandler eventHandler) {
        synchronized (f1005b) {
            if (f1006c == null) {
                InterfaceC0875a.a.m1071a(application);
                f1006c = InterfaceC0875a.a.m1072b().mo1048b();
            }
        }
        f1006c.m1668a(new EventHandler() { // from class: com.nintendo.npf.sdk.NPFSDK.1
            @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
            public void onBaaSAuthError(NPFError nPFError) {
                EventHandler eventHandler2 = eventHandler;
                if (eventHandler2 != null) {
                    eventHandler2.onBaaSAuthError(nPFError);
                }
            }

            @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
            public void onBaaSAuthStart() {
                EventHandler eventHandler2 = eventHandler;
                if (eventHandler2 != null) {
                    eventHandler2.onBaaSAuthStart();
                }
            }

            @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
            public void onBaaSAuthUpdate(BaaSUser baaSUser) {
                EventHandler eventHandler2 = eventHandler;
                if (eventHandler2 != null) {
                    eventHandler2.onBaaSAuthUpdate(baaSUser);
                }
            }

            @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
            public void onNintendoAccountAuthError(NPFError nPFError) {
                EventHandler eventHandler2 = eventHandler;
                if (eventHandler2 != null) {
                    eventHandler2.onNintendoAccountAuthError(nPFError);
                }
            }

            @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
            public void onPendingAuthorizationByNintendoAccount2() {
                EventHandler eventHandler2 = eventHandler;
                if (eventHandler2 != null) {
                    eventHandler2.onPendingAuthorizationByNintendoAccount2();
                }
            }

            @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
            public void onPendingSwitchByNintendoAccount2() {
                EventHandler eventHandler2 = eventHandler;
                if (eventHandler2 != null) {
                    eventHandler2.onPendingSwitchByNintendoAccount2();
                }
            }

            @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
            public void onVirtualCurrencyPurchaseProcessError(NPFError nPFError) {
                EventHandler eventHandler2 = eventHandler;
                if (eventHandler2 != null) {
                    eventHandler2.onVirtualCurrencyPurchaseProcessError(nPFError);
                }
            }

            @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
            public void onVirtualCurrencyPurchaseProcessSuccess(Map<String, VirtualCurrencyWallet> map) {
                EventHandler eventHandler2 = eventHandler;
                if (eventHandler2 != null) {
                    eventHandler2.onVirtualCurrencyPurchaseProcessSuccess(map);
                }
            }
        });
    }

    public static boolean isSandbox() {
        return f1006c.m1680g();
    }

    public static void resetDeviceAccount() {
        f1006c.m1681h();
    }

    public static void retryBaaSAuth(final BaaSUser.AuthorizationCallback authorizationCallback) {
        f1006c.m1669a(new BaaSUser.AuthorizationCallback() { // from class: com.nintendo.npf.sdk.NPFSDK.2
            @Override // com.nintendo.npf.sdk.user.BaaSUser.AuthorizationCallback
            public void onComplete(BaaSUser baaSUser, NPFError nPFError) {
                BaaSUser.AuthorizationCallback authorizationCallback2 = authorizationCallback;
                if (authorizationCallback2 != null) {
                    authorizationCallback2.onComplete(baaSUser, nPFError);
                }
            }
        });
    }

    public static void retryBaaSAuth(String str, String str2, final BaaSUser.AuthorizationCallback authorizationCallback) {
        f1006c.m1672a(str, str2, new BaaSUser.AuthorizationCallback() { // from class: com.nintendo.npf.sdk.NPFSDK.3
            @Override // com.nintendo.npf.sdk.user.BaaSUser.AuthorizationCallback
            public void onComplete(BaaSUser baaSUser, NPFError nPFError) {
                BaaSUser.AuthorizationCallback authorizationCallback2 = authorizationCallback;
                if (authorizationCallback2 != null) {
                    authorizationCallback2.onComplete(baaSUser, nPFError);
                }
            }
        });
    }

    public static void retryPendingAuthorizationByNintendoAccount2(final NintendoAccount.AuthorizationCallback authorizationCallback) {
        f1006c.m1670a(new NintendoAccount.AuthorizationCallback() { // from class: com.nintendo.npf.sdk.NPFSDK.6
            @Override // com.nintendo.npf.sdk.user.NintendoAccount.AuthorizationCallback
            public void onComplete(NintendoAccount nintendoAccount, NPFError nPFError) {
                NintendoAccount.AuthorizationCallback authorizationCallback2 = authorizationCallback;
                if (authorizationCallback2 != null) {
                    authorizationCallback2.onComplete(nintendoAccount, nPFError);
                }
            }
        });
    }

    public static void setLanguage(String str) {
        f1006c.m1671a(str);
    }

    public static void setReadTimeout(int i) {
        f1006c.m1666a(i);
    }

    public static void setRequestTimeout(int i) {
        f1006c.m1674b(i);
    }
}
