package com.nintendo.npf.sdk.audit;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class ProfanityWord {
    private ProfanityCheckStatus checkStatus;
    private ProfanityDictionaryType dictionaryType;
    private String language;
    private String text;

    public interface CheckProfanityWordCallback {
        void onComplete(List<ProfanityWord> list, NPFError nPFError);
    }

    public enum ProfanityCheckStatus {
        UNCHECKED(-1),
        INVALID(0),
        VALID(1);


        /* JADX INFO: renamed from: a */
        private int f1016a;

        ProfanityCheckStatus(int i) {
            this.f1016a = i;
        }

        public int getInt() {
            return this.f1016a;
        }
    }

    public enum ProfanityDictionaryType {
        NICKNAME(0),
        COMMON(1);


        /* JADX INFO: renamed from: a */
        private int f1018a;

        ProfanityDictionaryType(int i) {
            this.f1018a = i;
        }

        public int getInt() {
            return this.f1018a;
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.audit.ProfanityWord$a */
    private static class C0871a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1019a = InterfaceC0875a.a.m1072b();
    }

    public ProfanityWord(String str, String str2, ProfanityDictionaryType profanityDictionaryType) {
        this.checkStatus = ProfanityCheckStatus.UNCHECKED;
        this.language = str;
        this.text = str2;
        this.dictionaryType = profanityDictionaryType;
    }

    protected ProfanityWord(String str, String str2, ProfanityDictionaryType profanityDictionaryType, ProfanityCheckStatus profanityCheckStatus) {
        this.checkStatus = ProfanityCheckStatus.UNCHECKED;
        this.language = str;
        this.text = str2;
        this.dictionaryType = profanityDictionaryType;
        this.checkStatus = profanityCheckStatus;
    }

    public static void checkProfanityWord(List<ProfanityWord> list, final CheckProfanityWordCallback checkProfanityWordCallback) {
        C0871a.f1019a.mo1057k().m1729a(list, new CheckProfanityWordCallback() { // from class: com.nintendo.npf.sdk.audit.ProfanityWord.1
            @Override // com.nintendo.npf.sdk.audit.ProfanityWord.CheckProfanityWordCallback
            public void onComplete(List<ProfanityWord> list2, NPFError nPFError) {
                CheckProfanityWordCallback checkProfanityWordCallback2 = checkProfanityWordCallback;
                if (checkProfanityWordCallback2 != null) {
                    checkProfanityWordCallback2.onComplete(list2, nPFError);
                }
            }
        });
    }

    public ProfanityCheckStatus getCheckStatus() {
        return this.checkStatus;
    }

    public ProfanityDictionaryType getDictionaryType() {
        return this.dictionaryType;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getText() {
        return this.text;
    }
}
