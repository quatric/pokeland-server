package android.support.v4.net;

import android.net.TrafficStats;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MotionEventCompat;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class TrafficStatsCompat {
    private static final TrafficStatsCompatBaseImpl IMPL;

    @RequiresApi(MotionEventCompat.AXIS_DISTANCE)
    static class TrafficStatsCompatApi24Impl extends TrafficStatsCompatBaseImpl {
        TrafficStatsCompatApi24Impl() {
        }

        @Override // android.support.v4.net.TrafficStatsCompat.TrafficStatsCompatBaseImpl
        public void tagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
            TrafficStats.tagDatagramSocket(datagramSocket);
        }

        @Override // android.support.v4.net.TrafficStatsCompat.TrafficStatsCompatBaseImpl
        public void untagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
            TrafficStats.untagDatagramSocket(datagramSocket);
        }
    }

    static class TrafficStatsCompatBaseImpl {
        TrafficStatsCompatBaseImpl() {
        }

        public void tagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
            ParcelFileDescriptor parcelFileDescriptorFromDatagramSocket = ParcelFileDescriptor.fromDatagramSocket(datagramSocket);
            TrafficStats.tagSocket(new DatagramSocketWrapper(datagramSocket, parcelFileDescriptorFromDatagramSocket.getFileDescriptor()));
            parcelFileDescriptorFromDatagramSocket.detachFd();
        }

        public void untagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
            ParcelFileDescriptor parcelFileDescriptorFromDatagramSocket = ParcelFileDescriptor.fromDatagramSocket(datagramSocket);
            TrafficStats.untagSocket(new DatagramSocketWrapper(datagramSocket, parcelFileDescriptorFromDatagramSocket.getFileDescriptor()));
            parcelFileDescriptorFromDatagramSocket.detachFd();
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 24) {
            IMPL = new TrafficStatsCompatApi24Impl();
        } else {
            IMPL = new TrafficStatsCompatBaseImpl();
        }
    }

    private TrafficStatsCompat() {
    }

    @Deprecated
    public static void clearThreadStatsTag() {
        TrafficStats.clearThreadStatsTag();
    }

    @Deprecated
    public static int getThreadStatsTag() {
        return TrafficStats.getThreadStatsTag();
    }

    @Deprecated
    public static void incrementOperationCount(int i) {
        TrafficStats.incrementOperationCount(i);
    }

    @Deprecated
    public static void incrementOperationCount(int i, int i2) {
        TrafficStats.incrementOperationCount(i, i2);
    }

    @Deprecated
    public static void setThreadStatsTag(int i) {
        TrafficStats.setThreadStatsTag(i);
    }

    public static void tagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
        IMPL.tagDatagramSocket(datagramSocket);
    }

    @Deprecated
    public static void tagSocket(Socket socket) throws SocketException {
        TrafficStats.tagSocket(socket);
    }

    public static void untagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
        IMPL.untagDatagramSocket(datagramSocket);
    }

    @Deprecated
    public static void untagSocket(Socket socket) throws SocketException {
        TrafficStats.untagSocket(socket);
    }
}
