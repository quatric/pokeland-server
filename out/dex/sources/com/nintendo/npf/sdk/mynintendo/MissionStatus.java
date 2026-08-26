package com.nintendo.npf.sdk.mynintendo;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class MissionStatus {
    private Map<String, Long> availableGifts;
    private boolean completed;
    private Integer currentSteps;
    private String detail;
    private Long limitEndsAt;
    private boolean limited;
    private String missionId;
    private String missionKey;
    private int pointAmount;
    private Integer timesCompleted;
    private String title;
    private int totalSteps;

    public interface ReceivingGiftsCallback {
        void onComplete(NPFError nPFError);
    }

    public interface RetrievingCallback {
        void onComplete(List<MissionStatus> list, NPFError nPFError);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.mynintendo.MissionStatus$a */
    private static class C1039a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1763a = InterfaceC0875a.a.m1072b();
    }

    protected MissionStatus(String str, String str2, String str3, String str4, int i, boolean z, Integer num, int i2, Integer num2, boolean z2, Long l, Map<String, Long> map) {
        this.missionId = str;
        this.missionKey = str2;
        this.title = str3;
        this.detail = str4;
        this.pointAmount = i;
        this.completed = z;
        this.timesCompleted = num;
        this.totalSteps = i2;
        this.currentSteps = num2;
        this.limited = z2;
        this.limitEndsAt = l;
        this.availableGifts = map;
    }

    public static void getAll(final RetrievingCallback retrievingCallback) {
        C1039a.f1763a.mo1066t().m1654a(new RetrievingCallback() { // from class: com.nintendo.npf.sdk.mynintendo.MissionStatus.1
            @Override // com.nintendo.npf.sdk.mynintendo.MissionStatus.RetrievingCallback
            public void onComplete(List<MissionStatus> list, NPFError nPFError) {
                RetrievingCallback retrievingCallback2 = retrievingCallback;
                if (retrievingCallback2 != null) {
                    retrievingCallback2.onComplete(list, nPFError);
                }
            }
        });
    }

    public Map<String, Long> getAvailableGifts() {
        return this.availableGifts;
    }

    public Integer getCurrentSteps() {
        return this.currentSteps;
    }

    public String getDetail() {
        return this.detail;
    }

    public Long getLimitEndsAt() {
        return this.limitEndsAt;
    }

    public boolean getLimited() {
        return this.limited;
    }

    public String getMissionId() {
        return this.missionId;
    }

    public String getMissionKey() {
        return this.missionKey;
    }

    public int getPointAmount() {
        return this.pointAmount;
    }

    public Integer getTimesCompleted() {
        return this.timesCompleted;
    }

    public String getTitle() {
        return this.title;
    }

    public int getTotalSteps() {
        return this.totalSteps;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void receiveAvailableGifts(final ReceivingGiftsCallback receivingGiftsCallback) {
        C1039a.f1763a.mo1066t().m1655a(this.availableGifts, new ReceivingGiftsCallback() { // from class: com.nintendo.npf.sdk.mynintendo.MissionStatus.2
            @Override // com.nintendo.npf.sdk.mynintendo.MissionStatus.ReceivingGiftsCallback
            public void onComplete(NPFError nPFError) {
                ReceivingGiftsCallback receivingGiftsCallback2 = receivingGiftsCallback;
                if (receivingGiftsCallback2 != null) {
                    receivingGiftsCallback2.onComplete(nPFError);
                }
            }
        });
    }
}
