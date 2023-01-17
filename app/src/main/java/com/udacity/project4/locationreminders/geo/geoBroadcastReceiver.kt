package com.udacity.project4.locationreminders.geo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.udacity.project4.locationreminders.geo.TransitionsService.Companion.enqueueWork


class geoBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        enqueueWork(context, intent)
    }
}
