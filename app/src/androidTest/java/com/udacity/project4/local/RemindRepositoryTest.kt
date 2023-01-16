package com.udacity.project4.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.data.RemindData
import com.udacity.project4.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)

@MediumTest
class RemindersLocalRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var remindersLocalRepository: RemindRepository

    private lateinit var database:ReminderDatabase

    @Before
    fun setup() {

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ReminderDatabase::class.java
        ).allowMainThreadQueries().build()

        remindersLocalRepository = RemindRepository(
            database.reminderDao(),
            Dispatchers.Main
        )
    }

    @After
    fun cleanUp() {
        database.close()
    }


    @Test
    fun saveReminder_retrieveReminderById() = runBlocking {
        val reminder = RemindData("My Shop", "Get to the Shop", "Abuja", 6.54545, 7.54545)
        remindersLocalRepository.saveReminder(reminder)

        val result = remindersLocalRepository.getReminder(reminder.id) as? Result.Success

        assertThat(result is Result.Success, `is`(true))
        result as Result.Success


        assertThat(result.data.title, `is`(reminder.title))
        assertThat(result.data.description, `is`(reminder.description))
        assertThat(result.data.latitude, `is`(reminder.latitude))
        assertThat(result.data.longitude, `is`(reminder.longitude))
        assertThat(result.data.location, `is`(reminder.location))
    }


    @Test
    fun deleteReminders_EmptyList()= runBlocking {
        val reminder = RemindData("My Shop", "Get to the Shop", "Abuja", 6.54545, 7.54545)
        remindersLocalRepository.saveReminder(reminder)

        remindersLocalRepository.deleteAllReminders()

        val result = remindersLocalRepository.getReminders()

        assertThat(result is Result.Success, `is`(true))
        result as Result.Success

        assertThat(result.data, `is` (emptyList()))
    }

    @Test
    fun retrieveReminderById_ReturnError() = runBlocking {
        val reminder = RemindData("My Shop", "Get to the Shop", "Abuja", 6.54545, 7.54545)
        remindersLocalRepository.saveReminder(reminder)

        remindersLocalRepository.deleteAllReminders()

        val result = remindersLocalRepository.getReminder(reminder.id)

        assertThat(result is Result.Error, `is`(true))
        result as Result.Error
        assertThat(result.message, `is`("Reminder not found!"))
    }

}