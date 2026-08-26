package android.support.v4.media.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.mediacompat.C0007R;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.view.MotionEventCompat;
import android.widget.RemoteViews;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class NotificationCompat {

    public static class DecoratedMediaCustomViewStyle extends MediaStyle {
        private void setBackgroundColor(RemoteViews remoteViews) {
            remoteViews.setInt(C0007R.id.status_bar_latest_event_content, "setBackgroundColor", this.mBuilder.getColor() != 0 ? this.mBuilder.getColor() : this.mBuilder.mContext.getResources().getColor(C0007R.color.notification_material_background_media_default_color));
        }

        @Override // android.support.v4.media.app.NotificationCompat.MediaStyle, android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                notificationBuilderWithBuilderAccessor.getBuilder().setStyle(fillInMediaStyle(new Notification.DecoratedMediaCustomViewStyle()));
            } else {
                super.apply(notificationBuilderWithBuilderAccessor);
            }
        }

        @Override // android.support.v4.media.app.NotificationCompat.MediaStyle
        int getBigContentViewLayoutResource(int i) {
            return i <= 3 ? C0007R.layout.notification_template_big_media_narrow_custom : C0007R.layout.notification_template_big_media_custom;
        }

        @Override // android.support.v4.media.app.NotificationCompat.MediaStyle
        int getContentViewLayoutResource() {
            return this.mBuilder.getContentView() != null ? C0007R.layout.notification_template_media_custom : super.getContentViewLayoutResource();
        }

        @Override // android.support.v4.media.app.NotificationCompat.MediaStyle, android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews bigContentView = this.mBuilder.getBigContentView() != null ? this.mBuilder.getBigContentView() : this.mBuilder.getContentView();
            if (bigContentView == null) {
                return null;
            }
            RemoteViews remoteViewsGenerateBigContentView = generateBigContentView();
            buildIntoRemoteViews(remoteViewsGenerateBigContentView, bigContentView);
            if (Build.VERSION.SDK_INT >= 21) {
                setBackgroundColor(remoteViewsGenerateBigContentView);
            }
            return remoteViewsGenerateBigContentView;
        }

        @Override // android.support.v4.media.app.NotificationCompat.MediaStyle, android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            boolean z = true;
            boolean z2 = this.mBuilder.getContentView() != null;
            if (Build.VERSION.SDK_INT >= 21) {
                if (!z2 && this.mBuilder.getBigContentView() == null) {
                    z = false;
                }
                if (z) {
                    RemoteViews remoteViewsGenerateContentView = generateContentView();
                    if (z2) {
                        buildIntoRemoteViews(remoteViewsGenerateContentView, this.mBuilder.getContentView());
                    }
                    setBackgroundColor(remoteViewsGenerateContentView);
                    return remoteViewsGenerateContentView;
                }
            } else {
                RemoteViews remoteViewsGenerateContentView2 = generateContentView();
                if (z2) {
                    buildIntoRemoteViews(remoteViewsGenerateContentView2, this.mBuilder.getContentView());
                    return remoteViewsGenerateContentView2;
                }
            }
            return null;
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews headsUpContentView = this.mBuilder.getHeadsUpContentView() != null ? this.mBuilder.getHeadsUpContentView() : this.mBuilder.getContentView();
            if (headsUpContentView == null) {
                return null;
            }
            RemoteViews remoteViewsGenerateBigContentView = generateBigContentView();
            buildIntoRemoteViews(remoteViewsGenerateBigContentView, headsUpContentView);
            if (Build.VERSION.SDK_INT >= 21) {
                setBackgroundColor(remoteViewsGenerateBigContentView);
            }
            return remoteViewsGenerateBigContentView;
        }
    }

    public static class MediaStyle extends android.support.v4.app.NotificationCompat.Style {
        private static final int MAX_MEDIA_BUTTONS = 5;
        private static final int MAX_MEDIA_BUTTONS_IN_COMPACT = 3;
        int[] mActionsToShowInCompact = null;
        PendingIntent mCancelButtonIntent;
        boolean mShowCancelButton;
        MediaSessionCompat.Token mToken;

        public MediaStyle() {
        }

        public MediaStyle(android.support.v4.app.NotificationCompat.Builder builder) {
            setBuilder(builder);
        }

        private RemoteViews generateMediaActionButton(android.support.v4.app.NotificationCompat.Action action) {
            boolean z = action.getActionIntent() == null;
            RemoteViews remoteViews = new RemoteViews(this.mBuilder.mContext.getPackageName(), C0007R.layout.notification_media_action);
            remoteViews.setImageViewResource(C0007R.id.action0, action.getIcon());
            if (!z) {
                remoteViews.setOnClickPendingIntent(C0007R.id.action0, action.getActionIntent());
            }
            if (Build.VERSION.SDK_INT >= 15) {
                remoteViews.setContentDescription(C0007R.id.action0, action.getTitle());
            }
            return remoteViews;
        }

        public static MediaSessionCompat.Token getMediaSession(Notification notification) {
            Bundle extras = android.support.v4.app.NotificationCompat.getExtras(notification);
            if (extras == null) {
                return null;
            }
            if (Build.VERSION.SDK_INT >= 21) {
                Parcelable parcelable = extras.getParcelable(android.support.v4.app.NotificationCompat.EXTRA_MEDIA_SESSION);
                if (parcelable != null) {
                    return MediaSessionCompat.Token.fromToken(parcelable);
                }
                return null;
            }
            IBinder binder = BundleCompat.getBinder(extras, android.support.v4.app.NotificationCompat.EXTRA_MEDIA_SESSION);
            if (binder == null) {
                return null;
            }
            Parcel parcelObtain = Parcel.obtain();
            parcelObtain.writeStrongBinder(binder);
            parcelObtain.setDataPosition(0);
            MediaSessionCompat.Token tokenCreateFromParcel = MediaSessionCompat.Token.CREATOR.createFromParcel(parcelObtain);
            parcelObtain.recycle();
            return tokenCreateFromParcel;
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 21) {
                notificationBuilderWithBuilderAccessor.getBuilder().setStyle(fillInMediaStyle(new Notification.MediaStyle()));
            } else if (this.mShowCancelButton) {
                notificationBuilderWithBuilderAccessor.getBuilder().setOngoing(true);
            }
        }

        @RequiresApi(MotionEventCompat.AXIS_WHEEL)
        Notification.MediaStyle fillInMediaStyle(Notification.MediaStyle mediaStyle) {
            int[] iArr = this.mActionsToShowInCompact;
            if (iArr != null) {
                mediaStyle.setShowActionsInCompactView(iArr);
            }
            MediaSessionCompat.Token token = this.mToken;
            if (token != null) {
                mediaStyle.setMediaSession((MediaSession.Token) token.getToken());
            }
            return mediaStyle;
        }

        RemoteViews generateBigContentView() {
            int iMin = Math.min(this.mBuilder.mActions.size(), 5);
            RemoteViews remoteViewsApplyStandardTemplate = applyStandardTemplate(false, getBigContentViewLayoutResource(iMin), false);
            remoteViewsApplyStandardTemplate.removeAllViews(C0007R.id.media_actions);
            if (iMin > 0) {
                for (int i = 0; i < iMin; i++) {
                    remoteViewsApplyStandardTemplate.addView(C0007R.id.media_actions, generateMediaActionButton(this.mBuilder.mActions.get(i)));
                }
            }
            if (this.mShowCancelButton) {
                remoteViewsApplyStandardTemplate.setViewVisibility(C0007R.id.cancel_action, 0);
                remoteViewsApplyStandardTemplate.setInt(C0007R.id.cancel_action, "setAlpha", this.mBuilder.mContext.getResources().getInteger(C0007R.integer.cancel_button_image_alpha));
                remoteViewsApplyStandardTemplate.setOnClickPendingIntent(C0007R.id.cancel_action, this.mCancelButtonIntent);
            } else {
                remoteViewsApplyStandardTemplate.setViewVisibility(C0007R.id.cancel_action, 8);
            }
            return remoteViewsApplyStandardTemplate;
        }

        RemoteViews generateContentView() {
            RemoteViews remoteViewsApplyStandardTemplate = applyStandardTemplate(false, getContentViewLayoutResource(), true);
            int size = this.mBuilder.mActions.size();
            int[] iArr = this.mActionsToShowInCompact;
            int iMin = iArr == null ? 0 : Math.min(iArr.length, 3);
            remoteViewsApplyStandardTemplate.removeAllViews(C0007R.id.media_actions);
            if (iMin > 0) {
                for (int i = 0; i < iMin; i++) {
                    if (i >= size) {
                        throw new IllegalArgumentException(String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", Integer.valueOf(i), Integer.valueOf(size - 1)));
                    }
                    remoteViewsApplyStandardTemplate.addView(C0007R.id.media_actions, generateMediaActionButton(this.mBuilder.mActions.get(this.mActionsToShowInCompact[i])));
                }
            }
            if (this.mShowCancelButton) {
                remoteViewsApplyStandardTemplate.setViewVisibility(C0007R.id.end_padder, 8);
                remoteViewsApplyStandardTemplate.setViewVisibility(C0007R.id.cancel_action, 0);
                remoteViewsApplyStandardTemplate.setOnClickPendingIntent(C0007R.id.cancel_action, this.mCancelButtonIntent);
                remoteViewsApplyStandardTemplate.setInt(C0007R.id.cancel_action, "setAlpha", this.mBuilder.mContext.getResources().getInteger(C0007R.integer.cancel_button_image_alpha));
            } else {
                remoteViewsApplyStandardTemplate.setViewVisibility(C0007R.id.end_padder, 0);
                remoteViewsApplyStandardTemplate.setViewVisibility(C0007R.id.cancel_action, 8);
            }
            return remoteViewsApplyStandardTemplate;
        }

        int getBigContentViewLayoutResource(int i) {
            return i <= 3 ? C0007R.layout.notification_template_big_media_narrow : C0007R.layout.notification_template_big_media;
        }

        int getContentViewLayoutResource() {
            return C0007R.layout.notification_template_media;
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 21) {
                return null;
            }
            return generateBigContentView();
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 21) {
                return null;
            }
            return generateContentView();
        }

        public MediaStyle setCancelButtonIntent(PendingIntent pendingIntent) {
            this.mCancelButtonIntent = pendingIntent;
            return this;
        }

        public MediaStyle setMediaSession(MediaSessionCompat.Token token) {
            this.mToken = token;
            return this;
        }

        public MediaStyle setShowActionsInCompactView(int... iArr) {
            this.mActionsToShowInCompact = iArr;
            return this;
        }

        public MediaStyle setShowCancelButton(boolean z) {
            if (Build.VERSION.SDK_INT < 21) {
                this.mShowCancelButton = z;
            }
            return this;
        }
    }

    private NotificationCompat() {
    }
}
