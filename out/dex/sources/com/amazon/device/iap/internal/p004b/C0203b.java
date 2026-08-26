package com.amazon.device.iap.internal.p004b;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.amazon.android.framework.context.ContextManager;
import com.amazon.android.framework.prompt.PromptContent;
import com.amazon.android.framework.prompt.SimplePrompt;
import com.amazon.android.framework.resource.Resource;
import com.amazon.device.iap.internal.util.C0246e;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.b */
/* JADX INFO: compiled from: FailurePrompt.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0203b extends SimplePrompt {

    /* JADX INFO: renamed from: a */
    private static final String f162a = "b";

    /* JADX INFO: renamed from: b */
    @Resource
    private ContextManager f163b;

    /* JADX INFO: renamed from: c */
    private final PromptContent f164c;

    public C0203b(PromptContent promptContent) {
        super(promptContent);
        this.f164c = promptContent;
    }

    protected void doAction() {
        C0246e.m412a(f162a, "doAction");
        if ("Amazon Appstore required".equalsIgnoreCase(this.f164c.getTitle()) || "Amazon Appstore Update Required".equalsIgnoreCase(this.f164c.getTitle())) {
            try {
                Activity visible = this.f163b.getVisible();
                if (visible == null) {
                    visible = this.f163b.getRoot();
                }
                visible.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.amazon.com/gp/mas/get-appstore/android/ref=mas_mx_mba_iap_dl")));
            } catch (Exception e) {
                C0246e.m414b(f162a, "Exception in PurchaseItemCommandTask.OnSuccess: " + e);
            }
        }
    }

    protected long getExpirationDurationInSeconds() {
        return 31536000L;
    }

    public String toString() {
        return f162a;
    }
}
