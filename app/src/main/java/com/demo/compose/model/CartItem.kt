package com.demo.compose.model

import java.util.UUID


data class CartItem(
    val pizzaId: UUID,
    val count: Int,
)