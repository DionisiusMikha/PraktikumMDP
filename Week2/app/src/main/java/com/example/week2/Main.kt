package com.example.week2


val userList: ArrayList<User> = arrayListOf()
val accountTypes: ArrayList<Tabungan> = arrayListOf()
val listVA: ArrayList<VirtualAccount> = arrayListOf()


fun initializeData() {
    accountTypes.apply {
        add(Tabungan("Silver", 10000, 0.01, 15000))
        add(Tabungan("Gold", 15000, 0.025, 50000))
    }

    userList.apply {
        add(User("qwe", "qwe", "qwe", BCA(11111), accountTypes[0], 100000))
        add(User("123", "123", "123", BCA(22222), accountTypes[0], 100000))
        add(User("asd", "asd", "asd", BNI(33333), accountTypes[1], 200000))
        add(User("rty", "rty", "rty", BNI(44444), accountTypes[1], 200000))
        add(User("zxc", "zxc", "zxc", CIMB(55555), accountTypes[0], 120000))
        add(User("vbn", "vbn", "vbn", CIMB(66666), accountTypes[0], 120000))
    }

    listVA.apply {
        add(VirtualAccount("GOPAY", 123))
        add(VirtualAccount("OVO", 456))
        add(VirtualAccount("DANA", 789))
    }
}



fun main() {
    initializeData()

    do {
        println("Welcome to Bank App")
        println("1. Login")
        println("2. Register")
        println("3. Exit")
        print(">> ")
        val input = readLine()?.toIntOrNull()
        println()

        when (input) {
            1 -> login()
            2 -> register()
            3 -> println("Goodbye!")
            else -> println("Invalid Input!")
        }
    } while (input != 3)
}

fun chooseBank(): Int {
    var bankName = 0
    do {
        println("Pilih Bank")
        println("1. BCA")
        println("2. BNI")
        println("3. CIMB")
        bankName = readLine()?.toIntOrNull() ?: 0
    } while (!(bankName in 1..3))
    println()
    return bankName
}

fun chooseAccountType(): Int {
    var jenis = 0
    do {
        println("Jenis Tabungan")
        println("1. Silver")
        println("2. Gold")
        print(">> ")
        jenis = readLine()?.toIntOrNull() ?: 0
    } while (!(jenis in 1..2))
    println()
    return jenis
}


fun register() {
    var option = true
    do {
        println("REGISTER")
        print("Nama: ")
        var nama = readLine()
        print("Username: ")
        var username = readLine()
        print("Password: ")
        var password = readLine()
        print("Confirm Password: ")
        var confpass = readLine()

        val cekUser = userList.find { it.username == username }
        if (cekUser != null) {
            println("Username '$username' already exists.")
            println()
        } else {
            if (password != confpass) {
                println("Password tidak sama")
                println()
            } else {
                option = false
                val bankName = chooseBank()
                val jenis = chooseAccountType()

                var cekSetoran = true
                do {
                    print("Setoran Awal: ")
                    var setoran = readLine()?.toIntOrNull() ?: 0
                    val tabungan = accountTypes[jenis - 1]

                    if (setoran < tabungan.saldoMinim) {
                        println("Saldo kurang!")
                        cekSetoran = true
                    } else {
                        val noRek = (10000..99999).random()
                        val bank = when (bankName) {
                            1 -> BCA(noRek)
                            2 -> BNI(noRek)
                            else -> CIMB(noRek)
                        }
                        userList.add(User(nama ?: "", username ?: "", password ?: "", bank, tabungan, setoran))
                        println("Berhasil register nasabah baru!")
                        println("Nomor rekening: $noRek")
                        cekSetoran = false
                        println()
                    }
                } while (cekSetoran)
            }
        }
    } while (option)
}


fun menuAdmin() {
    do {
        println("ADMIN MENU")
        println("1. List of Customers")
        println("2. List of Virtual Accounts")
        println("3. Change Month")
        println("4. Exit")
        print(">> ")
        var menuAdmin = readLine()?.toIntOrNull() ?: 0
        println()

        when (menuAdmin) {
            1 -> {
                var cek1 = true
                do {
                    println("List of Customers")
                    println("1. BCA Bank Customers")
                    println("2. BNI Bank Customers")
                    println("3. CIMB Bank Customers")
                    println("99. Back")
                    var pilihBank = readLine()?.toIntOrNull() ?: 0
                    println()

                    when (pilihBank) {
                        1, 2, 3 -> {
                            var bool = true
                            do {
                                when (pilihBank) {
                                    1 -> println("BCA Bank Customers")
                                    2 -> println("BNI Bank Customers")
                                    3 -> println("CIMB Bank Customers")
                                }
                                Bank.listNasabah(pilihBank)
                                println("99. Back")
                                print(">> ")
                                var back = readLine()?.toIntOrNull() ?: 0
                                println()
                                if (back == 99) {
                                    bool = false
                                }
                            } while (bool)
                        }
                        99 -> cek1 = false
                    }
                } while (cek1)
            }
            2 -> {
                var cek2 = true
                do {
                    println("List of Virtual Accounts")
                    if (listVA.isEmpty()) {
                        println("Virtual Accounts Not Registered Yet")
                    } else {
                        VirtualAccount.listVirtualAccounts(listVA)
                    }
                    print("""
                        98. Add VA
                        99. Back
                        >> 
                    """.trimIndent())
                    var pilihVA = readLine()?.toIntOrNull() ?: 0
                    println()

                    when (pilihVA) {
                        98 -> {
                            print("VA Name: ")
                            var VAName = readLine()
                            print("VA Code: ")
                            var VACode = readLine()?.toIntOrNull() ?: 0

                            if (listVA.none { it.kode == VACode }) {
                                if (!VAName.isNullOrEmpty() && VACode in 100..999) {
                                    listVA.add(VirtualAccount(VAName.toUpperCase(), VACode))
                                    println("Successfully added VA ${VAName.toUpperCase()} with code $VACode")
                                    println()
                                } else {
                                    println("Invalid Input! VA Name cannot be empty and VA Code must consist of 3 digits!")
                                    println()
                                }
                            } else {
                                println("VA Name is already registered!")
                                println()
                            }
                        }
                        99 -> cek2 = false
                    }
                } while (cek2)
            }
            3 -> {
                println("Successfully changed the month!")
                for (user in userList) {
                    val adminFee = user.jenisTabungan.biayaAdmin
                    val interest = (user.jenisTabungan.bungaBulanan * user.saldo).toInt()
                    user.saldo -= adminFee + interest
                    user.listMutasi.add("(- $adminFee) Monthly Admin Fee")
                    user.listMutasi.add("(+ $interest) Monthly Interest")
                }
            }
        }
    } while (menuAdmin != 4)
}

fun login() {
    println("LOGIN")
    print("Username: ")
    var username = readLine() ?: ""
    print("Password: ")
    var password = readLine() ?: ""

    if (username == "admin" && password == "admin") {
        println("Login... Admin")
        menuAdmin()
    } else {
        val cekUser = userList.firstOrNull { it.username == username }
        if (cekUser != null) {
            if (cekUser.password == password) {
                println("Login... as ${cekUser.nama}")
                menuNasabah(cekUser)
            } else {
                println("Password salah!")
            }
        } else {
            println("User tidak ditemukan")
            println()
        }
    }
}

fun menuNasabah(nasabah: User) {
    do {
        println()
        with(nasabah) {
            println("""
                Welcome, $nama
                Nomor Rekening: ${bank.noRek}
                Tabungan: ${bank.namaBank} ${jenisTabungan.jenis}
                Saldo: Rp $saldo
                1. Mutasi
                2. Daftar Transfer
                3. Setor
                4. Transfer Antar Rekening
                5. Transfer Antar Bank
                6. Transfer Virtual Account
                7. Logout
                >> 
            """.trimIndent())
        }
        val menuNasabah = readln()
        println()
        when (menuNasabah) {
            "1" -> mutasi(nasabah)
            "2" -> daftarTransfer(nasabah)
            "3" -> setor(nasabah)
            "4" -> tfAntarRekening(nasabah)
            "5" -> tfAntarBank(nasabah)
            "6" -> tfVA(nasabah)
        }
    } while (menuNasabah != "7")
}

fun mutasi(nasabah: User) {
    println("Mutasi")
    if (nasabah.listMutasi.isEmpty()) {
        println("Mutasi tidak ditemukan")
    } else {
        for (mutasi in nasabah.listMutasi) {
            println(mutasi)
        }
    }
    println()
}

fun daftarTransfer(nasabah: User) {
    println("Daftar Transfer")
    for (user in userList) {
        if (user != nasabah) {
            println(user.daftarTransfer())
        }
    }
    println()
}

fun setor(nasabah: User) {
    println("Setor")
    print("Jumlah: ")
    val jumlah = readln().toIntOrNull() ?: 0
    if (jumlah > 0) {
        nasabah.saldo += jumlah
        nasabah.listMutasi.add("(+ $jumlah) Setor")
        println("Berhasil setor Rp $jumlah")
    } else {
        println("Jumlah setoran tidak valid")
    }
    println()
}

fun tfAntarRekening(nasabah: User) {
    println("Transfer Antar Rekening")

    // Mendapatkan daftar rekening tujuan yang memiliki bank yang sama dengan nasabah
    val rekeningTujuanSamaBank = nasabah.listRekening.filter { it.bank.namaBank == nasabah.bank.namaBank }

    if (rekeningTujuanSamaBank.isNotEmpty()) {
        println("Pilih Rekening Tujuan")
        rekeningTujuanSamaBank.forEachIndexed { index, user ->
            println("${index + 1}. ${user.nama} - ${user.bank.noRek}")
        }

        print(">> ")
        val rekTujuanIdx = readLine()?.toIntOrNull()?.minus(1) ?: -1

        if (rekTujuanIdx in 0 until rekeningTujuanSamaBank.size) {
            val tujuan = rekeningTujuanSamaBank[rekTujuanIdx]
            print("Jumlah: ")
            val jumlah = readLine()?.toIntOrNull() ?: 0

            if (jumlah > 5000 && jumlah <= nasabah.saldo - nasabah.jenisTabungan.saldoMinim) {
                nasabah.saldo -= jumlah
                tujuan.saldo += jumlah
                nasabah.listMutasi.add("(- $jumlah) Transfer ke ${tujuan.nama} (${tujuan.bank.noRek})")
                tujuan.listMutasi.add("(+ $jumlah) Transfer dari ${nasabah.nama} (${nasabah.bank.noRek})")
                println("Berhasil transfer Rp $jumlah ke ${tujuan.nama} (${tujuan.bank.noRek})")
                println("Sisa saldo Anda: ${nasabah.saldo}")
            } else {
                println("Jumlah transfer tidak valid atau saldo tidak mencukupi")
            }
        } else {
            println("Rekening tujuan tidak ditemukan")
        }
    } else {
        println("Tidak ada rekening tujuan yang memiliki bank yang sama")
    }
    println()
}

fun tfAntarBank(nasabah: User) {
    println("Transfer Antar Bank")

    // Menampilkan daftar bank tujuan transfer
    println("Pilih Bank Tujuan")
    println("1. BCA")
    println("2. BNI")
    println("3. CIMB")
    print(">> ")
    val bankTujuan = readLine()?.toIntOrNull() ?: 0

    if (bankTujuan in 1..3) {
        val namaBankTujuan = when (bankTujuan) {
            1 -> "BCA"
            2 -> "BNI"
            else -> "CIMB"
        }

        val listTujuan = Bank.getNasabah(userList, namaBankTujuan)

        if (listTujuan.isNotEmpty()) {
            println("Pilih Rekening Tujuan")
            listTujuan.filter { it != nasabah }.forEachIndexed { index, user ->
                println("${index + 1}. ${user.daftarTransfer()}")
            }

            print("masukkan nomor rekening tujuan:")
            val rekTujuan = readLine()?.toIntOrNull() ?: 0

            val tujuan = listTujuan.find { it.bank.noRek == rekTujuan }
            if (tujuan != null) {
                val biayaAdmin = 2500
                println("Biaya admin untuk transfer ke bank ${tujuan.bank.namaBank}: Rp $biayaAdmin")

                print("Jumlah: ")
                val jumlah = readLine()?.toIntOrNull() ?: 0

                if (jumlah > 5000 && nasabah.saldo >= jumlah + biayaAdmin) {
                    nasabah.saldo -= jumlah + biayaAdmin
                    tujuan.saldo += jumlah
                    nasabah.listMutasi.add("(- $jumlah) Transfer ke ${tujuan.nama} (${tujuan.bank.noRek})")
                    nasabah.listMutasi.add("(- $biayaAdmin) Biaya Admin Transfer ke ${tujuan.bank.namaBank}")
                    tujuan.listMutasi.add("(+ $jumlah) Transfer dari ${nasabah.nama} (${nasabah.bank.noRek})")
                    println("Berhasil transfer Rp $jumlah ke ${tujuan.nama} (${tujuan.bank.noRek})")
                    println("Sisa saldo Anda: ${nasabah.saldo}")
                } else {
                    println("Jumlah transfer tidak valid atau saldo tidak mencukupi")
                }
            } else {
                println("Rekening tujuan tidak ditemukan")
            }
        } else {
            println("Tidak ada nasabah di bank tujuan")
        }
    } else {
        println("Bank tujuan tidak valid")
    }
    println()
}

fun tfVA(nasabah: User) {
    println("Transfer Virtual Account")
}



