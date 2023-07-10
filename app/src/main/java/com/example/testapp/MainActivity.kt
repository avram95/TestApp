package com.example.testapp

import android.app.NotificationManager
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import com.example.testapp.db.AppDatabase
import com.example.testapp.db.BootInfo
import com.example.testapp.ui.theme.TestAppTheme
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private var db: AppDatabase? = null
    private val bootReceiver = BootReceiver {
        //move to repository, run async
        db!!.bootInfoDao().insertBootInfo(BootInfo(timestamp = System.currentTimeMillis()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = AppDatabase.getDatabase(this)
        setContent {
            TestAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    BootText(bootInfoFormat())
                }
            }
        }
        sendNotification()
    }

    private fun sendNotification() {
        val notification = NotificationCompat.Builder(this, "test_app")
            .setContentTitle("Test")
            .setContentText(System.currentTimeMillis().toString())
            .setAutoCancel(true)
            .build()

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)
    }

    private fun bootInfoFormat(): String {
        val strBuilder = StringBuilder()
        //move to repository, run async
        val bootList = db!!.bootInfoDao().getBootInfoList()
        bootList?.forEachIndexed { index, bootInfo ->
            strBuilder.append("$index. $bootInfo")
        }
        return strBuilder.toString()
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(
            bootReceiver, IntentFilter("android.intent.action.BOOT_COMPLETED")
        )
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(bootReceiver)
    }
}

@Composable
fun BootText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestAppTheme {
        BootText("Android")
    }
}