package com.google.api.client.json.jackson2;

import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonToken;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class JacksonParser extends JsonParser {
    private final JacksonFactory factory;
    private final com.fasterxml.jackson.core.JsonParser parser;

    JacksonParser(JacksonFactory jacksonFactory, com.fasterxml.jackson.core.JsonParser jsonParser) {
        this.factory = jacksonFactory;
        this.parser = jsonParser;
    }

    @Override // com.google.api.client.json.JsonParser
    public void close() throws IOException {
        this.parser.close();
    }

    @Override // com.google.api.client.json.JsonParser
    public BigInteger getBigIntegerValue() throws IOException {
        return this.parser.getBigIntegerValue();
    }

    @Override // com.google.api.client.json.JsonParser
    public byte getByteValue() throws IOException {
        return this.parser.getByteValue();
    }

    @Override // com.google.api.client.json.JsonParser
    public String getCurrentName() throws IOException {
        return this.parser.getCurrentName();
    }

    @Override // com.google.api.client.json.JsonParser
    public JsonToken getCurrentToken() {
        return JacksonFactory.convert(this.parser.getCurrentToken());
    }

    @Override // com.google.api.client.json.JsonParser
    public BigDecimal getDecimalValue() throws IOException {
        return this.parser.getDecimalValue();
    }

    @Override // com.google.api.client.json.JsonParser
    public double getDoubleValue() throws IOException {
        return this.parser.getDoubleValue();
    }

    @Override // com.google.api.client.json.JsonParser
    public JacksonFactory getFactory() {
        return this.factory;
    }

    @Override // com.google.api.client.json.JsonParser
    public float getFloatValue() throws IOException {
        return this.parser.getFloatValue();
    }

    @Override // com.google.api.client.json.JsonParser
    public int getIntValue() throws IOException {
        return this.parser.getIntValue();
    }

    @Override // com.google.api.client.json.JsonParser
    public long getLongValue() throws IOException {
        return this.parser.getLongValue();
    }

    @Override // com.google.api.client.json.JsonParser
    public short getShortValue() throws IOException {
        return this.parser.getShortValue();
    }

    @Override // com.google.api.client.json.JsonParser
    public String getText() throws IOException {
        return this.parser.getText();
    }

    @Override // com.google.api.client.json.JsonParser
    public JsonToken nextToken() throws IOException {
        return JacksonFactory.convert(this.parser.nextToken());
    }

    @Override // com.google.api.client.json.JsonParser
    public JsonParser skipChildren() throws IOException {
        this.parser.skipChildren();
        return this;
    }
}
