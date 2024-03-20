package com.dionisius.ebook.add

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dionisius.ebook.R
import com.dionisius.ebook.data.Book
import com.dionisius.ebook.databinding.ActivityAddBinding
import com.dionisius.ebook.utils.Data
import kotlin.random.Random

class AddActivity : AppCompatActivity() {

    private lateinit var addBinding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(addBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        addBinding.btnAdd.setOnClickListener {
            addNewBook()
        }
    }

    private fun validateForm(): Boolean =
        addBinding.edtTitle.text.toString().isNotEmpty()
                && addBinding.edtAuthor.text.toString().isNotEmpty()
                && addBinding.edtSynopsis.text.toString().isNotEmpty()

    private fun addNewBook() {
        if (validateForm()) {
            val title = addBinding.edtTitle.text.toString()
            val author = addBinding.edtAuthor.text.toString()
            val synopsis = addBinding.edtSynopsis.text.toString()
            val newBook = Book(
                title = title,
                author = author,
                synopsis = synopsis,
                price = Random.nextLong(50000, 300000)
            )
            Data.books.add(newBook)

            if (newBook in Data.books) {
                Toast
                    .makeText(this, "Sukses menambahkan buku!", Toast.LENGTH_SHORT)
                    .show()
                onBackPressedDispatcher.onBackPressed()
            } else {
                Toast.makeText(this, "Gagal menambahkan buku", Toast.LENGTH_SHORT).show()
            }

        } else
            Toast.makeText(this, "Lengkapi semua form terlebih dahulu", Toast.LENGTH_SHORT).show()
    }
}