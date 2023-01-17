package com.udacity.project4.data

import com.udacity.project4.data.RemindData
import com.udacity.project4.data.Result

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource(var reminders: MutableList<RemindData>? = mutableListOf()) :
    RemindDataSource {

    override suspend fun getReminders(): Result<List<RemindData>> {
        reminders?.let { return Result.Success(ArrayList(it)) }
        return Result.Error(
            "reminders not found"
        )
    }

    override suspend fun saveReminder(reminder: RemindData) {
        reminders?.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<RemindData> {
        reminders?.firstOrNull { it.id == id }?.let { return Result.Success(it) }
        return Result.Error(
            "reminder not found"
        )
    }

    override suspend fun deleteAllReminders() {
        reminders?.clear()
    }
}