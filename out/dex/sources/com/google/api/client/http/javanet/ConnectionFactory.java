package com.google.api.client.http.javanet;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface ConnectionFactory {
    HttpURLConnection openConnection(URL url) throws IOException, ClassCastException;
}
