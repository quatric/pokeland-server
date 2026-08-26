package com.google.api.client.testing.util;

import com.google.api.client.util.Beta;
import com.google.api.client.util.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public class LogRecordingHandler extends Handler {
    private final List<LogRecord> records = Lists.newArrayList();

    @Override // java.util.logging.Handler
    public void close() throws SecurityException {
    }

    @Override // java.util.logging.Handler
    public void flush() {
    }

    public List<String> messages() {
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        Iterator<LogRecord> it = this.records.iterator();
        while (it.hasNext()) {
            arrayListNewArrayList.add(it.next().getMessage());
        }
        return arrayListNewArrayList;
    }

    @Override // java.util.logging.Handler
    public void publish(LogRecord logRecord) {
        this.records.add(logRecord);
    }
}
