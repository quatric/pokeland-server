package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.JsonPointer;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class JsonPointerBasedFilter extends TokenFilter {
    protected final JsonPointer _pathToMatch;

    public JsonPointerBasedFilter(JsonPointer jsonPointer) {
        this._pathToMatch = jsonPointer;
    }

    public JsonPointerBasedFilter(String str) {
        this(JsonPointer.compile(str));
    }

    @Override // com.fasterxml.jackson.core.filter.TokenFilter
    protected boolean _includeScalar() {
        return this._pathToMatch.matches();
    }

    @Override // com.fasterxml.jackson.core.filter.TokenFilter
    public TokenFilter filterStartArray() {
        return this;
    }

    @Override // com.fasterxml.jackson.core.filter.TokenFilter
    public TokenFilter filterStartObject() {
        return this;
    }

    @Override // com.fasterxml.jackson.core.filter.TokenFilter
    public TokenFilter includeElement(int i) {
        JsonPointer jsonPointerMatchElement = this._pathToMatch.matchElement(i);
        if (jsonPointerMatchElement == null) {
            return null;
        }
        return jsonPointerMatchElement.matches() ? TokenFilter.INCLUDE_ALL : new JsonPointerBasedFilter(jsonPointerMatchElement);
    }

    @Override // com.fasterxml.jackson.core.filter.TokenFilter
    public TokenFilter includeProperty(String str) {
        JsonPointer jsonPointerMatchProperty = this._pathToMatch.matchProperty(str);
        if (jsonPointerMatchProperty == null) {
            return null;
        }
        return jsonPointerMatchProperty.matches() ? TokenFilter.INCLUDE_ALL : new JsonPointerBasedFilter(jsonPointerMatchProperty);
    }

    @Override // com.fasterxml.jackson.core.filter.TokenFilter
    public String toString() {
        return "[JsonPointerFilter at: " + this._pathToMatch + "]";
    }
}
