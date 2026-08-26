package com.google.api.client.json.jackson2;

import com.google.api.client.json.JsonGenerator;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class JacksonGenerator extends JsonGenerator {
    private final JacksonFactory factory;
    private final com.fasterxml.jackson.core.JsonGenerator generator;

    JacksonGenerator(JacksonFactory jacksonFactory, com.fasterxml.jackson.core.JsonGenerator jsonGenerator) {
        this.factory = jacksonFactory;
        this.generator = jsonGenerator;
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void close() throws IOException {
        this.generator.close();
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void enablePrettyPrint() throws IOException {
        this.generator.useDefaultPrettyPrinter();
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void flush() throws IOException {
        this.generator.flush();
    }

    @Override // com.google.api.client.json.JsonGenerator
    public JacksonFactory getFactory() {
        return this.factory;
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void writeBoolean(boolean z) throws IOException {
        this.generator.writeBoolean(z);
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void writeEndArray() throws IOException {
        this.generator.writeEndArray();
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void writeEndObject() throws IOException {
        this.generator.writeEndObject();
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void writeFieldName(String str) throws IOException {
        this.generator.writeFieldName(str);
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void writeNull() throws IOException {
        this.generator.writeNull();
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void writeNumber(double d) throws IOException {
        this.generator.writeNumber(d);
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void writeNumber(float f) throws IOException {
        this.generator.writeNumber(f);
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void writeNumber(int i) throws IOException {
        this.generator.writeNumber(i);
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void writeNumber(long j) throws IOException {
        this.generator.writeNumber(j);
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void writeNumber(String str) throws IOException {
        this.generator.writeNumber(str);
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void writeNumber(BigDecimal bigDecimal) throws IOException {
        this.generator.writeNumber(bigDecimal);
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void writeNumber(BigInteger bigInteger) throws IOException {
        this.generator.writeNumber(bigInteger);
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void writeStartArray() throws IOException {
        this.generator.writeStartArray();
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void writeStartObject() throws IOException {
        this.generator.writeStartObject();
    }

    @Override // com.google.api.client.json.JsonGenerator
    public void writeString(String str) throws IOException {
        this.generator.writeString(str);
    }
}
