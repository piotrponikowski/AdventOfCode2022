import kotlin.math.pow

class Day25(val input: List<String>) {
    
    private fun fromSnafu(input: String, index: Int = 0):Long  {
        return when(input.isNotEmpty()) {
            true -> fromSnafu(input.dropLast(1), index + 1) + (5.0.pow(index).toLong() * decode(input.last()))
            false -> 0
        }
    }

    private fun toSnafu(input: Long): String {
        return when (input > 0) {
            true -> toSnafu((input + 2) / 5) + encode(input % 5)
            false -> ""
        }
    }

    private fun encode(input: Long) = when (input) {
        4L -> '-'
        3L -> '='
        2L -> '2'
        1L -> '1'
        0L -> '0'
        else -> throw RuntimeException("Unknown digit")
    }

    private fun decode(input: Char) = when (input) {
        '2' -> 2L
        '1' -> 1L
        '0' -> 0L
        '-' -> -1L
        '=' -> -2L
        else -> throw RuntimeException("Unknown digit")
    }

    fun part1() = toSnafu(input.sumOf { snafu -> fromSnafu(snafu) })
}