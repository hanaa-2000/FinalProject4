package com.udacity.project4.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.project4.data.RemindData


@Dao
interface RemindDao {

    @Query("SELECT * FROM reminders")
    suspend fun getReminders(): List<RemindData>


    @Query("SELECT * FROM reminders where entry_id = :reminderId")
    suspend fun getReminderById(reminderId: String): RemindData?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReminder(reminder: RemindData)


    @Query("DELETE FROM reminders")
    suspend fun deleteAllReminders()

}