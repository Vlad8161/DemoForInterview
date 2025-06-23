package com.demo.compose.presentation.home

import androidx.lifecycle.ViewModel
import com.demo.compose.domain.AddItemToCartInteractor
import com.demo.compose.domain.LoadPizzaListInteractor
import com.demo.compose.domain.RemoveItemFromCartInteractor
import com.demo.compose.model.Order
import com.demo.compose.model.PizzaState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadPizzaListInteractor: LoadPizzaListInteractor,
    private val addToCartInteractor: AddItemToCartInteractor,
    private val removeFromCartInteractor: RemoveItemFromCartInteractor,
) : ViewModel() {
    private var _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State>
        get() = _state

    private val scope: CoroutineScope = CoroutineScope(SupervisorJob())
    private var loadPizzaJob: Job? = null
    private val cartJobs: MutableMap<UUID, Job> = mutableMapOf()
    private val favJobs: MutableMap<UUID, Job> = mutableMapOf()

    init {
        loadPizza()
    }

    fun onAddToCart(pizzaId: UUID) {
        updateCartCount(pizzaId, 1)
    }

    fun onRemoveFromCart(pizzaId: UUID) {
        updateCartCount(pizzaId, -1)
    }

    fun onFavClick(itemId: UUID) {
        val s = state.value as State.Done
        _state.value = s.copy(
            pizzaList = s.pizzaList.updateStateByPizzaId(itemId) { itemState ->
                itemState.copy(inFavorites = !itemState.inFavorites)
            }
        )
    }

    private fun loadPizza() {
        loadPizzaJob?.cancel()
        loadPizzaJob = scope.launch {
            try {
                _state.value = State.Loading
                val (order, pizza) = loadPizzaListInteractor(Order.ByName(desc = false))
                _state.value = State.Done(
                    order = order,
                    pizzaList = pizza.map { it.wrap() }
                )
            } catch (e: Exception) {
                _state.value = State.Error
            }
        }
    }

    private fun updateCartCount(pizzaId: UUID, delta: Int) {
        if (cartJobs[pizzaId]?.isActive == true) {
            return
        }

        val s = state.value as State.Done
        _state.value = s.copy(
            pizzaList = s.pizzaList.updateLoadingByPizzaId(pizzaId) { pizzaItem ->
                pizzaItem.copy(loadingCart = true)
            }
        )

        cartJobs[pizzaId] = scope.launch {
            try {
                if (delta > 0) {
                    addToCartInteractor(pizzaId)
                } else {
                    removeFromCartInteractor(pizzaId)
                }

                val s = state.value as? State.Done ?: return@launch
                _state.value = s.copy(
                    pizzaList = s.pizzaList.updateLoadingByPizzaId(pizzaId) { pizzaItem ->
                        pizzaItem.copy(
                            loadingCart = false,
                            state = pizzaItem.state.copy(
                                countInCart = pizzaItem.state.countInCart + delta
                            )
                        )
                    }
                )
            } catch (e: Exception) {
                val s = state.value as? State.Done ?: return@launch
                _state.value = s.copy(
                    pizzaList = s.pizzaList.updateLoadingByPizzaId(pizzaId) { pizzaItem ->
                        pizzaItem.copy(loadingCart = false)
                    }
                )
            }
        }
    }

    private fun List<PizzaStateLoadingWrapper>.updateStateByPizzaId(
        id: UUID,
        transform: (PizzaState) -> PizzaState
    ) = map { item ->
        if (item.state.pizza.id == id) {
            item.copy(state = transform(item.state))
        } else {
            item
        }
    }

    private fun List<PizzaStateLoadingWrapper>.updateLoadingByPizzaId(
        id: UUID,
        transform: (PizzaStateLoadingWrapper) -> PizzaStateLoadingWrapper
    ) = map { item ->
        if (item.state.pizza.id == id) {
            transform(item)
        } else {
            item
        }
    }

    sealed interface State {
        data object Loading : State
        data object Error : State
        data class Done(
            val order: Order,
            val pizzaList: List<PizzaStateLoadingWrapper>
        ) : State
    }
}

data class PizzaStateLoadingWrapper(
    val state: PizzaState,
    val loadingCart: Boolean,
    val loadingFav: Boolean,
)

private fun PizzaState.wrap(loadingCart: Boolean = false, loadingFav: Boolean = false) =
    PizzaStateLoadingWrapper(this, loadingCart, loadingFav)