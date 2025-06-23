package com.demo.compose.model

import java.util.UUID


data class Pizza(
    val id: UUID,
    val name: String,
    val category: String,
    val price: Int,
    val rating: Float,
    val commentCount: Int,
    val imageUrl: String?
)

