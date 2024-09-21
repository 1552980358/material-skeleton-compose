package me.ks.chan.material.skeleton.compose.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.ks.chan.material.skeleton.compose.example.ui.nav.Home
import me.ks.chan.material.skeleton.compose.example.ui.screen.home.HomeScreen
import me.ks.chan.material.skeleton.compose.example.ui.theme.MaterialskeletoncomposeTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialskeletoncomposeTheme {
                Layout()
            }
        }
    }
}

@Preview
@Composable
private fun Layout() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home::class,
    ) {
        composable<Home> { HomeScreen() }
    }
}