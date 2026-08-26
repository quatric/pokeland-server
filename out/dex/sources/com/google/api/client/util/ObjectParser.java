package com.google.api.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface ObjectParser {
    <T> T parseAndClose(InputStream inputStream, Charset charset, Class<T> cls) throws IOException;

    Object parseAndClose(InputStream inputStream, Charset charset, Type type) throws IOException;

    <T> T parseAndClose(Reader reader, Class<T> cls) throws IOException;

    Object parseAndClose(Reader reader, Type type) throws IOException;
}
