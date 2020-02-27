package com.example.notifyme

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
//import sun.jvm.hotspot.utilities.IntArray


class MainActivity : AppCompatActivity() {

    private val NOTIFICATION_ID = 0

    private var mNotifyManager: NotificationManager? = null
    private val PRIMARY_CHANNEL_ID = "primary_notification_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()
    }

    private fun getNotificationBuilder(): NotificationCompat.Builder? {

        val notifyBuilder =
            NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)


        return NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
            .setContentTitle("Hello Notification Title")
            .setContentText("Hello Notification Content")
            .setSmallIcon(R.drawable.ic_money)



    }


    fun onClick(view: View) {
        sendNotification()
    }

    private fun sendNotification() {

        val notifyBuilder = getNotificationBuilder()
        mNotifyManager!!.notify(NOTIFICATION_ID, notifyBuilder!!.build())

    }

    fun createNotificationChannel() {
        mNotifyManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        // Condition to Check API level  (must be over API 26)
        if (android.os.Build.VERSION.SDK_INT >=
            android.os.Build.VERSION_CODES.O
        ) {
            // Create a NotificationChannel
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Mascot Notification", NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Notification from Mascot"
            mNotifyManager!!.createNotificationChannel(notificationChannel)


        }
    }
}
