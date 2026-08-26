package com.google.firebase.messaging.cpp;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class SerializedEvent extends Table {
    public static void addEvent(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static void addEventType(FlatBufferBuilder flatBufferBuilder, byte b) {
        flatBufferBuilder.addByte(0, b, 0);
    }

    public static int createSerializedEvent(FlatBufferBuilder flatBufferBuilder, byte b, int i) {
        flatBufferBuilder.startObject(2);
        addEvent(flatBufferBuilder, i);
        addEventType(flatBufferBuilder, b);
        return endSerializedEvent(flatBufferBuilder);
    }

    public static int endSerializedEvent(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static void finishSerializedEventBuffer(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.finish(i);
    }

    public static void finishSizePrefixedSerializedEventBuffer(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.finishSizePrefixed(i);
    }

    public static SerializedEvent getRootAsSerializedEvent(ByteBuffer byteBuffer) {
        return getRootAsSerializedEvent(byteBuffer, new SerializedEvent());
    }

    public static SerializedEvent getRootAsSerializedEvent(ByteBuffer byteBuffer, SerializedEvent serializedEvent) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return serializedEvent.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startSerializedEvent(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(2);
    }

    public SerializedEvent __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.f355bb = byteBuffer;
        this.vtable_start = this.bb_pos - this.f355bb.getInt(this.bb_pos);
        this.vtable_size = this.f355bb.getShort(this.vtable_start);
    }

    public Table event(Table table) {
        int i__offset = __offset(6);
        if (i__offset != 0) {
            return __union(table, i__offset);
        }
        return null;
    }

    public byte eventType() {
        int i__offset = __offset(4);
        if (i__offset != 0) {
            return this.f355bb.get(i__offset + this.bb_pos);
        }
        return (byte) 0;
    }
}
