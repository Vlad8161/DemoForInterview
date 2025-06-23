package com.demo.compose.domain.repo

import com.demo.compose.model.Order
import com.demo.compose.model.Pizza


interface PizzaApi {
    suspend fun loadPizza(order: Order): List<Pizza>
}