package com.iamasaw.moviebox.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.iamasaw.moviebox.R
import com.iamasaw.moviebox.presentation.theme.Black
import com.iamasaw.moviebox.presentation.theme.White
import com.iamasaw.moviebox.presentation.theme.rubikFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onBackClick: () -> Unit, innerPaddingValues: PaddingValues) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(R.string.profile), color = White)
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = Black,
                    scrolledContainerColor = Black,
                    navigationIconContentColor = White,
                    titleContentColor = White,
                    actionIconContentColor = White,
                    subtitleContentColor = White,
                ),
            )
        },
    ) { padding ->

        var expanded by remember { mutableStateOf(false) }
        var lang by remember { mutableStateOf("Русский") }
        var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
        val icon = if (expanded) Icons.Filled.KeyboardArrowUp
        else Icons.Filled.KeyboardArrowDown

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Black),
            contentAlignment = Alignment.TopCenter,
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(horizontal = 18.dp)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    stringResource(R.string.language),
                    color = White,
                    fontSize = 18.sp,
                    fontFamily = rubikFontFamily,
                )

                TextField(
                    value = lang,
                    onValueChange = { lang = it },
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            mTextFieldSize = coordinates.size.toSize()
                        }
                        .padding(start = 40.dp)
                        .clickable { expanded = !expanded }
                        .border(1.dp, White, RoundedCornerShape(8.dp)),
                    trailingIcon = {
                        Icon(
                            icon, "contentDescription",
                            Modifier.clickable { expanded = !expanded },
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        disabledTextColor = White,
                        disabledContainerColor = Black,
                        disabledPlaceholderColor = White,
                        disabledTrailingIconColor = White,
                        unfocusedContainerColor = White,
                    ),
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
                        .onGloballyPositioned { coordinates ->
                            mTextFieldSize = coordinates.size.toSize()
                        },
                ) {

                    DropdownMenuItem(
                        text = { Text("Русский") },
                        onClick = {
                            lang = "Русский"
                            expanded = false
                        },
                    )
                    DropdownMenuItem(
                        text = { Text("Английский") },
                        onClick = {
                            lang = "Английский"
                            expanded = false
                        },
                    )

                }
            }
        }
    }
}
