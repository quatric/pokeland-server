package com.google.common.escape;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
public abstract class Escaper {
    private final Function<String, String> asFunction = new Function<String, String>() { // from class: com.google.common.escape.Escaper.1
        @Override // com.google.common.base.Function
        public String apply(String str) {
            return Escaper.this.escape(str);
        }
    };

    protected Escaper() {
    }

    public final Function<String, String> asFunction() {
        return this.asFunction;
    }

    public abstract String escape(String str);
}
