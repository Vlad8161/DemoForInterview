package com.demo.compose.ui.screen.main.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.demo.compose.ui.screen.main.Route
import kotlinx.serialization.Serializable


@Serializable
object CartRoute : Route {
    override val route: String = "cart"
}

@Composable
fun CartScreen() {
    Box(Modifier.fillMaxSize()) {
        Text(modifier = Modifier.align(Alignment.Center), text = "Cart")
    }
}