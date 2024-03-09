package com.example.week2

class UsersVirtualAccount(nama: String = "VA", kode: Int = 12345689) : VirtualAccount(nama = nama, kode = kode) {
    var nasabah: User = userList[0]

    fun registerVA(): String {
        return "${this.nama} (${this.kode})"
    }

    constructor(nama: String, kode: Int, nasabah: User) : this() {
        this.nama = nama
        this.kode = kode
        this.nasabah = nasabah
    }

    override fun toString(): String {
        return "${this.nama} - ${this.kode}"
    }
}
