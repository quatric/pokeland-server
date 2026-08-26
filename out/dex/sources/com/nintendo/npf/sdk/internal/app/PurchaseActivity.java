package com.nintendo.npf.sdk.internal.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.metaps.common.C0856j;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C0998a;
import com.nintendo.npf.sdk.internal.p016a.AbstractC0880e;
import com.nintendo.npf.sdk.internal.p016a.InterfaceC0883h;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import java.math.BigDecimal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class PurchaseActivity extends Activity implements AbstractC0880e.a, InterfaceC0883h.c {

    /* JADX INFO: renamed from: a */
    private static final String f1135a = "PurchaseActivity";

    /* JADX INFO: renamed from: b */
    private String f1136b;

    /* JADX INFO: renamed from: c */
    private BigDecimal f1137c;

    /* JADX INFO: renamed from: d */
    private String f1138d;

    /* JADX INFO: renamed from: e */
    private String f1139e;

    /* JADX INFO: renamed from: f */
    private String f1140f;

    /* JADX INFO: renamed from: g */
    private boolean f1141g = false;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.app.PurchaseActivity$a */
    private static class C0893a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1142a = InterfaceC0875a.a.m1072b();
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.app.PurchaseActivity$b */
    public static class DialogFragmentC0894b extends DialogFragment {

        /* JADX INFO: renamed from: a */
        private DialogInterface.OnClickListener f1143a;

        /* JADX INFO: renamed from: b */
        private DialogInterface.OnCancelListener f1144b;

        /* JADX INFO: renamed from: a */
        public void m1160a(DialogInterface.OnCancelListener onCancelListener) {
            this.f1144b = onCancelListener;
        }

        /* JADX INFO: renamed from: a */
        public void m1161a(DialogInterface.OnClickListener onClickListener) {
            this.f1143a = onClickListener;
        }

        @Override // android.app.DialogFragment, android.content.DialogInterface.OnCancelListener
        public void onCancel(DialogInterface dialogInterface) {
            this.f1144b.onCancel(dialogInterface);
        }

        @Override // android.app.DialogFragment
        public Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("SANDBOX MODE");
            builder.setMessage("Purchase is complete.");
            builder.setNegativeButton("OK", this.f1143a);
            return builder.create();
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.app.PurchaseActivity$c */
    private class DialogInterfaceOnCancelListenerC0895c implements DialogInterface.OnCancelListener, DialogInterface.OnClickListener {
        private DialogInterfaceOnCancelListenerC0895c() {
        }

        /* JADX INFO: renamed from: a */
        private void m1162a() {
            PurchaseActivity.this.onActivityResult(8213, C0893a.f1142a.mo1063q().mo1134e(), null);
        }

        @Override // android.content.DialogInterface.OnCancelListener
        public void onCancel(DialogInterface dialogInterface) {
            m1162a();
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            m1162a();
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.app.PurchaseActivity$d */
    public static class DialogFragmentC0896d extends DialogFragment {

        /* JADX INFO: renamed from: a */
        private String f1146a;

        /* JADX INFO: renamed from: b */
        private String f1147b;

        /* JADX INFO: renamed from: c */
        private int f1148c;

        /* JADX INFO: renamed from: d */
        private int f1149d;

        /* JADX INFO: renamed from: e */
        private String f1150e;

        /* JADX INFO: renamed from: f */
        private DialogInterface.OnClickListener f1151f;

        /* JADX INFO: renamed from: g */
        private DialogInterface.OnCancelListener f1152g;

        /* JADX INFO: renamed from: a */
        public void m1163a(DialogInterface.OnCancelListener onCancelListener) {
            this.f1152g = onCancelListener;
        }

        /* JADX INFO: renamed from: a */
        public void m1164a(DialogInterface.OnClickListener onClickListener) {
            this.f1151f = onClickListener;
        }

        /* JADX INFO: renamed from: a */
        public void m1165a(String str, String str2, int i, int i2, String str3) {
            this.f1146a = str;
            this.f1147b = str2;
            this.f1148c = i;
            this.f1149d = i2;
            this.f1150e = str3;
        }

        @Override // android.app.DialogFragment, android.content.DialogInterface.OnCancelListener
        public void onCancel(DialogInterface dialogInterface) {
            this.f1152g.onCancel(dialogInterface);
        }

        @Override // android.app.DialogFragment
        public Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("SANDBOX MODE");
            builder.setMessage(String.format("Do you want to buy \"%s [%s (+free:%s) %s]\"?\n\n%s", this.f1146a, String.valueOf(this.f1148c), String.valueOf(this.f1149d), this.f1150e, this.f1147b));
            builder.setPositiveButton("Yes", this.f1151f);
            builder.setNegativeButton("No", this.f1151f);
            return builder.create();
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.app.PurchaseActivity$e */
    private class DialogInterfaceOnCancelListenerC0897e implements DialogInterface.OnCancelListener, DialogInterface.OnClickListener {
        private DialogInterfaceOnCancelListenerC0897e() {
        }

        /* JADX INFO: renamed from: a */
        private void m1166a() {
            PurchaseActivity.this.onActivityResult(8213, C0893a.f1142a.mo1063q().mo1136g(), null);
        }

        @Override // android.content.DialogInterface.OnCancelListener
        public void onCancel(DialogInterface dialogInterface) {
            m1166a();
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -2) {
                m1166a();
                return;
            }
            if (i == -1) {
                DialogFragmentC0894b dialogFragmentC0894b = new DialogFragmentC0894b();
                dialogFragmentC0894b.m1161a((DialogInterface.OnClickListener) new DialogInterfaceOnCancelListenerC0895c());
                dialogFragmentC0894b.m1160a((DialogInterface.OnCancelListener) new DialogInterfaceOnCancelListenerC0895c());
                dialogFragmentC0894b.show(PurchaseActivity.this.getFragmentManager(), dialogFragmentC0894b.getTag());
            }
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.InterfaceC0883h.c
    /* JADX INFO: renamed from: a */
    public void mo1140a(int i, Intent intent) {
        if (!AbstractC0880e.m1127b()) {
            onActivityResult(8213, i, intent);
        } else {
            if (C0893a.f1142a.mo1063q().mo1131b(i)) {
                return;
            }
            onActivityResult(8213, i, null);
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e.a
    /* JADX INFO: renamed from: a */
    public void mo1137a(InterfaceC0883h interfaceC0883h, int i) {
        C0955e.m1391a(f1135a, "PurchaseActivity#onCompleteGetIBillingMarketService");
        if (C0893a.f1142a.mo1063q().mo1131b(i)) {
            interfaceC0883h.mo1085a(this, this.f1136b, this.f1137c, this.f1138d, 8213, this.f1139e, this.f1140f, this);
        } else {
            C0893a.f1142a.mo1063q().mo1133d();
            C0893a.f1142a.mo1049c().m1524e().mo1148a(8213, i, null);
        }
    }

    @Override // android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        C0955e.m1393b(f1135a, "onActivityResult requestCode : " + i);
        C0955e.m1391a(f1135a, "onActivityResult resultCode : " + i2);
        C0893a.f1142a.mo1063q().mo1133d();
        if (C0893a.f1142a.mo1049c().m1524e() != null) {
            C0893a.f1142a.mo1049c().m1524e().mo1148a(i, i2, intent);
            C0893a.f1142a.mo1049c().m1516a((C0998a.a) null);
        }
        finish();
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        InterfaceC0875a.a.m1071a(getApplication());
        int i = getIntent().getExtras().getInt("requestCode");
        C0955e.m1391a(f1135a, "onCreate requestCode : " + i);
        if (i != 8213) {
            finish();
            return;
        }
        this.f1136b = getIntent().getExtras().getString("sku");
        String string = getIntent().getExtras().getString(C0856j.f955a);
        this.f1137c = new BigDecimal(getIntent().getExtras().getString(FirebaseAnalytics.Param.PRICE));
        this.f1138d = getIntent().getExtras().getString("priceCode");
        String string2 = getIntent().getExtras().getString("displayPrice");
        int i2 = getIntent().getExtras().getInt("amount");
        int i3 = getIntent().getExtras().getInt("extraAmount");
        this.f1139e = getIntent().getExtras().getString("customAttribute");
        this.f1140f = getIntent().getExtras().getString("purchaseProductInfo");
        String string3 = getIntent().getExtras().getString("virtualCurrencyName");
        if (!C0893a.f1142a.mo1065s().m1335j()) {
            C0893a.f1142a.mo1063q().mo1130a(this);
            return;
        }
        DialogFragmentC0896d dialogFragmentC0896d = new DialogFragmentC0896d();
        dialogFragmentC0896d.m1165a(string, string2, i2, i3, string3);
        dialogFragmentC0896d.m1164a((DialogInterface.OnClickListener) new DialogInterfaceOnCancelListenerC0897e());
        dialogFragmentC0896d.m1163a((DialogInterface.OnCancelListener) new DialogInterfaceOnCancelListenerC0897e());
        dialogFragmentC0896d.show(getFragmentManager(), dialogFragmentC0896d.getTag());
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        C0955e.m1391a(f1135a, "onDestroy");
        if (C0893a.f1142a.mo1049c().m1524e() != null) {
            C0893a.f1142a.mo1049c().m1524e().mo1148a(8213, C0893a.f1142a.mo1063q().mo1136g(), null);
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        if (AbstractC0880e.m1128c()) {
            return;
        }
        if (this.f1141g) {
            finish();
        } else {
            this.f1141g = true;
        }
    }
}
