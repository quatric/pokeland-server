package com.metaps.analytics.assist;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class AppSpotConfig {

    /* JADX INFO: renamed from: a */
    private boolean f426a = false;

    /* JADX INFO: renamed from: b */
    private int f427b = 53;

    /* JADX INFO: renamed from: c */
    private int f428c = 1;

    /* JADX INFO: renamed from: d */
    private int f429d = 0;

    /* JADX INFO: renamed from: e */
    private int f430e = 80;

    /* JADX INFO: renamed from: f */
    private boolean f431f = false;

    /* JADX INFO: renamed from: a */
    protected int m665a() {
        return this.f427b;
    }

    /* JADX INFO: renamed from: b */
    protected int m666b() {
        return this.f428c;
    }

    /* JADX INFO: renamed from: c */
    protected int m667c() {
        return this.f429d;
    }

    /* JADX INFO: renamed from: d */
    protected int m668d() {
        return this.f430e;
    }

    /* JADX INFO: renamed from: e */
    protected boolean m669e() {
        return this.f431f;
    }

    public boolean isClickHandledManually() {
        return this.f426a;
    }

    public void setBannerFitScreenWidth(boolean z) {
        this.f431f = z;
    }

    public void setBannerPosition(int i) {
        this.f430e = i;
    }

    public void setClickHandledManually(boolean z) {
        this.f426a = z;
    }

    public void setIconCount(int i) {
        this.f428c = i;
    }

    public void setIconOrientation(int i) {
        this.f429d = i;
    }

    public void setIconPosition(int i) {
        this.f427b = i;
    }
}
