package com.demo.compose.ui.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.demo.compose.ui.theme.AppTheme
import kotlinx.serialization.Serializable


@Serializable
object MainRoute

@Preview
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            MainNavBar(navController = navController)
        },
    ) { paddingValues ->
        MainScreenNav(
            modifier = Modifier.padding(paddingValues),
            navController = navController
        )
    }
}