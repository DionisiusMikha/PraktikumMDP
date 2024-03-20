package com.dionisius.ebook.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dionisius.ebook.R
import com.dionisius.ebook.data.Book
import com.dionisius.ebook.databinding.ItemListBookBinding
import com.dionisius.ebook.detail.DetailActivity

class LibraryAdapter(
    private val onDeleteClicked: (Book) -> Unit,
    private val onFavoriteClicked: (Book) -> Unit,
) : RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder>() {

    var books = ArrayList<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val view = ItemListBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibraryViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bindView(books[position], onDeleteClicked, onFavoriteClicked)
    }

    override fun getItemCount(): Int = books.size

    class LibraryViewHolder(private val view: ItemListBookBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bindView(
            book: Book,
            onDeleteClicked: (Book) -> Unit,
            onFavoriteClicked: (Book) -> Unit,
        ) {
            view.apply {
                view.tvTitle.text = book.title
                view.tvAuthor.text = book.author

                if (book.isFavorite)
                    ivFavourite.setImageResource(R.drawable.ic_favorite)
                else
                    ivFavourite.setImageResource(R.drawable.ic_unfavourite)

                ivFavourite.setOnClickListener {
                    onFavoriteClicked(book)
                    if (book.isFavorite)
                        ivFavourite.setImageResource(R.drawable.ic_favorite)
                    else
                        ivFavourite.setImageResource(R.drawable.ic_unfavourite)
                }

                ivDelete.setOnClickListener {
                    book.isOnLibrary = false
                    onDeleteClicked(book)
                }
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.apply {
                    putExtra("title", book.title)
                    putExtra("author", book.author)
                    putExtra("synopsis", book.synopsis)
                    putExtra("price", book.price)
                    putExtra("isOnLibrary", book.isOnLibrary)
                }

                itemView.context.startActivity(intent)
            }
        }

    }
}