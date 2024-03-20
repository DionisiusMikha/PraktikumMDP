package com.dionisius.ebook.utils

import com.dionisius.ebook.data.Book

fun filterBooks(allBooks: List<Book>, query: String): List<Book> {
    return allBooks.filter { book ->
        book.title.contains(query, true) ||
                book.author.contains(query, true) ||
                book.synopsis.contains(query, true)
    }
}