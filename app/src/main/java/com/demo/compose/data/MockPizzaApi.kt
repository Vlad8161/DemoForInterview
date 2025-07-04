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
                id = UUID.fromString("cb05fb92-4fcd-11f0-8600-77e163d9034d"),
                name = "Pepperoni",
                category = "Italian kitchen",
                price = 20,
                rating = 4.4f,
                commentCount = 4536,
                imageUrl = "https://cdn.create.vista.com/api/media/small/77490208/stock-photo-delicious-cheese-stringy-slice-lifted-of-full-supreme-pizza-baked-fresh-out-of-the-oven",
            ),
            Pizza(
                id = UUID.fromString("d8b373fa-4fcd-11f0-bb78-73b1ab6102d1"),
                name = "Pepperoni",
                category = "Italian kitchen",
                price = 20,
                rating = 4.4f,
                commentCount = 4536,
                imageUrl = null,
            ),
            Pizza(
                id = UUID.fromString("d79be5f2-501c-11f0-85e2-63c23a725e03"),
                name = "Pepperoni",
                category = "Italian kitchen",
                price = 20,
                rating = 4.4f,
                commentCount = 4536,
                imageUrl = "https://cdn.create.vista.com/api/media/small/77490208/stock-photo-delicious-cheese-stringy-slice-lifted-of-full-supreme-pizza-baked-fresh-out-of-the-oven",
            ),
            Pizza(
                id = UUID.fromString("e0da0694-501c-11f0-b8e8-43ad99b9d284"),
                name = "Pepperoni",
                category = "Italian kitchen",
                price = 20,
                rating = 4.4f,
                commentCount = 4536,
                imageUrl = null,
            ),
            Pizza(
                id = UUID.fromString("e9566a2e-501c-11f0-81e3-c3e4d725d440"),
                name = "Pepperoni",
                category = "Italian kitchen",
                price = 20,
                rating = 4.4f,
                commentCount = 4536,
                imageUrl = "https://cdn.create.vista.com/api/media/small/77490208/stock-photo-delicious-cheese-stringy-slice-lifted-of-full-supreme-pizza-baked-fresh-out-of-the-oven",
            ),
            Pizza(
                id = UUID.fromString("f031bf60-501c-11f0-bab1-33260906951a"),
                name = "Pepperoni",
                category = "Italian kitchen",
                price = 20,
                rating = 4.4f,
                commentCount = 4536,
                imageUrl = null,
            ),
            Pizza(
                id = UUID.fromString("f7992414-501c-11f0-9d9a-b71808a344eb"),
                name = "Pepperoni",
                category = "Italian kitchen",
                price = 20,
                rating = 4.4f,
                commentCount = 4536,
                imageUrl = "https://cdn.create.vista.com/api/media/small/77490208/stock-photo-delicious-cheese-stringy-slice-lifted-of-full-supreme-pizza-baked-fresh-out-of-the-oven",
            ),
            Pizza(
                id = UUID.fromString("fce1615c-501c-11f0-9ff8-23239fa60a8d"),
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