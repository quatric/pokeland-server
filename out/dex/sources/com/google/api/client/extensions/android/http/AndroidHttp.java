package com.google.api.client.extensions.android.http;

import com.google.api.client.extensions.android.AndroidUtils;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.Beta;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public class AndroidHttp {
    private AndroidHttp() {
    }

    public static HttpTransport newCompatibleTransport() {
        return AndroidUtils.isMinimumSdkLevel(9) ? new NetHttpTransport() : new ApacheHttpTransport();
    }
}
