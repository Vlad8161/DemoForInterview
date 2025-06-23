package com.demo.compose.domain

import com.demo.compose.domain.repo.FavoriteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject


class RemoveFromFavInteractor @Inject constructor(
    private val favoriteRepo: FavoriteRepo
) {
    suspend operator fun invoke(pizzaId: UUID): Unit = withContext(Dispatchers.IO) {
        delay(1000)
        favoriteRepo.remove(pizzaId)
    }
}