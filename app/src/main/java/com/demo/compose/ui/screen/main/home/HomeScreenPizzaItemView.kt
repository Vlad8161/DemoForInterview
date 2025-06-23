package com.demo.compose.ui.screen.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.demo.compose.R
import com.demo.compose.model.Pizza
import com.demo.compose.model.PizzaState
import com.demo.compose.presentation.home.PizzaStateLoadingWrapper
import com.demo.compose.ui.theme.AppTheme
import com.demo.compose.ui.views.ExpandableCartButton
import com.demo.compose.ui.views.IconButton
import com.demo.compose.utils.toStringWithWhitespace
import java.util.UUID

@Composable
fun HomeScreenPizzaItemView(
    item: PizzaStateLoadingWrapper,
    modifier: Modifier = Modifier,
    onAddToCart: () -> Unit,
    onRemoveFromCart: () -> Unit,
    onFavClick: () -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    val typo = MaterialTheme.typography
    val ctx = LocalContext.current
    Box(modifier) {
        Card(
            colors = CardDefaults.cardColors()
                .copy(containerColor = colors.surfaceContainer),
            elevation = CardDefaults.cardElevation(2.dp),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(bottom = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (item.state.pizza.imageUrl != null) {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(ctx)
                            .data(item.state.pizza.imageUrl)
                            .crossfade(true)
                            .build(),
                        loading = {
                            Box {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(36.dp)
                                )
                            }
                        },
                        contentScale = ContentScale.Crop,
                        contentDescription = item.state.pizza.name,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .size(114.dp),
                        success = {
                            SubcomposeAsyncImageContent()
                        },
                        error = {
                            Box(
                                modifier = Modifier
                                    .size(114.dp, 114.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(colors.errorContainer)
                            )
                        }
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(114.dp, 114.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(colors.surfaceContainerHighest)
                    )
                }
                Column {
                    Text(
                        text = item.state.pizza.name,
                        style = typo.titleMedium,
                        color = colors.onSurface,
                        modifier = Modifier
                            .padding(top = 12.dp, start = 16.dp, end = 16.dp)

                    )
                    Text(
                        text = item.state.pizza.category,
                        style = typo.labelMedium,
                        color = colors.outline,
                        modifier = Modifier
                            .padding(top = 2.dp, start = 16.dp, end = 16.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 9.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .weight(1f),
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.icon_comment),
                                contentDescription = ctx.getString(R.string.item_pizza_comment_description),
                                tint = colors.tertiary
                            )
                            Text(
                                text = item.state.pizza.commentCount.toStringWithWhitespace(),
                                style = typo.labelLarge,
                                color = colors.onSurfaceVariant,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.icon_star),
                                contentDescription = ctx.getString(R.string.item_pizza_rating_description),
                                tint = colors.tertiary
                            )
                            Text(
                                text = item.state.pizza.commentCount.toStringWithWhitespace(),
                                style = typo.labelLarge,
                                color = colors.onSurfaceVariant,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
        IconButton(
            painter = painterResource(R.drawable.icon_favorite),
            bgColor = colors.primaryContainer,
            fgColor = colors.onPrimaryContainer,
            loading = item.loadingFav,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp),
            onClick = { onFavClick() }
        )
        ExpandableCartButton(
            countInCart = item.state.countInCart,
            loading = item.loadingCart,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 134.dp),
            onClickAdd = { onAddToCart() },
            onClickRemove = { onRemoveFromCart() },
        )
    }
}

@Preview
@Composable
fun HomeScreenPizzaItemPreview() {
    AppTheme {
        HomeScreenPizzaItemView(
            PizzaStateLoadingWrapper(
                state = PizzaState(
                    pizza = Pizza(
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 16.dp, end = 16.dp),
            onAddToCart = {},
            onRemoveFromCart = {},
            onFavClick = {},
        )
    }
}
