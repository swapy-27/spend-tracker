package com.example.spendtracker.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ&\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/example/spendtracker/util/NotificationUtils;", "", "()V", "CHANNEL_DESC", "", "CHANNEL_ID", "CHANNEL_NAME", "createNotificationChannel", "", "context", "Landroid/content/Context;", "showThresholdNotification", "title", "message", "notificationId", "", "app_debug"})
public final class NotificationUtils {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_ID = "spend_alerts_channel";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_NAME = "Spend Alerts";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_DESC = "Notifications when spending exceeds thresholds";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.spendtracker.util.NotificationUtils INSTANCE = null;
    
    private NotificationUtils() {
        super();
    }
    
    public final void createNotificationChannel(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void showThresholdNotification(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String message, int notificationId) {
    }
}