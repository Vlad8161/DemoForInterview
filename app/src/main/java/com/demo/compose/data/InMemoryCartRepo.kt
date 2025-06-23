package com.demo.compose.data

import com.demo.compose.domain.repo.CartRepo
import com.demo.compose.model.CartItem
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.UUID
import javax.inject.Inject


class InMemoryCartRepo @Inject constructor() : CartRepo {
    private val inMemoryCart: MutableMap<UUID, CartItem> = mutableMapOf()
    private val mutex: Mutex = Mutex()

    override suspend fun getCart(): List<CartItem> = mutex.withLock {
        ArrayList(inMemoryCart.values)
    }

    override suspend fun add(pizzaId: UUID) {
        mutex.withLock {
            val item = inMemoryCart[pizzaId]
                ?: CartItem(pizzaId, 0)
            inMemoryCart[pizzaId] = item.copy(count = item.count + 1)
        }
    }

    override suspend fun remove(pizzaId: UUID) {
        mutex.withLock {
            val item = inMemoryCart[pizzaId] ?: return
            if (item.count > 1) {
                inMemoryCart[pizzaId] = item.copy(count = item.count - 1)
            } else {
                inMemoryCart.remove(pizzaId)
            }
        }
    }
}