package com.dionisius.ebook.browse

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.dionisius.ebook.R
import com.dionisius.ebook.adapter.BrowseAdapter
import com.dionisius.ebook.data.Book
import com.dionisius.ebook.databinding.ActivityBrowseBinding
import com.dionisius.ebook.utils.Data
import com.dionisius.ebook.utils.filterBooks
import com.dionisius.ebook.utils.toCurrency

class BrowseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBrowseBinding
    private lateinit var browseAdapter: BrowseAdapter
    private var allBooks = Data.books

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBrowseBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        browseAdapter = BrowseAdapter(
            onBuyTap = { book ->
                if (Data.balance - book.price >= 0) {
                    book.isOnLibrary = true
                    Data.apply {
                        library.add(book)
                        balance -= book.price
                    }
                    binding.tvBalance.text = Data.balance.toString().toCurrency()
                } else
                    Toast.makeText(this, "Saldo tidak cukup!", Toast.LENGTH_SHORT).show()
            }
        )
        browseAdapter.availableBooks = Data.books
        binding.rvBrowse.apply {
            adapter = browseAdapter
            layoutManager =
                LinearLayoutManager(this@BrowseActivity, LinearLayoutManager.VERTICAL, false)
        }

        binding.btnBrowse.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.edtSearch.addTextChangedListener {
            val query = it.toString().trim()
            val filteredBooks = filterBooks(allBooks, query)
            browseAdapter.availableBooks = filteredBooks as ArrayList<Book>
            browseAdapter.notifyDataSetChanged()
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

    override fun onResume() {
        super.onResume()
        browseAdapter.availableBooks = Data.books
        browseAdapter.notifyDataSetChanged()
        binding.tvBalance.text = Data.balance.toString().toCurrency()
    }
}