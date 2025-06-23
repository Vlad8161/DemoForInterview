package com.demo.compose.domain

import com.demo.compose.domain.repo.CartRepo
import com.demo.compose.domain.repo.FavoriteRepo
import com.demo.compose.domain.repo.PizzaApi
import com.demo.compose.model.Order
import com.demo.compose.model.PizzaState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadPizzaListInteractor @Inject constructor(
    private val api: PizzaApi,
    private val cartRepo: CartRepo,
    private val favoriteRepo: FavoriteRepo,
) {
    suspend operator fun invoke(order: Order): Result = withContext(Dispatchers.IO) {
        coroutineScope {
            val pizzaList = async { api.loadPizza(order) }
            val cart = async { cartRepo.getCart() }
            val favorites = async { favoriteRepo.getFavorites() }
                .await()

            val cartMap = cart.await()
                .associateBy { it.pizzaId }

            Result(
                order = order,
                pizzaList = pizzaList.await()
                    .map { pizza ->
                        PizzaState(
                            pizza = pizza,
                            countInCart = cartMap[pizza.id]?.count ?: 0,
                            inFavorites = pizza.id in favorites
                        )
                    }
            )
        }
    }

    data class Result(
        val order: Order,
        val pizzaList: List<PizzaState>,
    )
}