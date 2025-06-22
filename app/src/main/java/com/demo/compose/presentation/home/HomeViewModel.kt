package com.demo.compose.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private var _state: MutableStateFlow<State> = MutableStateFlow(
        State.Done(
            order = Order.ByPrice(desc = true),
            items = mockItems(),
        )
    )
    val state: StateFlow<State>
        get() = _state

    fun onAddToCart(itemId: UUID) {
        val s = state.value as State.Done
        _state.value = s.copy(
            items = s.items.updateItemStateById(itemId) { itemState ->
                itemState.copy(countInCart = itemState.countInCart + 1)
            }
        )
    }

    fun onRemoveFromCart(itemId: UUID) {
        val s = state.value as State.Done
        _state.value = s.copy(
            items = s.items.updateItemStateById(itemId) { itemState ->
                itemState.copy(countInCart = (itemState.countInCart - 1).coerceAtLeast(0))
            }
        )
    }

    fun onFavClick(itemId: UUID) {
        val s = state.value as State.Done
        _state.value = s.copy(
            items = s.items.updateItemStateById(itemId) { itemState ->
                itemState.copy(inFavorites = !itemState.inFavorites)
            }
        )
    }

    private fun List<PizzaItemLoadingState>.updateItemStateById(
        id: UUID,
        transform: (PizzaItemState) -> PizzaItemState
    ) = map { item ->
        if (item.state.item.id == id) {
            item.copy(state = transform(item.state))
        } else {
            item
        }
    }

    private fun mockItems() = listOf(
        PizzaItemLoadingState(
            state = PizzaItemState(
                item = PizzaItem(
                    id = UUID.randomUUID(),
                    name = "Pepperoni",
                    category = "Italian kitchen",
                    price = 20,
                    rating = 4.4f,
                    commentCount = 4536,
                    imageUrl = null,
                ),
                countInCart = 0,
                inFavorites = false
            ),
            loadingCart = false,
            loadingFav = false
        ),
        PizzaItemLoadingState(
            state = PizzaItemState(
                item = PizzaItem(
                    id = UUID.randomUUID(),
                    name = "Pepperoni",
                    category = "Italian kitchen",
                    price = 20,
                    rating = 4.4f,
                    commentCount = 4536,
                    imageUrl = null,
                ),
                countInCart = 0,
                inFavorites = false
            ),
            loadingCart = false,
            loadingFav = false
        ),
    )

    sealed interface State {
        data object Loading : State
        data object Error : State
        data class Done(
            val order: Order,
            val items: List<PizzaItemLoadingState>
        ) : State
    }
}

data class PizzaItem(
    val id: UUID,
    val name: String,
    val category: String,
    val price: Int,
    val rating: Float,
    val commentCount: Int,
    val imageUrl: String?
)

data class PizzaItemState(
    val item: PizzaItem,
    val countInCart: Int,
    val inFavorites: Boolean
)

data class PizzaItemLoadingState(
    val state: PizzaItemState,
    val loadingCart: Boolean,
    val loadingFav: Boolean,
)

sealed interface Order {
    val desc: Boolean

    data class ByPrice(override val desc: Boolean) : Order
    data class ByName(override val desc: Boolean) : Order
    data class ByCategory(override val desc: Boolean) : Order
}