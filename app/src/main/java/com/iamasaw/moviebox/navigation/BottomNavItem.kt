package com.iamasaw.moviebox.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    @param:StringRes val labelRes: Int,
    val icon: ImageVector,
    val route: String
)
