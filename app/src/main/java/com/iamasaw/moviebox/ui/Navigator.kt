package com.iamasaw.moviebox.ui

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

interface Navigator {
    fun setController(navControlles: NavController)
    fun navigate(route: NavDestination, builder: NavOptionsBuilder.() -> Unit = {})
    fun popBackStack()
    fun popBackStack(route: NavDestination, inclusive: Boolean, saveState: Boolean = false)
}

class RealNavigator : Navigator {
    private var navController: NavController? = null
    override fun setController(navController: NavController) {
        this.navController = navController
    }

    override fun navigate(route: NavDestination, builder: NavOptionsBuilder.() -> Unit) {
        navController?.navigate(route.buildRoute(), builder) ?: Log.w(
            "Navigator",
            "No navController set in the Navigator"
        )
    }

    override fun popBackStack() {
        navController?.popBackStack() ?: Log.w("Navigator", "No navController set in the Navigator")
    }

    override fun popBackStack(route: NavDestination, inclusive: Boolean, saveState: Boolean) {
        navController?.popBackStack(route.buildRoute(), inclusive, saveState)
    }
}