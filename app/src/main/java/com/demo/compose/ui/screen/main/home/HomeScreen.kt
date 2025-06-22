package com.demo.compose.ui.screen.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.compose.R
import com.demo.compose.presentation.home.HomeViewModel
import com.demo.compose.presentation.home.Order
import com.demo.compose.presentation.home.PizzaItem
import com.demo.compose.presentation.home.PizzaItemLoadingState
import com.demo.compose.presentation.home.PizzaItemState
import com.demo.compose.ui.screen.main.Route
import com.demo.compose.ui.theme.AppTheme
import kotlinx.serialization.Serializable
import java.util.UUID


@Serializable
object HomeRoute : Route<HomeViewModel> {
    override val route: String = "home"

    @Composable
    override fun provideViewModel(): HomeViewModel =
        hiltViewModel()
}

@Composable
@Preview
fun HomeScreenPreview() {
    val done = HomeViewModel.State.Done(
        order = Order.ByPrice(desc = true),
        items = listOf(
            PizzaItemLoadingState(
                state = PizzaItemState(
                    item = PizzaItem(
                        id = UUID.randomUUID(),
                        name = "Pepperoni",
                        category = "Italian kitchen",
                        price = 20,
                        rating = 4.4f,
                        commentCount = 4536,
                        imageUrl = null,
                    ),
                    countInCart = 10,
                    inFavorites = false
                ),
                loadingCart = false,
                loadingFav = false
            ),

            PizzaItemLoadingState(
                state = PizzaItemState(
                    item = PizzaItem(
                        id = UUID.randomUUID(),
                        name = "Pepperoni",
                        category = "Italian kitchen",
                        price = 20,
                        rating = 4.4f,
                        commentCount = 4536,
                        imageUrl = null,
                    ),
                    countInCart = 10,
                    inFavorites = false
                ),
                loadingCart = false,
                loadingFav = false
            ),

            PizzaItemLoadingState(
                state = PizzaItemState(
                    item = PizzaItem(
                        id = UUID.randomUUID(),
                        name = "Pepperoni",
                        category = "Italian kitchen",
                        price = 20,
                        rating = 4.4f,
                        commentCount = 4536,
                        imageUrl = null,
                    ),
                    countInCart = 10,
                    inFavorites = false
                ),
                loadingCart = false,
                loadingFav = false
            ),
        )
    )
    val error = HomeViewModel.State.Error
    val loading = HomeViewModel.State.Loading
    AppTheme {
        HomeScreen(
            state = done,
            onAddToCart = {},
            onRemoveFromCart = {},
            onFavClick = {}
        )
    }
}

@Composable
fun HomeScreen(
    state: HomeViewModel.State,
    onAddToCart: (id: UUID) -> Unit,
    onRemoveFromCart: (id: UUID) -> Unit,
    onFavClick: (id: UUID) -> Unit,
) {
    val ctx = LocalContext.current
    val colors = MaterialTheme.colorScheme
    val typo = MaterialTheme.typography
    Column(
        modifier = Modifier
            .background(colors.surfaceContainer)
            .fillMaxSize()
    ) {
        Text(
            text = ctx.getString(R.string.home_title),
            style = typo.titleLarge,
            color = colors.onSurface,
            modifier = Modifier
                .padding(start = 32.dp, top = 48.dp)
        )
        Text(
            text = ctx.getString(R.string.home_subtitle),
            style = typo.titleSmall,
            color = colors.onSurfaceVariant,
            modifier = Modifier
                .padding(start = 32.dp, top = 8.dp, bottom = 24.dp)
        )
        Column(
            modifier = Modifier
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                )
                .background(colors.surface)
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when (state) {
                    is HomeViewModel.State.Loading -> HomeScreenLoading()
                    is HomeViewModel.State.Error -> HomeScreenError()
                    is HomeViewModel.State.Done -> HomeScreenDone(
                        state = state,
                        onAddToCart = onAddToCart,
                        onRemoveFromCart = onRemoveFromCart,
                        onFavClick = onFavClick
                    )
                }
            }
        }
    }
}

@Composable
fun BoxScope.HomeScreenLoading() {
    CircularProgressIndicator(
        modifier = Modifier
            .align(Alignment.Center)
    )
}

@Composable
fun BoxScope.HomeScreenError() {
    val ctx = LocalContext.current
    val typo = MaterialTheme.typography
    val colors = MaterialTheme.colorScheme
    Text(
        text = ctx.getString(R.string.home_error),
        style = typo.bodyMedium,
        color = colors.onSurface,
        modifier = Modifier
            .padding(32.dp)
            .align(Alignment.Center)
    )
}

@Composable
fun HomeScreenDone(
    state: HomeViewModel.State.Done,
    modifier: Modifier = Modifier,
    onAddToCart: (id: UUID) -> Unit,
    onRemoveFromCart: (id: UUID) -> Unit,
    onFavClick: (id: UUID) -> Unit,
) {
    val ctx = LocalContext.current
    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            HomeOrder(order = state.order)
        }
        for (item in state.items) {
            HomeScreenPizzaItemView(
                item = item,
                onAddToCart = { onAddToCart(item.state.item.id) },
                onRemoveFromCart = { onRemoveFromCart(item.state.item.id) },
                onFavClick = { onFavClick(item.state.item.id) },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 12.dp)
            )
        }
    }
}

@Composable
fun HomeOrder(order: Order, modifier: Modifier = Modifier) {
    val ctx = LocalContext.current
    val typo = MaterialTheme.typography
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true),
                onClick = { /* TODO */ }
            )
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp, 12.dp)
        ) {
            Text(
                text = ctx.getString(R.string.home_order_label),
                style = typo.labelSmall,
                color = colors.outline
            )
            Text(
                text = when (order) {
                    is Order.ByName -> ctx.getString(R.string.home_order_name)
                    is Order.ByPrice -> ctx.getString(R.string.home_order_price)
                    is Order.ByCategory -> ctx.getString(R.string.home_order_category)
                },
                style = typo.labelMedium,
                color = colors.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
            )
        }
    }
}