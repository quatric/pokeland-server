package com.nintendo.npf.sdk.internal.impl;

import android.util.Base64;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.metaps.common.C0854h;
import com.metaps.common.C0856j;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.NPFSDK;
import com.nintendo.npf.sdk.audit.ProfanityWord;
import com.nintendo.npf.sdk.inquiry.InquiryStatus;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.mynintendo.MissionStatus;
import com.nintendo.npf.sdk.promo.PromoCodeBundle;
import com.nintendo.npf.sdk.subscription.SubscriptionProduct;
import com.nintendo.npf.sdk.subscription.SubscriptionPurchase;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.user.Mii;
import com.nintendo.npf.sdk.user.NintendoAccount;
import com.nintendo.npf.sdk.user.OtherUser;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchaseSummaryBySku;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyTransaction;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class NativeBridgeUtil {

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil$a */
    private static class C0961a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1342a = InterfaceC0875a.a.m1072b();
    }

    /* JADX INFO: renamed from: a */
    private static JSONObject m1447a(Map<String, VirtualCurrencyPurchaseSummaryBySku> map) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry<String, VirtualCurrencyPurchaseSummaryBySku> entry : map.entrySet()) {
            VirtualCurrencyPurchaseSummaryBySku value = entry.getValue();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("sku", value.getSKU());
            jSONObject2.put("count", value.getCount());
            jSONObject2.put("purchasedAmount", value.getPurchasedAmount());
            jSONObject2.put("purchasedUSD", value.getPurchasedUSD());
            jSONObject.put(entry.getKey(), jSONObject2);
        }
        return jSONObject;
    }

    public static String getMarket() {
        return NPFSDK.getMarket();
    }

    public static String getRuntimeOSVersion() {
        return "Android " + C0961a.f1342a.mo1065s().m1344s();
    }

    public static String getTargetedOS() {
        return C0854h.f926h;
    }

    public static int getTimeZoneOffsetMin() {
        TimeZone timeZone = new GregorianCalendar().getTimeZone();
        return ((timeZone.getRawOffset() + timeZone.getDSTSavings()) / 1000) / 60;
    }

    public static JSONObject parseJsonString(String str) {
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    public static JSONObject toJSONFromMissionStatus(MissionStatus missionStatus) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("completed", missionStatus.isCompleted());
        jSONObject.put("currentSteps", missionStatus.getCurrentSteps() != null ? missionStatus.getCurrentSteps() : JSONObject.NULL);
        jSONObject.put("limited", missionStatus.getLimited());
        jSONObject.put("limitEndsAt", missionStatus.getLimitEndsAt());
        jSONObject.put("detail", missionStatus.getDetail());
        jSONObject.put("missionId", missionStatus.getMissionId());
        jSONObject.put("missionKey", missionStatus.getMissionKey());
        jSONObject.put("pointAmount", missionStatus.getPointAmount());
        jSONObject.put("timesCompleted", missionStatus.getTimesCompleted() != null ? missionStatus.getTimesCompleted() : JSONObject.NULL);
        jSONObject.put(C0856j.f955a, missionStatus.getTitle());
        jSONObject.put("totalSteps", missionStatus.getTotalSteps());
        if (missionStatus.getAvailableGifts() != null) {
            Map<String, Long> availableGifts = missionStatus.getAvailableGifts();
            JSONObject jSONObject2 = new JSONObject();
            for (String str : availableGifts.keySet()) {
                jSONObject2.put(str, availableGifts.get(str));
            }
            jSONObject.put("availableGifts", jSONObject2);
        } else {
            jSONObject.put("availableGifts", JSONObject.NULL);
        }
        return jSONObject;
    }

    public static JSONObject toJsonFromBaaSUser(BaaSUser baaSUser) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("userId", baaSUser.getUserId() != null ? baaSUser.getUserId() : JSONObject.NULL);
        jSONObject.put("idToken", baaSUser.getIdToken() != null ? baaSUser.getIdToken() : JSONObject.NULL);
        jSONObject.put("accessToken", baaSUser.getAccessToken() != null ? baaSUser.getAccessToken() : JSONObject.NULL);
        jSONObject.put("deviceAccount", baaSUser.getDeviceAccount() != null ? baaSUser.getDeviceAccount() : JSONObject.NULL);
        jSONObject.put("devicePassword", baaSUser.getDevicePassword() != null ? baaSUser.getDevicePassword() : JSONObject.NULL);
        jSONObject.put("nickname", baaSUser.getNickname() != null ? baaSUser.getNickname() : JSONObject.NULL);
        jSONObject.put("country", baaSUser.getCountry() != null ? baaSUser.getCountry() : JSONObject.NULL);
        jSONObject.put("gender", baaSUser.getGender() != null ? baaSUser.getGender().toString().toLowerCase() : JSONObject.NULL);
        jSONObject.put("birthdayYear", baaSUser.getBirthdayYear() != null ? baaSUser.getBirthdayYear() : JSONObject.NULL);
        jSONObject.put("birthdayMonth", baaSUser.getBirthdayMonth() != null ? baaSUser.getBirthdayMonth() : JSONObject.NULL);
        jSONObject.put("birthdayDay", baaSUser.getBirthdayDay() != null ? baaSUser.getBirthdayDay() : JSONObject.NULL);
        jSONObject.put("hasUnreadCsComment", baaSUser.getInquiryStatus() != null ? Boolean.valueOf(baaSUser.getInquiryStatus().isHavingUnreadComments()) : JSONObject.NULL);
        jSONObject.put("personalAnalytics", baaSUser.isPersonalAnalytics());
        jSONObject.put("personalNotification", baaSUser.isPersonalNotification());
        jSONObject.put("createdAt", baaSUser.getCreatedAt());
        jSONObject.put("nintendoAccount", baaSUser.getNintendoAccount() != null ? toJsonFromNintendoAccount(baaSUser.getNintendoAccount()) : JSONObject.NULL);
        return jSONObject;
    }

    public static JSONObject toJsonFromInquiryStatus(InquiryStatus inquiryStatus) throws JSONException {
        if (inquiryStatus == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("hasUnreadCsComment", inquiryStatus.isHavingUnreadComments());
        return jSONObject;
    }

    public static JSONObject toJsonFromMii(Mii mii) throws JSONException {
        if (mii == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("imageUriTemplate", mii.getUrlTemplate());
        jSONObject.put("miiId", mii.getMiiId());
        if (mii.getCoreData() != null) {
            jSONObject.put("coreData", Base64.encodeToString(mii.getCoreData(), 0));
        } else {
            jSONObject.put("coreData", JSONObject.NULL);
        }
        if (mii.getStoreData() != null) {
            jSONObject.put("storeData", Base64.encodeToString(mii.getStoreData(), 0));
        } else {
            jSONObject.put("storeData", JSONObject.NULL);
        }
        jSONObject.put("imageOrigin", mii.getImageOrigin());
        jSONObject.put("etag", mii.getEtag());
        jSONObject.put("favoriteColor", mii.getFavoriteColor().toString().toLowerCase());
        return jSONObject;
    }

    public static JSONArray toJsonFromMissionStatuses(List<MissionStatus> list) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        Iterator<MissionStatus> it = list.iterator();
        while (it.hasNext()) {
            jSONArray.put(toJSONFromMissionStatus(it.next()));
        }
        return jSONArray;
    }

    public static JSONObject toJsonFromNPFError(NPFError nPFError) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("errorType", nPFError.getErrorType().name());
        jSONObject.put("errorCode", nPFError.getErrorCode());
        jSONObject.put("errorMessage", nPFError.getErrorMessage() != null ? nPFError.getErrorMessage() : JSONObject.NULL);
        return jSONObject;
    }

    public static JSONObject toJsonFromNintendoAccount(NintendoAccount nintendoAccount) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("nintendoAccountId", nintendoAccount.getNintendoAccountId());
        jSONObject.put("type", nintendoAccount.getType() != null ? nintendoAccount.getType().toString().toLowerCase() : JSONObject.NULL);
        jSONObject.put("nickname", nintendoAccount.getNickname() != null ? nintendoAccount.getNickname() : JSONObject.NULL);
        jSONObject.put("country", nintendoAccount.getCountry() != null ? nintendoAccount.getCountry() : JSONObject.NULL);
        jSONObject.put("region", nintendoAccount.getRegion() != null ? nintendoAccount.getRegion() : JSONObject.NULL);
        jSONObject.put("language", nintendoAccount.getLanguage() != null ? nintendoAccount.getLanguage() : JSONObject.NULL);
        jSONObject.put("timezone", nintendoAccount.getTimezone() != null ? nintendoAccount.getTimezone() : JSONObject.NULL);
        jSONObject.put("gender", nintendoAccount.getGender() != null ? nintendoAccount.getGender().toString().toLowerCase() : JSONObject.NULL);
        jSONObject.put("birthdayYear", nintendoAccount.getBirthdayYear() != null ? nintendoAccount.getBirthdayYear() : JSONObject.NULL);
        jSONObject.put("birthdayMonth", nintendoAccount.getBirthdayMonth() != null ? nintendoAccount.getBirthdayMonth() : JSONObject.NULL);
        jSONObject.put("birthdayDay", nintendoAccount.getBirthdayDay() != null ? nintendoAccount.getBirthdayDay() : JSONObject.NULL);
        jSONObject.put("email", nintendoAccount.getEmail() != null ? nintendoAccount.getEmail() : JSONObject.NULL);
        jSONObject.put("nintendoNetworkId", nintendoAccount.getNintendoNetworkId() != null ? nintendoAccount.getNintendoNetworkId() : JSONObject.NULL);
        jSONObject.put("mii", nintendoAccount.getMii() != null ? toJsonFromMii(nintendoAccount.getMii()) : JSONObject.NULL);
        jSONObject.put("accessToken", nintendoAccount.getAccessToken() != null ? nintendoAccount.getAccessToken() : JSONObject.NULL);
        jSONObject.put("idToken", nintendoAccount.getIdToken() != null ? nintendoAccount.getIdToken() : JSONObject.NULL);
        return jSONObject;
    }

    public static JSONArray toJsonFromOtherUsers(List<OtherUser> list) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (OtherUser otherUser : list) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("userId", otherUser.getUserId() != null ? otherUser.getUserId() : JSONObject.NULL);
            jSONObject.put("nickname", otherUser.getNickname() != null ? otherUser.getNickname() : JSONObject.NULL);
            jSONObject.put("nintendoAccountNickname", otherUser.getNintendoAccountNickname() != null ? otherUser.getNintendoAccountNickname() : JSONObject.NULL);
            jSONObject.put("nintendoAccountMii", otherUser.getNintendoAccountMii() != null ? toJsonFromMii(otherUser.getNintendoAccountMii()) : JSONObject.NULL);
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    public static JSONArray toJsonFromProfanityWords(List<ProfanityWord> list) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (ProfanityWord profanityWord : list) {
            JSONObject jSONObject = new JSONObject();
            if (profanityWord.getDictionaryType() == ProfanityWord.ProfanityDictionaryType.NICKNAME) {
                jSONObject.put("dictionaryType", "nickname");
            } else {
                jSONObject.put("dictionaryType", "common");
            }
            jSONObject.put("language", profanityWord.getLanguage());
            jSONObject.put("text", profanityWord.getText());
            if (profanityWord.getCheckStatus() == ProfanityWord.ProfanityCheckStatus.VALID) {
                jSONObject.put("checkStatus", "valid");
            } else if (profanityWord.getCheckStatus() == ProfanityWord.ProfanityCheckStatus.INVALID) {
                jSONObject.put("checkStatus", "invalid");
            } else {
                jSONObject.put("checkStatus", "unchecked");
            }
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    public static JSONArray toJsonFromPromoCodeBundle(List<PromoCodeBundle> list) throws JSONException {
        if (list == null) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        for (PromoCodeBundle promoCodeBundle : list) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("sku", promoCodeBundle.getSku());
            jSONObject.put("customAttribute", promoCodeBundle.getCustomAttribute() != null ? promoCodeBundle.getCustomAttribute() : JSONObject.NULL);
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    public static JSONArray toJsonFromSubscriptionProducts(List<SubscriptionProduct> list) {
        if (list == null) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        Iterator<SubscriptionProduct> it = list.iterator();
        while (it.hasNext()) {
            JSONObject jSONObjectM1777a = C0961a.f1342a.mo1053g().m1777a(it.next());
            if (jSONObjectM1777a != null) {
                jSONArray.put(jSONObjectM1777a);
            }
        }
        return jSONArray;
    }

    public static JSONArray toJsonFromSubscriptionPurchases(List<SubscriptionPurchase> list) {
        if (list == null) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        Iterator<SubscriptionPurchase> it = list.iterator();
        while (it.hasNext()) {
            JSONObject jSONObjectM1533a = C0961a.f1342a.mo1052f().m1533a(it.next());
            if (jSONObjectM1533a != null) {
                jSONArray.put(jSONObjectM1533a);
            }
        }
        return jSONArray;
    }

    public static JSONObject toJsonFromVCBundles(Map<String, List<VirtualCurrencyBundle>> map) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String str : map.keySet()) {
            List<VirtualCurrencyBundle> list = map.get(str);
            JSONArray jSONArray = new JSONArray();
            for (VirtualCurrencyBundle virtualCurrencyBundle : list) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("sku", virtualCurrencyBundle.getSKU());
                jSONObject2.put(C0856j.f955a, virtualCurrencyBundle.getTitle());
                jSONObject2.put(FirebaseAnalytics.Param.PRICE, virtualCurrencyBundle.getPrice());
                jSONObject2.put("priceCode", virtualCurrencyBundle.getPriceCode());
                jSONObject2.put("displayPrice", virtualCurrencyBundle.getDisplayPrice());
                jSONObject2.put("usdPrice", virtualCurrencyBundle.getUsdPrice());
                jSONObject2.put("detail", virtualCurrencyBundle.getDetail());
                jSONObject2.put("virtualCurrencyName", virtualCurrencyBundle.getVirtualCurrencyName());
                jSONObject2.put("amount", virtualCurrencyBundle.getAmount());
                jSONObject2.put("extraAmount", virtualCurrencyBundle.getExtraAmount());
                jSONObject2.put("customAttribute", virtualCurrencyBundle.getCustomAttribute() != null ? virtualCurrencyBundle.getCustomAttribute() : JSONObject.NULL);
                jSONArray.put(jSONObject2);
            }
            jSONObject.put(str, jSONArray);
        }
        return jSONObject;
    }

    public static JSONObject toJsonFromVCPurchaseSummaries(Map<String, VirtualCurrencyPurchasedSummary> map) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String str : map.keySet()) {
            VirtualCurrencyPurchasedSummary virtualCurrencyPurchasedSummary = map.get(str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("virtualCurrencyName", virtualCurrencyPurchasedSummary.getVirtualCurrencyName());
            jSONObject2.put("lifeTimePurchasedUSD", virtualCurrencyPurchasedSummary.getLifeTimePurchasedUSD());
            jSONObject2.put("lifeTimePurchasedAmount", virtualCurrencyPurchasedSummary.getLifeTimePurchasedAmount());
            jSONObject2.put("lifeTimePurchasesBySKU", m1447a(virtualCurrencyPurchasedSummary.getLifeTimePurchasesBySKU()));
            jSONObject2.put("thisDayPurchasedUSD", virtualCurrencyPurchasedSummary.getThisDayPurchasedUSD());
            jSONObject2.put("thisDayPurchasedAmount", virtualCurrencyPurchasedSummary.getThisDayPurchasedAmount());
            jSONObject2.put("thisDayPurchasesBySKU", m1447a(virtualCurrencyPurchasedSummary.getThisDayPurchasesBySKU()));
            jSONObject2.put("thisMonthPurchasedUSD", virtualCurrencyPurchasedSummary.getThisMonthPurchasedUSD());
            jSONObject2.put("thisMonthPurchasedAmount", virtualCurrencyPurchasedSummary.getThisMonthPurchasedAmount());
            jSONObject2.put("thisMonthPurchasesBySKU", m1447a(virtualCurrencyPurchasedSummary.getThisMonthPurchasesBySKU()));
            jSONObject.put(str, jSONObject2);
        }
        return jSONObject;
    }

    public static JSONArray toJsonFromVCTransactions(List<VirtualCurrencyTransaction> list) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (VirtualCurrencyTransaction virtualCurrencyTransaction : list) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("orderId", virtualCurrencyTransaction.getOrderId());
            jSONObject.put("sku", virtualCurrencyTransaction.getSKU());
            jSONObject.put("state", virtualCurrencyTransaction.getState().toString());
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    public static JSONObject toJsonFromVCWallets(Map<String, VirtualCurrencyWallet> map) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String str : map.keySet()) {
            VirtualCurrencyWallet virtualCurrencyWallet = map.get(str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("virtualCurrencyName", virtualCurrencyWallet.getVirtualCurrencyName());
            jSONObject2.put("totalBalance", virtualCurrencyWallet.getTotalBalance());
            jSONObject2.put("freeBalance", virtualCurrencyWallet.getFreeBalance());
            Map<String, Integer> paidBalance = virtualCurrencyWallet.getPaidBalance();
            JSONObject jSONObject3 = new JSONObject();
            for (String str2 : paidBalance.keySet()) {
                jSONObject3.put(str2, paidBalance.get(str2));
            }
            jSONObject2.put("paidBalance", jSONObject3);
            jSONObject.put(str, jSONObject2);
        }
        return jSONObject;
    }

    public static JSONArray toJsonFromWordList(List<ProfanityWord> list) throws JSONException {
        if (list == null) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        for (ProfanityWord profanityWord : list) {
            JSONObject jSONObject = new JSONObject();
            if (profanityWord.getDictionaryType() == ProfanityWord.ProfanityDictionaryType.NICKNAME) {
                jSONObject.put("dictionaryType", "nickname");
            } else {
                jSONObject.put("dictionaryType", "common");
            }
            jSONObject.put("language", profanityWord.getLanguage());
            jSONObject.put("text", profanityWord.getText());
            if (profanityWord.getCheckStatus() == ProfanityWord.ProfanityCheckStatus.VALID) {
                jSONObject.put("checkStatus", "valid");
            } else if (profanityWord.getCheckStatus() == ProfanityWord.ProfanityCheckStatus.INVALID) {
                jSONObject.put("checkStatus", "invalid");
            } else {
                jSONObject.put("checkStatus", "unchecked");
            }
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    public static Object toNullableJsonFromBaaSUser(BaaSUser baaSUser) throws JSONException {
        return baaSUser != null ? toJsonFromBaaSUser(baaSUser) : JSONObject.NULL;
    }

    public static Object toNullableJsonFromInquiryStatus(InquiryStatus inquiryStatus) throws JSONException {
        return inquiryStatus != null ? toJsonFromInquiryStatus(inquiryStatus) : JSONObject.NULL;
    }

    public static Object toNullableJsonFromMissionStatuses(List<MissionStatus> list) throws JSONException {
        return list != null ? toJsonFromMissionStatuses(list) : JSONObject.NULL;
    }

    public static Object toNullableJsonFromNPFError(NPFError nPFError) throws JSONException {
        return nPFError != null ? toJsonFromNPFError(nPFError) : JSONObject.NULL;
    }

    public static Object toNullableJsonFromNintendoAccount(NintendoAccount nintendoAccount) throws JSONException {
        return nintendoAccount != null ? toJsonFromNintendoAccount(nintendoAccount) : JSONObject.NULL;
    }

    public static Object toNullableJsonFromOtherUsers(List<OtherUser> list) throws JSONException {
        return list != null ? toJsonFromOtherUsers(list) : JSONObject.NULL;
    }

    public static Object toNullableJsonFromProfanityWords(List<ProfanityWord> list) throws JSONException {
        return list != null ? toJsonFromProfanityWords(list) : JSONObject.NULL;
    }

    public static Object toNullableJsonFromSubscriptionProducts(List<SubscriptionProduct> list) {
        return list != null ? toJsonFromSubscriptionProducts(list) : JSONObject.NULL;
    }

    public static Object toNullableJsonFromSubscriptionPurchases(List<SubscriptionPurchase> list) {
        return list != null ? toJsonFromSubscriptionPurchases(list) : JSONObject.NULL;
    }

    public static Object toNullableJsonFromVCBundles(Map<String, List<VirtualCurrencyBundle>> map) throws JSONException {
        return map != null ? toJsonFromVCBundles(map) : JSONObject.NULL;
    }

    public static Object toNullableJsonFromVCPurchaseSummaries(Map<String, VirtualCurrencyPurchasedSummary> map) throws JSONException {
        return map != null ? toJsonFromVCPurchaseSummaries(map) : JSONObject.NULL;
    }

    public static Object toNullableJsonFromVCTransactions(List<VirtualCurrencyTransaction> list) throws JSONException {
        return list != null ? toJsonFromVCTransactions(list) : JSONObject.NULL;
    }

    public static Object toNullableJsonFromVCWallets(Map<String, VirtualCurrencyWallet> map) throws JSONException {
        return map != null ? toJsonFromVCWallets(map) : JSONObject.NULL;
    }
}
