package com.amazon.device.iap.internal.p004b;

import android.os.RemoteException;
import com.amazon.android.Kiwi;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.android.framework.prompt.PromptContent;
import com.amazon.android.framework.task.command.AbstractCommandTask;
import com.amazon.android.licensing.LicenseFailurePromptContentMapper;
import com.amazon.device.iap.PurchasingService;
import com.amazon.device.iap.internal.util.C0245d;
import com.amazon.device.iap.internal.util.C0246e;
import com.amazon.venezia.command.FailureResult;
import com.amazon.venezia.command.SuccessResult;
import com.deploygate.service.DeployGateEvent;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.i */
/* JADX INFO: compiled from: KiwiCommand.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class AbstractC0232i extends AbstractCommandTask {

    /* JADX INFO: renamed from: a */
    private static final String f201a = "i";

    /* JADX INFO: renamed from: b */
    private final C0218e f202b;

    /* JADX INFO: renamed from: c */
    private final String f203c;

    /* JADX INFO: renamed from: d */
    private final String f204d;

    /* JADX INFO: renamed from: e */
    private final String f205e;

    /* JADX INFO: renamed from: h */
    private boolean f208h;

    /* JADX INFO: renamed from: i */
    private AbstractC0232i f209i;

    /* JADX INFO: renamed from: j */
    private AbstractC0232i f210j;

    /* JADX INFO: renamed from: g */
    private final LicenseFailurePromptContentMapper f207g = new LicenseFailurePromptContentMapper();

    /* JADX INFO: renamed from: k */
    private boolean f211k = false;

    /* JADX INFO: renamed from: f */
    private final Map<String, Object> f206f = new HashMap();

    public AbstractC0232i(C0218e c0218e, String str, String str2) {
        this.f202b = c0218e;
        this.f203c = c0218e.m341c().toString();
        this.f204d = str;
        this.f205e = str2;
        this.f206f.put("requestId", this.f203c);
        this.f206f.put(DeployGateEvent.EXTRA_SDK_VERSION, PurchasingService.SDK_VERSION);
        this.f208h = true;
        this.f209i = null;
        this.f210j = null;
    }

    /* JADX INFO: renamed from: a */
    private void m351a(PromptContent promptContent) {
        if (promptContent == null) {
            return;
        }
        Kiwi.getPromptManager().present(new C0203b(promptContent));
    }

    /* JADX INFO: renamed from: a */
    public AbstractC0232i m352a(boolean z) {
        this.f211k = z;
        return this;
    }

    /* JADX INFO: renamed from: a */
    public void m353a(AbstractC0232i abstractC0232i) {
        this.f209i = abstractC0232i;
    }

    /* JADX INFO: renamed from: a */
    protected void m354a(String str, Object obj) {
        this.f206f.put(str, obj);
    }

    /* JADX INFO: renamed from: a */
    protected abstract boolean mo326a(SuccessResult successResult) throws Exception;

    /* JADX INFO: renamed from: a_ */
    public void mo345a_() {
        Kiwi.addCommandToCommandTaskPipeline(this);
    }

    /* JADX INFO: renamed from: b */
    protected C0218e m355b() {
        return this.f202b;
    }

    /* JADX INFO: renamed from: b */
    public void m356b(AbstractC0232i abstractC0232i) {
        this.f210j = abstractC0232i;
    }

    /* JADX INFO: renamed from: b */
    protected void m357b(boolean z) {
        this.f208h = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: c */
    public String m358c() {
        return this.f203c;
    }

    protected Map<String, Object> getCommandData() {
        return this.f206f;
    }

    protected String getCommandName() {
        return this.f204d;
    }

    protected String getCommandVersion() {
        return this.f205e;
    }

    protected boolean isExecutionNeeded() {
        return true;
    }

    protected final void onException(KiwiException kiwiException) {
        AbstractC0232i abstractC0232i;
        C0246e.m412a(f201a, "onException: exception = " + kiwiException.getMessage());
        if ("UNHANDLED_EXCEPTION".equals(kiwiException.getType()) && "2.0".equals(this.f205e) && (abstractC0232i = this.f210j) != null) {
            abstractC0232i.m352a(this.f211k);
            this.f210j.mo345a_();
            return;
        }
        if (this.f208h) {
            m351a(this.f207g.map(kiwiException));
        }
        if (this.f211k) {
            return;
        }
        this.f202b.mo330b();
    }

    protected final void onFailure(FailureResult failureResult) throws RemoteException, KiwiException {
        AbstractC0232i abstractC0232i;
        String str;
        C0246e.m412a(f201a, "onFailure: result = " + failureResult);
        if (((failureResult == null || (str = (String) failureResult.getExtensionData().get("maxVersion")) == null || !str.equalsIgnoreCase("1.0")) ? false : true) && (abstractC0232i = this.f210j) != null) {
            abstractC0232i.m352a(this.f211k);
            this.f210j.mo345a_();
            return;
        }
        if (this.f208h) {
            m351a(new PromptContent(failureResult.getDisplayableName(), failureResult.getDisplayableMessage(), failureResult.getButtonLabel(), failureResult.show()));
        }
        if (this.f211k) {
            return;
        }
        this.f202b.mo330b();
    }

    protected final void onSuccess(SuccessResult successResult) throws RemoteException {
        AbstractC0232i abstractC0232i;
        String str = (String) successResult.getData().get("errorMessage");
        C0246e.m412a(f201a, "onSuccess: result = " + successResult + ", errorMessage: " + str);
        if (!C0245d.m411a(str)) {
            if (this.f211k) {
                return;
            }
            this.f202b.mo330b();
            return;
        }
        boolean zMo326a = false;
        try {
            zMo326a = mo326a(successResult);
        } catch (Exception e) {
            C0246e.m414b(f201a, "Error calling onResult: " + e);
        }
        if (zMo326a && (abstractC0232i = this.f209i) != null) {
            abstractC0232i.mo345a_();
        } else {
            if (this.f211k) {
                return;
            }
            if (zMo326a) {
                this.f202b.mo329a();
            } else {
                this.f202b.mo330b();
            }
        }
    }
}
