package com.udacity.project4

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

import kotlinx.android.synthetic.main.activity_remind.*


class RemindActivity : AppCompatActivity() {
//lateinit var binding:ActivityRemindersBinding

//lateinit var nav: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remind)
           //  nav=findViewById<Fragment>(R.id.nav_host_fragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                (nav_host_fragment as NavHostFragment).navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
