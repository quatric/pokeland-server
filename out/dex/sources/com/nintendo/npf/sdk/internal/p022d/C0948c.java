package com.nintendo.npf.sdk.internal.p022d;

import android.content.SharedPreferences;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.d.c */
/* JADX INFO: compiled from: Credentials.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0948c {

    /* JADX INFO: renamed from: a */
    private final InterfaceC0875a f1263a = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: c */
    private void m1352c(String str) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, IOException, NoSuchProviderException {
        m1353c("deviceAccount", str);
    }

    /* JADX INFO: renamed from: c */
    private void m1353c(String str, String str2) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, IOException, NoSuchProviderException {
        SharedPreferences.Editor editorEdit = this.f1263a.mo1047a().getSharedPreferences("deviceAccount:", 0).edit();
        editorEdit.putString(str, str2);
        editorEdit.apply();
    }

    /* JADX INFO: renamed from: d */
    private void m1354d(String str) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, IOException, NoSuchProviderException {
        m1353c("devicePassword", str);
    }

    /* JADX INFO: renamed from: e */
    private String m1355e(String str) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, InvalidKeyException, NoSuchProviderException {
        String string;
        SharedPreferences sharedPreferences = this.f1263a.mo1047a().getSharedPreferences("deviceAccount:", 0);
        if (sharedPreferences == null || (string = sharedPreferences.getString(str, null)) == null) {
            return null;
        }
        return string;
    }

    /* JADX INFO: renamed from: a */
    public String m1356a() {
        try {
            return m1355e("deviceAccount");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1357a(String str) {
        try {
            m1353c("sessionToken", str);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1358a(String str, String str2) {
        try {
            m1352c(str);
            m1354d(str2);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    /* JADX INFO: renamed from: b */
    public String m1359b() {
        try {
            return m1355e("devicePassword");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    /* JADX INFO: renamed from: b */
    public void m1360b(String str) {
        try {
            m1353c("nintendoAccountId", str);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    /* JADX INFO: renamed from: b */
    public void m1361b(String str, String str2) {
        try {
            m1353c("idToken", str);
            m1353c("nintendoAccountId", str2);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    /* JADX INFO: renamed from: c */
    public String m1362c() {
        try {
            return m1355e("sessionToken");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    /* JADX INFO: renamed from: d */
    public String m1363d() {
        try {
            return m1355e("idToken");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    /* JADX INFO: renamed from: e */
    public String m1364e() {
        try {
            return m1355e("nintendoAccountId");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
}
