class Day10(input: List<String>) {

    val instructions = input


    fun solve() :String {
        val cycles = mutableListOf(1)
        val picture = mutableListOf<Char>()

        instructions.forEach { instruction ->

//            if (picture.isNotEmpty()) {
//                println(picture.chunked(40)
//                    .map { line -> line.joinToString("") }.last()
//                )
//            }
            
//            if(picture.size > 196) {
//                println()
//            }


            val repeatTimes = if (instruction == "noop") 1 else 2

            repeat(repeatTimes) {
                val crtPosition = (picture.size % 40) + 1
                val spritePosition = (cycles.last()..(cycles.last() + 2))

                if (crtPosition in spritePosition) {
                    picture += '#'
                } else {
                    picture += '.'
                }
            }

            if (instruction == "noop") {
                cycles += cycles.last()

            } else {
                val add = instruction.split(" ").last().toInt()
                cycles += cycles.last()
                cycles += cycles.last() + add
            }
        }

//        println(cycles)
//        println(cycles[20])
//        println(cycles[60])
//
        val c = listOf(20, 60, 100, 140, 180, 220)
        val r = c.map { a -> cycles[a - 1] * a }.sum()
        return picture.chunked(40).map { line -> line.joinToString("") }.joinToString(System.lineSeparator())
    }


    fun part1() = 1

    fun part2() = solve()
}

fun main() {

    val input = readLines("day10.txt")
//    val input = readLines("day10.txt", true)

    val result = Day10(input).solve()
    println(result)

}