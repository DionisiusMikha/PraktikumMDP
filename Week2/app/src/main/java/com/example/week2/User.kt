package com.example.week2

open class User(
    var nama: String,
    var username: String,
    var password: String,
    var bank: Bank,
    var jenisTabungan: Tabungan,
    var saldo: Int = 0
) {
    var listRekening: MutableList<User> = mutableListOf()
    var listMutasi: MutableList<String> = mutableListOf()


    fun daftarTransfer(): String {
        return "${this.nama} (${this.bank.noRek})"
    }



    override fun toString(): String {
        return "${this.nama} - ${this.jenisTabungan.jenis} - ${this.bank.noRek}"
    }
}
