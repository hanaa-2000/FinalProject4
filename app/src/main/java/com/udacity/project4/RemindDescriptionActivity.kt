package com.udacity.project4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.udacity.project4.databinding.ActivityReminderDescriptionBinding
import com.udacity.project4.remindlist.RemindDataItem


class RemindDescriptionActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_ReminderDataItem = "EXTRA_ReminderDataItem"

        fun newIntent(context: Context, remindDataItem: RemindDataItem): Intent {
            val intent = Intent(context, RemindDescriptionActivity::class.java)
            intent.putExtra(EXTRA_ReminderDataItem, remindDataItem)
            return intent
        }
    }

    private lateinit var binding: ActivityReminderDescriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_reminder_description
        )


        val reminderData = intent.extras?.get(EXTRA_ReminderDataItem) as RemindDataItem

       binding.reminderDataItem = reminderData
        binding.lifecycleOwner = this

    }
}
