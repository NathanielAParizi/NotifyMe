package com.example.notifyme

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mReceiver: NotificationReceiver = NotificationReceiver()
    private val ACTION_UPDATE_NOTIFICATION =
        "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION"

    private val NOTIFICATION_ID = 0

    private var mNotifyManager: NotificationManager? = null
    private val PRIMARY_CHANNEL_ID = "primary_notification_channel"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()
        setNotificationButtonState(true, false, false);
        registerReceiver(mReceiver, IntentFilter(ACTION_UPDATE_NOTIFICATION));

    }

    private fun getNotificationBuilder(): NotificationCompat.Builder? {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val notificationPendingIntent = PendingIntent.getActivity(
            this,
            NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notifyBuilder =
            NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)



        return NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
            .setContentTitle("Hello Notification Title")
            .setContentText("Hello Notification Content")
            .setSmallIcon(R.drawable.ic_android)
            .setContentIntent(notificationPendingIntent)
            .setAutoCancel(true)


    }


    fun onClick(view: View) {
        when (view.id) {
            R.id.notifyBtn -> {
                sendNotification()
            }
            R.id.cancelBtn -> {
                cancelNotification()
            }
            R.id.updateBtn -> {
                updateNotification()
            }

        }
    }

    private fun updateNotification() {

        val androidImage = BitmapFactory.decodeResource(resources, R.drawable.mascot_1)
        val notifyBuilder = getNotificationBuilder()
        notifyBuilder!!.setStyle(
            NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle("Notification Updated!")
        )


        mNotifyManager?.notify(NOTIFICATION_ID, notifyBuilder.build());

        setNotificationButtonState(false, false, true);

    }

    private fun cancelNotification() {
        mNotifyManager?.cancel(NOTIFICATION_ID)

        setNotificationButtonState(true, false, false);

    }

    private fun sendNotification() {


        val notifyBuilder = getNotificationBuilder()
        mNotifyManager!!.notify(NOTIFICATION_ID, notifyBuilder!!.build())
        setNotificationButtonState(false, true, true);

        val updateIntent = Intent(ACTION_UPDATE_NOTIFICATION)
        val updatePendingIntent = PendingIntent.getBroadcast(
            this,
            NOTIFICATION_ID,
            updateIntent,
            PendingIntent.FLAG_ONE_SHOT
        )
        notifyBuilder.addAction(R.drawable.ic_update, "Update Notification", updatePendingIntent);
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

    fun setNotificationButtonState(
        isNotifyEnabled: Boolean?,
        isUpdateEnabled: Boolean?,
        isCancelEnabled: Boolean?
    ) {
        notifyBtn.setEnabled(isNotifyEnabled!!)
        updateBtn.setEnabled(isUpdateEnabled!!)
        cancelBtn.setEnabled(isCancelEnabled!!)

    }

    override fun onDestroy() {
        unregisterReceiver(mReceiver)
        super.onDestroy()
    }

}


//Broadcast Receiver that will call updateNotification()
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent
    ) { // Update the notification
    }


}