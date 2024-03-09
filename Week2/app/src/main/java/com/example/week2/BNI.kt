package com.example.week2

class BNI(namaBank: String = "BNI", biayaTF: Int = 6000, biayaVA: Int = 1000) : Bank(namaBank = namaBank, biayaTF = biayaTF, biayaVA = biayaVA) {
    constructor(nomor: Int) : this() {
        noRek = nomor
        this.namaBank = namaBank
        this.biayaTF = biayaTF
        this.biayaVA = biayaVA
    }
}
