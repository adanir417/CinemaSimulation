package CinemaSimulation




var rowNumber = 0
var columnNumber = 0

var rowOfSeat: MutableList<MutableList<String>>? = null

var customerRowNumber = 0
var customerColumnNumber = 0
var exit = 0
var secim = 0

val currentIncomeS = "Current income:"
var currentIncome = 0

val purchasedTicketS = "Number of purchased tickets:"
var purchasedTicket = 0

val percentageS = "Percentage:"
var percentage = 0.0
var formatPercentage = ""

val totalIncomeS = "Total income:"
var totalIncome = 0

val menuYazisi = """
1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
""".trim()


fun cinemaStart() {

    takeRowAndColumnNum()

    println()

    //rowOfSeat = MutableList(rowNumber) { mutableListOf<String>() }

    val roomSize = columnNumber * rowNumber

    writeTickets(rowNumber, columnNumber)

    while (exit == 0) {
        println(menuYazisi)
        secim = readln().toInt()
        println()

        when (secim) {
            0 -> {
                exit = 1
            }

            1 -> {

                showCinemaSeats(rowNumber, columnNumber)
                println()
            }

            2 -> {

                buyTicket(roomSize)

            }

            3 -> {
                println()
                showStatistics(roomSize)
                println()


            }

        }
    }

}


fun takeRowAndColumnNum() {
    try {
        println("Enter the number of rows:")
        rowNumber = readln().toInt()
        if (rowNumber > 9) {
            //println("RowIF")
            throw Exception("number can't be over 9")
            //rowNumber = 1
        }



        println("Enter the number of seats in each row:")
        columnNumber = readln().toInt()
        if (columnNumber > 9) {
           // println("ColumnIF")
            throw Exception("number can't be over 9")
            //columnNumber = 1
            }


        println()
        rowOfSeat = MutableList(rowNumber) { mutableListOf<String>() }
    }catch (illegal: IllegalArgumentException) {
        takeRowAndColumnNum()
    }
    catch (e: Exception)
    {
        println(e.message)
        takeRowAndColumnNum()
    }


}

fun buyTicket(roomSize: Int) {
    try {
        println("Enter a row number:")
        customerRowNumber = readln().toInt()

        println("Enter a seat number in that row:")
        customerColumnNumber = readln().toInt()

        if (isSeatReserved(customerRowNumber, customerColumnNumber)) {
            println()
            print("That ticket has already been purchased!")
            println()
            buyTicket(roomSize)
        } else {
            changeSeat(customerRowNumber, customerColumnNumber)
            println()
            calculate(roomSize)
            println()
        }
    } catch (indexFault: IndexOutOfBoundsException) {
        println("Wrong input!")
        buyTicket(roomSize)
    }
}

fun calculate(roomSize: Int) {
    if (!isLargeRoom(roomSize)) {

        print("Ticket price : $${calculateTicketPriceForSmallRoom(roomSize)}")
        purchasedTicket++
        currentIncome += calculateTicketPriceForSmallRoom(roomSize)
        println()

    } else {

        print(
            "Ticket price : $${
                calculateTicketPriceForLargeRoom(
                    rowNumber, columnNumber, customerRowNumber,
                    customerColumnNumber
                )
            }"
        )
        purchasedTicket++
        currentIncome += calculateTicketPriceForLargeRoom(
            rowNumber, columnNumber, customerRowNumber,
            customerColumnNumber
        )
        println()


    }
}


fun calculateTotalPrice(roomSize: Int) {
    if (!isLargeRoom(roomSize)) {
        totalIncome = calculateTicketPriceTotalForSmallRoom(roomSize)
    } else {
        totalIncome = calculateTicketPriceTotalForLargeRoom(rowNumber, columnNumber)
    }
}


fun showStatistics(roomSize: Int) {
    calculateTotalPrice(roomSize)
    percentage = ((purchasedTicket.toDouble() / roomSize.toDouble()) * 100)
    formatPercentage = "%.2f".format(percentage)

    println("${purchasedTicketS} ${purchasedTicket}")
    println("${percentageS} ${formatPercentage}%")
    println("${currentIncomeS} $${currentIncome}")
    println("${totalIncomeS} $${totalIncome}")
}

fun isLargeRoom(roomSize: Int): Boolean {
    return roomSize > 60
}

fun calculateTicketPriceTotalForSmallRoom(roomSize: Int): Int {
    return roomSize * 10
}

fun calculateTicketPriceForSmallRoom(roomSize: Int): Int {
    return 10
}


fun calculateTicketPriceTotalForLargeRoom(row: Int, eachRow: Int): Int {
    val firstRowPrice = ((row / 2) * eachRow) * 10
    val secondRowPrice = ((row - (row / 2)) * eachRow) * 8

    return firstRowPrice + secondRowPrice
}

fun calculateTicketPriceForLargeRoom(row: Int, column: Int, customerRow: Int, customerColumn: Int): Int {
    if ((customerRow <= (row / 2))) return 10 else return 8

}

fun showCinemaSeats(row: Int, column: Int) {
    println("Cinema:")
    for (i in 0..row) {

        if (i >= 1) {
            print("$i")
        }
        print(" ")
        for (j in 1..column) {

            if (i == 0) {
                print(" ")
                print("${j}")
            }
            if (i != 0) {
                //print("S ")
                print("${showSeat(i - 1, j - 1)} ")
            }
            if (j == column) {
                println()
            }

        }


    }
}

fun writeTickets(row: Int, column: Int) {

    try {
        for (rOfS in 0 until row) {
            for (cOfS in 0 until column) {
                rowOfSeat!![rOfS].add("S")
            }
        }

    } catch (e: Exception) {
        println(e.message)
        e.printStackTrace()
    }


}

fun changeSeat(row: Int, column: Int) {
    rowOfSeat!![row - 1][column - 1] = "B"
}

fun showSeat(row: Int, column: Int): String {
    if (rowOfSeat!![row][column] == "B") {
        return "B"
    } else {
        return "S"
    }
}

fun isSeatReserved(row: Int, column: Int): Boolean {
    return rowOfSeat!![row - 1][column - 1] == "B"
}