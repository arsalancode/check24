package com.check24.app.navigation

import android.content.Intent
object AppNavigation {


    fun openHome(): Intent {
        return Intent("action.github.home.open").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }

}