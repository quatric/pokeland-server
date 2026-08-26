package com.google.firebase.messaging.cpp;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class SerializedMessage extends Table {
    public static void addCollapseKey(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(2, i, 0);
    }

    public static void addData(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(3, i, 0);
    }

    public static void addError(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(9, i, 0);
    }

    public static void addErrorDescription(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(10, i, 0);
    }

    public static void addFrom(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static void addLink(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(13, i, 0);
    }

    public static void addMessageId(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(5, i, 0);
    }

    public static void addMessageType(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(6, i, 0);
    }

    public static void addNotification(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(11, i, 0);
    }

    public static void addNotificationOpened(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(12, z, false);
    }

    public static void addOriginalPriority(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(15, i, 0);
    }

    public static void addPriority(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(7, i, 0);
    }

    public static void addRawData(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(4, i, 0);
    }

    public static void addSentTime(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.addLong(14, j, 0L);
    }

    public static void addTimeToLive(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(8, i, 0);
    }

    public static void addTo(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static int createDataVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addOffset(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int createSerializedMessage(FlatBufferBuilder flatBufferBuilder, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, boolean z, int i13, long j, int i14) {
        flatBufferBuilder.startObject(16);
        addSentTime(flatBufferBuilder, j);
        addOriginalPriority(flatBufferBuilder, i14);
        addLink(flatBufferBuilder, i13);
        addNotification(flatBufferBuilder, i12);
        addErrorDescription(flatBufferBuilder, i11);
        addError(flatBufferBuilder, i10);
        addTimeToLive(flatBufferBuilder, i9);
        addPriority(flatBufferBuilder, i8);
        addMessageType(flatBufferBuilder, i7);
        addMessageId(flatBufferBuilder, i6);
        addRawData(flatBufferBuilder, i5);
        addData(flatBufferBuilder, i4);
        addCollapseKey(flatBufferBuilder, i3);
        addTo(flatBufferBuilder, i2);
        addFrom(flatBufferBuilder, i);
        addNotificationOpened(flatBufferBuilder, z);
        return endSerializedMessage(flatBufferBuilder);
    }

    public static int endSerializedMessage(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static SerializedMessage getRootAsSerializedMessage(ByteBuffer byteBuffer) {
        return getRootAsSerializedMessage(byteBuffer, new SerializedMessage());
    }

    public static SerializedMessage getRootAsSerializedMessage(ByteBuffer byteBuffer, SerializedMessage serializedMessage) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return serializedMessage.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startDataVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void startSerializedMessage(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(16);
    }

    public SerializedMessage __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.f355bb = byteBuffer;
        this.vtable_start = this.bb_pos - this.f355bb.getInt(this.bb_pos);
        this.vtable_size = this.f355bb.getShort(this.vtable_start);
    }

    public String collapseKey() {
        int i__offset = __offset(8);
        if (i__offset != 0) {
            return __string(i__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer collapseKeyAsByteBuffer() {
        return __vector_as_bytebuffer(8, 1);
    }

    public ByteBuffer collapseKeyInByteBuffer(ByteBuffer byteBuffer) {
        return __vector_in_bytebuffer(byteBuffer, 8, 1);
    }

    public DataPair data(int i) {
        return data(new DataPair(), i);
    }

    public DataPair data(DataPair dataPair, int i) {
        int i__offset = __offset(10);
        if (i__offset != 0) {
            return dataPair.__assign(__indirect(__vector(i__offset) + (i * 4)), this.f355bb);
        }
        return null;
    }

    public int dataLength() {
        int i__offset = __offset(10);
        if (i__offset != 0) {
            return __vector_len(i__offset);
        }
        return 0;
    }

    public String error() {
        int i__offset = __offset(22);
        if (i__offset != 0) {
            return __string(i__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer errorAsByteBuffer() {
        return __vector_as_bytebuffer(22, 1);
    }

    public String errorDescription() {
        int i__offset = __offset(24);
        if (i__offset != 0) {
            return __string(i__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer errorDescriptionAsByteBuffer() {
        return __vector_as_bytebuffer(24, 1);
    }

    public ByteBuffer errorDescriptionInByteBuffer(ByteBuffer byteBuffer) {
        return __vector_in_bytebuffer(byteBuffer, 24, 1);
    }

    public ByteBuffer errorInByteBuffer(ByteBuffer byteBuffer) {
        return __vector_in_bytebuffer(byteBuffer, 22, 1);
    }

    public String from() {
        int i__offset = __offset(4);
        if (i__offset != 0) {
            return __string(i__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer fromAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }

    public ByteBuffer fromInByteBuffer(ByteBuffer byteBuffer) {
        return __vector_in_bytebuffer(byteBuffer, 4, 1);
    }

    public String link() {
        int i__offset = __offset(30);
        if (i__offset != 0) {
            return __string(i__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer linkAsByteBuffer() {
        return __vector_as_bytebuffer(30, 1);
    }

    public ByteBuffer linkInByteBuffer(ByteBuffer byteBuffer) {
        return __vector_in_bytebuffer(byteBuffer, 30, 1);
    }

    public String messageId() {
        int i__offset = __offset(14);
        if (i__offset != 0) {
            return __string(i__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer messageIdAsByteBuffer() {
        return __vector_as_bytebuffer(14, 1);
    }

    public ByteBuffer messageIdInByteBuffer(ByteBuffer byteBuffer) {
        return __vector_in_bytebuffer(byteBuffer, 14, 1);
    }

    public String messageType() {
        int i__offset = __offset(16);
        if (i__offset != 0) {
            return __string(i__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer messageTypeAsByteBuffer() {
        return __vector_as_bytebuffer(16, 1);
    }

    public ByteBuffer messageTypeInByteBuffer(ByteBuffer byteBuffer) {
        return __vector_in_bytebuffer(byteBuffer, 16, 1);
    }

    public SerializedNotification notification() {
        return notification(new SerializedNotification());
    }

    public SerializedNotification notification(SerializedNotification serializedNotification) {
        int i__offset = __offset(26);
        if (i__offset != 0) {
            return serializedNotification.__assign(__indirect(i__offset + this.bb_pos), this.f355bb);
        }
        return null;
    }

    public boolean notificationOpened() {
        int i__offset = __offset(28);
        return (i__offset == 0 || this.f355bb.get(i__offset + this.bb_pos) == 0) ? false : true;
    }

    public String originalPriority() {
        int i__offset = __offset(34);
        if (i__offset != 0) {
            return __string(i__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer originalPriorityAsByteBuffer() {
        return __vector_as_bytebuffer(34, 1);
    }

    public ByteBuffer originalPriorityInByteBuffer(ByteBuffer byteBuffer) {
        return __vector_in_bytebuffer(byteBuffer, 34, 1);
    }

    public String priority() {
        int i__offset = __offset(18);
        if (i__offset != 0) {
            return __string(i__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer priorityAsByteBuffer() {
        return __vector_as_bytebuffer(18, 1);
    }

    public ByteBuffer priorityInByteBuffer(ByteBuffer byteBuffer) {
        return __vector_in_bytebuffer(byteBuffer, 18, 1);
    }

    public String rawData() {
        int i__offset = __offset(12);
        if (i__offset != 0) {
            return __string(i__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer rawDataAsByteBuffer() {
        return __vector_as_bytebuffer(12, 1);
    }

    public ByteBuffer rawDataInByteBuffer(ByteBuffer byteBuffer) {
        return __vector_in_bytebuffer(byteBuffer, 12, 1);
    }

    public long sentTime() {
        int i__offset = __offset(32);
        if (i__offset != 0) {
            return this.f355bb.getLong(i__offset + this.bb_pos);
        }
        return 0L;
    }

    public int timeToLive() {
        int i__offset = __offset(20);
        if (i__offset != 0) {
            return this.f355bb.getInt(i__offset + this.bb_pos);
        }
        return 0;
    }

    /* JADX INFO: renamed from: to */
    public String m608to() {
        int i__offset = __offset(6);
        if (i__offset != 0) {
            return __string(i__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer toAsByteBuffer() {
        return __vector_as_bytebuffer(6, 1);
    }

    public ByteBuffer toInByteBuffer(ByteBuffer byteBuffer) {
        return __vector_in_bytebuffer(byteBuffer, 6, 1);
    }
}
