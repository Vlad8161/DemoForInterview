package com.demo.compose.data

import com.demo.compose.domain.repo.PizzaApi
import com.demo.compose.model.Order
import com.demo.compose.model.Pizza
import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject


class MockPizzaApi @Inject constructor() : PizzaApi {
    override suspend fun loadPizza(order: Order): List<Pizza> {
        delay(1000) // Simulate loading

        return listOf(
            Pizza(
                id = UUID.randomUUID(),
                name = "Pepperoni",
                category = "Italian kitchen",
                price = 20,
                rating = 4.4f,
                commentCount = 4536,
                imageUrl = "https://cdn.create.vista.com/api/media/small/77490208/stock-photo-delicious-cheese-stringy-slice-lifted-of-full-supreme-pizza-baked-fresh-out-of-the-oven",
            ),
            Pizza(
                id = UUID.randomUUID(),
                name = "Pepperoni",
                category = "Italian kitchen",
                price = 20,
                rating = 4.4f,
                commentCount = 4536,
                imageUrl = null,
            ),
        ).let {
            if (order.desc) {
                when (order) {
                    is Order.ByName -> it.sortedByDescending { pizza -> pizza.name }
                    is Order.ByPrice -> it.sortedByDescending { pizza -> pizza.price }
                    is Order.ByCategory -> it.sortedByDescending { pizza -> pizza.category }
                }
            } else {
                when (order) {
                    is Order.ByName -> it.sortedByDescending { pizza -> pizza.name }
                    is Order.ByPrice -> it.sortedByDescending { pizza -> pizza.price }
                    is Order.ByCategory -> it.sortedByDescending { pizza -> pizza.category }
                }
            }
        }
    }
}