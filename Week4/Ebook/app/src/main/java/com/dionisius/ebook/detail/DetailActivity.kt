package com.dionisius.ebook.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dionisius.ebook.R
import com.dionisius.ebook.databinding.ActivityDetailBinding
import com.dionisius.ebook.utils.Data
import com.dionisius.ebook.utils.toCurrency

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private var title = ""
    private var author = ""
    private var synopsis = ""
    private var price = 0L
    private var isOnLibrary = false

    override fun onCreate(savedInstanceState: Bundle?) {
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(detailBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        title = intent.getStringExtra("title").toString()
        author = intent.getStringExtra("author").toString()
        price = intent.getLongExtra("price", 0)
        synopsis = intent.getStringExtra("synopsis").toString()
        isOnLibrary = intent.getBooleanExtra("isOnLibrary", false)

        setupView(title, author, price, synopsis, isOnLibrary)
    }

    @SuppressLint("SetTextI18n")
    private fun setupView(
        title: String,
        author: String,
        price: Long,
        synopsis: String,
        isOnLibrary: Boolean,
    ) {
        detailBinding.apply {
            tvTitle.text = title
            tvAuthor.text = author
            tvPrice.text = price.toString().toCurrency()
            tvSynopsis.text = synopsis
            tvBalance.text = Data.balance.toString().toCurrency()
            ivWallet.setOnClickListener {
                Data.balance += 100000
                tvBalance.text = Data.balance.toString().toCurrency()
            }
            tvBalance.setOnClickListener {
                Data.balance += 100000
                tvBalance.text = Data.balance.toString().toCurrency()
            }

            if (isOnLibrary) {
                tvPrice.visibility = View.GONE
                btnBuy.visibility = View.GONE
            }
            else {
                btnBuy.apply {
                    text = "Buy ${price.toString().toCurrency()}"
                    setOnClickListener {
                        if (Data.balance - price >= 0) {
                            val book = Data.books.find {
                                it.title == title
                                        && it.author == author
                                        && it.synopsis == synopsis
                            }
                            book!!.isOnLibrary = true
                            Data.apply {
                                library.add(book)
                                balance -= price
                            }
                            tvBalance.text = Data.balance.toString().toCurrency()
                            btnBuy.visibility = View.GONE
                        } else
                            Toast.makeText(this@DetailActivity, "Saldo tidak cukup!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}