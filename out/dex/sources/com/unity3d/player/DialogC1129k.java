package com.unity3d.player;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.api.client.googleapis.media.MediaHttpDownloader;

/* JADX INFO: renamed from: com.unity3d.player.k */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class DialogC1129k extends Dialog implements TextWatcher, View.OnClickListener {

    /* JADX INFO: renamed from: c */
    private static int f2045c = 1627389952;

    /* JADX INFO: renamed from: d */
    private static int f2046d = -1;

    /* JADX INFO: renamed from: e */
    private static int f2047e = 134217728;

    /* JADX INFO: renamed from: f */
    private static int f2048f = 67108864;

    /* JADX INFO: renamed from: a */
    private Context f2049a;

    /* JADX INFO: renamed from: b */
    private UnityPlayer f2050b;

    public DialogC1129k(Context context, UnityPlayer unityPlayer, String str, int i, boolean z, boolean z2, boolean z3, String str2, int i2, boolean z4) {
        super(context);
        this.f2049a = null;
        this.f2050b = null;
        this.f2049a = context;
        this.f2050b = unityPlayer;
        m1941a(z4);
        getWindow().requestFeature(1);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setContentView(createSoftInputView());
        getWindow().setLayout(-1, -2);
        getWindow().clearFlags(2);
        if (C1128j.f2041a) {
            getWindow().clearFlags(f2047e);
            getWindow().clearFlags(f2048f);
        }
        EditText editText = (EditText) findViewById(1057292289);
        Button button = (Button) findViewById(1057292290);
        m1933a(editText, str, i, z, z2, z3, str2, i2);
        button.setOnClickListener(this);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.unity3d.player.k.1
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z5) {
                if (z5) {
                    DialogC1129k.this.getWindow().setSoftInputMode(5);
                }
            }
        });
        editText.requestFocus();
    }

    /* JADX INFO: renamed from: a */
    private static int m1930a(int i, boolean z, boolean z2, boolean z3) {
        int i2 = (z ? 32768 : 524288) | (z2 ? 131072 : 0) | (z3 ? 128 : 0);
        if (i < 0 || i > 10) {
            return i2;
        }
        int[] iArr = {1, 16385, 12290, 17, 2, 3, 8289, 33, 1, 16417, 17};
        return (iArr[i] & 2) != 0 ? iArr[i] : iArr[i] | i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public String m1931a() {
        EditText editText = (EditText) findViewById(1057292289);
        if (editText == null) {
            return null;
        }
        return editText.getText().toString().trim();
    }

    /* JADX INFO: renamed from: a */
    private void m1933a(EditText editText, String str, int i, boolean z, boolean z2, boolean z3, String str2, int i2) {
        editText.setImeOptions(6);
        editText.setText(str);
        editText.setHint(str2);
        editText.setHintTextColor(f2045c);
        editText.setInputType(m1930a(i, z, z2, z3));
        editText.setImeOptions(MediaHttpDownloader.MAXIMUM_CHUNK_SIZE);
        if (i2 > 0) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i2)});
        }
        editText.addTextChangedListener(this);
        editText.setSelection(editText.getText().length());
        editText.setClickable(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1935a(String str, boolean z) {
        ((EditText) findViewById(1057292289)).setSelection(0, 0);
        this.f2050b.reportSoftInputStr(str, 1, z);
    }

    /* JADX INFO: renamed from: a */
    public final void m1938a(int i) {
        EditText editText = (EditText) findViewById(1057292289);
        if (editText != null) {
            if (i > 0) {
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i)});
            } else {
                editText.setFilters(new InputFilter[0]);
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m1939a(int i, int i2) {
        int i3;
        EditText editText = (EditText) findViewById(1057292289);
        if (editText == null || editText.getText().length() < (i3 = i2 + i)) {
            return;
        }
        editText.setSelection(i, i3);
    }

    /* JADX INFO: renamed from: a */
    public final void m1940a(String str) {
        EditText editText = (EditText) findViewById(1057292289);
        if (editText != null) {
            editText.setText(str);
            editText.setSelection(str.length());
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m1941a(boolean z) {
        int i;
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (z) {
            attributes.gravity = 8;
            i = 20000;
        } else {
            attributes.gravity = 80;
            i = 0;
        }
        attributes.x = i;
        attributes.y = i;
        window.setAttributes(attributes);
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        this.f2050b.reportSoftInputStr(editable.toString(), 0, false);
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    protected final View createSoftInputView() {
        RelativeLayout relativeLayout = new RelativeLayout(this.f2049a);
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        relativeLayout.setBackgroundColor(f2046d);
        EditText editText = new EditText(this.f2049a) { // from class: com.unity3d.player.k.2
            @Override // android.widget.TextView, android.view.View
            public final boolean onKeyPreIme(int i, KeyEvent keyEvent) {
                if (i == 4) {
                    DialogC1129k dialogC1129k = DialogC1129k.this;
                    dialogC1129k.m1935a(dialogC1129k.m1931a(), true);
                    return true;
                }
                if (i == 84) {
                    return true;
                }
                return super.onKeyPreIme(i, keyEvent);
            }

            @Override // android.widget.TextView
            protected final void onSelectionChanged(int i, int i2) {
                DialogC1129k.this.f2050b.reportSoftInputSelection(i, i2 - i);
            }

            @Override // android.widget.TextView, android.view.View
            public final void onWindowFocusChanged(boolean z) {
                super.onWindowFocusChanged(z);
                if (z) {
                    ((InputMethodManager) DialogC1129k.this.f2049a.getSystemService("input_method")).showSoftInput(this, 0);
                }
            }
        };
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(15);
        layoutParams.addRule(0, 1057292290);
        editText.setLayoutParams(layoutParams);
        editText.setId(1057292289);
        relativeLayout.addView(editText);
        Button button = new Button(this.f2049a);
        button.setText(this.f2049a.getResources().getIdentifier("ok", "string", "android"));
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(15);
        layoutParams2.addRule(11);
        button.setLayoutParams(layoutParams2);
        button.setId(1057292290);
        button.setBackgroundColor(0);
        relativeLayout.addView(button);
        ((EditText) relativeLayout.findViewById(1057292289)).setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.unity3d.player.k.3
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 6) {
                    DialogC1129k dialogC1129k = DialogC1129k.this;
                    dialogC1129k.m1935a(dialogC1129k.m1931a(), false);
                }
                return false;
            }
        });
        relativeLayout.setPadding(16, 16, 16, 16);
        return relativeLayout;
    }

    @Override // android.app.Dialog
    public final void onBackPressed() {
        m1935a(m1931a(), true);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        m1935a(m1931a(), false);
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
