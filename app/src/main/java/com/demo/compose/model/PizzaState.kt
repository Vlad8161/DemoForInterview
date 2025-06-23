package com.demo.compose.model


data class PizzaState(
    val pizza: Pizza,
    val countInCart: Int,
    val inFavorites: Boolean
)

