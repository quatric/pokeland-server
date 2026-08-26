package android.support.v4.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class SwipeProgressBar {
    private static final int ANIMATION_DURATION_MS = 2000;
    private static final int COLOR1 = -1291845632;
    private static final int COLOR2 = Integer.MIN_VALUE;
    private static final int COLOR3 = 1291845632;
    private static final int COLOR4 = 436207616;
    private static final int FINISH_ANIMATION_DURATION_MS = 1000;
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private long mFinishTime;
    private View mParent;
    private boolean mRunning;
    private long mStartTime;
    private float mTriggerPercentage;
    private final Paint mPaint = new Paint();
    private final RectF mClipRect = new RectF();
    private Rect mBounds = new Rect();
    private int mColor1 = COLOR1;
    private int mColor2 = Integer.MIN_VALUE;
    private int mColor3 = COLOR3;
    private int mColor4 = COLOR4;

    SwipeProgressBar(View view) {
        this.mParent = view;
    }

    private void drawCircle(Canvas canvas, float f, float f2, int i, float f3) {
        this.mPaint.setColor(i);
        canvas.save();
        canvas.translate(f, f2);
        float interpolation = INTERPOLATOR.getInterpolation(f3);
        canvas.scale(interpolation, interpolation);
        canvas.drawCircle(0.0f, 0.0f, f, this.mPaint);
        canvas.restore();
    }

    private void drawTrigger(Canvas canvas, int i, int i2) {
        this.mPaint.setColor(this.mColor1);
        float f = i;
        canvas.drawCircle(f, i2, this.mTriggerPercentage * f, this.mPaint);
    }

    void draw(Canvas canvas) {
        int iWidth = this.mBounds.width();
        int iHeight = this.mBounds.height();
        int i = iWidth / 2;
        int i2 = iHeight / 2;
        int iSave = canvas.save();
        canvas.clipRect(this.mBounds);
        if (this.mRunning || this.mFinishTime > 0) {
            long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            long j = this.mStartTime;
            long j2 = (jCurrentAnimationTimeMillis - j) % 2000;
            long j3 = (jCurrentAnimationTimeMillis - j) / 2000;
            float f = j2 / 20.0f;
            boolean z = false;
            if (!this.mRunning) {
                long j4 = this.mFinishTime;
                if (jCurrentAnimationTimeMillis - j4 >= 1000) {
                    this.mFinishTime = 0L;
                    return;
                }
                float f2 = (((jCurrentAnimationTimeMillis - j4) % 1000) / 10.0f) / 100.0f;
                float f3 = i;
                float interpolation = INTERPOLATOR.getInterpolation(f2) * f3;
                this.mClipRect.set(f3 - interpolation, 0.0f, f3 + interpolation, iHeight);
                canvas.saveLayerAlpha(this.mClipRect, 0, 0);
                z = true;
            }
            if (j3 == 0) {
                canvas.drawColor(this.mColor1);
            } else if (f >= 0.0f && f < 25.0f) {
                canvas.drawColor(this.mColor4);
            } else if (f >= 25.0f && f < 50.0f) {
                canvas.drawColor(this.mColor1);
            } else if (f < 50.0f || f >= 75.0f) {
                canvas.drawColor(this.mColor3);
            } else {
                canvas.drawColor(this.mColor2);
            }
            if (f >= 0.0f && f <= 25.0f) {
                drawCircle(canvas, i, i2, this.mColor1, ((f + 25.0f) * 2.0f) / 100.0f);
            }
            if (f >= 0.0f && f <= 50.0f) {
                drawCircle(canvas, i, i2, this.mColor2, (f * 2.0f) / 100.0f);
            }
            if (f >= 25.0f && f <= 75.0f) {
                drawCircle(canvas, i, i2, this.mColor3, ((f - 25.0f) * 2.0f) / 100.0f);
            }
            if (f >= 50.0f && f <= 100.0f) {
                drawCircle(canvas, i, i2, this.mColor4, ((f - 50.0f) * 2.0f) / 100.0f);
            }
            if (f >= 75.0f && f <= 100.0f) {
                drawCircle(canvas, i, i2, this.mColor1, ((f - 75.0f) * 2.0f) / 100.0f);
            }
            if (this.mTriggerPercentage > 0.0f && z) {
                canvas.restoreToCount(iSave);
                int iSave2 = canvas.save();
                canvas.clipRect(this.mBounds);
                drawTrigger(canvas, i, i2);
                iSave = iSave2;
            }
            ViewCompat.postInvalidateOnAnimation(this.mParent, this.mBounds.left, this.mBounds.top, this.mBounds.right, this.mBounds.bottom);
        } else {
            float f4 = this.mTriggerPercentage;
            if (f4 > 0.0f && f4 <= 1.0d) {
                drawTrigger(canvas, i, i2);
            }
        }
        canvas.restoreToCount(iSave);
    }

    boolean isRunning() {
        return this.mRunning || this.mFinishTime > 0;
    }

    void setBounds(int i, int i2, int i3, int i4) {
        Rect rect = this.mBounds;
        rect.left = i;
        rect.top = i2;
        rect.right = i3;
        rect.bottom = i4;
    }

    void setColorScheme(int i, int i2, int i3, int i4) {
        this.mColor1 = i;
        this.mColor2 = i2;
        this.mColor3 = i3;
        this.mColor4 = i4;
    }

    void setTriggerPercentage(float f) {
        this.mTriggerPercentage = f;
        this.mStartTime = 0L;
        ViewCompat.postInvalidateOnAnimation(this.mParent, this.mBounds.left, this.mBounds.top, this.mBounds.right, this.mBounds.bottom);
    }

    void start() {
        if (this.mRunning) {
            return;
        }
        this.mTriggerPercentage = 0.0f;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mRunning = true;
        this.mParent.postInvalidate();
    }

    void stop() {
        if (this.mRunning) {
            this.mTriggerPercentage = 0.0f;
            this.mFinishTime = AnimationUtils.currentAnimationTimeMillis();
            this.mRunning = false;
            this.mParent.postInvalidate();
        }
    }
}
