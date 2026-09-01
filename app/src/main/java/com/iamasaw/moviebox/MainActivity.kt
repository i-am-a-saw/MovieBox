package com.iamasaw.moviebox

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iamasaw.moviebox.ui.theme.Beidge10
import com.iamasaw.moviebox.ui.theme.Beidge30
import com.iamasaw.moviebox.ui.theme.Beidge50
import com.iamasaw.moviebox.ui.theme.Black
import com.iamasaw.moviebox.ui.theme.Grey10
import com.iamasaw.moviebox.ui.theme.MovieBoxTheme
import com.iamasaw.moviebox.ui.theme.White
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoadInitialScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadInitialScreen() {
    MovieBoxTheme {

        var showNavigationIcon by rememberSaveable { mutableStateOf(true) }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        Scaffold(
            topBar = {
            TopAppBar(title = { Text("MovieBox") }, navigationIcon = {
                if (showNavigationIcon) {
                    IconButton(onClick = { scope.launch { drawerState.apply { if (isOpen) close() else open() } } }) {
                        Icon(Icons.Default.Menu, contentDescription = "fuck")
                    }
                } else {
                    IconButton(onClick = { scope.launch { drawerState.apply { if (isOpen) close() else open() } } }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "fuck"
                        )
                    }
                }
            })
        }, bottomBar = {
            BottomAppBar(containerColor = Beidge30, contentColor = Beidge50) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom Bar"
                )
            }
        }, containerColor = Black
        ) { innerPadding ->

            Box(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
                LazyColumn(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(8.dp)),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(movies.size) { index ->
                        MovieCard(movies[index])

                    }
                }
            }
        }
    }
}

@Composable
fun MovieCard(movie: Movie) {
    val image = painterResource(R.drawable.interstellar)
    val imageModifier =
        Modifier
            .size(90.dp, 150.dp)
            .border(BorderStroke(1.dp, Color.Black))
            .background(Beidge50)

    Row(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(Black)
            .padding(10.dp),
    ) {
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = imageModifier
            )
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(
                    "Movie number ${movie.id}",
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                    Text(
                        "\uD83D\uDCC5" + " 06 April, 2022",
                        color = Grey10,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        lineHeight = 20.sp
                    )
                    Text(
                        "☆" + " 6.8 (2,060)",
                        color = Grey10,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        lineHeight = 20.sp
                    )
                    Text(
                        "\uD83D\uDD57" + " 2hr 22min",
                        color = Grey10,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        lineHeight = 20.sp
                    )
                }
            }
        Spacer(modifier = Modifier.weight(1f))  // moves the button to right side
            IconButton(onClick = {}) {
                Icon(Icons.Default.Clear, contentDescription = "Delete from favourites", tint = White, )
            }
        }
    }
