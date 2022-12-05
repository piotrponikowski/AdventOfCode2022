class Day5(input: String) {

    val pattern = Regex("""^move (\d+) from (\d+) to (\d+)$""")


    val max = 8
    val parts = input.split(System.lineSeparator().repeat(2))

    val start = parts[0].split(System.lineSeparator())
    val instructions = parts[1].split(System.lineSeparator())


    val instructions2 = instructions.map { pattern.matchEntire(it)!!.destructured }

    fun solve(max: Int): String {

        val port = (0..max).map { stackIndex ->
            start.dropLast(1).map { line -> line[stackIndex * 4 + 1] }.filter { it != ' ' }
        }

        return instructions2
            .fold(port) { state, ins ->
                val (a, b, c) = ins

                val count = a.toInt()
                val from = b.toInt() - 1
                val to = c.toInt() - 1

//            println(state)
//            println("$count, $from, $to")


                val step = state.mapIndexed { index, stack ->
                    when {
                        index == from -> stack.drop(count)
                        index == to -> state[from].take(count) + stack
                        else -> stack
                    }
                }
//            println(step)
//            println()


                step
            }.map { it.first().toString() }
            .joinToString("")
    }

    fun part1() = solve(2)
    
    fun part2() = solve(8)
}

fun main() {

    val input = readText("day5.txt")
//    val input = readText("day5.txt", true)

    val day = Day5(input)
    val r = Day5(input).part1()
    println(r)
}