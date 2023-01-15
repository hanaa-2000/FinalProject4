package com.udacity.project4.basic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.udacity.project4.until.SingleLive


abstract class BasicViewModel(app: Application) : AndroidViewModel(app) {

    val navigationCommand: SingleLive<Navigation> = SingleLive()
    val showErrorMessage: SingleLive<String> = SingleLive()
    val showSnackBar: SingleLive<String> = SingleLive()
    val showSnackBarInt: SingleLive<Int> = SingleLive()
    val showToast: SingleLive<String> = SingleLive()
    val showLoading: SingleLive<Boolean> = SingleLive()
    val showNoData: MutableLiveData<Boolean> = MutableLiveData()

}