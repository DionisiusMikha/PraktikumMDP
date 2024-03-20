package com.dionisius.ebook.data

data class Book(
    val title: String,
    val author: String,
    val synopsis: String,
    val price: Long,
    var isOnLibrary: Boolean = false,
    var isFavorite: Boolean = false,
)
