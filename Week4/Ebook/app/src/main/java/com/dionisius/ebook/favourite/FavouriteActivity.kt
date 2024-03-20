package com.dionisius.ebook.favourite

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dionisius.ebook.adapter.FavoriteAdapter
import com.dionisius.ebook.data.Book
import com.dionisius.ebook.databinding.ActivityFavouriteBinding
import com.dionisius.ebook.utils.Data
import com.dionisius.ebook.utils.toCurrency

class FavouriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        favoriteAdapter = FavoriteAdapter(
            onDeleteClicked = {
                Data.library.remove(it)
                favoriteAdapter.notifyDataSetChanged()
            },
            onFavoriteClicked = { book ->
                book.isFavorite = !book.isFavorite
                favoriteAdapter.favoriteBooks = Data.books.filter { it.isFavorite } as ArrayList<Book>
                favoriteAdapter.notifyDataSetChanged()
            }
        )
        favoriteAdapter.favoriteBooks = Data.books.filter { it.isFavorite } as ArrayList<Book>
        binding.rvFavourite.apply {
            adapter = favoriteAdapter
            layoutManager =
                LinearLayoutManager(this@FavouriteActivity, LinearLayoutManager.VERTICAL, false)
        }

        binding.tvBalance.text = Data.balance.toString().toCurrency()

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.ivWallet.setOnClickListener {
            Data.balance += 100000
            binding.tvBalance.text = Data.balance.toString().toCurrency()
        }
        binding.tvBalance.setOnClickListener {
            Data.balance += 100000
            binding.tvBalance.text = Data.balance.toString().toCurrency()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        favoriteAdapter.favoriteBooks = Data.books.filter { it.isFavorite } as ArrayList<Book>
        favoriteAdapter.notifyDataSetChanged()
        binding.tvBalance.text = Data.balance.toString().toCurrency()
    }
}