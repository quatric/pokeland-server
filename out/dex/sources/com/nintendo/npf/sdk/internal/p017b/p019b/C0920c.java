package com.nintendo.npf.sdk.internal.p017b.p019b;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.VisibleForTesting;
import com.google.api.client.googleapis.MethodOverride;
import com.google.api.client.http.HttpStatusCodes;
import com.google.common.net.HttpHeaders;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1017g;
import com.nintendo.npf.sdk.internal.impl.C1026p;
import com.nintendo.npf.sdk.internal.p022d.C0947b;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.user.NintendoAccount;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.b.c */
/* JADX INFO: compiled from: HttpRequest.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0920c {

    /* JADX INFO: renamed from: a */
    private static final String f1188a = "c";

    /* JADX INFO: renamed from: b */
    private static long f1189b = 10000;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.b.c$a */
    /* JADX INFO: compiled from: HttpRequest.java */
    public interface a {
        /* JADX INFO: renamed from: a */
        void mo1225a(int i, Map<String, List<String>> map, String str);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.b.c$b */
    /* JADX INFO: compiled from: HttpRequest.java */
    @VisibleForTesting
    static class b extends AsyncTask<Void, Void, d> implements C1017g.a {

        /* JADX INFO: renamed from: a */
        private String f1190a;

        /* JADX INFO: renamed from: b */
        private String f1191b;

        /* JADX INFO: renamed from: c */
        private String f1192c;

        /* JADX INFO: renamed from: d */
        private String f1193d;

        /* JADX INFO: renamed from: e */
        private Map<String, String> f1194e;

        /* JADX INFO: renamed from: f */
        private Map<String, String> f1195f;

        /* JADX INFO: renamed from: g */
        private String f1196g;

        /* JADX INFO: renamed from: h */
        private byte[] f1197h;

        /* JADX INFO: renamed from: i */
        private a f1198i;

        /* JADX INFO: renamed from: j */
        private boolean f1199j;

        /* JADX INFO: renamed from: k */
        private Handler f1200k;

        /* JADX INFO: renamed from: l */
        private Runnable f1201l;

        /* JADX INFO: renamed from: m */
        private volatile HttpURLConnection f1202m;

        b(String str, String str2, String str3, String str4, Map<String, String> map, Map<String, String> map2, String str5, byte[] bArr, a aVar, boolean z) {
            this.f1190a = str;
            this.f1191b = str2;
            this.f1192c = str3;
            this.f1193d = str4;
            this.f1194e = map;
            if (this.f1194e == null) {
                this.f1194e = new HashMap();
            }
            if (!this.f1194e.containsKey(HttpHeaders.ACCEPT_LANGUAGE)) {
                String strM1227a = C0920c.m1227a();
                C0955e.m1391a(C0920c.f1188a, "Accept-Language: " + strM1227a);
                this.f1194e.put(HttpHeaders.ACCEPT_LANGUAGE, strM1227a);
            }
            C0947b c0947bMo1065s = c.f1204a.mo1065s();
            this.f1194e.put(HttpHeaders.USER_AGENT, c0947bMo1065s.m1337l() + "/" + c0947bMo1065s.m1339n() + " " + c0947bMo1065s.m1345t() + "/" + c0947bMo1065s.m1344s() + " NPFSDK/" + c0947bMo1065s.m1340o());
            this.f1195f = map2;
            this.f1196g = str5;
            this.f1197h = bArr;
            this.f1198i = aVar;
            this.f1199j = z;
            HandlerThread handlerThread = new HandlerThread("com.nintendo.npf.sdk.internal.impl.HttpAsyncTask.timer");
            handlerThread.start();
            this.f1200k = new Handler(handlerThread.getLooper());
            this.f1201l = new Runnable() { // from class: com.nintendo.npf.sdk.internal.b.b.c.b.1
                @Override // java.lang.Runnable
                public void run() {
                    if (b.this.isCancelled()) {
                        return;
                    }
                    b.this.m1232a();
                }
            };
            this.f1202m = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: a */
        public void m1232a() {
            cancel(false);
            if (this.f1202m != null) {
                this.f1202m.disconnect();
            }
        }

        @VisibleForTesting
        /* JADX INFO: renamed from: a */
        d m1234a(long j, BaaSUser baaSUser, NintendoAccount nintendoAccount) {
            if (c.f1204a.mo1050d().m1625a(baaSUser) - j < C0920c.f1189b) {
                return new d(HttpStatusCodes.STATUS_CODE_UNAUTHORIZED, null, null);
            }
            if (baaSUser.getNintendoAccount() == null || baaSUser.getNintendoAccount().getAccessToken() == null || c.f1204a.mo1051e().m1708a(nintendoAccount) - j >= C0920c.f1189b) {
                return null;
            }
            return new d(HttpStatusCodes.STATUS_CODE_UNAUTHORIZED, null, null);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Code duplicated, block: B:178:0x0349 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:193:0x01e3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:197:0x033e A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v26, types: [java.net.HttpURLConnection] */
        /* JADX WARN: Type inference failed for: r1v27 */
        /* JADX WARN: Type inference failed for: r1v29, types: [java.io.DataOutputStream] */
        /* JADX WARN: Type inference failed for: r4v0 */
        /* JADX WARN: Type inference failed for: r4v1 */
        /* JADX WARN: Type inference failed for: r4v10 */
        /* JADX WARN: Type inference failed for: r4v2, types: [java.io.InputStream] */
        /* JADX WARN: Type inference failed for: r4v3 */
        /* JADX WARN: Type inference failed for: r4v6, types: [java.io.InputStream] */
        /* JADX WARN: Type inference failed for: r4v9 */
        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public d doInBackground(Void... voidArr) throws Throwable {
            InputStream inputStream;
            InputStream errorStream;
            byte[] byteArray;
            String str;
            byte[] bArr;
            DataOutputStream dataOutputStream;
            d dVarM1234a;
            ?? r4 = 0;
            if (isCancelled()) {
                return new d(0, null, "timeout");
            }
            C1026p c1026pMo1048b = c.f1204a.mo1048b();
            if (this.f1199j && (dVarM1234a = m1234a(Calendar.getInstance().getTimeInMillis(), c1026pMo1048b.m1665a(), c1026pMo1048b.m1673b())) != null) {
                return dVarM1234a;
            }
            try {
                String str2 = this.f1193d;
                if (this.f1195f != null && this.f1195f.size() > 0) {
                    for (String str3 : this.f1195f.keySet()) {
                        str2 = (str2.contains("?") ? str2 + "&" : str2 + "?") + URLEncoder.encode(str3, "UTF-8") + "=" + URLEncoder.encode(this.f1195f.get(str3), "UTF-8");
                    }
                }
                URL url = new URL(this.f1191b + "://" + this.f1192c + str2);
                C0955e.m1391a(C0920c.f1188a, "URL : " + url.toString());
                if (isCancelled()) {
                    return new d(0, null, "timeout");
                }
                try {
                    this.f1202m = (HttpURLConnection) url.openConnection();
                    try {
                        try {
                            this.f1202m.setRequestMethod(this.f1190a);
                        } catch (ProtocolException unused) {
                            this.f1202m.setRequestMethod("POST");
                            if (this.f1194e != null) {
                                this.f1194e.put(MethodOverride.HEADER, this.f1190a);
                            }
                        }
                        if (this.f1196g != null) {
                            this.f1202m.setRequestProperty(HttpHeaders.CONTENT_TYPE, this.f1196g);
                        }
                        Map<String, String> map = this.f1194e;
                        if (map != null) {
                            for (String str4 : map.keySet()) {
                                String str5 = this.f1194e.get(str4);
                                C0922e.m1244a(str4.length());
                                C0922e.m1244a(str5.length());
                                this.f1202m.setRequestProperty(str4, str5);
                            }
                        }
                        this.f1202m.setUseCaches(true);
                        this.f1202m.setInstanceFollowRedirects(false);
                        this.f1202m.setConnectTimeout(10000);
                        this.f1202m.setReadTimeout(c.f1204a.mo1065s().m1342q());
                        this.f1202m.setDoInput(true);
                        if (this.f1197h != null) {
                            String str6 = C0920c.f1188a;
                            StringBuilder sb = new StringBuilder();
                            sb.append("Request Body : ");
                            bArr = this.f1197h;
                            sb.append(new String(bArr));
                            C0955e.m1391a(str6, sb.toString());
                            C0922e.m1244a(this.f1197h.length);
                            ?? r1 = this.f1202m;
                            r1.setDoOutput(true);
                            try {
                                try {
                                    dataOutputStream = new DataOutputStream(this.f1202m.getOutputStream());
                                    try {
                                        dataOutputStream.write(this.f1197h);
                                        dataOutputStream.flush();
                                        dataOutputStream.close();
                                        try {
                                            dataOutputStream.close();
                                        } catch (IOException unused2) {
                                        }
                                    } catch (IOException e) {
                                        e = e;
                                        d dVar = new d(0, null, e.getLocalizedMessage());
                                        if (dataOutputStream != null) {
                                            try {
                                                dataOutputStream.close();
                                            } catch (IOException unused3) {
                                            }
                                        }
                                        return dVar;
                                    }
                                } catch (Throwable th) {
                                    th = th;
                                    if (r1 != 0) {
                                        try {
                                            r1.close();
                                        } catch (IOException unused4) {
                                        }
                                    }
                                    throw th;
                                }
                            } catch (IOException e2) {
                                e = e2;
                                dataOutputStream = null;
                            } catch (Throwable th2) {
                                th = th2;
                                r1 = 0;
                                if (r1 != 0) {
                                    r1.close();
                                }
                                throw th;
                            }
                        }
                        try {
                            int responseCode = this.f1202m.getResponseCode();
                            if (isCancelled()) {
                                return new d(0, null, "timeout");
                            }
                            C0955e.m1391a(C0920c.f1188a, "Status Code : " + responseCode);
                            Map<String, List<String>> headerFields = this.f1202m.getHeaderFields();
                            if (headerFields != null && C0922e.m1246b()) {
                                for (String str7 : headerFields.keySet()) {
                                    if (str7 != null) {
                                        C0922e.m1245b(str7.length());
                                        List<String> list = headerFields.get(str7);
                                        if (list != null) {
                                            Iterator<String> it = list.iterator();
                                            while (it.hasNext()) {
                                                C0922e.m1245b(it.next().length());
                                            }
                                        }
                                    }
                                }
                            }
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            try {
                                try {
                                    try {
                                        inputStream = this.f1202m.getInputStream();
                                        if (inputStream != null) {
                                            try {
                                                byte[] bArr2 = new byte[512];
                                                while (true) {
                                                    int i = inputStream.read(bArr2);
                                                    if (i == -1) {
                                                        break;
                                                    }
                                                    byteArrayOutputStream.write(bArr2, 0, i);
                                                }
                                                byteArray = byteArrayOutputStream.toByteArray();
                                                inputStream.close();
                                            } catch (IOException e3) {
                                                e = e3;
                                                if (isCancelled()) {
                                                    d dVar2 = new d(0, null, e.getMessage());
                                                    if (inputStream != null) {
                                                        try {
                                                            byteArrayOutputStream.close();
                                                            inputStream.close();
                                                        } catch (IOException unused5) {
                                                        }
                                                    }
                                                    return dVar2;
                                                }
                                                String message = "";
                                                try {
                                                    errorStream = this.f1202m.getErrorStream();
                                                    if (errorStream != null) {
                                                        try {
                                                            byte[] bArr3 = new byte[512];
                                                            while (true) {
                                                                int i2 = errorStream.read(bArr3);
                                                                if (i2 == -1) {
                                                                    break;
                                                                }
                                                                byteArrayOutputStream.write(bArr3, 0, i2);
                                                            }
                                                            byte[] byteArray2 = byteArrayOutputStream.toByteArray();
                                                            String str8 = new String(byteArray2, "UTF-8");
                                                            errorStream.close();
                                                            if (byteArray2 != null) {
                                                                C0922e.m1245b(byteArray2.length);
                                                            }
                                                            message = str8;
                                                        } catch (IOException e4) {
                                                            e = e4;
                                                            message = e.getMessage();
                                                            r4 = errorStream;
                                                            C0955e.m1391a(C0920c.f1188a, "Response Data : " + message);
                                                            d dVar3 = new d(responseCode, headerFields, message);
                                                            if (r4 != 0) {
                                                                try {
                                                                    byteArrayOutputStream.close();
                                                                    r4.close();
                                                                } catch (IOException unused6) {
                                                                }
                                                            }
                                                            return dVar3;
                                                        }
                                                    }
                                                    byteArrayOutputStream.close();
                                                    r4 = errorStream;
                                                } catch (IOException e5) {
                                                    e = e5;
                                                    errorStream = inputStream;
                                                }
                                                C0955e.m1391a(C0920c.f1188a, "Response Data : " + message);
                                                d dVar4 = new d(responseCode, headerFields, message);
                                                if (r4 != 0) {
                                                    byteArrayOutputStream.close();
                                                    r4.close();
                                                }
                                                return dVar4;
                                            }
                                        } else {
                                            byteArray = null;
                                        }
                                        byteArrayOutputStream.close();
                                        if (inputStream != null) {
                                            try {
                                                byteArrayOutputStream.close();
                                                inputStream.close();
                                            } catch (IOException unused7) {
                                            }
                                        }
                                        if (byteArray != null) {
                                            try {
                                                C0922e.m1245b(byteArray.length);
                                                str = new String(byteArray, "utf-8");
                                                C0955e.m1391a(C0920c.f1188a, "Response Data : " + new String(byteArray));
                                            } catch (UnsupportedEncodingException e6) {
                                                return new d(500, null, e6.getMessage());
                                            }
                                        } else {
                                            str = null;
                                        }
                                        return new d(responseCode, headerFields, str);
                                    } catch (Throwable th3) {
                                        th = th3;
                                        r4 = bArr;
                                        if (r4 != 0) {
                                            try {
                                                byteArrayOutputStream.close();
                                                r4.close();
                                            } catch (IOException unused8) {
                                            }
                                        }
                                        throw th;
                                    }
                                } catch (IOException e7) {
                                    e = e7;
                                    inputStream = null;
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                if (r4 != 0) {
                                    byteArrayOutputStream.close();
                                    r4.close();
                                }
                                throw th;
                            }
                        } catch (IOException e8) {
                            return new d(0, null, e8.getLocalizedMessage());
                        }
                    } catch (ProtocolException e9) {
                        return new d(500, null, e9.getLocalizedMessage());
                    }
                } catch (IOException e10) {
                    return new d(0, null, e10.getLocalizedMessage());
                }
            } catch (UnsupportedEncodingException | MalformedURLException e11) {
                throw new IllegalStateException(e11);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(d dVar) {
            this.f1200k.removeCallbacks(this.f1201l);
            this.f1200k.getLooper().quit();
            C0955e.m1391a(C0920c.f1188a, "retry : " + this.f1199j);
            if (dVar.m1239a() == 401 && this.f1199j) {
                C1017g.m1615a((String) null, (String) null, this);
            } else {
                this.f1198i.mo1225a(dVar.m1239a(), dVar.m1240b(), dVar.m1241c());
            }
        }

        @Override // com.nintendo.npf.sdk.internal.impl.C1017g.a
        /* JADX INFO: renamed from: a */
        public void mo1237a(BaaSUser baaSUser, String str, NPFError nPFError) {
            if (nPFError != null) {
                this.f1198i.mo1225a(nPFError.getErrorCode(), null, nPFError.getErrorMessage());
                return;
            }
            if (this.f1194e == null) {
                this.f1194e = new HashMap();
            }
            if (this.f1192c.equals(c.f1204a.mo1065s().m1317a())) {
                this.f1194e.put(HttpHeaders.AUTHORIZATION, "Bearer " + baaSUser.getAccessToken());
            } else if (baaSUser.getNintendoAccount() != null) {
                this.f1194e.put(HttpHeaders.AUTHORIZATION, "Bearer " + baaSUser.getNintendoAccount().getAccessToken());
            }
            C0920c.m1229a(this.f1190a, this.f1191b, this.f1192c, this.f1193d, this.f1194e, this.f1195f, this.f1196g, this.f1197h, this.f1198i, this.f1199j);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public void onCancelled(d dVar) {
            this.f1200k.removeCallbacks(this.f1201l);
            this.f1200k.getLooper().quit();
            this.f1198i.mo1225a(0, null, "timeout");
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            this.f1200k.postDelayed(this.f1201l, c.f1204a.mo1065s().m1343r());
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.b.c$c */
    /* JADX INFO: compiled from: HttpRequest.java */
    private static class c {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1204a = InterfaceC0875a.a.m1072b();
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.b.c$d */
    /* JADX INFO: compiled from: HttpRequest.java */
    @VisibleForTesting
    static class d {

        /* JADX INFO: renamed from: a */
        private int f1205a;

        /* JADX INFO: renamed from: b */
        private Map<String, List<String>> f1206b;

        /* JADX INFO: renamed from: c */
        private String f1207c;

        d(int i, Map<String, List<String>> map, String str) {
            this.f1205a = -1;
            this.f1205a = i;
            this.f1206b = map;
            this.f1207c = str;
        }

        /* JADX INFO: renamed from: a */
        int m1239a() {
            return this.f1205a;
        }

        /* JADX INFO: renamed from: b */
        Map<String, List<String>> m1240b() {
            return this.f1206b;
        }

        /* JADX INFO: renamed from: c */
        String m1241c() {
            return this.f1207c;
        }
    }

    /* JADX INFO: renamed from: a */
    public static String m1227a() {
        return m1228a(c.f1204a.mo1065s().m1347v());
    }

    /* JADX INFO: renamed from: a */
    public static String m1228a(String str) {
        String str2 = str + "; q=1";
        if (str.contains("-")) {
            str2 = str2 + ", " + str.split("-")[0] + "; q=0.5";
        }
        return str2 + ", *; q=0.001";
    }

    /* JADX INFO: renamed from: a */
    public static void m1229a(String str, String str2, String str3, String str4, Map<String, String> map, Map<String, String> map2, String str5, byte[] bArr, a aVar, boolean z) {
        new b(str, str2, str3, str4, map, map2, str5, bArr, aVar, z).execute(new Void[0]);
    }
}
