package com.demo.compose.domain

import com.demo.compose.domain.repo.CartRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject


class RemoveItemFromCartInteractor @Inject constructor(
    private val cartRepo: CartRepo,
) {
    suspend operator fun invoke(pizzaId: UUID): Unit = withContext(Dispatchers.IO) {
        delay(1000)
        cartRepo.remove(pizzaId)
    }
}