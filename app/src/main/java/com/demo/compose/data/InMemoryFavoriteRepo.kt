package com.demo.compose.data

import com.demo.compose.domain.repo.FavoriteRepo
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.HashSet
import java.util.UUID
import javax.inject.Inject


class InMemoryFavoriteRepo @Inject constructor(): FavoriteRepo {
    private val inMemoryFavorites: MutableSet<UUID> = mutableSetOf()
    private val mutex: Mutex = Mutex()

    override suspend fun getFavorites(): Set<UUID> = mutex.withLock {
        HashSet(inMemoryFavorites)
    }
}