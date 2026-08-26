package com.nintendo.npf.sdk.promo;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class PromoCode {

    public interface CheckRemainExchangePromotionPurchasedCallback {
        void onComplete(List<PromoCodeBundle> list, NPFError nPFError);
    }

    public interface EventHandler {
        void onOthersNotificationSuccess(List<PromoCodeBundle> list);

        void onPromotionNotificationSuccess(List<PromoCodeBundle> list);

        void onPromotionNotoficationError(NPFError nPFError);
    }

    public interface ExchangePromotionPurchasedCallback {
        void onComplete(List<PromoCodeBundle> list, NPFError nPFError);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.promo.PromoCode$a */
    private static class C1049a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1781a = InterfaceC0875a.a.m1072b();
    }

    public static void checkRemainExchangePromotionPurchased(final CheckRemainExchangePromotionPurchasedCallback checkRemainExchangePromotionPurchasedCallback) {
        C1049a.f1781a.mo1069w().m1731a(new CheckRemainExchangePromotionPurchasedCallback() { // from class: com.nintendo.npf.sdk.promo.PromoCode.2
            @Override // com.nintendo.npf.sdk.promo.PromoCode.CheckRemainExchangePromotionPurchasedCallback
            public void onComplete(List<PromoCodeBundle> list, NPFError nPFError) {
                CheckRemainExchangePromotionPurchasedCallback checkRemainExchangePromotionPurchasedCallback2 = checkRemainExchangePromotionPurchasedCallback;
                if (checkRemainExchangePromotionPurchasedCallback2 != null) {
                    checkRemainExchangePromotionPurchasedCallback2.onComplete(list, nPFError);
                }
            }
        });
    }

    public static void exchangePromotionPurchased(final ExchangePromotionPurchasedCallback exchangePromotionPurchasedCallback) {
        C1049a.f1781a.mo1069w().m1733a(new ExchangePromotionPurchasedCallback() { // from class: com.nintendo.npf.sdk.promo.PromoCode.3
            @Override // com.nintendo.npf.sdk.promo.PromoCode.ExchangePromotionPurchasedCallback
            public void onComplete(List<PromoCodeBundle> list, NPFError nPFError) {
                ExchangePromotionPurchasedCallback exchangePromotionPurchasedCallback2 = exchangePromotionPurchasedCallback;
                if (exchangePromotionPurchasedCallback2 != null) {
                    exchangePromotionPurchasedCallback2.onComplete(list, nPFError);
                }
            }
        });
    }

    public static void init(final EventHandler eventHandler) {
        C1049a.f1781a.mo1069w().m1732a(new EventHandler() { // from class: com.nintendo.npf.sdk.promo.PromoCode.1
            @Override // com.nintendo.npf.sdk.promo.PromoCode.EventHandler
            public void onOthersNotificationSuccess(List<PromoCodeBundle> list) {
                EventHandler eventHandler2 = eventHandler;
                if (eventHandler2 != null) {
                    eventHandler2.onOthersNotificationSuccess(list);
                }
            }

            @Override // com.nintendo.npf.sdk.promo.PromoCode.EventHandler
            public void onPromotionNotificationSuccess(List<PromoCodeBundle> list) {
                EventHandler eventHandler2 = eventHandler;
                if (eventHandler2 != null) {
                    eventHandler2.onPromotionNotificationSuccess(list);
                }
            }

            @Override // com.nintendo.npf.sdk.promo.PromoCode.EventHandler
            public void onPromotionNotoficationError(NPFError nPFError) {
                EventHandler eventHandler2 = eventHandler;
                if (eventHandler2 != null) {
                    eventHandler2.onPromotionNotoficationError(nPFError);
                }
            }
        });
    }
}
