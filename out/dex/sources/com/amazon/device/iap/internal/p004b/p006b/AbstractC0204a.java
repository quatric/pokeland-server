package com.amazon.device.iap.internal.p004b.p006b;

import android.app.Activity;
import android.content.Intent;
import android.os.RemoteException;
import com.amazon.android.framework.context.ContextManager;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.android.framework.resource.Resource;
import com.amazon.android.framework.task.Task;
import com.amazon.android.framework.task.TaskManager;
import com.amazon.android.framework.task.pipeline.TaskPipelineId;
import com.amazon.device.iap.internal.p004b.AbstractC0232i;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.device.iap.internal.util.C0246e;
import com.amazon.device.iap.internal.util.MetricsHelper;
import com.amazon.venezia.command.SuccessResult;
import java.util.Map;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.b.a */
/* JADX INFO: compiled from: PurchaseItemCommandBase.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class AbstractC0204a extends AbstractC0232i {

    /* JADX INFO: renamed from: d */
    private static final String f165d = "a";

    /* JADX INFO: renamed from: a */
    @Resource
    protected TaskManager f166a;

    /* JADX INFO: renamed from: b */
    @Resource
    protected ContextManager f167b;

    /* JADX INFO: renamed from: c */
    protected final String f168c;

    AbstractC0204a(C0218e c0218e, String str, String str2) {
        super(c0218e, "purchase_item", str);
        this.f168c = str2;
        m354a("sku", this.f168c);
    }

    @Override // com.amazon.device.iap.internal.p004b.AbstractC0232i
    /* JADX INFO: renamed from: a */
    protected boolean mo326a(SuccessResult successResult) throws RemoteException, KiwiException {
        Map data = successResult.getData();
        C0246e.m412a(f165d, "data: " + data);
        if (!data.containsKey("purchaseItemIntent")) {
            C0246e.m414b(f165d, "did not find intent");
            return false;
        }
        C0246e.m412a(f165d, "found intent");
        final Intent intent = (Intent) data.remove("purchaseItemIntent");
        this.f166a.enqueueAtFront(TaskPipelineId.FOREGROUND, new Task() { // from class: com.amazon.device.iap.internal.b.b.a.1
            public void execute() {
                try {
                    Activity visible = AbstractC0204a.this.f167b.getVisible();
                    if (visible == null) {
                        visible = AbstractC0204a.this.f167b.getRoot();
                    }
                    C0246e.m412a(AbstractC0204a.f165d, "About to fire intent with activity " + visible);
                    visible.startActivity(intent);
                } catch (Exception e) {
                    MetricsHelper.submitExceptionMetrics(AbstractC0204a.this.m358c(), AbstractC0204a.f165d + ".onResult().execute()", e);
                    C0246e.m414b(AbstractC0204a.f165d, "Exception when attempting to fire intent: " + e);
                }
            }
        });
        return true;
    }
}
