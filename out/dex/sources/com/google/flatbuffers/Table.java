package com.google.flatbuffers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class Table {
    public static final ThreadLocal<Charset> UTF8_CHARSET = new ThreadLocal<Charset>() { // from class: com.google.flatbuffers.Table.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public Charset initialValue() {
            return Charset.forName("UTF-8");
        }
    };

    /* JADX INFO: renamed from: bb */
    protected ByteBuffer f355bb;
    protected int bb_pos;
    Utf8 utf8 = Utf8.getDefault();
    protected int vtable_size;
    protected int vtable_start;

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    protected static boolean __has_identifier(ByteBuffer byteBuffer, String str) {
        if (str.length() != 4) {
            throw new AssertionError("FlatBuffers: file identifier must be length 4");
        }
        for (int i = 0; i < 4; i++) {
            if (str.charAt(i) != ((char) byteBuffer.get(byteBuffer.position() + 4 + i))) {
                return false;
            }
        }
        return true;
    }

    protected static int __indirect(int i, ByteBuffer byteBuffer) {
        return i + byteBuffer.getInt(i);
    }

    protected static int __offset(int i, int i2, ByteBuffer byteBuffer) {
        int iCapacity = byteBuffer.capacity() - i2;
        return byteBuffer.getShort((i + iCapacity) - byteBuffer.getInt(iCapacity)) + iCapacity;
    }

    protected static int compareStrings(int i, int i2, ByteBuffer byteBuffer) {
        int i3 = i + byteBuffer.getInt(i);
        int i4 = i2 + byteBuffer.getInt(i2);
        int i5 = byteBuffer.getInt(i3);
        int i6 = byteBuffer.getInt(i4);
        int i7 = i3 + 4;
        int i8 = i4 + 4;
        int iMin = Math.min(i5, i6);
        for (int i9 = 0; i9 < iMin; i9++) {
            int i10 = i9 + i7;
            int i11 = i9 + i8;
            if (byteBuffer.get(i10) != byteBuffer.get(i11)) {
                return byteBuffer.get(i10) - byteBuffer.get(i11);
            }
        }
        return i5 - i6;
    }

    protected static int compareStrings(int i, byte[] bArr, ByteBuffer byteBuffer) {
        int i2 = i + byteBuffer.getInt(i);
        int i3 = byteBuffer.getInt(i2);
        int length = bArr.length;
        int i4 = i2 + 4;
        int iMin = Math.min(i3, length);
        for (int i5 = 0; i5 < iMin; i5++) {
            int i6 = i5 + i4;
            if (byteBuffer.get(i6) != bArr[i5]) {
                return byteBuffer.get(i6) - bArr[i5];
            }
        }
        return i3 - length;
    }

    protected int __indirect(int i) {
        return i + this.f355bb.getInt(i);
    }

    protected int __offset(int i) {
        if (i < this.vtable_size) {
            return this.f355bb.getShort(this.vtable_start + i);
        }
        return 0;
    }

    public void __reset() {
        this.f355bb = null;
        this.bb_pos = 0;
        this.vtable_start = 0;
        this.vtable_size = 0;
    }

    protected String __string(int i) {
        int i2 = i + this.f355bb.getInt(i);
        return this.utf8.decodeUtf8(this.f355bb, i2 + 4, this.f355bb.getInt(i2));
    }

    protected Table __union(Table table, int i) {
        int i2 = i + this.bb_pos;
        table.bb_pos = i2 + this.f355bb.getInt(i2);
        table.f355bb = this.f355bb;
        int i3 = table.bb_pos;
        table.vtable_start = i3 - this.f355bb.getInt(i3);
        table.vtable_size = this.f355bb.getShort(table.vtable_start);
        return table;
    }

    protected int __vector(int i) {
        int i2 = i + this.bb_pos;
        return i2 + this.f355bb.getInt(i2) + 4;
    }

    protected ByteBuffer __vector_as_bytebuffer(int i, int i2) {
        int i__offset = __offset(i);
        if (i__offset == 0) {
            return null;
        }
        ByteBuffer byteBufferOrder = this.f355bb.duplicate().order(ByteOrder.LITTLE_ENDIAN);
        int i__vector = __vector(i__offset);
        byteBufferOrder.position(i__vector);
        byteBufferOrder.limit(i__vector + (__vector_len(i__offset) * i2));
        return byteBufferOrder;
    }

    protected ByteBuffer __vector_in_bytebuffer(ByteBuffer byteBuffer, int i, int i2) {
        int i__offset = __offset(i);
        if (i__offset == 0) {
            return null;
        }
        int i__vector = __vector(i__offset);
        byteBuffer.rewind();
        byteBuffer.limit((__vector_len(i__offset) * i2) + i__vector);
        byteBuffer.position(i__vector);
        return byteBuffer;
    }

    protected int __vector_len(int i) {
        int i2 = i + this.bb_pos;
        return this.f355bb.getInt(i2 + this.f355bb.getInt(i2));
    }

    public ByteBuffer getByteBuffer() {
        return this.f355bb;
    }

    protected int keysCompare(Integer num, Integer num2, ByteBuffer byteBuffer) {
        return 0;
    }

    protected void sortTables(int[] iArr, final ByteBuffer byteBuffer) {
        Integer[] numArr = new Integer[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            numArr[i] = Integer.valueOf(iArr[i]);
        }
        Arrays.sort(numArr, new Comparator<Integer>() { // from class: com.google.flatbuffers.Table.2
            @Override // java.util.Comparator
            public int compare(Integer num, Integer num2) {
                return Table.this.keysCompare(num, num2, byteBuffer);
            }
        });
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr[i2] = numArr[i2].intValue();
        }
    }
}
