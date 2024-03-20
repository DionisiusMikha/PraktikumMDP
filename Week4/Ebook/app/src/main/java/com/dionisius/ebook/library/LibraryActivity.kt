package com.dionisius.ebook.library

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.dionisius.ebook.adapter.LibraryAdapter
import com.dionisius.ebook.add.AddActivity
import com.dionisius.ebook.browse.BrowseActivity
import com.dionisius.ebook.data.Book
import com.dionisius.ebook.databinding.ActivityLibraryBinding
import com.dionisius.ebook.favourite.FavouriteActivity
import com.dionisius.ebook.utils.Data
import com.dionisius.ebook.utils.filterBooks
import com.dionisius.ebook.utils.toCurrency

class LibraryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLibraryBinding
    private lateinit var libraryAdapter: LibraryAdapter
    private var allBooks = Data.library

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLibraryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        libraryAdapter = LibraryAdapter(
            onDeleteClicked = {
                Data.library.remove(it)
                libraryAdapter.notifyDataSetChanged()
            },
            onFavoriteClicked = {
                it.isFavorite = !it.isFavorite
                libraryAdapter.notifyDataSetChanged()
            }
        )
        libraryAdapter.books = Data.library
        binding.rvLibrary.apply {
            adapter = libraryAdapter
            layoutManager =
                LinearLayoutManager(this@LibraryActivity, LinearLayoutManager.VERTICAL, false)
        }

        binding.btnAddBook.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        binding.clFavourite.setOnClickListener {
            val intent = Intent(this, FavouriteActivity::class.java)
            startActivity(intent)
        }

        binding.btnBrowse.setOnClickListener {
            val intent = Intent(this, BrowseActivity::class.java)
            startActivity(intent)
        }

        binding.edtSearch.addTextChangedListener {
            val query = it.toString().trim()
            val filteredBooks = filterBooks(allBooks, query)
            libraryAdapter.apply {
                books = filteredBooks as ArrayList<Book>
                notifyDataSetChanged()
            }
        }

        binding.tvBalance.text = Data.balance.toString().toCurrency()
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
        libraryAdapter.apply {
            books = Data.library
            notifyDataSetChanged()
        }
        binding.tvBalance.text = Data.balance.toString().toCurrency()
    }
}