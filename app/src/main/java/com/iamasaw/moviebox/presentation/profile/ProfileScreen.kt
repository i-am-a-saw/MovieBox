package com.iamasaw.moviebox.presentation.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.iamasaw.moviebox.presentation.theme.Black
import com.iamasaw.moviebox.presentation.theme.White

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    innerPaddingValues: PaddingValues,
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text("Profile", color = White)
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackClick,
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            },
            colors =
                TopAppBarColors(
                    containerColor = Black,
                    scrolledContainerColor = Black,
                    navigationIconContentColor = White,
                    titleContentColor = White,
                    actionIconContentColor = White,
                    subtitleContentColor = White,
                ),
        )
    }) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPaddingValues)
                    .background(Black),
            contentAlignment = Alignment.Center,
        ) {
            Text("This is your profile.", color = White)
        }
    }
}
