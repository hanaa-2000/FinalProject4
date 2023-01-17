package com.udacity.project4.remindlist

import android.app.Application
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.R
import com.udacity.project4.data.FakeDataSource
import com.udacity.project4.locationreminders.MainCoroutine
import com.udacity.project4.locationreminders.getOrAwaitValue
import com.udacity.project4.remindlist.RemindDataItem
import com.udacity.project4.savereminder.SaveRemindViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class SaveReminderViewModelTest {


    //TODO: provide testing to the SaveReminderView and its live data objects

    private lateinit var saveReminderViewModel: SaveRemindViewModel

    private lateinit var fakeDataSource: FakeDataSource

    private lateinit var appContext: Application

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = MainCoroutine()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() = runBlockingTest {
        fakeDataSource = FakeDataSource()

        appContext = getApplicationContext()
        saveReminderViewModel = SaveRemindViewModel(
            appContext,
            fakeDataSource
        )
    }

    @Test
    fun checkLoading_test() {
        mainCoroutineRule.pauseDispatcher()
        val reminder1 = RemindDataItem("Title1", "Description1", "location1", 32.1, 32.1)
        saveReminderViewModel.saveReminder(reminder1)

        assertThat(saveReminderViewModel.showLoading.getOrAwaitValue(), `is`(true))

        mainCoroutineRule.resumeDispatcher()

        assertThat(saveReminderViewModel.showLoading.getOrAwaitValue(), `is`(false))
        assertThat(
            saveReminderViewModel.showToast.getOrAwaitValue(),
            `is`(appContext.getString(R.string.reminder_saved))
        )
    }

    @Test
    fun shouldReturnErrorSnackBar_emptyTitle() {
        saveReminderViewModel =
            SaveRemindViewModel(appContext, fakeDataSource)
        val reminder1 = RemindDataItem(null, null, null, 32.1, 32.1)
        saveReminderViewModel.validateEnteredData(reminder1)

        assertThat(
            saveReminderViewModel.showSnackBarInt.getOrAwaitValue(),
            `is`(R.string.err_enter_title)
        )
    }

    @Test
    fun shouldReturnErrorSnackBar_emptylocation() {
        saveReminderViewModel =
            SaveRemindViewModel(appContext, fakeDataSource)
        val reminder1 = RemindDataItem("Title", null, null, 32.1, 32.1)
        saveReminderViewModel.validateEnteredData(reminder1)

        assertThat(
            saveReminderViewModel.showSnackBarInt.getOrAwaitValue(),
            `is`(R.string.err_select_location)
        )

    }
}