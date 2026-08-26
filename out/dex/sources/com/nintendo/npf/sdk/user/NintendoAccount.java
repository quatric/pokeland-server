package com.nintendo.npf.sdk.user;

import android.app.Activity;
import android.support.annotation.RestrictTo;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.NPFSDK;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class NintendoAccount {

    /* JADX INFO: renamed from: a */
    public transient long f1812a;
    public String accessToken;
    public int birthdayDay;
    public int birthdayMonth;
    public int birthdayYear;
    public String country;
    public String email;
    public Gender gender;
    public String idToken;
    public String language;
    public Mii mii;
    public String nickname;
    public String nintendoAccountId;
    public String nintendoNetworkId;
    public String region;
    public String sessionToken;
    public String timezone;
    public Type type;

    public interface AuthorizationCallback {
        void onComplete(NintendoAccount nintendoAccount, NPFError nPFError);
    }

    public interface RetrieveNintendoAccountsCallback {
        void onComplete(List<NintendoAccount> list, NPFError nPFError);
    }

    public enum Type {
        UNKNOWN(0),
        GENERAL(1),
        CHILD(2);


        /* JADX INFO: renamed from: a */
        private final int f1815a;

        Type(int i) {
            this.f1815a = i;
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.user.NintendoAccount$a */
    private static class C1064a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1816a = InterfaceC0875a.a.m1072b();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public NintendoAccount() {
        this.f1812a = 0L;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public NintendoAccount(String str, Type type, String str2, Gender gender, String str3, String str4, String str5, String str6, int i, int i2, int i3, String str7, String str8, Mii mii, String str9, String str10, long j) {
        this.f1812a = 0L;
        this.nintendoAccountId = str;
        this.type = type;
        this.nickname = str2;
        this.gender = gender;
        this.language = str3;
        this.country = str4;
        this.region = str5;
        this.timezone = str6;
        this.birthdayYear = i;
        this.birthdayMonth = i2;
        this.birthdayDay = i3;
        this.email = str7;
        this.nintendoNetworkId = str8;
        this.mii = mii;
        this.idToken = str9;
        this.accessToken = str10;
        this.f1812a = j;
    }

    public static void openMiiStudio(Activity activity, final NPFSDK.NPFErrorCallback nPFErrorCallback) {
        C1064a.f1816a.mo1051e().m1710a(activity, new NPFSDK.NPFErrorCallback() { // from class: com.nintendo.npf.sdk.user.NintendoAccount.1
            @Override // com.nintendo.npf.sdk.NPFSDK.NPFErrorCallback
            public void onComplete(NPFError nPFError) {
                NPFSDK.NPFErrorCallback nPFErrorCallback2 = nPFErrorCallback;
                if (nPFErrorCallback2 != null) {
                    nPFErrorCallback2.onComplete(nPFError);
                }
            }
        });
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

    public String getEmail() {
        return this.email;
    }

    public Gender getGender() {
        return this.gender;
    }

    public String getIdToken() {
        return this.idToken;
    }

    public String getLanguage() {
        return this.language;
    }

    public Mii getMii() {
        return this.mii;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getNintendoAccountId() {
        return this.nintendoAccountId;
    }

    public String getNintendoNetworkId() {
        return this.nintendoNetworkId;
    }

    public String getRegion() {
        return this.region;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public Type getType() {
        return this.type;
    }
}
