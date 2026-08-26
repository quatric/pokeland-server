package com.nintendo.npf.sdk.internal.impl.cpp;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import com.nintendo.npf.sdk.mynintendo.MissionStatus;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class MissionStatusEventHandler implements MissionStatus.ReceivingGiftsCallback, MissionStatus.RetrievingCallback {

    /* JADX INFO: renamed from: c */
    private static Map<String, MissionStatus> f1514c = new ConcurrentHashMap();

    /* JADX INFO: renamed from: a */
    private long f1515a;

    /* JADX INFO: renamed from: b */
    private long f1516b;

    public MissionStatusEventHandler() {
        this.f1515a = -1L;
        this.f1516b = -1L;
    }

    public MissionStatusEventHandler(long j, long j2) {
        this.f1515a = -1L;
        this.f1516b = -1L;
        this.f1515a = j;
        this.f1516b = j2;
    }

    public static void getAll(long j, long j2) {
        f1514c.clear();
        MissionStatus.getAll(new MissionStatusEventHandler(j, j2));
    }

    private static native void onMissionStatusGetAllComplete(long j, long j2, String str, String str2);

    private static native void onMissionStatusReceiveAvailableGifts(long j, long j2, String str);

    public static void receiveAvailableGifts(long j, long j2, String str) {
        Map<String, MissionStatus> map = f1514c;
        if (map == null || !map.containsKey(str)) {
            try {
                onMissionStatusReceiveAvailableGifts(j, j2, NativeBridgeUtil.toJsonFromNPFError(new C1025o(NPFError.ErrorType.NPF_ERROR, 500, "Can't find the MissionStatus! (missionId : " + str + ")")).toString());
                return;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        f1514c.get(str).receiveAvailableGifts(new MissionStatusEventHandler(j, j2));
    }

    @Override // com.nintendo.npf.sdk.mynintendo.MissionStatus.ReceivingGiftsCallback
    public void onComplete(NPFError nPFError) {
        JSONException e;
        String string;
        if (nPFError != null) {
            try {
                string = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
                try {
                    onMissionStatusReceiveAvailableGifts(this.f1515a, this.f1516b, string);
                    return;
                } catch (JSONException e2) {
                    e = e2;
                    e.printStackTrace();
                    onMissionStatusReceiveAvailableGifts(this.f1515a, this.f1516b, string);
                }
            } catch (JSONException e3) {
                e = e3;
                string = null;
            }
        } else {
            string = null;
        }
        onMissionStatusReceiveAvailableGifts(this.f1515a, this.f1516b, string);
    }

    @Override // com.nintendo.npf.sdk.mynintendo.MissionStatus.RetrievingCallback
    public void onComplete(List<MissionStatus> list, NPFError nPFError) {
        String str;
        String string;
        String str2;
        String string2 = null;
        if (list != null) {
            try {
                for (MissionStatus missionStatus : list) {
                    f1514c.put(missionStatus.getMissionId(), missionStatus);
                }
                string = NativeBridgeUtil.toJsonFromMissionStatuses(list).toString();
            } catch (JSONException e) {
                e = e;
                str = null;
                e.printStackTrace();
                str2 = str;
                onMissionStatusGetAllComplete(this.f1515a, this.f1516b, str2, string2);
            }
        } else {
            string = null;
        }
        if (nPFError != null) {
            try {
                string2 = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
            } catch (JSONException e2) {
                str = string;
                e = e2;
                e.printStackTrace();
                str2 = str;
            }
        }
        str2 = string;
        onMissionStatusGetAllComplete(this.f1515a, this.f1516b, str2, string2);
    }
}
