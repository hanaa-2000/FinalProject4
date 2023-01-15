package com.udacity.project4.until

import androidx.test.espresso.idling.CountingIdlingResource

object EspResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }

    inline fun <T> wrapEspressoIdlingResource(function: () -> T): T {
          EspResource.increment()
        return try {
            function()
        } finally {
            EspResource.decrement()
        }
    }
}