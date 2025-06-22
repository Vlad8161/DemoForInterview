package com.demo.compose.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.compose.ui.screen.main.MainRoute
import com.demo.compose.ui.screen.main.MainScreen
import com.demo.compose.ui.theme.AppTheme


@Composable
fun Main() {
    val navController = rememberNavController()

    AppTheme {
        Nav(navController)
    }
}

@Composable
fun Nav(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MainRoute,
    ) {
        composable<MainRoute> {
            MainScreen()
        }
    }
}