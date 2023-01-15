package com.udacity.project4

import android.app.Application
import com.udacity.project4.data.RemindDataSource
import com.udacity.project4.local.Database
import com.udacity.project4.local.RemindRepository
import com.udacity.project4.remindlist.RemindListViewModel
import com.udacity.project4.savereminder.SaveRemindViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApp : Application() {

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
            single { Database.createRemindersDao(this@MainApp) }
        }

        startKoin {
            androidContext(this@MainApp)
            modules(listOf(myModule))
        }
    }
}