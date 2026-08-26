package com.google.android.gms.common.server.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zag implements FastParser.zaa<BigInteger> {
    zag() {
    }

    @Override // com.google.android.gms.common.server.response.FastParser.zaa
    public final /* synthetic */ BigInteger zah(FastParser fastParser, BufferedReader bufferedReader) throws FastParser.ParseException, IOException {
        return fastParser.zaf(bufferedReader);
    }
}
