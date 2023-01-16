package com.udacity.project4.basic

import androidx.navigation.NavDirections


sealed class Navigation {

    data class To(val directions: NavDirections) : Navigation()

    object Back : Navigation()


    data class BackTo(val destinationId: Int) : Navigation()
}