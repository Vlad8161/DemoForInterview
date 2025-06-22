package com.demo.compose.ui.screen.main.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.demo.compose.ui.screen.main.Route
import kotlinx.serialization.Serializable


@Serializable
object CartRoute : Route<ViewModel> {
    override val route: String = "cart"


    @Composable
    override fun provideViewModel(): ViewModel {
        TODO("Not yet implemented")
    }
}

@Composable
fun CartScreen() {
    Box(Modifier.fillMaxSize()) {
        Text(modifier = Modifier.align(Alignment.Center), text = "Cart")
    }
}