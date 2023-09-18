package com.asadbek.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_HIGH
import com.asadbek.notification.databinding.ActivityMainBinding

const val ID = 1
const val CHANNEL_ID = "CHANNEL_ID"
class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var notificationManager: NotificationManager
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        binding.btn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
            // FLAG_MUTTABLE bo`lsa ishlaydi agar o`xshamasa FLAG_UNMUTABLE yoki CURRENT UPDATE dan foydalanish kerak
            val pendingIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_MUTABLE)
            val notificationBuilder = NotificationCompat.Builder(this)
                .setChannelId(CHANNEL_ID) // channel id - kanal id nomi
                .setAutoCancel(false) // avto bekor qilish
                .setSmallIcon(R.drawable.ic_launcher_foreground) // notification ikonkasi
                .setWhen(System.currentTimeMillis()) // notif vaqti
                .setContentIntent(pendingIntent) // pending intenti
                .setContentTitle("Notification") // notif nomi
                .setContentText("This is notification") // notif xabari
                .setPriority(PRIORITY_HIGH) // kichik versiyalar uchun

            createChannelIfNedded(notificationManager) // channel ni yaratish uchun funktsiya agar mavjud bo`lmasa yaratadi
            notificationManager.notify(ID,notificationBuilder.build()) // notif chiqarish


        }


    }

    private fun createChannelIfNedded(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_ID,NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}