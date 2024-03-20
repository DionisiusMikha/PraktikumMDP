package com.dionisius.ebook.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dionisius.ebook.data.Book
import com.dionisius.ebook.databinding.ItemListBrowseBookBinding
import com.dionisius.ebook.detail.DetailActivity

class BrowseAdapter(
    private val onBuyTap: (Book) -> Unit
) : RecyclerView.Adapter<BrowseAdapter.BrowseViewHolder>() {

    var availableBooks = ArrayList<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseViewHolder {
        val view = ItemListBrowseBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BrowseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrowseViewHolder, position: Int) {
        holder.bindView(availableBooks[position], onBuyTap)
    }

    override fun getItemCount(): Int = availableBooks.size

    class BrowseViewHolder(private val view: ItemListBrowseBookBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bindView(book: Book, onBuyTap: (Book) -> Unit) {
            view.apply {
                tvTitle.text = book.title
                tvAuthor.text = book.author

                if (book.isOnLibrary)
                    view.btnBuy.visibility = View.GONE

                btnBuy.setOnClickListener {
                    onBuyTap(book)
                    if (book.isOnLibrary)
                        view.btnBuy.visibility = View.GONE
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