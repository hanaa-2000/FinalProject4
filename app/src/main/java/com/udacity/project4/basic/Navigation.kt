package com.udacity.project4.basic

import androidx.navigation.NavDirections

/**
 * Sealed class used with the live data to navigate between the fragments
 */
sealed class Navigation {
    /**
     * navigate to a direction
     */
    data class To(val directions: NavDirections) : Navigation()

    /**
     * navigate back to the previous fragment
     */
    object Back : Navigation()

    /**
     * navigate back to a destination in the back stack
     */
    data class BackTo(val destinationId: Int) : Navigation()
}