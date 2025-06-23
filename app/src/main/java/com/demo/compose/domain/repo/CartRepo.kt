package com.demo.compose.domain.repo

import com.demo.compose.model.CartItem
import java.util.UUID


interface CartRepo {
    suspend fun getCart(): List<CartItem>

    suspend fun add(pizzaId: UUID)

    suspend fun remove(pizzaId: UUID)
}