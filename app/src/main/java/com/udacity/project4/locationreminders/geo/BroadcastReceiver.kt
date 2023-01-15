package com.udacity.project4.locationreminders.geo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.udacity.project4.savereminder.SaveReminderFragment.Companion.ACTION_GEOFENCE_EVENT



class BroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == ACTION_GEOFENCE_EVENT) {
            Log.i("GeofenceReceiver", "Geofence event received")
            TransitionService.enqueueWork(context, intent)
        }

    }
}