package com.udacity.project4.remindlist

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.project4.basic.BasicViewModel
import com.udacity.project4.data.RemindDataSource
import com.udacity.project4.data.RemindData
import com.udacity.project4.data.Result
import kotlinx.coroutines.launch

class RemindListViewModel(
    app: Application,
    private val dataSource: RemindDataSource
) : BasicViewModel(app) {
    val remindersList = MutableLiveData<List<RemindDataItem>>()


    fun loadReminders() {
        showLoading.value = true
        viewModelScope.launch {
            val result = dataSource.getReminders()
            showLoading.postValue(false)
            when (result) {
                is com.udacity.project4.data.Result.Success<*> -> {
                    val dataList = ArrayList<RemindDataItem>()
                    dataList.addAll((result.data as List<RemindData>).map { reminder ->
                        RemindDataItem(
                            reminder.title,
                            reminder.description,
                            reminder.location,
                            reminder.latitude,
                            reminder.longitude,
                            reminder.id
                        )
                    })
                    remindersList.value = dataList
                }
                is com.udacity.project4.data.Result.Error ->
                    showSnackBar.value = result.message
            }

            invalidateShowNoData()
        }
    }


    private fun invalidateShowNoData() {
        showNoData.value = remindersList.value == null || remindersList.value!!.isEmpty()
    }
}