import org.intellij.lang.annotations.Pattern

class Day21(input: List<String>) {

    private val numericPattern = Regex("""^\d+$""")

    val monkeys = input.map { line ->
        println(line)
        val (name, type) = line.split(": ")

        if (type.matches(numericPattern)) {
            ValueMonkey(name, type.toLong())
        } else {
            val (arg1, action, arg2) = type.split(" ")
            OperationMonkey(name, arg1, arg2, action)
        }
    }

    fun solve() {
        val solution = mutableMapOf<String, Long>()
        val unsolvedMonkeys = monkeys.toMutableList()

        while (unsolvedMonkeys.isNotEmpty()) {
            val solvedMonkeys = mutableSetOf<Monkey>()
            for (monkey in unsolvedMonkeys) {
                when (monkey) {
                    is ValueMonkey -> {
                        solution[monkey.name] = monkey.value
                        solvedMonkeys += monkey
                        
                        println("Solved: ${monkey.name}")
                    }
                    is OperationMonkey -> {
                        val val1 = solution[monkey.arg1]
                        val val2 = solution[monkey.arg2]
                        if (val1 != null && val2 != null) {
                            solution[monkey.name] = monkey.calculate(val1, val2)
                            solvedMonkeys += monkey
                            
                            println("Solved: ${monkey.name}")
                        }
                    }
                }
            }

            unsolvedMonkeys -= solvedMonkeys
        }
        
        println(solution)
        println(solution["root"])
    }

    fun part1()  {
        solve()
    }

    fun part2() = 2


    sealed interface Monkey

    data class ValueMonkey(val name: String, val value: Long) : Monkey

    data class OperationMonkey(val name: String, val arg1: String, val arg2: String, val action: String) : Monkey {
        fun calculate(val1: Long, val2: Long) =
            when (action) {
                "+" -> val1 + val2
                "-" -> val1 - val2
                "*" -> val1 * val2
                "/" -> val1 / val2
                else -> throw RuntimeException("Unknown operation $action")
            }
    }
}

fun main() {

    val input = readLines("day21.txt")
//    val input = readLines("day21.txt", true)

    val result = Day21(input).part1()
    println(result)

}