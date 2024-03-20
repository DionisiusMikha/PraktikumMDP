package com.dionisius.ebook.utils

import java.text.NumberFormat
import java.util.Locale

fun String.toCurrency(locale: Locale = Locale("id", "ID")) = run {
    val numberFormat = NumberFormat.getCurrencyInstance(locale)
    numberFormat.minimumFractionDigits = 0
    numberFormat.format(this.toInt()).toString()
}