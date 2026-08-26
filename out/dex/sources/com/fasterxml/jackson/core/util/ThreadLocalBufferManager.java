package com.fasterxml.jackson.core.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class ThreadLocalBufferManager {
    private final Object RELEASE_LOCK = new Object();
    private final Map<SoftReference<BufferRecycler>, Boolean> _trackedRecyclers = new ConcurrentHashMap();
    private final ReferenceQueue<BufferRecycler> _refQueue = new ReferenceQueue<>();

    private static final class ThreadLocalBufferManagerHolder {
        static final ThreadLocalBufferManager manager = new ThreadLocalBufferManager();

        private ThreadLocalBufferManagerHolder() {
        }
    }

    ThreadLocalBufferManager() {
    }

    public static ThreadLocalBufferManager instance() {
        return ThreadLocalBufferManagerHolder.manager;
    }

    private void removeSoftRefsClearedByGc() {
        while (true) {
            SoftReference softReference = (SoftReference) this._refQueue.poll();
            if (softReference == null) {
                return;
            } else {
                this._trackedRecyclers.remove(softReference);
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public int releaseBuffers() {
        int i;
        synchronized (this.RELEASE_LOCK) {
            i = 0;
            removeSoftRefsClearedByGc();
            Iterator<SoftReference<BufferRecycler>> it = this._trackedRecyclers.keySet().iterator();
            while (it.hasNext()) {
                it.next().clear();
                i++;
            }
            this._trackedRecyclers.clear();
        }
        return i;
    }

    public SoftReference<BufferRecycler> wrapAndTrack(BufferRecycler bufferRecycler) {
        SoftReference<BufferRecycler> softReference = new SoftReference<>(bufferRecycler, this._refQueue);
        this._trackedRecyclers.put(softReference, true);
        removeSoftRefsClearedByGc();
        return softReference;
    }
}
