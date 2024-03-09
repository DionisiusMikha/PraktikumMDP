package com.example.week2

open class VirtualAccount(var nama: String, var kode: Int) {
    override fun toString(): String {
        return "${this.nama} (${this.kode})"
    }

    companion object {
        fun listVirtualAccounts(list: ArrayList<VirtualAccount>) {
            val sortedVA = list.sortedBy { it.nama.first() }
            for ((idx, va) in sortedVA.withIndex()) {
                println("${idx + 1}. ${va.toString()}")
            }
        }

    }
}
