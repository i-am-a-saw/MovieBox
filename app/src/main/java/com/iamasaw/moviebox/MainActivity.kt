package com.iamasaw.moviebox


// hello

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iamasaw.moviebox.ui.theme.Beidge10
import com.iamasaw.moviebox.ui.theme.Beidge30
import com.iamasaw.moviebox.ui.theme.Beidge50
import com.iamasaw.moviebox.ui.theme.MovieBoxTheme
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
        }, containerColor = Beidge10
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
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(Beidge50)
            .padding(10.dp),
    ) {
        val image = painterResource(R.drawable.androidparty)
        val imageModifier = Modifier
            .size(150.dp, 300.dp)
            .border(BorderStroke(1.dp, Color.Black))
            .background(Beidge50)

        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = imageModifier
        )
        Column(modifier = Modifier.padding(start = 5.dp)) {
            Text("Movie number ${movie.id}", color = Beidge10)
            Spacer(modifier = Modifier.padding(20.dp))
            Text(
                "In a dystopian future where Earth has become near-uninhabitable, a team of astronauts embark on a mission to find a new home for humanity.",
                fontSize = 12.sp,
                color = Beidge10,
                fontWeight = FontWeight.Light
            )
        }
    }
}