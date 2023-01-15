package com.udacity.project4.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.udacity.project4.data.RemindData


@Database(entities = [RemindData::class], version = 1, exportSchema = false)
abstract class ReminderDatabase : RoomDatabase() {

    abstract fun reminderDao(): RemindDao
}