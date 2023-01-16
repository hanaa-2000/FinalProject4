package com.udacity.project4.data

import com.udacity.project4.data.RemindData
import com.udacity.project4.data.Result
import com.udacity.project4.remindlist.RemindDataItem
import net.bytebuddy.implementation.bytecode.Throw


class FakeDataSource(var remindersList: MutableList<RemindData>? = mutableListOf()) : RemindDataSource {


    private var shouldReturnError = false


    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getReminders(): Result<List<RemindData>> {
        if (shouldReturnError) {
            return Result.Error(
                "Error getting reminders"
            )
        }
        remindersList?.let { return Result.Success(it) }
        return Result.Error("Reminders not found")
    }

    override suspend fun saveReminder(reminder: RemindData) {
        remindersList?.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<RemindData> {
        val reminder = remindersList?.find { reminderDTO ->
            reminderDTO.id == id
        }

        return when {
            shouldReturnError -> {
                Result.Error("Reminder not found!")
            }

            reminder != null -> {
                Result.Success(reminder)
            }
            else -> {
                Result.Error("Reminder not found!")
            }
        }
    }

    override suspend fun deleteAllReminders() {
        remindersList?.clear()
    }

}