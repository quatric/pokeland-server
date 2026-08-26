package com.nintendo.npf.sdk.user;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class OtherUser {
    private String nickname;
    private Mii nintendoAccountMii;
    private String nintendoAccountNickname;
    private String userId;

    public interface RetrievingCallback {
        void onComplete(List<OtherUser> list, NPFError nPFError);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.user.OtherUser$a */
    private static class C1066a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1818a = InterfaceC0875a.a.m1072b();
    }

    protected OtherUser(String str, String str2, String str3, Mii mii) {
        this.userId = str;
        this.nickname = str2;
        this.nintendoAccountNickname = str3;
        this.nintendoAccountMii = mii;
    }

    public static void getAsList(List<String> list, final RetrievingCallback retrievingCallback) {
        C1066a.f1818a.mo1068v().m1727a(list, new RetrievingCallback() { // from class: com.nintendo.npf.sdk.user.OtherUser.1
            @Override // com.nintendo.npf.sdk.user.OtherUser.RetrievingCallback
            public void onComplete(List<OtherUser> list2, NPFError nPFError) {
                RetrievingCallback retrievingCallback2 = retrievingCallback;
                if (retrievingCallback2 != null) {
                    retrievingCallback2.onComplete(list2, nPFError);
                }
            }
        });
    }

    public String getNickname() {
        return this.nickname;
    }

    public Mii getNintendoAccountMii() {
        return this.nintendoAccountMii;
    }

    public String getNintendoAccountNickname() {
        return this.nintendoAccountNickname;
    }

    public String getUserId() {
        return this.userId;
    }
}
