package com.example.chesstournamentportable

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.chesstournamentportable.ui.theme.ChessTournamentPortableTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.PreviewParameter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sample = listOf(
            Bloc("7ème trophée de Païta", "15/08/2025", Status.TERMINE),
            Bloc("Championnat de NC lente", "17/10/2025", Status.EN_COURS),
            Bloc("Championnat blitz de NC", "15/11/2024", Status.TERMINE)
        )
        setContent {
            ChessTournamentPortableTheme {
                ChessTournamentPortableApp(sample)
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun ChessTournamentPortableApp(blocs: List<Bloc> = emptyList()) {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = { Icon(it.icon, contentDescription = it.label) },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold (floatingActionButton =
            {
                FloatingActionButton(
                    onClick = { /* action quand on clique */ },
                    contentColor = Color.White,
                    containerColor = Color(0xFF0B7189)
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp) // ✅ padding interne
                    ) {
                        Text("+ Créer un tournoi")
                    }
                }
            }){ innerPadding ->



                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item { Greeting(name = "Jomi") }
                    items(blocs) { bloc -> BlocCard(bloc) }
                }

        }
    }
}


enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Home", Icons.Default.Home),
    FAVORITES("Favorites", Icons.Default.Favorite),
    PROFILE("Profile", Icons.Default.AccountBox),
}

enum class Status { TERMINE, EN_COURS }

data class Bloc(
    val title: String,
    val description: String,
    val status: Status
)

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun BlocCard(bloc: Bloc) {
    Card(
        modifier = Modifier
            .width((LocalConfiguration.current.screenWidthDp*0.9).dp)
            .background(color = Color.White),
        shape = MaterialTheme.shapes.medium,

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = bloc.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = bloc.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(12.dp))
            // pousse le statut "tout en bas" du bloc
            // (si vous voulez qu'il colle au bas visuel, utilisez aussi minHeight sur la Card)
            Spacer(Modifier.weight(1f, fill = false))
            val (label, color) = when (bloc.status) {
                Status.TERMINE -> "Terminé" to Color.Red
                Status.EN_COURS -> "En cours" to Color.Green
            }
            Text(
                text = label,
                color = color,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Malo $name Tawasu ?!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChessTournamentPortableTheme {
        Greeting("Android")
    }
}