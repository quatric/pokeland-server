package com.google.firebase.messaging.cpp;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class SerializedTokenReceived extends Table {
    public static void addToken(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static int createSerializedTokenReceived(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startObject(1);
        addToken(flatBufferBuilder, i);
        return endSerializedTokenReceived(flatBufferBuilder);
    }

    public static int endSerializedTokenReceived(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static SerializedTokenReceived getRootAsSerializedTokenReceived(ByteBuffer byteBuffer) {
        return getRootAsSerializedTokenReceived(byteBuffer, new SerializedTokenReceived());
    }

    public static SerializedTokenReceived getRootAsSerializedTokenReceived(ByteBuffer byteBuffer, SerializedTokenReceived serializedTokenReceived) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return serializedTokenReceived.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startSerializedTokenReceived(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(1);
    }

    public SerializedTokenReceived __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.f355bb = byteBuffer;
        this.vtable_start = this.bb_pos - this.f355bb.getInt(this.bb_pos);
        this.vtable_size = this.f355bb.getShort(this.vtable_start);
    }

    public String token() {
        int i__offset = __offset(4);
        if (i__offset != 0) {
            return __string(i__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer tokenAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }

    public ByteBuffer tokenInByteBuffer(ByteBuffer byteBuffer) {
        return __vector_in_bytebuffer(byteBuffer, 4, 1);
    }
}
