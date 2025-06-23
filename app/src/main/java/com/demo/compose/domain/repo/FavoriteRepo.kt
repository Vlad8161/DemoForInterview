package com.demo.compose.domain.repo

import java.util.UUID


interface FavoriteRepo {
    suspend fun getFavorites(): Set<UUID>
}