package com.udacity.project4.basic

import androidx.navigation.NavDirections


sealed class NavigationCommand {

    data class To(val directions: NavDirections) : NavigationCommand()

    object Back : NavigationCommand()


    data class BackTo(val destinationId: Int) : NavigationCommand()
}