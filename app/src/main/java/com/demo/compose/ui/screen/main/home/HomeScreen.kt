package com.demo.compose.ui.screen.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.demo.compose.ui.screen.main.Route
import kotlinx.serialization.Serializable


@Serializable
object HomeRoute : Route {
    override val route: String = "home"
}

@Composable
fun HomeScreen() {
    Box(Modifier.fillMaxSize()) {
        Text(modifier = Modifier.align(Alignment.Center), text = "Home")
    }
}