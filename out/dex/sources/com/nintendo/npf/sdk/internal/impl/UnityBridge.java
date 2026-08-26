package com.nintendo.npf.sdk.internal.impl;

import android.app.Activity;
import android.support.v4.view.MotionEventCompat;
import com.google.api.client.http.HttpStatusCodes;
import com.google.common.base.Ascii;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.NPFSDK;
import com.nintendo.npf.sdk.analytics.Analytics;
import com.nintendo.npf.sdk.audit.ProfanityWord;
import com.nintendo.npf.sdk.inquiry.InquiryStatus;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p023e.AbstractC0952b;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.mynintendo.MissionStatus;
import com.nintendo.npf.sdk.mynintendo.PointProgramService;
import com.nintendo.npf.sdk.notification.PushNotificationChannel;
import com.nintendo.npf.sdk.promo.PromoCode;
import com.nintendo.npf.sdk.promo.PromoCodeBundle;
import com.nintendo.npf.sdk.subscription.SubscriptionProduct;
import com.nintendo.npf.sdk.subscription.SubscriptionPurchase;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.user.Gender;
import com.nintendo.npf.sdk.user.NintendoAccount;
import com.nintendo.npf.sdk.user.OtherUser;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyTransaction;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class UnityBridge implements NPFSDK.EventHandler, PointProgramService.EventCallback, PromoCode.EventHandler {

    /* JADX INFO: renamed from: a */
    private static AbstractC0952b<UnityBridge> f1344a = new AbstractC0952b<UnityBridge>() { // from class: com.nintendo.npf.sdk.internal.impl.UnityBridge.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.nintendo.npf.sdk.internal.p023e.AbstractC0952b
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public UnityBridge mo1074b() {
            return new UnityBridge();
        }
    };

    /* JADX INFO: renamed from: b */
    private Map<String, List<VirtualCurrencyBundle>> f1345b;

    /* JADX INFO: renamed from: c */
    private List<MissionStatus> f1346c;

    /* JADX INFO: renamed from: d */
    private PointProgramService f1347d = null;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$a */
    private static class C0965a implements NintendoAccount.AuthorizationCallback {

        /* JADX INFO: renamed from: a */
        String f1350a;

        /* JADX INFO: renamed from: b */
        JSONArray f1351b;

        C0965a(String str, JSONArray jSONArray) {
            this.f1350a = str;
            this.f1351b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1470a() throws JSONException {
            ArrayList arrayList = new ArrayList();
            if (!this.f1351b.isNull(0)) {
                JSONArray jSONArray = this.f1351b.getJSONArray(0);
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(jSONArray.getString(i));
                }
            }
            NPFSDK.authorizeByNintendoAccount(UnityBridge.m1459b(), arrayList, null, this);
        }

        @Override // com.nintendo.npf.sdk.user.NintendoAccount.AuthorizationCallback
        public void onComplete(NintendoAccount nintendoAccount, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1350a, NativeBridgeUtil.toNullableJsonFromNintendoAccount(nintendoAccount), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$aa */
    private static class C0966aa implements VirtualCurrencyWallet.RetrievingCallback {

        /* JADX INFO: renamed from: a */
        String f1352a;

        /* JADX INFO: renamed from: b */
        JSONArray f1353b;

        C0966aa(String str, JSONArray jSONArray) {
            this.f1352a = str;
            this.f1353b = jSONArray;
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        /* JADX INFO: renamed from: a */
        public void m1471a() throws JSONException {
            String string = this.f1353b.getString(0);
            String string2 = this.f1353b.getString(1);
            VirtualCurrencyBundle virtualCurrencyBundle = null;
            String string3 = this.f1353b.isNull(2) ? null : this.f1353b.getString(2);
            for (VirtualCurrencyBundle virtualCurrencyBundle2 : (List) UnityBridge.getInstance().f1345b.get(string)) {
                if (virtualCurrencyBundle2.getSKU().equals(string2)) {
                    virtualCurrencyBundle = virtualCurrencyBundle2;
                }
            }
            if (virtualCurrencyBundle == null) {
                throw new IllegalStateException("Invalid operation for VirtualCurrencyPurchase");
            }
            if (string3 == null) {
                virtualCurrencyBundle.purchase(UnityBridge.m1459b(), this);
            } else {
                virtualCurrencyBundle.purchaseProductInfo(UnityBridge.m1459b(), this, string3);
            }
        }

        @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet.RetrievingCallback
        public void onComplete(Map<String, VirtualCurrencyWallet> map, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1352a, NativeBridgeUtil.toNullableJsonFromVCWallets(map), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$ab */
    private static class C0967ab implements VirtualCurrencyWallet.RetrievingCallback {

        /* JADX INFO: renamed from: a */
        String f1354a;

        /* JADX INFO: renamed from: b */
        JSONArray f1355b;

        C0967ab(String str, JSONArray jSONArray) {
            this.f1354a = str;
            this.f1355b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1472a() throws JSONException {
            C0976e.f1372a.mo1059m().m1552a(this);
        }

        @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet.RetrievingCallback
        public void onComplete(Map<String, VirtualCurrencyWallet> map, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1354a, NativeBridgeUtil.toNullableJsonFromVCWallets(map), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$ac */
    private static class C0968ac implements VirtualCurrencyPurchasedSummary.RetrievingCallback {

        /* JADX INFO: renamed from: a */
        String f1356a;

        /* JADX INFO: renamed from: b */
        JSONArray f1357b;

        C0968ac(String str, JSONArray jSONArray) {
            this.f1356a = str;
            this.f1357b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1473a() throws JSONException {
            VirtualCurrencyPurchasedSummary.getAll(this.f1357b.getInt(0), this);
        }

        @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary.RetrievingCallback
        public void onComplete(Map<String, VirtualCurrencyPurchasedSummary> map, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1356a, NativeBridgeUtil.toNullableJsonFromVCPurchaseSummaries(map), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$ad */
    private static class C0969ad implements VirtualCurrencyPurchasedSummary.GetAllByMarketCallback {

        /* JADX INFO: renamed from: a */
        String f1358a;

        /* JADX INFO: renamed from: b */
        JSONArray f1359b;

        C0969ad(String str, JSONArray jSONArray) {
            this.f1358a = str;
            this.f1359b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1474a() throws JSONException {
            VirtualCurrencyPurchasedSummary.getAllByMarket(this.f1359b.getInt(0), this.f1359b.getString(1), this);
        }

        @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary.GetAllByMarketCallback
        public void onComplete(Map<String, VirtualCurrencyPurchasedSummary> map, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1358a, NativeBridgeUtil.toNullableJsonFromVCPurchaseSummaries(map), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$ae */
    private static class C0970ae implements VirtualCurrencyPurchasedSummary.RetrievingCallback {

        /* JADX INFO: renamed from: a */
        String f1360a;

        /* JADX INFO: renamed from: b */
        JSONArray f1361b;

        C0970ae(String str, JSONArray jSONArray) {
            this.f1360a = str;
            this.f1361b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1475a() throws JSONException {
            VirtualCurrencyPurchasedSummary.getAllCache(this.f1361b.getInt(0), this);
        }

        @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary.RetrievingCallback
        public void onComplete(Map<String, VirtualCurrencyPurchasedSummary> map, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1360a, NativeBridgeUtil.toNullableJsonFromVCPurchaseSummaries(map), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$af */
    private static class C0971af implements VirtualCurrencyPurchasedSummary.GetAllByMarketCallback {

        /* JADX INFO: renamed from: a */
        String f1362a;

        /* JADX INFO: renamed from: b */
        JSONArray f1363b;

        C0971af(String str, JSONArray jSONArray) {
            this.f1362a = str;
            this.f1363b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1476a() throws JSONException {
            VirtualCurrencyPurchasedSummary.getAllCacheByMarket(this.f1363b.getInt(0), this.f1363b.getString(1), this);
        }

        @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary.GetAllByMarketCallback
        public void onComplete(Map<String, VirtualCurrencyPurchasedSummary> map, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1362a, NativeBridgeUtil.toNullableJsonFromVCPurchaseSummaries(map), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$ag */
    private static class C0972ag implements VirtualCurrencyWallet.RetrievingCallback {

        /* JADX INFO: renamed from: a */
        String f1364a;

        /* JADX INFO: renamed from: b */
        JSONArray f1365b;

        C0972ag(String str, JSONArray jSONArray) {
            this.f1364a = str;
            this.f1365b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1477a() throws JSONException {
            VirtualCurrencyWallet.getAll(this);
        }

        @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet.RetrievingCallback
        public void onComplete(Map<String, VirtualCurrencyWallet> map, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1364a, NativeBridgeUtil.toNullableJsonFromVCWallets(map), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$b */
    private static class C0973b implements NintendoAccount.AuthorizationCallback {

        /* JADX INFO: renamed from: a */
        String f1366a;

        /* JADX INFO: renamed from: b */
        JSONArray f1367b;

        C0973b(String str, JSONArray jSONArray) {
            this.f1366a = str;
            this.f1367b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1478a() throws JSONException {
            ArrayList arrayList = new ArrayList();
            if (!this.f1367b.isNull(0)) {
                JSONArray jSONArray = this.f1367b.getJSONArray(0);
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(jSONArray.getString(i));
                }
            }
            NPFSDK.authorizeByNintendoAccount2(UnityBridge.m1459b(), arrayList, null, this);
        }

        @Override // com.nintendo.npf.sdk.user.NintendoAccount.AuthorizationCallback
        public void onComplete(NintendoAccount nintendoAccount, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1366a, NativeBridgeUtil.toNullableJsonFromNintendoAccount(nintendoAccount), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$c */
    private static class C0974c implements OtherUser.RetrievingCallback {

        /* JADX INFO: renamed from: a */
        String f1368a;

        /* JADX INFO: renamed from: b */
        JSONArray f1369b;

        C0974c(String str, JSONArray jSONArray) {
            this.f1368a = str;
            this.f1369b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1479a() throws JSONException {
            JSONArray jSONArray = this.f1369b.getJSONArray(0);
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add(jSONArray.getString(i));
            }
            OtherUser.getAsList(arrayList, this);
        }

        @Override // com.nintendo.npf.sdk.user.OtherUser.RetrievingCallback
        public void onComplete(List<OtherUser> list, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1368a, NativeBridgeUtil.toNullableJsonFromOtherUsers(list), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$d */
    private static class C0975d implements InquiryStatus.CheckCallback {

        /* JADX INFO: renamed from: a */
        String f1370a;

        /* JADX INFO: renamed from: b */
        JSONArray f1371b;

        C0975d(String str, JSONArray jSONArray) {
            this.f1370a = str;
            this.f1371b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1480a() {
            InquiryStatus.check(this);
        }

        @Override // com.nintendo.npf.sdk.inquiry.InquiryStatus.CheckCallback
        public void onComplete(InquiryStatus inquiryStatus, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1370a, NativeBridgeUtil.toNullableJsonFromInquiryStatus(inquiryStatus), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$e */
    static class C0976e {

        /* JADX INFO: renamed from: a */
        static InterfaceC0875a f1372a = InterfaceC0875a.a.m1072b();
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$f */
    private static class C0977f implements BaaSUser.LinkNintendoAccountCallback {

        /* JADX INFO: renamed from: a */
        String f1373a;

        /* JADX INFO: renamed from: b */
        JSONArray f1374b;

        C0977f(String str, JSONArray jSONArray) {
            this.f1373a = str;
            this.f1374b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1481a() throws JSONException {
            if (C0976e.f1372a.mo1048b().m1673b().getNintendoAccountId().equals(this.f1374b.getString(0))) {
                C0976e.f1372a.mo1050d().m1629a(C0976e.f1372a.mo1048b().m1665a(), C0976e.f1372a.mo1048b().m1673b(), this);
            } else {
                onComplete(new C1025o(NPFError.ErrorType.INVALID_NA_TOKEN, HttpStatusCodes.STATUS_CODE_BAD_REQUEST, "Please use correct re-authorization information"));
            }
        }

        @Override // com.nintendo.npf.sdk.user.BaaSUser.LinkNintendoAccountCallback
        public void onComplete(NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1373a, NativeBridgeUtil.toNullableJsonFromNintendoAccount(C0976e.f1372a.mo1048b().m1665a().getNintendoAccount()), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$g */
    private static class C0978g implements MissionStatus.RetrievingCallback {

        /* JADX INFO: renamed from: a */
        String f1375a;

        /* JADX INFO: renamed from: b */
        JSONArray f1376b;

        C0978g(String str, JSONArray jSONArray) {
            this.f1375a = str;
            this.f1376b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1482a() {
            MissionStatus.getAll(this);
        }

        @Override // com.nintendo.npf.sdk.mynintendo.MissionStatus.RetrievingCallback
        public void onComplete(List<MissionStatus> list, NPFError nPFError) {
            if (list != null) {
                UnityBridge.getInstance().f1346c = list;
            }
            try {
                UnityBridge.getInstance().m1456a(this.f1375a, NativeBridgeUtil.toNullableJsonFromMissionStatuses(list), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$h */
    private static class C0979h implements MissionStatus.ReceivingGiftsCallback {

        /* JADX INFO: renamed from: a */
        String f1377a;

        /* JADX INFO: renamed from: b */
        JSONArray f1378b;

        C0979h(String str, JSONArray jSONArray) {
            this.f1377a = str;
            this.f1378b = jSONArray;
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        /* JADX INFO: renamed from: a */
        public void m1483a() throws JSONException {
            MissionStatus missionStatus;
            String string = this.f1378b.getString(0);
            Iterator it = UnityBridge.getInstance().f1346c.iterator();
            do {
                if (!it.hasNext()) {
                    missionStatus = null;
                    break;
                }
                missionStatus = (MissionStatus) it.next();
            } while (!missionStatus.getMissionId().equals(string));
            if (missionStatus == null) {
                throw new IllegalStateException("Invalid operation for MissionReceiveAvailableGifts");
            }
            missionStatus.receiveAvailableGifts(this);
        }

        @Override // com.nintendo.npf.sdk.mynintendo.MissionStatus.ReceivingGiftsCallback
        public void onComplete(NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1377a, NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$i */
    private static class C0980i implements NPFSDK.NPFErrorCallback {

        /* JADX INFO: renamed from: a */
        String f1379a;

        C0980i(String str, JSONArray jSONArray) {
            this.f1379a = str;
        }

        /* JADX INFO: renamed from: a */
        public void m1484a() throws JSONException {
            C0976e.f1372a.mo1051e().m1710a(UnityBridge.m1459b(), this);
        }

        @Override // com.nintendo.npf.sdk.NPFSDK.NPFErrorCallback
        public void onComplete(NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1379a, NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$j */
    private static class C0981j implements ProfanityWord.CheckProfanityWordCallback {

        /* JADX INFO: renamed from: a */
        String f1380a;

        /* JADX INFO: renamed from: b */
        JSONArray f1381b;

        C0981j(String str, JSONArray jSONArray) {
            this.f1380a = str;
            this.f1381b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1485a() throws JSONException {
            JSONArray jSONArray = this.f1381b.getJSONArray(0);
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                arrayList.add(new ProfanityWord(jSONObject.getString("language"), jSONObject.getString("text"), jSONObject.getString("dictionaryType").equals("nickname") ? ProfanityWord.ProfanityDictionaryType.NICKNAME : ProfanityWord.ProfanityDictionaryType.COMMON));
            }
            ProfanityWord.checkProfanityWord(arrayList, this);
        }

        @Override // com.nintendo.npf.sdk.audit.ProfanityWord.CheckProfanityWordCallback
        public void onComplete(List<ProfanityWord> list, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1380a, NativeBridgeUtil.toNullableJsonFromProfanityWords(list), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$k */
    private static class C0982k implements PromoCode.CheckRemainExchangePromotionPurchasedCallback {

        /* JADX INFO: renamed from: a */
        String f1382a;

        /* JADX INFO: renamed from: b */
        JSONArray f1383b;

        C0982k(String str, JSONArray jSONArray) {
            this.f1382a = str;
            this.f1383b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1486a() throws JSONException {
            PromoCode.checkRemainExchangePromotionPurchased(this);
        }

        @Override // com.nintendo.npf.sdk.promo.PromoCode.CheckRemainExchangePromotionPurchasedCallback
        public void onComplete(List<PromoCodeBundle> list, NPFError nPFError) {
            try {
                UnityBridge unityBridge = UnityBridge.getInstance();
                String str = this.f1382a;
                Object[] objArr = new Object[2];
                objArr[0] = list != null ? NativeBridgeUtil.toJsonFromPromoCodeBundle(list) : JSONObject.NULL;
                objArr[1] = NativeBridgeUtil.toNullableJsonFromNPFError(nPFError);
                unityBridge.m1456a(str, objArr);
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$l */
    private static class C0983l implements PromoCode.ExchangePromotionPurchasedCallback {

        /* JADX INFO: renamed from: a */
        String f1384a;

        /* JADX INFO: renamed from: b */
        JSONArray f1385b;

        C0983l(String str, JSONArray jSONArray) {
            this.f1384a = str;
            this.f1385b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1487a() throws JSONException {
            PromoCode.exchangePromotionPurchased(this);
        }

        @Override // com.nintendo.npf.sdk.promo.PromoCode.ExchangePromotionPurchasedCallback
        public void onComplete(List<PromoCodeBundle> list, NPFError nPFError) {
            try {
                UnityBridge unityBridge = UnityBridge.getInstance();
                String str = this.f1384a;
                Object[] objArr = new Object[2];
                objArr[0] = list != null ? NativeBridgeUtil.toJsonFromPromoCodeBundle(list) : JSONObject.NULL;
                objArr[1] = NativeBridgeUtil.toNullableJsonFromNPFError(nPFError);
                unityBridge.m1456a(str, objArr);
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$m */
    private static class C0984m implements PushNotificationChannel.GetDeviceTokenCallback {

        /* JADX INFO: renamed from: a */
        String f1386a;

        /* JADX INFO: renamed from: b */
        JSONArray f1387b;

        C0984m(String str, JSONArray jSONArray) {
            this.f1386a = str;
            this.f1387b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1488a() throws JSONException {
            PushNotificationChannel.getDeviceToken(this);
        }

        @Override // com.nintendo.npf.sdk.notification.PushNotificationChannel.GetDeviceTokenCallback
        public void onGetDeviceTokenCallbackComplete(String str, NPFError nPFError) {
            try {
                UnityBridge unityBridge = UnityBridge.getInstance();
                String str2 = this.f1386a;
                Object[] objArr = new Object[2];
                objArr[0] = str != null ? str : JSONObject.NULL;
                objArr[1] = NativeBridgeUtil.toNullableJsonFromNPFError(nPFError);
                unityBridge.m1456a(str2, objArr);
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$n */
    private static class C0985n implements PushNotificationChannel.RegisterDeviceTokenCallback {

        /* JADX INFO: renamed from: a */
        String f1388a;

        /* JADX INFO: renamed from: b */
        JSONArray f1389b;

        C0985n(String str, JSONArray jSONArray) {
            this.f1388a = str;
            this.f1389b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1489a() throws JSONException {
            PushNotificationChannel.registerDeviceToken(this.f1389b.getString(0), this);
        }

        @Override // com.nintendo.npf.sdk.notification.PushNotificationChannel.RegisterDeviceTokenCallback
        public void onRegisterDeviceTokenComplete(NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1388a, NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$o */
    private static class C0986o implements NintendoAccount.AuthorizationCallback {

        /* JADX INFO: renamed from: a */
        String f1390a;

        /* JADX INFO: renamed from: b */
        JSONArray f1391b;

        C0986o(String str, JSONArray jSONArray) {
            this.f1390a = str;
            this.f1391b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1490a() throws JSONException {
            NPFSDK.retryPendingAuthorizationByNintendoAccount2(this);
        }

        @Override // com.nintendo.npf.sdk.user.NintendoAccount.AuthorizationCallback
        public void onComplete(NintendoAccount nintendoAccount, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1390a, NativeBridgeUtil.toNullableJsonFromNintendoAccount(nintendoAccount), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$p */
    private static class C0987p implements BaaSUser.SwitchByNintendoAccountCallback {

        /* JADX INFO: renamed from: a */
        String f1392a;

        /* JADX INFO: renamed from: b */
        JSONArray f1393b;

        C0987p(String str, JSONArray jSONArray) {
            this.f1392a = str;
            this.f1393b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1491a() throws JSONException {
            C0976e.f1372a.mo1050d().m1628a(C0976e.f1372a.mo1048b().m1665a(), this);
        }

        @Override // com.nintendo.npf.sdk.user.BaaSUser.SwitchByNintendoAccountCallback
        public void onComplete(String str, String str2, NintendoAccount nintendoAccount, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1392a, str, str2, NativeBridgeUtil.toNullableJsonFromBaaSUser(NPFSDK.getCurrentBaaSUser()), NativeBridgeUtil.toNullableJsonFromNintendoAccount(nintendoAccount), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$q */
    private static class C0988q implements BaaSUser.SaveCallback {

        /* JADX INFO: renamed from: a */
        String f1394a;

        /* JADX INFO: renamed from: b */
        JSONArray f1395b;

        C0988q(String str, JSONArray jSONArray) {
            this.f1394a = str;
            this.f1395b = jSONArray;
        }

        /* JADX WARN: Code duplicated, block: B:18:0x0058  */
        /* JADX INFO: renamed from: a */
        public void m1492a() throws JSONException {
            Gender gender;
            BaaSUser baaSUserM1665a = C0976e.f1372a.mo1048b().m1665a();
            baaSUserM1665a.setNickname(!this.f1395b.isNull(0) ? this.f1395b.getString(0) : null);
            baaSUserM1665a.setCountry(!this.f1395b.isNull(1) ? this.f1395b.getString(1) : null);
            if (this.f1395b.isNull(2)) {
                gender = null;
            } else {
                String string = this.f1395b.getString(2);
                if (string.equals("male")) {
                    gender = Gender.MALE;
                } else if (string.equals("female")) {
                    gender = Gender.FEMALE;
                } else {
                    gender = null;
                }
            }
            baaSUserM1665a.setGender(gender);
            baaSUserM1665a.setBirthdayYear(!this.f1395b.isNull(3) ? Integer.valueOf(this.f1395b.getInt(3)) : null);
            baaSUserM1665a.setBirthdayMonth(!this.f1395b.isNull(4) ? Integer.valueOf(this.f1395b.getInt(4)) : null);
            baaSUserM1665a.setBirthdayDay(this.f1395b.isNull(5) ? null : Integer.valueOf(this.f1395b.getInt(5)));
            baaSUserM1665a.save(this);
        }

        @Override // com.nintendo.npf.sdk.user.BaaSUser.SaveCallback
        public void onComplete(NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1394a, NativeBridgeUtil.toNullableJsonFromBaaSUser(C0976e.f1372a.mo1048b().m1665a()), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$r */
    private static class C0989r implements SubscriptionProduct.GetProductsCallback {

        /* JADX INFO: renamed from: a */
        String f1396a;

        /* JADX INFO: renamed from: b */
        JSONArray f1397b;

        C0989r(String str, JSONArray jSONArray) {
            this.f1396a = str;
            this.f1397b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1493a() {
            SubscriptionProduct.getProducts(UnityBridge.m1459b(), this);
        }

        @Override // com.nintendo.npf.sdk.subscription.SubscriptionProduct.GetProductsCallback
        public void onComplete(List<SubscriptionProduct> list, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1396a, UnityBridge.m1460b(list), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$s */
    private static class C0990s implements SubscriptionPurchase.PurchaseCallback {

        /* JADX INFO: renamed from: a */
        String f1398a;

        /* JADX INFO: renamed from: b */
        JSONArray f1399b;

        C0990s(String str, JSONArray jSONArray) {
            this.f1398a = str;
            this.f1399b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1494a() throws JSONException {
            SubscriptionPurchase.purchase(UnityBridge.m1459b(), this.f1399b.getString(0), this);
        }

        @Override // com.nintendo.npf.sdk.subscription.SubscriptionPurchase.PurchaseCallback
        public void onComplete(NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1398a, NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$t */
    private static class C0991t implements SubscriptionPurchase.PurchasesCallback {

        /* JADX INFO: renamed from: a */
        String f1400a;

        /* JADX INFO: renamed from: b */
        JSONArray f1401b;

        C0991t(String str, JSONArray jSONArray) {
            this.f1400a = str;
            this.f1401b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1495a() {
            SubscriptionPurchase.getPurchases(this);
        }

        @Override // com.nintendo.npf.sdk.subscription.SubscriptionPurchase.PurchasesCallback
        public void onComplete(List<SubscriptionPurchase> list, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1400a, NativeBridgeUtil.toNullableJsonFromSubscriptionPurchases(list), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$u */
    private static class C0992u implements SubscriptionPurchase.OwnershipsCallback {

        /* JADX INFO: renamed from: a */
        String f1402a;

        /* JADX INFO: renamed from: b */
        JSONArray f1403b;

        C0992u(String str, JSONArray jSONArray) {
            this.f1402a = str;
            this.f1403b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1496a() {
            SubscriptionPurchase.updateOwnerships(UnityBridge.m1459b(), this);
        }

        @Override // com.nintendo.npf.sdk.subscription.SubscriptionPurchase.OwnershipsCallback
        public void onComplete(int i, long j, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1402a, Integer.valueOf(i), Long.valueOf(j), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$v */
    private static class C0993v implements SubscriptionPurchase.PurchasesCallback {

        /* JADX INFO: renamed from: a */
        String f1404a;

        /* JADX INFO: renamed from: b */
        JSONArray f1405b;

        C0993v(String str, JSONArray jSONArray) {
            this.f1404a = str;
            this.f1405b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1497a() {
            SubscriptionPurchase.updatePurchases(UnityBridge.m1459b(), this);
        }

        @Override // com.nintendo.npf.sdk.subscription.SubscriptionPurchase.PurchasesCallback
        public void onComplete(List<SubscriptionPurchase> list, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1404a, NativeBridgeUtil.toNullableJsonFromSubscriptionPurchases(list), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$w */
    private static class C0994w implements BaaSUser.SwitchByNintendoAccountCallback {

        /* JADX INFO: renamed from: a */
        String f1406a;

        /* JADX INFO: renamed from: b */
        JSONArray f1407b;

        C0994w(String str, JSONArray jSONArray) {
            this.f1406a = str;
            this.f1407b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1498a() throws JSONException {
            ArrayList arrayList = new ArrayList();
            if (!this.f1407b.isNull(0)) {
                JSONArray jSONArray = this.f1407b.getJSONArray(0);
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(jSONArray.getString(i));
                }
            }
            C0976e.f1372a.mo1050d().m1626a(C0976e.f1372a.mo1048b().m1665a(), UnityBridge.m1459b(), arrayList, this);
        }

        @Override // com.nintendo.npf.sdk.user.BaaSUser.SwitchByNintendoAccountCallback
        public void onComplete(String str, String str2, NintendoAccount nintendoAccount, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1406a, str, str2, NativeBridgeUtil.toNullableJsonFromBaaSUser(C0976e.f1372a.mo1048b().m1665a()), NativeBridgeUtil.toNullableJsonFromNintendoAccount(nintendoAccount), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$x */
    private static class C0995x implements BaaSUser.SwitchByNintendoAccountCallback {

        /* JADX INFO: renamed from: a */
        String f1408a;

        /* JADX INFO: renamed from: b */
        JSONArray f1409b;

        C0995x(String str, JSONArray jSONArray) {
            this.f1408a = str;
            this.f1409b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1499a() throws JSONException {
            ArrayList arrayList = new ArrayList();
            if (!this.f1409b.isNull(0)) {
                JSONArray jSONArray = this.f1409b.getJSONArray(0);
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(jSONArray.getString(i));
                }
            }
            C0976e.f1372a.mo1050d().m1632b(C0976e.f1372a.mo1048b().m1665a(), UnityBridge.m1459b(), arrayList, this);
        }

        @Override // com.nintendo.npf.sdk.user.BaaSUser.SwitchByNintendoAccountCallback
        public void onComplete(String str, String str2, NintendoAccount nintendoAccount, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1408a, str, str2, NativeBridgeUtil.toNullableJsonFromBaaSUser(NPFSDK.getCurrentBaaSUser()), NativeBridgeUtil.toNullableJsonFromNintendoAccount(nintendoAccount), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$y */
    private static class C0996y implements VirtualCurrencyBundle.UnprocessedPurchaseCallback {

        /* JADX INFO: renamed from: a */
        String f1410a;

        /* JADX INFO: renamed from: b */
        JSONArray f1411b;

        C0996y(String str, JSONArray jSONArray) {
            this.f1410a = str;
            this.f1411b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1500a() throws JSONException {
            C0976e.f1372a.mo1059m().m1551a(this);
        }

        @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle.UnprocessedPurchaseCallback
        public void onComplete(List<VirtualCurrencyTransaction> list, NPFError nPFError) {
            try {
                UnityBridge.getInstance().m1456a(this.f1410a, NativeBridgeUtil.toNullableJsonFromVCTransactions(list), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.UnityBridge$z */
    private static class C0997z implements VirtualCurrencyBundle.RetrievingCallback {

        /* JADX INFO: renamed from: a */
        String f1412a;

        /* JADX INFO: renamed from: b */
        JSONArray f1413b;

        C0997z(String str, JSONArray jSONArray) {
            this.f1412a = str;
            this.f1413b = jSONArray;
        }

        /* JADX INFO: renamed from: a */
        public void m1501a() throws JSONException {
            C0976e.f1372a.mo1059m().m1550a(this);
        }

        @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle.RetrievingCallback
        public void onComplete(Map<String, List<VirtualCurrencyBundle>> map, NPFError nPFError) {
            if (map != null) {
                UnityBridge.getInstance().f1345b = map;
            }
            try {
                UnityBridge.getInstance().m1456a(this.f1412a, NativeBridgeUtil.toNullableJsonFromVCBundles(map), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private synchronized void m1454a(String str) {
        try {
            C0955e.m1391a("UnityBridge", str);
            Class<?> cls = Class.forName("com.unity3d.player.UnityPlayer");
            cls.getMethod("UnitySendMessage", String.class, String.class, String.class).invoke(cls, "NPFSDK", "NativeBridgeCallback", str);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1455a(String str, JSONArray jSONArray) throws JSONException {
        PointProgramService.showMissionUI(m1459b(), (float) jSONArray.getDouble(0), jSONArray.getString(1), this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1456a(String str, Object... objArr) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (Object obj : objArr) {
            jSONArray.put(obj);
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("callback", str);
        jSONObject.put("params", jSONArray);
        m1454a(jSONObject.toString());
    }

    /* JADX INFO: renamed from: a */
    private void m1457a(JSONArray jSONArray) throws JSONException {
        Analytics.reportEvent(jSONArray.getString(0), jSONArray.getString(1), jSONArray.getJSONObject(2), jSONArray.getJSONObject(3));
    }

    /* JADX INFO: renamed from: a */
    private void m1458a(JSONArray jSONArray, final String str) throws JSONException {
        BaaSUser.AuthorizationCallback authorizationCallback = new BaaSUser.AuthorizationCallback() { // from class: com.nintendo.npf.sdk.internal.impl.UnityBridge.2
            @Override // com.nintendo.npf.sdk.user.BaaSUser.AuthorizationCallback
            public void onComplete(BaaSUser baaSUser, NPFError nPFError) {
                try {
                    UnityBridge.this.m1456a(str, NativeBridgeUtil.toNullableJsonFromBaaSUser(baaSUser), NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
                } catch (JSONException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
        if (jSONArray.length() == 0) {
            NPFSDK.retryBaaSAuth(authorizationCallback);
            return;
        }
        if (jSONArray.length() == 2) {
            NPFSDK.retryBaaSAuth(jSONArray.getString(0), jSONArray.getString(1), authorizationCallback);
            return;
        }
        throw new IllegalStateException("UnityBridge.RetryBaaSAuth: Bad params[" + jSONArray.toString() + "]");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public static Activity m1459b() {
        try {
            Class<?> cls = Class.forName("com.unity3d.player.UnityPlayer");
            return (Activity) cls.getField("currentActivity").get(cls);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public static Object m1460b(List<SubscriptionProduct> list) throws JSONException {
        if (list == null) {
            return JSONObject.NULL;
        }
        JSONArray jSONArray = new JSONArray();
        for (SubscriptionProduct subscriptionProduct : list) {
            JSONObject jSONObjectM1777a = C0976e.f1372a.mo1053g().m1777a(subscriptionProduct);
            if (jSONObjectM1777a != null) {
                jSONObjectM1777a.put("priceAmountMicros", Long.toString(subscriptionProduct.getPriceAmountMicros()));
                jSONObjectM1777a.put("introductoryPriceAmountMicros", Long.toString(subscriptionProduct.getIntroductoryPriceAmountMicros()));
                jSONArray.put(jSONObjectM1777a);
            }
        }
        return jSONArray;
    }

    /* JADX INFO: renamed from: b */
    private void m1462b(String str, JSONArray jSONArray) throws JSONException {
        PointProgramService.showRewardUI(m1459b(), (float) jSONArray.getDouble(0), jSONArray.getString(1), this);
    }

    /* JADX INFO: renamed from: b */
    private void m1463b(JSONArray jSONArray) throws JSONException {
        if (jSONArray.length() == 1) {
            PointProgramService.setDebugCurrentTimestamp(jSONArray.getLong(0));
        }
    }

    /* JADX INFO: renamed from: c */
    private void m1464c() {
        SubscriptionPurchase.openLink(m1459b());
    }

    /* JADX INFO: renamed from: c */
    private void m1465c(String str, JSONArray jSONArray) throws JSONException {
        PointProgramService pointProgramService = this.f1347d;
        if (pointProgramService != null) {
            pointProgramService.dismiss();
        }
    }

    /* JADX INFO: renamed from: c */
    private void m1466c(JSONArray jSONArray) throws JSONException {
        SubscriptionPurchase.openDeepLink(m1459b(), jSONArray.getString(0));
    }

    /* JADX INFO: renamed from: d */
    private void m1467d(String str, JSONArray jSONArray) throws JSONException {
        PointProgramService pointProgramService = this.f1347d;
        if (pointProgramService != null) {
            pointProgramService.hide();
        }
    }

    /* JADX INFO: renamed from: e */
    private void m1468e(String str, JSONArray jSONArray) throws JSONException {
        if (this.f1347d != null) {
            this.f1347d.resume(jSONArray.getBoolean(0));
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static void execute(String str) {
        try {
            C0955e.m1391a("UnityBridge", "JSON message : " + str);
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString(FirebaseAnalytics.Param.METHOD);
            JSONArray jSONArray = jSONObject.getJSONArray("params");
            String string2 = jSONObject.getString("callback");
            String lowerCase = string.toLowerCase();
            byte b = -1;
            switch (lowerCase.hashCode()) {
                case -1870530008:
                    if (lowerCase.equals("resetdeviceaccount")) {
                        b = 1;
                    }
                    break;
                case -1790762726:
                    if (lowerCase.equals("subscriptionpurchasegetpurchases")) {
                        b = 39;
                    }
                    break;
                case -1678005140:
                    if (lowerCase.equals("virtualcurrencybundlecheckunprocessedpurchase")) {
                        b = Ascii.f291SI;
                    }
                    break;
                case -1628775998:
                    if (lowerCase.equals("pushnotificationchannelregisterdevicetoken")) {
                        b = 34;
                    }
                    break;
                case -1496333175:
                    if (lowerCase.equals("missionstatusgetall")) {
                        b = Ascii.ETB;
                    }
                    break;
                case -1306132260:
                    if (lowerCase.equals("profanitywordcheckprofanityword")) {
                        b = Ascii.NAK;
                    }
                    break;
                case -1253308769:
                    if (lowerCase.equals("virtualcurrencybundlepurchase")) {
                        b = Ascii.f282CR;
                    }
                    break;
                case -1244316881:
                    if (lowerCase.equals("pointprogramservicesetdebugcurrenttimestamp")) {
                        b = Ascii.f290RS;
                    }
                    break;
                case -1099859166:
                    if (lowerCase.equals("pointprogramserviceshowmissionui")) {
                        b = Ascii.f283EM;
                    }
                    break;
                case -869374015:
                    if (lowerCase.equals("pointprogramserviceshowrewardui")) {
                        b = Ascii.SUB;
                    }
                    break;
                case -326359317:
                    if (lowerCase.equals("virtualcurrencypurchasedsummarygetallcachebymarket")) {
                        b = Ascii.DC4;
                    }
                    break;
                case -247290216:
                    if (lowerCase.equals("subscriptionpurchaseexecutepurchase")) {
                        b = 38;
                    }
                    break;
                case -223236578:
                    if (lowerCase.equals("linknintendoaccount")) {
                        b = 7;
                    }
                    break;
                case -178174433:
                    if (lowerCase.equals("promocodeexchangepromotionpurchased")) {
                        b = 33;
                    }
                    break;
                case 2007083:
                    if (lowerCase.equals("pushnotificationchannelgetdevicetoken")) {
                        b = 35;
                    }
                    break;
                case 178682030:
                    if (lowerCase.equals("pointprogramserviceresume")) {
                        b = Ascii.f286GS;
                    }
                    break;
                case 228609281:
                    if (lowerCase.equals("nintendoaccountopenmiistudio")) {
                        b = Ascii.f295VT;
                    }
                    break;
                case 261430401:
                    if (lowerCase.equals("retrybaasauth")) {
                        b = 0;
                    }
                    break;
                case 293606232:
                    if (lowerCase.equals("authorizebynintendoaccount")) {
                        b = 4;
                    }
                    break;
                case 323452458:
                    if (lowerCase.equals("virtualcurrencypurchasedsummarygetall")) {
                        b = 17;
                    }
                    break;
                case 353473634:
                    if (lowerCase.equals("subscriptionpurchaseopenlink")) {
                        b = 42;
                    }
                    break;
                case 493592392:
                    if (lowerCase.equals("promocodecheckremainexchangepurchased")) {
                        b = 32;
                    }
                    break;
                case 511858650:
                    if (lowerCase.equals("authorizebynintendoaccount2")) {
                        b = 5;
                    }
                    break;
                case 762558859:
                    if (lowerCase.equals("subscriptionpurchaseupdateownerships")) {
                        b = 41;
                    }
                    break;
                case 772261710:
                    if (lowerCase.equals("subscriptionpurchaseopendeeplink")) {
                        b = 43;
                    }
                    break;
                case 822311832:
                    if (lowerCase.equals("virtualcurrencypurchasedsummarygetallcache")) {
                        b = 19;
                    }
                    break;
                case 839816191:
                    if (lowerCase.equals("missionstatusreceiveavailablegifts")) {
                        b = Ascii.CAN;
                    }
                    break;
                case 869900335:
                    if (lowerCase.equals("inquirystatuscheck")) {
                        b = 36;
                    }
                    break;
                case 931588709:
                    if (lowerCase.equals("switchbynintendoaccount2")) {
                        b = 9;
                    }
                    break;
                case 1070339227:
                    if (lowerCase.equals("retrypendingauthorizationbynintendoaccount2")) {
                        b = 6;
                    }
                    break;
                case 1122652832:
                    if (lowerCase.equals("analyticsreportevent")) {
                        b = Ascii.SYN;
                    }
                    break;
                case 1262560267:
                    if (lowerCase.equals("subscriptionpurchaseupdatepurchases")) {
                        b = 40;
                    }
                    break;
                case 1395769917:
                    if (lowerCase.equals("virtualcurrencybundlerecoverpurchased")) {
                        b = Ascii.f292SO;
                    }
                    break;
                case 1447934467:
                    if (lowerCase.equals("pointprogramservicehide")) {
                        b = Ascii.f285FS;
                    }
                    break;
                case 1656059773:
                    if (lowerCase.equals("virtualcurrencypurchasedsummarygetallbymarket")) {
                        b = Ascii.DC2;
                    }
                    break;
                case 1668900692:
                    if (lowerCase.equals("retrypendingswitchbynintendoaccount2")) {
                        b = 10;
                    }
                    break;
                case 1744167392:
                    if (lowerCase.equals("virtualcurrencywalletgetall")) {
                        b = Ascii.DLE;
                    }
                    break;
                case 1817932713:
                    if (lowerCase.equals("virtualcurrencybundlegetall")) {
                        b = Ascii.f284FF;
                    }
                    break;
                case 1818300969:
                    if (lowerCase.equals("pointprogramservicedismiss")) {
                        b = Ascii.ESC;
                    }
                    break;
                case 1841812686:
                    if (lowerCase.equals("getotherusers")) {
                        b = 3;
                    }
                    break;
                case 1859559052:
                    if (lowerCase.equals("promocodeinit")) {
                        b = Ascii.f294US;
                    }
                    break;
                case 1943020665:
                    if (lowerCase.equals("savebaasuser")) {
                        b = 2;
                    }
                    break;
                case 1983835720:
                    if (lowerCase.equals("subscriptionproductgetproducts")) {
                        b = 37;
                    }
                    break;
                case 2108261229:
                    if (lowerCase.equals("switchbynintendoaccount")) {
                        b = 8;
                    }
                    break;
            }
            switch (b) {
                case 0:
                    getInstance().m1458a(jSONArray, string2);
                    return;
                case 1:
                    NPFSDK.resetDeviceAccount();
                    return;
                case 2:
                    new C0988q(string2, jSONArray).m1492a();
                    return;
                case 3:
                    new C0974c(string2, jSONArray).m1479a();
                    return;
                case 4:
                    new C0965a(string2, jSONArray).m1470a();
                    return;
                case 5:
                    new C0973b(string2, jSONArray).m1478a();
                    return;
                case 6:
                    new C0986o(string2, jSONArray).m1490a();
                    return;
                case 7:
                    new C0977f(string2, jSONArray).m1481a();
                    return;
                case 8:
                    new C0994w(string2, jSONArray).m1498a();
                    return;
                case 9:
                    new C0995x(string2, jSONArray).m1499a();
                    return;
                case 10:
                    new C0987p(string2, jSONArray).m1491a();
                    return;
                case 11:
                    new C0980i(string2, jSONArray).m1484a();
                    return;
                case 12:
                    new C0997z(string2, jSONArray).m1501a();
                    return;
                case 13:
                    new C0966aa(string2, jSONArray).m1471a();
                    return;
                case 14:
                    new C0967ab(string2, jSONArray).m1472a();
                    return;
                case 15:
                    new C0996y(string2, jSONArray).m1500a();
                    return;
                case 16:
                    new C0972ag(string2, jSONArray).m1477a();
                    return;
                case 17:
                    new C0968ac(string2, jSONArray).m1473a();
                    return;
                case 18:
                    new C0969ad(string2, jSONArray).m1474a();
                    return;
                case 19:
                    new C0970ae(string2, jSONArray).m1475a();
                    return;
                case 20:
                    new C0971af(string2, jSONArray).m1476a();
                    return;
                case MotionEventCompat.AXIS_WHEEL /* 21 */:
                    new C0981j(string2, jSONArray).m1485a();
                    return;
                case MotionEventCompat.AXIS_GAS /* 22 */:
                    getInstance().m1457a(jSONArray);
                    return;
                case MotionEventCompat.AXIS_BRAKE /* 23 */:
                    new C0978g(string2, jSONArray).m1482a();
                    return;
                case MotionEventCompat.AXIS_DISTANCE /* 24 */:
                    new C0979h(string2, jSONArray).m1483a();
                    return;
                case 25:
                    getInstance().m1455a(string2, jSONArray);
                    return;
                case MotionEventCompat.AXIS_SCROLL /* 26 */:
                    getInstance().m1462b(string2, jSONArray);
                    return;
                case MotionEventCompat.AXIS_RELATIVE_X /* 27 */:
                    getInstance().m1465c(string2, jSONArray);
                    return;
                case MotionEventCompat.AXIS_RELATIVE_Y /* 28 */:
                    getInstance().m1467d(string2, jSONArray);
                    return;
                case 29:
                    getInstance().m1468e(string2, jSONArray);
                    return;
                case 30:
                    getInstance().m1463b(jSONArray);
                    return;
                case 31:
                    PromoCode.init(getInstance());
                    return;
                case 32:
                    new C0982k(string2, jSONArray).m1486a();
                    return;
                case 33:
                    new C0983l(string2, jSONArray).m1487a();
                    return;
                case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
                    new C0985n(string2, jSONArray).m1489a();
                    return;
                case MotionEventCompat.AXIS_GENERIC_4 /* 35 */:
                    new C0984m(string2, jSONArray).m1488a();
                    return;
                case MotionEventCompat.AXIS_GENERIC_5 /* 36 */:
                    new C0975d(string2, jSONArray).m1480a();
                    return;
                case MotionEventCompat.AXIS_GENERIC_6 /* 37 */:
                    new C0989r(string2, jSONArray).m1493a();
                    return;
                case MotionEventCompat.AXIS_GENERIC_7 /* 38 */:
                    new C0990s(string2, jSONArray).m1494a();
                    return;
                case MotionEventCompat.AXIS_GENERIC_8 /* 39 */:
                    new C0991t(string2, jSONArray).m1495a();
                    return;
                case MotionEventCompat.AXIS_GENERIC_9 /* 40 */:
                    new C0993v(string2, jSONArray).m1497a();
                    return;
                case MotionEventCompat.AXIS_GENERIC_10 /* 41 */:
                    new C0992u(string2, jSONArray).m1496a();
                    return;
                case MotionEventCompat.AXIS_GENERIC_11 /* 42 */:
                    getInstance().m1464c();
                    return;
                case MotionEventCompat.AXIS_GENERIC_12 /* 43 */:
                    getInstance().m1466c(jSONArray);
                    return;
                default:
                    throw new IllegalStateException(String.format("UnityBridge.Execute: Unknown method [%s].", string));
            }
        } catch (JSONException e) {
            throw new IllegalStateException("UnityBridge.Execute: Could not parse JSON.", e);
        }
    }

    public static String getAppVersion() {
        return C0976e.f1372a.mo1065s().m1339n();
    }

    public static String getDeviceName() {
        return C0976e.f1372a.mo1065s().m1345t();
    }

    public static UnityBridge getInstance() {
        return f1344a.m1386c();
    }

    public static String getMarket() {
        return NativeBridgeUtil.getMarket();
    }

    public static long getPointProgramServiceDebugCurrentTimestamp() {
        return PointProgramService.getDebugCurrentTimestamp();
    }

    public static boolean getPointProgramServiceIsShowing() {
        if (getInstance().f1347d != null) {
            return getInstance().f1347d.isShowing();
        }
        return false;
    }

    public static String getRuntimeOSVersion() {
        return NativeBridgeUtil.getRuntimeOSVersion();
    }

    public static String getTargetedOS() {
        return NativeBridgeUtil.getTargetedOS();
    }

    public static String getTimeZone() {
        return C0976e.f1372a.mo1065s().m1350y();
    }

    public static int getTimeZoneOffsetMin() {
        return NativeBridgeUtil.getTimeZoneOffsetMin();
    }

    public static void init(boolean z) {
        NPFSDK.init(m1459b().getApplication(), getInstance());
        if (z) {
            C0976e.f1372a.mo1049c().m1517a(new BaaSUser.AuthorizationCallback() { // from class: com.nintendo.npf.sdk.internal.impl.UnityBridge.3
                @Override // com.nintendo.npf.sdk.user.BaaSUser.AuthorizationCallback
                public void onComplete(BaaSUser baaSUser, NPFError nPFError) {
                }
            });
        }
    }

    public static void setIABNonConsumable(boolean z) {
        C0976e.f1372a.mo1065s().m1323a(z);
    }

    @Override // com.nintendo.npf.sdk.mynintendo.PointProgramService.EventCallback
    public void onAppeared(PointProgramService pointProgramService) {
        C0955e.m1391a("UnityBridge", "onAppeared");
        this.f1347d = pointProgramService;
        try {
            m1456a("PointProgramServiceOnAppeared", new Object[0]);
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
    public void onBaaSAuthError(NPFError nPFError) {
        try {
            m1456a("onBaaSAuthError", NativeBridgeUtil.toJsonFromNPFError(nPFError));
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
    public void onBaaSAuthStart() {
        try {
            m1456a("onBaaSAuthStart", new Object[0]);
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
    public void onBaaSAuthUpdate(BaaSUser baaSUser) {
        try {
            m1456a("onBaaSAuthUpdate", NativeBridgeUtil.toJsonFromBaaSUser(baaSUser));
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // com.nintendo.npf.sdk.mynintendo.PointProgramService.EventCallback
    public void onDismiss(NPFError nPFError) {
        C0955e.m1391a("UnityBridge", "onDismiss");
        this.f1347d = null;
        try {
            m1456a("PointProgramServiceOnDismiss", NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // com.nintendo.npf.sdk.mynintendo.PointProgramService.EventCallback
    public void onHide(PointProgramService pointProgramService) {
        C0955e.m1391a("UnityBridge", "onHide");
        this.f1347d = pointProgramService;
        try {
            m1456a("PointProgramServiceOnHide", new Object[0]);
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
    public void onNintendoAccountAuthError(NPFError nPFError) {
        try {
            m1456a("onNintendoAccountAuthError", NativeBridgeUtil.toJsonFromNPFError(nPFError));
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // com.nintendo.npf.sdk.mynintendo.PointProgramService.EventCallback
    public void onNintendoAccountLogin(PointProgramService pointProgramService) {
        C0955e.m1391a("UnityBridge", "onNintendoAccountLogin");
        this.f1347d = pointProgramService;
        try {
            m1456a("PointProgramServiceOnNintendoAccountLogin", new Object[0]);
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // com.nintendo.npf.sdk.promo.PromoCode.EventHandler
    public void onOthersNotificationSuccess(List<PromoCodeBundle> list) {
        C0955e.m1391a("UnityBridge", "onOthersNotificationSuccess");
        try {
            m1456a("PromoCodeEventHandlerOnOthersNotificationSuccess", NativeBridgeUtil.toJsonFromPromoCodeBundle(list));
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
    public void onPendingAuthorizationByNintendoAccount2() {
        try {
            m1456a("onPendingAuthorizationByNintendoAccount2", new Object[0]);
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
    public void onPendingSwitchByNintendoAccount2() {
        try {
            m1456a("onPendingSwitchByNintendoAccount2", new Object[0]);
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // com.nintendo.npf.sdk.promo.PromoCode.EventHandler
    public void onPromotionNotificationSuccess(List<PromoCodeBundle> list) {
        C0955e.m1391a("UnityBridge", "onPromotionNotificationSuccess");
        try {
            m1456a("PromoCodeEventHandlerOnPromotionNotificationSuccess", NativeBridgeUtil.toJsonFromPromoCodeBundle(list));
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // com.nintendo.npf.sdk.promo.PromoCode.EventHandler
    public void onPromotionNotoficationError(NPFError nPFError) {
        C0955e.m1391a("UnityBridge", "onPromotionNotoficationError");
        try {
            m1456a("PromoCodeEventHandlerOnPromotionNotoficationError", NativeBridgeUtil.toNullableJsonFromNPFError(nPFError));
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
    public void onVirtualCurrencyPurchaseProcessError(NPFError nPFError) {
        try {
            m1456a("onVirtualCurrencyPurchaseProcessError", NativeBridgeUtil.toJsonFromNPFError(nPFError));
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
    public void onVirtualCurrencyPurchaseProcessSuccess(Map<String, VirtualCurrencyWallet> map) {
        try {
            m1456a("onVirtualCurrencyPurchaseProcessSuccess", NativeBridgeUtil.toJsonFromVCWallets(map));
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }
}
