package com.udacity.project4.authentication

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.map

    class AuthenticationViewModel : ViewModel() {


        enum class AuthenticationState {
            AUTHENTICATED, UNAUTHENTICATED
        }

        val authenticationState = UserFirbaseViewData().map {
            if (it != null) {
                AuthenticationState.AUTHENTICATED
            } else {
                AuthenticationState.UNAUTHENTICATED
            }
        }
    }
