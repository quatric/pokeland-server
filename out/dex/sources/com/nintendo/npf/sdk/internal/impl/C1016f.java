package com.nintendo.npf.sdk.internal.impl;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Base64;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.model.PublishRequest;
import com.google.api.services.pubsub.model.PublishResponse;
import com.google.api.services.pubsub.model.PubsubMessage;
import com.nintendo.npf.sdk.NPFSDK;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p022d.C0946a;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.f */
/* JADX INFO: compiled from: AnalyticsPubsubPublisher.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1016f implements InterfaceC1015e {

    /* JADX INFO: renamed from: a */
    private static final String f1562a = "f";

    /* JADX INFO: renamed from: b */
    private boolean f1563b;

    /* JADX INFO: renamed from: c */
    private int f1564c;

    /* JADX INFO: renamed from: d */
    private int f1565d;

    /* JADX INFO: renamed from: e */
    private C0946a f1566e;

    /* JADX INFO: renamed from: f */
    private final InterfaceC0875a f1567f = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: a */
    private static String m1598a(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bArr);
            return Base64.encodeToString(messageDigest.digest(), 2);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1599a(int i, String str) {
        if (this.f1565d == i) {
            return;
        }
        this.f1565d = i;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("errorType", "NPF_ERROR");
            jSONObject.put("errorCode", i);
            jSONObject.put("errorMessage", str);
            this.f1567f.mo1056j().m1586a("NPFAUDIT", "PUBSUB", null, jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1602a(Map<String, JSONObject> map) {
        this.f1567f.mo1056j().m1587a(map);
        this.f1563b = false;
        this.f1564c = 0;
        this.f1565d = 0;
        new Timer().schedule(new TimerTask() { // from class: com.nintendo.npf.sdk.internal.impl.f.2
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                C1016f.this.f1567f.mo1056j().m1596e();
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1603a(Map<String, JSONObject> map, int i, String str) {
        C0955e.m1395c(f1562a, "drainAnalyticsEvents Error Code: " + i + " Message: " + str);
        if (i == 400) {
            this.f1567f.mo1056j().m1593c(map);
            this.f1563b = false;
            m1599a(i, str);
            return;
        }
        if (i == 401 || i == 403 || i == 404) {
            this.f1567f.mo1056j().m1591b(map);
            this.f1563b = false;
            int i2 = this.f1564c;
            if (i2 < 2) {
                this.f1564c = i2 + 1;
                this.f1567f.mo1056j().m1595d();
                return;
            } else {
                this.f1564c = 0;
                this.f1566e.m1286a(C0946a.a.V1);
                this.f1567f.mo1056j().m1584a(this.f1566e);
                m1599a(i, str);
                return;
            }
        }
        if (i == 429) {
            this.f1567f.mo1056j().m1591b(map);
            this.f1563b = false;
            this.f1566e.m1286a(C0946a.a.NONE);
            this.f1567f.mo1056j().m1584a(this.f1566e);
            m1599a(i, str);
            return;
        }
        if (i != 500 && i != 503) {
            this.f1567f.mo1056j().m1591b(map);
            this.f1563b = false;
            return;
        }
        this.f1567f.mo1056j().m1591b(map);
        this.f1563b = false;
        int i3 = this.f1564c;
        if (i3 < 2) {
            this.f1564c = i3 + 1;
            return;
        }
        this.f1564c = 0;
        this.f1566e.m1286a(C0946a.a.V1);
        this.f1567f.mo1056j().m1584a(this.f1566e);
        m1599a(i, str);
    }

    /* JADX INFO: renamed from: a */
    private void m1604a(JSONObject jSONObject) {
        try {
            jSONObject.put("applicationId", this.f1566e.m1291c());
            JSONObject jSONObject2 = jSONObject.getJSONObject("cacheInfo");
            jSONObject2.put("country", this.f1566e.m1300h());
            jSONObject2.put("region", this.f1566e.m1301i());
            jSONObject2.put("city", this.f1566e.m1302j());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public PubsubMessage[] m1606a(Map<String, JSONObject> map, String str) {
        PubsubMessage[] pubsubMessageArr = new PubsubMessage[map.size()];
        Iterator<Map.Entry<String, JSONObject>> it = map.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            JSONObject value = it.next().getValue();
            m1604a(value);
            byte[] bytes = value.toString().getBytes();
            String strM1598a = m1598a(bytes);
            HashMap map2 = new HashMap();
            map2.put("ID_LABEL_ANALYTICS_EVENTS_V02", strM1598a);
            map2.put("AUTHORIZATION", str);
            PubsubMessage pubsubMessage = new PubsubMessage();
            pubsubMessage.setData(Base64.encodeToString(bytes, 2));
            pubsubMessage.setAttributes(map2);
            pubsubMessageArr[i] = pubsubMessage;
            i++;
        }
        return pubsubMessageArr;
    }

    /* JADX INFO: renamed from: a */
    public void m1608a(C0946a c0946a) {
        this.f1566e = c0946a;
    }

    @Override // com.nintendo.npf.sdk.internal.impl.InterfaceC1015e
    /* JADX INFO: renamed from: a */
    public boolean mo1568a(final Map<String, JSONObject> map, BaaSUser baaSUser) {
        if (!this.f1563b && this.f1566e != null) {
            final String accessToken = baaSUser.getAccessToken();
            if (TextUtils.isEmpty(accessToken)) {
                C0955e.m1395c(f1562a, "Access token is null");
                return false;
            }
            this.f1563b = true;
            try {
                new AsyncTask<Void, Void, Void>() { // from class: com.nintendo.npf.sdk.internal.impl.f.1
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // android.os.AsyncTask
                    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                    public Void doInBackground(Void... voidArr) {
                        String sDKVersion = NPFSDK.getSDKVersion();
                        GoogleCredential googleCredential = new GoogleCredential();
                        googleCredential.setAccessToken(C1016f.this.f1566e.m1297f());
                        PubsubMessage[] pubsubMessageArrM1606a = C1016f.this.m1606a((Map<String, JSONObject>) map, accessToken);
                        PublishRequest publishRequest = new PublishRequest();
                        publishRequest.setMessages(Arrays.asList(pubsubMessageArrM1606a));
                        Pubsub pubsubBuild = new Pubsub.Builder(AndroidHttp.newCompatibleTransport(), JacksonFactory.getDefaultInstance(), googleCredential).setApplicationName(sDKVersion).build();
                        BatchRequest batchRequestBatch = pubsubBuild.batch();
                        try {
                            pubsubBuild.projects().topics().publish(C1016f.this.f1566e.m1299g(), publishRequest).queue(batchRequestBatch, new JsonBatchCallback<PublishResponse>() { // from class: com.nintendo.npf.sdk.internal.impl.f.1.1
                                @Override // com.google.api.client.googleapis.batch.BatchCallback
                                /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                                public void onSuccess(PublishResponse publishResponse, HttpHeaders httpHeaders) {
                                    C1016f.this.m1602a((Map<String, JSONObject>) map);
                                }

                                @Override // com.google.api.client.googleapis.batch.json.JsonBatchCallback
                                public void onFailure(GoogleJsonError googleJsonError, HttpHeaders httpHeaders) {
                                    C1016f.this.m1603a((Map<String, JSONObject>) map, googleJsonError.getCode(), googleJsonError.getMessage());
                                }
                            });
                            batchRequestBatch.execute();
                            return null;
                        } catch (IOException e) {
                            C1016f.this.m1603a((Map<String, JSONObject>) map, 0, e.getMessage());
                            return null;
                        }
                    }
                }.execute(new Void[0]);
                return true;
            } catch (IllegalStateException e) {
                e.printStackTrace();
                this.f1563b = false;
            }
        }
        return false;
    }
}
