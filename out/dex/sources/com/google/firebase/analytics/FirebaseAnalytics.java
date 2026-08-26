package com.google.firebase.analytics;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.Size;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.internal.measurement.zzx;
import com.google.android.gms.internal.measurement.zzz;
import com.google.android.gms.measurement.internal.zzfj;
import com.google.android.gms.measurement.internal.zzhi;
import com.google.android.gms.measurement.internal.zzr;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.iid.FirebaseInstanceId;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class FirebaseAnalytics {
    private static volatile FirebaseAnalytics zzabt;
    private final zzz zzabu;
    private String zzabv;
    private long zzabw;
    private final Object zzabx;
    private ExecutorService zzad;
    private final zzfj zzj;
    private final boolean zzl;

    public static class Event {
        public static final String ADD_PAYMENT_INFO = "add_payment_info";
        public static final String ADD_TO_CART = "add_to_cart";
        public static final String ADD_TO_WISHLIST = "add_to_wishlist";
        public static final String APP_OPEN = "app_open";
        public static final String BEGIN_CHECKOUT = "begin_checkout";
        public static final String CAMPAIGN_DETAILS = "campaign_details";
        public static final String CHECKOUT_PROGRESS = "checkout_progress";
        public static final String EARN_VIRTUAL_CURRENCY = "earn_virtual_currency";
        public static final String ECOMMERCE_PURCHASE = "ecommerce_purchase";
        public static final String GENERATE_LEAD = "generate_lead";
        public static final String JOIN_GROUP = "join_group";
        public static final String LEVEL_END = "level_end";
        public static final String LEVEL_START = "level_start";
        public static final String LEVEL_UP = "level_up";
        public static final String LOGIN = "login";
        public static final String POST_SCORE = "post_score";
        public static final String PRESENT_OFFER = "present_offer";
        public static final String PURCHASE_REFUND = "purchase_refund";
        public static final String REMOVE_FROM_CART = "remove_from_cart";
        public static final String SEARCH = "search";
        public static final String SELECT_CONTENT = "select_content";
        public static final String SET_CHECKOUT_OPTION = "set_checkout_option";
        public static final String SHARE = "share";
        public static final String SIGN_UP = "sign_up";
        public static final String SPEND_VIRTUAL_CURRENCY = "spend_virtual_currency";
        public static final String TUTORIAL_BEGIN = "tutorial_begin";
        public static final String TUTORIAL_COMPLETE = "tutorial_complete";
        public static final String UNLOCK_ACHIEVEMENT = "unlock_achievement";
        public static final String VIEW_ITEM = "view_item";
        public static final String VIEW_ITEM_LIST = "view_item_list";
        public static final String VIEW_SEARCH_RESULTS = "view_search_results";

        protected Event() {
        }
    }

    public static class Param {
        public static final String ACHIEVEMENT_ID = "achievement_id";
        public static final String ACLID = "aclid";
        public static final String AFFILIATION = "affiliation";
        public static final String CAMPAIGN = "campaign";
        public static final String CHARACTER = "character";
        public static final String CHECKOUT_OPTION = "checkout_option";
        public static final String CHECKOUT_STEP = "checkout_step";
        public static final String CONTENT = "content";
        public static final String CONTENT_TYPE = "content_type";
        public static final String COUPON = "coupon";
        public static final String CP1 = "cp1";
        public static final String CREATIVE_NAME = "creative_name";
        public static final String CREATIVE_SLOT = "creative_slot";
        public static final String CURRENCY = "currency";
        public static final String DESTINATION = "destination";
        public static final String END_DATE = "end_date";
        public static final String EXTEND_SESSION = "extend_session";
        public static final String FLIGHT_NUMBER = "flight_number";
        public static final String GROUP_ID = "group_id";
        public static final String INDEX = "index";
        public static final String ITEM_BRAND = "item_brand";
        public static final String ITEM_CATEGORY = "item_category";
        public static final String ITEM_ID = "item_id";
        public static final String ITEM_LIST = "item_list";
        public static final String ITEM_LOCATION_ID = "item_location_id";
        public static final String ITEM_NAME = "item_name";
        public static final String ITEM_VARIANT = "item_variant";
        public static final String LEVEL = "level";
        public static final String LEVEL_NAME = "level_name";
        public static final String LOCATION = "location";
        public static final String MEDIUM = "medium";
        public static final String METHOD = "method";
        public static final String NUMBER_OF_NIGHTS = "number_of_nights";
        public static final String NUMBER_OF_PASSENGERS = "number_of_passengers";
        public static final String NUMBER_OF_ROOMS = "number_of_rooms";
        public static final String ORIGIN = "origin";
        public static final String PRICE = "price";
        public static final String QUANTITY = "quantity";
        public static final String SCORE = "score";
        public static final String SEARCH_TERM = "search_term";
        public static final String SHIPPING = "shipping";

        @Deprecated
        public static final String SIGN_UP_METHOD = "sign_up_method";
        public static final String SOURCE = "source";
        public static final String START_DATE = "start_date";
        public static final String SUCCESS = "success";
        public static final String TAX = "tax";
        public static final String TERM = "term";
        public static final String TRANSACTION_ID = "transaction_id";
        public static final String TRAVEL_CLASS = "travel_class";
        public static final String VALUE = "value";
        public static final String VIRTUAL_CURRENCY_NAME = "virtual_currency_name";

        protected Param() {
        }
    }

    public static class UserProperty {
        public static final String ALLOW_AD_PERSONALIZATION_SIGNALS = "allow_personalized_ads";
        public static final String SIGN_UP_METHOD = "sign_up_method";

        protected UserProperty() {
        }
    }

    private FirebaseAnalytics(zzz zzzVar) {
        Preconditions.checkNotNull(zzzVar);
        this.zzj = null;
        this.zzabu = zzzVar;
        this.zzl = true;
        this.zzabx = new Object();
    }

    private FirebaseAnalytics(zzfj zzfjVar) {
        Preconditions.checkNotNull(zzfjVar);
        this.zzj = zzfjVar;
        this.zzabu = null;
        this.zzl = false;
        this.zzabx = new Object();
    }

    @Keep
    @NonNull
    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.WAKE_LOCK"})
    public static FirebaseAnalytics getInstance(@NonNull Context context) {
        if (zzabt == null) {
            synchronized (FirebaseAnalytics.class) {
                if (zzabt == null) {
                    if (zzz.zzf(context)) {
                        zzabt = new FirebaseAnalytics(zzz.zza(context));
                    } else {
                        zzabt = new FirebaseAnalytics(zzfj.zza(context, (zzx) null));
                    }
                }
            }
        }
        return zzabt;
    }

    @Keep
    public static zzhi getScionFrontendApiImplementation(Context context, Bundle bundle) {
        zzz zzzVarZza;
        if (zzz.zzf(context) && (zzzVarZza = zzz.zza(context, (String) null, (String) null, (String) null, bundle)) != null) {
            return new zza(zzzVarZza);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzbg(String str) {
        synchronized (this.zzabx) {
            this.zzabv = str;
            if (this.zzl) {
                this.zzabw = DefaultClock.getInstance().elapsedRealtime();
            } else {
                this.zzabw = this.zzj.zzx().elapsedRealtime();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String zzi() {
        synchronized (this.zzabx) {
            if (Math.abs((this.zzl ? DefaultClock.getInstance().elapsedRealtime() : this.zzj.zzx().elapsedRealtime()) - this.zzabw) < 1000) {
                return this.zzabv;
            }
            return null;
        }
    }

    private final ExecutorService zzrq() {
        ExecutorService executorService;
        synchronized (FirebaseAnalytics.class) {
            if (this.zzad == null) {
                this.zzad = new ThreadPoolExecutor(0, 1, 30L, TimeUnit.SECONDS, new ArrayBlockingQueue(100));
            }
            executorService = this.zzad;
        }
        return executorService;
    }

    @NonNull
    public final Task<String> getAppInstanceId() {
        try {
            String strZzi = zzi();
            return strZzi != null ? Tasks.forResult(strZzi) : Tasks.call(zzrq(), new zzb(this));
        } catch (Exception e) {
            if (this.zzl) {
                this.zzabu.zza(5, "Failed to schedule task for getAppInstanceId", (Object) null, (Object) null, (Object) null);
            } else {
                this.zzj.zzab().zzgn().zzao("Failed to schedule task for getAppInstanceId");
            }
            return Tasks.forException(e);
        }
    }

    @Keep
    public final String getFirebaseInstanceId() {
        return FirebaseInstanceId.getInstance().getId();
    }

    public final void logEvent(@Size(max = 40, min = 1) @NonNull String str, @Nullable Bundle bundle) {
        if (this.zzl) {
            this.zzabu.logEvent(str, bundle);
        } else {
            this.zzj.zzq().zza("app", str, bundle, true);
        }
    }

    public final void resetAnalyticsData() {
        zzbg(null);
        if (this.zzl) {
            this.zzabu.resetAnalyticsData();
        } else {
            this.zzj.zzq().resetAnalyticsData(this.zzj.zzx().currentTimeMillis());
        }
    }

    public final void setAnalyticsCollectionEnabled(boolean z) {
        if (this.zzl) {
            this.zzabu.setMeasurementEnabled(z);
        } else {
            this.zzj.zzq().setMeasurementEnabled(z);
        }
    }

    @Keep
    @MainThread
    public final void setCurrentScreen(@NonNull Activity activity, @Size(max = 36, min = 1) @Nullable String str, @Size(max = 36, min = 1) @Nullable String str2) {
        if (this.zzl) {
            this.zzabu.setCurrentScreen(activity, str, str2);
        } else if (zzr.isMainThread()) {
            this.zzj.zzt().setCurrentScreen(activity, str, str2);
        } else {
            this.zzj.zzab().zzgn().zzao("setCurrentScreen must be called from the main thread");
        }
    }

    @Deprecated
    public final void setMinimumSessionDuration(long j) {
        if (this.zzl) {
            this.zzabu.setMinimumSessionDuration(j);
        } else {
            this.zzj.zzq().setMinimumSessionDuration(j);
        }
    }

    public final void setSessionTimeoutDuration(long j) {
        if (this.zzl) {
            this.zzabu.setSessionTimeoutDuration(j);
        } else {
            this.zzj.zzq().setSessionTimeoutDuration(j);
        }
    }

    public final void setUserId(@Nullable String str) {
        if (this.zzl) {
            this.zzabu.setUserId(str);
        } else {
            this.zzj.zzq().zzb("app", "_id", (Object) str, true);
        }
    }

    public final void setUserProperty(@Size(max = 24, min = 1) @NonNull String str, @Size(max = 36) @Nullable String str2) {
        if (this.zzl) {
            this.zzabu.setUserProperty(str, str2);
        } else {
            this.zzj.zzq().zzb("app", str, (Object) str2, false);
        }
    }
}
