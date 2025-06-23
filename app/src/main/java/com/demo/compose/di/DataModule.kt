package com.demo.compose.di

import com.demo.compose.data.InMemoryCartRepo
import com.demo.compose.data.InMemoryFavoriteRepo
import com.demo.compose.data.MockPizzaApi
import com.demo.compose.domain.repo.CartRepo
import com.demo.compose.domain.repo.FavoriteRepo
import com.demo.compose.domain.repo.PizzaApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun bindCartRepo(cartRepo: InMemoryCartRepo): CartRepo

    @Binds
    @Singleton
    fun bindFavoritesRepo(favoriteRepo: InMemoryFavoriteRepo): FavoriteRepo

    @Binds
    @Singleton
    fun bindPizzaApi(pizzaApi: MockPizzaApi): PizzaApi
}