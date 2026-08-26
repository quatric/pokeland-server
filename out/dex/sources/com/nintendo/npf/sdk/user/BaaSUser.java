package com.nintendo.npf.sdk.user;

import android.app.Activity;
import android.support.annotation.RestrictTo;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.inquiry.InquiryStatus;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1018h;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class BaaSUser {
    private static final String TAG = "BaaSUser";
    public String accessToken;
    public int birthdayDay;
    public int birthdayMonth;
    public int birthdayYear;
    public String country;
    public long createdAt;
    public String deviceAccount;
    public String devicePassword;
    public long expiresTime;
    public Gender gender;
    public String idToken;
    public InquiryStatus inquiryStatus;
    public String nickname;
    public NintendoAccount nintendoAccount;
    public boolean personalAnalytics;
    public long personalAnalyticsUpdatedAt;
    public boolean personalNotification;
    public long personalNotificationUpdatedAt;
    public String userId;

    /* JADX INFO: renamed from: a */
    private final transient InterfaceC0875a f1789a = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: b */
    private final transient C1018h f1790b = this.f1789a.mo1050d();

    public interface AuthorizationCallback {
        void onComplete(BaaSUser baaSUser, NPFError nPFError);
    }

    public interface LinkNintendoAccountCallback {
        void onComplete(NPFError nPFError);
    }

    public interface SaveCallback {
        void onComplete(NPFError nPFError);
    }

    public interface SwitchByNintendoAccountCallback {
        void onComplete(String str, String str2, NintendoAccount nintendoAccount, NPFError nPFError);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public BaaSUser() {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public BaaSUser(String str, String str2, String str3, Gender gender, int i, int i2, int i3, boolean z, boolean z2, long j, long j2, InquiryStatus inquiryStatus, long j3) {
        this.userId = str;
        this.nickname = str2;
        this.country = str3;
        this.gender = gender;
        this.birthdayYear = i;
        this.birthdayMonth = i2;
        this.birthdayDay = i3;
        this.personalAnalytics = z;
        this.personalNotification = z2;
        this.personalAnalyticsUpdatedAt = j;
        this.personalNotificationUpdatedAt = j2;
        this.inquiryStatus = inquiryStatus;
        this.createdAt = j3;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public Integer getBirthdayDay() {
        return Integer.valueOf(this.birthdayDay);
    }

    public Integer getBirthdayMonth() {
        return Integer.valueOf(this.birthdayMonth);
    }

    public Integer getBirthdayYear() {
        return Integer.valueOf(this.birthdayYear);
    }

    public String getCountry() {
        return this.country;
    }

    public long getCreatedAt() {
        return this.createdAt;
    }

    public String getDeviceAccount() {
        return this.deviceAccount;
    }

    public String getDevicePassword() {
        return this.devicePassword;
    }

    public Gender getGender() {
        return this.gender;
    }

    public String getIdToken() {
        return this.idToken;
    }

    public InquiryStatus getInquiryStatus() {
        return this.inquiryStatus;
    }

    public String getNickname() {
        return this.nickname;
    }

    public NintendoAccount getNintendoAccount() {
        return this.nintendoAccount;
    }

    public String getUserId() {
        return this.userId;
    }

    public boolean isPersonalAnalytics() {
        return this.personalAnalytics;
    }

    public boolean isPersonalNotification() {
        return this.personalNotification;
    }

    public void linkNintendoAccount(NintendoAccount nintendoAccount, final LinkNintendoAccountCallback linkNintendoAccountCallback) {
        this.f1790b.m1629a(this, nintendoAccount, new LinkNintendoAccountCallback() { // from class: com.nintendo.npf.sdk.user.BaaSUser.1
            @Override // com.nintendo.npf.sdk.user.BaaSUser.LinkNintendoAccountCallback
            public void onComplete(NPFError nPFError) {
                LinkNintendoAccountCallback linkNintendoAccountCallback2 = linkNintendoAccountCallback;
                if (linkNintendoAccountCallback2 != null) {
                    linkNintendoAccountCallback2.onComplete(nPFError);
                }
            }
        });
    }

    public void retryPendingSwitchByNintendoAccount2(final SwitchByNintendoAccountCallback switchByNintendoAccountCallback) {
        this.f1790b.m1628a(this, new SwitchByNintendoAccountCallback() { // from class: com.nintendo.npf.sdk.user.BaaSUser.4
            @Override // com.nintendo.npf.sdk.user.BaaSUser.SwitchByNintendoAccountCallback
            public void onComplete(String str, String str2, NintendoAccount nintendoAccount, NPFError nPFError) {
                SwitchByNintendoAccountCallback switchByNintendoAccountCallback2 = switchByNintendoAccountCallback;
                if (switchByNintendoAccountCallback2 != null) {
                    switchByNintendoAccountCallback2.onComplete(str, str2, nintendoAccount, nPFError);
                }
            }
        });
    }

    public void save(final SaveCallback saveCallback) {
        this.f1790b.m1627a(this, new SaveCallback() { // from class: com.nintendo.npf.sdk.user.BaaSUser.5
            @Override // com.nintendo.npf.sdk.user.BaaSUser.SaveCallback
            public void onComplete(NPFError nPFError) {
                SaveCallback saveCallback2 = saveCallback;
                if (saveCallback2 != null) {
                    saveCallback2.onComplete(nPFError);
                }
            }
        });
    }

    public void setBirthdayDay(Integer num) {
        this.birthdayDay = num.intValue();
    }

    public void setBirthdayMonth(Integer num) {
        this.birthdayMonth = num.intValue();
    }

    public void setBirthdayYear(Integer num) {
        this.birthdayYear = num.intValue();
    }

    public void setCountry(String str) {
        this.country = str;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setNickname(String str) {
        this.nickname = str;
    }

    public void switchByNintendoAccount(Activity activity, List<String> list, Map<String, String> map, final SwitchByNintendoAccountCallback switchByNintendoAccountCallback) {
        this.f1790b.m1626a(this, activity, list, new SwitchByNintendoAccountCallback() { // from class: com.nintendo.npf.sdk.user.BaaSUser.2
            @Override // com.nintendo.npf.sdk.user.BaaSUser.SwitchByNintendoAccountCallback
            public void onComplete(String str, String str2, NintendoAccount nintendoAccount, NPFError nPFError) {
                SwitchByNintendoAccountCallback switchByNintendoAccountCallback2 = switchByNintendoAccountCallback;
                if (switchByNintendoAccountCallback2 != null) {
                    switchByNintendoAccountCallback2.onComplete(str, str2, nintendoAccount, nPFError);
                }
            }
        });
    }

    public void switchByNintendoAccount2(Activity activity, List<String> list, Map<String, String> map, final SwitchByNintendoAccountCallback switchByNintendoAccountCallback) {
        this.f1790b.m1632b(this, activity, list, new SwitchByNintendoAccountCallback() { // from class: com.nintendo.npf.sdk.user.BaaSUser.3
            @Override // com.nintendo.npf.sdk.user.BaaSUser.SwitchByNintendoAccountCallback
            public void onComplete(String str, String str2, NintendoAccount nintendoAccount, NPFError nPFError) {
                SwitchByNintendoAccountCallback switchByNintendoAccountCallback2 = switchByNintendoAccountCallback;
                if (switchByNintendoAccountCallback2 != null) {
                    switchByNintendoAccountCallback2.onComplete(str, str2, nintendoAccount, nPFError);
                }
            }
        });
    }
}
