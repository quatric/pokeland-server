package com.fasterxml.jackson.core.format;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.p014io.MergedStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class DataFormatMatcher {
    protected final byte[] _bufferedData;
    protected final int _bufferedLength;
    protected final int _bufferedStart;
    protected final JsonFactory _match;
    protected final MatchStrength _matchStrength;
    protected final InputStream _originalStream;

    protected DataFormatMatcher(InputStream inputStream, byte[] bArr, int i, int i2, JsonFactory jsonFactory, MatchStrength matchStrength) {
        this._originalStream = inputStream;
        this._bufferedData = bArr;
        this._bufferedStart = i;
        this._bufferedLength = i2;
        this._match = jsonFactory;
        this._matchStrength = matchStrength;
        if ((i | i2) < 0 || i + i2 > bArr.length) {
            throw new IllegalArgumentException(String.format("Illegal start/length (%d/%d) wrt input array of %d bytes", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(bArr.length)));
        }
    }

    public JsonParser createParserWithMatch() throws IOException {
        JsonFactory jsonFactory = this._match;
        if (jsonFactory == null) {
            return null;
        }
        return this._originalStream == null ? jsonFactory.createParser(this._bufferedData, this._bufferedStart, this._bufferedLength) : jsonFactory.createParser(getDataStream());
    }

    public InputStream getDataStream() {
        InputStream inputStream = this._originalStream;
        return inputStream == null ? new ByteArrayInputStream(this._bufferedData, this._bufferedStart, this._bufferedLength) : new MergedStream(null, inputStream, this._bufferedData, this._bufferedStart, this._bufferedLength);
    }

    public JsonFactory getMatch() {
        return this._match;
    }

    public MatchStrength getMatchStrength() {
        MatchStrength matchStrength = this._matchStrength;
        return matchStrength == null ? MatchStrength.INCONCLUSIVE : matchStrength;
    }

    public String getMatchedFormatName() {
        return this._match.getFormatName();
    }

    public boolean hasMatch() {
        return this._match != null;
    }
}
