package com.udacity.project4

import android.app.Activity
import android.app.Application
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.udacity.project4.data.RemindDataSource
import com.udacity.project4.local.Database
import com.udacity.project4.local.RemindRepository
import com.udacity.project4.remindlist.RemindListViewModel
import com.udacity.project4.savereminder.SaveRemindViewModel
import com.udacity.project4.until.DataBindingIdlingResource
import com.udacity.project4.until.monitorActivity
import com.udacity.project4.until.EspResource
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get

@RunWith(AndroidJUnit4::class)
@LargeTest
class ReminderActivityTest :
    AutoCloseKoinTest() {
    private lateinit var repository: RemindDataSource
    private lateinit var appContext: Application

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun init() {
        stopKoin()
        appContext = getApplicationContext()
        val myModule = module {
            viewModel {
                RemindListViewModel(
                    appContext,
                    get() as RemindDataSource
                )
            }
            single {
                SaveRemindViewModel(
                    appContext,
                    get() as RemindDataSource
                )
            }
            single { RemindRepository(get()) as RemindDataSource }
            single { Database.createRemindersDao(appContext) }
        }

        startKoin {
            modules(listOf(myModule))
        }

        repository = get()


        runBlocking {
            repository.deleteAllReminders()
        }
    }




   @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

        @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }


    @Test
    fun saveReminderScreen_showSnackBarTitleError() {

        val activityScenario = ActivityScenario.launch(RemindActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        onView(withId(R.id.addReminderFAB)).perform(click())
        onView(withId(R.id.saveReminder)).perform(click())

        val snackBarMessage = appContext.getString(R.string.err_enter_title)
        onView(withText(snackBarMessage)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun saveReminderScreen_showSnackBarLocationError() {

        val activityScenario = ActivityScenario.launch(RemindActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        onView(withId(R.id.addReminderFAB)).perform(click())
        onView(withId(R.id.reminderTitle)).perform(typeText("Title"))
        closeSoftKeyboard()
        onView(withId(R.id.saveReminder)).perform(click())

        val snackBarMessage = appContext.getString(R.string.err_select_location)
        onView(withText(snackBarMessage)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun saveReminderScreen_showToastMessage() {

        val activityScenario = ActivityScenario.launch(RemindActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        onView(withId(R.id.addReminderFAB)).perform(click())
        onView(withId(R.id.reminderTitle)).perform(typeText("Title"))
        closeSoftKeyboard()
        onView(withId(R.id.reminderDescription)).perform(typeText("Description"))
        closeSoftKeyboard()

        onView(withId(R.id.selectLocation)).perform(click())
        onView(withId(R.id.mapFragment)).perform(longClick())
        onView(withId(R.id.save_button)).perform(click())

        onView(withId(R.id.saveReminder)).perform(click())

        onView(withText(R.string.reminder_saved)).inRoot(withDecorView(not(`is`(getActivity(activityScenario).window.decorView))))
            .check(matches(isDisplayed()))

        activityScenario.close()
    }

    private fun getActivity(activityScenario: ActivityScenario<RemindActivity>): Activity {
        lateinit var activity: Activity
        activityScenario.onActivity {
            activity = it
        }
        return activity
    }


}
