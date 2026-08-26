package com.google.firebase.messaging.cpp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.flatbuffers.FlatBufferBuilder;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileLock;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class RegistrationIntentService extends IntentService {
    private static final String TAG = "FirebaseRegService";

    public RegistrationIntentService() {
        super(TAG);
    }

    private static byte[] generateTokenByteBuffer(String str) {
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        if (str == null) {
            str = "";
        }
        int iCreateString = flatBufferBuilder.createString(str);
        SerializedTokenReceived.startSerializedTokenReceived(flatBufferBuilder);
        SerializedTokenReceived.addToken(flatBufferBuilder, iCreateString);
        int iEndSerializedTokenReceived = SerializedTokenReceived.endSerializedTokenReceived(flatBufferBuilder);
        SerializedEvent.startSerializedEvent(flatBufferBuilder);
        SerializedEvent.addEventType(flatBufferBuilder, (byte) 2);
        SerializedEvent.addEvent(flatBufferBuilder, iEndSerializedTokenReceived);
        flatBufferBuilder.finish(SerializedEvent.endSerializedEvent(flatBufferBuilder));
        return flatBufferBuilder.sizedByteArray();
    }

    public static void writeTokenToInternalStorage(Context context, String str) {
        byte[] bArrGenerateTokenByteBuffer = generateTokenByteBuffer(str);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferAllocate.putInt(bArrGenerateTokenByteBuffer.length);
        FileLock fileLockLock = null;
        try {
            try {
                try {
                    fileLockLock = context.openFileOutput("FIREBASE_CLOUD_MESSAGING_LOCKFILE", 0).getChannel().lock();
                    FileOutputStream fileOutputStreamOpenFileOutput = context.openFileOutput("FIREBASE_CLOUD_MESSAGING_LOCAL_STORAGE", 32768);
                    fileOutputStreamOpenFileOutput.write(byteBufferAllocate.array());
                    fileOutputStreamOpenFileOutput.write(bArrGenerateTokenByteBuffer);
                    fileOutputStreamOpenFileOutput.close();
                    if (fileLockLock != null) {
                        fileLockLock.release();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (fileLockLock == null) {
                    } else {
                        fileLockLock.release();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th) {
            if (fileLockLock != null) {
                try {
                    fileLockLock.release();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        String token = FirebaseInstanceId.getInstance().getToken();
        DebugLogging.log(TAG, String.format("onHandleIntent token=%s", token));
        if (token != null) {
            writeTokenToInternalStorage(this, token);
        }
    }
}
