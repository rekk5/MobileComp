package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.myapplication.ui.theme.MyApplicationTheme

private const val CHANNEL_ID = "defaultId"
class MainActivity : ComponentActivity() {
    private lateinit var notificationIntegration: NotificationIntegration
    private lateinit var sensorLight: SensorLight
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {

        }

        notificationIntegration = NotificationIntegration(this)
        notificationIntegration.createNotificationChannel(CHANNEL_ID)

        sensorLight = SensorLight(this)

        notificationIntegration.notification(CHANNEL_ID)

        setContent {
            MyApplicationTheme(darkTheme = false) {
                NavController(sensorLight)
            }
        }
    }
    override fun onStop() {
        super.onStop()
        notificationIntegration.notification(CHANNEL_ID)
        sensorLight.removeListener()
    }
}


