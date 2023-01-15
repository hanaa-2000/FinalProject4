package com.udacity.project4.local

import android.content.Context
import androidx.room.Room



object Database {


    fun createRemindersDao(context: Context): RemindDao {
        return Room.databaseBuilder(
            context.applicationContext,
            ReminderDatabase::class.java, "locationReminders.db"
        ).build().reminderDao()
    }

}