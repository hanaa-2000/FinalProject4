package com.udacity.project4.data

import com.udacity.project4.data.RemindDataSource
import com.udacity.project4.data.RemindData
import com.udacity.project4.data.Result

//Use FakeDataSource that acts as a test double to the LocalDataSource

class FakeDataSource(var tasks: MutableList<RemindData>? = mutableListOf()) : RemindDataSource {

//    DONE: Create a fake data source to act as a double to the real data source

    override suspend fun getReminders(): Result<List<RemindData>> {

        tasks?.let { return Result.Success(it) }
        return Result.Error("No reminders found")
    }

    override suspend fun saveReminder(reminder: RemindData){
        tasks?.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<RemindData> {
        tasks?.firstOrNull { it.id == id }?.let { return Result.Success(it) }
        return Result.Error("Reminder not found")
    }

    override suspend fun deleteAllReminders() {
        tasks = mutableListOf()
    }


}