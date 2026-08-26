package com.google.common.hash;

import com.google.common.base.Preconditions;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class MacHashFunction extends AbstractStreamingHashFunction {
    private final int bits;
    private final Key key;
    private final Mac prototype;
    private final boolean supportsClone;
    private final String toString;

    private static final class MacHasher extends AbstractByteHasher {
        private boolean done;
        private final Mac mac;

        private MacHasher(Mac mac) {
            this.mac = mac;
        }

        private void checkNotDone() {
            Preconditions.checkState(!this.done, "Cannot re-use a Hasher after calling hash() on it");
        }

        @Override // com.google.common.hash.Hasher
        public HashCode hash() {
            checkNotDone();
            this.done = true;
            return HashCode.fromBytesNoCopy(this.mac.doFinal());
        }

        @Override // com.google.common.hash.AbstractByteHasher
        protected void update(byte b) {
            checkNotDone();
            this.mac.update(b);
        }

        @Override // com.google.common.hash.AbstractByteHasher
        protected void update(byte[] bArr) {
            checkNotDone();
            this.mac.update(bArr);
        }

        @Override // com.google.common.hash.AbstractByteHasher
        protected void update(byte[] bArr, int i, int i2) {
            checkNotDone();
            this.mac.update(bArr, i, i2);
        }
    }

    MacHashFunction(String str, Key key, String str2) {
        this.prototype = getMac(str, key);
        this.key = (Key) Preconditions.checkNotNull(key);
        this.toString = (String) Preconditions.checkNotNull(str2);
        this.bits = this.prototype.getMacLength() * 8;
        this.supportsClone = supportsClone(this.prototype);
    }

    private static Mac getMac(String str, Key key) {
        try {
            Mac mac = Mac.getInstance(str);
            mac.init(key);
            return mac;
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchAlgorithmException e2) {
            throw new IllegalStateException(e2);
        }
    }

    private static boolean supportsClone(Mac mac) {
        try {
            mac.clone();
            return true;
        } catch (CloneNotSupportedException unused) {
            return false;
        }
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return this.bits;
    }

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher() {
        if (this.supportsClone) {
            try {
                return new MacHasher((Mac) this.prototype.clone());
            } catch (CloneNotSupportedException unused) {
            }
        }
        return new MacHasher(getMac(this.prototype.getAlgorithm(), this.key));
    }

    public String toString() {
        return this.toString;
    }
}
