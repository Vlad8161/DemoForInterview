package com.demo.compose.ui.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.compose.R
import com.demo.compose.ui.theme.AppTheme

@Preview
@Composable
fun IconButtonPreview() {
    AppTheme {
        val colors = MaterialTheme.colorScheme
        IconButton(
            painter = painterResource(R.drawable.icon_cart),
            bgColor = colors.primary,
            fgColor = colors.onPrimary,
            onClick = {}
        )
    }
}

@Composable
fun IconButton(
    painter: Painter,
    bgColor: Color,
    fgColor: Color,
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 3.dp,
        ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .clickable(
                    enabled = !loading,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    onClick = onClick
                )
        ) {
            AnimatedContent(
                targetState = loading
            ) { loading ->
                if (loading) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .padding(10.dp)
                            .size(18.dp, 18.dp)
                    )
                } else {
                    Icon(
                        painter,
                        contentDescription = null,
                        tint = fgColor,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
        }
    }
}