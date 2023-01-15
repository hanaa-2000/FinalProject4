package com.udacity.project4.data


interface RemindDataSource {
    suspend fun getReminders(): Result<List<RemindData>>
    suspend fun saveReminder(reminder: RemindData)
    suspend fun getReminder(id: String): Result<RemindData>
    suspend fun deleteAllReminders()
}