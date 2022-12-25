import kotlin.math.pow

class Day25(input: List<String>) {

    val numbers = input.map { it.toCharArray().toTypedArray().toList() }

    fun fromSnafu(snafu: List<Char>) = snafu.reversed().mapIndexed { index, digit ->
        val value1 = 5.0.pow(index).toLong()
        val value2 = when (digit) {
            '2' -> 2L
            '1' -> 1L
            '0' -> 0L
            '-' -> -1L
            '=' -> -2L
            else -> throw RuntimeException("Unknown digit")
        }
        value1 * value2
    }.sum()


    fun part1() {
        var result = numbers.map { snafu -> fromSnafu(snafu) }.sum()

        var result2 = ""
        
        while(result > 0) {
           val p1 =  result % 5
            result2 += p1.toString()
            
            result = (result+2) /5
        }
        
        
        result2 = result2.reversed()
        result2 = result2.map { a -> 
            when(a) {
                '4' -> '='
                '3' -> '-'
                '2' -> '2'
                '1' -> '1'
                '0' -> '0'
                else -> throw RuntimeException("Unknown digit")
            }
        }.joinToString("")
        
        println(result2)
    }
}

fun main() {

    val input = readLines("day25.txt")
//    val input = readLines("day25.txt", true)

    val result = Day25(input).part1()
    println(result)

}

//2011-=2=-1020-1===-1
//2011=-2-=1020=1---=1
