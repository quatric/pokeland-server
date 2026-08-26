package com.nintendo.npf.sdk.internal.impl.cpp;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.user.Gender;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class BaaSUserSaveEventHandler implements BaaSUser.SaveCallback {

    /* JADX INFO: renamed from: a */
    private long f1506a;

    /* JADX INFO: renamed from: b */
    private long f1507b;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.cpp.BaaSUserSaveEventHandler$a */
    private static class C1007a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1508a = InterfaceC0875a.a.m1072b();
    }

    public BaaSUserSaveEventHandler() {
        this.f1506a = -1L;
        this.f1507b = -1L;
    }

    public BaaSUserSaveEventHandler(long j, long j2) {
        this.f1506a = -1L;
        this.f1507b = -1L;
        this.f1506a = j;
        this.f1507b = j2;
    }

    private static native void onSaveCallback(long j, long j2, String str, String str2);

    public static void save(long j, long j2, byte[] bArr, byte[] bArr2, byte[] bArr3, int i, int i2, int i3) {
        BaaSUser baaSUserM1665a = C1007a.f1508a.mo1048b().m1665a();
        baaSUserM1665a.setNickname(new String(bArr));
        baaSUserM1665a.setCountry(new String(bArr2));
        Gender gender = "male".equals(new String(bArr3)) ? Gender.MALE : null;
        if ("female".equals(new String(bArr3))) {
            gender = Gender.FEMALE;
        }
        baaSUserM1665a.setGender(gender);
        baaSUserM1665a.setBirthdayYear(Integer.valueOf(i));
        baaSUserM1665a.setBirthdayMonth(Integer.valueOf(i2));
        baaSUserM1665a.setBirthdayDay(Integer.valueOf(i3));
        baaSUserM1665a.save(new BaaSUserSaveEventHandler(j, j2));
    }

    @Override // com.nintendo.npf.sdk.user.BaaSUser.SaveCallback
    public void onComplete(NPFError nPFError) {
        String string;
        String string2 = null;
        try {
            string = NativeBridgeUtil.toJsonFromBaaSUser(C1007a.f1508a.mo1048b().m1665a()).toString();
            if (nPFError != null) {
                try {
                    string2 = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
                } catch (JSONException e) {
                    e = e;
                    e.printStackTrace();
                }
            }
        } catch (JSONException e2) {
            e = e2;
            string = null;
        }
        onSaveCallback(this.f1506a, this.f1507b, string, string2);
    }
}
