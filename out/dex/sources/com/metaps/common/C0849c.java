package com.metaps.common;

import android.os.Build;
import android.util.Pair;
import com.google.common.net.HttpHeaders;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.common.c */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0849c {

    /* JADX INFO: renamed from: a */
    public static final String f861a = "application/x-www-form-urlencoded";

    /* JADX INFO: renamed from: b */
    public static final String f862b = "application/json";

    /* JADX INFO: renamed from: c */
    public static final String f863c = "GET";

    /* JADX INFO: renamed from: d */
    public static final String f864d = "POST";

    /* JADX INFO: renamed from: e */
    private String f865e;

    /* JADX INFO: renamed from: com.metaps.common.c$a */
    public static final class a {

        /* JADX INFO: renamed from: a */
        protected static final String f866a = "code";

        /* JADX INFO: renamed from: b */
        protected static final String f867b = "message";

        /* JADX INFO: renamed from: c */
        protected static final String f868c = "contents";

        /* JADX INFO: renamed from: d */
        protected static final String f869d = "noretry";

        /* JADX INFO: renamed from: e */
        protected static final String f870e = "451";

        /* JADX INFO: renamed from: f */
        public int f871f;

        /* JADX INFO: renamed from: g */
        public Map<String, List<String>> f872g = new HashMap();

        /* JADX INFO: renamed from: h */
        public String f873h;

        /* JADX INFO: renamed from: i */
        public String f874i;

        /* JADX INFO: renamed from: j */
        public String f875j;

        /* JADX INFO: renamed from: k */
        public JSONObject f876k;

        /* JADX INFO: renamed from: l */
        public boolean f877l;

        protected a() {
        }

        /* JADX INFO: renamed from: a */
        private void m922a() {
            try {
                JSONObject jSONObject = new JSONObject(this.f873h);
                if (jSONObject.has(f866a)) {
                    this.f874i = jSONObject.getString(f866a);
                }
                if (jSONObject.has(f867b)) {
                    this.f875j = jSONObject.getString(f867b);
                }
                if (jSONObject.has(f868c)) {
                    this.f876k = jSONObject.getJSONObject(f868c);
                }
                if (jSONObject.has(f869d)) {
                    this.f877l = jSONObject.has(f869d) && jSONObject.getBoolean(f869d);
                }
            } catch (NullPointerException e) {
                C0847a.m905a(a.class.toString(), "Failed to parse null response", e);
            } catch (JSONException unused) {
                C0847a.m903a(a.class.toString(), "Failed to parse response");
            }
        }

        /* JADX INFO: renamed from: a */
        protected void m923a(String str) {
            this.f873h = str;
            m922a();
        }

        /* JADX INFO: renamed from: a */
        protected void m924a(Map<String, List<String>> map) {
            this.f872g = map;
        }
    }

    public C0849c() {
        this.f865e = null;
        StringBuilder sb = new StringBuilder();
        if (Build.VERSION.RELEASE != null) {
            sb.append(Build.VERSION.RELEASE);
        }
        sb.append(";");
        sb.append(Build.VERSION.SDK_INT);
        sb.append(";");
        this.f865e = sb.toString();
    }

    /* JADX INFO: renamed from: a */
    private static String m917a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int i = inputStream.read(bArr);
            if (i <= 0) {
                inputStream.close();
                return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
            }
            byteArrayOutputStream.write(bArr, 0, i);
        }
    }

    /* JADX INFO: renamed from: a */
    public a m918a(String str, String str2) throws C0848b {
        return m920a(str, "", str2, "GET");
    }

    /* JADX INFO: renamed from: a */
    public a m919a(String str, String str2, String str3) throws C0848b {
        return m920a(str, str2, str3, "POST");
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: a */
    public a m920a(String str, String str2, String str3, String str4) throws Throwable {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (str == null || str.length() == 0) {
            throw new C0848b("The url can't be null or empty");
        }
        String str5 = str2 == null ? "" : str2;
        C0847a.m903a(getClass().toString(), "API call " + str + " with POST parameters :");
        ArrayList<Pair> arrayList = new ArrayList();
        arrayList.add(new Pair(HttpHeaders.CONTENT_TYPE, str3));
        arrayList.add(new Pair(HttpHeaders.ACCEPT_ENCODING, "identity"));
        arrayList.add(new Pair(HttpHeaders.USER_AGENT, m921a()));
        C0847a.m903a(getClass().toString(), " with headers :");
        for (Iterator it = arrayList.iterator(); it.hasNext(); it = it) {
            Pair pair = (Pair) it.next();
            C0847a.m903a(getClass().toString(), "     " + ((String) pair.first) + " = " + ((String) pair.second));
        }
        C0847a.m903a(getClass().toString(), " with body :");
        if (str3.equals(f862b)) {
            try {
                C0847a.m903a(C0849c.class.toString(), new JSONObject(str5).toString(4));
            } catch (JSONException unused) {
            }
        } else {
            C0847a.m903a(C0849c.class.toString(), str5);
        }
        a aVar = new a();
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(str4.equals("POST"));
            httpURLConnection.setRequestMethod(str4);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setFixedLengthStreamingMode(str5.getBytes().length);
            for (Pair pair2 : arrayList) {
                httpURLConnection.setRequestProperty((String) pair2.first, (String) pair2.second);
            }
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(15000);
            if (str4.equals("POST")) {
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(str5);
                bufferedWriter.close();
                outputStream.close();
            }
            try {
                try {
                    try {
                        try {
                            aVar.f871f = httpURLConnection.getResponseCode();
                            try {
                                if (aVar.f871f != 200 && (aVar.f871f == 302 || aVar.f871f == 301 || aVar.f871f == 303)) {
                                    a aVarM920a = m920a(httpURLConnection.getHeaderField(HttpHeaders.LOCATION), str5, str3, str4);
                                    httpURLConnection.disconnect();
                                    return aVarM920a;
                                }
                                if (aVar.f871f == 200) {
                                    aVar.m924a(httpURLConnection.getHeaderFields());
                                }
                                C0847a.m903a(getClass().toString(), str + " API try to get input stream");
                                aVar.m923a(m917a(httpURLConnection.getInputStream()));
                                C0847a.m903a(C0849c.class.toString(), "API Response " + aVar.f871f + " " + aVar.f873h);
                                httpURLConnection.disconnect();
                                C0847a.m904a(C0849c.class.toString(), str + " API called TOTAL", jCurrentTimeMillis);
                                return aVar;
                            } catch (IOException e) {
                                e = e;
                                C0847a.m903a(getClass().toString(), str + " API try to get error stream for error");
                                aVar.m923a(m917a(httpURLConnection.getErrorStream()));
                            }
                        } catch (Throwable th) {
                            th = th;
                            httpURLConnection.disconnect();
                            throw th;
                        }
                    } catch (IOException e2) {
                        e = e2;
                    } catch (Throwable th2) {
                        th = th2;
                        httpURLConnection.disconnect();
                        throw th;
                    }
                    httpURLConnection.disconnect();
                    C0847a.m904a(C0849c.class.toString(), str + " API called TOTAL", jCurrentTimeMillis);
                    return aVar;
                } catch (IOException e3) {
                    e = e3;
                    C0847a.m911c("Problem in encoding or the connection was aborted " + e.getClass());
                    C0847a.m905a(getClass().toString(), "url=[" + str + "]", e);
                    throw new C0848b("Problem in encoding or the connection was aborted " + e.getClass() + " " + e.getMessage());
                } catch (RuntimeException e4) {
                    e = e4;
                    C0847a.m911c("RuntimeException " + e.getClass());
                    C0847a.m905a(getClass().toString(), "url=[" + str + "]", e);
                    throw new C0848b("Runtime exception when calling API " + e.getClass() + " " + e.getMessage());
                }
                C0847a.m903a(getClass().toString(), str + " API try to get error stream for error");
                aVar.m923a(m917a(httpURLConnection.getErrorStream()));
            } catch (IOException unused2) {
                C0847a.m911c("Problem in encoding or the connection was aborted " + e.getClass());
                C0847a.m905a(getClass().toString(), "url=[" + str + "]", e);
                throw new C0848b("Problem in encoding or the connection was aborted " + e.getClass() + " " + e.getMessage());
            }
        } catch (IOException e5) {
            e = e5;
        } catch (RuntimeException e6) {
            e = e6;
        }
    }

    /* JADX INFO: renamed from: a */
    protected String m921a() {
        StringBuffer stringBuffer = new StringBuffer(C0854h.f925g);
        stringBuffer.append(UnityWrapper.WRAPPER_VERSION);
        stringBuffer.append(" ");
        stringBuffer.append("(" + this.f865e + ")");
        stringBuffer.append(" ");
        stringBuffer.append(C0854h.f926h);
        stringBuffer.append(" ");
        stringBuffer.append(C0854h.m974b());
        return stringBuffer.toString();
    }
}
