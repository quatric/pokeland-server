package com.google.android.gms.measurement.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@WorkerThread
final class zzhn implements Runnable {
    private final String packageName;
    private final URL url;
    private final byte[] zzlc;
    private final Map<String, String> zzle;
    private final zzhk zzqm;
    private final /* synthetic */ zzhl zzqn;

    public zzhn(zzhl zzhlVar, String str, URL url, byte[] bArr, Map<String, String> map, zzhk zzhkVar) {
        this.zzqn = zzhlVar;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(zzhkVar);
        this.url = url;
        this.zzlc = null;
        this.zzqm = zzhkVar;
        this.packageName = str;
        this.zzle = null;
    }

    private final void zza(final int i, final Exception exc, final byte[] bArr, final Map<String, List<String>> map) {
        this.zzqn.zzaa().zza(new Runnable(this, i, exc, bArr, map) { // from class: com.google.android.gms.measurement.internal.zzhm
            private final zzhn zzqh;
            private final int zzqi;
            private final Exception zzqj;
            private final byte[] zzqk;
            private final Map zzql;

            {
                this.zzqh = this;
                this.zzqi = i;
                this.zzqj = exc;
                this.zzqk = bArr;
                this.zzql = map;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zzqh.zzb(this.zzqi, this.zzqj, this.zzqk, this.zzql);
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0070  */
    /* JADX WARN: Code duplicated, block: B:40:0x007d  */
    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        Map<String, List<String>> map;
        HttpURLConnection httpURLConnectionZza;
        Map<String, List<String>> map2;
        int i;
        int i2;
        this.zzqn.zzn();
        try {
            httpURLConnectionZza = this.zzqn.zza(this.url);
            try {
                if (this.zzle != null) {
                    for (Map.Entry<String, String> entry : this.zzle.entrySet()) {
                        httpURLConnectionZza.addRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
                int responseCode = httpURLConnectionZza.getResponseCode();
                try {
                    Map<String, List<String>> headerFields = httpURLConnectionZza.getHeaderFields();
                    try {
                        zzhl zzhlVar = this.zzqn;
                        byte[] bArrZza = zzhl.zza(httpURLConnectionZza);
                        if (httpURLConnectionZza != null) {
                            httpURLConnectionZza.disconnect();
                        }
                        zza(responseCode, null, bArrZza, headerFields);
                    } catch (IOException e) {
                        i2 = responseCode;
                        map2 = headerFields;
                        e = e;
                        if (httpURLConnectionZza != null) {
                            httpURLConnectionZza.disconnect();
                        }
                        zza(i2, e, null, map2);
                    } catch (Throwable th) {
                        i = responseCode;
                        map = headerFields;
                        th = th;
                        if (httpURLConnectionZza != null) {
                            httpURLConnectionZza.disconnect();
                        }
                        zza(i, null, null, map);
                        throw th;
                    }
                } catch (IOException e2) {
                    e = e2;
                    i2 = responseCode;
                    map2 = null;
                } catch (Throwable th2) {
                    th = th2;
                    i = responseCode;
                    map = null;
                }
            } catch (IOException e3) {
                e = e3;
                map2 = null;
                i2 = 0;
                if (httpURLConnectionZza != null) {
                    httpURLConnectionZza.disconnect();
                }
                zza(i2, e, null, map2);
            } catch (Throwable th3) {
                th = th3;
                map = null;
                i = 0;
                if (httpURLConnectionZza != null) {
                    httpURLConnectionZza.disconnect();
                }
                zza(i, null, null, map);
                throw th;
            }
        } catch (IOException e4) {
            e = e4;
            map2 = null;
            httpURLConnectionZza = null;
        } catch (Throwable th4) {
            th = th4;
            map = null;
            httpURLConnectionZza = null;
        }
    }

    final /* synthetic */ void zzb(int i, Exception exc, byte[] bArr, Map map) {
        this.zzqm.zza(this.packageName, i, exc, bArr, map);
    }
}
