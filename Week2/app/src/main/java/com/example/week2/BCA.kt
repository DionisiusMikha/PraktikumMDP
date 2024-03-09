package com.example.week2

class BCA(namaBank: String = "BCA", biayaTF: Int = 2500, biayaVA: Int = 1000) : Bank(namaBank = namaBank, biayaTF = biayaTF, biayaVA = biayaVA) {
    constructor(nomor: Int) : this() {
        noRek = nomor
        this.namaBank = namaBank
        this.biayaTF = biayaTF
        this.biayaVA = biayaVA
    }
}
