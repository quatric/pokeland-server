package com.nintendo.npf.sdk.internal.impl.cpp;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import com.nintendo.npf.sdk.promo.PromoCode;
import com.nintendo.npf.sdk.promo.PromoCodeBundle;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class PromoCodeEventHandler implements PromoCode.EventHandler {
    public static void checkRemainExchangePromotionPurchased(final long j, final long j2) {
        PromoCode.checkRemainExchangePromotionPurchased(new PromoCode.CheckRemainExchangePromotionPurchasedCallback() { // from class: com.nintendo.npf.sdk.internal.impl.cpp.PromoCodeEventHandler.1
            @Override // com.nintendo.npf.sdk.promo.PromoCode.CheckRemainExchangePromotionPurchasedCallback
            public void onComplete(List<PromoCodeBundle> list, NPFError nPFError) {
                String str;
                String str2;
                String string;
                String string2 = null;
                try {
                    if (nPFError != null) {
                        string = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
                    } else {
                        string2 = NativeBridgeUtil.toJsonFromPromoCodeBundle(list).toString();
                        string = null;
                    }
                    str2 = string;
                    str = string2;
                } catch (JSONException e) {
                    e.printStackTrace();
                    str = string2;
                    str2 = str;
                }
                PromoCodeEventHandler.onCheckRemainExchangePromotionPurchasedCallback(j, j2, str, str2);
            }
        });
    }

    public static void exchangePromotionPurchased(final long j, final long j2) {
        PromoCode.exchangePromotionPurchased(new PromoCode.ExchangePromotionPurchasedCallback() { // from class: com.nintendo.npf.sdk.internal.impl.cpp.PromoCodeEventHandler.2
            @Override // com.nintendo.npf.sdk.promo.PromoCode.ExchangePromotionPurchasedCallback
            public void onComplete(List<PromoCodeBundle> list, NPFError nPFError) {
                String str;
                String str2;
                String string;
                String string2 = null;
                try {
                    if (nPFError != null) {
                        string = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
                    } else {
                        string2 = NativeBridgeUtil.toJsonFromPromoCodeBundle(list).toString();
                        string = null;
                    }
                    str2 = string;
                    str = string2;
                } catch (JSONException e) {
                    e.printStackTrace();
                    str = string2;
                    str2 = str;
                }
                PromoCodeEventHandler.onExchangePromotionPurchasedCallback(j, j2, str, str2);
            }
        });
    }

    public static void init() {
        PromoCode.init(new PromoCodeEventHandler());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void onCheckRemainExchangePromotionPurchasedCallback(long j, long j2, String str, String str2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void onExchangePromotionPurchasedCallback(long j, long j2, String str, String str2);

    private static native void onOthersNotificationSuccess(String str);

    private static native void onPromotionNotificationSuccess(String str);

    private static native void onPromotionNotoficationError(String str);

    @Override // com.nintendo.npf.sdk.promo.PromoCode.EventHandler
    public void onOthersNotificationSuccess(List<PromoCodeBundle> list) {
        String string;
        try {
            string = NativeBridgeUtil.toJsonFromPromoCodeBundle(list).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            string = null;
        }
        onOthersNotificationSuccess(string);
    }

    @Override // com.nintendo.npf.sdk.promo.PromoCode.EventHandler
    public void onPromotionNotificationSuccess(List<PromoCodeBundle> list) {
        String string;
        try {
            string = NativeBridgeUtil.toJsonFromPromoCodeBundle(list).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            string = null;
        }
        onPromotionNotificationSuccess(string);
    }

    @Override // com.nintendo.npf.sdk.promo.PromoCode.EventHandler
    public void onPromotionNotoficationError(NPFError nPFError) {
        String string;
        try {
            string = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            string = null;
        }
        onPromotionNotoficationError(string);
    }
}
