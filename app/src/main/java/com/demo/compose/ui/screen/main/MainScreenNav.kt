package com.demo.compose.ui.screen.main

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.demo.compose.R
import com.demo.compose.ui.screen.main.cart.CartRoute
import com.demo.compose.ui.screen.main.cart.CartScreen
import com.demo.compose.ui.screen.main.fav.FavRoute
import com.demo.compose.ui.screen.main.fav.FavScreen
import com.demo.compose.ui.screen.main.home.HomeRoute
import com.demo.compose.ui.screen.main.home.HomeScreen
import com.demo.compose.ui.screen.main.profile.ProfileRoute
import com.demo.compose.ui.screen.main.profile.ProfileScreen

interface Route<T : ViewModel> {
    val route: String

    @Composable
    fun provideViewModel(): T
}

@Composable
fun MainScreenNav(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeRoute.route,
    ) {
        composable(HomeRoute.route) {
            val viewModel = HomeRoute.provideViewModel()
            val viewState by viewModel.state.collectAsStateWithLifecycle()
            HomeScreen(
                state = viewState,
                onAddToCart = viewModel::onAddToCart,
                onRemoveFromCart = viewModel::onRemoveFromCart,
                onFavClick = viewModel::onFavClick,
            )
        }
        composable(FavRoute.route) {
            FavScreen()
        }
        composable(CartRoute.route) {
            CartScreen()
        }
        composable(ProfileRoute.route) {
            ProfileScreen()
        }
    }
}

@Composable
fun MainNavBar(navController: NavHostController, modifier: Modifier = Modifier) {
    val outlineVariant = MaterialTheme.colorScheme.outlineVariant
    val backstackEntry by navController.currentBackStackEntryAsState()

    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = outlineVariant,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 2.dp.toPx()
                )
            },
    ) {
        MainNavItem(
            selected = HomeRoute.isOnTop(backstackEntry),
            iconId = R.drawable.icon_home,
            labelId = R.string.menu_item_home
        ) {
            navController.replace(HomeRoute)
        }
        MainNavItem(
            selected = FavRoute.isOnTop(backstackEntry),
            iconId = R.drawable.icon_favorite,
            labelId = R.string.menu_item_fav
        ) {
            navController.replace(FavRoute)
        }
        MainNavItem(
            selected = CartRoute.isOnTop(backstackEntry),
            iconId = R.drawable.icon_cart,
            labelId = R.string.menu_item_cart
        ) {
            navController.replace(CartRoute)
        }
        MainNavItem(
            selected = ProfileRoute.isOnTop(backstackEntry),
            iconId = R.drawable.icon_profile,
            labelId = R.string.menu_item_profile
        ) {
            navController.replace(ProfileRoute)
        }
    }
}

@Composable
fun RowScope.MainNavItem(
    selected: Boolean,
    @DrawableRes iconId: Int,
    @StringRes labelId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val ctx = LocalContext.current
    val primary = MaterialTheme.colorScheme.primary
    val outline = MaterialTheme.colorScheme.outline
    val animatedTint by animateColorAsState(if (selected) primary else outline)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .height(80.dp)
            .weight(1f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = false),
                onClick = onClick
            )
    ) {
        Icon(
            painterResource(iconId),
            contentDescription = ctx.getString(labelId),
            tint = animatedTint,
        )
        Text(
            ctx.getString(labelId),
            color = animatedTint,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

fun Route<*>.isOnTop(entry: NavBackStackEntry?): Boolean {
    Log.d("LOGUSIKI", "${entry?.destination?.route}")
    return entry?.destination?.route == route
}

fun NavController.replace(route: Route<*>) {
    navigate(route.route, NavOptions.Builder()
        .also {
            val currentId = currentDestination?.id
            if (currentId != null) {
                it.setPopUpTo(currentId, inclusive = true)
            }
        }
        .build())
}