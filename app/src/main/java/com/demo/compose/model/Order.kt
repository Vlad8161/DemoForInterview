package com.demo.compose.model


sealed interface Order {
    val desc: Boolean

    data class ByPrice(override val desc: Boolean) : Order
    data class ByName(override val desc: Boolean) : Order
    data class ByCategory(override val desc: Boolean) : Order
}
