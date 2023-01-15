package com.udacity.project4.local

import com.udacity.project4.data.RemindDataSource
import com.udacity.project4.data.RemindData
import com.udacity.project4.data.Result
import com.udacity.project4.until.EspResource.wrapEspressoIdlingResource
import kotlinx.coroutines.*


class RemindRepository(
    private val remindersDao: RemindDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RemindDataSource {


    override suspend fun getReminders(): Result<List<RemindData>> = withContext(ioDispatcher) {
        wrapEspressoIdlingResource{
            return@withContext try {
                Result.Success(remindersDao.getReminders())
            } catch (ex: Exception) {
                Result.Error(ex.localizedMessage)
            }
        }

    }


    override suspend fun saveReminder(reminder: RemindData) =
        withContext(ioDispatcher) {
            wrapEspressoIdlingResource {
                remindersDao.saveReminder(reminder)
            }

        }


    override suspend fun getReminder(id: String): Result<RemindData> = withContext(ioDispatcher) {
        wrapEspressoIdlingResource {
            try {
                val reminder = remindersDao.getReminderById(id)
                if (reminder != null) {
                    return@withContext Result.Success(reminder)
                } else {
                    return@withContext Result.Error("Reminder not found!")
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e.localizedMessage)
            }
        }

    }

    override suspend fun deleteAllReminders() {
        wrapEspressoIdlingResource {
            withContext(ioDispatcher) {
                remindersDao.deleteAllReminders()
            }
        }

    }
}
