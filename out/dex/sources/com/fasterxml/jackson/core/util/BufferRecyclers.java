package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.p014io.JsonStringEncoder;
import java.lang.ref.SoftReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class BufferRecyclers {
    public static final String SYSTEM_PROPERTY_TRACK_REUSABLE_BUFFERS = "com.fasterxml.jackson.core.util.BufferRecyclers.trackReusableBuffers";
    private static final ThreadLocalBufferManager _bufferRecyclerTracker;
    protected static final ThreadLocal<SoftReference<JsonStringEncoder>> _encoderRef;
    protected static final ThreadLocal<SoftReference<BufferRecycler>> _recyclerRef;

    static {
        _bufferRecyclerTracker = "true".equals(System.getProperty(SYSTEM_PROPERTY_TRACK_REUSABLE_BUFFERS)) ? ThreadLocalBufferManager.instance() : null;
        _recyclerRef = new ThreadLocal<>();
        _encoderRef = new ThreadLocal<>();
    }

    public static byte[] encodeAsUTF8(String str) {
        return getJsonStringEncoder().encodeAsUTF8(str);
    }

    public static BufferRecycler getBufferRecycler() {
        SoftReference<BufferRecycler> softReference = _recyclerRef.get();
        BufferRecycler bufferRecycler = softReference == null ? null : softReference.get();
        if (bufferRecycler == null) {
            bufferRecycler = new BufferRecycler();
            ThreadLocalBufferManager threadLocalBufferManager = _bufferRecyclerTracker;
            _recyclerRef.set(threadLocalBufferManager != null ? threadLocalBufferManager.wrapAndTrack(bufferRecycler) : new SoftReference<>(bufferRecycler));
        }
        return bufferRecycler;
    }

    public static JsonStringEncoder getJsonStringEncoder() {
        SoftReference<JsonStringEncoder> softReference = _encoderRef.get();
        JsonStringEncoder jsonStringEncoder = softReference == null ? null : softReference.get();
        if (jsonStringEncoder != null) {
            return jsonStringEncoder;
        }
        JsonStringEncoder jsonStringEncoder2 = new JsonStringEncoder();
        _encoderRef.set(new SoftReference<>(jsonStringEncoder2));
        return jsonStringEncoder2;
    }

    public static void quoteAsJsonText(CharSequence charSequence, StringBuilder sb) {
        getJsonStringEncoder().quoteAsString(charSequence, sb);
    }

    public static char[] quoteAsJsonText(String str) {
        return getJsonStringEncoder().quoteAsString(str);
    }

    public static byte[] quoteAsJsonUTF8(String str) {
        return getJsonStringEncoder().quoteAsUTF8(str);
    }

    public static int releaseBuffers() {
        ThreadLocalBufferManager threadLocalBufferManager = _bufferRecyclerTracker;
        if (threadLocalBufferManager != null) {
            return threadLocalBufferManager.releaseBuffers();
        }
        return -1;
    }
}
