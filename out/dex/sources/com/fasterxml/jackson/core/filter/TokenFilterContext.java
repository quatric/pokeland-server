package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import kotlin.text.Typography;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class TokenFilterContext extends JsonStreamContext {
    protected TokenFilterContext _child;
    protected String _currentName;
    protected TokenFilter _filter;
    protected boolean _needToHandleName;
    protected final TokenFilterContext _parent;
    protected boolean _startHandled;

    protected TokenFilterContext(int i, TokenFilterContext tokenFilterContext, TokenFilter tokenFilter, boolean z) {
        this._type = i;
        this._parent = tokenFilterContext;
        this._filter = tokenFilter;
        this._index = -1;
        this._startHandled = z;
        this._needToHandleName = false;
    }

    private void _writePath(JsonGenerator jsonGenerator) throws IOException {
        TokenFilter tokenFilter = this._filter;
        if (tokenFilter == null || tokenFilter == TokenFilter.INCLUDE_ALL) {
            return;
        }
        TokenFilterContext tokenFilterContext = this._parent;
        if (tokenFilterContext != null) {
            tokenFilterContext._writePath(jsonGenerator);
        }
        if (this._startHandled) {
            if (this._needToHandleName) {
                this._needToHandleName = false;
                jsonGenerator.writeFieldName(this._currentName);
                return;
            }
            return;
        }
        this._startHandled = true;
        if (this._type != 2) {
            if (this._type == 1) {
                jsonGenerator.writeStartArray();
            }
        } else {
            jsonGenerator.writeStartObject();
            if (this._needToHandleName) {
                this._needToHandleName = false;
                jsonGenerator.writeFieldName(this._currentName);
            }
        }
    }

    public static TokenFilterContext createRootContext(TokenFilter tokenFilter) {
        return new TokenFilterContext(0, null, tokenFilter, true);
    }

    protected void appendDesc(StringBuilder sb) {
        TokenFilterContext tokenFilterContext = this._parent;
        if (tokenFilterContext != null) {
            tokenFilterContext.appendDesc(sb);
        }
        if (this._type != 2) {
            if (this._type != 1) {
                sb.append("/");
                return;
            }
            sb.append('[');
            sb.append(getCurrentIndex());
            sb.append(']');
            return;
        }
        sb.append('{');
        if (this._currentName != null) {
            sb.append(Typography.quote);
            sb.append(this._currentName);
            sb.append(Typography.quote);
        } else {
            sb.append('?');
        }
        sb.append('}');
    }

    public TokenFilter checkValue(TokenFilter tokenFilter) {
        if (this._type == 2) {
            return tokenFilter;
        }
        int i = this._index + 1;
        this._index = i;
        return this._type == 1 ? tokenFilter.includeElement(i) : tokenFilter.includeRootValue(i);
    }

    public TokenFilterContext closeArray(JsonGenerator jsonGenerator) throws IOException {
        if (this._startHandled) {
            jsonGenerator.writeEndArray();
        }
        TokenFilter tokenFilter = this._filter;
        if (tokenFilter != null && tokenFilter != TokenFilter.INCLUDE_ALL) {
            this._filter.filterFinishArray();
        }
        return this._parent;
    }

    public TokenFilterContext closeObject(JsonGenerator jsonGenerator) throws IOException {
        if (this._startHandled) {
            jsonGenerator.writeEndObject();
        }
        TokenFilter tokenFilter = this._filter;
        if (tokenFilter != null && tokenFilter != TokenFilter.INCLUDE_ALL) {
            this._filter.filterFinishObject();
        }
        return this._parent;
    }

    public TokenFilterContext createChildArrayContext(TokenFilter tokenFilter, boolean z) {
        TokenFilterContext tokenFilterContext = this._child;
        if (tokenFilterContext != null) {
            return tokenFilterContext.reset(1, tokenFilter, z);
        }
        TokenFilterContext tokenFilterContext2 = new TokenFilterContext(1, this, tokenFilter, z);
        this._child = tokenFilterContext2;
        return tokenFilterContext2;
    }

    public TokenFilterContext createChildObjectContext(TokenFilter tokenFilter, boolean z) {
        TokenFilterContext tokenFilterContext = this._child;
        if (tokenFilterContext != null) {
            return tokenFilterContext.reset(2, tokenFilter, z);
        }
        TokenFilterContext tokenFilterContext2 = new TokenFilterContext(2, this, tokenFilter, z);
        this._child = tokenFilterContext2;
        return tokenFilterContext2;
    }

    public TokenFilterContext findChildOf(TokenFilterContext tokenFilterContext) {
        TokenFilterContext tokenFilterContext2 = this._parent;
        if (tokenFilterContext2 == tokenFilterContext) {
            return this;
        }
        while (tokenFilterContext2 != null) {
            TokenFilterContext tokenFilterContext3 = tokenFilterContext2._parent;
            if (tokenFilterContext3 == tokenFilterContext) {
                return tokenFilterContext2;
            }
            tokenFilterContext2 = tokenFilterContext3;
        }
        return null;
    }

    @Override // com.fasterxml.jackson.core.JsonStreamContext
    public final String getCurrentName() {
        return this._currentName;
    }

    @Override // com.fasterxml.jackson.core.JsonStreamContext
    public Object getCurrentValue() {
        return null;
    }

    public TokenFilter getFilter() {
        return this._filter;
    }

    @Override // com.fasterxml.jackson.core.JsonStreamContext
    public final TokenFilterContext getParent() {
        return this._parent;
    }

    @Override // com.fasterxml.jackson.core.JsonStreamContext
    public boolean hasCurrentName() {
        return this._currentName != null;
    }

    public boolean isStartHandled() {
        return this._startHandled;
    }

    public JsonToken nextTokenToRead() {
        if (!this._startHandled) {
            this._startHandled = true;
            return this._type == 2 ? JsonToken.START_OBJECT : JsonToken.START_ARRAY;
        }
        if (!this._needToHandleName || this._type != 2) {
            return null;
        }
        this._needToHandleName = false;
        return JsonToken.FIELD_NAME;
    }

    protected TokenFilterContext reset(int i, TokenFilter tokenFilter, boolean z) {
        this._type = i;
        this._filter = tokenFilter;
        this._index = -1;
        this._currentName = null;
        this._startHandled = z;
        this._needToHandleName = false;
        return this;
    }

    @Override // com.fasterxml.jackson.core.JsonStreamContext
    public void setCurrentValue(Object obj) {
    }

    public TokenFilter setFieldName(String str) throws JsonProcessingException {
        this._currentName = str;
        this._needToHandleName = true;
        return this._filter;
    }

    public void skipParentChecks() {
        this._filter = null;
        for (TokenFilterContext tokenFilterContext = this._parent; tokenFilterContext != null; tokenFilterContext = tokenFilterContext._parent) {
            this._parent._filter = null;
        }
    }

    @Override // com.fasterxml.jackson.core.JsonStreamContext
    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        appendDesc(sb);
        return sb.toString();
    }

    public void writeImmediatePath(JsonGenerator jsonGenerator) throws IOException {
        TokenFilter tokenFilter = this._filter;
        if (tokenFilter == null || tokenFilter == TokenFilter.INCLUDE_ALL) {
            return;
        }
        if (this._startHandled) {
            if (this._needToHandleName) {
                jsonGenerator.writeFieldName(this._currentName);
                return;
            }
            return;
        }
        this._startHandled = true;
        if (this._type != 2) {
            if (this._type == 1) {
                jsonGenerator.writeStartArray();
            }
        } else {
            jsonGenerator.writeStartObject();
            if (this._needToHandleName) {
                jsonGenerator.writeFieldName(this._currentName);
            }
        }
    }

    public void writePath(JsonGenerator jsonGenerator) throws IOException {
        TokenFilter tokenFilter = this._filter;
        if (tokenFilter == null || tokenFilter == TokenFilter.INCLUDE_ALL) {
            return;
        }
        TokenFilterContext tokenFilterContext = this._parent;
        if (tokenFilterContext != null) {
            tokenFilterContext._writePath(jsonGenerator);
        }
        if (this._startHandled) {
            if (this._needToHandleName) {
                jsonGenerator.writeFieldName(this._currentName);
                return;
            }
            return;
        }
        this._startHandled = true;
        if (this._type == 2) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeFieldName(this._currentName);
        } else if (this._type == 1) {
            jsonGenerator.writeStartArray();
        }
    }
}
