package com.punch.navdisplay

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class PunchNavService : NotificationListenerService() {

    companion object {
        var isRunning = false
        var lastLine1 = ""
        var lastLine2 = ""
        var onDataUpdated: ((String, String) -> Unit)? = null
        var instance: PunchNavService? = null
    }

    private var publisher: AvrcpPublisher? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        publisher = AvrcpPublisher(this)
        isRunning = true
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        if (sbn == null) return

        // Intercept Google Maps navigation notifications
        if (sbn.packageName == "com.google.android.apps.maps") {
            val extras = sbn.notification.extras
            val title = extras.getString(Notification.EXTRA_TITLE) ?: return
            val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: ""

            // Read preferences
            val prefs = getSharedPreferences("punch_nav_prefs", Context.MODE_PRIVATE)
            val useAscii = prefs.getBoolean("use_ascii", false)

            val navInfo = NavParser.parse(title, text, useAscii)

            lastLine1 = navInfo.line1
            lastLine2 = navInfo.line2

            publisher?.publish(navInfo.line1, navInfo.line2)
            onDataUpdated?.invoke(navInfo.line1, navInfo.line2)
        }
    }

    fun sendTestPreview(useAscii: Boolean) {
        val navInfo = NavParser.parse("In 200m Turn right", "MG Road • 18m left", useAscii)
        lastLine1 = navInfo.line1
        lastLine2 = navInfo.line2
        publisher?.publish(navInfo.line1, navInfo.line2)
        onDataUpdated?.invoke(navInfo.line1, navInfo.line2)
    }

    override fun onDestroy() {
        isRunning = false
        instance = null
        publisher?.release()
        super.onDestroy()
    }
}
