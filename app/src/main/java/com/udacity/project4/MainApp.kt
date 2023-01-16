package com.udacity.project4

import android.app.Application
import com.udacity.project4.data.RemindDataSource
import com.udacity.project4.local.LocalDatabase
import com.udacity.project4.local.RemindRepository
import com.udacity.project4.remindlist.RemindListViewModel
import com.udacity.project4.savereminder.SaveRemindViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()


        val myModule = module {
            viewModel {
                RemindListViewModel(
                    get(),
                    get() as RemindDataSource
                )
            }
            single {
                SaveRemindViewModel(
                    get(),
                    get() as RemindDataSource
                )
            }
            single { RemindRepository(get()) as RemindDataSource }
            single { LocalDatabase.createRemindersDao(this@MyApp) }
        }

        startKoin {
            androidContext(this@MyApp)
            modules(listOf(myModule))
        }
    }
}