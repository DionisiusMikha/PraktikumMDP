package com.dionisius.ebook.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dionisius.ebook.R
import com.dionisius.ebook.data.Book
import com.dionisius.ebook.databinding.ItemListBookBinding
import com.dionisius.ebook.detail.DetailActivity

class FavoriteAdapter(
    private val onDeleteClicked: (Book) -> Unit,
    private val onFavoriteClicked: (Book) -> Unit,
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var favoriteBooks = ArrayList<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = ItemListBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindView(favoriteBooks[position], onFavoriteClicked, onDeleteClicked)
    }

    override fun getItemCount(): Int = favoriteBooks.size

    class FavoriteViewHolder(private val view: ItemListBookBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bindView(
            book: Book,
            onFavoriteClicked: (Book) -> Unit,
            onDeleteClicked: (Book) -> Unit,
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