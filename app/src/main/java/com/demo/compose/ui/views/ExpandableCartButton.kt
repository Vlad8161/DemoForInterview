package com.demo.compose.ui.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.compose.R
import com.demo.compose.ui.theme.AppTheme
import com.demo.compose.utils.toStringWithWhitespace

@Preview
@Composable
fun CartButtonPreview() {
    AppTheme {
        val colors = MaterialTheme.colorScheme
        ExpandableCartButton(
            countInCart = 1,
            loading = false,
            onClickAdd = { },
            onClickRemove = { },
        )
    }
}

@Composable
fun ExpandableCartButton(
    countInCart: Int,
    loading: Boolean,
    modifier: Modifier = Modifier,
    onClickAdd: () -> Unit,
    onClickRemove: () -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    val typo = MaterialTheme.typography
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 3.dp,
        ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colors.primary),
        modifier = modifier
            .clickable(
                enabled = countInCart == 0 && !loading,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = onClickAdd
            )
    ) {
        Row {
            val buttonState = when {
                loading -> ExpandableCartButtonState.LOADING
                countInCart == 0 -> ExpandableCartButtonState.CART
                else -> ExpandableCartButtonState.EXPANDED
            }
            AnimatedVisibility(visible = countInCart != 0) {
                Icon(
                    painterResource(R.drawable.icon_minus),
                    contentDescription = null,
                    tint = colors.onPrimary,
                    modifier = Modifier
                        .clickable(
                            enabled = !loading,
                            onClick = onClickRemove
                        )
                        .padding(6.dp)
                )
            }
            AnimatedContent(
                targetState = buttonState,
            ) { targetState ->
                when (targetState) {
                    ExpandableCartButtonState.EXPANDED -> {
                        Box(
                            modifier = Modifier
                                .size(36.dp, 36.dp)
                        ) {
                            Text(
                                text = countInCart.toStringWithWhitespace(),
                                textAlign = TextAlign.Center,
                                style = typo.labelLarge,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                    ExpandableCartButtonState.CART -> {
                        Icon(
                            painterResource(R.drawable.icon_cart),
                            contentDescription = null,
                            tint = colors.onPrimary,
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                    ExpandableCartButtonState.LOADING -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(6.dp)
                                .size(24.dp, 24.dp)
                        )
                    }
                }
            }
            AnimatedVisibility(visible = countInCart != 0) {
                Icon(
                    painterResource(R.drawable.icon_plus),
                    contentDescription = null,
                    tint = colors.onPrimary,
                    modifier = Modifier
                        .clickable(
                            enabled = !loading,
                            onClick = onClickAdd
                        )
                        .padding(6.dp)
                )
            }
        }
    }
}

private enum class ExpandableCartButtonState {
    LOADING,
    CART,
    EXPANDED
}