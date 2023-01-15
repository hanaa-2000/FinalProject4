package com.udacity.project4.remindlist

import com.udacity.project4.R
import com.udacity.project4.basic.RemindRecyclerView


class RemindListAdapter(callBack: (selectedReminder: RemindDataItem) -> Unit) :
    RemindRecyclerView<RemindDataItem>(callBack) {
    override fun getLayoutRes(viewType: Int) = R.layout.it_reminder
}