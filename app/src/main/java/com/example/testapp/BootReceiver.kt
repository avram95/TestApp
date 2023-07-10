package com.example.testapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BootReceiver( val onBootReceived: () -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "onReceive", Toast.LENGTH_SHORT).show()
        onBootReceived()
        //Do what you need to execute on boot here.
    }
}